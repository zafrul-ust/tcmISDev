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
import com.tcmis.supplier.invoice.beans.SupplierPaymentHeaderBean;

/******************************************************************************
 * CLASSNAME: SupplierPaymentHeaderBeanFactory <br>
 * @version: 1.0, Aug 22, 2007 <br>
 *****************************************************************************/

public class SupplierPaymentHeaderBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_COMPANY = "COMPANY";
  public String ATTRIBUTE_PAYMENT_NUM = "PAYMENT_NUM";
  public String ATTRIBUTE_CHECK_NUM = "CHECK_NUM";
  public String ATTRIBUTE_CHECK_DATE = "CHECK_DATE";
  public String ATTRIBUTE_VENDOR_CODE = "VENDOR_CODE";
  public String ATTRIBUTE_PAY_TO_CODE = "PAY_TO_CODE";
  public String ATTRIBUTE_CURRENCY_CODE = "CURRENCY_CODE";
  public String ATTRIBUTE_PAYEE_NAME = "PAYEE_NAME";
  public String ATTRIBUTE_DOC_DESC = "DOC_DESC";
  public String ATTRIBUTE_AMT_NET = "AMT_NET";
  public String ATTRIBUTE_AMT_ON_ACCT = "AMT_ON_ACCT";
  public String ATTRIBUTE_PAYMENT_CODE = "PAYMENT_CODE";
  public String ATTRIBUTE_PAYMENT_TYPE = "PAYMENT_TYPE";

  //table name
  public String TABLE = "SUPPLIER_PAYMENT_HEADER";

  //constructor
  public SupplierPaymentHeaderBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("checkNum")) {
      return ATTRIBUTE_CHECK_NUM;
    }
    else if (attributeName.equals("checkDate")) {
      return ATTRIBUTE_CHECK_DATE;
    }
    else if (attributeName.equals("vendorCode")) {
      return ATTRIBUTE_VENDOR_CODE;
    }
    else if (attributeName.equals("payToCode")) {
      return ATTRIBUTE_PAY_TO_CODE;
    }
    else if (attributeName.equals("currencyCode")) {
      return ATTRIBUTE_CURRENCY_CODE;
    }
    else if (attributeName.equals("payeeName")) {
      return ATTRIBUTE_PAYEE_NAME;
    }
    else if (attributeName.equals("docDesc")) {
      return ATTRIBUTE_DOC_DESC;
    }
    else if (attributeName.equals("amtNet")) {
      return ATTRIBUTE_AMT_NET;
    }
    else if (attributeName.equals("amtOnAcct")) {
      return ATTRIBUTE_AMT_ON_ACCT;
    }
    else if (attributeName.equals("paymentCode")) {
      return ATTRIBUTE_PAYMENT_CODE;
    }
    else if (attributeName.equals("paymentType")) {
      return ATTRIBUTE_PAYMENT_TYPE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, SupplierPaymentHeaderBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(SupplierPaymentHeaderBean supplierPaymentHeaderBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("company", "SearchCriterion.EQUALS",
     "" + supplierPaymentHeaderBean.getCompany());
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
   public int delete(SupplierPaymentHeaderBean supplierPaymentHeaderBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("company", "SearchCriterion.EQUALS",
     "" + supplierPaymentHeaderBean.getCompany());
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
   public int insert(SupplierPaymentHeaderBean supplierPaymentHeaderBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(supplierPaymentHeaderBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(SupplierPaymentHeaderBean supplierPaymentHeaderBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_COMPANY + "," +
     ATTRIBUTE_PAYMENT_NUM + "," +
     ATTRIBUTE_CHECK_NUM + "," +
     ATTRIBUTE_CHECK_DATE + "," +
     ATTRIBUTE_VENDOR_CODE + "," +
     ATTRIBUTE_PAY_TO_CODE + "," +
     ATTRIBUTE_CURRENCY_CODE + "," +
     ATTRIBUTE_PAYEE_NAME + "," +
     ATTRIBUTE_DOC_DESC + "," +
     ATTRIBUTE_AMT_NET + "," +
     ATTRIBUTE_AMT_ON_ACCT + "," +
     ATTRIBUTE_PAYMENT_CODE + "," +
     ATTRIBUTE_PAYMENT_TYPE + ")" +
     " values (" +
     SqlHandler.delimitString(supplierPaymentHeaderBean.getCompany()) + "," +
       SqlHandler.delimitString(supplierPaymentHeaderBean.getPaymentNum()) + "," +
     SqlHandler.delimitString(supplierPaymentHeaderBean.getCheckNum()) + "," +
     DateHandler.getOracleToDateFunction(supplierPaymentHeaderBean.getCheckDate()) + "," +
       SqlHandler.delimitString(supplierPaymentHeaderBean.getVendorCode()) + "," +
     SqlHandler.delimitString(supplierPaymentHeaderBean.getPayToCode()) + "," +
       SqlHandler.delimitString(supplierPaymentHeaderBean.getCurrencyCode()) + "," +
     SqlHandler.delimitString(supplierPaymentHeaderBean.getPayeeName()) + "," +
     SqlHandler.delimitString(supplierPaymentHeaderBean.getDocDesc()) + "," +
     supplierPaymentHeaderBean.getAmtNet() + "," +
     supplierPaymentHeaderBean.getAmtOnAcct() + "," +
       SqlHandler.delimitString(supplierPaymentHeaderBean.getPaymentCode()) + "," +
     supplierPaymentHeaderBean.getPaymentType() + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(SupplierPaymentHeaderBean supplierPaymentHeaderBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(supplierPaymentHeaderBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(SupplierPaymentHeaderBean supplierPaymentHeaderBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_COMPANY + "=" +
      SqlHandler.delimitString(supplierPaymentHeaderBean.getCompany()) + "," +
     ATTRIBUTE_PAYMENT_NUM + "=" +
       SqlHandler.delimitString(supplierPaymentHeaderBean.getPaymentNum()) + "," +
     ATTRIBUTE_CHECK_NUM + "=" +
      SqlHandler.delimitString(supplierPaymentHeaderBean.getCheckNum()) + "," +
     ATTRIBUTE_CHECK_DATE + "=" +
      DateHandler.getOracleToDateFunction(supplierPaymentHeaderBean.getCheckDate()) + "," +
     ATTRIBUTE_VENDOR_CODE + "=" +
       SqlHandler.delimitString(supplierPaymentHeaderBean.getVendorCode()) + "," +
     ATTRIBUTE_PAY_TO_CODE + "=" +
       SqlHandler.delimitString(supplierPaymentHeaderBean.getPayToCode()) + "," +
     ATTRIBUTE_CURRENCY_CODE + "=" +
       SqlHandler.delimitString(supplierPaymentHeaderBean.getCurrencyCode()) + "," +
     ATTRIBUTE_PAYEE_NAME + "=" +
       SqlHandler.delimitString(supplierPaymentHeaderBean.getPayeeName()) + "," +
     ATTRIBUTE_DOC_DESC + "=" +
      SqlHandler.delimitString(supplierPaymentHeaderBean.getDocDesc()) + "," +
     ATTRIBUTE_AMT_NET + "=" +
      StringHandler.nullIfZero(supplierPaymentHeaderBean.getAmtNet()) + "," +
     ATTRIBUTE_AMT_ON_ACCT + "=" +
       StringHandler.nullIfZero(supplierPaymentHeaderBean.getAmtOnAcct()) + "," +
     ATTRIBUTE_PAYMENT_CODE + "=" +
       SqlHandler.delimitString(supplierPaymentHeaderBean.getPaymentCode()) + "," +
     ATTRIBUTE_PAYMENT_TYPE + "=" +
       StringHandler.nullIfZero(supplierPaymentHeaderBean.getPaymentType()) + " " +
     "where " + ATTRIBUTE_COMPANY + "=" +
      supplierPaymentHeaderBean.getCompany();
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
    Collection supplierPaymentHeaderBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    SupplierPaymentDetailBeanFactory detailFactory = new SupplierPaymentDetailBeanFactory(this.getDbManager());
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      SupplierPaymentHeaderBean supplierPaymentHeaderBean = new
          SupplierPaymentHeaderBean();
      load(dataSetRow, supplierPaymentHeaderBean);
      supplierPaymentHeaderBean.setSupplierPaymentDetailBeanCollection(detailFactory.select(new SearchCriteria("paymentNum", SearchCriterion.EQUALS, supplierPaymentHeaderBean.getPaymentNum()), new SortCriteria()));
      supplierPaymentHeaderBeanColl.add(supplierPaymentHeaderBean);
    }
    return supplierPaymentHeaderBeanColl;
  }
}