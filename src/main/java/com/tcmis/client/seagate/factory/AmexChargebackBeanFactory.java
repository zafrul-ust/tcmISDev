package com.tcmis.client.seagate.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.seagate.beans.AmexChargebackBean;
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
 * CLASSNAME: AmexChargebackBeanFactory <br>
 * @version: 1.0, Apr 5, 2005 <br>
 *****************************************************************************/

public class AmexChargebackBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_SOC_ID = "SOC_ID";
  public String ATTRIBUTE_SOC_LINE = "SOC_LINE";
  public String ATTRIBUTE_PAYMENT_ID = "PAYMENT_ID";
  public String ATTRIBUTE_SOC_AMOUNT = "SOC_AMOUNT";
  public String ATTRIBUTE_CHARGEBACK_AMOUNT = "CHARGEBACK_AMOUNT";
  public String ATTRIBUTE_NET_CHARGEBACK_AMOUNT = "NET_CHARGEBACK_AMOUNT";
  public String ATTRIBUTE_DISCOUNT_AMOUNT = "DISCOUNT_AMOUNT";
  public String ATTRIBUTE_DISCOUNT_RATE = "DISCOUNT_RATE";
  public String ATTRIBUTE_SERVICE_FEE_AMOUNT = "SERVICE_FEE_AMOUNT";
  public String ATTRIBUTE_SERVICE_FEE_RATE = "SERVICE_FEE_RATE";
  public String ATTRIBUTE_SUBMIT_DATE = "SUBMIT_DATE";
  public String ATTRIBUTE_PROCESS_DATE = "PROCESS_DATE";
  public String ATTRIBUTE_CHARGEBACK_REASON = "CHARGEBACK_REASON";

  //table name
  public String TABLE = "AMEX_CHARGEBACK";

  //constructor
  public AmexChargebackBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("socAmount")) {
      return ATTRIBUTE_SOC_AMOUNT;
    }
    else if (attributeName.equals("chargebackAmount")) {
      return ATTRIBUTE_CHARGEBACK_AMOUNT;
    }
    else if (attributeName.equals("netChargebackAmount")) {
      return ATTRIBUTE_NET_CHARGEBACK_AMOUNT;
    }
    else if (attributeName.equals("discountAmount")) {
      return ATTRIBUTE_DISCOUNT_AMOUNT;
    }
    else if (attributeName.equals("discountRate")) {
      return ATTRIBUTE_DISCOUNT_RATE;
    }
    else if (attributeName.equals("serviceFeeAmount")) {
      return ATTRIBUTE_SERVICE_FEE_AMOUNT;
    }
    else if (attributeName.equals("serviceFeeRate")) {
      return ATTRIBUTE_SERVICE_FEE_RATE;
    }
    else if (attributeName.equals("submitDate")) {
      return ATTRIBUTE_SUBMIT_DATE;
    }
    else if (attributeName.equals("processDate")) {
      return ATTRIBUTE_PROCESS_DATE;
    }
    else if (attributeName.equals("chargebackReason")) {
      return ATTRIBUTE_CHARGEBACK_REASON;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, AmexChargebackBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(AmexChargebackBean amexChargebackBean)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("socId", "SearchCriterion.EQUALS",
     "" + amexChargebackBean.getSocId());
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
   public int delete(AmexChargebackBean amexChargebackBean, Connection conn)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("socId", "SearchCriterion.EQUALS",
     "" + amexChargebackBean.getSocId());
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
  public int insert(AmexChargebackBean amexChargebackBean) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(amexChargebackBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(AmexChargebackBean amexChargebackBean, Connection conn) throws
      BaseException {

    SqlManager sqlManager = new SqlManager();

    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_SOC_ID + "," +
        ATTRIBUTE_SOC_LINE + "," +
        ATTRIBUTE_PAYMENT_ID + "," +
        ATTRIBUTE_SOC_AMOUNT + "," +
        ATTRIBUTE_CHARGEBACK_AMOUNT + "," +
        ATTRIBUTE_NET_CHARGEBACK_AMOUNT + "," +
        ATTRIBUTE_DISCOUNT_AMOUNT + "," +
        ATTRIBUTE_DISCOUNT_RATE + "," +
        ATTRIBUTE_SERVICE_FEE_AMOUNT + "," +
        ATTRIBUTE_SERVICE_FEE_RATE + "," +
        ATTRIBUTE_SUBMIT_DATE + "," +
        ATTRIBUTE_PROCESS_DATE + "," +
        ATTRIBUTE_CHARGEBACK_REASON + ")" +
        "values (" +
        StringHandler.nullIfZero(amexChargebackBean.getSocId()) + "," +
        amexChargebackBean.getSocLine() + "," +
        SqlHandler.delimitString(amexChargebackBean.getPaymentId()) + "," +
        amexChargebackBean.getSocAmount() + "," +
        amexChargebackBean.getChargebackAmount() +
        "," +
        amexChargebackBean.getNetChargebackAmount() +
        "," +
        amexChargebackBean.getDiscountAmount() + "," +
        amexChargebackBean.getDiscountRate() + "," +
        amexChargebackBean.getServiceFeeAmount() +
        "," +
        amexChargebackBean.getServiceFeeRate() + "," +
        DateHandler.getOracleToDateFunction(amexChargebackBean.getSubmitDate()) +
        "," +
        DateHandler.getOracleToDateFunction(amexChargebackBean.getProcessDate()) +
        "," +
        SqlHandler.delimitString(amexChargebackBean.getChargebackReason()) +
        ")";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    return sqlManager.update(conn, query);
  }

//update
  public int update(AmexChargebackBean amexChargebackBean) throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = update(amexChargebackBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int update(AmexChargebackBean amexChargebackBean, Connection conn) throws
      BaseException {
    String query = "update " + TABLE + " set " +
        ATTRIBUTE_SOC_ID + "=" +
        amexChargebackBean.getSocId() + "," +
        ATTRIBUTE_SOC_LINE + "=" +
        amexChargebackBean.getSocLine() + "," +
        ATTRIBUTE_PAYMENT_ID + "=" +
        SqlHandler.delimitString(amexChargebackBean.getPaymentId()) + "," +
        ATTRIBUTE_SOC_AMOUNT + "=" +
        amexChargebackBean.getSocAmount() + "," +
        ATTRIBUTE_CHARGEBACK_AMOUNT + "=" +
        amexChargebackBean.getChargebackAmount() +
        "," +
        ATTRIBUTE_NET_CHARGEBACK_AMOUNT + "=" +
        amexChargebackBean.getNetChargebackAmount() +
        "," +
        ATTRIBUTE_DISCOUNT_AMOUNT + "=" +
        amexChargebackBean.getDiscountAmount() + "," +
        ATTRIBUTE_DISCOUNT_RATE + "=" +
        amexChargebackBean.getDiscountRate() + "," +
        ATTRIBUTE_SERVICE_FEE_AMOUNT + "=" +
        amexChargebackBean.getServiceFeeAmount() +
        "," +
        ATTRIBUTE_SERVICE_FEE_RATE + "=" +
        amexChargebackBean.getServiceFeeRate() + "," +
        ATTRIBUTE_SUBMIT_DATE + "=" +
        DateHandler.getOracleToDateFunction(amexChargebackBean.getSubmitDate()) +
        "," +
        ATTRIBUTE_PROCESS_DATE + "=" +
        DateHandler.getOracleToDateFunction(amexChargebackBean.getProcessDate()) +
        "," +
        ATTRIBUTE_CHARGEBACK_REASON + "=" +
        SqlHandler.delimitString(amexChargebackBean.getChargebackReason()) +
        " " +
        "where " + ATTRIBUTE_SOC_ID + "=" +
        amexChargebackBean.getSocId() + " and " +
        ATTRIBUTE_SOC_LINE + "=" +
        amexChargebackBean.getSocLine() + " and " +
        ATTRIBUTE_PAYMENT_ID + "=" +
        SqlHandler.delimitString(amexChargebackBean.getPaymentId());
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

    Collection amexChargebackBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      AmexChargebackBean amexChargebackBean = new AmexChargebackBean();
      load(dataSetRow, amexChargebackBean);
      amexChargebackBeanColl.add(amexChargebackBean);
    }

    return amexChargebackBeanColl;
  }

  public boolean isPresent(AmexChargebackBean bean) throws
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