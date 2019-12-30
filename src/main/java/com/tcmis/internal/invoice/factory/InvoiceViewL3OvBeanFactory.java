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
import com.tcmis.internal.invoice.beans.InvoiceViewL3OvBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewL3OvBeanFactory <br>
 * @version: 1.0, May 16, 2005 <br>
 *****************************************************************************/

public class InvoiceViewL3OvBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_DETAIL = "DETAIL";

  //table name
  public String TABLE = "INVOICE_VIEW_L3_OV";

  //constructor
  public InvoiceViewL3OvBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("accountNumber")) {
      return ATTRIBUTE_ACCOUNT_NUMBER;
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
    return super.getType(attributeName, InvoiceViewL3OvBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceViewL3OvBean invoiceViewL3OvBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewL3OvBean.getInvoice());
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
   public int delete(InvoiceViewL3OvBean invoiceViewL3OvBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewL3OvBean.getInvoice());
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
   public int insert(InvoiceViewL3OvBean invoiceViewL3OvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceViewL3OvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceViewL3OvBean invoiceViewL3OvBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "," +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_DETAIL + ")" +
     " values (" +
     invoiceViewL3OvBean.getInvoice() + "," +
     SqlHandler.delimitString(invoiceViewL3OvBean.getAccountNumber()) + "," +
     invoiceViewL3OvBean.getInvoicePeriod() + "," +
     SqlHandler.delimitString(invoiceViewL3OvBean.getDetail()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceViewL3OvBean invoiceViewL3OvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceViewL3OvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceViewL3OvBean invoiceViewL3OvBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewL3OvBean.getInvoice()) + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "=" +
      SqlHandler.delimitString(invoiceViewL3OvBean.getAccountNumber()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoiceViewL3OvBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_DETAIL + "=" +
      SqlHandler.delimitString(invoiceViewL3OvBean.getDetail()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      invoiceViewL3OvBean.getInvoice();
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

    Collection invoiceViewL3OvBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewL3OvBean invoiceViewL3OvBean = new InvoiceViewL3OvBean();
      load(dataSetRow, invoiceViewL3OvBean);
      invoiceViewL3OvBeanColl.add(invoiceViewL3OvBean);
    }

    return invoiceViewL3OvBeanColl;
  }

  public Collection selectObject(SearchCriteria criteria) throws BaseException,
      Exception {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      java.util.Map map = connection.getTypeMap();
      map.put("INVOICE_VIEW_L3_OBJ",
              Class.forName(
          "com.tcmis.internal.invoice.beans.InvoiceViewL3OvBean"));
      map.put("INVOICE_VIEW_L3_DETAIL_OBJ",
              Class.forName(
          "com.tcmis.internal.invoice.beans.InvoiceViewL3DetailBean"));

      c = selectObject(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectObject(SearchCriteria criteria, Connection conn) throws
      BaseException, Exception {

    Collection invoiceViewL3BeanColl = new Vector();

    String query = "select value(p) from " + TABLE + " p " +
        getWhereClause(criteria);
    //System.out.println("QUERY:" + query);
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    Statement st = conn.createStatement();
    ResultSet rs = st.executeQuery(query);
    while (rs.next()) {
      InvoiceViewL3OvBean b = (InvoiceViewL3OvBean) rs.getObject(1);
      invoiceViewL3BeanColl.add(b);
    }
    rs.close();
    st.close();
    return invoiceViewL3BeanColl;
  }

}