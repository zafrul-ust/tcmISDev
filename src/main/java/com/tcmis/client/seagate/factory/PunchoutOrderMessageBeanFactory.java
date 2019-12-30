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
import com.tcmis.client.seagate.beans.PunchoutOrderMessageBean;

/******************************************************************************
 * CLASSNAME: PunchoutOrderMessageBeanFactory <br>
 * @version: 1.0, Jun 9, 2006 <br>
 *****************************************************************************/

public class PunchoutOrderMessageBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PAYLOAD_ID = "PAYLOAD_ID";
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_BUYER_COOKIE = "BUYER_COOKIE";
  public String ATTRIBUTE_BROWSER_FORM_POST = "BROWSER_FORM_POST";
  public String ATTRIBUTE_RESPONSE_PAYLOAD_ID = "RESPONSE_PAYLOAD_ID";
  public String ATTRIBUTE_PUNCHOUT = "PUNCHOUT";
  public String ATTRIBUTE_POSTED_TO_ORACLE = "POSTED_TO_ORACLE";

  //table name
  public String TABLE = "PUNCHOUT_ORDER_MESSAGE";

  //constructor
  public PunchoutOrderMessageBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("payloadId")) {
      return ATTRIBUTE_PAYLOAD_ID;
    }
    else if (attributeName.equals("prNumber")) {
      return ATTRIBUTE_PR_NUMBER;
    }
    else if (attributeName.equals("buyerCookie")) {
      return ATTRIBUTE_BUYER_COOKIE;
    }
    else if (attributeName.equals("browserFormPost")) {
      return ATTRIBUTE_BROWSER_FORM_POST;
    }
    else if (attributeName.equals("responsePayloadId")) {
      return ATTRIBUTE_RESPONSE_PAYLOAD_ID;
    }
    else if (attributeName.equals("punchout")) {
      return ATTRIBUTE_PUNCHOUT;
    }
    else if (attributeName.equals("postedToOracle")) {
      return ATTRIBUTE_POSTED_TO_ORACLE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, PunchoutOrderMessageBean.class);
  }


//delete
   public int delete(PunchoutOrderMessageBean punchoutOrderMessageBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("payloadId", "SearchCriterion.EQUALS",
     "" + punchoutOrderMessageBean.getPayloadId());
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

   public int delete(PunchoutOrderMessageBean punchoutOrderMessageBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("payloadId", "SearchCriterion.EQUALS",
     "" + punchoutOrderMessageBean.getPayloadId());
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
   public int insert(PunchoutOrderMessageBean punchoutOrderMessageBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(punchoutOrderMessageBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }

   public int insert(PunchoutOrderMessageBean punchoutOrderMessageBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_PAYLOAD_ID + "," +
     ATTRIBUTE_PR_NUMBER + "," +
     ATTRIBUTE_BUYER_COOKIE + "," +
     ATTRIBUTE_BROWSER_FORM_POST + "," +
     ATTRIBUTE_RESPONSE_PAYLOAD_ID + "," +
     ATTRIBUTE_PUNCHOUT + "," +
     ATTRIBUTE_POSTED_TO_ORACLE + ")" +
     " values (" +
     SqlHandler.delimitString(punchoutOrderMessageBean.getPayloadId()) + "," +
     punchoutOrderMessageBean.getPrNumber() + "," +
       SqlHandler.delimitString(punchoutOrderMessageBean.getBuyerCookie()) + "," +
       SqlHandler.delimitString(punchoutOrderMessageBean.getBrowserFormPost()) + "," +
     SqlHandler.delimitString(punchoutOrderMessageBean.getResponsePayloadId()) + "," +
     SqlHandler.delimitString(punchoutOrderMessageBean.getPunchout()) + "," +
       SqlHandler.delimitString(punchoutOrderMessageBean.getPostedToOracle()) + ")";
    return sqlManager.update(conn, query);
   }

//update
   public int update(PunchoutOrderMessageBean punchoutOrderMessageBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(punchoutOrderMessageBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }

   public int update(PunchoutOrderMessageBean punchoutOrderMessageBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_PAYLOAD_ID + "=" +
      SqlHandler.delimitString(punchoutOrderMessageBean.getPayloadId()) + "," +
     ATTRIBUTE_PR_NUMBER + "=" +
      StringHandler.nullIfZero(punchoutOrderMessageBean.getPrNumber()) + "," +
     ATTRIBUTE_BUYER_COOKIE + "=" +
       SqlHandler.delimitString(punchoutOrderMessageBean.getBuyerCookie()) + "," +
     ATTRIBUTE_BROWSER_FORM_POST + "=" +
       SqlHandler.delimitString(punchoutOrderMessageBean.getBrowserFormPost()) + "," +
     ATTRIBUTE_RESPONSE_PAYLOAD_ID + "=" +
      SqlHandler.delimitString(punchoutOrderMessageBean.getResponsePayloadId()) + "," +
     ATTRIBUTE_PUNCHOUT + "=" +
      SqlHandler.delimitString(punchoutOrderMessageBean.getPunchout()) + "," +
     ATTRIBUTE_POSTED_TO_ORACLE + "=" +
       SqlHandler.delimitString(punchoutOrderMessageBean.getPostedToOracle()) + " " +
     "where " + ATTRIBUTE_PAYLOAD_ID + "=" +
      SqlHandler.delimitString(punchoutOrderMessageBean.getPayloadId());
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

    Collection punchoutOrderMessageBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PunchoutOrderMessageBean punchoutOrderMessageBean = new
          PunchoutOrderMessageBean();
      load(dataSetRow, punchoutOrderMessageBean);
      punchoutOrderMessageBeanColl.add(punchoutOrderMessageBean);
    }

    return punchoutOrderMessageBeanColl;
  }
}