package com.tcmis.internal.distribution.process;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.admin.beans.UserPageAdminViewBean;
import com.tcmis.common.admin.factory.UserPageAdminViewBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcess;

import java.util.Locale;
import java.io.Writer;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.internal.currency.beans.CurrencyExchangeRateBean;
   /******************************************************************************
   * Process for UserPageAdminView
   * @version 1.0
   *****************************************************************************/
  public class CurrencyExchangeRateProcess
   extends GenericProcess {
   Log log = LogFactory.getLog(this.getClass());

   public CurrencyExchangeRateProcess(String client) {
  	super(client);
   }
   
   public CurrencyExchangeRateProcess(String client,String locale) {
	    super(client,locale);
   }

   public Collection getCurrencyExchangeRate(CurrencyExchangeRateBean bean) throws BaseException {
	  	DbManager dbManager = new DbManager(getClient(),getLocale());
	  	String query = "select * from table(pkg_exchange_rate.fx_display())";
	  	return factory.setBean(bean).selectQuery(query);
   }

public String updateExchangeRate(CurrencyExchangeRateBean bean)  throws
   BaseException, Exception {
   Collection inArgs = new Vector();
// setting business logic...   
   if( this.isBlank(bean.getCurrencyId()) || this.isBlank(bean.getExchangeRate()))
	   return "";

//   PROCEDURE p_insert (a_currency_id IN vv_currency.currency_id%TYPE,
//           a_date        IN DATE,
//           a_rate        IN NUMBER);
   
   inArgs.add(bean.getCurrencyId());
   if( this.isBlank( bean.getStartDate() ))
	   inArgs.add(new Date());
   else
	   inArgs.add(bean.getStartDate());
   inArgs.add(bean.getExchangeRate());

   Vector outArgs = new Vector();
   outArgs.add( new Integer(java.sql.Types.VARCHAR) );

   Collection coll = factory.doProcedure("pkg_exchange_rate.p_insert", inArgs,outArgs);

   String errorMsg = (String)((Vector)coll).get(0);
   if( ! "OK".equals(errorMsg)  )
	   return errorMsg;
   
   return "";
   }
	
//public ExcelHandler  getAdminExcelReport(UserPageAdminViewBean inputBean,Collection data, Locale locale,String fullName) throws
//NoDataException, BaseException, Exception
//{
//	  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
//
//	  Iterator iterator = data.iterator();
//	  ExcelHandler pw = new ExcelHandler(library);
//	  
//	  pw.addTable();
//
////	  write column headers
//	  pw.addRow();
//	  pw.addCellKeyBold("label.name");
//	  pw.addCell(fullName);
//	  
//	  pw.addRow();
//	  
////	  write column headers
//	  pw.addRow();
//	  /*Pass the header keys for the Excel.*/
//	    String[] headerkeys = {
//	      "label.company","label.pages","label.access", "label.admin"};
//	    /*This array defines the type of the excel column.
//	    0 means default depending on the data type. */
//	    int[] types = {
//	      0,0,0,0};
//	    /*This array defines the default width of the column when the Excel file opens.
//	    0 means the width will be default depending on the data type.*/
//	    int[] widths = {
//	      30,40,0,0};
//	    /*This array defines the horizontal alignment of the data in a cell.
//	    0 means excel defaults the horizontal alignemnt by the data type.*/
//	    int[] horizAligns = {
//	      0,0,pw.ALIGN_CENTER,pw.ALIGN_CENTER};
//	      
//	    pw.applyColumnHeader(headerkeys, types, widths, horizAligns);	  
//
////	  now write data
////	  DateFormat longDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
////	  DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
////	  DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
//	  while(iterator.hasNext())
//	  {
//		  UserPageAdminViewBean bean = (UserPageAdminViewBean) iterator.next();
////		  String startDate = (bean.getDateOfReceipt()==null)?"":shortDateFormat.format(bean.getDateOfReceipt() );
////		  String productionDate = (bean.getTransactionDate()==null)?"":longDateFormat.format(bean.getTransactionDate() );
////		  BigDecimal zero = new BigDecimal(0);
////		  String startPageOrder = (bean.getStartPageOrder()==null)?"":bean.getStartPageOrder().toString();
//		  String isAdmin=(bean.getAdminRole()!=null && (bean.getAdminRole().indexOf("Admin")>=0 ))?"Y":"N";
//		  pw.addRow();
//		  pw.addCell( bean.getCompanyName());
//		  pw.addCell( bean.getPageName());
//		  pw.addCell( bean.getPageAccess());
//		  pw.addCell( isAdmin );
//		  
//	  }
//	  return pw;
//}
}
