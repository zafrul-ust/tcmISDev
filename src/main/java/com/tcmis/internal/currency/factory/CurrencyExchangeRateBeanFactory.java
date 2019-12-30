package com.tcmis.internal.currency.factory;

import java.math.BigDecimal;
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
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.currency.beans.CurrencyExchangeRateBean;

/******************************************************************************
 * CLASSNAME: CurrencyExchangeRateBeanFactory <br>
 * @version: 1.0, Feb 1, 2005 <br>
 *****************************************************************************/

public class CurrencyExchangeRateBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
  public String ATTRIBUTE_EXCHANGE_RATE = "EXCHANGE_RATE";
  public String ATTRIBUTE_START_DATE = "START_DATE";
  public String ATTRIBUTE_END_DATE = "END_DATE";
  public String ATTRIBUTE_EXCHANGE_RATE_SOURCE = "EXCHANGE_RATE_SOURCE";

  //table name
  public String TABLE = "CURRENCY_EXCHANGE_RATE";

  //constructor
  public CurrencyExchangeRateBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("currencyId")) {
      return ATTRIBUTE_CURRENCY_ID;
    }
    else if (attributeName.equals("exchangeRate")) {
      return ATTRIBUTE_EXCHANGE_RATE;
    }
    else if (attributeName.equals("startDate")) {
      return ATTRIBUTE_START_DATE;
    }
    else if (attributeName.equals("endDate")) {
      return ATTRIBUTE_END_DATE;
    }
    else if (attributeName.equals("exchangeRateSource")) {
        return ATTRIBUTE_EXCHANGE_RATE_SOURCE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, CurrencyExchangeRateBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(CurrencyExchangeRateBean currencyExchangeRateBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("currencyId", "=",
     "" + currencyExchangeRateBean.getCurrencyId());
    Connection connection = this.getDbManager().getConnection();
    int result = this.delete(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return result;
   }
   public int delete(CurrencyExchangeRateBean currencyExchangeRateBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("currencyId", "=",
     "" + currencyExchangeRateBean.getCurrencyId());
    return delete(criteria, conn);
   }
   */

  public int delete(SearchCriteria criteria) throws BaseException {

    Connection connection = getDbManager().getConnection();
    int result = delete(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return result;
  }

  public int delete(SearchCriteria criteria, Connection conn) throws
      BaseException {

    String sqlQuery = " delete from " + TABLE + " " +
        getWhereClause(criteria);

    return new SqlManager().update(conn, sqlQuery);
  }

//insert
  public int insert(CurrencyExchangeRateBean currencyExchangeRateBean) throws
      BaseException {
    Connection connection = getDbManager().getConnection();
    int result = insert(currencyExchangeRateBean, connection);
    this.getDbManager().returnConnection(connection);
    return result;
  }

  public int insert(CurrencyExchangeRateBean currencyExchangeRateBean,
                    Connection conn) throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_CURRENCY_ID + "," +
        ATTRIBUTE_START_DATE + "," + 
        ATTRIBUTE_EXCHANGE_RATE_SOURCE + "," +
        ATTRIBUTE_EXCHANGE_RATE + ") VALUES (" +
        SqlHandler.delimitString(currencyExchangeRateBean.getCurrencyId()) +        "," +
        " sysdate ," +
        SqlHandler.delimitString(currencyExchangeRateBean.getExchangeRateSource()) +        "," +
        StringHandler.nullIfZero(currencyExchangeRateBean.getExchangeRate().setScale(5, BigDecimal.ROUND_HALF_UP)) + ")"
        ;
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    return sqlManager.update(conn, query);
  }

  //you need to verify the primary key(s) before uncommenting this
  /*
//update
     public int update(CurrencyExchangeRateBean currencyExchangeRateBean)
      throws BaseException {
      Connection connection = getDbManager().getConnection();
      int result = update(criteria, connection);
      this.getDbManager().returnConnection(connection);
      return result;
     }
     public int update(CurrencyExchangeRateBean currencyExchangeRateBean, Connection conn)
      throws BaseException {
      String query  = "update " + TABLE + " set " +
       ATTRIBUTE_CURRENCY_ID + "=" +
       SqlHandler.delimitString(currencyExchangeRateBean.getCurrencyId()) + "," +
       ATTRIBUTE_EXCHANGE_RATE + "=" +
       StringHandler.nullIfZero(currencyExchangeRateBean.getExchangeRate()) + "," +
       ATTRIBUTE_START_DATE + "=" +
        DateHandler.getOracleToDateFunction(currencyExchangeRateBean.getStartDate()) + "," +
       ATTRIBUTE_END_DATE + "=" +
        DateHandler.getOracleToDateFunction(currencyExchangeRateBean.getEndDate()) + " " +
       "where " + ATTRIBUTE_CURRENCY_ID + "=" +
        StringHandler.nullIfZero(currencyExchangeRateBean.getCurrencyId());
      return new SqlManager().update(conn, query);
     }
   */

  //select
  public Collection select(SearchCriteria criteria) throws BaseException {

    Connection connection = this.getDbManager().getConnection();
    Collection c = select(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return c;
  }

  public Collection select(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection currencyExchangeRateBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    //System.out.println("QUERY:" + query);
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CurrencyExchangeRateBean currencyExchangeRateBean = new
          CurrencyExchangeRateBean();
      load(dataSetRow, currencyExchangeRateBean);
      currencyExchangeRateBeanColl.add(currencyExchangeRateBean);
    }

    return currencyExchangeRateBeanColl;
  }
}