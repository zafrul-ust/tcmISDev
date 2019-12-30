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
import com.tcmis.internal.invoice.beans.InvoicePaymentBean;

/******************************************************************************
 * CLASSNAME: InvoicePaymentBeanFactory <br>
 * @version: 1.0, Mar 16, 2006 <br>
 *****************************************************************************/

public class InvoicePaymentBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_PAYMENT_AMOUNT = "PAYMENT_AMOUNT";
  public String ATTRIBUTE_PAYMENT_ID = "PAYMENT_ID";
  public String ATTRIBUTE_PAYMENT_DATE = "PAYMENT_DATE";
  public String ATTRIBUTE_TRANSACTION_ID = "TRANSACTION_ID";

  //table name
  public String TABLE = "INVOICE_PAYMENT";

  //constructor
  public InvoicePaymentBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("paymentAmount")) {
      return ATTRIBUTE_PAYMENT_AMOUNT;
    }
    else if (attributeName.equals("paymentId")) {
      return ATTRIBUTE_PAYMENT_ID;
    }
    else if (attributeName.equals("paymentDate")) {
      return ATTRIBUTE_PAYMENT_DATE;
    }
    else if (attributeName.equals("transactionId")) {
      return ATTRIBUTE_TRANSACTION_ID;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoicePaymentBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoicePaymentBean invoicePaymentBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoicePaymentBean.getInvoice());
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
   public int delete(InvoicePaymentBean invoicePaymentBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoicePaymentBean.getInvoice());
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

  //insert
  public int insert(InvoicePaymentBean invoicePaymentBean) throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(invoicePaymentBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(InvoicePaymentBean invoicePaymentBean, Connection conn) throws
      BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_INVOICE + "," +
        ATTRIBUTE_COMPANY_ID + "," +
        ATTRIBUTE_PAYMENT_AMOUNT + "," +
        ATTRIBUTE_PAYMENT_ID + "," +
        ATTRIBUTE_PAYMENT_DATE + "," +
        ATTRIBUTE_TRANSACTION_ID + ")" +
        " values (" +
        invoicePaymentBean.getInvoice() + "," +
        SqlHandler.delimitString(invoicePaymentBean.getCompanyId()) + "," +
        invoicePaymentBean.getPaymentAmount() + "," +
        SqlHandler.delimitString(invoicePaymentBean.getPaymentId()) + "," +
        DateHandler.getOracleToDateFunction(invoicePaymentBean.getPaymentDate()) + "," +
        SqlHandler.delimitString(invoicePaymentBean.getTransactionId()) + ")";
    return sqlManager.update(conn, query);
  }

  /*
//update
   public int update(InvoicePaymentBean invoicePaymentBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoicePaymentBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoicePaymentBean invoicePaymentBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoicePaymentBean.getInvoice()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(invoicePaymentBean.getCompanyId()) + "," +
     ATTRIBUTE_PAYMENT_AMOUNT + "=" +
      StringHandler.nullIfZero(invoicePaymentBean.getPaymentAmount()) + "," +
     ATTRIBUTE_PAYMENT_ID + "=" +
      SqlHandler.delimitString(invoicePaymentBean.getPaymentId()) + "," +
     ATTRIBUTE_PAYMENT_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoicePaymentBean.getPaymentDate()) + "," +
     ATTRIBUTE_TRANSACTION_ID + "=" +
      SqlHandler.delimitString(invoicePaymentBean.getTransactionId()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      invoicePaymentBean.getInvoice();
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
    Collection invoicePaymentBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoicePaymentBean invoicePaymentBean = new InvoicePaymentBean();
      load(dataSetRow, invoicePaymentBean);
      invoicePaymentBeanColl.add(invoicePaymentBean);
    }
    return invoicePaymentBeanColl;
  }
}