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
import com.tcmis.internal.invoice.beans.InvoiceFormatViewBdPrepBean;


/******************************************************************************
 * CLASSNAME: InvoiceFormatViewBdPrepBeanFactory <br>
 * @version: 1.0, Mar 24, 2005 <br>
 *****************************************************************************/


public class InvoiceFormatViewBdPrepBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_INVOICE_GROUP = "INVOICE_GROUP";
	public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
	public String ATTRIBUTE_INVOICE = "INVOICE";
	public String ATTRIBUTE_DESCRIPTION = "DESCRIPTION";
	public String ATTRIBUTE_CHEMICAL_AMOUNT = "CHEMICAL_AMOUNT";
	public String ATTRIBUTE_PERCENT_OF_TOTAL = "PERCENT_OF_TOTAL";
	public String ATTRIBUTE_ODC = "ODC";
	public String ATTRIBUTE_NONCHEM = "NONCHEM";
	public String ATTRIBUTE_ODC_NONCHEM = "ODC_NONCHEM";
	public String ATTRIBUTE_LABOR = "LABOR";
	public String ATTRIBUTE_TOTAL = "TOTAL";
	public String ATTRIBUTE_JOURNAL_ENTRY = "JOURNAL_ENTRY";
	public String ATTRIBUTE_DEPARTMENT = "DEPARTMENT";
	public String ATTRIBUTE_CCN = "CCN";
	public String ATTRIBUTE_JOURNAL_ENTRY2 = "JOURNAL_ENTRY2";
	public String ATTRIBUTE_DEPARTMENT2 = "DEPARTMENT2";
	public String ATTRIBUTE_CCN2 = "CCN2";
	public String ATTRIBUTE_TOTAL_JOURNAL_ENTRIES = "TOTAL_JOURNAL_ENTRIES";

	//table name
	public String TABLE = "INVOICE_FORMAT_VIEW_BD_PREP";


	//constructor
	public InvoiceFormatViewBdPrepBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("invoiceGroup")) {
			return ATTRIBUTE_INVOICE_GROUP;
		}
		else if(attributeName.equals("invoicePeriod")) {
			return ATTRIBUTE_INVOICE_PERIOD;
		}
		else if(attributeName.equals("invoice")) {
			return ATTRIBUTE_INVOICE;
		}
		else if(attributeName.equals("description")) {
			return ATTRIBUTE_DESCRIPTION;
		}
		else if(attributeName.equals("chemicalAmount")) {
			return ATTRIBUTE_CHEMICAL_AMOUNT;
		}
		else if(attributeName.equals("percentOfTotal")) {
			return ATTRIBUTE_PERCENT_OF_TOTAL;
		}
		else if(attributeName.equals("odc")) {
			return ATTRIBUTE_ODC;
		}
		else if(attributeName.equals("nonchem")) {
			return ATTRIBUTE_NONCHEM;
		}
		else if(attributeName.equals("odcNonchem")) {
			return ATTRIBUTE_ODC_NONCHEM;
		}
		else if(attributeName.equals("labor")) {
			return ATTRIBUTE_LABOR;
		}
		else if(attributeName.equals("total")) {
			return ATTRIBUTE_TOTAL;
		}
		else if(attributeName.equals("journalEntry")) {
			return ATTRIBUTE_JOURNAL_ENTRY;
		}
		else if(attributeName.equals("department")) {
			return ATTRIBUTE_DEPARTMENT;
		}
		else if(attributeName.equals("ccn")) {
			return ATTRIBUTE_CCN;
		}
		else if(attributeName.equals("journalEntry2")) {
			return ATTRIBUTE_JOURNAL_ENTRY2;
		}
		else if(attributeName.equals("department2")) {
			return ATTRIBUTE_DEPARTMENT2;
		}
		else if(attributeName.equals("ccn2")) {
			return ATTRIBUTE_CCN2;
		}
		else if(attributeName.equals("totalJournalEntries")) {
			return ATTRIBUTE_TOTAL_JOURNAL_ENTRIES;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, InvoiceFormatViewBdPrepBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(InvoiceFormatViewBdPrepBean invoiceFormatViewBdPrepBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + invoiceFormatViewBdPrepBean.getCompanyId());

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


	public int delete(InvoiceFormatViewBdPrepBean invoiceFormatViewBdPrepBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + invoiceFormatViewBdPrepBean.getCompanyId());

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
	public int insert(InvoiceFormatViewBdPrepBean invoiceFormatViewBdPrepBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(invoiceFormatViewBdPrepBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(InvoiceFormatViewBdPrepBean invoiceFormatViewBdPrepBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_INVOICE_GROUP + "," +
			ATTRIBUTE_INVOICE_PERIOD + "," +
			ATTRIBUTE_INVOICE + "," +
			ATTRIBUTE_DESCRIPTION + "," +
			ATTRIBUTE_CHEMICAL_AMOUNT + "," +
			ATTRIBUTE_PERCENT_OF_TOTAL + "," +
			ATTRIBUTE_ODC + "," +
			ATTRIBUTE_NONCHEM + "," +
			ATTRIBUTE_ODC_NONCHEM + "," +
			ATTRIBUTE_LABOR + "," +
			ATTRIBUTE_TOTAL + "," +
			ATTRIBUTE_JOURNAL_ENTRY + "," +
			ATTRIBUTE_DEPARTMENT + "," +
			ATTRIBUTE_CCN + "," +
			ATTRIBUTE_JOURNAL_ENTRY2 + "," +
			ATTRIBUTE_DEPARTMENT2 + "," +
			ATTRIBUTE_CCN2 + "," +
			ATTRIBUTE_TOTAL_JOURNAL_ENTRIES + ")" +
 values (
			SqlHandler.delimitString(invoiceFormatViewBdPrepBean.getCompanyId()) + "," +
			SqlHandler.delimitString(invoiceFormatViewBdPrepBean.getInvoiceGroup()) + "," +
			StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getInvoicePeriod()) + "," +
			StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getInvoice()) + "," +
			SqlHandler.delimitString(invoiceFormatViewBdPrepBean.getDescription()) + "," +
			StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getChemicalAmount()) + "," +
			StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getPercentOfTotal()) + "," +
			StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getOdc()) + "," +
			StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getNonchem()) + "," +
			StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getOdcNonchem()) + "," +
			StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getLabor()) + "," +
			StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getTotal()) + "," +
			StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getJournalEntry()) + "," +
			SqlHandler.delimitString(invoiceFormatViewBdPrepBean.getDepartment()) + "," +
			SqlHandler.delimitString(invoiceFormatViewBdPrepBean.getCcn()) + "," +
			StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getJournalEntry2()) + "," +
			SqlHandler.delimitString(invoiceFormatViewBdPrepBean.getDepartment2()) + "," +
			SqlHandler.delimitString(invoiceFormatViewBdPrepBean.getCcn2()) + "," +
			StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getTotalJournalEntries()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(InvoiceFormatViewBdPrepBean invoiceFormatViewBdPrepBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(invoiceFormatViewBdPrepBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(InvoiceFormatViewBdPrepBean invoiceFormatViewBdPrepBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(invoiceFormatViewBdPrepBean.getCompanyId()) + "," +
			ATTRIBUTE_INVOICE_GROUP + "=" + 
				SqlHandler.delimitString(invoiceFormatViewBdPrepBean.getInvoiceGroup()) + "," +
			ATTRIBUTE_INVOICE_PERIOD + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getInvoicePeriod()) + "," +
			ATTRIBUTE_INVOICE + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getInvoice()) + "," +
			ATTRIBUTE_DESCRIPTION + "=" + 
				SqlHandler.delimitString(invoiceFormatViewBdPrepBean.getDescription()) + "," +
			ATTRIBUTE_CHEMICAL_AMOUNT + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getChemicalAmount()) + "," +
			ATTRIBUTE_PERCENT_OF_TOTAL + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getPercentOfTotal()) + "," +
			ATTRIBUTE_ODC + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getOdc()) + "," +
			ATTRIBUTE_NONCHEM + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getNonchem()) + "," +
			ATTRIBUTE_ODC_NONCHEM + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getOdcNonchem()) + "," +
			ATTRIBUTE_LABOR + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getLabor()) + "," +
			ATTRIBUTE_TOTAL + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getTotal()) + "," +
			ATTRIBUTE_JOURNAL_ENTRY + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getJournalEntry()) + "," +
			ATTRIBUTE_DEPARTMENT + "=" + 
				SqlHandler.delimitString(invoiceFormatViewBdPrepBean.getDepartment()) + "," +
			ATTRIBUTE_CCN + "=" + 
				SqlHandler.delimitString(invoiceFormatViewBdPrepBean.getCcn()) + "," +
			ATTRIBUTE_JOURNAL_ENTRY2 + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getJournalEntry2()) + "," +
			ATTRIBUTE_DEPARTMENT2 + "=" + 
				SqlHandler.delimitString(invoiceFormatViewBdPrepBean.getDepartment2()) + "," +
			ATTRIBUTE_CCN2 + "=" + 
				SqlHandler.delimitString(invoiceFormatViewBdPrepBean.getCcn2()) + "," +
			ATTRIBUTE_TOTAL_JOURNAL_ENTRIES + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getTotalJournalEntries()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				StringHandler.nullIfZero(invoiceFormatViewBdPrepBean.getCompanyId());

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

		Collection invoiceFormatViewBdPrepBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			InvoiceFormatViewBdPrepBean invoiceFormatViewBdPrepBean = new InvoiceFormatViewBdPrepBean();
			load(dataSetRow, invoiceFormatViewBdPrepBean);
			invoiceFormatViewBdPrepBeanColl.add(invoiceFormatViewBdPrepBean);
		}

		return invoiceFormatViewBdPrepBeanColl;
	}
}