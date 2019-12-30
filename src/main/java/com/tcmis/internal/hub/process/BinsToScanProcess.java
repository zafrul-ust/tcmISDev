package com.tcmis.internal.hub.process;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.HubInventoryGroupOvBean;
import com.tcmis.internal.hub.beans.*;
import com.tcmis.internal.hub.factory.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.*;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.ResourceLibrary;

/******************************************************************************
 * Process tcreate the .
 * @version 1.0
 *****************************************************************************/
public class BinsToScanProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public BinsToScanProcess(String client) {
    super(client);
  }
  
  public BinsToScanProcess(String client,String locale) {
	    super(client,locale);
 }

  public Collection getDropDownData(Collection hubCollection) throws BaseException {
    Collection c = null;
    try {
      if(hubCollection != null) {
        SearchCriteria criteria = new SearchCriteria();
        Iterator iterator = hubCollection.iterator();
        int count = 0;
        while (iterator.hasNext()) {
          HubInventoryGroupOvBean bean = (HubInventoryGroupOvBean) iterator.
              next();
          if (count == 0) {
            criteria.addCriterion("hub", SearchCriterion.EQUALS,
                                  bean.getBranchPlant());
          }
          else {
            criteria.addValueToCriterion("hub", bean.getBranchPlant());
          }
          count++;
        }
        DbManager dbManager = new DbManager(getClient(),getLocale());
        HubRoomOvBeanFactory factory = new HubRoomOvBeanFactory(dbManager);
        c = factory.selectObject(criteria);
      }
    }
    catch(Exception e) {
      log.error("Error getting dropdown data for binstoscan page", e);
      throw new BaseException(e);
    }
    return c;
  }

  public Collection getSearchData(BinsToScanInputBean bean) throws BaseException {
    Collection c = null;
    try {
      SearchCriteria criteria = new SearchCriteria();
      if(!StringHandler.isBlankString(bean.getBranchPlant())) {
        criteria.addCriterion("branchPlant", SearchCriterion.EQUALS, bean.getBranchPlant());
      }
      if(!StringHandler.isBlankString(bean.getRoom())) {
        criteria.addCriterion("room", SearchCriterion.EQUALS, bean.getRoom());
      }
      if(bean.getItemId() != null) {
        criteria.addCriterion("itemId", SearchCriterion.EQUALS, bean.getItemId().toString());
      }
      if(bean.getBinCountDays() != null) {
        criteria.addCriterion("countDatetime", 
                              SearchCriterion.LESS_THAN_OR_EQUAL_TO, 
                              DateHandler.formatOracleDate(DateHandler.add(Calendar.DATE, bean.getBinCountDays().negate().intValue(), new Date())));
        
      }
      if(bean.getReceiptDaySpan() != null) {
        criteria.addCriterion("datePicked", 
                              SearchCriterion.GREATER_THAN_OR_EQUAL_TO, 
                              DateHandler.formatOracleDate(DateHandler.add(Calendar.DATE, bean.getReceiptDaySpan().negate().intValue(), new Date())));
      }
      if(bean.getInventoryValueMax() != null) {
        criteria.addCriterion("inventoryCost", SearchCriterion.LESS_THAN, bean.getInventoryValueMax().toString());
      }
      if(bean.getInventoryValueMin() != null) {
        criteria.addCriterion("inventoryCost", SearchCriterion.GREATER_THAN, bean.getInventoryValueMin().toString());
      }
      if(bean.getUnitCostMax() != null) {
        criteria.addCriterion("unitPrice", SearchCriterion.LESS_THAN, bean.getUnitCostMax().toString());
      }
      if(bean.getUnitCostMin() != null) {
        criteria.addCriterion("unitPrice", SearchCriterion.GREATER_THAN, bean.getUnitCostMin().toString());
      }
      SortCriteria sortCriteria = new SortCriteria("room");
      sortCriteria.addCriterion("bin");
      DbManager dbManager = new DbManager(getClient(),getLocale());
      BinsToScanViewBeanFactory factory = new BinsToScanViewBeanFactory(dbManager);
      c = factory.selectDistinct(criteria, sortCriteria);
    }
    catch(Exception e) {
      log.error("Error getting dropdown data for binstoscan page", e);
      throw new BaseException(e);
    }
    return c;
  }
  
  public ExcelHandler  getExcelReport(Collection bean, Locale locale) throws NoDataException, BaseException {
		ResourceLibrary library = new ResourceLibrary(
				"com.tcmis.common.resources.CommonResources", locale);
		Collection<BinsToScanViewBean> data = bean;
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
  //write column headers
		pw.addRow();
  /*Pass the header keys for the Excel.*/
  String[] headerkeys = {
    "label.room","label.bin"};
  /*This array defines the type of the excel column.
  0 means default depending on the data type. */
  int[] types = {
    0,0};
  /*This array defines the default width of the column when the Excel file opens.
  0 means the width will be default depending on the data type.*/
  int[] widths = {
    40,30};
  /*This array defines the horizontal alignment of the data in a cell.
  0 means excel defaults the horizontal alignemnt by the data type.*/
  int[] horizAligns = {
    0,0};
    
  pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
  
		// now write data
	  int i = 1;
		for (BinsToScanViewBean member : data) {
    i++;
    pw.addRow();

    pw.addCell(member.getRoom());
    pw.addCell(member.getBin());
		}
		return pw;
	}

  
  

}