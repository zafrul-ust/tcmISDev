package com.tcmis.client.seagate.factory;

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
import com.tcmis.client.seagate.beans.ShoppingCartItemTypeViewBean;

/******************************************************************************
 * CLASSNAME: ShoppingCartItemTypeViewBeanFactory <br>
 * @version: 1.0, Jul 21, 2006 <br>
 *****************************************************************************/

public class ShoppingCartItemTypeViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
  public String ATTRIBUTE_CANCEL_STATUS = "CANCEL_STATUS";
  public String ATTRIBUTE_PR_STATUS = "PR_STATUS";
  public String ATTRIBUTE_REQUEST_LINE_STATUS = "REQUEST_LINE_STATUS";
  public String ATTRIBUTE_ENTRY_ID = "ENTRY_ID";

  //table name
  public String TABLE = "SHOPPING_CART_ITEM_TYPE_VIEW";

  //constructor
  public ShoppingCartItemTypeViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("prNumber")) {
      return ATTRIBUTE_PR_NUMBER;
    }
    else if (attributeName.equals("lineItem")) {
      return ATTRIBUTE_LINE_ITEM;
    }
    else if (attributeName.equals("itemType")) {
      return ATTRIBUTE_ITEM_TYPE;
    }
    else if (attributeName.equals("cancelStatus")) {
      return ATTRIBUTE_CANCEL_STATUS;
    }
    else if (attributeName.equals("prStatus")) {
      return ATTRIBUTE_PR_STATUS;
    }
    else if (attributeName.equals("requestLineStatus")) {
      return ATTRIBUTE_REQUEST_LINE_STATUS;
    }
    else if (attributeName.equals("entryId")) {
      return ATTRIBUTE_ENTRY_ID;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, ShoppingCartItemTypeViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(ShoppingCartItemTypeViewBean shoppingCartItemTypeViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
     "" + shoppingCartItemTypeViewBean.getPrNumber());
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
   public int delete(ShoppingCartItemTypeViewBean shoppingCartItemTypeViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
     "" + shoppingCartItemTypeViewBean.getPrNumber());
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
   public int insert(ShoppingCartItemTypeViewBean shoppingCartItemTypeViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(shoppingCartItemTypeViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(ShoppingCartItemTypeViewBean shoppingCartItemTypeViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_PR_NUMBER + "," +
     ATTRIBUTE_LINE_ITEM + "," +
     ATTRIBUTE_ITEM_TYPE + "," +
     ATTRIBUTE_CANCEL_STATUS + "," +
     ATTRIBUTE_PR_STATUS + "," +
     ATTRIBUTE_REQUEST_LINE_STATUS + "," +
     ATTRIBUTE_ENTRY_ID + ")" +
     " values (" +
     shoppingCartItemTypeViewBean.getPrNumber() + "," +
       SqlHandler.delimitString(shoppingCartItemTypeViewBean.getLineItem()) + "," +
       SqlHandler.delimitString(shoppingCartItemTypeViewBean.getItemType()) + "," +
       SqlHandler.delimitString(shoppingCartItemTypeViewBean.getCancelStatus()) + "," +
       SqlHandler.delimitString(shoppingCartItemTypeViewBean.getPrStatus()) + "," +
     SqlHandler.delimitString(shoppingCartItemTypeViewBean.getRequestLineStatus()) + "," +
     shoppingCartItemTypeViewBean.getEntryId() + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(ShoppingCartItemTypeViewBean shoppingCartItemTypeViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(shoppingCartItemTypeViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(ShoppingCartItemTypeViewBean shoppingCartItemTypeViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_PR_NUMBER + "=" +
       StringHandler.nullIfZero(shoppingCartItemTypeViewBean.getPrNumber()) + "," +
     ATTRIBUTE_LINE_ITEM + "=" +
       SqlHandler.delimitString(shoppingCartItemTypeViewBean.getLineItem()) + "," +
     ATTRIBUTE_ITEM_TYPE + "=" +
       SqlHandler.delimitString(shoppingCartItemTypeViewBean.getItemType()) + "," +
     ATTRIBUTE_CANCEL_STATUS + "=" +
       SqlHandler.delimitString(shoppingCartItemTypeViewBean.getCancelStatus()) + "," +
     ATTRIBUTE_PR_STATUS + "=" +
       SqlHandler.delimitString(shoppingCartItemTypeViewBean.getPrStatus()) + "," +
     ATTRIBUTE_REQUEST_LINE_STATUS + "=" +
      SqlHandler.delimitString(shoppingCartItemTypeViewBean.getRequestLineStatus()) + "," +
     ATTRIBUTE_ENTRY_ID + "=" +
       StringHandler.nullIfZero(shoppingCartItemTypeViewBean.getEntryId()) + " " +
     "where " + ATTRIBUTE_PR_NUMBER + "=" +
      shoppingCartItemTypeViewBean.getPrNumber();
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

    Collection shoppingCartItemTypeViewBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      ShoppingCartItemTypeViewBean shoppingCartItemTypeViewBean = new
          ShoppingCartItemTypeViewBean();
      load(dataSetRow, shoppingCartItemTypeViewBean);
      shoppingCartItemTypeViewBeanColl.add(shoppingCartItemTypeViewBean);
    }

    return shoppingCartItemTypeViewBeanColl;
  }
}