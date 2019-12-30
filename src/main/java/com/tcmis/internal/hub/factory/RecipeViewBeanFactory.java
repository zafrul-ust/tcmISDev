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
import com.tcmis.internal.hub.beans.RecipeViewBean;

/******************************************************************************
 * CLASSNAME: RecipeViewBeanFactory <br>
 * @version: 1.0, Sep 25, 2007 <br>
 *****************************************************************************/

public class RecipeViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_RECIPE_ID = "RECIPE_ID";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
  public String ATTRIBUTE_RECIPE_NAME = "RECIPE_NAME";
  public String ATTRIBUTE_RECIPE_DESCRIPTION = "RECIPE_DESCRIPTION";
  public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
  public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
  public String ATTRIBUTE_YIELD_AMOUNT = "YIELD_AMOUNT";
  public String ATTRIBUTE_YIELD_AMOUNT_UNIT = "YIELD_AMOUNT_UNIT";
  public String ATTRIBUTE_RECIPE_QC_DATE = "RECIPE_QC_DATE";
  public String ATTRIBUTE_RECIPE_QC_USER_ID = "RECIPE_QC_USER_ID";
  public String ATTRIBUTE_RECIPE_QC_USERNAME = "RECIPE_QC_USERNAME";
  public String ATTRIBUTE_INACTIVATION_DATE = "INACTIVATION_DATE";
  public String ATTRIBUTE_INSTRUCTIONS = "INSTRUCTIONS";

  //table name
  public String TABLE = "RECIPE_VIEW";

  //constructor
  public RecipeViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("recipeId")) {
      return ATTRIBUTE_RECIPE_ID;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("catalogId")) {
      return ATTRIBUTE_CATALOG_ID;
    }
    else if (attributeName.equals("recipeName")) {
      return ATTRIBUTE_RECIPE_NAME;
    }
    else if (attributeName.equals("recipeDescription")) {
      return ATTRIBUTE_RECIPE_DESCRIPTION;
    }
    else if (attributeName.equals("itemId")) {
      return ATTRIBUTE_ITEM_ID;
    }
    else if (attributeName.equals("itemDesc")) {
      return ATTRIBUTE_ITEM_DESC;
    }
    else if (attributeName.equals("yieldAmount")) {
      return ATTRIBUTE_YIELD_AMOUNT;
    }
    else if (attributeName.equals("yieldAmountUnit")) {
      return ATTRIBUTE_YIELD_AMOUNT_UNIT;
    }
    else if (attributeName.equals("recipeQcDate")) {
      return ATTRIBUTE_RECIPE_QC_DATE;
    }
    else if (attributeName.equals("recipeQcUserId")) {
      return ATTRIBUTE_RECIPE_QC_USER_ID;
    }
    else if (attributeName.equals("recipeQcUsername")) {
      return ATTRIBUTE_RECIPE_QC_USERNAME;
    }
    else if (attributeName.equals("inactivationDate")) {
      return ATTRIBUTE_INACTIVATION_DATE;
    }
    else if (attributeName.equals("instructions")) {
      return ATTRIBUTE_INSTRUCTIONS;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, RecipeViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(RrecipeViewBean recipeViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("recipeId", "SearchCriterion.EQUALS",
     "" + recipeViewBean.getRecipeId());
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
   public int delete(RecipeViewBean recipeViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("recipeId", "SearchCriterion.EQUALS",
     "" + recipeViewBean.getRecipeId());
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
   public int insert(RecipeViewBean recipeViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(recipeViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(RecipeViewBean recipeViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_RECIPE_ID + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_CATALOG_ID + "," +
     ATTRIBUTE_RECIPE_NAME + "," +
     ATTRIBUTE_RECIPE_DESCRIPTION + "," +
     ATTRIBUTE_ITEM_ID + "," +
     ATTRIBUTE_ITEM_DESC + "," +
     ATTRIBUTE_YIELD_AMOUNT + "," +
     ATTRIBUTE_YIELD_AMOUNT_UNIT + "," +
     ATTRIBUTE_RECIPE_QC_DATE + "," +
     ATTRIBUTE_RECIPE_QC_USER_ID + "," +
     ATTRIBUTE_RECIPE_QC_USERNAME + "," +
     ATTRIBUTE_INACTIVATION_DATE + "," +
     ATTRIBUTE_INSTRUCTIONS + ")" +
     " values (" +
     recipeViewBean.getRecipeId() + "," +
     SqlHandler.delimitString(recipeViewBean.getCompanyId()) + "," +
     SqlHandler.delimitString(recipeViewBean.getCatalogId()) + "," +
     SqlHandler.delimitString(recipeViewBean.getRecipeName()) + "," +
       SqlHandler.delimitString(recipeViewBean.getRecipeDescription()) + "," +
     recipeViewBean.getItemId() + "," +
     SqlHandler.delimitString(recipeViewBean.getItemDesc()) + "," +
     recipeViewBean.getYieldAmount() + "," +
       SqlHandler.delimitString(recipeViewBean.getYieldAmountUnit()) + "," +
     DateHandler.getOracleToDateFunction(recipeViewBean.getRecipeQcDate()) + "," +
     recipeViewBean.getRecipeQcUserId() + "," +
       SqlHandler.delimitString(recipeViewBean.getRecipeQcUsername()) + "," +
     DateHandler.getOracleToDateFunction(recipeViewBean.getInactivationDate()) + "," +
     SqlHandler.delimitString(recipeViewBean.getInstructions()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(RecipeViewBean recipeViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(recipeViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(RecipeViewBean recipeViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_RECIPE_ID + "=" +
      StringHandler.nullIfZero(recipeViewBean.getRecipeId()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(recipeViewBean.getCompanyId()) + "," +
     ATTRIBUTE_CATALOG_ID + "=" +
      SqlHandler.delimitString(recipeViewBean.getCatalogId()) + "," +
     ATTRIBUTE_RECIPE_NAME + "=" +
      SqlHandler.delimitString(recipeViewBean.getRecipeName()) + "," +
     ATTRIBUTE_RECIPE_DESCRIPTION + "=" +
       SqlHandler.delimitString(recipeViewBean.getRecipeDescription()) + "," +
     ATTRIBUTE_ITEM_ID + "=" +
      StringHandler.nullIfZero(recipeViewBean.getItemId()) + "," +
     ATTRIBUTE_ITEM_DESC + "=" +
      SqlHandler.delimitString(recipeViewBean.getItemDesc()) + "," +
     ATTRIBUTE_YIELD_AMOUNT + "=" +
       StringHandler.nullIfZero(recipeViewBean.getYieldAmount()) + "," +
     ATTRIBUTE_YIELD_AMOUNT_UNIT + "=" +
       SqlHandler.delimitString(recipeViewBean.getYieldAmountUnit()) + "," +
     ATTRIBUTE_RECIPE_QC_DATE + "=" +
      DateHandler.getOracleToDateFunction(recipeViewBean.getRecipeQcDate()) + "," +
     ATTRIBUTE_RECIPE_QC_USER_ID + "=" +
       StringHandler.nullIfZero(recipeViewBean.getRecipeQcUserId()) + "," +
     ATTRIBUTE_RECIPE_QC_USERNAME + "=" +
       SqlHandler.delimitString(recipeViewBean.getRecipeQcUsername()) + "," +
     ATTRIBUTE_INACTIVATION_DATE + "=" +
      DateHandler.getOracleToDateFunction(recipeViewBean.getInactivationDate()) + "," +
     ATTRIBUTE_INSTRUCTIONS + "=" +
       SqlHandler.delimitString(recipeViewBean.getInstructions()) + " " +
     "where " + ATTRIBUTE_RECIPE_ID + "=" +
      recipeViewBean.getRecipeId();
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
    Collection recipeViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      RecipeViewBean recipeViewBean = new RecipeViewBean();
      load(dataSetRow, recipeViewBean);
      recipeViewBeanColl.add(recipeViewBean);
    }
    return recipeViewBeanColl;
  }
}