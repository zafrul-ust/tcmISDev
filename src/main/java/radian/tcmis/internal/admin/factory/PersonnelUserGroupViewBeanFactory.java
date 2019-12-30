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
import radian.tcmis.internal.admin.beans.PersonnelUserGroupViewBean;

/******************************************************************************
 * CLASSNAME: PersonnelUserGroupViewBeanFactory <br>
 * @version: 1.0, Aug 13, 2004 <br>
 *****************************************************************************/

public class PersonnelUserGroupViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_USER_GROUP_ID = "USER_GROUP_ID";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";

  //table name
  public String TABLE = "PERSONNEL_USER_GROUP_VIEW";

  //constructor
  public PersonnelUserGroupViewBeanFactory(String client) {
    super(client);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("personnelId")) {
      return ATTRIBUTE_PERSONNEL_ID;
    }
    else if (attributeName.equals("userGroupId")) {
      return ATTRIBUTE_USER_GROUP_ID;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, PersonnelUserGroupViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(PersonnelUserGroupViewBean personnelUserGroupViewBean, TcmISDBModel dbModel)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("personnelId", "=",
     "" + personnelUserGroupViewBean.getPersonnelId());
    return delete(criteria, dbModel.getConnection());
   }
   public int delete(PersonnelUserGroupViewBean personnelUserGroupViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("personnelId", "=",
     "" + personnelUserGroupViewBean.getPersonnelId());
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
   public int insert(PersonnelUserGroupViewBean personnelUserGroupViewBean, TcmISDBModel dbModel)
    throws BaseException {
    return insert(personnelUserGroupViewBean, dbModel.getConnection());
   }
   public int insert(PersonnelUserGroupViewBean personnelUserGroupViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_PERSONNEL_ID + "," +
     ATTRIBUTE_USER_GROUP_ID + "," +
     ATTRIBUTE_FACILITY_ID + ")" +
       StringHandler.nullIfZero(personnelUserGroupViewBean.getPersonnelId()) + "," +
       SqlHandler.delimitString(personnelUserGroupViewBean.getUserGroupId()) + "," +
       SqlHandler.delimitString(personnelUserGroupViewBean.getFacilityId()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(PersonnelUserGroupViewBean personnelUserGroupViewBean, TcmISDBModel dbModel)
    throws BaseException {
    return update(personnelUserGroupViewBean, dbModel.getConnection());
   }
   public int update(PersonnelUserGroupViewBean personnelUserGroupViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_PERSONNEL_ID + "=" +
       StringHandler.nullIfZero(personnelUserGroupViewBean.getPersonnelId()) + "," +
     ATTRIBUTE_USER_GROUP_ID + "=" +
       SqlHandler.delimitString(personnelUserGroupViewBean.getUserGroupId()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
       SqlHandler.delimitString(personnelUserGroupViewBean.getFacilityId()) + " " +
     "where " + ATTRIBUTE_PERSONNEL_ID + "=" +
      StringHandler.nullIfZero(personnelUserGroupViewBean.getPersonnelId());
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

    Collection personnelUserGroupViewBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PersonnelUserGroupViewBean personnelUserGroupViewBean = new
          PersonnelUserGroupViewBean();
      load(dataSetRow, personnelUserGroupViewBean);
      personnelUserGroupViewBeanColl.add(personnelUserGroupViewBean);
    }

    return personnelUserGroupViewBeanColl;
  }

  public Collection selectDistinctUserGroupId(SearchCriteria criteria,
                                              TcmISDBModel dbModel) throws
      BaseException {

    Collection personnelUserGroupViewBeanColl = new Vector();

    String query = "select distinct " + ATTRIBUTE_USER_GROUP_ID + " from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(dbModel.getConnection(), query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PersonnelUserGroupViewBean personnelUserGroupViewBean = new
          PersonnelUserGroupViewBean();
      load(dataSetRow, personnelUserGroupViewBean);
      personnelUserGroupViewBeanColl.add(personnelUserGroupViewBean);
    }

    return personnelUserGroupViewBeanColl;
  }

}