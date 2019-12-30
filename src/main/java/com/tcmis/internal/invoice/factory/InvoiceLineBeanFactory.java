package com.tcmis.internal.invoice.factory;
import java.math.BigDecimal;
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
import com.tcmis.internal.invoice.beans.InvoiceLineBean;

/******************************************************************************
 * CLASSNAME: InvoiceLineBeanFactory <br>
 * @version: 1.0, Mar 9, 2005 <br>
 *****************************************************************************/

public class InvoiceLineBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_LINE = "INVOICE_LINE";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
  public String ATTRIBUTE_ACCOUNT_NUMBER2 = "ACCOUNT_NUMBER2";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_EXTENDED_PRICE = "EXTENDED_PRICE";
  public String ATTRIBUTE_REBATE = "REBATE";
  public String ATTRIBUTE_SERVICE_FEE = "SERVICE_FEE";
  public String ATTRIBUTE_TOTAL_AMOUNT = "TOTAL_AMOUNT";
  public String ATTRIBUTE_ADDITIONAL_CHARGES = "ADDITIONAL_CHARGES";
  public String ATTRIBUTE_CHARGE_TYPE = "CHARGE_TYPE";
  public String ATTRIBUTE_SALES_TAX = "SALES_TAX";
  public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";

  //table name
  public String TABLE = "INVOICE_LINE";

  //constructor
  public InvoiceLineBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("invoiceLine")) {
      return ATTRIBUTE_INVOICE_LINE;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("accountNumber")) {
      return ATTRIBUTE_ACCOUNT_NUMBER;
    }
    else if (attributeName.equals("accountNumber2")) {
      return ATTRIBUTE_ACCOUNT_NUMBER2;
    }
    else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
    }
    else if (attributeName.equals("extendedPrice")) {
      return ATTRIBUTE_EXTENDED_PRICE;
    }
    else if (attributeName.equals("rebate")) {
      return ATTRIBUTE_REBATE;
    }
    else if (attributeName.equals("serviceFee")) {
      return ATTRIBUTE_SERVICE_FEE;
    }
    else if (attributeName.equals("totalAmount")) {
      return ATTRIBUTE_TOTAL_AMOUNT;
    }
    else if (attributeName.equals("additionalCharges")) {
      return ATTRIBUTE_ADDITIONAL_CHARGES;
    }
    else if (attributeName.equals("chargeType")) {
      return ATTRIBUTE_CHARGE_TYPE;
    }
    else if (attributeName.equals("salesTax")) {
      return ATTRIBUTE_SALES_TAX;
    }
    else if (attributeName.equals("currencyId")) {
      return ATTRIBUTE_CURRENCY_ID;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceLineBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceLineBean invoiceLineBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceLineBean.getInvoice());
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
   public int delete(InvoiceLineBean invoiceLineBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceLineBean.getInvoice());
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
   public int insert(InvoiceLineBean invoiceLineBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceLineBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceLineBean invoiceLineBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_LINE + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "," +
     ATTRIBUTE_ACCOUNT_NUMBER2 + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_EXTENDED_PRICE + "," +
     ATTRIBUTE_REBATE + "," +
     ATTRIBUTE_SERVICE_FEE + "," +
     ATTRIBUTE_TOTAL_AMOUNT + "," +
     ATTRIBUTE_ADDITIONAL_CHARGES + "," +
     ATTRIBUTE_CHARGE_TYPE + "," +
     ATTRIBUTE_SALES_TAX + "," +
     ATTRIBUTE_CURRENCY_ID + ")" +
   values (
     StringHandler.nullIfZero(invoiceLineBean.getInvoice()) + "," +
     StringHandler.nullIfZero(invoiceLineBean.getInvoiceLine()) + "," +
     SqlHandler.delimitString(invoiceLineBean.getCompanyId()) + "," +
     SqlHandler.delimitString(invoiceLineBean.getAccountNumber()) + "," +
     SqlHandler.delimitString(invoiceLineBean.getAccountNumber2()) + "," +
     SqlHandler.delimitString(invoiceLineBean.getPoNumber()) + "," +
     StringHandler.nullIfZero(invoiceLineBean.getExtendedPrice()) + "," +
     StringHandler.nullIfZero(invoiceLineBean.getRebate()) + "," +
     StringHandler.nullIfZero(invoiceLineBean.getServiceFee()) + "," +
     StringHandler.nullIfZero(invoiceLineBean.getTotalAmount()) + "," +
     StringHandler.nullIfZero(invoiceLineBean.getAdditionalCharges()) + "," +
     SqlHandler.delimitString(invoiceLineBean.getChargeType()) + "," +
     StringHandler.nullIfZero(invoiceLineBean.getSalesTax()) + "," +
     SqlHandler.delimitString(invoiceLineBean.getCurrencyId()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceLineBean invoiceLineBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceLineBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceLineBean invoiceLineBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceLineBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_LINE + "=" +
      StringHandler.nullIfZero(invoiceLineBean.getInvoiceLine()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(invoiceLineBean.getCompanyId()) + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "=" +
      SqlHandler.delimitString(invoiceLineBean.getAccountNumber()) + "," +
     ATTRIBUTE_ACCOUNT_NUMBER2 + "=" +
      SqlHandler.delimitString(invoiceLineBean.getAccountNumber2()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
      SqlHandler.delimitString(invoiceLineBean.getPoNumber()) + "," +
     ATTRIBUTE_EXTENDED_PRICE + "=" +
      StringHandler.nullIfZero(invoiceLineBean.getExtendedPrice()) + "," +
     ATTRIBUTE_REBATE + "=" +
      StringHandler.nullIfZero(invoiceLineBean.getRebate()) + "," +
     ATTRIBUTE_SERVICE_FEE + "=" +
      StringHandler.nullIfZero(invoiceLineBean.getServiceFee()) + "," +
     ATTRIBUTE_TOTAL_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceLineBean.getTotalAmount()) + "," +
     ATTRIBUTE_ADDITIONAL_CHARGES + "=" +
      StringHandler.nullIfZero(invoiceLineBean.getAdditionalCharges()) + "," +
     ATTRIBUTE_CHARGE_TYPE + "=" +
      SqlHandler.delimitString(invoiceLineBean.getChargeType()) + "," +
     ATTRIBUTE_SALES_TAX + "=" +
      StringHandler.nullIfZero(invoiceLineBean.getSalesTax()) + "," +
     ATTRIBUTE_CURRENCY_ID + "=" +
      SqlHandler.delimitString(invoiceLineBean.getCurrencyId()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceLineBean.getInvoice());
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

    Collection invoiceLineBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceLineBean invoiceLineBean = new InvoiceLineBean();
      load(dataSetRow, invoiceLineBean);
      invoiceLineBeanColl.add(invoiceLineBean);
    }

    return invoiceLineBeanColl;
  }

  public Collection selectFss(BigDecimal invoice) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectFss(invoice, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectFss(BigDecimal invoice, Connection conn) throws
      BaseException {

    Collection invoiceLineBeanColl = new Vector();

    String query = "select to_char(INVOICE_LINE,'0000') invoice_line,ACCOUNT_NUMBER, " +
                   "ACCOUNT_NUMBER2,decode(sign(total_amount),-1, " +
                   "to_char(TOTAL_AMOUNT,'00000000000.00'), " +
                   "to_char(total_amount,'000000000000.00')) total_amount " +
                   "from invoice_line where invoice = " + invoice + " " +
                   "order by to_char(invoice_line,'0000')";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceLineBean invoiceLineBean = new InvoiceLineBean();
      load(dataSetRow, invoiceLineBean);
      invoiceLineBeanColl.add(invoiceLineBean);
    }

    return invoiceLineBeanColl;
  }
}