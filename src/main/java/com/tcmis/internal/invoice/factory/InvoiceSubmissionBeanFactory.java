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
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.invoice.beans.InvoiceSubmissionBean;

/******************************************************************************
 * CLASSNAME: InvoiceSubmissionBeanFactory <br>
 * @version: 1.0, Dec 6, 2005 <br>
 *****************************************************************************/

public class InvoiceSubmissionBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_ISSUE_ID = "ISSUE_ID";
  public String ATTRIBUTE_ISSUE_COST_REVISION = "ISSUE_COST_REVISION";
  public String ATTRIBUTE_DATE_SENT = "DATE_SENT";
  public String ATTRIBUTE_DATE_REJECTED = "DATE_REJECTED";
  public String ATTRIBUTE_REASON_REJECTED = "REASON_REJECTED";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_XBLNR = "XBLNR";
  public String ATTRIBUTE_DOCUMENT_CONTROL_NUMBER = "DOCUMENT_CONTROL_NUMBER";
  public String ATTRIBUTE_DATE_ACKNOWLEDGED = "DATE_ACKNOWLEDGED";
  public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
  public String ATTRIBUTE_CREDIT_CARD_NUMBER = "CREDIT_CARD_NUMBER";
  public String ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE = "CREDIT_CARD_EXPIRATION_DATE";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_INVOICE_LINE = "INVOICE_LINE";
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_ISSUE_AMOUNT = "ISSUE_AMOUNT";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_TXREF = "TXREF";
  public String ATTRIBUTE_AUTHCODE = "AUTHCODE";
  public String ATTRIBUTE_RESULT_CODE = "RESULT_CODE";
  public String ATTRIBUTE_ENTRY_ID = "ENTRY_ID";
  public String ATTRIBUTE_STATUS = "STATUS";
  public String ATTRIBUTE_CREDIT_CARD_TYPE = "CREDIT_CARD_TYPE";
  public String ATTRIBUTE_CREDIT_CARD_NAME = "CREDIT_CARD_NAME";

  //table name
  public String TABLE = "INVOICE_SUBMISSION";

  //constructor
  public InvoiceSubmissionBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("issueId")) {
      return ATTRIBUTE_ISSUE_ID;
    }
    else if (attributeName.equals("issueCostRevision")) {
      return ATTRIBUTE_ISSUE_COST_REVISION;
    }
    else if (attributeName.equals("dateSent")) {
      return ATTRIBUTE_DATE_SENT;
    }
    else if (attributeName.equals("dateRejected")) {
      return ATTRIBUTE_DATE_REJECTED;
    }
    else if (attributeName.equals("reasonRejected")) {
      return ATTRIBUTE_REASON_REJECTED;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("xblnr")) {
      return ATTRIBUTE_XBLNR;
    }
    else if (attributeName.equals("documentControlNumber")) {
      return ATTRIBUTE_DOCUMENT_CONTROL_NUMBER;
    }
    else if (attributeName.equals("dateAcknowledged")) {
      return ATTRIBUTE_DATE_ACKNOWLEDGED;
    }
    else if (attributeName.equals("invoiceAmount")) {
      return ATTRIBUTE_INVOICE_AMOUNT;
    }
    else if (attributeName.equals("creditCardNumber")) {
      return ATTRIBUTE_CREDIT_CARD_NUMBER;
    }
    else if (attributeName.equals("creditCardExpirationDate")) {
      return ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else if (attributeName.equals("invoiceLine")) {
      return ATTRIBUTE_INVOICE_LINE;
    }
    else if (attributeName.equals("prNumber")) {
      return ATTRIBUTE_PR_NUMBER;
    }
    else if (attributeName.equals("lineItem")) {
      return ATTRIBUTE_LINE_ITEM;
    }
    else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
    }
    else if (attributeName.equals("issueAmount")) {
      return ATTRIBUTE_ISSUE_AMOUNT;
    }
    else if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else if (attributeName.equals("txref")) {
      return ATTRIBUTE_TXREF;
    }
    else if (attributeName.equals("authcode")) {
      return ATTRIBUTE_AUTHCODE;
    }
    else if (attributeName.equals("resultCode")) {
      return ATTRIBUTE_RESULT_CODE;
    }
    else if (attributeName.equals("entryId")) {
      return ATTRIBUTE_ENTRY_ID;
    }
    else if (attributeName.equals("status")) {
      return ATTRIBUTE_STATUS;
    }
    else if (attributeName.equals("creditCardType")) {
      return ATTRIBUTE_CREDIT_CARD_TYPE;
    }
    else if (attributeName.equals("creditCardName")) {
      return ATTRIBUTE_CREDIT_CARD_NAME;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceSubmissionBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceSubmissionBean invoiceSubmissionBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceSubmissionBean.getInvoice());
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
   public int delete(InvoiceSubmissionBean invoiceSubmissionBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceSubmissionBean.getInvoice());
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

  public int delete(SearchCriteria criteria, Connection conn) throws BaseException {
    String sqlQuery = " delete from " + TABLE + " " +
        getWhereClause(criteria);
    return new SqlManager().update(conn, sqlQuery);
  }

