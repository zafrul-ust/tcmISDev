package com.tcmis.internal.msds.factory;

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
import com.tcmis.internal.msds.beans.CurrentMsdsLoadViewBean;

/******************************************************************************
 * CLASSNAME: CurrentMsdsLoadViewBeanFactory <br>
 * @version: 1.0, May 23, 2005 <br>
 *****************************************************************************/

public class CurrentMsdsLoadViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_CUSTOMER_MSDS_NO = "CUSTOMER_MSDS_NO";
  public String ATTRIBUTE_TRADE_NAME = "TRADE_NAME";
  public String ATTRIBUTE_SEARCH = "SEARCH";
  public String ATTRIBUTE_STATUS = "STATUS";
  public String ATTRIBUTE_MANUFACTURER_NAME = "MANUFACTURER_NAME";
  public String ATTRIBUTE_REVISION_DATE = "REVISION_DATE";
  public String ATTRIBUTE_CONTENT = "CONTENT";
  public String ATTRIBUTE_ON_LINE = "ON_LINE";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_BUILDING = "BUILDING";
  public String ATTRIBUTE_FLOOR = "FLOOR";
  public String ATTRIBUTE_DEPARTMENT = "DEPARTMENT";

  //table name
  public String TABLE = "CURRENT_MSDS_LOAD_VIEW";

  //constructor
  public CurrentMsdsLoadViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("customerMsdsNo")) {
      return ATTRIBUTE_CUSTOMER_MSDS_NO;
    }
    else if (attributeName.equals("tradeName")) {
      return ATTRIBUTE_TRADE_NAME;
    }
    else if (attributeName.equals("search")) {
      return ATTRIBUTE_SEARCH;
    }
    else if (attributeName.equals("status")) {
      return ATTRIBUTE_STATUS;
    }
    else if (attributeName.equals("manufacturerName")) {
      return ATTRIBUTE_MANUFACTURER_NAME;
    }
    else if (attributeName.equals("revisionDate")) {
      return ATTRIBUTE_REVISION_DATE;
    }
    else if (attributeName.equals("content")) {
      return ATTRIBUTE_CONTENT;
    }
    else if (attributeName.equals("onLine")) {
      return ATTRIBUTE_ON_LINE;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("building")) {
      return ATTRIBUTE_BUILDING;
    }
    else if (attributeName.equals("floor")) {
      return ATTRIBUTE_FLOOR;
    }
    else if (attributeName.equals("department")) {
      return ATTRIBUTE_DEPARTMENT;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, CurrentMsdsLoadViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(CurrentMsdsLoadViewBean currentMsdsLoadViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("customerMsdsNo", "SearchCriterion.EQUALS",
     "" + currentMsdsLoadViewBean.getCustomerMsdsNo());
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
   public int delete(CurrentMsdsLoadViewBean currentMsdsLoadViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("customerMsdsNo", "SearchCriterion.EQUALS",
     "" + currentMsdsLoadViewBean.getCustomerMsdsNo());
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
   public int insert(CurrentMsdsLoadViewBean currentMsdsLoadViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(currentMsdsLoadViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(CurrentMsdsLoadViewBean currentMsdsLoadViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_CUSTOMER_MSDS_NO + "," +
     ATTRIBUTE_TRADE_NAME + "," +
     ATTRIBUTE_SEARCH + "," +
     ATTRIBUTE_STATUS + "," +
     ATTRIBUTE_MANUFACTURER_NAME + "," +
     ATTRIBUTE_REVISION_DATE + "," +
     ATTRIBUTE_CONTENT + "," +
     ATTRIBUTE_ON_LINE + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_BUILDING + "," +
     ATTRIBUTE_FLOOR + "," +
     ATTRIBUTE_DEPARTMENT + ")" +
     " values (" +
       SqlHandler.delimitString(currentMsdsLoadViewBean.getCustomerMsdsNo()) + "," +
     SqlHandler.delimitString(currentMsdsLoadViewBean.getTradeName()) + "," +
     SqlHandler.delimitString(currentMsdsLoadViewBean.getSearch()) + "," +
     SqlHandler.delimitString(currentMsdsLoadViewBean.getStatus()) + "," +
       SqlHandler.delimitString(currentMsdsLoadViewBean.getManufacturerName()) + "," +
     DateHandler.getOracleToDateFunction(currentMsdsLoadViewBean.getRevisionDate()) + "," +
     SqlHandler.delimitString(currentMsdsLoadViewBean.getContent()) + "," +
     SqlHandler.delimitString(currentMsdsLoadViewBean.getOnLine()) + "," +
     SqlHandler.delimitString(currentMsdsLoadViewBean.getFacilityId()) + "," +
     SqlHandler.delimitString(currentMsdsLoadViewBean.getBuilding()) + "," +
     SqlHandler.delimitString(currentMsdsLoadViewBean.getFloor()) + "," +
     SqlHandler.delimitString(currentMsdsLoadViewBean.getDepartment()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(CurrentMsdsLoadViewBean currentMsdsLoadViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(currentMsdsLoadViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(CurrentMsdsLoadViewBean currentMsdsLoadViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_CUSTOMER_MSDS_NO + "=" +
       SqlHandler.delimitString(currentMsdsLoadViewBean.getCustomerMsdsNo()) + "," +
     ATTRIBUTE_TRADE_NAME + "=" +
      SqlHandler.delimitString(currentMsdsLoadViewBean.getTradeName()) + "," +
     ATTRIBUTE_SEARCH + "=" +
      SqlHandler.delimitString(currentMsdsLoadViewBean.getSearch()) + "," +
     ATTRIBUTE_STATUS + "=" +
      SqlHandler.delimitString(currentMsdsLoadViewBean.getStatus()) + "," +
     ATTRIBUTE_MANUFACTURER_NAME + "=" +
       SqlHandler.delimitString(currentMsdsLoadViewBean.getManufacturerName()) + "," +
     ATTRIBUTE_REVISION_DATE + "=" +
      DateHandler.getOracleToDateFunction(currentMsdsLoadViewBean.getRevisionDate()) + "," +
     ATTRIBUTE_CONTENT + "=" +
      SqlHandler.delimitString(currentMsdsLoadViewBean.getContent()) + "," +
     ATTRIBUTE_ON_LINE + "=" +
      SqlHandler.delimitString(currentMsdsLoadViewBean.getOnLine()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(currentMsdsLoadViewBean.getFacilityId()) + "," +
     ATTRIBUTE_BUILDING + "=" +
      SqlHandler.delimitString(currentMsdsLoadViewBean.getBuilding()) + "," +
     ATTRIBUTE_FLOOR + "=" +
      SqlHandler.delimitString(currentMsdsLoadViewBean.getFloor()) + "," +
     ATTRIBUTE_DEPARTMENT + "=" +
      SqlHandler.delimitString(currentMsdsLoadViewBean.getDepartment()) + " " +
     "where " + ATTRIBUTE_CUSTOMER_MSDS_NO + "=" +
      currentMsdsLoadViewBean.getCustomerMsdsNo();
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection currentMsdsLoadViewBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CurrentMsdsLoadViewBean currentMsdsLoadViewBean = new
          CurrentMsdsLoadViewBean();
      load(dataSetRow, currentMsdsLoadViewBean);
      currentMsdsLoadViewBeanColl.add(currentMsdsLoadViewBean);
    }
    return currentMsdsLoadViewBeanColl;
  }
}