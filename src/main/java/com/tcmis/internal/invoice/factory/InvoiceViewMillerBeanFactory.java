package com.tcmis.internal.invoice.factory;


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
import com.tcmis.internal.invoice.beans.InvoiceViewMillerBean;


/******************************************************************************
 * CLASSNAME: InvoiceViewMillerBeanFactory <br>
 * @version: 1.0, May 26, 2005 <br>
 *****************************************************************************/


public class InvoiceViewMillerBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_INVOICE = "INVOICE";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";

	//table name
	public String TABLE = "INVOICE_VIEW_MILLER";


	//constructor
	public InvoiceViewMillerBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("invoice")) {
			return ATTRIBUTE_INVOICE;
		}
		else if(attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if(attributeName.equals("invoiceDate")) {
			return ATTRIBUTE_INVOICE_DATE;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("invoicePeriod")) {
			return ATTRIBUTE_INVOICE_PERIOD;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, InvoiceViewMillerBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(InvoiceViewMillerBean invoiceViewMillerBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
			"" + invoiceViewMillerBean.getInvoice());

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


	public int delete(InvoiceViewMillerBean invoiceViewMillerBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
			"" + invoiceViewMillerBean.getInvoice());

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
	public int insert(InvoiceViewMillerBean invoiceViewMillerBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(invoiceViewMillerBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(InvoiceViewMillerBean invoiceViewMillerBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_INVOICE + "," +
			ATTRIBUTE_PO_NUMBER + "," +
			ATTRIBUTE_INVOICE_DATE + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_INVOICE_PERIOD + ")" +
			" values (" +
			invoiceViewMillerBean.getInvoice() + "," +
			SqlHandler.delimitString(invoiceViewMillerBean.getPoNumber()) + "," +
			DateHandler.getOracleToDateFunction(invoiceViewMillerBean.getInvoiceDate()) + "," +
			SqlHandler.delimitString(invoiceViewMillerBean.getFacilityId()) + "," +
			invoiceViewMillerBean.getInvoicePeriod() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(InvoiceViewMillerBean invoiceViewMillerBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(invoiceViewMillerBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(InvoiceViewMillerBean invoiceViewMillerBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_INVOICE + "=" + 
				StringHandler.nullIfZero(invoiceViewMillerBean.getInvoice()) + "," +
			ATTRIBUTE_PO_NUMBER + "=" + 
				SqlHandler.delimitString(invoiceViewMillerBean.getPoNumber()) + "," +
			ATTRIBUTE_INVOICE_DATE + "=" + 
				DateHandler.getOracleToDateFunction(invoiceViewMillerBean.getInvoiceDate()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" + 
				SqlHandler.delimitString(invoiceViewMillerBean.getFacilityId()) + "," +
			ATTRIBUTE_INVOICE_PERIOD + "=" + 
				StringHandler.nullIfZero(invoiceViewMillerBean.getInvoicePeriod()) + " " +
			"where " + ATTRIBUTE_INVOICE + "=" +
				invoiceViewMillerBean.getInvoice();

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

		Collection invoiceViewMillerBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			InvoiceViewMillerBean invoiceViewMillerBean = new InvoiceViewMillerBean();
			load(dataSetRow, invoiceViewMillerBean);
			invoiceViewMillerBeanColl.add(invoiceViewMillerBean);
		}

		return invoiceViewMillerBeanColl;
	}
}