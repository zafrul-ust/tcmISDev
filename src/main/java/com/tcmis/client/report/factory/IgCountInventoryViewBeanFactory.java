package com.tcmis.client.report.factory;

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
import com.tcmis.client.report.beans.IgCountInventoryViewBean;

/******************************************************************************
 * CLASSNAME: IgCountInventoryViewBeanFactory <br>
 * @version: 1.0, Jun 12, 2007 <br>
 *****************************************************************************/

public class IgCountInventoryViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
  public String ATTRIBUTE_INVENTORY_GROUP_COLLECTION =
      "INVENTORY_GROUP_COLLECTION";
  public String ATTRIBUTE_TANK_NAME = "TANK_NAME";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_REORDER_POINT = "REORDER_POINT";
  public String ATTRIBUTE_REORDER_QUANTITY = "REORDER_QUANTITY";
  public String ATTRIBUTE_LOW_ALARM = "LOW_ALARM";
  public String ATTRIBUTE_HIGH_ALARM = "HIGH_ALARM";
  public String ATTRIBUTE_CAPACITY = "CAPACITY";
  public String ATTRIBUTE_COUNT_UOM = "COUNT_UOM";
  public String ATTRIBUTE_STATUS = "STATUS";
  public String ATTRIBUTE_INVENTORY = "INVENTORY";
  public String ATTRIBUTE_INVENTORY_UOM = "INVENTORY_UOM";
  public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
  public String ATTRIBUTE_LAST_COUNT_DATE = "LAST_COUNT_DATE";
  public String ATTRIBUTE_NEXT_DELIVERY_DATE = "NEXT_DELIVERY_DATE";
  public String ATTRIBUTE_INVENTORY_FRACTION = "INVENTORY_FRACTION";
  public String ATTRIBUTE_LOW_ALARM_FRACTION = "LOW_ALARM_FRACTION";
  public String ATTRIBUTE_HIGH_ALARM_FRACTION = "HIGH_ALARM_FRACTION";
  public String ATTRIBUTE_TANK_DIVISIONS = "TANK_DIVISIONS";
  public String ATTRIBUTE_INVENTORY_COUNT_UOM = "INVENTORY_COUNT_UOM";
  public String ATTRIBUTE_LOW_ALARM_COUNT_UOM = "LOW_ALARM_COUNT_UOM";
  public String ATTRIBUTE_HIGH_ALARM_COUNT_UOM = "HIGH_ALARM_COUNT_UOM";
  public String ATTRIBUTE_CAPACITY_COUNT_UOM = "CAPACITY_COUNT_UOM";

  //table name
  public String TABLE = "IG_COUNT_INVENTORY_VIEW";

  //constructor
  public IgCountInventoryViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("inventoryGroup")) {
      return ATTRIBUTE_INVENTORY_GROUP;
    }
    else if (attributeName.equals("inventoryGroupCollection")) {
      return ATTRIBUTE_INVENTORY_GROUP_COLLECTION;
    }
    else if (attributeName.equals("tankName")) {
      return ATTRIBUTE_TANK_NAME;
    }
    else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("reorderPoint")) {
      return ATTRIBUTE_REORDER_POINT;
    }
    else if (attributeName.equals("reorderQuantity")) {
      return ATTRIBUTE_REORDER_QUANTITY;
    }
    else if (attributeName.equals("lowAlarm")) {
      return ATTRIBUTE_LOW_ALARM;
    }
    else if (attributeName.equals("highAlarm")) {
      return ATTRIBUTE_HIGH_ALARM;
    }
    else if (attributeName.equals("capacity")) {
      return ATTRIBUTE_CAPACITY;
    }
    else if (attributeName.equals("countUom")) {
      return ATTRIBUTE_COUNT_UOM;
    }
    else if (attributeName.equals("status")) {
      return ATTRIBUTE_STATUS;
    }
    else if (attributeName.equals("inventory")) {
      return ATTRIBUTE_INVENTORY;
    }
    else if (attributeName.equals("inventoryUom")) {
      return ATTRIBUTE_INVENTORY_UOM;
    }
    else if (attributeName.equals("itemId")) {
      return ATTRIBUTE_ITEM_ID;
    }
    else if (attributeName.equals("lastCountDate")) {
      return ATTRIBUTE_LAST_COUNT_DATE;
    }
    else if (attributeName.equals("nextDeliveryDate")) {
      return ATTRIBUTE_NEXT_DELIVERY_DATE;
    }
    else if (attributeName.equals("inventoryFraction")) {
      return ATTRIBUTE_INVENTORY_FRACTION;
    }
    else if (attributeName.equals("lowAlarmFraction")) {
      return ATTRIBUTE_LOW_ALARM_FRACTION;
    }
    else if (attributeName.equals("highAlarmFraction")) {
      return ATTRIBUTE_HIGH_ALARM_FRACTION;
    }
    else if (attributeName.equals("tankDivisions")) {
      return ATTRIBUTE_TANK_DIVISIONS;
    }
    else if (attributeName.equals("inventoryCountUom")) {
      return ATTRIBUTE_INVENTORY_COUNT_UOM;
    }
    else if (attributeName.equals("lowAlarmCountUom")) {
      return ATTRIBUTE_LOW_ALARM_COUNT_UOM;
    }
    else if (attributeName.equals("highAlarmCountUom")) {
      return ATTRIBUTE_HIGH_ALARM_COUNT_UOM;
    }
    else if (attributeName.equals("capacityCountUom")) {
      return ATTRIBUTE_CAPACITY_COUNT_UOM;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, IgCountInventoryViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(IgCountInventoryViewBean igCountInventoryViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
     "" + igCountInventoryViewBean.getCompanyId());
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
   public int delete(IgCountInventoryViewBean igCountInventoryViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
     "" + igCountInventoryViewBean.getCompanyId());
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
   public int insert(IgCountInventoryViewBean igCountInventoryViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(igCountInventoryViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(IgCountInventoryViewBean igCountInventoryViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_INVENTORY_GROUP + "," +
     ATTRIBUTE_INVENTORY_GROUP_COLLECTION + "," +
     ATTRIBUTE_TANK_NAME + "," +
     ATTRIBUTE_CAT_PART_NO + "," +
     ATTRIBUTE_REORDER_POINT + "," +
     ATTRIBUTE_REORDER_QUANTITY + "," +
     ATTRIBUTE_LOW_ALARM + "," +
     ATTRIBUTE_HIGH_ALARM + "," +
     ATTRIBUTE_CAPACITY + "," +
     ATTRIBUTE_COUNT_UOM + "," +
     ATTRIBUTE_STATUS + "," +
     ATTRIBUTE_INVENTORY + "," +
     ATTRIBUTE_INVENTORY_UOM + "," +
     ATTRIBUTE_ITEM_ID + "," +
     ATTRIBUTE_LAST_COUNT_DATE + "," +
     ATTRIBUTE_NEXT_DELIVERY_DATE + "," +
     ATTRIBUTE_INVENTORY_FRACTION + "," +
     ATTRIBUTE_LOW_ALARM_FRACTION + "," +
     ATTRIBUTE_HIGH_ALARM_FRACTION + "," +
     ATTRIBUTE_TANK_DIVISIONS + "," +
     ATTRIBUTE_INVENTORY_COUNT_UOM + "," +
     ATTRIBUTE_LOW_ALARM_COUNT_UOM + "," +
     ATTRIBUTE_HIGH_ALARM_COUNT_UOM + "," +
     ATTRIBUTE_CAPACITY_COUNT_UOM + ")" +
     " values (" +
     SqlHandler.delimitString(igCountInventoryViewBean.getCompanyId()) + "," +
       SqlHandler.delimitString(igCountInventoryViewBean.getInventoryGroup()) + "," +
     SqlHandler.delimitString(igCountInventoryViewBean.getInventoryGroupCollection()) + "," +
     SqlHandler.delimitString(igCountInventoryViewBean.getTankName()) + "," +
     SqlHandler.delimitString(igCountInventoryViewBean.getCatPartNo()) + "," +
     igCountInventoryViewBean.getReorderPoint() + "," +
     igCountInventoryViewBean.getReorderQuantity() + "," +
     igCountInventoryViewBean.getLowAlarm() + "," +
     igCountInventoryViewBean.getHighAlarm() + "," +
     igCountInventoryViewBean.getCapacity() + "," +
     SqlHandler.delimitString(igCountInventoryViewBean.getCountUom()) + "," +
     SqlHandler.delimitString(igCountInventoryViewBean.getStatus()) + "," +
     igCountInventoryViewBean.getInventory() + "," +
       SqlHandler.delimitString(igCountInventoryViewBean.getInventoryUom()) + "," +
     igCountInventoryViewBean.getItemId() + "," +
     DateHandler.getOracleToDateFunction(igCountInventoryViewBean.getLastCountDate()) + "," +
     DateHandler.getOracleToDateFunction(igCountInventoryViewBean.getNextDeliveryDate()) + "," +
     igCountInventoryViewBean.getInventoryFraction() + "," +
     igCountInventoryViewBean.getLowAlarmFraction() + "," +
     igCountInventoryViewBean.getHighAlarmFraction() + "," +
     igCountInventoryViewBean.getTankDivisions() + "," +
     igCountInventoryViewBean.getInventoryCountUom() + "," +
     igCountInventoryViewBean.getLowAlarmCountUom() + "," +
     igCountInventoryViewBean.getHighAlarmCountUom() + "," +
     igCountInventoryViewBean.getCapacityCountUom() + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(IgCountInventoryViewBean igCountInventoryViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(igCountInventoryViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(IgCountInventoryViewBean igCountInventoryViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(igCountInventoryViewBean.getCompanyId()) + "," +
     ATTRIBUTE_INVENTORY_GROUP + "=" +
       SqlHandler.delimitString(igCountInventoryViewBean.getInventoryGroup()) + "," +
     ATTRIBUTE_INVENTORY_GROUP_COLLECTION + "=" +
      SqlHandler.delimitString(igCountInventoryViewBean.getInventoryGroupCollection()) + "," +
     ATTRIBUTE_TANK_NAME + "=" +
      SqlHandler.delimitString(igCountInventoryViewBean.getTankName()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
      SqlHandler.delimitString(igCountInventoryViewBean.getCatPartNo()) + "," +
     ATTRIBUTE_REORDER_POINT + "=" +
       StringHandler.nullIfZero(igCountInventoryViewBean.getReorderPoint()) + "," +
     ATTRIBUTE_REORDER_QUANTITY + "=" +
       StringHandler.nullIfZero(igCountInventoryViewBean.getReorderQuantity()) + "," +
     ATTRIBUTE_LOW_ALARM + "=" +
      StringHandler.nullIfZero(igCountInventoryViewBean.getLowAlarm()) + "," +
     ATTRIBUTE_HIGH_ALARM + "=" +
      StringHandler.nullIfZero(igCountInventoryViewBean.getHighAlarm()) + "," +
     ATTRIBUTE_CAPACITY + "=" +
      StringHandler.nullIfZero(igCountInventoryViewBean.getCapacity()) + "," +
     ATTRIBUTE_COUNT_UOM + "=" +
      SqlHandler.delimitString(igCountInventoryViewBean.getCountUom()) + "," +
     ATTRIBUTE_STATUS + "=" +
      SqlHandler.delimitString(igCountInventoryViewBean.getStatus()) + "," +
     ATTRIBUTE_INVENTORY + "=" +
      StringHandler.nullIfZero(igCountInventoryViewBean.getInventory()) + "," +
     ATTRIBUTE_INVENTORY_UOM + "=" +
       SqlHandler.delimitString(igCountInventoryViewBean.getInventoryUom()) + "," +
     ATTRIBUTE_ITEM_ID + "=" +
      StringHandler.nullIfZero(igCountInventoryViewBean.getItemId()) + "," +
     ATTRIBUTE_LAST_COUNT_DATE + "=" +
      DateHandler.getOracleToDateFunction(igCountInventoryViewBean.getLastCountDate()) + "," +
     ATTRIBUTE_NEXT_DELIVERY_DATE + "=" +
      DateHandler.getOracleToDateFunction(igCountInventoryViewBean.getNextDeliveryDate()) + "," +
     ATTRIBUTE_INVENTORY_FRACTION + "=" +
      StringHandler.nullIfZero(igCountInventoryViewBean.getInventoryFraction()) + "," +
     ATTRIBUTE_LOW_ALARM_FRACTION + "=" +
       StringHandler.nullIfZero(igCountInventoryViewBean.getLowAlarmFraction()) + "," +
     ATTRIBUTE_HIGH_ALARM_FRACTION + "=" +
      StringHandler.nullIfZero(igCountInventoryViewBean.getHighAlarmFraction()) + "," +
     ATTRIBUTE_TANK_DIVISIONS + "=" +
       StringHandler.nullIfZero(igCountInventoryViewBean.getTankDivisions()) + "," +
     ATTRIBUTE_INVENTORY_COUNT_UOM + "=" +
      StringHandler.nullIfZero(igCountInventoryViewBean.getInventoryCountUom()) + "," +
     ATTRIBUTE_LOW_ALARM_COUNT_UOM + "=" +
       StringHandler.nullIfZero(igCountInventoryViewBean.getLowAlarmCountUom()) + "," +
     ATTRIBUTE_HIGH_ALARM_COUNT_UOM + "=" +
      StringHandler.nullIfZero(igCountInventoryViewBean.getHighAlarmCountUom()) + "," +
     ATTRIBUTE_CAPACITY_COUNT_UOM + "=" +
       StringHandler.nullIfZero(igCountInventoryViewBean.getCapacityCountUom()) + " " +
     "where " + ATTRIBUTE_COMPANY_ID + "=" +
      igCountInventoryViewBean.getCompanyId();
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws
      BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(criteria, sortCriteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria,
                           Connection conn) throws BaseException {
    Collection igCountInventoryViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      IgCountInventoryViewBean igCountInventoryViewBean = new
          IgCountInventoryViewBean();
      load(dataSetRow, igCountInventoryViewBean);
      igCountInventoryViewBeanColl.add(igCountInventoryViewBean);
    }
    return igCountInventoryViewBeanColl;
  }
}