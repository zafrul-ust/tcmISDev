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
import com.tcmis.client.catalog.beans.CatalogSpecViewBean;

/******************************************************************************
 * CLASSNAME: CatalogSpecViewBeanFactory <br>
 * @version: 1.0, Dec 1, 2006 <br>
 *****************************************************************************/

public class CatalogSpecViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_SPECIFICATION = "SPECIFICATION";
  public String ATTRIBUTE_CONTENT = "CONTENT";
  public String ATTRIBUTE_ON_LINE = "ON_LINE";
  public String ATTRIBUTE_COC = "COC";
  public String ATTRIBUTE_COA = "COA";
  public String ATTRIBUTE_ITAR = "ITAR";
  public String ATTRIBUTE_SPEC_ID = "SPEC_ID";

  //table name
  public String TABLE = "CATALOG_SPEC_VIEW";

  //constructor
  public CatalogSpecViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("catalogId")) {
      return ATTRIBUTE_CATALOG_ID;
    }
    else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("specification")) {
      return ATTRIBUTE_SPECIFICATION;
    }
    else if (attributeName.equals("content")) {
      return ATTRIBUTE_CONTENT;
    }
    else if (attributeName.equals("onLine")) {
      return ATTRIBUTE_ON_LINE;
    }
    else if (attributeName.equals("coc")) {
      return ATTRIBUTE_COC;
    }
    else if (attributeName.equals("coa")) {
        return ATTRIBUTE_COA;
      }
    else if (attributeName.equals("itar")) {
        return ATTRIBUTE_ITAR;
      }
    else if (attributeName.equals("specId")) {
      return ATTRIBUTE_SPEC_ID;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, CatalogSpecViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(CatalogSpecViewBean catalogSpecViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("catalogId", "SearchCriterion.EQUALS",
     "" + catalogSpecViewBean.getCatalogId());
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
   public int delete(CatalogSpecViewBean catalogSpecViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("catalogId", "SearchCriterion.EQUALS",
     "" + catalogSpecViewBean.getCatalogId());
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
   public int insert(CatalogSpecViewBean catalogSpecViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(catalogSpecViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(CatalogSpecViewBean catalogSpecViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_CATALOG_ID + "," +
     ATTRIBUTE_CAT_PART_NO + "," +
     ATTRIBUTE_SPECIFICATION + "," +
     ATTRIBUTE_CONTENT + "," +
     ATTRIBUTE_ON_LINE + "," +
     ATTRIBUTE_COC + "," +
     ATTRIBUTE_COA + ")" +
     " values (" +
     SqlHandler.delimitString(catalogSpecViewBean.getCatalogId()) + "," +
     SqlHandler.delimitString(catalogSpecViewBean.getCatPartNo()) + "," +
     SqlHandler.delimitString(catalogSpecViewBean.getSpecification()) + "," +
     SqlHandler.delimitString(catalogSpecViewBean.getContent()) + "," +
     SqlHandler.delimitString(catalogSpecViewBean.getOnLine()) + "," +
     SqlHandler.delimitString(catalogSpecViewBean.getCoc()) + "," +
     SqlHandler.delimitString(catalogSpecViewBean.getCoa()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(CatalogSpecViewBean catalogSpecViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(catalogSpecViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(CatalogSpecViewBean catalogSpecViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_CATALOG_ID + "=" +
      SqlHandler.delimitString(catalogSpecViewBean.getCatalogId()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
      SqlHandler.delimitString(catalogSpecViewBean.getCatPartNo()) + "," +
     ATTRIBUTE_SPECIFICATION + "=" +
      SqlHandler.delimitString(catalogSpecViewBean.getSpecification()) + "," +
     ATTRIBUTE_CONTENT + "=" +
      SqlHandler.delimitString(catalogSpecViewBean.getContent()) + "," +
     ATTRIBUTE_ON_LINE + "=" +
      SqlHandler.delimitString(catalogSpecViewBean.getOnLine()) + "," +
     ATTRIBUTE_COC + "=" +
      SqlHandler.delimitString(catalogSpecViewBean.getCoc()) + "," +
     ATTRIBUTE_COA + "=" +
      SqlHandler.delimitString(catalogSpecViewBean.getCoa()) + " " +
     "where " + ATTRIBUTE_CATALOG_ID + "=" +
      catalogSpecViewBean.getCatalogId();
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
    Collection catalogSpecViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CatalogSpecViewBean catalogSpecViewBean = new CatalogSpecViewBean();
      load(dataSetRow, catalogSpecViewBean);
      catalogSpecViewBeanColl.add(catalogSpecViewBean);
    }
    return catalogSpecViewBeanColl;
  }

  public Collection selectCatalogSpecByPart(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectCatalogSpecByPart(criteria, sortCriteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectCatalogSpecByPart(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws
      BaseException {
    Collection catalogSpecViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);

    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CatalogSpecViewBean catalogSpecViewBean = new CatalogSpecViewBean();
      load(dataSetRow, catalogSpecViewBean);
      catalogSpecViewBeanColl.add(catalogSpecViewBean);
    }
    return catalogSpecViewBeanColl;
  }

  public Collection selectCatalogSpecBySpec(String catalogId, String spec, String criterion, SortCriteria sortCriteria) throws BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectCatalogSpecBySpec(catalogId, spec, criterion, sortCriteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectCatalogSpecBySpec(String catalogId, String spec, String criterion, SortCriteria sortCriteria, Connection conn) throws
      BaseException {
    Collection catalogSpecViewBeanColl = new Vector();
//select * from CATALOG_SPEC_VIEW where cat_part_no in (select cat_part_no from CATALOG_SPEC_VIEW where spec_id like '%000-597-123_C%')
    String specQueryText = "";
    if(!StringHandler.isBlankString(spec)) {
      specQueryText = " and SPECIFICATION ";
      if ("equals".equalsIgnoreCase(criterion)) {
        specQueryText = specQueryText + " = " + SqlHandler.delimitString(spec) + " ";
      }
      else if ("contains".equalsIgnoreCase(criterion)) {
        specQueryText = specQueryText + " LIKE '%" + SqlHandler.validQuery(spec) + "%' ";
      }
      else if ("startsWith".equalsIgnoreCase(criterion)) {
        specQueryText = specQueryText + " LIKE '" + SqlHandler.validQuery(spec) + "%' ";
      }
      else if ("endsWith".equalsIgnoreCase(criterion)) {
        specQueryText = specQueryText + " LIKE '%" + SqlHandler.validQuery(spec) + "' ";
      }
    }

    String query = "select " + ATTRIBUTE_CAT_PART_NO + " from " + TABLE + " " +
        "where CATALOG_ID=" + SqlHandler.delimitString(catalogId) + specQueryText;
    query = "select * from " + TABLE + " where CATALOG_ID = " + SqlHandler.delimitString(catalogId) + " and " +
            "cat_part_no in (" + query + ") " + getOrderByClause(sortCriteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CatalogSpecViewBean catalogSpecViewBean = new CatalogSpecViewBean();
      load(dataSetRow, catalogSpecViewBean);
      catalogSpecViewBeanColl.add(catalogSpecViewBean);
    }
    return catalogSpecViewBeanColl;
  }
}