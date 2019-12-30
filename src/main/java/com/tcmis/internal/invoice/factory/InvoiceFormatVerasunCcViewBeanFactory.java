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
import com.tcmis.internal.invoice.beans.InvoiceFormatVerasunCcViewBean;

/******************************************************************************
 * CLASSNAME: InvoiceFormatVerasunCcViewBeanFactory <br>
 * @version: 1.0, Aug 14, 2007 <br>
 *****************************************************************************/

public class InvoiceFormatVerasunCcViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
  public String ATTRIBUTE_MATERIAL_COST = "MATERIAL_COST";
  public String ATTRIBUTE_WASTE_COST = "WASTE_COST";
  public String ATTRIBUTE_GAS_COST = "GAS_COST";
  public String ATTRIBUTE_CYLINDER_RENTAL_COST = "CYLINDER_RENTAL_COST";
  public String ATTRIBUTE_SERVICE_FEE = "SERVICE_FEE";
  public String ATTRIBUTE_SALES_TAX = "SALES_TAX";
  public String ATTRIBUTE_TOTAL_DUE = "TOTAL_DUE";
  public String ATTRIBUTE_MAX_DATE_DELIVERED = "MAX_DATE_DELIVERED";

  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";

  //table name
  public String TABLE = "INVOICE_FORMAT_VERASUN_CC_VIEW";

  //constructor
  public InvoiceFormatVerasunCcViewBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("invoiceAmount")) {
      return ATTRIBUTE_INVOICE_AMOUNT;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else if (attributeName.equals("accountNumber")) {
      return ATTRIBUTE_ACCOUNT_NUMBER;
    }
    else if (attributeName.equals("materialCost")) {
      return ATTRIBUTE_MATERIAL_COST;
    }
    else if (attributeName.equals("wasteCost")) {
      return ATTRIBUTE_WASTE_COST;
    }
    else if (attributeName.equals("gasCost")) {
      return ATTRIBUTE_GAS_COST;
    }
    else if (attributeName.equals("cylinderRentalCost")) {
      return ATTRIBUTE_CYLINDER_RENTAL_COST;
    }
    else if (attributeName.equals("serviceFee")) {
      return ATTRIBUTE_SERVICE_FEE;
    }
    else if (attributeName.equals("salesTax")) {
      return ATTRIBUTE_SALES_TAX;
    }
    else if (attributeName.equals("totalDue")) {
      return ATTRIBUTE_TOTAL_DUE;
    }
    else if (attributeName.equals("maxDateDelivered")) {
      return ATTRIBUTE_MAX_DATE_DELIVERED;
    }
    else if (attributeName.equals("partDescription")) {
      return ATTRIBUTE_PART_DESCRIPTION;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceFormatVerasunCcViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
       public int delete(InvoiceFormatVerasunCcViewBean invoiceFormatVerasunCcViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoicePeriod", "SearchCriterion.EQUALS",
     "" + invoiceFormatVerasunCcViewBean.getInvoicePeriod());
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
   public int delete(InvoiceFormatVerasunCcViewBean invoiceFormatVerasunCcViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoicePeriod", "SearchCriterion.EQUALS",
     "" + invoiceFormatVerasunCcViewBean.getInvoicePeriod());
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
       public int insert(InvoiceFormatVerasunCcViewBean invoiceFormatVerasunCcViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceFormatVerasunCcViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceFormatVerasunCcViewBean invoiceFormatVerasunCcViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "," +
     ATTRIBUTE_INVOICE_DATE + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "," +
     ATTRIBUTE_MATERIAL_COST + "," +
     ATTRIBUTE_WASTE_COST + "," +
     ATTRIBUTE_GAS_COST + "," +
     ATTRIBUTE_CYLINDER_RENTAL_COST + "," +
     ATTRIBUTE_SERVICE_FEE + "," +
     ATTRIBUTE_SALES_TAX + "," +
     ATTRIBUTE_TOTAL_DUE + "," +
     ATTRIBUTE_MAX_DATE_DELIVERED + ")" +
     " values (" +
     invoiceFormatVerasunCcViewBean.getInvoicePeriod() + "," +
     invoiceFormatVerasunCcViewBean.getInvoice() + "," +
     invoiceFormatVerasunCcViewBean.getInvoiceAmount() + "," +
     DateHandler.getOracleToDateFunction(invoiceFormatVerasunCcViewBean.getInvoiceDate()) + "," +
     SqlHandler.delimitString(invoiceFormatVerasunCcViewBean.getAccountNumber()) + "," +
     invoiceFormatVerasunCcViewBean.getMaterialCost() + "," +
     invoiceFormatVerasunCcViewBean.getWasteCost() + "," +
     invoiceFormatVerasunCcViewBean.getGasCost() + "," +
     invoiceFormatVerasunCcViewBean.getCylinderRentalCost() + "," +
     invoiceFormatVerasunCcViewBean.getServiceFee() + "," +
     invoiceFormatVerasunCcViewBean.getSalesTax() + "," +
     invoiceFormatVerasunCcViewBean.getTotalDue() + "," +
     DateHandler.getOracleToDateFunction(invoiceFormatVerasunCcViewBean.getMaxDateDelivered()) + ")";
    return sqlManager.update(conn, query);
   }
//update
       public int update(InvoiceFormatVerasunCcViewBean invoiceFormatVerasunCcViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceFormatVerasunCcViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceFormatVerasunCcViewBean invoiceFormatVerasunCcViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoiceFormatVerasunCcViewBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_INVOICE + "=" +
       StringHandler.nullIfZero(invoiceFormatVerasunCcViewBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceFormatVerasunCcViewBean.getInvoiceAmount()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoiceFormatVerasunCcViewBean.getInvoiceDate()) + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "=" +
      SqlHandler.delimitString(invoiceFormatVerasunCcViewBean.getAccountNumber()) + "," +
     ATTRIBUTE_MATERIAL_COST + "=" +
      StringHandler.nullIfZero(invoiceFormatVerasunCcViewBean.getMaterialCost()) + "," +
     ATTRIBUTE_WASTE_COST + "=" +
       StringHandler.nullIfZero(invoiceFormatVerasunCcViewBean.getWasteCost()) + "," +
     ATTRIBUTE_GAS_COST + "=" +
       StringHandler.nullIfZero(invoiceFormatVerasunCcViewBean.getGasCost()) + "," +
     ATTRIBUTE_CYLINDER_RENTAL_COST + "=" +
      StringHandler.nullIfZero(invoiceFormatVerasunCcViewBean.getCylinderRentalCost()) + "," +
     ATTRIBUTE_SERVICE_FEE + "=" +
       StringHandler.nullIfZero(invoiceFormatVerasunCcViewBean.getServiceFee()) + "," +
     ATTRIBUTE_SALES_TAX + "=" +
       StringHandler.nullIfZero(invoiceFormatVerasunCcViewBean.getSalesTax()) + "," +
     ATTRIBUTE_TOTAL_DUE + "=" +
       StringHandler.nullIfZero(invoiceFormatVerasunCcViewBean.getTotalDue()) + "," +
     ATTRIBUTE_MAX_DATE_DELIVERED + "=" +
      DateHandler.getOracleToDateFunction(invoiceFormatVerasunCcViewBean.getMaxDateDelivered()) + " " +
     "where " + ATTRIBUTE_INVOICE_PERIOD + "=" +
      invoiceFormatVerasunCcViewBean.getInvoicePeriod();
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
/*
    Collection invoiceFormatVerasunCcViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceFormatVerasunCcViewBean invoiceFormatVerasunCcViewBean = new
          InvoiceFormatVerasunCcViewBean();
      load(dataSetRow, invoiceFormatVerasunCcViewBean);
      invoiceFormatVerasunCcViewBeanColl.add(invoiceFormatVerasunCcViewBean);
    }
    return invoiceFormatVerasunCcViewBeanColl;
*/
    Collection invoiceColl = new Vector();
    String query = "select unique invoice from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet baseDataSet = new SqlManager().select(conn, query);
    Iterator baseIter = baseDataSet.iterator();
    while (baseIter.hasNext()) {
      DataSetRow baseRow = (DataSetRow) baseIter.next();
      InvoiceFormatVerasunCcViewBean bean = new
          InvoiceFormatVerasunCcViewBean();
      load(baseRow, bean);
      //now I use the unique invoice # to query by invoice
      Collection invoiceFormatVerasunViewBeanColl = new Vector();
      query = "select * from " + TABLE + " " +
          getWhereClause(new SearchCriteria("invoice", SearchCriterion.EQUALS, bean.getInvoice().toString())) + getOrderByClause(sortCriteria);
      if(log.isDebugEnabled()) {
        log.debug("QUERY:" + query);
      }
      DataSet dataSet = new SqlManager().select(conn, query);
      Iterator dataIter = dataSet.iterator();
      while (dataIter.hasNext()) {
        DataSetRow dataSetRow = (DataSetRow) dataIter.next();
        InvoiceFormatVerasunCcViewBean invoiceFormatVerasunViewBean = new
            InvoiceFormatVerasunCcViewBean();
        load(dataSetRow, invoiceFormatVerasunViewBean);
        invoiceFormatVerasunViewBeanColl.add(invoiceFormatVerasunViewBean);
      }
      invoiceColl.add(invoiceFormatVerasunViewBeanColl);
    }
    return invoiceColl;
  }
}