package com.tcmis.client.seagate.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.seagate.beans.InvoiceFormatSeagateOvBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: InvoiceFormatSeagateOvBeanFactory <br>
 * @version: 1.0, Mar 28, 2005 <br>
 *****************************************************************************/

public class InvoiceFormatSeagateOvBeanFactory
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

  //table name
  public String TABLE = "INVOICE_FORMAT_SEAGATE_OV";

  //constructor
  public InvoiceFormatSeagateOvBeanFactory(DbManager dbManager) {
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
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceFormatSeagateOvBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceFormatSeagateOvBean invoiceFormatSeagateOvBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceFormatSeagateOvBean.getInvoice());
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
   public int delete(InvoiceFormatSeagateOvBean invoiceFormatSeagateOvBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceFormatSeagateOvBean.getInvoice());
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
   public int insert(InvoiceFormatSeagateOvBean invoiceFormatSeagateOvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceFormatSeagateOvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceFormatSeagateOvBean invoiceFormatSeagateOvBean, Connection conn)
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
     StringHandler.nullIfZero(invoiceFormatSeagateOvBean.getInvoice()) + "," +
       StringHandler.nullIfZero(invoiceFormatSeagateOvBean.getInvoicePeriod()) + "," +
     SqlHandler.delimitString(invoiceFormatSeagateOvBean.getZip()) + "," +
       StringHandler.nullIfZero(invoiceFormatSeagateOvBean.getInvoiceAmount()) + "," +
     StringHandler.nullIfZero(invoiceFormatSeagateOvBean.getCreditCardNumber()) + "," +
     DateHandler.getOracleToDateFunction(invoiceFormatSeagateOvBean.getCreditCardExpirationDate()) + "," +
     SqlHandler.delimitString(invoiceFormatSeagateOvBean.getTxref()) + "," +
       SqlHandler.delimitString(invoiceFormatSeagateOvBean.getResultCode()) + "," +
     SqlHandler.delimitString(invoiceFormatSeagateOvBean.getDetail()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceFormatSeagateOvBean invoiceFormatSeagateOvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceFormatSeagateOvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceFormatSeagateOvBean invoiceFormatSeagateOvBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceFormatSeagateOvBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
       StringHandler.nullIfZero(invoiceFormatSeagateOvBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_ZIP + "=" +
      SqlHandler.delimitString(invoiceFormatSeagateOvBean.getZip()) + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "=" +
       StringHandler.nullIfZero(invoiceFormatSeagateOvBean.getInvoiceAmount()) + "," +
     ATTRIBUTE_CREDIT_CARD_NUMBER + "=" +
      StringHandler.nullIfZero(invoiceFormatSeagateOvBean.getCreditCardNumber()) + "," +
     ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoiceFormatSeagateOvBean.getCreditCardExpirationDate()) + "," +
     ATTRIBUTE_TXREF + "=" +
      SqlHandler.delimitString(invoiceFormatSeagateOvBean.getTxref()) + "," +
     ATTRIBUTE_RESULT_CODE + "=" +
       SqlHandler.delimitString(invoiceFormatSeagateOvBean.getResultCode()) + "," +
     ATTRIBUTE_DETAIL + "=" +
      SqlHandler.delimitString(invoiceFormatSeagateOvBean.getDetail()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceFormatSeagateOvBean.getInvoice());
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

    Collection invoiceFormatSeagateOvBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceFormatSeagateOvBean invoiceFormatSeagateOvBean = new
          InvoiceFormatSeagateOvBean();
      load(dataSetRow, invoiceFormatSeagateOvBean);
      invoiceFormatSeagateOvBeanColl.add(invoiceFormatSeagateOvBean);
    }

    return invoiceFormatSeagateOvBeanColl;
  }

  public Collection selectObject(SearchCriteria criteria) throws BaseException,
      Exception {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      java.util.Map map = connection.getTypeMap();
      map.put("INVOICE_FORMAT_SEAGATE_OBJ",
              Class.forName(
          "com.tcmis.client.seagate.beans.InvoiceFormatSeagateOvBean"));
      map.put("INVOICE_FORMAT_DETAI_OBJ",
              Class.forName(
          "com.tcmis.client.seagate.beans.InvoiceFormatDetailViewBean"));

      c = selectObject(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectObject(SearchCriteria criteria, Connection conn) throws
      BaseException, Exception {

    Collection seagateBeanColl = new Vector();

    String query = "select value(p) from " + TABLE + " p " +
        getWhereClause(criteria);
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    Statement st = conn.createStatement();
    ResultSet rs = st.executeQuery(query);
    while (rs.next()) {
      InvoiceFormatSeagateOvBean b = (InvoiceFormatSeagateOvBean) rs.getObject(
          1);
      seagateBeanColl.add(b);
    }
    rs.close();
    st.close();
    return seagateBeanColl;
  }

}