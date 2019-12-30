package com.tcmis.internal.invoice.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
import com.tcmis.internal.invoice.beans.InvoiceViewSwaOvBean;
import com.tcmis.internal.invoice.factory.InvoiceAddChargeDetailBeanFactory;

/******************************************************************************
 * CLASSNAME: InvoiceViewSwaOvBeanFactory <br>
 * @version: 1.0, Mar 22, 2005 <br>
 *****************************************************************************/

public class InvoiceViewSwaOvBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_LINE = "INVOICE_LINE";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_INVOICE_QUANTITY = "INVOICE_QUANTITY";
  public String ATTRIBUTE_QUANTITY_ORDERED = "QUANTITY_ORDERED";
  public String ATTRIBUTE_INVOICE_UNIT_PRICE = "INVOICE_UNIT_PRICE";
  public String ATTRIBUTE_UNIT_DISCOUNT = "UNIT_DISCOUNT";
  public String ATTRIBUTE_EXTENDED_PRICE = "EXTENDED_PRICE";
  public String ATTRIBUTE_TOTAL_DISCOUNT = "TOTAL_DISCOUNT";
  public String ATTRIBUTE_NET_AMOUNT = "NET_AMOUNT";
  public String ATTRIBUTE_TOTAL_ADDITIONAL_CHARGE = "TOTAL_ADDITIONAL_CHARGE";
  public String ATTRIBUTE_SERVICE_FEE = "SERVICE_FEE";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
  public String ATTRIBUTE_REQUESTOR_NAME = "REQUESTOR_NAME";
  public String ATTRIBUTE_HAAS_REQUEST_NUMBER = "HAAS_REQUEST_NUMBER";
  public String ATTRIBUTE_FINAL_EXTENDED_PRICE = "FINAL_EXTENDED_PRICE";
  public String ATTRIBUTE_TOTAL_AMOUNT = "TOTAL_AMOUNT";
  public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
  public String ATTRIBUTE_MANUFACTURER_DESC = "MANUFACTURER_DESC";
  public String ATTRIBUTE_INVOICE_SUPPLIER = "INVOICE_SUPPLIER";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_DETAIL = "DETAIL";

  public String ATTRIBUTE_ISSUE_ID = "DETAIL";
  //table name
  public String TABLE = "INVOICE_VIEW_SWA_OV";

  //constructor
  public InvoiceViewSwaOvBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("quantityOrdered")) {
      return ATTRIBUTE_QUANTITY_ORDERED;
    }
    else if (attributeName.equals("invoiceUnitPrice")) {
      return ATTRIBUTE_INVOICE_UNIT_PRICE;
    }
    else if (attributeName.equals("unitDiscount")) {
      return ATTRIBUTE_UNIT_DISCOUNT;
    }
    else if (attributeName.equals("extendedPrice")) {
      return ATTRIBUTE_EXTENDED_PRICE;
    }
    else if (attributeName.equals("totalDiscount")) {
      return ATTRIBUTE_TOTAL_DISCOUNT;
    }
    else if (attributeName.equals("netAmount")) {
      return ATTRIBUTE_NET_AMOUNT;
    }
    else if (attributeName.equals("totalAdditionalCharge")) {
      return ATTRIBUTE_TOTAL_ADDITIONAL_CHARGE;
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
    else if (attributeName.equals("haasRequestNumber")) {
      return ATTRIBUTE_HAAS_REQUEST_NUMBER;
    }
    else if (attributeName.equals("finalExtendedPrice")) {
      return ATTRIBUTE_FINAL_EXTENDED_PRICE;
    }
    else if (attributeName.equals("totalAmount")) {
      return ATTRIBUTE_TOTAL_AMOUNT;
    }
    else if (attributeName.equals("invoiceAmount")) {
      return ATTRIBUTE_INVOICE_AMOUNT;
    }
    else if (attributeName.equals("manufacturerDesc")) {
      return ATTRIBUTE_MANUFACTURER_DESC;
    }
    else if (attributeName.equals("invoiceSupplier")) {
      return ATTRIBUTE_INVOICE_SUPPLIER;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else if (attributeName.equals("detail")) {
      return ATTRIBUTE_DETAIL;
    }
    else if (attributeName.equals("issueId")) {
      return ATTRIBUTE_ISSUE_ID;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceViewSwaOvBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceViewSwaOvBean invoiceViewSwaOvBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewSwaOvBean.getInvoice());
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
       public int delete(InvoiceViewSwaOvBean invoiceViewSwaOvBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewSwaOvBean.getInvoice());
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
   public int insert(InvoiceViewSwaOvBean invoiceViewSwaOvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceViewSwaOvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int insert(InvoiceViewSwaOvBean invoiceViewSwaOvBean, Connection conn)
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
     ATTRIBUTE_QUANTITY_ORDERED + "," +
     ATTRIBUTE_INVOICE_UNIT_PRICE + "," +
     ATTRIBUTE_UNIT_DISCOUNT + "," +
     ATTRIBUTE_EXTENDED_PRICE + "," +
     ATTRIBUTE_TOTAL_DISCOUNT + "," +
     ATTRIBUTE_NET_AMOUNT + "," +
     ATTRIBUTE_TOTAL_ADDITIONAL_CHARGE + "," +
     ATTRIBUTE_SERVICE_FEE + "," +
     ATTRIBUTE_PART_DESCRIPTION + "," +
     ATTRIBUTE_REQUESTOR_NAME + "," +
     ATTRIBUTE_HAAS_REQUEST_NUMBER + "," +
     ATTRIBUTE_FINAL_EXTENDED_PRICE + "," +
     ATTRIBUTE_TOTAL_AMOUNT + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "," +
     ATTRIBUTE_MANUFACTURER_DESC + "," +
     ATTRIBUTE_INVOICE_SUPPLIER + "," +
     ATTRIBUTE_INVOICE_DATE + "," +
     ATTRIBUTE_DETAIL + ")" +
   values (
     StringHandler.nullIfZero(invoiceViewSwaOvBean.getInvoice()) + "," +
     StringHandler.nullIfZero(invoiceViewSwaOvBean.getInvoiceLine()) + "," +
     SqlHandler.delimitString(invoiceViewSwaOvBean.getPoNumber()) + "," +
     StringHandler.nullIfZero(invoiceViewSwaOvBean.getInvoicePeriod()) + "," +
     SqlHandler.delimitString(invoiceViewSwaOvBean.getCatPartNo()) + "," +
       StringHandler.nullIfZero(invoiceViewSwaOvBean.getInvoiceQuantity()) + "," +
       StringHandler.nullIfZero(invoiceViewSwaOvBean.getQuantityOrdered()) + "," +
       StringHandler.nullIfZero(invoiceViewSwaOvBean.getInvoiceUnitPrice()) + "," +
     StringHandler.nullIfZero(invoiceViewSwaOvBean.getUnitDiscount()) + "," +
     StringHandler.nullIfZero(invoiceViewSwaOvBean.getExtendedPrice()) + "," +
     StringHandler.nullIfZero(invoiceViewSwaOvBean.getTotalDiscount()) + "," +
     StringHandler.nullIfZero(invoiceViewSwaOvBean.getNetAmount()) + "," +
     StringHandler.nullIfZero(invoiceViewSwaOvBean.getTotalAdditionalCharge()) + "," +
     StringHandler.nullIfZero(invoiceViewSwaOvBean.getServiceFee()) + "," +
       SqlHandler.delimitString(invoiceViewSwaOvBean.getPartDescription()) + "," +
     SqlHandler.delimitString(invoiceViewSwaOvBean.getRequestorName()) + "," +
       SqlHandler.delimitString(invoiceViewSwaOvBean.getHaasRequestNumber()) + "," +
       StringHandler.nullIfZero(invoiceViewSwaOvBean.getFinalExtendedPrice()) + "," +
     StringHandler.nullIfZero(invoiceViewSwaOvBean.getTotalAmount()) + "," +
     StringHandler.nullIfZero(invoiceViewSwaOvBean.getInvoiceAmount()) + "," +
       SqlHandler.delimitString(invoiceViewSwaOvBean.getManufacturerDesc()) + "," +
       SqlHandler.delimitString(invoiceViewSwaOvBean.getInvoiceSupplier()) + "," +
     DateHandler.getOracleToDateFunction(invoiceViewSwaOvBean.getInvoiceDate()) + "," +
     SqlHandler.delimitString(invoiceViewSwaOvBean.getDetail()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceViewSwaOvBean invoiceViewSwaOvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceViewSwaOvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int update(InvoiceViewSwaOvBean invoiceViewSwaOvBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewSwaOvBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_LINE + "=" +
      StringHandler.nullIfZero(invoiceViewSwaOvBean.getInvoiceLine()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
      SqlHandler.delimitString(invoiceViewSwaOvBean.getPoNumber()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoiceViewSwaOvBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
      SqlHandler.delimitString(invoiceViewSwaOvBean.getCatPartNo()) + "," +
     ATTRIBUTE_INVOICE_QUANTITY + "=" +
       StringHandler.nullIfZero(invoiceViewSwaOvBean.getInvoiceQuantity()) + "," +
     ATTRIBUTE_QUANTITY_ORDERED + "=" +
       StringHandler.nullIfZero(invoiceViewSwaOvBean.getQuantityOrdered()) + "," +
     ATTRIBUTE_INVOICE_UNIT_PRICE + "=" +
       StringHandler.nullIfZero(invoiceViewSwaOvBean.getInvoiceUnitPrice()) + "," +
     ATTRIBUTE_UNIT_DISCOUNT + "=" +
      StringHandler.nullIfZero(invoiceViewSwaOvBean.getUnitDiscount()) + "," +
     ATTRIBUTE_EXTENDED_PRICE + "=" +
      StringHandler.nullIfZero(invoiceViewSwaOvBean.getExtendedPrice()) + "," +
     ATTRIBUTE_TOTAL_DISCOUNT + "=" +
      StringHandler.nullIfZero(invoiceViewSwaOvBean.getTotalDiscount()) + "," +
     ATTRIBUTE_NET_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceViewSwaOvBean.getNetAmount()) + "," +
     ATTRIBUTE_TOTAL_ADDITIONAL_CHARGE + "=" +
      StringHandler.nullIfZero(invoiceViewSwaOvBean.getTotalAdditionalCharge()) + "," +
     ATTRIBUTE_SERVICE_FEE + "=" +
      StringHandler.nullIfZero(invoiceViewSwaOvBean.getServiceFee()) + "," +
     ATTRIBUTE_PART_DESCRIPTION + "=" +
       SqlHandler.delimitString(invoiceViewSwaOvBean.getPartDescription()) + "," +
     ATTRIBUTE_REQUESTOR_NAME + "=" +
      SqlHandler.delimitString(invoiceViewSwaOvBean.getRequestorName()) + "," +
     ATTRIBUTE_HAAS_REQUEST_NUMBER + "=" +
       SqlHandler.delimitString(invoiceViewSwaOvBean.getHaasRequestNumber()) + "," +
     ATTRIBUTE_FINAL_EXTENDED_PRICE + "=" +
       StringHandler.nullIfZero(invoiceViewSwaOvBean.getFinalExtendedPrice()) + "," +
     ATTRIBUTE_TOTAL_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceViewSwaOvBean.getTotalAmount()) + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceViewSwaOvBean.getInvoiceAmount()) + "," +
     ATTRIBUTE_MANUFACTURER_DESC + "=" +
       SqlHandler.delimitString(invoiceViewSwaOvBean.getManufacturerDesc()) + "," +
     ATTRIBUTE_INVOICE_SUPPLIER + "=" +
       SqlHandler.delimitString(invoiceViewSwaOvBean.getInvoiceSupplier()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoiceViewSwaOvBean.getInvoiceDate()) + "," +
     ATTRIBUTE_DETAIL + "=" +
      SqlHandler.delimitString(invoiceViewSwaOvBean.getDetail()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewSwaOvBean.getInvoice());
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(BigDecimal invoicePeriod, BigDecimal invoiceNumber) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(invoicePeriod, invoiceNumber, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(BigDecimal invoicePeriod, BigDecimal invoiceNumber, Connection conn) throws
      BaseException {

    Collection invoiceViewSwaOvBeanColl = new Vector();

    String query = "select d.invoice,d.invoice_line,l.po_number, " +
        "d.cat_part_no,sum(d.quantity) invoice_quantity, " +
        "r.quantity quantity_ordered, " +
        "round(d.invoice_unit_price, 5) invoice_unit_price, " +
        "round(nvl(unit_rebate,0),5 ) unit_discount, " +
        "round(l.extended_price, 5) extended_price, " +
        "round(nvl(l.rebate,0), 5) total_discount, " +
        "round(sum(net_amount), 5) net_amount, " +
        "round(sum(nvl(total_add_charge,0)), 5) total_additional_charge, " +
        "round(l.service_fee, 5) service_fee, " +
        "d.part_description,d.requestor_name, " +
        "(d.pr_number || '-' || d.line_item) haas_request_number, " +
        "round(l.extended_price-l.rebate, 5) final_extended_price, " +
        "round(l.extended_price-l.rebate+l.service_fee, 5) total_amount, " +
        "round(l.extended_price-l.rebate+l.service_fee+nvl(l.additional_charges,0), 5) invoice_amount, " +
        "fx_mfg_desc(d.item_id) manufacturer_desc, " +
        "fx_swa_invoice_supplier(i.invoice) invoice_supplier,i.invoice_date, d.issue_id " +
        "from invoice_line l,invoice_detail d,request_line_item r,invoice i " +
        "where ";
        if(invoiceNumber != null) {
          query = query + " i.invoice = " + invoiceNumber + " ";
        }
        query = query + "and i.invoice not in " +
        "(select d.invoice " +
        "from invoice i,invoice_detail d where " +
        "i.invoice = d.invoice and " +
        "i.company_id = 'SWA' and i.invoice_period =  " + invoicePeriod + " ";
        if(invoiceNumber != null) {
          query = query + " and i.invoice = " + invoiceNumber + " ";
        }
        query = query + "group by d.invoice,d.issue_id having count(distinct quantity) > 1) " +
        "and i.invoice = l.invoice and i.invoice = d.invoice and " +
        "i.company_id = 'SWA' and l.invoice_line = d.invoice_line " +
        "and d.pr_number = r.pr_number and d.line_item = r.line_item " +
        "and d.invoice not in (0) " +
        "group by d.invoice,d.invoice_line,l.po_number, " +
        "d.cat_part_no, " +
        "r.quantity, " +
        "d.invoice_unit_price, " +
        "nvl(unit_rebate,0), " +
        "l.extended_price, " +
        "nvl(l.rebate,0), " +
        "l.service_fee, " +
        "d.part_description,d.requestor_name, " +
        "(d.pr_number || '-' || d.line_item), " +
        "l.extended_price-l.rebate, " +
        "l.extended_price-l.rebate+l.service_fee, " +
        "l.extended_price-l.rebate+l.service_fee+nvl(l.additional_charges,0), " +
        "fx_mfg_desc(d.item_id), " +
        "decode(l.extended_price-l.rebate,0,0,round(l.service_fee*100/(l.extended_price-l.rebate),2)), " +
        "fx_swa_invoice_supplier(i.invoice),i.invoice_date, " +
        "d.invoice_unit_price, " +
        "d.issue_id";
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();
    InvoiceAddChargeDetailBeanFactory fac = new InvoiceAddChargeDetailBeanFactory(getDbManager());
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewSwaOvBean invoiceViewSwaOvBean = new InvoiceViewSwaOvBean();
      load(dataSetRow, invoiceViewSwaOvBean);
      SearchCriteria criteria = new SearchCriteria();
      criteria.addCriterion("invoice", SearchCriterion.EQUALS, invoiceViewSwaOvBean.getInvoice().toString());
      criteria.addCriterion("invoiceLine", SearchCriterion.EQUALS, invoiceViewSwaOvBean.getInvoiceLine().toString());
      invoiceViewSwaOvBean.setDetail(fac.selectSwa(criteria));
      invoiceViewSwaOvBeanColl.add(invoiceViewSwaOvBean);
    }

    return invoiceViewSwaOvBeanColl;
  }

  public Collection selectCorrectionDetail(BigDecimal invoicePeriod) throws
      BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectCorrectionDetail(invoicePeriod, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectCorrectionDetail(BigDecimal invoice,
                                           Connection conn) throws
      BaseException {

    Collection invoiceViewSwaOvBeanColl = new Vector();

    String query = "select distinct d.invoice,d.invoice_line,l.po_number, " +
        "cat_part_no,d.quantity invoice_quantity, " +
        "r.quantity quantity_ordered, " +
        "round(invoice_unit_price, 5) invoice_unit_price, " +
        "round(unit_rebate, 5) unit_rebate, " +
        "round(d.net_amount-d.service_fee-nvl(d.total_add_charge,0), 5) extended_price, " +
        "round(l.rebate, 5) rebate, " +
        "round(net_amount, 5) net_amount, " +
        "round(nvl(total_add_charge,0), 5) total_additional_charge, " +
        "round(d.service_fee, 5) service_fee, " +
        "d.part_description,d.requestor_name, " +
        "rtrim(d.pr_number || '-' || d.line_item) haas_request_number, " +
        "round(l.extended_price-l.rebate+l.service_fee+nvl(l.additional_charges,0), 5) invoice_amount, " +
        "fx_mfg_desc(d.item_id) manufacturer_desc, " +
        "fx_swa_invoice_supplier(i.invoice) invoice_supplier,issue_id, " +
        "i.invoice_date " +
        "from invoice_line l,invoice_detail d,request_line_item r,invoice i " +
        "where i.invoice = " + invoice + " " +
        "and i.invoice = l.invoice and i.invoice = d.invoice and " +
        "i.company_id = 'SWA' and l.invoice_line = d.invoice_line " +
        "and d.pr_number = r.pr_number and d.line_item = r.line_item " +
        "and d.invoice in  " +
        "  (select d.invoice " +
        "  from invoice i,invoice_detail d where " +
        "  i.invoice = d.invoice and " +
        "  i.company_id = 'SWA' and i.invoice = " + invoice + " " +
        "  group by d.invoice,d.issue_id having count(distinct quantity) > 1) " +
        "order by invoice,to_char(d.quantity,'999999') desc";
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();
    InvoiceAddChargeDetailBeanFactory fac = new InvoiceAddChargeDetailBeanFactory(getDbManager());
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewSwaOvBean invoiceViewSwaOvBean = new InvoiceViewSwaOvBean();
      load(dataSetRow, invoiceViewSwaOvBean);
      invoiceViewSwaOvBean.setDetail(fac.selectSwaCorrection(invoiceViewSwaOvBean.getInvoice(),
          invoiceViewSwaOvBean.getInvoiceLine(), invoiceViewSwaOvBean.getIssueId()));

      invoiceViewSwaOvBeanColl.add(invoiceViewSwaOvBean);
    }

    return invoiceViewSwaOvBeanColl;
  }

  public Collection selectCorrection(BigDecimal invoicePeriod, BigDecimal invoiceNumber) throws
      BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectCorrection(invoicePeriod, invoiceNumber, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectCorrection(BigDecimal invoicePeriod, BigDecimal invoiceNumber, Connection conn) throws
      BaseException {

    Collection invoiceViewSwaOvBeanColl = new Vector();

    String query = "select unique d.invoice " +
        "from invoice i,invoice_detail d where " +
        "i.invoice = d.invoice and " +
        "i.company_id = 'SWA' and i.invoice_period = " + invoicePeriod + " ";
    if(invoiceNumber != null) {
      query = query + " and i.invoice = " + invoiceNumber + " ";
    }
        query = query + "group by d.invoice,d.issue_id having count(distinct quantity) > 1";
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewSwaOvBean invoiceViewSwaOvBean = new InvoiceViewSwaOvBean();
      load(dataSetRow, invoiceViewSwaOvBean);
      invoiceViewSwaOvBeanColl.add(invoiceViewSwaOvBean);
    }

    return invoiceViewSwaOvBeanColl;
  }

  public Collection selectObject(SearchCriteria criteria) throws BaseException,
      Exception {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      java.util.Map map = connection.getTypeMap();
      map.put("INVOICE_VIEW_SWA_OBJ",
              Class.forName(
          "com.tcmis.internal.invoice.beans.InvoiceViewMillerOvBean"));
      map.put("INVOICE_ADD_CHARGE_DETAIL_OBJ",
              Class.forName(
          "com.tcmis.internal.invoice.beans.InvoiceAddChargeDetailBean"));

      c = selectObject(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectObject(SearchCriteria criteria, Connection conn) throws
      BaseException, Exception {

    Collection invoiceViewSwaBeanColl = new Vector();

    String query = "select value(p) from " + TABLE + " p " +
        getWhereClause(criteria);
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    Statement st = conn.createStatement();
    ResultSet rs = st.executeQuery(query);
    while (rs.next()) {
      InvoiceViewSwaOvBean b = (InvoiceViewSwaOvBean) rs.getObject(1);
      invoiceViewSwaBeanColl.add(b);
    }
    rs.close();
    st.close();
    return invoiceViewSwaBeanColl;
  }

}