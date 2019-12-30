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
 * CLASSNAME: HubCabinetViewBeanFactory <br>
 * @version: 1.0, Jan 21, 2005 <br>
 *****************************************************************************/

public class HubCabinetViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PREFERRED_WAREHOUSE = "PREFERRED_WAREHOUSE";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_FACILITY_NAME = "FACILITY_NAME";
  public String ATTRIBUTE_APPLICATION = "APPLICATION";
  public String ATTRIBUTE_APPLICATION_DESC = "APPLICATION_DESC";
  public String ATTRIBUTE_CABINET_ID = "CABINET_ID";
  public String ATTRIBUTE_CABINET_NAME = "CABINET_NAME";
  public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
  public String ATTRIBUTE_HUB_NAME = "HUB_NAME";


  //table name
  public String TABLE = "HUB_CABINET_VIEW";

  //constructor
  public HubCabinetViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("preferredWarehouse")) {
      return ATTRIBUTE_PREFERRED_WAREHOUSE;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("facilityName")) {
      return ATTRIBUTE_FACILITY_NAME;
    }
    else if (attributeName.equals("application")) {
      return ATTRIBUTE_APPLICATION;
    }
    else if (attributeName.equals("applicationDesc")) {
      return ATTRIBUTE_APPLICATION_DESC;
    }
    else if (attributeName.equals("cabinetId")) {
      return ATTRIBUTE_CABINET_ID;
    }
    else if (attributeName.equals("cabinetName")) {
      return ATTRIBUTE_CABINET_NAME;
    }
    else if (attributeName.equals("branchPlant")) {
      return ATTRIBUTE_BRANCH_PLANT;
    }
    else if (attributeName.equals("hubName")) {
      return ATTRIBUTE_HUB_NAME;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, HubCabinetViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(HubCabinetViewBean hubCabinetViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("preferredWarehouse", "=",
     "" + hubCabinetViewBean.getPreferredWarehouse());
    Connection connection = this.getDbManager().getConnection();
    int result = this.delete(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return result;
   }
   public int delete(HubCabinetViewBean hubCabinetViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("preferredWarehouse", "=",
     "" + hubCabinetViewBean.getPreferredWarehouse());
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
   public int insert(HubCabinetViewBean hubCabinetViewBean)
    throws BaseException {
    Connection connection = getDbManager().getConnection();
    int result = insert(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return result;
   }
   public int insert(HubCabinetViewBean hubCabinetViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_PREFERRED_WAREHOUSE + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_FACILITY_NAME + "," +
     ATTRIBUTE_APPLICATION + "," +
     ATTRIBUTE_APPLICATION_DESC + "," +
     ATTRIBUTE_CABINET_ID + "," +
     ATTRIBUTE_CABINET_NAME + ")" +
       SqlHandler.delimitString(hubCabinetViewBean.getPreferredWarehouse()) + "," +
     SqlHandler.delimitString(hubCabinetViewBean.getCompanyId()) + "," +
     SqlHandler.delimitString(hubCabinetViewBean.getFacilityId()) + "," +
     SqlHandler.delimitString(hubCabinetViewBean.getFacilityName()) + "," +
     SqlHandler.delimitString(hubCabinetViewBean.getApplication()) + "," +
     SqlHandler.delimitString(hubCabinetViewBean.getApplicationDesc()) + "," +
     StringHandler.nullIfZero(hubCabinetViewBean.getCabinetId()) + "," +
     SqlHandler.delimitString(hubCabinetViewBean.getCabinetName()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(HubCabinetViewBean hubCabinetViewBean)
    throws BaseException {
    Connection connection = getDbManager().getConnection();
    int result = update(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return result;
   }
   public int update(HubCabinetViewBean hubCabinetViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_PREFERRED_WAREHOUSE + "=" +
       SqlHandler.delimitString(hubCabinetViewBean.getPreferredWarehouse()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(hubCabinetViewBean.getCompanyId()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(hubCabinetViewBean.getFacilityId()) + "," +
     ATTRIBUTE_FACILITY_NAME + "=" +
      SqlHandler.delimitString(hubCabinetViewBean.getFacilityName()) + "," +
     ATTRIBUTE_APPLICATION + "=" +
      SqlHandler.delimitString(hubCabinetViewBean.getApplication()) + "," +
     ATTRIBUTE_APPLICATION_DESC + "=" +
      SqlHandler.delimitString(hubCabinetViewBean.getApplicationDesc()) + "," +
     ATTRIBUTE_CABINET_ID + "=" +
      StringHandler.nullIfZero(hubCabinetViewBean.getCabinetId()) + "," +
     ATTRIBUTE_CABINET_NAME + "=" +
      SqlHandler.delimitString(hubCabinetViewBean.getCabinetName()) + " " +
     "where " + ATTRIBUTE_PREFERRED_WAREHOUSE + "=" +
      StringHandler.nullIfZero(hubCabinetViewBean.getPreferredWarehouse());
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

    Connection connection = this.getDbManager().getConnection();
    Collection c = select(criteria, sortCriteria, connection);
    this.getDbManager().returnConnection(connection);
    return c;
  }

  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws
      BaseException {

    Collection hubCabinetViewBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    if(log.isDebugEnabled()) {
      //log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      HubCabinetViewBean hubCabinetViewBean = new HubCabinetViewBean();
      load(dataSetRow, hubCabinetViewBean);
      if (hubCabinetViewBean.getCabinetName() != null) {
    	  hubCabinetViewBean.setCabinetName(hubCabinetViewBean.getCabinetName().replace("\"", "\\\""));
      }
      hubCabinetViewBeanColl.add(hubCabinetViewBean);
    }

    return hubCabinetViewBeanColl;
  }
}