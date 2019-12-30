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
import com.tcmis.internal.hub.beans.ShipmentCreationStageBean;

/******************************************************************************
 * CLASSNAME: ShipmentCreationStageBeanFactory <br>
 * @version: 1.0, Feb 19, 2007 <br>
 *****************************************************************************/

public class ShipmentCreationStageBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_PICKLIST_ID = "PICKLIST_ID";
  public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
  public String ATTRIBUTE_ACTION = "ACTION";
  public String ATTRIBUTE_DATE_INSERTED = "DATE_INSERTED";

  //table name
  public String TABLE = "SHIPMENT_CREATION_STAGE";

  //constructor
  public ShipmentCreationStageBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("picklistId")) {
      return ATTRIBUTE_PICKLIST_ID;
    }
    else if (attributeName.equals("shipmentId")) {
      return ATTRIBUTE_SHIPMENT_ID;
    }
    else if (attributeName.equals("action")) {
      return ATTRIBUTE_ACTION;
    }
    else if (attributeName.equals("dateInserted")) {
      return ATTRIBUTE_DATE_INSERTED;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, ShipmentCreationStageBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(ShipmentCreationStageBean shipmentCreationStageBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
     "" + shipmentCreationStageBean.getPrNumber());
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
   public int delete(ShipmentCreationStageBean shipmentCreationStageBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
     "" + shipmentCreationStageBean.getPrNumber());
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
  public int insert(ShipmentCreationStageBean shipmentCreationStageBean) throws
      BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(shipmentCreationStageBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(ShipmentCreationStageBean shipmentCreationStageBean,
                    Connection conn) throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_PR_NUMBER + "," +
        ATTRIBUTE_LINE_ITEM + "," +
        ATTRIBUTE_PICKLIST_ID + "," +
        ATTRIBUTE_SHIPMENT_ID + "," +
        ATTRIBUTE_ACTION + "," +
        ATTRIBUTE_DATE_INSERTED + ")" +
        " values (" +
        shipmentCreationStageBean.getPrNumber() + "," +
        SqlHandler.delimitString(shipmentCreationStageBean.getLineItem()) + "," +
        shipmentCreationStageBean.getPicklistId() + "," +
        shipmentCreationStageBean.getShipmentId() + "," +
        SqlHandler.delimitString(shipmentCreationStageBean.getAction()) + "," +
        DateHandler.getOracleToDateFunction(shipmentCreationStageBean.
                                            getDateInserted()) + ")";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    return sqlManager.update(conn, query);
  }

  /*
//update
     public int update(ShipmentCreationStageBean shipmentCreationStageBean)
      throws BaseException {
      Connection connection = null;
      int result = 0;
      try {
       connection = getDbManager().getConnection();
       result = update(shipmentCreationStageBean, connection);
      }
      finally {
       this.getDbManager().returnConnection(connection);
      }
      return result;
     }
     public int update(ShipmentCreationStageBean shipmentCreationStageBean, Connection conn)
      throws BaseException {
      String query  = "update " + TABLE + " set " +
       ATTRIBUTE_PR_NUMBER + "=" +
       StringHandler.nullIfZero(shipmentCreationStageBean.getPrNumber()) + "," +
       ATTRIBUTE_LINE_ITEM + "=" +
       SqlHandler.delimitString(shipmentCreationStageBean.getLineItem()) + "," +
       ATTRIBUTE_PICKLIST_ID + "=" +
       StringHandler.nullIfZero(shipmentCreationStageBean.getPicklistId()) + "," +
       ATTRIBUTE_SHIPMENT_ID + "=" +
       StringHandler.nullIfZero(shipmentCreationStageBean.getShipmentId()) + "," +
       ATTRIBUTE_ACTION + "=" +
        SqlHandler.delimitString(shipmentCreationStageBean.getAction()) + "," +
       ATTRIBUTE_DATE_INSERTED + "=" +
        DateHandler.getOracleToDateFunction(shipmentCreationStageBean.getDateInserted()) + " " +
       "where " + ATTRIBUTE_PR_NUMBER + "=" +
        shipmentCreationStageBean.getPrNumber();
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
    Collection shipmentCreationStageBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      ShipmentCreationStageBean shipmentCreationStageBean = new
          ShipmentCreationStageBean();
      load(dataSetRow, shipmentCreationStageBean);
      shipmentCreationStageBeanColl.add(shipmentCreationStageBean);
    }
    return shipmentCreationStageBeanColl;
  }
}