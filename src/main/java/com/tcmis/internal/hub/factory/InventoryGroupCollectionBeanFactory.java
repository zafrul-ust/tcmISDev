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
import com.tcmis.internal.hub.beans.InventoryGroupCollectionBean;

/******************************************************************************
 * CLASSNAME: InventoryGroupCollectionBeanFactory <br>
 * @version: 1.0, Oct 2, 2006 <br>
 *****************************************************************************/

public class InventoryGroupCollectionBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
  public String ATTRIBUTE_HUB = "HUB";
  public String ATTRIBUTE_INVENTORY_GROUP_COLLECTION =
      "INVENTORY_GROUP_COLLECTION";

  //table name
  public String TABLE = "INVENTORY_GROUP_COLLECTION";

  //constructor
  public InventoryGroupCollectionBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("inventoryGroup")) {
      return ATTRIBUTE_INVENTORY_GROUP;
    }
    else if (attributeName.equals("hub")) {
      return ATTRIBUTE_HUB;
    }
    else if (attributeName.equals("inventoryGroupCollection")) {
      return ATTRIBUTE_INVENTORY_GROUP_COLLECTION;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InventoryGroupCollectionBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InventoryGroupCollectionBean inventoryGroupCollectionBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
     "" + inventoryGroupCollectionBean.getInventoryGroup());
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
   public int delete(InventoryGroupCollectionBean inventoryGroupCollectionBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
     "" + inventoryGroupCollectionBean.getInventoryGroup());
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
   public int insert(InventoryGroupCollectionBean inventoryGroupCollectionBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(inventoryGroupCollectionBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InventoryGroupCollectionBean inventoryGroupCollectionBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVENTORY_GROUP + "," +
     ATTRIBUTE_HUB + "," +
     ATTRIBUTE_INVENTORY_GROUP_COLLECTION + ")" +
     " values (" +
     SqlHandler.delimitString(inventoryGroupCollectionBean.getInventoryGroup()) + "," +
     SqlHandler.delimitString(inventoryGroupCollectionBean.getHub()) + "," +
     SqlHandler.delimitString(inventoryGroupCollectionBean.getInventoryGroupCollection()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InventoryGroupCollectionBean inventoryGroupCollectionBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(inventoryGroupCollectionBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InventoryGroupCollectionBean inventoryGroupCollectionBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVENTORY_GROUP + "=" +
      SqlHandler.delimitString(inventoryGroupCollectionBean.getInventoryGroup()) + "," +
     ATTRIBUTE_HUB + "=" +
      SqlHandler.delimitString(inventoryGroupCollectionBean.getHub()) + "," +
     ATTRIBUTE_INVENTORY_GROUP_COLLECTION + "=" +
      SqlHandler.delimitString(inventoryGroupCollectionBean.getInventoryGroupCollection()) + " " +
     "where " + ATTRIBUTE_INVENTORY_GROUP + "=" +
      inventoryGroupCollectionBean.getInventoryGroup();
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
    Collection inventoryGroupCollectionBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InventoryGroupCollectionBean inventoryGroupCollectionBean = new
          InventoryGroupCollectionBean();
      load(dataSetRow, inventoryGroupCollectionBean);
      inventoryGroupCollectionBeanColl.add(inventoryGroupCollectionBean);
    }
    return inventoryGroupCollectionBeanColl;
  }
}