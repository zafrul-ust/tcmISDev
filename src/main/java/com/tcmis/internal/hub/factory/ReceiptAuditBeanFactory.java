package com.tcmis.internal.hub.factory;


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
import com.tcmis.internal.hub.beans.ReceiptAuditBean;


/******************************************************************************
 * CLASSNAME: ReceiptAuditBeanFactory <br>
 * @version: 1.0, Oct 9, 2006 <br>
 *****************************************************************************/


public class ReceiptAuditBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_BIN = "BIN";
	public String ATTRIBUTE_DATE_OF_RECEIPT = "DATE_OF_RECEIPT";
	public String ATTRIBUTE_TRANSACTION_DATE = "TRANSACTION_DATE";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_DATE_OF_MANUFACTURE = "DATE_OF_MANUFACTURE";
	public String ATTRIBUTE_QUANTITY_RECEIVED = "QUANTITY_RECEIVED";
	public String ATTRIBUTE_DOC_NUM = "DOC_NUM";
	public String ATTRIBUTE_DOC_TYPE = "DOC_TYPE";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_LOT_STATUS = "LOT_STATUS";
	public String ATTRIBUTE_TRANSFER_REQUEST_ID = "TRANSFER_REQUEST_ID";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_RECEIVER_ID = "RECEIVER_ID";
	public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
	public String ATTRIBUTE_DOC_NUM_LINE = "DOC_NUM_LINE";
	public String ATTRIBUTE_APPROVED = "APPROVED";
	public String ATTRIBUTE_QC_USER_ID = "QC_USER_ID";
	public String ATTRIBUTE_USERNAME = "USERNAME";
	public String ATTRIBUTE_OSUSER = "OSUSER";
	public String ATTRIBUTE_SERVER = "SERVER";
	public String ATTRIBUTE_MACHINE = "MACHINE";
	public String ATTRIBUTE_TERMINAL = "TERMINAL";
	public String ATTRIBUTE_PROGRAM = "PROGRAM";
	public String ATTRIBUTE_TIME_STAMP = "TIME_STAMP";
	public String ATTRIBUTE_QC_DATE = "QC_DATE";
	public String ATTRIBUTE_RECEIPT_GROUP = "RECEIPT_GROUP";
	public String ATTRIBUTE_USAGE_FACTOR = "USAGE_FACTOR";
	public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
	public String ATTRIBUTE_ACCOUNT_NUMBER2 = "ACCOUNT_NUMBER2";
	public String ATTRIBUTE_ACCOUNT_SYS_ID = "ACCOUNT_SYS_ID";
	public String ATTRIBUTE_CERTIFICATION_DATE = "CERTIFICATION_DATE";
	public String ATTRIBUTE_CERTIFIED_BY = "CERTIFIED_BY";
	public String ATTRIBUTE_CHARGE_TYPE = "CHARGE_TYPE";
	public String ATTRIBUTE_COST_FACTOR = "COST_FACTOR";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_CUSTOMER_RECEIPT_ID = "CUSTOMER_RECEIPT_ID";
	public String ATTRIBUTE_DATE_OF_SHIPMENT = "DATE_OF_SHIPMENT";
	public String ATTRIBUTE_DELIVERY_TICKET = "DELIVERY_TICKET";
	public String ATTRIBUTE_DOC_PROCESS_DATE = "DOC_PROCESS_DATE";
	public String ATTRIBUTE_ESTABLISHED_UNIT_PRICE = "ESTABLISHED_UNIT_PRICE";
	public String ATTRIBUTE_EXCESS = "EXCESS";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_FACILITY_RESTRICTION = "FACILITY_RESTRICTION";
	public String ATTRIBUTE_INSERT_DATE = "INSERT_DATE";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_ITEM_CONVERSION_ISSUE_ID = "ITEM_CONVERSION_ISSUE_ID";
	public String ATTRIBUTE_LAST_LABEL_PRINT_DATE = "LAST_LABEL_PRINT_DATE";
	public String ATTRIBUTE_LAST_MODIFIED_BY = "LAST_MODIFIED_BY";
	public String ATTRIBUTE_LOT_STATUS_ROOT_CAUSE = "LOT_STATUS_ROOT_CAUSE";
	public String ATTRIBUTE_LOT_STATUS_ROOT_CAUSE_NOTES = "LOT_STATUS_ROOT_CAUSE_NOTES";
	public String ATTRIBUTE_NOTES = "NOTES";
	public String ATTRIBUTE_ORIGINAL_RECEIPT_ID = "ORIGINAL_RECEIPT_ID";
	public String ATTRIBUTE_OWNER_COMPANY_ID = "OWNER_COMPANY_ID";
	public String ATTRIBUTE_PICKABLE = "PICKABLE";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_RECERT_NUMBER = "RECERT_NUMBER";
	public String ATTRIBUTE_REPORTING_TRANSACTION_DATE = "REPORTING_TRANSACTION_DATE";
	public String ATTRIBUTE_RESPONSIBLE_COMPANY_ID = "RESPONSIBLE_COMPANY_ID";
	public String ATTRIBUTE_RETURN_LINE_ITEM = "RETURN_LINE_ITEM";
	public String ATTRIBUTE_RETURN_PR_NUMBER = "RETURN_PR_NUMBER";
	public String ATTRIBUTE_RETURN_RECEIPT_ID = "RETURN_RECEIPT_ID";
	public String ATTRIBUTE_TOTAL_QTY_VIRTUAL_RMA = "TOTAL_QTY_VIRTUAL_RMA";
	public String ATTRIBUTE_TOTAL_QUANTITY_ISSUED = "TOTAL_QUANTITY_ISSUED";
	public String ATTRIBUTE_TOTAL_QUANTITY_RETURNED = "TOTAL_QUANTITY_RETURNED";
	public String ATTRIBUTE_TRANSFER_RECEIPT_ID = "TRANSFER_RECEIPT_ID";
	public String ATTRIBUTE_VENDOR_SHIP_DATE = "VENDOR_SHIP_DATE";
	public String ATTRIBUTE_WORK_AREA_RESTRICTION = "WORK_AREA_RESTRICTION";
        public String ATTRIBUTE_LAST_DATE_IN_BIN = "LAST_DATE_IN_BIN";
	//table name
	public String TABLE = "RECEIPT_AUDIT";


	//constructor
	public ReceiptAuditBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if(attributeName.equals("bin")) {
			return ATTRIBUTE_BIN;
		}
		else if(attributeName.equals("dateOfReceipt")) {
			return ATTRIBUTE_DATE_OF_RECEIPT;
		}
		else if(attributeName.equals("transactionDate")) {
			return ATTRIBUTE_TRANSACTION_DATE;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("dateOfManufacture")) {
			return ATTRIBUTE_DATE_OF_MANUFACTURE;
		}
		else if(attributeName.equals("quantityReceived")) {
			return ATTRIBUTE_QUANTITY_RECEIVED;
		}
		else if(attributeName.equals("docNum")) {
			return ATTRIBUTE_DOC_NUM;
		}
		else if(attributeName.equals("docType")) {
			return ATTRIBUTE_DOC_TYPE;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("lotStatus")) {
			return ATTRIBUTE_LOT_STATUS;
		}
		else if(attributeName.equals("transferRequestId")) {
			return ATTRIBUTE_TRANSFER_REQUEST_ID;
		}
		else if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("receiverId")) {
			return ATTRIBUTE_RECEIVER_ID;
		}
		else if(attributeName.equals("expireDate")) {
			return ATTRIBUTE_EXPIRE_DATE;
		}
		else if(attributeName.equals("docNumLine")) {
			return ATTRIBUTE_DOC_NUM_LINE;
		}
		else if(attributeName.equals("approved")) {
			return ATTRIBUTE_APPROVED;
		}
		else if(attributeName.equals("qcUserId")) {
			return ATTRIBUTE_QC_USER_ID;
		}
		else if(attributeName.equals("username")) {
			return ATTRIBUTE_USERNAME;
		}
		else if(attributeName.equals("osuser")) {
			return ATTRIBUTE_OSUSER;
		}
		else if(attributeName.equals("server")) {
			return ATTRIBUTE_SERVER;
		}
		else if(attributeName.equals("machine")) {
			return ATTRIBUTE_MACHINE;
		}
		else if(attributeName.equals("terminal")) {
			return ATTRIBUTE_TERMINAL;
		}
		else if(attributeName.equals("program")) {
			return ATTRIBUTE_PROGRAM;
		}
		else if(attributeName.equals("timeStamp")) {
			return ATTRIBUTE_TIME_STAMP;
		}
		else if(attributeName.equals("qcDate")) {
			return ATTRIBUTE_QC_DATE;
		}
		else if(attributeName.equals("receiptGroup")) {
			return ATTRIBUTE_RECEIPT_GROUP;
		}
		else if(attributeName.equals("usageFactor")) {
			return ATTRIBUTE_USAGE_FACTOR;
		}
		else if(attributeName.equals("accountNumber")) {
			return ATTRIBUTE_ACCOUNT_NUMBER;
		}
		else if(attributeName.equals("accountNumber2")) {
			return ATTRIBUTE_ACCOUNT_NUMBER2;
		}
		else if(attributeName.equals("accountSysId")) {
			return ATTRIBUTE_ACCOUNT_SYS_ID;
		}
		else if(attributeName.equals("certificationDate")) {
			return ATTRIBUTE_CERTIFICATION_DATE;
		}
		else if(attributeName.equals("certifiedBy")) {
			return ATTRIBUTE_CERTIFIED_BY;
		}
		else if(attributeName.equals("chargeType")) {
			return ATTRIBUTE_CHARGE_TYPE;
		}
		else if(attributeName.equals("costFactor")) {
			return ATTRIBUTE_COST_FACTOR;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("customerReceiptId")) {
			return ATTRIBUTE_CUSTOMER_RECEIPT_ID;
		}
		else if(attributeName.equals("dateOfShipment")) {
			return ATTRIBUTE_DATE_OF_SHIPMENT;
		}
		else if(attributeName.equals("deliveryTicket")) {
			return ATTRIBUTE_DELIVERY_TICKET;
		}
		else if(attributeName.equals("docProcessDate")) {
			return ATTRIBUTE_DOC_PROCESS_DATE;
		}
		else if(attributeName.equals("establishedUnitPrice")) {
			return ATTRIBUTE_ESTABLISHED_UNIT_PRICE;
		}
		else if(attributeName.equals("excess")) {
			return ATTRIBUTE_EXCESS;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("facilityRestriction")) {
			return ATTRIBUTE_FACILITY_RESTRICTION;
		}
		else if(attributeName.equals("insertDate")) {
			return ATTRIBUTE_INSERT_DATE;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("itemConversionIssueId")) {
			return ATTRIBUTE_ITEM_CONVERSION_ISSUE_ID;
		}
		else if(attributeName.equals("lastLabelPrintDate")) {
			return ATTRIBUTE_LAST_LABEL_PRINT_DATE;
		}
		else if(attributeName.equals("lastModifiedBy")) {
			return ATTRIBUTE_LAST_MODIFIED_BY;
		}
		else if(attributeName.equals("lotStatusRootCause")) {
			return ATTRIBUTE_LOT_STATUS_ROOT_CAUSE;
		}
		else if(attributeName.equals("lotStatusRootCauseNotes")) {
			return ATTRIBUTE_LOT_STATUS_ROOT_CAUSE_NOTES;
		}
		else if(attributeName.equals("notes")) {
			return ATTRIBUTE_NOTES;
		}
		else if(attributeName.equals("originalReceiptId")) {
			return ATTRIBUTE_ORIGINAL_RECEIPT_ID;
		}
		else if(attributeName.equals("ownerCompanyId")) {
			return ATTRIBUTE_OWNER_COMPANY_ID;
		}
		else if(attributeName.equals("pickable")) {
			return ATTRIBUTE_PICKABLE;
		}
		else if(attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if(attributeName.equals("recertNumber")) {
			return ATTRIBUTE_RECERT_NUMBER;
		}
		else if(attributeName.equals("reportingTransactionDate")) {
			return ATTRIBUTE_REPORTING_TRANSACTION_DATE;
		}
		else if(attributeName.equals("responsibleCompanyId")) {
			return ATTRIBUTE_RESPONSIBLE_COMPANY_ID;
		}
		else if(attributeName.equals("returnLineItem")) {
			return ATTRIBUTE_RETURN_LINE_ITEM;
		}
		else if(attributeName.equals("returnPrNumber")) {
			return ATTRIBUTE_RETURN_PR_NUMBER;
		}
		else if(attributeName.equals("returnReceiptId")) {
			return ATTRIBUTE_RETURN_RECEIPT_ID;
		}
		else if(attributeName.equals("totalQtyVirtualRma")) {
			return ATTRIBUTE_TOTAL_QTY_VIRTUAL_RMA;
		}
		else if(attributeName.equals("totalQuantityIssued")) {
			return ATTRIBUTE_TOTAL_QUANTITY_ISSUED;
		}
		else if(attributeName.equals("totalQuantityReturned")) {
			return ATTRIBUTE_TOTAL_QUANTITY_RETURNED;
		}
		else if(attributeName.equals("transferReceiptId")) {
			return ATTRIBUTE_TRANSFER_RECEIPT_ID;
		}
		else if(attributeName.equals("vendorShipDate")) {
			return ATTRIBUTE_VENDOR_SHIP_DATE;
		}
		else if(attributeName.equals("workAreaRestriction")) {
			return ATTRIBUTE_WORK_AREA_RESTRICTION;
		}
                else if (attributeName.equals("lastDateInBin")) {
                        return ATTRIBUTE_LAST_DATE_IN_BIN;
                }
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ReceiptAuditBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ReceiptAuditBean receiptAuditBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("itemId", "SearchCriterion.EQUALS",
			"" + receiptAuditBean.getItemId());

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


	public int delete(ReceiptAuditBean receiptAuditBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("itemId", "SearchCriterion.EQUALS",
			"" + receiptAuditBean.getItemId());

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
	public int insert(ReceiptAuditBean receiptAuditBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(receiptAuditBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ReceiptAuditBean receiptAuditBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_MFG_LOT + "," +
			ATTRIBUTE_BIN + "," +
			ATTRIBUTE_DATE_OF_RECEIPT + "," +
			ATTRIBUTE_TRANSACTION_DATE + "," +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_PO_LINE + "," +
			ATTRIBUTE_DATE_OF_MANUFACTURE + "," +
			ATTRIBUTE_QUANTITY_RECEIVED + "," +
			ATTRIBUTE_DOC_NUM + "," +
			ATTRIBUTE_DOC_TYPE + "," +
			ATTRIBUTE_RECEIPT_ID + "," +
			ATTRIBUTE_LOT_STATUS + "," +
			ATTRIBUTE_TRANSFER_REQUEST_ID + "," +
			ATTRIBUTE_HUB + "," +
			ATTRIBUTE_RECEIVER_ID + "," +
			ATTRIBUTE_EXPIRE_DATE + "," +
			ATTRIBUTE_DOC_NUM_LINE + "," +
			ATTRIBUTE_APPROVED + "," +
			ATTRIBUTE_QC_USER_ID + "," +
			ATTRIBUTE_USERNAME + "," +
			ATTRIBUTE_OSUSER + "," +
			ATTRIBUTE_SERVER + "," +
			ATTRIBUTE_MACHINE + "," +
			ATTRIBUTE_TERMINAL + "," +
			ATTRIBUTE_PROGRAM + "," +
			ATTRIBUTE_TIME_STAMP + "," +
			ATTRIBUTE_QC_DATE + "," +
			ATTRIBUTE_RECEIPT_GROUP + "," +
			ATTRIBUTE_USAGE_FACTOR + "," +
			ATTRIBUTE_ACCOUNT_NUMBER + "," +
			ATTRIBUTE_ACCOUNT_NUMBER2 + "," +
			ATTRIBUTE_ACCOUNT_SYS_ID + "," +
			ATTRIBUTE_CERTIFICATION_DATE + "," +
			ATTRIBUTE_CERTIFIED_BY + "," +
			ATTRIBUTE_CHARGE_TYPE + "," +
			ATTRIBUTE_COST_FACTOR + "," +
			ATTRIBUTE_CURRENCY_ID + "," +
			ATTRIBUTE_CUSTOMER_RECEIPT_ID + "," +
			ATTRIBUTE_DATE_OF_SHIPMENT + "," +
			ATTRIBUTE_DELIVERY_TICKET + "," +
			ATTRIBUTE_DOC_PROCESS_DATE + "," +
			ATTRIBUTE_ESTABLISHED_UNIT_PRICE + "," +
			ATTRIBUTE_EXCESS + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_FACILITY_RESTRICTION + "," +
			ATTRIBUTE_INSERT_DATE + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_ITEM_CONVERSION_ISSUE_ID + "," +
			ATTRIBUTE_LAST_LABEL_PRINT_DATE + "," +
			ATTRIBUTE_LAST_MODIFIED_BY + "," +
			ATTRIBUTE_LOT_STATUS_ROOT_CAUSE + "," +
			ATTRIBUTE_LOT_STATUS_ROOT_CAUSE_NOTES + "," +
			ATTRIBUTE_NOTES + "," +
			ATTRIBUTE_ORIGINAL_RECEIPT_ID + "," +
			ATTRIBUTE_OWNER_COMPANY_ID + "," +
			ATTRIBUTE_PICKABLE + "," +
			ATTRIBUTE_PO_NUMBER + "," +
			ATTRIBUTE_RECERT_NUMBER + "," +
			ATTRIBUTE_REPORTING_TRANSACTION_DATE + "," +
			ATTRIBUTE_RESPONSIBLE_COMPANY_ID + "," +
			ATTRIBUTE_RETURN_LINE_ITEM + "," +
			ATTRIBUTE_RETURN_PR_NUMBER + "," +
			ATTRIBUTE_RETURN_RECEIPT_ID + "," +
			ATTRIBUTE_TOTAL_QTY_VIRTUAL_RMA + "," +
			ATTRIBUTE_TOTAL_QUANTITY_ISSUED + "," +
			ATTRIBUTE_TOTAL_QUANTITY_RETURNED + "," +
			ATTRIBUTE_TRANSFER_RECEIPT_ID + "," +
			ATTRIBUTE_VENDOR_SHIP_DATE + "," +
			ATTRIBUTE_WORK_AREA_RESTRICTION + ")" +
 values (
			StringHandler.nullIfZero(receiptAuditBean.getItemId()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getMfgLot()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getBin()) + "," +
			DateHandler.getOracleToDateFunction(receiptAuditBean.getDateOfReceipt()) + "," +
			DateHandler.getOracleToDateFunction(receiptAuditBean.getTransactionDate()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getRadianPo()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getPoLine()) + "," +
			DateHandler.getOracleToDateFunction(receiptAuditBean.getDateOfManufacture()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getQuantityReceived()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getDocNum()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getDocType()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getReceiptId()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getLotStatus()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getTransferRequestId()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getHub()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getReceiverId()) + "," +
			DateHandler.getOracleToDateFunction(receiptAuditBean.getExpireDate()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getDocNumLine()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getApproved()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getQcUserId()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getUsername()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getOsuser()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getServer()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getMachine()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getTerminal()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getProgram()) + "," +
			DateHandler.getOracleToDateFunction(receiptAuditBean.getTimeStamp()) + "," +
			DateHandler.getOracleToDateFunction(receiptAuditBean.getQcDate()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getReceiptGroup()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getUsageFactor()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getAccountNumber()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getAccountNumber2()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getAccountSysId()) + "," +
			DateHandler.getOracleToDateFunction(receiptAuditBean.getCertificationDate()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getCertifiedBy()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getChargeType()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getCostFactor()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getCurrencyId()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getCustomerReceiptId()) + "," +
			DateHandler.getOracleToDateFunction(receiptAuditBean.getDateOfShipment()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getDeliveryTicket()) + "," +
			DateHandler.getOracleToDateFunction(receiptAuditBean.getDocProcessDate()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getEstablishedUnitPrice()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getExcess()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getFacilityId()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getFacilityRestriction()) + "," +
			DateHandler.getOracleToDateFunction(receiptAuditBean.getInsertDate()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getInventoryGroup()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getItemConversionIssueId()) + "," +
			DateHandler.getOracleToDateFunction(receiptAuditBean.getLastLabelPrintDate()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getLastModifiedBy()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getLotStatusRootCause()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getLotStatusRootCauseNotes()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getNotes()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getOriginalReceiptId()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getOwnerCompanyId()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getPickable()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getPoNumber()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getRecertNumber()) + "," +
			DateHandler.getOracleToDateFunction(receiptAuditBean.getReportingTransactionDate()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getResponsibleCompanyId()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getReturnLineItem()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getReturnPrNumber()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getReturnReceiptId()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getTotalQtyVirtualRma()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getTotalQuantityIssued()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getTotalQuantityReturned()) + "," +
			StringHandler.nullIfZero(receiptAuditBean.getTransferReceiptId()) + "," +
			DateHandler.getOracleToDateFunction(receiptAuditBean.getVendorShipDate()) + "," +
			SqlHandler.delimitString(receiptAuditBean.getWorkAreaRestriction()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ReceiptAuditBean receiptAuditBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(receiptAuditBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ReceiptAuditBean receiptAuditBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getItemId()) + "," +
			ATTRIBUTE_MFG_LOT + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getMfgLot()) + "," +
			ATTRIBUTE_BIN + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getBin()) + "," +
			ATTRIBUTE_DATE_OF_RECEIPT + "=" + 
				DateHandler.getOracleToDateFunction(receiptAuditBean.getDateOfReceipt()) + "," +
			ATTRIBUTE_TRANSACTION_DATE + "=" + 
				DateHandler.getOracleToDateFunction(receiptAuditBean.getTransactionDate()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getRadianPo()) + "," +
			ATTRIBUTE_PO_LINE + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getPoLine()) + "," +
			ATTRIBUTE_DATE_OF_MANUFACTURE + "=" + 
				DateHandler.getOracleToDateFunction(receiptAuditBean.getDateOfManufacture()) + "," +
			ATTRIBUTE_QUANTITY_RECEIVED + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getQuantityReceived()) + "," +
			ATTRIBUTE_DOC_NUM + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getDocNum()) + "," +
			ATTRIBUTE_DOC_TYPE + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getDocType()) + "," +
			ATTRIBUTE_RECEIPT_ID + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getReceiptId()) + "," +
			ATTRIBUTE_LOT_STATUS + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getLotStatus()) + "," +
			ATTRIBUTE_TRANSFER_REQUEST_ID + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getTransferRequestId()) + "," +
			ATTRIBUTE_HUB + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getHub()) + "," +
			ATTRIBUTE_RECEIVER_ID + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getReceiverId()) + "," +
			ATTRIBUTE_EXPIRE_DATE + "=" + 
				DateHandler.getOracleToDateFunction(receiptAuditBean.getExpireDate()) + "," +
			ATTRIBUTE_DOC_NUM_LINE + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getDocNumLine()) + "," +
			ATTRIBUTE_APPROVED + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getApproved()) + "," +
			ATTRIBUTE_QC_USER_ID + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getQcUserId()) + "," +
			ATTRIBUTE_USERNAME + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getUsername()) + "," +
			ATTRIBUTE_OSUSER + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getOsuser()) + "," +
			ATTRIBUTE_SERVER + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getServer()) + "," +
			ATTRIBUTE_MACHINE + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getMachine()) + "," +
			ATTRIBUTE_TERMINAL + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getTerminal()) + "," +
			ATTRIBUTE_PROGRAM + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getProgram()) + "," +
			ATTRIBUTE_TIME_STAMP + "=" + 
				DateHandler.getOracleToDateFunction(receiptAuditBean.getTimeStamp()) + "," +
			ATTRIBUTE_QC_DATE + "=" + 
				DateHandler.getOracleToDateFunction(receiptAuditBean.getQcDate()) + "," +
			ATTRIBUTE_RECEIPT_GROUP + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getReceiptGroup()) + "," +
			ATTRIBUTE_USAGE_FACTOR + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getUsageFactor()) + "," +
			ATTRIBUTE_ACCOUNT_NUMBER + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getAccountNumber()) + "," +
			ATTRIBUTE_ACCOUNT_NUMBER2 + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getAccountNumber2()) + "," +
			ATTRIBUTE_ACCOUNT_SYS_ID + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getAccountSysId()) + "," +
			ATTRIBUTE_CERTIFICATION_DATE + "=" + 
				DateHandler.getOracleToDateFunction(receiptAuditBean.getCertificationDate()) + "," +
			ATTRIBUTE_CERTIFIED_BY + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getCertifiedBy()) + "," +
			ATTRIBUTE_CHARGE_TYPE + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getChargeType()) + "," +
			ATTRIBUTE_COST_FACTOR + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getCostFactor()) + "," +
			ATTRIBUTE_CURRENCY_ID + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getCurrencyId()) + "," +
			ATTRIBUTE_CUSTOMER_RECEIPT_ID + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getCustomerReceiptId()) + "," +
			ATTRIBUTE_DATE_OF_SHIPMENT + "=" + 
				DateHandler.getOracleToDateFunction(receiptAuditBean.getDateOfShipment()) + "," +
			ATTRIBUTE_DELIVERY_TICKET + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getDeliveryTicket()) + "," +
			ATTRIBUTE_DOC_PROCESS_DATE + "=" + 
				DateHandler.getOracleToDateFunction(receiptAuditBean.getDocProcessDate()) + "," +
			ATTRIBUTE_ESTABLISHED_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getEstablishedUnitPrice()) + "," +
			ATTRIBUTE_EXCESS + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getExcess()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getFacilityId()) + "," +
			ATTRIBUTE_FACILITY_RESTRICTION + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getFacilityRestriction()) + "," +
			ATTRIBUTE_INSERT_DATE + "=" + 
				DateHandler.getOracleToDateFunction(receiptAuditBean.getInsertDate()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getInventoryGroup()) + "," +
			ATTRIBUTE_ITEM_CONVERSION_ISSUE_ID + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getItemConversionIssueId()) + "," +
			ATTRIBUTE_LAST_LABEL_PRINT_DATE + "=" + 
				DateHandler.getOracleToDateFunction(receiptAuditBean.getLastLabelPrintDate()) + "," +
			ATTRIBUTE_LAST_MODIFIED_BY + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getLastModifiedBy()) + "," +
			ATTRIBUTE_LOT_STATUS_ROOT_CAUSE + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getLotStatusRootCause()) + "," +
			ATTRIBUTE_LOT_STATUS_ROOT_CAUSE_NOTES + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getLotStatusRootCauseNotes()) + "," +
			ATTRIBUTE_NOTES + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getNotes()) + "," +
			ATTRIBUTE_ORIGINAL_RECEIPT_ID + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getOriginalReceiptId()) + "," +
			ATTRIBUTE_OWNER_COMPANY_ID + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getOwnerCompanyId()) + "," +
			ATTRIBUTE_PICKABLE + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getPickable()) + "," +
			ATTRIBUTE_PO_NUMBER + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getPoNumber()) + "," +
			ATTRIBUTE_RECERT_NUMBER + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getRecertNumber()) + "," +
			ATTRIBUTE_REPORTING_TRANSACTION_DATE + "=" + 
				DateHandler.getOracleToDateFunction(receiptAuditBean.getReportingTransactionDate()) + "," +
			ATTRIBUTE_RESPONSIBLE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getResponsibleCompanyId()) + "," +
			ATTRIBUTE_RETURN_LINE_ITEM + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getReturnLineItem()) + "," +
			ATTRIBUTE_RETURN_PR_NUMBER + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getReturnPrNumber()) + "," +
			ATTRIBUTE_RETURN_RECEIPT_ID + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getReturnReceiptId()) + "," +
			ATTRIBUTE_TOTAL_QTY_VIRTUAL_RMA + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getTotalQtyVirtualRma()) + "," +
			ATTRIBUTE_TOTAL_QUANTITY_ISSUED + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getTotalQuantityIssued()) + "," +
			ATTRIBUTE_TOTAL_QUANTITY_RETURNED + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getTotalQuantityReturned()) + "," +
			ATTRIBUTE_TRANSFER_RECEIPT_ID + "=" + 
				StringHandler.nullIfZero(receiptAuditBean.getTransferReceiptId()) + "," +
			ATTRIBUTE_VENDOR_SHIP_DATE + "=" + 
				DateHandler.getOracleToDateFunction(receiptAuditBean.getVendorShipDate()) + "," +
			ATTRIBUTE_WORK_AREA_RESTRICTION + "=" + 
				SqlHandler.delimitString(receiptAuditBean.getWorkAreaRestriction()) + " " +
			"where " + ATTRIBUTE_ITEM_ID + "=" +
				StringHandler.nullIfZero(receiptAuditBean.getItemId());

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

		Collection receiptAuditBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ReceiptAuditBean receiptAuditBean = new ReceiptAuditBean();
			load(dataSetRow, receiptAuditBean);
			receiptAuditBeanColl.add(receiptAuditBean);
		}

		return receiptAuditBeanColl;
	}
        
	//select receipt bin history
	public Collection selectBinHistory(SearchCriteria criteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectBinHistory(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
        
	public Collection selectBinHistory(SearchCriteria criteria, Connection conn)
		throws BaseException {

		Collection receiptAuditBeanColl = new Vector();

		String query = "select " + ATTRIBUTE_BIN +"," + ATTRIBUTE_RECEIPT_ID + "," + 
                               "to_char(max(" + ATTRIBUTE_TIME_STAMP + "),'mm/dd/yyyy hh24:mi PM') " + ATTRIBUTE_LAST_DATE_IN_BIN +
                               " from " + TABLE + " " +
			       getWhereClause(criteria) + 
                               " group by " + ATTRIBUTE_BIN +"," + ATTRIBUTE_RECEIPT_ID + 
                               " order by max(" + ATTRIBUTE_TIME_STAMP + ") desc";

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ReceiptAuditBean receiptAuditBean = new ReceiptAuditBean();
			load(dataSetRow, receiptAuditBean);
			receiptAuditBeanColl.add(receiptAuditBean);
		}

		return receiptAuditBeanColl;
	}
        
}