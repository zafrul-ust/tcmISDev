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
import com.tcmis.supplier.invoice.beans.SupplierPaymentHeaderViewBean;

/******************************************************************************
 * CLASSNAME: SupplierPaymentHeaderViewBeanFactory <br>
 * @version: 1.0, Sep 18, 2007 <br>
 *****************************************************************************/

public class SupplierPaymentHeaderViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_COMPANY = "COMPANY";
  public String ATTRIBUTE_OPERATING_ENTITY_NAME = "OPERATING_ENTITY_NAME";
  public String ATTRIBUTE_PAYMENT_NUM = "PAYMENT_NUM";
  public String ATTRIBUTE_CHECK_NUM = "CHECK_NUM";
  public String ATTRIBUTE_CHECK_DATE = "CHECK_DATE";
  public String ATTRIBUTE_VENDOR_CODE = "VENDOR_CODE";
  public String ATTRIBUTE_PAY_TO_CODE = "PAY_TO_CODE";
  public String ATTRIBUTE_PAYEE_NAME = "PAYEE_NAME";
  public String ATTRIBUTE_DOC_DESC = "DOC_DESC";
  public String ATTRIBUTE_AMT_NET = "AMT_NET";
  public String ATTRIBUTE_AMT_ON_ACCT = "AMT_ON_ACCT";
  public String ATTRIBUTE_CURRENCY_CODE = "CURRENCY_CODE";
  public String ATTRIBUTE_PAYMENT_CODE = "PAYMENT_CODE";
  public String ATTRIBUTE_PAYMENT_TYPE = "PAYMENT_TYPE";
  public String ATTRIBUTE_AP_CONTACT_NAME = "AP_CONTACT_NAME";
  public String ATTRIBUTE_AP_CONTACT_EMAIL = "AP_CONTACT_EMAIL";
  public String ATTRIBUTE_AP_CONTACT_PHONE = "AP_CONTACT_PHONE";
  public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
  public String ATTRIBUTE_SUPPLIER = "SUPPLIER";

  //table name
  public String TABLE = "SUPPLIER_PAYMENT_HEADER_VIEW";

  //constructor
  public SupplierPaymentHeaderViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("company")) {
      return ATTRIBUTE_COMPANY;
    }
    else if (attributeName.equals("operatingEntityName")) {
      return ATTRIBUTE_OPERATING_ENTITY_NAME;
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
    else if (attributeName.equals("currencyCode")) {
      return ATTRIBUTE_CURRENCY_CODE;
    }
    else if (attributeName.equals("paymentCode")) {
      return ATTRIBUTE_PAYMENT_CODE;
    }
    else if (attributeName.equals("paymentType")) {
      return ATTRIBUTE_PAYMENT_TYPE;
    }
    else if (attributeName.equals("apContactName")) {
      return ATTRIBUTE_AP_CONTACT_NAME;
    }
    else if (attributeName.equals("apContactEmail")) {
      return ATTRIBUTE_AP_CONTACT_EMAIL;
    }
    else if (attributeName.equals("apContactPhone")) {
      return ATTRIBUTE_AP_CONTACT_PHONE;
    }
    else if (attributeName.equals("supplierName")) {
      return ATTRIBUTE_SUPPLIER_NAME;
    }
    else if (attributeName.equals("supplier")) {
      return ATTRIBUTE_SUPPLIER;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, SupplierPaymentHeaderViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
       public int delete(SupplierPaymentHeaderViewBean supplierPaymentHeaderViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("company", "SearchCriterion.EQUALS",
     "" + supplierPaymentHeaderViewBean.getCompany());
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
   public int delete(SupplierPaymentHeaderViewBean supplierPaymentHeaderViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("company", "SearchCriterion.EQUALS",
     "" + supplierPaymentHeaderViewBean.getCompany());
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
       public int insert(SupplierPaymentHeaderViewBean supplierPaymentHeaderViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(supplierPaymentHeaderViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(SupplierPaymentHeaderViewBean supplierPaymentHeaderViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_COMPANY + "," +
     ATTRIBUTE_OPERATING_ENTITY_NAME + "," +
     ATTRIBUTE_PAYMENT_NUM + "," +
     ATTRIBUTE_CHECK_NUM + "," +
     ATTRIBUTE_CHECK_DATE + "," +
     ATTRIBUTE_VENDOR_CODE + "," +
     ATTRIBUTE_PAY_TO_CODE + "," +
     ATTRIBUTE_PAYEE_NAME + "," +
     ATTRIBUTE_DOC_DESC + "," +
     ATTRIBUTE_AMT_NET + "," +
     ATTRIBUTE_AMT_ON_ACCT + "," +
     ATTRIBUTE_CURRENCY_CODE + "," +
     ATTRIBUTE_PAYMENT_CODE + "," +
     ATTRIBUTE_PAYMENT_TYPE + "," +
     ATTRIBUTE_AP_CONTACT_NAME + "," +
     ATTRIBUTE_AP_CONTACT_EMAIL + "," +
     ATTRIBUTE_AP_CONTACT_PHONE + "," +
     ATTRIBUTE_SUPPLIER_NAME + "," +
     ATTRIBUTE_SUPPLIER + ")" +
     " values (" +
       SqlHandler.delimitString(supplierPaymentHeaderViewBean.getCompany()) + "," +
     SqlHandler.delimitString(supplierPaymentHeaderViewBean.getOperatingEntityName()) + "," +
       SqlHandler.delimitString(supplierPaymentHeaderViewBean.getPaymentNum()) + "," +
       SqlHandler.delimitString(supplierPaymentHeaderViewBean.getCheckNum()) + "," +
     DateHandler.getOracleToDateFunction(supplierPaymentHeaderViewBean.getCheckDate()) + "," +
       SqlHandler.delimitString(supplierPaymentHeaderViewBean.getVendorCode()) + "," +
       SqlHandler.delimitString(supplierPaymentHeaderViewBean.getPayToCode()) + "," +
       SqlHandler.delimitString(supplierPaymentHeaderViewBean.getPayeeName()) + "," +
       SqlHandler.delimitString(supplierPaymentHeaderViewBean.getDocDesc()) + "," +
     supplierPaymentHeaderViewBean.getAmtNet() + "," +
     supplierPaymentHeaderViewBean.getAmtOnAcct() + "," +
     SqlHandler.delimitString(supplierPaymentHeaderViewBean.getCurrencyCode()) + "," +
       SqlHandler.delimitString(supplierPaymentHeaderViewBean.getPaymentCode()) + "," +
     supplierPaymentHeaderViewBean.getPaymentType() + "," +
     SqlHandler.delimitString(supplierPaymentHeaderViewBean.getApContactName()) + "," +
     SqlHandler.delimitString(supplierPaymentHeaderViewBean.getApContactEmail()) + "," +
     SqlHandler.delimitString(supplierPaymentHeaderViewBean.getApContactPhone()) + "," +
     SqlHandler.delimitString(supplierPaymentHeaderViewBean.getSupplierName()) + "," +
       SqlHandler.delimitString(supplierPaymentHeaderViewBean.getSupplier()) + ")";
    return sqlManager.update(conn, query);
   }
//update
       public int update(SupplierPaymentHeaderViewBean supplierPaymentHeaderViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(supplierPaymentHeaderViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(SupplierPaymentHeaderViewBean supplierPaymentHeaderViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_COMPANY + "=" +
       SqlHandler.delimitString(supplierPaymentHeaderViewBean.getCompany()) + "," +
     ATTRIBUTE_OPERATING_ENTITY_NAME + "=" +
      SqlHandler.delimitString(supplierPaymentHeaderViewBean.getOperatingEntityName()) + "," +
     ATTRIBUTE_PAYMENT_NUM + "=" +
       SqlHandler.delimitString(supplierPaymentHeaderViewBean.getPaymentNum()) + "," +
     ATTRIBUTE_CHECK_NUM + "=" +
       SqlHandler.delimitString(supplierPaymentHeaderViewBean.getCheckNum()) + "," +
     ATTRIBUTE_CHECK_DATE + "=" +
      DateHandler.getOracleToDateFunction(supplierPaymentHeaderViewBean.getCheckDate()) + "," +
     ATTRIBUTE_VENDOR_CODE + "=" +
       SqlHandler.delimitString(supplierPaymentHeaderViewBean.getVendorCode()) + "," +
     ATTRIBUTE_PAY_TO_CODE + "=" +
       SqlHandler.delimitString(supplierPaymentHeaderViewBean.getPayToCode()) + "," +
     ATTRIBUTE_PAYEE_NAME + "=" +
       SqlHandler.delimitString(supplierPaymentHeaderViewBean.getPayeeName()) + "," +
     ATTRIBUTE_DOC_DESC + "=" +
       SqlHandler.delimitString(supplierPaymentHeaderViewBean.getDocDesc()) + "," +
     ATTRIBUTE_AMT_NET + "=" +
       StringHandler.nullIfZero(supplierPaymentHeaderViewBean.getAmtNet()) + "," +
     ATTRIBUTE_AMT_ON_ACCT + "=" +
       StringHandler.nullIfZero(supplierPaymentHeaderViewBean.getAmtOnAcct()) + "," +
     ATTRIBUTE_CURRENCY_CODE + "=" +
      SqlHandler.delimitString(supplierPaymentHeaderViewBean.getCurrencyCode()) + "," +
     ATTRIBUTE_PAYMENT_CODE + "=" +
       SqlHandler.delimitString(supplierPaymentHeaderViewBean.getPaymentCode()) + "," +
     ATTRIBUTE_PAYMENT_TYPE + "=" +
       StringHandler.nullIfZero(supplierPaymentHeaderViewBean.getPaymentType()) + "," +
     ATTRIBUTE_AP_CONTACT_NAME + "=" +
      SqlHandler.delimitString(supplierPaymentHeaderViewBean.getApContactName()) + "," +
     ATTRIBUTE_AP_CONTACT_EMAIL + "=" +
      SqlHandler.delimitString(supplierPaymentHeaderViewBean.getApContactEmail()) + "," +
     ATTRIBUTE_AP_CONTACT_PHONE + "=" +
      SqlHandler.delimitString(supplierPaymentHeaderViewBean.getApContactPhone()) + "," +
     ATTRIBUTE_SUPPLIER_NAME + "=" +
      SqlHandler.delimitString(supplierPaymentHeaderViewBean.getSupplierName()) + "," +
     ATTRIBUTE_SUPPLIER + "=" +
       SqlHandler.delimitString(supplierPaymentHeaderViewBean.getSupplier()) + " " +
     "where " + ATTRIBUTE_COMPANY + "=" +
      supplierPaymentHeaderViewBean.getCompany();
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
    Collection supplierPaymentHeaderViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      SupplierPaymentHeaderViewBean supplierPaymentHeaderViewBean = new
          SupplierPaymentHeaderViewBean();
      load(dataSetRow, supplierPaymentHeaderViewBean);
      supplierPaymentHeaderViewBeanColl.add(supplierPaymentHeaderViewBean);
    }
    return supplierPaymentHeaderViewBeanColl;
  }

  public Collection selectDetail(SearchCriteria criteria, SortCriteria sortCriteria) throws
      BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectDetail(criteria, sortCriteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectDetail(SearchCriteria criteria, SortCriteria sortCriteria,
                           Connection conn) throws BaseException {
    Collection supplierPaymentHeaderViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    SupplierPaymentDetailViewBeanFactory detailFactory = new SupplierPaymentDetailViewBeanFactory(this.getDbManager());
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      SupplierPaymentHeaderViewBean supplierPaymentHeaderViewBean = new
          SupplierPaymentHeaderViewBean();
      load(dataSetRow, supplierPaymentHeaderViewBean);
      supplierPaymentHeaderViewBean.setSupplierPaymentDetailViewBeanCollection(detailFactory.select(new SearchCriteria("paymentNum", SearchCriterion.EQUALS, supplierPaymentHeaderViewBean.getPaymentNum()), new SortCriteria()));
      supplierPaymentHeaderViewBeanColl.add(supplierPaymentHeaderViewBean);
    }
    return supplierPaymentHeaderViewBeanColl;
  }
}