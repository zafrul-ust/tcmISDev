package com.tcmis.internal.hub.process;

import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.PolchemDpmsStuckOrderBean;
import com.tcmis.internal.hub.beans.PolchemDpmsStuckOrderInputBean;

/******************************************************************************
 * Process for polchem dpms stuck orders
 * @version 1.0
 *****************************************************************************/
public class PolchemDpmsStuckOrderProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public PolchemDpmsStuckOrderProcess(String client,String locale) {
	    super(client,locale);
	  }

  public Collection<PolchemDpmsStuckOrderBean> getSearchResult() throws BaseException {
	  DbManager dbManager = new DbManager(getClient(),getLocale());
	  GenericSqlFactory fac = new GenericSqlFactory(dbManager,new PolchemDpmsStuckOrderBean());
	  SortCriteria criteria = new SortCriteria();
	  criteria.addCriterion("dateAllocated");
	  Collection c = fac.select(null,criteria,"POLCHEM_DPMS_STUCK_ORDERS");

	  return c;
  }
  

  public ExcelHandler getExcelReport(PolchemDpmsStuckOrderInputBean polchemDpmsStuckOrderInputBean, Locale locale) throws BaseException{
	  ResourceLibrary library = new ResourceLibrary(	"com.tcmis.common.resources.CommonResources", locale);

	  Collection<PolchemDpmsStuckOrderBean> data = getSearchResult();
	  ExcelHandler pw = new ExcelHandler(library);

	  pw.addTable();
	  //write column headers
	  pw.addRow();
	  /*Pass the header keys for the Excel.*/
	  String[] headerkeys = {
	    "label.date","label.customerpo","label.mrline", "label.partnumber",
	    "label.orderqty","label.quantityopen","label.orderdate", "label.allocateddate"};
	  
	  /*This array defines the type of the excel column.
	  0 means default depending on the data type. */
	  int[] types = {
	    pw.TYPE_DATE, pw.TYPE_STRING, pw.TYPE_STRING, pw.TYPE_STRING,
	    pw.TYPE_NUMBER,pw.TYPE_NUMBER,pw.TYPE_DATE,pw.TYPE_DATE};
	  
	  /*This array defines the default width of the column when the Excel file opens.
	  0 means the width will be default depending on the data type.*/
	  int[] widths = {
	    0,0,0,0,
	    0,0,0,0};
	  /*This array defines the horizontal alignment of the data in a cell.
	  0 means excel defaults the horizontal alignemnt by the data type.*/
	  int[] aligns = {
	      0,0,0,0,
	      0,0,0,0};
	    
	  pw.applyColumnHeader(headerkeys, types, widths, aligns);
	  
	  for(PolchemDpmsStuckOrderBean member:data){
		  pw.addRow();
		  pw.addCell(member.getTimeStamp());
		  pw.addCell(member.getPoNumber());
		  pw.addCell(member.getPrNumber() + "-" + member.getLineItem());
		  pw.addCell(member.getFacPartNo());
		  pw.addCell(member.getRequisitionQuantity());
		  pw.addCell(member.getOpenQuantity());
		  pw.addCell(member.getDateOrderCreated());
		  pw.addCell(member.getDateAllocated());
	  }
	  
	  return pw;
  }
}