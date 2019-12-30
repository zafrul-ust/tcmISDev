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
import com.tcmis.internal.distribution.beans.CustEntPriceGpAddRequestBean;


/******************************************************************************
 * CLASSNAME: CustEntPriceGpAddRequestBeanFactory <br>
 * @version: 1.0, Jul 12, 2010 <br>
 *****************************************************************************/


public class CustEntPriceGpAddRequestBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_CUSTOMER_REQUEST_ID = "CUSTOMER_REQUEST_ID";
	public String ATTRIBUTE_CUSTOMER_ID = "CUSTOMER_ID";
	public String ATTRIBUTE_BILL_TO_COMPANY_ID = "BILL_TO_COMPANY_ID";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_OPERATING_COMPANY_ID = "OPERATING_COMPANY_ID";
	public String ATTRIBUTE_PRICE_GROUP_ID = "PRICE_GROUP_ID";

	//table name
	public String TABLE = "CUST_ENT_PRICE_GP_ADD_REQUEST";


	//constructor
	public CustEntPriceGpAddRequestBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("operatingCompanyId")) {
			return ATTRIBUTE_OPERATING_COMPANY_ID;
		}
		else if(attributeName.equals("priceGroupId")) {
			return ATTRIBUTE_PRICE_GROUP_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CustEntPriceGpAddRequestBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(CustEntPriceGpAddRequestBean custEntPriceGpAddRequestBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("customerReqId", "SearchCriterion.EQUALS",
			"" + custEntPriceGpAddRequestBean.getCustomerReqId());

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


	public int delete(CustEntPriceGpAddRequestBean custEntPriceGpAddRequestBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("customerReqId", "SearchCriterion.EQUALS",
			"" + custEntPriceGpAddRequestBean.getCustomerReqId());

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
	public int insert(CustEntPriceGpAddRequestBean custEntPriceGpAddRequestBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(custEntPriceGpAddRequestBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(CustEntPriceGpAddRequestBean custEntPriceGpAddRequestBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_CUSTOMER_REQUEST_ID + "," +
			ATTRIBUTE_CUSTOMER_ID + "," +
			ATTRIBUTE_BILL_TO_COMPANY_ID + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "," +
			ATTRIBUTE_OPERATING_COMPANY_ID + "," +
			ATTRIBUTE_PRICE_GROUP_ID + ")" +
			" values (" +
			custEntPriceGpAddRequestBean.getCustomerRequestId() + "," +
			custEntPriceGpAddRequestBean.getCustomerId() + "," +
			SqlHandler.delimitString(custEntPriceGpAddRequestBean.getBillToCompanyId()) + "," +
			SqlHandler.delimitString(custEntPriceGpAddRequestBean.getOpsEntityId()) + "," +
			SqlHandler.delimitString(custEntPriceGpAddRequestBean.getOperatingCompanyId()) + "," +
			SqlHandler.delimitString(custEntPriceGpAddRequestBean.getPriceGroupId()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(CustEntPriceGpAddRequestBean custEntPriceGpAddRequestBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(custEntPriceGpAddRequestBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(CustEntPriceGpAddRequestBean custEntPriceGpAddRequestBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_CUSTOMER_REQUEST_ID + "=" + 
				StringHandler.nullIfZero(custEntPriceGpAddRequestBean.getCustomerRequestId()) + "," +
			ATTRIBUTE_CUSTOMER_ID + "=" + 
				StringHandler.nullIfZero(custEntPriceGpAddRequestBean.getCustomerId()) + "," +
			ATTRIBUTE_BILL_TO_COMPANY_ID + "=" + 
				SqlHandler.delimitString(custEntPriceGpAddRequestBean.getBillToCompanyId()) + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "=" + 
				SqlHandler.delimitString(custEntPriceGpAddRequestBean.getOpsEntityId()) + "," +
			ATTRIBUTE_OPERATING_COMPANY_ID + "=" + 
				SqlHandler.delimitString(custEntPriceGpAddRequestBean.getOperatingCompanyId()) + "," +
			ATTRIBUTE_PRICE_GROUP_ID + "=" + 
				SqlHandler.delimitString(custEntPriceGpAddRequestBean.getPriceGroupId()) + " " +
			"where " + ATTRIBUTE_CUSTOMER_REQUEST_ID + "=" +
				custEntPriceGpAddRequestBean.getCustomerRequestId();

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

		Collection custEntPriceGpAddRequestBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CustEntPriceGpAddRequestBean custEntPriceGpAddRequestBean = new CustEntPriceGpAddRequestBean();
			load(dataSetRow, custEntPriceGpAddRequestBean);
			custEntPriceGpAddRequestBeanColl.add(custEntPriceGpAddRequestBean);
		}

		return custEntPriceGpAddRequestBeanColl;
	}
}