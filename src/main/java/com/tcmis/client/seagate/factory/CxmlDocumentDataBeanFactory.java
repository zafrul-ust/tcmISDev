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
import com.tcmis.client.seagate.beans.CxmlDocumentDataBean;

/******************************************************************************
 * CLASSNAME: CxmlDocumentDataBeanFactory <br>
 * @version: 1.0, Jun 9, 2006 <br>
 *****************************************************************************/

public class CxmlDocumentDataBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PAYLOAD_ID = "PAYLOAD_ID";
  public String ATTRIBUTE_TIMESTAMP = "TIMESTAMP";
  public String ATTRIBUTE_TYPE = "TYPE";
  public String ATTRIBUTE_FROM_DOMAIN = "FROM_DOMAIN";
  public String ATTRIBUTE_FROM_IDENTITY = "FROM_IDENTITY";
  public String ATTRIBUTE_TO_DOMAIN = "TO_DOMAIN";
  public String ATTRIBUTE_TO_IDENTITY = "TO_IDENTITY";
  public String ATTRIBUTE_SENDER_DOMAIN = "SENDER_DOMAIN";
  public String ATTRIBUTE_SENDER_IDENTITY = "SENDER_IDENTITY";
  public String ATTRIBUTE_USER_AGENT = "USER_AGENT";
  public String ATTRIBUTE_RESPONSE_PAYLOAD_ID = "RESPONSE_PAYLOAD_ID";
  public String ATTRIBUTE_RESPONSE_CODE = "RESPONSE_CODE";

  //table name
  public String TABLE = "CXML_DOCUMENT_DATA";

  //constructor
  public CxmlDocumentDataBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("payloadId")) {
      return ATTRIBUTE_PAYLOAD_ID;
    }
    else if (attributeName.equals("timestamp")) {
      return ATTRIBUTE_TIMESTAMP;
    }
    else if (attributeName.equals("type")) {
      return ATTRIBUTE_TYPE;
    }
    else if (attributeName.equals("fromDomain")) {
      return ATTRIBUTE_FROM_DOMAIN;
    }
    else if (attributeName.equals("fromIdentity")) {
      return ATTRIBUTE_FROM_IDENTITY;
    }
    else if (attributeName.equals("toDomain")) {
      return ATTRIBUTE_TO_DOMAIN;
    }
    else if (attributeName.equals("toIdentity")) {
      return ATTRIBUTE_TO_IDENTITY;
    }
    else if (attributeName.equals("senderDomain")) {
      return ATTRIBUTE_SENDER_DOMAIN;
    }
    else if (attributeName.equals("senderIdentity")) {
      return ATTRIBUTE_SENDER_IDENTITY;
    }
    else if (attributeName.equals("userAgent")) {
      return ATTRIBUTE_USER_AGENT;
    }
    else if (attributeName.equals("responsePayloadId")) {
      return ATTRIBUTE_RESPONSE_PAYLOAD_ID;
    }
    else if (attributeName.equals("responseCode")) {
      return ATTRIBUTE_RESPONSE_CODE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, CxmlDocumentDataBean.class);
  }


