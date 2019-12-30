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
import com.tcmis.internal.distribution.beans.CustomerContactAddRequestBean;


/******************************************************************************
 * CLASSNAME: CustomerContactAddRequestBeanFactory <br>
 * @version: 1.0, Aug 20, 2009 <br>
 *****************************************************************************/


public class CustomerContactAddRequestBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_CUSTOMER_REQUEST_ID = "CUSTOMER_REQUEST_ID";
	public String ATTRIBUTE_CUSTOMER_ID = "CUSTOMER_ID";
	public String ATTRIBUTE_BILL_TO_COMPANY_ID = "BILL_TO_COMPANY_ID";
	public String ATTRIBUTE_CONTACT_PERSONNEL_ID = "CONTACT_PERSONNEL_ID";
	public String ATTRIBUTE_CONTACT_TYPE = "CONTACT_TYPE";
	public String ATTRIBUTE_FIRST_NAME = "FIRST_NAME";
	public String ATTRIBUTE_LAST_NAME = "LAST_NAME";
	public String ATTRIBUTE_NICKNAME = "NICKNAME";
	public String ATTRIBUTE_PHONE = "PHONE";
	public String ATTRIBUTE_MOBILE = "MOBILE";
	public String ATTRIBUTE_FAX = "FAX";
	public String ATTRIBUTE_EMAIL = "EMAIL";
	public String ATTRIBUTE_PURCHASING = "PURCHASING";
	public String ATTRIBUTE_ACCOUNTS_PAYABLE = "ACCOUNTS_PAYABLE";
	public String ATTRIBUTE_RECEIVING = "RECEIVING";
	public String ATTRIBUTE_QUALITY_ASSURANCE = "QUALITY_ASSURANCE";
	public String ATTRIBUTE_MANAGEMENT = "MANAGEMENT";
	public String ATTRIBUTE_DEFAULT_CONTACT = "DEFAULT_CONTACT";

	//table name
	public String TABLE = "CUSTOMER_CONTACT_ADD_REQUEST";


	//constructor
	public CustomerContactAddRequestBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("contactPersonnelId")) {
			return ATTRIBUTE_CONTACT_PERSONNEL_ID;
		}
		else if(attributeName.equals("contactType")) {
			return ATTRIBUTE_CONTACT_TYPE;
		}
		else if(attributeName.equals("firstName")) {
			return ATTRIBUTE_FIRST_NAME;
		}
		else if(attributeName.equals("lastName")) {
			return ATTRIBUTE_LAST_NAME;
		}
		else if(attributeName.equals("nickname")) {
			return ATTRIBUTE_NICKNAME;
		}
		else if(attributeName.equals("phone")) {
			return ATTRIBUTE_PHONE;
		}
		else if(attributeName.equals("mobile")) {
			return ATTRIBUTE_MOBILE;
		}
		else if(attributeName.equals("fax")) {
			return ATTRIBUTE_FAX;
		}
		else if(attributeName.equals("email")) {
			return ATTRIBUTE_EMAIL;
		}
		else if(attributeName.equals("purchasing")) {
			return ATTRIBUTE_PURCHASING;
		}
		else if(attributeName.equals("accountsPayable")) {
			return ATTRIBUTE_ACCOUNTS_PAYABLE;
		}
		else if(attributeName.equals("receiving")) {
			return ATTRIBUTE_RECEIVING;
		}
		else if(attributeName.equals("qualityAssurance")) {
			return ATTRIBUTE_QUALITY_ASSURANCE;
		}
		else if(attributeName.equals("management")) {
			return ATTRIBUTE_MANAGEMENT;
		}
		else if(attributeName.equals("defaultContact")) {
			return ATTRIBUTE_DEFAULT_CONTACT;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CustomerContactAddRequestBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(CustomerContactAddRequestBean customerContactAddRequestBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("customerRequestId", "SearchCriterion.EQUALS",
			"" + customerContactAddRequestBean.getCustomerRequestId());

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


	public int delete(CustomerContactAddRequestBean customerContactAddRequestBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("customerRequestId", "SearchCriterion.EQUALS",
			"" + customerContactAddRequestBean.getCustomerRequestId());

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
	public int insert(CustomerContactAddRequestBean customerContactAddRequestBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(customerContactAddRequestBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(CustomerContactAddRequestBean customerContactAddRequestBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();
		
		String defaultContact = "''";
		if("true".equals(customerContactAddRequestBean.getDefaultContact())) defaultContact = "'Y'";
		
		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_CUSTOMER_REQUEST_ID + "," +
			ATTRIBUTE_CUSTOMER_ID + "," +
			ATTRIBUTE_BILL_TO_COMPANY_ID + "," +
			//ATTRIBUTE_CONTACT_PERSONNEL_ID + "," +
			ATTRIBUTE_CONTACT_TYPE + "," +
			ATTRIBUTE_FIRST_NAME + "," +
			ATTRIBUTE_LAST_NAME + "," +
			ATTRIBUTE_NICKNAME + "," +
			ATTRIBUTE_PHONE + "," +
			ATTRIBUTE_MOBILE + "," +
			ATTRIBUTE_FAX + "," +
			ATTRIBUTE_EMAIL + "," +
			ATTRIBUTE_PURCHASING + "," +
			ATTRIBUTE_ACCOUNTS_PAYABLE + "," +
			ATTRIBUTE_RECEIVING + "," +
			ATTRIBUTE_QUALITY_ASSURANCE + "," +
			ATTRIBUTE_MANAGEMENT + "," +
			ATTRIBUTE_DEFAULT_CONTACT + ")" +
			" values (" +
			customerContactAddRequestBean.getCustomerRequestId() + "," +
			customerContactAddRequestBean.getCustomerId() + "," +
			SqlHandler.delimitString(customerContactAddRequestBean.getBillToCompanyId()) + "," +
			//customerContactAddRequestBean.getContactPersonnelId() + "," +
			SqlHandler.delimitString(customerContactAddRequestBean.getContactType()) + "," +
			SqlHandler.delimitString(customerContactAddRequestBean.getFirstName()) + "," +
			SqlHandler.delimitString(customerContactAddRequestBean.getLastName()) + "," +
			SqlHandler.delimitString(customerContactAddRequestBean.getNickname()) + "," +
			SqlHandler.delimitString(customerContactAddRequestBean.getPhone()) + "," +
			SqlHandler.delimitString(customerContactAddRequestBean.getMobile()) + "," +
			SqlHandler.delimitString(customerContactAddRequestBean.getFax()) + "," +
			SqlHandler.delimitString(customerContactAddRequestBean.getEmail()) + "," +
			SqlHandler.delimitString(customerContactAddRequestBean.getPurchasing()) + "," +
			SqlHandler.delimitString(customerContactAddRequestBean.getAccountsPayable()) + "," +
			SqlHandler.delimitString(customerContactAddRequestBean.getReceiving()) + "," +
			SqlHandler.delimitString(customerContactAddRequestBean.getQualityAssurance()) + "," +
			SqlHandler.delimitString(customerContactAddRequestBean.getManagement()) + "," +
			defaultContact + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(CustomerContactAddRequestBean customerContactAddRequestBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(customerContactAddRequestBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(CustomerContactAddRequestBean customerContactAddRequestBean, Connection conn)
		throws BaseException {
		
		String defaultContact = "''";
		if("true".equals(customerContactAddRequestBean.getDefaultContact())) defaultContact = "'Y'";
		
		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_CUSTOMER_REQUEST_ID + "=" + 
				StringHandler.nullIfZero(customerContactAddRequestBean.getCustomerRequestId()) + "," +
			ATTRIBUTE_CUSTOMER_ID + "=" + 
				StringHandler.nullIfZero(customerContactAddRequestBean.getCustomerId()) + "," +
			ATTRIBUTE_BILL_TO_COMPANY_ID + "=" + 
				SqlHandler.delimitString(customerContactAddRequestBean.getBillToCompanyId()) + "," +
			ATTRIBUTE_CONTACT_PERSONNEL_ID + "=" + 
				StringHandler.nullIfZero(customerContactAddRequestBean.getContactPersonnelId()) + "," +
			ATTRIBUTE_CONTACT_TYPE + "=" + 
				SqlHandler.delimitString(customerContactAddRequestBean.getContactType()) + "," +
			ATTRIBUTE_FIRST_NAME + "=" + 
				SqlHandler.delimitString(customerContactAddRequestBean.getFirstName()) + "," +
			ATTRIBUTE_LAST_NAME + "=" + 
				SqlHandler.delimitString(customerContactAddRequestBean.getLastName()) + "," +
			ATTRIBUTE_NICKNAME + "=" + 
				SqlHandler.delimitString(customerContactAddRequestBean.getNickname()) + "," +
			ATTRIBUTE_PHONE + "=" + 
				SqlHandler.delimitString(customerContactAddRequestBean.getPhone()) + "," +
			ATTRIBUTE_MOBILE + "=" + 
				SqlHandler.delimitString(customerContactAddRequestBean.getMobile()) + "," +
			ATTRIBUTE_FAX + "=" + 
				SqlHandler.delimitString(customerContactAddRequestBean.getFax()) + "," +
			ATTRIBUTE_EMAIL + "=" + 
				SqlHandler.delimitString(customerContactAddRequestBean.getEmail()) + "," +
			ATTRIBUTE_PURCHASING + "=" + 
				SqlHandler.delimitString(customerContactAddRequestBean.getPurchasing()) + "," +
			ATTRIBUTE_ACCOUNTS_PAYABLE + "=" + 
				SqlHandler.delimitString(customerContactAddRequestBean.getAccountsPayable()) + "," +
			ATTRIBUTE_RECEIVING + "=" + 
				SqlHandler.delimitString(customerContactAddRequestBean.getReceiving()) + "," +
			ATTRIBUTE_QUALITY_ASSURANCE + "=" + 
				SqlHandler.delimitString(customerContactAddRequestBean.getQualityAssurance()) + "," +
			ATTRIBUTE_MANAGEMENT + "=" + 
				SqlHandler.delimitString(customerContactAddRequestBean.getManagement()) + " " +
			ATTRIBUTE_DEFAULT_CONTACT + "=" + 
				defaultContact + " " +
			"where " + ATTRIBUTE_CUSTOMER_REQUEST_ID + "=" +
				customerContactAddRequestBean.getCustomerRequestId() +
				" and " + ATTRIBUTE_FIRST_NAME + " = '" + customerContactAddRequestBean.getFirstName() + "'" +
				" and " + ATTRIBUTE_LAST_NAME + " = '" + customerContactAddRequestBean.getLastName() + "'" 
				;

		return new SqlManager().update(conn, query);
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

		Collection customerContactAddRequestBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CustomerContactAddRequestBean customerContactAddRequestBean = new CustomerContactAddRequestBean();
			load(dataSetRow, customerContactAddRequestBean);
			customerContactAddRequestBeanColl.add(customerContactAddRequestBean);
		}

		return customerContactAddRequestBeanColl;
	}
}