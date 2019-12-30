package com.tcmis.client.catalog.process;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.logging.*;

import com.tcmis.common.admin.process.*;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.client.catalog.beans.*;
import com.tcmis.client.catalog.factory.*;

/******************************************************************************
 * Process for allocation analysis
 * @version 1.0
 *****************************************************************************/
public class CatalogSpecProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public CatalogSpecProcess(String client) {
    super(client);
  }
  
  public CatalogSpecProcess(String client,String locale) {
	    super(client,locale);
  }

  public Collection getCatalogDropDown(BigDecimal personnelId) throws
      BaseException, Exception {
    BigDecimal itemId = null;
    String partNumber = null;
    DbManager dbManager = new DbManager(getClient(),getLocale());
    PersonnelCatalogViewBeanFactory factory = new
        PersonnelCatalogViewBeanFactory(dbManager);
    SearchCriteria criteria = new SearchCriteria("personnelId", SearchCriterion.EQUALS, personnelId.toString());
    return factory.selectDistinct(criteria);
  }

  public Collection getSearchData(CatalogSpecInputBean bean) throws
      BaseException, Exception {
    BigDecimal itemId = null;
    String partNumber = null;
    DbManager dbManager = new DbManager(getClient(),getLocale());
    CatalogSpecViewBeanFactory factory = new
        CatalogSpecViewBeanFactory(dbManager);
    SortCriteria sortCriteria = new SortCriteria();
    if("cps".equalsIgnoreCase(bean.getSortBy())) {
      sortCriteria.addCriterion("catalogId");
      sortCriteria.addCriterion("catPartNo");
      sortCriteria.addCriterion("specId");
    }
    else if("scp".equalsIgnoreCase(bean.getSortBy())) {
      sortCriteria.addCriterion("specId");
      sortCriteria.addCriterion("catalogId");
      sortCriteria.addCriterion("catPartNo");
    }
//    sortCriteria.addCriterion("catalogId");
//    sortCriteria.addCriterion("catPartNo");
//    sortCriteria.addCriterion("specId");
    if("catPartNo".equalsIgnoreCase(bean.getCriteria())) {
      SearchCriteria criteria = new SearchCriteria();
      criteria.addCriterion("catalogId", SearchCriterion.EQUALS, bean.getCatalogId());
      if (!StringHandler.isBlankString(bean.getSearch())) {
        if ("equals".equalsIgnoreCase(bean.getCriterion())) {
          criteria.addCriterion(bean.getCriteria(), SearchCriterion.EQUALS,
                                bean.getSearch());
        }
        else if ("contains".equalsIgnoreCase(bean.getCriterion())) {
          criteria.addCriterion(bean.getCriteria(), SearchCriterion.LIKE,
                                bean.getSearch());
        }
        else if ("startsWith".equalsIgnoreCase(bean.getCriterion())) {
          criteria.addCriterion(bean.getCriteria(), SearchCriterion.STARTS_WITH,
                                bean.getSearch());
        }
        else if ("endsWith".equalsIgnoreCase(bean.getCriterion())) {
          criteria.addCriterion(bean.getCriteria(), SearchCriterion.ENDS_WITH,
                                bean.getSearch());
        }
      }
      return factory.selectCatalogSpecByPart(criteria, sortCriteria);
    }
    else if("specId".equalsIgnoreCase(bean.getCriteria())) {
      return factory.selectCatalogSpecBySpec(bean.getCatalogId(), bean.getSearch(), bean.getCriterion(), sortCriteria);
    }
    return null;
  }
