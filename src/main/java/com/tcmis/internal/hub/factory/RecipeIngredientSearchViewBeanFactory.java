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
import com.tcmis.internal.hub.beans.RecipeIngredientSearchViewBean;

/******************************************************************************
 * CLASSNAME: RecipeIngredientSearchViewBeanFactory <br>
 * @version: 1.0, Sep 25, 2007 <br>
 *****************************************************************************/

public class RecipeIngredientSearchViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
  public String ATTRIBUTE_MATERIAL_DESC = "MATERIAL_DESC";
  public String ATTRIBUTE_GRADE = "GRADE";
  public String ATTRIBUTE_MFG_DESC = "MFG_DESC";
  public String ATTRIBUTE_SEARCH_STRING = "SEARCH_STRING";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
  public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
  public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";

  //table name
  public String TABLE = "RECIPE_INGREDIENT_SEARCH_VIEW";

  //constructor
  public RecipeIngredientSearchViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("itemId")) {
      return ATTRIBUTE_ITEM_ID;
    }
    else if (attributeName.equals("materialDesc")) {
      return ATTRIBUTE_MATERIAL_DESC;
    }
    else if (attributeName.equals("grade")) {
      return ATTRIBUTE_GRADE;
    }
    else if (attributeName.equals("mfgDesc")) {
      return ATTRIBUTE_MFG_DESC;
    }
    else if (attributeName.equals("searchString")) {
      return ATTRIBUTE_SEARCH_STRING;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
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
    return super.getType(attributeName, RecipeIngredientSearchViewBean.class);
  }

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
    Collection recipeIngredientSearchViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      RecipeIngredientSearchViewBean recipeIngredientSearchViewBean = new
          RecipeIngredientSearchViewBean();
      load(dataSetRow, recipeIngredientSearchViewBean);
      recipeIngredientSearchViewBeanColl.add(recipeIngredientSearchViewBean);
    }
    return recipeIngredientSearchViewBeanColl;
  }
}