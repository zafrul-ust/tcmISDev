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
import com.tcmis.client.report.beans.UserReportCategoryViewBean;

/******************************************************************************
 * CLASSNAME: UserReportCategoryViewBeanFactory <br>
 * @version: 1.0, Mar 1, 2006 <br>
 *****************************************************************************/

public class UserReportCategoryViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_APPLICATION = "APPLICATION";
  public String ATTRIBUTE_ROOT_CATEGORY = "ROOT_CATEGORY";
  public String ATTRIBUTE_LOCATION = "LOCATION";
  public String ATTRIBUTE_APPLICATION_ORDER = "APPLICATION_ORDER";
  public String ATTRIBUTE_CATEGORY_ORDER = "CATEGORY_ORDER";
  public String ATTRIBUTE_LOCATION_ORDER = "LOCATION_ORDER";

  //table name
  public String TABLE = "USER_REPORT_CATEGORY_VIEW";

  //constructor
  public UserReportCategoryViewBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("rootCategory")) {
      return ATTRIBUTE_ROOT_CATEGORY;
    }
    else if (attributeName.equals("location")) {
      return ATTRIBUTE_LOCATION;
    }
    else if (attributeName.equals("applicationOrder")) {
      return ATTRIBUTE_APPLICATION_ORDER;
    }
    else if (attributeName.equals("categoryOrder")) {
      return ATTRIBUTE_CATEGORY_ORDER;
    }
    else if (attributeName.equals("locationOrder")) {
      return ATTRIBUTE_LOCATION_ORDER;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, UserReportCategoryViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(UserReportCategoryViewBean userReportCategoryViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("personnelId", "SearchCriterion.EQUALS",
     "" + userReportCategoryViewBean.getPersonnelId());
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
       public int delete(UserReportCategoryViewBean userReportCategoryViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("personnelId", "SearchCriterion.EQUALS",
     "" + userReportCategoryViewBean.getPersonnelId());
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
   public int insert(UserReportCategoryViewBean userReportCategoryViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(userReportCategoryViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int insert(UserReportCategoryViewBean userReportCategoryViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_PERSONNEL_ID + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_APPLICATION + "," +
     ATTRIBUTE_ROOT_CATEGORY + "," +
     ATTRIBUTE_LOCATION + "," +
     ATTRIBUTE_APPLICATION_ORDER + "," +
     ATTRIBUTE_CATEGORY_ORDER + "," +
     ATTRIBUTE_LOCATION_ORDER + ")" +
     " values (" +
     userReportCategoryViewBean.getPersonnelId() + "," +
     SqlHandler.delimitString(userReportCategoryViewBean.getFacilityId()) + "," +
     SqlHandler.delimitString(userReportCategoryViewBean.getApplication()) + "," +
     SqlHandler.delimitString(userReportCategoryViewBean.getRootCategory()) + "," +
     SqlHandler.delimitString(userReportCategoryViewBean.getLocation()) + "," +
     userReportCategoryViewBean.getApplicationOrder() + "," +
     userReportCategoryViewBean.getCategoryOrder() + "," +
     userReportCategoryViewBean.getLocationOrder() + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(UserReportCategoryViewBean userReportCategoryViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(userReportCategoryViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int update(UserReportCategoryViewBean userReportCategoryViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_PERSONNEL_ID + "=" +
      StringHandler.nullIfZero(userReportCategoryViewBean.getPersonnelId()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(userReportCategoryViewBean.getFacilityId()) + "," +
     ATTRIBUTE_APPLICATION + "=" +
      SqlHandler.delimitString(userReportCategoryViewBean.getApplication()) + "," +
     ATTRIBUTE_ROOT_CATEGORY + "=" +
      SqlHandler.delimitString(userReportCategoryViewBean.getRootCategory()) + "," +
     ATTRIBUTE_LOCATION + "=" +
      SqlHandler.delimitString(userReportCategoryViewBean.getLocation()) + "," +
     ATTRIBUTE_APPLICATION_ORDER + "=" +
      StringHandler.nullIfZero(userReportCategoryViewBean.getApplicationOrder()) + "," +
     ATTRIBUTE_CATEGORY_ORDER + "=" +
      StringHandler.nullIfZero(userReportCategoryViewBean.getCategoryOrder()) + "," +
     ATTRIBUTE_LOCATION_ORDER + "=" +
      StringHandler.nullIfZero(userReportCategoryViewBean.getLocationOrder()) + " " +
     "where " + ATTRIBUTE_PERSONNEL_ID + "=" +
      userReportCategoryViewBean.getPersonnelId();
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
    Collection userReportCategoryViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      UserReportCategoryViewBean userReportCategoryViewBean = new
          UserReportCategoryViewBean();
      load(dataSetRow, userReportCategoryViewBean);
      userReportCategoryViewBeanColl.add(userReportCategoryViewBean);
    }
    return userReportCategoryViewBeanColl;
  }
}