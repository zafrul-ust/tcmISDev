package com.tcmis.internal.hub.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.internal.hub.beans.HubIgDockOvBean;

/******************************************************************************
 * CLASSNAME: HubIgDockOvBeanFactory <br>
 * @version: 1.0, Apr 19, 2005 <br>
 *****************************************************************************/

public class HubIgDockOvBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_PRIORITY = "PRIORITY";
  public String ATTRIBUTE_HOME_COMPANY_ID = "HOME_COMPANY_ID";
  public String ATTRIBUTE_HOME_CURRENCY_ID = "HOME_CURRENCY_ID";
  public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
  public String ATTRIBUTE_HUB_NAME = "HUB_NAME";
  public String ATTRIBUTE_INVENTORY_GROUP_DOCKS = "INVENTORY_GROUP_DOCKS";

  //table name
  public String TABLE = "HUB_IG_DOCK_OV";

  //constructor
  public HubIgDockOvBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("priority")) {
      return ATTRIBUTE_PRIORITY;
    }
    else if (attributeName.equals("homeCompanyId")) {
      return ATTRIBUTE_HOME_COMPANY_ID;
    }
    else if (attributeName.equals("homeCurrencyId")) {
      return ATTRIBUTE_HOME_CURRENCY_ID;
    }
    else if (attributeName.equals("branchPlant")) {
      return ATTRIBUTE_BRANCH_PLANT;
    }
    else if (attributeName.equals("hubName")) {
      return ATTRIBUTE_HUB_NAME;
    }
    else if (attributeName.equals("inventoryGroupDocks")) {
      return ATTRIBUTE_INVENTORY_GROUP_DOCKS;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, HubIgDockOvBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(HubIgDockOvBean hubIgDockOvBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
     "" + hubIgDockOvBean.getCompanyId());
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
   public int delete(HubIgDockOvBean hubIgDockOvBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
     "" + hubIgDockOvBean.getCompanyId());
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
   public int insert(HubIgDockOvBean hubIgDockOvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(hubIgDockOvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(HubIgDockOvBean hubIgDockOvBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_PERSONNEL_ID + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_PRIORITY + "," +
     ATTRIBUTE_HOME_COMPANY_ID + "," +
     ATTRIBUTE_HOME_CURRENCY_ID + "," +
     ATTRIBUTE_BRANCH_PLANT + "," +
     ATTRIBUTE_HUB_NAME + "," +
     ATTRIBUTE_INVENTORY_GROUP_DOCKS + ")" +
     " values (" +
     SqlHandler.delimitString(hubIgDockOvBean.getCompanyId()) + "," +
     hubIgDockOvBean.getPersonnelId() + "," +
     SqlHandler.delimitString(hubIgDockOvBean.getFacilityId()) + "," +
     hubIgDockOvBean.getPriority() + "," +
     SqlHandler.delimitString(hubIgDockOvBean.getHomeCompanyId()) + "," +
     SqlHandler.delimitString(hubIgDockOvBean.getHomeCurrencyId()) + "," +
     SqlHandler.delimitString(hubIgDockOvBean.getBranchPlant()) + "," +
     SqlHandler.delimitString(hubIgDockOvBean.getHubName()) + "," +
     SqlHandler.delimitString(hubIgDockOvBean.getInventoryGroupDocks()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(HubIgDockOvBean hubIgDockOvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(hubIgDockOvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(HubIgDockOvBean hubIgDockOvBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(hubIgDockOvBean.getCompanyId()) + "," +
     ATTRIBUTE_PERSONNEL_ID + "=" +
      StringHandler.nullIfZero(hubIgDockOvBean.getPersonnelId()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(hubIgDockOvBean.getFacilityId()) + "," +
     ATTRIBUTE_PRIORITY + "=" +
      StringHandler.nullIfZero(hubIgDockOvBean.getPriority()) + "," +
     ATTRIBUTE_HOME_COMPANY_ID + "=" +
      SqlHandler.delimitString(hubIgDockOvBean.getHomeCompanyId()) + "," +
     ATTRIBUTE_HOME_CURRENCY_ID + "=" +
      SqlHandler.delimitString(hubIgDockOvBean.getHomeCurrencyId()) + "," +
     ATTRIBUTE_BRANCH_PLANT + "=" +
      SqlHandler.delimitString(hubIgDockOvBean.getBranchPlant()) + "," +
     ATTRIBUTE_HUB_NAME + "=" +
      SqlHandler.delimitString(hubIgDockOvBean.getHubName()) + "," +
     ATTRIBUTE_INVENTORY_GROUP_DOCKS + "=" +
       SqlHandler.delimitString(hubIgDockOvBean.getInventoryGroupDocks()) + " " +
     "where " + ATTRIBUTE_COMPANY_ID + "=" +
      hubIgDockOvBean.getCompanyId();
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

    Collection hubIgDockOvBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      HubIgDockOvBean hubIgDockOvBean = new HubIgDockOvBean();
      load(dataSetRow, hubIgDockOvBean);
      hubIgDockOvBeanColl.add(hubIgDockOvBean);
    }

    return hubIgDockOvBeanColl;
  }

  public Collection selectObject(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      java.util.Map map = connection.getTypeMap();
      map.put("HUB_IG_DOCK_OBJ",
              Class.forName(
          "com.tcmis.internal.hub.beans.HubIgDockOvBean"));
      map.put("INVENTORY_GROUP_DOCK_OBJ",
              Class.forName(
          "com.tcmis.internal.hub.beans.InventoryGroupDockObjBean"));
      map.put("CUSTOMER.FACILITY_DOCK_OBJ",
              Class.forName(
          "com.tcmis.internal.hub.beans.FacilityDockObjBean"));

      c = selectObject(criteria, connection);
    }
    catch (Exception e) {
      log.error("selectObject error:" + e.getMessage());
			DbSelectException ex = new DbSelectException("error.db.select");
      /*DbSelectException ex = new DbSelectException("db.selectObject.error:" +
          e.getMessage());*/
      ex.setRootCause(e);
      throw ex;
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectObject(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection hubIgDockOvBeanColl = new Vector();

	String sortBy = " order by " + ATTRIBUTE_PRIORITY+ "," + ATTRIBUTE_FACILITY_ID;
    String query = "select value(p) from " + TABLE + " p " +
        getWhereClause(criteria) + sortBy;

    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    try {
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery(query);
      while (rs.next()) {
        HubIgDockOvBean b = (HubIgDockOvBean) rs.getObject(1);
        hubIgDockOvBeanColl.add(b);
      }
      rs.close();
      st.close();
    }
    catch (SQLException e) {
      log.error("selectObject error:" + e.getMessage());
			DbSelectException ex = new DbSelectException("error.db.select");
      /*DbSelectException ex = new DbSelectException("selectObject error:" +
          e.getMessage());*/
      ex.setRootCause(e);
      throw ex;
    }
    return hubIgDockOvBeanColl;
  }

}