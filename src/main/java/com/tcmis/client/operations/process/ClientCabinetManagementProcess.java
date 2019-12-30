package com.tcmis.client.operations.process;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.logging.*;

import com.tcmis.client.operations.beans.HubPreferredWarehouseViewBean;
import com.tcmis.common.admin.beans.HubInventoryGroupOvBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.*;
import com.tcmis.internal.hub.factory.*;

/******************************************************************************
 * Process for allocation analysis
 * @version 1.0
 *****************************************************************************/
public class ClientCabinetManagementProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public ClientCabinetManagementProcess(String client) {
    super(client);
  }
  
  public ClientCabinetManagementProcess(String client,String locale) {
	    super(client,locale);
  }

  public Collection getAllLabelData( Collection<HubPreferredWarehouseViewBean> hubPreferredWareHouseCollection) throws BaseException, Exception {
    Collection criteriaColl = new Vector();
    
    for (HubPreferredWarehouseViewBean bean : hubPreferredWareHouseCollection) {
    	criteriaColl.add(bean.getBranchPlant());
	}
    
    DbManager dbManager = new DbManager(getClient(),getLocale());
    HubCabinetViewBeanFactory factory = new HubCabinetViewBeanFactory(dbManager);
    SearchCriteria criteria = new SearchCriteria("branchPlant", SearchCriterion.EQUALS, criteriaColl);

    SortCriteria sortCriteria = new SortCriteria();
    sortCriteria.addCriterion("hubName");
    sortCriteria.addCriterion("facilityId");
    sortCriteria.addCriterion("application");
    sortCriteria.addCriterion("cabinetName");
    Collection flatCollection = factory.select(criteria, sortCriteria);
    return this.getNormalizedHubCollection(flatCollection);
  }

  private Collection getSortedCabinetBeanForAllWorkAreasCollection(String branchPlant, String facilityId) throws BaseException, Exception {
    DbManager dbManager = new DbManager(getClient(),getLocale());
    HubCabinetViewBeanFactory factory = new HubCabinetViewBeanFactory(dbManager);
    SearchCriteria criteria = new SearchCriteria("branchPlant", SearchCriterion.EQUALS, branchPlant);
    criteria.addCriterion("facilityId", SearchCriterion.EQUALS, facilityId);
    SortCriteria sortCriteria = new SortCriteria();
    sortCriteria.addCriterion("cabinetName");
    return factory.select(criteria, sortCriteria);
  }

  /*public Collection getCabinetDefinitions(CabinetManagementInputBean bean) throws BaseException, Exception {
     return getNormalizedWorkAreaCollection( getSearchData(bean) );
  }*/

