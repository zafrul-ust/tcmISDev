package com.tcmis.internal.invoice.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;
import java.math.BigDecimal;
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
import com.tcmis.internal.invoice.beans.InvoiceViewDanaBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewDanaBeanFactory <br>
 * @version: 1.0, Mar 6, 2007 <br>
 *****************************************************************************/

public class InvoiceViewDanaBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_LOCATION_DESC = "LOCATION_DESC";
  public String ATTRIBUTE_ADDRESS_LINE_1 = "ADDRESS_LINE_1";
  public String ATTRIBUTE_ADDRESS_LINE_2 = "ADDRESS_LINE_2";
  public String ATTRIBUTE_ADDRESS_LINE_3 = "ADDRESS_LINE_3";
  public String ATTRIBUTE_CITY = "CITY";
  public String ATTRIBUTE_STATE_ABBREV = "STATE_ABBREV";
  public String ATTRIBUTE_COUNTRY_ABBREV = "COUNTRY_ABBREV";
  public String ATTRIBUTE_ZIP = "ZIP";
  public String ATTRIBUTE_ATTENTION = "ATTENTION";
  public String ATTRIBUTE_INVOICE_INQUIRIES = "INVOICE_INQUIRIES";
  public String ATTRIBUTE_CONTACT_PHONE = "CONTACT_PHONE";
  public String ATTRIBUTE_CONTACT_EMAIL = "CONTACT_EMAIL";
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_INVOICE_GROUP = "INVOICE_GROUP";
  public String ATTRIBUTE_LINE_AMOUNT = "LINE_AMOUNT";
  public String ATTRIBUTE_LINE_DESCRIPTION = "LINE_DESCRIPTION";

  public String ATTRIBUTE_NET_AMOUNT = "NET_AMOUNT";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";

  //table name
  public String TABLE = "INVOICE_VIEW_DANA";

  //constructor
  public InvoiceViewDanaBeanFactory(DbManager dbManager) {
    super(dbManager);
  }
  
  public InvoiceViewDanaBeanFactory(DbManager dbManager,String newTable) {
	super(dbManager);
	if( newTable != null && newTable.length() != 0 ) TABLE = newTable;
  }

  //get column names
  public String getColumnName(String attributeName) {
   if (attributeName.equals("locationDesc")) {
      return ATTRIBUTE_LOCATION_DESC;
    }
    else if (attributeName.equals("addressLine1")) {
      return ATTRIBUTE_ADDRESS_LINE_1;
    }
    else if (attributeName.equals("addressLine2")) {
      return ATTRIBUTE_ADDRESS_LINE_2;
    }
    else if (attributeName.equals("addressLine3")) {
      return ATTRIBUTE_ADDRESS_LINE_3;
    }
    else if (attributeName.equals("city")) {
      return ATTRIBUTE_CITY;
    }
    else if (attributeName.equals("stateAbbrev")) {
        return ATTRIBUTE_STATE_ABBREV;
      }
    else if (attributeName.equals("countryAbbrev")) {
        return ATTRIBUTE_COUNTRY_ABBREV;
      }
    else if (attributeName.equals("zip")) {
        return ATTRIBUTE_ZIP;
      }
    else if (attributeName.equals("attention")) {
        return ATTRIBUTE_ATTENTION;
      }
    else if (attributeName.equals("invoiceInquiries")) {
        return ATTRIBUTE_INVOICE_INQUIRIES;
      }
    else if (attributeName.equals("contactPhone")) {
        return ATTRIBUTE_CONTACT_PHONE;
      }
    else if (attributeName.equals("contactEmail")) {
        return ATTRIBUTE_CONTACT_EMAIL;
      }
	else if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("invoiceAmount")) {
      return ATTRIBUTE_INVOICE_AMOUNT;
    }
    else if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else if (attributeName.equals("invoiceGroup")) {
      return ATTRIBUTE_INVOICE_GROUP;
    }
    else if (attributeName.equals("lineAmount")) {
      return ATTRIBUTE_LINE_AMOUNT;
    }
    else if (attributeName.equals("lineDescription")) {
      return ATTRIBUTE_LINE_DESCRIPTION;
    }
    else if (attributeName.equals("netAmount")) {
      return ATTRIBUTE_NET_AMOUNT;
    }
    else if (attributeName.equals("partDescription")) {
      return ATTRIBUTE_PART_DESCRIPTION;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceViewDanaBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceViewDanaBean invoiceViewDanaBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewDanaBean.getInvoice());
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
   public int delete(InvoiceViewDanaBean invoiceViewDanaBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceViewDanaBean.getInvoice());
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
   public int insert(InvoiceViewDanaBean invoiceViewDanaBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceViewDanaBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceViewDanaBean invoiceViewDanaBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_INVOICE_DATE + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "," +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_INVOICE_GROUP + "," +
     ATTRIBUTE_LINE_AMOUNT + "," +
     ATTRIBUTE_LINE_DESCRIPTION + ")" +
     " values (" +
     invoiceViewDanaBean.getInvoice() + "," +
     SqlHandler.delimitString(invoiceViewDanaBean.getPoNumber()) + "," +
     DateHandler.getOracleToDateFunction(invoiceViewDanaBean.getInvoiceDate()) + "," +
     SqlHandler.delimitString(invoiceViewDanaBean.getFacilityId()) + "," +
     invoiceViewDanaBean.getInvoiceAmount() + "," +
     invoiceViewDanaBean.getInvoicePeriod() + "," +
     SqlHandler.delimitString(invoiceViewDanaBean.getInvoiceGroup()) + "," +
     invoiceViewDanaBean.getLineAmount() + "," +
     SqlHandler.delimitString(invoiceViewDanaBean.getLineDescription()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceViewDanaBean invoiceViewDanaBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceViewDanaBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceViewDanaBean invoiceViewDanaBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceViewDanaBean.getInvoice()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
      SqlHandler.delimitString(invoiceViewDanaBean.getPoNumber()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoiceViewDanaBean.getInvoiceDate()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(invoiceViewDanaBean.getFacilityId()) + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceViewDanaBean.getInvoiceAmount()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoiceViewDanaBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_INVOICE_GROUP + "=" +
      SqlHandler.delimitString(invoiceViewDanaBean.getInvoiceGroup()) + "," +
     ATTRIBUTE_LINE_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceViewDanaBean.getLineAmount()) + "," +
     ATTRIBUTE_LINE_DESCRIPTION + "=" +
       SqlHandler.delimitString(invoiceViewDanaBean.getLineDescription()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      invoiceViewDanaBean.getInvoice();
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
    Collection invoiceViewDanaBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewDanaBean invoiceViewDanaBean = new InvoiceViewDanaBean();
      load(dataSetRow, invoiceViewDanaBean);
      invoiceViewDanaBeanColl.add(invoiceViewDanaBean);
    }
    return invoiceViewDanaBeanColl;
  }

  public Collection selectCgps(BigDecimal invoicePeriod) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectCgps(invoicePeriod, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectCgps(BigDecimal invoicePeriod, Connection conn) throws
      BaseException {

    Collection invoiceViewDanaBeanColl = new Vector();

    String query = "select D.INVOICE,round(D.NET_AMOUNT, 5) NET_AMOUNT,D.PART_DESCRIPTION, round(i.invoice_amount, 5) invoice_amount, i.invoice_date, i.po_number " +
                   "from invoice_detail D, invoice I " +
                   "WHERE D.invoice = I.invoice AND invoice_group='DANA-CGPS' " +
                   "and invoice_period=" + invoicePeriod;
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewDanaBean invoiceViewDanaBean = new InvoiceViewDanaBean();
      load(dataSetRow, invoiceViewDanaBean);
      invoiceViewDanaBeanColl.add(invoiceViewDanaBean);
    }

    return invoiceViewDanaBeanColl;
  }
}