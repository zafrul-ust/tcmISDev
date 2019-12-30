package com.tcmis.internal.invoice.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.*;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.internal.invoice.beans.InvoiceViewBaeBean;
import com.tcmis.internal.invoice.factory.InvoiceViewBaeDetailBeanFactory;

/******************************************************************************
 * CLASSNAME: InvoiceViewBaeBeanFactory <br>
 * @version: 1.0, Feb 28, 2005 <br>
 *****************************************************************************/

public class InvoiceViewBaeBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_INVOICE_GROUP = "INVOICE_GROUP";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_START_DATE = "START_DATE";
  public String ATTRIBUTE_END_DATE = "END_DATE";

  //table name
  public String TABLE = "INVOICE_VIEW_BAE";

  //constructor
  public InvoiceViewBaeBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("accountNumber")) {
      return ATTRIBUTE_ACCOUNT_NUMBER;
    }
    else if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else if (attributeName.equals("invoiceGroup")) {
      return ATTRIBUTE_INVOICE_GROUP;
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
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceViewBaeBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceViewBaeBean invoiceViewBaeBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewBaeBean.getInvoice());
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
   public int delete(InvoiceViewBaeBean invoiceViewBaeBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewBaeBean.getInvoice());
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
   public int insert(InvoiceViewBaeBean invoiceViewBaeBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceViewBaeBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceViewBaeBean invoiceViewBaeBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "," +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_INVOICE_GROUP + ")" +
   values (
     StringHandler.nullIfZero(invoiceViewBaeBean.getInvoice()) + "," +
     SqlHandler.delimitString(invoiceViewBaeBean.getAccountNumber()) + "," +
     StringHandler.nullIfZero(invoiceViewBaeBean.getInvoicePeriod()) + "," +
     SqlHandler.delimitString(invoiceViewBaeBean.getInvoiceGroup()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceViewBaeBean invoiceViewBaeBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceViewBaeBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceViewBaeBean invoiceViewBaeBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewBaeBean.getInvoice()) + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "=" +
      SqlHandler.delimitString(invoiceViewBaeBean.getAccountNumber()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoiceViewBaeBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_INVOICE_GROUP + "=" +
      SqlHandler.delimitString(invoiceViewBaeBean.getInvoiceGroup()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewBaeBean.getInvoice());
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

    Collection invoiceViewBaeBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();
    InvoiceViewBaeDetailBeanFactory detailFactory = new InvoiceViewBaeDetailBeanFactory(this.getDbManager());
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewBaeBean invoiceViewBaeBean = new InvoiceViewBaeBean();
      load(dataSetRow, invoiceViewBaeBean);
      SearchCriteria detailCriteria = new SearchCriteria();
      detailCriteria.addCriterion("invoice", SearchCriterion.EQUALS, invoiceViewBaeBean.getInvoice().toString());
      detailCriteria.addCriterion("accountNumber", SearchCriterion.EQUALS, invoiceViewBaeBean.getAccountNumber());
      invoiceViewBaeBean.setDetail(detailFactory.select(detailCriteria));
      invoiceViewBaeBeanColl.add(invoiceViewBaeBean);
    }

    return invoiceViewBaeBeanColl;
  }

  public Collection select(BigDecimal invoicePeriod) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(invoicePeriod, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(BigDecimal invoicePeriod, Connection conn) throws
      BaseException {

    Collection invoiceViewBaeBeanColl = new Vector();

    String query = "select invoice, account_number, invoice_period, invoice_group, invoice_date," +
    		"start_date,  end_date  from invoice_view_bae where invoice_period=" + invoicePeriod;

    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    InvoiceViewBaeDetailBeanFactory detailFactory = new InvoiceViewBaeDetailBeanFactory(this.getDbManager());
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewBaeBean invoiceViewBaeBean = new InvoiceViewBaeBean();
      load(dataSetRow, invoiceViewBaeBean);
      SearchCriteria detailCriteria = new SearchCriteria();
      detailCriteria.addCriterion("invoice", SearchCriterion.EQUALS, invoiceViewBaeBean.getInvoice().toString());
      detailCriteria.addCriterion("accountNumber", SearchCriterion.EQUALS, invoiceViewBaeBean.getAccountNumber());
      invoiceViewBaeBean.setDetail(detailFactory.select(detailCriteria));
      invoiceViewBaeBeanColl.add(invoiceViewBaeBean);
    }

    return invoiceViewBaeBeanColl;
  }

}