//delete
   public int delete(CxmlDocumentDataBean cxmlDocumentDataBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("payloadId", "SearchCriterion.EQUALS",
     "" + cxmlDocumentDataBean.getPayloadId());
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
       public int delete(CxmlDocumentDataBean cxmlDocumentDataBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("payloadId", "SearchCriterion.EQUALS",
     "" + cxmlDocumentDataBean.getPayloadId());
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
   public int insert(CxmlDocumentDataBean cxmlDocumentDataBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(cxmlDocumentDataBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int insert(CxmlDocumentDataBean cxmlDocumentDataBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_PAYLOAD_ID + "," +
     ATTRIBUTE_TIMESTAMP + "," +
     ATTRIBUTE_TYPE + "," +
     ATTRIBUTE_FROM_DOMAIN + "," +
     ATTRIBUTE_FROM_IDENTITY + "," +
     ATTRIBUTE_TO_DOMAIN + "," +
     ATTRIBUTE_TO_IDENTITY + "," +
     ATTRIBUTE_SENDER_DOMAIN + "," +
     ATTRIBUTE_SENDER_IDENTITY + "," +
     ATTRIBUTE_USER_AGENT + "," +
     ATTRIBUTE_RESPONSE_PAYLOAD_ID + "," +
     ATTRIBUTE_RESPONSE_CODE + ")" +
     " values (" +
     SqlHandler.delimitString(cxmlDocumentDataBean.getPayloadId()) + "," +
     SqlHandler.delimitString(cxmlDocumentDataBean.getTimestamp()) + "," +
     SqlHandler.delimitString(cxmlDocumentDataBean.getType()) + "," +
     SqlHandler.delimitString(cxmlDocumentDataBean.getFromDomain()) + "," +
     SqlHandler.delimitString(cxmlDocumentDataBean.getFromIdentity()) + "," +
     SqlHandler.delimitString(cxmlDocumentDataBean.getToDomain()) + "," +
     SqlHandler.delimitString(cxmlDocumentDataBean.getToIdentity()) + "," +
     SqlHandler.delimitString(cxmlDocumentDataBean.getSenderDomain()) + "," +
     SqlHandler.delimitString(cxmlDocumentDataBean.getSenderIdentity()) + "," +
     SqlHandler.delimitString(cxmlDocumentDataBean.getUserAgent()) + "," +
       SqlHandler.delimitString(cxmlDocumentDataBean.getResponsePayloadId()) + "," +
     SqlHandler.delimitString(cxmlDocumentDataBean.getResponseCode()) + ")";
    return sqlManager.update(conn, query);
   }

//update
   public int update(CxmlDocumentDataBean cxmlDocumentDataBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(cxmlDocumentDataBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
       public int update(CxmlDocumentDataBean cxmlDocumentDataBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_PAYLOAD_ID + "=" +
      SqlHandler.delimitString(cxmlDocumentDataBean.getPayloadId()) + "," +
     ATTRIBUTE_TIMESTAMP + "=" +
      SqlHandler.delimitString(cxmlDocumentDataBean.getTimestamp()) + "," +
     ATTRIBUTE_TYPE + "=" +
      SqlHandler.delimitString(cxmlDocumentDataBean.getType()) + "," +
     ATTRIBUTE_FROM_DOMAIN + "=" +
      SqlHandler.delimitString(cxmlDocumentDataBean.getFromDomain()) + "," +
     ATTRIBUTE_FROM_IDENTITY + "=" +
      SqlHandler.delimitString(cxmlDocumentDataBean.getFromIdentity()) + "," +
     ATTRIBUTE_TO_DOMAIN + "=" +
      SqlHandler.delimitString(cxmlDocumentDataBean.getToDomain()) + "," +
     ATTRIBUTE_TO_IDENTITY + "=" +
      SqlHandler.delimitString(cxmlDocumentDataBean.getToIdentity()) + "," +
     ATTRIBUTE_SENDER_DOMAIN + "=" +
      SqlHandler.delimitString(cxmlDocumentDataBean.getSenderDomain()) + "," +
     ATTRIBUTE_SENDER_IDENTITY + "=" +
       SqlHandler.delimitString(cxmlDocumentDataBean.getSenderIdentity()) + "," +
     ATTRIBUTE_USER_AGENT + "=" +
      SqlHandler.delimitString(cxmlDocumentDataBean.getUserAgent()) + "," +
     ATTRIBUTE_RESPONSE_PAYLOAD_ID + "=" +
       SqlHandler.delimitString(cxmlDocumentDataBean.getResponsePayloadId()) + "," +
     ATTRIBUTE_RESPONSE_CODE + "=" +
      SqlHandler.delimitString(cxmlDocumentDataBean.getResponseCode()) + " " +
     "where " + ATTRIBUTE_PAYLOAD_ID + "=" +
      cxmlDocumentDataBean.getPayloadId();
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

    Collection cxmlDocumentDataBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CxmlDocumentDataBean cxmlDocumentDataBean = new CxmlDocumentDataBean();
      load(dataSetRow, cxmlDocumentDataBean);
      cxmlDocumentDataBeanColl.add(cxmlDocumentDataBean);
    }

    return cxmlDocumentDataBeanColl;
  }
}