/*
  private Collection getNormalizedResultCollection(Collection c) throws BaseException {
    Collection normalizedCollection = new Vector(c.size());
    if(c.size() > 0) {
    try {
      Iterator flatIterator = c.iterator();
      String previousCatalogId = "";
      String previousNeededDate = "";
      String previousItem = "";
      PrOpenOrderBean mrLineBean = null;
      PrOpenOrderBean neededDateBean = null;
      PrOpenOrderBean itemBean = null;
      while (flatIterator.hasNext()) {
        PrOpenOrderBean flatBean = (PrOpenOrderBean) flatIterator.next();
        if (!previousMrLine.equalsIgnoreCase(flatBean.getPrNumber() + "-" + flatBean.getLineItem())) {
          //new bean
          if (previousMrLine.length() == 0) {
            //first time in loop
            mrLineBean = new PrOpenOrderBean();
            neededDateBean = new PrOpenOrderBean();
            itemBean = new PrOpenOrderBean();
            this.copyItemData(flatBean, itemBean);
            this.copyNeededDateData(flatBean, neededDateBean);
            this.copyMrLineData(flatBean, mrLineBean);
            this.copyNotes(mrLineBean, itemBean);
          }
          else {
            //different hub
            itemBean.incrementCounter();
            neededDateBean.incrementCounter();
            mrLineBean.incrementCounter();
            neededDateBean.addItemBean( (PrOpenOrderBean)itemBean.clone());
            mrLineBean.addNeededDateBean( (PrOpenOrderBean) neededDateBean.clone());
            normalizedCollection.add( (PrOpenOrderBean) mrLineBean.clone());
            mrLineBean = new PrOpenOrderBean();
            neededDateBean = new PrOpenOrderBean();
            itemBean = new PrOpenOrderBean();
            this.copyItemData(flatBean, itemBean);
            this.copyNeededDateData(flatBean, neededDateBean);
            this.copyMrLineData(flatBean, mrLineBean);
            this.copyNotes(mrLineBean, itemBean);
          }
        }
        else {
          //same mr line
          if (!previousNeededDate.equalsIgnoreCase(flatBean.getRequiredDatetime())) {
            //new needed date
            itemBean.incrementCounter();
            neededDateBean.incrementCounter();
            mrLineBean.incrementCounter();
            neededDateBean.addItemBean( (PrOpenOrderBean)itemBean.clone());
            mrLineBean.addNeededDateBean( (PrOpenOrderBean) neededDateBean.clone());
            neededDateBean = new PrOpenOrderBean();
            itemBean = new PrOpenOrderBean();
            this.copyItemData(flatBean, itemBean);
            this.copyNeededDateData(flatBean, neededDateBean);
            this.copyNotes(mrLineBean, itemBean);
          }
          else {
            //same needed date
            itemBean.incrementCounter();
            neededDateBean.incrementCounter();
            mrLineBean.incrementCounter();
            neededDateBean.addItemBean( (PrOpenOrderBean)itemBean.clone());
            itemBean = new PrOpenOrderBean();
            this.copyItemData(flatBean, itemBean);
            this.copyNotes(mrLineBean, itemBean);
          }
        }
        previousMrLine = flatBean.getPrNumber() + "-" + flatBean.getLineItem();
        previousNeededDate = flatBean.getRequiredDatetime();
      }
      itemBean.incrementCounter();
      neededDateBean.incrementCounter();
      mrLineBean.incrementCounter();
      neededDateBean.addItemBean( (PrOpenOrderBean) itemBean.clone());
      mrLineBean.addNeededDateBean( (PrOpenOrderBean) neededDateBean.clone());
      normalizedCollection.add( (PrOpenOrderBean) mrLineBean.clone());
    }
    catch(Exception e) {
      log.error("Error normalizing data", e);
      throw new BaseException(e);
    }
    }
    return normalizedCollection;
  }

  private void copyMrLineData(PrOpenOrderBean fromBean, PrOpenOrderBean toBean) {
    toBean.setPrNumber(fromBean.getPrNumber());
    toBean.setLineItem(fromBean.getLineItem());
    toBean.setFacilityId(fromBean.getFacilityId());
    toBean.setApplication(fromBean.getApplication());
    toBean.setRequestorFirstName(fromBean.getRequestorFirstName());
    toBean.setRequestorLastName(fromBean.getRequestorLastName());
    toBean.setItemType(fromBean.getItemType());
    toBean.setFacPartNo(fromBean.getFacPartNo());
    toBean.setPartDescription(fromBean.getPartDescription());
    toBean.setMrNotes(fromBean.getMrNotes());
    toBean.setCritical(fromBean.getCritical());
    toBean.setDeliveryType(fromBean.getDeliveryType());
    toBean.setLookAheadDays(fromBean.getLookAheadDays());
    toBean.setRequiredDatetimeSort(fromBean.getRequiredDatetimeSort());
  }

  private void copyNeededDateData(PrOpenOrderBean fromBean, PrOpenOrderBean toBean) {
    toBean.setRequiredDatetime(fromBean.getRequiredDatetime());
    toBean.setOpenQuantity(fromBean.getOpenQuantity());
    toBean.setOrderQuantity(fromBean.getOrderQuantity());
  }
*/

  public Collection getHistory(CatalogSpecInputBean bean) throws
      BaseException, Exception {

    DbManager dbManager = new DbManager(getClient(),getLocale());
    CatalogSpecHistoryBeanFactory factory = new
        CatalogSpecHistoryBeanFactory(dbManager);
    return factory.getHistory(bean);
  }

  public Collection update(Collection catalogSpecInputBeanCollection)  throws
      BaseException, Exception {
    Collection errorCollection = new Vector(0);
    if(catalogSpecInputBeanCollection != null) {
      Iterator iterator = catalogSpecInputBeanCollection.iterator();
      while(iterator.hasNext()) {
        CatalogSpecInputBean bean = (CatalogSpecInputBean)iterator.next();
        if("true".equalsIgnoreCase(bean.getNewRow()) ) {
          //log.debug("new row:" + bean.getCatPartNo());
          if (!"Y".equalsIgnoreCase(bean.getDelete()))
					{
					 this.addValue(bean);
					}
        }
        else if(this.isRowChanged(bean)) {
          try {
            this.updateCurrentValue(bean);
//log.debug("updating:" + bean.getCatPartNo());
          }
          catch(Exception e) {
            //log.error("Error changing min max levels for " + bean.getInventoryGroup() + "," + bean.getCatalogId() + "," + bean.getCatPartNo(), e);
            MailProcess.sendEmail(MailProcess.DEFAULT_FROM_EMAIL, "", MailProcess.DEFAULT_FROM_EMAIL, "Catalog Spec Change Error", e.getMessage());
            errorCollection.add(bean);
          }
        }
      }
    }
    return errorCollection;
  }

	private boolean isRowChanged(CatalogSpecInputBean bean) throws BaseException, Exception {
		if (bean.isDeleted() || bean.isCoaChanged() || bean.isCocChanged() || bean.isItarChanged()) {
			return true;
		}

		return false;
	}

  private void updateCurrentValue(CatalogSpecInputBean bean)  throws
      BaseException, Exception {
    Collection inArgs = new Vector(7);
    if(bean.getCatalogId() != null) {
      inArgs.add(bean.getCatalogId());
    }
    else {
      inArgs.add("");
    }
    if(bean.getCatPartNo() != null) {
      inArgs.add(bean.getCatPartNo());
    }
    else {
      inArgs.add("");
    }
    if(bean.getSpecId() != null) {
      inArgs.add(bean.getSpecId());
    }
    else {
      inArgs.add("");
    }
    if(bean.getCoc() != null && bean.getCoc().length() > 0) {
      inArgs.add(bean.getCoc());
    }
    else {
      inArgs.add("N");
    }
    if(bean.getCoa() != null && bean.getCoa().length() > 0) {
      inArgs.add(bean.getCoa());
    }
    else {
      inArgs.add("N");
    }
    if(bean.getPersonnelId() != null) {
      inArgs.add(bean.getPersonnelId());
    }
    else {
      inArgs.add("");
    }
    if(bean.getDelete() != null && bean.getDelete().length() > 0) {
      inArgs.add(bean.getDelete());
    }
    else {
      inArgs.add("N");
    }

    inArgs.add(bean.isItar() ? "Y" : "N");

    DbManager dbManager = new DbManager(getClient(),getLocale());
    GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
    procFactory.doProcedure("p_catalog_spec_update", inArgs);
  }

  private void addValue(CatalogSpecInputBean bean)  throws
      BaseException, Exception {

    if("No Specification".equalsIgnoreCase(bean.getSpecId())) {
      bean.setCoa("N");
      bean.setCoc("N");
    }
    Collection inArgs = new Vector(9);
    if(bean.getCatalogId() != null) {
      inArgs.add(bean.getCatalogId());
    }
    else {
      inArgs.add("");
    }
    if(bean.getCatPartNo() != null) {
      inArgs.add(bean.getCatPartNo());
    }
    else {
      inArgs.add("");
    }
    if(bean.getSpecId() != null) {
      inArgs.add(bean.getSpecId());
    }
    else {
      inArgs.add("");
    }
    //this is spec library
    inArgs.add("global");
    //this is detail
    inArgs.add("");
    //this is notes
    inArgs.add("");
    if(bean.getCoc() != null && bean.getCoc().length() > 0) {
      inArgs.add(bean.getCoc());
    }
    else {
      inArgs.add("N");
    }
    if(bean.getCoa() != null && bean.getCoa().length() > 0) {
      inArgs.add(bean.getCoa());
    }
    else {
      inArgs.add("N");
    }
    if(bean.getPersonnelId() != null) {
      inArgs.add(bean.getPersonnelId());
    }
    else {
      inArgs.add("");
    }
    
    inArgs.add(bean.isItar() ? "Y" : "N");
    
    DbManager dbManager = new DbManager(getClient(),getLocale());
    GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
    procFactory.doProcedure("p_catalog_spec_insert", inArgs);
  }



  public ExcelHandler  getExcelReport(CatalogSpecInputBean bean, Locale locale) throws
      NoDataException, BaseException, Exception {
    ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
    Collection data = this.getSearchData(bean);
//log.debug("data:" + data);
    Iterator iterator = data.iterator();
	  ExcelHandler pw = new ExcelHandler(library);
	  pw.addTable();

//	  write column headers
	  pw.addRow();

            String[] headerkeys = {"label.catalog","label.partnumber","label.specification","catalogspec.label.coc","catalogspec.label.coa"};
        int  [] widths = {0, 30, 18, 0, 0};

        int [] types =   {0,0,0,0,0} ;

        int[] aligns = {0,0,0,0,0};


       pw.applyColumnHeader(headerkeys, types, widths, aligns);  

  /*  pw.addCellKeyBold("label.catalog");
    pw.addCellKeyBold("label.partnumber");
    pw.addCellKeyBold("label.specification");
    pw.addCellKeyBold("catalogspec.label.coc");
    pw.addCellKeyBold("catalogspec.label.coa");*/
    
    //now write data
    while(iterator.hasNext()) {
      CatalogSpecViewBean catalogSpecViewBean = (CatalogSpecViewBean)iterator.next();
      pw.addRow();
      pw.addCell(catalogSpecViewBean.getCatalogId());
      pw.addCell(catalogSpecViewBean.getCatPartNo());
      pw.addCell(catalogSpecViewBean.getSpecification());
      pw.addCell(catalogSpecViewBean.getCoc());
      pw.addCell(catalogSpecViewBean.getCoa());
      
    }
    return pw;
  }
}