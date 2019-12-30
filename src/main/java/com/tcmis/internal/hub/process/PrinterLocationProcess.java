package com.tcmis.internal.hub.process;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.exceptions.DbUpdateException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.framework.GenericSqlFactory;
//import com.tcmis.common.framework.GenericProcedureFactory;

import com.tcmis.internal.hub.beans.LocationLabelPrinterBean;
import com.tcmis.internal.hub.beans.LocationLabelPrinterInputBean;
import com.tcmis.internal.hub.factory.LocationLabelPrinterBeanFactory;
import com.tcmis.common.admin.factory.HubInventoryGroupOvBeanFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.*;

/******************************************************************************
 * Process tcreate the .
 * @version 1.0
 *****************************************************************************/
public class PrinterLocationProcess extends BaseProcess {
   Log log = LogFactory.getLog(this.getClass());
   
   public PrinterLocationProcess(String client,String locale) {
      super(client,locale);
   }
   
   public Collection getAllHubDropdown() throws BaseException {      
      DbManager dbManager = new DbManager(getClient(),getLocale());      
      HubInventoryGroupOvBeanFactory factory = new HubInventoryGroupOvBeanFactory(dbManager);      
      SearchCriteria criteria = new SearchCriteria();      
      criteria.addCriterion("personnelId", SearchCriterion.EQUALS, "-1");      
      return factory.selectObject(criteria);      
   }
   
   
   
   public Collection getSearchResult(String selectedHub) throws BaseException {
      DbManager dbManager = new DbManager(getClient(),getLocale());
      GenericSqlFactory factory = new GenericSqlFactory(dbManager, new LocationLabelPrinterBean());      
      SearchCriteria criteria = new SearchCriteria();      
      if(selectedHub != null && selectedHub.length() > 0) {
         criteria.addCriterion("hub", SearchCriterion.EQUALS, selectedHub);
      }      
      SortCriteria scriteria = new SortCriteria();      
      return factory.select(criteria,scriteria, "location_label_printer");
   }
   
   // public Collection update(Collection pageNameViewBeanCollection,PersonnelBean personnelBean)  throws BaseException {
   public int update(Collection<LocationLabelPrinterInputBean> beans) throws DbUpdateException, BaseException {
      DbManager dbManager = new DbManager(getClient(),getLocale());
      
      LocationLabelPrinterBeanFactory factory = new LocationLabelPrinterBeanFactory(dbManager);      
      //      Vector messages = new Vector();
      //      String errorMsg;
      int i = 0;
      
      for (LocationLabelPrinterInputBean bean : beans) {
        if (bean.getOkDoUpdate() != null && ("1".equals(bean.getOkDoUpdate()) || "true".equals(bean.getOkDoUpdate())))
            //	if( permissionBean.hasInventoryGroupPermission("ForceBuy", updateBean.getInventoryGroup(),null,null))
        {
        
        	if(!bean.getOldPrinterPath().equalsIgnoreCase(""))
                 {i = i + factory.update(bean);  }
        	else {
        		   i = i + factory.insert(bean); 
        	     }
        
         }
      }
      return i;
   }
   
   public ExcelHandler  getExcelReport(Collection bean, Locale locale) throws NoDataException, BaseException {
      ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
      Collection<LocationLabelPrinterBean> data = bean;
      ExcelHandler pw = new ExcelHandler(library);
      
      pw.addTable();
      //write column headers
      pw.addRow();
      /*Pass the header keys for the Excel.*/
      String[] headerkeys = {"label.labelPrinter","label.size","label.printerpath"};
      /*This array defines the type of the excel column.
        0 means default depending on the data type. */
      int[] types = {0,0,pw.TYPE_STRING};
      /*This array defines the default width of the column when the Excel file opens.
        0 means the width will be default depending on the data type.*/
      int[] widths = {25,0,25};
      /*This array defines the horizontal alignment of the data in a cell.
        0 means excel defaults the horizontal alignemnt by the data type.*/
      int[] horizAligns = {0,0,0};
               
      pw.applyColumnHeader(headerkeys, types, widths, horizAligns);               
      // now write data
      int i = 1;
      for (LocationLabelPrinterBean member : data) {
          i++;
          pw.addRow();                  
          pw.addCell(member.getPrinterLocation());
          pw.addCell(member.getLabelStock());
          pw.addCell(member.getPrinterPath());
      }
      return pw;
   }
   
   public Collection<LocationLabelPrinterBean> getAlternatePrinterLocations(String selectedHub, String managedPrintQueue, String labelStock, String printerLocation) throws BaseException {
      DbManager dbManager = new DbManager(getClient(),getLocale());
      GenericSqlFactory factory = new GenericSqlFactory(dbManager, new LocationLabelPrinterBean());      
      SearchCriteria criteria = new SearchCriteria();      
      if(selectedHub != null && selectedHub.length() > 0) {
         criteria.addCriterion("hub", SearchCriterion.EQUALS, selectedHub);
      }
      if (managedPrintQueue != null && managedPrintQueue.length() > 0) {
         criteria.addCriterion("managedPrintQueue", SearchCriterion.EQUALS, managedPrintQueue);
      }
      criteria.addCriterion("labelStock", SearchCriterion.EQUALS, labelStock);
      criteria.addCriterion("printerLocation", SearchCriterion.NOT_EQUAL, printerLocation);
      
      SortCriteria scriteria = new SortCriteria();      
      return factory.select(criteria,scriteria, "location_label_printer");
   }
   
}