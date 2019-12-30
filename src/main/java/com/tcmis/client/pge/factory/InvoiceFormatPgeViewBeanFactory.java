package com.tcmis.client.pge.factory;


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
import com.tcmis.client.pge.beans.InvoiceFormatPgeViewBean;


/******************************************************************************
 * CLASSNAME: InvoiceFormatPgeViewBeanFactory <br>
 * @version: 1.0, Nov 15, 2005 <br>
 *****************************************************************************/


public class InvoiceFormatPgeViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_BILL_TO_NAME = "BILL_TO_NAME";
	public String ATTRIBUTE_BILL_TO_ADDR1 = "BILL_TO_ADDR1";
	public String ATTRIBUTE_BILL_TO_CITY = "BILL_TO_CITY";
	public String ATTRIBUTE_BILL_TO_STATE = "BILL_TO_STATE";
	public String ATTRIBUTE_BILL_TO_ZIP = "BILL_TO_ZIP";
	public String ATTRIBUTE_REMIT_TO_NAME = "REMIT_TO_NAME";
	public String ATTRIBUTE_REMIT_TO_ADDR1 = "REMIT_TO_ADDR1";
	public String ATTRIBUTE_REMIT_TO_CITY = "REMIT_TO_CITY";
	public String ATTRIBUTE_REMIT_TO_STATE = "REMIT_TO_STATE";
	public String ATTRIBUTE_REMIT_TO_ZIP = "REMIT_TO_ZIP";
	public String ATTRIBUTE_SHIP_TO_NAME = "SHIP_TO_NAME";
	public String ATTRIBUTE_SHIP_TO_ADDR1 = "SHIP_TO_ADDR1";
	public String ATTRIBUTE_SHIP_TO_ADDR2 = "SHIP_TO_ADDR2";
	public String ATTRIBUTE_SHIP_TO_CITY = "SHIP_TO_CITY";
	public String ATTRIBUTE_SHIP_TO_STATE = "SHIP_TO_STATE";
	public String ATTRIBUTE_SHIP_TO_ZIP = "SHIP_TO_ZIP";
	public String ATTRIBUTE_INVOICE = "INVOICE";
	public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_PO_LINE_NUMBER = "PO_LINE_NUMBER";
	public String ATTRIBUTE_PO_LINE_SCHEDULE = "PO_LINE_SCHEDULE";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_LINE_ITEM_QUANTITY = "LINE_ITEM_QUANTITY";
	public String ATTRIBUTE_UOM = "UOM";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_TOTAL_SALES_TAX = "TOTAL_SALES_TAX";
	public String ATTRIBUTE_TOTAL_FREIGHT = "TOTAL_FREIGHT";
	public String ATTRIBUTE_TOTAL_SURCHARGE = "TOTAL_SURCHARGE";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";
	public String ATTRIBUTE_NET_DAYS = "NET_DAYS";
	public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";

	//table name
	public String TABLE = "INVOICE_FORMAT_PGE_VIEW";


	//constructor
	public InvoiceFormatPgeViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("billToName")) {
			return ATTRIBUTE_BILL_TO_NAME;
		}
		else if(attributeName.equals("billToAddr1")) {
			return ATTRIBUTE_BILL_TO_ADDR1;
		}
		else if(attributeName.equals("billToCity")) {
			return ATTRIBUTE_BILL_TO_CITY;
		}
		else if(attributeName.equals("billToState")) {
			return ATTRIBUTE_BILL_TO_STATE;
		}
		else if(attributeName.equals("billToZip")) {
			return ATTRIBUTE_BILL_TO_ZIP;
		}
		else if(attributeName.equals("remitToName")) {
			return ATTRIBUTE_REMIT_TO_NAME;
		}
		else if(attributeName.equals("remitToAddr1")) {
			return ATTRIBUTE_REMIT_TO_ADDR1;
		}
		else if(attributeName.equals("remitToCity")) {
			return ATTRIBUTE_REMIT_TO_CITY;
		}
		else if(attributeName.equals("remitToState")) {
			return ATTRIBUTE_REMIT_TO_STATE;
		}
		else if(attributeName.equals("remitToZip")) {
			return ATTRIBUTE_REMIT_TO_ZIP;
		}
		else if(attributeName.equals("shipToName")) {
			return ATTRIBUTE_SHIP_TO_NAME;
		}
		else if(attributeName.equals("shipToAddr1")) {
			return ATTRIBUTE_SHIP_TO_ADDR1;
		}
		else if(attributeName.equals("shipToAddr2")) {
			return ATTRIBUTE_SHIP_TO_ADDR2;
		}
		else if(attributeName.equals("shipToCity")) {
			return ATTRIBUTE_SHIP_TO_CITY;
		}
		else if(attributeName.equals("shipToState")) {
			return ATTRIBUTE_SHIP_TO_STATE;
		}
		else if(attributeName.equals("shipToZip")) {
			return ATTRIBUTE_SHIP_TO_ZIP;
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
		else if(attributeName.equals("poLineNumber")) {
			return ATTRIBUTE_PO_LINE_NUMBER;
		}
		else if(attributeName.equals("poLineSchedule")) {
			return ATTRIBUTE_PO_LINE_SCHEDULE;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("partDescription")) {
			return ATTRIBUTE_PART_DESCRIPTION;
		}
		else if(attributeName.equals("lineItemQuantity")) {
			return ATTRIBUTE_LINE_ITEM_QUANTITY;
		}
		else if(attributeName.equals("uom")) {
			return ATTRIBUTE_UOM;
		}
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("totalSalesTax")) {
			return ATTRIBUTE_TOTAL_SALES_TAX;
		}
		else if(attributeName.equals("totalFreight")) {
			return ATTRIBUTE_TOTAL_FREIGHT;
		}
		else if(attributeName.equals("totalSurcharge")) {
			return ATTRIBUTE_TOTAL_SURCHARGE;
		}
		else if(attributeName.equals("comments")) {
			return ATTRIBUTE_COMMENTS;
		}
		else if(attributeName.equals("netDays")) {
			return ATTRIBUTE_NET_DAYS;
		}
		else if(attributeName.equals("invoiceAmount")) {
			return ATTRIBUTE_INVOICE_AMOUNT;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, InvoiceFormatPgeViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(InvoiceFormatPgeViewBean invoiceFormatPgeViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + invoiceFormatPgeViewBean.getCompanyId());

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


	public int delete(InvoiceFormatPgeViewBean invoiceFormatPgeViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + invoiceFormatPgeViewBean.getCompanyId());

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
	public int insert(InvoiceFormatPgeViewBean invoiceFormatPgeViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(invoiceFormatPgeViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(InvoiceFormatPgeViewBean invoiceFormatPgeViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_BILL_TO_NAME + "," +
			ATTRIBUTE_BILL_TO_ADDR1 + "," +
			ATTRIBUTE_BILL_TO_CITY + "," +
			ATTRIBUTE_BILL_TO_STATE + "," +
			ATTRIBUTE_BILL_TO_ZIP + "," +
			ATTRIBUTE_REMIT_TO_NAME + "," +
			ATTRIBUTE_REMIT_TO_ADDR1 + "," +
			ATTRIBUTE_REMIT_TO_CITY + "," +
			ATTRIBUTE_REMIT_TO_STATE + "," +
			ATTRIBUTE_REMIT_TO_ZIP + "," +
			ATTRIBUTE_SHIP_TO_NAME + "," +
			ATTRIBUTE_SHIP_TO_ADDR1 + "," +
			ATTRIBUTE_SHIP_TO_ADDR2 + "," +
			ATTRIBUTE_SHIP_TO_CITY + "," +
			ATTRIBUTE_SHIP_TO_STATE + "," +
			ATTRIBUTE_SHIP_TO_ZIP + "," +
			ATTRIBUTE_INVOICE + "," +
			ATTRIBUTE_INVOICE_DATE + "," +
			ATTRIBUTE_PO_NUMBER + "," +
			ATTRIBUTE_PO_LINE_NUMBER + "," +
			ATTRIBUTE_PO_LINE_SCHEDULE + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_PART_DESCRIPTION + "," +
			ATTRIBUTE_LINE_ITEM_QUANTITY + "," +
			ATTRIBUTE_UOM + "," +
			ATTRIBUTE_UNIT_PRICE + "," +
			ATTRIBUTE_TOTAL_SALES_TAX + "," +
			ATTRIBUTE_TOTAL_FREIGHT + "," +
			ATTRIBUTE_TOTAL_SURCHARGE + "," +
			ATTRIBUTE_COMMENTS + "," +
			ATTRIBUTE_NET_DAYS + "," +
			ATTRIBUTE_INVOICE_AMOUNT + ")" +
 values (
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getBillToName()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getBillToAddr1()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getBillToCity()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getBillToState()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getBillToZip()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getRemitToName()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getRemitToAddr1()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getRemitToCity()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getRemitToState()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getRemitToZip()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getShipToName()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getShipToAddr1()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getShipToAddr2()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getShipToCity()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getShipToState()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getShipToZip()) + "," +
			StringHandler.nullIfZero(invoiceFormatPgeViewBean.getInvoice()) + "," +
			DateHandler.getOracleToDateFunction(invoiceFormatPgeViewBean.getInvoiceDate()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getPoNumber()) + "," +
			StringHandler.nullIfZero(invoiceFormatPgeViewBean.getPoLineNumber()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getPoLineSchedule()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getCatPartNo()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getPartDescription()) + "," +
			StringHandler.nullIfZero(invoiceFormatPgeViewBean.getLineItemQuantity()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getUom()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getUnitPrice()) + "," +
			StringHandler.nullIfZero(invoiceFormatPgeViewBean.getTotalSalesTax()) + "," +
			StringHandler.nullIfZero(invoiceFormatPgeViewBean.getTotalFreight()) + "," +
			StringHandler.nullIfZero(invoiceFormatPgeViewBean.getTotalSurcharge()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getComments()) + "," +
			SqlHandler.delimitString(invoiceFormatPgeViewBean.getNetDays()) + "," +
			StringHandler.nullIfZero(invoiceFormatPgeViewBean.getInvoiceAmount()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(InvoiceFormatPgeViewBean invoiceFormatPgeViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(invoiceFormatPgeViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(InvoiceFormatPgeViewBean invoiceFormatPgeViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getCompanyId()) + "," +
			ATTRIBUTE_BILL_TO_NAME + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getBillToName()) + "," +
			ATTRIBUTE_BILL_TO_ADDR1 + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getBillToAddr1()) + "," +
			ATTRIBUTE_BILL_TO_CITY + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getBillToCity()) + "," +
			ATTRIBUTE_BILL_TO_STATE + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getBillToState()) + "," +
			ATTRIBUTE_BILL_TO_ZIP + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getBillToZip()) + "," +
			ATTRIBUTE_REMIT_TO_NAME + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getRemitToName()) + "," +
			ATTRIBUTE_REMIT_TO_ADDR1 + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getRemitToAddr1()) + "," +
			ATTRIBUTE_REMIT_TO_CITY + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getRemitToCity()) + "," +
			ATTRIBUTE_REMIT_TO_STATE + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getRemitToState()) + "," +
			ATTRIBUTE_REMIT_TO_ZIP + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getRemitToZip()) + "," +
			ATTRIBUTE_SHIP_TO_NAME + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getShipToName()) + "," +
			ATTRIBUTE_SHIP_TO_ADDR1 + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getShipToAddr1()) + "," +
			ATTRIBUTE_SHIP_TO_ADDR2 + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getShipToAddr2()) + "," +
			ATTRIBUTE_SHIP_TO_CITY + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getShipToCity()) + "," +
			ATTRIBUTE_SHIP_TO_STATE + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getShipToState()) + "," +
			ATTRIBUTE_SHIP_TO_ZIP + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getShipToZip()) + "," +
			ATTRIBUTE_INVOICE + "=" + 
				StringHandler.nullIfZero(invoiceFormatPgeViewBean.getInvoice()) + "," +
			ATTRIBUTE_INVOICE_DATE + "=" + 
				DateHandler.getOracleToDateFunction(invoiceFormatPgeViewBean.getInvoiceDate()) + "," +
			ATTRIBUTE_PO_NUMBER + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getPoNumber()) + "," +
			ATTRIBUTE_PO_LINE_NUMBER + "=" + 
				StringHandler.nullIfZero(invoiceFormatPgeViewBean.getPoLineNumber()) + "," +
			ATTRIBUTE_PO_LINE_SCHEDULE + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getPoLineSchedule()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getCatPartNo()) + "," +
			ATTRIBUTE_PART_DESCRIPTION + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getPartDescription()) + "," +
			ATTRIBUTE_LINE_ITEM_QUANTITY + "=" + 
				StringHandler.nullIfZero(invoiceFormatPgeViewBean.getLineItemQuantity()) + "," +
			ATTRIBUTE_UOM + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getUom()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getUnitPrice()) + "," +
			ATTRIBUTE_TOTAL_SALES_TAX + "=" + 
				StringHandler.nullIfZero(invoiceFormatPgeViewBean.getTotalSalesTax()) + "," +
			ATTRIBUTE_TOTAL_FREIGHT + "=" + 
				StringHandler.nullIfZero(invoiceFormatPgeViewBean.getTotalFreight()) + "," +
			ATTRIBUTE_TOTAL_SURCHARGE + "=" + 
				StringHandler.nullIfZero(invoiceFormatPgeViewBean.getTotalSurcharge()) + "," +
			ATTRIBUTE_COMMENTS + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getComments()) + "," +
			ATTRIBUTE_NET_DAYS + "=" + 
				SqlHandler.delimitString(invoiceFormatPgeViewBean.getNetDays()) + "," +
			ATTRIBUTE_INVOICE_AMOUNT + "=" + 
				StringHandler.nullIfZero(invoiceFormatPgeViewBean.getInvoiceAmount()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				StringHandler.nullIfZero(invoiceFormatPgeViewBean.getCompanyId());

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

		Collection invoiceFormatPgeViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			InvoiceFormatPgeViewBean invoiceFormatPgeViewBean = new InvoiceFormatPgeViewBean();
			load(dataSetRow, invoiceFormatPgeViewBean);
			invoiceFormatPgeViewBeanColl.add(invoiceFormatPgeViewBean);
		}

		return invoiceFormatPgeViewBeanColl;
	}
}