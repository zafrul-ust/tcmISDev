package com.tcmis.client.openCustomer.process;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.UserPageAdminViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
   /******************************************************************************
   * Process for UserPageAdminView
   * @version 1.0
   *****************************************************************************/
  public class UserPageAdminDataProcess
   extends BaseProcess {
   Log log = LogFactory.getLog(this.getClass());

   public UserPageAdminDataProcess(String client) {
	   super(client);
   }
   
   public UserPageAdminDataProcess(String client,String locale) {
	    super(client,locale);
   }
   
   public Collection getDistinctStartupSearchResult(UserPageAdminViewBean bean) throws BaseException {
	    DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new UserPageAdminViewBean());
		
		StringBuilder query = new StringBuilder("select * from table (PKG_OPEN_CUSTOMER.FX_GET_USER_PAGE_ADMIN_DATA(");
		query.append(bean.getUserId()).append(",").append(bean.getPersonnelId()).append(")) order by page_name");
		
		return factory.selectQuery(query.toString());
   }
   
   public String updateValue(UserPageAdminViewBean bean)  throws
   			BaseException, Exception {
	   Collection inArgs = new Vector();
	   if( bean.getPersonnelId() != null) {
	   		inArgs.add( bean.getPersonnelId() );
	   }
	   else {
	   		inArgs.add("");
	   }
	
	   if( bean.getPageId() != null) {
	   		inArgs.add( bean.getPageId() );
	   }
	   else {
	   		inArgs.add("");
	   }
	
	   if( bean.getStartPage() != null) {
	   		inArgs.add( bean.getStartPage() );
	   }
	   else {
	   		inArgs.add("");
	   }
	
	   if( bean.getStartPageOrder() != null) {
	   		inArgs.add( bean.getStartPageOrder() );
	   }
	   else {
	   		inArgs.add("");
	   }
	
	   if( bean.getUserId() != null) {
		   inArgs.add( bean.getUserId() );
	   }
	   else {
		   inArgs.add("");
	   }
	
	   Vector outArgs = new Vector();
	   outArgs.add( new Integer(java.sql.Types.VARCHAR) );
	
	   DbManager dbManager = new DbManager(getClient(),getLocale());
	   GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
	   Collection coll = procFactory.doProcedure("p_set_user_start_page", inArgs,outArgs);
	   
	   String errorMsg = (String)((Vector)coll).get(0);
	   if( errorMsg != null && errorMsg.length() > 0 )
		   return errorMsg;
	   return "";
   }


	public ExcelHandler  getStartupExcelReport(UserPageAdminViewBean inputBean,Collection data, Locale locale,String fullName) throws
			NoDataException, BaseException, Exception
	{
		  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	
		  Iterator iterator = data.iterator();
		  ExcelHandler pw = new ExcelHandler(library);
		  
		  pw.addTable();
	
	//	  write column headers
		  pw.addRow();
		  pw.addCellKeyBold("label.name");
		  pw.addCell(fullName);
		  
		  pw.addRow();
		  
	//	  write column headers
		  pw.addRow();
		  /*Pass the header keys for the Excel.*/
		    String[] headerkeys = {
		      "label.pages","label.startup","label.startupOrder"};
		    /*This array defines the type of the excel column.
		    0 means default depending on the data type. */
		    int[] types = {
		      0,0,0};
		    /*This array defines the default width of the column when the Excel file opens.
		    0 means the width will be default depending on the data type.*/
		    int[] widths = {
		      50,30,0};
		    /*This array defines the horizontal alignment of the data in a cell.
		    0 means excel defaults the horizontal alignemnt by the data type.*/
		    int[] horizAligns = {
		      0,0,0};
		      
		    pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
		
	//	  now write data
	//	  DateFormat longDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	//	  DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	//	  DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
				  while(iterator.hasNext())
		  {
			  UserPageAdminViewBean bean = (UserPageAdminViewBean) iterator.next();
	//		  String startDate = (bean.getDateOfReceipt()==null)?"":shortDateFormat.format(bean.getDateOfReceipt() );
	//		  String productionDate = (bean.getTransactionDate()==null)?"":longDateFormat.format(bean.getTransactionDate() );
	//		  BigDecimal zero = new BigDecimal(0);
			  String startPageOrder = (bean.getStartPageOrder()==null)?"":bean.getStartPageOrder().toString();
			  String isStartPage=(bean.getStartPage()!=null && bean.getStartPage().equals("Y"))?"Yes":"No";
			  pw.addRow();
			  pw.addCell(bean.getPageName());
			  pw.addCell( isStartPage );
			  pw.addCell( startPageOrder );
			  
		  }
		return pw;
	}

}
