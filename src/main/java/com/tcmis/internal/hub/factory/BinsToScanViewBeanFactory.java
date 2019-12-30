package com.tcmis.internal.hub.factory;

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
import com.tcmis.internal.hub.beans.BinsToScanViewBean;

/******************************************************************************
 * CLASSNAME: BinsToScanViewBeanFactory <br>
 * @version: 1.0, Sep 13, 2006 <br>
 *****************************************************************************/

public class BinsToScanViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
  public String ATTRIBUTE_BIN = "BIN";
  public String ATTRIBUTE_ROOM = "ROOM";
  public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
  public String ATTRIBUTE_COUNT_DATETIME = "COUNT_DATETIME";
  public String ATTRIBUTE_INVENTORY_COST = "INVENTORY_COST";
  public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
  public String ATTRIBUTE_DATE_PICKED = "DATE_PICKED";

  //table name
  public String TABLE = "BINS_TO_SCAN_VIEW";

  //constructor
  public BinsToScanViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("branchPlant")) {
      return ATTRIBUTE_BRANCH_PLANT;
    }
    else if (attributeName.equals("bin")) {
      return ATTRIBUTE_BIN;
    }
    else if (attributeName.equals("room")) {
      return ATTRIBUTE_ROOM;
    }
    else if (attributeName.equals("itemId")) {
      return ATTRIBUTE_ITEM_ID;
    }
    else if (attributeName.equals("countDatetime")) {
      return ATTRIBUTE_COUNT_DATETIME;
    }
    else if (attributeName.equals("inventoryCost")) {
      return ATTRIBUTE_INVENTORY_COST;
    }
    else if (attributeName.equals("unitPrice")) {
      return ATTRIBUTE_UNIT_PRICE;
    }
    else if (attributeName.equals("datePicked")) {
      return ATTRIBUTE_DATE_PICKED;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, BinsToScanViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(BinsToScanViewBean binsToScanViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("branchPlant", "SearchCriterion.EQUALS",
     "" + binsToScanViewBean.getBranchPlant());
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
   public int delete(BinsToScanViewBean binsToScanViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("branchPlant", "SearchCriterion.EQUALS",
     "" + binsToScanViewBean.getBranchPlant());
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
   public int insert(BinsToScanViewBean binsToScanViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(binsToScanViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(BinsToScanViewBean binsToScanViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_BRANCH_PLANT + "," +
     ATTRIBUTE_BIN + "," +
     ATTRIBUTE_ROOM + "," +
     ATTRIBUTE_ITEM_ID + "," +
     ATTRIBUTE_COUNT_DATETIME + "," +
     ATTRIBUTE_INVENTORY_COST + "," +
     ATTRIBUTE_UNIT_PRICE + "," +
     ATTRIBUTE_DATE_PICKED + ")" +
     " values (" +
     SqlHandler.delimitString(binsToScanViewBean.getBranchPlant()) + "," +
     SqlHandler.delimitString(binsToScanViewBean.getBin()) + "," +
     SqlHandler.delimitString(binsToScanViewBean.getRoom()) + "," +
     binsToScanViewBean.getItemId() + "," +
     DateHandler.getOracleToDateFunction(binsToScanViewBean.getCountDatetime()) + "," +
     binsToScanViewBean.getInventoryCost() + "," +
     binsToScanViewBean.getUnitPrice() + "," +
       DateHandler.getOracleToDateFunction(binsToScanViewBean.getDatePicked()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(BinsToScanViewBean binsToScanViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(binsToScanViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(BinsToScanViewBean binsToScanViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_BRANCH_PLANT + "=" +
      SqlHandler.delimitString(binsToScanViewBean.getBranchPlant()) + "," +
     ATTRIBUTE_BIN + "=" +
      SqlHandler.delimitString(binsToScanViewBean.getBin()) + "," +
     ATTRIBUTE_ROOM + "=" +
      SqlHandler.delimitString(binsToScanViewBean.getRoom()) + "," +
     ATTRIBUTE_ITEM_ID + "=" +
      StringHandler.nullIfZero(binsToScanViewBean.getItemId()) + "," +
     ATTRIBUTE_COUNT_DATETIME + "=" +
      DateHandler.getOracleToDateFunction(binsToScanViewBean.getCountDatetime()) + "," +
     ATTRIBUTE_INVENTORY_COST + "=" +
      StringHandler.nullIfZero(binsToScanViewBean.getInventoryCost()) + "," +
     ATTRIBUTE_UNIT_PRICE + "=" +
      StringHandler.nullIfZero(binsToScanViewBean.getUnitPrice()) + "," +
     ATTRIBUTE_DATE_PICKED + "=" +
       DateHandler.getOracleToDateFunction(binsToScanViewBean.getDatePicked()) + " " +
     "where " + ATTRIBUTE_BRANCH_PLANT + "=" +
      binsToScanViewBean.getBranchPlant();
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

    Collection binsToScanViewBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      BinsToScanViewBean binsToScanViewBean = new BinsToScanViewBean();
      load(dataSetRow, binsToScanViewBean);
      binsToScanViewBeanColl.add(binsToScanViewBean);
    }

    return binsToScanViewBeanColl;
  }

  public Collection selectDistinct(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectDistinct(criteria, sortCriteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectDistinct(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws
      BaseException {

    Collection binsToScanViewBeanColl = new Vector();

    String query = "select distinct BRANCH_PLANT,ROOM,BIN  from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      BinsToScanViewBean binsToScanViewBean = new BinsToScanViewBean();
      load(dataSetRow, binsToScanViewBean);
      binsToScanViewBeanColl.add(binsToScanViewBean);
    }

    return binsToScanViewBeanColl;
  }
}