package com.tcmis.internal.supply.factory;


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
import com.tcmis.internal.supply.beans.SuppExcAddPaytermsViewBean;
import com.tcmis.internal.supply.beans.SuppAddPaymentTermsViewBean;


/******************************************************************************
 * CLASSNAME: SupplierAddExcPaymentTermsBeanFactory <br>
 * @version: 1.0, Oct 29, 2009 <br>
 *****************************************************************************/


public class SupplierAddExcPaymentTermsBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SUPPLIER_REQUEST_ID = "SUPPLIER_REQUEST_ID";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_PAYMENT_TERMS = "PAYMENT_TERMS";
	public String ATTRIBUTE_APPROVED_BY = "APPROVED_BY";
	public String ATTRIBUTE_APPROVED_ON = "APPROVED_ON";
	public String ATTRIBUTE_CURRENT_PAYMENT_TERMS = "CURRENT_PAYMENT_TERMS";

	//table name
	public String TABLE = "SUPPLIER_ADD_EXC_PAYMENT_TERMS";


	//constructor
	public SupplierAddExcPaymentTermsBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("supplierRequestId")) {
			return ATTRIBUTE_SUPPLIER_REQUEST_ID;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("paymentTerms")) {
			return ATTRIBUTE_PAYMENT_TERMS;
		}
		else if(attributeName.equals("approvedBy")) {
			return ATTRIBUTE_APPROVED_BY;
		}
		else if(attributeName.equals("approvedOn")) {
			return ATTRIBUTE_APPROVED_ON;
		}
		else if(attributeName.equals("currentPaymentTerms")) {
			return ATTRIBUTE_CURRENT_PAYMENT_TERMS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, SuppExcAddPaytermsViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(SupplierAddExcPaymentTermsBean supplierAddExcPaymentTermsBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplierRequestId", "SearchCriterion.EQUALS",
			"" + supplierAddExcPaymentTermsBean.getSupplierRequestId());

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


	public int delete(SupplierAddExcPaymentTermsBean supplierAddExcPaymentTermsBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplierRequestId", "SearchCriterion.EQUALS",
			"" + supplierAddExcPaymentTermsBean.getSupplierRequestId());

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
	public int insert(SuppExcAddPaytermsViewBean suppExcAddPaytermsViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(suppExcAddPaytermsViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(SuppExcAddPaytermsViewBean suppExcAddPaytermsViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_SUPPLIER_REQUEST_ID + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_PAYMENT_TERMS + "," +
			ATTRIBUTE_APPROVED_BY + "," +
			ATTRIBUTE_APPROVED_ON + "," +
			ATTRIBUTE_CURRENT_PAYMENT_TERMS + ")" +
			" values (" +
			suppExcAddPaytermsViewBean.getSupplierRequestId() + "," +
			SqlHandler.delimitString(suppExcAddPaytermsViewBean.getSupplier()) + "," +
			SqlHandler.delimitString(suppExcAddPaytermsViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(suppExcAddPaytermsViewBean.getPaymentTerms()) + "," +
			suppExcAddPaytermsViewBean.getApprovedBy() + "," +
			DateHandler.getOracleToDateFunction(suppExcAddPaytermsViewBean.getApprovedOn()) + "," +
			SqlHandler.delimitString(suppExcAddPaytermsViewBean.getCurrentPaymentTerms()) + ")";

		return sqlManager.update(conn, query);
	}

/*
	//update
	public int update(SupplierAddExcPaymentTermsBean supplierAddExcPaymentTermsBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(supplierAddExcPaymentTermsBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(SupplierAddExcPaymentTermsBean supplierAddExcPaymentTermsBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_SUPPLIER_REQUEST_ID + "=" + 
				StringHandler.nullIfZero(supplierAddExcPaymentTermsBean.getSupplierRequestId()) + "," +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(supplierAddExcPaymentTermsBean.getSupplier()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(supplierAddExcPaymentTermsBean.getInventoryGroup()) + "," +
			ATTRIBUTE_PAYMENT_TERMS + "=" + 
				SqlHandler.delimitString(supplierAddExcPaymentTermsBean.getPaymentTerms()) + "," +
			ATTRIBUTE_APPROVED_BY + "=" + 
				StringHandler.nullIfZero(supplierAddExcPaymentTermsBean.getApprovedBy()) + "," +
			ATTRIBUTE_APPROVED_ON + "=" + 
				DateHandler.getOracleToDateFunction(supplierAddExcPaymentTermsBean.getApprovedOn()) + "," +
			ATTRIBUTE_CURRENT_PAYMENT_TERMS + "=" + 
				SqlHandler.delimitString(supplierAddExcPaymentTermsBean.getCurrentPaymentTerms()) + " " +
			"where " + ATTRIBUTE_SUPPLIER_REQUEST_ID + "=" +
				supplierAddExcPaymentTermsBean.getSupplierRequestId();

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

		Collection supplierAddExcPaymentTermsBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			SuppExcAddPaytermsViewBean supplierAddExcPaymentTermsBean = new SuppExcAddPaytermsViewBean();
			load(dataSetRow, supplierAddExcPaymentTermsBean);
			supplierAddExcPaymentTermsBeanColl.add(supplierAddExcPaymentTermsBean);
		}

		return supplierAddExcPaymentTermsBeanColl;
	}
}