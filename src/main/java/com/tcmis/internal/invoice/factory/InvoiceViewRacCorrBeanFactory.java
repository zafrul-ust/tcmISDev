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
import com.tcmis.internal.invoice.beans.InvoiceViewRacCorrBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewRacCorrBeanFactory <br>
 * @version: 1.0, Mar 18, 2005 <br>
 *****************************************************************************/

public class InvoiceViewRacCorrBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_SUM = "SUM";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";

  //table name
  public String TABLE = "INVOICE_VIEW_RAC_CORR";

  //constructor
  public InvoiceViewRacCorrBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("sum")) {
      return ATTRIBUTE_SUM;
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
    return super.getType(attributeName, InvoiceViewRacCorrBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceViewRacCorrBean invoiceViewRacCorrBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewRacCorrBean.getInvoice());
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
   public int delete(InvoiceViewRacCorrBean invoiceViewRacCorrBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewRacCorrBean.getInvoice());
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
   public int insert(InvoiceViewRacCorrBean invoiceViewRacCorrBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceViewRacCorrBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceViewRacCorrBean invoiceViewRacCorrBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_SUM + "," +
     ATTRIBUTE_INVOICE_PERIOD + ")" +
   values (
     StringHandler.nullIfZero(invoiceViewRacCorrBean.getInvoice()) + "," +
     StringHandler.nullIfZero(invoiceViewRacCorrBean.getSum()) + "," +
     StringHandler.nullIfZero(invoiceViewRacCorrBean.getInvoicePeriod()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceViewRacCorrBean invoiceViewRacCorrBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceViewRacCorrBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceViewRacCorrBean invoiceViewRacCorrBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewRacCorrBean.getInvoice()) + "," +
     ATTRIBUTE_SUM + "=" +
      StringHandler.nullIfZero(invoiceViewRacCorrBean.getSum()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
       StringHandler.nullIfZero(invoiceViewRacCorrBean.getInvoicePeriod()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewRacCorrBean.getInvoice());
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

    Collection invoiceViewRacCorrBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewRacCorrBean invoiceViewRacCorrBean = new
          InvoiceViewRacCorrBean();
      load(dataSetRow, invoiceViewRacCorrBean);
      invoiceViewRacCorrBeanColl.add(invoiceViewRacCorrBean);
    }

    return invoiceViewRacCorrBeanColl;
  }
}