package com.tcmis.internal.invoice.factory;

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
import com.tcmis.internal.invoice.beans.InvoiceAddChargePrepBean;

/******************************************************************************
 * CLASSNAME: InvoiceAddChargePrepBeanFactory <br>
 * @version: 1.0, Mar 9, 2005 <br>
 *****************************************************************************/

public class InvoiceAddChargePrepBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_LINE = "INVOICE_LINE";
  public String ATTRIBUTE_ISSUE_ID = "ISSUE_ID";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_ACCOUNT_SYS_ID = "ACCOUNT_SYS_ID";
  public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
  public String ATTRIBUTE_ACCOUNT_NUMBER2 = "ACCOUNT_NUMBER2";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_ADD_CHARGE_DESC = "ADD_CHARGE_DESC";
  public String ATTRIBUTE_ADD_CHARGE_AMOUNT = "ADD_CHARGE_AMOUNT";
  public String ATTRIBUTE_ADD_CHARGE_ITEM_ID = "ADD_CHARGE_ITEM_ID";
  public String ATTRIBUTE_DATE_INSERTED = "DATE_INSERTED";
  public String ATTRIBUTE_CHARGE_TYPE = "CHARGE_TYPE";
  public String ATTRIBUTE_ISSUE_COST_REVISION = "ISSUE_COST_REVISION";
  public String ATTRIBUTE_SALES_TAX_APPLIED = "SALES_TAX_APPLIED";

  //table name
  public String TABLE = "INVOICE_ADD_CHARGE_PREP";

  //constructor
  public InvoiceAddChargePrepBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("accountSysId")) {
      return ATTRIBUTE_ACCOUNT_SYS_ID;
    }
    else if (attributeName.equals("accountNumber")) {
      return ATTRIBUTE_ACCOUNT_NUMBER;
    }
    else if (attributeName.equals("accountNumber2")) {
      return ATTRIBUTE_ACCOUNT_NUMBER2;
    }
    else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
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
    else if (attributeName.equals("addChargeDesc")) {
      return ATTRIBUTE_ADD_CHARGE_DESC;
    }
    else if (attributeName.equals("addChargeAmount")) {
      return ATTRIBUTE_ADD_CHARGE_AMOUNT;
    }
    else if (attributeName.equals("addChargeItemId")) {
      return ATTRIBUTE_ADD_CHARGE_ITEM_ID;
    }
    else if (attributeName.equals("dateInserted")) {
      return ATTRIBUTE_DATE_INSERTED;
    }
    else if (attributeName.equals("chargeType")) {
      return ATTRIBUTE_CHARGE_TYPE;
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
    return super.getType(attributeName, InvoiceAddChargePrepBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceAddChargePrepBean invoiceAddChargePrepBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceAddChargePrepBean.getInvoice());
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
   public int delete(InvoiceAddChargePrepBean invoiceAddChargePrepBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceAddChargePrepBean.getInvoice());
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
   public int insert(InvoiceAddChargePrepBean invoiceAddChargePrepBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceAddChargePrepBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceAddChargePrepBean invoiceAddChargePrepBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_LINE + "," +
     ATTRIBUTE_ISSUE_ID + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_ACCOUNT_SYS_ID + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "," +
     ATTRIBUTE_ACCOUNT_NUMBER2 + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_ITEM_TYPE + "," +
     ATTRIBUTE_PR_NUMBER + "," +
     ATTRIBUTE_LINE_ITEM + "," +
     ATTRIBUTE_ADD_CHARGE_DESC + "," +
     ATTRIBUTE_ADD_CHARGE_AMOUNT + "," +
     ATTRIBUTE_ADD_CHARGE_ITEM_ID + "," +
     ATTRIBUTE_DATE_INSERTED + "," +
     ATTRIBUTE_CHARGE_TYPE + "," +
     ATTRIBUTE_ISSUE_COST_REVISION + "," +
     ATTRIBUTE_SALES_TAX_APPLIED + ")" +
   values (
     StringHandler.nullIfZero(invoiceAddChargePrepBean.getInvoice()) + "," +
       StringHandler.nullIfZero(invoiceAddChargePrepBean.getInvoiceLine()) + "," +
     StringHandler.nullIfZero(invoiceAddChargePrepBean.getIssueId()) + "," +
     SqlHandler.delimitString(invoiceAddChargePrepBean.getCompanyId()) + "," +
     SqlHandler.delimitString(invoiceAddChargePrepBean.getFacilityId()) + "," +
       SqlHandler.delimitString(invoiceAddChargePrepBean.getAccountSysId()) + "," +
       SqlHandler.delimitString(invoiceAddChargePrepBean.getAccountNumber()) + "," +
       SqlHandler.delimitString(invoiceAddChargePrepBean.getAccountNumber2()) + "," +
     SqlHandler.delimitString(invoiceAddChargePrepBean.getPoNumber()) + "," +
     SqlHandler.delimitString(invoiceAddChargePrepBean.getItemType()) + "," +
     StringHandler.nullIfZero(invoiceAddChargePrepBean.getPrNumber()) + "," +
     SqlHandler.delimitString(invoiceAddChargePrepBean.getLineItem()) + "," +
       SqlHandler.delimitString(invoiceAddChargePrepBean.getAddChargeDesc()) + "," +
       StringHandler.nullIfZero(invoiceAddChargePrepBean.getAddChargeAmount()) + "," +
       StringHandler.nullIfZero(invoiceAddChargePrepBean.getAddChargeItemId()) + "," +
     DateHandler.getOracleToDateFunction(invoiceAddChargePrepBean.getDateInserted()) + "," +
     SqlHandler.delimitString(invoiceAddChargePrepBean.getChargeType()) + "," +
     StringHandler.nullIfZero(invoiceAddChargePrepBean.getIssueCostRevision()) + "," +
       SqlHandler.delimitString(invoiceAddChargePrepBean.getSalesTaxApplied()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceAddChargePrepBean invoiceAddChargePrepBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceAddChargePrepBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceAddChargePrepBean invoiceAddChargePrepBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceAddChargePrepBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_LINE + "=" +
       StringHandler.nullIfZero(invoiceAddChargePrepBean.getInvoiceLine()) + "," +
     ATTRIBUTE_ISSUE_ID + "=" +
      StringHandler.nullIfZero(invoiceAddChargePrepBean.getIssueId()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(invoiceAddChargePrepBean.getCompanyId()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
       SqlHandler.delimitString(invoiceAddChargePrepBean.getFacilityId()) + "," +
     ATTRIBUTE_ACCOUNT_SYS_ID + "=" +
       SqlHandler.delimitString(invoiceAddChargePrepBean.getAccountSysId()) + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "=" +
       SqlHandler.delimitString(invoiceAddChargePrepBean.getAccountNumber()) + "," +
     ATTRIBUTE_ACCOUNT_NUMBER2 + "=" +
       SqlHandler.delimitString(invoiceAddChargePrepBean.getAccountNumber2()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
      SqlHandler.delimitString(invoiceAddChargePrepBean.getPoNumber()) + "," +
     ATTRIBUTE_ITEM_TYPE + "=" +
      SqlHandler.delimitString(invoiceAddChargePrepBean.getItemType()) + "," +
     ATTRIBUTE_PR_NUMBER + "=" +
      StringHandler.nullIfZero(invoiceAddChargePrepBean.getPrNumber()) + "," +
     ATTRIBUTE_LINE_ITEM + "=" +
      SqlHandler.delimitString(invoiceAddChargePrepBean.getLineItem()) + "," +
     ATTRIBUTE_ADD_CHARGE_DESC + "=" +
       SqlHandler.delimitString(invoiceAddChargePrepBean.getAddChargeDesc()) + "," +
     ATTRIBUTE_ADD_CHARGE_AMOUNT + "=" +
       StringHandler.nullIfZero(invoiceAddChargePrepBean.getAddChargeAmount()) + "," +
     ATTRIBUTE_ADD_CHARGE_ITEM_ID + "=" +
       StringHandler.nullIfZero(invoiceAddChargePrepBean.getAddChargeItemId()) + "," +
     ATTRIBUTE_DATE_INSERTED + "=" +
      DateHandler.getOracleToDateFunction(invoiceAddChargePrepBean.getDateInserted()) + "," +
     ATTRIBUTE_CHARGE_TYPE + "=" +
       SqlHandler.delimitString(invoiceAddChargePrepBean.getChargeType()) + "," +
     ATTRIBUTE_ISSUE_COST_REVISION + "=" +
      StringHandler.nullIfZero(invoiceAddChargePrepBean.getIssueCostRevision()) + "," +
     ATTRIBUTE_SALES_TAX_APPLIED + "=" +
       SqlHandler.delimitString(invoiceAddChargePrepBean.getSalesTaxApplied()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceAddChargePrepBean.getInvoice());
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

    Collection invoiceAddChargePrepBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceAddChargePrepBean invoiceAddChargePrepBean = new
          InvoiceAddChargePrepBean();
      load(dataSetRow, invoiceAddChargePrepBean);
      invoiceAddChargePrepBeanColl.add(invoiceAddChargePrepBean);
    }

    return invoiceAddChargePrepBeanColl;
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

    Collection invoiceAddChargePrepBeanColl = new Vector();
    String query = "select invoice, decode(add_charge_desc,'Estimated Freight','Shipping Charge',add_charge_desc) add_charge_desc ,round(add_charge_amount,5) add_charge_amount " +
        "from " + TABLE + " " +
        getWhereClause(criteria);

    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceAddChargePrepBean invoiceAddChargePrepBean = new
          InvoiceAddChargePrepBean();
      load(dataSetRow, invoiceAddChargePrepBean);
      invoiceAddChargePrepBeanColl.add(invoiceAddChargePrepBean);
    }

    return invoiceAddChargePrepBeanColl;
  }
}