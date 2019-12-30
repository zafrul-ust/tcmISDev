package radian.tcmis.internal.snoop.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.sql.Connection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import radian.tcmis.common.exceptions.BaseException;
import radian.tcmis.common.framework.BaseBeanFactory;
import radian.tcmis.common.db.SqlManager;
import radian.tcmis.common.util.*;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.internal.snoop.beans.UserEnvLogBean;

/******************************************************************************
 * CLASSNAME: UserEnvLogBeanFactory <br>
 * @version: 1.0, Dec 3, 2004 <br>
 *****************************************************************************/

public class UserEnvLogBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_TIME_STAMP = "TIME_STAMP";
  public String ATTRIBUTE_DETAIL = "DETAIL";

  //table name
  public String TABLE = "USER_ENV_LOG";

  //constructor
  public UserEnvLogBeanFactory(String client) {
    super(client);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("personnelId")) {
      return ATTRIBUTE_PERSONNEL_ID;
    }
    else if (attributeName.equals("timeStamp")) {
      return ATTRIBUTE_TIME_STAMP;
    }
    else if (attributeName.equals("detail")) {
      return ATTRIBUTE_DETAIL;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, UserEnvLogBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(UserEnvLogBean userEnvLogBean, TcmISDBModel dbModel)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("personnelId", "=",
     "" + userEnvLogBean.getPersonnelId());
    return delete(criteria, dbModel.getConnection());
   }
   public int delete(UserEnvLogBean userEnvLogBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("personnelId", "=",
     "" + userEnvLogBean.getPersonnelId());
    return delete(criteria, conn);
   }
   */

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
  public int insert(UserEnvLogBean userEnvLogBean, TcmISDBModel dbModel) throws
      BaseException {
    return insert(userEnvLogBean, dbModel.getConnection());
  }

  public int insert(UserEnvLogBean userEnvLogBean, Connection conn) throws
      BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_PERSONNEL_ID + "," +
        ATTRIBUTE_TIME_STAMP + "," +
        ATTRIBUTE_DETAIL + ") values (" +
        StringHandler.nullIfZero(userEnvLogBean.getPersonnelId()) + "," +
        DateHandler.getOracleToDateFunction(userEnvLogBean.getTimeStamp()) +
        "," +
        SqlHandler.delimitString(userEnvLogBean.getDetail()) + ")";

    return sqlManager.update(conn, query);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//update
     public int update(UserEnvLogBean userEnvLogBean, TcmISDBModel dbModel)
      throws BaseException {
      return update(userEnvLogBean, dbModel.getConnection());
     }
     public int update(UserEnvLogBean userEnvLogBean, Connection conn)
      throws BaseException {
      String query  = "update " + TABLE + " set " +
       ATTRIBUTE_PERSONNEL_ID + "=" +
        StringHandler.nullIfZero(userEnvLogBean.getPersonnelId()) + "," +
       ATTRIBUTE_TIME_STAMP + "=" +
       DateHandler.getOracleToDateFunction(userEnvLogBean.getTimeStamp()) + "," +
       ATTRIBUTE_DETAIL + "=" +
        SqlHandler.delimitString(userEnvLogBean.getDetail()) + " " +
       "where " + ATTRIBUTE_PERSONNEL_ID + "=" +
        StringHandler.nullIfZero(userEnvLogBean.getPersonnelId());
      return new SqlManager().update(conn, query);
     }
   */

  //select
  public Collection select(SearchCriteria criteria, TcmISDBModel dbModel) throws
      BaseException {

    return select(criteria, dbModel.getConnection());
  }

  public Collection select(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection userEnvLogBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      UserEnvLogBean userEnvLogBean = new UserEnvLogBean();
      load(dataSetRow, userEnvLogBean);
      userEnvLogBeanColl.add(userEnvLogBean);
    }

    return userEnvLogBeanColl;
  }
}