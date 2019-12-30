package com.tcmis.internal.accountspayable.factory;


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
import com.tcmis.internal.accountspayable.beans.UnfinishedInvoiceViewBean;


/******************************************************************************
 * CLASSNAME: UnfinishedInvoiceViewBeanFactory <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class UnfinishedInvoiceViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_VOUCHER_ID = "VOUCHER_ID";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_SUPPLIER_INVOICE_ID = "SUPPLIER_INVOICE_ID";
	public String ATTRIBUTE_VOUCHER_STATUS = "VOUCHER_STATUS";
	public String ATTRIBUTE_REMAINING_PRICE = "REMAINING_PRICE";

	//table name
	public String TABLE = "UNFINISHED_INVOICE_VIEW";


	//constructor
	public UnfinishedInvoiceViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("voucherId")) {
			return ATTRIBUTE_VOUCHER_ID;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("supplierInvoiceId")) {
			return ATTRIBUTE_SUPPLIER_INVOICE_ID;
		}
		else if(attributeName.equals("voucherStatus")) {
			return ATTRIBUTE_VOUCHER_STATUS;
		}
		else if(attributeName.equals("remainingPrice")) {
			return ATTRIBUTE_REMAINING_PRICE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, UnfinishedInvoiceViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(UnfinishedInvoiceViewBean unfinishedInvoiceViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("voucherId", "SearchCriterion.EQUALS",
			"" + unfinishedInvoiceViewBean.getVoucherId());

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


	public int delete(UnfinishedInvoiceViewBean unfinishedInvoiceViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("voucherId", "SearchCriterion.EQUALS",
			"" + unfinishedInvoiceViewBean.getVoucherId());

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
	public int insert(UnfinishedInvoiceViewBean unfinishedInvoiceViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(unfinishedInvoiceViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(UnfinishedInvoiceViewBean unfinishedInvoiceViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_VOUCHER_ID + "," +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_SUPPLIER_INVOICE_ID + "," +
			ATTRIBUTE_VOUCHER_STATUS + "," +
			ATTRIBUTE_REMAINING_PRICE + ")" +
			" values (" +
			unfinishedInvoiceViewBean.getVoucherId() + "," +
			unfinishedInvoiceViewBean.getRadianPo() + "," +
			SqlHandler.delimitString(unfinishedInvoiceViewBean.getSupplierInvoiceId()) + "," +
			SqlHandler.delimitString(unfinishedInvoiceViewBean.getVoucherStatus()) + "," +
			unfinishedInvoiceViewBean.getRemainingPrice() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(UnfinishedInvoiceViewBean unfinishedInvoiceViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(unfinishedInvoiceViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(UnfinishedInvoiceViewBean unfinishedInvoiceViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_VOUCHER_ID + "=" + 
				StringHandler.nullIfZero(unfinishedInvoiceViewBean.getVoucherId()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(unfinishedInvoiceViewBean.getRadianPo()) + "," +
			ATTRIBUTE_SUPPLIER_INVOICE_ID + "=" + 
				SqlHandler.delimitString(unfinishedInvoiceViewBean.getSupplierInvoiceId()) + "," +
			ATTRIBUTE_VOUCHER_STATUS + "=" + 
				SqlHandler.delimitString(unfinishedInvoiceViewBean.getVoucherStatus()) + "," +
			ATTRIBUTE_REMAINING_PRICE + "=" + 
				StringHandler.nullIfZero(unfinishedInvoiceViewBean.getRemainingPrice()) + " " +
			"where " + ATTRIBUTE_VOUCHER_ID + "=" +
				unfinishedInvoiceViewBean.getVoucherId();

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

		Collection unfinishedInvoiceViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			UnfinishedInvoiceViewBean unfinishedInvoiceViewBean = new UnfinishedInvoiceViewBean();
			load(dataSetRow, unfinishedInvoiceViewBean);
			unfinishedInvoiceViewBeanColl.add(unfinishedInvoiceViewBean);
		}

		return unfinishedInvoiceViewBeanColl;
	}
}