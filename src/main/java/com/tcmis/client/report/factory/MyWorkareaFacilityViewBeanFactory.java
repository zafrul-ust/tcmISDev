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
import com.tcmis.client.report.beans.MyWorkareaFacilityViewBean;

/******************************************************************************
 * CLASSNAME: MyWorkareaFacilityViewBeanFactory <br>
 * @version: 1.0, Mar 1, 2006 <br>
 *****************************************************************************/

public class MyWorkareaFacilityViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_APPLICATION = "APPLICATION";
  public String ATTRIBUTE_APPLICATION_DESC = "APPLICATION_DESC";
  public String ATTRIBUTE_FACILITY_NAME = "FACILITY_NAME";
  public String ATTRIBUTE_STATUS = "STATUS";
  public String ATTRIBUTE_REPORTING_ENTITY_ID = "REPORTING_ENTITY_ID";
  public String ATTRIBUTE_REPORTING_ENTITY_LABEL = "REPORTING_ENTITY_LABEL";
  public String ATTRIBUTE_REPORTING_ENTITY_DESCRIPTION = "REPORTING_ENTITY_DESCRIPTION";
  public String ATTRIBUTE_DELIVERY_POINT = "DELIVERY_POINT";
  public String ATTRIBUTE_DELIVERY_POINT_DESC = "DELIVERY_POINT_DESC";
  public String ATTRIBUTE_DOCK_LOCATION_ID = "DOCK_LOCATION_ID";
  public String ATTRIBUTE_DOCK_DESC = "DOCK_DESC";

  //table name
  public String TABLE = "MY_WORKAREA_FACILITY_VIEW";

  //constructor
  public MyWorkareaFacilityViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("personnelId")) {
      return ATTRIBUTE_PERSONNEL_ID;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("application")) {
      return ATTRIBUTE_APPLICATION;
    }
    else if (attributeName.equals("applicationDesc")) {
      return ATTRIBUTE_APPLICATION_DESC;
    }
    else if (attributeName.equals("facilityName")) {
      return ATTRIBUTE_FACILITY_NAME;
    }
    else if (attributeName.equals("status")) {
      return ATTRIBUTE_STATUS;
    }
    else if (attributeName.equals("reportingEntityId")) {
      return ATTRIBUTE_REPORTING_ENTITY_ID;
    }
    else if (attributeName.equals("reportingEntityLabel")) {
      return ATTRIBUTE_REPORTING_ENTITY_LABEL;
    }
    else if (attributeName.equals("reportingEntityDescription")) {
      return ATTRIBUTE_REPORTING_ENTITY_DESCRIPTION;
    }
    else if (attributeName.equals("deliveryPoint")) {
      return ATTRIBUTE_DELIVERY_POINT;
    }
    else if (attributeName.equals("deliveryPointDesc")) {
      return ATTRIBUTE_DELIVERY_POINT_DESC;
    }
    else if (attributeName.equals("dockLocationId")) {
      return ATTRIBUTE_DOCK_LOCATION_ID;
    }
    else if (attributeName.equals("dockDesc")) {
      return ATTRIBUTE_DOCK_DESC;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, MyWorkareaFacilityViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(MyWorkareaFacilityViewBean myWorkareaFacilityViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("personnelId", "SearchCriterion.EQUALS",
     "" + myWorkareaFacilityViewBean.getPersonnelId());
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
       public int delete(MyWorkareaFacilityViewBean myWorkareaFacilityViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("personnelId", "SearchCriterion.EQUALS",
     "" + myWorkareaFacilityViewBean.getPersonnelId());
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
  /*
//insert
   public int insert(MyWorkareaFacilityViewBean myWorkareaFacilityViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(myWorkareaFacilityViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int insert(MyWorkareaFacilityViewBean myWorkareaFacilityViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_PERSONNEL_ID + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_APPLICATION + "," +
     ATTRIBUTE_APPLICATION_DESC + "," +
     ATTRIBUTE_FACILITY_NAME + "," +
     ATTRIBUTE_STATUS + "," +
     ATTRIBUTE_REPORTING_ENTITY_ID + "," +
     ATTRIBUTE_REPORTING_ENTITY_LABEL + "," +
     ATTRIBUTE_REPORTING_ENTITY_DESCRIPTION + "," +
     ATTRIBUTE_DELIVERY_POINT + "," +
     ATTRIBUTE_DELIVERY_POINT_DESC + "," +
     ATTRIBUTE_DOCK_LOCATION_ID + "," +
     ATTRIBUTE_DOCK_DESC + ")" +
     " values (" +
     myWorkareaFacilityViewBean.getPersonnelId() + "," +
     SqlHandler.delimitString(myWorkareaFacilityViewBean.getFacilityId()) + "," +
     SqlHandler.delimitString(myWorkareaFacilityViewBean.getApplication()) + "," +
     SqlHandler.delimitString(myWorkareaFacilityViewBean.getApplicationDesc()) + "," +
     SqlHandler.delimitString(myWorkareaFacilityViewBean.getFacilityName()) + "," +
     SqlHandler.delimitString(myWorkareaFacilityViewBean.getStatus()) + "," +
     SqlHandler.delimitString(myWorkareaFacilityViewBean.getReportingEntityId()) + "," +
       SqlHandler.delimitString(myWorkareaFacilityViewBean.getReportingEntityLabel()) + "," +
     SqlHandler.delimitString(myWorkareaFacilityViewBean.getReportingEntityDescription()) + "," +
     SqlHandler.delimitString(myWorkareaFacilityViewBean.getDeliveryPoint()) + "," +
     SqlHandler.delimitString(myWorkareaFacilityViewBean.getDeliveryPointDesc()) + "," +
     SqlHandler.delimitString(myWorkareaFacilityViewBean.getDockLocationId()) + "," +
     SqlHandler.delimitString(myWorkareaFacilityViewBean.getDockDesc()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(MyWorkareaFacilityViewBean myWorkareaFacilityViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(myWorkareaFacilityViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int update(MyWorkareaFacilityViewBean myWorkareaFacilityViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_PERSONNEL_ID + "=" +
      StringHandler.nullIfZero(myWorkareaFacilityViewBean.getPersonnelId()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(myWorkareaFacilityViewBean.getFacilityId()) + "," +
     ATTRIBUTE_APPLICATION + "=" +
      SqlHandler.delimitString(myWorkareaFacilityViewBean.getApplication()) + "," +
     ATTRIBUTE_APPLICATION_DESC + "=" +
      SqlHandler.delimitString(myWorkareaFacilityViewBean.getApplicationDesc()) + "," +
     ATTRIBUTE_FACILITY_NAME + "=" +
      SqlHandler.delimitString(myWorkareaFacilityViewBean.getFacilityName()) + "," +
     ATTRIBUTE_STATUS + "=" +
      SqlHandler.delimitString(myWorkareaFacilityViewBean.getStatus()) + "," +
     ATTRIBUTE_REPORTING_ENTITY_ID + "=" +
      SqlHandler.delimitString(myWorkareaFacilityViewBean.getReportingEntityId()) + "," +
     ATTRIBUTE_REPORTING_ENTITY_LABEL + "=" +
       SqlHandler.delimitString(myWorkareaFacilityViewBean.getReportingEntityLabel()) + "," +
     ATTRIBUTE_REPORTING_ENTITY_DESCRIPTION + "=" +
      SqlHandler.delimitString(myWorkareaFacilityViewBean.getReportingEntityDescription()) + "," +
     ATTRIBUTE_DELIVERY_POINT + "=" +
      SqlHandler.delimitString(myWorkareaFacilityViewBean.getDeliveryPoint()) + "," +
     ATTRIBUTE_DELIVERY_POINT_DESC + "=" +
      SqlHandler.delimitString(myWorkareaFacilityViewBean.getDeliveryPointDesc()) + "," +
     ATTRIBUTE_DOCK_LOCATION_ID + "=" +
      SqlHandler.delimitString(myWorkareaFacilityViewBean.getDockLocationId()) + "," +
     ATTRIBUTE_DOCK_DESC + "=" +
      SqlHandler.delimitString(myWorkareaFacilityViewBean.getDockDesc()) + " " +
     "where " + ATTRIBUTE_PERSONNEL_ID + "=" +
      myWorkareaFacilityViewBean.getPersonnelId();
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
    Collection myWorkareaFacilityViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      MyWorkareaFacilityViewBean myWorkareaFacilityViewBean = new
          MyWorkareaFacilityViewBean();
      load(dataSetRow, myWorkareaFacilityViewBean);
      myWorkareaFacilityViewBeanColl.add(myWorkareaFacilityViewBean);
    }
    return myWorkareaFacilityViewBeanColl;
  }
}