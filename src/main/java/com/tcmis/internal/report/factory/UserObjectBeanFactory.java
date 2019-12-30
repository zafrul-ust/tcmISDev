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
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.report.beans.UserObjectBean;

/******************************************************************************
 * CLASSNAME: UserObjectBeanFactory <br>
 * @version: 1.0, Apr 21, 2004 <br>
 *****************************************************************************/

public class UserObjectBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_TABLE_NAME = "TABLE_NAME";
  public String ATTRIBUTE_COMMENTS = "COMMENTS";

  //sequence and table names
  public String SEQUENCE = "PERSONNEL_SEQ";
  public String TABLE = "USER_OBJECT";

  //constructor
  public UserObjectBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("personnelId")) {
      return ATTRIBUTE_PERSONNEL_ID;
    }
    else if (attributeName.equals("tableName")) {
      return ATTRIBUTE_TABLE_NAME;
    }
    else if (attributeName.equals("comments")) {
      return ATTRIBUTE_COMMENTS;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, UserObjectBean.class);
  }

  /*
    //delete
       public int delete(UserObjectBean userObjectBean, TcmISDBModel dbModel) throws
        BaseException {
      SearchCriteria criteria = new SearchCriteria("personnelId", "=",
                                                   "" +
       userObjectBean.getPersonnelId());
      return delete(criteria, dbModel.getConnection());
    }
    public int delete(UserObjectBean userObjectBean, Connection conn) throws
        BaseException {
      SearchCriteria criteria = new SearchCriteria("personnelId", "=",
                                                   "" +
       userObjectBean.getPersonnelId());
      return delete(criteria, conn);
    }
    public int delete(SearchCriteria criteria, TcmISDBModel dbModel) throws
        BaseException {
      return delete(criteria, dbModel.getConnection());
    }
    public int delete(SearchCriteria criteria, Connection conn) throws
        BaseException {
      String sqlQuery = " delete from " + TABLE + " " +
          getWhereClause(criteria);
      return new SqlManager().update(conn, sqlQuery);
    }
    //insert
       public int insert(UserObjectBean userObjectBean, TcmISDBModel dbModel) throws
        BaseException {
      return insert(userObjectBean, dbModel.getConnection());
    }
    public int insert(UserObjectBean userObjectBean, Connection conn) throws
        BaseException {
      SqlManager sqlManager = new SqlManager();
      userObjectBean.
          setPersonnelId(sqlManager.getOracleSequence(conn, SEQUENCE));
      String query =
          "insert into " + TABLE + " (" +
          ATTRIBUTE_PERSONNEL_ID + "," +
          ATTRIBUTE_TABLE_NAME + "," +
          ATTRIBUTE_COMMENTS + ")" +
          StringHandler.nullIfZero(userObjectBean.getPersonnelId()) + "," +
          SqlHandler.delimitString(userObjectBean.getTableName()) + "," +
          SqlHandler.delimitString(userObjectBean.getComments()) + ")";
      return sqlManager.update(conn, query);
    }
    //update
       public int update(UserObjectBean userObjectBean, TcmISDBModel dbModel) throws
        BaseException {
      return update(userObjectBean, dbModel.getConnection());
    }
    public int update(UserObjectBean userObjectBean, Connection conn) throws
        BaseException {
      String query = "update " + TABLE + " set " +
          ATTRIBUTE_PERSONNEL_ID + "=" +
          StringHandler.nullIfZero(userObjectBean.getPersonnelId()) + "," +
          ATTRIBUTE_TABLE_NAME + "=" +
          SqlHandler.delimitString(userObjectBean.getTableName()) + "," +
          ATTRIBUTE_COMMENTS + "=" +
          SqlHandler.delimitString(userObjectBean.getComments()) + " " +
          "where " + ATTRIBUTE_PERSONNEL_ID + "=" +
          StringHandler.nullIfZero(userObjectBean.getPersonnelId());
      return new SqlManager().update(conn, query);
    }
   */
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

    UserColCommentsBeanFactory factory = new UserColCommentsBeanFactory(this.
        getDbManager());
    Collection userObjectBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if (log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      UserObjectBean userObjectBean = new UserObjectBean();
      load(dataSetRow, userObjectBean);

      //load the collection of UserColCommentsBean
      SearchCriteria searchCriteria = new SearchCriteria();
      searchCriteria.addCriterion("tableName",
                                  SearchCriterion.EQUALS,
                                  dataSetRow.getString(ATTRIBUTE_TABLE_NAME));
      userObjectBean.setUserColCommentsBeanCollection(factory.
          selectWithDataType(dataSetRow.getString(ATTRIBUTE_TABLE_NAME), conn));

      userObjectBeanColl.add(userObjectBean);
    }

    return userObjectBeanColl;
  }

  public Collection selectTableOnly(SearchCriteria criteria) throws
      BaseException {
    Connection connection = this.getDbManager().getConnection();
    Collection c = selectTableOnly(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return c;
  }

  public Collection selectTableOnly(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection userObjectBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if (log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      UserObjectBean userObjectBean = new UserObjectBean();
      load(dataSetRow, userObjectBean);
      userObjectBeanColl.add(userObjectBean);
    }
    return userObjectBeanColl;
  }

}