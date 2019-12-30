package com.tcmis.internal.invoice.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;
import java.math.BigDecimal;
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
import com.tcmis.internal.invoice.beans.InvoiceViewPrismDetailBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewPrismDetailBeanFactory <br>
 * @version: 1.0, May 16, 2007 <br>
 *****************************************************************************/

public class InvoiceViewPrismDetailBeanFactory
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
  public String TABLE = "INVOICE_VIEW_PRISM_DETAIL";

  //constructor
  public InvoiceViewPrismDetailBeanFactory(DbManager dbManager) {
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
    return super.getType(attributeName, InvoiceViewPrismDetailBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceViewPrismDetailBean invoiceViewPrismDetailBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewPrismDetailBean.getInvoice());
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
   public int delete(InvoiceViewPrismDetailBean invoiceViewPrismDetailBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewPrismDetailBean.getInvoice());
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
   public int insert(InvoiceViewPrismDetailBean invoiceViewPrismDetailBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceViewPrismDetailBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceViewPrismDetailBean invoiceViewPrismDetailBean, Connection conn)
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
     " values (" +
     invoiceViewPrismDetailBean.getInvoice() + "," +
     invoiceViewPrismDetailBean.getInvoiceLine() + "," +
     SqlHandler.delimitString(invoiceViewPrismDetailBean.getPoNumber()) + "," +
     invoiceViewPrismDetailBean.getInvoicePeriod() + "," +
       SqlHandler.delimitString(invoiceViewPrismDetailBean.getCatPartNo()) + "," +
     invoiceViewPrismDetailBean.getInvoiceQuantity() + "," +
     invoiceViewPrismDetailBean.getRequestedQuantity() + "," +
     invoiceViewPrismDetailBean.getInvoiceUnitPrice() + "," +
     invoiceViewPrismDetailBean.getUnitRebate() + "," +
     invoiceViewPrismDetailBean.getExtendedPrice() + "," +
     invoiceViewPrismDetailBean.getRebate() + "," +
     invoiceViewPrismDetailBean.getNetAmount() + "," +
     invoiceViewPrismDetailBean.getTotalAddCharge() + "," +
     invoiceViewPrismDetailBean.getServiceFee() + "," +
     SqlHandler.delimitString(invoiceViewPrismDetailBean.getPartDescription()) + "," +
       SqlHandler.delimitString(invoiceViewPrismDetailBean.getRequestorName()) + "," +
       SqlHandler.delimitString(invoiceViewPrismDetailBean.getRequestNumber()) + "," +
     invoiceViewPrismDetailBean.getNetExtPrice() + "," +
     invoiceViewPrismDetailBean.getFinalExtPrice() + "," +
     invoiceViewPrismDetailBean.getInvoiceAmount() + "," +
     SqlHandler.delimitString(invoiceViewPrismDetailBean.getMfgDesc()) + "," +
     invoiceViewPrismDetailBean.getServiceFeePercent() + "," +
     SqlHandler.delimitString(invoiceViewPrismDetailBean.getInvoiceSupplier()) + "," +
     DateHandler.getOracleToDateFunction(invoiceViewPrismDetailBean.getInvoiceDate()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceViewPrismDetailBean invoiceViewPrismDetailBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceViewPrismDetailBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceViewPrismDetailBean invoiceViewPrismDetailBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewPrismDetailBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_LINE + "=" +
       StringHandler.nullIfZero(invoiceViewPrismDetailBean.getInvoiceLine()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
       SqlHandler.delimitString(invoiceViewPrismDetailBean.getPoNumber()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
       StringHandler.nullIfZero(invoiceViewPrismDetailBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
       SqlHandler.delimitString(invoiceViewPrismDetailBean.getCatPartNo()) + "," +
     ATTRIBUTE_INVOICE_QUANTITY + "=" +
      StringHandler.nullIfZero(invoiceViewPrismDetailBean.getInvoiceQuantity()) + "," +
     ATTRIBUTE_REQUESTED_QUANTITY + "=" +
      StringHandler.nullIfZero(invoiceViewPrismDetailBean.getRequestedQuantity()) + "," +
     ATTRIBUTE_INVOICE_UNIT_PRICE + "=" +
      StringHandler.nullIfZero(invoiceViewPrismDetailBean.getInvoiceUnitPrice()) + "," +
     ATTRIBUTE_UNIT_REBATE + "=" +
       StringHandler.nullIfZero(invoiceViewPrismDetailBean.getUnitRebate()) + "," +
     ATTRIBUTE_EXTENDED_PRICE + "=" +
       StringHandler.nullIfZero(invoiceViewPrismDetailBean.getExtendedPrice()) + "," +
     ATTRIBUTE_REBATE + "=" +
      StringHandler.nullIfZero(invoiceViewPrismDetailBean.getRebate()) + "," +
     ATTRIBUTE_NET_AMOUNT + "=" +
       StringHandler.nullIfZero(invoiceViewPrismDetailBean.getNetAmount()) + "," +
     ATTRIBUTE_TOTAL_ADD_CHARGE + "=" +
       StringHandler.nullIfZero(invoiceViewPrismDetailBean.getTotalAddCharge()) + "," +
     ATTRIBUTE_SERVICE_FEE + "=" +
       StringHandler.nullIfZero(invoiceViewPrismDetailBean.getServiceFee()) + "," +
     ATTRIBUTE_PART_DESCRIPTION + "=" +
      SqlHandler.delimitString(invoiceViewPrismDetailBean.getPartDescription()) + "," +
     ATTRIBUTE_REQUESTOR_NAME + "=" +
       SqlHandler.delimitString(invoiceViewPrismDetailBean.getRequestorName()) + "," +
     ATTRIBUTE_REQUEST_NUMBER + "=" +
       SqlHandler.delimitString(invoiceViewPrismDetailBean.getRequestNumber()) + "," +
     ATTRIBUTE_NET_EXT_PRICE + "=" +
       StringHandler.nullIfZero(invoiceViewPrismDetailBean.getNetExtPrice()) + "," +
     ATTRIBUTE_FINAL_EXT_PRICE + "=" +
       StringHandler.nullIfZero(invoiceViewPrismDetailBean.getFinalExtPrice()) + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "=" +
       StringHandler.nullIfZero(invoiceViewPrismDetailBean.getInvoiceAmount()) + "," +
     ATTRIBUTE_MFG_DESC + "=" +
      SqlHandler.delimitString(invoiceViewPrismDetailBean.getMfgDesc()) + "," +
     ATTRIBUTE_SERVICE_FEE_PERCENT + "=" +
      StringHandler.nullIfZero(invoiceViewPrismDetailBean.getServiceFeePercent()) + "," +
     ATTRIBUTE_INVOICE_SUPPLIER + "=" +
      SqlHandler.delimitString(invoiceViewPrismDetailBean.getInvoiceSupplier()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoiceViewPrismDetailBean.getInvoiceDate()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      invoiceViewPrismDetailBean.getInvoice();
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
    Collection invoiceViewPrismDetailBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewPrismDetailBean invoiceViewPrismDetailBean = new
          InvoiceViewPrismDetailBean();
      load(dataSetRow, invoiceViewPrismDetailBean);
      invoiceViewPrismDetailBeanColl.add(invoiceViewPrismDetailBean);
    }
    return invoiceViewPrismDetailBeanColl;
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
      InvoiceViewPrismDetailBean invoiceViewPrismDetailBean = new
          InvoiceViewPrismDetailBean();
      load(dataSetRow, invoiceViewPrismDetailBean);
      SearchCriteria detailCriteria = new SearchCriteria();
      detailCriteria.addCriterion("invoice",
                                  SearchCriterion.EQUALS,
                                  "" + invoiceViewPrismDetailBean.getInvoice());
      detailCriteria.addCriterion("invoiceLine",
                                  SearchCriterion.EQUALS,
                                  "" + invoiceViewPrismDetailBean.getInvoiceLine());
      invoiceViewPrismDetailBean.setInvoiceAddChargeDetailBeanColl(factory.selectEw(detailCriteria));
      invoiceViewGoletaewDetailBeanColl.add(invoiceViewPrismDetailBean);
    }

    return invoiceViewGoletaewDetailBeanColl;
  }
}