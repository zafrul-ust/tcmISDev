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
import com.tcmis.internal.invoice.beans.InvoiceViewRacCorrDetailBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewRacCorrDetailBeanFactory <br>
 * @version: 1.0, Mar 18, 2005 <br>
 *****************************************************************************/

public class InvoiceViewRacCorrDetailBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_LINE = "INVOICE_LINE";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_INVOICE_QUANTITY = "INVOICE_QUANTITY";
  public String ATTRIBUTE_ORDERED_QUANTITY = "ORDERED_QUANTITY";
  public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
  public String ATTRIBUTE_PRICE = "PRICE";
  public String ATTRIBUTE_TOTAL_ADD_CHARGE = "TOTAL_ADD_CHARGE";
  public String ATTRIBUTE_NET_AMOUNT = "NET_AMOUNT";
  public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";

  //table name
  public String TABLE = "INVOICE_VIEW_RAC_CORR_DETAIL";

  //constructor
  public InvoiceViewRacCorrDetailBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("invoiceQuantity")) {
      return ATTRIBUTE_INVOICE_QUANTITY;
    }
    else if (attributeName.equals("orderedQuantity")) {
      return ATTRIBUTE_ORDERED_QUANTITY;
    }
    else if (attributeName.equals("dateDelivered")) {
      return ATTRIBUTE_DATE_DELIVERED;
    }
    else if (attributeName.equals("price")) {
      return ATTRIBUTE_PRICE;
    }
    else if (attributeName.equals("totalAddCharge")) {
      return ATTRIBUTE_TOTAL_ADD_CHARGE;
    }
    else if (attributeName.equals("netAmount")) {
      return ATTRIBUTE_NET_AMOUNT;
    }
    else if (attributeName.equals("invoiceAmount")) {
      return ATTRIBUTE_INVOICE_AMOUNT;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceViewRacCorrDetailBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceViewRacCorrDetailBean invoiceViewRacCorrDetailBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewRacCorrDetailBean.getInvoice());
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
   public int delete(InvoiceViewRacCorrDetailBean invoiceViewRacCorrDetailBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewRacCorrDetailBean.getInvoice());
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
   public int insert(InvoiceViewRacCorrDetailBean invoiceViewRacCorrDetailBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceViewRacCorrDetailBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceViewRacCorrDetailBean invoiceViewRacCorrDetailBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_LINE + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_CAT_PART_NO + "," +
     ATTRIBUTE_INVOICE_QUANTITY + "," +
     ATTRIBUTE_ORDERED_QUANTITY + "," +
     ATTRIBUTE_DATE_DELIVERED + "," +
     ATTRIBUTE_PRICE + "," +
     ATTRIBUTE_TOTAL_ADD_CHARGE + "," +
     ATTRIBUTE_NET_AMOUNT + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "," +
     ATTRIBUTE_INVOICE_DATE + "," +
     ATTRIBUTE_INVOICE_PERIOD + ")" +
   values (
       StringHandler.nullIfZero(invoiceViewRacCorrDetailBean.getInvoice()) + "," +
       StringHandler.nullIfZero(invoiceViewRacCorrDetailBean.getInvoiceLine()) + "," +
       SqlHandler.delimitString(invoiceViewRacCorrDetailBean.getPoNumber()) + "," +
       SqlHandler.delimitString(invoiceViewRacCorrDetailBean.getCatPartNo()) + "," +
     StringHandler.nullIfZero(invoiceViewRacCorrDetailBean.getInvoiceQuantity()) + "," +
     StringHandler.nullIfZero(invoiceViewRacCorrDetailBean.getOrderedQuantity()) + "," +
     DateHandler.getOracleToDateFunction(invoiceViewRacCorrDetailBean.getDateDelivered()) + "," +
     StringHandler.nullIfZero(invoiceViewRacCorrDetailBean.getPrice()) + "," +
     StringHandler.nullIfZero(invoiceViewRacCorrDetailBean.getTotalAddCharge()) + "," +
       StringHandler.nullIfZero(invoiceViewRacCorrDetailBean.getNetAmount()) + "," +
     StringHandler.nullIfZero(invoiceViewRacCorrDetailBean.getInvoiceAmount()) + "," +
     DateHandler.getOracleToDateFunction(invoiceViewRacCorrDetailBean.getInvoiceDate()) + "," +
       StringHandler.nullIfZero(invoiceViewRacCorrDetailBean.getInvoicePeriod()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceViewRacCorrDetailBean invoiceViewRacCorrDetailBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceViewRacCorrDetailBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceViewRacCorrDetailBean invoiceViewRacCorrDetailBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
       StringHandler.nullIfZero(invoiceViewRacCorrDetailBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_LINE + "=" +
       StringHandler.nullIfZero(invoiceViewRacCorrDetailBean.getInvoiceLine()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
       SqlHandler.delimitString(invoiceViewRacCorrDetailBean.getPoNumber()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
       SqlHandler.delimitString(invoiceViewRacCorrDetailBean.getCatPartNo()) + "," +
     ATTRIBUTE_INVOICE_QUANTITY + "=" +
      StringHandler.nullIfZero(invoiceViewRacCorrDetailBean.getInvoiceQuantity()) + "," +
     ATTRIBUTE_ORDERED_QUANTITY + "=" +
      StringHandler.nullIfZero(invoiceViewRacCorrDetailBean.getOrderedQuantity()) + "," +
     ATTRIBUTE_DATE_DELIVERED + "=" +
      DateHandler.getOracleToDateFunction(invoiceViewRacCorrDetailBean.getDateDelivered()) + "," +
     ATTRIBUTE_PRICE + "=" +
      StringHandler.nullIfZero(invoiceViewRacCorrDetailBean.getPrice()) + "," +
     ATTRIBUTE_TOTAL_ADD_CHARGE + "=" +
      StringHandler.nullIfZero(invoiceViewRacCorrDetailBean.getTotalAddCharge()) + "," +
     ATTRIBUTE_NET_AMOUNT + "=" +
       StringHandler.nullIfZero(invoiceViewRacCorrDetailBean.getNetAmount()) + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceViewRacCorrDetailBean.getInvoiceAmount()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoiceViewRacCorrDetailBean.getInvoiceDate()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoiceViewRacCorrDetailBean.getInvoicePeriod()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewRacCorrDetailBean.getInvoice());
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

    Collection invoiceViewRacCorrDetailBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewRacCorrDetailBean invoiceViewRacCorrDetailBean = new
          InvoiceViewRacCorrDetailBean();
      load(dataSetRow, invoiceViewRacCorrDetailBean);
      invoiceViewRacCorrDetailBeanColl.add(invoiceViewRacCorrDetailBean);
    }

    return invoiceViewRacCorrDetailBeanColl;
  }
}