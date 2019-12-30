package com.tcmis.client.seagate.factory;

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
import com.tcmis.client.seagate.beans.PunchoutSetupRequestBean;

/******************************************************************************
 * CLASSNAME: PunchoutSetupRequestBeanFactory <br>
 * @version: 1.0, Jun 9, 2006 <br>
 *****************************************************************************/

public class PunchoutSetupRequestBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PAYLOAD_ID = "PAYLOAD_ID";
  public String ATTRIBUTE_OPERATION = "OPERATION";
  public String ATTRIBUTE_BUYER_COOKIE = "BUYER_COOKIE";
  public String ATTRIBUTE_BROWSER_FORM_POST = "BROWSER_FORM_POST";

  //table name
  public String TABLE = "PUNCHOUT_SETUP_REQUEST";

  //constructor
  public PunchoutSetupRequestBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("payloadId")) {
      return ATTRIBUTE_PAYLOAD_ID;
    }
    else if (attributeName.equals("operation")) {
      return ATTRIBUTE_OPERATION;
    }
    else if (attributeName.equals("buyerCookie")) {
      return ATTRIBUTE_BUYER_COOKIE;
    }
    else if (attributeName.equals("browserFormPost")) {
      return ATTRIBUTE_BROWSER_FORM_POST;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, PunchoutSetupRequestBean.class);
  }


//delete
   public int delete(PunchoutSetupRequestBean punchoutSetupRequestBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("payloadId", "SearchCriterion.EQUALS",
     "" + punchoutSetupRequestBean.getPayloadId());
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

   public int delete(PunchoutSetupRequestBean punchoutSetupRequestBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("payloadId", "SearchCriterion.EQUALS",
     "" + punchoutSetupRequestBean.getPayloadId());
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
   public int insert(PunchoutSetupRequestBean punchoutSetupRequestBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(punchoutSetupRequestBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }

   public int insert(PunchoutSetupRequestBean punchoutSetupRequestBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_PAYLOAD_ID + "," +
     ATTRIBUTE_OPERATION + "," +
     ATTRIBUTE_BUYER_COOKIE + "," +
     ATTRIBUTE_BROWSER_FORM_POST + ")" +
     " values (" +
     SqlHandler.delimitString(punchoutSetupRequestBean.getPayloadId()) + "," +
     SqlHandler.delimitString(punchoutSetupRequestBean.getOperation()) + "," +
       SqlHandler.delimitString(punchoutSetupRequestBean.getBuyerCookie()) + "," +
       SqlHandler.delimitString(punchoutSetupRequestBean.getBrowserFormPost()) + ")";
    return sqlManager.update(conn, query);
   }

//update
   public int update(PunchoutSetupRequestBean punchoutSetupRequestBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(punchoutSetupRequestBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }

   public int update(PunchoutSetupRequestBean punchoutSetupRequestBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_PAYLOAD_ID + "=" +
      SqlHandler.delimitString(punchoutSetupRequestBean.getPayloadId()) + "," +
     ATTRIBUTE_OPERATION + "=" +
      SqlHandler.delimitString(punchoutSetupRequestBean.getOperation()) + "," +
     ATTRIBUTE_BUYER_COOKIE + "=" +
       SqlHandler.delimitString(punchoutSetupRequestBean.getBuyerCookie()) + "," +
     ATTRIBUTE_BROWSER_FORM_POST + "=" +
       SqlHandler.delimitString(punchoutSetupRequestBean.getBrowserFormPost()) + " " +
     "where " + ATTRIBUTE_PAYLOAD_ID + "=" +
      punchoutSetupRequestBean.getPayloadId();
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

    Collection punchoutSetupRequestBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PunchoutSetupRequestBean punchoutSetupRequestBean = new
          PunchoutSetupRequestBean();
      load(dataSetRow, punchoutSetupRequestBean);
      punchoutSetupRequestBeanColl.add(punchoutSetupRequestBean);
    }

    return punchoutSetupRequestBeanColl;
  }
}