//you need to verify the primary key(s) before uncommenting this

  //insert
  public int insert(InvoiceSubmissionBean invoiceSubmissionBean) throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(invoiceSubmissionBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(InvoiceSubmissionBean invoiceSubmissionBean, Connection conn) throws
      BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_INVOICE + "," +
        ATTRIBUTE_ISSUE_ID + "," +
        ATTRIBUTE_ISSUE_COST_REVISION + "," +
        ATTRIBUTE_DATE_SENT + "," +
        ATTRIBUTE_DATE_REJECTED + "," +
        ATTRIBUTE_REASON_REJECTED + "," +
        ATTRIBUTE_FACILITY_ID + "," +
        ATTRIBUTE_XBLNR + "," +
        ATTRIBUTE_DOCUMENT_CONTROL_NUMBER + "," +
        ATTRIBUTE_DATE_ACKNOWLEDGED + "," +
        ATTRIBUTE_INVOICE_AMOUNT + "," +
        ATTRIBUTE_CREDIT_CARD_NUMBER + "," +
        ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE + "," +
        ATTRIBUTE_INVOICE_DATE + "," +
        ATTRIBUTE_INVOICE_LINE + "," +
        ATTRIBUTE_PR_NUMBER + "," +
        ATTRIBUTE_LINE_ITEM + "," +
        ATTRIBUTE_PO_NUMBER + "," +
        ATTRIBUTE_ISSUE_AMOUNT + "," +
        ATTRIBUTE_INVOICE_PERIOD + "," +
        ATTRIBUTE_TXREF + "," +
        ATTRIBUTE_AUTHCODE + "," +
        ATTRIBUTE_RESULT_CODE + "," +
        ATTRIBUTE_ENTRY_ID + "," +
        ATTRIBUTE_STATUS + "," +
        ATTRIBUTE_CREDIT_CARD_TYPE + "," +
        ATTRIBUTE_CREDIT_CARD_NAME + ")" +
        " values (" +
        invoiceSubmissionBean.getInvoice() + "," +
        invoiceSubmissionBean.getIssueId() + "," +
        invoiceSubmissionBean.getIssueCostRevision() + "," +
        DateHandler.getOracleToDateFunction(invoiceSubmissionBean.getDateSent()) + "," +
        DateHandler.getOracleToDateFunction(invoiceSubmissionBean.getDateRejected()) +
        "," +
        SqlHandler.delimitString(invoiceSubmissionBean.getReasonRejected()) + "," +
        SqlHandler.delimitString(invoiceSubmissionBean.getFacilityId()) + "," +
        SqlHandler.delimitString(invoiceSubmissionBean.getXblnr()) + "," +
        SqlHandler.delimitString(invoiceSubmissionBean.getDocumentControlNumber()) + "," +
        DateHandler.getOracleToDateFunction(invoiceSubmissionBean.getDateAcknowledged()) +
        "," +
        invoiceSubmissionBean.getInvoiceAmount() + "," +
        invoiceSubmissionBean.getCreditCardNumber() + "," +
        DateHandler.getOracleToDateFunction(invoiceSubmissionBean.
                                            getCreditCardExpirationDate()) + "," +
        DateHandler.getOracleToDateFunction(invoiceSubmissionBean.getInvoiceDate()) + "," +
        invoiceSubmissionBean.getInvoiceLine() + "," +
        invoiceSubmissionBean.getPrNumber() + "," +
        invoiceSubmissionBean.getLineItem() + "," +
        SqlHandler.delimitString(invoiceSubmissionBean.getPoNumber()) + "," +
        invoiceSubmissionBean.getIssueAmount() + "," +
        invoiceSubmissionBean.getInvoicePeriod() + "," +
        SqlHandler.delimitString(invoiceSubmissionBean.getTxref()) + "," +
        SqlHandler.delimitString(invoiceSubmissionBean.getAuthcode()) + "," +
        SqlHandler.delimitString(invoiceSubmissionBean.getResultCode()) + "," +
        invoiceSubmissionBean.getEntryId() + "," +
        SqlHandler.delimitString(invoiceSubmissionBean.getStatus()) + "," +
        SqlHandler.delimitString(invoiceSubmissionBean.getCreditCardType()) + "," +
        SqlHandler.delimitString(invoiceSubmissionBean.getCreditCardName()) + ")";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
//System.out.println("QUERY:" + query);
    return sqlManager.update(conn, query);
  }

  /*
//update
   public int update(InvoiceSubmissionBean invoiceSubmissionBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceSubmissionBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceSubmissionBean invoiceSubmissionBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceSubmissionBean.getInvoice()) + "," +
     ATTRIBUTE_ISSUE_ID + "=" +
      StringHandler.nullIfZero(invoiceSubmissionBean.getIssueId()) + "," +
     ATTRIBUTE_ISSUE_COST_REVISION + "=" +
      StringHandler.nullIfZero(invoiceSubmissionBean.getIssueCostRevision()) + "," +
     ATTRIBUTE_DATE_SENT + "=" +
      DateHandler.getOracleToDateFunction(invoiceSubmissionBean.getDateSent()) + "," +
     ATTRIBUTE_DATE_REJECTED + "=" +
       DateHandler.getOracleToDateFunction(invoiceSubmissionBean.getDateRejected()) + "," +
     ATTRIBUTE_REASON_REJECTED + "=" +
      SqlHandler.delimitString(invoiceSubmissionBean.getReasonRejected()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(invoiceSubmissionBean.getFacilityId()) + "," +
     ATTRIBUTE_XBLNR + "=" +
      SqlHandler.delimitString(invoiceSubmissionBean.getXblnr()) + "," +
     ATTRIBUTE_DOCUMENT_CONTROL_NUMBER + "=" +
      SqlHandler.delimitString(invoiceSubmissionBean.getDocumentControlNumber()) + "," +
     ATTRIBUTE_DATE_ACKNOWLEDGED + "=" +
       DateHandler.getOracleToDateFunction(invoiceSubmissionBean.getDateAcknowledged()) + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceSubmissionBean.getInvoiceAmount()) + "," +
     ATTRIBUTE_CREDIT_CARD_NUMBER + "=" +
      StringHandler.nullIfZero(invoiceSubmissionBean.getCreditCardNumber()) + "," +
     ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoiceSubmissionBean.getCreditCardExpirationDate()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoiceSubmissionBean.getInvoiceDate()) + "," +
     ATTRIBUTE_INVOICE_LINE + "=" +
      StringHandler.nullIfZero(invoiceSubmissionBean.getInvoiceLine()) + "," +
     ATTRIBUTE_PR_NUMBER + "=" +
      StringHandler.nullIfZero(invoiceSubmissionBean.getPrNumber()) + "," +
     ATTRIBUTE_LINE_ITEM + "=" +
      StringHandler.nullIfZero(invoiceSubmissionBean.getLineItem()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
      SqlHandler.delimitString(invoiceSubmissionBean.getPoNumber()) + "," +
     ATTRIBUTE_ISSUE_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceSubmissionBean.getIssueAmount()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoiceSubmissionBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_TXREF + "=" +
      SqlHandler.delimitString(invoiceSubmissionBean.getTxref()) + "," +
     ATTRIBUTE_AUTHCODE + "=" +
      SqlHandler.delimitString(invoiceSubmissionBean.getAuthcode()) + "," +
     ATTRIBUTE_RESULT_CODE + "=" +
      SqlHandler.delimitString(invoiceSubmissionBean.getResultCode()) + "," +
     ATTRIBUTE_ENTRY_ID + "=" +
      StringHandler.nullIfZero(invoiceSubmissionBean.getEntryId()) + "," +
     ATTRIBUTE_STATUS + "=" +
      SqlHandler.delimitString(invoiceSubmissionBean.getStatus()) + "," +
     ATTRIBUTE_CREDIT_CARD_TYPE + "=" +
      SqlHandler.delimitString(invoiceSubmissionBean.getCreditCardType()) + "," +
     ATTRIBUTE_CREDIT_CARD_NAME + "=" +
      SqlHandler.delimitString(invoiceSubmissionBean.getCreditCardName()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      invoiceSubmissionBean.getInvoice();
    return new SqlManager().update(conn, query);
   }
   */

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

  public Collection select(SearchCriteria criteria, Connection conn) throws BaseException {
    Collection invoiceSubmissionBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceSubmissionBean invoiceSubmissionBean = new InvoiceSubmissionBean();
      load(dataSetRow, invoiceSubmissionBean);
      invoiceSubmissionBeanColl.add(invoiceSubmissionBean);
    }
    return invoiceSubmissionBeanColl;
  }
}