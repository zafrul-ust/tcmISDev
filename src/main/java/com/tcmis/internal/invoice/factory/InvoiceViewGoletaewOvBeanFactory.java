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
import com.tcmis.internal.invoice.beans.InvoiceViewGoletaewOvBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewGoletaewOvBeanFactory <br>
 * @version: 1.0, Mar 4, 2005 <br>
 *****************************************************************************/

public class InvoiceViewGoletaewOvBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_LINE = "INVOICE_LINE";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_INVOICE_QUANTITY = "INVOICE_QUANTITY";
  public String ATTRIBUTE_REQUESTED_QUANTITY = "REQUESTED_QUANTITY";
  public String ATTRIBUTE_INVOICE_UNIT_PRICE = "INVOICE_UNIT_PRICE";
  public String ATTRIBUTE_UNIT_REBATE = "UNIT_REBATE";
  public String ATTRIBUTE_EXTENDED_PRICE = "EXTENDED_PRICE";
  public String ATTRIBUTE_REBATE = "REBATE";
  public String ATTRIBUTE_NET_AMOUNT = "NET_AMOUNT";
  public String ATTRIBUTE_TOTAL_ADD_CHARGE = "TOTAL_ADD_CHARGE";
  public String ATTRIBUTE_SERVICE_FEE = "SERVICE_FEE";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
  public String ATTRIBUTE_REQUESTOR_NAME = "REQUESTOR_NAME";
  public String ATTRIBUTE_REQUEST_NUMBER = "REQUEST_NUMBER";
  public String ATTRIBUTE_NET_EXT_PRICE = "NET_EXT_PRICE";
  public String ATTRIBUTE_FINAL_EXT_PRICE = "FINAL_EXT_PRICE";
  public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
  public String ATTRIBUTE_MFG_DESC = "MFG_DESC";
  public String ATTRIBUTE_SERVICE_FEE_PERCENT = "SERVICE_FEE_PERCENT";
  public String ATTRIBUTE_INVOICE_SUPPLIER = "INVOICE_SUPPLIER";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_DETAIL = "DETAIL";

  //table name
  public String TABLE = "INVOICE_VIEW_GOLETAEW_OV";

  //constructor
  public InvoiceViewGoletaewOvBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("invoiceQuantity")) {
      return ATTRIBUTE_INVOICE_QUANTITY;
    }
    else if (attributeName.equals("requestedQuantity")) {
      return ATTRIBUTE_REQUESTED_QUANTITY;
    }
    else if (attributeName.equals("invoiceUnitPrice")) {
      return ATTRIBUTE_INVOICE_UNIT_PRICE;
    }
    else if (attributeName.equals("unitRebate")) {
      return ATTRIBUTE_UNIT_REBATE;
    }
    else if (attributeName.equals("extendedPrice")) {
      return ATTRIBUTE_EXTENDED_PRICE;
    }
    else if (attributeName.equals("rebate")) {
      return ATTRIBUTE_REBATE;
    }
    else if (attributeName.equals("netAmount")) {
      return ATTRIBUTE_NET_AMOUNT;
    }
    else if (attributeName.equals("totalAddCharge")) {
      return ATTRIBUTE_TOTAL_ADD_CHARGE;
    }
    else if (attributeName.equals("serviceFee")) {
      return ATTRIBUTE_SERVICE_FEE;
    }
    else if (attributeName.equals("partDescription")) {
      return ATTRIBUTE_PART_DESCRIPTION;
    }
    else if (attributeName.equals("requestorName")) {
      return ATTRIBUTE_REQUESTOR_NAME;
    }
    else if (attributeName.equals("requestNumber")) {
      return ATTRIBUTE_REQUEST_NUMBER;
    }
    else if (attributeName.equals("netExtPrice")) {
      return ATTRIBUTE_NET_EXT_PRICE;
    }
    else if (attributeName.equals("finalExtPrice")) {
      return ATTRIBUTE_FINAL_EXT_PRICE;
    }
    else if (attributeName.equals("invoiceAmount")) {
      return ATTRIBUTE_INVOICE_AMOUNT;
    }
    else if (attributeName.equals("mfgDesc")) {
      return ATTRIBUTE_MFG_DESC;
    }
    else if (attributeName.equals("serviceFeePercent")) {
      return ATTRIBUTE_SERVICE_FEE_PERCENT;
    }
    else if (attributeName.equals("invoiceSupplier")) {
      return ATTRIBUTE_INVOICE_SUPPLIER;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
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
    return super.getType(attributeName, InvoiceViewGoletaewOvBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceViewGoletaewOvBean invoiceViewGoletaewOvBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewGoletaewOvBean.getInvoice());
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
   public int delete(InvoiceViewGoletaewOvBean invoiceViewGoletaewOvBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewGoletaewOvBean.getInvoice());
    return delete(criteria, conn);
   }
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
   */
