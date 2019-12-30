package com.tcmis.client.cxml.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.Connection;

import java.text.ParseException;

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
import com.tcmis.client.cxml.beans.OrderRequestBean;

/******************************************************************************
 * CLASSNAME: CxmlOrderRequestBeanFactory <br>
 * @version: 1.0, Jun 9, 2006 <br>
 *****************************************************************************/

public class CxmlOrderRequestBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PAYLOAD_ID = "PAYLOAD_ID";
  public String ATTRIBUTE_ORDER_ID = "ORDER_ID";
  public String ATTRIBUTE_ORDER_DATE = "ORDER_DATE";
  public String ATTRIBUTE_ORDER_TYPE = "ORDER_TYPE";
  public String ATTRIBUTE_REQUISITION_ID = "REQUISITION_ID";
  public String ATTRIBUTE_SHIP_COMPLETE = "SHIP_COMPLETE";
  public String ATTRIBUTE_CURRENCY = "CURRENCY";
  public String ATTRIBUTE_TOTAL_AMOUNT = "TOTAL_AMOUNT";

  //table name
  public String TABLE = "CXML_ORDER_REQUEST";

  //constructor
  public CxmlOrderRequestBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("payloadId")) {
      return ATTRIBUTE_PAYLOAD_ID;
    }
    else if (attributeName.equals("orderId")) {
      return ATTRIBUTE_ORDER_ID;
    }
    else if (attributeName.equals("orderDate")) {
      return ATTRIBUTE_ORDER_DATE;
    }
    else if (attributeName.equals("orderType")) {
      return ATTRIBUTE_ORDER_TYPE;
    }
    else if (attributeName.equals("requisitionId")) {
      return ATTRIBUTE_REQUISITION_ID;
    }
    else if (attributeName.equals("shipComplete")) {
      return ATTRIBUTE_SHIP_COMPLETE;
    }
    else if (attributeName.equals("currency")) {
      return ATTRIBUTE_CURRENCY;
    }
    else if (attributeName.equals("totalAmount")) {
      return ATTRIBUTE_TOTAL_AMOUNT;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, OrderRequestBean.class);
  }


//delete
   public int delete(OrderRequestBean cxmlOrderRequestBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("payloadId", "SearchCriterion.EQUALS",
     "" + cxmlOrderRequestBean.getPayloadId());
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

   public int delete(OrderRequestBean cxmlOrderRequestBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("payloadId", "SearchCriterion.EQUALS",
     "" + cxmlOrderRequestBean.getPayloadId());
    return delete(criteria, conn);
   }


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


//insert
   public int insert(OrderRequestBean cxmlOrderRequestBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(cxmlOrderRequestBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }

   public int insert(OrderRequestBean cxmlOrderRequestBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_PAYLOAD_ID + "," +
     ATTRIBUTE_ORDER_ID + "," +
     ATTRIBUTE_ORDER_DATE + "," +
     ATTRIBUTE_ORDER_TYPE + "," +
     ATTRIBUTE_REQUISITION_ID + "," +
     ATTRIBUTE_SHIP_COMPLETE + "," +
     ATTRIBUTE_CURRENCY + "," +
     ATTRIBUTE_TOTAL_AMOUNT + ")" +
     " values (" +
     SqlHandler.delimitString(cxmlOrderRequestBean.getPayloadId()) + "," +
     SqlHandler.delimitString(cxmlOrderRequestBean.getOrderId()) + "," +
     SqlHandler.delimitString(cxmlOrderRequestBean.getOrderDate()) + "," +
     SqlHandler.delimitString(cxmlOrderRequestBean.getOrderType()) + "," +
// Larry Note: I don't see these are been used     
//     SqlHandler.delimitString(cxmlOrderRequestBean.getRequisitionId()) + "," +
//     SqlHandler.delimitString(cxmlOrderRequestBean.getShipComplete()) + "," +
     SqlHandler.delimitString(cxmlOrderRequestBean.getCurrency()) + "," +
     SqlHandler.delimitString(cxmlOrderRequestBean.getTotalAmount()) + ")";
    return sqlManager.update(conn, query);
   }

//update
   public int update(OrderRequestBean cxmlOrderRequestBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(cxmlOrderRequestBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }

       public int update(OrderRequestBean cxmlOrderRequestBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_PAYLOAD_ID + "=" +
      SqlHandler.delimitString(cxmlOrderRequestBean.getPayloadId()) + "," +
     ATTRIBUTE_ORDER_ID + "=" +
      SqlHandler.delimitString(cxmlOrderRequestBean.getOrderId()) + "," +
     ATTRIBUTE_ORDER_DATE + "=" +
      SqlHandler.delimitString(cxmlOrderRequestBean.getOrderDate()) + "," +
     ATTRIBUTE_ORDER_TYPE + "=" +
      SqlHandler.delimitString(cxmlOrderRequestBean.getOrderType()) + "," +
//    Larry Note: I don't see these are been used
//     ATTRIBUTE_REQUISITION_ID + "=" +
//     SqlHandler.delimitString(cxmlOrderRequestBean.getRequisitionId()) + "," +
//     ATTRIBUTE_SHIP_COMPLETE + "=" +
//      SqlHandler.delimitString(cxmlOrderRequestBean.getShipComplete()) + "," +
     ATTRIBUTE_CURRENCY + "=" +
      SqlHandler.delimitString(cxmlOrderRequestBean.getCurrency()) + "," +
     ATTRIBUTE_TOTAL_AMOUNT + "=" +
      SqlHandler.delimitString(cxmlOrderRequestBean.getTotalAmount()) + " " +
     "where " + ATTRIBUTE_PAYLOAD_ID + "=" +
      cxmlOrderRequestBean.getPayloadId();
    return new SqlManager().update(conn, query);
   }


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

    Collection cxmlOrderRequestBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      OrderRequestBean cxmlOrderRequestBean = new OrderRequestBean();
      load(dataSetRow, cxmlOrderRequestBean);
      cxmlOrderRequestBeanColl.add(cxmlOrderRequestBean);
    }

    return cxmlOrderRequestBeanColl;
  }
}