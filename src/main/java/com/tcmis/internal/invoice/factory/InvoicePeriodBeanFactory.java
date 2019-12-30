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
import com.tcmis.common.util.*;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.internal.invoice.beans.InvoicePeriodBean;

/******************************************************************************
 * CLASSNAME: InvoicePeriodBeanFactory <br>
 * @version: 1.0, Mar 9, 2005 <br>
 *****************************************************************************/

public class InvoicePeriodBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_START_DATE = "START_DATE";
  public String ATTRIBUTE_END_DATE = "END_DATE";
  public String ATTRIBUTE_INVOICE_GROUP = "INVOICE_GROUP";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";

  //table name
  public String TABLE = "INVOICE_PERIOD";

  //constructor
  public InvoicePeriodBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("startDate")) {
      return ATTRIBUTE_START_DATE;
    }
    else if (attributeName.equals("endDate")) {
      return ATTRIBUTE_END_DATE;
    }
    else if (attributeName.equals("invoiceGroup")) {
      return ATTRIBUTE_INVOICE_GROUP;
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
    return super.getType(attributeName, InvoicePeriodBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoicePeriodBean invoicePeriodBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoicePeriod", "SearchCriterion.EQUALS",
     "" + invoicePeriodBean.getInvoicePeriod());
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
   public int delete(InvoicePeriodBean invoicePeriodBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoicePeriod", "SearchCriterion.EQUALS",
     "" + invoicePeriodBean.getInvoicePeriod());
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
   public int insert(InvoicePeriodBean invoicePeriodBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoicePeriodBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoicePeriodBean invoicePeriodBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_START_DATE + "," +
     ATTRIBUTE_END_DATE + "," +
     ATTRIBUTE_INVOICE_GROUP + "," +
     ATTRIBUTE_INVOICE_DATE + ")" +
   values (
     StringHandler.nullIfZero(invoicePeriodBean.getInvoicePeriod()) + "," +
     SqlHandler.delimitString(invoicePeriodBean.getCompanyId()) + "," +
       DateHandler.getOracleToDateFunction(invoicePeriodBean.getStartDate()) + "," +
       DateHandler.getOracleToDateFunction(invoicePeriodBean.getEndDate()) + "," +
     SqlHandler.delimitString(invoicePeriodBean.getInvoiceGroup()) + "," +
       DateHandler.getOracleToDateFunction(invoicePeriodBean.getInvoiceDate()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoicePeriodBean invoicePeriodBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoicePeriodBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoicePeriodBean invoicePeriodBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoicePeriodBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(invoicePeriodBean.getCompanyId()) + "," +
     ATTRIBUTE_START_DATE + "=" +
       DateHandler.getOracleToDateFunction(invoicePeriodBean.getStartDate()) + "," +
     ATTRIBUTE_END_DATE + "=" +
       DateHandler.getOracleToDateFunction(invoicePeriodBean.getEndDate()) + "," +
     ATTRIBUTE_INVOICE_GROUP + "=" +
      SqlHandler.delimitString(invoicePeriodBean.getInvoiceGroup()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
       DateHandler.getOracleToDateFunction(invoicePeriodBean.getInvoiceDate()) + " " +
     "where " + ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoicePeriodBean.getInvoicePeriod());
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

    Collection invoicePeriodBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoicePeriodBean invoicePeriodBean = new InvoicePeriodBean();
      load(dataSetRow, invoicePeriodBean);
      invoicePeriodBeanColl.add(invoicePeriodBean);
    }

    return invoicePeriodBeanColl;
  }

  public Collection selectMaxInvoicePeriod(String companyId, 
                                           String invoiceGroup) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectMaxInvoicePeriod(companyId, invoiceGroup, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectMaxInvoicePeriod(String companyId, 
                                           String invoiceGroup, 
                                           Connection conn) throws
      BaseException {

    Collection invoiceBeanColl = new Vector();

    String query = "select max(invoice_period) invoice_period from " + TABLE + " " + 
        "where company_id=" + SqlHandler.delimitString(companyId) + " " +
        "and invoice_group=" + SqlHandler.delimitString(invoiceGroup);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoicePeriodBean invoicePeriodBean = new InvoicePeriodBean();
      load(dataSetRow, invoicePeriodBean);
      invoiceBeanColl.add(invoicePeriodBean);
    }

    return invoiceBeanColl;
  }
}