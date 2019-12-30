package com.tcmis.supplier.invoice.factory;

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
import com.tcmis.supplier.invoice.beans.SupplierPaymentDetailViewBean;

/******************************************************************************
 * CLASSNAME: SupplierPaymentDetailViewBeanFactory <br>
 * @version: 1.0, Sep 18, 2007 <br>
 *****************************************************************************/

public class SupplierPaymentDetailViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_COMPANY = "COMPANY";
  public String ATTRIBUTE_PAYMENT_NUM = "PAYMENT_NUM";
  public String ATTRIBUTE_SEQUENCE_ID = "SEQUENCE_ID";
  public String ATTRIBUTE_APPLY_TO_NUM = "APPLY_TO_NUM";
  public String ATTRIBUTE_INVOICE_NUM = "INVOICE_NUM";
  public String ATTRIBUTE_VOUCHER_NUM = "VOUCHER_NUM";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_INVOICE_AMT = "INVOICE_AMT";
  public String ATTRIBUTE_PO_NUM = "PO_NUM";
  public String ATTRIBUTE_AMT_APPLIED = "AMT_APPLIED";
  public String ATTRIBUTE_AMT_DISC_TAKEN = "AMT_DISC_TAKEN";
  public String ATTRIBUTE_LINE_DESC = "LINE_DESC";

  //table name
  public String TABLE = "SUPPLIER_PAYMENT_DETAIL_VIEW";

  //constructor
  public SupplierPaymentDetailViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("company")) {
      return ATTRIBUTE_COMPANY;
    }
    else if (attributeName.equals("paymentNum")) {
      return ATTRIBUTE_PAYMENT_NUM;
    }
    else if (attributeName.equals("sequenceId")) {
      return ATTRIBUTE_SEQUENCE_ID;
    }
    else if (attributeName.equals("applyToNum")) {
      return ATTRIBUTE_APPLY_TO_NUM;
    }
    else if (attributeName.equals("invoiceNum")) {
      return ATTRIBUTE_INVOICE_NUM;
    }
    else if (attributeName.equals("voucherNum")) {
      return ATTRIBUTE_VOUCHER_NUM;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else if (attributeName.equals("invoiceAmt")) {
      return ATTRIBUTE_INVOICE_AMT;
    }
    else if (attributeName.equals("poNum")) {
      return ATTRIBUTE_PO_NUM;
    }
    else if (attributeName.equals("amtApplied")) {
      return ATTRIBUTE_AMT_APPLIED;
    }
    else if (attributeName.equals("amtDiscTaken")) {
      return ATTRIBUTE_AMT_DISC_TAKEN;
    }
    else if (attributeName.equals("lineDesc")) {
      return ATTRIBUTE_LINE_DESC;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, SupplierPaymentDetailViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
       public int delete(SupplierPaymentDetailViewBean supplierPaymentDetailViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("company", "SearchCriterion.EQUALS",
     "" + supplierPaymentDetailViewBean.getCompany());
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
   public int delete(SupplierPaymentDetailViewBean supplierPaymentDetailViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("company", "SearchCriterion.EQUALS",
     "" + supplierPaymentDetailViewBean.getCompany());
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
       public int insert(SupplierPaymentDetailViewBean supplierPaymentDetailViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(supplierPaymentDetailViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(SupplierPaymentDetailViewBean supplierPaymentDetailViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_COMPANY + "," +
     ATTRIBUTE_PAYMENT_NUM + "," +
     ATTRIBUTE_SEQUENCE_ID + "," +
     ATTRIBUTE_APPLY_TO_NUM + "," +
     ATTRIBUTE_INVOICE_NUM + "," +
     ATTRIBUTE_VOUCHER_NUM + "," +
     ATTRIBUTE_INVOICE_DATE + "," +
     ATTRIBUTE_INVOICE_AMT + "," +
     ATTRIBUTE_PO_NUM + "," +
     ATTRIBUTE_AMT_APPLIED + "," +
     ATTRIBUTE_AMT_DISC_TAKEN + "," +
     ATTRIBUTE_LINE_DESC + ")" +
     " values (" +
       SqlHandler.delimitString(supplierPaymentDetailViewBean.getCompany()) + "," +
       SqlHandler.delimitString(supplierPaymentDetailViewBean.getPaymentNum()) + "," +
     supplierPaymentDetailViewBean.getSequenceId() + "," +
       SqlHandler.delimitString(supplierPaymentDetailViewBean.getApplyToNum()) + "," +
       SqlHandler.delimitString(supplierPaymentDetailViewBean.getInvoiceNum()) + "," +
       SqlHandler.delimitString(supplierPaymentDetailViewBean.getVoucherNum()) + "," +
     DateHandler.getOracleToDateFunction(supplierPaymentDetailViewBean.getInvoiceDate()) + "," +
     supplierPaymentDetailViewBean.getInvoiceAmt() + "," +
     SqlHandler.delimitString(supplierPaymentDetailViewBean.getPoNum()) + "," +
     supplierPaymentDetailViewBean.getAmtApplied() + "," +
     supplierPaymentDetailViewBean.getAmtDiscTaken() + "," +
       SqlHandler.delimitString(supplierPaymentDetailViewBean.getLineDesc()) + ")";
    return sqlManager.update(conn, query);
   }
//update
       public int update(SupplierPaymentDetailViewBean supplierPaymentDetailViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(supplierPaymentDetailViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(SupplierPaymentDetailViewBean supplierPaymentDetailViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_COMPANY + "=" +
       SqlHandler.delimitString(supplierPaymentDetailViewBean.getCompany()) + "," +
     ATTRIBUTE_PAYMENT_NUM + "=" +
       SqlHandler.delimitString(supplierPaymentDetailViewBean.getPaymentNum()) + "," +
     ATTRIBUTE_SEQUENCE_ID + "=" +
       StringHandler.nullIfZero(supplierPaymentDetailViewBean.getSequenceId()) + "," +
     ATTRIBUTE_APPLY_TO_NUM + "=" +
       SqlHandler.delimitString(supplierPaymentDetailViewBean.getApplyToNum()) + "," +
     ATTRIBUTE_INVOICE_NUM + "=" +
       SqlHandler.delimitString(supplierPaymentDetailViewBean.getInvoiceNum()) + "," +
     ATTRIBUTE_VOUCHER_NUM + "=" +
       SqlHandler.delimitString(supplierPaymentDetailViewBean.getVoucherNum()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
      DateHandler.getOracleToDateFunction(supplierPaymentDetailViewBean.getInvoiceDate()) + "," +
     ATTRIBUTE_INVOICE_AMT + "=" +
       StringHandler.nullIfZero(supplierPaymentDetailViewBean.getInvoiceAmt()) + "," +
     ATTRIBUTE_PO_NUM + "=" +
       SqlHandler.delimitString(supplierPaymentDetailViewBean.getPoNum()) + "," +
     ATTRIBUTE_AMT_APPLIED + "=" +
       StringHandler.nullIfZero(supplierPaymentDetailViewBean.getAmtApplied()) + "," +
     ATTRIBUTE_AMT_DISC_TAKEN + "=" +
      StringHandler.nullIfZero(supplierPaymentDetailViewBean.getAmtDiscTaken()) + "," +
     ATTRIBUTE_LINE_DESC + "=" +
       SqlHandler.delimitString(supplierPaymentDetailViewBean.getLineDesc()) + " " +
     "where " + ATTRIBUTE_COMPANY + "=" +
      supplierPaymentDetailViewBean.getCompany();
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
    Collection supplierPaymentDetailViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      SupplierPaymentDetailViewBean supplierPaymentDetailViewBean = new
          SupplierPaymentDetailViewBean();
      load(dataSetRow, supplierPaymentDetailViewBean);
      supplierPaymentDetailViewBeanColl.add(supplierPaymentDetailViewBean);
    }
    return supplierPaymentDetailViewBeanColl;
  }
}