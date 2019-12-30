package com.tcmis.internal.hub.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

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
import com.tcmis.internal.hub.beans.RecipeUserGroupOvBean;

/******************************************************************************
 * CLASSNAME: RecipeUserGroupOvBeanFactory <br>
 * @version: 1.0, Oct 2, 2007 <br>
 *****************************************************************************/

public class RecipeUserGroupOvBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_USER_GROUP_ID = "USER_GROUP_ID";
  public String ATTRIBUTE_COMPANIES = "COMPANIES";

  //table name
  public String TABLE = "RECIPE_USER_GROUP_OV";

  //constructor
  public RecipeUserGroupOvBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("userGroupId")) {
      return ATTRIBUTE_USER_GROUP_ID;
    }
    else if (attributeName.equals("companies")) {
      return ATTRIBUTE_COMPANIES;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, RecipeUserGroupOvBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(RecipeUserGroupOvBean recipeUserGroupOvBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("personnelId", "SearchCriterion.EQUALS",
     "" + recipeUserGroupOvBean.getPersonnelId());
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
       public int delete(RecipeUserGroupOvBean recipeUserGroupOvBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("personnelId", "SearchCriterion.EQUALS",
     "" + recipeUserGroupOvBean.getPersonnelId());
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
   public int insert(RecipeUserGroupOvBean recipeUserGroupOvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(recipeUserGroupOvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int insert(RecipeUserGroupOvBean recipeUserGroupOvBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_PERSONNEL_ID + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_USER_GROUP_ID + "," +
     ATTRIBUTE_COMPANIES + ")" +
     " values (" +
     recipeUserGroupOvBean.getPersonnelId() + "," +
     SqlHandler.delimitString(recipeUserGroupOvBean.getCompanyId()) + "," +
     SqlHandler.delimitString(recipeUserGroupOvBean.getUserGroupId()) + "," +
     SqlHandler.delimitString(recipeUserGroupOvBean.getCompanies()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(RecipeUserGroupOvBean recipeUserGroupOvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(recipeUserGroupOvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int update(RecipeUserGroupOvBean recipeUserGroupOvBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_PERSONNEL_ID + "=" +
      StringHandler.nullIfZero(recipeUserGroupOvBean.getPersonnelId()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(recipeUserGroupOvBean.getCompanyId()) + "," +
     ATTRIBUTE_USER_GROUP_ID + "=" +
      SqlHandler.delimitString(recipeUserGroupOvBean.getUserGroupId()) + "," +
     ATTRIBUTE_COMPANIES + "=" +
      SqlHandler.delimitString(recipeUserGroupOvBean.getCompanies()) + " " +
     "where " + ATTRIBUTE_PERSONNEL_ID + "=" +
      recipeUserGroupOvBean.getPersonnelId();
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws
      BaseException {
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

  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria,
                           Connection conn) throws BaseException {
    Collection recipeUserGroupOvBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      RecipeUserGroupOvBean recipeUserGroupOvBean = new RecipeUserGroupOvBean();
      load(dataSetRow, recipeUserGroupOvBean);
      recipeUserGroupOvBeanColl.add(recipeUserGroupOvBean);
    }
    return recipeUserGroupOvBeanColl;
  }

  public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException,
      Exception {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      java.util.Map map = connection.getTypeMap();
      map.put("CUSTOMER.RECIPE_USER_GROUP_OBJ",
              Class.forName(
          "com.tcmis.internal.hub.beans.RecipeUserGroupOvBean"));
      map.put("CUSTOMER.COMPANY_CATALOG_OBJ",
              Class.forName(
          "com.tcmis.internal.hub.beans.CompanyCatalogObjBean"));
      map.put("CUSTOMER.CATALOG_OBJ",
              Class.forName(
          "com.tcmis.internal.hub.beans.CatalogObjBean"));

      c = selectObject(criteria, sortCriteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws
      BaseException, Exception {

    Collection beanColl = new Vector();

    String query = "select value(p) from " + TABLE + " p " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    Statement st = conn.createStatement();
    ResultSet rs = st.executeQuery(query);
    while (rs.next()) {
      RecipeUserGroupOvBean b = (RecipeUserGroupOvBean) rs.getObject(1);
      beanColl.add(b);
    }
    rs.close();
    st.close();
    return beanColl;
  }
}