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
import com.tcmis.internal.accountspayable.beans.VoucherLineViewBean;


/******************************************************************************
 * CLASSNAME: VoucherLineViewBeanFactory <br>
 * @version: 1.0, Mar 10, 2006 <br>
 *****************************************************************************/


public class VoucherLineViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_VOUCHER_ID = "VOUCHER_ID";
	public String ATTRIBUTE_VOUCHER_LINE = "VOUCHER_LINE";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_QUANTITY_INVOICED = "QUANTITY_INVOICED";
	public String ATTRIBUTE_INVOICE_UNIT_PRICE = "INVOICE_UNIT_PRICE";
	public String ATTRIBUTE_TRANSACTION_DATE = "TRANSACTION_DATE";
	public String ATTRIBUTE_REC_QUANTITY_MATCH_DATE = "REC_QUANTITY_MATCH_DATE";
	public String ATTRIBUTE_REC_UNIT_PRICE_MATCH_DATE = "REC_UNIT_PRICE_MATCH_DATE";

	//table name
	public String TABLE = "VOUCHER_LINE_VIEW";


	//constructor
	public VoucherLineViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("voucherId")) {
			return ATTRIBUTE_VOUCHER_ID;
		}
		else if(attributeName.equals("voucherLine")) {
			return ATTRIBUTE_VOUCHER_LINE;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("quantityInvoiced")) {
			return ATTRIBUTE_QUANTITY_INVOICED;
		}
		else if(attributeName.equals("invoiceUnitPrice")) {
			return ATTRIBUTE_INVOICE_UNIT_PRICE;
		}
		else if(attributeName.equals("transactionDate")) {
			return ATTRIBUTE_TRANSACTION_DATE;
		}
		else if(attributeName.equals("recQuantityMatchDate")) {
			return ATTRIBUTE_REC_QUANTITY_MATCH_DATE;
		}
		else if(attributeName.equals("recUnitPriceMatchDate")) {
			return ATTRIBUTE_REC_UNIT_PRICE_MATCH_DATE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, VoucherLineViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(VoucherLineViewBean voucherLineViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("voucherId", "SearchCriterion.EQUALS",
			"" + voucherLineViewBean.getVoucherId());

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


	public int delete(VoucherLineViewBean voucherLineViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("voucherId", "SearchCriterion.EQUALS",
			"" + voucherLineViewBean.getVoucherId());

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
	public int insert(VoucherLineViewBean voucherLineViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(voucherLineViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(VoucherLineViewBean voucherLineViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_VOUCHER_ID + "," +
			ATTRIBUTE_VOUCHER_LINE + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_ITEM_DESC + "," +
			ATTRIBUTE_QUANTITY_INVOICED + "," +
			ATTRIBUTE_INVOICE_UNIT_PRICE + "," +
			ATTRIBUTE_TRANSACTION_DATE + "," +
			ATTRIBUTE_REC_QUANTITY_MATCH_DATE + "," +
			ATTRIBUTE_REC_UNIT_PRICE_MATCH_DATE + ")" +
			" values (" +
			voucherLineViewBean.getVoucherId() + "," +
			voucherLineViewBean.getVoucherLine() + "," +
			voucherLineViewBean.getItemId() + "," +
			SqlHandler.delimitString(voucherLineViewBean.getItemDesc()) + "," +
			voucherLineViewBean.getQuantityInvoiced() + "," +
			voucherLineViewBean.getInvoiceUnitPrice() + "," +
			DateHandler.getOracleToDateFunction(voucherLineViewBean.getTransactionDate()) + "," +
			DateHandler.getOracleToDateFunction(voucherLineViewBean.getRecQuantityMatchDate()) + "," +
			DateHandler.getOracleToDateFunction(voucherLineViewBean.getRecUnitPriceMatchDate()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(VoucherLineViewBean voucherLineViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(voucherLineViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(VoucherLineViewBean voucherLineViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_VOUCHER_ID + "=" + 
				StringHandler.nullIfZero(voucherLineViewBean.getVoucherId()) + "," +
			ATTRIBUTE_VOUCHER_LINE + "=" + 
				StringHandler.nullIfZero(voucherLineViewBean.getVoucherLine()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(voucherLineViewBean.getItemId()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" + 
				SqlHandler.delimitString(voucherLineViewBean.getItemDesc()) + "," +
			ATTRIBUTE_QUANTITY_INVOICED + "=" + 
				StringHandler.nullIfZero(voucherLineViewBean.getQuantityInvoiced()) + "," +
			ATTRIBUTE_INVOICE_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(voucherLineViewBean.getInvoiceUnitPrice()) + "," +
			ATTRIBUTE_TRANSACTION_DATE + "=" + 
				DateHandler.getOracleToDateFunction(voucherLineViewBean.getTransactionDate()) + "," +
			ATTRIBUTE_REC_QUANTITY_MATCH_DATE + "=" + 
				DateHandler.getOracleToDateFunction(voucherLineViewBean.getRecQuantityMatchDate()) + "," +
			ATTRIBUTE_REC_UNIT_PRICE_MATCH_DATE + "=" + 
				DateHandler.getOracleToDateFunction(voucherLineViewBean.getRecUnitPriceMatchDate()) + " " +
			"where " + ATTRIBUTE_VOUCHER_ID + "=" +
				voucherLineViewBean.getVoucherId();

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, Connection conn)
		throws BaseException {

		Collection voucherLineViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			VoucherLineViewBean voucherLineViewBean = new VoucherLineViewBean();
			load(dataSetRow, voucherLineViewBean);
			voucherLineViewBeanColl.add(voucherLineViewBean);
		}

		return voucherLineViewBeanColl;
	}
}