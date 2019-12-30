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
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.invoice.beans.InvoiceViewGoletaewDetailBean;
import com.tcmis.internal.invoice.factory.InvoiceAddChargeDetailBeanFactory;

/******************************************************************************
 * CLASSNAME: InvoiceViewGoletaewDetailBeanFactory <br>
 * @version: 1.0, Mar 9, 2005 <br>
 *****************************************************************************/

public class InvoiceViewGoletaewDetailBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_LINE = "INVOICE_LINE";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_INVOICE_QUANTITY = "INVOICE_QUANTITY";
  public String ATTRIBUTE_REQUESTED_QUANTITY = "REQUESTED_QUANTITY";
  public String ATTRIBUTE_INVOICE_UNIT_PRICE = "INVOICE_UNIT_PRICE";
  public String ATTRIBUTE_UNIT_REBATE = "UNIT_REBATE";
  public String ATTRIBUTE_EXTENDED_PRICE = "EXTENDED_PRICE";
  public String ATTRIBUTE_REBATE = "REBATE";
  public String ATTRIBUTE_NET_AMOUNT = "NET_AMOUNT";
  public String ATTRIBUTE_TOTAL_ADD_CHARGE = "TOTAL_ADD_CHARGE";
  public String ATTRIBUTE_SERVICE_FEE = "SERVICE_FEE";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
  public String ATTRIBUTE_REQUESTOR_NAME = "REQUESTOR_NAME";
  public String ATTRIBUTE_REQUEST_NUMBER = "REQUEST_NUMBER";
  public String ATTRIBUTE_NET_EXT_PRICE = "NET_EXT_PRICE";
  public String ATTRIBUTE_FINAL_EXT_PRICE = "FINAL_EXT_PRICE";
  public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
  public String ATTRIBUTE_MFG_DESC = "MFG_DESC";
  public String ATTRIBUTE_SERVICE_FEE_PERCENT = "SERVICE_FEE_PERCENT";
  public String ATTRIBUTE_INVOICE_SUPPLIER = "INVOICE_SUPPLIER";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";

  //table name
  public String TABLE = "INVOICE_VIEW_GOLETAEW_DETAIL";

  //constructor
  public InvoiceViewGoletaewDetailBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("invoiceQuantity")) {
      return ATTRIBUTE_INVOICE_QUANTITY;
    }
    else if (attributeName.equals("requestedQuantity")) {
      return ATTRIBUTE_REQUESTED_QUANTITY;
    }
    else if (attributeName.equals("invoiceUnitPrice")) {
      return ATTRIBUTE_INVOICE_UNIT_PRICE;
    }
    else if (attributeName.equals("unitRebate")) {
      return ATTRIBUTE_UNIT_REBATE;
    }
    else if (attributeName.equals("extendedPrice")) {
      return ATTRIBUTE_EXTENDED_PRICE;
    }
    else if (attributeName.equals("rebate")) {
      return ATTRIBUTE_REBATE;
    }
    else if (attributeName.equals("netAmount")) {
      return ATTRIBUTE_NET_AMOUNT;
    }
    else if (attributeName.equals("totalAddCharge")) {
      return ATTRIBUTE_TOTAL_ADD_CHARGE;
    }
    else if (attributeName.equals("serviceFee")) {
      return ATTRIBUTE_SERVICE_FEE;
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
    else if (attributeName.equals("netExtPrice")) {
      return ATTRIBUTE_NET_EXT_PRICE;
    }
    else if (attributeName.equals("finalExtPrice")) {
      return ATTRIBUTE_FINAL_EXT_PRICE;
    }
    else if (attributeName.equals("invoiceAmount")) {
      return ATTRIBUTE_INVOICE_AMOUNT;
    }
    else if (attributeName.equals("mfgDesc")) {
      return ATTRIBUTE_MFG_DESC;
    }
    else if (attributeName.equals("serviceFeePercent")) {
      return ATTRIBUTE_SERVICE_FEE_PERCENT;
    }
    else if (attributeName.equals("invoiceSupplier")) {
      return ATTRIBUTE_INVOICE_SUPPLIER;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceViewGoletaewDetailBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
       public int delete(InvoiceViewGoletaewDetailBean invoiceViewGoletaewDetailBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewGoletaewDetailBean.getInvoice());
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
   public int delete(InvoiceViewGoletaewDetailBean invoiceViewGoletaewDetailBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewGoletaewDetailBean.getInvoice());
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
       public int insert(InvoiceViewGoletaewDetailBean invoiceViewGoletaewDetailBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceViewGoletaewDetailBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceViewGoletaewDetailBean invoiceViewGoletaewDetailBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_LINE + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_CAT_PART_NO + "," +
     ATTRIBUTE_INVOICE_QUANTITY + "," +
     ATTRIBUTE_REQUESTED_QUANTITY + "," +
     ATTRIBUTE_INVOICE_UNIT_PRICE + "," +
     ATTRIBUTE_UNIT_REBATE + "," +
     ATTRIBUTE_EXTENDED_PRICE + "," +
     ATTRIBUTE_REBATE + "," +
     ATTRIBUTE_NET_AMOUNT + "," +
     ATTRIBUTE_TOTAL_ADD_CHARGE + "," +
     ATTRIBUTE_SERVICE_FEE + "," +
     ATTRIBUTE_PART_DESCRIPTION + "," +
     ATTRIBUTE_REQUESTOR_NAME + "," +
     ATTRIBUTE_REQUEST_NUMBER + "," +
     ATTRIBUTE_NET_EXT_PRICE + "," +
     ATTRIBUTE_FINAL_EXT_PRICE + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "," +
     ATTRIBUTE_MFG_DESC + "," +
     ATTRIBUTE_SERVICE_FEE_PERCENT + "," +
     ATTRIBUTE_INVOICE_SUPPLIER + "," +
     ATTRIBUTE_INVOICE_DATE + ")" +
   values (
       StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getInvoice()) + "," +
       StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getInvoiceLine()) + "," +
       SqlHandler.delimitString(invoiceViewGoletaewDetailBean.getPoNumber()) + "," +
     StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getInvoicePeriod()) + "," +
       SqlHandler.delimitString(invoiceViewGoletaewDetailBean.getCatPartNo()) + "," +
     StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getInvoiceQuantity()) + "," +
     StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getRequestedQuantity()) + "," +
     StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getInvoiceUnitPrice()) + "," +
       StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getUnitRebate()) + "," +
     StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getExtendedPrice()) + "," +
       StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getRebate()) + "," +
       StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getNetAmount()) + "," +
     StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getTotalAddCharge()) + "," +
       StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getServiceFee()) + "," +
     SqlHandler.delimitString(invoiceViewGoletaewDetailBean.getPartDescription()) + "," +
     SqlHandler.delimitString(invoiceViewGoletaewDetailBean.getRequestorName()) + "," +
     SqlHandler.delimitString(invoiceViewGoletaewDetailBean.getRequestNumber()) + "," +
       StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getNetExtPrice()) + "," +
     StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getFinalExtPrice()) + "," +
     StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getInvoiceAmount()) + "," +
       SqlHandler.delimitString(invoiceViewGoletaewDetailBean.getMfgDesc()) + "," +
     StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getServiceFeePercent()) + "," +
     SqlHandler.delimitString(invoiceViewGoletaewDetailBean.getInvoiceSupplier()) + "," +
     DateHandler.getOracleToDateFunction(invoiceViewGoletaewDetailBean.getInvoiceDate()) + ")";
    return sqlManager.update(conn, query);
   }
