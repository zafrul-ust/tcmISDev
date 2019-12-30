package com.tcmis.client.waste.process;

import org.apache.commons.logging.*;
import com.tcmis.common.framework.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.util.*;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Locale;


import com.tcmis.client.waste.beans.WasteTsdfSourceViewBean;
import com.tcmis.client.waste.beans.TsdfReportViewBean;
import com.tcmis.client.waste.beans.TsdfReportInputBean;
import com.tcmis.client.waste.factory.TsdfReportViewBeanFactory;
import com.tcmis.client.waste.factory.TsdfReportDropdownOvBeanFactory;
import com.tcmis.client.waste.factory.WasteRequestLineItemBeanFactory;

/******************************************************************************
 * Process for TDSF Report
 * @version 1.0
 *****************************************************************************/
public class TsdfReportProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public TsdfReportProcess(String client) {
    super(client);
  }

  public void updateTagNumber(String wasteRequestLine,String tagNumber) throws BaseException {
    DbManager dbManager = new DbManager(this.getClient());
    try {
      //first break waste request line into id and line item
      String wasteRequestId = "0";
      String lineItem = "0";
      if (wasteRequestLine != null ) {
        String[] tmp = wasteRequestLine.split("-");
        if (tmp.length == 2) {
          wasteRequestId = tmp[0];
          lineItem = tmp[1];
          WasteRequestLineItemBeanFactory factory = new WasteRequestLineItemBeanFactory(dbManager);
          SearchCriteria criteria = new SearchCriteria();
          criteria.addCriterion("wasteRequestId", SearchCriterion.EQUALS,wasteRequestId);
          criteria.addCriterion("lineItem", SearchCriterion.EQUALS,lineItem);
          factory.updateTagNumber(criteria, tagNumber);
        }
      }
    } catch (Exception e) {
      log.error("Base Exception in getTsdfDropdown: " + e);
    } finally {
      dbManager = null;
    }
  } //end of method

  public Collection getTsdfDropdownData(int userId) throws BaseException {
    Collection tsdfDropdownColl = null;
    DbManager dbManager = new DbManager(this.getClient());
    try {
      TsdfReportDropdownOvBeanFactory factory = new TsdfReportDropdownOvBeanFactory(dbManager);
      SearchCriteria criteria = new SearchCriteria();
      criteria.addCriterion("personnelId", SearchCriterion.EQUALS,(new Integer(userId)).toString());
      tsdfDropdownColl = factory.selectObject(criteria);
    } catch (Exception e) {
      log.error("Base Exception in getTsdfDropdown: " + e);
    } finally {
      dbManager = null;
    }

    return tsdfDropdownColl;
  } //end of method

  public Collection getTsdfReportData(TsdfReportInputBean inputBean) throws BaseException {
    Collection ilmBeans = null;
    DbManager dbManager = new DbManager(this.getClient());
    try {
      TsdfReportViewBeanFactory factory = new TsdfReportViewBeanFactory(dbManager);
      SearchCriteria criteria = new SearchCriteria();
      //tsdf search info
      //tsdf
      if (!StringHandler.isBlankString(inputBean.getTsdf())) {
        criteria.addCriterion("tsdfFacilityId", SearchCriterion.EQUALS,inputBean.getTsdf());
      }
      //vendor
      if (!StringHandler.isBlankString(inputBean.getTsdfVendor())) {
        criteria.addCriterion("tsdfVendorId", SearchCriterion.EQUALS,inputBean.getTsdfVendor());
      }
      //manifest
      if (!StringHandler.isBlankString(inputBean.getTsdfManifest())) {
        criteria.addCriterion("tsdfManifest", SearchCriterion.LIKE,inputBean.getTsdfManifest());
      }
      //profile
      if (!StringHandler.isBlankString(inputBean.getTsdfProfile())) {
        criteria.addCriterion("tsdfVendorProfileId", SearchCriterion.LIKE,inputBean.getTsdfProfile());
      }
      //container id
      if (inputBean.getTsdfContainerId() != null) {
        criteria.addCriterion("tsdfContainerId", SearchCriterion.EQUALS,inputBean.getTsdfContainerId().toString());
      }
      //not ship
      if (inputBean.getNotShip() != null) {
        criteria.addCriterion("tsdfShipDate",SearchCriterion.IS,"null");
      }else {
        //ship date
        if (inputBean.getTsdfStartShipDate() != null && inputBean.getTsdfEndShipDate() != null) {
          //ship date is between
          criteria.addCriterion("tsdfShipDate", SearchCriterion.GREATER_THAN_OR_EQUAL_TO,DateHandler.formatDate(inputBean.getTsdfStartShipDate(), "MM/dd/yyyy"));
          criteria.addCriterion("tsdfShipDate", SearchCriterion.LESS_THAN_OR_EQUAL_TO,DateHandler.formatDate(inputBean.getTsdfEndShipDate(), "MM/dd/yyyy"));
        }else if (inputBean.getTsdfStartShipDate() != null) {
          //ship date is after start date
          criteria.addCriterion("tsdfShipDate", SearchCriterion.GREATER_THAN_OR_EQUAL_TO,DateHandler.formatDate(inputBean.getTsdfStartShipDate(), "MM/dd/yyyy"));
        }else if (inputBean.getTsdfEndShipDate() != null) {
          //ship date is before end date
          criteria.addCriterion("tsdfShipDate", SearchCriterion.LESS_THAN_OR_EQUAL_TO,DateHandler.formatDate(inputBean.getTsdfEndShipDate(), "MM/dd/yyyy"));
        }
      }

      //generator search info
      //gen company
      if (!StringHandler.isBlankString(inputBean.getGenCompany())) {
        criteria.addCriterion("genCompanyId", SearchCriterion.EQUALS,inputBean.getGenCompany());
      }
      //gen facility
      if (!StringHandler.isBlankString(inputBean.getGenFacilityId())) {
        criteria.addCriterion("genFacilityId", SearchCriterion.EQUALS,inputBean.getGenFacilityId());
      }
      //gen waste location
      if (!StringHandler.isBlankString(inputBean.getGenerationPoint())) {
        //check to see if generation is actual location for it is a group
        //format:
        //  .location = specific location
        //  group.location = specific location
        //  group. = group of locations
        //note: can't use split() because it doesn't handle "."
        String tmp = inputBean.getGenerationPoint();
        if (tmp.indexOf(".") == 0) {
          criteria.addCriterion("generationPoint", SearchCriterion.EQUALS, tmp.substring(1));
        }else if (tmp.indexOf(".") < tmp.length()-1) {
          criteria.addCriterion("generationPoint", SearchCriterion.EQUALS, tmp.substring(tmp.indexOf(".")+1));
        }else {
          criteria.addCriterion("locationGroup", SearchCriterion.EQUALS, tmp.substring(0,tmp.indexOf(".")));
        }
      }
      //ship date
      if (inputBean.getGenStartShipDate() != null && inputBean.getGenEndShipDate() != null) {
        //ship date is between
        criteria.addCriterion("genShipDate", SearchCriterion.GREATER_THAN_OR_EQUAL_TO,DateHandler.formatDate(inputBean.getGenStartShipDate(), "MM/dd/yyyy"));
        criteria.addCriterion("genShipDate", SearchCriterion.LESS_THAN_OR_EQUAL_TO,DateHandler.formatDate(inputBean.getGenEndShipDate(), "MM/dd/yyyy"));
      }else if (inputBean.getGenStartShipDate() != null) {
        //ship date is after start date
        criteria.addCriterion("genShipDate", SearchCriterion.GREATER_THAN_OR_EQUAL_TO,DateHandler.formatDate(inputBean.getGenStartShipDate(), "MM/dd/yyyy"));
      }else if (inputBean.getGenEndShipDate() != null) {
        //ship date is before end date
        criteria.addCriterion("genShipDate", SearchCriterion.LESS_THAN_OR_EQUAL_TO,DateHandler.formatDate(inputBean.getGenEndShipDate(), "MM/dd/yyyy"));
      }
      //no manifest
      if (inputBean.getNotShip() != null) {
        criteria.addCriterion("genManifest",SearchCriterion.IS,"null");
      }else {
        //manifest
        if (!StringHandler.isBlankString(inputBean.getGenManifest())) {
          criteria.addCriterion("genManifest", SearchCriterion.LIKE,inputBean.getGenManifest());
        }
      }
      //profile
      if (!StringHandler.isBlankString(inputBean.getGenProfile())) {
        criteria.addCriterion("genVendorProfileId", SearchCriterion.LIKE,inputBean.getGenProfile());
      }
      //container id
      if (inputBean.getGenContainerId() != null) {
        criteria.addCriterion("genContainerId", SearchCriterion.EQUALS,inputBean.getGenContainerId().toString());
      }
      //waste tag
      if (!StringHandler.isBlankString(inputBean.getWasteTag())) {
        criteria.addCriterion("tagNumber", SearchCriterion.LIKE,inputBean.getWasteTag());
      }

      ilmBeans = factory.select(criteria);
    } catch (Exception e) {
      log.error("Base Exception in getTsdfReport: " + e);
    } finally {
      dbManager = null;
    }

    return ilmBeans;
  } //end of method

  public ExcelHandler  getExcelReport(TsdfReportInputBean bean, Locale locale) throws NoDataException, BaseException, Exception {
    ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
    Collection data = this.getTsdfReportData(bean);

    Iterator iterator = data.iterator();
    ExcelHandler pw = new ExcelHandler(library);
	  
	  pw.addTable();

//	  write column headers
	  pw.addRow();
	    pw.addThRegion("tsdfcontainerreport.tsdfsearchinformation", 1, 2);
	    pw.addThRegion("tsdfcontainerreport.gensearchinformation", 1, 4);
    //row 2
    pw.addRow();
    pw.addCellKeyBold("label.tsdf");
    pw.addCell(bean.getTsdf());
    pw.addCellKeyBold("label.company");
    pw.addCell(bean.getGenCompany());
    pw.addCellKeyBold("label.profile");
    pw.addCell(bean.getGenProfile());
    

    //row 3
    pw.addRow();
    pw.addCellKeyBold("label.vendor");
    pw.addCell(bean.getTsdfVendor());
    pw.addCellKeyBold("label.facility");
    pw.addCell(bean.getGenFacilityId());
    pw.addCellKeyBold("label.containerid");
    pw.addCell(bean.getGenContainerId());
    
    //row 4
    pw.addRow();
    pw.addCellKeyBold("label.manifest");
    pw.addCell(bean.getTsdfManifest());
    pw.addCellKeyBold("label.generationpoint");
    pw.addCell(bean.getGenerationPoint());
    pw.addCellKeyBold("label.wastetag");
    pw.addCell(bean.getWasteTag());
    
    //row 5
    pw.addRow();
    pw.addCellKeyBold("label.profile");
    pw.addCell(bean.getTsdfProfile());
    if (bean.getGenStartShipDate() != null && bean.getGenEndShipDate() != null) {
      //gen ship date is between
      pw.addCellKeyBold("label.shipdatebetween");
      pw.addTdRegion(StringHandler.emptyIfNull(DateHandler.formatDate(bean.getGenStartShipDate(), "MM/dd/yyyy"))
    		  +" " + library.getString("label.and") + " " + StringHandler.emptyIfNull(DateHandler.formatDate(bean.getGenEndShipDate(), "MM/dd/yyyy"))
    		  , 1, 3);
    }else if (bean.getGenStartShipDate() != null) {
      //gen ship date is after start date
      pw.addCellKeyBold("label.shipdateafter");
      pw.addCell(DateHandler.formatDate(bean.getGenStartShipDate(), "MM/dd/yyyy"));
    }else if (bean.getGenEndShipDate() != null) {
      //gen ship date is before end date
      pw.addCellKeyBold("label.shipdatebefore");
      pw.addCell(DateHandler.formatDate(bean.getGenEndShipDate(), "MM/dd/yyyy"));
    }else {
      //no gen ship date
      pw.addCellKeyBold("label.shipdate");
      pw.addTdEmpty();
    }
    
    //row 6
    pw.addRow();
    pw.addCellKeyBold("label.containerid");
    pw.addCell(bean.getTsdfContainerId());
    pw.addCellKeyBold("label.manifest");
    pw.addCell(bean.getGenManifest());
    
    //row 7
    pw.addRow();
    if (bean.getTsdfStartShipDate() != null && bean.getTsdfEndShipDate() != null) {
      //tsdf ship date is between
      pw.addCellKeyBold("label.shipdatebetween");
      pw.addTdRegionBold(StringHandler.emptyIfNull(DateHandler.formatDate(bean.getTsdfStartShipDate(), "MM/dd/yyyy"))
    		  + " " + library.getString("label.and") + " " +StringHandler.emptyIfNull(DateHandler.formatDate(bean.getTsdfEndShipDate(), "MM/dd/yyyy")), 
    		  1, 3);
    }else if (bean.getTsdfStartShipDate() != null) {
      //tsdf ship date is after start date
      pw.addCellKeyBold("label.shipdateafter");
      pw.addCell(DateHandler.formatDate(bean.getTsdfStartShipDate(), "MM/dd/yyyy"));
    }else if (bean.getTsdfEndShipDate() != null) {
      //tsdf ship date is before end date
      pw.addCellKeyBold("label.shipdatebefore");
      pw.addCell(DateHandler.formatDate(bean.getTsdfEndShipDate(), "MM/dd/yyyy"));
    }else {
      //no tsdf ship date
      pw.addCellKeyBold("label.shipdate");
      pw.addTdEmpty();
    }
    
    //blank row
    pw.addRow();
    

    //result table
    //write column headers
    pw.addRow();
    pw.addThRegion("tsdfcontainerreport.tsdfinformation", 1, 6);
    pw.addThRegion("tsdfcontainerreport.geninformation", 1, 11);
    
    pw.addRow();
    pw.addCellKeyBold("label.vendor");
    pw.addCellKeyBold("label.manifest");
    pw.addCellKeyBold("label.shipdate");
    pw.addCellKeyBold("label.profile");
    pw.addCellKeyBold("label.packaging");
    pw.addCellKeyBold("label.currentcontainerid");
    
    pw.addCellKeyBold("label.manifest");
    pw.addCellKeyBold("label.shipdate");
    pw.addCellKeyBold("label.profile");
    pw.addCellKeyBold("label.packaging");
    pw.addCellKeyBold("label.company");
    pw.addCellKeyBold("label.facility");
    pw.addCellKeyBold("label.generationpoint");
    pw.addCellKeyBold("label.wasterequestline");
    pw.addCellKeyBold("label.wastetag");
    pw.addCellKeyBold("label.requestor");
    pw.addCellKeyBold("label.originalcontainerid");
    

    //now write data
    while (iterator.hasNext()) {
      TsdfReportViewBean tsdfReportViewBean = (TsdfReportViewBean) iterator.next();
      pw.addRow();
      tsdfReportViewBean.getTsdfVendorDesc();
      tsdfReportViewBean.getTsdfManifest();
      DateHandler.formatDate(tsdfReportViewBean.getTsdfShipDate(),"MM/dd/yyyy");
      tsdfReportViewBean.getTsdfVendorProfileId();
      tsdfReportViewBean.getTsdfPackaging();
      tsdfReportViewBean.getTsdfContainerId();
      tsdfReportViewBean.getGenManifest();
      DateHandler.formatDate(tsdfReportViewBean.getGenShipDate(),"MM/dd/yyyy");
      tsdfReportViewBean.getGenVendorProfileId();
      tsdfReportViewBean.getGenPackaging();
      tsdfReportViewBean.getGenCompanyId();
      tsdfReportViewBean.getGenFacilityId();
      tsdfReportViewBean.getGenerationPoint();
      tsdfReportViewBean.getGenWrLineItem();
      tsdfReportViewBean.getTagNumber();
      tsdfReportViewBean.getRequestor();
      tsdfReportViewBean.getGenContainerId();
      
    }
    return pw;
  } //end of method
} //end of class