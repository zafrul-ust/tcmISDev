package com.tcmis.client.seagate.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.seagate.beans.SeagateInvoiceHistoryBean;
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

/******************************************************************************
 * CLASSNAME: SeagateInvoiceHistoryBeanFactory <br>
 * @version: 1.0, Feb 9, 2005 <br>
 *****************************************************************************/

public class SeagateInvoiceHistoryBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_ENTRY_ID = "ENTRY_ID";
  public String ATTRIBUTE_DO_NUMBER = "DO_NUMBER";
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_ISSUE_ID = "ISSUE_ID";
  public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
  public String ATTRIBUTE_CC_NUMBER = "CC_NUMBER";
  public String ATTRIBUTE_CC_EXPIRATION = "CC_EXPIRATION";
  public String ATTRIBUTE_TXREF = "TXREF";
  public String ATTRIBUTE_AUTHCODE = "AUTHCODE";
  public String ATTRIBUTE_RESULT_CODE = "RESULT_CODE";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_INVOICE_LINE = "INVOICE_LINE";
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_ISSUE_AMOUNT = "ISSUE_AMOUNT";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_ORIGINAL_TXREF = "ORIGINAL_TXREF";
  public String ATTRIBUTE_STATUS = "STATUS";

  //table name
  public String TABLE = "SEAGATE_INVOICE_HISTORY";

  //constructor
  public SeagateInvoiceHistoryBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("entryId")) {
      return ATTRIBUTE_ENTRY_ID;
    }
    else if (attributeName.equals("doNumber")) {
      return ATTRIBUTE_DO_NUMBER;
    }
    else if (attributeName.equals("prNumber")) {
      return ATTRIBUTE_PR_NUMBER;
    }
    else if (attributeName.equals("lineItem")) {
      return ATTRIBUTE_LINE_ITEM;
    }
    else if (attributeName.equals("issueId")) {
      return ATTRIBUTE_ISSUE_ID;
    }
    else if (attributeName.equals("invoiceAmount")) {
      return ATTRIBUTE_INVOICE_AMOUNT;
    }
    else if (attributeName.equals("ccNumber")) {
      return ATTRIBUTE_CC_NUMBER;
    }
    else if (attributeName.equals("ccExpiration")) {
      return ATTRIBUTE_CC_EXPIRATION;
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
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else if (attributeName.equals("invoiceLine")) {
      return ATTRIBUTE_INVOICE_LINE;
    }
    else if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("issueAmount")) {
      return ATTRIBUTE_ISSUE_AMOUNT;
    }
    else if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else if (attributeName.equals("originalTxref")) {
      return ATTRIBUTE_ORIGINAL_TXREF;
    }
    else if (attributeName.equals("status")) {
      return ATTRIBUTE_STATUS;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, SeagateInvoiceHistoryBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(SeagateInvoiceHistoryBean seagateInvoiceHistoryBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("entryId", "SearchCriterion.EQUALS",
     "" + seagateInvoiceHistoryBean.getEntryId());
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
   public int delete(SeagateInvoiceHistoryBean seagateInvoiceHistoryBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("entryId", "SearchCriterion.EQUALS",
     "" + seagateInvoiceHistoryBean.getEntryId());
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

  public int delete(SearchCriteria criteria, Connection conn) throws
      BaseException {

    String sqlQuery = " delete from " + TABLE + " " +
        getWhereClause(criteria);

    return new SqlManager().update(conn, sqlQuery);
  }

//insert
  public int insert(SeagateInvoiceHistoryBean seagateInvoiceHistoryBean) throws
      BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(seagateInvoiceHistoryBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(SeagateInvoiceHistoryBean seagateInvoiceHistoryBean,
                    Connection conn) throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_ENTRY_ID + "," +
        ATTRIBUTE_DO_NUMBER + "," +
        ATTRIBUTE_PR_NUMBER + "," +
        ATTRIBUTE_LINE_ITEM + "," +
        ATTRIBUTE_ISSUE_ID + "," +
        ATTRIBUTE_INVOICE_AMOUNT + "," +
        ATTRIBUTE_CC_NUMBER + "," +
        ATTRIBUTE_CC_EXPIRATION + "," +
        ATTRIBUTE_TXREF + "," +
        ATTRIBUTE_AUTHCODE + "," +
        ATTRIBUTE_RESULT_CODE + "," +
        ATTRIBUTE_INVOICE_DATE + "," +
        ATTRIBUTE_INVOICE_LINE + "," +
        ATTRIBUTE_INVOICE + "," +
        ATTRIBUTE_ISSUE_AMOUNT + "," +
        ATTRIBUTE_INVOICE_PERIOD + "," +
        ATTRIBUTE_ORIGINAL_TXREF + "," +
        ATTRIBUTE_STATUS + " )" +
        "values (" +
        seagateInvoiceHistoryBean.getEntryId() + "," +
        SqlHandler.delimitString(seagateInvoiceHistoryBean.getDoNumber()) + "," +
        seagateInvoiceHistoryBean.getPrNumber() + "," +
        SqlHandler.delimitString(seagateInvoiceHistoryBean.getLineItem()) + "," +
        seagateInvoiceHistoryBean.getIssueId() + "," +
        seagateInvoiceHistoryBean.getInvoiceAmount() +
        "," +
        seagateInvoiceHistoryBean.getCcNumber() + "," +
        DateHandler.getOracleToDateFunction(seagateInvoiceHistoryBean.
                                            getCcExpiration()) + "," +
        SqlHandler.delimitString(seagateInvoiceHistoryBean.getTxref()) + "," +
        SqlHandler.delimitString(seagateInvoiceHistoryBean.getAuthcode()) + "," +
        SqlHandler.delimitString(seagateInvoiceHistoryBean.getResultCode()) +
        "," +
        DateHandler.getOracleToDateFunction(seagateInvoiceHistoryBean.
                                            getInvoiceDate()) + "," +
        seagateInvoiceHistoryBean.getInvoiceLine() +
        "," +
        seagateInvoiceHistoryBean.getInvoice() + "," +
        seagateInvoiceHistoryBean.getIssueAmount() +
        "," +
        seagateInvoiceHistoryBean.getInvoicePeriod() +
        "," +
        SqlHandler.delimitString(seagateInvoiceHistoryBean.getOriginalTxref()) +
        "," +
        SqlHandler.delimitString(seagateInvoiceHistoryBean.getStatus()) +
        ")";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    return sqlManager.update(conn, query);
  }

//update
  public int update(SeagateInvoiceHistoryBean seagateInvoiceHistoryBean) throws
      BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = update(seagateInvoiceHistoryBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int update(SeagateInvoiceHistoryBean seagateInvoiceHistoryBean,
                    Connection conn) throws BaseException {
    String query = "update " + TABLE + " set " +
        ATTRIBUTE_ENTRY_ID + "=" +
        seagateInvoiceHistoryBean.getEntryId() + "," +
        ATTRIBUTE_DO_NUMBER + "=" +
        SqlHandler.delimitString(seagateInvoiceHistoryBean.getDoNumber()) + "," +
        ATTRIBUTE_PR_NUMBER + "=" +
        seagateInvoiceHistoryBean.getPrNumber() + "," +
        ATTRIBUTE_LINE_ITEM + "=" +
        SqlHandler.delimitString(seagateInvoiceHistoryBean.getLineItem()) + "," +
        ATTRIBUTE_ISSUE_ID + "=" +
        seagateInvoiceHistoryBean.getIssueId() + "," +
        ATTRIBUTE_INVOICE_AMOUNT + "=" +
        seagateInvoiceHistoryBean.getInvoiceAmount() +
        "," +
        ATTRIBUTE_CC_NUMBER + "=" +
        seagateInvoiceHistoryBean.getCcNumber() + "," +
        ATTRIBUTE_CC_EXPIRATION + "=" +
        DateHandler.getOracleToDateFunction(seagateInvoiceHistoryBean.
                                            getCcExpiration()) + "," +
        ATTRIBUTE_TXREF + "=" +
        SqlHandler.delimitString(seagateInvoiceHistoryBean.getTxref()) + "," +
        ATTRIBUTE_AUTHCODE + "=" +
        SqlHandler.delimitString(seagateInvoiceHistoryBean.getAuthcode()) + "," +
        ATTRIBUTE_RESULT_CODE + "=" +
        SqlHandler.delimitString(seagateInvoiceHistoryBean.getResultCode()) +
        "," +
        ATTRIBUTE_INVOICE_DATE + "=" +
        DateHandler.getOracleToDateFunction(seagateInvoiceHistoryBean.
                                            getInvoiceDate()) + "," +
        ATTRIBUTE_INVOICE_LINE + "=" +
        seagateInvoiceHistoryBean.getInvoiceLine() +
        "," +
        ATTRIBUTE_INVOICE + "=" +
        seagateInvoiceHistoryBean.getInvoice() + "," +
        ATTRIBUTE_ISSUE_AMOUNT + "=" +
        seagateInvoiceHistoryBean.getIssueAmount() +
        "," +
        ATTRIBUTE_INVOICE_PERIOD + "=" +
        seagateInvoiceHistoryBean.getInvoicePeriod() +
        "," +
        ATTRIBUTE_ORIGINAL_TXREF + "=" +
        SqlHandler.delimitString(seagateInvoiceHistoryBean.getOriginalTxref()) +
        "," +
        ATTRIBUTE_STATUS + "=" +
        SqlHandler.delimitString(seagateInvoiceHistoryBean.getStatus()) +
        " " +
        "where " + ATTRIBUTE_ENTRY_ID + "=" +
        seagateInvoiceHistoryBean.getEntryId() +
        " and " + ATTRIBUTE_ISSUE_ID + "=" +
        seagateInvoiceHistoryBean.getIssueId() +
        " and " + ATTRIBUTE_INVOICE_DATE + "=" +
        DateHandler.getOracleToDateFunction(seagateInvoiceHistoryBean.
                                            getInvoiceDate());
    return new SqlManager().update(conn, query);
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

  public Collection select(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection seagateInvoiceHistoryBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      SeagateInvoiceHistoryBean seagateInvoiceHistoryBean = new
          SeagateInvoiceHistoryBean();
      load(dataSetRow, seagateInvoiceHistoryBean);
      seagateInvoiceHistoryBeanColl.add(seagateInvoiceHistoryBean);
    }

    return seagateInvoiceHistoryBeanColl;
  }
}