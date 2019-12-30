package com.tcmis.client.utc.factory;

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
import com.tcmis.client.utc.beans.Cr658CollectionNumberBean;

/******************************************************************************
 * CLASSNAME: Cr658CollectionNumberBeanFactory <br>
 * @version: 1.0, Aug 29, 2006 <br>
 *****************************************************************************/

public class Cr658CollectionNumberBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_DIVISION = "DIVISION";
  public String ATTRIBUTE_COLLNO = "COLLNO";

  //table name
  public String TABLE = "CSIMPSON.CR658_COLLECTION_NUMBER";

  //constructor
  public Cr658CollectionNumberBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("division")) {
      return ATTRIBUTE_DIVISION;
    }
    else if (attributeName.equals("collno")) {
      return ATTRIBUTE_COLLNO;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, Cr658CollectionNumberBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(Cr658CollectionNumberBean cr658CollectionNumberBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("division", "SearchCriterion.EQUALS",
     "" + cr658CollectionNumberBean.getDivision());
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
   public int delete(Cr658CollectionNumberBean cr658CollectionNumberBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("division", "SearchCriterion.EQUALS",
     "" + cr658CollectionNumberBean.getDivision());
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
   public int insert(Cr658CollectionNumberBean cr658CollectionNumberBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(cr658CollectionNumberBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(Cr658CollectionNumberBean cr658CollectionNumberBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_DIVISION + "," +
     ATTRIBUTE_COLLNO + ")" +
     " values (" +
     SqlHandler.delimitString(cr658CollectionNumberBean.getDivision()) + "," +
     cr658CollectionNumberBean.getCollno() + ")";
    return sqlManager.update(conn, query);
   }
   */
//update
  public int update(Cr658CollectionNumberBean cr658CollectionNumberBean) throws
      BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = update(cr658CollectionNumberBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int update(Cr658CollectionNumberBean cr658CollectionNumberBean,
                    Connection conn) throws BaseException {
    String query = "update " + TABLE + " set " +
        ATTRIBUTE_DIVISION + "=" +
        SqlHandler.delimitString(cr658CollectionNumberBean.getDivision()) + "," +
        ATTRIBUTE_COLLNO + "=" +
        StringHandler.nullIfZero(cr658CollectionNumberBean.getCollno()) + " " +
        "where " + ATTRIBUTE_DIVISION + "=" +
        SqlHandler.delimitString(cr658CollectionNumberBean.getDivision());
      if(log.isDebugEnabled()) {
          log.debug("QUERY:" + query);
      }
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

    Collection cr658CollectionNumberBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      Cr658CollectionNumberBean cr658CollectionNumberBean = new
          Cr658CollectionNumberBean();
      load(dataSetRow, cr658CollectionNumberBean);
      cr658CollectionNumberBeanColl.add(cr658CollectionNumberBean);
    }

    return cr658CollectionNumberBeanColl;
  }
}