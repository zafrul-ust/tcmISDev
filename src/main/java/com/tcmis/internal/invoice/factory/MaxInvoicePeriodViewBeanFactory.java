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
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.internal.invoice.beans.MaxInvoicePeriodViewBean;

/******************************************************************************
 * CLASSNAME: MaxInvoicePeriodViewBeanFactory <br>
 * @version: 1.0, Dec 7, 2005 <br>
 *****************************************************************************/

public class MaxInvoicePeriodViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_INVOICE_GROUP = "INVOICE_GROUP";
  public String ATTRIBUTE_MAX_PERIOD = "MAX_PERIOD";

  //table name
  public String TABLE = "MAX_INVOICE_PERIOD_VIEW";

  //constructor
  public MaxInvoicePeriodViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("invoiceGroup")) {
      return ATTRIBUTE_INVOICE_GROUP;
    }
    else if (attributeName.equals("maxPeriod")) {
      return ATTRIBUTE_MAX_PERIOD;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, MaxInvoicePeriodViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(MaxInvoicePeriodViewBean maxInvoicePeriodViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
     "" + maxInvoicePeriodViewBean.getCompanyId());
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
   public int delete(MaxInvoicePeriodViewBean maxInvoicePeriodViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
     "" + maxInvoicePeriodViewBean.getCompanyId());
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
  /*
//insert
   public int insert(MaxInvoicePeriodViewBean maxInvoicePeriodViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(maxInvoicePeriodViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(MaxInvoicePeriodViewBean maxInvoicePeriodViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_INVOICE_GROUP + "," +
     ATTRIBUTE_MAX_PERIOD + ")" +
     " values (" +
     SqlHandler.delimitString(maxInvoicePeriodViewBean.getCompanyId()) + "," +
     SqlHandler.delimitString(maxInvoicePeriodViewBean.getInvoiceGroup()) + "," +
     maxInvoicePeriodViewBean.getMaxPeriod() + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(MaxInvoicePeriodViewBean maxInvoicePeriodViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(maxInvoicePeriodViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(MaxInvoicePeriodViewBean maxInvoicePeriodViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(maxInvoicePeriodViewBean.getCompanyId()) + "," +
     ATTRIBUTE_INVOICE_GROUP + "=" +
      SqlHandler.delimitString(maxInvoicePeriodViewBean.getInvoiceGroup()) + "," +
     ATTRIBUTE_MAX_PERIOD + "=" +
      StringHandler.nullIfZero(maxInvoicePeriodViewBean.getMaxPeriod()) + " " +
     "where " + ATTRIBUTE_COMPANY_ID + "=" +
      maxInvoicePeriodViewBean.getCompanyId();
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
    Collection maxInvoicePeriodViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      MaxInvoicePeriodViewBean maxInvoicePeriodViewBean = new MaxInvoicePeriodViewBean();
      load(dataSetRow, maxInvoicePeriodViewBean);
      maxInvoicePeriodViewBeanColl.add(maxInvoicePeriodViewBean);
    }
    return maxInvoicePeriodViewBeanColl;
  }
}