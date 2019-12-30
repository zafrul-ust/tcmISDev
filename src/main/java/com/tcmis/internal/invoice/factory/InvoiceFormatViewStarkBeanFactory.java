package com.tcmis.internal.invoice.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;
import java.math.BigDecimal;

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
import com.tcmis.internal.invoice.beans.InvoiceFormatViewStarkBean;

/******************************************************************************
 * CLASSNAME: InvoiceFormatViewStarkBeanFactory <br>
 * @version: 1.0, Aug 10, 2007 <br>
 *****************************************************************************/

public class InvoiceFormatViewStarkBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
  public String ATTRIBUTE_QUANTITY = "QUANTITY";
  public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";
  public String ATTRIBUTE_UNIT_OF_SALE_PRICE = "UNIT_OF_SALE_PRICE";
  public String ATTRIBUTE_ADD_CHARGE = "ADD_CHARGE";
  public String ATTRIBUTE_NET_AMOUNT = "NET_AMOUNT";

  //table name
  public String TABLE = "INVOICE_FORMAT_VIEW_STARK";

  //constructor
  public InvoiceFormatViewStarkBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else if (attributeName.equals("invoiceAmount")) {
      return ATTRIBUTE_INVOICE_AMOUNT;
    }
    else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
    }
    else if (attributeName.equals("prNumber")) {
      return ATTRIBUTE_PR_NUMBER;
    }
    else if (attributeName.equals("lineItem")) {
      return ATTRIBUTE_LINE_ITEM;
    }
    else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("dateDelivered")) {
      return ATTRIBUTE_DATE_DELIVERED;
    }
    else if (attributeName.equals("quantity")) {
      return ATTRIBUTE_QUANTITY;
    }
    else if (attributeName.equals("unitOfSale")) {
      return ATTRIBUTE_UNIT_OF_SALE;
    }
    else if (attributeName.equals("unitOfSalePrice")) {
      return ATTRIBUTE_UNIT_OF_SALE_PRICE;
    }
    else if (attributeName.equals("addCharge")) {
      return ATTRIBUTE_ADD_CHARGE;
    }
    else if (attributeName.equals("netAmount")) {
      return ATTRIBUTE_NET_AMOUNT;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceFormatViewStarkBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceFormatViewStarkBean invoiceFormatViewStarkBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoicePeriod", "SearchCriterion.EQUALS",
     "" + invoiceFormatViewStarkBean.getInvoicePeriod());
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
   public int delete(InvoiceFormatViewStarkBean invoiceFormatViewStarkBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoicePeriod", "SearchCriterion.EQUALS",
     "" + invoiceFormatViewStarkBean.getInvoicePeriod());
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
   public int insert(InvoiceFormatViewStarkBean invoiceFormatViewStarkBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceFormatViewStarkBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceFormatViewStarkBean invoiceFormatViewStarkBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_DATE + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_PR_NUMBER + "," +
     ATTRIBUTE_LINE_ITEM + "," +
     ATTRIBUTE_CAT_PART_NO + "," +
     ATTRIBUTE_DATE_DELIVERED + "," +
     ATTRIBUTE_QUANTITY + "," +
     ATTRIBUTE_UNIT_OF_SALE + "," +
     ATTRIBUTE_UNIT_OF_SALE_PRICE + "," +
     ATTRIBUTE_ADD_CHARGE + "," +
     ATTRIBUTE_NET_AMOUNT + ")" +
     " values (" +
     invoiceFormatViewStarkBean.getInvoicePeriod() + "," +
     invoiceFormatViewStarkBean.getInvoice() + "," +
     DateHandler.getOracleToDateFunction(invoiceFormatViewStarkBean.getInvoiceDate()) + "," +
     invoiceFormatViewStarkBean.getInvoiceAmount() + "," +
     SqlHandler.delimitString(invoiceFormatViewStarkBean.getPoNumber()) + "," +
     invoiceFormatViewStarkBean.getPrNumber() + "," +
     SqlHandler.delimitString(invoiceFormatViewStarkBean.getLineItem()) + "," +
       SqlHandler.delimitString(invoiceFormatViewStarkBean.getCatPartNo()) + "," +
     DateHandler.getOracleToDateFunction(invoiceFormatViewStarkBean.getDateDelivered()) + "," +
     invoiceFormatViewStarkBean.getQuantity() + "," +
       SqlHandler.delimitString(invoiceFormatViewStarkBean.getUnitOfSale()) + "," +
     invoiceFormatViewStarkBean.getUnitOfSalePrice() + "," +
     invoiceFormatViewStarkBean.getAddCharge() + "," +
     invoiceFormatViewStarkBean.getNetAmount() + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceFormatViewStarkBean invoiceFormatViewStarkBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceFormatViewStarkBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceFormatViewStarkBean invoiceFormatViewStarkBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
       StringHandler.nullIfZero(invoiceFormatViewStarkBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceFormatViewStarkBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoiceFormatViewStarkBean.getInvoiceDate()) + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "=" +
       StringHandler.nullIfZero(invoiceFormatViewStarkBean.getInvoiceAmount()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
       SqlHandler.delimitString(invoiceFormatViewStarkBean.getPoNumber()) + "," +
     ATTRIBUTE_PR_NUMBER + "=" +
       StringHandler.nullIfZero(invoiceFormatViewStarkBean.getPrNumber()) + "," +
     ATTRIBUTE_LINE_ITEM + "=" +
       SqlHandler.delimitString(invoiceFormatViewStarkBean.getLineItem()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
       SqlHandler.delimitString(invoiceFormatViewStarkBean.getCatPartNo()) + "," +
     ATTRIBUTE_DATE_DELIVERED + "=" +
      DateHandler.getOracleToDateFunction(invoiceFormatViewStarkBean.getDateDelivered()) + "," +
     ATTRIBUTE_QUANTITY + "=" +
       StringHandler.nullIfZero(invoiceFormatViewStarkBean.getQuantity()) + "," +
     ATTRIBUTE_UNIT_OF_SALE + "=" +
       SqlHandler.delimitString(invoiceFormatViewStarkBean.getUnitOfSale()) + "," +
     ATTRIBUTE_UNIT_OF_SALE_PRICE + "=" +
      StringHandler.nullIfZero(invoiceFormatViewStarkBean.getUnitOfSalePrice()) + "," +
     ATTRIBUTE_ADD_CHARGE + "=" +
       StringHandler.nullIfZero(invoiceFormatViewStarkBean.getAddCharge()) + "," +
     ATTRIBUTE_NET_AMOUNT + "=" +
       StringHandler.nullIfZero(invoiceFormatViewStarkBean.getNetAmount()) + " " +
     "where " + ATTRIBUTE_INVOICE_PERIOD + "=" +
      invoiceFormatViewStarkBean.getInvoicePeriod();
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
    Collection invoiceFormatViewStarkBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceFormatViewStarkBean invoiceFormatViewStarkBean = new
          InvoiceFormatViewStarkBean();
      load(dataSetRow, invoiceFormatViewStarkBean);
      invoiceFormatViewStarkBeanColl.add(invoiceFormatViewStarkBean);
    }
    return invoiceFormatViewStarkBeanColl;
  }

  public Collection select(BigDecimal invoicePeriod) throws
      BaseException {
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

  public Collection select(BigDecimal invoicePeriod,
                           Connection conn) throws BaseException {
    Collection invoiceFormatViewStarkBeanColl = new Vector();
    String query = "select unique invoice from " + TABLE + " where invoice_period=" + invoicePeriod;
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceFormatViewStarkBean invoiceFormatViewStarkBean = new
          InvoiceFormatViewStarkBean();
      load(dataSetRow, invoiceFormatViewStarkBean);
      //now add child records
      String query2 = "select * from " + TABLE + " where invoice=" +
          invoiceFormatViewStarkBean.getInvoice();
      if (log.isDebugEnabled()) {
        log.debug("QUERY:" + query2);
      }
      DataSet dataSet2 = new SqlManager().select(conn, query2);
      Iterator dataIter2 = dataSet2.iterator();
      while (dataIter2.hasNext()) {
        DataSetRow dataSetRow2 = (DataSetRow) dataIter2.next();
        InvoiceFormatViewStarkBean invoiceFormatViewStarkBean2 = new
            InvoiceFormatViewStarkBean();
        load(dataSetRow2, invoiceFormatViewStarkBean2);
        invoiceFormatViewStarkBean.addLineBean(invoiceFormatViewStarkBean2);
      }
      invoiceFormatViewStarkBeanColl.add(invoiceFormatViewStarkBean);
    }
    return invoiceFormatViewStarkBeanColl;
  }
}