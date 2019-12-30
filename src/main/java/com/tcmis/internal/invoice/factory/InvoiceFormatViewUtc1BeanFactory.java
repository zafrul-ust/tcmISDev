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
import com.tcmis.internal.invoice.beans.InvoiceFormatViewUtc1Bean;

/******************************************************************************
 * CLASSNAME: InvoiceFormatViewUtc1BeanFactory <br>
 * @version: 1.0, Mar 22, 2005 <br>
 *****************************************************************************/

public class InvoiceFormatViewUtc1BeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_INVOICE_GROUP = "INVOICE_GROUP";
  public String ATTRIBUTE_DIVISION_NAME = "DIVISION_NAME";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_CONTACT_NAME = "CONTACT_NAME";
  public String ATTRIBUTE_CONTACT_PHONE = "CONTACT_PHONE";
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_PO_LINE = "PO_LINE";
  public String ATTRIBUTE_PO_LINE_DESCRIPTION = "PO_LINE_DESCRIPTION";
  public String ATTRIBUTE_PO_LINE_AMOUNT = "PO_LINE_AMOUNT";
  public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";

  //table name
  public String TABLE = "INVOICE_FORMAT_VIEW_UTC1";

  //constructor
  public InvoiceFormatViewUtc1BeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("invoiceGroup")) {
      return ATTRIBUTE_INVOICE_GROUP;
    }
    else if (attributeName.equals("divisionName")) {
      return ATTRIBUTE_DIVISION_NAME;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("contactName")) {
      return ATTRIBUTE_CONTACT_NAME;
    }
    else if (attributeName.equals("contactPhone")) {
      return ATTRIBUTE_CONTACT_PHONE;
    }
    else if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("invoiceAmount")) {
      return ATTRIBUTE_INVOICE_AMOUNT;
    }
    else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
    }
    else if (attributeName.equals("poLine")) {
      return ATTRIBUTE_PO_LINE;
    }
    else if (attributeName.equals("poLineDescription")) {
      return ATTRIBUTE_PO_LINE_DESCRIPTION;
    }
    else if (attributeName.equals("poLineAmount")) {
      return ATTRIBUTE_PO_LINE_AMOUNT;
    }
    else if (attributeName.equals("dateDelivered")) {
      return ATTRIBUTE_DATE_DELIVERED;
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
    return super.getType(attributeName, InvoiceFormatViewUtc1Bean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceFormatViewUtc1Bean invoiceFormatViewUtc1Bean)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("invoicePeriod", "SearchCriterion.EQUALS",
     "" + invoiceFormatViewUtc1Bean.getInvoicePeriod());
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
       public int delete(InvoiceFormatViewUtc1Bean invoiceFormatViewUtc1Bean, Connection conn)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("invoicePeriod", "SearchCriterion.EQUALS",
     "" + invoiceFormatViewUtc1Bean.getInvoicePeriod());
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
   public int insert(InvoiceFormatViewUtc1Bean invoiceFormatViewUtc1Bean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceFormatViewUtc1Bean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int insert(InvoiceFormatViewUtc1Bean invoiceFormatViewUtc1Bean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_INVOICE_GROUP + "," +
     ATTRIBUTE_DIVISION_NAME + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_CONTACT_NAME + "," +
     ATTRIBUTE_CONTACT_PHONE + "," +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_PO_LINE + "," +
     ATTRIBUTE_PO_LINE_DESCRIPTION + "," +
     ATTRIBUTE_PO_LINE_AMOUNT + ")" +
   values (
       StringHandler.nullIfZero(invoiceFormatViewUtc1Bean.getInvoicePeriod()) + "," +
     SqlHandler.delimitString(invoiceFormatViewUtc1Bean.getCompanyId()) + "," +
       SqlHandler.delimitString(invoiceFormatViewUtc1Bean.getInvoiceGroup()) + "," +
       SqlHandler.delimitString(invoiceFormatViewUtc1Bean.getDivisionName()) + "," +
       SqlHandler.delimitString(invoiceFormatViewUtc1Bean.getFacilityId()) + "," +
       SqlHandler.delimitString(invoiceFormatViewUtc1Bean.getContactName()) + "," +
       SqlHandler.delimitString(invoiceFormatViewUtc1Bean.getContactPhone()) + "," +
     StringHandler.nullIfZero(invoiceFormatViewUtc1Bean.getInvoice()) + "," +
       StringHandler.nullIfZero(invoiceFormatViewUtc1Bean.getInvoiceAmount()) + "," +
     SqlHandler.delimitString(invoiceFormatViewUtc1Bean.getPoNumber()) + "," +
     SqlHandler.delimitString(invoiceFormatViewUtc1Bean.getPoLine()) + "," +
     SqlHandler.delimitString(invoiceFormatViewUtc1Bean.getPoLineDescription()) + "," +
       StringHandler.nullIfZero(invoiceFormatViewUtc1Bean.getPoLineAmount()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceFormatViewUtc1Bean invoiceFormatViewUtc1Bean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceFormatViewUtc1Bean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int update(InvoiceFormatViewUtc1Bean invoiceFormatViewUtc1Bean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
       StringHandler.nullIfZero(invoiceFormatViewUtc1Bean.getInvoicePeriod()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
       SqlHandler.delimitString(invoiceFormatViewUtc1Bean.getCompanyId()) + "," +
     ATTRIBUTE_INVOICE_GROUP + "=" +
       SqlHandler.delimitString(invoiceFormatViewUtc1Bean.getInvoiceGroup()) + "," +
     ATTRIBUTE_DIVISION_NAME + "=" +
       SqlHandler.delimitString(invoiceFormatViewUtc1Bean.getDivisionName()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
       SqlHandler.delimitString(invoiceFormatViewUtc1Bean.getFacilityId()) + "," +
     ATTRIBUTE_CONTACT_NAME + "=" +
       SqlHandler.delimitString(invoiceFormatViewUtc1Bean.getContactName()) + "," +
     ATTRIBUTE_CONTACT_PHONE + "=" +
       SqlHandler.delimitString(invoiceFormatViewUtc1Bean.getContactPhone()) + "," +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceFormatViewUtc1Bean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "=" +
       StringHandler.nullIfZero(invoiceFormatViewUtc1Bean.getInvoiceAmount()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
      SqlHandler.delimitString(invoiceFormatViewUtc1Bean.getPoNumber()) + "," +
     ATTRIBUTE_PO_LINE + "=" +
      SqlHandler.delimitString(invoiceFormatViewUtc1Bean.getPoLine()) + "," +
     ATTRIBUTE_PO_LINE_DESCRIPTION + "=" +
      SqlHandler.delimitString(invoiceFormatViewUtc1Bean.getPoLineDescription()) + "," +
     ATTRIBUTE_PO_LINE_AMOUNT + "=" +
       StringHandler.nullIfZero(invoiceFormatViewUtc1Bean.getPoLineAmount()) + " " +
     "where " + ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoiceFormatViewUtc1Bean.getInvoicePeriod());
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
    Collection invoiceFormatViewUtc1BeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceFormatViewUtc1Bean invoiceFormatViewUtc1Bean = new
          InvoiceFormatViewUtc1Bean();
      load(dataSetRow, invoiceFormatViewUtc1Bean);
      invoiceFormatViewUtc1BeanColl.add(invoiceFormatViewUtc1Bean);
    }
    return invoiceFormatViewUtc1BeanColl;
  }

  public Collection selectInvoice(BigDecimal invoicePeriod, String division) throws
      BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectInvoice(invoicePeriod, division, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectInvoice(BigDecimal invoicePeriod, String division, Connection conn) throws
      BaseException {
    Collection invoiceFormatViewUtc1BeanColl = new Vector();

    String query = "select distinct v.invoice,v.invoice_amount,v.po_number, v.division_name,v.contact_name,v.contact_phone, i.invoice_date " +
                   "from invoice_format_view_utc1 v, invoice i " +
                   "where v.invoice = i.invoice " +
                   "and v.invoice_period = " + invoicePeriod + " " +
                   "and v.invoice_group = '" + division + "' " +
                   "order by v.invoice";
/*
    String query = "select distinct invoice,invoice_amount,po_number, " +
        "division_name,contact_name,contact_phone " +
        "from invoice_format_view_utc1 " +
        "where invoice_period = " + invoicePeriod + " " +
        "and invoice_group = '" + division + "' " +
        "order by invoice";
*/
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceFormatViewUtc1Bean invoiceFormatViewUtc1Bean = new
          InvoiceFormatViewUtc1Bean();
      load(dataSetRow, invoiceFormatViewUtc1Bean);
      invoiceFormatViewUtc1Bean.setDetail(this.selectInvoiceDetail(
          invoiceFormatViewUtc1Bean.getInvoice(),
          invoiceFormatViewUtc1Bean.getPoNumber(), conn));
      invoiceFormatViewUtc1BeanColl.add(invoiceFormatViewUtc1Bean);
    }
    return invoiceFormatViewUtc1BeanColl;
  }

  private Collection selectInvoiceDetail(BigDecimal invoice, String poNumber, Connection conn) throws
      BaseException {
    Collection invoiceFormatViewUtc1BeanColl = new Vector();
    String query = "select * from invoice_format_view_utc1 " +
                   "where invoice = " + invoice + " " +
                   "and po_number = '" + poNumber + "' order by po_line";
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceFormatViewUtc1Bean invoiceFormatViewUtc1Bean = new
          InvoiceFormatViewUtc1Bean();
      load(dataSetRow, invoiceFormatViewUtc1Bean);
      invoiceFormatViewUtc1BeanColl.add(invoiceFormatViewUtc1Bean);
    }
    return invoiceFormatViewUtc1BeanColl;
  }

}