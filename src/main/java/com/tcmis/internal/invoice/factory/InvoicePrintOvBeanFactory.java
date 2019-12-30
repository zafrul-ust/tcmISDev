package com.tcmis.internal.invoice.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.internal.invoice.beans.InvoicePrintOvBean;

/******************************************************************************
 * CLASSNAME: InvoicePrintOvBeanFactory <br>
 * @version: 1.0, Mar 9, 2005 <br>
 *****************************************************************************/

public class InvoicePrintOvBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_ACCOUNT_SYS_ID = "ACCOUNT_SYS_ID";
  public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_TRANSACTION_DATE = "TRANSACTION_DATE";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_ACCOUNT_SYS_LABEL = "ACCOUNT_SYS_LABEL";
  public String ATTRIBUTE_LOCATION_LABEL = "LOCATION_LABEL";
  public String ATTRIBUTE_INVOICE_GROUP = "INVOICE_GROUP";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_COMMODITY = "COMMODITY";
  public String ATTRIBUTE_ORIGINAL_INVOICE = "ORIGINAL_INVOICE";
  public String ATTRIBUTE_CREDIT_INVOICE = "CREDIT_INVOICE";
  public String ATTRIBUTE_CREDIT_CARD_NUMBER = "CREDIT_CARD_NUMBER";
  public String ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE =
      "CREDIT_CARD_EXPIRATION_DATE";
  public String ATTRIBUTE_INVOICE_LINES = "INVOICE_LINES";

  //table name
  public String TABLE = "INVOICE_PRINT_OV";

  //constructor
  public InvoicePrintOvBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("accountSysId")) {
      return ATTRIBUTE_ACCOUNT_SYS_ID;
    }
    else if (attributeName.equals("invoiceAmount")) {
      return ATTRIBUTE_INVOICE_AMOUNT;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else if (attributeName.equals("transactionDate")) {
      return ATTRIBUTE_TRANSACTION_DATE;
    }
    else if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else if (attributeName.equals("accountSysLabel")) {
      return ATTRIBUTE_ACCOUNT_SYS_LABEL;
    }
    else if (attributeName.equals("locationLabel")) {
      return ATTRIBUTE_LOCATION_LABEL;
    }
    else if (attributeName.equals("invoiceGroup")) {
      return ATTRIBUTE_INVOICE_GROUP;
    }
    else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
    }
    else if (attributeName.equals("commodity")) {
      return ATTRIBUTE_COMMODITY;
    }
    else if (attributeName.equals("originalInvoice")) {
      return ATTRIBUTE_ORIGINAL_INVOICE;
    }
    else if (attributeName.equals("creditInvoice")) {
      return ATTRIBUTE_CREDIT_INVOICE;
    }
    else if (attributeName.equals("creditCardNumber")) {
      return ATTRIBUTE_CREDIT_CARD_NUMBER;
    }
    else if (attributeName.equals("creditCardExpirationDate")) {
      return ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE;
    }
    else if (attributeName.equals("invoiceLines")) {
      return ATTRIBUTE_INVOICE_LINES;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoicePrintOvBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoicePrintOvBean invoicePrintOvBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoicePrintOvBean.getInvoice());
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
   public int delete(InvoicePrintOvBean invoicePrintOvBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoicePrintOvBean.getInvoice());
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
   public int insert(InvoicePrintOvBean invoicePrintOvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoicePrintOvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoicePrintOvBean invoicePrintOvBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_ACCOUNT_SYS_ID + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "," +
     ATTRIBUTE_INVOICE_DATE + "," +
     ATTRIBUTE_TRANSACTION_DATE + "," +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_ACCOUNT_SYS_LABEL + "," +
     ATTRIBUTE_LOCATION_LABEL + "," +
     ATTRIBUTE_INVOICE_GROUP + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_COMMODITY + "," +
     ATTRIBUTE_ORIGINAL_INVOICE + "," +
     ATTRIBUTE_CREDIT_INVOICE + "," +
     ATTRIBUTE_CREDIT_CARD_NUMBER + "," +
     ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE + "," +
     ATTRIBUTE_INVOICE_LINES + ")" +
   values (
     StringHandler.nullIfZero(invoicePrintOvBean.getInvoice()) + "," +
     SqlHandler.delimitString(invoicePrintOvBean.getCompanyId()) + "," +
     SqlHandler.delimitString(invoicePrintOvBean.getFacilityId()) + "," +
     SqlHandler.delimitString(invoicePrintOvBean.getAccountSysId()) + "," +
     StringHandler.nullIfZero(invoicePrintOvBean.getInvoiceAmount()) + "," +
       DateHandler.getOracleToDateFunction(invoicePrintOvBean.getInvoiceDate()) + "," +
     DateHandler.getOracleToDateFunction(invoicePrintOvBean.getTransactionDate()) + "," +
     StringHandler.nullIfZero(invoicePrintOvBean.getInvoicePeriod()) + "," +
     SqlHandler.delimitString(invoicePrintOvBean.getAccountSysLabel()) + "," +
     SqlHandler.delimitString(invoicePrintOvBean.getLocationLabel()) + "," +
     SqlHandler.delimitString(invoicePrintOvBean.getInvoiceGroup()) + "," +
     SqlHandler.delimitString(invoicePrintOvBean.getPoNumber()) + "," +
     SqlHandler.delimitString(invoicePrintOvBean.getCommodity()) + "," +
     StringHandler.nullIfZero(invoicePrintOvBean.getOriginalInvoice()) + "," +
     StringHandler.nullIfZero(invoicePrintOvBean.getCreditInvoice()) + "," +
     StringHandler.nullIfZero(invoicePrintOvBean.getCreditCardNumber()) + "," +
     DateHandler.getOracleToDateFunction(invoicePrintOvBean.getCreditCardExpirationDate()) + "," +
     SqlHandler.delimitString(invoicePrintOvBean.getInvoiceLines()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoicePrintOvBean invoicePrintOvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoicePrintOvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoicePrintOvBean invoicePrintOvBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoicePrintOvBean.getInvoice()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(invoicePrintOvBean.getCompanyId()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(invoicePrintOvBean.getFacilityId()) + "," +
     ATTRIBUTE_ACCOUNT_SYS_ID + "=" +
      SqlHandler.delimitString(invoicePrintOvBean.getAccountSysId()) + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "=" +
      StringHandler.nullIfZero(invoicePrintOvBean.getInvoiceAmount()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
       DateHandler.getOracleToDateFunction(invoicePrintOvBean.getInvoiceDate()) + "," +
     ATTRIBUTE_TRANSACTION_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoicePrintOvBean.getTransactionDate()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoicePrintOvBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_ACCOUNT_SYS_LABEL + "=" +
      SqlHandler.delimitString(invoicePrintOvBean.getAccountSysLabel()) + "," +
     ATTRIBUTE_LOCATION_LABEL + "=" +
      SqlHandler.delimitString(invoicePrintOvBean.getLocationLabel()) + "," +
     ATTRIBUTE_INVOICE_GROUP + "=" +
      SqlHandler.delimitString(invoicePrintOvBean.getInvoiceGroup()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
      SqlHandler.delimitString(invoicePrintOvBean.getPoNumber()) + "," +
     ATTRIBUTE_COMMODITY + "=" +
      SqlHandler.delimitString(invoicePrintOvBean.getCommodity()) + "," +
     ATTRIBUTE_ORIGINAL_INVOICE + "=" +
      StringHandler.nullIfZero(invoicePrintOvBean.getOriginalInvoice()) + "," +
     ATTRIBUTE_CREDIT_INVOICE + "=" +
      StringHandler.nullIfZero(invoicePrintOvBean.getCreditInvoice()) + "," +
     ATTRIBUTE_CREDIT_CARD_NUMBER + "=" +
       StringHandler.nullIfZero(invoicePrintOvBean.getCreditCardNumber()) + "," +
     ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoicePrintOvBean.getCreditCardExpirationDate()) + "," +
     ATTRIBUTE_INVOICE_LINES + "=" +
      SqlHandler.delimitString(invoicePrintOvBean.getInvoiceLines()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoicePrintOvBean.getInvoice());
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

    Collection invoicePrintOvBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoicePrintOvBean invoicePrintOvBean = new InvoicePrintOvBean();
      load(dataSetRow, invoicePrintOvBean);
      invoicePrintOvBeanColl.add(invoicePrintOvBean);
    }

    return invoicePrintOvBeanColl;
  }

  public Collection selectObject(SearchCriteria criteria) throws BaseException,
      Exception {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      java.util.Map map = connection.getTypeMap();
      map.put("INVOICE_PRINT_OBJ",
              Class.forName(
          "com.tcmis.internal.invoice.beans.InvoicePrintOvBean"));
      map.put("INVOICE_LINE_PRINT_OBJ",
              Class.forName("com.tcmis.internal.invoice.beans.InvoiceLineBean"));

      c = selectObject(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectObject(SearchCriteria criteria, Connection conn) throws
      BaseException, Exception {

    Collection invoicePrintBeanColl = new Vector();

    String query = "select value(p) from " + TABLE + " p " +
        getWhereClause(criteria);

    Statement st = conn.createStatement();
    ResultSet rs = st.executeQuery(query);
    while (rs.next()) {
      InvoicePrintOvBean b = (InvoicePrintOvBean) rs.getObject(1);
      invoicePrintBeanColl.add(b);
    }
    rs.close();
    st.close();
    return invoicePrintBeanColl;
  }

}