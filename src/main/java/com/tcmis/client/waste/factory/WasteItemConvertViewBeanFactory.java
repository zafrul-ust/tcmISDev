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
import com.tcmis.client.waste.beans.WasteItemConvertViewBean;

/******************************************************************************
 * CLASSNAME: WasteItemConvertViewBeanFactory <br>
 * @version: 1.0, Dec 19, 2006 <br>
 *****************************************************************************/

public class WasteItemConvertViewBeanFactory extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_FROM_WASTE_ITEM_ID = "FROM_WASTE_ITEM_ID";
  public String ATTRIBUTE_FROM_FACILITY_ID = "FROM_FACILITY_ID";
  public String ATTRIBUTE_FROM_VENDOR_PROFILE_ID = "FROM_VENDOR_PROFILE_ID";
  public String ATTRIBUTE_FROM_VENDOR_ID = "FROM_VENDOR_ID";
  public String ATTRIBUTE_FROM_COMPANY_NAME = "FROM_COMPANY_NAME";
  public String ATTRIBUTE_FROM_WASTE_DESCRIPTION = "FROM_WASTE_DESCRIPTION";
  public String ATTRIBUTE_FROM_PACKAGING = "FROM_PACKAGING";
  public String ATTRIBUTE_FROM_WASTE_CATEGORY_ID = "FROM_WASTE_CATEGORY_ID";
  public String ATTRIBUTE_FROM_WASTE_TYPE_ID = "FROM_WASTE_TYPE_ID";
  public String ATTRIBUTE_FROM_LAB_PACK = "FROM_LAB_PACK";
  public String ATTRIBUTE_FROM_COMPANY_ID = "FROM_COMPANY_ID";
  public String ATTRIBUTE_TO_WASTE_ITEM_ID = "TO_WASTE_ITEM_ID";
  public String ATTRIBUTE_TO_FACILITY_ID = "TO_FACILITY_ID";
  public String ATTRIBUTE_TO_VENDOR_PROFILE_ID = "TO_VENDOR_PROFILE_ID";
  public String ATTRIBUTE_TO_VENDOR_ID = "TO_VENDOR_ID";
  public String ATTRIBUTE_TO_COMPANY_NAME = "TO_COMPANY_NAME";
  public String ATTRIBUTE_TO_DESCRIPTION = "TO_DESCRIPTION";
  public String ATTRIBUTE_TO_PACKAGING = "TO_PACKAGING";
  public String ATTRIBUTE_TO_WASTE_CATEGORY_ID = "TO_WASTE_CATEGORY_ID";
  public String ATTRIBUTE_TO_WASTE_TYPE_ID = "TO_WASTE_TYPE_ID";
  public String ATTRIBUTE_TO_LAB_PACK = "TO_LAB_PACK";
  public String ATTRIBUTE_TO_COMPANY_ID = "TO_COMPANY_ID";

  //table name
  public String TABLE = "WASTE_ITEM_CONVERT_VIEW";

  //constructor
  public WasteItemConvertViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("fromWasteItemId")) {
      return ATTRIBUTE_FROM_WASTE_ITEM_ID;
    } else if (attributeName.equals("fromFacilityId")) {
      return ATTRIBUTE_FROM_FACILITY_ID;
    } else if (attributeName.equals("fromVendorProfileId")) {
      return ATTRIBUTE_FROM_VENDOR_PROFILE_ID;
    } else if (attributeName.equals("fromVendorId")) {
      return ATTRIBUTE_FROM_VENDOR_ID;
    } else if (attributeName.equals("fromCompanyName")) {
      return ATTRIBUTE_FROM_COMPANY_NAME;
    } else if (attributeName.equals("fromWasteDescription")) {
      return ATTRIBUTE_FROM_WASTE_DESCRIPTION;
    } else if (attributeName.equals("fromPackaging")) {
      return ATTRIBUTE_FROM_PACKAGING;
    } else if (attributeName.equals("fromWasteCategoryId")) {
      return ATTRIBUTE_FROM_WASTE_CATEGORY_ID;
    } else if (attributeName.equals("fromWasteTypeId")) {
      return ATTRIBUTE_FROM_WASTE_TYPE_ID;
    } else if (attributeName.equals("fromLabPack")) {
      return ATTRIBUTE_FROM_LAB_PACK;
    } else if (attributeName.equals("fromCompanyId")) {
      return ATTRIBUTE_FROM_COMPANY_ID;
    } else if (attributeName.equals("toWasteItemId")) {
      return ATTRIBUTE_TO_WASTE_ITEM_ID;
    } else if (attributeName.equals("toFacilityId")) {
      return ATTRIBUTE_TO_FACILITY_ID;
    } else if (attributeName.equals("toVendorProfileId")) {
      return ATTRIBUTE_TO_VENDOR_PROFILE_ID;
    } else if (attributeName.equals("toVendorId")) {
      return ATTRIBUTE_TO_VENDOR_ID;
    } else if (attributeName.equals("toCompanyName")) {
      return ATTRIBUTE_TO_COMPANY_NAME;
    } else if (attributeName.equals("toDescription")) {
      return ATTRIBUTE_TO_DESCRIPTION;
    } else if (attributeName.equals("toPackaging")) {
      return ATTRIBUTE_TO_PACKAGING;
    } else if (attributeName.equals("toWasteCategoryId")) {
      return ATTRIBUTE_TO_WASTE_CATEGORY_ID;
    } else if (attributeName.equals("toWasteTypeId")) {
      return ATTRIBUTE_TO_WASTE_TYPE_ID;
    } else if (attributeName.equals("toLabPack")) {
      return ATTRIBUTE_TO_LAB_PACK;
    } else if (attributeName.equals("toCompanyId")) {
      return ATTRIBUTE_TO_COMPANY_ID;
    } else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, WasteItemConvertViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(WasteItemConvertViewBean wasteItemConvertViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("fromWasteItemId", "SearchCriterion.EQUALS",
     "" + wasteItemConvertViewBean.getFromWasteItemId());
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
   public int delete(WasteItemConvertViewBean wasteItemConvertViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("fromWasteItemId", "SearchCriterion.EQUALS",
     "" + wasteItemConvertViewBean.getFromWasteItemId());
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
   public int insert(WasteItemConvertViewBean wasteItemConvertViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(wasteItemConvertViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(WasteItemConvertViewBean wasteItemConvertViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_FROM_WASTE_ITEM_ID + "," +
     ATTRIBUTE_FROM_FACILITY_ID + "," +
     ATTRIBUTE_FROM_VENDOR_PROFILE_ID + "," +
     ATTRIBUTE_FROM_VENDOR_ID + "," +
     ATTRIBUTE_FROM_COMPANY_NAME + "," +
     ATTRIBUTE_FROM_WASTE_DESCRIPTION + "," +
     ATTRIBUTE_FROM_PACKAGING + "," +
     ATTRIBUTE_FROM_WASTE_CATEGORY_ID + "," +
     ATTRIBUTE_FROM_WASTE_TYPE_ID + "," +
     ATTRIBUTE_FROM_LAB_PACK + "," +
     ATTRIBUTE_FROM_COMPANY_ID + "," +
     ATTRIBUTE_TO_WASTE_ITEM_ID + "," +
     ATTRIBUTE_TO_FACILITY_ID + "," +
     ATTRIBUTE_TO_VENDOR_PROFILE_ID + "," +
     ATTRIBUTE_TO_VENDOR_ID + "," +
     ATTRIBUTE_TO_COMPANY_NAME + "," +
     ATTRIBUTE_TO_DESCRIPTION + "," +
     ATTRIBUTE_TO_PACKAGING + "," +
     ATTRIBUTE_TO_WASTE_CATEGORY_ID + "," +
     ATTRIBUTE_TO_WASTE_TYPE_ID + "," +
     ATTRIBUTE_TO_LAB_PACK + "," +
     ATTRIBUTE_TO_COMPANY_ID + ")" +
     " values (" +
     wasteItemConvertViewBean.getFromWasteItemId() + "," +
     SqlHandler.delimitString(wasteItemConvertViewBean.getFromFacilityId()) + "," +
     SqlHandler.delimitString(wasteItemConvertViewBean.getFromVendorProfileId()) + "," +
     SqlHandler.delimitString(wasteItemConvertViewBean.getFromVendorId()) + "," +
     SqlHandler.delimitString(wasteItemConvertViewBean.getFromCompanyName()) + "," +
     SqlHandler.delimitString(wasteItemConvertViewBean.getFromWasteDescription()) + "," +
     SqlHandler.delimitString(wasteItemConvertViewBean.getFromPackaging()) + "," +
     SqlHandler.delimitString(wasteItemConvertViewBean.getFromWasteCategoryId()) + "," +
     SqlHandler.delimitString(wasteItemConvertViewBean.getFromWasteTypeId()) + "," +
     SqlHandler.delimitString(wasteItemConvertViewBean.getFromLabPack()) + "," +
     SqlHandler.delimitString(wasteItemConvertViewBean.getFromCompanyId()) + "," +
     wasteItemConvertViewBean.getToWasteItemId() + "," +
     SqlHandler.delimitString(wasteItemConvertViewBean.getToFacilityId()) + "," +
     SqlHandler.delimitString(wasteItemConvertViewBean.getToVendorProfileId()) + "," +
     SqlHandler.delimitString(wasteItemConvertViewBean.getToVendorId()) + "," +
     SqlHandler.delimitString(wasteItemConvertViewBean.getToCompanyName()) + "," +
     SqlHandler.delimitString(wasteItemConvertViewBean.getToDescription()) + "," +
     SqlHandler.delimitString(wasteItemConvertViewBean.getToPackaging()) + "," +
     SqlHandler.delimitString(wasteItemConvertViewBean.getToWasteCategoryId()) + "," +
     SqlHandler.delimitString(wasteItemConvertViewBean.getToWasteTypeId()) + "," +
     SqlHandler.delimitString(wasteItemConvertViewBean.getToLabPack()) + "," +
     SqlHandler.delimitString(wasteItemConvertViewBean.getToCompanyId()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(WasteItemConvertViewBean wasteItemConvertViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(wasteItemConvertViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(WasteItemConvertViewBean wasteItemConvertViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_FROM_WASTE_ITEM_ID + "=" +
      StringHandler.nullIfZero(wasteItemConvertViewBean.getFromWasteItemId()) + "," +
     ATTRIBUTE_FROM_FACILITY_ID + "=" +
      SqlHandler.delimitString(wasteItemConvertViewBean.getFromFacilityId()) + "," +
     ATTRIBUTE_FROM_VENDOR_PROFILE_ID + "=" +
      SqlHandler.delimitString(wasteItemConvertViewBean.getFromVendorProfileId()) + "," +
     ATTRIBUTE_FROM_VENDOR_ID + "=" +
      SqlHandler.delimitString(wasteItemConvertViewBean.getFromVendorId()) + "," +
     ATTRIBUTE_FROM_COMPANY_NAME + "=" +
      SqlHandler.delimitString(wasteItemConvertViewBean.getFromCompanyName()) + "," +
     ATTRIBUTE_FROM_WASTE_DESCRIPTION + "=" +
      SqlHandler.delimitString(wasteItemConvertViewBean.getFromWasteDescription()) + "," +
     ATTRIBUTE_FROM_PACKAGING + "=" +
      SqlHandler.delimitString(wasteItemConvertViewBean.getFromPackaging()) + "," +
     ATTRIBUTE_FROM_WASTE_CATEGORY_ID + "=" +
      SqlHandler.delimitString(wasteItemConvertViewBean.getFromWasteCategoryId()) + "," +
     ATTRIBUTE_FROM_WASTE_TYPE_ID + "=" +
      SqlHandler.delimitString(wasteItemConvertViewBean.getFromWasteTypeId()) + "," +
     ATTRIBUTE_FROM_LAB_PACK + "=" +
      SqlHandler.delimitString(wasteItemConvertViewBean.getFromLabPack()) + "," +
     ATTRIBUTE_FROM_COMPANY_ID + "=" +
      SqlHandler.delimitString(wasteItemConvertViewBean.getFromCompanyId()) + "," +
     ATTRIBUTE_TO_WASTE_ITEM_ID + "=" +
      StringHandler.nullIfZero(wasteItemConvertViewBean.getToWasteItemId()) + "," +
     ATTRIBUTE_TO_FACILITY_ID + "=" +
      SqlHandler.delimitString(wasteItemConvertViewBean.getToFacilityId()) + "," +
     ATTRIBUTE_TO_VENDOR_PROFILE_ID + "=" +
      SqlHandler.delimitString(wasteItemConvertViewBean.getToVendorProfileId()) + "," +
     ATTRIBUTE_TO_VENDOR_ID + "=" +
      SqlHandler.delimitString(wasteItemConvertViewBean.getToVendorId()) + "," +
     ATTRIBUTE_TO_COMPANY_NAME + "=" +
      SqlHandler.delimitString(wasteItemConvertViewBean.getToCompanyName()) + "," +
     ATTRIBUTE_TO_DESCRIPTION + "=" +
      SqlHandler.delimitString(wasteItemConvertViewBean.getToDescription()) + "," +
     ATTRIBUTE_TO_PACKAGING + "=" +
      SqlHandler.delimitString(wasteItemConvertViewBean.getToPackaging()) + "," +
     ATTRIBUTE_TO_WASTE_CATEGORY_ID + "=" +
      SqlHandler.delimitString(wasteItemConvertViewBean.getToWasteCategoryId()) + "," +
     ATTRIBUTE_TO_WASTE_TYPE_ID + "=" +
      SqlHandler.delimitString(wasteItemConvertViewBean.getToWasteTypeId()) + "," +
     ATTRIBUTE_TO_LAB_PACK + "=" +
      SqlHandler.delimitString(wasteItemConvertViewBean.getToLabPack()) + "," +
     ATTRIBUTE_TO_COMPANY_ID + "=" +
      SqlHandler.delimitString(wasteItemConvertViewBean.getToCompanyId()) + " " +
     "where " + ATTRIBUTE_FROM_WASTE_ITEM_ID + "=" +
      wasteItemConvertViewBean.getFromWasteItemId();
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

    Collection wasteItemConvertViewBeanColl = new Vector();

    String query = "select * from " + TABLE + " " + getWhereClause(criteria);
    log.debug(query);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      WasteItemConvertViewBean wasteItemConvertViewBean = new WasteItemConvertViewBean();
      load(dataSetRow, wasteItemConvertViewBean);
      wasteItemConvertViewBeanColl.add(wasteItemConvertViewBean);
    }

    return wasteItemConvertViewBeanColl;
  }
}