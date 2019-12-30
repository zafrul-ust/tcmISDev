package com.tcmis.client.kilfrost.factory;


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
import com.tcmis.client.kilfrost.beans.KilfrostDeliveryConfirmationBean;


/******************************************************************************
 * CLASSNAME: KilfrostDeliveryConfirmationBeanFactory <br>
 * @version: 1.0, Sep 25, 2008 <br>
 *****************************************************************************/


public class KilfrostDeliveryConfirmationBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_KILFROST_ORDER_NUMBER = "KILFROST_ORDER_NUMBER";
	public String ATTRIBUTE_CARRIER = "CARRIER";
	public String ATTRIBUTE_TRACKING_NUMBER = "TRACKING_NUMBER";
	public String ATTRIBUTE_DATE_SHIPPED = "DATE_SHIPPED";
	public String ATTRIBUTE_QUANTITY_SHIPPED = "QUANTITY_SHIPPED";
	public String ATTRIBUTE_BATCH_NUMBER = "BATCH_NUMBER";
	public String ATTRIBUTE_TRANSACTION_NUMBER = "TRANSACTION_NUMBER";
	public String ATTRIBUTE_FREIGHT_COST = "FREIGHT_COST";
	public String ATTRIBUTE_FREIGHT_COST_CURRENCY_ID = "FREIGHT_COST_CURRENCY_ID";
	public String ATTRIBUTE_ORDER_STATUS = "ORDER_STATUS";

	//table name
	public String TABLE = "KILFROST_DELIVERY_CONFIRMATION";


	//constructor
	public KilfrostDeliveryConfirmationBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("kilfrostOrderNumber")) {
			return ATTRIBUTE_KILFROST_ORDER_NUMBER;
		}
		else if(attributeName.equals("carrier")) {
			return ATTRIBUTE_CARRIER;
		}
		else if(attributeName.equals("trackingNumber")) {
			return ATTRIBUTE_TRACKING_NUMBER;
		}
		else if(attributeName.equals("dateShipped")) {
			return ATTRIBUTE_DATE_SHIPPED;
		}
		else if(attributeName.equals("quantityShipped")) {
			return ATTRIBUTE_QUANTITY_SHIPPED;
		}
		else if(attributeName.equals("batchNumber")) {
			return ATTRIBUTE_BATCH_NUMBER;
		}
		else if(attributeName.equals("transactionNumber")) {
			return ATTRIBUTE_TRANSACTION_NUMBER;
		}
		else if(attributeName.equals("freightCost")) {
			return ATTRIBUTE_FREIGHT_COST;
		}
		else if(attributeName.equals("freightCostCurrencyId")) {
			return ATTRIBUTE_FREIGHT_COST_CURRENCY_ID;
		}
		else if(attributeName.equals("orderStatus")) {
			return ATTRIBUTE_ORDER_STATUS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, KilfrostDeliveryConfirmationBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(KilfrostDeliveryConfirmationBean kilfrostDeliveryConfirmationBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + kilfrostDeliveryConfirmationBean.getCompanyId());

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


	public int delete(KilfrostDeliveryConfirmationBean kilfrostDeliveryConfirmationBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + kilfrostDeliveryConfirmationBean.getCompanyId());

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
/*
	//insert
	public int insert(KilfrostDeliveryConfirmationBean kilfrostDeliveryConfirmationBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(kilfrostDeliveryConfirmationBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(KilfrostDeliveryConfirmationBean kilfrostDeliveryConfirmationBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_KILFROST_ORDER_NUMBER + "," +
			ATTRIBUTE_CARRIER + "," +
			ATTRIBUTE_TRACKING_NUMBER + "," +
			ATTRIBUTE_DATE_SHIPPED + "," +
			ATTRIBUTE_QUANTITY_SHIPPED + "," +
			ATTRIBUTE_BATCH_NUMBER + "," +
			ATTRIBUTE_TRANSACTION_NUMBER + "," +
			ATTRIBUTE_FREIGHT_COST + "," +
			ATTRIBUTE_FREIGHT_COST_CURRENCY_ID + ")" +
			" values (" +
			SqlHandler.delimitString(kilfrostDeliveryConfirmationBean.getCompanyId()) + "," +
			SqlHandler.delimitString(kilfrostDeliveryConfirmationBean.getKilfrostOrderNumber()) + "," +
			SqlHandler.delimitString(kilfrostDeliveryConfirmationBean.getCarrier()) + "," +
			SqlHandler.delimitString(kilfrostDeliveryConfirmationBean.getTrackingNumber()) + "," +
			DateHandler.getOracleToDateFunction(kilfrostDeliveryConfirmationBean.getDateShipped()) + "," +
			kilfrostDeliveryConfirmationBean.getQuantityShipped() + "," +
			SqlHandler.delimitString(kilfrostDeliveryConfirmationBean.getBatchNumber()) + "," +
			kilfrostDeliveryConfirmationBean.getTransactionNumber() + "," +
			kilfrostDeliveryConfirmationBean.getFreightCost() + "," +
			SqlHandler.delimitString(kilfrostDeliveryConfirmationBean.getFreightCostCurrencyId()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(KilfrostDeliveryConfirmationBean kilfrostDeliveryConfirmationBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(kilfrostDeliveryConfirmationBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(KilfrostDeliveryConfirmationBean kilfrostDeliveryConfirmationBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(kilfrostDeliveryConfirmationBean.getCompanyId()) + "," +
			ATTRIBUTE_KILFROST_ORDER_NUMBER + "=" + 
				SqlHandler.delimitString(kilfrostDeliveryConfirmationBean.getKilfrostOrderNumber()) + "," +
			ATTRIBUTE_CARRIER + "=" + 
				SqlHandler.delimitString(kilfrostDeliveryConfirmationBean.getCarrier()) + "," +
			ATTRIBUTE_TRACKING_NUMBER + "=" + 
				SqlHandler.delimitString(kilfrostDeliveryConfirmationBean.getTrackingNumber()) + "," +
			ATTRIBUTE_DATE_SHIPPED + "=" + 
				DateHandler.getOracleToDateFunction(kilfrostDeliveryConfirmationBean.getDateShipped()) + "," +
			ATTRIBUTE_QUANTITY_SHIPPED + "=" + 
				StringHandler.nullIfZero(kilfrostDeliveryConfirmationBean.getQuantityShipped()) + "," +
			ATTRIBUTE_BATCH_NUMBER + "=" + 
				SqlHandler.delimitString(kilfrostDeliveryConfirmationBean.getBatchNumber()) + "," +
			ATTRIBUTE_TRANSACTION_NUMBER + "=" + 
				StringHandler.nullIfZero(kilfrostDeliveryConfirmationBean.getTransactionNumber()) + "," +
			ATTRIBUTE_FREIGHT_COST + "=" + 
				StringHandler.nullIfZero(kilfrostDeliveryConfirmationBean.getFreightCost()) + "," +
			ATTRIBUTE_FREIGHT_COST_CURRENCY_ID + "=" + 
				SqlHandler.delimitString(kilfrostDeliveryConfirmationBean.getFreightCostCurrencyId()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				kilfrostDeliveryConfirmationBean.getCompanyId();

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria, sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection kilfrostDeliveryConfirmationBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			KilfrostDeliveryConfirmationBean kilfrostDeliveryConfirmationBean = new KilfrostDeliveryConfirmationBean();
			load(dataSetRow, kilfrostDeliveryConfirmationBean);
			kilfrostDeliveryConfirmationBeanColl.add(kilfrostDeliveryConfirmationBean);
		}

		return kilfrostDeliveryConfirmationBeanColl;
	}
}