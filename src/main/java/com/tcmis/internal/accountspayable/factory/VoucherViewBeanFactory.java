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
import com.tcmis.internal.accountspayable.beans.VoucherViewBean;


/******************************************************************************
 * CLASSNAME: VoucherViewBeanFactory <br>
 * @version: 1.0, Jan 29, 2008 <br>
 *****************************************************************************/


public class VoucherViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_VOUCHER_ID = "VOUCHER_ID";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SUPPLIER_INVOICE_ID = "SUPPLIER_INVOICE_ID";
	public String ATTRIBUTE_ORIGINAL_INVOICE_ID = "ORIGINAL_INVOICE_ID";
	public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
	public String ATTRIBUTE_DATE_INVOICE_RECEIVED = "DATE_INVOICE_RECEIVED";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_CUSTOMER_REF_PO = "CUSTOMER_REF_PO";
	public String ATTRIBUTE_INVOICE_FILE_PATH = "INVOICE_FILE_PATH";
	public String ATTRIBUTE_NET_INVOICE_AMOUNT = "NET_INVOICE_AMOUNT";
	public String ATTRIBUTE_PYMT_TERM_START_DATE = "PYMT_TERM_START_DATE";
	public String ATTRIBUTE_DATE_TO_PAY = "DATE_TO_PAY";
	public String ATTRIBUTE_REMIT_TO_LOC_ID = "REMIT_TO_LOC_ID";
	public String ATTRIBUTE_SUPPLIER_CONTACT_ID = "SUPPLIER_CONTACT_ID";
	public String ATTRIBUTE_ORIGINAL_SUPPLIER = "ORIGINAL_SUPPLIER";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_PAYMENT_TERMS = "PAYMENT_TERMS";
	public String ATTRIBUTE_AP_NOTE = "AP_NOTE";
	public String ATTRIBUTE_AP_USER_ID = "AP_USER_ID";
	public String ATTRIBUTE_DATE_LAST_UPDATED = "DATE_LAST_UPDATED";
	public String ATTRIBUTE_DATE_SENT_TO_HASS = "DATE_SENT_TO_HASS";
	public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
	public String ATTRIBUTE_INVOICE_URL = "INVOICE_URL";
	public String ATTRIBUTE_VOUCHER_BILLING_LOCATION = "VOUCHER_BILLING_LOCATION";
	public String ATTRIBUTE_VERIFIED = "VERIFIED";
	public String ATTRIBUTE_STATUS_DATE = "STATUS_DATE";
	public String ATTRIBUTE_CHANGES_AFTER_APPROVE_NOTES = "CHANGES_AFTER_APPROVE_NOTES";
	public String ATTRIBUTE_INSERT_DATE = "INSERT_DATE";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_E_TAX_RATE = "E_TAX_RATE";
	public String ATTRIBUTE_APPROVER = "APPROVER";
	public String ATTRIBUTE_APPROVED_DATE = "APPROVED_DATE";
	public String ATTRIBUTE_QC_USER = "QC_USER";
	public String ATTRIBUTE_QC_DATE = "QC_DATE";
	public String ATTRIBUTE_DATE_VOUCHER_IMPORTED = "DATE_VOUCHER_IMPORTED";
	public String ATTRIBUTE_AP_USER_NAME = "AP_USER_NAME";
	public String ATTRIBUTE_AP_APPROVER_NAME = "AP_APPROVER_NAME";
	public String ATTRIBUTE_AP_QC_USER_NAME = "AP_QC_USER_NAME";
	public String ATTRIBUTE_ORIG_APPROVER = "ORIG_APPROVER";
	public String ATTRIBUTE_AP_ORIG_APPROVER_NAME = "AP_ORIG_APPROVER_NAME";
	public String ATTRIBUTE_UPLOAD_STATUS = "UPLOAD_STATUS";

	//table name
	public String TABLE = "VOUCHER_VIEW";


	//constructor
	public VoucherViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("voucherId")) {
			return ATTRIBUTE_VOUCHER_ID;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("supplierInvoiceId")) {
			return ATTRIBUTE_SUPPLIER_INVOICE_ID;
		}
		else if(attributeName.equals("originalInvoiceId")) {
			return ATTRIBUTE_ORIGINAL_INVOICE_ID;
		}
		else if(attributeName.equals("invoiceDate")) {
			return ATTRIBUTE_INVOICE_DATE;
		}
		else if(attributeName.equals("dateInvoiceReceived")) {
			return ATTRIBUTE_DATE_INVOICE_RECEIVED;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("customerRefPo")) {
			return ATTRIBUTE_CUSTOMER_REF_PO;
		}
		else if(attributeName.equals("invoiceFilePath")) {
			return ATTRIBUTE_INVOICE_FILE_PATH;
		}
		else if(attributeName.equals("netInvoiceAmount")) {
			return ATTRIBUTE_NET_INVOICE_AMOUNT;
		}
		else if(attributeName.equals("pymtTermStartDate")) {
			return ATTRIBUTE_PYMT_TERM_START_DATE;
		}
		else if(attributeName.equals("dateToPay")) {
			return ATTRIBUTE_DATE_TO_PAY;
		}
		else if(attributeName.equals("remitToLocId")) {
			return ATTRIBUTE_REMIT_TO_LOC_ID;
		}
		else if(attributeName.equals("supplierContactId")) {
			return ATTRIBUTE_SUPPLIER_CONTACT_ID;
		}
		else if(attributeName.equals("originalSupplier")) {
			return ATTRIBUTE_ORIGINAL_SUPPLIER;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if(attributeName.equals("paymentTerms")) {
			return ATTRIBUTE_PAYMENT_TERMS;
		}
		else if(attributeName.equals("apNote")) {
			return ATTRIBUTE_AP_NOTE;
		}
		else if(attributeName.equals("apUserId")) {
			return ATTRIBUTE_AP_USER_ID;
		}
		else if(attributeName.equals("dateLastUpdated")) {
			return ATTRIBUTE_DATE_LAST_UPDATED;
		}
		else if(attributeName.equals("dateSentToHass")) {
			return ATTRIBUTE_DATE_SENT_TO_HASS;
		}
		else if(attributeName.equals("invoiceAmount")) {
			return ATTRIBUTE_INVOICE_AMOUNT;
		}
		else if(attributeName.equals("invoiceUrl")) {
			return ATTRIBUTE_INVOICE_URL;
		}
		else if(attributeName.equals("voucherBillingLocation")) {
			return ATTRIBUTE_VOUCHER_BILLING_LOCATION;
		}
		else if(attributeName.equals("verified")) {
			return ATTRIBUTE_VERIFIED;
		}
		else if(attributeName.equals("statusDate")) {
			return ATTRIBUTE_STATUS_DATE;
		}
		else if(attributeName.equals("changesAfterApproveNotes")) {
			return ATTRIBUTE_CHANGES_AFTER_APPROVE_NOTES;
		}
		else if(attributeName.equals("insertDate")) {
			return ATTRIBUTE_INSERT_DATE;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("eTaxRate")) {
			return ATTRIBUTE_E_TAX_RATE;
		}
		else if(attributeName.equals("approver")) {
			return ATTRIBUTE_APPROVER;
		}
		else if(attributeName.equals("approvedDate")) {
			return ATTRIBUTE_APPROVED_DATE;
		}
		else if(attributeName.equals("qcUser")) {
			return ATTRIBUTE_QC_USER;
		}
		else if(attributeName.equals("qcDate")) {
			return ATTRIBUTE_QC_DATE;
		}
		else if(attributeName.equals("dateVoucherImported")) {
			return ATTRIBUTE_DATE_VOUCHER_IMPORTED;
		}
		else if(attributeName.equals("apUserName")) {
			return ATTRIBUTE_AP_USER_NAME;
		}
		else if(attributeName.equals("apApproverName")) {
			return ATTRIBUTE_AP_APPROVER_NAME;
		}
		else if(attributeName.equals("apQcUserName")) {
			return ATTRIBUTE_AP_QC_USER_NAME;
		}
		else if(attributeName.equals("origApprover")) {
			return ATTRIBUTE_ORIG_APPROVER;
		}
		else if(attributeName.equals("apOrigApproverName")) {
			return ATTRIBUTE_AP_ORIG_APPROVER_NAME;
		}
		else if(attributeName.equals("uploadStatus")) {
			return ATTRIBUTE_UPLOAD_STATUS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, VoucherViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(VoucherViewBean voucherViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("voucherId", "SearchCriterion.EQUALS",
			"" + voucherViewBean.getVoucherId());

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


	public int delete(VoucherViewBean voucherViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("voucherId", "SearchCriterion.EQUALS",
			"" + voucherViewBean.getVoucherId());

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
	public int insert(VoucherViewBean voucherViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(voucherViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(VoucherViewBean voucherViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_VOUCHER_ID + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_SUPPLIER_INVOICE_ID + "," +
			ATTRIBUTE_ORIGINAL_INVOICE_ID + "," +
			ATTRIBUTE_INVOICE_DATE + "," +
			ATTRIBUTE_DATE_INVOICE_RECEIVED + "," +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_CUSTOMER_REF_PO + "," +
			ATTRIBUTE_INVOICE_FILE_PATH + "," +
			ATTRIBUTE_NET_INVOICE_AMOUNT + "," +
			ATTRIBUTE_PYMT_TERM_START_DATE + "," +
			ATTRIBUTE_DATE_TO_PAY + "," +
			ATTRIBUTE_REMIT_TO_LOC_ID + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_ID + "," +
			ATTRIBUTE_ORIGINAL_SUPPLIER + "," +
			ATTRIBUTE_STATUS + "," +
			ATTRIBUTE_PAYMENT_TERMS + "," +
			ATTRIBUTE_AP_NOTE + "," +
			ATTRIBUTE_AP_USER_ID + "," +
			ATTRIBUTE_DATE_LAST_UPDATED + "," +
			ATTRIBUTE_DATE_SENT_TO_HASS + "," +
			ATTRIBUTE_INVOICE_AMOUNT + "," +
			ATTRIBUTE_INVOICE_URL + "," +
			ATTRIBUTE_VOUCHER_BILLING_LOCATION + "," +
			ATTRIBUTE_VERIFIED + "," +
			ATTRIBUTE_STATUS_DATE + "," +
			ATTRIBUTE_CHANGES_AFTER_APPROVE_NOTES + "," +
			ATTRIBUTE_INSERT_DATE + "," +
			ATTRIBUTE_CURRENCY_ID + "," +
			ATTRIBUTE_E_TAX_RATE + "," +
			ATTRIBUTE_APPROVER + "," +
			ATTRIBUTE_APPROVED_DATE + "," +
			ATTRIBUTE_QC_USER + "," +
			ATTRIBUTE_QC_DATE + "," +
			ATTRIBUTE_DATE_VOUCHER_IMPORTED + "," +
			ATTRIBUTE_AP_USER_NAME + "," +
			ATTRIBUTE_AP_APPROVER_NAME + "," +
			ATTRIBUTE_AP_QC_USER_NAME + "," +
			ATTRIBUTE_ORIG_APPROVER + "," +
			ATTRIBUTE_AP_ORIG_APPROVER_NAME + "," +
			ATTRIBUTE_UPLOAD_STATUS + ")" +
			" values (" +
			voucherViewBean.getVoucherId() + "," +
			SqlHandler.delimitString(voucherViewBean.getSupplier()) + "," +
			SqlHandler.delimitString(voucherViewBean.getSupplierInvoiceId()) + "," +
			SqlHandler.delimitString(voucherViewBean.getOriginalInvoiceId()) + "," +
			DateHandler.getOracleToDateFunction(voucherViewBean.getInvoiceDate()) + "," +
			DateHandler.getOracleToDateFunction(voucherViewBean.getDateInvoiceReceived()) + "," +
			voucherViewBean.getRadianPo() + "," +
			SqlHandler.delimitString(voucherViewBean.getCustomerRefPo()) + "," +
			SqlHandler.delimitString(voucherViewBean.getInvoiceFilePath()) + "," +
			voucherViewBean.getNetInvoiceAmount() + "," +
			DateHandler.getOracleToDateFunction(voucherViewBean.getPymtTermStartDate()) + "," +
			DateHandler.getOracleToDateFunction(voucherViewBean.getDateToPay()) + "," +
			SqlHandler.delimitString(voucherViewBean.getRemitToLocId()) + "," +
			voucherViewBean.getSupplierContactId() + "," +
			SqlHandler.delimitString(voucherViewBean.getOriginalSupplier()) + "," +
			SqlHandler.delimitString(voucherViewBean.getStatus()) + "," +
			SqlHandler.delimitString(voucherViewBean.getPaymentTerms()) + "," +
			SqlHandler.delimitString(voucherViewBean.getApNote()) + "," +
			voucherViewBean.getApUserId() + "," +
			DateHandler.getOracleToDateFunction(voucherViewBean.getDateLastUpdated()) + "," +
			DateHandler.getOracleToDateFunction(voucherViewBean.getDateSentToHass()) + "," +
			voucherViewBean.getInvoiceAmount() + "," +
			SqlHandler.delimitString(voucherViewBean.getInvoiceUrl()) + "," +
			SqlHandler.delimitString(voucherViewBean.getVoucherBillingLocation()) + "," +
			SqlHandler.delimitString(voucherViewBean.getVerified()) + "," +
			DateHandler.getOracleToDateFunction(voucherViewBean.getStatusDate()) + "," +
			SqlHandler.delimitString(voucherViewBean.getChangesAfterApproveNotes()) + "," +
			DateHandler.getOracleToDateFunction(voucherViewBean.getInsertDate()) + "," +
			SqlHandler.delimitString(voucherViewBean.getCurrencyId()) + "," +
			voucherViewBean.getETaxRate() + "," +
			voucherViewBean.getApprover() + "," +
			DateHandler.getOracleToDateFunction(voucherViewBean.getApprovedDate()) + "," +
			voucherViewBean.getQcUser() + "," +
			DateHandler.getOracleToDateFunction(voucherViewBean.getQcDate()) + "," +
			DateHandler.getOracleToDateFunction(voucherViewBean.getDateVoucherImported()) + "," +
			SqlHandler.delimitString(voucherViewBean.getApUserName()) + "," +
			SqlHandler.delimitString(voucherViewBean.getApApproverName()) + "," +
			SqlHandler.delimitString(voucherViewBean.getApQcUserName()) + "," +
			voucherViewBean.getOrigApprover() + "," +
			SqlHandler.delimitString(voucherViewBean.getApOrigApproverName()) + "," +
			SqlHandler.delimitString(voucherViewBean.getUploadStatus()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(VoucherViewBean voucherViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(voucherViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(VoucherViewBean voucherViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_VOUCHER_ID + "=" + 
				StringHandler.nullIfZero(voucherViewBean.getVoucherId()) + "," +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(voucherViewBean.getSupplier()) + "," +
			ATTRIBUTE_SUPPLIER_INVOICE_ID + "=" + 
				SqlHandler.delimitString(voucherViewBean.getSupplierInvoiceId()) + "," +
			ATTRIBUTE_ORIGINAL_INVOICE_ID + "=" + 
				SqlHandler.delimitString(voucherViewBean.getOriginalInvoiceId()) + "," +
			ATTRIBUTE_INVOICE_DATE + "=" + 
				DateHandler.getOracleToDateFunction(voucherViewBean.getInvoiceDate()) + "," +
			ATTRIBUTE_DATE_INVOICE_RECEIVED + "=" + 
				DateHandler.getOracleToDateFunction(voucherViewBean.getDateInvoiceReceived()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(voucherViewBean.getRadianPo()) + "," +
			ATTRIBUTE_CUSTOMER_REF_PO + "=" + 
				SqlHandler.delimitString(voucherViewBean.getCustomerRefPo()) + "," +
			ATTRIBUTE_INVOICE_FILE_PATH + "=" + 
				SqlHandler.delimitString(voucherViewBean.getInvoiceFilePath()) + "," +
			ATTRIBUTE_NET_INVOICE_AMOUNT + "=" + 
				StringHandler.nullIfZero(voucherViewBean.getNetInvoiceAmount()) + "," +
			ATTRIBUTE_PYMT_TERM_START_DATE + "=" + 
				DateHandler.getOracleToDateFunction(voucherViewBean.getPymtTermStartDate()) + "," +
			ATTRIBUTE_DATE_TO_PAY + "=" + 
				DateHandler.getOracleToDateFunction(voucherViewBean.getDateToPay()) + "," +
			ATTRIBUTE_REMIT_TO_LOC_ID + "=" + 
				SqlHandler.delimitString(voucherViewBean.getRemitToLocId()) + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_ID + "=" + 
				StringHandler.nullIfZero(voucherViewBean.getSupplierContactId()) + "," +
			ATTRIBUTE_ORIGINAL_SUPPLIER + "=" + 
				SqlHandler.delimitString(voucherViewBean.getOriginalSupplier()) + "," +
			ATTRIBUTE_STATUS + "=" + 
				SqlHandler.delimitString(voucherViewBean.getStatus()) + "," +
			ATTRIBUTE_PAYMENT_TERMS + "=" + 
				SqlHandler.delimitString(voucherViewBean.getPaymentTerms()) + "," +
			ATTRIBUTE_AP_NOTE + "=" + 
				SqlHandler.delimitString(voucherViewBean.getApNote()) + "," +
			ATTRIBUTE_AP_USER_ID + "=" + 
				StringHandler.nullIfZero(voucherViewBean.getApUserId()) + "," +
			ATTRIBUTE_DATE_LAST_UPDATED + "=" + 
				DateHandler.getOracleToDateFunction(voucherViewBean.getDateLastUpdated()) + "," +
			ATTRIBUTE_DATE_SENT_TO_HASS + "=" + 
				DateHandler.getOracleToDateFunction(voucherViewBean.getDateSentToHass()) + "," +
			ATTRIBUTE_INVOICE_AMOUNT + "=" + 
				StringHandler.nullIfZero(voucherViewBean.getInvoiceAmount()) + "," +
			ATTRIBUTE_INVOICE_URL + "=" + 
				SqlHandler.delimitString(voucherViewBean.getInvoiceUrl()) + "," +
			ATTRIBUTE_VOUCHER_BILLING_LOCATION + "=" + 
				SqlHandler.delimitString(voucherViewBean.getVoucherBillingLocation()) + "," +
			ATTRIBUTE_VERIFIED + "=" + 
				SqlHandler.delimitString(voucherViewBean.getVerified()) + "," +
			ATTRIBUTE_STATUS_DATE + "=" + 
				DateHandler.getOracleToDateFunction(voucherViewBean.getStatusDate()) + "," +
			ATTRIBUTE_CHANGES_AFTER_APPROVE_NOTES + "=" + 
				SqlHandler.delimitString(voucherViewBean.getChangesAfterApproveNotes()) + "," +
			ATTRIBUTE_INSERT_DATE + "=" + 
				DateHandler.getOracleToDateFunction(voucherViewBean.getInsertDate()) + "," +
			ATTRIBUTE_CURRENCY_ID + "=" + 
				SqlHandler.delimitString(voucherViewBean.getCurrencyId()) + "," +
			ATTRIBUTE_E_TAX_RATE + "=" + 
				StringHandler.nullIfZero(voucherViewBean.getETaxRate()) + "," +
			ATTRIBUTE_APPROVER + "=" + 
				StringHandler.nullIfZero(voucherViewBean.getApprover()) + "," +
			ATTRIBUTE_APPROVED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(voucherViewBean.getApprovedDate()) + "," +
			ATTRIBUTE_QC_USER + "=" + 
				StringHandler.nullIfZero(voucherViewBean.getQcUser()) + "," +
			ATTRIBUTE_QC_DATE + "=" + 
				DateHandler.getOracleToDateFunction(voucherViewBean.getQcDate()) + "," +
			ATTRIBUTE_DATE_VOUCHER_IMPORTED + "=" + 
				DateHandler.getOracleToDateFunction(voucherViewBean.getDateVoucherImported()) + "," +
			ATTRIBUTE_AP_USER_NAME + "=" + 
				SqlHandler.delimitString(voucherViewBean.getApUserName()) + "," +
			ATTRIBUTE_AP_APPROVER_NAME + "=" + 
				SqlHandler.delimitString(voucherViewBean.getApApproverName()) + "," +
			ATTRIBUTE_AP_QC_USER_NAME + "=" + 
				SqlHandler.delimitString(voucherViewBean.getApQcUserName()) + "," +
			ATTRIBUTE_ORIG_APPROVER + "=" + 
				StringHandler.nullIfZero(voucherViewBean.getOrigApprover()) + "," +
			ATTRIBUTE_AP_ORIG_APPROVER_NAME + "=" + 
				SqlHandler.delimitString(voucherViewBean.getApOrigApproverName()) + "," +
			ATTRIBUTE_UPLOAD_STATUS + "=" + 
				SqlHandler.delimitString(voucherViewBean.getUploadStatus()) + " " +
			"where " + ATTRIBUTE_VOUCHER_ID + "=" +
				voucherViewBean.getVoucherId();

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

		Collection voucherViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			VoucherViewBean voucherViewBean = new VoucherViewBean();
			load(dataSetRow, voucherViewBean);
			voucherViewBeanColl.add(voucherViewBean);
		}

		return voucherViewBeanColl;
	}
}