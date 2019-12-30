package com.tcmis.internal.invoice.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.internal.invoice.beans.InvoiceFormatVerisignOvBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: InvoiceFormatVerisignOvBeanFactory <br>
 * @version: 1.0, Mar 28, 2005 <br>
 *****************************************************************************/

public class InvoiceFormatVerisignOvBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_ZIP = "ZIP";
  public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
  public String ATTRIBUTE_CREDIT_CARD_NUMBER = "CREDIT_CARD_NUMBER";
  public String ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE =
      "CREDIT_CARD_EXPIRATION_DATE";
  public String ATTRIBUTE_TXREF = "TXREF";
  public String ATTRIBUTE_RESULT_CODE = "RESULT_CODE";
  public String ATTRIBUTE_DETAIL = "DETAIL";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_INVOICE_GROUP = "INVOICE_GROUP";

  //table name
  public String TABLE = "INVOICE_FORMAT_VERISIGN_OV";

  //constructor
  public InvoiceFormatVerisignOvBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else if (attributeName.equals("zip")) {
      return ATTRIBUTE_ZIP;
    }
    else if (attributeName.equals("invoiceAmount")) {
      return ATTRIBUTE_INVOICE_AMOUNT;
    }
    else if (attributeName.equals("creditCardNumber")) {
      return ATTRIBUTE_CREDIT_CARD_NUMBER;
    }
    else if (attributeName.equals("creditCardExpirationDate")) {
      return ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE;
    }
    else if (attributeName.equals("txref")) {
      return ATTRIBUTE_TXREF;
    }
    else if (attributeName.equals("resultCode")) {
      return ATTRIBUTE_RESULT_CODE;
    }
    else if (attributeName.equals("detail")) {
      return ATTRIBUTE_DETAIL;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("invoiceGroup")) {
      return ATTRIBUTE_INVOICE_GROUP;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceFormatVerisignOvBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceFormatVerisignOvBean invoiceFormatVerisignOvBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceFormatVerisignOvBean.getInvoice());
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
   public int delete(InvoiceFormatVerisignOvBean invoiceFormatVerisignOvBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceFormatVerisignOvBean.getInvoice());
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
   public int insert(InvoiceFormatVerisignOvBean invoiceFormatVerisignOvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceFormatVerisignOvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceFormatVerisignOvBean invoiceFormatVerisignOvBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_ZIP + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "," +
     ATTRIBUTE_CREDIT_CARD_NUMBER + "," +
     ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE + "," +
     ATTRIBUTE_TXREF + "," +
     ATTRIBUTE_RESULT_CODE + "," +
     ATTRIBUTE_DETAIL + ")" +
   values (
     StringHandler.nullIfZero(invoiceFormatVerisignOvBean.getInvoice()) + "," +
       StringHandler.nullIfZero(invoiceFormatVerisignOvBean.getInvoicePeriod()) + "," +
     SqlHandler.delimitString(invoiceFormatVerisignOvBean.getZip()) + "," +
       StringHandler.nullIfZero(invoiceFormatVerisignOvBean.getInvoiceAmount()) + "," +
     StringHandler.nullIfZero(invoiceFormatVerisignOvBean.getCreditCardNumber()) + "," +
     DateHandler.getOracleToDateFunction(invoiceFormatVerisignOvBean.getCreditCardExpirationDate()) + "," +
     SqlHandler.delimitString(invoiceFormatVerisignOvBean.getTxref()) + "," +
       SqlHandler.delimitString(invoiceFormatVerisignOvBean.getResultCode()) + "," +
     SqlHandler.delimitString(invoiceFormatVerisignOvBean.getDetail()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceFormatVerisignOvBean invoiceFormatVerisignOvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceFormatVerisignOvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceFormatVerisignOvBean invoiceFormatVerisignOvBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceFormatVerisignOvBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
       StringHandler.nullIfZero(invoiceFormatVerisignOvBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_ZIP + "=" +
      SqlHandler.delimitString(invoiceFormatVerisignOvBean.getZip()) + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "=" +
       StringHandler.nullIfZero(invoiceFormatVerisignOvBean.getInvoiceAmount()) + "," +
     ATTRIBUTE_CREDIT_CARD_NUMBER + "=" +
      StringHandler.nullIfZero(invoiceFormatVerisignOvBean.getCreditCardNumber()) + "," +
     ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoiceFormatVerisignOvBean.getCreditCardExpirationDate()) + "," +
     ATTRIBUTE_TXREF + "=" +
      SqlHandler.delimitString(invoiceFormatVerisignOvBean.getTxref()) + "," +
     ATTRIBUTE_RESULT_CODE + "=" +
       SqlHandler.delimitString(invoiceFormatVerisignOvBean.getResultCode()) + "," +
     ATTRIBUTE_DETAIL + "=" +
      SqlHandler.delimitString(invoiceFormatVerisignOvBean.getDetail()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceFormatVerisignOvBean.getInvoice());
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

    Collection invoiceFormatVerisignOvBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceFormatVerisignOvBean invoiceFormatVerisignOvBean = new
          InvoiceFormatVerisignOvBean();
      load(dataSetRow, invoiceFormatVerisignOvBean);
      invoiceFormatVerisignOvBeanColl.add(invoiceFormatVerisignOvBean);
    }

    return invoiceFormatVerisignOvBeanColl;
  }

  public Collection selectObject(SearchCriteria criteria) throws BaseException,
      Exception {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      java.util.Map map = connection.getTypeMap();
      map.put("TCM_OPS.INVOICE_FORMAT_VERISIGN_OBJ",
              Class.forName(
          "com.tcmis.internal.invoice.beans.InvoiceFormatVerisignOvBean"));
      map.put("TCM_OPS.INVOICE_FORMAT_DETAI_OBJ",
              Class.forName(
          "com.tcmis.internal.invoice.beans.InvoiceFormatDetailViewBean"));

      c = selectObject(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectObject(SearchCriteria criteria, Connection conn) throws
      BaseException, Exception {

    Collection beanColl = new Vector();

    String query = "select value(p) from " + TABLE + " p " +
        getWhereClause(criteria);
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    Statement st = conn.createStatement();
    ResultSet rs = st.executeQuery(query);
    while (rs.next()) {
      InvoiceFormatVerisignOvBean b = (InvoiceFormatVerisignOvBean) rs.getObject(
          1);
      beanColl.add(b);
    }
    rs.close();
    st.close();
    return beanColl;
  }

}