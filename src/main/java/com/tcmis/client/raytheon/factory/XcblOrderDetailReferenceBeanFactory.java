package com.tcmis.client.raytheon.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.raytheon.beans.XcblOrderDetailReferenceBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SqlHandler;

/******************************************************************************
 * CLASSNAME: XcblOrderDetailReferenceBeanFactory <br>
 * @version: 1.0, Jul 20, 2005 <br>
 *****************************************************************************/

public class XcblOrderDetailReferenceBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_ID = "ID";
  public String ATTRIBUTE_XCBL_ORDER_DETAIL_ID = "XCBL_ORDER_DETAIL_ID";
  public String ATTRIBUTE_KEY = "KEY";
  public String ATTRIBUTE_VALUE = "VALUE";

  //table name
  public String TABLE = "XCBL_ORDER_DETAIL_REFERENCE";

  //constructor
  public XcblOrderDetailReferenceBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("id")) {
      return ATTRIBUTE_ID;
    }
    else if (attributeName.equals("xcblOrderDetailId")) {
      return ATTRIBUTE_XCBL_ORDER_DETAIL_ID;
    }
    else if (attributeName.equals("key")) {
      return ATTRIBUTE_KEY;
    }
    else if (attributeName.equals("value")) {
      return ATTRIBUTE_VALUE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, XcblOrderDetailReferenceBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(XcblOrderDetailReferenceBean xcblOrderDetailReferenceBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("id", "SearchCriterion.EQUALS",
     "" + xcblOrderDetailReferenceBean.getId());
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
   public int delete(XcblOrderDetailReferenceBean xcblOrderDetailReferenceBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("id", "SearchCriterion.EQUALS",
     "" + xcblOrderDetailReferenceBean.getId());
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

  public int delete(SearchCriteria criteria, Connection conn) throws BaseException {
    String sqlQuery = " delete from " + TABLE + " " +
        getWhereClause(criteria);
    return new SqlManager().update(conn, sqlQuery);
  }

//insert
  public int insert(XcblOrderDetailReferenceBean xcblOrderDetailReferenceBean) throws
      BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(xcblOrderDetailReferenceBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(XcblOrderDetailReferenceBean xcblOrderDetailReferenceBean,
                    Connection conn) throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_ID + "," +
        ATTRIBUTE_XCBL_ORDER_DETAIL_ID + "," +
        ATTRIBUTE_KEY + "," +
        ATTRIBUTE_VALUE + ")" +
        " values (" +
        xcblOrderDetailReferenceBean.getId() + "," +
        xcblOrderDetailReferenceBean.getXcblOrderDetailId() + "," +
        SqlHandler.delimitString(xcblOrderDetailReferenceBean.getKey()) + "," +
        SqlHandler.delimitString(xcblOrderDetailReferenceBean.getValue()) + ")";
    return sqlManager.update(conn, query);
  }

  /*
//update
     public int update(XcblOrderDetailReferenceBean xcblOrderDetailReferenceBean)
      throws BaseException {
      Connection connection = null;
      int result = 0;
      try {
       connection = getDbManager().getConnection();
       result = update(xcblOrderDetailReferenceBean, connection);
      }
      finally {
       this.getDbManager().returnConnection(connection);
      }
      return result;
     }
     public int update(XcblOrderDetailReferenceBean xcblOrderDetailReferenceBean, Connection conn)
      throws BaseException {
      String query  = "update " + TABLE + " set " +
       ATTRIBUTE_ID + "=" +
        StringHandler.nullIfZero(xcblOrderDetailReferenceBean.getId()) + "," +
       ATTRIBUTE_XCBL_ORDER_DETAIL_ID + "=" +
       StringHandler.nullIfZero(xcblOrderDetailReferenceBean.getXcblOrderDetailId()) + "," +
       ATTRIBUTE_KEY + "=" +
        SqlHandler.delimitString(xcblOrderDetailReferenceBean.getKey()) + "," +
       ATTRIBUTE_VALUE + "=" +
        SqlHandler.delimitString(xcblOrderDetailReferenceBean.getValue()) + " " +
       "where " + ATTRIBUTE_ID + "=" +
        xcblOrderDetailReferenceBean.getId();
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

  public Collection select(SearchCriteria criteria, Connection conn) throws BaseException {
    Collection xcblOrderDetailReferenceBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      XcblOrderDetailReferenceBean xcblOrderDetailReferenceBean = new
          XcblOrderDetailReferenceBean();
      load(dataSetRow, xcblOrderDetailReferenceBean);
      xcblOrderDetailReferenceBeanColl.add(xcblOrderDetailReferenceBean);
    }
    return xcblOrderDetailReferenceBeanColl;
  }
}