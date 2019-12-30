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
import com.tcmis.internal.msds.beans.VvCustomerMsdsStatusBean;

/******************************************************************************
 * CLASSNAME: VvCustomerMsdsStatusBeanFactory <br>
 * @version: 1.0, May 23, 2005 <br>
 *****************************************************************************/

public class VvCustomerMsdsStatusBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_STATUS = "STATUS";
  public String ATTRIBUTE_DESCRIPTION = "DESCRIPTION";
  public String ATTRIBUTE_DISPLAY = "DISPLAY";

  //table name
  public String TABLE = "VV_CUSTOMER_MSDS_STATUS";

  //constructor
  public VvCustomerMsdsStatusBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("status")) {
      return ATTRIBUTE_STATUS;
    }
    else if (attributeName.equals("description")) {
      return ATTRIBUTE_DESCRIPTION;
    }
    else if (attributeName.equals("display")) {
      return ATTRIBUTE_DISPLAY;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, VvCustomerMsdsStatusBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(VvCustomerMsdsStatusBean vvCustomerMsdsStatusBean)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("status", "SearchCriterion.EQUALS",
     "" + vvCustomerMsdsStatusBean.getStatus());
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
   public int delete(VvCustomerMsdsStatusBean vvCustomerMsdsStatusBean, Connection conn)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("status", "SearchCriterion.EQUALS",
     "" + vvCustomerMsdsStatusBean.getStatus());
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
   public int insert(VvCustomerMsdsStatusBean vvCustomerMsdsStatusBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(vvCustomerMsdsStatusBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(VvCustomerMsdsStatusBean vvCustomerMsdsStatusBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_STATUS + "," +
     ATTRIBUTE_DESCRIPTION + "," +
     ATTRIBUTE_DISPLAY + ")" +
     " values (" +
     SqlHandler.delimitString(vvCustomerMsdsStatusBean.getStatus()) + "," +
       SqlHandler.delimitString(vvCustomerMsdsStatusBean.getDescription()) + "," +
     SqlHandler.delimitString(vvCustomerMsdsStatusBean.getDisplay()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(VvCustomerMsdsStatusBean vvCustomerMsdsStatusBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(vvCustomerMsdsStatusBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(VvCustomerMsdsStatusBean vvCustomerMsdsStatusBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_STATUS + "=" +
      SqlHandler.delimitString(vvCustomerMsdsStatusBean.getStatus()) + "," +
     ATTRIBUTE_DESCRIPTION + "=" +
       SqlHandler.delimitString(vvCustomerMsdsStatusBean.getDescription()) + "," +
     ATTRIBUTE_DISPLAY + "=" +
      SqlHandler.delimitString(vvCustomerMsdsStatusBean.getDisplay()) + " " +
     "where " + ATTRIBUTE_STATUS + "=" +
      vvCustomerMsdsStatusBean.getStatus();
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

    Collection vvCustomerMsdsStatusBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      VvCustomerMsdsStatusBean vvCustomerMsdsStatusBean = new
          VvCustomerMsdsStatusBean();
      load(dataSetRow, vvCustomerMsdsStatusBean);
      vvCustomerMsdsStatusBeanColl.add(vvCustomerMsdsStatusBean);
    }

    return vvCustomerMsdsStatusBeanColl;
  }
}