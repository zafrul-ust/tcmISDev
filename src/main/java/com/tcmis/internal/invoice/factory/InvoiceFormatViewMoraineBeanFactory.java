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
import com.tcmis.internal.invoice.beans.InvoiceFormatViewMoraineBean;

/******************************************************************************
 * CLASSNAME: InvoiceFormatViewMoraineBeanFactory <br>
 * @version: 1.0, Jun 28, 2007 <br>
 *****************************************************************************/

public class InvoiceFormatViewMoraineBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE_GROUP = "INVOICE_GROUP";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_CATEGORY = "CATEGORY";
  public String ATTRIBUTE_QTY_ORDERED = "QTY_ORDERED";
  public String ATTRIBUTE_QTY_SHIPPED = "QTY_SHIPPED";
  public String ATTRIBUTE_UOM = "UOM";
  public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
  public String ATTRIBUTE_EXTENDED_PRICE = "EXTENDED_PRICE";

  //table name
  public String TABLE = "INVOICE_FORMAT_VIEW_MORAINE";

  //constructor
  public InvoiceFormatViewMoraineBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoiceGroup")) {
      return ATTRIBUTE_INVOICE_GROUP;
    }
    else if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
    }
    else if (attributeName.equals("category")) {
      return ATTRIBUTE_CATEGORY;
    }
    else if (attributeName.equals("qtyOrdered")) {
      return ATTRIBUTE_QTY_ORDERED;
    }
    else if (attributeName.equals("qtyShipped")) {
      return ATTRIBUTE_QTY_SHIPPED;
    }
    else if (attributeName.equals("uom")) {
      return ATTRIBUTE_UOM;
    }
    else if (attributeName.equals("unitPrice")) {
      return ATTRIBUTE_UNIT_PRICE;
    }
    else if (attributeName.equals("extendedPrice")) {
      return ATTRIBUTE_EXTENDED_PRICE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceFormatViewMoraineBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceFormatViewMoraineBean invoiceFormatViewMoraineBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoiceGroup", "SearchCriterion.EQUALS",
     "" + invoiceFormatViewMoraineBean.getInvoiceGroup());
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
   public int delete(InvoiceFormatViewMoraineBean invoiceFormatViewMoraineBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoiceGroup", "SearchCriterion.EQUALS",
     "" + invoiceFormatViewMoraineBean.getInvoiceGroup());
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
   public int insert(InvoiceFormatViewMoraineBean invoiceFormatViewMoraineBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceFormatViewMoraineBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceFormatViewMoraineBean invoiceFormatViewMoraineBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE_GROUP + "," +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_DATE + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_CATEGORY + "," +
     ATTRIBUTE_QTY_ORDERED + "," +
     ATTRIBUTE_QTY_SHIPPED + "," +
     ATTRIBUTE_UOM + "," +
     ATTRIBUTE_UNIT_PRICE + "," +
     ATTRIBUTE_EXTENDED_PRICE + ")" +
     " values (" +
       SqlHandler.delimitString(invoiceFormatViewMoraineBean.getInvoiceGroup()) + "," +
     invoiceFormatViewMoraineBean.getInvoicePeriod() + "," +
     invoiceFormatViewMoraineBean.getInvoice() + "," +
     DateHandler.getOracleToDateFunction(invoiceFormatViewMoraineBean.getInvoiceDate()) + "," +
       SqlHandler.delimitString(invoiceFormatViewMoraineBean.getPoNumber()) + "," +
       SqlHandler.delimitString(invoiceFormatViewMoraineBean.getCategory()) + "," +
       SqlHandler.delimitString(invoiceFormatViewMoraineBean.getQtyOrdered()) + "," +
       SqlHandler.delimitString(invoiceFormatViewMoraineBean.getQtyShipped()) + "," +
     SqlHandler.delimitString(invoiceFormatViewMoraineBean.getUom()) + "," +
     invoiceFormatViewMoraineBean.getUnitPrice() + "," +
     invoiceFormatViewMoraineBean.getExtendedPrice() + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceFormatViewMoraineBean invoiceFormatViewMoraineBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceFormatViewMoraineBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceFormatViewMoraineBean invoiceFormatViewMoraineBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE_GROUP + "=" +
       SqlHandler.delimitString(invoiceFormatViewMoraineBean.getInvoiceGroup()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoiceFormatViewMoraineBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_INVOICE + "=" +
       StringHandler.nullIfZero(invoiceFormatViewMoraineBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoiceFormatViewMoraineBean.getInvoiceDate()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
       SqlHandler.delimitString(invoiceFormatViewMoraineBean.getPoNumber()) + "," +
     ATTRIBUTE_CATEGORY + "=" +
       SqlHandler.delimitString(invoiceFormatViewMoraineBean.getCategory()) + "," +
     ATTRIBUTE_QTY_ORDERED + "=" +
       SqlHandler.delimitString(invoiceFormatViewMoraineBean.getQtyOrdered()) + "," +
     ATTRIBUTE_QTY_SHIPPED + "=" +
       SqlHandler.delimitString(invoiceFormatViewMoraineBean.getQtyShipped()) + "," +
     ATTRIBUTE_UOM + "=" +
      SqlHandler.delimitString(invoiceFormatViewMoraineBean.getUom()) + "," +
     ATTRIBUTE_UNIT_PRICE + "=" +
       StringHandler.nullIfZero(invoiceFormatViewMoraineBean.getUnitPrice()) + "," +
     ATTRIBUTE_EXTENDED_PRICE + "=" +
      StringHandler.nullIfZero(invoiceFormatViewMoraineBean.getExtendedPrice()) + " " +
     "where " + ATTRIBUTE_INVOICE_GROUP + "=" +
      invoiceFormatViewMoraineBean.getInvoiceGroup();
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
    Collection invoiceFormatViewMoraineBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceFormatViewMoraineBean invoiceFormatViewMoraineBean = new
          InvoiceFormatViewMoraineBean();
      load(dataSetRow, invoiceFormatViewMoraineBean);
      invoiceFormatViewMoraineBeanColl.add(invoiceFormatViewMoraineBean);
    }
    return invoiceFormatViewMoraineBeanColl;
  }
}