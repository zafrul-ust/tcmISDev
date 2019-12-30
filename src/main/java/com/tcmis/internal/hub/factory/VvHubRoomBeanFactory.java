package com.tcmis.internal.hub.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;
import java.math.BigDecimal;
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
import com.tcmis.internal.hub.beans.VvHubRoomBean;

/******************************************************************************
 * CLASSNAME: VvHubRoomBeanFactory <br>
 * @version: 1.0, Sep 13, 2006 <br>
 *****************************************************************************/

public class VvHubRoomBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_HUB = "HUB";
  public String ATTRIBUTE_ROOM = "ROOM";
  public String ATTRIBUTE_ROOM_DESCRIPTION = "ROOM_DESCRIPTION";
  public String ATTRIBUTE_ROUTE_ORDER = "ROUTE_ORDER";

  //table name
  public String TABLE = "VV_HUB_ROOM";

  //constructor
  public VvHubRoomBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("hub")) {
      return ATTRIBUTE_HUB;
    }
    else if (attributeName.equals("room")) {
      return ATTRIBUTE_ROOM;
    }
    else if (attributeName.equals("roomDescription")) {
      return ATTRIBUTE_ROOM_DESCRIPTION;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, VvHubRoomBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  
//delete
   public int delete(VvHubRoomBean vvHubRoomBean)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("hub", "SearchCriterion.EQUALS",
     "" + vvHubRoomBean.getHub());
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
   public int delete(VvHubRoomBean vvHubRoomBean, Connection conn)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("hub", "SearchCriterion.EQUALS",
     "" + vvHubRoomBean.getHub());
    return delete(criteria, conn);
   }
   

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
  
//insert
   public int insert(VvHubRoomBean vvHubRoomBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(vvHubRoomBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(VvHubRoomBean vvHubRoomBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_HUB + "," +
     ATTRIBUTE_ROOM + "," +
     ATTRIBUTE_ROOM_DESCRIPTION + ")" +
     " values (" +
     SqlHandler.delimitString(vvHubRoomBean.getHub()) + "," +
     SqlHandler.delimitString(vvHubRoomBean.getRoom()) + "," +
     SqlHandler.delimitString(vvHubRoomBean.getRoomDescription()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(VvHubRoomBean vvHubRoomBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(vvHubRoomBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(VvHubRoomBean vvHubRoomBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_HUB + "=" +
      SqlHandler.delimitString(vvHubRoomBean.getHub()) + "," +
     ATTRIBUTE_ROOM + "=" +
      SqlHandler.delimitString(vvHubRoomBean.getRoom()) + "," +
     ATTRIBUTE_ROOM_DESCRIPTION + "=" +
      SqlHandler.delimitString(vvHubRoomBean.getRoomDescription()) + " " +
     "where " + ATTRIBUTE_HUB + "=" +
      vvHubRoomBean.getHub();
    return new SqlManager().update(conn, query);
   }
   

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

    Collection vvHubRoomBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      VvHubRoomBean vvHubRoomBean = new VvHubRoomBean();
      load(dataSetRow, vvHubRoomBean);
      vvHubRoomBeanColl.add(vvHubRoomBean);
    }

    return vvHubRoomBeanColl;
  }
}