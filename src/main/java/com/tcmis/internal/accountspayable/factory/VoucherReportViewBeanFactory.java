package com.tcmis.internal.accountspayable.factory;


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
import com.tcmis.internal.accountspayable.beans.VoucherReportViewBean;


/******************************************************************************
 * CLASSNAME: VoucherReportViewBeanFactory <br>
 * @version: 1.0, Sep 13, 2006 <br>
 *****************************************************************************/


public class VoucherReportViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_VOUCHER_ID = "VOUCHER_ID";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_AP_USER_ID = "AP_USER_ID";
	public String ATTRIBUTE_BUYER_NAME = "BUYER_NAME";
	public String ATTRIBUTE_BUYER_EMAIL = "BUYER_EMAIL";
	public String ATTRIBUTE_BUYER_PHONE = "BUYER_PHONE";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_SUPPLIER_INVOICE_ID = "SUPPLIER_INVOICE_ID";
	public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
	public String ATTRIBUTE_VOUCHER_AGE = "VOUCHER_AGE";
	public String ATTRIBUTE_PO_TERMS = "PO_TERMS";
	public String ATTRIBUTE_SUPPLIER_TERMS = "SUPPLIER_TERMS";
	public String ATTRIBUTE_NET_INVOICE_AMOUNT = "NET_INVOICE_AMOUNT";
	public String ATTRIBUTE_AVAILABLE_RECEIPTS = "AVAILABLE_RECEIPTS";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_AP_NOTE = "AP_NOTE";
	public String ATTRIBUTE_APPROVER = "APPROVER";
	public String ATTRIBUTE_APPROVED_DATE = "APPROVED_DATE";
	public String ATTRIBUTE_QC_USER = "QC_USER";
	public String ATTRIBUTE_QC_DATE = "QC_DATE";
	public String ATTRIBUTE_DATE_VOUCHER_IMPORTED = "DATE_VOUCHER_IMPORTED";
	public String ATTRIBUTE_AP_USER_NAME = "AP_USER_NAME";
	public String ATTRIBUTE_AP_APPROVER_NAME = "AP_APPROVER_NAME";
	public String ATTRIBUTE_AP_QC_USER_NAME = "AP_QC_USER_NAME";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	
	public String ATTRIBUTE_PO_HUB_NAME = "PO_HUB_NAME";
	public String ATTRIBUTE_PO_INVENTORY_GROUP_NAME = "PO_INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_PO_OPERATING_ENTITY_NAME = "PO_OPERATING_ENTITY_NAME";
	public String ATTRIBUTE_SUPPLIER_OPS_ENTITY_ID = "SUPPLIER_OPS_ENTITY_ID";
	public String ATTRIBUTE_BUYER_ID = "BUYER_ID";

	//table name
	public String TABLE = "VOUCHER_REPORT_VIEW";


	//constructor
	public VoucherReportViewBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("apUserId")) {
			return ATTRIBUTE_AP_USER_ID;
		}
		else if(attributeName.equals("buyerName")) {
			return ATTRIBUTE_BUYER_NAME;
		}
		else if(attributeName.equals("buyerEmail")) {
			return ATTRIBUTE_BUYER_EMAIL;
		}
		else if(attributeName.equals("buyerPhone")) {
			return ATTRIBUTE_BUYER_PHONE;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("supplierInvoiceId")) {
			return ATTRIBUTE_SUPPLIER_INVOICE_ID;
		}
		else if(attributeName.equals("invoiceDate")) {
			return ATTRIBUTE_INVOICE_DATE;
		}
		else if(attributeName.equals("voucherAge")) {
			return ATTRIBUTE_VOUCHER_AGE;
		}
		else if(attributeName.equals("poTerms")) {
			return ATTRIBUTE_PO_TERMS;
		}
		else if(attributeName.equals("supplierTerms")) {
			return ATTRIBUTE_SUPPLIER_TERMS;
		}
		else if(attributeName.equals("netInvoiceAmount")) {
			return ATTRIBUTE_NET_INVOICE_AMOUNT;
		}
		else if(attributeName.equals("availableReceipts")) {
			return ATTRIBUTE_AVAILABLE_RECEIPTS;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if(attributeName.equals("apNote")) {
			return ATTRIBUTE_AP_NOTE;
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
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("branchPlant")) {
			return ATTRIBUTE_BRANCH_PLANT;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("poHubName")) {
			return ATTRIBUTE_PO_HUB_NAME;
		}
		else if(attributeName.equals("poInventoryGroupName")) {
			return ATTRIBUTE_PO_INVENTORY_GROUP_NAME;
		}
		else if(attributeName.equals("poOperatingEntityName")) {
			return ATTRIBUTE_PO_OPERATING_ENTITY_NAME;
		}
		else if(attributeName.equals("supplierOpsEntityId")) {
			return ATTRIBUTE_SUPPLIER_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("buyerId")) {
			return ATTRIBUTE_BUYER_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, VoucherReportViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(VoucherReportViewBean voucherReportViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("voucherId", "SearchCriterion.EQUALS",
			"" + voucherReportViewBean.getVoucherId());

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


	public int delete(VoucherReportViewBean voucherReportViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("voucherId", "SearchCriterion.EQUALS",
			"" + voucherReportViewBean.getVoucherId());

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
	public int insert(VoucherReportViewBean voucherReportViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(voucherReportViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(VoucherReportViewBean voucherReportViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_VOUCHER_ID + "," +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_AP_USER_ID + "," +
			ATTRIBUTE_BUYER_NAME + "," +
			ATTRIBUTE_BUYER_EMAIL + "," +
			ATTRIBUTE_BUYER_PHONE + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_SUPPLIER_INVOICE_ID + "," +
			ATTRIBUTE_INVOICE_DATE + "," +
			ATTRIBUTE_VOUCHER_AGE + "," +
			ATTRIBUTE_PO_TERMS + "," +
			ATTRIBUTE_SUPPLIER_TERMS + "," +
			ATTRIBUTE_NET_INVOICE_AMOUNT + "," +
			ATTRIBUTE_AVAILABLE_RECEIPTS + "," +
			ATTRIBUTE_STATUS + "," +
			ATTRIBUTE_AP_NOTE + "," +
			ATTRIBUTE_APPROVER + "," +
			ATTRIBUTE_APPROVED_DATE + "," +
			ATTRIBUTE_QC_USER + "," +
			ATTRIBUTE_QC_DATE + "," +
			ATTRIBUTE_DATE_VOUCHER_IMPORTED + "," +
			ATTRIBUTE_AP_USER_NAME + "," +
			ATTRIBUTE_AP_APPROVER_NAME + "," +
			ATTRIBUTE_AP_QC_USER_NAME + ")" +
 values (
			StringHandler.nullIfZero(voucherReportViewBean.getVoucherId()) + "," +
			StringHandler.nullIfZero(voucherReportViewBean.getRadianPo()) + "," +
			StringHandler.nullIfZero(voucherReportViewBean.getApUserId()) + "," +
			SqlHandler.delimitString(voucherReportViewBean.getBuyerName()) + "," +
			SqlHandler.delimitString(voucherReportViewBean.getBuyerEmail()) + "," +
			SqlHandler.delimitString(voucherReportViewBean.getBuyerPhone()) + "," +
			SqlHandler.delimitString(voucherReportViewBean.getSupplierName()) + "," +
			SqlHandler.delimitString(voucherReportViewBean.getSupplierInvoiceId()) + "," +
			DateHandler.getOracleToDateFunction(voucherReportViewBean.getInvoiceDate()) + "," +
			StringHandler.nullIfZero(voucherReportViewBean.getVoucherAge()) + "," +
			SqlHandler.delimitString(voucherReportViewBean.getPoTerms()) + "," +
			SqlHandler.delimitString(voucherReportViewBean.getSupplierTerms()) + "," +
			StringHandler.nullIfZero(voucherReportViewBean.getNetInvoiceAmount()) + "," +
			SqlHandler.delimitString(voucherReportViewBean.getAvailableReceipts()) + "," +
			SqlHandler.delimitString(voucherReportViewBean.getStatus()) + "," +
			SqlHandler.delimitString(voucherReportViewBean.getApNote()) + "," +
			StringHandler.nullIfZero(voucherReportViewBean.getApprover()) + "," +
			DateHandler.getOracleToDateFunction(voucherReportViewBean.getApprovedDate()) + "," +
			StringHandler.nullIfZero(voucherReportViewBean.getQcUser()) + "," +
			DateHandler.getOracleToDateFunction(voucherReportViewBean.getQcDate()) + "," +
			DateHandler.getOracleToDateFunction(voucherReportViewBean.getDateVoucherImported()) + "," +
			SqlHandler.delimitString(voucherReportViewBean.getApUserName()) + "," +
			SqlHandler.delimitString(voucherReportViewBean.getApApproverName()) + "," +
			SqlHandler.delimitString(voucherReportViewBean.getApQcUserName()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(VoucherReportViewBean voucherReportViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(voucherReportViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(VoucherReportViewBean voucherReportViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_VOUCHER_ID + "=" + 
				StringHandler.nullIfZero(voucherReportViewBean.getVoucherId()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(voucherReportViewBean.getRadianPo()) + "," +
			ATTRIBUTE_AP_USER_ID + "=" + 
				StringHandler.nullIfZero(voucherReportViewBean.getApUserId()) + "," +
			ATTRIBUTE_BUYER_NAME + "=" + 
				SqlHandler.delimitString(voucherReportViewBean.getBuyerName()) + "," +
			ATTRIBUTE_BUYER_EMAIL + "=" + 
				SqlHandler.delimitString(voucherReportViewBean.getBuyerEmail()) + "," +
			ATTRIBUTE_BUYER_PHONE + "=" + 
				SqlHandler.delimitString(voucherReportViewBean.getBuyerPhone()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(voucherReportViewBean.getSupplierName()) + "," +
			ATTRIBUTE_SUPPLIER_INVOICE_ID + "=" + 
				SqlHandler.delimitString(voucherReportViewBean.getSupplierInvoiceId()) + "," +
			ATTRIBUTE_INVOICE_DATE + "=" + 
				DateHandler.getOracleToDateFunction(voucherReportViewBean.getInvoiceDate()) + "," +
			ATTRIBUTE_VOUCHER_AGE + "=" + 
				StringHandler.nullIfZero(voucherReportViewBean.getVoucherAge()) + "," +
			ATTRIBUTE_PO_TERMS + "=" + 
				SqlHandler.delimitString(voucherReportViewBean.getPoTerms()) + "," +
			ATTRIBUTE_SUPPLIER_TERMS + "=" + 
				SqlHandler.delimitString(voucherReportViewBean.getSupplierTerms()) + "," +
			ATTRIBUTE_NET_INVOICE_AMOUNT + "=" + 
				StringHandler.nullIfZero(voucherReportViewBean.getNetInvoiceAmount()) + "," +
			ATTRIBUTE_AVAILABLE_RECEIPTS + "=" + 
				SqlHandler.delimitString(voucherReportViewBean.getAvailableReceipts()) + "," +
			ATTRIBUTE_STATUS + "=" + 
				SqlHandler.delimitString(voucherReportViewBean.getStatus()) + "," +
			ATTRIBUTE_AP_NOTE + "=" + 
				SqlHandler.delimitString(voucherReportViewBean.getApNote()) + "," +
			ATTRIBUTE_APPROVER + "=" + 
				StringHandler.nullIfZero(voucherReportViewBean.getApprover()) + "," +
			ATTRIBUTE_APPROVED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(voucherReportViewBean.getApprovedDate()) + "," +
			ATTRIBUTE_QC_USER + "=" + 
				StringHandler.nullIfZero(voucherReportViewBean.getQcUser()) + "," +
			ATTRIBUTE_QC_DATE + "=" + 
				DateHandler.getOracleToDateFunction(voucherReportViewBean.getQcDate()) + "," +
			ATTRIBUTE_DATE_VOUCHER_IMPORTED + "=" + 
				DateHandler.getOracleToDateFunction(voucherReportViewBean.getDateVoucherImported()) + "," +
			ATTRIBUTE_AP_USER_NAME + "=" + 
				SqlHandler.delimitString(voucherReportViewBean.getApUserName()) + "," +
			ATTRIBUTE_AP_APPROVER_NAME + "=" + 
				SqlHandler.delimitString(voucherReportViewBean.getApApproverName()) + "," +
			ATTRIBUTE_AP_QC_USER_NAME + "=" + 
				SqlHandler.delimitString(voucherReportViewBean.getApQcUserName()) + " " +
			"where " + ATTRIBUTE_VOUCHER_ID + "=" +
				StringHandler.nullIfZero(voucherReportViewBean.getVoucherId());

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
			c = select(criteria, null, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
        
	public Collection select(SearchCriteria criteria,
	 SortCriteria sortCriteria) throws BaseException {

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
        
	public Collection select(SearchCriteria criteria,  SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection voucherReportViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

	    if (sortCriteria != null) {
	   	   query += getOrderByClause(sortCriteria);
	    }
       
	    if (log.isDebugEnabled()) {
				log.debug(query);
	    }    
	        
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			VoucherReportViewBean voucherReportViewBean = new VoucherReportViewBean();
			load(dataSetRow, voucherReportViewBean);
			voucherReportViewBeanColl.add(voucherReportViewBean);
		}

		return voucherReportViewBeanColl;
	}
}