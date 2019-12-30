package com.tcmis.internal.invoice.factory;


import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.invoice.beans.InvoiceFormatViewSlacBean;


/******************************************************************************
 * CLASSNAME: InvoiceFormatViewSlacBeanFactory <br>
 * @version: 1.0, Sep 18, 2008 <br>
 *****************************************************************************/


public class InvoiceFormatViewSlacBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_CHARGE_TYPE = "CHARGE_TYPE";
	public String ATTRIBUTE_COMMODITY = "COMMODITY";
	public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
	public String ATTRIBUTE_INVOICE = "INVOICE";
	public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
	public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
	public String ATTRIBUTE_ACCOUNT_NUMBER2 = "ACCOUNT_NUMBER2";
	public String ATTRIBUTE_REQUESTOR_NAME = "REQUESTOR_NAME";
	public String ATTRIBUTE_APPROVER = "APPROVER";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_CATALOG_PRICE = "CATALOG_PRICE";
	public String ATTRIBUTE_INVOICE_UNIT_PRICE = "INVOICE_UNIT_PRICE";
	public String ATTRIBUTE_TOTAL_ADD_CHARGE = "TOTAL_ADD_CHARGE";
	public String ATTRIBUTE_NET_AMOUNT = "NET_AMOUNT";
	public String ATTRIBUTE_PRICE_FLAG = "PRICE_FLAG";
	public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
	public String ATTRIBUTE_PRICE_DIFFERENCE = "PRICE_DIFFERENCE";
	public String ATTRIBUTE_ADD_CHARGE_DESC = "ADD_CHARGE_DESC";

	//table name
	public String TABLE = "INVOICE_FORMAT_VIEW_SLAC";


	//constructor
	public InvoiceFormatViewSlacBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("chargeType")) {
			return ATTRIBUTE_CHARGE_TYPE;
		}
		else if(attributeName.equals("commodity")) {
			return ATTRIBUTE_COMMODITY;
		}
		else if(attributeName.equals("itemType")) {
			return ATTRIBUTE_ITEM_TYPE;
		}
		else if(attributeName.equals("invoice")) {
			return ATTRIBUTE_INVOICE;
		}
		else if(attributeName.equals("invoiceDate")) {
			return ATTRIBUTE_INVOICE_DATE;
		}
		else if(attributeName.equals("accountNumber")) {
			return ATTRIBUTE_ACCOUNT_NUMBER;
		}
		else if(attributeName.equals("accountNumber2")) {
			return ATTRIBUTE_ACCOUNT_NUMBER2;
		}
		else if(attributeName.equals("requestorName")) {
			return ATTRIBUTE_REQUESTOR_NAME;
		}
		else if(attributeName.equals("approver")) {
			return ATTRIBUTE_APPROVER;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("partDescription")) {
			return ATTRIBUTE_PART_DESCRIPTION;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("dateDelivered")) {
			return ATTRIBUTE_DATE_DELIVERED;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("catalogPrice")) {
			return ATTRIBUTE_CATALOG_PRICE;
		}
		else if(attributeName.equals("invoiceUnitPrice")) {
			return ATTRIBUTE_INVOICE_UNIT_PRICE;
		}
		else if(attributeName.equals("totalAddCharge")) {
			return ATTRIBUTE_TOTAL_ADD_CHARGE;
		}
		else if(attributeName.equals("netAmount")) {
			return ATTRIBUTE_NET_AMOUNT;
		}
		else if(attributeName.equals("priceFlag")) {
			return ATTRIBUTE_PRICE_FLAG;
		}
		else if(attributeName.equals("invoicePeriod")) {
			return ATTRIBUTE_INVOICE_PERIOD;
		}
		else if(attributeName.equals("priceDifference")) {
			return ATTRIBUTE_PRICE_DIFFERENCE;
		}
		else if(attributeName.equals("addChargeDesc")) {
			return ATTRIBUTE_ADD_CHARGE_DESC;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, InvoiceFormatViewSlacBean.class);
	}


	//you need to verify the primary key(s) before uncommenting this
	/*
	//delete
	public int delete(InvoiceFormatViewSlacBean invoiceFormatViewSlacBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("facilityId", "SearchCriterion.EQUALS",
			"" + invoiceFormatViewSlacBean.getFacilityId());

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


	public int delete(InvoiceFormatViewSlacBean invoiceFormatViewSlacBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("facilityId", "SearchCriterion.EQUALS",
			"" + invoiceFormatViewSlacBean.getFacilityId());

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
	public int insert(InvoiceFormatViewSlacBean invoiceFormatViewSlacBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(invoiceFormatViewSlacBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(InvoiceFormatViewSlacBean invoiceFormatViewSlacBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_APPLICATION + "," +
			ATTRIBUTE_CHARGE_TYPE + "," +
			ATTRIBUTE_COMMODITY + "," +
			ATTRIBUTE_ITEM_TYPE + "," +
			ATTRIBUTE_INVOICE + "," +
			ATTRIBUTE_INVOICE_DATE + "," +
			ATTRIBUTE_ACCOUNT_NUMBER + "," +
			ATTRIBUTE_ACCOUNT_NUMBER2 + "," +
			ATTRIBUTE_REQUESTOR_NAME + "," +
			ATTRIBUTE_APPROVER + "," +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_PART_DESCRIPTION + "," +
			ATTRIBUTE_PACKAGING + "," +
			ATTRIBUTE_RECEIPT_ID + "," +
			ATTRIBUTE_DATE_DELIVERED + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_CATALOG_PRICE + "," +
			ATTRIBUTE_INVOICE_UNIT_PRICE + "," +
			ATTRIBUTE_TOTAL_ADD_CHARGE + "," +
			ATTRIBUTE_NET_AMOUNT + "," +
			ATTRIBUTE_PRICE_FLAG + "," +
			ATTRIBUTE_INVOICE_PERIOD + "," +
			ATTRIBUTE_PRICE_DIFFERENCE + "," +
			ATTRIBUTE_ADD_CHARGE_DESC + ")" +
			" values (" +
			SqlHandler.delimitString(invoiceFormatViewSlacBean.getFacilityId()) + "," +
			SqlHandler.delimitString(invoiceFormatViewSlacBean.getApplication()) + "," +
			SqlHandler.delimitString(invoiceFormatViewSlacBean.getChargeType()) + "," +
			SqlHandler.delimitString(invoiceFormatViewSlacBean.getCommodity()) + "," +
			SqlHandler.delimitString(invoiceFormatViewSlacBean.getItemType()) + "," +
			invoiceFormatViewSlacBean.getInvoice() + "," +
			DateHandler.getOracleToDateFunction(invoiceFormatViewSlacBean.getInvoiceDate()) + "," +
			SqlHandler.delimitString(invoiceFormatViewSlacBean.getAccountNumber()) + "," +
			SqlHandler.delimitString(invoiceFormatViewSlacBean.getAccountNumber2()) + "," +
			SqlHandler.delimitString(invoiceFormatViewSlacBean.getRequestorName()) + "," +
			SqlHandler.delimitString(invoiceFormatViewSlacBean.getApprover()) + "," +
			invoiceFormatViewSlacBean.getPrNumber() + "," +
			SqlHandler.delimitString(invoiceFormatViewSlacBean.getLineItem()) + "," +
			SqlHandler.delimitString(invoiceFormatViewSlacBean.getPartDescription()) + "," +
			SqlHandler.delimitString(invoiceFormatViewSlacBean.getPackaging()) + "," +
			invoiceFormatViewSlacBean.getReceiptId() + "," +
			DateHandler.getOracleToDateFunction(invoiceFormatViewSlacBean.getDateDelivered()) + "," +
			invoiceFormatViewSlacBean.getQuantity() + "," +
			SqlHandler.delimitString(invoiceFormatViewSlacBean.getCatalogPrice()) + "," +
			SqlHandler.delimitString(invoiceFormatViewSlacBean.getInvoiceUnitPrice()) + "," +
			SqlHandler.delimitString(invoiceFormatViewSlacBean.getTotalAddCharge()) + "," +
			SqlHandler.delimitString(invoiceFormatViewSlacBean.getNetAmount()) + "," +
			SqlHandler.delimitString(invoiceFormatViewSlacBean.getPriceFlag()) + "," +
			invoiceFormatViewSlacBean.getInvoicePeriod() + "," +
			invoiceFormatViewSlacBean.getPriceDifference() + "," +
			SqlHandler.delimitString(invoiceFormatViewSlacBean.getAddChargeDesc()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(InvoiceFormatViewSlacBean invoiceFormatViewSlacBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(invoiceFormatViewSlacBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(InvoiceFormatViewSlacBean invoiceFormatViewSlacBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_FACILITY_ID + "=" +
				SqlHandler.delimitString(invoiceFormatViewSlacBean.getFacilityId()) + "," +
			ATTRIBUTE_APPLICATION + "=" +
				SqlHandler.delimitString(invoiceFormatViewSlacBean.getApplication()) + "," +
			ATTRIBUTE_CHARGE_TYPE + "=" +
				SqlHandler.delimitString(invoiceFormatViewSlacBean.getChargeType()) + "," +
			ATTRIBUTE_COMMODITY + "=" +
				SqlHandler.delimitString(invoiceFormatViewSlacBean.getCommodity()) + "," +
			ATTRIBUTE_ITEM_TYPE + "=" +
				SqlHandler.delimitString(invoiceFormatViewSlacBean.getItemType()) + "," +
			ATTRIBUTE_INVOICE + "=" +
				StringHandler.nullIfZero(invoiceFormatViewSlacBean.getInvoice()) + "," +
			ATTRIBUTE_INVOICE_DATE + "=" +
				DateHandler.getOracleToDateFunction(invoiceFormatViewSlacBean.getInvoiceDate()) + "," +
			ATTRIBUTE_ACCOUNT_NUMBER + "=" +
				SqlHandler.delimitString(invoiceFormatViewSlacBean.getAccountNumber()) + "," +
			ATTRIBUTE_ACCOUNT_NUMBER2 + "=" +
				SqlHandler.delimitString(invoiceFormatViewSlacBean.getAccountNumber2()) + "," +
			ATTRIBUTE_REQUESTOR_NAME + "=" +
				SqlHandler.delimitString(invoiceFormatViewSlacBean.getRequestorName()) + "," +
			ATTRIBUTE_APPROVER + "=" +
				SqlHandler.delimitString(invoiceFormatViewSlacBean.getApprover()) + "," +
			ATTRIBUTE_PR_NUMBER + "=" +
				StringHandler.nullIfZero(invoiceFormatViewSlacBean.getPrNumber()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" +
				SqlHandler.delimitString(invoiceFormatViewSlacBean.getLineItem()) + "," +
			ATTRIBUTE_PART_DESCRIPTION + "=" +
				SqlHandler.delimitString(invoiceFormatViewSlacBean.getPartDescription()) + "," +
			ATTRIBUTE_PACKAGING + "=" +
				SqlHandler.delimitString(invoiceFormatViewSlacBean.getPackaging()) + "," +
			ATTRIBUTE_RECEIPT_ID + "=" +
				StringHandler.nullIfZero(invoiceFormatViewSlacBean.getReceiptId()) + "," +
			ATTRIBUTE_DATE_DELIVERED + "=" +
				DateHandler.getOracleToDateFunction(invoiceFormatViewSlacBean.getDateDelivered()) + "," +
			ATTRIBUTE_QUANTITY + "=" +
				StringHandler.nullIfZero(invoiceFormatViewSlacBean.getQuantity()) + "," +
			ATTRIBUTE_CATALOG_PRICE + "=" +
				SqlHandler.delimitString(invoiceFormatViewSlacBean.getCatalogPrice()) + "," +
			ATTRIBUTE_INVOICE_UNIT_PRICE + "=" +
				SqlHandler.delimitString(invoiceFormatViewSlacBean.getInvoiceUnitPrice()) + "," +
			ATTRIBUTE_TOTAL_ADD_CHARGE + "=" +
				SqlHandler.delimitString(invoiceFormatViewSlacBean.getTotalAddCharge()) + "," +
			ATTRIBUTE_NET_AMOUNT + "=" +
				SqlHandler.delimitString(invoiceFormatViewSlacBean.getNetAmount()) + "," +
			ATTRIBUTE_PRICE_FLAG + "=" +
				SqlHandler.delimitString(invoiceFormatViewSlacBean.getPriceFlag()) + "," +
			ATTRIBUTE_INVOICE_PERIOD + "=" +
				StringHandler.nullIfZero(invoiceFormatViewSlacBean.getInvoicePeriod()) + "," +
			ATTRIBUTE_PRICE_DIFFERENCE + "=" +
				StringHandler.nullIfZero(invoiceFormatViewSlacBean.getPriceDifference()) + "," +
			ATTRIBUTE_ADD_CHARGE_DESC + "=" +
				SqlHandler.delimitString(invoiceFormatViewSlacBean.getAddChargeDesc()) + " " +
			"where " + ATTRIBUTE_FACILITY_ID + "=" +
				invoiceFormatViewSlacBean.getFacilityId();

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
		catch (Exception e) {
			log.fatal("Error querying DB ", e);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
	throws BaseException {

		Collection invoiceFormatViewSlacBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			InvoiceFormatViewSlacBean invoiceFormatViewSlacBean = new InvoiceFormatViewSlacBean();
			load(dataSetRow, invoiceFormatViewSlacBean);
			invoiceFormatViewSlacBeanColl.add(invoiceFormatViewSlacBean);
		}

		return invoiceFormatViewSlacBeanColl;
	}

	public Collection selectInvoicePeriod(SearchCriteria criteria) throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectInvoicePeriod(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection selectInvoicePeriod(SearchCriteria criteria, Connection conn) throws
	BaseException {

		Collection invoiceFormatViewSlacBeanColl = new Vector();

		String query = "select unique invoice_period from " + TABLE + " " +
		getWhereClause(criteria);
		if(log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			InvoiceFormatViewSlacBean invoiceFormatViewSlacBean = new
			InvoiceFormatViewSlacBean();
			load(dataSetRow, invoiceFormatViewSlacBean);
			invoiceFormatViewSlacBeanColl.add(invoiceFormatViewSlacBean);
		}

		return invoiceFormatViewSlacBeanColl;
	}

}