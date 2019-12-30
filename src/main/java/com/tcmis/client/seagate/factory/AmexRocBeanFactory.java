package com.tcmis.client.seagate.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.seagate.beans.AmexRocBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: AmexRocBeanFactory <br>
 * @version: 1.0, Apr 5, 2005 <br>
 *****************************************************************************/

public class AmexRocBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_SOC_ID = "SOC_ID";
  public String ATTRIBUTE_SOC_LINE = "SOC_LINE";
  public String ATTRIBUTE_PAYMENT_ID = "PAYMENT_ID";
  public String ATTRIBUTE_ROC_ID = "ROC_ID";
  public String ATTRIBUTE_ROC_AMOUNT = "ROC_AMOUNT";
  public String ATTRIBUTE_SUBMIT_DATE = "SUBMIT_DATE";
  public String ATTRIBUTE_PROCESS_DATE = "PROCESS_DATE";
  public String ATTRIBUTE_CREDIT_CARD_NUMBER = "CREDIT_CARD_NUMBER";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_ENTRY_REF = "ENTRY_REF";
  public String ATTRIBUTE_ISSUE_ID = "ISSUE_ID";
  public String ATTRIBUTE_INVOICE = "INVOICE";

  //table name
  public String TABLE = "AMEX_ROC";

  //constructor
  public AmexRocBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("socId")) {
      return ATTRIBUTE_SOC_ID;
    }
    else if (attributeName.equals("socLine")) {
      return ATTRIBUTE_SOC_LINE;
    }
    else if (attributeName.equals("paymentId")) {
      return ATTRIBUTE_PAYMENT_ID;
    }
    else if (attributeName.equals("rocId")) {
      return ATTRIBUTE_ROC_ID;
    }
    else if (attributeName.equals("rocAmount")) {
      return ATTRIBUTE_ROC_AMOUNT;
    }
    else if (attributeName.equals("submitDate")) {
      return ATTRIBUTE_SUBMIT_DATE;
    }
    else if (attributeName.equals("processDate")) {
      return ATTRIBUTE_PROCESS_DATE;
    }
    else if (attributeName.equals("creditCardNumber")) {
      return ATTRIBUTE_CREDIT_CARD_NUMBER;
    }
    else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
    }
    else if (attributeName.equals("prNumber")) {
      return ATTRIBUTE_PR_NUMBER;
    }
    else if (attributeName.equals("lineItem")) {
      return ATTRIBUTE_LINE_ITEM;
    }
    else if (attributeName.equals("entryRef")) {
      return ATTRIBUTE_ENTRY_REF;
    }
    else if (attributeName.equals("issueId")) {
      return ATTRIBUTE_ISSUE_ID;
    }
    else if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, AmexRocBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(AmexRocBean amexRocBean)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("socId", "SearchCriterion.EQUALS",
     "" + amexRocBean.getSocId());
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
   public int delete(AmexRocBean amexRocBean, Connection conn)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("socId", "SearchCriterion.EQUALS",
     "" + amexRocBean.getSocId());
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
  public int insert(AmexRocBean amexRocBean) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(amexRocBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(AmexRocBean amexRocBean, Connection conn) throws
      BaseException {

    SqlManager sqlManager = new SqlManager();

    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_SOC_ID + "," +
        ATTRIBUTE_SOC_LINE + "," +
        ATTRIBUTE_PAYMENT_ID + "," +
        ATTRIBUTE_ROC_ID + "," +
        ATTRIBUTE_ROC_AMOUNT + "," +
        ATTRIBUTE_SUBMIT_DATE + "," +
        ATTRIBUTE_PROCESS_DATE + "," +
        ATTRIBUTE_CREDIT_CARD_NUMBER + "," +
        ATTRIBUTE_PO_NUMBER + "," +
        ATTRIBUTE_PR_NUMBER + "," +
        ATTRIBUTE_LINE_ITEM + "," +
        ATTRIBUTE_ENTRY_REF + "," +
        ATTRIBUTE_ISSUE_ID + "," +
        ATTRIBUTE_INVOICE + ")" +
        "values (" +
        amexRocBean.getSocId() + "," +
        amexRocBean.getSocLine() + "," +
        SqlHandler.delimitString(amexRocBean.getPaymentId()) + "," +
        amexRocBean.getRocId() + "," +
        amexRocBean.getRocAmount() + "," +
        DateHandler.getOracleToDateFunction(amexRocBean.getSubmitDate()) + "," +
        DateHandler.getOracleToDateFunction(amexRocBean.getProcessDate()) + "," +
        SqlHandler.delimitString(amexRocBean.getCreditCardNumber()) + "," +
        SqlHandler.delimitString(amexRocBean.getPoNumber()) + "," +
        SqlHandler.delimitString(amexRocBean.getPrNumber()) + "," +
        SqlHandler.delimitString(amexRocBean.getLineItem()) + "," +
        amexRocBean.getEntryRef() + "," +
        amexRocBean.getIssueId() + "," +
        amexRocBean.getInvoice() + ")";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    return sqlManager.update(conn, query);
  }

//update
  public int update(AmexRocBean amexRocBean) throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = update(amexRocBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int update(AmexRocBean amexRocBean, Connection conn) throws
      BaseException {
    String query = "update " + TABLE + " set " +
        ATTRIBUTE_SOC_ID + "=" +
        amexRocBean.getSocId() + "," +
        ATTRIBUTE_SOC_LINE + "=" +
        amexRocBean.getSocLine() + "," +
        ATTRIBUTE_PAYMENT_ID + "=" +
        SqlHandler.delimitString(amexRocBean.getPaymentId()) + "," +
        ATTRIBUTE_ROC_ID + "=" +
        amexRocBean.getRocId() + "," +
        ATTRIBUTE_ROC_AMOUNT + "=" +
        amexRocBean.getRocAmount() + "," +
        ATTRIBUTE_SUBMIT_DATE + "=" +
        DateHandler.getOracleToDateFunction(amexRocBean.getSubmitDate()) + "," +
        ATTRIBUTE_PROCESS_DATE + "=" +
        DateHandler.getOracleToDateFunction(amexRocBean.getProcessDate()) + "," +
        ATTRIBUTE_CREDIT_CARD_NUMBER + "=" +
        SqlHandler.delimitString(amexRocBean.getCreditCardNumber()) + "," +
        ATTRIBUTE_PO_NUMBER + "=" +
        SqlHandler.delimitString(amexRocBean.getPoNumber()) + "," +
        ATTRIBUTE_PR_NUMBER + "=" +
        SqlHandler.delimitString(amexRocBean.getPrNumber()) + "," +
        ATTRIBUTE_LINE_ITEM + "=" +
        SqlHandler.delimitString(amexRocBean.getLineItem()) + "," +
        ATTRIBUTE_ENTRY_REF + "=" +
        amexRocBean.getEntryRef() + "," +
        ATTRIBUTE_ISSUE_ID + "=" +
        amexRocBean.getIssueId() + "," +
        ATTRIBUTE_INVOICE + "=" +
        amexRocBean.getInvoice() + " " +
        "where " + ATTRIBUTE_SOC_ID + "=" +
        amexRocBean.getSocId() + " and " +
        ATTRIBUTE_SOC_LINE + "=" +
        amexRocBean.getSocLine() + " and " +
        ATTRIBUTE_PAYMENT_ID + "=" +
        SqlHandler.delimitString(amexRocBean.getPaymentId()) + " and " +
        ATTRIBUTE_ROC_ID + "=" +
        amexRocBean.getRocId();
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
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

    Collection amexRocBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      AmexRocBean amexRocBean = new AmexRocBean();
      load(dataSetRow, amexRocBean);
      amexRocBeanColl.add(amexRocBean);
    }

    return amexRocBeanColl;
  }

  public boolean isPresent(AmexRocBean bean) throws
      BaseException {
    Connection connection = null;
    boolean result = false;
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("socId",
                          SearchCriterion.EQUALS,
                          bean.getSocId().toString());
    criteria.addCriterion("socLine",
                          SearchCriterion.EQUALS,
                          bean.getSocLine().toString());
    criteria.addCriterion("paymentId",
                          SearchCriterion.EQUALS,
                          bean.getPaymentId());
    criteria.addCriterion("rocId",
                          SearchCriterion.EQUALS,
                          bean.getRocId().toString());
    criteria.addCriterion("entryRef",
                          SearchCriterion.EQUALS,
                          bean.getEntryRef().toString());
    String query = "select * from " + TABLE + " " + getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    try {
      connection = getDbManager().getConnection();
      result = new SqlManager().check(connection, query);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

}