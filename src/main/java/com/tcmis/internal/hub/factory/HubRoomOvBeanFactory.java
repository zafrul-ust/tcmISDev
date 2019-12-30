package com.tcmis.internal.hub.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

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
import com.tcmis.internal.hub.beans.HubRoomOvBean;

/******************************************************************************
 * CLASSNAME: HubRoomOvBeanFactory <br>
 * @version: 1.0, Sep 13, 2006 <br>
 *****************************************************************************/

public class HubRoomOvBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_HUB = "HUB";
  public String ATTRIBUTE_HUB_NAME = "HUB_NAME";
  public String ATTRIBUTE_ROOM_VAR = "ROOM_VAR";

  //table name
  public String TABLE = "HUB_ROOM_OV";

  //constructor
  public HubRoomOvBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("hub")) {
      return ATTRIBUTE_HUB;
    }
    else if (attributeName.equals("hubName")) {
      return ATTRIBUTE_HUB_NAME;
    }
    else if (attributeName.equals("roomVar")) {
      return ATTRIBUTE_ROOM_VAR;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, HubRoomOvBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(HubRoomOvBean hubRoomOvBean)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("hub", "SearchCriterion.EQUALS",
     "" + hubRoomOvBean.getHub());
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
   public int delete(HubRoomOvBean hubRoomOvBean, Connection conn)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("hub", "SearchCriterion.EQUALS",
     "" + hubRoomOvBean.getHub());
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
   public int insert(HubRoomOvBean hubRoomOvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(hubRoomOvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(HubRoomOvBean hubRoomOvBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_HUB + "," +
     ATTRIBUTE_HUB_NAME + "," +
     ATTRIBUTE_ROOM_VAR + ")" +
     " values (" +
     SqlHandler.delimitString(hubRoomOvBean.getHub()) + "," +
     SqlHandler.delimitString(hubRoomOvBean.getHubName()) + "," +
     SqlHandler.delimitString(hubRoomOvBean.getRoomVar()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(HubRoomOvBean hubRoomOvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(hubRoomOvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(HubRoomOvBean hubRoomOvBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_HUB + "=" +
      SqlHandler.delimitString(hubRoomOvBean.getHub()) + "," +
     ATTRIBUTE_HUB_NAME + "=" +
      SqlHandler.delimitString(hubRoomOvBean.getHubName()) + "," +
     ATTRIBUTE_ROOM_VAR + "=" +
      SqlHandler.delimitString(hubRoomOvBean.getRoomVar()) + " " +
     "where " + ATTRIBUTE_HUB + "=" +
      hubRoomOvBean.getHub();
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

    Collection hubRoomOvBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      HubRoomOvBean hubRoomOvBean = new HubRoomOvBean();
      load(dataSetRow, hubRoomOvBean);
      hubRoomOvBeanColl.add(hubRoomOvBean);
    }

    return hubRoomOvBeanColl;
  }

  public Collection selectObject(SearchCriteria criteria) throws BaseException,
      Exception {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      java.util.Map map = connection.getTypeMap();
      map.put("HUB_ROOM_OBJ",
              Class.forName(
          "com.tcmis.internal.hub.beans.HubRoomOvBean"));
      map.put("ROOM_OBJ",
              Class.forName(
          "com.tcmis.internal.hub.beans.VvHubRoomBean"));

      c = selectObject(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectObject(SearchCriteria criteria, Connection conn) throws
      BaseException, Exception {

    Collection beanColl = new Vector();

    String query = "select value(p) from " + TABLE + " p " +
        getWhereClause(criteria) + " ORDER BY " + ATTRIBUTE_HUB_NAME;
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    Statement st = conn.createStatement();
    ResultSet rs = st.executeQuery(query);
    while (rs.next()) {
      HubRoomOvBean b = (HubRoomOvBean) rs.getObject(
          1);
      beanColl.add(b);
    }
    rs.close();
    st.close();
    return beanColl;
  }
}