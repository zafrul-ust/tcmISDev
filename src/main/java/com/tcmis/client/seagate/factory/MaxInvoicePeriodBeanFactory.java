package com.tcmis.client.seagate.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.seagate.beans.MaxInvoicePeriodBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: MaxInvoicePeriodBeanFactory <br>
 * @version: 1.0, Feb 9, 2005 <br>
 *****************************************************************************/

public class MaxInvoicePeriodBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_MAX_PERIOD = "MAX_PERIOD";

  //table name
  public String TABLE = "MAX_INVOICE_PERIOD";

  //constructor
  public MaxInvoicePeriodBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("maxPeriod")) {
      return ATTRIBUTE_MAX_PERIOD;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, MaxInvoicePeriodBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(MaxInvoicePeriodBean maxInvoicePeriodBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("maxPeriod", "SearchCriterion.EQUALS",
     "" + maxInvoicePeriodBean.getMaxPeriod());
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
       public int delete(MaxInvoicePeriodBean maxInvoicePeriodBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("maxPeriod", "SearchCriterion.EQUALS",
     "" + maxInvoicePeriodBean.getMaxPeriod());
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

//you need to verify the primary key(s) before uncommenting this
  /*
//insert
   public int insert(MaxInvoicePeriodBean maxInvoicePeriodBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(maxInvoicePeriodBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int insert(MaxInvoicePeriodBean maxInvoicePeriodBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_MAX_PERIOD + ")" +
     StringHandler.nullIfZero(maxInvoicePeriodBean.getMaxPeriod()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(MaxInvoicePeriodBean maxInvoicePeriodBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(maxInvoicePeriodBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int update(MaxInvoicePeriodBean maxInvoicePeriodBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_MAX_PERIOD + "=" +
      StringHandler.nullIfZero(maxInvoicePeriodBean.getMaxPeriod()) + " " +
     "where " + ATTRIBUTE_MAX_PERIOD + "=" +
      StringHandler.nullIfZero(maxInvoicePeriodBean.getMaxPeriod());
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

  public Collection select(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection maxInvoicePeriodBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      MaxInvoicePeriodBean maxInvoicePeriodBean = new MaxInvoicePeriodBean();
      load(dataSetRow, maxInvoicePeriodBean);
      maxInvoicePeriodBeanColl.add(maxInvoicePeriodBean);
    }

    return maxInvoicePeriodBeanColl;
  }
}