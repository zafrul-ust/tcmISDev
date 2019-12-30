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
import com.tcmis.internal.invoice.beans.InvoiceFormatExostarViewBean;

/******************************************************************************
 * CLASSNAME: InvoiceFormatExostarViewBeanFactory <br>
 * @version: 1.0, Apr 28, 2006 <br>
 *****************************************************************************/

public class InvoiceFormatExostarViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
  public String ATTRIBUTE_REQUESTOR_NAME = "REQUESTOR_NAME";
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
  public String ATTRIBUTE_QUANTITY = "QUANTITY";
  public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";
  public String ATTRIBUTE_LINE_AMOUNT = "LINE_AMOUNT";
  public String ATTRIBUTE_FREIGHT = "FREIGHT";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
  public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";

  //table name
  public String TABLE = "INVOICE_FORMAT_EXOSTAR_VIEW";

  //constructor
  public InvoiceFormatExostarViewBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
    }
    else if (attributeName.equals("invoiceAmount")) {
      return ATTRIBUTE_INVOICE_AMOUNT;
    }
    else if (attributeName.equals("requestorName")) {
      return ATTRIBUTE_REQUESTOR_NAME;
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
    else if (attributeName.equals("lineAmount")) {
      return ATTRIBUTE_LINE_AMOUNT;
    }
    else if (attributeName.equals("freight")) {
      return ATTRIBUTE_FREIGHT;
    }
    else if (attributeName.equals("partDescription")) {
      return ATTRIBUTE_PART_DESCRIPTION;
    }
    else if (attributeName.equals("unitPrice")) {
      return ATTRIBUTE_UNIT_PRICE;
    }

    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceFormatExostarViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceFormatExostarViewBean invoiceFormatExostarViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoicePeriod", "SearchCriterion.EQUALS",
     "" + invoiceFormatExostarViewBean.getInvoicePeriod());
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
   public int delete(InvoiceFormatExostarViewBean invoiceFormatExostarViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoicePeriod", "SearchCriterion.EQUALS",
     "" + invoiceFormatExostarViewBean.getInvoicePeriod());
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
   public int insert(InvoiceFormatExostarViewBean invoiceFormatExostarViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceFormatExostarViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceFormatExostarViewBean invoiceFormatExostarViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_DATE + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "," +
     ATTRIBUTE_REQUESTOR_NAME + "," +
     ATTRIBUTE_PR_NUMBER + "," +
     ATTRIBUTE_LINE_ITEM + "," +
     ATTRIBUTE_CAT_PART_NO + "," +
     ATTRIBUTE_DATE_DELIVERED + "," +
     ATTRIBUTE_QUANTITY + "," +
     ATTRIBUTE_UNIT_OF_SALE + "," +
     ATTRIBUTE_LINE_AMOUNT + "," +
     ATTRIBUTE_FREIGHT + "," +
     ATTRIBUTE_PART_DESCRIPTION + ")" +
     " values (" +
     invoiceFormatExostarViewBean.getInvoicePeriod() + "," +
     invoiceFormatExostarViewBean.getInvoice() + "," +
     DateHandler.getOracleToDateFunction(invoiceFormatExostarViewBean.getInvoiceDate()) + "," +
       SqlHandler.delimitString(invoiceFormatExostarViewBean.getPoNumber()) + "," +
     invoiceFormatExostarViewBean.getInvoiceAmount() + "," +
     SqlHandler.delimitString(invoiceFormatExostarViewBean.getRequestorName()) + "," +
     invoiceFormatExostarViewBean.getPrNumber() + "," +
       SqlHandler.delimitString(invoiceFormatExostarViewBean.getLineItem()) + "," +
       SqlHandler.delimitString(invoiceFormatExostarViewBean.getCatPartNo()) + "," +
     DateHandler.getOracleToDateFunction(invoiceFormatExostarViewBean.getDateDelivered()) + "," +
     invoiceFormatExostarViewBean.getQuantity() + "," +
       SqlHandler.delimitString(invoiceFormatExostarViewBean.getUnitOfSale()) + "," +
     invoiceFormatExostarViewBean.getLineAmount() + "," +
     invoiceFormatExostarViewBean.getFreight() + "," +
     SqlHandler.delimitString(invoiceFormatExostarViewBean.getPartDescription()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceFormatExostarViewBean invoiceFormatExostarViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceFormatExostarViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceFormatExostarViewBean invoiceFormatExostarViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoiceFormatExostarViewBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_INVOICE + "=" +
       StringHandler.nullIfZero(invoiceFormatExostarViewBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoiceFormatExostarViewBean.getInvoiceDate()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
       SqlHandler.delimitString(invoiceFormatExostarViewBean.getPoNumber()) + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceFormatExostarViewBean.getInvoiceAmount()) + "," +
     ATTRIBUTE_REQUESTOR_NAME + "=" +
      SqlHandler.delimitString(invoiceFormatExostarViewBean.getRequestorName()) + "," +
     ATTRIBUTE_PR_NUMBER + "=" +
       StringHandler.nullIfZero(invoiceFormatExostarViewBean.getPrNumber()) + "," +
     ATTRIBUTE_LINE_ITEM + "=" +
       SqlHandler.delimitString(invoiceFormatExostarViewBean.getLineItem()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
       SqlHandler.delimitString(invoiceFormatExostarViewBean.getCatPartNo()) + "," +
     ATTRIBUTE_DATE_DELIVERED + "=" +
      DateHandler.getOracleToDateFunction(invoiceFormatExostarViewBean.getDateDelivered()) + "," +
     ATTRIBUTE_QUANTITY + "=" +
       StringHandler.nullIfZero(invoiceFormatExostarViewBean.getQuantity()) + "," +
     ATTRIBUTE_UNIT_OF_SALE + "=" +
       SqlHandler.delimitString(invoiceFormatExostarViewBean.getUnitOfSale()) + "," +
     ATTRIBUTE_LINE_AMOUNT + "=" +
       StringHandler.nullIfZero(invoiceFormatExostarViewBean.getLineAmount()) + "," +
     ATTRIBUTE_FREIGHT + "=" +
       StringHandler.nullIfZero(invoiceFormatExostarViewBean.getFreight()) + "," +
     ATTRIBUTE_PART_DESCRIPTION + "=" +
      SqlHandler.delimitString(invoiceFormatExostarViewBean.getPartDescription()) + " " +
     "where " + ATTRIBUTE_INVOICE_PERIOD + "=" +
      invoiceFormatExostarViewBean.getInvoicePeriod();
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

    Collection invoiceFormatExostarViewBeanColl = new Vector();
    String orderBy = " order by pr_number, line_item, invoice";
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + orderBy;
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceFormatExostarViewBean invoiceFormatExostarViewBean = new
          InvoiceFormatExostarViewBean();
      load(dataSetRow, invoiceFormatExostarViewBean);
      invoiceFormatExostarViewBeanColl.add(invoiceFormatExostarViewBean);
    }

    return invoiceFormatExostarViewBeanColl;
  }
}