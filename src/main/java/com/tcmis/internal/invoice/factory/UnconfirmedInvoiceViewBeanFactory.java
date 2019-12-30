package com.tcmis.internal.invoice.factory;

import java.sql.Connection;
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
import com.tcmis.internal.invoice.beans.UnconfirmedInvoiceViewBean;

/******************************************************************************
 * CLASSNAME: UnconfirmedInvoiceViewBeanFactory <br>
 * @version: 1.0, Dec 7, 2005 <br>
 *****************************************************************************/

public class UnconfirmedInvoiceViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_ZIP = "ZIP";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_OPEN_AMOUNT = "OPEN_AMOUNT";
  public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
  public String ATTRIBUTE_CREDIT_CARD_NUMBER = "CREDIT_CARD_NUMBER";
  public String ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE = "CREDIT_CARD_EXPIRATION_DATE";
  public String ATTRIBUTE_TXREF = "TXREF";
  public String ATTRIBUTE_RESULT_CODE = "RESULT_CODE";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_INVOICE_GROUP = "INVOICE_GROUP";

  //table name
  public String TABLE = "UNCONFIRMED_INVOICE_VIEW";

  //constructor
  public UnconfirmedInvoiceViewBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
    }
    else if (attributeName.equals("openAmount")) {
      return ATTRIBUTE_OPEN_AMOUNT;
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
    return super.getType(attributeName, UnconfirmedInvoiceViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(UnconfirmedInvoiceViewBean unconfirmedInvoiceViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + unconfirmedInvoiceViewBean.getInvoice());
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
       public int delete(UnconfirmedInvoiceViewBean unconfirmedInvoiceViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + unconfirmedInvoiceViewBean.getInvoice());
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

//you need to verify the primary key(s) before uncommenting this
  /*
//insert
   public int insert(UnconfirmedInvoiceViewBean unconfirmedInvoiceViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(unconfirmedInvoiceViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int insert(UnconfirmedInvoiceViewBean unconfirmedInvoiceViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_ZIP + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_OPEN_AMOUNT + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "," +
     ATTRIBUTE_CREDIT_CARD_NUMBER + "," +
     ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE + "," +
     ATTRIBUTE_TXREF + "," +
     ATTRIBUTE_RESULT_CODE + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_INVOICE_GROUP + ")" +
     " values (" +
     unconfirmedInvoiceViewBean.getInvoice() + "," +
     unconfirmedInvoiceViewBean.getInvoicePeriod() + "," +
     SqlHandler.delimitString(unconfirmedInvoiceViewBean.getZip()) + "," +
     SqlHandler.delimitString(unconfirmedInvoiceViewBean.getPoNumber()) + "," +
     unconfirmedInvoiceViewBean.getOpenAmount() + "," +
     unconfirmedInvoiceViewBean.getInvoiceAmount() + "," +
     unconfirmedInvoiceViewBean.getCreditCardNumber() + "," +
     DateHandler.getOracleToDateFunction(unconfirmedInvoiceViewBean.getCreditCardExpirationDate()) + "," +
     SqlHandler.delimitString(unconfirmedInvoiceViewBean.getTxref()) + "," +
     SqlHandler.delimitString(unconfirmedInvoiceViewBean.getResultCode()) + "," +
     SqlHandler.delimitString(unconfirmedInvoiceViewBean.getCompanyId()) + "," +
     SqlHandler.delimitString(unconfirmedInvoiceViewBean.getInvoiceGroup()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(UnconfirmedInvoiceViewBean unconfirmedInvoiceViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(unconfirmedInvoiceViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int update(UnconfirmedInvoiceViewBean unconfirmedInvoiceViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(unconfirmedInvoiceViewBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(unconfirmedInvoiceViewBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_ZIP + "=" +
      SqlHandler.delimitString(unconfirmedInvoiceViewBean.getZip()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
      SqlHandler.delimitString(unconfirmedInvoiceViewBean.getPoNumber()) + "," +
     ATTRIBUTE_OPEN_AMOUNT + "=" +
      StringHandler.nullIfZero(unconfirmedInvoiceViewBean.getOpenAmount()) + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "=" +
      StringHandler.nullIfZero(unconfirmedInvoiceViewBean.getInvoiceAmount()) + "," +
     ATTRIBUTE_CREDIT_CARD_NUMBER + "=" +
      StringHandler.nullIfZero(unconfirmedInvoiceViewBean.getCreditCardNumber()) + "," +
     ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE + "=" +
      DateHandler.getOracleToDateFunction(unconfirmedInvoiceViewBean.getCreditCardExpirationDate()) + "," +
     ATTRIBUTE_TXREF + "=" +
      SqlHandler.delimitString(unconfirmedInvoiceViewBean.getTxref()) + "," +
     ATTRIBUTE_RESULT_CODE + "=" +
      SqlHandler.delimitString(unconfirmedInvoiceViewBean.getResultCode()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(unconfirmedInvoiceViewBean.getCompanyId()) + "," +
     ATTRIBUTE_INVOICE_GROUP + "=" +
      SqlHandler.delimitString(unconfirmedInvoiceViewBean.getInvoiceGroup()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      unconfirmedInvoiceViewBean.getInvoice();
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
    Collection unconfirmedInvoiceViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      UnconfirmedInvoiceViewBean unconfirmedInvoiceViewBean = new
          UnconfirmedInvoiceViewBean();
      load(dataSetRow, unconfirmedInvoiceViewBean);
      unconfirmedInvoiceViewBeanColl.add(unconfirmedInvoiceViewBean);
    }
    return unconfirmedInvoiceViewBeanColl;
  }
}