/*  public Collection update(Collection cabinetManagementInputBeanCollection,
                           CabinetManagementInputBean bean) throws BaseException, Exception {
    DbManager dbManager = new DbManager(getClient(),getLocale());
    CabinetPartItemBinBeanFactory factory = new CabinetPartItemBinBeanFactory(dbManager);
    Iterator iterator = cabinetManagementInputBeanCollection.iterator();
    while(iterator.hasNext()) {
      CabinetManagementInputBean updateBean = (CabinetManagementInputBean)iterator.next();
      if(updateBean.getStatus() == null) {
        updateBean.setStatus("I");
      }
      if(updateBean.getOldStatus() == null) {
        updateBean.setOldStatus("I");
      }
      if(!updateBean.getStatus().equalsIgnoreCase(updateBean.getOldStatus())) {
        factory.updateStatus(updateBean.getBinId(), updateBean.getStatus());
      }
    }
    return this.getSearchData(bean);
  }*/

  /*public ExcelHandler getExcelReport(CabinetManagementInputBean inputBean, Locale locale)
  throws Exception
  {
  	  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
  	  ExcelHandler pw = new ExcelHandler(library);
  	Collection<CabinetPartLevelViewBean> data = this.getSearchData(inputBean);
  	
  	  pw.addTable();
 
    pw.addRow();  
    String[] headerkeys = { "label.item","label.description","label.containersize","label.catalogid","label.cabinet",
                            "label.bin","label.partnumber","label.msds","label.stockinglevel","label.reorderpoint"};
        int[] widths = {7,0,0,10,18,
                        22,26,13,0,0};
        int [] types = {0,pw.TYPE_PARAGRAPH,pw.TYPE_PARAGRAPH,0,0,
                        0,0,0,0,0};
        int[] aligns = {pw.ALIGN_CENTER,0,0,pw.ALIGN_CENTER,0,
        		0,0,0,0,0};
        pw.applyColumnHeader(headerkeys, types, widths, aligns);

    //now write data
        for (CabinetPartLevelViewBean member : data) {
     

	  pw.addRow();
      pw.addCell(member.getItemId());
      pw.addCell(member.getMaterialDesc());
      pw.addCell(member.getPackaging());
      pw.addCell(member.getCatalogId());
      pw.addCell(member.getCabinetName());
      pw.addCell(member.getBinName());
      pw.addCell(member.getCatPartNo());
      pw.addCell(member.getMaterialIdString());
      pw.addCell(member.getStockingLevel());
      pw.addCell(member.getReorderPoint());
    }
	  return pw;
  }*/


  /*public Collection getSearchData(CabinetManagementInputBean bean) throws BaseException,
      Exception {
    DbManager dbManager = new DbManager(getClient(),getLocale());
    CabinetPartLevelViewBeanFactory factory = new CabinetPartLevelViewBeanFactory(dbManager);
    SearchCriteria criteria = new SearchCriteria();
    if(!StringHandler.isBlankString(bean.getBranchPlant())) {
      criteria = new SearchCriteria("sourceHub",
                                    SearchCriterion.EQUALS,
                                    bean.getBranchPlant());
    }
    if(!StringHandler.isBlankString(bean.getFacilityId())) {
      criteria.addCriterion("facilityId",
                            SearchCriterion.EQUALS,
                            bean.getFacilityId());
    }
    if(!StringHandler.isBlankString(bean.getApplication())) {
      criteria.addCriterion("application",
                            SearchCriterion.EQUALS,
                            bean.getApplication());
    }
    if(!StringHandler.isBlankString(bean.getUseApplication())) {
      criteria.addCriterion("useApplication",
                            SearchCriterion.EQUALS,
                            bean.getUseApplication());
    }

    if(bean.getCabinetIdArray() != null && bean.getCabinetIdArray().length > 0) {
      boolean initFlag = true;
      for(int i=0; i<bean.getCabinetIdArray().length; i++) {
        if(bean.getCabinetIdArray()[i].length() > 0) {
          if (initFlag) {
            criteria.addCriterion("cabinetId", SearchCriterion.EQUALS,
                                  bean.getCabinetIdArray()[i].toString());
          }
          else {
            criteria.addValueToCriterion("cabinetId",
                                         bean.getCabinetIdArray()[i].toString());
          }
          initFlag = false;
        }
      }
    }
    if(!StringHandler.isBlankString(bean.getCriteria())) {
      if("part".equalsIgnoreCase(bean.getItemOrPart())) {
        if("is".equalsIgnoreCase(bean.getCriterion())) {
          criteria.addCriterion("catPartNo", SearchCriterion.EQUALS, bean.getCriteria());
        }
        else if("contains".equalsIgnoreCase(bean.getCriterion())) {
          criteria.addCriterion("catPartNo", SearchCriterion.LIKE, bean.getCriteria());
        }
      }
      else if("item".equalsIgnoreCase(bean.getItemOrPart())) {
        if("is".equalsIgnoreCase(bean.getCriterion())) {
          criteria.addCriterion("itemId", SearchCriterion.EQUALS, bean.getCriteria());
        }
        else if("contains".equalsIgnoreCase(bean.getCriterion())) {
          criteria.addCriterion("itemId", SearchCriterion.LIKE, bean.getCriteria());
        }
      }
    }
    if(StringHandler.isBlankString(bean.getInactive())) {
      criteria.addCriterion("status", SearchCriterion.EQUALS, "A");
    }

    SortCriteria sortCriteria = new SortCriteria();
    if(!StringHandler.isBlankString(bean.getSortBy())) {
      if("cabinetId".equalsIgnoreCase(bean.getSortBy())) {
        //x.CABINET_ID,x.CAT_PART_NO
        sortCriteria.addCriterion("cabinetId");
        sortCriteria.addCriterion("catPartNo");
      }
      else if("partNumber".equalsIgnoreCase(bean.getSortBy())) {
        //x.CAT_PART_NO,x.CABINET_ID
        sortCriteria.addCriterion("catPartNo");
        sortCriteria.addCriterion("cabinetId");
      }
      else if("itemId".equalsIgnoreCase(bean.getSortBy())) {
        //x.ITEM_ID,x.CABINET_ID
        sortCriteria.addCriterion("itemId");
        sortCriteria.addCriterion("cabinetId");
      }
    }
    return factory.select(criteria, sortCriteria);
  }*/

  private Collection getNormalizedHubCollection(Collection c) throws Exception {

    Collection normalizedCollection = new Vector(c.size());
    Iterator flatIterator = c.iterator();
    String previousHub = "";
    String previousFacility = "";
    String previousApplication = "";
    HubCabinetViewBean hubBean = null;
    HubCabinetViewBean facilityBean = null;
    HubCabinetViewBean applicationBean = null;
    HubCabinetViewBean cabinetBean = null;
    while(flatIterator.hasNext()) {

      HubCabinetViewBean flatBean = (HubCabinetViewBean)flatIterator.next();
      if (flatBean != null) {

         if(previousHub!=null && !previousHub.equalsIgnoreCase(flatBean.getBranchPlant())) {
           //new bean
           if(previousHub.length() == 0) {
             //first time in loop
             hubBean = new HubCabinetViewBean();
             facilityBean = new HubCabinetViewBean();
             applicationBean = new HubCabinetViewBean();
             cabinetBean = new HubCabinetViewBean();
             this.copyCabinetData(flatBean, cabinetBean);
             this.copyApplicationData(flatBean, applicationBean);
             this.copyFacilityData(flatBean, facilityBean);
             this.copyHubData(flatBean, hubBean);
           }
           else {
             //different hub
             applicationBean.addCabinetBean((HubCabinetViewBean)cabinetBean.clone());
             facilityBean.addApplicationBean((HubCabinetViewBean)applicationBean.clone());
             hubBean.addFacilityBean((HubCabinetViewBean)facilityBean.clone());
             normalizedCollection.add((HubCabinetViewBean)hubBean.clone());
             hubBean = new HubCabinetViewBean();
             facilityBean = new HubCabinetViewBean();
             applicationBean = new HubCabinetViewBean();
             cabinetBean = new HubCabinetViewBean();
             this.copyCabinetData(flatBean, cabinetBean);
             this.copyApplicationData(flatBean, applicationBean);
             this.copyFacilityData(flatBean, facilityBean);
             this.copyHubData(flatBean, hubBean);
           }
         }
         else {
           //same hub
           if(previousFacility!=null && !previousFacility.equalsIgnoreCase(flatBean.getFacilityId())) {
             //new facility
             applicationBean.addCabinetBean((HubCabinetViewBean)cabinetBean.clone());
             facilityBean.addApplicationBean((HubCabinetViewBean)applicationBean.clone());
             hubBean.addFacilityBean((HubCabinetViewBean)facilityBean.clone());
             facilityBean = new HubCabinetViewBean();
             applicationBean = new HubCabinetViewBean();
             cabinetBean = new HubCabinetViewBean();
             this.copyCabinetData(flatBean, cabinetBean);
             this.copyApplicationData(flatBean, applicationBean);
             this.copyFacilityData(flatBean, facilityBean);
           }
           else {
             //same dock, check for new application
             if(previousApplication!=null && !previousApplication.equalsIgnoreCase(flatBean.getApplication())) {
               applicationBean.addCabinetBean((HubCabinetViewBean)
                                               cabinetBean.clone());
               facilityBean.addApplicationBean( (HubCabinetViewBean)
                                               applicationBean.clone());
               applicationBean = new HubCabinetViewBean();
               cabinetBean = new HubCabinetViewBean();
               this.copyCabinetData(flatBean, cabinetBean);
               this.copyApplicationData(flatBean, applicationBean);
             }
             else {
               //same application, add cabinet
               applicationBean.addCabinetBean((HubCabinetViewBean)
                                               cabinetBean.clone());
               cabinetBean = new HubCabinetViewBean();
               this.copyCabinetData(flatBean, cabinetBean);
             }
           }
         }
         previousHub = flatBean.getBranchPlant();
         previousFacility = flatBean.getFacilityId();
         previousApplication = flatBean.getApplication();
      }
    }
    applicationBean.addCabinetBean((HubCabinetViewBean)cabinetBean.clone());
    facilityBean.addApplicationBean((HubCabinetViewBean)applicationBean.clone());
    hubBean.addFacilityBean((HubCabinetViewBean)facilityBean.clone());
    normalizedCollection.add((HubCabinetViewBean)hubBean.clone());
    return normalizedCollection;
  }

  private void copyHubData(HubCabinetViewBean fromBean, HubCabinetViewBean toBean){
    //toBean.setPreferredWarehouse(fromBean.getPreferredWarehouse());
    toBean.setBranchPlant(fromBean.getBranchPlant());
    toBean.setHubName(fromBean.getHubName());
  }

  private void copyFacilityData(HubCabinetViewBean fromBean, HubCabinetViewBean toBean) throws BaseException, Exception {
    toBean.setFacilityId(fromBean.getFacilityId());
    toBean.setFacilityName(fromBean.getFacilityName());
    toBean.setCompanyId(fromBean.getCompanyId());
    toBean.setSortedCabinetBeanForAllWorkAreasCollection(this.getSortedCabinetBeanForAllWorkAreasCollection(fromBean.getBranchPlant(), fromBean.getFacilityId()));
  }

  private void copyApplicationData(HubCabinetViewBean fromBean, HubCabinetViewBean toBean) {
    toBean.setApplication(fromBean.getApplication());
    toBean.setApplicationDesc(fromBean.getApplicationDesc());
  }

  private void copyCabinetData(HubCabinetViewBean fromBean, HubCabinetViewBean toBean) {
    toBean.setCabinetId(fromBean.getCabinetId());
    toBean.setCabinetName(fromBean.getCabinetName());
  }

  private Collection getNormalizedWorkAreaCollection(Collection flatData) {
     CabinetPartLevelViewBean flatBean = null;
     CabinetPartLevelViewBean workArea = null;
     CabinetPartLevelViewBean cabinet = null;
     CabinetPartLevelViewBean bin = null;
     String lastWorkArea = "";
     String lastCabinet = "";
     Vector workAreas = new Vector();
     Vector cabinets = null;
     Vector bins = null;
     Iterator flatDataIter = flatData.iterator();
     int workareaSize=0;

     try {
        while (flatDataIter.hasNext()) {
           flatBean = (CabinetPartLevelViewBean) flatDataIter.next();
           if (! flatBean.getUseApplication().equalsIgnoreCase(lastWorkArea)) {
              // new work area
              if (workArea != null) {
                 cabinet.setBins((Vector)bins.clone());
                 cabinet.setCabinetRowspan(bins.size());
                 cabinets.add((CabinetPartLevelViewBean)cabinet.clone());
                 workArea.setCabinets((Vector)cabinets.clone());
                 workArea.setWorkareaRowspan(workareaSize + bins.size());
                 workAreas.add((CabinetPartLevelViewBean)workArea.clone());
              }
              workArea = new CabinetPartLevelViewBean();
              copyWorkAreaData(flatBean,workArea);
              cabinets = new Vector();
              lastWorkArea = workArea.getUseApplication();
              lastCabinet = "";
              cabinet = null;
              bin = null;
              workareaSize=0;
           }

           if (! flatBean.getCabinetName().equalsIgnoreCase(lastCabinet)) {
              // new cabinet
              if (cabinet != null) {
                 cabinet.setBins((Vector)bins.clone());
                 cabinet.setCabinetRowspan(bins.size());
                 cabinets.add((CabinetPartLevelViewBean)cabinet.clone());
                 workareaSize += bins.size();
              }

              cabinet = new CabinetPartLevelViewBean();
              copyCabinetData(flatBean,cabinet);
              bins = new Vector();
              lastCabinet = cabinet.getCabinetName();
              bin = null;
           }
           bin = new CabinetPartLevelViewBean();
           copyBinData(flatBean,bin);
           bins.add((CabinetPartLevelViewBean)bin.clone());
        }
        if (workArea != null) {
          cabinet.setBins((Vector)bins.clone());
          cabinet.setCabinetRowspan(bins.size());
          cabinets.add((CabinetPartLevelViewBean)cabinet.clone());
          workArea.setCabinets((Vector)cabinets.clone());
          workArea.setWorkareaRowspan(workareaSize+bins.size());
          workAreas.add((CabinetPartLevelViewBean)workArea.clone());
        }
     } catch (Exception e) {
        log.error("Exception in getNormalizedWorkAreaCollection: " + e);
     }

     return workAreas;
  }

  private void copyBinData(CabinetPartLevelViewBean source, CabinetPartLevelViewBean dest) {
     dest.setBinName(source.getBinName());
     dest.setCatalogId(source.getCatalogId());
     dest.setCatPartNo(source.getCatPartNo());
     dest.setItemId(source.getItemId());
     dest.setMaterialDesc(source.getMaterialDesc());
     dest.setMfgDesc(source.getMfgDesc());
  }

  private void copyCabinetData(CabinetPartLevelViewBean source, CabinetPartLevelViewBean dest) {
     dest.setCabinetName(source.getCabinetName());
     dest.setCabinetId(source.getCabinetId());
  }

  private void copyWorkAreaData(CabinetPartLevelViewBean source, CabinetPartLevelViewBean dest) {
     dest.setApplication(source.getApplication());
     dest.setUseApplication(source.getUseApplication());
  }
}

