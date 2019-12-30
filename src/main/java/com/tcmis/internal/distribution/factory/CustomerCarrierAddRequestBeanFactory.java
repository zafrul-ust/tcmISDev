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
import com.tcmis.internal.distribution.beans.CustomerCarrierAddRequestBean;


/******************************************************************************
 * CLASSNAME: CustomerCarrierAddRequestBeanFactory <br>
 * @version: 1.0, Aug 21, 2009 <br>
 *****************************************************************************/


public class CustomerCarrierAddRequestBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_CUSTOMER_REQUEST_ID = "CUSTOMER_REQUEST_ID";
	public String ATTRIBUTE_CUSTOMER_ID = "CUSTOMER_ID";
	public String ATTRIBUTE_BILL_TO_COMPANY_ID = "BILL_TO_COMPANY_ID";
	public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
	public String ATTRIBUTE_CARRIER_ACCOUNT = "CARRIER_ACCOUNT";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_TRANSPORTATION_MODE = "TRANSPORTATION_MODE";
	public String ATTRIBUTE_CARRIER_METHOD = "CARRIER_METHOD";
	public String ATTRIBUTE_NOTES = "NOTES";

	//table name
	public String TABLE = "CUSTOMER_CARRIER_ADD_REQUEST";


	//constructor
	public CustomerCarrierAddRequestBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("carrierName")) {
			return ATTRIBUTE_CARRIER_NAME;
		}
		else if(attributeName.equals("carrierAccount")) {
			return ATTRIBUTE_CARRIER_ACCOUNT;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("transportationMode")) {
			return ATTRIBUTE_TRANSPORTATION_MODE;
		}
		else if(attributeName.equals("carrierMethod")) {
			return ATTRIBUTE_CARRIER_METHOD;
		}
		else if(attributeName.equals("notes")) {
			return ATTRIBUTE_NOTES;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CustomerCarrierAddRequestBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(CustomerCarrierAddRequestBean customerCarrierAddRequestBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("customerRequestId", "SearchCriterion.EQUALS",
			"" + customerCarrierAddRequestBean.getCustomerRequestId());

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


	public int delete(CustomerCarrierAddRequestBean customerCarrierAddRequestBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("customerRequestId", "SearchCriterion.EQUALS",
			"" + customerCarrierAddRequestBean.getCustomerRequestId());

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
	public int insert(CustomerCarrierAddRequestBean customerCarrierAddRequestBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(customerCarrierAddRequestBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(CustomerCarrierAddRequestBean customerCarrierAddRequestBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_CUSTOMER_REQUEST_ID + "," +
			ATTRIBUTE_CUSTOMER_ID + "," +
			ATTRIBUTE_BILL_TO_COMPANY_ID + "," +
			ATTRIBUTE_CARRIER_NAME + "," +
			ATTRIBUTE_CARRIER_ACCOUNT + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_TRANSPORTATION_MODE + "," +
			ATTRIBUTE_NOTES + "," +
			ATTRIBUTE_CARRIER_METHOD + ")" +
			" values (" +
			customerCarrierAddRequestBean.getCustomerRequestId() + "," +
			customerCarrierAddRequestBean.getCustomerId() + "," +
			SqlHandler.delimitString(customerCarrierAddRequestBean.getBillToCompanyId()) + "," +
			SqlHandler.delimitString(customerCarrierAddRequestBean.getCarrierName()) + "," +
			SqlHandler.delimitString(customerCarrierAddRequestBean.getCarrierAccount()) + "," +
			SqlHandler.delimitString(customerCarrierAddRequestBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(customerCarrierAddRequestBean.getTransportationMode()) + "," +
			SqlHandler.delimitString(customerCarrierAddRequestBean.getNotes()) + "," +
			SqlHandler.delimitString(customerCarrierAddRequestBean.getCarrierMethod()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(CustomerCarrierAddRequestBean customerCarrierAddRequestBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(customerCarrierAddRequestBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(CustomerCarrierAddRequestBean customerCarrierAddRequestBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_CUSTOMER_REQUEST_ID + "=" + 
				StringHandler.nullIfZero(customerCarrierAddRequestBean.getCustomerRequestId()) + "," +
			ATTRIBUTE_CUSTOMER_ID + "=" + 
				StringHandler.nullIfZero(customerCarrierAddRequestBean.getCustomerId()) + "," +
			ATTRIBUTE_BILL_TO_COMPANY_ID + "=" + 
				SqlHandler.delimitString(customerCarrierAddRequestBean.getBillToCompanyId()) + "," +
			ATTRIBUTE_CARRIER_NAME + "=" + 
				SqlHandler.delimitString(customerCarrierAddRequestBean.getCarrierName()) + "," +
			ATTRIBUTE_CARRIER_ACCOUNT + "=" + 
				SqlHandler.delimitString(customerCarrierAddRequestBean.getCarrierAccount()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(customerCarrierAddRequestBean.getInventoryGroup()) + "," +
			ATTRIBUTE_TRANSPORTATION_MODE + "=" + 
				SqlHandler.delimitString(customerCarrierAddRequestBean.getTransportationMode()) + "," +
			ATTRIBUTE_NOTES + "=" + 
				SqlHandler.delimitString(customerCarrierAddRequestBean.getNotes()) + "," +
			ATTRIBUTE_CARRIER_METHOD + "=" + 
				SqlHandler.delimitString(customerCarrierAddRequestBean.getCarrierMethod()) + " " +
			"where " + ATTRIBUTE_CUSTOMER_REQUEST_ID + "=" +
				customerCarrierAddRequestBean.getCustomerRequestId();

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

		Collection customerCarrierAddRequestBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CustomerCarrierAddRequestBean customerCarrierAddRequestBean = new CustomerCarrierAddRequestBean();
			load(dataSetRow, customerCarrierAddRequestBean);
			customerCarrierAddRequestBeanColl.add(customerCarrierAddRequestBean);
		}

		return customerCarrierAddRequestBeanColl;
	}
}