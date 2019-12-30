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
import com.tcmis.internal.invoice.beans.InvoiceFormatVerasunFdViewBean;

/******************************************************************************
 * CLASSNAME: InvoiceFormatVerasunFdViewBeanFactory <br>
 * @version: 1.0, Jun 11, 2007 <br>
 *****************************************************************************/

public class InvoiceFormatVerasunFdViewBeanFactory
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
  public String TABLE = "INVOICE_FORMAT_VERASUN_FD_VIEW";

  //constructor
  public InvoiceFormatVerasunFdViewBeanFactory(DbManager dbManager) {
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
    return super.getType(attributeName, InvoiceFormatVerasunFdViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
       public int delete(InvoiceFormatVerasunFdViewBean invoiceFormatVerasunFdViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoicePeriod", "SearchCriterion.EQUALS",
     "" + invoiceFormatVerasunFdViewBean.getInvoicePeriod());
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
   public int delete(InvoiceFormatVerasunFdViewBean invoiceFormatVerasunFdViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoicePeriod", "SearchCriterion.EQUALS",
     "" + invoiceFormatVerasunFdViewBean.getInvoicePeriod());
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
       public int insert(InvoiceFormatVerasunFdViewBean invoiceFormatVerasunFdViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceFormatVerasunFdViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceFormatVerasunFdViewBean invoiceFormatVerasunFdViewBean, Connection conn)
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
     ATTRIBUTE_TOTAL_DUE + ")" +
     " values (" +
     invoiceFormatVerasunFdViewBean.getInvoicePeriod() + "," +
     invoiceFormatVerasunFdViewBean.getInvoice() + "," +
     invoiceFormatVerasunFdViewBean.getInvoiceAmount() + "," +
     DateHandler.getOracleToDateFunction(invoiceFormatVerasunFdViewBean.getInvoiceDate()) + "," +
     SqlHandler.delimitString(invoiceFormatVerasunFdViewBean.getAccountNumber()) + "," +
     invoiceFormatVerasunFdViewBean.getMaterialCost() + "," +
     invoiceFormatVerasunFdViewBean.getWasteCost() + "," +
     invoiceFormatVerasunFdViewBean.getGasCost() + "," +
     invoiceFormatVerasunFdViewBean.getCylinderRentalCost() + "," +
     invoiceFormatVerasunFdViewBean.getServiceFee() + "," +
     invoiceFormatVerasunFdViewBean.getSalesTax() + "," +
     invoiceFormatVerasunFdViewBean.getTotalDue() + ")";
    return sqlManager.update(conn, query);
   }
//update
       public int update(InvoiceFormatVerasunFdViewBean invoiceFormatVerasunFdViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceFormatVerasunFdViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceFormatVerasunFdViewBean invoiceFormatVerasunFdViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoiceFormatVerasunFdViewBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_INVOICE + "=" +
       StringHandler.nullIfZero(invoiceFormatVerasunFdViewBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceFormatVerasunFdViewBean.getInvoiceAmount()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoiceFormatVerasunFdViewBean.getInvoiceDate()) + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "=" +
      SqlHandler.delimitString(invoiceFormatVerasunFdViewBean.getAccountNumber()) + "," +
     ATTRIBUTE_MATERIAL_COST + "=" +
      StringHandler.nullIfZero(invoiceFormatVerasunFdViewBean.getMaterialCost()) + "," +
     ATTRIBUTE_WASTE_COST + "=" +
       StringHandler.nullIfZero(invoiceFormatVerasunFdViewBean.getWasteCost()) + "," +
     ATTRIBUTE_GAS_COST + "=" +
       StringHandler.nullIfZero(invoiceFormatVerasunFdViewBean.getGasCost()) + "," +
     ATTRIBUTE_CYLINDER_RENTAL_COST + "=" +
      StringHandler.nullIfZero(invoiceFormatVerasunFdViewBean.getCylinderRentalCost()) + "," +
     ATTRIBUTE_SERVICE_FEE + "=" +
       StringHandler.nullIfZero(invoiceFormatVerasunFdViewBean.getServiceFee()) + "," +
     ATTRIBUTE_SALES_TAX + "=" +
       StringHandler.nullIfZero(invoiceFormatVerasunFdViewBean.getSalesTax()) + "," +
     ATTRIBUTE_TOTAL_DUE + "=" +
       StringHandler.nullIfZero(invoiceFormatVerasunFdViewBean.getTotalDue()) + " " +
     "where " + ATTRIBUTE_INVOICE_PERIOD + "=" +
      invoiceFormatVerasunFdViewBean.getInvoicePeriod();
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
      InvoiceFormatVerasunFdViewBean bean = new
          InvoiceFormatVerasunFdViewBean();
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
        InvoiceFormatVerasunFdViewBean invoiceFormatVerasunViewBean = new
            InvoiceFormatVerasunFdViewBean();
        load(dataSetRow, invoiceFormatVerasunViewBean);
        invoiceFormatVerasunViewBeanColl.add(invoiceFormatVerasunViewBean);
      }
      invoiceColl.add(invoiceFormatVerasunViewBeanColl);
    }
    return invoiceColl;
  }
}