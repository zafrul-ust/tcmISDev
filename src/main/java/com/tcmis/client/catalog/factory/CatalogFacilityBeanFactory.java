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
import com.tcmis.client.catalog.beans.CatalogFacilityBean;

/******************************************************************************
 * CLASSNAME: CatalogFacilityBeanFactory <br>
 * @version: 1.0, Oct 23, 2006 <br>
 *****************************************************************************/

public class CatalogFacilityBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_DISPLAY = "DISPLAY";
  public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";

  //table name
  public String TABLE = "CATALOG_FACILITY";

  //constructor
  public CatalogFacilityBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("catalogId")) {
      return ATTRIBUTE_CATALOG_ID;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("display")) {
      return ATTRIBUTE_DISPLAY;
    }
    else if (attributeName.equals("catalogCompanyId")) {
      return ATTRIBUTE_CATALOG_COMPANY_ID;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, CatalogFacilityBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(CatalogFacilityBean catalogFacilityBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("catalogId", "SearchCriterion.EQUALS",
     "" + catalogFacilityBean.getCatalogId());
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
   public int delete(CatalogFacilityBean catalogFacilityBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("catalogId", "SearchCriterion.EQUALS",
     "" + catalogFacilityBean.getCatalogId());
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
   public int insert(CatalogFacilityBean catalogFacilityBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(catalogFacilityBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(CatalogFacilityBean catalogFacilityBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_CATALOG_ID + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_DISPLAY + ")" +
     " values (" +
     SqlHandler.delimitString(catalogFacilityBean.getCatalogId()) + "," +
     SqlHandler.delimitString(catalogFacilityBean.getFacilityId()) + "," +
     SqlHandler.delimitString(catalogFacilityBean.getCompanyId()) + "," +
     SqlHandler.delimitString(catalogFacilityBean.getDisplay()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(CatalogFacilityBean catalogFacilityBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(catalogFacilityBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(CatalogFacilityBean catalogFacilityBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_CATALOG_ID + "=" +
      SqlHandler.delimitString(catalogFacilityBean.getCatalogId()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(catalogFacilityBean.getFacilityId()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(catalogFacilityBean.getCompanyId()) + "," +
     ATTRIBUTE_DISPLAY + "=" +
      SqlHandler.delimitString(catalogFacilityBean.getDisplay()) + " " +
     "where " + ATTRIBUTE_CATALOG_ID + "=" +
      catalogFacilityBean.getCatalogId();
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
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws
      BaseException {
    Collection catalogFacilityBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CatalogFacilityBean catalogFacilityBean = new CatalogFacilityBean();
      load(dataSetRow, catalogFacilityBean);
      catalogFacilityBeanColl.add(catalogFacilityBean);
    }
    return catalogFacilityBeanColl;
  }
}