package radian.tcmis.internal.admin.factory;

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
import radian.tcmis.internal.admin.beans.UserGroupMemberIgBean;

/******************************************************************************
 * CLASSNAME: UserGroupMemberIgBeanFactory <br>
 * @version: 1.0, Sep 16, 2004 <br>
 *****************************************************************************/

public class UserGroupMemberIgBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_USER_GROUP_ID = "USER_GROUP_ID";
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";

  //table name
  public String TABLE = "USER_GROUP_MEMBER_IG";

  //constructor
  public UserGroupMemberIgBeanFactory(String client) {
    super(client);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("userGroupId")) {
      return ATTRIBUTE_USER_GROUP_ID;
    }
    else if (attributeName.equals("personnelId")) {
      return ATTRIBUTE_PERSONNEL_ID;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("inventoryGroup")) {
      return ATTRIBUTE_INVENTORY_GROUP;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, UserGroupMemberIgBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(UserGroupMemberIgBean userGroupMemberIgBean, TcmISDBModel dbModel)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("userGroupId", "=",
     "" + userGroupMemberIgBean.getUserGroupId());
    return delete(criteria, dbModel.getConnection());
   }
       public int delete(UserGroupMemberIgBean userGroupMemberIgBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("userGroupId", "=",
     "" + userGroupMemberIgBean.getUserGroupId());
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

//you need to verify the primary key(s) before uncommenting this
  /*
//insert
   public int insert(UserGroupMemberIgBean userGroupMemberIgBean, TcmISDBModel dbModel)
    throws BaseException {
    return insert(userGroupMemberIgBean, dbModel.getConnection());
   }
       public int insert(UserGroupMemberIgBean userGroupMemberIgBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_USER_GROUP_ID + "," +
     ATTRIBUTE_PERSONNEL_ID + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_INVENTORY_GROUP + ")" +
     SqlHandler.delimitString(userGroupMemberIgBean.getUserGroupId()) + "," +
     StringHandler.nullIfZero(userGroupMemberIgBean.getPersonnelId()) + "," +
     SqlHandler.delimitString(userGroupMemberIgBean.getFacilityId()) + "," +
     SqlHandler.delimitString(userGroupMemberIgBean.getCompanyId()) + "," +
     SqlHandler.delimitString(userGroupMemberIgBean.getInventoryGroup()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(UserGroupMemberIgBean userGroupMemberIgBean, TcmISDBModel dbModel)
    throws BaseException {
    return update(userGroupMemberIgBean, dbModel.getConnection());
   }
       public int update(UserGroupMemberIgBean userGroupMemberIgBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_USER_GROUP_ID + "=" +
      SqlHandler.delimitString(userGroupMemberIgBean.getUserGroupId()) + "," +
     ATTRIBUTE_PERSONNEL_ID + "=" +
      StringHandler.nullIfZero(userGroupMemberIgBean.getPersonnelId()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(userGroupMemberIgBean.getFacilityId()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(userGroupMemberIgBean.getCompanyId()) + "," +
     ATTRIBUTE_INVENTORY_GROUP + "=" +
       SqlHandler.delimitString(userGroupMemberIgBean.getInventoryGroup()) + " " +
     "where " + ATTRIBUTE_USER_GROUP_ID + "=" +
      StringHandler.nullIfZero(userGroupMemberIgBean.getUserGroupId());
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

    Collection userGroupMemberIgBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      UserGroupMemberIgBean userGroupMemberIgBean = new UserGroupMemberIgBean();
      load(dataSetRow, userGroupMemberIgBean);
      userGroupMemberIgBeanColl.add(userGroupMemberIgBean);
    }

    return userGroupMemberIgBeanColl;
  }

  public Collection selectDistinctIg(SearchCriteria criteria,
                                     TcmISDBModel dbModel) throws
      BaseException {

    Collection userGroupMemberIgBeanColl = new Vector();

    String query = "select distinct " + ATTRIBUTE_INVENTORY_GROUP + " from " +
        TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(dbModel.getConnection(), query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      UserGroupMemberIgBean userGroupMemberIgBean = new
          UserGroupMemberIgBean();
      load(dataSetRow, userGroupMemberIgBean);
      userGroupMemberIgBeanColl.add(userGroupMemberIgBean);
    }

    return userGroupMemberIgBeanColl;
  }
}