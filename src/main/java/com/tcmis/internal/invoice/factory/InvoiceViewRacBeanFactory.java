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
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.invoice.beans.InvoiceViewRacBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewRacBeanFactory <br>
 * @version: 1.0, Mar 9, 2005 <br>
 *****************************************************************************/

public class InvoiceViewRacBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_LINE = "INVOICE_LINE";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_INVOICE_QUANTITY = "INVOICE_QUANTITY";
  public String ATTRIBUTE_ORDERED_QUANTITY = "ORDERED_QUANTITY";
  public String ATTRIBUTE_RAC_LPP = "RAC_LPP";
  public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
  public String ATTRIBUTE_UNIT_PRICE_SAVING = "UNIT_PRICE_SAVING";
  public String ATTRIBUTE_HAAS_GAIN_SHARE = "HAAS_GAIN_SHARE";
  public String ATTRIBUTE_EXTENDED_PRICE = "EXTENDED_PRICE";
  public String ATTRIBUTE_TOTAL_HAAS_GAIN_SHARE = "TOTAL_HAAS_GAIN_SHARE";
  public String ATTRIBUTE_TOTAL_AMOUNT_DUE = "TOTAL_AMOUNT_DUE";
  public String ATTRIBUTE_TOTAL_RAC_GAIN_SHARE = "TOTAL_RAC_GAIN_SHARE";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
  public String ATTRIBUTE_REQUESTOR_NAME = "REQUESTOR_NAME";
  public String ATTRIBUTE_REQUEST_NUMBER = "REQUEST_NUMBER";
  public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";
  public String ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH =
      "UNIT_OF_SALE_QUANTITY_PER_EACH";
  public String ATTRIBUTE_FEE_FOR_WEEK = "FEE_FOR_WEEK";
  public String ATTRIBUTE_TOTAL_SAVINGS = "TOTAL_SAVINGS";
  public String ATTRIBUTE_RAC_GAIN_SHARE = "RAC_GAIN_SHARE";
  public String ATTRIBUTE_HAAS_UNIT_PRICE = "HAAS_UNIT_PRICE";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";

  //table name
  public String TABLE = "INVOICE_VIEW_RAC";

  //constructor
  public InvoiceViewRacBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("invoiceQuantity")) {
      return ATTRIBUTE_INVOICE_QUANTITY;
    }
    else if (attributeName.equals("orderedQuantity")) {
      return ATTRIBUTE_ORDERED_QUANTITY;
    }
    else if (attributeName.equals("racLpp")) {
      return ATTRIBUTE_RAC_LPP;
    }
    else if (attributeName.equals("unitPrice")) {
      return ATTRIBUTE_UNIT_PRICE;
    }
    else if (attributeName.equals("unitPriceSaving")) {
      return ATTRIBUTE_UNIT_PRICE_SAVING;
    }
    else if (attributeName.equals("haasGainShare")) {
      return ATTRIBUTE_HAAS_GAIN_SHARE;
    }
    else if (attributeName.equals("extendedPrice")) {
      return ATTRIBUTE_EXTENDED_PRICE;
    }
    else if (attributeName.equals("totalHaasGainShare")) {
      return ATTRIBUTE_TOTAL_HAAS_GAIN_SHARE;
    }
    else if (attributeName.equals("totalAmountDue")) {
      return ATTRIBUTE_TOTAL_AMOUNT_DUE;
    }
    else if (attributeName.equals("totalRacGainShare")) {
      return ATTRIBUTE_TOTAL_RAC_GAIN_SHARE;
    }
    else if (attributeName.equals("partDescription")) {
      return ATTRIBUTE_PART_DESCRIPTION;
    }
    else if (attributeName.equals("requestorName")) {
      return ATTRIBUTE_REQUESTOR_NAME;
    }
    else if (attributeName.equals("requestNumber")) {
      return ATTRIBUTE_REQUEST_NUMBER;
    }
    else if (attributeName.equals("unitOfSale")) {
      return ATTRIBUTE_UNIT_OF_SALE;
    }
    else if (attributeName.equals("unitOfSaleQuantityPerEach")) {
      return ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH;
    }
    else if (attributeName.equals("feeForWeek")) {
      return ATTRIBUTE_FEE_FOR_WEEK;
    }
    else if (attributeName.equals("totalSavings")) {
      return ATTRIBUTE_TOTAL_SAVINGS;
    }
    else if (attributeName.equals("racGainShare")) {
      return ATTRIBUTE_RAC_GAIN_SHARE;
    }
    else if (attributeName.equals("haasUnitPrice")) {
      return ATTRIBUTE_HAAS_UNIT_PRICE;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceViewRacBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceViewRacBean invoiceViewRacBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewRacBean.getInvoice());
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
   public int delete(InvoiceViewRacBean invoiceViewRacBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewRacBean.getInvoice());
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
   public int insert(InvoiceViewRacBean invoiceViewRacBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceViewRacBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceViewRacBean invoiceViewRacBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_LINE + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_CAT_PART_NO + "," +
     ATTRIBUTE_INVOICE_QUANTITY + "," +
     ATTRIBUTE_ORDERED_QUANTITY + "," +
     ATTRIBUTE_RAC_LPP + "," +
     ATTRIBUTE_UNIT_PRICE + "," +
     ATTRIBUTE_UNIT_PRICE_SAVING + "," +
     ATTRIBUTE_HAAS_GAIN_SHARE + "," +
     ATTRIBUTE_EXTENDED_PRICE + "," +
     ATTRIBUTE_TOTAL_HAAS_GAIN_SHARE + "," +
     ATTRIBUTE_TOTAL_AMOUNT_DUE + "," +
     ATTRIBUTE_TOTAL_RAC_GAIN_SHARE + "," +
     ATTRIBUTE_PART_DESCRIPTION + "," +
     ATTRIBUTE_REQUESTOR_NAME + "," +
     ATTRIBUTE_REQUEST_NUMBER + "," +
     ATTRIBUTE_UNIT_OF_SALE + "," +
     ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH + "," +
     ATTRIBUTE_FEE_FOR_WEEK + "," +
     ATTRIBUTE_TOTAL_SAVINGS + "," +
     ATTRIBUTE_RAC_GAIN_SHARE + "," +
     ATTRIBUTE_HAAS_UNIT_PRICE + "," +
     ATTRIBUTE_INVOICE_DATE + "," +
     ATTRIBUTE_INVOICE_PERIOD + ")" +
   values (
     StringHandler.nullIfZero(invoiceViewRacBean.getInvoice()) + "," +
     StringHandler.nullIfZero(invoiceViewRacBean.getInvoiceLine()) + "," +
     SqlHandler.delimitString(invoiceViewRacBean.getPoNumber()) + "," +
     SqlHandler.delimitString(invoiceViewRacBean.getCatPartNo()) + "," +
     StringHandler.nullIfZero(invoiceViewRacBean.getInvoiceQuantity()) + "," +
     StringHandler.nullIfZero(invoiceViewRacBean.getOrderedQuantity()) + "," +
     StringHandler.nullIfZero(invoiceViewRacBean.getRacLpp()) + "," +
     StringHandler.nullIfZero(invoiceViewRacBean.getUnitPrice()) + "," +
     StringHandler.nullIfZero(invoiceViewRacBean.getUnitPriceSaving()) + "," +
     StringHandler.nullIfZero(invoiceViewRacBean.getHaasGainShare()) + "," +
     StringHandler.nullIfZero(invoiceViewRacBean.getExtendedPrice()) + "," +
       StringHandler.nullIfZero(invoiceViewRacBean.getTotalHaasGainShare()) + "," +
     StringHandler.nullIfZero(invoiceViewRacBean.getTotalAmountDue()) + "," +
       StringHandler.nullIfZero(invoiceViewRacBean.getTotalRacGainShare()) + "," +
     SqlHandler.delimitString(invoiceViewRacBean.getPartDescription()) + "," +
     SqlHandler.delimitString(invoiceViewRacBean.getRequestorName()) + "," +
     SqlHandler.delimitString(invoiceViewRacBean.getRequestNumber()) + "," +
     SqlHandler.delimitString(invoiceViewRacBean.getUnitOfSale()) + "," +
     StringHandler.nullIfZero(invoiceViewRacBean.getUnitOfSaleQuantityPerEach()) + "," +
     SqlHandler.delimitString(invoiceViewRacBean.getFeeForWeek()) + "," +
     StringHandler.nullIfZero(invoiceViewRacBean.getTotalSavings()) + "," +
     StringHandler.nullIfZero(invoiceViewRacBean.getRacGainShare()) + "," +
     StringHandler.nullIfZero(invoiceViewRacBean.getHaasUnitPrice()) + "," +
       DateHandler.getOracleToDateFunction(invoiceViewRacBean.getInvoiceDate()) + "," +
     StringHandler.nullIfZero(invoiceViewRacBean.getInvoicePeriod()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceViewRacBean invoiceViewRacBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceViewRacBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceViewRacBean invoiceViewRacBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewRacBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_LINE + "=" +
      StringHandler.nullIfZero(invoiceViewRacBean.getInvoiceLine()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
      SqlHandler.delimitString(invoiceViewRacBean.getPoNumber()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
      SqlHandler.delimitString(invoiceViewRacBean.getCatPartNo()) + "," +
     ATTRIBUTE_INVOICE_QUANTITY + "=" +
      StringHandler.nullIfZero(invoiceViewRacBean.getInvoiceQuantity()) + "," +
     ATTRIBUTE_ORDERED_QUANTITY + "=" +
      StringHandler.nullIfZero(invoiceViewRacBean.getOrderedQuantity()) + "," +
     ATTRIBUTE_RAC_LPP + "=" +
      StringHandler.nullIfZero(invoiceViewRacBean.getRacLpp()) + "," +
     ATTRIBUTE_UNIT_PRICE + "=" +
      StringHandler.nullIfZero(invoiceViewRacBean.getUnitPrice()) + "," +
     ATTRIBUTE_UNIT_PRICE_SAVING + "=" +
      StringHandler.nullIfZero(invoiceViewRacBean.getUnitPriceSaving()) + "," +
     ATTRIBUTE_HAAS_GAIN_SHARE + "=" +
      StringHandler.nullIfZero(invoiceViewRacBean.getHaasGainShare()) + "," +
     ATTRIBUTE_EXTENDED_PRICE + "=" +
      StringHandler.nullIfZero(invoiceViewRacBean.getExtendedPrice()) + "," +
     ATTRIBUTE_TOTAL_HAAS_GAIN_SHARE + "=" +
       StringHandler.nullIfZero(invoiceViewRacBean.getTotalHaasGainShare()) + "," +
     ATTRIBUTE_TOTAL_AMOUNT_DUE + "=" +
      StringHandler.nullIfZero(invoiceViewRacBean.getTotalAmountDue()) + "," +
     ATTRIBUTE_TOTAL_RAC_GAIN_SHARE + "=" +
       StringHandler.nullIfZero(invoiceViewRacBean.getTotalRacGainShare()) + "," +
     ATTRIBUTE_PART_DESCRIPTION + "=" +
      SqlHandler.delimitString(invoiceViewRacBean.getPartDescription()) + "," +
     ATTRIBUTE_REQUESTOR_NAME + "=" +
      SqlHandler.delimitString(invoiceViewRacBean.getRequestorName()) + "," +
     ATTRIBUTE_REQUEST_NUMBER + "=" +
      SqlHandler.delimitString(invoiceViewRacBean.getRequestNumber()) + "," +
     ATTRIBUTE_UNIT_OF_SALE + "=" +
      SqlHandler.delimitString(invoiceViewRacBean.getUnitOfSale()) + "," +
     ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH + "=" +
      StringHandler.nullIfZero(invoiceViewRacBean.getUnitOfSaleQuantityPerEach()) + "," +
     ATTRIBUTE_FEE_FOR_WEEK + "=" +
      SqlHandler.delimitString(invoiceViewRacBean.getFeeForWeek()) + "," +
     ATTRIBUTE_TOTAL_SAVINGS + "=" +
      StringHandler.nullIfZero(invoiceViewRacBean.getTotalSavings()) + "," +
     ATTRIBUTE_RAC_GAIN_SHARE + "=" +
      StringHandler.nullIfZero(invoiceViewRacBean.getRacGainShare()) + "," +
     ATTRIBUTE_HAAS_UNIT_PRICE + "=" +
      StringHandler.nullIfZero(invoiceViewRacBean.getHaasUnitPrice()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
       DateHandler.getOracleToDateFunction(invoiceViewRacBean.getInvoiceDate()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoiceViewRacBean.getInvoicePeriod()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewRacBean.getInvoice());
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

    Collection invoiceViewRacBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewRacBean invoiceViewRacBean = new InvoiceViewRacBean();
      load(dataSetRow, invoiceViewRacBean);
      invoiceViewRacBeanColl.add(invoiceViewRacBean);
    }

    return invoiceViewRacBeanColl;
  }

  public Collection selectDetail(int invoicePeriod) throws
      BaseException {
    Connection connection = null;
    Collection invoiceViewGoletaewDetailBeanColl = new Vector();

    String query = "select * from " + TABLE + " where invoice_period=" +
        invoicePeriod +
        " and invoice not in (select invoice from invoice_view_rac_corr where invoice_period=" +
        invoicePeriod + ")";
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    try {
      connection = this.getDbManager().getConnection();
      DataSet dataSet = new SqlManager().select(this.getDbManager().
                                                getConnection(), query);

      Iterator dataIter = dataSet.iterator();
      InvoiceAddChargeDetailBeanFactory factory = new
          InvoiceAddChargeDetailBeanFactory(this.getDbManager());
      while (dataIter.hasNext()) {
        DataSetRow dataSetRow = (DataSetRow) dataIter.next();
        InvoiceViewRacBean invoiceViewRacBean = new
            InvoiceViewRacBean();
        load(dataSetRow, invoiceViewRacBean);
        SearchCriteria crit = new SearchCriteria();
        crit.addCriterion("invoice", SearchCriterion.EQUALS,
                          invoiceViewRacBean.getInvoice().
                          toString());
        invoiceViewRacBean.setInvoiceAddChargeDetailBeanColl(factory.
            select(crit, connection));
        invoiceViewGoletaewDetailBeanColl.add(invoiceViewRacBean);
      }
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return invoiceViewGoletaewDetailBeanColl;
  }

  public Collection selectCorrection(int invoice, int invoicePeriod) throws
      BaseException {
    Connection connection = null;
    Collection invoiceViewGoletaewDetailBeanColl = new Vector();

    String query = "select * from " + TABLE + " where invoice_period=" +
        invoicePeriod +
        " and invoice in (select invoice from invoice_view_rac_corr where invoice_period=" +
        invoicePeriod + " and invoice=" + invoice + ")";
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    try {
      connection = this.getDbManager().getConnection();
      DataSet dataSet = new SqlManager().select(this.getDbManager().
                                                getConnection(), query);

      Iterator dataIter = dataSet.iterator();
      InvoiceAddChargeDetailBeanFactory factory = new
          InvoiceAddChargeDetailBeanFactory(this.getDbManager());
      while (dataIter.hasNext()) {
        DataSetRow dataSetRow = (DataSetRow) dataIter.next();
        InvoiceViewRacBean invoiceViewRacBean = new
            InvoiceViewRacBean();
        load(dataSetRow, invoiceViewRacBean);
        SearchCriteria crit = new SearchCriteria();
        crit.addCriterion("invoice", SearchCriterion.EQUALS,
                          invoiceViewRacBean.getInvoice().
                          toString());
        invoiceViewRacBean.setInvoiceAddChargeDetailBeanColl(factory.
            select(crit, connection));
        invoiceViewGoletaewDetailBeanColl.add(invoiceViewRacBean);
      }
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return invoiceViewGoletaewDetailBeanColl;
  }

}