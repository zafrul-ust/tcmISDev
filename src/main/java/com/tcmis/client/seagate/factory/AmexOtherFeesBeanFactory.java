package com.tcmis.client.seagate.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.seagate.beans.AmexOtherFeesBean;
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
 * CLASSNAME: AmexOtherFeesBeanFactory <br>
 * @version: 1.0, Apr 5, 2005 <br>
 *****************************************************************************/

public class AmexOtherFeesBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_FEE_ID = "FEE_ID";
  public String ATTRIBUTE_ASSET_BILLING_AMOUNT = "ASSET_BILLING_AMOUNT";
  public String ATTRIBUTE_ASSET_BILLING_DESCRIPTION =
      "ASSET_BILLING_DESCRIPTION";
  public String ATTRIBUTE_COMMISSION_AMOUNT = "COMMISSION_AMOUNT";
  public String ATTRIBUTE_COMMISSION_DESCRIPTION = "COMMISSION_DESCRIPTION";
  public String ATTRIBUTE_OTHER_FEE_AMOUNT = "OTHER_FEE_AMOUNT";
  public String ATTRIBUTE_OTHER_FEE_DESCRIPTION = "OTHER_FEE_DESCRIPTION";
  public String ATTRIBUTE_PROCESS_DATE = "PROCESS_DATE";
  public String ATTRIBUTE_PAYMENT_ID = "PAYMENT_ID";
  public String ATTRIBUTE_PAYMENT_DATE = "PAYMENT_DATE";

  //table name
  public String TABLE = "AMEX_OTHER_FEES";

  //constructor
  public AmexOtherFeesBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("feeId")) {
      return ATTRIBUTE_FEE_ID;
    }
    else if (attributeName.equals("assetBillingAmount")) {
      return ATTRIBUTE_ASSET_BILLING_AMOUNT;
    }
    else if (attributeName.equals("assetBillingDescription")) {
      return ATTRIBUTE_ASSET_BILLING_DESCRIPTION;
    }
    else if (attributeName.equals("commissionAmount")) {
      return ATTRIBUTE_COMMISSION_AMOUNT;
    }
    else if (attributeName.equals("commissionDescription")) {
      return ATTRIBUTE_COMMISSION_DESCRIPTION;
    }
    else if (attributeName.equals("otherFeeAmount")) {
      return ATTRIBUTE_OTHER_FEE_AMOUNT;
    }
    else if (attributeName.equals("otherFeeDescription")) {
      return ATTRIBUTE_OTHER_FEE_DESCRIPTION;
    }
    else if (attributeName.equals("processDate")) {
      return ATTRIBUTE_PROCESS_DATE;
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
    return super.getType(attributeName, AmexOtherFeesBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(AmexOtherFeesBean amexOtherFeesBean)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("feeId", "SearchCriterion.EQUALS",
     "" + amexOtherFeesBean.getFeeId());
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
   public int delete(AmexOtherFeesBean amexOtherFeesBean, Connection conn)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("feeId", "SearchCriterion.EQUALS",
     "" + amexOtherFeesBean.getFeeId());
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
  public int insert(AmexOtherFeesBean amexOtherFeesBean) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(amexOtherFeesBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(AmexOtherFeesBean amexOtherFeesBean, Connection conn) throws
      BaseException {

    SqlManager sqlManager = new SqlManager();

    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_FEE_ID + "," +
        ATTRIBUTE_ASSET_BILLING_AMOUNT + "," +
        ATTRIBUTE_ASSET_BILLING_DESCRIPTION + "," +
        ATTRIBUTE_COMMISSION_AMOUNT + "," +
        ATTRIBUTE_COMMISSION_DESCRIPTION + "," +
        ATTRIBUTE_OTHER_FEE_AMOUNT + "," +
        ATTRIBUTE_OTHER_FEE_DESCRIPTION + "," +
        ATTRIBUTE_PROCESS_DATE + "," +
        ATTRIBUTE_PAYMENT_ID + "," +
        ATTRIBUTE_PAYMENT_DATE + ")" +
        "values (" +
        amexOtherFeesBean.getFeeId() + "," +
        amexOtherFeesBean.getAssetBillingAmount() +
        "," +
        SqlHandler.delimitString(amexOtherFeesBean.getAssetBillingDescription()) +
        "," +
        amexOtherFeesBean.getCommissionAmount() + "," +
        SqlHandler.delimitString(amexOtherFeesBean.getCommissionDescription()) +
        "," +
        amexOtherFeesBean.getOtherFeeAmount() + "," +
        SqlHandler.delimitString(amexOtherFeesBean.getOtherFeeDescription()) +
        "," +
        DateHandler.getOracleToDateFunction(amexOtherFeesBean.getProcessDate()) +
        "," +
        SqlHandler.delimitString(amexOtherFeesBean.getPaymentId()) + "," +
        DateHandler.getOracleToDateFunction(amexOtherFeesBean.getPaymentDate()) +
        ")";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    return sqlManager.update(conn, query);
  }

//update
  public int update(AmexOtherFeesBean amexOtherFeesBean) throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = update(amexOtherFeesBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int update(AmexOtherFeesBean amexOtherFeesBean, Connection conn) throws
      BaseException {
    String query = "update " + TABLE + " set " +
        ATTRIBUTE_FEE_ID + "=" +
        amexOtherFeesBean.getFeeId() + "," +
        ATTRIBUTE_ASSET_BILLING_AMOUNT + "=" +
        amexOtherFeesBean.getAssetBillingAmount() +
        "," +
        ATTRIBUTE_ASSET_BILLING_DESCRIPTION + "=" +
        SqlHandler.delimitString(amexOtherFeesBean.getAssetBillingDescription()) +
        "," +
        ATTRIBUTE_COMMISSION_AMOUNT + "=" +
        amexOtherFeesBean.getCommissionAmount() + "," +
        ATTRIBUTE_COMMISSION_DESCRIPTION + "=" +
        SqlHandler.delimitString(amexOtherFeesBean.getCommissionDescription()) +
        "," +
        ATTRIBUTE_OTHER_FEE_AMOUNT + "=" +
        amexOtherFeesBean.getOtherFeeAmount() + "," +
        ATTRIBUTE_OTHER_FEE_DESCRIPTION + "=" +
        SqlHandler.delimitString(amexOtherFeesBean.getOtherFeeDescription()) +
        "," +
        ATTRIBUTE_PROCESS_DATE + "=" +
        DateHandler.getOracleToDateFunction(amexOtherFeesBean.getProcessDate()) +
        "," +
        ATTRIBUTE_PAYMENT_ID + "=" +
        SqlHandler.delimitString(amexOtherFeesBean.getPaymentId()) + "," +
        ATTRIBUTE_PAYMENT_DATE + "=" +
        DateHandler.getOracleToDateFunction(amexOtherFeesBean.getPaymentDate()) +
        " " +
        "where " + ATTRIBUTE_FEE_ID + "=" +
        amexOtherFeesBean.getFeeId();
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

    Collection amexOtherFeesBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      AmexOtherFeesBean amexOtherFeesBean = new AmexOtherFeesBean();
      load(dataSetRow, amexOtherFeesBean);
      amexOtherFeesBeanColl.add(amexOtherFeesBean);
    }

    return amexOtherFeesBeanColl;
  }

  public boolean isPresent(AmexOtherFeesBean bean) throws
      BaseException {
    Connection connection = null;
    boolean result = false;
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("feeId",
                          SearchCriterion.EQUALS,
                          bean.getFeeId().toString());
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