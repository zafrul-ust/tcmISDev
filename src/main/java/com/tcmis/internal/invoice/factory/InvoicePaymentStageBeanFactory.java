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
import com.tcmis.internal.invoice.beans.InvoicePaymentStageBean;

/******************************************************************************
 * CLASSNAME: InvoicePaymentStageBeanFactory <br>
 * @version: 1.0, Mar 17, 2006 <br>
 *****************************************************************************/

public class InvoicePaymentStageBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_TOTAL_AMOUNT_PAID = "TOTAL_AMOUNT_PAID";
  public String ATTRIBUTE_TRANSACTION_REFERENCE_NUMBER = "TRANSACTION_REFERENCE_NUMBER";
  public String ATTRIBUTE_TRANSACTION_CREATION_DATE = "TRANSACTION_CREATION_DATE";
  public String ATTRIBUTE_PAYEE_COMPANY_ID = "PAYEE_COMPANY_ID";
  public String ATTRIBUTE_PAYER_COMPANY_ID = "PAYER_COMPANY_ID";
  public String ATTRIBUTE_TRANSACTION_TYPE = "TRANSACTION_TYPE";
  public String ATTRIBUTE_INVOICE_NUMBER = "INVOICE_NUMBER";
  public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_CHECK_ISSUE_DATE = "CHECK_ISSUE_DATE";
  public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
  public String ATTRIBUTE_PAYER_CONTACT_PHONE = "PAYER_CONTACT_PHONE";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_REMITTANCE_NOTES = "REMITTANCE_NOTES";

  //table name
  public String TABLE = "INVOICE_PAYMENT_STAGE";

  //constructor
  public InvoicePaymentStageBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("totalAmountPaid")) {
      return ATTRIBUTE_TOTAL_AMOUNT_PAID;
    }
    else if (attributeName.equals("transactionReferenceNumber")) {
      return ATTRIBUTE_TRANSACTION_REFERENCE_NUMBER;
    }
    else if (attributeName.equals("transactionCreationDate")) {
      return ATTRIBUTE_TRANSACTION_CREATION_DATE;
    }
    else if (attributeName.equals("payeeCompanyId")) {
      return ATTRIBUTE_PAYEE_COMPANY_ID;
    }
    else if (attributeName.equals("payerCompanyId")) {
      return ATTRIBUTE_PAYER_COMPANY_ID;
    }
    else if (attributeName.equals("transactionType")) {
      return ATTRIBUTE_TRANSACTION_TYPE;
    }
    else if (attributeName.equals("invoiceNumber")) {
      return ATTRIBUTE_INVOICE_NUMBER;
    }
    else if (attributeName.equals("invoiceAmount")) {
      return ATTRIBUTE_INVOICE_AMOUNT;
    }
    else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
    }
    else if (attributeName.equals("checkIssueDate")) {
      return ATTRIBUTE_CHECK_ISSUE_DATE;
    }
    else if (attributeName.equals("currencyId")) {
      return ATTRIBUTE_CURRENCY_ID;
    }
    else if (attributeName.equals("payerContactPhone")) {
      return ATTRIBUTE_PAYER_CONTACT_PHONE;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else if (attributeName.equals("remittanceNotes")) {
      return ATTRIBUTE_REMITTANCE_NOTES;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoicePaymentStageBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoicePaymentStageBean invoicePaymentStageBean)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("totalAmountPaid", "SearchCriterion.EQUALS",
     "" + invoicePaymentStageBean.getTotalAmountPaid());
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
   public int delete(InvoicePaymentStageBean invoicePaymentStageBean, Connection conn)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("totalAmountPaid", "SearchCriterion.EQUALS",
     "" + invoicePaymentStageBean.getTotalAmountPaid());
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

  public int delete(SearchCriteria criteria, Connection conn) throws BaseException {
    String sqlQuery = " delete from " + TABLE + " " +
        getWhereClause(criteria);
    return new SqlManager().update(conn, sqlQuery);
  }

  //insert
  public int insert(InvoicePaymentStageBean invoicePaymentStageBean) throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(invoicePaymentStageBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(InvoicePaymentStageBean invoicePaymentStageBean, Connection conn) throws
      BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_TOTAL_AMOUNT_PAID + "," +
        ATTRIBUTE_TRANSACTION_REFERENCE_NUMBER + "," +
        ATTRIBUTE_TRANSACTION_CREATION_DATE + "," +
        ATTRIBUTE_PAYEE_COMPANY_ID + "," +
        ATTRIBUTE_PAYER_COMPANY_ID + "," +
        ATTRIBUTE_TRANSACTION_TYPE + "," +
        ATTRIBUTE_INVOICE_NUMBER + "," +
        ATTRIBUTE_INVOICE_AMOUNT + "," +
        ATTRIBUTE_PO_NUMBER + "," +
        ATTRIBUTE_CHECK_ISSUE_DATE + "," +
        ATTRIBUTE_CURRENCY_ID + "," +
        ATTRIBUTE_PAYER_CONTACT_PHONE + "," +
        ATTRIBUTE_INVOICE_DATE + "," +
        ATTRIBUTE_REMITTANCE_NOTES + ")" +
        " values (" +
        invoicePaymentStageBean.getTotalAmountPaid() + "," +
        SqlHandler.delimitString(invoicePaymentStageBean.getTransactionReferenceNumber()) +
        "," +
        DateHandler.getOracleToDateFunction(invoicePaymentStageBean.
                                            getTransactionCreationDate()) + "," +
        SqlHandler.delimitString(invoicePaymentStageBean.getPayeeCompanyId()) + "," +
        SqlHandler.delimitString(invoicePaymentStageBean.getPayerCompanyId()) + "," +
        SqlHandler.delimitString(invoicePaymentStageBean.getTransactionType()) + "," +
        SqlHandler.delimitString(invoicePaymentStageBean.getInvoiceNumber()) + "," +
        invoicePaymentStageBean.getInvoiceAmount() + "," +
        SqlHandler.delimitString(invoicePaymentStageBean.getPoNumber()) + "," +
        DateHandler.getOracleToDateFunction(invoicePaymentStageBean.getCheckIssueDate()) +
        "," +
        SqlHandler.delimitString(invoicePaymentStageBean.getCurrencyId()) + "," +
        SqlHandler.delimitString(invoicePaymentStageBean.getPayerContactPhone()) + "," +
        DateHandler.getOracleToDateFunction(invoicePaymentStageBean.getInvoiceDate()) +
        "," +
        SqlHandler.delimitString(invoicePaymentStageBean.getRemittanceNotes()) + ")";
System.out.println("QUERY:" + query);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    return sqlManager.update(conn, query);
  }

  /*
//update
   public int update(InvoicePaymentStageBean invoicePaymentStageBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoicePaymentStageBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoicePaymentStageBean invoicePaymentStageBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_TOTAL_AMOUNT_PAID + "=" +
      StringHandler.nullIfZero(invoicePaymentStageBean.getTotalAmountPaid()) + "," +
     ATTRIBUTE_TRANSACTION_REFERENCE_NUMBER + "=" +
       SqlHandler.delimitString(invoicePaymentStageBean.getTransactionReferenceNumber()) + "," +
     ATTRIBUTE_TRANSACTION_CREATION_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoicePaymentStageBean.getTransactionCreationDate()) + "," +
     ATTRIBUTE_PAYEE_COMPANY_ID + "=" +
      SqlHandler.delimitString(invoicePaymentStageBean.getPayeeCompanyId()) + "," +
     ATTRIBUTE_PAYER_COMPANY_ID + "=" +
      SqlHandler.delimitString(invoicePaymentStageBean.getPayerCompanyId()) + "," +
     ATTRIBUTE_TRANSACTION_TYPE + "=" +
      SqlHandler.delimitString(invoicePaymentStageBean.getTransactionType()) + "," +
     ATTRIBUTE_INVOICE_NUMBER + "=" +
      SqlHandler.delimitString(invoicePaymentStageBean.getInvoiceNumber()) + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "=" +
      StringHandler.nullIfZero(invoicePaymentStageBean.getInvoiceAmount()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
      SqlHandler.delimitString(invoicePaymentStageBean.getPoNumber()) + "," +
     ATTRIBUTE_CHECK_ISSUE_DATE + "=" +
       DateHandler.getOracleToDateFunction(invoicePaymentStageBean.getCheckIssueDate()) + "," +
     ATTRIBUTE_CURRENCY_ID + "=" +
      SqlHandler.delimitString(invoicePaymentStageBean.getCurrencyId()) + "," +
     ATTRIBUTE_PAYER_CONTACT_PHONE + "=" +
      SqlHandler.delimitString(invoicePaymentStageBean.getPayerContactPhone()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
       DateHandler.getOracleToDateFunction(invoicePaymentStageBean.getInvoiceDate()) + "," +
     ATTRIBUTE_REMITTANCE_NOTES + "=" +
      SqlHandler.delimitString(invoicePaymentStageBean.getRemittanceNotes()) + " " +
     "where " + ATTRIBUTE_TOTAL_AMOUNT_PAID + "=" +
      invoicePaymentStageBean.getTotalAmountPaid();
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

  public Collection select(SearchCriteria criteria, Connection conn) throws BaseException {
    Collection invoicePaymentStageBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoicePaymentStageBean invoicePaymentStageBean = new InvoicePaymentStageBean();
      load(dataSetRow, invoicePaymentStageBean);
      invoicePaymentStageBeanColl.add(invoicePaymentStageBean);
    }
    return invoicePaymentStageBeanColl;
  }

  public boolean doesCheckExist(String checkNumber) throws BaseException {
    Connection connection = null;
    boolean b = false;
    try {
      connection = this.getDbManager().getConnection();
      b = doesCheckExist(checkNumber, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return b;
  }

  public boolean doesCheckExist(String checkNumber, Connection conn) throws BaseException {
    //Collection invoicePaymentStageBeanColl = new Vector();
    boolean b = false;
    String query = "select * from " + TABLE + 
        " where " + ATTRIBUTE_TRANSACTION_REFERENCE_NUMBER + " = " + 
        SqlHandler.delimitString(checkNumber);
System.out.println("QUERY:" + query);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext() && !b) {
      b = true;
    }
    return b;
  }
}