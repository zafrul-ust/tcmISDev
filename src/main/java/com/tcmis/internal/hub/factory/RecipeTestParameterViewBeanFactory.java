package com.tcmis.internal.hub.factory;

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
import com.tcmis.internal.hub.beans.RecipeTestParameterViewBean;

/******************************************************************************
 * CLASSNAME: RecipeTestParameterViewBeanFactory <br>
 * @version: 1.0, Sep 27, 2007 <br>
 *****************************************************************************/

public class RecipeTestParameterViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_RECIPE_ID = "RECIPE_ID";
  public String ATTRIBUTE_TEST_PARAMETER = "TEST_PARAMETER";
  public String ATTRIBUTE_TEST_PARAMETER_UNIT = "TEST_PARAMETER_UNIT";

  //table name
  public String TABLE = "RECIPE_TEST_PARAMETER_VIEW";

  //constructor
  public RecipeTestParameterViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("recipeId")) {
      return ATTRIBUTE_RECIPE_ID;
    }
    else if (attributeName.equals("testParameter")) {
      return ATTRIBUTE_TEST_PARAMETER;
    }
    else if (attributeName.equals("testParameterUnit")) {
      return ATTRIBUTE_TEST_PARAMETER_UNIT;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, RecipeTestParameterViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(RecipeTestParameterViewBean recipeTestParameterViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
     "" + recipeTestParameterViewBean.getCompanyId());
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
   public int delete(RecipeTestParameterViewBean recipeTestParameterViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
     "" + recipeTestParameterViewBean.getCompanyId());
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
   public int insert(RecipeTestParameterViewBean recipeTestParameterViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(recipeTestParameterViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(RecipeTestParameterViewBean recipeTestParameterViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_RECIPE_ID + "," +
     ATTRIBUTE_TEST_PARAMETER + "," +
     ATTRIBUTE_TEST_PARAMETER_UNIT + ")" +
     " values (" +
       SqlHandler.delimitString(recipeTestParameterViewBean.getCompanyId()) + "," +
     recipeTestParameterViewBean.getRecipeId() + "," +
       SqlHandler.delimitString(recipeTestParameterViewBean.getTestParameter()) + "," +
     SqlHandler.delimitString(recipeTestParameterViewBean.getTestParameterUnit()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(RecipeTestParameterViewBean recipeTestParameterViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(recipeTestParameterViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(RecipeTestParameterViewBean recipeTestParameterViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_COMPANY_ID + "=" +
       SqlHandler.delimitString(recipeTestParameterViewBean.getCompanyId()) + "," +
     ATTRIBUTE_RECIPE_ID + "=" +
       StringHandler.nullIfZero(recipeTestParameterViewBean.getRecipeId()) + "," +
     ATTRIBUTE_TEST_PARAMETER + "=" +
       SqlHandler.delimitString(recipeTestParameterViewBean.getTestParameter()) + "," +
     ATTRIBUTE_TEST_PARAMETER_UNIT + "=" +
      SqlHandler.delimitString(recipeTestParameterViewBean.getTestParameterUnit()) + " " +
     "where " + ATTRIBUTE_COMPANY_ID + "=" +
      recipeTestParameterViewBean.getCompanyId();
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
    Collection recipeTestParameterViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      RecipeTestParameterViewBean recipeTestParameterViewBean = new
          RecipeTestParameterViewBean();
      load(dataSetRow, recipeTestParameterViewBean);
      recipeTestParameterViewBeanColl.add(recipeTestParameterViewBean);
    }
    return recipeTestParameterViewBeanColl;
  }
}