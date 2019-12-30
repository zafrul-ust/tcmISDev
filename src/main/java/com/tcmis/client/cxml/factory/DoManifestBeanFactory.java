package com.tcmis.client.cxml.factory;

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
import com.tcmis.client.cxml.beans.DoManifestBean;

/******************************************************************************
 * CLASSNAME: DoManifestBeanFactory <br>
 * @version: 1.0, Jul 18, 2006 <br>
 *****************************************************************************/

public class DoManifestBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_DO_NUMBER = "DO_NUMBER";
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_QUANTITY = "QUANTITY";
  public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";

  //table name
  public String TABLE = "DO_MANIFEST";

  //constructor
  public DoManifestBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("doNumber")) {
      return ATTRIBUTE_DO_NUMBER;
    }
    else if (attributeName.equals("prNumber")) {
      return ATTRIBUTE_PR_NUMBER;
    }
    else if (attributeName.equals("lineItem")) {
      return ATTRIBUTE_LINE_ITEM;
    }
    else if (attributeName.equals("quantity")) {
      return ATTRIBUTE_QUANTITY;
    }
    else if (attributeName.equals("unitPrice")) {
      return ATTRIBUTE_UNIT_PRICE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, DoManifestBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(DoManifestBean doManifestBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("doNumber", "SearchCriterion.EQUALS",
     "" + doManifestBean.getDoNumber());
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
   public int delete(DoManifestBean doManifestBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("doNumber", "SearchCriterion.EQUALS",
     "" + doManifestBean.getDoNumber());
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
  public int insert(DoManifestBean doManifestBean) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(doManifestBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(DoManifestBean doManifestBean, Connection conn) throws
      BaseException {

    SqlManager sqlManager = new SqlManager();

    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_DO_NUMBER + "," +
        ATTRIBUTE_PR_NUMBER + "," +
        ATTRIBUTE_LINE_ITEM + "," +
        ATTRIBUTE_QUANTITY + "," +
        ATTRIBUTE_UNIT_PRICE + ")" +
        " values (" +
        SqlHandler.delimitString(doManifestBean.getDoNumber()) + "," +
        doManifestBean.getPrNumber() + "," +
        SqlHandler.delimitString(doManifestBean.getLineItem()) + "," +
        doManifestBean.getQuantity() + "," +
        doManifestBean.getUnitPrice() + ")";

    return sqlManager.update(conn, query);
  }

  /*
//update
   public int update(DoManifestBean doManifestBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(doManifestBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(DoManifestBean doManifestBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_DO_NUMBER + "=" +
      SqlHandler.delimitString(doManifestBean.getDoNumber()) + "," +
     ATTRIBUTE_PR_NUMBER + "=" +
      StringHandler.nullIfZero(doManifestBean.getPrNumber()) + "," +
     ATTRIBUTE_LINE_ITEM + "=" +
      SqlHandler.delimitString(doManifestBean.getLineItem()) + "," +
     ATTRIBUTE_QUANTITY + "=" +
      StringHandler.nullIfZero(doManifestBean.getQuantity()) + "," +
     ATTRIBUTE_UNIT_PRICE + "=" +
      StringHandler.nullIfZero(doManifestBean.getUnitPrice()) + " " +
     "where " + ATTRIBUTE_DO_NUMBER + "=" +
      doManifestBean.getDoNumber();
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

    Collection doManifestBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      DoManifestBean doManifestBean = new DoManifestBean();
      load(dataSetRow, doManifestBean);
      doManifestBeanColl.add(doManifestBean);
    }

    return doManifestBeanColl;
  }
}