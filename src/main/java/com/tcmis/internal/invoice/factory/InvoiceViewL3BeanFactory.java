package com.tcmis.internal.invoice.factory;

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
import com.tcmis.internal.invoice.beans.InvoiceViewL3Bean;

/******************************************************************************
 * CLASSNAME: InvoiceViewL3BeanFactory <br>
 * @version: 1.0, May 26, 2005 <br>
 *****************************************************************************/

public class InvoiceViewL3BeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_START_DATE = "START_DATE";
  public String ATTRIBUTE_END_DATE = "END_DATE";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";

  //table name
  public String TABLE = "INVOICE_VIEW_L3";

  //constructor
  public InvoiceViewL3BeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("startDate")) {
      return ATTRIBUTE_START_DATE;
    }
    else if (attributeName.equals("endDate")) {
      return ATTRIBUTE_END_DATE;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceViewL3Bean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceViewL3Bean invoiceViewL3Bean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewL3Bean.getInvoice());
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
   public int delete(InvoiceViewL3Bean invoiceViewL3Bean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewL3Bean.getInvoice());
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
   public int insert(InvoiceViewL3Bean invoiceViewL3Bean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceViewL3Bean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceViewL3Bean invoiceViewL3Bean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "," +
     ATTRIBUTE_INVOICE_PERIOD + ")" +
     " values (" +
     invoiceViewL3Bean.getInvoice() + "," +
     SqlHandler.delimitString(invoiceViewL3Bean.getAccountNumber()) + "," +
     invoiceViewL3Bean.getInvoicePeriod() + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceViewL3Bean invoiceViewL3Bean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceViewL3Bean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceViewL3Bean invoiceViewL3Bean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewL3Bean.getInvoice()) + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "=" +
      SqlHandler.delimitString(invoiceViewL3Bean.getAccountNumber()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoiceViewL3Bean.getInvoicePeriod()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      invoiceViewL3Bean.getInvoice();
    return new SqlManager().update(conn, query);
   }
   */

  /*
     //THIS IS NOT ACTUALLY QUERYING A VIEW!!!!!!!
//IF YOU WANT TO USE SEARCHCRITERIA MAKE SURE THAT THERE IS A VIEW!!!!!
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
      Collection invoiceViewL3BeanColl = new Vector();
      String query = "select * from " + TABLE + " " +
          getWhereClause(criteria);
      DataSet dataSet = new SqlManager().select(conn, query);
      Iterator dataIter = dataSet.iterator();
      while (dataIter.hasNext()) {
        DataSetRow dataSetRow = (DataSetRow) dataIter.next();
        InvoiceViewL3Bean invoiceViewL3Bean = new InvoiceViewL3Bean();
        load(dataSetRow, invoiceViewL3Bean);
        invoiceViewL3BeanColl.add(invoiceViewL3Bean);
      }
      return invoiceViewL3BeanColl;
    }
   */

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

    Collection invoiceViewL3BeanColl = new Vector();

    String query =
        "select distinct i.invoice,l.account_number, invoice_period, invoice_date, " +
        "(select start_date from invoice_period i where i.company_id = 'L3' " +
        "and invoice_period = " + invoicePeriod + ") as start_date," +
        "(select end_date from invoice_period i where i.company_id = 'L3' " +
        "and invoice_period = " + invoicePeriod +") as end_date " +
        "from invoice i, invoice_line l " +
        "where i.company_id='L3' and i.invoice = l.invoice " +
        "and invoice_period = " + invoicePeriod + " " +
        "order by i.invoice,l.account_number";
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();
    InvoiceViewL3DetailBeanFactory detailFactory = new
        InvoiceViewL3DetailBeanFactory(this.getDbManager());
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewL3Bean invoiceViewL3Bean = new InvoiceViewL3Bean();
      load(dataSetRow, invoiceViewL3Bean);
      invoiceViewL3Bean.setDetail(detailFactory.select(invoiceViewL3Bean.
          getInvoice(), invoiceViewL3Bean.getAccountNumber()));
      invoiceViewL3BeanColl.add(invoiceViewL3Bean);
    }

    return invoiceViewL3BeanColl;
  }

}