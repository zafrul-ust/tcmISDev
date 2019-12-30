package com.tcmis.internal.invoice.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.math.BigDecimal;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.internal.invoice.beans.InvoiceFormatPolchemViewBean;


/******************************************************************************
 * CLASSNAME: InvoiceFormatPolchemViewBeanFactory <br>
 * @version: 1.0, Oct 31, 2008 <br>
 *****************************************************************************/


public class InvoiceFormatPolchemViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
	public String ATTRIBUTE_INVOICE = "INVOICE";
	public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_RIC = "RIC";
	public String ATTRIBUTE_INVOICE_LINE = "INVOICE_LINE";
	public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_NSN = "NSN";
	public String ATTRIBUTE_SAIC_SKU = "SAIC_SKU";
	public String ATTRIBUTE_DATE_SHIPPED = "DATE_SHIPPED";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_INVOICE_UNIT_PRICE = "INVOICE_UNIT_PRICE";
	public String ATTRIBUTE_CATALOG_PRICE = "CATALOG_PRICE";
	public String ATTRIBUTE_NET_AMOUNT = "NET_AMOUNT";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
	public String ATTRIBUTE_SALES_TAX = "SALES_TAX";

	//table name
	public String TABLE = "INVOICE_FORMAT_POLCHEM_VIEW";


	//constructor
	public InvoiceFormatPolchemViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("invoicePeriod")) {
			return ATTRIBUTE_INVOICE_PERIOD;
		}
		else if(attributeName.equals("invoice")) {
			return ATTRIBUTE_INVOICE;
		}
		else if(attributeName.equals("invoiceDate")) {
			return ATTRIBUTE_INVOICE_DATE;
		}
		else if(attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("ric")) {
			return ATTRIBUTE_RIC;
		}
		else if(attributeName.equals("invoiceLine")) {
			return ATTRIBUTE_INVOICE_LINE;
		}
		else if(attributeName.equals("invoiceAmount")) {
			return ATTRIBUTE_INVOICE_AMOUNT;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("nsn")) {
			return ATTRIBUTE_NSN;
		}
		else if(attributeName.equals("saicSku")) {
			return ATTRIBUTE_SAIC_SKU;
		}
		else if(attributeName.equals("dateShipped")) {
			return ATTRIBUTE_DATE_SHIPPED;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("invoiceUnitPrice")) {
			return ATTRIBUTE_INVOICE_UNIT_PRICE;
		}
		else if(attributeName.equals("catalogPrice")) {
			return ATTRIBUTE_CATALOG_PRICE;
		}
		else if(attributeName.equals("netAmount")) {
			return ATTRIBUTE_NET_AMOUNT;
		}
		else if(attributeName.equals("partDescription")) {
			return ATTRIBUTE_PART_DESCRIPTION;
		}
		else if(attributeName.equals("itemType")) {
			return ATTRIBUTE_ITEM_TYPE;
		}
		else if(attributeName.equals("salesTax")) {
			return ATTRIBUTE_SALES_TAX;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, InvoiceFormatPolchemViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(InvoiceFormatPolchemViewBean invoiceFormatPolchemViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("invoicePeriod", "SearchCriterion.EQUALS",
			"" + invoiceFormatPolchemViewBean.getInvoicePeriod());

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


	public int delete(InvoiceFormatPolchemViewBean invoiceFormatPolchemViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("invoicePeriod", "SearchCriterion.EQUALS",
			"" + invoiceFormatPolchemViewBean.getInvoicePeriod());

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
	public int insert(InvoiceFormatPolchemViewBean invoiceFormatPolchemViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(invoiceFormatPolchemViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(InvoiceFormatPolchemViewBean invoiceFormatPolchemViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_INVOICE_PERIOD + "," +
			ATTRIBUTE_INVOICE + "," +
			ATTRIBUTE_INVOICE_DATE + "," +
			ATTRIBUTE_PO_NUMBER + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_RIC + "," +
			ATTRIBUTE_INVOICE_LINE + "," +
			ATTRIBUTE_INVOICE_AMOUNT + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_NSN + "," +
			ATTRIBUTE_SAIC_SKU + "," +
			ATTRIBUTE_DATE_SHIPPED + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_INVOICE_UNIT_PRICE + "," +
			ATTRIBUTE_CATALOG_PRICE + "," +
			ATTRIBUTE_NET_AMOUNT + "," +
			ATTRIBUTE_PART_DESCRIPTION + "," +
			ATTRIBUTE_ITEM_TYPE + "," +
			ATTRIBUTE_SALES_TAX + ")" +
			" values (" +
			invoiceFormatPolchemViewBean.getInvoicePeriod() + "," +
			invoiceFormatPolchemViewBean.getInvoice() + "," +
			DateHandler.getOracleToDateFunction(invoiceFormatPolchemViewBean.getInvoiceDate()) + "," +
			SqlHandler.delimitString(invoiceFormatPolchemViewBean.getPoNumber()) + "," +
			SqlHandler.delimitString(invoiceFormatPolchemViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(invoiceFormatPolchemViewBean.getRic()) + "," +
			invoiceFormatPolchemViewBean.getInvoiceLine() + "," +
			invoiceFormatPolchemViewBean.getInvoiceAmount() + "," +
			SqlHandler.delimitString(invoiceFormatPolchemViewBean.getLineItem()) + "," +
			SqlHandler.delimitString(invoiceFormatPolchemViewBean.getNsn()) + "," +
			SqlHandler.delimitString(invoiceFormatPolchemViewBean.getSaicSku()) + "," +
			DateHandler.getOracleToDateFunction(invoiceFormatPolchemViewBean.getDateShipped()) + "," +
			invoiceFormatPolchemViewBean.getQuantity() + "," +
			invoiceFormatPolchemViewBean.getInvoiceUnitPrice() + "," +
			invoiceFormatPolchemViewBean.getCatalogPrice() + "," +
			invoiceFormatPolchemViewBean.getNetAmount() + "," +
			SqlHandler.delimitString(invoiceFormatPolchemViewBean.getPartDescription()) + "," +
			SqlHandler.delimitString(invoiceFormatPolchemViewBean.getItemType()) + "," +
			invoiceFormatPolchemViewBean.getSalesTax() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(InvoiceFormatPolchemViewBean invoiceFormatPolchemViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(invoiceFormatPolchemViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(InvoiceFormatPolchemViewBean invoiceFormatPolchemViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_INVOICE_PERIOD + "=" + 
				StringHandler.nullIfZero(invoiceFormatPolchemViewBean.getInvoicePeriod()) + "," +
			ATTRIBUTE_INVOICE + "=" + 
				StringHandler.nullIfZero(invoiceFormatPolchemViewBean.getInvoice()) + "," +
			ATTRIBUTE_INVOICE_DATE + "=" + 
				DateHandler.getOracleToDateFunction(invoiceFormatPolchemViewBean.getInvoiceDate()) + "," +
			ATTRIBUTE_PO_NUMBER + "=" + 
				SqlHandler.delimitString(invoiceFormatPolchemViewBean.getPoNumber()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(invoiceFormatPolchemViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_RIC + "=" + 
				SqlHandler.delimitString(invoiceFormatPolchemViewBean.getRic()) + "," +
			ATTRIBUTE_INVOICE_LINE + "=" + 
				StringHandler.nullIfZero(invoiceFormatPolchemViewBean.getInvoiceLine()) + "," +
			ATTRIBUTE_INVOICE_AMOUNT + "=" + 
				StringHandler.nullIfZero(invoiceFormatPolchemViewBean.getInvoiceAmount()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" + 
				SqlHandler.delimitString(invoiceFormatPolchemViewBean.getLineItem()) + "," +
			ATTRIBUTE_NSN + "=" + 
				SqlHandler.delimitString(invoiceFormatPolchemViewBean.getNsn()) + "," +
			ATTRIBUTE_SAIC_SKU + "=" + 
				SqlHandler.delimitString(invoiceFormatPolchemViewBean.getSaicSku()) + "," +
			ATTRIBUTE_DATE_SHIPPED + "=" + 
				DateHandler.getOracleToDateFunction(invoiceFormatPolchemViewBean.getDateShipped()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(invoiceFormatPolchemViewBean.getQuantity()) + "," +
			ATTRIBUTE_INVOICE_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(invoiceFormatPolchemViewBean.getInvoiceUnitPrice()) + "," +
			ATTRIBUTE_CATALOG_PRICE + "=" + 
				StringHandler.nullIfZero(invoiceFormatPolchemViewBean.getCatalogPrice()) + "," +
			ATTRIBUTE_NET_AMOUNT + "=" + 
				StringHandler.nullIfZero(invoiceFormatPolchemViewBean.getNetAmount()) + "," +
			ATTRIBUTE_PART_DESCRIPTION + "=" + 
				SqlHandler.delimitString(invoiceFormatPolchemViewBean.getPartDescription()) + "," +
			ATTRIBUTE_ITEM_TYPE + "=" + 
				SqlHandler.delimitString(invoiceFormatPolchemViewBean.getItemType()) + "," +
			ATTRIBUTE_SALES_TAX + "=" + 
				StringHandler.nullIfZero(invoiceFormatPolchemViewBean.getSalesTax()) + " " +
			"where " + ATTRIBUTE_INVOICE_PERIOD + "=" +
				invoiceFormatPolchemViewBean.getInvoicePeriod();

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

		Collection invoiceFormatPolchemViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			InvoiceFormatPolchemViewBean invoiceFormatPolchemViewBean = new InvoiceFormatPolchemViewBean();
			load(dataSetRow, invoiceFormatPolchemViewBean);
			invoiceFormatPolchemViewBeanColl.add(invoiceFormatPolchemViewBean);
		}

		return invoiceFormatPolchemViewBeanColl;
	}

	public Collection selectInvoice(BigDecimal invoicePeriod)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectInvoice(invoicePeriod, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection selectInvoice(BigDecimal invoicePeriod, Connection conn)
		throws BaseException {

		Collection invoiceFormatPolchemViewBeanColl = new Vector();

//		String query = "select invoice,invoice_Date,invoice_amount,item_type,min(date_shipped) date_shipped from INVOICE_FORMAT_POLCHEM_VIEW " +
//                "where invoice_period=" + invoicePeriod + " " +
//                "group by invoice, invoice_Date,invoice_amount,item_type ";
		String query = "select invoice,invoice_Date,invoice_amount from INVOICE_FORMAT_POLCHEM_VIEW " +
        "where invoice_period=" + invoicePeriod + " " +
        "group by invoice, invoice_Date,invoice_amount ";

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			InvoiceFormatPolchemViewBean invoiceFormatPolchemViewBean = new InvoiceFormatPolchemViewBean();
			load(dataSetRow, invoiceFormatPolchemViewBean);
            String query2 = "select invoice,invoice_date, inventory_group, ric, invoice_amount,sum(net_amount) net_amount, sum(sales_tax) sales_tax " +
                            "From INVOICE_FORMAT_POLCHEM_VIEW " +
                            "where invoice=" + invoiceFormatPolchemViewBean.getInvoice() + " " +
                            "group by invoice,invoice_date, inventory_group, ric, invoice_amount order by inventory_group";
            DataSet dataSet2 = new SqlManager().select(conn, query2);
            Iterator dataIter2 = dataSet2.iterator();
            Collection invoiceFormatPolchemViewBeanColl2 = new Vector();
            while (dataIter2.hasNext()) {
                DataSetRow dataSetRow2 = (DataSetRow)dataIter2.next();
                InvoiceFormatPolchemViewBean invoiceFormatPolchemViewBean2 = new InvoiceFormatPolchemViewBean();
                load(dataSetRow2, invoiceFormatPolchemViewBean2);
                invoiceFormatPolchemViewBeanColl2.add(invoiceFormatPolchemViewBean2);
            }
            invoiceFormatPolchemViewBean.setDetailCollection(invoiceFormatPolchemViewBeanColl2);
            invoiceFormatPolchemViewBeanColl.add(invoiceFormatPolchemViewBean);
		}

		return invoiceFormatPolchemViewBeanColl;
	}
}