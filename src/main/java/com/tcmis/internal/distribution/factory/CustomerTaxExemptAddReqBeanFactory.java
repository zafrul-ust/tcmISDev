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
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.internal.distribution.beans.CustomerTaxExemptAddReqBean;


/******************************************************************************
 * CLASSNAME: CustomerTaxExemptAddReqBeanFactory <br>
 * @version: 1.0, Sep 2, 2009 <br>
 *****************************************************************************/


public class CustomerTaxExemptAddReqBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_CUSTOMER_REQUEST_ID = "CUSTOMER_REQUEST_ID";
	public String ATTRIBUTE_CUSTOMER_ID = "CUSTOMER_ID";
	public String ATTRIBUTE_BILL_TO_COMPANY_ID = "BILL_TO_COMPANY_ID";
	public String ATTRIBUTE_STATE_ABBREV = "STATE_ABBREV";
	public String ATTRIBUTE_TAX_EXEMPTION_CERTIFICATE = "TAX_EXEMPTION_CERTIFICATE";
	public String ATTRIBUTE_COUNTRY_ABBREV = "COUNTRY_ABBREV";
	public String ATTRIBUTE_EXPIRATION_DATE = "EXPIRATION_DATE";
//	
	//table name
	public String TABLE = "CUSTOMER_TAX_EXEMPT_ADD_REQ";


	//constructor
	public CustomerTaxExemptAddReqBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("stateAbbrev")) {
			return ATTRIBUTE_STATE_ABBREV;
		}
		else if(attributeName.equals("countryAbbrev")) {
			return ATTRIBUTE_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("taxExemptionCertificate")) {
			return ATTRIBUTE_TAX_EXEMPTION_CERTIFICATE;
		}
		else if(attributeName.equals("expirationDate")) {
			return ATTRIBUTE_EXPIRATION_DATE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CustomerTaxExemptAddReqBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(CustomerTaxExemptAddReqBean customerTaxExemptAddReqBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("customerRequestId", "SearchCriterion.EQUALS",
			"" + customerTaxExemptAddReqBean.getCustomerRequestId());

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


	public int delete(CustomerTaxExemptAddReqBean customerTaxExemptAddReqBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("customerRequestId", "SearchCriterion.EQUALS",
			"" + customerTaxExemptAddReqBean.getCustomerRequestId());

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
	public int insert(CustomerTaxExemptAddReqBean customerTaxExemptAddReqBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(customerTaxExemptAddReqBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int insert(CustomerTaxExemptAddReqBean customerTaxExemptAddReqBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_CUSTOMER_REQUEST_ID + "," +
			ATTRIBUTE_CUSTOMER_ID + "," +
			ATTRIBUTE_BILL_TO_COMPANY_ID + "," +
			ATTRIBUTE_STATE_ABBREV + "," +
			ATTRIBUTE_COUNTRY_ABBREV + "," +
			ATTRIBUTE_EXPIRATION_DATE + "," +
			ATTRIBUTE_TAX_EXEMPTION_CERTIFICATE + ")" +
			" values (" +
			customerTaxExemptAddReqBean.getCustomerRequestId() + "," +
			customerTaxExemptAddReqBean.getCustomerId() + "," +
			SqlHandler.delimitString(customerTaxExemptAddReqBean.getBillToCompanyId()) + "," +
			SqlHandler.delimitString(customerTaxExemptAddReqBean.getStateAbbrev()) + "," +
			SqlHandler.delimitString(customerTaxExemptAddReqBean.getCountryAbbrev()) + "," +
			DateHandler.getOracleToDateFunction(customerTaxExemptAddReqBean.getExpirationDate()) + "," +
			SqlHandler.delimitString(customerTaxExemptAddReqBean.getTaxExemptionCertificate()) + ")";

		return sqlManager.update(conn, query);
	}

	/*

	//update
	public int update(CustomerTaxExemptAddReqBean customerTaxExemptAddReqBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(customerTaxExemptAddReqBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(CustomerTaxExemptAddReqBean customerTaxExemptAddReqBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_CUSTOMER_REQUEST_ID + "=" + 
				StringHandler.nullIfZero(customerTaxExemptAddReqBean.getCustomerRequestId()) + "," +
			ATTRIBUTE_CUSTOMER_ID + "=" + 
				StringHandler.nullIfZero(customerTaxExemptAddReqBean.getCustomerId()) + "," +
			ATTRIBUTE_BILL_TO_COMPANY_ID + "=" + 
				SqlHandler.delimitString(customerTaxExemptAddReqBean.getBillToCompanyId()) + "," +
			ATTRIBUTE_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(customerTaxExemptAddReqBean.getStateAbbrev()) + "," +
			ATTRIBUTE_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(customerTaxExemptAddReqBean.getCountryAbbrev()) + "," +
			ATTRIBUTE_TAX_EXEMPTION_CERTIFICATE + "=" + 
				SqlHandler.delimitString(customerTaxExemptAddReqBean.getTaxExemptionCertificate()) + " " +
			"where " + ATTRIBUTE_CUSTOMER_REQUEST_ID + "=" +
				customerTaxExemptAddReqBean.getCustomerRequestId();

		return new SqlManager().update(conn, query);
	}
*/

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

		Collection customerTaxExemptAddReqBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CustomerTaxExemptAddReqBean customerTaxExemptAddReqBean = new CustomerTaxExemptAddReqBean();
			load(dataSetRow, customerTaxExemptAddReqBean);
			customerTaxExemptAddReqBeanColl.add(customerTaxExemptAddReqBean);
		}

		return customerTaxExemptAddReqBeanColl;
	}
}