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
import com.tcmis.internal.invoice.beans.InvoiceViewQosBean;

/******************************************************************************
 * CLASSNAME: QosInvoiceViewBeanFactory <br>
 * @version: 1.0, Oct 26, 2005 <br>
 *****************************************************************************/

public class InvoiceViewQosBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_LINE = "INVOICE_LINE";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
  public String ATTRIBUTE_QUANTITY = "QUANTITY";
  public String ATTRIBUTE_EXT_PRICE = "EXT_PRICE";
  public String ATTRIBUTE_NET_LINE_AMOUNT = "NET_LINE_AMOUNT";
  public String ATTRIBUTE_ADDITIONAL_CHARGES = "ADDITIONAL_CHARGES";
  public String ATTRIBUTE_SERVICE_FEE = "SERVICE_FEE";
  public String ATTRIBUTE_PERCENT_SPLIT_CHARGE = "PERCENT_SPLIT_CHARGE";
  public String ATTRIBUTE_MR_NUMBER = "MR_NUMBER";
  public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
  public String ATTRIBUTE_INVOICE_UNIT_PRICE = "INVOICE_UNIT_PRICE";
  public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
  public String ATTRIBUTE_SALES_TAX = "SALES_TAX";

  //table name
  public String TABLE = "QOS_INVOICE_VIEW";

  //constructor
  public InvoiceViewQosBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("partDescription")) {
      return ATTRIBUTE_PART_DESCRIPTION;
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
    else if (attributeName.equals("percentSplitCharge")) {
      return ATTRIBUTE_PERCENT_SPLIT_CHARGE;
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
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceViewQosBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(QosInvoiceViewBean qosInvoiceViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + qosInvoiceViewBean.getInvoice());
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
   public int delete(QosInvoiceViewBean qosInvoiceViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + qosInvoiceViewBean.getInvoice());
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
   public int insert(QosInvoiceViewBean qosInvoiceViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(qosInvoiceViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(QosInvoiceViewBean qosInvoiceViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_LINE + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_CAT_PART_NO + "," +
     ATTRIBUTE_PART_DESCRIPTION + "," +
     ATTRIBUTE_QUANTITY + "," +
     ATTRIBUTE_EXT_PRICE + "," +
     ATTRIBUTE_NET_LINE_AMOUNT + "," +
     ATTRIBUTE_ADDITIONAL_CHARGES + "," +
     ATTRIBUTE_SERVICE_FEE + "," +
     ATTRIBUTE_PERCENT_SPLIT_CHARGE + "," +
     ATTRIBUTE_MR_NUMBER + "," +
     ATTRIBUTE_DATE_DELIVERED + "," +
     ATTRIBUTE_INVOICE_UNIT_PRICE + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "," +
     ATTRIBUTE_SALES_TAX + ")" +
     " values (" +
     qosInvoiceViewBean.getInvoice() + "," +
     qosInvoiceViewBean.getInvoiceLine() + "," +
     SqlHandler.delimitString(qosInvoiceViewBean.getPoNumber()) + "," +
     SqlHandler.delimitString(qosInvoiceViewBean.getCatPartNo()) + "," +
     SqlHandler.delimitString(qosInvoiceViewBean.getPartDescription()) + "," +
     qosInvoiceViewBean.getQuantity() + "," +
     qosInvoiceViewBean.getExtPrice() + "," +
     qosInvoiceViewBean.getNetLineAmount() + "," +
     qosInvoiceViewBean.getAdditionalCharges() + "," +
     qosInvoiceViewBean.getServiceFee() + "," +
     qosInvoiceViewBean.getPercentSplitCharge() + "," +
     SqlHandler.delimitString(qosInvoiceViewBean.getMrNumber()) + "," +
     DateHandler.getOracleToDateFunction(qosInvoiceViewBean.getDateDelivered()) + "," +
     qosInvoiceViewBean.getInvoiceUnitPrice() + "," +
     SqlHandler.delimitString(qosInvoiceViewBean.getAccountNumber()) + "," +
     qosInvoiceViewBean.getSalesTax() + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(QosInvoiceViewBean qosInvoiceViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(qosInvoiceViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(QosInvoiceViewBean qosInvoiceViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(qosInvoiceViewBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_LINE + "=" +
      StringHandler.nullIfZero(qosInvoiceViewBean.getInvoiceLine()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
      SqlHandler.delimitString(qosInvoiceViewBean.getPoNumber()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
      SqlHandler.delimitString(qosInvoiceViewBean.getCatPartNo()) + "," +
     ATTRIBUTE_PART_DESCRIPTION + "=" +
      SqlHandler.delimitString(qosInvoiceViewBean.getPartDescription()) + "," +
     ATTRIBUTE_QUANTITY + "=" +
      StringHandler.nullIfZero(qosInvoiceViewBean.getQuantity()) + "," +
     ATTRIBUTE_EXT_PRICE + "=" +
      StringHandler.nullIfZero(qosInvoiceViewBean.getExtPrice()) + "," +
     ATTRIBUTE_NET_LINE_AMOUNT + "=" +
      StringHandler.nullIfZero(qosInvoiceViewBean.getNetLineAmount()) + "," +
     ATTRIBUTE_ADDITIONAL_CHARGES + "=" +
      StringHandler.nullIfZero(qosInvoiceViewBean.getAdditionalCharges()) + "," +
     ATTRIBUTE_SERVICE_FEE + "=" +
      StringHandler.nullIfZero(qosInvoiceViewBean.getServiceFee()) + "," +
     ATTRIBUTE_PERCENT_SPLIT_CHARGE + "=" +
      StringHandler.nullIfZero(qosInvoiceViewBean.getPercentSplitCharge()) + "," +
     ATTRIBUTE_MR_NUMBER + "=" +
      SqlHandler.delimitString(qosInvoiceViewBean.getMrNumber()) + "," +
     ATTRIBUTE_DATE_DELIVERED + "=" +
      DateHandler.getOracleToDateFunction(qosInvoiceViewBean.getDateDelivered()) + "," +
     ATTRIBUTE_INVOICE_UNIT_PRICE + "=" +
      StringHandler.nullIfZero(qosInvoiceViewBean.getInvoiceUnitPrice()) + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "=" +
      SqlHandler.delimitString(qosInvoiceViewBean.getAccountNumber()) + "," +
     ATTRIBUTE_SALES_TAX + "=" +
      StringHandler.nullIfZero(qosInvoiceViewBean.getSalesTax()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      qosInvoiceViewBean.getInvoice();
    return new SqlManager().update(conn, query);
   }
   */
  /*
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
       public Collection select(SearchCriteria criteria, Connection conn) throws BaseException {
      Collection qosInvoiceViewBeanColl = new Vector();
      String query = "select * from " + TABLE + " " +
          getWhereClause(criteria);
      DataSet dataSet = new SqlManager().select(conn, query);
      Iterator dataIter = dataSet.iterator();
      while (dataIter.hasNext()) {
        DataSetRow dataSetRow = (DataSetRow) dataIter.next();
        InvoiceViewQosBean qosInvoiceViewBean = new InvoiceViewQosBean();
        load(dataSetRow, qosInvoiceViewBean);
        qosInvoiceViewBeanColl.add(qosInvoiceViewBean);
      }
      return qosInvoiceViewBeanColl;
    }
   */
  public Collection select(BigDecimal invoice) throws BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(invoice, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(BigDecimal invoice, Connection conn) throws BaseException {
    Collection qosInvoiceViewBeanColl = new Vector();
    String query = "select d.invoice,d.invoice_line,l.po_number, " +
        "d.cat_part_no,  " +
        "d.part_description," +
        "sum(d.quantity) quantity," +
        "round(sum((d.quantity*d.invoice_unit_price) + nvl(d.total_add_charge,0)), 5) ext_price, " +
        "round(sum(d.net_amount),5) net_line_amount,  " +
        "round(sum(nvl(d.total_add_charge,0)),5) additional_charges," +
        "round(sum(d.service_fee), 5) service_fee, " +
        "percent_split_charge, " +
        "(d.pr_number || '-' || d.line_item) mr_number," +
        "d.date_delivered,  " +
        "round(d.invoice_unit_price, 5) invoice_unit_price, " +
        "l.account_number,  " +
        "round(sum(nvl(d.total_sales_tax,0)), 5) sales_tax " +
        "from invoice i, invoice_detail d, invoice_line l " +
        "where i.invoice=d.invoice and i.invoice=l.invoice and l.invoice_line = d.invoice_line " +
        "and i.company_id='QOS' and i.invoice=" + invoice + " " +
        "group by d.invoice,d.invoice_line,l.po_number, " +
        "d.cat_part_no, " +
        "d.part_description, " +
        "(d.pr_number || '-' || d.line_item), " +
        "d.date_delivered, " +
        "d.invoice_unit_price," +
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
      InvoiceViewQosBean qosInvoiceViewBean = new InvoiceViewQosBean();
      load(dataSetRow, qosInvoiceViewBean);
      qosInvoiceViewBeanColl.add(qosInvoiceViewBean);
    }
    return qosInvoiceViewBeanColl;
  }
}