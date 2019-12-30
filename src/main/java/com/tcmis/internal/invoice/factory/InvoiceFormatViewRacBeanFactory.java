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
import com.tcmis.internal.invoice.beans.InvoiceFormatViewRacBean;
import com.tcmis.internal.invoice.factory.InvoiceAddChargeDetailBeanFactory;
import com.tcmis.internal.invoice.factory.InvoiceAddChargePrepBeanFactory;

/******************************************************************************
 * CLASSNAME: Sbuffum.invoiceFormatViewRacBeanFactory <br>
 * @version: 1.0, May 1, 2006 <br>
 *****************************************************************************/

public class InvoiceFormatViewRacBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_LINE = "INVOICE_LINE";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_QUANTITY = "QUANTITY";
  public String ATTRIBUTE_REQUEST_QUANTITY = "REQUEST_QUANTITY";
  public String ATTRIBUTE_RAW_INVOICE_UNIT_PRICE = "RAW_INVOICE_UNIT_PRICE";
  public String ATTRIBUTE_ADJ_INVOICE_UNIT_PRICE = "ADJ_INVOICE_UNIT_PRICE";
  public String ATTRIBUTE_UNIT_PRICE_SAVINGS = "UNIT_PRICE_SAVINGS";
  public String ATTRIBUTE_UNIT_REBATE = "UNIT_REBATE";
  public String ATTRIBUTE_EXTENDED_PRICE = "EXTENDED_PRICE";
  public String ATTRIBUTE_HAAS_GAINSHARE = "HAAS_GAINSHARE";
  public String ATTRIBUTE_NET_AMOUNT = "NET_AMOUNT";
  public String ATTRIBUTE_NET_REBATE = "NET_REBATE";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
  public String ATTRIBUTE_REQUESTOR_NAME = "REQUESTOR_NAME";
  public String ATTRIBUTE_MR_NUMBER = "MR_NUMBER";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";
  public String ATTRIBUTE_QTY_PER_EACH = "QTY_PER_EACH";
  public String ATTRIBUTE_SERVICE_FEE_DATE = "SERVICE_FEE_DATE";
  public String ATTRIBUTE_NET_SAVINGS = "NET_SAVINGS";
  public String ATTRIBUTE_RAC_UNIT_GAINSHARE = "RAC_UNIT_GAINSHARE";
  public String ATTRIBUTE_UNIT_COST = "UNIT_COST";
  public String ATTRIBUTE_INVOICE_DATE_STR = "INVOICE_DATE_STR";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_BASELINE_PRICE = "BASELINE_PRICE";
  public String ATTRIBUTE_RELEASE_NUMBER = "RELEASE_NUMBER";
  public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
  public String ATTRIBUTE_MFG_LOT = "MFG_LOT";


  public String ATTRIBUTE_TOTAL_ADD_CHARGE = "TOTAL_ADD_CHARGE";
  public String ATTRIBUTE_DATE_DELIVERED_DATE = "DATE_DELIVERED_DATE";
  public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
  public String ATTRIBUTE_INVOICE_DATE_DATE = "INVOICE_DATE_DATE";
  //table name
  public String TABLE = "SBUFFUM.INVOICE_FORMAT_VIEW_RAC";

  //constructor
  public InvoiceFormatViewRacBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("requestQuantity")) {
      return ATTRIBUTE_REQUEST_QUANTITY;
    }
    else if (attributeName.equals("rawInvoiceUnitPrice")) {
      return ATTRIBUTE_RAW_INVOICE_UNIT_PRICE;
    }
    else if (attributeName.equals("adjInvoiceUnitPrice")) {
      return ATTRIBUTE_ADJ_INVOICE_UNIT_PRICE;
    }
    else if (attributeName.equals("unitPriceSavings")) {
      return ATTRIBUTE_UNIT_PRICE_SAVINGS;
    }
    else if (attributeName.equals("unitRebate")) {
      return ATTRIBUTE_UNIT_REBATE;
    }
    else if (attributeName.equals("extendedPrice")) {
      return ATTRIBUTE_EXTENDED_PRICE;
    }
    else if (attributeName.equals("haasGainshare")) {
      return ATTRIBUTE_HAAS_GAINSHARE;
    }
    else if (attributeName.equals("netAmount")) {
      return ATTRIBUTE_NET_AMOUNT;
    }
    else if (attributeName.equals("netRebate")) {
      return ATTRIBUTE_NET_REBATE;
    }
    else if (attributeName.equals("partDescription")) {
      return ATTRIBUTE_PART_DESCRIPTION;
    }
    else if (attributeName.equals("requestorName")) {
      return ATTRIBUTE_REQUESTOR_NAME;
    }
    else if (attributeName.equals("mrNumber")) {
      return ATTRIBUTE_MR_NUMBER;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else if (attributeName.equals("unitOfSale")) {
      return ATTRIBUTE_UNIT_OF_SALE;
    }
    else if (attributeName.equals("qtyPerEach")) {
      return ATTRIBUTE_QTY_PER_EACH;
    }
    else if (attributeName.equals("serviceFeeDate")) {
      return ATTRIBUTE_SERVICE_FEE_DATE;
    }
    else if (attributeName.equals("netSavings")) {
      return ATTRIBUTE_NET_SAVINGS;
    }
    else if (attributeName.equals("racUnitGainshare")) {
      return ATTRIBUTE_RAC_UNIT_GAINSHARE;
    }
    else if (attributeName.equals("unitCost")) {
      return ATTRIBUTE_UNIT_COST;
    }
    else if (attributeName.equals("invoiceDateStr")) {
      return ATTRIBUTE_INVOICE_DATE_STR;
    }
    else if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else if (attributeName.equals("baselinePrice")) {
      return ATTRIBUTE_BASELINE_PRICE;
    }
    else if (attributeName.equals("releaseNumber")) {
      return ATTRIBUTE_RELEASE_NUMBER;
    }
    else if (attributeName.equals("dateDelivered")) {
      return ATTRIBUTE_DATE_DELIVERED;
    }
    else if (attributeName.equals("mfgLot")) {
      return ATTRIBUTE_MFG_LOT;
    }
    else if (attributeName.equals("totalAddCharge")) {
      return ATTRIBUTE_TOTAL_ADD_CHARGE;
    }
    else if (attributeName.equals("dateDeliveredDate")) {
      return ATTRIBUTE_DATE_DELIVERED_DATE;
    }
    else if (attributeName.equals("invoiceAmount")) {
      return ATTRIBUTE_INVOICE_AMOUNT;
    }
    else if (attributeName.equals("invoiceDateDate")) {
      return ATTRIBUTE_INVOICE_DATE_DATE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceFormatViewRacBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(Sbuffum.invoiceFormatViewRacBean sbuffum.invoiceFormatViewRacBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + sbuffum.invoiceFormatViewRacBean.getInvoice());
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
   public int delete(Sbuffum.invoiceFormatViewRacBean sbuffum.invoiceFormatViewRacBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + sbuffum.invoiceFormatViewRacBean.getInvoice());
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
   public int insert(Sbuffum.invoiceFormatViewRacBean sbuffum.invoiceFormatViewRacBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(sbuffum.invoiceFormatViewRacBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(Sbuffum.invoiceFormatViewRacBean sbuffum.invoiceFormatViewRacBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_LINE + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_CAT_PART_NO + "," +
     ATTRIBUTE_QUANTITY + "," +
     ATTRIBUTE_REQUEST_QUANTITY + "," +
     ATTRIBUTE_RAW_INVOICE_UNIT_PRICE + "," +
     ATTRIBUTE_ADJ_INVOICE_UNIT_PRICE + "," +
     ATTRIBUTE_UNIT_PRICE_SAVINGS + "," +
     ATTRIBUTE_UNIT_REBATE + "," +
     ATTRIBUTE_EXTENDED_PRICE + "," +
     ATTRIBUTE_HAAS_GAINSHARE + "," +
     ATTRIBUTE_NET_AMOUNT + "," +
     ATTRIBUTE_NET_REBATE + "," +
     ATTRIBUTE_PART_DESCRIPTION + "," +
     ATTRIBUTE_REQUESTOR_NAME + "," +
     ATTRIBUTE_MR_NUMBER + "," +
     ATTRIBUTE_INVOICE_DATE + "," +
     ATTRIBUTE_UNIT_OF_SALE + "," +
     ATTRIBUTE_QTY_PER_EACH + "," +
     ATTRIBUTE_SERVICE_FEE_DATE + "," +
     ATTRIBUTE_NET_SAVINGS + "," +
     ATTRIBUTE_RAC_UNIT_GAINSHARE + "," +
     ATTRIBUTE_UNIT_COST + "," +
     ATTRIBUTE_INVOICE_DATE_STR + "," +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_BASELINE_PRICE + "," +
     ATTRIBUTE_RELEASE_NUMBER + "," +
     ATTRIBUTE_DATE_DELIVERED + "," +
     ATTRIBUTE_MFG_LOT + ")" +
     " values (" +
     sbuffum.invoiceFormatViewRacBean.getInvoice() + "," +
     sbuffum.invoiceFormatViewRacBean.getInvoiceLine() + "," +
       SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getPoNumber()) + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getCatPartNo()) + "," +
       SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getQuantity()) + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getRequestQuantity()) + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getRawInvoiceUnitPrice()) + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getAdjInvoiceUnitPrice()) + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getUnitPriceSavings()) + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getUnitRebate()) + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getExtendedPrice()) + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getHaasGainshare()) + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getNetAmount()) + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getNetRebate()) + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getPartDescription()) + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getRequestorName()) + "," +
       SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getMrNumber()) + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getInvoiceDate()) + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getUnitOfSale()) + "," +
     sbuffum.invoiceFormatViewRacBean.getQtyPerEach() + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getServiceFeeDate()) + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getNetSavings()) + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getRacUnitGainshare()) + "," +
       SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getUnitCost()) + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getInvoiceDateStr()) + "," +
     sbuffum.invoiceFormatViewRacBean.getInvoicePeriod() + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getBaselinePrice()) + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getReleaseNumber()) + "," +
     SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getDateDelivered()) + "," +
       SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getMfgLot()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(Sbuffum.invoiceFormatViewRacBean sbuffum.invoiceFormatViewRacBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(sbuffum.invoiceFormatViewRacBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(Sbuffum.invoiceFormatViewRacBean sbuffum.invoiceFormatViewRacBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
       StringHandler.nullIfZero(sbuffum.invoiceFormatViewRacBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_LINE + "=" +
      StringHandler.nullIfZero(sbuffum.invoiceFormatViewRacBean.getInvoiceLine()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
       SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getPoNumber()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getCatPartNo()) + "," +
     ATTRIBUTE_QUANTITY + "=" +
       SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getQuantity()) + "," +
     ATTRIBUTE_REQUEST_QUANTITY + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getRequestQuantity()) + "," +
     ATTRIBUTE_RAW_INVOICE_UNIT_PRICE + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getRawInvoiceUnitPrice()) + "," +
     ATTRIBUTE_ADJ_INVOICE_UNIT_PRICE + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getAdjInvoiceUnitPrice()) + "," +
     ATTRIBUTE_UNIT_PRICE_SAVINGS + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getUnitPriceSavings()) + "," +
     ATTRIBUTE_UNIT_REBATE + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getUnitRebate()) + "," +
     ATTRIBUTE_EXTENDED_PRICE + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getExtendedPrice()) + "," +
     ATTRIBUTE_HAAS_GAINSHARE + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getHaasGainshare()) + "," +
     ATTRIBUTE_NET_AMOUNT + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getNetAmount()) + "," +
     ATTRIBUTE_NET_REBATE + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getNetRebate()) + "," +
     ATTRIBUTE_PART_DESCRIPTION + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getPartDescription()) + "," +
     ATTRIBUTE_REQUESTOR_NAME + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getRequestorName()) + "," +
     ATTRIBUTE_MR_NUMBER + "=" +
       SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getMrNumber()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getInvoiceDate()) + "," +
     ATTRIBUTE_UNIT_OF_SALE + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getUnitOfSale()) + "," +
     ATTRIBUTE_QTY_PER_EACH + "=" +
      StringHandler.nullIfZero(sbuffum.invoiceFormatViewRacBean.getQtyPerEach()) + "," +
     ATTRIBUTE_SERVICE_FEE_DATE + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getServiceFeeDate()) + "," +
     ATTRIBUTE_NET_SAVINGS + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getNetSavings()) + "," +
     ATTRIBUTE_RAC_UNIT_GAINSHARE + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getRacUnitGainshare()) + "," +
     ATTRIBUTE_UNIT_COST + "=" +
       SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getUnitCost()) + "," +
     ATTRIBUTE_INVOICE_DATE_STR + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getInvoiceDateStr()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(sbuffum.invoiceFormatViewRacBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_BASELINE_PRICE + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getBaselinePrice()) + "," +
     ATTRIBUTE_RELEASE_NUMBER + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getReleaseNumber()) + "," +
     ATTRIBUTE_DATE_DELIVERED + "=" +
      SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getDateDelivered()) + "," +
     ATTRIBUTE_MFG_LOT + "=" +
       SqlHandler.delimitString(sbuffum.invoiceFormatViewRacBean.getMfgLot()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      sbuffum.invoiceFormatViewRacBean.getInvoice();
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(BigDecimal invoicePeriod) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(invoicePeriod, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(BigDecimal invoicePeriod, Connection conn) throws
      BaseException {

    Collection invoiceFormatViewRacBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
                   "where invoice_period = " + invoicePeriod + " " +
                   "and invoice not in (select d.invoice " +
                     "from invoice i,invoice_detail d where " +
                     "i.invoice = d.invoice and i.company_id = 'RAYTHEON' " +
                     "and i.invoice_group = 'RAC' and " +
                     "i.invoice_period = " + invoicePeriod + " " +
                     "group by d.invoice having sum(quantity) = 0) " +
                    "order by invoice,date_delivered";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();
    InvoiceAddChargeDetailBeanFactory fac = new InvoiceAddChargeDetailBeanFactory(this.getDbManager());
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceFormatViewRacBean invoiceFormatViewRacBean = new
          InvoiceFormatViewRacBean();
      load(dataSetRow, invoiceFormatViewRacBean);
      SearchCriteria criteria = new SearchCriteria("invoice",
                                                   SearchCriterion.EQUALS,
                                                   "" + invoiceFormatViewRacBean.getInvoice());
      criteria.addCriterion("invoiceLine", SearchCriterion.EQUALS, "" + invoiceFormatViewRacBean.getInvoiceLine());
      invoiceFormatViewRacBean.setAddChargeBeanCollection(fac.selectRac(criteria));
      invoiceFormatViewRacBeanColl.add(invoiceFormatViewRacBean);
    }

    return invoiceFormatViewRacBeanColl;
  }

  public Collection selectCorrectionInvoices(BigDecimal invoicePeriod) throws
      BaseException {
    Connection connection = null;
    Collection c = new Vector();
    String query = "select d.invoice,sum(quantity) quantity_sum, invoice_period " +
        "from invoice i,invoice_detail d " +
        "where i.invoice = d.invoice and " +
        "i.company_id = 'RAYTHEON' and i.invoice_group = 'RAC' " +
        "and i.invoice_period = " + invoicePeriod + " " +
        "group by invoice_period, d.invoice having sum(quantity) = 0";
    try {
      connection = this.getDbManager().getConnection();

      DataSet dataSet = new SqlManager().select(connection, query);

      Iterator dataIter = dataSet.iterator();

      while (dataIter.hasNext()) {
        DataSetRow dataSetRow = (DataSetRow) dataIter.next();
        InvoiceFormatViewRacBean invoiceFormatViewRacBean = new
            InvoiceFormatViewRacBean();
        load(dataSetRow, invoiceFormatViewRacBean);
        c.add(invoiceFormatViewRacBean);
      }
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }


  public Collection selectCorrectionInvoicesDetail(BigDecimal invoice) throws
      BaseException {
    Connection connection = null;
    Collection c = new Vector();
    String query = "select distinct p.invoice, p.invoice_line, p.po_number, " +
                   "cat_part_no, p.quantity, r.quantity request_quantity, " +
                   "date_delivered date_delivered_date, invoice_unit_price - unit_rebate adj_invoice_unit_price, " +
                   "nvl(total_add_charge, 0) total_add_charge, net_amount, " +
                   "invoice_amount, invoice_date invoice_date_date " +
                   "from invoice_prep p, request_line_item r, invoice i " +
                   "where i.invoice = " + invoice + " " +
                   "and i.invoice = p.invoice " +
                   "and i.invoice_group = 'RAC' " +
                   "and p.pr_number = r.pr_number " +
                   "and p.line_item = r.line_item " +
                   "and p.invoice in(" +
                      "select d.invoice " +
                      "from invoice i, invoice_detail d " +
                      "where i.invoice = d.invoice " +
                      "and i.company_id = 'RAYTHEON' " +
                      "and i.invoice_group = 'RAC' " +
                      "and i.invoice = " + invoice + " " +
                      "group by d.invoice having sum(quantity) = 0) " + 
                    "order by invoice, p.quantity";
      if(log.isDebugEnabled()) {
        log.debug("QUERY:" + query);
      }
      try {
      connection = this.getDbManager().getConnection();

      DataSet dataSet = new SqlManager().select(connection, query);

      Iterator dataIter = dataSet.iterator();
      InvoiceAddChargePrepBeanFactory fac = new InvoiceAddChargePrepBeanFactory(this.getDbManager());
      boolean racFlag = true;;
      while (dataIter.hasNext()) {
        DataSetRow dataSetRow = (DataSetRow) dataIter.next();
        InvoiceFormatViewRacBean invoiceFormatViewRacBean = new
            InvoiceFormatViewRacBean();
        load(dataSetRow, invoiceFormatViewRacBean);
        //I only want the add charge detail in one bean
        if(racFlag) {
          SearchCriteria criteria = new SearchCriteria("invoice",
              SearchCriterion.EQUALS,
              "" + invoiceFormatViewRacBean.getInvoice());
          criteria.addCriterion("invoiceLine", SearchCriterion.EQUALS,
                                "" + invoiceFormatViewRacBean.getInvoiceLine());
          invoiceFormatViewRacBean.setAddChargeBeanCollection(fac.selectRac(
              criteria));
          racFlag = false;
        }
        c.add(invoiceFormatViewRacBean);
      }
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }
}