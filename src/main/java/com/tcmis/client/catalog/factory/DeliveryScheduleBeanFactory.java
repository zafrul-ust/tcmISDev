package com.tcmis.client.catalog.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.Connection;

import java.text.ParseException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.client.catalog.beans.DeliveryScheduleBean;

/******************************************************************************
 * CLASSNAME: DeliveryScheduleBeanFactory <br>
 * @version: 1.0, Jun 21, 2007 <br>
 *****************************************************************************/

public class DeliveryScheduleBeanFactory extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_DATE_TO_DELIVER = "DATE_TO_DELIVER";
  public String ATTRIBUTE_QTY = "QTY";
  public String ATTRIBUTE_QTY_OPEN = "QTY_OPEN";

  //table name
  public String TABLE = "DELIVERY_SCHEDULE";

  //constructor
  public DeliveryScheduleBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    } else if (attributeName.equals("prNumber")) {
      return ATTRIBUTE_PR_NUMBER;
    } else if (attributeName.equals("lineItem")) {
      return ATTRIBUTE_LINE_ITEM;
    } else if (attributeName.equals("dateToDeliver")) {
      return ATTRIBUTE_DATE_TO_DELIVER;
    } else if (attributeName.equals("qty")) {
      return ATTRIBUTE_QTY;
    } else if (attributeName.equals("qtyOpen")) {
      return ATTRIBUTE_QTY_OPEN;
    } else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, DeliveryScheduleBean.class);
  }

  public int delete(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = delete(criteria, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int delete(SearchCriteria criteria, Connection conn) throws BaseException {

    String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

    return new SqlManager().update(conn, sqlQuery);
  }

  //insert
  public int insert(DeliveryScheduleBean deliveryScheduleBean) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(deliveryScheduleBean, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(DeliveryScheduleBean deliveryScheduleBean, Connection conn) throws BaseException {

    SqlManager sqlManager = new SqlManager();

    String query = "insert into " + TABLE + " (" + ATTRIBUTE_COMPANY_ID + "," + ATTRIBUTE_PR_NUMBER + "," + ATTRIBUTE_LINE_ITEM + "," + ATTRIBUTE_DATE_TO_DELIVER + "," + ATTRIBUTE_QTY + "," + ATTRIBUTE_QTY_OPEN + ")" + " values (" +
        SqlHandler.delimitString(deliveryScheduleBean.getCompanyId()) + "," + deliveryScheduleBean.getPrNumber() + "," + SqlHandler.delimitString(deliveryScheduleBean.getLineItem()) + "," +
        DateHandler.getOracleToDateFunction(deliveryScheduleBean.getDateToDeliver()) + "," + deliveryScheduleBean.getQty() + "," + deliveryScheduleBean.getQtyOpen() + ")";

    return sqlManager.update(conn, query);
  }

  //update
  public int update(DeliveryScheduleBean deliveryScheduleBean) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = update(deliveryScheduleBean, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int update(DeliveryScheduleBean deliveryScheduleBean, Connection conn) throws BaseException {

    String query = "update " + TABLE + " set " + ATTRIBUTE_COMPANY_ID + "=" + SqlHandler.delimitString(deliveryScheduleBean.getCompanyId()) +
                   "," + ATTRIBUTE_PR_NUMBER + "=" + StringHandler.nullIfZero(deliveryScheduleBean.getPrNumber()) + "," +
                   ATTRIBUTE_LINE_ITEM + "=" + SqlHandler.delimitString(deliveryScheduleBean.getLineItem()) + "," +
                   ATTRIBUTE_DATE_TO_DELIVER + "=" + DateHandler.getOracleToDateFunction(deliveryScheduleBean.getDateToDeliver()) + "," +
                   ATTRIBUTE_QTY + "=" + StringHandler.nullIfZero(deliveryScheduleBean.getQty()) + ","
                   + ATTRIBUTE_QTY_OPEN + "=" + StringHandler.nullIfZero(deliveryScheduleBean.getQtyOpen()) + " " +
                   "where " + ATTRIBUTE_COMPANY_ID + "=" +deliveryScheduleBean.getCompanyId();
    return new SqlManager().update(conn, query);
  }

  public int updateQuantity(SearchCriteria criteria,BigDecimal qty) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = updateQuantity(criteria, connection, qty);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int updateQuantity(SearchCriteria criteria, Connection conn, BigDecimal qty) throws BaseException {

    String sqlQuery = " update " + TABLE + " set qty = "+qty+" " + getWhereClause(criteria);

    return new SqlManager().update(conn, sqlQuery);
  }


  //select
  public Collection select(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(criteria, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(SearchCriteria criteria, Connection conn) throws BaseException {

    Collection deliveryScheduleBeanColl = new Vector();

    String query = "select * from " + TABLE + " " + getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      DeliveryScheduleBean deliveryScheduleBean = new DeliveryScheduleBean();
      load(dataSetRow, deliveryScheduleBean);
      deliveryScheduleBeanColl.add(deliveryScheduleBean);
    }

    return deliveryScheduleBeanColl;
  }
}