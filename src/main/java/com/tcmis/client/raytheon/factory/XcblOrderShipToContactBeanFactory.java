package com.tcmis.client.raytheon.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.raytheon.beans.XcblOrderShipToContactBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SqlHandler;

/******************************************************************************
 * CLASSNAME: XcblOrderShipToContactBeanFactory <br>
 * @version: 1.0, Aug 2, 2005 <br>
 *****************************************************************************/

public class XcblOrderShipToContactBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_ID = "ID";
  public String ATTRIBUTE_XCBL_ORDER_ID = "XCBL_ORDER_ID";
  public String ATTRIBUTE_KEY = "KEY";
  public String ATTRIBUTE_VALUE = "VALUE";

  //table name
  public String TABLE = "XCBL_ORDER_SHIP_TO_CONTACT";

  //constructor
  public XcblOrderShipToContactBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("id")) {
      return ATTRIBUTE_ID;
    }
    else if (attributeName.equals("xcblOrderId")) {
      return ATTRIBUTE_XCBL_ORDER_ID;
    }
    else if (attributeName.equals("key")) {
      return ATTRIBUTE_KEY;
    }
    else if (attributeName.equals("value")) {
      return ATTRIBUTE_VALUE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, XcblOrderShipToContactBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(XcblOrderShipToContactBean xcblOrderShipToContactBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("id", "SearchCriterion.EQUALS",
     "" + xcblOrderShipToContactBean.getId());
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
       public int delete(XcblOrderShipToContactBean xcblOrderShipToContactBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("id", "SearchCriterion.EQUALS",
     "" + xcblOrderShipToContactBean.getId());
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

  public int delete(SearchCriteria criteria, Connection conn) throws BaseException {
    String sqlQuery = " delete from " + TABLE + " " +
        getWhereClause(criteria);
    return new SqlManager().update(conn, sqlQuery);
  }

  //insert
  public int insert(XcblOrderShipToContactBean xcblOrderShipToContactBean) throws
      BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(xcblOrderShipToContactBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(XcblOrderShipToContactBean xcblOrderShipToContactBean,
                    Connection conn) throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_ID + "," +
        ATTRIBUTE_XCBL_ORDER_ID + "," +
        ATTRIBUTE_KEY + "," +
        ATTRIBUTE_VALUE + ")" +
        " values (" +
        xcblOrderShipToContactBean.getId() + "," +
        xcblOrderShipToContactBean.getXcblOrderId() + "," +
        SqlHandler.delimitString(xcblOrderShipToContactBean.getKey()) + "," +
        SqlHandler.delimitString(xcblOrderShipToContactBean.getValue()) + ")";
    return sqlManager.update(conn, query);
  }

  /*
//update
   public int update(XcblOrderShipToContactBean xcblOrderShipToContactBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(xcblOrderShipToContactBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int update(XcblOrderShipToContactBean xcblOrderShipToContactBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_ID + "=" +
      StringHandler.nullIfZero(xcblOrderShipToContactBean.getId()) + "," +
     ATTRIBUTE_XCBL_ORDER_ID + "=" +
      StringHandler.nullIfZero(xcblOrderShipToContactBean.getXcblOrderId()) + "," +
     ATTRIBUTE_KEY + "=" +
      SqlHandler.delimitString(xcblOrderShipToContactBean.getKey()) + "," +
     ATTRIBUTE_VALUE + "=" +
      SqlHandler.delimitString(xcblOrderShipToContactBean.getValue()) + " " +
     "where " + ATTRIBUTE_ID + "=" +
      xcblOrderShipToContactBean.getId();
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

  public Collection select(SearchCriteria criteria, Connection conn) throws BaseException {
    Collection xcblOrderShipToContactBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      XcblOrderShipToContactBean xcblOrderShipToContactBean = new
          XcblOrderShipToContactBean();
      load(dataSetRow, xcblOrderShipToContactBean);
      xcblOrderShipToContactBeanColl.add(xcblOrderShipToContactBean);
    }
    return xcblOrderShipToContactBeanColl;
  }
}