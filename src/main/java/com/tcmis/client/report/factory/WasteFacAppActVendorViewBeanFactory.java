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
import com.tcmis.client.report.beans.WasteFacAppActVendorViewBean;

/******************************************************************************
 * CLASSNAME: WasteFacAppActVendorViewBeanFactory <br>
 * @version: 1.0, Mar 8, 2006 <br>
 *****************************************************************************/

public class WasteFacAppActVendorViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_WASTE_LOCATION_ID = "WASTE_LOCATION_ID";
  public String ATTRIBUTE_APPLICATION = "APPLICATION";
  public String ATTRIBUTE_APPLICATION_DESC = "APPLICATION_DESC";
  public String ATTRIBUTE_VENDOR_ID = "VENDOR_ID";
  public String ATTRIBUTE_COMPANY_NAME = "COMPANY_NAME";
  public String ATTRIBUTE_FACILITY_NAME = "FACILITY_NAME";
  public String ATTRIBUTE_WASTE_LOCATION_DESC = "WASTE_LOCATION_DESC";

  //table name
  public String TABLE = "WASTE_FAC_APP_ACT_VENDOR_VIEW";

  //constructor
  public WasteFacAppActVendorViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("wasteLocationId")) {
      return ATTRIBUTE_WASTE_LOCATION_ID;
    }
    else if (attributeName.equals("application")) {
      return ATTRIBUTE_APPLICATION;
    }
    else if (attributeName.equals("applicationDesc")) {
      return ATTRIBUTE_APPLICATION_DESC;
    }
    else if (attributeName.equals("vendorId")) {
      return ATTRIBUTE_VENDOR_ID;
    }
    else if (attributeName.equals("companyName")) {
      return ATTRIBUTE_COMPANY_NAME;
    }
    else if (attributeName.equals("facilityName")) {
      return ATTRIBUTE_FACILITY_NAME;
    }
    else if (attributeName.equals("wasteLocationDesc")) {
      return ATTRIBUTE_WASTE_LOCATION_DESC;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, WasteFacAppActVendorViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(WasteFacAppActVendorViewBean wasteFacAppActVendorViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("facilityId", "SearchCriterion.EQUALS",
     "" + wasteFacAppActVendorViewBean.getFacilityId());
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
   public int delete(WasteFacAppActVendorViewBean wasteFacAppActVendorViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("facilityId", "SearchCriterion.EQUALS",
     "" + wasteFacAppActVendorViewBean.getFacilityId());
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
   public int insert(WasteFacAppActVendorViewBean wasteFacAppActVendorViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(wasteFacAppActVendorViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(WasteFacAppActVendorViewBean wasteFacAppActVendorViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_WASTE_LOCATION_ID + "," +
     ATTRIBUTE_APPLICATION + "," +
     ATTRIBUTE_APPLICATION_DESC + "," +
     ATTRIBUTE_VENDOR_ID + "," +
     ATTRIBUTE_COMPANY_NAME + "," +
     ATTRIBUTE_FACILITY_NAME + "," +
     ATTRIBUTE_WASTE_LOCATION_DESC + ")" +
     " values (" +
     SqlHandler.delimitString(wasteFacAppActVendorViewBean.getFacilityId()) + "," +
     SqlHandler.delimitString(wasteFacAppActVendorViewBean.getWasteLocationId()) + "," +
     SqlHandler.delimitString(wasteFacAppActVendorViewBean.getApplication()) + "," +
     SqlHandler.delimitString(wasteFacAppActVendorViewBean.getApplicationDesc()) + "," +
     SqlHandler.delimitString(wasteFacAppActVendorViewBean.getVendorId()) + "," +
     SqlHandler.delimitString(wasteFacAppActVendorViewBean.getCompanyName()) + "," +
     SqlHandler.delimitString(wasteFacAppActVendorViewBean.getFacilityName()) + "," +
     SqlHandler.delimitString(wasteFacAppActVendorViewBean.getWasteLocationDesc()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(WasteFacAppActVendorViewBean wasteFacAppActVendorViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(wasteFacAppActVendorViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(WasteFacAppActVendorViewBean wasteFacAppActVendorViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(wasteFacAppActVendorViewBean.getFacilityId()) + "," +
     ATTRIBUTE_WASTE_LOCATION_ID + "=" +
      SqlHandler.delimitString(wasteFacAppActVendorViewBean.getWasteLocationId()) + "," +
     ATTRIBUTE_APPLICATION + "=" +
      SqlHandler.delimitString(wasteFacAppActVendorViewBean.getApplication()) + "," +
     ATTRIBUTE_APPLICATION_DESC + "=" +
      SqlHandler.delimitString(wasteFacAppActVendorViewBean.getApplicationDesc()) + "," +
     ATTRIBUTE_VENDOR_ID + "=" +
      SqlHandler.delimitString(wasteFacAppActVendorViewBean.getVendorId()) + "," +
     ATTRIBUTE_COMPANY_NAME + "=" +
      SqlHandler.delimitString(wasteFacAppActVendorViewBean.getCompanyName()) + "," +
     ATTRIBUTE_FACILITY_NAME + "=" +
      SqlHandler.delimitString(wasteFacAppActVendorViewBean.getFacilityName()) + "," +
     ATTRIBUTE_WASTE_LOCATION_DESC + "=" +
       SqlHandler.delimitString(wasteFacAppActVendorViewBean.getWasteLocationDesc()) + " " +
     "where " + ATTRIBUTE_FACILITY_ID + "=" +
      wasteFacAppActVendorViewBean.getFacilityId();
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
    Collection wasteFacAppActVendorViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + " order by facility_id, application, waste_location_id, vendor_id";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      WasteFacAppActVendorViewBean wasteFacAppActVendorViewBean = new
          WasteFacAppActVendorViewBean();
      load(dataSetRow, wasteFacAppActVendorViewBean);
      wasteFacAppActVendorViewBeanColl.add(wasteFacAppActVendorViewBean);
    }
    return wasteFacAppActVendorViewBeanColl;
  }
}