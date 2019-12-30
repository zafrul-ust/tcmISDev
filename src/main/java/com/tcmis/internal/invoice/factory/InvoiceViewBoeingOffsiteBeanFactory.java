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
import com.tcmis.internal.invoice.beans.InvoiceViewBoeingOffsiteBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewBoeingOffsiteBeanFactory <br>
 * @version: 1.0, Mar 3, 2005 <br>
 *****************************************************************************/

public class InvoiceViewBoeingOffsiteBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_APPLICATION = "APPLICATION";
  public String ATTRIBUTE_WORK_ORDER = "WORK_ORDER";
  public String ATTRIBUTE_PO = "PO";
  public String ATTRIBUTE_QUANTITY = "QUANTITY";
  public String ATTRIBUTE_UNIT_COST = "UNIT_COST";
  public String ATTRIBUTE_EXTENDED_COST = "EXTENDED_COST";
  public String ATTRIBUTE_ADDITIONAL_CHARGE = "ADDITIONAL_CHARGE";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";

  //table name
  public String TABLE = "INVOICE_VIEW_BOEING_OFFSITE";

  //constructor
  public InvoiceViewBoeingOffsiteBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("application")) {
      return ATTRIBUTE_APPLICATION;
    }
    else if (attributeName.equals("workOrder")) {
      return ATTRIBUTE_WORK_ORDER;
    }
    else if (attributeName.equals("po")) {
      return ATTRIBUTE_PO;
    }
    else if (attributeName.equals("quantity")) {
      return ATTRIBUTE_QUANTITY;
    }
    else if (attributeName.equals("unitCost")) {
      return ATTRIBUTE_UNIT_COST;
    }
    else if (attributeName.equals("extendedCost")) {
      return ATTRIBUTE_EXTENDED_COST;
    }
    else if (attributeName.equals("additionalCharge")) {
      return ATTRIBUTE_ADDITIONAL_CHARGE;
    }
    else if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
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
    return super.getType(attributeName, InvoiceViewBoeingOffsiteBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceViewBoeingOffsiteBean invoiceViewBoeingOffsiteBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewBoeingOffsiteBean.getInvoice());
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
   public int delete(InvoiceViewBoeingOffsiteBean invoiceViewBoeingOffsiteBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewBoeingOffsiteBean.getInvoice());
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
   public int insert(InvoiceViewBoeingOffsiteBean invoiceViewBoeingOffsiteBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceViewBoeingOffsiteBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceViewBoeingOffsiteBean invoiceViewBoeingOffsiteBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_APPLICATION + "," +
     ATTRIBUTE_WORK_ORDER + "," +
     ATTRIBUTE_PO + "," +
     ATTRIBUTE_QUANTITY + "," +
     ATTRIBUTE_UNIT_COST + "," +
     ATTRIBUTE_EXTENDED_COST + "," +
     ATTRIBUTE_ADDITIONAL_CHARGE + "," +
     ATTRIBUTE_INVOICE_PERIOD + ")" +
   values (
       StringHandler.nullIfZero(invoiceViewBoeingOffsiteBean.getInvoice()) + "," +
       SqlHandler.delimitString(invoiceViewBoeingOffsiteBean.getFacilityId()) + "," +
       SqlHandler.delimitString(invoiceViewBoeingOffsiteBean.getApplication()) + "," +
       SqlHandler.delimitString(invoiceViewBoeingOffsiteBean.getWorkOrder()) + "," +
     SqlHandler.delimitString(invoiceViewBoeingOffsiteBean.getPo()) + "," +
       StringHandler.nullIfZero(invoiceViewBoeingOffsiteBean.getQuantity()) + "," +
       StringHandler.nullIfZero(invoiceViewBoeingOffsiteBean.getUnitCost()) + "," +
       StringHandler.nullIfZero(invoiceViewBoeingOffsiteBean.getExtendedCost()) + "," +
     StringHandler.nullIfZero(invoiceViewBoeingOffsiteBean.getAdditionalCharge()) + "," +
       StringHandler.nullIfZero(invoiceViewBoeingOffsiteBean.getInvoicePeriod()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceViewBoeingOffsiteBean invoiceViewBoeingOffsiteBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceViewBoeingOffsiteBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceViewBoeingOffsiteBean invoiceViewBoeingOffsiteBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
       StringHandler.nullIfZero(invoiceViewBoeingOffsiteBean.getInvoice()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
       SqlHandler.delimitString(invoiceViewBoeingOffsiteBean.getFacilityId()) + "," +
     ATTRIBUTE_APPLICATION + "=" +
       SqlHandler.delimitString(invoiceViewBoeingOffsiteBean.getApplication()) + "," +
     ATTRIBUTE_WORK_ORDER + "=" +
       SqlHandler.delimitString(invoiceViewBoeingOffsiteBean.getWorkOrder()) + "," +
     ATTRIBUTE_PO + "=" +
      SqlHandler.delimitString(invoiceViewBoeingOffsiteBean.getPo()) + "," +
     ATTRIBUTE_QUANTITY + "=" +
       StringHandler.nullIfZero(invoiceViewBoeingOffsiteBean.getQuantity()) + "," +
     ATTRIBUTE_UNIT_COST + "=" +
       StringHandler.nullIfZero(invoiceViewBoeingOffsiteBean.getUnitCost()) + "," +
     ATTRIBUTE_EXTENDED_COST + "=" +
       StringHandler.nullIfZero(invoiceViewBoeingOffsiteBean.getExtendedCost()) + "," +
     ATTRIBUTE_ADDITIONAL_CHARGE + "=" +
      StringHandler.nullIfZero(invoiceViewBoeingOffsiteBean.getAdditionalCharge()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoiceViewBoeingOffsiteBean.getInvoicePeriod()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewBoeingOffsiteBean.getInvoice());
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

    Collection invoiceViewBoeingOffsiteBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewBoeingOffsiteBean invoiceViewBoeingOffsiteBean = new
          InvoiceViewBoeingOffsiteBean();
      load(dataSetRow, invoiceViewBoeingOffsiteBean);
      invoiceViewBoeingOffsiteBeanColl.add(invoiceViewBoeingOffsiteBean);
    }

    return invoiceViewBoeingOffsiteBeanColl;
  }

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

    Collection invoiceViewBoeingOffsiteBeanColl = new Vector();

    String query = "select i.invoice, i.application, i.work_order, round(sum(i.extended_cost), 5) extended_cost, " +
                   "i.invoice_date " +
                   "from invoice_view_boeing_offsite i " +
                   "where invoice_period=" + invoicePeriod + " " +
                   "group by invoice, application, work_order, invoice_date";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewBoeingOffsiteBean invoiceViewBoeingOffsiteBean = new
          InvoiceViewBoeingOffsiteBean();
      load(dataSetRow, invoiceViewBoeingOffsiteBean);
      invoiceViewBoeingOffsiteBean.setDetailCollection(this.selectDetail(invoiceViewBoeingOffsiteBean.getInvoice()));
      invoiceViewBoeingOffsiteBeanColl.add(invoiceViewBoeingOffsiteBean);
    }

    return invoiceViewBoeingOffsiteBeanColl;
  }

  public Collection selectDetail(BigDecimal invoice) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectDetail(invoice, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectDetail(BigDecimal invoice, Connection conn) throws
      BaseException {

    Collection invoiceViewBoeingOffsiteBeanColl = new Vector();

    String query = "select * from invoice_view_boeing_offsite " +
                   "where invoice=" + invoice;
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewBoeingOffsiteBean invoiceViewBoeingOffsiteBean = new
          InvoiceViewBoeingOffsiteBean();
      load(dataSetRow, invoiceViewBoeingOffsiteBean);
      invoiceViewBoeingOffsiteBeanColl.add(invoiceViewBoeingOffsiteBean);
    }

    return invoiceViewBoeingOffsiteBeanColl;
  }

  /*
      public Collection selectGroupedByInvoices(SearchCriteria criteria, Connection conn) throws
          BaseException {
        Collection invoiceViewBoeingOffsiteBeanColl = new Vector();
        String query = "select unique invoice from " + TABLE + " " +
            getWhereClause(criteria);
        DataSet dataSet = new SqlManager().select(conn, query);
        Iterator dataIter = dataSet.iterator();
        while (dataIter.hasNext()) {
          DataSetRow dataSetRow = (DataSetRow) dataIter.next();
          InvoiceViewBoeingOffsiteBean invoiceViewBoeingOffsiteBean = new
              InvoiceViewBoeingOffsiteBean();
          load(dataSetRow, invoiceViewBoeingOffsiteBean);
          SearchCriterion criterion = criteria.getCriterion("invoicePeriod");
          Collection c = criterion.getValues();
          Iterator i = c.iterator();
          String s = null;
          while(i.hasNext()) {
            s = (String)i.next();
          }
          SearchCriteria crit = new SearchCriteria();
          crit.addCriterion("invoicePeriod", SearchCriterion.EQUALS, s);
          crit.addCriterion("invoice", SearchCriterion.EQUALS, new Integer(invoiceViewBoeingOffsiteBean.getInvoice()).toString());
          invoiceViewBoeingOffsiteBean.setColl(this.select(crit, conn));
          invoiceViewBoeingOffsiteBeanColl.add(invoiceViewBoeingOffsiteBean);
        }
        return invoiceViewBoeingOffsiteBeanColl;
      }
   */
}