package com.tcmis.internal.report.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.report.beans.UsersSavedQueriesBean;

/******************************************************************************
 * CLASSNAME: UsersSavedQueriesBeanFactory <br>
 * @version: 1.0, Apr 13, 2004 <br>
 *****************************************************************************/

public class UsersSavedQueriesBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_QUERY_NAME = "QUERY_NAME";
  public String ATTRIBUTE_QUERY_TEXT = "QUERY_TEXT";
  public String ATTRIBUTE_DATE_SAVED = "DATE_SAVED";

  //sequence and table names
  public String SEQUENCE = "COMPANY_SEQ";
  public String TABLE = "USERS_SAVED_QUERIES";

  //constructor
  public UsersSavedQueriesBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("personnelId")) {
      return ATTRIBUTE_PERSONNEL_ID;
    }
    else if (attributeName.equals("queryName")) {
      return ATTRIBUTE_QUERY_NAME;
    }
    else if (attributeName.equals("query")) {
      return ATTRIBUTE_QUERY_TEXT;
    }
    else if (attributeName.equals("dateSaved")) {
      return ATTRIBUTE_DATE_SAVED;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, UsersSavedQueriesBean.class);
  }

  //delete
  public int delete(UsersSavedQueriesBean usersSavedQueriesBean) throws
      BaseException {
    int result = 0;
    Connection connection = getDbManager().getConnection();
    result = this.delete(usersSavedQueriesBean, connection);
    getDbManager().returnConnection(connection);
    return result;
  }

  public int delete(UsersSavedQueriesBean usersSavedQueriesBean,
                    Connection conn) throws BaseException {

    SearchCriteria criteria =
        new SearchCriteria("companyId",
                           SearchCriterion.EQUALS,
                           usersSavedQueriesBean.getCompanyId());
    criteria.addCriterion("personnelId",
                          SearchCriterion.EQUALS,
                          new Integer(usersSavedQueriesBean.getPersonnelId()).
                          toString());
    criteria.addCriterion("queryName",
                          SearchCriterion.EQUALS,
                          usersSavedQueriesBean.getQueryName());

    return delete(criteria, conn);
  }

  public int delete(SearchCriteria criteria) throws
      BaseException {
    int result = 0;
    Connection connection = getDbManager().getConnection();
    result = this.delete(criteria, connection);
    getDbManager().returnConnection(connection);
    return result;

  }

  public int delete(SearchCriteria criteria, Connection conn) throws
      BaseException {

    String query = " delete from " + TABLE + " " +
        getWhereClause(criteria);
    if (log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    return new SqlManager().update(conn, query);
  }

  //insert
  public int insert(UsersSavedQueriesBean usersSavedQueriesBean) throws
      BaseException {
    int result = 0;
    Connection connection = getDbManager().getConnection();
    result = this.insert(usersSavedQueriesBean, connection);
    getDbManager().returnConnection(connection);
    return result;
  }

  public int insert(UsersSavedQueriesBean usersSavedQueriesBean,
                    Connection conn) throws BaseException {

    SqlManager sqlManager = new SqlManager();

    String query =
        "insert into " + TABLE + " (" +
        //ATTRIBUTE_COMPANY_ID + "," +
        ATTRIBUTE_PERSONNEL_ID + "," +
        ATTRIBUTE_QUERY_NAME + "," +
        ATTRIBUTE_QUERY_TEXT + "," +
        ATTRIBUTE_DATE_SAVED + ") VALUES (" +
        //SqlHandler.delimitString(usersSavedQueriesBean.getCompanyId()) + "," +
        StringHandler.nullIfZero(usersSavedQueriesBean.getPersonnelId()) + "," +
        SqlHandler.delimitString(usersSavedQueriesBean.getQueryName()) + "," +
        SqlHandler.delimitString(usersSavedQueriesBean.getQuery()) + "," +
        DateHandler.getOracleToDateFunction(usersSavedQueriesBean.getDateSaved()) +
        ")";
    if (log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    return sqlManager.update(conn, query);
  }

  //update
  public int update(UsersSavedQueriesBean usersSavedQueriesBean) throws
      BaseException {
    int result = 0;
    Connection connection = getDbManager().getConnection();
    result = this.update(usersSavedQueriesBean, connection);
    getDbManager().returnConnection(connection);
    return result;
  }

  public int update(UsersSavedQueriesBean usersSavedQueriesBean,
                    Connection conn) throws BaseException {

    String query = "update " + TABLE + " set " +
        //ATTRIBUTE_COMPANY_ID + "=" +
        //SqlHandler.delimitString(usersSavedQueriesBean.getCompanyId()) + "," +
        ATTRIBUTE_PERSONNEL_ID + "=" +
        StringHandler.nullIfZero(usersSavedQueriesBean.getPersonnelId()) + "," +
        ATTRIBUTE_QUERY_NAME + "=" +
        SqlHandler.delimitString(usersSavedQueriesBean.getQueryName()) + "," +
        ATTRIBUTE_QUERY_TEXT + "=" +
        SqlHandler.delimitString(usersSavedQueriesBean.getQuery()) + "," +
        ATTRIBUTE_DATE_SAVED + "=" +
        DateHandler.getOracleToDateFunction(usersSavedQueriesBean.getDateSaved()) +
        " " +
        //"where " + ATTRIBUTE_COMPANY_ID + "=" +
        //SqlHandler.delimitString(usersSavedQueriesBean.getCompanyId()) +
        " where " + ATTRIBUTE_PERSONNEL_ID + "=" +
        StringHandler.nullIfZero(usersSavedQueriesBean.getPersonnelId()) +
        " and " + ATTRIBUTE_QUERY_NAME + "=" +
        SqlHandler.delimitString(usersSavedQueriesBean.getQueryName());
    if (log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    return new SqlManager().update(conn, query);
  }

  //select
  public Collection select(SearchCriteria criteria) throws
      BaseException {
    Connection connection = this.getDbManager().getConnection();
    Collection c = select(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return c;
  }

  public Collection select(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection usersSavedQueriesBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if (log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      UsersSavedQueriesBean usersSavedQueriesBean = new UsersSavedQueriesBean();
      load(dataSetRow, usersSavedQueriesBean);
      usersSavedQueriesBeanColl.add(usersSavedQueriesBean);
    }

    return usersSavedQueriesBeanColl;
  }
}