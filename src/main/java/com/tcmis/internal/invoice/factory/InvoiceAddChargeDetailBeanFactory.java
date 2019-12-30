package com.tcmis.internal.invoice.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
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
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.invoice.beans.InvoiceAddChargeDetailBean;

/******************************************************************************
 * CLASSNAME: InvoiceAddChargeDetailBeanFactory <br>
 * @version: 1.0, Mar 4, 2005 <br>
 *****************************************************************************/

public class InvoiceAddChargeDetailBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_LINE = "INVOICE_LINE";
  public String ATTRIBUTE_ISSUE_ID = "ISSUE_ID";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_ADDITIONAL_CHARGE_DESC = "ADDITIONAL_CHARGE_DESC";
  public String ATTRIBUTE_ADDITIONAL_CHARGE_AMOUNT = "ADDITIONAL_CHARGE_AMOUNT";
  public String ATTRIBUTE_ADDITIONAL_CHARGE_ITEM_ID =
      "ADDITIONAL_CHARGE_ITEM_ID";
  public String ATTRIBUTE_ISSUE_COST_REVISION = "ISSUE_COST_REVISION";
  public String ATTRIBUTE_SALES_TAX_APPLIED = "SALES_TAX_APPLIED";

  //table name
  public String TABLE = "INVOICE_ADD_CHARGE_DETAIL";

  //constructor
  public InvoiceAddChargeDetailBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("itemType")) {
      return ATTRIBUTE_ITEM_TYPE;
    }
    else if (attributeName.equals("prNumber")) {
      return ATTRIBUTE_PR_NUMBER;
    }
    else if (attributeName.equals("lineItem")) {
      return ATTRIBUTE_LINE_ITEM;
    }
    else if (attributeName.equals("additionalChargeDesc")) {
      return ATTRIBUTE_ADDITIONAL_CHARGE_DESC;
    }
    else if (attributeName.equals("additionalChargeAmount")) {
      return ATTRIBUTE_ADDITIONAL_CHARGE_AMOUNT;
    }
    else if (attributeName.equals("additionalChargeItemId")) {
      return ATTRIBUTE_ADDITIONAL_CHARGE_ITEM_ID;
    }
    else if (attributeName.equals("issueCostRevision")) {
      return ATTRIBUTE_ISSUE_COST_REVISION;
    }
    else if (attributeName.equals("salesTaxApplied")) {
      return ATTRIBUTE_SALES_TAX_APPLIED;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceAddChargeDetailBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceAddChargeDetailBean invoiceAddChargeDetailBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceAddChargeDetailBean.getInvoice());
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
   public int delete(InvoiceAddChargeDetailBean invoiceAddChargeDetailBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceAddChargeDetailBean.getInvoice());
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
   public int insert(InvoiceAddChargeDetailBean invoiceAddChargeDetailBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceAddChargeDetailBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceAddChargeDetailBean invoiceAddChargeDetailBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_LINE + "," +
     ATTRIBUTE_ISSUE_ID + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_ITEM_TYPE + "," +
     ATTRIBUTE_PR_NUMBER + "," +
     ATTRIBUTE_LINE_ITEM + "," +
     ATTRIBUTE_ADDITIONAL_CHARGE_DESC + "," +
     ATTRIBUTE_ADDITIONAL_CHARGE_AMOUNT + "," +
     ATTRIBUTE_ADDITIONAL_CHARGE_ITEM_ID + "," +
     ATTRIBUTE_ISSUE_COST_REVISION + "," +
     ATTRIBUTE_SALES_TAX_APPLIED + ")" +
   values (
     StringHandler.nullIfZero(invoiceAddChargeDetailBean.getInvoice()) + "," +
       StringHandler.nullIfZero(invoiceAddChargeDetailBean.getInvoiceLine()) + "," +
     StringHandler.nullIfZero(invoiceAddChargeDetailBean.getIssueId()) + "," +
       SqlHandler.delimitString(invoiceAddChargeDetailBean.getCompanyId()) + "," +
     SqlHandler.delimitString(invoiceAddChargeDetailBean.getItemType()) + "," +
     StringHandler.nullIfZero(invoiceAddChargeDetailBean.getPrNumber()) + "," +
     SqlHandler.delimitString(invoiceAddChargeDetailBean.getLineItem()) + "," +
     SqlHandler.delimitString(invoiceAddChargeDetailBean.getAdditionalChargeDesc()) + "," +
     StringHandler.nullIfZero(invoiceAddChargeDetailBean.getAdditionalChargeAmount()) + "," +
     StringHandler.nullIfZero(invoiceAddChargeDetailBean.getAdditionalChargeItemId()) + "," +
     StringHandler.nullIfZero(invoiceAddChargeDetailBean.getIssueCostRevision()) + "," +
       SqlHandler.delimitString(invoiceAddChargeDetailBean.getSalesTaxApplied()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceAddChargeDetailBean invoiceAddChargeDetailBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceAddChargeDetailBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceAddChargeDetailBean invoiceAddChargeDetailBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceAddChargeDetailBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_LINE + "=" +
       StringHandler.nullIfZero(invoiceAddChargeDetailBean.getInvoiceLine()) + "," +
     ATTRIBUTE_ISSUE_ID + "=" +
      StringHandler.nullIfZero(invoiceAddChargeDetailBean.getIssueId()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
       SqlHandler.delimitString(invoiceAddChargeDetailBean.getCompanyId()) + "," +
     ATTRIBUTE_ITEM_TYPE + "=" +
       SqlHandler.delimitString(invoiceAddChargeDetailBean.getItemType()) + "," +
     ATTRIBUTE_PR_NUMBER + "=" +
       StringHandler.nullIfZero(invoiceAddChargeDetailBean.getPrNumber()) + "," +
     ATTRIBUTE_LINE_ITEM + "=" +
       SqlHandler.delimitString(invoiceAddChargeDetailBean.getLineItem()) + "," +
     ATTRIBUTE_ADDITIONAL_CHARGE_DESC + "=" +
      SqlHandler.delimitString(invoiceAddChargeDetailBean.getAdditionalChargeDesc()) + "," +
     ATTRIBUTE_ADDITIONAL_CHARGE_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceAddChargeDetailBean.getAdditionalChargeAmount()) + "," +
     ATTRIBUTE_ADDITIONAL_CHARGE_ITEM_ID + "=" +
      StringHandler.nullIfZero(invoiceAddChargeDetailBean.getAdditionalChargeItemId()) + "," +
     ATTRIBUTE_ISSUE_COST_REVISION + "=" +
      StringHandler.nullIfZero(invoiceAddChargeDetailBean.getIssueCostRevision()) + "," +
     ATTRIBUTE_SALES_TAX_APPLIED + "=" +
      SqlHandler.delimitString(invoiceAddChargeDetailBean.getSalesTaxApplied()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceAddChargeDetailBean.getInvoice());
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

    Collection invoiceAddChargeDetailBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceAddChargeDetailBean invoiceAddChargeDetailBean = new
          InvoiceAddChargeDetailBean();
      load(dataSetRow, invoiceAddChargeDetailBean);
      invoiceAddChargeDetailBeanColl.add(invoiceAddChargeDetailBean);
    }

    return invoiceAddChargeDetailBeanColl;
  }

  public Collection selectEw(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectEw(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectEw(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection invoiceAddChargeDetailBeanColl = new Vector();

    String query = "select INVOICE, INVOICE_LINE,ADDITIONAL_CHARGE_DESC,round(sum(ADDITIONAL_CHARGE_amount), 5) ADDITIONAL_CHARGE_amount " +
        "from " + TABLE + " " +
        getWhereClause(criteria) +
        " group by INVOICE, INVOICE_LINE,ADDITIONAL_CHARGE_DESC";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceAddChargeDetailBean invoiceAddChargeDetailBean = new
          InvoiceAddChargeDetailBean();
      load(dataSetRow, invoiceAddChargeDetailBean);
      invoiceAddChargeDetailBeanColl.add(invoiceAddChargeDetailBean);
    }

    return invoiceAddChargeDetailBeanColl;
  }

  public Collection selectMiller(BigDecimal invoice,
                                 BigDecimal invoiceLine,
                                 String partNumber,
                                 Date deliveryDate,
                                 BigDecimal unitPrice,
                                 String partDescription) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectMiller(invoice, 
                       invoiceLine, 
                       partNumber,
                       deliveryDate,
                       unitPrice,
                       partDescription,
                       connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectMiller(BigDecimal invoice,
                                 BigDecimal invoiceLine,
                                 String partNumber,
                                 Date deliveryDate,
                                 BigDecimal unitPrice,
                                 String partDescription,
                                 Connection conn) throws
      BaseException {

    Collection invoiceAddChargeDetailBeanColl = new Vector();
/*
    String query = "select decode(additional_charge_desc, " +
        "'Estimated Freight','Shipping Charge','Surcharge', " +
        "'Fuel Surcharge',additional_charge_desc) || " +
        "' (' || to_char(date_delivered,'mm/dd/yyyy') || " +
        "')' additional_charge_desc, " +
        "sum(additional_charge_amount) additional_charge_amount " +
        "from invoice_add_charge_detail a,invoice_detail d " +
        "where d.invoice = " + invoice + " and " +
        "d.invoice_line = " + invoiceLine + " " +
        "and d.issue_id = a.issue_id and d.invoice = " +
        "a.invoice and d.issue_cost_revision = a.issue_cost_revision " +
        "group by date_delivered, " +
        "decode(additional_charge_desc, " +
        "'Estimated Freight','Shipping Charge','Surcharge', " +
        "'Fuel Surcharge',additional_charge_desc) ||  " +
        "' (' || to_char(date_delivered,'mm/dd/yyyy') || ')'";
*/
    String query = "select decode(additional_charge_desc,  " +
        "'Estimated Freight','Shipping Charge','Surcharge',  " +
        "'Fuel Surcharge',additional_charge_desc) ||  " +
        "' (' || to_char(date_delivered,'mm/dd/yyyy') ||  " +
        "')' additional_charge_desc,  " +
        "round(sum(additional_charge_amount), 5) additional_charge_amount  " +
        "from invoice_add_charge_detail a,invoice_detail d  " +
        "where d.invoice = " + invoice + " and " +
        	  "d.invoice_line = " + invoiceLine + " and " +  
                  "d.cat_part_no = " + SqlHandler.delimitString(partNumber) + " and  " +
                  "d.date_delivered = " + DateHandler.getOracleToDateFunction(deliveryDate) + " and  " +
                  "d.invoice_unit_price = " + unitPrice + " and  " +
                  "d.part_description = " + SqlHandler.delimitString(partDescription) + " and  " +
                  "d.issue_id = a.issue_id and  " +
                  "d.invoice = a.invoice and  " +
                  "d.issue_cost_revision = a.issue_cost_revision  " +
        "group by date_delivered,  " +
        "decode(additional_charge_desc,  " +
        "'Estimated Freight','Shipping Charge','Surcharge',  " +
        "'Fuel Surcharge',additional_charge_desc) ||   " +
        "' (' || to_char(date_delivered,'mm/dd/yyyy') || ')'";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceAddChargeDetailBean invoiceAddChargeDetailBean = new
          InvoiceAddChargeDetailBean();
      load(dataSetRow, invoiceAddChargeDetailBean);
      invoiceAddChargeDetailBeanColl.add(invoiceAddChargeDetailBean);
    }

    return invoiceAddChargeDetailBeanColl;
  }

  public Collection selectRac(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectRac(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectRac(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection invoiceAddChargeDetailBeanColl = new Vector();
    String groupBy = "group by decode(additional_charge_desc,'Estimated Freight','Shipping Charge',additional_charge_desc)";
    String query = "select decode(additional_charge_desc,'Estimated Freight','Shipping Charge',additional_charge_desc) additional_charge_desc, " +
                   "sum(additional_charge_amount) additional_charge_amount from " + TABLE + " " +
                   getWhereClause(criteria) + " " + groupBy;

    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceAddChargeDetailBean invoiceAddChargeDetailBean = new
          InvoiceAddChargeDetailBean();
      load(dataSetRow, invoiceAddChargeDetailBean);
      invoiceAddChargeDetailBeanColl.add(invoiceAddChargeDetailBean);
    }

    return invoiceAddChargeDetailBeanColl;
  }

  public Collection selectSwa(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectSwa(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectSwa(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection invoiceAddChargeDetailBeanColl = new Vector();
    String groupBy = "group by decode(additional_charge_desc,'Estimated Freight','Shipping Charge',additional_charge_desc)";

    String query = "select decode(additional_charge_desc,'Estimated Freight','Shipping Charge',additional_charge_desc) additional_charge_desc," +
                   "sum(additional_charge_amount) additional_charge_amount from " + TABLE + " " +
                   getWhereClause(criteria) + " " + groupBy;

    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceAddChargeDetailBean invoiceAddChargeDetailBean = new
          InvoiceAddChargeDetailBean();
      load(dataSetRow, invoiceAddChargeDetailBean);
      invoiceAddChargeDetailBeanColl.add(invoiceAddChargeDetailBean);
    }

    return invoiceAddChargeDetailBeanColl;
  }

  public Collection selectSwaCorrection(BigDecimal invoice, 
                                        BigDecimal invoiceLine, 
                                        BigDecimal issueId) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectSwaCorrection(invoice, invoiceLine, issueId, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectSwaCorrection(BigDecimal invoice, 
                                        BigDecimal invoiceLine, 
                                        BigDecimal issueId, 
                                        Connection conn) throws
      BaseException {

    Collection invoiceAddChargeDetailBeanColl = new Vector();
    String query = "select decode(additional_charge_desc,'Estimated Freight','Shipping Charge',additional_charge_desc) additional_charge_desc, " +
                    "additional_charge_amount " +
                    "from " + TABLE + " " +
                    "where invoice = " + invoice + " " +
                    "and invoice_line = " + invoiceLine + " " +
                    "and issue_id = " + issueId + " " +
                    "and issue_cost_revision = (select max(issue_cost_revision) from invoice_detail " +
                      "where invoice = " + invoice + " " +
                      "and invoice_line = " + invoiceLine + " " +
                      "and issue_id = " + issueId + ")";

    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceAddChargeDetailBean invoiceAddChargeDetailBean = new
          InvoiceAddChargeDetailBean();
      load(dataSetRow, invoiceAddChargeDetailBean);
      invoiceAddChargeDetailBeanColl.add(invoiceAddChargeDetailBean);
    }

    return invoiceAddChargeDetailBeanColl;
  }
}