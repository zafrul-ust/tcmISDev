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
import com.tcmis.client.report.beans.FacAppDockDpViewBean;

/******************************************************************************
 * CLASSNAME: FacAppDockDpViewBeanFactory <br>
 * @version: 1.0, Mar 1, 2006 <br>
 *****************************************************************************/

public class FacAppDockDpViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_DOCK_LOCATION_ID = "DOCK_LOCATION_ID";
  public String ATTRIBUTE_DOCK_DESC = "DOCK_DESC";
  public String ATTRIBUTE_DELIVERY_POINT = "DELIVERY_POINT";
  public String ATTRIBUTE_DELIVERY_POINT_DESC = "DELIVERY_POINT_DESC";

  //table name
  public String TABLE = "FAC_APP_DOCK_DP_VIEW";

  //constructor
  public FacAppDockDpViewBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("dockLocationId")) {
      return ATTRIBUTE_DOCK_LOCATION_ID;
    }
    else if (attributeName.equals("dockDesc")) {
      return ATTRIBUTE_DOCK_DESC;
    }
    else if (attributeName.equals("deliveryPoint")) {
      return ATTRIBUTE_DELIVERY_POINT;
    }
    else if (attributeName.equals("deliveryPointDesc")) {
      return ATTRIBUTE_DELIVERY_POINT_DESC;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, FacAppDockDpViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(FacAppDockDpViewBean facAppDockDpViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("personnelId", "SearchCriterion.EQUALS",
     "" + facAppDockDpViewBean.getPersonnelId());
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
   public int delete(FacAppDockDpViewBean facAppDockDpViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("personnelId", "SearchCriterion.EQUALS",
     "" + facAppDockDpViewBean.getPersonnelId());
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
   public int insert(FacAppDockDpViewBean facAppDockDpViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(facAppDockDpViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(FacAppDockDpViewBean facAppDockDpViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_PERSONNEL_ID + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_DOCK_LOCATION_ID + "," +
     ATTRIBUTE_DOCK_DESC + "," +
     ATTRIBUTE_DELIVERY_POINT + "," +
     ATTRIBUTE_DELIVERY_POINT_DESC + ")" +
     " values (" +
     facAppDockDpViewBean.getPersonnelId() + "," +
     SqlHandler.delimitString(facAppDockDpViewBean.getFacilityId()) + "," +
     SqlHandler.delimitString(facAppDockDpViewBean.getDockLocationId()) + "," +
     SqlHandler.delimitString(facAppDockDpViewBean.getDockDesc()) + "," +
     SqlHandler.delimitString(facAppDockDpViewBean.getDeliveryPoint()) + "," +
     SqlHandler.delimitString(facAppDockDpViewBean.getDeliveryPointDesc()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(FacAppDockDpViewBean facAppDockDpViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(facAppDockDpViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(FacAppDockDpViewBean facAppDockDpViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_PERSONNEL_ID + "=" +
      StringHandler.nullIfZero(facAppDockDpViewBean.getPersonnelId()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(facAppDockDpViewBean.getFacilityId()) + "," +
     ATTRIBUTE_DOCK_LOCATION_ID + "=" +
      SqlHandler.delimitString(facAppDockDpViewBean.getDockLocationId()) + "," +
     ATTRIBUTE_DOCK_DESC + "=" +
      SqlHandler.delimitString(facAppDockDpViewBean.getDockDesc()) + "," +
     ATTRIBUTE_DELIVERY_POINT + "=" +
      SqlHandler.delimitString(facAppDockDpViewBean.getDeliveryPoint()) + "," +
     ATTRIBUTE_DELIVERY_POINT_DESC + "=" +
      SqlHandler.delimitString(facAppDockDpViewBean.getDeliveryPointDesc()) + " " +
     "where " + ATTRIBUTE_PERSONNEL_ID + "=" +
      facAppDockDpViewBean.getPersonnelId();
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
    Collection facAppDockDpViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) +
		  " order by " + ATTRIBUTE_FACILITY_ID + "," + ATTRIBUTE_DOCK_DESC + "," + ATTRIBUTE_DELIVERY_POINT_DESC;
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      FacAppDockDpViewBean facAppDockDpViewBean = new FacAppDockDpViewBean();
      load(dataSetRow, facAppDockDpViewBean);
      facAppDockDpViewBeanColl.add(facAppDockDpViewBean);
    }
    return facAppDockDpViewBeanColl;
  }
}