package com.tcmis.internal.hub.factory;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.*;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.*;

/******************************************************************************
 * CLASSNAME: CabLastScannedViewBeanFactory <br>
 * @version: 1.0, Jan 21, 2005 <br>
 *****************************************************************************/

public class CabLastScannedViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_APPLICATION = "APPLICATION";
  public String ATTRIBUTE_LAST_SCANNED = "LAST_SCANNED";

  //table name
  public String TABLE = "CAB_LAST_SCANNED_VIEW";

  //constructor
  public CabLastScannedViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("application")) {
      return ATTRIBUTE_APPLICATION;
    }
    else if (attributeName.equals("lastScanned")) {
      return ATTRIBUTE_LAST_SCANNED;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, CabLastScannedViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(CabLastScannedViewBean cabLastScannedViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "=",
     "" + cabLastScannedViewBean.getCompanyId());
    Connection connection = this.getDbManager().getConnection();
    int result = this.delete(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return result;
   }
   public int delete(CabLastScannedViewBean cabLastScannedViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("companyId", "=",
     "" + cabLastScannedViewBean.getCompanyId());
    return delete(criteria, conn);
   }
   */

  public int delete(SearchCriteria criteria) throws BaseException {

    Connection connection = getDbManager().getConnection();
    int result = delete(criteria, connection);
    this.getDbManager().returnConnection(connection);
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
   public int insert(CabLastScannedViewBean cabLastScannedViewBean)
    throws BaseException {
    Connection connection = getDbManager().getConnection();
    int result = insert(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return result;
   }
   public int insert(CabLastScannedViewBean cabLastScannedViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_APPLICATION + "," +
     ATTRIBUTE_LAST_SCANNED + ")" +
     SqlHandler.delimitString(cabLastScannedViewBean.getCompanyId()) + "," +
     SqlHandler.delimitString(cabLastScannedViewBean.getApplication()) + "," +
     DateHandler.getOracleToDateFunction(cabLastScannedViewBean.getLastScanned()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(CabLastScannedViewBean cabLastScannedViewBean)
    throws BaseException {
    Connection connection = getDbManager().getConnection();
    int result = update(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return result;
   }
   public int update(CabLastScannedViewBean cabLastScannedViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(cabLastScannedViewBean.getCompanyId()) + "," +
     ATTRIBUTE_APPLICATION + "=" +
      SqlHandler.delimitString(cabLastScannedViewBean.getApplication()) + "," +
     ATTRIBUTE_LAST_SCANNED + "=" +
      DateHandler.getOracleToDateFunction(cabLastScannedViewBean.getLastScanned()) + " " +
     "where " + ATTRIBUTE_COMPANY_ID + "=" +
      StringHandler.nullIfZero(cabLastScannedViewBean.getCompanyId());
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(SearchCriteria criteria) throws BaseException {

    Connection connection = this.getDbManager().getConnection();
    Collection c = select(criteria, connection);
    this.getDbManager().returnConnection(connection);
    return c;
  }

  public Collection select(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection cabLastScannedViewBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CabLastScannedViewBean cabLastScannedViewBean = new
          CabLastScannedViewBean();
      load(dataSetRow, cabLastScannedViewBean);
      cabLastScannedViewBeanColl.add(cabLastScannedViewBean);
    }

    return cabLastScannedViewBeanColl;
  }
}