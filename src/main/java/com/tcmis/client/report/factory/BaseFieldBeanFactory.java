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
import com.tcmis.client.report.beans.BaseFieldBean;

/******************************************************************************
 * CLASSNAME: BaseFieldBeanFactory <br>
 * @version: 1.0, Mar 1, 2006 <br>
 *****************************************************************************/

public class BaseFieldBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_REPORT_TYPE = "REPORT_TYPE";
  public String ATTRIBUTE_BASE_FIELD = "BASE_FIELD";
  public String ATTRIBUTE_DISPLAY_ORDER = "DISPLAY_ORDER";
  public String ATTRIBUTE_COLUMN_SPEC = "COLUMN_SPEC";
  public String ATTRIBUTE_AGGREGATE = "AGGREGATE";
  public String ATTRIBUTE_COMPATIBILITY = "COMPATIBILITY";
  public String ATTRIBUTE_FROM_CLAUSE = "FROM_CLAUSE";
  public String ATTRIBUTE_WHERE_CLAUSE = "WHERE_CLAUSE";
  public String ATTRIBUTE_DESCRIPTION = "DESCRIPTION";

  //table name
  public String TABLE = "BASE_FIELD";

  //constructor
  public BaseFieldBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("reportType")) {
      return ATTRIBUTE_REPORT_TYPE;
    }
    else if (attributeName.equals("baseField")) {
      return ATTRIBUTE_BASE_FIELD;
    }
    else if (attributeName.equals("displayOrder")) {
      return ATTRIBUTE_DISPLAY_ORDER;
    }
    else if (attributeName.equals("columnSpec")) {
      return ATTRIBUTE_COLUMN_SPEC;
    }
    else if (attributeName.equals("aggregate")) {
      return ATTRIBUTE_AGGREGATE;
    }
    else if (attributeName.equals("compatibility")) {
      return ATTRIBUTE_COMPATIBILITY;
    }
    else if (attributeName.equals("fromClause")) {
      return ATTRIBUTE_FROM_CLAUSE;
    }
    else if (attributeName.equals("whereClause")) {
      return ATTRIBUTE_WHERE_CLAUSE;
    }
    else if (attributeName.equals("description")) {
      return ATTRIBUTE_DESCRIPTION;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, BaseFieldBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(BaseFieldBean baseFieldBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
     "" + baseFieldBean.getCompanyId());
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
   public int delete(BaseFieldBean baseFieldBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
     "" + baseFieldBean.getCompanyId());
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
   public int insert(BaseFieldBean baseFieldBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(baseFieldBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(BaseFieldBean baseFieldBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_REPORT_TYPE + "," +
     ATTRIBUTE_BASE_FIELD + "," +
     ATTRIBUTE_DISPLAY_ORDER + "," +
     ATTRIBUTE_COLUMN_SPEC + "," +
     ATTRIBUTE_AGGREGATE + "," +
     ATTRIBUTE_COMPATIBILITY + "," +
     ATTRIBUTE_FROM_CLAUSE + "," +
     ATTRIBUTE_WHERE_CLAUSE + "," +
     ATTRIBUTE_DESCRIPTION + ")" +
     " values (" +
     SqlHandler.delimitString(baseFieldBean.getCompanyId()) + "," +
     SqlHandler.delimitString(baseFieldBean.getReportType()) + "," +
     SqlHandler.delimitString(baseFieldBean.getBaseField()) + "," +
     baseFieldBean.getDisplayOrder() + "," +
     SqlHandler.delimitString(baseFieldBean.getColumnSpec()) + "," +
     SqlHandler.delimitString(baseFieldBean.getAggregate()) + "," +
     SqlHandler.delimitString(baseFieldBean.getCompatibility()) + "," +
     SqlHandler.delimitString(baseFieldBean.getFromClause()) + "," +
     SqlHandler.delimitString(baseFieldBean.getWhereClause()) + "," +
     SqlHandler.delimitString(baseFieldBean.getDescription()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(BaseFieldBean baseFieldBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(baseFieldBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(BaseFieldBean baseFieldBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(baseFieldBean.getCompanyId()) + "," +
     ATTRIBUTE_REPORT_TYPE + "=" +
      SqlHandler.delimitString(baseFieldBean.getReportType()) + "," +
     ATTRIBUTE_BASE_FIELD + "=" +
      SqlHandler.delimitString(baseFieldBean.getBaseField()) + "," +
     ATTRIBUTE_DISPLAY_ORDER + "=" +
      StringHandler.nullIfZero(baseFieldBean.getDisplayOrder()) + "," +
     ATTRIBUTE_COLUMN_SPEC + "=" +
      SqlHandler.delimitString(baseFieldBean.getColumnSpec()) + "," +
     ATTRIBUTE_AGGREGATE + "=" +
      SqlHandler.delimitString(baseFieldBean.getAggregate()) + "," +
     ATTRIBUTE_COMPATIBILITY + "=" +
      SqlHandler.delimitString(baseFieldBean.getCompatibility()) + "," +
     ATTRIBUTE_FROM_CLAUSE + "=" +
      SqlHandler.delimitString(baseFieldBean.getFromClause()) + "," +
     ATTRIBUTE_WHERE_CLAUSE + "=" +
      SqlHandler.delimitString(baseFieldBean.getWhereClause()) + "," +
     ATTRIBUTE_DESCRIPTION + "=" +
      SqlHandler.delimitString(baseFieldBean.getDescription()) + " " +
     "where " + ATTRIBUTE_COMPANY_ID + "=" +
      baseFieldBean.getCompanyId();
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(criteria, connection,sortCriteria);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(SearchCriteria criteria, Connection conn, SortCriteria sortCriteria) throws BaseException {
    Collection baseFieldBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      BaseFieldBean baseFieldBean = new BaseFieldBean();
      load(dataSetRow, baseFieldBean);
      baseFieldBeanColl.add(baseFieldBean);
    }
    return baseFieldBeanColl;
  }
}