package com.tcmis.internal.supply.factory;

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
import com.tcmis.internal.supply.beans.SurplusInventoryViewBean;

/******************************************************************************
 * CLASSNAME: SurplusInventoryViewBeanFactory <br>
 * @version: 1.0, Mar 15, 2007 <br>
 *****************************************************************************/

public class SurplusInventoryViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
  public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
  public String ATTRIBUTE_HUB = "HUB";
  public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
  public String ATTRIBUTE_BIN = "BIN";
  public String ATTRIBUTE_QUANTITY_AVAILABLE = "QUANTITY_AVAILABLE";
  public String ATTRIBUTE_LOT_STATUS = "LOT_STATUS";
  public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";

  //table name
  public String TABLE = "SURPLUS_INVENTORY_VIEW";

  //constructor
  public SurplusInventoryViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("itemId")) {
      return ATTRIBUTE_ITEM_ID;
    }
    else if (attributeName.equals("receiptId")) {
      return ATTRIBUTE_RECEIPT_ID;
    }
    else if (attributeName.equals("inventoryGroup")) {
      return ATTRIBUTE_INVENTORY_GROUP;
    }
    else if (attributeName.equals("hub")) {
      return ATTRIBUTE_HUB;
    }
    else if (attributeName.equals("mfgLot")) {
      return ATTRIBUTE_MFG_LOT;
    }
    else if (attributeName.equals("bin")) {
      return ATTRIBUTE_BIN;
    }
    else if (attributeName.equals("quantityAvailable")) {
      return ATTRIBUTE_QUANTITY_AVAILABLE;
    }
    else if (attributeName.equals("lotStatus")) {
      return ATTRIBUTE_LOT_STATUS;
    }
    else if (attributeName.equals("expireDate")) {
      return ATTRIBUTE_EXPIRE_DATE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, SurplusInventoryViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(SurplusInventoryViewBean surplusInventoryViewBean)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("itemId", "SearchCriterion.EQUALS",
     "" + surplusInventoryViewBean.getItemId());
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
   public int delete(SurplusInventoryViewBean surplusInventoryViewBean, Connection conn)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("itemId", "SearchCriterion.EQUALS",
     "" + surplusInventoryViewBean.getItemId());
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
   public int insert(SurplusInventoryViewBean surplusInventoryViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(surplusInventoryViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(SurplusInventoryViewBean surplusInventoryViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_ITEM_ID + "," +
     ATTRIBUTE_RECEIPT_ID + "," +
     ATTRIBUTE_INVENTORY_GROUP + "," +
     ATTRIBUTE_HUB + "," +
     ATTRIBUTE_MFG_LOT + "," +
     ATTRIBUTE_BIN + "," +
     ATTRIBUTE_QUANTITY_AVAILABLE + "," +
     ATTRIBUTE_LOT_STATUS + "," +
     ATTRIBUTE_EXPIRE_DATE + ")" +
     " values (" +
     surplusInventoryViewBean.getItemId() + "," +
     surplusInventoryViewBean.getReceiptId() + "," +
       SqlHandler.delimitString(surplusInventoryViewBean.getInventoryGroup()) + "," +
     SqlHandler.delimitString(surplusInventoryViewBean.getHub()) + "," +
     SqlHandler.delimitString(surplusInventoryViewBean.getMfgLot()) + "," +
     SqlHandler.delimitString(surplusInventoryViewBean.getBin()) + "," +
     surplusInventoryViewBean.getQuantityAvailable() + "," +
     SqlHandler.delimitString(surplusInventoryViewBean.getLotStatus()) + "," +
     DateHandler.getOracleToDateFunction(surplusInventoryViewBean.getExpireDate()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(SurplusInventoryViewBean surplusInventoryViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(surplusInventoryViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(SurplusInventoryViewBean surplusInventoryViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_ITEM_ID + "=" +
      StringHandler.nullIfZero(surplusInventoryViewBean.getItemId()) + "," +
     ATTRIBUTE_RECEIPT_ID + "=" +
      StringHandler.nullIfZero(surplusInventoryViewBean.getReceiptId()) + "," +
     ATTRIBUTE_INVENTORY_GROUP + "=" +
       SqlHandler.delimitString(surplusInventoryViewBean.getInventoryGroup()) + "," +
     ATTRIBUTE_HUB + "=" +
      SqlHandler.delimitString(surplusInventoryViewBean.getHub()) + "," +
     ATTRIBUTE_MFG_LOT + "=" +
      SqlHandler.delimitString(surplusInventoryViewBean.getMfgLot()) + "," +
     ATTRIBUTE_BIN + "=" +
      SqlHandler.delimitString(surplusInventoryViewBean.getBin()) + "," +
     ATTRIBUTE_QUANTITY_AVAILABLE + "=" +
      StringHandler.nullIfZero(surplusInventoryViewBean.getQuantityAvailable()) + "," +
     ATTRIBUTE_LOT_STATUS + "=" +
      SqlHandler.delimitString(surplusInventoryViewBean.getLotStatus()) + "," +
     ATTRIBUTE_EXPIRE_DATE + "=" +
      DateHandler.getOracleToDateFunction(surplusInventoryViewBean.getExpireDate()) + " " +
     "where " + ATTRIBUTE_ITEM_ID + "=" +
      surplusInventoryViewBean.getItemId();
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
    Collection surplusInventoryViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      SurplusInventoryViewBean surplusInventoryViewBean = new
          SurplusInventoryViewBean();
      load(dataSetRow, surplusInventoryViewBean);
      surplusInventoryViewBeanColl.add(surplusInventoryViewBean);
    }
    return surplusInventoryViewBeanColl;
  }
}