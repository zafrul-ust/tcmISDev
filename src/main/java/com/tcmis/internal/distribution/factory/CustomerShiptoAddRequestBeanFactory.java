package com.tcmis.internal.distribution.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.Connection;

import java.text.ParseException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.internal.distribution.beans.CustomerShiptoAddRequestBean;


/******************************************************************************
 * CLASSNAME: CustomerShiptoAddRequestBeanFactory <br>
 * @version: 1.0, Aug 20, 2009 <br>
 *****************************************************************************/


public class CustomerShiptoAddRequestBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_CUSTOMER_REQUEST_ID = "CUSTOMER_REQUEST_ID";
	public String ATTRIBUTE_CUSTOMER_ID = "CUSTOMER_ID";
	public String ATTRIBUTE_BILL_TO_COMPANY_ID = "BILL_TO_COMPANY_ID";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1 = "SHIP_TO_ADDRESS_LINE_1";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2 = "SHIP_TO_ADDRESS_LINE_2";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3 = "SHIP_TO_ADDRESS_LINE_3";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4 = "SHIP_TO_ADDRESS_LINE_4";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_5 = "SHIP_TO_ADDRESS_LINE_5";
	public String ATTRIBUTE_SHIP_TO_COUNTRY_ABBREV = "SHIP_TO_COUNTRY_ABBREV";
	public String ATTRIBUTE_SHIP_TO_STATE_ABBREV = "SHIP_TO_STATE_ABBREV";
	public String ATTRIBUTE_SHIP_TO_CITY = "SHIP_TO_CITY";
	public String ATTRIBUTE_SHIP_TO_ZIP = "SHIP_TO_ZIP";
	public String ATTRIBUTE_DEFAULT_INVENTORY_GROUP = "DEFAULT_INVENTORY_GROUP";
	public String ATTRIBUTE_LOCATION_DESC = "LOCATION_DESC";
	public String ATTRIBUTE_SALES_AGENT_ID = "SALES_AGENT_ID";
	public String ATTRIBUTE_FIELD_SALES_REP_ID = "FIELD_SALES_REP_ID";
	public String ATTRIBUTE_PRICE_GROUP_ID = "PRICE_GROUP_ID";
	public String ATTRIBUTE_INTERNAL_NOTE = "INTERNAL_NOTE";
	public String ATTRIBUTE_MSDS_LOCALE_OVERRIDE = "MSDS_LOCALE_OVERRIDE";
	public String ATTRIBUTE_JDE_CUSTOMER_SHIP_TO = "JDE_CUSTOMER_SHIP_TO";
	
	//table name
	public String TABLE = "CUSTOMER_SHIPTO_ADD_REQUEST";


	//constructor
	public CustomerShiptoAddRequestBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("customerRequestId")) {
			return ATTRIBUTE_CUSTOMER_REQUEST_ID;
		}
		else if(attributeName.equals("customerId")) {
			return ATTRIBUTE_CUSTOMER_ID;
		}
		else if(attributeName.equals("billToCompanyId")) {
			return ATTRIBUTE_BILL_TO_COMPANY_ID;
		}
		else if(attributeName.equals("shipToAddressLine1")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1;
		}
		else if(attributeName.equals("shipToAddressLine2")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2;
		}
		else if(attributeName.equals("shipToAddressLine3")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3;
		}
		else if(attributeName.equals("shipToAddressLine4")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4;
		}
		else if(attributeName.equals("shipToAddressLine5")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_5;
		}
		else if(attributeName.equals("shipToCountryAbbrev")) {
			return ATTRIBUTE_SHIP_TO_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("shipToStateAbbrev")) {
			return ATTRIBUTE_SHIP_TO_STATE_ABBREV;
		}
		else if(attributeName.equals("shipToCity")) {
			return ATTRIBUTE_SHIP_TO_CITY;
		}
		else if(attributeName.equals("shipToZip")) {
			return ATTRIBUTE_SHIP_TO_ZIP;
		}
		else if(attributeName.equals("defaultInventoryGroup")) {
			return ATTRIBUTE_DEFAULT_INVENTORY_GROUP;
		}
		else if(attributeName.equals("locationDesc")) {
			return ATTRIBUTE_LOCATION_DESC;
		}
		else if(attributeName.equals("salesAgentId")) {
			return ATTRIBUTE_SALES_AGENT_ID;
		}
		else if(attributeName.equals("fieldSalesRepId")) {
			return ATTRIBUTE_FIELD_SALES_REP_ID;
		}	
		else if(attributeName.equals("priceGroupId")) {
			return ATTRIBUTE_PRICE_GROUP_ID;
		}	
		else if(attributeName.equals("internalNote")) {
			return ATTRIBUTE_INTERNAL_NOTE;
		}	
		else if(attributeName.equals("msdsLocaleOverride")) {
			return ATTRIBUTE_MSDS_LOCALE_OVERRIDE;
		}
		else if(attributeName.equals("jdeCustomerShipTo")) {
			return ATTRIBUTE_JDE_CUSTOMER_SHIP_TO;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CustomerShiptoAddRequestBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(CustomerShiptoAddRequestBean customerShiptoAddRequestBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("customerRequestId", "SearchCriterion.EQUALS",
			"" + customerShiptoAddRequestBean.getCustomerRequestId());

		Connection connection = null;
		int result = 0;
		try {
			connection = this.getDbManager().getConnection();
			result = this.delete(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int delete(CustomerShiptoAddRequestBean customerShiptoAddRequestBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("customerRequestId", "SearchCriterion.EQUALS",
			"" + customerShiptoAddRequestBean.getCustomerRequestId());

		return delete(criteria, conn);
	}
*/

	public int delete(SearchCriteria criteria)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = delete(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int delete(SearchCriteria criteria, Connection conn)
		throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " +
			getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}


//you need to verify the primary key(s) before uncommenting this
	//insert
	public int insert(CustomerShiptoAddRequestBean customerShiptoAddRequestBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(customerShiptoAddRequestBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(CustomerShiptoAddRequestBean customerShiptoAddRequestBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();
		String query = "";

		try {

		query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_CUSTOMER_REQUEST_ID + "," +
			ATTRIBUTE_CUSTOMER_ID + "," +
			ATTRIBUTE_BILL_TO_COMPANY_ID + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1 + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2 + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3 + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4 + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_5 + "," +
			ATTRIBUTE_SHIP_TO_COUNTRY_ABBREV + "," +
			ATTRIBUTE_SHIP_TO_STATE_ABBREV + "," +
			ATTRIBUTE_SHIP_TO_CITY + "," +
			ATTRIBUTE_SHIP_TO_ZIP + "," +
			ATTRIBUTE_DEFAULT_INVENTORY_GROUP + "," +
			ATTRIBUTE_SALES_AGENT_ID + "," +
			ATTRIBUTE_FIELD_SALES_REP_ID + "," +
			ATTRIBUTE_PRICE_GROUP_ID + "," +
			ATTRIBUTE_INTERNAL_NOTE + "," +
			ATTRIBUTE_MSDS_LOCALE_OVERRIDE + "," +
			ATTRIBUTE_LOCATION_DESC  + "," +
			ATTRIBUTE_JDE_CUSTOMER_SHIP_TO + ")" +
			" values (" +
			customerShiptoAddRequestBean.getCustomerRequestId() + "," +
			customerShiptoAddRequestBean.getCustomerId() + "," +
			SqlHandler.delimitString(customerShiptoAddRequestBean.getBillToCompanyId()) + "," +
			SqlHandler.delimitString(customerShiptoAddRequestBean.getShipToAddressLine1()) + "," +
			SqlHandler.delimitString(customerShiptoAddRequestBean.getShipToAddressLine2()) + "," +
			SqlHandler.delimitString(customerShiptoAddRequestBean.getShipToAddressLine3()) + "," +
			SqlHandler.delimitString(customerShiptoAddRequestBean.getShipToAddressLine4()) + "," +
			SqlHandler.delimitString(customerShiptoAddRequestBean.getShipToAddressLine5()) + "," +
			SqlHandler.delimitString(customerShiptoAddRequestBean.getShipToCountryAbbrev()) + "," +
			SqlHandler.delimitString(customerShiptoAddRequestBean.getShipToStateAbbrev()) + "," +
			SqlHandler.delimitString(customerShiptoAddRequestBean.getShipToCity()) + "," +
			SqlHandler.delimitString(customerShiptoAddRequestBean.getShipToZip()) + "," +
			SqlHandler.delimitString(customerShiptoAddRequestBean.getDefaultInventoryGroup()) + "," +
			StringHandler.nullIfZero(customerShiptoAddRequestBean.getSalesAgentId()) + "," +
			StringHandler.nullIfZero(customerShiptoAddRequestBean.getFieldSalesRepId()) + "," +
			SqlHandler.delimitString(customerShiptoAddRequestBean.getPriceGroupId()) + "," +
			SqlHandler.delimitString(customerShiptoAddRequestBean.getInternalNote()) + "," +
			SqlHandler.delimitString(customerShiptoAddRequestBean.getMsdsLocaleOverride()) + "," +
			SqlHandler.delimitString(customerShiptoAddRequestBean.getLocationDesc())  + "," +
			StringHandler.nullIfZero(customerShiptoAddRequestBean.getJdeCustomerShipTo())+ ")";

		return sqlManager.update(conn, query);
		} catch(Exception ex) { 
			String retValue = null;
			java.io.StringWriter sw = new java.io.StringWriter();
			java.io.PrintWriter pw =  new java.io.PrintWriter(sw);
			ex.printStackTrace(pw);
			retValue = sw.toString();
			MailProcess.sendEmail("lliu@haastcm.com", "", "", "customer request shipto insert error:"+ex.getMessage(), retValue+"\n\nquery:\n"+query);
		}		
		return 1;
	}


	//update
	public int update(CustomerShiptoAddRequestBean customerShiptoAddRequestBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(customerShiptoAddRequestBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(CustomerShiptoAddRequestBean customerShiptoAddRequestBean, Connection conn)
		throws BaseException {
		String query = "";

		try {
			query = "update " + TABLE + " set " +
			ATTRIBUTE_CUSTOMER_REQUEST_ID + "=" + 
				StringHandler.nullIfZero(customerShiptoAddRequestBean.getCustomerRequestId()) + "," +
			ATTRIBUTE_CUSTOMER_ID + "=" + 
				StringHandler.nullIfZero(customerShiptoAddRequestBean.getCustomerId()) + "," +
			ATTRIBUTE_BILL_TO_COMPANY_ID + "=" + 
				SqlHandler.delimitString(customerShiptoAddRequestBean.getBillToCompanyId()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1 + "=" + 
				SqlHandler.delimitString(customerShiptoAddRequestBean.getShipToAddressLine1()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2 + "=" + 
				SqlHandler.delimitString(customerShiptoAddRequestBean.getShipToAddressLine2()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3 + "=" + 
				SqlHandler.delimitString(customerShiptoAddRequestBean.getShipToAddressLine3()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4 + "=" + 
				SqlHandler.delimitString(customerShiptoAddRequestBean.getShipToAddressLine4()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_5 + "=" + 
				SqlHandler.delimitString(customerShiptoAddRequestBean.getShipToAddressLine5()) + "," +
			ATTRIBUTE_SHIP_TO_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(customerShiptoAddRequestBean.getShipToCountryAbbrev()) + "," +
			ATTRIBUTE_SHIP_TO_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(customerShiptoAddRequestBean.getShipToStateAbbrev()) + "," +
			ATTRIBUTE_SHIP_TO_CITY + "=" + 
				SqlHandler.delimitString(customerShiptoAddRequestBean.getShipToCity()) + "," +
			ATTRIBUTE_SHIP_TO_ZIP + "=" + 
				SqlHandler.delimitString(customerShiptoAddRequestBean.getShipToZip()) + "," +
			ATTRIBUTE_DEFAULT_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(customerShiptoAddRequestBean.getDefaultInventoryGroup()) + " " +
			ATTRIBUTE_SALES_AGENT_ID + "=" + 
				StringHandler.nullIfZero(customerShiptoAddRequestBean.getSalesAgentId()) + "," +
			ATTRIBUTE_FIELD_SALES_REP_ID + "=" + 
				StringHandler.nullIfZero(customerShiptoAddRequestBean.getFieldSalesRepId()) + "," +
			ATTRIBUTE_LOCATION_DESC + "=" + 
				SqlHandler.delimitString(customerShiptoAddRequestBean.getLocationDesc()) + " " +
			ATTRIBUTE_INTERNAL_NOTE + "=" + 
				SqlHandler.delimitString(customerShiptoAddRequestBean.getInternalNote()) + " " +
			ATTRIBUTE_MSDS_LOCALE_OVERRIDE + "=" + 
				SqlHandler.delimitString(customerShiptoAddRequestBean.getMsdsLocaleOverride()) + " " +
			ATTRIBUTE_PRICE_GROUP_ID + "=" + 
				SqlHandler.delimitString(customerShiptoAddRequestBean.getPriceGroupId()) + " " +
			ATTRIBUTE_JDE_CUSTOMER_SHIP_TO + "=" +
			StringHandler.nullIfZero(customerShiptoAddRequestBean.getJdeCustomerShipTo())  + " " +
			"where " + ATTRIBUTE_CUSTOMER_REQUEST_ID + "=" +
				customerShiptoAddRequestBean.getCustomerRequestId() +
				" and shipToAddressLine1 = '" + SqlHandler.delimitString(customerShiptoAddRequestBean.getShipToAddressLine1()) +"'" + 
				" and shipToAddressLine2 = '" + SqlHandler.delimitString(customerShiptoAddRequestBean.getShipToAddressLine2()) +"'";
		
		return new SqlManager().update(conn, query);
		} catch(Exception ex) { 
			String retValue = null;
			java.io.StringWriter sw = new java.io.StringWriter();
			java.io.PrintWriter pw =  new java.io.PrintWriter(sw);
			ex.printStackTrace(pw);
			retValue = sw.toString();
			MailProcess.sendEmail("lliu@haastcm.com", "", "", "customer request shipto update error:"+ex.getMessage(), retValue+"\n\nquery:\n"+query);
		}		
		return 1;
	}

	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

		return select(criteria,null);

		}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria,sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection customerShiptoAddRequestBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CustomerShiptoAddRequestBean customerShiptoAddRequestBean = new CustomerShiptoAddRequestBean();
			load(dataSetRow, customerShiptoAddRequestBean);
			customerShiptoAddRequestBeanColl.add(customerShiptoAddRequestBean);
		}

		return customerShiptoAddRequestBeanColl;
	}
}