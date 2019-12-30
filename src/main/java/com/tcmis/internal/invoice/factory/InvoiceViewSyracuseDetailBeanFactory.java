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
import com.tcmis.internal.invoice.beans.InvoiceViewSyracuseDetailBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewSyracuseDetailBeanFactory <br>
 * @version: 1.0, May 26, 2005 <br>
 *****************************************************************************/

public class InvoiceViewSyracuseDetailBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_LINE = "INVOICE_LINE";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_PART_NUMBER = "PART_NUMBER";
  public String ATTRIBUTE_QUANTITY = "QUANTITY";
  public String ATTRIBUTE_EXT_PRICE = "EXT_PRICE";
  public String ATTRIBUTE_NET_LINE_AMOUNT = "NET_LINE_AMOUNT";
  public String ATTRIBUTE_ADDITIONAL_CHARGES = "ADDITIONAL_CHARGES";
  public String ATTRIBUTE_SERVICE_FEE = "SERVICE_FEE";
  public String ATTRIBUTE_MR_NUMBER = "MR_NUMBER";
  public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
  public String ATTRIBUTE_INVOICE_UNIT_PRICE = "INVOICE_UNIT_PRICE";
  public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
  public String ATTRIBUTE_SALES_TAX = "SALES_TAX";
  public String ATTRIBUTE_PERCENT_SPLIT_CHARGE = "PERCENT_SPLIT_CHARGE";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";

  //table name
  public String TABLE = "INVOICE_VIEW_SYRACUSE_DETAIL";

  //constructor
  public InvoiceViewSyracuseDetailBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("partNumber")) {
      return ATTRIBUTE_PART_NUMBER;
    }
    else if (attributeName.equals("quantity")) {
      return ATTRIBUTE_QUANTITY;
    }
    else if (attributeName.equals("extPrice")) {
      return ATTRIBUTE_EXT_PRICE;
    }
    else if (attributeName.equals("netLineAmount")) {
      return ATTRIBUTE_NET_LINE_AMOUNT;
    }
    else if (attributeName.equals("additionalCharges")) {
      return ATTRIBUTE_ADDITIONAL_CHARGES;
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
    else if (attributeName.equals("invoiceUnitPrice")) {
      return ATTRIBUTE_INVOICE_UNIT_PRICE;
    }
    else if (attributeName.equals("accountNumber")) {
      return ATTRIBUTE_ACCOUNT_NUMBER;
    }
    else if (attributeName.equals("salesTax")) {
      return ATTRIBUTE_SALES_TAX;
    }
    else if (attributeName.equals("percentSplitCharge")) {
      return ATTRIBUTE_PERCENT_SPLIT_CHARGE;
    }
    else if (attributeName.equals("partDescription")) {
      return ATTRIBUTE_PART_DESCRIPTION;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceViewSyracuseDetailBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceViewSyracuseDetailBean invoiceViewSyracuseDetailBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewSyracuseDetailBean.getInvoice());
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
   public int delete(InvoiceViewSyracuseDetailBean invoiceViewSyracuseDetailBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewSyracuseDetailBean.getInvoice());
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
   public int insert(InvoiceViewSyracuseDetailBean invoiceViewSyracuseDetailBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceViewSyracuseDetailBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceViewSyracuseDetailBean invoiceViewSyracuseDetailBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_LINE + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_PART_NUMBER + "," +
     ATTRIBUTE_QUANTITY + "," +
     ATTRIBUTE_EXT_PRICE + "," +
     ATTRIBUTE_NET_LINE_AMOUNT + "," +
     ATTRIBUTE_ADDITIONAL_CHARGES + "," +
     ATTRIBUTE_SERVICE_FEE + "," +
     ATTRIBUTE_MR_NUMBER + "," +
     ATTRIBUTE_DATE_DELIVERED + "," +
     ATTRIBUTE_INVOICE_UNIT_PRICE + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "," +
     ATTRIBUTE_SALES_TAX + ")" +
     " values (" +
     invoiceViewSyracuseDetailBean.getInvoice() + "," +
     invoiceViewSyracuseDetailBean.getInvoiceLine() + "," +
     SqlHandler.delimitString(invoiceViewSyracuseDetailBean.getPoNumber()) + "," +
     SqlHandler.delimitString(invoiceViewSyracuseDetailBean.getPartNumber()) + "," +
     invoiceViewSyracuseDetailBean.getQuantity() + "," +
     invoiceViewSyracuseDetailBean.getExtPrice() + "," +
     invoiceViewSyracuseDetailBean.getNetLineAmount() + "," +
     invoiceViewSyracuseDetailBean.getAdditionalCharges() + "," +
     invoiceViewSyracuseDetailBean.getServiceFee() + "," +
     SqlHandler.delimitString(invoiceViewSyracuseDetailBean.getMrNumber()) + "," +
     DateHandler.getOracleToDateFunction(invoiceViewSyracuseDetailBean.getDateDelivered()) + "," +
     invoiceViewSyracuseDetailBean.getInvoiceUnitPrice() + "," +
     SqlHandler.delimitString(invoiceViewSyracuseDetailBean.getAccountNumber()) + "," +
     invoiceViewSyracuseDetailBean.getSalesTax() + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceViewSyracuseDetailBean invoiceViewSyracuseDetailBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceViewSyracuseDetailBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceViewSyracuseDetailBean invoiceViewSyracuseDetailBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewSyracuseDetailBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_LINE + "=" +
      StringHandler.nullIfZero(invoiceViewSyracuseDetailBean.getInvoiceLine()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
      SqlHandler.delimitString(invoiceViewSyracuseDetailBean.getPoNumber()) + "," +
     ATTRIBUTE_PART_NUMBER + "=" +
      SqlHandler.delimitString(invoiceViewSyracuseDetailBean.getPartNumber()) + "," +
     ATTRIBUTE_QUANTITY + "=" +
      StringHandler.nullIfZero(invoiceViewSyracuseDetailBean.getQuantity()) + "," +
     ATTRIBUTE_EXT_PRICE + "=" +
      StringHandler.nullIfZero(invoiceViewSyracuseDetailBean.getExtPrice()) + "," +
     ATTRIBUTE_NET_LINE_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceViewSyracuseDetailBean.getNetLineAmount()) + "," +
     ATTRIBUTE_ADDITIONAL_CHARGES + "=" +
       StringHandler.nullIfZero(invoiceViewSyracuseDetailBean.getAdditionalCharges()) + "," +
     ATTRIBUTE_SERVICE_FEE + "=" +
      StringHandler.nullIfZero(invoiceViewSyracuseDetailBean.getServiceFee()) + "," +
     ATTRIBUTE_MR_NUMBER + "=" +
      SqlHandler.delimitString(invoiceViewSyracuseDetailBean.getMrNumber()) + "," +
     ATTRIBUTE_DATE_DELIVERED + "=" +
      DateHandler.getOracleToDateFunction(invoiceViewSyracuseDetailBean.getDateDelivered()) + "," +
     ATTRIBUTE_INVOICE_UNIT_PRICE + "=" +
       StringHandler.nullIfZero(invoiceViewSyracuseDetailBean.getInvoiceUnitPrice()) + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "=" +
      SqlHandler.delimitString(invoiceViewSyracuseDetailBean.getAccountNumber()) + "," +
     ATTRIBUTE_SALES_TAX + "=" +
      StringHandler.nullIfZero(invoiceViewSyracuseDetailBean.getSalesTax()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      invoiceViewSyracuseDetailBean.getInvoice();
    return new SqlManager().update(conn, query);
   }
   */

  //select
  
public Collection select(BigDecimal invoice, String poNumber) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(invoice, poNumber, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(BigDecimal invoice, String poNumber, Connection conn) throws BaseException {

    Collection invoiceViewSyracuseDetailBeanColl = new Vector();
/*
    String query = "select d.invoice,d.invoice_line,l.po_number, " +
        "decode(d.cat_part_no,'TCM0342',d.part_description,d.cat_part_no) part_number, " +
        "sum(d.quantity) quantity, " +
        "sum(d.quantity*d.invoice_unit_price*nvl(percent_split_charge,1)) ext_price, " +
        "sum(d.net_amount) net_line_amount, " +
        "sum(nvl(d.total_add_charge,0)*nvl(percent_split_charge,1)) additional_charges, " +
        "sum(d.service_fee*nvl(percent_split_charge,1)) service_fee, " +
        "(d.pr_number || '-' || d.line_item) mr_number, " +
        "d.date_delivered, " +
        "d.invoice_unit_price, " +
        "l.account_number, " +
        "percent_split_charge, " +
        "sum(nvl(d.total_sales_tax,0)*nvl(percent_split_charge,1)) sales_tax " +
*/
    String query = "select d.invoice,d.invoice_line,l.po_number, " + 
        "decode(d.cat_part_no,'TCM0342',d.part_description,d.cat_part_no) part_number,  " +
        "d.part_description, " +
        "sum(d.quantity) quantity,  " +
        "round(sum(d.quantity*d.invoice_unit_price), 5) ext_price,  " +
        "round(sum(d.net_amount),5) net_line_amount,  " +
        "round(sum(nvl(d.total_add_charge,0)),5) additional_charges,  " +
	"round(sum(d.service_fee), 5) service_fee, " +
	"percent_split_charge, " +
        "(d.pr_number || '-' || d.line_item) mr_number,  " +
        "d.date_delivered,  " +
        "round(d.invoice_unit_price, 5) invoice_unit_price,  " +
        "l.account_number,  " +
        "round(sum(nvl(d.total_sales_tax,0)), 5) sales_tax " +
        "from invoice_line l,invoice_detail d,invoice i " +
        "where i.invoice = " + invoice + " and l.po_number = " + 
        SqlHandler.delimitString(poNumber)  + " " +
        "and i.invoice = l.invoice and i.invoice = d.invoice and " +
        "i.invoice_group = 'SYR' and l.invoice_line = d.invoice_line " +
        "group by d.invoice,d.invoice_line,l.po_number, " +
        "decode(d.cat_part_no,'TCM0342',d.part_description,d.cat_part_no),nvl(percent_split_charge,1), " +
        "d.part_description, " +
        "(d.pr_number || '-' || d.line_item), " +
        "d.date_delivered, " +
        "d.invoice_unit_price, " +
        "nvl(d.unit_rebate,0), " +
        "l.account_number, " +
        "percent_split_charge " +
        "order by d.invoice_line,d.date_delivered";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewSyracuseDetailBean invoiceViewSyracuseDetailBean = new
          InvoiceViewSyracuseDetailBean();
      load(dataSetRow, invoiceViewSyracuseDetailBean);
      invoiceViewSyracuseDetailBeanColl.add(invoiceViewSyracuseDetailBean);
    }

    return invoiceViewSyracuseDetailBeanColl;
  }
}