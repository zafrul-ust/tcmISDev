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
import com.tcmis.internal.invoice.beans.InvoiceViewCalBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewCalBeanFactory <br>
 * @version: 1.0, Mar 3, 2005 <br>
 *****************************************************************************/

public class InvoiceViewCalBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
  public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_START_DATE = "START_DATE";
  public String ATTRIBUTE_END_DATE = "END_DATE";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";

  //table name
  public String TABLE = "INVOICE_VIEW_CAL";

  //constructor
  public InvoiceViewCalBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("invoiceAmount")) {
      return ATTRIBUTE_INVOICE_AMOUNT;
    }
    else if (attributeName.equals("accountNumber")) {
      return ATTRIBUTE_ACCOUNT_NUMBER;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else if (attributeName.equals("startDate")) {
      return ATTRIBUTE_START_DATE;
    }
    else if (attributeName.equals("endDate")) {
      return ATTRIBUTE_END_DATE;
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
    return super.getType(attributeName, InvoiceViewCalBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceViewCalBean invoiceViewCalBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewCalBean.getInvoice());
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
   public int delete(InvoiceViewCalBean invoiceViewCalBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewCalBean.getInvoice());
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
   public int insert(InvoiceViewCalBean invoiceViewCalBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceViewCalBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceViewCalBean invoiceViewCalBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "," +
     ATTRIBUTE_INVOICE_DATE + "," +
     ATTRIBUTE_START_DATE + "," +
     ATTRIBUTE_END_DATE + "," +
     ATTRIBUTE_INVOICE_PERIOD + ")" +
   values (
     StringHandler.nullIfZero(invoiceViewCalBean.getInvoice()) + "," +
     StringHandler.nullIfZero(invoiceViewCalBean.getInvoiceAmount()) + "," +
     SqlHandler.delimitString(invoiceViewCalBean.getAccountNumber()) + "," +
       DateHandler.getOracleToDateFunction(invoiceViewCalBean.getInvoiceDate()) + "," +
       DateHandler.getOracleToDateFunction(invoiceViewCalBean.getStartDate()) + "," +
       DateHandler.getOracleToDateFunction(invoiceViewCalBean.getEndDate()) + "," +
     StringHandler.nullIfZero(invoiceViewCalBean.getInvoicePeriod()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceViewCalBean invoiceViewCalBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceViewCalBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceViewCalBean invoiceViewCalBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewCalBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceViewCalBean.getInvoiceAmount()) + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "=" +
      SqlHandler.delimitString(invoiceViewCalBean.getAccountNumber()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
       DateHandler.getOracleToDateFunction(invoiceViewCalBean.getInvoiceDate()) + "," +
     ATTRIBUTE_START_DATE + "=" +
       DateHandler.getOracleToDateFunction(invoiceViewCalBean.getStartDate()) + "," +
     ATTRIBUTE_END_DATE + "=" +
       DateHandler.getOracleToDateFunction(invoiceViewCalBean.getEndDate()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoiceViewCalBean.getInvoicePeriod()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewCalBean.getInvoice());
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

    Collection invoiceViewCalBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewCalBean invoiceViewCalBean = new InvoiceViewCalBean();
      load(dataSetRow, invoiceViewCalBean);
      invoiceViewCalBeanColl.add(invoiceViewCalBean);
    }

    return invoiceViewCalBeanColl;
  }
}