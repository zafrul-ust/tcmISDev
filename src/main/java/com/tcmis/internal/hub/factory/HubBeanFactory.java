package com.tcmis.internal.hub.factory;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.*;

/******************************************************************************
 * CLASSNAME: HubBeanFactory <br>
 * @version: 1.0, Jan 21, 2005 <br>
 *****************************************************************************/

public class HubBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
  public String ATTRIBUTE_E_GL_SEGMENT2 = "E_GL_SEGMENT2";
  public String ATTRIBUTE_HUB_NAME = "HUB_NAME";
  public String ATTRIBUTE_TESTING_DAYS = "TESTING_DAYS";
  public String ATTRIBUTE_LOCATION_ID = "LOCATION_ID";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_BLDG_ID = "BLDG_ID";
  public String ATTRIBUTE_STORAGE_ID = "STORAGE_ID";
  public String ATTRIBUTE_HOME_COMPANY_ID = "HOME_COMPANY_ID";

  //table name
  public String TABLE = "HUB";

  //constructor
  public HubBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("branchPlant")) {
      return ATTRIBUTE_BRANCH_PLANT;
    }
    else if (attributeName.equals("eGlSegment2")) {
      return ATTRIBUTE_E_GL_SEGMENT2;
    }
    else if (attributeName.equals("hubName")) {
      return ATTRIBUTE_HUB_NAME;
    }
    else if (attributeName.equals("testingDays")) {
      return ATTRIBUTE_TESTING_DAYS;
    }
    else if (attributeName.equals("locationId")) {
      return ATTRIBUTE_LOCATION_ID;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("bldgId")) {
      return ATTRIBUTE_BLDG_ID;
    }
    else if (attributeName.equals("storageId")) {
      return ATTRIBUTE_STORAGE_ID;
    }
    else if (attributeName.equals("homeCompanyId")) {
      return ATTRIBUTE_HOME_COMPANY_ID;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, HubBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(HubBean hubBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "=",
     "" + hubBean.getCompanyId());
    Connection connection = this.getDbManager().getConnection();
    int result = this.delete(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return result;
   }
   public int delete(HubBean hubBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "=",
     "" + hubBean.getCompanyId());
    return delete(criteria, conn);
   }
   */

  public int delete(SearchCriteria criteria) throws BaseException {

    Connection connection = getDbManager().getConnection();
    int result = delete(criteria, connection);
    this.getDbManager().returnConnection(connection);
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
   public int insert(HubBean hubBean)
    throws BaseException {
    Connection connection = getDbManager().getConnection();
    int result = insert(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return result;
   }
   public int insert(HubBean hubBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_BRANCH_PLANT + "," +
     ATTRIBUTE_E_GL_SEGMENT2 + "," +
     ATTRIBUTE_HUB_NAME + "," +
     ATTRIBUTE_TESTING_DAYS + "," +
     ATTRIBUTE_LOCATION_ID + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_BLDG_ID + "," +
     ATTRIBUTE_STORAGE_ID + "," +
     ATTRIBUTE_HOME_COMPANY_ID + ")" +
     SqlHandler.delimitString(hubBean.getCompanyId()) + "," +
     SqlHandler.delimitString(hubBean.getBranchPlant()) + "," +
     SqlHandler.delimitString(hubBean.getEGlSegment2()) + "," +
     SqlHandler.delimitString(hubBean.getHubName()) + "," +
     StringHandler.nullIfZero(hubBean.getTestingDays()) + "," +
     SqlHandler.delimitString(hubBean.getLocationId()) + "," +
     SqlHandler.delimitString(hubBean.getFacilityId()) + "," +
     SqlHandler.delimitString(hubBean.getBldgId()) + "," +
     SqlHandler.delimitString(hubBean.getStorageId()) + "," +
     SqlHandler.delimitString(hubBean.getHomeCompanyId()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(HubBean hubBean)
    throws BaseException {
    Connection connection = getDbManager().getConnection();
    int result = update(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return result;
   }
   public int update(HubBean hubBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(hubBean.getCompanyId()) + "," +
     ATTRIBUTE_BRANCH_PLANT + "=" +
      SqlHandler.delimitString(hubBean.getBranchPlant()) + "," +
     ATTRIBUTE_E_GL_SEGMENT2 + "=" +
      SqlHandler.delimitString(hubBean.getEGlSegment2()) + "," +
     ATTRIBUTE_HUB_NAME + "=" +
      SqlHandler.delimitString(hubBean.getHubName()) + "," +
     ATTRIBUTE_TESTING_DAYS + "=" +
      StringHandler.nullIfZero(hubBean.getTestingDays()) + "," +
     ATTRIBUTE_LOCATION_ID + "=" +
      SqlHandler.delimitString(hubBean.getLocationId()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(hubBean.getFacilityId()) + "," +
     ATTRIBUTE_BLDG_ID + "=" +
      SqlHandler.delimitString(hubBean.getBldgId()) + "," +
     ATTRIBUTE_STORAGE_ID + "=" +
      SqlHandler.delimitString(hubBean.getStorageId()) + "," +
     ATTRIBUTE_HOME_COMPANY_ID + "=" +
      SqlHandler.delimitString(hubBean.getHomeCompanyId()) + " " +
     "where " + ATTRIBUTE_COMPANY_ID + "=" +
      StringHandler.nullIfZero(hubBean.getCompanyId());
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(SearchCriteria criteria) throws BaseException {

    Connection connection = this.getDbManager().getConnection();
    Collection c = select(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return c;
  }

  public Collection select(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection hubBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      HubBean hubBean = new HubBean();
      load(dataSetRow, hubBean);
      hubBeanColl.add(hubBean);
    }

    return hubBeanColl;
  }

  public Collection selectHubs() throws
      BaseException {

    Collection hubBeanColl = new Vector();

    String query =
        "select hub_name, branch_plant from hub where branch_plant in " +
        "(select hub from inventory_group_definition)";
    Connection connection = getDbManager().getConnection();
    DataSet dataSet = new SqlManager().select(connection, query);
    getDbManager().returnConnection(connection);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      HubBean hubBean = new HubBean();
      load(dataSetRow, hubBean);
      hubBeanColl.add(hubBean);
    }

    return hubBeanColl;
  }

}