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
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.invoice.beans.InvoiceDetailBean;

/******************************************************************************
 * CLASSNAME: InvoiceDetailBeanFactory <br>
 * @version: 1.0, Mar 9, 2005 <br>
 *****************************************************************************/

public class InvoiceDetailBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_LINE = "INVOICE_LINE";
  public String ATTRIBUTE_ISSUE_ID = "ISSUE_ID";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_QUANTITY = "QUANTITY";
  public String ATTRIBUTE_INVOICE_UNIT_PRICE = "INVOICE_UNIT_PRICE";
  public String ATTRIBUTE_UNIT_COST = "UNIT_COST";
  public String ATTRIBUTE_CATALOG_PRICE = "CATALOG_PRICE";
  public String ATTRIBUTE_BASELINE_PRICE = "BASELINE_PRICE";
  public String ATTRIBUTE_UNIT_REBATE = "UNIT_REBATE";
  public String ATTRIBUTE_TOTAL_ADD_CHARGE = "TOTAL_ADD_CHARGE";
  public String ATTRIBUTE_SERVICE_FEE = "SERVICE_FEE";
  public String ATTRIBUTE_SUPPLIER_DISCOUNT = "SUPPLIER_DISCOUNT";
  public String ATTRIBUTE_PERCENT_SPLIT_CHARGE = "PERCENT_SPLIT_CHARGE";
  public String ATTRIBUTE_SALES_ORDER = "SALES_ORDER";
  public String ATTRIBUTE_SO_LINE = "SO_LINE";
  public String ATTRIBUTE_DELIVERY_ID = "DELIVERY_ID";
  public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
  public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_APPLICATION = "APPLICATION";
  public String ATTRIBUTE_REQUESTOR_NAME = "REQUESTOR_NAME";
  public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
  public String ATTRIBUTE_PEI_AMOUNT = "PEI_AMOUNT";
  public String ATTRIBUTE_NET_AMOUNT = "NET_AMOUNT";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
  public String ATTRIBUTE_HUB = "HUB";
  public String ATTRIBUTE_ISSUE_COST_REVISION = "ISSUE_COST_REVISION";
  public String ATTRIBUTE_TOTAL_SALES_TAX = "TOTAL_SALES_TAX";
  public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
  public String ATTRIBUTE_TAX_JURISDICTION = "TAX_JURISDICTION";
  public String ATTRIBUTE_SALES_TAX_ID = "SALES_TAX_ID";
  public String ATTRIBUTE_WASTE_ORDER_NO = "WASTE_ORDER_NO";
  public String ATTRIBUTE_WASTE_MANIFEST_ID = "WASTE_MANIFEST_ID";
  public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
  public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";
  public String ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH =
      "UNIT_OF_SALE_QUANTITY_PER_EACH";
  public String ATTRIBUTE_REFERENCE_NUMBER = "REFERENCE_NUMBER";

  //this is for FEC
  public String ATTRIBUTE_ADDITIONAL_CHARGE_DESC = "ADDITIONAL_CHARGE_DESC";
  public String ATTRIBUTE_ADDITIONAL_CHARGE_AMOUNT = "ADDITIONAL_CHARGE_AMOUNT";

  //table name
  public String TABLE = "INVOICE_DETAIL";

  //constructor
  public InvoiceDetailBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("issueId")) {
      return ATTRIBUTE_ISSUE_ID;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("quantity")) {
      return ATTRIBUTE_QUANTITY;
    }
    else if (attributeName.equals("invoiceUnitPrice")) {
      return ATTRIBUTE_INVOICE_UNIT_PRICE;
    }
    else if (attributeName.equals("unitCost")) {
      return ATTRIBUTE_UNIT_COST;
    }
    else if (attributeName.equals("catalogPrice")) {
      return ATTRIBUTE_CATALOG_PRICE;
    }
    else if (attributeName.equals("baselinePrice")) {
      return ATTRIBUTE_BASELINE_PRICE;
    }
    else if (attributeName.equals("unitRebate")) {
      return ATTRIBUTE_UNIT_REBATE;
    }
    else if (attributeName.equals("totalAddCharge")) {
      return ATTRIBUTE_TOTAL_ADD_CHARGE;
    }
    else if (attributeName.equals("serviceFee")) {
      return ATTRIBUTE_SERVICE_FEE;
    }
    else if (attributeName.equals("supplierDiscount")) {
      return ATTRIBUTE_SUPPLIER_DISCOUNT;
    }
    else if (attributeName.equals("percentSplitCharge")) {
      return ATTRIBUTE_PERCENT_SPLIT_CHARGE;
    }
    else if (attributeName.equals("salesOrder")) {
      return ATTRIBUTE_SALES_ORDER;
    }
    else if (attributeName.equals("soLine")) {
      return ATTRIBUTE_SO_LINE;
    }
    else if (attributeName.equals("deliveryId")) {
      return ATTRIBUTE_DELIVERY_ID;
    }
    else if (attributeName.equals("itemId")) {
      return ATTRIBUTE_ITEM_ID;
    }
    else if (attributeName.equals("dateDelivered")) {
      return ATTRIBUTE_DATE_DELIVERED;
    }
    else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("itemType")) {
      return ATTRIBUTE_ITEM_TYPE;
    }
    else if (attributeName.equals("prNumber")) {
      return ATTRIBUTE_PR_NUMBER;
    }
    else if (attributeName.equals("lineItem")) {
      return ATTRIBUTE_LINE_ITEM;
    }
    else if (attributeName.equals("application")) {
      return ATTRIBUTE_APPLICATION;
    }
    else if (attributeName.equals("requestorName")) {
      return ATTRIBUTE_REQUESTOR_NAME;
    }
    else if (attributeName.equals("mfgLot")) {
      return ATTRIBUTE_MFG_LOT;
    }
    else if (attributeName.equals("peiAmount")) {
      return ATTRIBUTE_PEI_AMOUNT;
    }
    else if (attributeName.equals("netAmount")) {
      return ATTRIBUTE_NET_AMOUNT;
    }
    else if (attributeName.equals("partDescription")) {
      return ATTRIBUTE_PART_DESCRIPTION;
    }
    else if (attributeName.equals("hub")) {
      return ATTRIBUTE_HUB;
    }
    else if (attributeName.equals("issueCostRevision")) {
      return ATTRIBUTE_ISSUE_COST_REVISION;
    }
    else if (attributeName.equals("totalSalesTax")) {
      return ATTRIBUTE_TOTAL_SALES_TAX;
    }
    else if (attributeName.equals("receiptId")) {
      return ATTRIBUTE_RECEIPT_ID;
    }
    else if (attributeName.equals("taxJurisdiction")) {
      return ATTRIBUTE_TAX_JURISDICTION;
    }
    else if (attributeName.equals("salesTaxId")) {
      return ATTRIBUTE_SALES_TAX_ID;
    }
    else if (attributeName.equals("wasteOrderNo")) {
      return ATTRIBUTE_WASTE_ORDER_NO;
    }
    else if (attributeName.equals("wasteManifestId")) {
      return ATTRIBUTE_WASTE_MANIFEST_ID;
    }
    else if (attributeName.equals("catalogId")) {
      return ATTRIBUTE_CATALOG_ID;
    }
    else if (attributeName.equals("unitOfSale")) {
      return ATTRIBUTE_UNIT_OF_SALE;
    }
    else if (attributeName.equals("unitOfSaleQuantityPerEach")) {
      return ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH;
    }
    else if (attributeName.equals("referenceNumber")) {
      return ATTRIBUTE_REFERENCE_NUMBER;
    }
    else if (attributeName.equals("additionalChargeDesc")) {
      return ATTRIBUTE_ADDITIONAL_CHARGE_DESC;
    }
    else if (attributeName.equals("additionalChargeAmount")) {
      return ATTRIBUTE_ADDITIONAL_CHARGE_AMOUNT;
    }

    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceDetailBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceDetailBean invoiceDetailBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceDetailBean.getInvoice());
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
   public int delete(InvoiceDetailBean invoiceDetailBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceDetailBean.getInvoice());
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
   public int insert(InvoiceDetailBean invoiceDetailBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceDetailBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceDetailBean invoiceDetailBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_LINE + "," +
     ATTRIBUTE_ISSUE_ID + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_QUANTITY + "," +
     ATTRIBUTE_INVOICE_UNIT_PRICE + "," +
     ATTRIBUTE_UNIT_COST + "," +
     ATTRIBUTE_CATALOG_PRICE + "," +
     ATTRIBUTE_BASELINE_PRICE + "," +
     ATTRIBUTE_UNIT_REBATE + "," +
     ATTRIBUTE_TOTAL_ADD_CHARGE + "," +
     ATTRIBUTE_SERVICE_FEE + "," +
     ATTRIBUTE_SUPPLIER_DISCOUNT + "," +
     ATTRIBUTE_PERCENT_SPLIT_CHARGE + "," +
     ATTRIBUTE_SALES_ORDER + "," +
     ATTRIBUTE_SO_LINE + "," +
     ATTRIBUTE_DELIVERY_ID + "," +
     ATTRIBUTE_ITEM_ID + "," +
     ATTRIBUTE_DATE_DELIVERED + "," +
     ATTRIBUTE_CAT_PART_NO + "," +
     ATTRIBUTE_ITEM_TYPE + "," +
     ATTRIBUTE_PR_NUMBER + "," +
     ATTRIBUTE_LINE_ITEM + "," +
     ATTRIBUTE_APPLICATION + "," +
     ATTRIBUTE_REQUESTOR_NAME + "," +
     ATTRIBUTE_MFG_LOT + "," +
     ATTRIBUTE_PEI_AMOUNT + "," +
     ATTRIBUTE_NET_AMOUNT + "," +
     ATTRIBUTE_PART_DESCRIPTION + "," +
     ATTRIBUTE_HUB + "," +
     ATTRIBUTE_ISSUE_COST_REVISION + "," +
     ATTRIBUTE_TOTAL_SALES_TAX + "," +
     ATTRIBUTE_RECEIPT_ID + "," +
     ATTRIBUTE_TAX_JURISDICTION + "," +
     ATTRIBUTE_SALES_TAX_ID + "," +
     ATTRIBUTE_WASTE_ORDER_NO + "," +
     ATTRIBUTE_WASTE_MANIFEST_ID + "," +
     ATTRIBUTE_CATALOG_ID + "," +
     ATTRIBUTE_UNIT_OF_SALE + "," +
     ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH + ")" +
   values (
     StringHandler.nullIfZero(invoiceDetailBean.getInvoice()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getInvoiceLine()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getIssueId()) + "," +
     SqlHandler.delimitString(invoiceDetailBean.getCompanyId()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getQuantity()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getInvoiceUnitPrice()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getUnitCost()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getCatalogPrice()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getBaselinePrice()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getUnitRebate()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getTotalAddCharge()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getServiceFee()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getSupplierDiscount()) + "," +
       StringHandler.nullIfZero(invoiceDetailBean.getPercentSplitCharge()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getSalesOrder()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getSoLine()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getDeliveryId()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getItemId()) + "," +
     DateHandler.getOracleToDateFunction(invoiceDetailBean.getDateDelivered()) + "," +
     SqlHandler.delimitString(invoiceDetailBean.getCatPartNo()) + "," +
     SqlHandler.delimitString(invoiceDetailBean.getItemType()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getPrNumber()) + "," +
     SqlHandler.delimitString(invoiceDetailBean.getLineItem()) + "," +
     SqlHandler.delimitString(invoiceDetailBean.getApplication()) + "," +
     SqlHandler.delimitString(invoiceDetailBean.getRequestorName()) + "," +
     SqlHandler.delimitString(invoiceDetailBean.getMfgLot()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getPeiAmount()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getNetAmount()) + "," +
     SqlHandler.delimitString(invoiceDetailBean.getPartDescription()) + "," +
     SqlHandler.delimitString(invoiceDetailBean.getHub()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getIssueCostRevision()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getTotalSalesTax()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getReceiptId()) + "," +
     SqlHandler.delimitString(invoiceDetailBean.getTaxJurisdiction()) + "," +
     SqlHandler.delimitString(invoiceDetailBean.getSalesTaxId()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getWasteOrderNo()) + "," +
     SqlHandler.delimitString(invoiceDetailBean.getWasteManifestId()) + "," +
     SqlHandler.delimitString(invoiceDetailBean.getCatalogId()) + "," +
     SqlHandler.delimitString(invoiceDetailBean.getUnitOfSale()) + "," +
     StringHandler.nullIfZero(invoiceDetailBean.getUnitOfSaleQuantityPerEach()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceDetailBean invoiceDetailBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceDetailBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceDetailBean invoiceDetailBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_LINE + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getInvoiceLine()) + "," +
     ATTRIBUTE_ISSUE_ID + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getIssueId()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(invoiceDetailBean.getCompanyId()) + "," +
     ATTRIBUTE_QUANTITY + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getQuantity()) + "," +
     ATTRIBUTE_INVOICE_UNIT_PRICE + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getInvoiceUnitPrice()) + "," +
     ATTRIBUTE_UNIT_COST + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getUnitCost()) + "," +
     ATTRIBUTE_CATALOG_PRICE + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getCatalogPrice()) + "," +
     ATTRIBUTE_BASELINE_PRICE + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getBaselinePrice()) + "," +
     ATTRIBUTE_UNIT_REBATE + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getUnitRebate()) + "," +
     ATTRIBUTE_TOTAL_ADD_CHARGE + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getTotalAddCharge()) + "," +
     ATTRIBUTE_SERVICE_FEE + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getServiceFee()) + "," +
     ATTRIBUTE_SUPPLIER_DISCOUNT + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getSupplierDiscount()) + "," +
     ATTRIBUTE_PERCENT_SPLIT_CHARGE + "=" +
       StringHandler.nullIfZero(invoiceDetailBean.getPercentSplitCharge()) + "," +
     ATTRIBUTE_SALES_ORDER + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getSalesOrder()) + "," +
     ATTRIBUTE_SO_LINE + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getSoLine()) + "," +
     ATTRIBUTE_DELIVERY_ID + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getDeliveryId()) + "," +
     ATTRIBUTE_ITEM_ID + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getItemId()) + "," +
     ATTRIBUTE_DATE_DELIVERED + "=" +
      DateHandler.getOracleToDateFunction(invoiceDetailBean.getDateDelivered()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
      SqlHandler.delimitString(invoiceDetailBean.getCatPartNo()) + "," +
     ATTRIBUTE_ITEM_TYPE + "=" +
      SqlHandler.delimitString(invoiceDetailBean.getItemType()) + "," +
     ATTRIBUTE_PR_NUMBER + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getPrNumber()) + "," +
     ATTRIBUTE_LINE_ITEM + "=" +
      SqlHandler.delimitString(invoiceDetailBean.getLineItem()) + "," +
     ATTRIBUTE_APPLICATION + "=" +
      SqlHandler.delimitString(invoiceDetailBean.getApplication()) + "," +
     ATTRIBUTE_REQUESTOR_NAME + "=" +
      SqlHandler.delimitString(invoiceDetailBean.getRequestorName()) + "," +
     ATTRIBUTE_MFG_LOT + "=" +
      SqlHandler.delimitString(invoiceDetailBean.getMfgLot()) + "," +
     ATTRIBUTE_PEI_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getPeiAmount()) + "," +
     ATTRIBUTE_NET_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getNetAmount()) + "," +
     ATTRIBUTE_PART_DESCRIPTION + "=" +
      SqlHandler.delimitString(invoiceDetailBean.getPartDescription()) + "," +
     ATTRIBUTE_HUB + "=" +
      SqlHandler.delimitString(invoiceDetailBean.getHub()) + "," +
     ATTRIBUTE_ISSUE_COST_REVISION + "=" +
       StringHandler.nullIfZero(invoiceDetailBean.getIssueCostRevision()) + "," +
     ATTRIBUTE_TOTAL_SALES_TAX + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getTotalSalesTax()) + "," +
     ATTRIBUTE_RECEIPT_ID + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getReceiptId()) + "," +
     ATTRIBUTE_TAX_JURISDICTION + "=" +
      SqlHandler.delimitString(invoiceDetailBean.getTaxJurisdiction()) + "," +
     ATTRIBUTE_SALES_TAX_ID + "=" +
      SqlHandler.delimitString(invoiceDetailBean.getSalesTaxId()) + "," +
     ATTRIBUTE_WASTE_ORDER_NO + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getWasteOrderNo()) + "," +
     ATTRIBUTE_WASTE_MANIFEST_ID + "=" +
      SqlHandler.delimitString(invoiceDetailBean.getWasteManifestId()) + "," +
     ATTRIBUTE_CATALOG_ID + "=" +
      SqlHandler.delimitString(invoiceDetailBean.getCatalogId()) + "," +
     ATTRIBUTE_UNIT_OF_SALE + "=" +
      SqlHandler.delimitString(invoiceDetailBean.getUnitOfSale()) + "," +
     ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getUnitOfSaleQuantityPerEach()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceDetailBean.getInvoice());
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

    Collection invoiceDetailBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
        log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceDetailBean invoiceDetailBean = new InvoiceDetailBean();
      load(dataSetRow, invoiceDetailBean);
      invoiceDetailBeanColl.add(invoiceDetailBean);
    }

    return invoiceDetailBeanColl;
  }

  public Collection selectFec(BigDecimal invoice, String referenceNumber) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectFec(invoice, referenceNumber, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectFec(BigDecimal invoice, String referenceNumber, Connection conn) throws
      BaseException {

    Collection invoiceDetailBeanColl = new Vector();

    String query = "select a.ADDITIONAL_CHARGE_DESC, a.ADDITIONAL_CHARGE_AMOUNT, d.* " +
                   "From invoice_detail d, invoice_add_charge_detail a " +
                   "where d.invoice=" + invoice + " " +
                   "and d.reference_number=" + SqlHandler.delimitString(referenceNumber) + " " +
                   "and d.invoice = a.invoice and d.issue_id = a.issue_id " +
                   "order by d.invoice, d.reference_number";

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceDetailBean invoiceDetailBean = new InvoiceDetailBean();
      load(dataSetRow, invoiceDetailBean);
      invoiceDetailBeanColl.add(invoiceDetailBean);
    }

    return invoiceDetailBeanColl;
  }

  public Collection selectCaterpillar(BigDecimal invoice) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectCaterpillar(invoice, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectCaterpillar(BigDecimal invoice, Connection conn) throws
      BaseException {

    Collection invoiceDetailBeanColl = new Vector();

    String query = "select invoice, invoice_unit_price,unit_of_sale, part_Description, sum(quantity) quantity " +
            "from invoice_detail " +
            "where invoice=" +invoice + " " +
            "group by invoice,invoice_unit_price,unit_of_sale, part_Description";

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceDetailBean invoiceDetailBean = new InvoiceDetailBean();
      load(dataSetRow, invoiceDetailBean);
      invoiceDetailBeanColl.add(invoiceDetailBean);
    }

    return invoiceDetailBeanColl;
  }
}