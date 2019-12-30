package com.tcmis.client.seagate.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.seagate.beans.AmexAdjustmentBean;
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
 * CLASSNAME: AmexAdjustmentBeanFactory <br>
 * @version: 1.0, Apr 5, 2005 <br>
 *****************************************************************************/

public class AmexAdjustmentBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_ADJUSTMENT_ID = "ADJUSTMENT_ID";
  public String ATTRIBUTE_ADJUSTMENT_DATE = "ADJUSTMENT_DATE";
  public String ATTRIBUTE_ADJUSTMENT_AMOUNT = "ADJUSTMENT_AMOUNT";
  public String ATTRIBUTE_DISCOUNT_AMOUNT = "DISCOUNT_AMOUNT";
  public String ATTRIBUTE_DISCOUNT_RATE = "DISCOUNT_RATE";
  public String ATTRIBUTE_SERVICE_FEE_AMOUNT = "SERVICE_FEE_AMOUNT";
  public String ATTRIBUTE_SERVICE_FEE_RATE = "SERVICE_FEE_RATE";
  public String ATTRIBUTE_ADJUSTMENT_REASON = "ADJUSTMENT_REASON";
  public String ATTRIBUTE_PAYMENT_ID = "PAYMENT_ID";
  public String ATTRIBUTE_PAYMENT_DATE = "PAYMENT_DATE";

  //table name
  public String TABLE = "AMEX_ADJUSTMENT";

  //constructor
  public AmexAdjustmentBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("adjustmentId")) {
      return ATTRIBUTE_ADJUSTMENT_ID;
    }
    else if (attributeName.equals("adjustmentDate")) {
      return ATTRIBUTE_ADJUSTMENT_DATE;
    }
    else if (attributeName.equals("adjustmentAmount")) {
      return ATTRIBUTE_ADJUSTMENT_AMOUNT;
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
    else if (attributeName.equals("adjustmentReason")) {
      return ATTRIBUTE_ADJUSTMENT_REASON;
    }
    else if (attributeName.equals("paymentId")) {
      return ATTRIBUTE_PAYMENT_ID;
    }
    else if (attributeName.equals("paymentDate")) {
      return ATTRIBUTE_PAYMENT_DATE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, AmexAdjustmentBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(AmexAdjustmentBean amexAdjustmentBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("adjustmentId", "SearchCriterion.EQUALS",
     "" + amexAdjustmentBean.getAdjustmentId());
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
   public int delete(AmexAdjustmentBean amexAdjustmentBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("adjustmentId", "SearchCriterion.EQUALS",
     "" + amexAdjustmentBean.getAdjustmentId());
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
  public int insert(AmexAdjustmentBean amexAdjustmentBean) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(amexAdjustmentBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(AmexAdjustmentBean amexAdjustmentBean, Connection conn) throws
      BaseException {

    SqlManager sqlManager = new SqlManager();

    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_ADJUSTMENT_ID + "," +
        ATTRIBUTE_ADJUSTMENT_DATE + "," +
        ATTRIBUTE_ADJUSTMENT_AMOUNT + "," +
        ATTRIBUTE_DISCOUNT_AMOUNT + "," +
        ATTRIBUTE_DISCOUNT_RATE + "," +
        ATTRIBUTE_SERVICE_FEE_AMOUNT + "," +
        ATTRIBUTE_SERVICE_FEE_RATE + "," +
        ATTRIBUTE_ADJUSTMENT_REASON + "," +
        ATTRIBUTE_PAYMENT_ID + "," +
        ATTRIBUTE_PAYMENT_DATE + ")" +
        "values (" +
        amexAdjustmentBean.getAdjustmentId() + "," +
        DateHandler.getOracleToDateFunction(amexAdjustmentBean.
                                            getAdjustmentDate()) + "," +
        amexAdjustmentBean.getAdjustmentAmount() +
        "," +
        amexAdjustmentBean.getDiscountAmount() + "," +
        amexAdjustmentBean.getDiscountRate() + "," +
        amexAdjustmentBean.getServiceFeeAmount() +
        "," +
        amexAdjustmentBean.getServiceFeeRate() + "," +
        SqlHandler.delimitString(amexAdjustmentBean.getAdjustmentReason()) +
        "," +
        SqlHandler.delimitString(amexAdjustmentBean.getPaymentId()) + "," +
        DateHandler.getOracleToDateFunction(amexAdjustmentBean.getPaymentDate()) +
        ")";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    return sqlManager.update(conn, query);
  }

  //update
  public int update(AmexAdjustmentBean amexAdjustmentBean) throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = update(amexAdjustmentBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int update(AmexAdjustmentBean amexAdjustmentBean, Connection conn) throws
      BaseException {
    String query = "update " + TABLE + " set " +
        ATTRIBUTE_ADJUSTMENT_ID + "=" +
        amexAdjustmentBean.getAdjustmentId() + "," +
        ATTRIBUTE_ADJUSTMENT_DATE + "=" +
        DateHandler.getOracleToDateFunction(amexAdjustmentBean.
                                            getAdjustmentDate()) + "," +
        ATTRIBUTE_ADJUSTMENT_AMOUNT + "=" +
        amexAdjustmentBean.getAdjustmentAmount() +
        "," +
        ATTRIBUTE_DISCOUNT_AMOUNT + "=" +
        amexAdjustmentBean.getDiscountAmount() + "," +
        ATTRIBUTE_DISCOUNT_RATE + "=" +
        amexAdjustmentBean.getDiscountRate() + "," +
        ATTRIBUTE_SERVICE_FEE_AMOUNT + "=" +
        amexAdjustmentBean.getServiceFeeAmount() +
        "," +
        ATTRIBUTE_SERVICE_FEE_RATE + "=" +
        amexAdjustmentBean.getServiceFeeRate() + "," +
        ATTRIBUTE_ADJUSTMENT_REASON + "=" +
        SqlHandler.delimitString(amexAdjustmentBean.getAdjustmentReason()) +
        "," +
        ATTRIBUTE_PAYMENT_ID + "=" +
        SqlHandler.delimitString(amexAdjustmentBean.getPaymentId()) + "," +
        ATTRIBUTE_PAYMENT_DATE + "=" +
        DateHandler.getOracleToDateFunction(amexAdjustmentBean.getPaymentDate()) +
        " " +
        "where " + ATTRIBUTE_ADJUSTMENT_ID + "=" +
        amexAdjustmentBean.getAdjustmentId();
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

    Collection amexAdjustmentBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      AmexAdjustmentBean amexAdjustmentBean = new AmexAdjustmentBean();
      load(dataSetRow, amexAdjustmentBean);
      amexAdjustmentBeanColl.add(amexAdjustmentBean);
    }

    return amexAdjustmentBeanColl;
  }

  public boolean isPresent(AmexAdjustmentBean bean) throws
      BaseException {
    Connection connection = null;
    boolean result = false;
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("adjustmentId",
                          SearchCriterion.EQUALS,
                          bean.getAdjustmentId().toString());
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