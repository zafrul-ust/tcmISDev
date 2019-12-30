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
import com.tcmis.internal.invoice.beans.InvoiceViewL3DetailBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewL3DetailBeanFactory <br>
 * @version: 1.0, May 26, 2005 <br>
 *****************************************************************************/

public class InvoiceViewL3DetailBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_LINE = "INVOICE_LINE";
  public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_QUANTITY = "QUANTITY";
  public String ATTRIBUTE_TOTAL_PRICE = "TOTAL_PRICE";
  public String ATTRIBUTE_TOTAL_REBATE = "TOTAL_REBATE";
  public String ATTRIBUTE_NET_AMOUNT = "NET_AMOUNT";
  public String ATTRIBUTE_TOTAL_ADD_CHARGE = "TOTAL_ADD_CHARGE";
  public String ATTRIBUTE_SERVICE_FEE = "SERVICE_FEE";
  public String ATTRIBUTE_MR_NUMBER = "MR_NUMBER";
  public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
  public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
  public String ATTRIBUTE_INVOICE_UNIT_PRICE = "INVOICE_UNIT_PRICE";
  public String ATTRIBUTE_UNIT_REBATE = "UNIT_REBATE";
  public String ATTRIBUTE_PERCENT_SPLIT_CHARGE = "PERCENT_SPLIT_CHARGE";
  public String ATTRIBUTE_DESCRIPTION = "DESCRIPTION";

  //table name
  public String TABLE = "INVOICE_VIEW_L3_DETAIL";

  //constructor
  public InvoiceViewL3DetailBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("accountNumber")) {
      return ATTRIBUTE_ACCOUNT_NUMBER;
    }
    else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("quantity")) {
      return ATTRIBUTE_QUANTITY;
    }
    else if (attributeName.equals("totalPrice")) {
      return ATTRIBUTE_TOTAL_PRICE;
    }
    else if (attributeName.equals("totalRebate")) {
      return ATTRIBUTE_TOTAL_REBATE;
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
    else if (attributeName.equals("percentSplitCharge")) {
      return ATTRIBUTE_PERCENT_SPLIT_CHARGE;
    }
    else if (attributeName.equals("description")) {
      return ATTRIBUTE_DESCRIPTION;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceViewL3DetailBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceViewL3DetailBean invoiceViewL3DetailBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewL3DetailBean.getInvoice());
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
   public int delete(InvoiceViewL3DetailBean invoiceViewL3DetailBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewL3DetailBean.getInvoice());
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
   public int insert(InvoiceViewL3DetailBean invoiceViewL3DetailBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceViewL3DetailBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceViewL3DetailBean invoiceViewL3DetailBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_LINE + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "," +
     ATTRIBUTE_CAT_PART_NO + "," +
     ATTRIBUTE_QUANTITY + "," +
     ATTRIBUTE_TOTAL_PRICE + "," +
     ATTRIBUTE_TOTAL_REBATE + "," +
     ATTRIBUTE_NET_AMOUNT + "," +
     ATTRIBUTE_TOTAL_ADD_CHARGE + "," +
     ATTRIBUTE_SERVICE_FEE + "," +
     ATTRIBUTE_MR_NUMBER + "," +
     ATTRIBUTE_DATE_DELIVERED + "," +
     ATTRIBUTE_RADIAN_PO + "," +
     ATTRIBUTE_INVOICE_UNIT_PRICE + "," +
     ATTRIBUTE_UNIT_REBATE + "," +
     ATTRIBUTE_PERCENT_SPLIT_CHARGE + "," +
     ATTRIBUTE_DESCRIPTION + ")" +
     " values (" +
     invoiceViewL3DetailBean.getInvoice() + "," +
     invoiceViewL3DetailBean.getInvoiceLine() + "," +
       SqlHandler.delimitString(invoiceViewL3DetailBean.getAccountNumber()) + "," +
     SqlHandler.delimitString(invoiceViewL3DetailBean.getCatPartNo()) + "," +
     invoiceViewL3DetailBean.getQuantity() + "," +
     invoiceViewL3DetailBean.getTotalPrice() + "," +
     invoiceViewL3DetailBean.getTotalRebate() + "," +
     invoiceViewL3DetailBean.getNetAmount() + "," +
     invoiceViewL3DetailBean.getTotalAddCharge() + "," +
     invoiceViewL3DetailBean.getServiceFee() + "," +
     SqlHandler.delimitString(invoiceViewL3DetailBean.getMrNumber()) + "," +
     DateHandler.getOracleToDateFunction(invoiceViewL3DetailBean.getDateDelivered()) + "," +
     invoiceViewL3DetailBean.getRadianPo() + "," +
     invoiceViewL3DetailBean.getInvoiceUnitPrice() + "," +
     invoiceViewL3DetailBean.getUnitRebate() + "," +
     invoiceViewL3DetailBean.getPercentSplitCharge() + "," +
     SqlHandler.delimitString(invoiceViewL3DetailBean.getDescription()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceViewL3DetailBean invoiceViewL3DetailBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceViewL3DetailBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceViewL3DetailBean invoiceViewL3DetailBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewL3DetailBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_LINE + "=" +
       StringHandler.nullIfZero(invoiceViewL3DetailBean.getInvoiceLine()) + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "=" +
       SqlHandler.delimitString(invoiceViewL3DetailBean.getAccountNumber()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
      SqlHandler.delimitString(invoiceViewL3DetailBean.getCatPartNo()) + "," +
     ATTRIBUTE_QUANTITY + "=" +
      StringHandler.nullIfZero(invoiceViewL3DetailBean.getQuantity()) + "," +
     ATTRIBUTE_TOTAL_PRICE + "=" +
      StringHandler.nullIfZero(invoiceViewL3DetailBean.getTotalPrice()) + "," +
     ATTRIBUTE_TOTAL_REBATE + "=" +
       StringHandler.nullIfZero(invoiceViewL3DetailBean.getTotalRebate()) + "," +
     ATTRIBUTE_NET_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceViewL3DetailBean.getNetAmount()) + "," +
     ATTRIBUTE_TOTAL_ADD_CHARGE + "=" +
       StringHandler.nullIfZero(invoiceViewL3DetailBean.getTotalAddCharge()) + "," +
     ATTRIBUTE_SERVICE_FEE + "=" +
      StringHandler.nullIfZero(invoiceViewL3DetailBean.getServiceFee()) + "," +
     ATTRIBUTE_MR_NUMBER + "=" +
      SqlHandler.delimitString(invoiceViewL3DetailBean.getMrNumber()) + "," +
     ATTRIBUTE_DATE_DELIVERED + "=" +
      DateHandler.getOracleToDateFunction(invoiceViewL3DetailBean.getDateDelivered()) + "," +
     ATTRIBUTE_RADIAN_PO + "=" +
      StringHandler.nullIfZero(invoiceViewL3DetailBean.getRadianPo()) + "," +
     ATTRIBUTE_INVOICE_UNIT_PRICE + "=" +
       StringHandler.nullIfZero(invoiceViewL3DetailBean.getInvoiceUnitPrice()) + "," +
     ATTRIBUTE_UNIT_REBATE + "=" +
      StringHandler.nullIfZero(invoiceViewL3DetailBean.getUnitRebate()) + "," +
     ATTRIBUTE_PERCENT_SPLIT_CHARGE + "=" +
      StringHandler.nullIfZero(invoiceViewL3DetailBean.getPercentSplitCharge()) + "," +
     ATTRIBUTE_DESCRIPTION + "=" +
       SqlHandler.delimitString(invoiceViewL3DetailBean.getDescription()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      invoiceViewL3DetailBean.getInvoice();
    return new SqlManager().update(conn, query);
   }
   */

  /* THIS IS NOT QUERYING A VIEW!!!!
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
      Collection invoiceViewL3DetailBeanColl = new Vector();
      String query = "select * from " + TABLE + " " +
          getWhereClause(criteria);
      DataSet dataSet = new SqlManager().select(conn, query);
      Iterator dataIter = dataSet.iterator();
      while (dataIter.hasNext()) {
        DataSetRow dataSetRow = (DataSetRow) dataIter.next();
        InvoiceViewL3DetailBean invoiceViewL3DetailBean = new
            InvoiceViewL3DetailBean();
        load(dataSetRow, invoiceViewL3DetailBean);
        invoiceViewL3DetailBeanColl.add(invoiceViewL3DetailBean);
      }
      return invoiceViewL3DetailBeanColl;
    }
   */

  public Collection select(BigDecimal invoice, String accountNumber) throws
      BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(invoice, accountNumber, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(BigDecimal invoice, String accountNumber,
                           Connection conn) throws
      BaseException {

    Collection invoiceViewL3DetailBeanColl = new Vector();

    String query = "select  d.invoice, " +
        "d.invoice_line, " +
        "l.account_number, " +
        "d.cat_part_no, " +
        "round(sum(d.quantity), 5) quantity, " +
        "round(sum(d.quantity*d.invoice_unit_price*percent_split_charge), 5) total_price, " +
        "round(sum(d.quantity*d.unit_rebate*percent_split_charge), 5) total_rebate, " +
        "round(sum(d.net_amount), 5) net_amount, " +
        "round(sum(nvl(d.total_add_charge,0)), 5) total_add_charge, " +
        "round(sum(d.service_fee*percent_split_charge), 5) service_fee, " +
        "rtrim(d.pr_number || '-' || d.line_item) mr_number, " +
        "d.date_delivered, " +
        "r.radian_po, " +
        "round(d.invoice_unit_price, 5) invoice_unit_price, " +
        "round(d.unit_rebate, 5) unit_rebate, " +
        "round(percent_split_charge, 5) percent_split_charge, " +
        "rpad(substr(d.part_description,0,35),35,' ') description " +
        "from invoice_line l,invoice_detail d,invoice i,receipt r " +
        "where i.invoice=" + invoice + " " +
        "and l.account_number =" + SqlHandler.delimitString(accountNumber) +
        " and " +
        "i.invoice = l.invoice and i.invoice = d.invoice and " +
        "i.company_id='L3' and l.invoice_line = d.invoice_line " +
        "and d.receipt_id = r.receipt_id (+) " +
        "group by d.invoice,d.invoice_line,l.po_number, " +
        "d.cat_part_no,percent_split_charge, " +
        "rtrim(d.pr_number || '-' || d.line_item), " +
        "d.date_delivered,r.radian_po, " +
        "d.invoice_unit_price, " +
        "d.unit_rebate,l.account_number, " +
        "rpad(substr(d.part_description,0,35),35,' ') " +
        "order by d.invoice_line,d.date_delivered";
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewL3DetailBean invoiceViewL3DetailBean = new
          InvoiceViewL3DetailBean();
      load(dataSetRow, invoiceViewL3DetailBean);
      invoiceViewL3DetailBeanColl.add(invoiceViewL3DetailBean);
    }

    return invoiceViewL3DetailBeanColl;
  }

}