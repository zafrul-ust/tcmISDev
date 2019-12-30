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
import com.tcmis.internal.invoice.beans.InvoiceViewBaeDetailBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewBaeDetailBeanFactory <br>
 * @version: 1.0, Feb 28, 2005 <br>
 *****************************************************************************/

public class InvoiceViewBaeDetailBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_LINE = "INVOICE_LINE";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_QUANTITY = "QUANTITY";
  public String ATTRIBUTE_NET_PRICE = "NET_PRICE";
  public String ATTRIBUTE_TOTAL_REBATE = "TOTAL_REBATE";
  public String ATTRIBUTE_NET_AMOUNT = "NET_AMOUNT";
  public String ATTRIBUTE_ADDITIONAL_CHARGE = "ADDITIONAL_CHARGE";
  public String ATTRIBUTE_SERVICE_FEE = "SERVICE_FEE";
  public String ATTRIBUTE_MR_NUMBER = "MR_NUMBER";
  public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
  public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
  public String ATTRIBUTE_INVOICE_UNIT_PRICE = "INVOICE_UNIT_PRICE";
  public String ATTRIBUTE_UNIT_REBATE = "UNIT_REBATE";
  public String ATTRIBUTE_NET_LINE_AMOUNT = "NET_LINE_AMOUNT";
  public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
  public String ATTRIBUTE_PERCENT_SPLIT_CHARGE = "PERCENT_SPLIT_CHARGE";

  //table name
  public String TABLE = "INVOICE_VIEW_BAE_DETAIL";

  //constructor
  public InvoiceViewBaeDetailBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
    }
    else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("quantity")) {
      return ATTRIBUTE_QUANTITY;
    }
    else if (attributeName.equals("netPrice")) {
      return ATTRIBUTE_NET_PRICE;
    }
    else if (attributeName.equals("totalRebate")) {
      return ATTRIBUTE_TOTAL_REBATE;
    }
    else if (attributeName.equals("netAmount")) {
      return ATTRIBUTE_NET_AMOUNT;
    }
    else if (attributeName.equals("additionalCharge")) {
      return ATTRIBUTE_ADDITIONAL_CHARGE;
    }
    else if (attributeName.equals("serviceFee")) {
      return ATTRIBUTE_SERVICE_FEE;
    }
    else if (attributeName.equals("mrNumber")) {
      return ATTRIBUTE_MR_NUMBER;
    }
    else if (attributeName.equals("dateDelivered")) {
      return ATTRIBUTE_DATE_DELIVERED;
    }
    else if (attributeName.equals("radianPo")) {
      return ATTRIBUTE_RADIAN_PO;
    }
    else if (attributeName.equals("invoiceUnitPrice")) {
      return ATTRIBUTE_INVOICE_UNIT_PRICE;
    }
    else if (attributeName.equals("unitRebate")) {
      return ATTRIBUTE_UNIT_REBATE;
    }
    else if (attributeName.equals("netLineAmount")) {
      return ATTRIBUTE_NET_LINE_AMOUNT;
    }
    else if (attributeName.equals("accountNumber")) {
      return ATTRIBUTE_ACCOUNT_NUMBER;
    }
    else if (attributeName.equals("percentSplitCharge")) {
      return ATTRIBUTE_PERCENT_SPLIT_CHARGE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceViewBaeDetailBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceViewBaeDetailBean invoiceViewBaeDetailBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewBaeDetailBean.getInvoice());
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
   public int delete(InvoiceViewBaeDetailBean invoiceViewBaeDetailBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewBaeDetailBean.getInvoice());
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
   public int insert(InvoiceViewBaeDetailBean invoiceViewBaeDetailBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceViewBaeDetailBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceViewBaeDetailBean invoiceViewBaeDetailBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_LINE + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_CAT_PART_NO + "," +
     ATTRIBUTE_QUANTITY + "," +
     ATTRIBUTE_NET_PRICE + "," +
     ATTRIBUTE_TOTAL_REBATE + "," +
     ATTRIBUTE_NET_AMOUNT + "," +
     ATTRIBUTE_ADDITIONAL_CHARGE + "," +
     ATTRIBUTE_SERVICE_FEE + "," +
     ATTRIBUTE_MR_NUMBER + "," +
     ATTRIBUTE_DATE_DELIVERED + "," +
     ATTRIBUTE_RADIAN_PO + "," +
     ATTRIBUTE_INVOICE_UNIT_PRICE + "," +
     ATTRIBUTE_UNIT_REBATE + "," +
     ATTRIBUTE_NET_LINE_AMOUNT + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "," +
     ATTRIBUTE_PERCENT_SPLIT_CHARGE + ")" +
   values (
     StringHandler.nullIfZero(invoiceViewBaeDetailBean.getInvoice()) + "," +
       StringHandler.nullIfZero(invoiceViewBaeDetailBean.getInvoiceLine()) + "," +
     SqlHandler.delimitString(invoiceViewBaeDetailBean.getPoNumber()) + "," +
     SqlHandler.delimitString(invoiceViewBaeDetailBean.getCatPartNo()) + "," +
     StringHandler.nullIfZero(invoiceViewBaeDetailBean.getQuantity()) + "," +
     StringHandler.nullIfZero(invoiceViewBaeDetailBean.getNetPrice()) + "," +
       StringHandler.nullIfZero(invoiceViewBaeDetailBean.getTotalRebate()) + "," +
     StringHandler.nullIfZero(invoiceViewBaeDetailBean.getNetAmount()) + "," +
       StringHandler.nullIfZero(invoiceViewBaeDetailBean.getAdditionalCharge()) + "," +
     StringHandler.nullIfZero(invoiceViewBaeDetailBean.getServiceFee()) + "," +
     SqlHandler.delimitString(invoiceViewBaeDetailBean.getMrNumber()) + "," +
     DateHandler.getOracleToDateFunction(invoiceViewBaeDetailBean.getDateDelivered()) + "," +
     StringHandler.nullIfZero(invoiceViewBaeDetailBean.getRadianPo()) + "," +
       StringHandler.nullIfZero(invoiceViewBaeDetailBean.getInvoiceUnitPrice()) + "," +
     StringHandler.nullIfZero(invoiceViewBaeDetailBean.getUnitRebate()) + "," +
       StringHandler.nullIfZero(invoiceViewBaeDetailBean.getNetLineAmount()) + "," +
       SqlHandler.delimitString(invoiceViewBaeDetailBean.getAccountNumber()) + "," +
     StringHandler.nullIfZero(invoiceViewBaeDetailBean.getPercentSplitCharge()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceViewBaeDetailBean invoiceViewBaeDetailBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceViewBaeDetailBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceViewBaeDetailBean invoiceViewBaeDetailBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewBaeDetailBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_LINE + "=" +
       StringHandler.nullIfZero(invoiceViewBaeDetailBean.getInvoiceLine()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
      SqlHandler.delimitString(invoiceViewBaeDetailBean.getPoNumber()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
      SqlHandler.delimitString(invoiceViewBaeDetailBean.getCatPartNo()) + "," +
     ATTRIBUTE_QUANTITY + "=" +
      StringHandler.nullIfZero(invoiceViewBaeDetailBean.getQuantity()) + "," +
     ATTRIBUTE_NET_PRICE + "=" +
      StringHandler.nullIfZero(invoiceViewBaeDetailBean.getNetPrice()) + "," +
     ATTRIBUTE_TOTAL_REBATE + "=" +
       StringHandler.nullIfZero(invoiceViewBaeDetailBean.getTotalRebate()) + "," +
     ATTRIBUTE_NET_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceViewBaeDetailBean.getNetAmount()) + "," +
     ATTRIBUTE_ADDITIONAL_CHARGE + "=" +
       StringHandler.nullIfZero(invoiceViewBaeDetailBean.getAdditionalCharge()) + "," +
     ATTRIBUTE_SERVICE_FEE + "=" +
       StringHandler.nullIfZero(invoiceViewBaeDetailBean.getServiceFee()) + "," +
     ATTRIBUTE_MR_NUMBER + "=" +
      SqlHandler.delimitString(invoiceViewBaeDetailBean.getMrNumber()) + "," +
     ATTRIBUTE_DATE_DELIVERED + "=" +
      DateHandler.getOracleToDateFunction(invoiceViewBaeDetailBean.getDateDelivered()) + "," +
     ATTRIBUTE_RADIAN_PO + "=" +
      StringHandler.nullIfZero(invoiceViewBaeDetailBean.getRadianPo()) + "," +
     ATTRIBUTE_INVOICE_UNIT_PRICE + "=" +
       StringHandler.nullIfZero(invoiceViewBaeDetailBean.getInvoiceUnitPrice()) + "," +
     ATTRIBUTE_UNIT_REBATE + "=" +
       StringHandler.nullIfZero(invoiceViewBaeDetailBean.getUnitRebate()) + "," +
     ATTRIBUTE_NET_LINE_AMOUNT + "=" +
       StringHandler.nullIfZero(invoiceViewBaeDetailBean.getNetLineAmount()) + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "=" +
       SqlHandler.delimitString(invoiceViewBaeDetailBean.getAccountNumber()) + "," +
     ATTRIBUTE_PERCENT_SPLIT_CHARGE + "=" +
      StringHandler.nullIfZero(invoiceViewBaeDetailBean.getPercentSplitCharge()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewBaeDetailBean.getInvoice());
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

    Collection invoiceViewBaeDetailBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewBaeDetailBean invoiceViewBaeDetailBean = new
          InvoiceViewBaeDetailBean();
      load(dataSetRow, invoiceViewBaeDetailBean);
      invoiceViewBaeDetailBeanColl.add(invoiceViewBaeDetailBean);
    }

    return invoiceViewBaeDetailBeanColl;
  }
}