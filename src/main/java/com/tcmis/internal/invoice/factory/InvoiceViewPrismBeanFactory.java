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
import com.tcmis.internal.invoice.beans.InvoiceViewPrismBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewPrismBeanFactory <br>
 * @version: 1.0, May 16, 2007 <br>
 *****************************************************************************/

public class InvoiceViewPrismBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_QUANTITY = "QUANTITY";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";

  //table name
  public String TABLE = "INVOICE_VIEW_PRISM";

  //constructor
  public InvoiceViewPrismBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("quantity")) {
      return ATTRIBUTE_QUANTITY;
    }
    else if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceViewPrismBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceViewPrismBean invoiceViewPrismBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewPrismBean.getInvoice());
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
       public int delete(InvoiceViewPrismBean invoiceViewPrismBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewPrismBean.getInvoice());
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
   public int insert(InvoiceViewPrismBean invoiceViewPrismBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceViewPrismBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int insert(InvoiceViewPrismBean invoiceViewPrismBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_QUANTITY + "," +
     ATTRIBUTE_INVOICE_PERIOD + ")" +
     " values (" +
     invoiceViewPrismBean.getInvoice() + "," +
     invoiceViewPrismBean.getQuantity() + "," +
     invoiceViewPrismBean.getInvoicePeriod() + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceViewPrismBean invoiceViewPrismBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceViewPrismBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int update(InvoiceViewPrismBean invoiceViewPrismBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewPrismBean.getInvoice()) + "," +
     ATTRIBUTE_QUANTITY + "=" +
      StringHandler.nullIfZero(invoiceViewPrismBean.getQuantity()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoiceViewPrismBean.getInvoicePeriod()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      invoiceViewPrismBean.getInvoice();
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws
      BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(criteria, sortCriteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria,
                           Connection conn) throws BaseException {
    Collection invoiceViewPrismBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewPrismBean invoiceViewPrismBean = new InvoiceViewPrismBean();
      load(dataSetRow, invoiceViewPrismBean);
      invoiceViewPrismBeanColl.add(invoiceViewPrismBean);
    }
    return invoiceViewPrismBeanColl;
  }
}