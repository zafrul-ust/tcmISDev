package com.tcmis.internal.invoice.factory;

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
import com.tcmis.internal.invoice.beans.InvoiceViewSyracuseOvBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewSyracuseOvBeanFactory <br>
 * @version: 1.0, Mar 16, 2005 <br>
 *****************************************************************************/

public class InvoiceViewSyracuseOvBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_DETAIL = "DETAIL";

  //table name
  public String TABLE = "INVOICE_VIEW_SYRACUSE_OV";

  //constructor
  public InvoiceViewSyracuseOvBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
    }
    else if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else if (attributeName.equals("detail")) {
      return ATTRIBUTE_DETAIL;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceViewSyracuseOvBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceViewSyracuseOvBean invoiceViewSyracuseOvBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewSyracuseOvBean.getInvoice());
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
   public int delete(InvoiceViewSyracuseOvBean invoiceViewSyracuseOvBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewSyracuseOvBean.getInvoice());
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
   public int insert(InvoiceViewSyracuseOvBean invoiceViewSyracuseOvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceViewSyracuseOvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceViewSyracuseOvBean invoiceViewSyracuseOvBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_DETAIL + ")" +
   values (
     StringHandler.nullIfZero(invoiceViewSyracuseOvBean.getInvoice()) + "," +
     SqlHandler.delimitString(invoiceViewSyracuseOvBean.getPoNumber()) + "," +
       StringHandler.nullIfZero(invoiceViewSyracuseOvBean.getInvoicePeriod()) + "," +
     SqlHandler.delimitString(invoiceViewSyracuseOvBean.getDetail()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceViewSyracuseOvBean invoiceViewSyracuseOvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceViewSyracuseOvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceViewSyracuseOvBean invoiceViewSyracuseOvBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewSyracuseOvBean.getInvoice()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
      SqlHandler.delimitString(invoiceViewSyracuseOvBean.getPoNumber()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
       StringHandler.nullIfZero(invoiceViewSyracuseOvBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_DETAIL + "=" +
      SqlHandler.delimitString(invoiceViewSyracuseOvBean.getDetail()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewSyracuseOvBean.getInvoice());
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

    Collection invoiceViewSyracuseOvBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewSyracuseOvBean invoiceViewSyracuseOvBean = new
          InvoiceViewSyracuseOvBean();
      load(dataSetRow, invoiceViewSyracuseOvBean);
      invoiceViewSyracuseOvBeanColl.add(invoiceViewSyracuseOvBean);
    }

    return invoiceViewSyracuseOvBeanColl;
  }

  public Collection selectObject(SearchCriteria criteria) throws BaseException,
      Exception {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      java.util.Map map = connection.getTypeMap();
      map.put("INVOICE_VIEW_SYRACUSE_OBJ",
              Class.forName(
          "com.tcmis.internal.invoice.beans.InvoiceViewSyracuseOvBean"));
      map.put("INVOICE_VIEW_SYRACUSE_DET_OBJ",
              Class.forName(
          "com.tcmis.internal.invoice.beans.InvoiceViewSyracuseDetailBean"));

      c = selectObject(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectObject(SearchCriteria criteria, Connection conn) throws
      BaseException, Exception {

    Collection invoiceViewSyracuseBeanColl = new Vector();

    String query = "select value(p) from " + TABLE + " p " +
        getWhereClause(criteria);
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    Statement st = conn.createStatement();
    ResultSet rs = st.executeQuery(query);
    while (rs.next()) {
      InvoiceViewSyracuseOvBean b = (InvoiceViewSyracuseOvBean) rs.getObject(1);
      invoiceViewSyracuseBeanColl.add(b);
    }
    rs.close();
    st.close();
    return invoiceViewSyracuseBeanColl;
  }

}