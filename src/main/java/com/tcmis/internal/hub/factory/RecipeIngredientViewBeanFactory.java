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
import com.tcmis.internal.hub.beans.RecipeIngredientViewBean;

/******************************************************************************
 * CLASSNAME: RecipeIngredientViewBeanFactory <br>
 * @version: 1.0, Sep 27, 2007 <br>
 *****************************************************************************/

public class RecipeIngredientViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_RECIPE_ID = "RECIPE_ID";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_AMOUNT = "AMOUNT";
  public String ATTRIBUTE_AMOUNT_UNIT = "AMOUNT_UNIT";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
  public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
  public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
  public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";

  //table name
  public String TABLE = "RECIPE_INGREDIENT_VIEW";

  //constructor
  public RecipeIngredientViewBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("amount")) {
      return ATTRIBUTE_AMOUNT;
    }
    else if (attributeName.equals("amountUnit")) {
      return ATTRIBUTE_AMOUNT_UNIT;
    }
    else if (attributeName.equals("partDescription")) {
      return ATTRIBUTE_PART_DESCRIPTION;
    }
    else if (attributeName.equals("catalogId")) {
      return ATTRIBUTE_CATALOG_ID;
    }
    else if (attributeName.equals("catalogCompanyId")) {
      return ATTRIBUTE_CATALOG_COMPANY_ID;
    }
    else if (attributeName.equals("partGroupNo")) {
      return ATTRIBUTE_PART_GROUP_NO;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, RecipeIngredientViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(RecipeIngredientViewBean recipeIngredientViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
     "" + recipeIngredientViewBean.getCompanyId());
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
   public int delete(RecipeIngredientViewBean recipeIngredientViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
     "" + recipeIngredientViewBean.getCompanyId());
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
   public int insert(RecipeIngredientViewBean recipeIngredientViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(recipeIngredientViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(RecipeIngredientViewBean recipeIngredientViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_RECIPE_ID + "," +
     ATTRIBUTE_CAT_PART_NO + "," +
     ATTRIBUTE_AMOUNT + "," +
     ATTRIBUTE_AMOUNT_UNIT + "," +
     ATTRIBUTE_PART_DESCRIPTION + "," +
     ATTRIBUTE_CATALOG_ID + "," +
     ATTRIBUTE_CATALOG_COMPANY_ID + "," +
     ATTRIBUTE_PART_GROUP_NO + ")" +
     " values (" +
     SqlHandler.delimitString(recipeIngredientViewBean.getCompanyId()) + "," +
     recipeIngredientViewBean.getRecipeId() + "," +
     SqlHandler.delimitString(recipeIngredientViewBean.getCatPartNo()) + "," +
     recipeIngredientViewBean.getAmount() + "," +
     SqlHandler.delimitString(recipeIngredientViewBean.getAmountUnit()) + "," +
       SqlHandler.delimitString(recipeIngredientViewBean.getPartDescription()) + "," +
     SqlHandler.delimitString(recipeIngredientViewBean.getCatalogId()) + "," +
       SqlHandler.delimitString(recipeIngredientViewBean.getCatalogCompanyId()) + "," +
     recipeIngredientViewBean.getPartGroupNo() + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(RecipeIngredientViewBean recipeIngredientViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(recipeIngredientViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(RecipeIngredientViewBean recipeIngredientViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(recipeIngredientViewBean.getCompanyId()) + "," +
     ATTRIBUTE_RECIPE_ID + "=" +
      StringHandler.nullIfZero(recipeIngredientViewBean.getRecipeId()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
      SqlHandler.delimitString(recipeIngredientViewBean.getCatPartNo()) + "," +
     ATTRIBUTE_AMOUNT + "=" +
      StringHandler.nullIfZero(recipeIngredientViewBean.getAmount()) + "," +
     ATTRIBUTE_AMOUNT_UNIT + "=" +
       SqlHandler.delimitString(recipeIngredientViewBean.getAmountUnit()) + "," +
     ATTRIBUTE_PART_DESCRIPTION + "=" +
       SqlHandler.delimitString(recipeIngredientViewBean.getPartDescription()) + "," +
     ATTRIBUTE_CATALOG_ID + "=" +
      SqlHandler.delimitString(recipeIngredientViewBean.getCatalogId()) + "," +
     ATTRIBUTE_CATALOG_COMPANY_ID + "=" +
       SqlHandler.delimitString(recipeIngredientViewBean.getCatalogCompanyId()) + "," +
     ATTRIBUTE_PART_GROUP_NO + "=" +
       StringHandler.nullIfZero(recipeIngredientViewBean.getPartGroupNo()) + " " +
     "where " + ATTRIBUTE_COMPANY_ID + "=" +
      recipeIngredientViewBean.getCompanyId();
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
    Collection recipeIngredientViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      RecipeIngredientViewBean recipeIngredientViewBean = new
          RecipeIngredientViewBean();
      load(dataSetRow, recipeIngredientViewBean);
      recipeIngredientViewBeanColl.add(recipeIngredientViewBean);
    }
    return recipeIngredientViewBeanColl;
  }
}