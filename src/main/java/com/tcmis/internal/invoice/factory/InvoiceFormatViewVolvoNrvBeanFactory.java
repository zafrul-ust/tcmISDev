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
import com.tcmis.internal.invoice.beans.InvoiceFormatViewVolvoNrvBean;

/******************************************************************************
 * CLASSNAME: InvoiceFormatViewVolvoNrvBeanFactory <br>
 * @version: 1.0, Jun 28, 2007 <br>
 *****************************************************************************/

public class InvoiceFormatViewVolvoNrvBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE_GROUP = "INVOICE_GROUP";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
  public String ATTRIBUTE_QUANTITY = "QUANTITY";
  public String ATTRIBUTE_UOM = "UOM";
  public String ATTRIBUTE_UOM_PRICE = "UOM_PRICE";
  public String ATTRIBUTE_TOTAL_PRICE = "TOTAL_PRICE";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_HAAS_PO = "HAAS_PO";

  //table name
  public String TABLE = "INVOICE_FORMAT_VIEW_VOLVO_NRV";

  //constructor
  public InvoiceFormatViewVolvoNrvBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
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
    else if (attributeName.equals("catPartNo")) {
        return ATTRIBUTE_CAT_PART_NO;
      }
    else if (attributeName.equals("haasPo")) {
        return ATTRIBUTE_HAAS_PO;
      }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceFormatViewVolvoNrvBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
       public int delete(InvoiceFormatViewVolvoNrvBean invoiceFormatViewVolvoNrvBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoiceGroup", "SearchCriterion.EQUALS",
     "" + invoiceFormatViewVolvoNrvBean.getInvoiceGroup());
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
   public int delete(InvoiceFormatViewVolvoNrvBean invoiceFormatViewVolvoNrvBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoiceGroup", "SearchCriterion.EQUALS",
     "" + invoiceFormatViewVolvoNrvBean.getInvoiceGroup());
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
       public int insert(InvoiceFormatViewVolvoNrvBean invoiceFormatViewVolvoNrvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceFormatViewVolvoNrvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceFormatViewVolvoNrvBean invoiceFormatViewVolvoNrvBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE_GROUP + "," +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_DATE + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_PART_DESCRIPTION + "," +
     ATTRIBUTE_QUANTITY + "," +
     ATTRIBUTE_UOM + "," +
     ATTRIBUTE_UOM_PRICE + "," +
     ATTRIBUTE_TOTAL_PRICE + ")" +
     " values (" +
     SqlHandler.delimitString(invoiceFormatViewVolvoNrvBean.getInvoiceGroup()) + "," +
     invoiceFormatViewVolvoNrvBean.getInvoicePeriod() + "," +
     invoiceFormatViewVolvoNrvBean.getInvoice() + "," +
     DateHandler.getOracleToDateFunction(invoiceFormatViewVolvoNrvBean.getInvoiceDate()) + "," +
       SqlHandler.delimitString(invoiceFormatViewVolvoNrvBean.getPoNumber()) + "," +
     SqlHandler.delimitString(invoiceFormatViewVolvoNrvBean.getPartDescription()) + "," +
     invoiceFormatViewVolvoNrvBean.getQuantity() + "," +
     SqlHandler.delimitString(invoiceFormatViewVolvoNrvBean.getUom()) + "," +
     invoiceFormatViewVolvoNrvBean.getUomPrice() + "," +
     invoiceFormatViewVolvoNrvBean.getTotalPrice() + ")";
    return sqlManager.update(conn, query);
   }
//update
       public int update(InvoiceFormatViewVolvoNrvBean invoiceFormatViewVolvoNrvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceFormatViewVolvoNrvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceFormatViewVolvoNrvBean invoiceFormatViewVolvoNrvBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE_GROUP + "=" +
      SqlHandler.delimitString(invoiceFormatViewVolvoNrvBean.getInvoiceGroup()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoiceFormatViewVolvoNrvBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_INVOICE + "=" +
       StringHandler.nullIfZero(invoiceFormatViewVolvoNrvBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoiceFormatViewVolvoNrvBean.getInvoiceDate()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
       SqlHandler.delimitString(invoiceFormatViewVolvoNrvBean.getPoNumber()) + "," +
     ATTRIBUTE_PART_DESCRIPTION + "=" +
      SqlHandler.delimitString(invoiceFormatViewVolvoNrvBean.getPartDescription()) + "," +
     ATTRIBUTE_QUANTITY + "=" +
       StringHandler.nullIfZero(invoiceFormatViewVolvoNrvBean.getQuantity()) + "," +
     ATTRIBUTE_UOM + "=" +
      SqlHandler.delimitString(invoiceFormatViewVolvoNrvBean.getUom()) + "," +
     ATTRIBUTE_UOM_PRICE + "=" +
       StringHandler.nullIfZero(invoiceFormatViewVolvoNrvBean.getUomPrice()) + "," +
     ATTRIBUTE_TOTAL_PRICE + "=" +
       StringHandler.nullIfZero(invoiceFormatViewVolvoNrvBean.getTotalPrice()) + " " +
     "where " + ATTRIBUTE_INVOICE_GROUP + "=" +
      invoiceFormatViewVolvoNrvBean.getInvoiceGroup();
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
    Collection invoiceFormatViewVolvoNrvBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceFormatViewVolvoNrvBean invoiceFormatViewVolvoNrvBean = new
          InvoiceFormatViewVolvoNrvBean();
      load(dataSetRow, invoiceFormatViewVolvoNrvBean);
      invoiceFormatViewVolvoNrvBeanColl.add(invoiceFormatViewVolvoNrvBean);
    }
    return invoiceFormatViewVolvoNrvBeanColl;
  }
}