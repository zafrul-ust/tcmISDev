package com.tcmis.client.raytheon.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.raytheon.beans.XcblOrderDetailScheduleBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SqlHandler;

/******************************************************************************
 * CLASSNAME: XcblOrderDetailScheduleBeanFactory <br>
 * @version: 1.0, Jul 13, 2005 <br>
 *****************************************************************************/

public class XcblOrderDetailScheduleBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_RAYTHEON_XCBL_ORDER_DETAIL_ID = "RAYTHEON_XCBL_ORDER_DETAIL_ID";
  public String ATTRIBUTE_ID = "ID";
  public String ATTRIBUTE_SCHEDULE_LINE_ID = "SCHEDULE_LINE_ID";
  public String ATTRIBUTE_QUANTITY = "QUANTITY";
  public String ATTRIBUTE_UOM = "UOM";
  public String ATTRIBUTE_REQUESTED_DELIVERY_DATE = "REQUESTED_DELIVERY_DATE";

  //table name
  public String TABLE = "XCBL_ORDER_DETAIL_SCHEDULE";

  //constructor
  public XcblOrderDetailScheduleBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("raytheonXcblOrderDetailId")) {
      return ATTRIBUTE_RAYTHEON_XCBL_ORDER_DETAIL_ID;
    }
    else if (attributeName.equals("id")) {
      return ATTRIBUTE_ID;
    }
    else if (attributeName.equals("scheduleLineId")) {
      return ATTRIBUTE_SCHEDULE_LINE_ID;
    }
    else if (attributeName.equals("quantity")) {
      return ATTRIBUTE_QUANTITY;
    }
    else if (attributeName.equals("uom")) {
      return ATTRIBUTE_UOM;
    }
    else if (attributeName.equals("requestedDeliveryDate")) {
      return ATTRIBUTE_REQUESTED_DELIVERY_DATE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, XcblOrderDetailScheduleBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(XcblOrderDetailScheduleBean xcblOrderDetailScheduleBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("raytheonXcblOrderDetailId", "SearchCriterion.EQUALS",
     "" + xcblOrderDetailScheduleBean.getRaytheonXcblOrderDetailId());
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
   public int delete(XcblOrderDetailScheduleBean xcblOrderDetailScheduleBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("raytheonXcblOrderDetailId", "SearchCriterion.EQUALS",
     "" + xcblOrderDetailScheduleBean.getRaytheonXcblOrderDetailId());
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
  public int insert(XcblOrderDetailScheduleBean xcblOrderDetailScheduleBean) throws
      BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(xcblOrderDetailScheduleBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(XcblOrderDetailScheduleBean xcblOrderDetailScheduleBean,
                    Connection conn) throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_RAYTHEON_XCBL_ORDER_DETAIL_ID + "," +
        ATTRIBUTE_ID + "," +
        ATTRIBUTE_SCHEDULE_LINE_ID + "," +
        ATTRIBUTE_QUANTITY + "," +
        ATTRIBUTE_UOM + "," +
        ATTRIBUTE_REQUESTED_DELIVERY_DATE + ")" +
        " values (" +
        xcblOrderDetailScheduleBean.getXcblOrderDetailId() + "," +
        xcblOrderDetailScheduleBean.getId() + "," +
        xcblOrderDetailScheduleBean.getScheduleLineId() + "," +
        xcblOrderDetailScheduleBean.getQuantity() + "," +
        SqlHandler.delimitString(xcblOrderDetailScheduleBean.getUom()) + "," +
        DateHandler.getOracleToDateFunction(xcblOrderDetailScheduleBean.
                                            getRequestedDeliveryDate()) + ")";
    return sqlManager.update(conn, query);
  }

  /*
//update
     public int update(XcblOrderDetailScheduleBean xcblOrderDetailScheduleBean)
      throws BaseException {
      Connection connection = null;
      int result = 0;
      try {
       connection = getDbManager().getConnection();
       result = update(xcblOrderDetailScheduleBean, connection);
      }
      finally {
       this.getDbManager().returnConnection(connection);
      }
      return result;
     }
     public int update(XcblOrderDetailScheduleBean xcblOrderDetailScheduleBean, Connection conn)
      throws BaseException {
      String query  = "update " + TABLE + " set " +
       ATTRIBUTE_RAYTHEON_XCBL_ORDER_DETAIL_ID + "=" +
        StringHandler.nullIfZero(xcblOrderDetailScheduleBean.getRaytheonXcblOrderDetailId()) + "," +
       ATTRIBUTE_ID + "=" +
        StringHandler.nullIfZero(xcblOrderDetailScheduleBean.getId()) + "," +
       ATTRIBUTE_SCHEDULE_LINE_ID + "=" +
        StringHandler.nullIfZero(xcblOrderDetailScheduleBean.getScheduleLineId()) + "," +
       ATTRIBUTE_QUANTITY + "=" +
        StringHandler.nullIfZero(xcblOrderDetailScheduleBean.getQuantity()) + "," +
       ATTRIBUTE_UOM + "=" +
        SqlHandler.delimitString(xcblOrderDetailScheduleBean.getUom()) + "," +
       ATTRIBUTE_REQUESTED_DELIVERY_DATE + "=" +
        DateHandler.getOracleToDateFunction(xcblOrderDetailScheduleBean.getRequestedDeliveryDate()) + " " +
       "where " + ATTRIBUTE_RAYTHEON_XCBL_ORDER_DETAIL_ID + "=" +
        xcblOrderDetailScheduleBean.getRaytheonXcblOrderDetailId();
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
    Collection xcblOrderDetailScheduleBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      XcblOrderDetailScheduleBean xcblOrderDetailScheduleBean = new
          XcblOrderDetailScheduleBean();
      load(dataSetRow, xcblOrderDetailScheduleBean);
      xcblOrderDetailScheduleBeanColl.add(xcblOrderDetailScheduleBean);
    }
    return xcblOrderDetailScheduleBeanColl;
  }
}