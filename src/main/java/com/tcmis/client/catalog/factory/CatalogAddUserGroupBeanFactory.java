package com.tcmis.client.catalog.factory;

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
import com.tcmis.client.catalog.beans.CatalogAddUserGroupBean;

/******************************************************************************
 * CLASSNAME: CatalogAddUserGroupBeanFactory <br>
 * @version: 1.0, Oct 30, 2007 <br>
 *****************************************************************************/

public class CatalogAddUserGroupBeanFactory extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_REQUEST_ID = "REQUEST_ID";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_WORK_AREA = "WORK_AREA";
  public String ATTRIBUTE_USER_GROUP_ID = "USER_GROUP_ID";
  public String ATTRIBUTE_PROCESS_DESC = "PROCESS_DESC";
  public String ATTRIBUTE_QUANTITY_1 = "QUANTITY_1";
  public String ATTRIBUTE_PER_1 = "PER_1";
  public String ATTRIBUTE_PERIOD_1 = "PERIOD_1";
  public String ATTRIBUTE_QUANTITY_2 = "QUANTITY_2";
  public String ATTRIBUTE_PER_2 = "PER_2";
  public String ATTRIBUTE_PERIOD_2 = "PERIOD_2";
  public String ATTRIBUTE_CHARGE_NUMBER = "CHARGE_NUMBER";

  //table name
  public String TABLE = "CATALOG_ADD_USER_GROUP";

  //constructor
  public CatalogAddUserGroupBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    } else if (attributeName.equals("requestId")) {
      return ATTRIBUTE_REQUEST_ID;
    } else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    } else if (attributeName.equals("workArea")) {
      return ATTRIBUTE_WORK_AREA;
    } else if (attributeName.equals("userGroupId")) {
      return ATTRIBUTE_USER_GROUP_ID;
    } else if (attributeName.equals("processDesc")) {
      return ATTRIBUTE_PROCESS_DESC;
    } else if (attributeName.equals("quantity11")) {
      return ATTRIBUTE_QUANTITY_1;
    } else if (attributeName.equals("per11")) {
      return ATTRIBUTE_PER_1;
    } else if (attributeName.equals("period11")) {
      return ATTRIBUTE_PERIOD_1;
    } else if (attributeName.equals("quantity22")) {
      return ATTRIBUTE_QUANTITY_2;
    } else if (attributeName.equals("per22")) {
      return ATTRIBUTE_PER_2;
    } else if (attributeName.equals("period22")) {
      return ATTRIBUTE_PERIOD_2;
    } else if (attributeName.equals("chargeNumber")) {
      return ATTRIBUTE_CHARGE_NUMBER;
    } else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, CatalogAddUserGroupBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(CatalogAddUserGroupBean catalogAddUserGroupBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
     "" + catalogAddUserGroupBean.getCompanyId());
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
   public int delete(CatalogAddUserGroupBean catalogAddUserGroupBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
     "" + catalogAddUserGroupBean.getCompanyId());
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

  //insert
  public int insert(CatalogAddUserGroupBean catalogAddUserGroupBean) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(catalogAddUserGroupBean, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(CatalogAddUserGroupBean catalogAddUserGroupBean, Connection conn) throws BaseException {

    SqlManager sqlManager = new SqlManager();

    String query = "insert into " + TABLE + " (" + ATTRIBUTE_COMPANY_ID + "," + ATTRIBUTE_REQUEST_ID + "," + ATTRIBUTE_FACILITY_ID + "," + ATTRIBUTE_WORK_AREA + "," + ATTRIBUTE_USER_GROUP_ID + "," + ATTRIBUTE_PROCESS_DESC + "," +
        ATTRIBUTE_QUANTITY_1 + "," + ATTRIBUTE_PER_1 + "," + ATTRIBUTE_PERIOD_1 + "," + ATTRIBUTE_QUANTITY_2 + "," + ATTRIBUTE_PER_2 + "," + ATTRIBUTE_PERIOD_2 + "," + ATTRIBUTE_CHARGE_NUMBER + ")" + " values (" +
        SqlHandler.delimitString(catalogAddUserGroupBean.getCompanyId()) + "," + catalogAddUserGroupBean.getRequestId() + "," + SqlHandler.delimitString(catalogAddUserGroupBean.getFacilityId()) + "," +
        SqlHandler.delimitString(catalogAddUserGroupBean.getWorkArea()) + "," + SqlHandler.delimitString(catalogAddUserGroupBean.getUserGroupId()) + "," + SqlHandler.delimitString(catalogAddUserGroupBean.getProcessDesc()) + "," +
        catalogAddUserGroupBean.getQuantity1() + "," + catalogAddUserGroupBean.getPer1() + "," + SqlHandler.delimitString(catalogAddUserGroupBean.getPeriod1()) + "," + catalogAddUserGroupBean.getQuantity2() + "," + catalogAddUserGroupBean.getPer2() +
        "," + SqlHandler.delimitString(catalogAddUserGroupBean.getPeriod2()) + "," + SqlHandler.delimitString(catalogAddUserGroupBean.getChargeNumber()) + ")";

    return sqlManager.update(conn, query);
  }

  //you need to verify the primary key(s) before uncommenting this
  /*
//update
   public int update(CatalogAddUserGroupBean catalogAddUserGroupBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(catalogAddUserGroupBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(CatalogAddUserGroupBean catalogAddUserGroupBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(catalogAddUserGroupBean.getCompanyId()) + "," +
     ATTRIBUTE_REQUEST_ID + "=" +
      StringHandler.nullIfZero(catalogAddUserGroupBean.getRequestId()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(catalogAddUserGroupBean.getFacilityId()) + "," +
     ATTRIBUTE_WORK_AREA + "=" +
      SqlHandler.delimitString(catalogAddUserGroupBean.getWorkArea()) + "," +
     ATTRIBUTE_USER_GROUP_ID + "=" +
      SqlHandler.delimitString(catalogAddUserGroupBean.getUserGroupId()) + "," +
     ATTRIBUTE_PROCESS_DESC + "=" +
      SqlHandler.delimitString(catalogAddUserGroupBean.getProcessDesc()) + "," +
     ATTRIBUTE_QUANTITY_1 + "=" +
      StringHandler.nullIfZero(catalogAddUserGroupBean.getQuantity1()) + "," +
     ATTRIBUTE_PER_1 + "=" +
      StringHandler.nullIfZero(catalogAddUserGroupBean.getPer1()) + "," +
     ATTRIBUTE_PERIOD_1 + "=" +
      SqlHandler.delimitString(catalogAddUserGroupBean.getPeriod1()) + "," +
     ATTRIBUTE_QUANTITY_2 + "=" +
      StringHandler.nullIfZero(catalogAddUserGroupBean.getQuantity2()) + "," +
     ATTRIBUTE_PER_2 + "=" +
      StringHandler.nullIfZero(catalogAddUserGroupBean.getPer2()) + "," +
     ATTRIBUTE_PERIOD_2 + "=" +
      SqlHandler.delimitString(catalogAddUserGroupBean.getPeriod2()) + "," +
     ATTRIBUTE_CHARGE_NUMBER + "=" +
      SqlHandler.delimitString(catalogAddUserGroupBean.getChargeNumber()) + " " +
     "where " + ATTRIBUTE_COMPANY_ID + "=" +
      catalogAddUserGroupBean.getCompanyId();
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(criteria, sortCriteria, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

    Collection catalogAddUserGroupBeanColl = new Vector();

    String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CatalogAddUserGroupBean catalogAddUserGroupBean = new CatalogAddUserGroupBean();
      load(dataSetRow, catalogAddUserGroupBean);
      catalogAddUserGroupBeanColl.add(catalogAddUserGroupBean);
    }

    return catalogAddUserGroupBeanColl;
  }
}