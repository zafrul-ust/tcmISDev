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
import com.tcmis.client.report.beans.FacAppReportViewBean;

/******************************************************************************
 * CLASSNAME: FacAppReportViewBeanFactory <br>
 * @version: 1.0, Mar 22, 2006 <br>
 *****************************************************************************/

public class FacAppReportViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_APPLICATION = "APPLICATION";
  public String ATTRIBUTE_APPLICATION_DESC = "APPLICATION_DESC";
  public String ATTRIBUTE_FACILITY_NAME = "FACILITY_NAME";
  public String ATTRIBUTE_STATUS = "STATUS";
  public String ATTRIBUTE_REPORTING_ENTITY_LABEL = "REPORTING_ENTITY_LABEL";
  public String ATTRIBUTE_DELIVERY_POINT = "DELIVERY_POINT";
  public String ATTRIBUTE_DELIVERY_POINT_DESC = "DELIVERY_POINT_DESC";
  public String ATTRIBUTE_DOCK_LOCATION_ID = "DOCK_LOCATION_ID";
  public String ATTRIBUTE_DOCK_DESC = "DOCK_DESC";
  public String ATTRIBUTE_REPORTING_ENTITY_ID = "REPORTING_ENTITY_ID";
  public String ATTRIBUTE_REPORTING_ENTITY_DESCRIPTION = "REPORTING_ENTITY_DESCRIPTION";

  //table name
  public String TABLE = "FAC_APP_REPORT_VIEW";

  //constructor
  public FacAppReportViewBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("reportingEntityLabel")) {
      return ATTRIBUTE_REPORTING_ENTITY_LABEL;
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
    else if (attributeName.equals("reportingEntityId")) {
      return ATTRIBUTE_REPORTING_ENTITY_ID;
    }
    else if (attributeName.equals("reportingEntityDescription")) {
      return ATTRIBUTE_REPORTING_ENTITY_DESCRIPTION;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, FacAppReportViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(FacAppReportViewBean facAppReportViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
     "" + facAppReportViewBean.getCompanyId());
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
   public int delete(FacAppReportViewBean facAppReportViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
     "" + facAppReportViewBean.getCompanyId());
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
   public int insert(FacAppReportViewBean facAppReportViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(facAppReportViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(FacAppReportViewBean facAppReportViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_PERSONNEL_ID + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_APPLICATION + "," +
     ATTRIBUTE_APPLICATION_DESC + "," +
     ATTRIBUTE_FACILITY_NAME + "," +
     ATTRIBUTE_STATUS + "," +
     ATTRIBUTE_REPORTING_ENTITY_LABEL + "," +
     ATTRIBUTE_DELIVERY_POINT + "," +
     ATTRIBUTE_DELIVERY_POINT_DESC + "," +
     ATTRIBUTE_DOCK_LOCATION_ID + "," +
     ATTRIBUTE_DOCK_DESC + "," +
     ATTRIBUTE_REPORTING_ENTITY_ID + "," +
     ATTRIBUTE_REPORTING_ENTITY_DESCRIPTION + ")" +
     " values (" +
     SqlHandler.delimitString(facAppReportViewBean.getCompanyId()) + "," +
     facAppReportViewBean.getPersonnelId() + "," +
     SqlHandler.delimitString(facAppReportViewBean.getFacilityId()) + "," +
     SqlHandler.delimitString(facAppReportViewBean.getApplication()) + "," +
     SqlHandler.delimitString(facAppReportViewBean.getApplicationDesc()) + "," +
     SqlHandler.delimitString(facAppReportViewBean.getFacilityName()) + "," +
     SqlHandler.delimitString(facAppReportViewBean.getStatus()) + "," +
     SqlHandler.delimitString(facAppReportViewBean.getReportingEntityLabel()) + "," +
     SqlHandler.delimitString(facAppReportViewBean.getDeliveryPoint()) + "," +
     SqlHandler.delimitString(facAppReportViewBean.getDeliveryPointDesc()) + "," +
     SqlHandler.delimitString(facAppReportViewBean.getDockLocationId()) + "," +
     SqlHandler.delimitString(facAppReportViewBean.getDockDesc()) + "," +
     SqlHandler.delimitString(facAppReportViewBean.getReportingEntityId()) + "," +
       SqlHandler.delimitString(facAppReportViewBean.getReportingEntityDescription()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(FacAppReportViewBean facAppReportViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(facAppReportViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(FacAppReportViewBean facAppReportViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(facAppReportViewBean.getCompanyId()) + "," +
     ATTRIBUTE_PERSONNEL_ID + "=" +
      StringHandler.nullIfZero(facAppReportViewBean.getPersonnelId()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(facAppReportViewBean.getFacilityId()) + "," +
     ATTRIBUTE_APPLICATION + "=" +
      SqlHandler.delimitString(facAppReportViewBean.getApplication()) + "," +
     ATTRIBUTE_APPLICATION_DESC + "=" +
      SqlHandler.delimitString(facAppReportViewBean.getApplicationDesc()) + "," +
     ATTRIBUTE_FACILITY_NAME + "=" +
      SqlHandler.delimitString(facAppReportViewBean.getFacilityName()) + "," +
     ATTRIBUTE_STATUS + "=" +
      SqlHandler.delimitString(facAppReportViewBean.getStatus()) + "," +
     ATTRIBUTE_REPORTING_ENTITY_LABEL + "=" +
      SqlHandler.delimitString(facAppReportViewBean.getReportingEntityLabel()) + "," +
     ATTRIBUTE_DELIVERY_POINT + "=" +
      SqlHandler.delimitString(facAppReportViewBean.getDeliveryPoint()) + "," +
     ATTRIBUTE_DELIVERY_POINT_DESC + "=" +
      SqlHandler.delimitString(facAppReportViewBean.getDeliveryPointDesc()) + "," +
     ATTRIBUTE_DOCK_LOCATION_ID + "=" +
      SqlHandler.delimitString(facAppReportViewBean.getDockLocationId()) + "," +
     ATTRIBUTE_DOCK_DESC + "=" +
      SqlHandler.delimitString(facAppReportViewBean.getDockDesc()) + "," +
     ATTRIBUTE_REPORTING_ENTITY_ID + "=" +
      SqlHandler.delimitString(facAppReportViewBean.getReportingEntityId()) + "," +
     ATTRIBUTE_REPORTING_ENTITY_DESCRIPTION + "=" +
       SqlHandler.delimitString(facAppReportViewBean.getReportingEntityDescription()) + " " +
     "where " + ATTRIBUTE_COMPANY_ID + "=" +
      facAppReportViewBean.getCompanyId();
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
    Collection facAppReportViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) +
		  " order by " + ATTRIBUTE_FACILITY_NAME + "," +
                       ATTRIBUTE_REPORTING_ENTITY_DESCRIPTION + "," +
                       ATTRIBUTE_APPLICATION_DESC;
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      FacAppReportViewBean facAppReportViewBean = new FacAppReportViewBean();
      load(dataSetRow, facAppReportViewBean);
      facAppReportViewBeanColl.add(facAppReportViewBean);
    }
    return facAppReportViewBeanColl;
  }


}