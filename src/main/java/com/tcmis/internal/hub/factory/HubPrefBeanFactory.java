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
 * CLASSNAME: HubPrefBeanFactory <br>
 * @version: 1.0, Jan 21, 2005 <br>
 *****************************************************************************/

public class HubPrefBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_HUB = "HUB";
  public String ATTRIBUTE_PRIORITY = "PRIORITY";

  //table name
  public String TABLE = "HUB_PREF";

  //constructor
  public HubPrefBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("personnelId")) {
      return ATTRIBUTE_PERSONNEL_ID;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("hub")) {
      return ATTRIBUTE_HUB;
    }
    else if (attributeName.equals("priority")) {
      return ATTRIBUTE_PRIORITY;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, HubPrefBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(HubPrefBean hubPrefBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "=",
     "" + hubPrefBean.getCompanyId());
    Connection connection = this.getDbManager().getConnection();
    int result = this.delete(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return result;
   }
   public int delete(HubPrefBean hubPrefBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "=",
     "" + hubPrefBean.getCompanyId());
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
   public int insert(HubPrefBean hubPrefBean)
    throws BaseException {
    Connection connection = getDbManager().getConnection();
    int result = insert(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return result;
   }
   public int insert(HubPrefBean hubPrefBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_PERSONNEL_ID + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_HUB + "," +
     ATTRIBUTE_PRIORITY + ")" +
     SqlHandler.delimitString(hubPrefBean.getCompanyId()) + "," +
     StringHandler.nullIfZero(hubPrefBean.getPersonnelId()) + "," +
     SqlHandler.delimitString(hubPrefBean.getFacilityId()) + "," +
     SqlHandler.delimitString(hubPrefBean.getHub()) + "," +
     StringHandler.nullIfZero(hubPrefBean.getPriority()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(HubPrefBean hubPrefBean)
    throws BaseException {
    Connection connection = getDbManager().getConnection();
    int result = update(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return result;
   }
   public int update(HubPrefBean hubPrefBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(hubPrefBean.getCompanyId()) + "," +
     ATTRIBUTE_PERSONNEL_ID + "=" +
      StringHandler.nullIfZero(hubPrefBean.getPersonnelId()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(hubPrefBean.getFacilityId()) + "," +
     ATTRIBUTE_HUB + "=" +
      SqlHandler.delimitString(hubPrefBean.getHub()) + "," +
     ATTRIBUTE_PRIORITY + "=" +
      StringHandler.nullIfZero(hubPrefBean.getPriority()) + " " +
     "where " + ATTRIBUTE_COMPANY_ID + "=" +
      StringHandler.nullIfZero(hubPrefBean.getCompanyId());
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

    Collection hubPrefBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      HubPrefBean hubPrefBean = new HubPrefBean();
      load(dataSetRow, hubPrefBean);
      hubPrefBeanColl.add(hubPrefBean);
    }

    return hubPrefBeanColl;
  }

  public Collection selectUniqueFacility() throws
      BaseException {

    Collection hubPrefBeanColl = new Vector();

    String query = "select unique " + ATTRIBUTE_FACILITY_ID + ", " +
        ATTRIBUTE_HUB + "," +
        ATTRIBUTE_PRIORITY + " from " + TABLE;
    Connection connection = getDbManager().getConnection();
    DataSet dataSet = new SqlManager().select(connection, query);
    getDbManager().returnConnection(connection);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      HubPrefBean hubPrefBean = new HubPrefBean();
      load(dataSetRow, hubPrefBean);
      hubPrefBeanColl.add(hubPrefBean);
    }

    return hubPrefBeanColl;
  }

}