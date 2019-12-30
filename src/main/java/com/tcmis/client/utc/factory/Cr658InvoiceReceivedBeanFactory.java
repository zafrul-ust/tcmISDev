package com.tcmis.client.utc.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.utc.beans.Cr658InvoiceReceivedBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SqlHandler;

/******************************************************************************
 * CLASSNAME: Cr658InvoiceReceivedBeanFactory <br>
 * @version: 1.0, May 3, 2005 <br>
 *****************************************************************************/

public class Cr658InvoiceReceivedBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_XBLNR = "XBLNR";
  public String ATTRIBUTE_DATE_RECEIVED = "DATE_RECEIVED";
  public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";

  //table name
  public String TABLE = "CR658_INVOICE_RECEIVED";

  //constructor
  public Cr658InvoiceReceivedBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("xblnr")) {
      return ATTRIBUTE_XBLNR;
    }
    else if (attributeName.equals("dateReceived")) {
      return ATTRIBUTE_DATE_RECEIVED;
    }
    else if (attributeName.equals("invoiceAmount")) {
      return ATTRIBUTE_INVOICE_AMOUNT;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, Cr658InvoiceReceivedBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(Cr658InvoiceReceivedBean cr658InvoiceReceivedBean)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("xblnr", "SearchCriterion.EQUALS",
     "" + cr658InvoiceReceivedBean.getXblnr());
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
   public int delete(Cr658InvoiceReceivedBean cr658InvoiceReceivedBean, Connection conn)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("xblnr", "SearchCriterion.EQUALS",
     "" + cr658InvoiceReceivedBean.getXblnr());
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

//insert
  public int insert(Cr658InvoiceReceivedBean cr658InvoiceReceivedBean) throws
      BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(cr658InvoiceReceivedBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(Cr658InvoiceReceivedBean cr658InvoiceReceivedBean,
                    Connection conn) throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_XBLNR + "," +
        ATTRIBUTE_DATE_RECEIVED + "," +
        ATTRIBUTE_INVOICE_AMOUNT + ")" +
        " values (" +
        SqlHandler.delimitString(cr658InvoiceReceivedBean.getXblnr()) + "," +
        DateHandler.getOracleToDateFunction(cr658InvoiceReceivedBean.
                                            getDateReceived()) + "," +
        cr658InvoiceReceivedBean.getInvoiceAmount() + ")";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    return sqlManager.update(conn, query);
  }

  /*
//update
     public int update(Cr658InvoiceReceivedBean cr658InvoiceReceivedBean)
      throws BaseException {
      Connection connection = null;
      int result = 0;
      try {
       connection = getDbManager().getConnection();
       result = update(cr658InvoiceReceivedBean, connection);
      }
      finally {
       this.getDbManager().returnConnection(connection);
      }
      return result;
     }
     public int update(Cr658InvoiceReceivedBean cr658InvoiceReceivedBean, Connection conn)
      throws BaseException {
      String query  = "update " + TABLE + " set " +
       ATTRIBUTE_XBLNR + "=" +
        SqlHandler.delimitString(cr658InvoiceReceivedBean.getXblnr()) + "," +
       ATTRIBUTE_DATE_RECEIVED + "=" +
        DateHandler.getOracleToDateFunction(cr658InvoiceReceivedBean.getDateReceived()) + "," +
       ATTRIBUTE_INVOICE_AMOUNT + "=" +
       StringHandler.nullIfZero(cr658InvoiceReceivedBean.getInvoiceAmount()) + " " +
       "where " + ATTRIBUTE_XBLNR + "=" +
        cr658InvoiceReceivedBean.getXblnr();
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

    Collection cr658InvoiceReceivedBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      Cr658InvoiceReceivedBean cr658InvoiceReceivedBean = new
          Cr658InvoiceReceivedBean();
      load(dataSetRow, cr658InvoiceReceivedBean);
      cr658InvoiceReceivedBeanColl.add(cr658InvoiceReceivedBean);
    }

    return cr658InvoiceReceivedBeanColl;
  }
}