//you need to verify the primary key(s) before uncommenting this
  /*
//insert
   public int insert(InvoiceViewGoletaewOvBean invoiceViewGoletaewOvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceViewGoletaewOvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceViewGoletaewOvBean invoiceViewGoletaewOvBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_LINE + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_CAT_PART_NO + "," +
     ATTRIBUTE_INVOICE_QUANTITY + "," +
     ATTRIBUTE_REQUESTED_QUANTITY + "," +
     ATTRIBUTE_INVOICE_UNIT_PRICE + "," +
     ATTRIBUTE_UNIT_REBATE + "," +
     ATTRIBUTE_EXTENDED_PRICE + "," +
     ATTRIBUTE_REBATE + "," +
     ATTRIBUTE_NET_AMOUNT + "," +
     ATTRIBUTE_TOTAL_ADD_CHARGE + "," +
     ATTRIBUTE_SERVICE_FEE + "," +
     ATTRIBUTE_PART_DESCRIPTION + "," +
     ATTRIBUTE_REQUESTOR_NAME + "," +
     ATTRIBUTE_REQUEST_NUMBER + "," +
     ATTRIBUTE_NET_EXT_PRICE + "," +
     ATTRIBUTE_FINAL_EXT_PRICE + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "," +
     ATTRIBUTE_MFG_DESC + "," +
     ATTRIBUTE_SERVICE_FEE_PERCENT + "," +
     ATTRIBUTE_INVOICE_SUPPLIER + "," +
     ATTRIBUTE_INVOICE_DATE + "," +
     ATTRIBUTE_DETAIL + ")" +
   values (
     StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getInvoice()) + "," +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getInvoiceLine()) + "," +
     SqlHandler.delimitString(invoiceViewGoletaewOvBean.getPoNumber()) + "," +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getInvoicePeriod()) + "," +
     SqlHandler.delimitString(invoiceViewGoletaewOvBean.getCatPartNo()) + "," +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getInvoiceQuantity()) + "," +
     StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getRequestedQuantity()) + "," +
     StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getInvoiceUnitPrice()) + "," +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getUnitRebate()) + "," +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getExtendedPrice()) + "," +
     StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getRebate()) + "," +
     StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getNetAmount()) + "," +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getTotalAddCharge()) + "," +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getServiceFee()) + "," +
       SqlHandler.delimitString(invoiceViewGoletaewOvBean.getPartDescription()) + "," +
       SqlHandler.delimitString(invoiceViewGoletaewOvBean.getRequestorName()) + "," +
       SqlHandler.delimitString(invoiceViewGoletaewOvBean.getRequestNumber()) + "," +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getNetExtPrice()) + "," +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getFinalExtPrice()) + "," +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getInvoiceAmount()) + "," +
     SqlHandler.delimitString(invoiceViewGoletaewOvBean.getMfgDesc()) + "," +
     StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getServiceFeePercent()) + "," +
       SqlHandler.delimitString(invoiceViewGoletaewOvBean.getInvoiceSupplier()) + "," +
     DateHandler.getOracleToDateFunction(invoiceViewGoletaewOvBean.getInvoiceDate()) + "," +
     SqlHandler.delimitString(invoiceViewGoletaewOvBean.getDetail()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceViewGoletaewOvBean invoiceViewGoletaewOvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceViewGoletaewOvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceViewGoletaewOvBean invoiceViewGoletaewOvBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_LINE + "=" +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getInvoiceLine()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
      SqlHandler.delimitString(invoiceViewGoletaewOvBean.getPoNumber()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
       SqlHandler.delimitString(invoiceViewGoletaewOvBean.getCatPartNo()) + "," +
     ATTRIBUTE_INVOICE_QUANTITY + "=" +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getInvoiceQuantity()) + "," +
     ATTRIBUTE_REQUESTED_QUANTITY + "=" +
      StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getRequestedQuantity()) + "," +
     ATTRIBUTE_INVOICE_UNIT_PRICE + "=" +
      StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getInvoiceUnitPrice()) + "," +
     ATTRIBUTE_UNIT_REBATE + "=" +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getUnitRebate()) + "," +
     ATTRIBUTE_EXTENDED_PRICE + "=" +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getExtendedPrice()) + "," +
     ATTRIBUTE_REBATE + "=" +
      StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getRebate()) + "," +
     ATTRIBUTE_NET_AMOUNT + "=" +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getNetAmount()) + "," +
     ATTRIBUTE_TOTAL_ADD_CHARGE + "=" +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getTotalAddCharge()) + "," +
     ATTRIBUTE_SERVICE_FEE + "=" +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getServiceFee()) + "," +
     ATTRIBUTE_PART_DESCRIPTION + "=" +
       SqlHandler.delimitString(invoiceViewGoletaewOvBean.getPartDescription()) + "," +
     ATTRIBUTE_REQUESTOR_NAME + "=" +
       SqlHandler.delimitString(invoiceViewGoletaewOvBean.getRequestorName()) + "," +
     ATTRIBUTE_REQUEST_NUMBER + "=" +
       SqlHandler.delimitString(invoiceViewGoletaewOvBean.getRequestNumber()) + "," +
     ATTRIBUTE_NET_EXT_PRICE + "=" +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getNetExtPrice()) + "," +
     ATTRIBUTE_FINAL_EXT_PRICE + "=" +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getFinalExtPrice()) + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "=" +
       StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getInvoiceAmount()) + "," +
     ATTRIBUTE_MFG_DESC + "=" +
      SqlHandler.delimitString(invoiceViewGoletaewOvBean.getMfgDesc()) + "," +
     ATTRIBUTE_SERVICE_FEE_PERCENT + "=" +
      StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getServiceFeePercent()) + "," +
     ATTRIBUTE_INVOICE_SUPPLIER + "=" +
       SqlHandler.delimitString(invoiceViewGoletaewOvBean.getInvoiceSupplier()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoiceViewGoletaewOvBean.getInvoiceDate()) + "," +
     ATTRIBUTE_DETAIL + "=" +
      SqlHandler.delimitString(invoiceViewGoletaewOvBean.getDetail()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewGoletaewOvBean.getInvoice());
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

    Collection invoiceViewGoletaewOvBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if (log.isDebugEnabled()) {
      log.debug("query:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewGoletaewOvBean invoiceViewGoletaewOvBean = new
          InvoiceViewGoletaewOvBean();
      load(dataSetRow, invoiceViewGoletaewOvBean);
      invoiceViewGoletaewOvBeanColl.add(invoiceViewGoletaewOvBean);
    }

    return invoiceViewGoletaewOvBeanColl;
  }

  public Collection selectObject(SearchCriteria criteria) throws BaseException,
      Exception {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      java.util.Map map = connection.getTypeMap();
      map.put("INVOICE_VIEW_GOLETAEW_DET_OBJ",
              Class.forName(
          "com.tcmis.internal.invoice.beans.InvoiceViewGoletaewOvBean"));
      map.put("INVOICE_ADD_CHARGE_DETAIL_OBJ",
              Class.forName(
          "com.tcmis.internal.invoice.beans.InvoiceAddChargeDetailBean"));

      c = selectObject(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectObject(SearchCriteria criteria, Connection conn) throws
      BaseException, Exception {

    Collection invoiceViewGoletaewBeanColl = new Vector();

    String query = "select value(p) from " + TABLE + " p " +
        getWhereClause(criteria);
    if (log.isDebugEnabled()) {
      log.debug("query:" + query);
    }
    Statement st = conn.createStatement();
    ResultSet rs = st.executeQuery(query);
    while (rs.next()) {
      InvoiceViewGoletaewOvBean b = (InvoiceViewGoletaewOvBean) rs.getObject(1);
      invoiceViewGoletaewBeanColl.add(b);
    }
    rs.close();
    st.close();
    return invoiceViewGoletaewBeanColl;
  }

  public Collection selectDetail(int invoicePeriod) throws
      BaseException, Exception {
    Connection connection = null;
    Collection invoiceViewGoletaewDetailBeanColl = new Vector();
    try {
      connection = this.getDbManager().getConnection();
      java.util.Map map = connection.getTypeMap();
      map.put("INVOICE_VIEW_GOLETAEW_DET_OBJ",
              Class.forName(
          "com.tcmis.internal.invoice.beans.InvoiceViewGoletaewOvBean"));
      map.put("INVOICE_ADD_CHARGE_DETAIL_OBJ",
              Class.forName(
          "com.tcmis.internal.invoice.beans.InvoiceAddChargeDetailBean"));

      String query = "select value(p) from " + TABLE +
          " p where invoice_period=" +
          invoicePeriod +
          " and invoice not in (select invoice from invoice_view_goletaew where invoice_period=" +
          invoicePeriod + ")";
      if (log.isDebugEnabled()) {
        log.debug("QUERY:" + query);
      }

      Statement st = connection.createStatement();
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        InvoiceViewGoletaewOvBean b = (InvoiceViewGoletaewOvBean) rs.getObject(
            1);
        invoiceViewGoletaewDetailBeanColl.add(b);
      }
      rs.close();
      st.close();

    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return invoiceViewGoletaewDetailBeanColl;
  }

}