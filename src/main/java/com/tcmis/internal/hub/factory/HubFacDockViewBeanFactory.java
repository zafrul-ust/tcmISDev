package com.tcmis.internal.hub.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.internal.hub.beans.CompanyOvBean;
import com.tcmis.internal.hub.beans.DockOvBean;
import com.tcmis.internal.hub.beans.FacilityOvBean;
import com.tcmis.internal.hub.beans.HubFacDockViewBean;
import com.tcmis.internal.hub.beans.HubFacDockViewOvBean;

/******************************************************************************
 * CLASSNAME: HubFacDockViewBeanFactory <br>
 * @version: 1.0, Apr 27, 2005 <br>
 *****************************************************************************/

public class HubFacDockViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_DOCK_LOCATION_ID = "DOCK_LOCATION_ID";
  public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
  public String ATTRIBUTE_HUB_NAME = "HUB_NAME";

  //table name
  public String TABLE = "HUB_FAC_DOCK_VIEW";

  //constructor
  public HubFacDockViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("personnelId")) {
      return ATTRIBUTE_PERSONNEL_ID;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("dockLocationId")) {
      return ATTRIBUTE_DOCK_LOCATION_ID;
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
    return super.getType(attributeName, HubFacDockViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(HubFacDockViewBean hubFacDockViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("personnelId", "SearchCriterion.EQUALS",
     "" + hubFacDockViewBean.getPersonnelId());
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
   public int delete(HubFacDockViewBean hubFacDockViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("personnelId", "SearchCriterion.EQUALS",
     "" + hubFacDockViewBean.getPersonnelId());
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
   public int insert(HubFacDockViewBean hubFacDockViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(hubFacDockViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(HubFacDockViewBean hubFacDockViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_PERSONNEL_ID + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_DOCK_LOCATION_ID + "," +
     ATTRIBUTE_BRANCH_PLANT + "," +
     ATTRIBUTE_HUB_NAME + ")" +
     " values (" +
     hubFacDockViewBean.getPersonnelId() + "," +
     SqlHandler.delimitString(hubFacDockViewBean.getCompanyId()) + "," +
     SqlHandler.delimitString(hubFacDockViewBean.getFacilityId()) + "," +
     SqlHandler.delimitString(hubFacDockViewBean.getDockLocationId()) + "," +
     SqlHandler.delimitString(hubFacDockViewBean.getBranchPlant()) + "," +
     SqlHandler.delimitString(hubFacDockViewBean.getHubName()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(HubFacDockViewBean hubFacDockViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(hubFacDockViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(HubFacDockViewBean hubFacDockViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_PERSONNEL_ID + "=" +
      StringHandler.nullIfZero(hubFacDockViewBean.getPersonnelId()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(hubFacDockViewBean.getCompanyId()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(hubFacDockViewBean.getFacilityId()) + "," +
     ATTRIBUTE_DOCK_LOCATION_ID + "=" +
      SqlHandler.delimitString(hubFacDockViewBean.getDockLocationId()) + "," +
     ATTRIBUTE_BRANCH_PLANT + "=" +
      SqlHandler.delimitString(hubFacDockViewBean.getBranchPlant()) + "," +
     ATTRIBUTE_HUB_NAME + "=" +
      SqlHandler.delimitString(hubFacDockViewBean.getHubName()) + " " +
     "where " + ATTRIBUTE_PERSONNEL_ID + "=" +
      hubFacDockViewBean.getPersonnelId();
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

    Collection hubFacDockViewBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) +
        " order by " +
        ATTRIBUTE_BRANCH_PLANT + "," +
        ATTRIBUTE_COMPANY_ID + "," +
        ATTRIBUTE_FACILITY_ID + "," +
        ATTRIBUTE_DOCK_LOCATION_ID;
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      HubFacDockViewBean hubFacDockViewBean = new HubFacDockViewBean();
      load(dataSetRow, hubFacDockViewBean);
      hubFacDockViewBeanColl.add(hubFacDockViewBean);
    }

    return hubFacDockViewBeanColl;
  }

  public Collection selectObject(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectObject(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectObject(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection hubFacDockViewBeanColl = new Vector();
    hubFacDockViewBeanColl = this.select(criteria, conn);
    return this.createRelationalObject(hubFacDockViewBeanColl);
  }

  private Collection createRelationalObject(Collection data) {
    String currentHub = null;
    String currentCompany = null;
    String currentFacility = null;
    String previousHub = null;
    String previousCompany = null;
    String previousFacility = null;
    Vector result = new Vector();
    Vector companies = new Vector();
    Vector facilities = new Vector();
    Vector docks = new Vector();
    HubFacDockViewOvBean hubFacDockViewOvBean = null;
    CompanyOvBean companyOvBean = null;
    FacilityOvBean facilityOvBean = null;
    com.tcmis.internal.hub.beans.HubFacDockViewBean bean = null;
    Iterator iterator = data.iterator();
    int hubCount = 0;
    boolean first = true;
    while (iterator.hasNext()) {
      bean = (com.tcmis.internal.hub.beans.HubFacDockViewBean) iterator.next();
      currentHub = bean.getBranchPlant();
      currentCompany = bean.getCompanyId();
      currentFacility = bean.getFacilityId();

      DockOvBean bean4 = new DockOvBean();
      bean4.setDock(bean.getDockLocationId());
      if (first) {
        facilityOvBean = new FacilityOvBean();
        facilityOvBean.setFacilityId(bean.getFacilityId());
        companyOvBean = new CompanyOvBean();
        companyOvBean.setCompanyId(bean.getCompanyId());
        hubFacDockViewOvBean = new HubFacDockViewOvBean();
        hubFacDockViewOvBean.setHub(bean.getBranchPlant());
        hubFacDockViewOvBean.setHubName(bean.getHubName());
      }
      else {
        if (!currentFacility.equalsIgnoreCase(previousFacility)) {
          //new facility
          FacilityOvBean copy3 = new FacilityOvBean();
          copy3.setFacilityId(facilityOvBean.getFacilityId());
          copy3.setDockCollection( (Vector) docks.clone());
          facilities.add(copy3);
          docks = new Vector();
          facilityOvBean = new FacilityOvBean();
          facilityOvBean.setFacilityId(bean.getFacilityId());
        }

        if (!currentCompany.equalsIgnoreCase(previousCompany)) {
          //new company
          CompanyOvBean copy2 = new CompanyOvBean();
          copy2.setCompanyId(companyOvBean.getCompanyId());
          copy2.setFacilityIdCollection( (Vector) facilities.clone());
          companies.add(copy2);
          facilities = new Vector();
          companyOvBean = new CompanyOvBean();
          companyOvBean.setCompanyId(bean.getCompanyId());
        }

        if (!currentHub.equalsIgnoreCase(previousHub)) {
          //new hub
          HubFacDockViewOvBean copy1 = new HubFacDockViewOvBean();
          copy1.setHub(hubFacDockViewOvBean.getHub());
          copy1.setHubName(hubFacDockViewOvBean.getHubName());
          copy1.setCompanyIdCollection( (Vector) companies.clone());
          result.add(copy1);
          companies = new Vector();
          hubFacDockViewOvBean = new HubFacDockViewOvBean();
          hubFacDockViewOvBean.setHub(bean.getBranchPlant());
          hubFacDockViewOvBean.setHubName(bean.getHubName());
        }
      }
      docks.add(bean4);
      first = false;
      previousHub = bean.getBranchPlant();
      previousCompany = bean.getCompanyId();
      previousFacility = bean.getFacilityId();
    }
    //now add last bean
    facilityOvBean.setDockCollection( (Vector) docks.clone());
    facilities.add(facilityOvBean);
    companyOvBean.setFacilityIdCollection( (Vector) facilities.clone());
    companies.add(companyOvBean);
    hubFacDockViewOvBean.setCompanyIdCollection( (Vector) companies.clone());
    result.add(hubFacDockViewOvBean);
    return result;
  }

}