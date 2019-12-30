package com.tcmis.internal.invoice.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.Connection;

import java.text.ParseException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.internal.invoice.beans.InvoicePeriodServiceDatesBean;

/******************************************************************************
 * CLASSNAME: InvoicePeriodServiceDatesBeanFactory <br>
 * @version: 1.0, May 8, 2006 <br>
 *****************************************************************************/

public class InvoicePeriodServiceDatesBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_START_DATE = "START_DATE";
  public String ATTRIBUTE_END_DATE = "END_DATE";

  //table name
  public String TABLE = "INVOICE_PERIOD_SERVICE_DATES";

  //constructor
  public InvoicePeriodServiceDatesBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else if (attributeName.equals("startDate")) {
      return ATTRIBUTE_START_DATE;
    }
    else if (attributeName.equals("endDate")) {
      return ATTRIBUTE_END_DATE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoicePeriodServiceDatesBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
       public int delete(InvoicePeriodServiceDatesBean invoicePeriodServiceDatesBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoicePeriod", "SearchCriterion.EQUALS",
     "" + invoicePeriodServiceDatesBean.getInvoicePeriod());
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
   public int delete(InvoicePeriodServiceDatesBean invoicePeriodServiceDatesBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoicePeriod", "SearchCriterion.EQUALS",
     "" + invoicePeriodServiceDatesBean.getInvoicePeriod());
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
       public int insert(InvoicePeriodServiceDatesBean invoicePeriodServiceDatesBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoicePeriodServiceDatesBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoicePeriodServiceDatesBean invoicePeriodServiceDatesBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_START_DATE + "," +
     ATTRIBUTE_END_DATE + ")" +
     " values (" +
     invoicePeriodServiceDatesBean.getInvoicePeriod() + "," +
     DateHandler.getOracleToDateFunction(invoicePeriodServiceDatesBean.getStartDate()) + "," +
     DateHandler.getOracleToDateFunction(invoicePeriodServiceDatesBean.getEndDate()) + ")";
    return sqlManager.update(conn, query);
   }
//update
       public int update(InvoicePeriodServiceDatesBean invoicePeriodServiceDatesBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoicePeriodServiceDatesBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoicePeriodServiceDatesBean invoicePeriodServiceDatesBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoicePeriodServiceDatesBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_START_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoicePeriodServiceDatesBean.getStartDate()) + "," +
     ATTRIBUTE_END_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoicePeriodServiceDatesBean.getEndDate()) + " " +
     "where " + ATTRIBUTE_INVOICE_PERIOD + "=" +
      invoicePeriodServiceDatesBean.getInvoicePeriod();
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

    Collection invoicePeriodServiceDatesBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoicePeriodServiceDatesBean invoicePeriodServiceDatesBean = new
          InvoicePeriodServiceDatesBean();
      load(dataSetRow, invoicePeriodServiceDatesBean);
      invoicePeriodServiceDatesBeanColl.add(invoicePeriodServiceDatesBean);
    }

    return invoicePeriodServiceDatesBeanColl;
  }
}