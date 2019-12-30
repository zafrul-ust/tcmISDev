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
import com.tcmis.internal.report.beans.UserColCommentsBean;

/******************************************************************************
 * CLASSNAME: UserColCommentsBeanFactory <br>
 * @version: 1.0, Mar 30, 2004 <br>
 *****************************************************************************/

public class UserColCommentsBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_TABLE_NAME = "TABLE_NAME";
  public String ATTRIBUTE_COLUMN_NAME = "COLUMN_NAME";
  public String ATTRIBUTE_COMMENTS = "COMMENTS";
  public String ATTRIBUTE_DATA_TYPE = "DATA_TYPE";

  //sequence and table names
  public String SEQUENCE = "TABLE_NAME";
  public String TABLE = "USER_COL_COMMENTS";

  //constructor
  public UserColCommentsBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("tableName")) {
      return ATTRIBUTE_TABLE_NAME;
    }
    else if (attributeName.equals("columnName")) {
      return ATTRIBUTE_COLUMN_NAME;
    }
    else if (attributeName.equals("comments")) {
      return ATTRIBUTE_COMMENTS;
    }
    else if (attributeName.equals("dataType")) {
      return ATTRIBUTE_DATA_TYPE;
    }

    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, UserColCommentsBean.class);
  }

  /*
//delete
       public int delete(UserColCommentsBean userColCommentsBean, TcmISDBModel dbModel)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("tableName", "=",
     "" + userColCommentsBean.getTableName());
    return delete(criteria, dbModel.getConnection());
   }
   public int delete(UserColCommentsBean userColCommentsBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("tableName", "=",
     "" + userColCommentsBean.getTableName());
    return delete(criteria, conn);
   }
   public int delete(SearchCriteria criteria, TcmISDBModel dbModel)
    throws BaseException {
    return delete(criteria, dbModel.getConnection());
   }
   public int delete(SearchCriteria criteria, Connection conn)
    throws BaseException {
    String sqlQuery = " delete from " + TABLE + " " +
     getWhereClause(criteria);
    return new SqlManager().update(conn, sqlQuery);
   }
//insert
       public int insert(UserColCommentsBean userColCommentsBean, TcmISDBModel dbModel)
    throws BaseException {
    return insert(userColCommentsBean, dbModel.getConnection());
   }
   public int insert(UserColCommentsBean userColCommentsBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    userColCommentsBean.
     setTableName(sqlManager.getOracleSequence(conn, SEQUENCE));
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_TABLE_NAME + "," +
     ATTRIBUTE_COLUMN_NAME + "," +
     ATTRIBUTE_COMMENTS + ")" +
     SqlHandler.delimitString(userColCommentsBean.getTableName()) + "," +
     SqlHandler.delimitString(userColCommentsBean.getColumnName()) + "," +
     SqlHandler.delimitString(userColCommentsBean.getComments()) + ")";
    return sqlManager.update(conn, query);
   }
//update
       public int update(UserColCommentsBean userColCommentsBean, TcmISDBModel dbModel)
    throws BaseException {
    return update(userColCommentsBean, dbModel.getConnection());
   }
   public int update(UserColCommentsBean userColCommentsBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_TABLE_NAME + "=" +
      SqlHandler.delimitString(userColCommentsBean.getTableName()) + "," +
     ATTRIBUTE_COLUMN_NAME + "=" +
      SqlHandler.delimitString(userColCommentsBean.getColumnName()) + "," +
     ATTRIBUTE_COMMENTS + "=" +
      SqlHandler.delimitString(userColCommentsBean.getComments()) + " " +
     "where " + ATTRIBUTE_TABLE_NAME + "=" +
      StringHandler.nullIfZero(userColCommentsBean.getTableName());
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

    Collection userColCommentsBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if (log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      UserColCommentsBean userColCommentsBean = new UserColCommentsBean();
      load(dataSetRow, userColCommentsBean);
      userColCommentsBeanColl.add(userColCommentsBean);
    }

    return userColCommentsBeanColl;
  }

  public Collection selectWithDataType(String tableName) throws
      BaseException {
    Connection connection = this.getDbManager().getConnection();
    Collection c = selectWithDataType(tableName, connection);
    this.getDbManager().returnConnection(connection);
    return c;

  }

  public Collection selectWithDataType(String tableName, Connection conn) throws
      BaseException {

    Collection userColCommentsBeanColl = new Vector();
    /*
        String query = "select t.table_name, " +
            "t.column_name, " +
            "t.data_type, " +
            "c.comments " +
            "from " +
            "user_col_comments c, " +
            "user_tab_columns t " +
            "where " +
            "t.table_name = c.table_name and " +
            "t.column_name = c.column_name and " +
            "t.table_name='" + tableName + "'";
     */
    String query = "select y.* from user_tab_columns x, user_col_comments y " +
        "where x.table_name='" + tableName + "' and " +
        "x.table_name=y.table_name  and " +
        "x.COLUMN_name=y.COLUMN_name " +
        "order by x.COLUMN_ID";

    if (log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      UserColCommentsBean userColCommentsBean = new UserColCommentsBean();
      load(dataSetRow, userColCommentsBean);
      userColCommentsBeanColl.add(userColCommentsBean);
    }

    return userColCommentsBeanColl;
  }

}