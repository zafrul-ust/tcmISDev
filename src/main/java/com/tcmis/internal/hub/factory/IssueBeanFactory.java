package com.tcmis.internal.hub.factory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.IssueBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/******************************************************************************
 * CLASSNAME: IssueBeanFactory <br>
 * @version: 1.0, Sep 7, 2005 <br>
 *****************************************************************************/

public class IssueBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_ISSUE_ID = "ISSUE_ID";
 public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
 public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
 public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
 public String ATTRIBUTE_SOURCE_HUB = "SOURCE_HUB";
 public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
 public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
 public String ATTRIBUTE_DATE_PICKED = "DATE_PICKED";
 public String ATTRIBUTE_QUANTITY = "QUANTITY";
 public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
 public String ATTRIBUTE_SALES_ORDER = "SALES_ORDER";
 public String ATTRIBUTE_SO_LINE = "SO_LINE";
 public String ATTRIBUTE_DELIVERY_ID = "DELIVERY_ID";
 public String ATTRIBUTE_BATCH = "BATCH";
 public String ATTRIBUTE_AUTHCODE = "AUTHCODE";
 public String ATTRIBUTE_PNREF = "PNREF";
 public String ATTRIBUTE_DOC_NUM = "DOC_NUM";
 public String ATTRIBUTE_DOC_STATE = "DOC_STATE";
 public String ATTRIBUTE_ERROR_DETAIL = "ERROR_DETAIL";
 public String ATTRIBUTE_DOC_PROCESS_DATE = "DOC_PROCESS_DATE";
 public String ATTRIBUTE_PROGRAM_CODE = "PROGRAM_CODE";
 public String ATTRIBUTE_DOC_STATE_ID = "DOC_STATE_ID";
 public String ATTRIBUTE_UNUSED1 = "UNUSED1";
 public String ATTRIBUTE_UNUSED2 = "UNUSED2";
 public String ATTRIBUTE_UNUSED3 = "UNUSED3";
 public String ATTRIBUTE_UNUSED4 = "UNUSED4";
 public String ATTRIBUTE_CONFIRMED = "CONFIRMED";
 public String ATTRIBUTE_ISSUER_ID = "ISSUER_ID";
 public String ATTRIBUTE_TRANSFER_REQUEST_ID = "TRANSFER_REQUEST_ID";
 public String ATTRIBUTE_TRANSACTION_DATE = "TRANSACTION_DATE";
 public String ATTRIBUTE_QC_USER = "QC_USER";
 public String ATTRIBUTE_QC_DATE = "QC_DATE";
 public String ATTRIBUTE_SHIP_CONFIRM_DATE = "SHIP_CONFIRM_DATE";
 public String ATTRIBUTE_SHIP_CONFIRM_USER = "SHIP_CONFIRM_USER";
 public String ATTRIBUTE_PRICE_QC_USER = "PRICE_QC_USER";
 public String ATTRIBUTE_PRICE_QC_DATE = "PRICE_QC_DATE";
 public String ATTRIBUTE_PICKLIST_ID = "PICKLIST_ID";
 public String ATTRIBUTE_PICKLIST_PRINT_DATE = "PICKLIST_PRINT_DATE";
 public String ATTRIBUTE_PICKLIST_QUANTITY = "PICKLIST_QUANTITY";
 public String ATTRIBUTE_NEED_DATE = "NEED_DATE";
 public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
 public String ATTRIBUTE_DELIVERY_MAIL_ID = "DELIVERY_MAIL_ID";
 public String ATTRIBUTE_DATE_DELIVERY_MAIL_SENT = "DATE_DELIVERY_MAIL_SENT";
 public String ATTRIBUTE_DOC_TYPE = "DOC_TYPE";
 public String ATTRIBUTE_HUB_EXPEDITING_FEE = "HUB_EXPEDITING_FEE";
 public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
 public String ATTRIBUTE_HUB_EXPEDITING_FEE_CURRENCY_ID =
	"HUB_EXPEDITING_FEE_CURRENCY_ID";
 public String ATTRIBUTE_REPORTING_TRANSACTION_DATE =
	"REPORTING_TRANSACTION_DATE";
 public String ATTRIBUTE_HUB_EXPEDITING_FEE_REFERENCE = "HUB_EXPEDITING_FEE_REFERENCE";
 public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
 public String ATTRIBUTE_SHIP_FROM_LOCATION_ID = "SHIP_FROM_LOCATION_ID";
 public String ATTRIBUTE_NUMBER_OF_BOXES = "NUMBER_OF_BOXES";
 public String ATTRIBUTE_DATE_SHIPPED = "DATE_SHIPPED";
 public String ATTRIBUTE_LOCKED = "LOCKED";
 public String ATTRIBUTE_PACKING_GROUP_ID = "PACKING_GROUP_ID";
 public String ATTRIBUTE_SUPPLIER_SALES_ORDER_NO = "SUPPLIER_SALES_ORDER_NO";

 //table name
 public String TABLE = "ISSUE";

 //constructor
 public IssueBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("issueId")) {
	 return ATTRIBUTE_ISSUE_ID;
	}
	else if (attributeName.equals("companyId")) {
	 return ATTRIBUTE_COMPANY_ID;
	}
	else if (attributeName.equals("prNumber")) {
	 return ATTRIBUTE_PR_NUMBER;
	}
	else if (attributeName.equals("lineItem")) {
	 return ATTRIBUTE_LINE_ITEM;
	}
	else if (attributeName.equals("sourceHub")) {
	 return ATTRIBUTE_SOURCE_HUB;
	}
	else if (attributeName.equals("receiptId")) {
	 return ATTRIBUTE_RECEIPT_ID;
	}
	else if (attributeName.equals("itemId")) {
	 return ATTRIBUTE_ITEM_ID;
	}
	else if (attributeName.equals("datePicked")) {
	 return ATTRIBUTE_DATE_PICKED;
	}
	else if (attributeName.equals("quantity")) {
	 return ATTRIBUTE_QUANTITY;
	}
	else if (attributeName.equals("dateDelivered")) {
	 return ATTRIBUTE_DATE_DELIVERED;
	}
	else if (attributeName.equals("salesOrder")) {
	 return ATTRIBUTE_SALES_ORDER;
	}
	else if (attributeName.equals("soLine")) {
	 return ATTRIBUTE_SO_LINE;
	}
	else if (attributeName.equals("deliveryId")) {
	 return ATTRIBUTE_DELIVERY_ID;
	}
	else if (attributeName.equals("batch")) {
	 return ATTRIBUTE_BATCH;
	}
	else if (attributeName.equals("authcode")) {
	 return ATTRIBUTE_AUTHCODE;
	}
	else if (attributeName.equals("pnref")) {
	 return ATTRIBUTE_PNREF;
	}
	else if (attributeName.equals("docNum")) {
	 return ATTRIBUTE_DOC_NUM;
	}
	else if (attributeName.equals("docState")) {
	 return ATTRIBUTE_DOC_STATE;
	}
	else if (attributeName.equals("errorDetail")) {
	 return ATTRIBUTE_ERROR_DETAIL;
	}
	else if (attributeName.equals("docProcessDate")) {
	 return ATTRIBUTE_DOC_PROCESS_DATE;
	}
	else if (attributeName.equals("programCode")) {
	 return ATTRIBUTE_PROGRAM_CODE;
	}
	else if (attributeName.equals("docStateId")) {
	 return ATTRIBUTE_DOC_STATE_ID;
	}
	else if (attributeName.equals("unused1")) {
	 return ATTRIBUTE_UNUSED1;
	}
	else if (attributeName.equals("unused2")) {
	 return ATTRIBUTE_UNUSED2;
	}
	else if (attributeName.equals("unused3")) {
	 return ATTRIBUTE_UNUSED3;
	}
	else if (attributeName.equals("unused4")) {
	 return ATTRIBUTE_UNUSED4;
	}
	else if (attributeName.equals("confirmed")) {
	 return ATTRIBUTE_CONFIRMED;
	}
	else if (attributeName.equals("issuerId")) {
	 return ATTRIBUTE_ISSUER_ID;
	}
	else if (attributeName.equals("transferRequestId")) {
	 return ATTRIBUTE_TRANSFER_REQUEST_ID;
	}
	else if (attributeName.equals("transactionDate")) {
	 return ATTRIBUTE_TRANSACTION_DATE;
	}
	else if (attributeName.equals("qcUser")) {
	 return ATTRIBUTE_QC_USER;
	}
	else if (attributeName.equals("qcDate")) {
	 return ATTRIBUTE_QC_DATE;
	}
	else if (attributeName.equals("shipConfirmDate")) {
	 return ATTRIBUTE_SHIP_CONFIRM_DATE;
	}
	else if (attributeName.equals("shipConfirmUser")) {
	 return ATTRIBUTE_SHIP_CONFIRM_USER;
	}
	else if (attributeName.equals("priceQcUser")) {
	 return ATTRIBUTE_PRICE_QC_USER;
	}
	else if (attributeName.equals("priceQcDate")) {
	 return ATTRIBUTE_PRICE_QC_DATE;
	}
	else if (attributeName.equals("picklistId")) {
	 return ATTRIBUTE_PICKLIST_ID;
	}
	else if (attributeName.equals("picklistPrintDate")) {
	 return ATTRIBUTE_PICKLIST_PRINT_DATE;
	}
	else if (attributeName.equals("picklistQuantity")) {
	 return ATTRIBUTE_PICKLIST_QUANTITY;
	}
	else if (attributeName.equals("needDate")) {
	 return ATTRIBUTE_NEED_DATE;
	}
	else if (attributeName.equals("inventoryGroup")) {
	 return ATTRIBUTE_INVENTORY_GROUP;
	}
	else if (attributeName.equals("deliveryMailId")) {
	 return ATTRIBUTE_DELIVERY_MAIL_ID;
	}
	else if (attributeName.equals("dateDeliveryMailSent")) {
	 return ATTRIBUTE_DATE_DELIVERY_MAIL_SENT;
	}
	else if (attributeName.equals("docType")) {
	 return ATTRIBUTE_DOC_TYPE;
	}
	else if (attributeName.equals("hubExpeditingFee")) {
	 return ATTRIBUTE_HUB_EXPEDITING_FEE;
	}
	else if (attributeName.equals("shipmentId")) {
	 return ATTRIBUTE_SHIPMENT_ID;
	}
	else if (attributeName.equals("hubExpeditingFeeCurrencyId")) {
	 return ATTRIBUTE_HUB_EXPEDITING_FEE_CURRENCY_ID;
	}
	else if (attributeName.equals("reportingTransactionDate")) {
	 return ATTRIBUTE_REPORTING_TRANSACTION_DATE;
	}
  else if(attributeName.equals("hubExpeditingFeeReference")) {
    return ATTRIBUTE_HUB_EXPEDITING_FEE_REFERENCE;
  }
  else if(attributeName.equals("supplier")) {
    return ATTRIBUTE_SUPPLIER;
  }
  else if(attributeName.equals("shipFromLocationId")) {
    return ATTRIBUTE_SHIP_FROM_LOCATION_ID;
  }
  else if(attributeName.equals("numberOfBoxes")) {
    return ATTRIBUTE_NUMBER_OF_BOXES;
  }
  else if(attributeName.equals("dateShipped")) {
    return ATTRIBUTE_DATE_SHIPPED;
  }
  else if(attributeName.equals("locked")) {
    return ATTRIBUTE_LOCKED;
  }
  else if(attributeName.equals("packingGroupId")) {
    return ATTRIBUTE_PACKING_GROUP_ID;
  }
  else if(attributeName.equals("supplierSalesOrderNo")) {
    return ATTRIBUTE_SUPPLIER_SALES_ORDER_NO;
  }
  else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, IssueBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	public int delete(IssueBean issueBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("issueId", "SearchCriterion.EQUALS",
		"" + issueBean.getIssueId());
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
	public int delete(IssueBean issueBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("issueId", "SearchCriterion.EQUALS",
		"" + issueBean.getIssueId());
	 return delete(criteria, conn);
	}
	*/

 public int delete(SearchCriteria criteria) throws BaseException {

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

 public int delete(SearchCriteria criteria,
	Connection conn) throws BaseException {

	String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

	return new SqlManager().update(conn, sqlQuery);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//insert
	public int insert(IssueBean issueBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = insert(issueBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int insert(IssueBean issueBean, Connection conn)
	 throws BaseException {
	 SqlManager sqlManager = new SqlManager();
	 String query  =
		"insert into " + TABLE + " (" +
		ATTRIBUTE_ISSUE_ID + "," +
		ATTRIBUTE_COMPANY_ID + "," +
		ATTRIBUTE_PR_NUMBER + "," +
		ATTRIBUTE_LINE_ITEM + "," +
		ATTRIBUTE_SOURCE_HUB + "," +
		ATTRIBUTE_RECEIPT_ID + "," +
		ATTRIBUTE_ITEM_ID + "," +
		ATTRIBUTE_DATE_PICKED + "," +
		ATTRIBUTE_QUANTITY + "," +
		ATTRIBUTE_DATE_DELIVERED + "," +
		ATTRIBUTE_SALES_ORDER + "," +
		ATTRIBUTE_SO_LINE + "," +
		ATTRIBUTE_DELIVERY_ID + "," +
		ATTRIBUTE_BATCH + "," +
		ATTRIBUTE_AUTHCODE + "," +
		ATTRIBUTE_PNREF + "," +
		ATTRIBUTE_DOC_NUM + "," +
		ATTRIBUTE_DOC_STATE + "," +
		ATTRIBUTE_ERROR_DETAIL + "," +
		ATTRIBUTE_DOC_PROCESS_DATE + "," +
		ATTRIBUTE_PROGRAM_CODE + "," +
		ATTRIBUTE_DOC_STATE_ID + "," +
		ATTRIBUTE_UNUSED1 + "," +
		ATTRIBUTE_UNUSED2 + "," +
		ATTRIBUTE_UNUSED3 + "," +
		ATTRIBUTE_UNUSED4 + "," +
		ATTRIBUTE_CONFIRMED + "," +
		ATTRIBUTE_ISSUER_ID + "," +
		ATTRIBUTE_TRANSFER_REQUEST_ID + "," +
		ATTRIBUTE_TRANSACTION_DATE + "," +
		ATTRIBUTE_QC_USER + "," +
		ATTRIBUTE_QC_DATE + "," +
		ATTRIBUTE_SHIP_CONFIRM_DATE + "," +
		ATTRIBUTE_SHIP_CONFIRM_USER + "," +
		ATTRIBUTE_PRICE_QC_USER + "," +
		ATTRIBUTE_PRICE_QC_DATE + "," +
		ATTRIBUTE_PICKLIST_ID + "," +
		ATTRIBUTE_PICKLIST_PRINT_DATE + "," +
		ATTRIBUTE_PICKLIST_QUANTITY + "," +
		ATTRIBUTE_NEED_DATE + "," +
		ATTRIBUTE_INVENTORY_GROUP + "," +
		ATTRIBUTE_DELIVERY_MAIL_ID + "," +
		ATTRIBUTE_DATE_DELIVERY_MAIL_SENT + "," +
		ATTRIBUTE_DOC_TYPE + "," +
		ATTRIBUTE_HUB_EXPEDITING_FEE + "," +
		ATTRIBUTE_SHIPMENT_ID + "," +
		ATTRIBUTE_HUB_EXPEDITING_FEE_CURRENCY_ID + "," +
		ATTRIBUTE_REPORTING_TRANSACTION_DATE + ")" +
		" values (" +
		issueBean.getIssueId() + "," +
		SqlHandler.delimitString(issueBean.getCompanyId()) + "," +
		issueBean.getPrNumber() + "," +
		SqlHandler.delimitString(issueBean.getLineItem()) + "," +
		SqlHandler.delimitString(issueBean.getSourceHub()) + "," +
		issueBean.getReceiptId() + "," +
		issueBean.getItemId() + "," +
		DateHandler.getOracleToDateFunction(issueBean.getDatePicked()) + "," +
		issueBean.getQuantity() + "," +
		DateHandler.getOracleToDateFunction(issueBean.getDateDelivered()) + "," +
		issueBean.getSalesOrder() + "," +
		issueBean.getSoLine() + "," +
		issueBean.getDeliveryId() + "," +
		issueBean.getBatch() + "," +
		SqlHandler.delimitString(issueBean.getAuthcode()) + "," +
		SqlHandler.delimitString(issueBean.getPnref()) + "," +
		issueBean.getDocNum() + "," +
		SqlHandler.delimitString(issueBean.getDocState()) + "," +
		SqlHandler.delimitString(issueBean.getErrorDetail()) + "," +
		DateHandler.getOracleToDateFunction(issueBean.getDocProcessDate()) + "," +
		SqlHandler.delimitString(issueBean.getProgramCode()) + "," +
		issueBean.getDocStateId() + "," +
		SqlHandler.delimitString(issueBean.getUnused1()) + "," +
		SqlHandler.delimitString(issueBean.getUnused2()) + "," +
		SqlHandler.delimitString(issueBean.getUnused3()) + "," +
		SqlHandler.delimitString(issueBean.getUnused4()) + "," +
		SqlHandler.delimitString(issueBean.getConfirmed()) + "," +
		issueBean.getIssuerId() + "," +
		issueBean.getTransferRequestId() + "," +
		DateHandler.getOracleToDateFunction(issueBean.getTransactionDate()) + "," +
		issueBean.getQcUser() + "," +
		DateHandler.getOracleToDateFunction(issueBean.getQcDate()) + "," +
		DateHandler.getOracleToDateFunction(issueBean.getShipConfirmDate()) + "," +
		issueBean.getShipConfirmUser() + "," +
		issueBean.getPriceQcUser() + "," +
		DateHandler.getOracleToDateFunction(issueBean.getPriceQcDate()) + "," +
		issueBean.getPicklistId() + "," +
	 DateHandler.getOracleToDateFunction(issueBean.getPicklistPrintDate()) + "," +
		issueBean.getPicklistQuantity() + "," +
		DateHandler.getOracleToDateFunction(issueBean.getNeedDate()) + "," +
		SqlHandler.delimitString(issueBean.getInventoryGroup()) + "," +
		issueBean.getDeliveryMailId() + "," +
	 DateHandler.getOracleToDateFunction(issueBean.getDateDeliveryMailSent()) + "," +
		SqlHandler.delimitString(issueBean.getDocType()) + "," +
		issueBean.getHubExpeditingFee() + "," +
		issueBean.getShipmentId() + "," +
		SqlHandler.delimitString(issueBean.getHubExpeditingFeeCurrencyId()) + "," +
		DateHandler.getOracleToDateFunction(issueBean.getReportingTransactionDate()) + ")";
	 return sqlManager.update(conn, query);
	}
//update
	public int update(IssueBean issueBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = update(issueBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int update(IssueBean issueBean, Connection conn)
	 throws BaseException {
	 String query  = "update " + TABLE + " set " +
		ATTRIBUTE_ISSUE_ID + "=" +
		 StringHandler.nullIfZero(issueBean.getIssueId()) + "," +
		ATTRIBUTE_COMPANY_ID + "=" +
		 SqlHandler.delimitString(issueBean.getCompanyId()) + "," +
		ATTRIBUTE_PR_NUMBER + "=" +
		 StringHandler.nullIfZero(issueBean.getPrNumber()) + "," +
		ATTRIBUTE_LINE_ITEM + "=" +
		 SqlHandler.delimitString(issueBean.getLineItem()) + "," +
		ATTRIBUTE_SOURCE_HUB + "=" +
		 SqlHandler.delimitString(issueBean.getSourceHub()) + "," +
		ATTRIBUTE_RECEIPT_ID + "=" +
		 StringHandler.nullIfZero(issueBean.getReceiptId()) + "," +
		ATTRIBUTE_ITEM_ID + "=" +
		 StringHandler.nullIfZero(issueBean.getItemId()) + "," +
		ATTRIBUTE_DATE_PICKED + "=" +
		 DateHandler.getOracleToDateFunction(issueBean.getDatePicked()) + "," +
		ATTRIBUTE_QUANTITY + "=" +
		 StringHandler.nullIfZero(issueBean.getQuantity()) + "," +
		ATTRIBUTE_DATE_DELIVERED + "=" +
		 DateHandler.getOracleToDateFunction(issueBean.getDateDelivered()) + "," +
		ATTRIBUTE_SALES_ORDER + "=" +
		 StringHandler.nullIfZero(issueBean.getSalesOrder()) + "," +
		ATTRIBUTE_SO_LINE + "=" +
		 StringHandler.nullIfZero(issueBean.getSoLine()) + "," +
		ATTRIBUTE_DELIVERY_ID + "=" +
		 StringHandler.nullIfZero(issueBean.getDeliveryId()) + "," +
		ATTRIBUTE_BATCH + "=" +
		 StringHandler.nullIfZero(issueBean.getBatch()) + "," +
		ATTRIBUTE_AUTHCODE + "=" +
		 SqlHandler.delimitString(issueBean.getAuthcode()) + "," +
		ATTRIBUTE_PNREF + "=" +
		 SqlHandler.delimitString(issueBean.getPnref()) + "," +
		ATTRIBUTE_DOC_NUM + "=" +
		 StringHandler.nullIfZero(issueBean.getDocNum()) + "," +
		ATTRIBUTE_DOC_STATE + "=" +
		 SqlHandler.delimitString(issueBean.getDocState()) + "," +
		ATTRIBUTE_ERROR_DETAIL + "=" +
		 SqlHandler.delimitString(issueBean.getErrorDetail()) + "," +
		ATTRIBUTE_DOC_PROCESS_DATE + "=" +
		 DateHandler.getOracleToDateFunction(issueBean.getDocProcessDate()) + "," +
		ATTRIBUTE_PROGRAM_CODE + "=" +
		 SqlHandler.delimitString(issueBean.getProgramCode()) + "," +
		ATTRIBUTE_DOC_STATE_ID + "=" +
		 StringHandler.nullIfZero(issueBean.getDocStateId()) + "," +
		ATTRIBUTE_UNUSED1 + "=" +
		 SqlHandler.delimitString(issueBean.getUnused1()) + "," +
		ATTRIBUTE_UNUSED2 + "=" +
		 SqlHandler.delimitString(issueBean.getUnused2()) + "," +
		ATTRIBUTE_UNUSED3 + "=" +
		 SqlHandler.delimitString(issueBean.getUnused3()) + "," +
		ATTRIBUTE_UNUSED4 + "=" +
		 SqlHandler.delimitString(issueBean.getUnused4()) + "," +
		ATTRIBUTE_CONFIRMED + "=" +
		 SqlHandler.delimitString(issueBean.getConfirmed()) + "," +
		ATTRIBUTE_ISSUER_ID + "=" +
		 StringHandler.nullIfZero(issueBean.getIssuerId()) + "," +
		ATTRIBUTE_TRANSFER_REQUEST_ID + "=" +
		 StringHandler.nullIfZero(issueBean.getTransferRequestId()) + "," +
		ATTRIBUTE_TRANSACTION_DATE + "=" +
	 DateHandler.getOracleToDateFunction(issueBean.getTransactionDate()) + "," +
		ATTRIBUTE_QC_USER + "=" +
		 StringHandler.nullIfZero(issueBean.getQcUser()) + "," +
		ATTRIBUTE_QC_DATE + "=" +
		 DateHandler.getOracleToDateFunction(issueBean.getQcDate()) + "," +
		ATTRIBUTE_SHIP_CONFIRM_DATE + "=" +
	 DateHandler.getOracleToDateFunction(issueBean.getShipConfirmDate()) + "," +
		ATTRIBUTE_SHIP_CONFIRM_USER + "=" +
		 StringHandler.nullIfZero(issueBean.getShipConfirmUser()) + "," +
		ATTRIBUTE_PRICE_QC_USER + "=" +
		 StringHandler.nullIfZero(issueBean.getPriceQcUser()) + "," +
		ATTRIBUTE_PRICE_QC_DATE + "=" +
		 DateHandler.getOracleToDateFunction(issueBean.getPriceQcDate()) + "," +
		ATTRIBUTE_PICKLIST_ID + "=" +
		 StringHandler.nullIfZero(issueBean.getPicklistId()) + "," +
		ATTRIBUTE_PICKLIST_PRINT_DATE + "=" +
	 DateHandler.getOracleToDateFunction(issueBean.getPicklistPrintDate()) + "," +
		ATTRIBUTE_PICKLIST_QUANTITY + "=" +
		 StringHandler.nullIfZero(issueBean.getPicklistQuantity()) + "," +
		ATTRIBUTE_NEED_DATE + "=" +
		 DateHandler.getOracleToDateFunction(issueBean.getNeedDate()) + "," +
		ATTRIBUTE_INVENTORY_GROUP + "=" +
		 SqlHandler.delimitString(issueBean.getInventoryGroup()) + "," +
		ATTRIBUTE_DELIVERY_MAIL_ID + "=" +
		 StringHandler.nullIfZero(issueBean.getDeliveryMailId()) + "," +
		ATTRIBUTE_DATE_DELIVERY_MAIL_SENT + "=" +
	 DateHandler.getOracleToDateFunction(issueBean.getDateDeliveryMailSent()) + "," +
		ATTRIBUTE_DOC_TYPE + "=" +
		 SqlHandler.delimitString(issueBean.getDocType()) + "," +
		ATTRIBUTE_HUB_EXPEDITING_FEE + "=" +
		 StringHandler.nullIfZero(issueBean.getHubExpeditingFee()) + "," +
		ATTRIBUTE_SHIPMENT_ID + "=" +
		 StringHandler.nullIfZero(issueBean.getShipmentId()) + "," +
		ATTRIBUTE_HUB_EXPEDITING_FEE_CURRENCY_ID + "=" +
	 SqlHandler.delimitString(issueBean.getHubExpeditingFeeCurrencyId()) + "," +
		ATTRIBUTE_REPORTING_TRANSACTION_DATE + "=" +
		 DateHandler.getOracleToDateFunction(issueBean.getReportingTransactionDate()) + " " +
		"where " + ATTRIBUTE_ISSUE_ID + "=" +
		 issueBean.getIssueId();
	 return new SqlManager().update(conn, query);
	}
	*/

 public BigDecimal selectBatchNumber() throws BaseException {
	BigDecimal batchNumber = null;
	String query = "select print_batch_seq.nextval " + ATTRIBUTE_BATCH +
	 " from dual";

	Connection connection = getDbManager().getConnection();
	DataSet dataSet = new SqlManager().select(connection, query);
	getDbManager().returnConnection(connection);

	Iterator dataIter = dataSet.iterator();
	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 IssueBean issueBean = new IssueBean();
	 load(dataSetRow, issueBean);

	 batchNumber = issueBean.getBatch();
	}

	return batchNumber;
 }

 //select
 public Collection select(SearchCriteria criteria) throws BaseException {

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

 public Collection select(SearchCriteria criteria,
	Connection conn) throws BaseException {
	Collection issueBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 IssueBean issueBean = new IssueBean();
	 load(dataSetRow, issueBean);
	 issueBeanColl.add(issueBean);
	}

	return issueBeanColl;
 }
}