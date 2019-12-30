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
import com.tcmis.client.cxml.beans.PunchoutSessionBean;

/******************************************************************************
 * CLASSNAME: PunchoutSessionBeanFactory <br>
 * @version: 1.0, Jun 9, 2006 <br>
 *****************************************************************************/

public class PunchoutSessionBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_SESSION_ID = "SESSION_ID";
  public String ATTRIBUTE_PAYLOAD_ID = "PAYLOAD_ID";
  public String ATTRIBUTE_USER_ID = "USER_ID";
  public String ATTRIBUTE_LOGGED = "LOGGED";
  public String ATTRIBUTE_MR_NUMBER = "MR_NUMBER";
  public String ATTRIBUTE_ORACLE = "ORACLE";

  //table name
  public String TABLE = "PUNCHOUT_SESSION";

  //constructor
  public PunchoutSessionBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("sessionId")) {
      return ATTRIBUTE_SESSION_ID;
    }
    else if (attributeName.equals("payloadId")) {
      return ATTRIBUTE_PAYLOAD_ID;
    }
    else if (attributeName.equals("userId")) {
      return ATTRIBUTE_USER_ID;
    }
    else if (attributeName.equals("logged")) {
      return ATTRIBUTE_LOGGED;
    }
    else if (attributeName.equals("mrNumber")) {
      return ATTRIBUTE_MR_NUMBER;
    }
    else if (attributeName.equals("oracle")) {
      return ATTRIBUTE_ORACLE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, PunchoutSessionBean.class);
  }


//delete
   public int delete(PunchoutSessionBean punchoutSessionBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("sessionId", "SearchCriterion.EQUALS",
     "" + punchoutSessionBean.getSessionId());
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

   public int delete(PunchoutSessionBean punchoutSessionBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("sessionId", "SearchCriterion.EQUALS",
     "" + punchoutSessionBean.getSessionId());
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
   public int insert(PunchoutSessionBean punchoutSessionBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(punchoutSessionBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }

   public int insert(PunchoutSessionBean punchoutSessionBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_SESSION_ID + "," +
     ATTRIBUTE_PAYLOAD_ID + "," +
     ATTRIBUTE_USER_ID + "," +
     ATTRIBUTE_LOGGED + "," +
     ATTRIBUTE_MR_NUMBER + "," +
     ATTRIBUTE_ORACLE + ")" +
     " values (" +
     SqlHandler.delimitString(punchoutSessionBean.getSessionId()) + "," +
     SqlHandler.delimitString(punchoutSessionBean.getPayloadId()) + "," +
     SqlHandler.delimitString(punchoutSessionBean.getUserId()) + "," +
     SqlHandler.delimitString(punchoutSessionBean.getLogged()) + "," +
     punchoutSessionBean.getMrNumber() + "," +
     SqlHandler.delimitString(punchoutSessionBean.getOracle()) + ")";
    return sqlManager.update(conn, query);
   }

//update
   public int update(PunchoutSessionBean punchoutSessionBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(punchoutSessionBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }

   public int update(PunchoutSessionBean punchoutSessionBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_SESSION_ID + "=" +
      SqlHandler.delimitString(punchoutSessionBean.getSessionId()) + "," +
     ATTRIBUTE_PAYLOAD_ID + "=" +
      SqlHandler.delimitString(punchoutSessionBean.getPayloadId()) + "," +
     ATTRIBUTE_USER_ID + "=" +
      SqlHandler.delimitString(punchoutSessionBean.getUserId()) + "," +
     ATTRIBUTE_LOGGED + "=" +
      SqlHandler.delimitString(punchoutSessionBean.getLogged()) + "," +
     ATTRIBUTE_MR_NUMBER + "=" +
      StringHandler.nullIfZero(punchoutSessionBean.getMrNumber()) + "," +
     ATTRIBUTE_ORACLE + "=" +
      SqlHandler.delimitString(punchoutSessionBean.getOracle()) + " " +
     "where " + ATTRIBUTE_SESSION_ID + "=" +
      punchoutSessionBean.getSessionId();
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

    Collection punchoutSessionBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PunchoutSessionBean punchoutSessionBean = new PunchoutSessionBean();
      load(dataSetRow, punchoutSessionBean);
      punchoutSessionBeanColl.add(punchoutSessionBean);
    }

    return punchoutSessionBeanColl;
  }
}