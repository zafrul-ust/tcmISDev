package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.MyWorkareaFacilityViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;

/******************************************************************************
 * CLASSNAME: MyWorkareaFacilityViewBeanFactory <br>
 * @version: 1.0, May 3, 2005 <br>
 *****************************************************************************/

public class MyWorkareaFacilityViewBeanFactory extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_APPLICATION = "APPLICATION";
  public String ATTRIBUTE_APPLICATION_DESC = "APPLICATION_DESC";
  public String ATTRIBUTE_FACILITY_NAME = "FACILITY_NAME";
  public String ATTRIBUTE_STATUS = "STATUS";

  //table name
  public String TABLE = "MY_WORKAREA_FACILITY_VIEW ";

  //constructor
  public MyWorkareaFacilityViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("personnelId")) {
      return ATTRIBUTE_PERSONNEL_ID;
    } else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    } else if (attributeName.equals("application")) {
      return ATTRIBUTE_APPLICATION;
    } else if (attributeName.equals("applicationDesc")) {
      return ATTRIBUTE_APPLICATION_DESC;
    } else if (attributeName.equals("facilityName")) {
      return ATTRIBUTE_FACILITY_NAME;
    } else if (attributeName.equals("status")) {
      return ATTRIBUTE_STATUS;
    } else {
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
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int delete(SearchCriteria criteria, Connection conn) throws BaseException {

    String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

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
   ATTRIBUTE_STATUS + ")" +
   " values (" +
   myWorkareaFacilityViewBean.getPersonnelId() + "," +
   SqlHandler.delimitString(myWorkareaFacilityViewBean.getFacilityId()) + "," +
   SqlHandler.delimitString(myWorkareaFacilityViewBean.getApplication()) + "," +
   SqlHandler.delimitString(myWorkareaFacilityViewBean.getApplicationDesc()) + "," +
   SqlHandler.delimitString(myWorkareaFacilityViewBean.getFacilityName()) + "," +
   SqlHandler.delimitString(myWorkareaFacilityViewBean.getStatus()) + ")";
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
    SqlHandler.delimitString(myWorkareaFacilityViewBean.getStatus()) + " " +
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
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(SearchCriteria criteria, Connection conn) throws BaseException {

    Collection myWorkareaFacilityViewBeanColl = new Vector();

    String query = "select * from " + TABLE + " " + getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      MyWorkareaFacilityViewBean myWorkareaFacilityViewBean = new MyWorkareaFacilityViewBean();
      load(dataSetRow, myWorkareaFacilityViewBean);
      myWorkareaFacilityViewBeanColl.add(myWorkareaFacilityViewBean);
    }

    return myWorkareaFacilityViewBeanColl;
  }

  //select
  public Collection selectOnlyFacility(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectOnlyFacility(criteria, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectOnlyFacility(SearchCriteria criteria, Connection conn) throws BaseException {

    Collection myWorkareaFacilityViewBeanColl = new Vector();

    String query = "select distinct facility_id,facility_name from " + TABLE + " " + getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      MyWorkareaFacilityViewBean myWorkareaFacilityViewBean = new MyWorkareaFacilityViewBean();
      load(dataSetRow, myWorkareaFacilityViewBean);
      myWorkareaFacilityViewBeanColl.add(myWorkareaFacilityViewBean);
    }

    return myWorkareaFacilityViewBeanColl;
  }

  //select
  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(criteria,sortCriteria, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(SearchCriteria criteria,SortCriteria sortCriteria, Connection conn) throws BaseException {

    Collection myWorkareaFacilityViewBeanColl = new Vector();

    String query = "select * from " + TABLE + " " + getWhereClause(criteria);
    if (sortCriteria !=null)
    {
      query += getOrderByClause(sortCriteria);
    }

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      MyWorkareaFacilityViewBean myWorkareaFacilityViewBean = new MyWorkareaFacilityViewBean();
      load(dataSetRow, myWorkareaFacilityViewBean);
      myWorkareaFacilityViewBeanColl.add(myWorkareaFacilityViewBean);
    }

    return myWorkareaFacilityViewBeanColl;
  }

}