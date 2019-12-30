package com.tcmis.client.waste.factory;

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
import com.tcmis.client.waste.beans.WasteTravelerViewBean;

/******************************************************************************
 * CLASSNAME: WasteTravelerViewBeanFactory <br>
 * @version: 1.0, Feb 12, 2007 <br>
 *****************************************************************************/

public class WasteTravelerViewBeanFactory extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_GENERATION_POINT = "GENERATION_POINT";
  public String ATTRIBUTE_VENDOR_ID = "VENDOR_ID";
  public String ATTRIBUTE_COMPANY_NAME = "COMPANY_NAME";
  public String ATTRIBUTE_VENDOR_PROFILE_ID = "VENDOR_PROFILE_ID";
  public String ATTRIBUTE_MANAGEMENT_OPTION_DESC = "MANAGEMENT_OPTION_DESC";
  public String ATTRIBUTE_DESCRIPTION = "DESCRIPTION";
  public String ATTRIBUTE_WASTE_CATEGORY_ID = "WASTE_CATEGORY_ID";
  public String ATTRIBUTE_TRAVELER_ID = "TRAVELER_ID";
  public String ATTRIBUTE_SEAL_DATE = "SEAL_DATE";
  public String ATTRIBUTE_CONTAINER_ID = "CONTAINER_ID";
  public String ATTRIBUTE_PROPER_SHIPPING_NAME = "PROPER_SHIPPING_NAME";
  public String ATTRIBUTE_HAZARD_CLASS = "HAZARD_CLASS";
  public String ATTRIBUTE_SHIPPING_ID = "SHIPPING_ID";
  public String ATTRIBUTE_PACKING_GROUP = "PACKING_GROUP";
  public String ATTRIBUTE_FROM_LOCATION = "FROM_LOCATION";
  public String ATTRIBUTE_TO_LOCATION = "TO_LOCATION";
  public String ATTRIBUTE_DOT_DESCRIPTION = "DOT_DESCRIPTION";
  public String ATTRIBUTE_STATE_WASTE_CODES = "STATE_WASTE_CODES";
  public String ATTRIBUTE_RCRA_CLASSIFICATION = "RCRA_CLASSIFICATION";
  public String ATTRIBUTE_LPAD_CONTAINER_ID = "LPAD_CONTAINER_ID";
  public String ATTRIBUTE_WASTE_REQUEST_ID_LINE_ITEM = "WASTE_REQUEST_ID_LINE_ITEM";

  //table name
  public String TABLE = "WASTE_TRAVELER_VIEW";

  //constructor
  public WasteTravelerViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    } else if (attributeName.equals("generationPoint")) {
      return ATTRIBUTE_GENERATION_POINT;
    } else if (attributeName.equals("vendorId")) {
      return ATTRIBUTE_VENDOR_ID;
    } else if (attributeName.equals("companyName")) {
      return ATTRIBUTE_COMPANY_NAME;
    } else if (attributeName.equals("vendorProfileId")) {
      return ATTRIBUTE_VENDOR_PROFILE_ID;
    } else if (attributeName.equals("managementOptionDesc")) {
      return ATTRIBUTE_MANAGEMENT_OPTION_DESC;
    } else if (attributeName.equals("description")) {
      return ATTRIBUTE_DESCRIPTION;
    } else if (attributeName.equals("wasteCategoryId")) {
      return ATTRIBUTE_WASTE_CATEGORY_ID;
    } else if (attributeName.equals("travelerId")) {
      return ATTRIBUTE_TRAVELER_ID;
    } else if (attributeName.equals("sealDate")) {
      return ATTRIBUTE_SEAL_DATE;
    } else if (attributeName.equals("containerId")) {
      return ATTRIBUTE_CONTAINER_ID;
    } else if (attributeName.equals("properShippingName")) {
      return ATTRIBUTE_PROPER_SHIPPING_NAME;
    } else if (attributeName.equals("hazardClass")) {
      return ATTRIBUTE_HAZARD_CLASS;
    } else if (attributeName.equals("shippingId")) {
      return ATTRIBUTE_SHIPPING_ID;
    } else if (attributeName.equals("packingGroup")) {
      return ATTRIBUTE_PACKING_GROUP;
    } else if (attributeName.equals("fromLocation")) {
      return ATTRIBUTE_FROM_LOCATION;
    } else if (attributeName.equals("toLocation")) {
      return ATTRIBUTE_TO_LOCATION;
    } else if (attributeName.equals("dotDescription")) {
      return ATTRIBUTE_DOT_DESCRIPTION;
    } else if (attributeName.equals("stateWasteCodes")) {
      return ATTRIBUTE_STATE_WASTE_CODES;
    } else if (attributeName.equals("rcraClassification")) {
      return ATTRIBUTE_RCRA_CLASSIFICATION;
    } else if (attributeName.equals("lpadContainerId")) {
      return ATTRIBUTE_LPAD_CONTAINER_ID;
    } else if (attributeName.equals("wasteRequestIdLineItem")) {
      return ATTRIBUTE_WASTE_REQUEST_ID_LINE_ITEM;
    } else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, WasteTravelerViewBean.class);
  }

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

    Collection wasteTravelerViewBeanColl = new Vector();

    String query = "select * from " + TABLE + " " + getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      WasteTravelerViewBean wasteTravelerViewBean = new WasteTravelerViewBean();
      load(dataSetRow, wasteTravelerViewBean);
      wasteTravelerViewBeanColl.add(wasteTravelerViewBean);
    }

    return wasteTravelerViewBeanColl;
  }
}