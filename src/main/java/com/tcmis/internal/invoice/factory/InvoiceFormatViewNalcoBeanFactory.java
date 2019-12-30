package com.tcmis.internal.invoice.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.Connection;

import java.text.ParseException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.internal.invoice.beans.InvoiceFormatViewNalcoBean;

/******************************************************************************
 * CLASSNAME: InvoiceFormatViewNalcoBeanFactory <br>
 * @version: 1.0, Oct 5, 2007 <br>
 *****************************************************************************/

public class InvoiceFormatViewNalcoBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE_GROUP = "INVOICE_GROUP";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
  public String ATTRIBUTE_QUANTITY = "QUANTITY";
  public String ATTRIBUTE_UOM = "UOM";
  public String ATTRIBUTE_UOM_PRICE = "UOM_PRICE";
  public String ATTRIBUTE_TOTAL_PRICE = "TOTAL_PRICE";

  //table name
  public String TABLE = "INVOICE_FORMAT_VIEW_NALCO";

  //constructor
  public InvoiceFormatViewNalcoBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoiceGroup")) {
      return ATTRIBUTE_INVOICE_GROUP;
    }
    else if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
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
    else if (attributeName.equals("uom")) {
      return ATTRIBUTE_UOM;
    }
    else if (attributeName.equals("uomPrice")) {
      return ATTRIBUTE_UOM_PRICE;
    }
    else if (attributeName.equals("totalPrice")) {
      return ATTRIBUTE_TOTAL_PRICE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceFormatViewNalcoBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceFormatViewNalcoBean invoiceFormatViewNalcoBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoiceGroup", "SearchCriterion.EQUALS",
     "" + invoiceFormatViewNalcoBean.getInvoiceGroup());
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
   public int delete(InvoiceFormatViewNalcoBean invoiceFormatViewNalcoBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoiceGroup", "SearchCriterion.EQUALS",
     "" + invoiceFormatViewNalcoBean.getInvoiceGroup());
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
   public int insert(InvoiceFormatViewNalcoBean invoiceFormatViewNalcoBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceFormatViewNalcoBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceFormatViewNalcoBean invoiceFormatViewNalcoBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE_GROUP + "," +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_DATE + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_CAT_PART_NO + "," +
     ATTRIBUTE_PART_DESCRIPTION + "," +
     ATTRIBUTE_QUANTITY + "," +
     ATTRIBUTE_UOM + "," +
     ATTRIBUTE_UOM_PRICE + "," +
     ATTRIBUTE_TOTAL_PRICE + ")" +
     " values (" +
       SqlHandler.delimitString(invoiceFormatViewNalcoBean.getInvoiceGroup()) + "," +
     invoiceFormatViewNalcoBean.getInvoicePeriod() + "," +
     invoiceFormatViewNalcoBean.getInvoice() + "," +
     DateHandler.getOracleToDateFunction(invoiceFormatViewNalcoBean.getInvoiceDate()) + "," +
       SqlHandler.delimitString(invoiceFormatViewNalcoBean.getFacilityId()) + "," +
     SqlHandler.delimitString(invoiceFormatViewNalcoBean.getPoNumber()) + "," +
       SqlHandler.delimitString(invoiceFormatViewNalcoBean.getCatPartNo()) + "," +
     SqlHandler.delimitString(invoiceFormatViewNalcoBean.getPartDescription()) + "," +
     invoiceFormatViewNalcoBean.getQuantity() + "," +
     SqlHandler.delimitString(invoiceFormatViewNalcoBean.getUom()) + "," +
     invoiceFormatViewNalcoBean.getUomPrice() + "," +
     invoiceFormatViewNalcoBean.getTotalPrice() + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceFormatViewNalcoBean invoiceFormatViewNalcoBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceFormatViewNalcoBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceFormatViewNalcoBean invoiceFormatViewNalcoBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE_GROUP + "=" +
       SqlHandler.delimitString(invoiceFormatViewNalcoBean.getInvoiceGroup()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
       StringHandler.nullIfZero(invoiceFormatViewNalcoBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceFormatViewNalcoBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoiceFormatViewNalcoBean.getInvoiceDate()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
       SqlHandler.delimitString(invoiceFormatViewNalcoBean.getFacilityId()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
       SqlHandler.delimitString(invoiceFormatViewNalcoBean.getPoNumber()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
       SqlHandler.delimitString(invoiceFormatViewNalcoBean.getCatPartNo()) + "," +
     ATTRIBUTE_PART_DESCRIPTION + "=" +
      SqlHandler.delimitString(invoiceFormatViewNalcoBean.getPartDescription()) + "," +
     ATTRIBUTE_QUANTITY + "=" +
       StringHandler.nullIfZero(invoiceFormatViewNalcoBean.getQuantity()) + "," +
     ATTRIBUTE_UOM + "=" +
      SqlHandler.delimitString(invoiceFormatViewNalcoBean.getUom()) + "," +
     ATTRIBUTE_UOM_PRICE + "=" +
       StringHandler.nullIfZero(invoiceFormatViewNalcoBean.getUomPrice()) + "," +
     ATTRIBUTE_TOTAL_PRICE + "=" +
       StringHandler.nullIfZero(invoiceFormatViewNalcoBean.getTotalPrice()) + " " +
     "where " + ATTRIBUTE_INVOICE_GROUP + "=" +
      invoiceFormatViewNalcoBean.getInvoiceGroup();
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws
      BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(criteria, sortCriteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria,
                           Connection conn) throws BaseException {
    Collection invoiceFormatViewNalcoBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceFormatViewNalcoBean invoiceFormatViewNalcoBean = new
          InvoiceFormatViewNalcoBean();
      load(dataSetRow, invoiceFormatViewNalcoBean);
      invoiceFormatViewNalcoBeanColl.add(invoiceFormatViewNalcoBean);
    }
    return invoiceFormatViewNalcoBeanColl;
  }

  public Collection selectNormalized(SearchCriteria criteria, SortCriteria sortCriteria) throws
      BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectNormalized(criteria, sortCriteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectNormalized(SearchCriteria criteria, SortCriteria sortCriteria,
                           Connection conn) throws BaseException {
    Collection invoiceFormatViewNalcoBeanColl = new Vector();
    String query = "select unique invoice from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceFormatViewNalcoBean invoiceFormatViewNalcoBean = new
          InvoiceFormatViewNalcoBean();
      load(dataSetRow, invoiceFormatViewNalcoBean);
      //now add child records
      SearchCriteria criteria2 = new SearchCriteria("invoice", SearchCriterion.EQUALS, invoiceFormatViewNalcoBean.getInvoice().toString());
      String query2 = "select * from " + TABLE + " " +
        getWhereClause(criteria2) + getOrderByClause(sortCriteria);
      //String query2 = "select * from " + TABLE + " where invoice=" +invoiceFormatViewStarkBean.getInvoice();
      if (log.isDebugEnabled()) {
        log.debug("QUERY:" + query2);
      }
      DataSet dataSet2 = new SqlManager().select(conn, query2);
      Iterator dataIter2 = dataSet2.iterator();
      while (dataIter2.hasNext()) {
        DataSetRow dataSetRow2 = (DataSetRow) dataIter2.next();
        InvoiceFormatViewNalcoBean invoiceFormatViewNalcoBean2 = new
            InvoiceFormatViewNalcoBean();
        load(dataSetRow2, invoiceFormatViewNalcoBean2);
        invoiceFormatViewNalcoBean.addLineBean(invoiceFormatViewNalcoBean2);
      }
      invoiceFormatViewNalcoBeanColl.add(invoiceFormatViewNalcoBean);
    }
    return invoiceFormatViewNalcoBeanColl;
  }
}