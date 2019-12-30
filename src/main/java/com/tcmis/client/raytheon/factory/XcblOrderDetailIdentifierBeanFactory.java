package com.tcmis.client.raytheon.factory;

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
import com.tcmis.client.raytheon.beans.XcblOrderDetailIdentifierBean;

/******************************************************************************
 * CLASSNAME: XcblOrderDetailIdentifierBeanFactory <br>
 * @version: 1.0, Aug 1, 2005 <br>
 *****************************************************************************/

public class XcblOrderDetailIdentifierBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_ID = "ID";
  public String ATTRIBUTE_XCBL_ORDER_DETAIL_ID = "XCBL_ORDER_DETAIL_ID";
  public String ATTRIBUTE_KEY = "KEY";
  public String ATTRIBUTE_VALUE = "VALUE";

  //table name
  public String TABLE = "XCBL_ORDER_DETAIL_IDENTIFIER";

  //constructor
  public XcblOrderDetailIdentifierBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("id")) {
      return ATTRIBUTE_ID;
    }
    else if (attributeName.equals("xcblOrderDetailId")) {
      return ATTRIBUTE_XCBL_ORDER_DETAIL_ID;
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
    return super.getType(attributeName, XcblOrderDetailIdentifierBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(XcblOrderDetailIdentifierBean xcblOrderDetailIdentifierBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("id", "SearchCriterion.EQUALS",
     "" + xcblOrderDetailIdentifierBean.getId());
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
   public int delete(XcblOrderDetailIdentifierBean xcblOrderDetailIdentifierBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("id", "SearchCriterion.EQUALS",
     "" + xcblOrderDetailIdentifierBean.getId());
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

//you need to verify the primary key(s) before uncommenting this

  //insert
  public int insert(XcblOrderDetailIdentifierBean xcblOrderDetailIdentifierBean) throws
      BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(xcblOrderDetailIdentifierBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(XcblOrderDetailIdentifierBean xcblOrderDetailIdentifierBean,
                    Connection conn) throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_ID + "," +
        ATTRIBUTE_XCBL_ORDER_DETAIL_ID + "," +
        ATTRIBUTE_KEY + "," +
        ATTRIBUTE_VALUE + ")" +
        " values (" +
        xcblOrderDetailIdentifierBean.getId() + "," +
        xcblOrderDetailIdentifierBean.getXcblOrderDetailId() + "," +
        SqlHandler.delimitString(xcblOrderDetailIdentifierBean.getKey()) + "," +
        SqlHandler.delimitString(xcblOrderDetailIdentifierBean.getValue()) + ")";
    return sqlManager.update(conn, query);
  }

  /*
//update
   public int update(XcblOrderDetailIdentifierBean xcblOrderDetailIdentifierBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(xcblOrderDetailIdentifierBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(XcblOrderDetailIdentifierBean xcblOrderDetailIdentifierBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_ID + "=" +
      StringHandler.nullIfZero(xcblOrderDetailIdentifierBean.getId()) + "," +
     ATTRIBUTE_XCBL_ORDER_DETAIL_ID + "=" +
       StringHandler.nullIfZero(xcblOrderDetailIdentifierBean.getXcblOrderDetailId()) + "," +
     ATTRIBUTE_KEY + "=" +
      SqlHandler.delimitString(xcblOrderDetailIdentifierBean.getKey()) + "," +
     ATTRIBUTE_VALUE + "=" +
      SqlHandler.delimitString(xcblOrderDetailIdentifierBean.getValue()) + " " +
     "where " + ATTRIBUTE_ID + "=" +
      xcblOrderDetailIdentifierBean.getId();
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
    Collection xcblOrderDetailIdentifierBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      XcblOrderDetailIdentifierBean xcblOrderDetailIdentifierBean = new
          XcblOrderDetailIdentifierBean();
      load(dataSetRow, xcblOrderDetailIdentifierBean);
      xcblOrderDetailIdentifierBeanColl.add(xcblOrderDetailIdentifierBean);
    }
    return xcblOrderDetailIdentifierBeanColl;
  }
}