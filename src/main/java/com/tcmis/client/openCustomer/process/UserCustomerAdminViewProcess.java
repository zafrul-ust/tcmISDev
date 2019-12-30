package com.tcmis.client.openCustomer.process;

import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.UserPageAdminViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;

import com.tcmis.client.openCustomer.beans.UserCustomerAdminViewBean;
import com.tcmis.internal.distribution.beans.SalesQuoteViewBean;
/******************************************************************************
 * Process for CustomerPagesAction
 * @version 1.0
 *****************************************************************************/
public class UserCustomerAdminViewProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public UserCustomerAdminViewProcess(String client) {
	    super(client);
	  }
		
	public UserCustomerAdminViewProcess(String client, String locale) {
	    super(client,locale);
	}

	public Collection getSearchResult(UserCustomerAdminViewBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new UserCustomerAdminViewBean());
		
		StringBuilder query = new StringBuilder("select * from table (PKG_OPEN_CUSTOMER.FX_GET_USER_CUST_ADMIN_DATA(");
		query.append(bean.getUserId()).append(",").append(bean.getPersonnelId()).append(")) order by customer_name");
		
		return factory.selectQuery(query.toString());
	}
	
	public String updateValue(UserCustomerAdminViewBean bean) throws BaseException {
		Collection inArgs = new Vector();
//		setting business logic...   
		String adminRole = "";
		if ((!bean.getCustomerAccess().equals(bean.getOldCustomerAccess())) || (!bean.getAdminRole().equals(bean.getOldAdminRole()))) {
			adminRole = "No Admin";
			if( !bean.getCustomerAccess().equals(bean.getOldCustomerAccess()) && bean.getOldCustomerAccess().equals("Y") ) {
					 adminRole ="None";
			}else {
				if( !bean.getAdminRole().equals(bean.getOldAdminRole())) {
					 if( bean.getAdminRole().equals("View"))
						 adminRole="No Admin";
					 else
						 adminRole="Admin";
				}
			}
		}


		if( bean.getCustomerId() != null) {
			inArgs.add( bean.getCustomerId() );
			}
			else {
			inArgs.add("");
		}
		
		if( bean.getPersonnelId() != null) {
			inArgs.add( bean.getPersonnelId() );
			}
			else {
			inArgs.add("");
			}

		inArgs.add(adminRole);

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
		Collection coll = procFactory.doProcedure("PKG_OPEN_CUSTOMER.P_SET_USER_CUSTOMER", inArgs,outArgs);

		   String errorMsg = (String)((Vector)coll).get(0);
		   if( errorMsg != null && errorMsg.length() > 0 )
			   return errorMsg;
		   return "";
/*		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new UserCustomerAdminViewBean());
		
		StringBuilder query = new StringBuilder("select * from table (PKG_OPEN_CUSTOMER.FX_GET_USER_CUST_ADMIN_DATA(");
		query.append(bean.getPersonnelId()).append(",").append(bean.getUserId()).append("))");
		
		return factory.selectQuery(query.toString()); */ 
	}
	
	public ExcelHandler getExcelReport(Collection<UserCustomerAdminViewBean> data, Locale locale) throws
    NoDataException, BaseException, Exception {
	  
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);
	
		pw.addTable();
	//write column headers
		pw.addRow();
	/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
		  "label.customer","label.access","label.admin"};
	
		int[] types = {
		  0,0,0};
		
		int[] widths = {
		  25,10,10};
	
		int[] horizAligns = {
		  0,0,0};
		  
		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	
		for (UserCustomerAdminViewBean member : data) {
		  
	    pw.addRow();
	    pw.addCell(member.getCustomerName());
	    pw.addCell(member.getCustomerAccess());
	    pw.addCell(member.getAdminRole());
	  }
	  return pw;
  }
	
}