//update
       public int update(InvoiceViewGoletaewDetailBean invoiceViewGoletaewDetailBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceViewGoletaewDetailBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceViewGoletaewDetailBean invoiceViewGoletaewDetailBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
       StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_LINE + "=" +
       StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getInvoiceLine()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
       SqlHandler.delimitString(invoiceViewGoletaewDetailBean.getPoNumber()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
       SqlHandler.delimitString(invoiceViewGoletaewDetailBean.getCatPartNo()) + "," +
     ATTRIBUTE_INVOICE_QUANTITY + "=" +
      StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getInvoiceQuantity()) + "," +
     ATTRIBUTE_REQUESTED_QUANTITY + "=" +
      StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getRequestedQuantity()) + "," +
     ATTRIBUTE_INVOICE_UNIT_PRICE + "=" +
      StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getInvoiceUnitPrice()) + "," +
     ATTRIBUTE_UNIT_REBATE + "=" +
       StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getUnitRebate()) + "," +
     ATTRIBUTE_EXTENDED_PRICE + "=" +
      StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getExtendedPrice()) + "," +
     ATTRIBUTE_REBATE + "=" +
       StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getRebate()) + "," +
     ATTRIBUTE_NET_AMOUNT + "=" +
       StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getNetAmount()) + "," +
     ATTRIBUTE_TOTAL_ADD_CHARGE + "=" +
      StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getTotalAddCharge()) + "," +
     ATTRIBUTE_SERVICE_FEE + "=" +
       StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getServiceFee()) + "," +
     ATTRIBUTE_PART_DESCRIPTION + "=" +
      SqlHandler.delimitString(invoiceViewGoletaewDetailBean.getPartDescription()) + "," +
     ATTRIBUTE_REQUESTOR_NAME + "=" +
      SqlHandler.delimitString(invoiceViewGoletaewDetailBean.getRequestorName()) + "," +
     ATTRIBUTE_REQUEST_NUMBER + "=" +
      SqlHandler.delimitString(invoiceViewGoletaewDetailBean.getRequestNumber()) + "," +
     ATTRIBUTE_NET_EXT_PRICE + "=" +
       StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getNetExtPrice()) + "," +
     ATTRIBUTE_FINAL_EXT_PRICE + "=" +
      StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getFinalExtPrice()) + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getInvoiceAmount()) + "," +
     ATTRIBUTE_MFG_DESC + "=" +
       SqlHandler.delimitString(invoiceViewGoletaewDetailBean.getMfgDesc()) + "," +
     ATTRIBUTE_SERVICE_FEE_PERCENT + "=" +
      StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getServiceFeePercent()) + "," +
     ATTRIBUTE_INVOICE_SUPPLIER + "=" +
      SqlHandler.delimitString(invoiceViewGoletaewDetailBean.getInvoiceSupplier()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoiceViewGoletaewDetailBean.getInvoiceDate()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewGoletaewDetailBean.getInvoice());
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

    Collection invoiceViewGoletaewDetailBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + " order by invoice";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();
    InvoiceAddChargeDetailBeanFactory factory = new InvoiceAddChargeDetailBeanFactory(this.getDbManager());
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewGoletaewDetailBean invoiceViewGoletaewDetailBean = new
          InvoiceViewGoletaewDetailBean();
      load(dataSetRow, invoiceViewGoletaewDetailBean);
      SearchCriteria detailCriteria = new SearchCriteria();
      detailCriteria.addCriterion("invoice",
                                  SearchCriterion.EQUALS,
                                  "" + invoiceViewGoletaewDetailBean.getInvoice());
      detailCriteria.addCriterion("invoiceLine",
                                  SearchCriterion.EQUALS,
                                  "" + invoiceViewGoletaewDetailBean.getInvoiceLine());
      invoiceViewGoletaewDetailBean.setInvoiceAddChargeDetailBeanColl(factory.select(detailCriteria));
      invoiceViewGoletaewDetailBeanColl.add(invoiceViewGoletaewDetailBean);
    }

    return invoiceViewGoletaewDetailBeanColl;
  }

  public Collection selectNonCorrectionInvoices(BigDecimal invoicePeriod) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectNonCorrectionInvoices(invoicePeriod, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectNonCorrectionInvoices(BigDecimal invoicePeriod, Connection conn) throws
      BaseException {

    Collection invoiceViewGoletaewDetailBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
                   "where invoice_period = " + invoicePeriod + " " +
                   "and invoice not in " +
                     "(select invoice from tcm_ops.INVOICE_VIEW_GOLETAEW " + 
                     "where INVOICE_PERIOD = " + invoicePeriod + ") " +
                   "order by invoice";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();
    InvoiceAddChargeDetailBeanFactory factory = new InvoiceAddChargeDetailBeanFactory(this.getDbManager());
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewGoletaewDetailBean invoiceViewGoletaewDetailBean = new
          InvoiceViewGoletaewDetailBean();
      load(dataSetRow, invoiceViewGoletaewDetailBean);
      SearchCriteria detailCriteria = new SearchCriteria();
      detailCriteria.addCriterion("invoice",
                                  SearchCriterion.EQUALS,
                                  "" + invoiceViewGoletaewDetailBean.getInvoice());
      detailCriteria.addCriterion("invoiceLine",
                                  SearchCriterion.EQUALS,
                                  "" + invoiceViewGoletaewDetailBean.getInvoiceLine());
      invoiceViewGoletaewDetailBean.setInvoiceAddChargeDetailBeanColl(factory.selectEw(detailCriteria));
      invoiceViewGoletaewDetailBeanColl.add(invoiceViewGoletaewDetailBean);
    }

    return invoiceViewGoletaewDetailBeanColl;
  }
}