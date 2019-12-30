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
import com.tcmis.internal.invoice.beans.InvoiceViewMillerDetailBean;
import com.tcmis.internal.invoice.factory.InvoiceAddChargeDetailBeanFactory;

/******************************************************************************
 * CLASSNAME: InvoiceViewMillerDetailBeanFactory <br>
 * @version: 1.0, May 26, 2005 <br>
 *****************************************************************************/

public class InvoiceViewMillerDetailBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_LINE = "INVOICE_LINE";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_QUANTITY_DELIVERED = "QUANTITY_DELIVERED";
  public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
  public String ATTRIBUTE_QUANTITY_ORDERED = "QUANTITY_ORDERED";
  public String ATTRIBUTE_TOTAL_AMOUNT = "TOTAL_AMOUNT";
  public String ATTRIBUTE_EXT_PRICE = "EXT_PRICE";
  public String ATTRIBUTE_ADDITIONAL_CHARGES = "ADDITIONAL_CHARGES";
  public String ATTRIBUTE_SERVICE_FEE = "SERVICE_FEE";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_INVOICE_UNIT_PRICE = "INVOICE_UNIT_PRICE";
  public String ATTRIBUTE_PACKAGING = "PACKAGING";
  public String ATTRIBUTE_ORIGINAL_INVOICE = "ORIGINAL_INVOICE";

  //table name
  public String TABLE = "INVOICE_VIEW_MILLER_DETAIL";

  //constructor
  public InvoiceViewMillerDetailBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("quantityDelivered")) {
      return ATTRIBUTE_QUANTITY_DELIVERED;
    }
    else if (attributeName.equals("dateDelivered")) {
      return ATTRIBUTE_DATE_DELIVERED;
    }
    else if (attributeName.equals("quantityOrdered")) {
      return ATTRIBUTE_QUANTITY_ORDERED;
    }
    else if (attributeName.equals("totalAmount")) {
      return ATTRIBUTE_TOTAL_AMOUNT;
    }
    else if (attributeName.equals("extPrice")) {
      return ATTRIBUTE_EXT_PRICE;
    }
    else if (attributeName.equals("additionalCharges")) {
      return ATTRIBUTE_ADDITIONAL_CHARGES;
    }
    else if (attributeName.equals("serviceFee")) {
      return ATTRIBUTE_SERVICE_FEE;
    }
    else if (attributeName.equals("partDescription")) {
      return ATTRIBUTE_PART_DESCRIPTION;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else if (attributeName.equals("invoiceUnitPrice")) {
      return ATTRIBUTE_INVOICE_UNIT_PRICE;
    }
    else if (attributeName.equals("packaging")) {
      return ATTRIBUTE_PACKAGING;
    }
    else if (attributeName.equals("originalInvoice")) {
      return ATTRIBUTE_ORIGINAL_INVOICE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceViewMillerDetailBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceViewMillerDetailBean invoiceViewMillerDetailBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewMillerDetailBean.getInvoice());
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
   public int delete(InvoiceViewMillerDetailBean invoiceViewMillerDetailBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewMillerDetailBean.getInvoice());
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
   public int insert(InvoiceViewMillerDetailBean invoiceViewMillerDetailBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceViewMillerDetailBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceViewMillerDetailBean invoiceViewMillerDetailBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_LINE + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_CAT_PART_NO + "," +
     ATTRIBUTE_QUANTITY_DELIVERED + "," +
     ATTRIBUTE_DATE_DELIVERED + "," +
     ATTRIBUTE_QUANTITY_ORDERED + "," +
     ATTRIBUTE_TOTAL_AMOUNT + "," +
     ATTRIBUTE_EXT_PRICE + "," +
     ATTRIBUTE_ADDITIONAL_CHARGES + "," +
     ATTRIBUTE_SERVICE_FEE + "," +
     ATTRIBUTE_PART_DESCRIPTION + "," +
     ATTRIBUTE_INVOICE_DATE + "," +
     ATTRIBUTE_INVOICE_UNIT_PRICE + "," +
     ATTRIBUTE_PACKAGING + "," +
     ATTRIBUTE_ORIGINAL_INVOICE + ")" +
     " values (" +
     invoiceViewMillerDetailBean.getInvoice() + "," +
     invoiceViewMillerDetailBean.getInvoiceLine() + "," +
       SqlHandler.delimitString(invoiceViewMillerDetailBean.getPoNumber()) + "," +
       SqlHandler.delimitString(invoiceViewMillerDetailBean.getCatPartNo()) + "," +
     invoiceViewMillerDetailBean.getQuantityDelivered() + "," +
     DateHandler.getOracleToDateFunction(invoiceViewMillerDetailBean.getDateDelivered()) + "," +
     invoiceViewMillerDetailBean.getQuantityOrdered() + "," +
     invoiceViewMillerDetailBean.getTotalAmount() + "," +
     invoiceViewMillerDetailBean.getExtPrice() + "," +
     invoiceViewMillerDetailBean.getAdditionalCharges() + "," +
     invoiceViewMillerDetailBean.getServiceFee() + "," +
     SqlHandler.delimitString(invoiceViewMillerDetailBean.getPartDescription()) + "," +
     DateHandler.getOracleToDateFunction(invoiceViewMillerDetailBean.getInvoiceDate()) + "," +
     invoiceViewMillerDetailBean.getInvoiceUnitPrice() + "," +
       SqlHandler.delimitString(invoiceViewMillerDetailBean.getPackaging()) + "," +
     invoiceViewMillerDetailBean.getOriginalInvoice() + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceViewMillerDetailBean invoiceViewMillerDetailBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceViewMillerDetailBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceViewMillerDetailBean invoiceViewMillerDetailBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
       StringHandler.nullIfZero(invoiceViewMillerDetailBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_LINE + "=" +
       StringHandler.nullIfZero(invoiceViewMillerDetailBean.getInvoiceLine()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
       SqlHandler.delimitString(invoiceViewMillerDetailBean.getPoNumber()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
       SqlHandler.delimitString(invoiceViewMillerDetailBean.getCatPartNo()) + "," +
     ATTRIBUTE_QUANTITY_DELIVERED + "=" +
      StringHandler.nullIfZero(invoiceViewMillerDetailBean.getQuantityDelivered()) + "," +
     ATTRIBUTE_DATE_DELIVERED + "=" +
      DateHandler.getOracleToDateFunction(invoiceViewMillerDetailBean.getDateDelivered()) + "," +
     ATTRIBUTE_QUANTITY_ORDERED + "=" +
      StringHandler.nullIfZero(invoiceViewMillerDetailBean.getQuantityOrdered()) + "," +
     ATTRIBUTE_TOTAL_AMOUNT + "=" +
       StringHandler.nullIfZero(invoiceViewMillerDetailBean.getTotalAmount()) + "," +
     ATTRIBUTE_EXT_PRICE + "=" +
       StringHandler.nullIfZero(invoiceViewMillerDetailBean.getExtPrice()) + "," +
     ATTRIBUTE_ADDITIONAL_CHARGES + "=" +
      StringHandler.nullIfZero(invoiceViewMillerDetailBean.getAdditionalCharges()) + "," +
     ATTRIBUTE_SERVICE_FEE + "=" +
       StringHandler.nullIfZero(invoiceViewMillerDetailBean.getServiceFee()) + "," +
     ATTRIBUTE_PART_DESCRIPTION + "=" +
      SqlHandler.delimitString(invoiceViewMillerDetailBean.getPartDescription()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoiceViewMillerDetailBean.getInvoiceDate()) + "," +
     ATTRIBUTE_INVOICE_UNIT_PRICE + "=" +
      StringHandler.nullIfZero(invoiceViewMillerDetailBean.getInvoiceUnitPrice()) + "," +
     ATTRIBUTE_PACKAGING + "=" +
       SqlHandler.delimitString(invoiceViewMillerDetailBean.getPackaging()) + "," +
     ATTRIBUTE_ORIGINAL_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewMillerDetailBean.getOriginalInvoice()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      invoiceViewMillerDetailBean.getInvoice();
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(BigDecimal invoice, BigDecimal invoicePeriod) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(invoice, invoicePeriod, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(BigDecimal invoice, BigDecimal invoicePeriod, Connection conn) throws
      BaseException {

    Collection invoiceViewMillerDetailBeanColl = new Vector();
/*
    String query = "select d.invoice, " +
        "d.invoice_line, " +
        "l.po_number,  " +
        "cat_part_no,  " +
        "sum(d.quantity) quantity_delivered, " +
        "d.date_delivered,  " +
        "r.quantity quantity_ordered, " +
        "l.total_amount,  " +
        "sum(net_amount-nvl(total_add_charge,0)) ext_price, " +
        "sum(nvl(total_add_charge,0)) additional_charges,  " +
        "l.service_fee,  " +
        "d.part_description,i.invoice_date, " +
        "invoice_unit_price,  " +
        "fx_kit_packaging(d.item_id) packaging, " +
        "i.original_invoice  " +
        "from invoice_line l,invoice_detail d,request_line_item r,invoice i " +
        "where invoice_period = " + invoicePeriod + " and " +
        "i.invoice = l.invoice and  " +
        "i.invoice = d.invoice and  " +
        "i.company_id = 'MILLER' and  " +
        "l.invoice_line = d.invoice_line and  " +
        "d.pr_number = r.pr_number and  " +
        "d.line_item = r.line_item and " +
        "i.invoice = " + invoice + " " +
        "group by d.invoice,d.invoice_line,l.po_number, " +
        "cat_part_no,d.date_delivered,  " +
        "r.quantity,  " +
        "invoice_unit_price, " +
        "l.total_amount,  " +
        "l.service_fee,  " +
        "d.part_description, " +
        "i.invoice_date,l.extended_price, " +
        "invoice_unit_price,  " +
        "fx_kit_packaging(d.item_id),i.original_invoice";

    String query = "select d.invoice,  " +
	   "d.invoice_line,  " +
	   "l.po_number,  " +
	   "cat_part_no,  " +
	   "sum(d.quantity) quantity_delivered,  " +
	   "d.date_delivered,  " +
	   //"sum(r.quantity) quantity_ordered,  " +
           "r.quantity quantity_ordered,  " +
	   "round(l.total_amount, 5) total_amount,  " +
	   "round(sum(net_amount-nvl(total_add_charge,0)), 5) ext_price,  " +
	   "round(sum(nvl(total_add_charge,0)), 5) additional_charges,  " +
	   "round(sum(nvl(l.service_fee,0)), 5) service_fee,  " +
	   "d.part_description,  " +
	   "i.invoice_date,  " +
	   "round(d.invoice_unit_price, 5) invoice_unit_price,  " +
	   "fx_kit_packaging(d.item_id) packaging,  " +
	   "i.original_invoice  " +
	   "from invoice_line l, " +
	   "invoice_detail d, " +
	   "request_line_item r, " +
	   "invoice i  " +
	   "where invoice_period = " + invoicePeriod + " and  " +
	  "i.invoice = l.invoice and  " +
	  "i.invoice = d.invoice and  " +
	  "i.company_id = 'MILLER' and  " +
	  "l.invoice_line = d.invoice_line and  " +
	  "d.pr_number = r.pr_number and  " +
	  "d.line_item = r.line_item and  " +
	  "i.invoice = " + invoice + " " +
          "group by d.invoice, " +
	  "d.invoice_line, " +
	  "l.po_number,  " +
	  "d.cat_part_no,  " +
	  "d.date_delivered,   " +
	  "invoice_unit_price,  " +
	  "l.total_amount,   " +
	  "d.part_description,  " +
	  "i.invoice_date, " +
	  "l.extended_price,  " +
	  "d.invoice_unit_price,  " +
	  "fx_kit_packaging(d.item_id),  " +
	  "i.original_invoice," +
          "r.quantity";
*/
      String query = "select d.invoice,  d.invoice_line,  l.po_number,  d.cat_part_no,  d.date_delivered, " + 
	   "sum( (select r.quantity " + 
	    	 "from request_line_item r " + 
			 "where d.pr_number = r.pr_number and  " + 
	  		  	 "  d.line_item = r.line_item )) quantity_ordered,  " + 
	   "d.part_description," + 
	   "i.invoice_date,  " + 
	   "round(d.invoice_unit_price, 5) invoice_unit_price,  " + 
	   "fx_kit_packaging(d.item_id) packaging,  " + 
	   "i.original_invoice,  " + 
	   "round(l.total_amount, 5) total_amount,  " + 
	   "sum(d.quantity) quantity_delivered,  " + 
   	   "round(sum(net_amount-nvl(total_add_charge,0)), 5) ext_price," + 
	   "round(sum(nvl(total_add_charge,0)), 5) additional_charges,  " + 
	   "round(sum(nvl(l.service_fee,0)), 5) service_fee from invoice_line l, " + 
           "invoice_detail d, invoice i where invoice_period = " + invoicePeriod + " and  " +
           "i.invoice = " + invoice + " and " +
           "i.invoice = l.invoice and  " + 
           "i.invoice = d.invoice and  " + 
           "i.company_id = 'MILLER' and  " + 
           "l.invoice_line = d.invoice_line  " + 
           "group by d.invoice, d.invoice_line, l.po_number,  d.cat_part_no,  d.date_delivered," + 
           "d.invoice_unit_price,  l.total_amount,   d.part_description,  i.invoice_date, " + 
	   "l.extended_price,  d.invoice_unit_price," + 
	   "fx_kit_packaging(d.item_id),  i.original_invoice";

    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    InvoiceAddChargeDetailBeanFactory factory = new InvoiceAddChargeDetailBeanFactory(getDbManager());
    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewMillerDetailBean invoiceViewMillerDetailBean = new
          InvoiceViewMillerDetailBean();
      load(dataSetRow, invoiceViewMillerDetailBean);
      invoiceViewMillerDetailBean.setAddChargeColl(
          factory.selectMiller(invoiceViewMillerDetailBean.getInvoice(), 
                               invoiceViewMillerDetailBean.getInvoiceLine(),
                               invoiceViewMillerDetailBean.getCatPartNo(),
                               invoiceViewMillerDetailBean.getDateDelivered(),
                               invoiceViewMillerDetailBean.getInvoiceUnitPrice(),
                               invoiceViewMillerDetailBean.getPartDescription()));
      invoiceViewMillerDetailBeanColl.add(invoiceViewMillerDetailBean);
    }

    return invoiceViewMillerDetailBeanColl;
  }

  public Collection selectCorrection(BigDecimal invoice, BigDecimal invoicePeriod) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectCorrection(invoice, invoicePeriod, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectCorrection(BigDecimal invoice, BigDecimal invoicePeriod, Connection conn) throws
      BaseException {

    Collection invoiceViewMillerDetailBeanColl = new Vector();
/*
    String query = "select d.invoice, " +
        "d.invoice_line,  " +
        "l.po_number,   " +
        "cat_part_no,   " +
        "round(sum(d.quantity), 5) quantity_delivered, " + 
        "d.date_delivered,   " +
        "r.quantity quantity_ordered, " + 
        "l.total_amount,   " +
        "round(sum(net_amount-nvl(total_add_charge,0)), 5) ext_price, " + 
        "round(sum(nvl(total_add_charge,0)), 5) additional_charges,   " +
        "round(l.service_fee, 5) service_fee,   " +
        "d.part_description,i.invoice_date, " + 
        "round(invoice_unit_price, 5) invoice_unit_price,   " +
        "fx_kit_packaging(d.item_id) packaging,  " +
        "i.original_invoice  " +
        "from invoice_line l,invoice_detail d,request_line_item r,invoice i " +
        "where invoice_period = " + invoicePeriod + " " +
        "and i.invoice = l.invoice and i.invoice = d.invoice and " +
        "i.company_id = 'MILLER' and l.invoice_line = d.invoice_line " +
        "and d.pr_number = r.pr_number and d.line_item = r.line_item " +
        "and d.invoice = " + invoice + " " +
        "group by d.invoice,d.invoice_line,l.po_number, " +
        "cat_part_no, " +
        "d.date_delivered, " +
        "r.quantity, " +
        "invoice_unit_price, " +
        "l.total_amount, " +
        "l.service_fee, " +
        "d.part_description, " +
        "i.invoice_date, " +
        "l.extended_price, " +
        "invoice_unit_price, " +
        "fx_kit_packaging(d.item_id),rtrim(i.facility_id), " +
        "i.original_invoice,d.issue_cost_revision " +
        "order by d.invoice,d.issue_cost_revision"; 
*/
    String query = "select d.invoice,  d.invoice_line,  l.po_number,  d.cat_part_no,  d.date_delivered, " +
"sum( (select r.quantity*nvl(r.unit_of_sale_quantity_per_each,1) " +
	"from request_line_item r  " +
	"where d.pr_number = r.pr_number and     " +
		"d.line_item = r.line_item )) quantity_ordered, d.part_description,i.invoice_date, " +
"round(d.invoice_unit_price/nvl(d.unit_of_sale_quantity_per_each,1), 5) invoice_unit_price, " +
"fx_kit_packaging(d.item_id) packaging, i.original_invoice, round(l.total_amount, 5) total_amount, " +
"sum(d.quantity*nvl(d.unit_of_sale_quantity_per_each,1)) quantity_delivered, d.unit_of_sale,  " +
"round(sum(net_amount-nvl(total_add_charge,0)), 5) ext_price, round(sum(nvl(total_add_charge,0)), 5) additional_charges,   " +
"round(sum(nvl(l.service_fee,0)), 5) service_fee  " +
"from invoice_line l, invoice_detail d, invoice i  " +
"where invoice_period =  " + invoicePeriod + " and " +
	"i.invoice = " + invoice + " and " +
	"i.invoice = l.invoice and   " +
	"i.invoice = d.invoice and  " +
	"i.company_id = 'MILLER' and   " +
	"l.invoice_line = d.invoice_line " +
"group by d.invoice, d.invoice_line, l.po_number,  d.cat_part_no,  d.date_delivered,  " +
"d.invoice_unit_price/nvl(d.unit_of_sale_quantity_per_each,1), " +
"l.total_amount, d.unit_of_sale, d.part_description,  i.invoice_date, l.extended_price,  d.invoice_unit_price, " +
"fx_kit_packaging(d.item_id),  i.original_invoice " +
"order by d.invoice";
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    InvoiceAddChargeDetailBeanFactory factory = new InvoiceAddChargeDetailBeanFactory(getDbManager());
    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewMillerDetailBean invoiceViewMillerDetailBean = new
          InvoiceViewMillerDetailBean();
      load(dataSetRow, invoiceViewMillerDetailBean);
      invoiceViewMillerDetailBean.setAddChargeColl(factory.selectMiller(invoiceViewMillerDetailBean.getInvoice(), 
                               invoiceViewMillerDetailBean.getInvoiceLine(),
                               invoiceViewMillerDetailBean.getCatPartNo(),
                               invoiceViewMillerDetailBean.getDateDelivered(),
                               invoiceViewMillerDetailBean.getInvoiceUnitPrice(),
                               invoiceViewMillerDetailBean.getPartDescription()));
      invoiceViewMillerDetailBeanColl.add(invoiceViewMillerDetailBean);
    }

    return invoiceViewMillerDetailBeanColl;
  }
}