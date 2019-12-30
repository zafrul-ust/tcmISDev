package com.tcmis.client.seagate.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.seagate.beans.AmexSocBean;
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
 * CLASSNAME: AmexSocBeanFactory <br>
 * @version: 1.0, Apr 5, 2005 <br>
 *****************************************************************************/

public class AmexSocBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_SOC_ID = "SOC_ID";
  public String ATTRIBUTE_SOC_LINE = "SOC_LINE";
  public String ATTRIBUTE_PAYMENT_ID = "PAYMENT_ID";
  public String ATTRIBUTE_SUBMIT_DATE = "SUBMIT_DATE";
  public String ATTRIBUTE_PROCESS_DATE = "PROCESS_DATE";
  public String ATTRIBUTE_SOC_AMOUNT = "SOC_AMOUNT";
  public String ATTRIBUTE_NET_SOC_AMOUNT = "NET_SOC_AMOUNT";
  public String ATTRIBUTE_DISCOUNT_AMOUNT = "DISCOUNT_AMOUNT";
  public String ATTRIBUTE_DISCOUNT_RATE = "DISCOUNT_RATE";
  public String ATTRIBUTE_ROC_COUNT = "ROC_COUNT";

  //table name
  public String TABLE = "AMEX_SOC";

  //constructor
  public AmexSocBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("submitDate")) {
      return ATTRIBUTE_SUBMIT_DATE;
    }
    else if (attributeName.equals("processDate")) {
      return ATTRIBUTE_PROCESS_DATE;
    }
    else if (attributeName.equals("socAmount")) {
      return ATTRIBUTE_SOC_AMOUNT;
    }
    else if (attributeName.equals("netSocAmount")) {
      return ATTRIBUTE_NET_SOC_AMOUNT;
    }
    else if (attributeName.equals("discountAmount")) {
      return ATTRIBUTE_DISCOUNT_AMOUNT;
    }
    else if (attributeName.equals("discountRate")) {
      return ATTRIBUTE_DISCOUNT_RATE;
    }
    else if (attributeName.equals("rocCount")) {
      return ATTRIBUTE_ROC_COUNT;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, AmexSocBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(AmexSocBean amexSocBean)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("socId", "SearchCriterion.EQUALS",
     "" + amexSocBean.getSocId());
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
   public int delete(AmexSocBean amexSocBean, Connection conn)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("socId", "SearchCriterion.EQUALS",
     "" + amexSocBean.getSocId());
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
  public int insert(AmexSocBean amexSocBean) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(amexSocBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(AmexSocBean amexSocBean, Connection conn) throws
      BaseException {

    SqlManager sqlManager = new SqlManager();

    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_SOC_ID + "," +
        ATTRIBUTE_SOC_LINE + "," +
        ATTRIBUTE_PAYMENT_ID + "," +
        ATTRIBUTE_SUBMIT_DATE + "," +
        ATTRIBUTE_PROCESS_DATE + "," +
        ATTRIBUTE_SOC_AMOUNT + "," +
        ATTRIBUTE_NET_SOC_AMOUNT + "," +
        ATTRIBUTE_DISCOUNT_AMOUNT + "," +
        ATTRIBUTE_DISCOUNT_RATE + "," +
        ATTRIBUTE_ROC_COUNT + ")" +
        "values (" +
        amexSocBean.getSocId() + "," +
        amexSocBean.getSocLine() + "," +
        SqlHandler.delimitString(amexSocBean.getPaymentId()) + "," +
        DateHandler.getOracleToDateFunction(amexSocBean.getSubmitDate()) + "," +
        DateHandler.getOracleToDateFunction(amexSocBean.getProcessDate()) + "," +
        amexSocBean.getSocAmount() + "," +
        amexSocBean.getNetSocAmount() + "," +
        amexSocBean.getDiscountAmount() + "," +
        amexSocBean.getDiscountRate() + "," +
        amexSocBean.getRocCount() + ")";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    return sqlManager.update(conn, query);
  }

//update
  public int update(AmexSocBean amexSocBean) throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = update(amexSocBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int update(AmexSocBean amexSocBean, Connection conn) throws
      BaseException {
    String query = "update " + TABLE + " set " +
        ATTRIBUTE_SOC_ID + "=" +
        amexSocBean.getSocId() + "," +
        ATTRIBUTE_SOC_LINE + "=" +
        amexSocBean.getSocLine() + "," +
        ATTRIBUTE_PAYMENT_ID + "=" +
        SqlHandler.delimitString(amexSocBean.getPaymentId()) + "," +
        ATTRIBUTE_SUBMIT_DATE + "=" +
        DateHandler.getOracleToDateFunction(amexSocBean.getSubmitDate()) + "," +
        ATTRIBUTE_PROCESS_DATE + "=" +
        DateHandler.getOracleToDateFunction(amexSocBean.getProcessDate()) + "," +
        ATTRIBUTE_SOC_AMOUNT + "=" +
        amexSocBean.getSocAmount() + "," +
        ATTRIBUTE_NET_SOC_AMOUNT + "=" +
        amexSocBean.getNetSocAmount() + "," +
        ATTRIBUTE_DISCOUNT_AMOUNT + "=" +
        amexSocBean.getDiscountAmount() + "," +
        ATTRIBUTE_DISCOUNT_RATE + "=" +
        amexSocBean.getDiscountRate() + "," +
        ATTRIBUTE_ROC_COUNT + "=" +
        amexSocBean.getRocCount() + " " +
        "where " + ATTRIBUTE_SOC_ID + "=" +
        amexSocBean.getSocId() + " and " +
        ATTRIBUTE_SOC_LINE + "=" +
        amexSocBean.getSocLine() + " and " +
        ATTRIBUTE_PAYMENT_ID + "=" +
        SqlHandler.delimitString(amexSocBean.getPaymentId());
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

    Collection amexSocBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      AmexSocBean amexSocBean = new AmexSocBean();
      load(dataSetRow, amexSocBean);
      amexSocBeanColl.add(amexSocBean);
    }

    return amexSocBeanColl;
  }

  public boolean isPresent(AmexSocBean bean) throws
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