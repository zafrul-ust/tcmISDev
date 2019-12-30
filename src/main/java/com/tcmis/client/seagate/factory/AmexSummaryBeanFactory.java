package com.tcmis.client.seagate.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.seagate.beans.AmexSummaryBean;
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
 * CLASSNAME: AmexSummaryBeanFactory <br>
 * @version: 1.0, Apr 6, 2005 <br>
 *****************************************************************************/

public class AmexSummaryBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PAYMENT_ID = "PAYMENT_ID";
  public String ATTRIBUTE_PAYMENT_DATE = "PAYMENT_DATE";
  public String ATTRIBUTE_PAYMENT_AMOUNT = "PAYMENT_AMOUNT";
  public String ATTRIBUTE_DEBIT_BALANCE_AMOUNT = "DEBIT_BALANCE_AMOUNT";
  public String ATTRIBUTE_ABA_BANK_ID = "ABA_BANK_ID";
  public String ATTRIBUTE_DDA_ACCOUNT_ID = "DDA_ACCOUNT_ID";
  public String ATTRIBUTE_TRANSACTION_DATE = "TRANSACTION_DATE";

  //table name
  public String TABLE = "AMEX_SUMMARY";

  //constructor
  public AmexSummaryBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("paymentId")) {
      return ATTRIBUTE_PAYMENT_ID;
    }
    else if (attributeName.equals("paymentDate")) {
      return ATTRIBUTE_PAYMENT_DATE;
    }
    else if (attributeName.equals("paymentAmount")) {
      return ATTRIBUTE_PAYMENT_AMOUNT;
    }
    else if (attributeName.equals("debitBalanceAmount")) {
      return ATTRIBUTE_DEBIT_BALANCE_AMOUNT;
    }
    else if (attributeName.equals("abaBankId")) {
      return ATTRIBUTE_ABA_BANK_ID;
    }
    else if (attributeName.equals("ddaAccountId")) {
      return ATTRIBUTE_DDA_ACCOUNT_ID;
    }
    else if (attributeName.equals("transactionDate")) {
      return ATTRIBUTE_TRANSACTION_DATE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, AmexSummaryBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(AmexSummaryBean amexSummaryBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("paymentId", "SearchCriterion.EQUALS",
     "" + amexSummaryBean.getPaymentId());
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
   public int delete(AmexSummaryBean amexSummaryBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("paymentId", "SearchCriterion.EQUALS",
     "" + amexSummaryBean.getPaymentId());
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
  public int insert(AmexSummaryBean amexSummaryBean) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(amexSummaryBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(AmexSummaryBean amexSummaryBean, Connection conn) throws
      BaseException {

    SqlManager sqlManager = new SqlManager();

    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_PAYMENT_ID + "," +
        ATTRIBUTE_PAYMENT_DATE + "," +
        ATTRIBUTE_PAYMENT_AMOUNT + "," +
        ATTRIBUTE_DEBIT_BALANCE_AMOUNT + "," +
        ATTRIBUTE_ABA_BANK_ID + "," +
        ATTRIBUTE_DDA_ACCOUNT_ID + "," +
        ATTRIBUTE_TRANSACTION_DATE + ")" +
        " values (" +
        SqlHandler.delimitString(amexSummaryBean.getPaymentId()) + "," +
        DateHandler.getOracleToDateFunction(amexSummaryBean.getPaymentDate()) +
        "," +
        SqlHandler.validBigDecimal(amexSummaryBean.getPaymentAmount()) + "," +
        amexSummaryBean.getDebitBalanceAmount() + "," +
        SqlHandler.delimitString(amexSummaryBean.getAbaBankId()) + "," +
        SqlHandler.delimitString(amexSummaryBean.getDdaAccountId()) + "," +
        DateHandler.getOracleToDateFunction(amexSummaryBean.getTransactionDate()) +
        ")";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    return sqlManager.update(conn, query);
  }

//update
  public int update(AmexSummaryBean amexSummaryBean) throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = update(amexSummaryBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int update(AmexSummaryBean amexSummaryBean, Connection conn) throws
      BaseException {
    String query = "update " + TABLE + " set " +
        ATTRIBUTE_PAYMENT_ID + "=" +
        SqlHandler.delimitString(amexSummaryBean.getPaymentId()) + "," +
        ATTRIBUTE_PAYMENT_DATE + "=" +
        DateHandler.getOracleToDateFunction(amexSummaryBean.getPaymentDate()) +
        "," +
        ATTRIBUTE_PAYMENT_AMOUNT + "=" +
        amexSummaryBean.getPaymentAmount() + "," +
        ATTRIBUTE_DEBIT_BALANCE_AMOUNT + "=" +
        amexSummaryBean.getDebitBalanceAmount() + "," +
        ATTRIBUTE_ABA_BANK_ID + "=" +
        SqlHandler.delimitString(amexSummaryBean.getAbaBankId()) + "," +
        ATTRIBUTE_DDA_ACCOUNT_ID + "=" +
        SqlHandler.delimitString(amexSummaryBean.getDdaAccountId()) + "," +
        ATTRIBUTE_TRANSACTION_DATE + "=" +
        DateHandler.getOracleToDateFunction(amexSummaryBean.getTransactionDate()) +
        " " +
        "where " + ATTRIBUTE_PAYMENT_ID + "=" +
        SqlHandler.delimitString(amexSummaryBean.getPaymentId());
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

    Collection amexSummaryBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      AmexSummaryBean amexSummaryBean = new AmexSummaryBean();
      load(dataSetRow, amexSummaryBean);
      amexSummaryBeanColl.add(amexSummaryBean);
    }

    return amexSummaryBeanColl;
  }

  public boolean isPresent(AmexSummaryBean bean) throws
      BaseException {
    Connection connection = null;
    boolean result = false;
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("paymentId",
                          SearchCriterion.EQUALS,
                          bean.getPaymentId());
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