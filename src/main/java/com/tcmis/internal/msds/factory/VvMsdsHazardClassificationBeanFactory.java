package com.tcmis.internal.msds.factory;

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
import com.tcmis.internal.msds.beans.VvMsdsHazardClassificationBean;

/******************************************************************************
 * CLASSNAME: VvMsdsHazardClassificationBeanFactory <br>
 * @version: 1.0, Oct 4, 2006 <br>
 *****************************************************************************/

public class VvMsdsHazardClassificationBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_HAZARD_CLASSIFICATION = "HAZARD_CLASSIFICATION";
  public String ATTRIBUTE_HAZARD_CLASSIFICATION_DESC =
      "HAZARD_CLASSIFICATION_DESC";

  //table name
  public String TABLE = "VV_MSDS_HAZARD_CLASSIFICATION";

  //constructor
  public VvMsdsHazardClassificationBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("hazardClassification")) {
      return ATTRIBUTE_HAZARD_CLASSIFICATION;
    }
    else if (attributeName.equals("hazardClassificationDesc")) {
      return ATTRIBUTE_HAZARD_CLASSIFICATION_DESC;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, VvMsdsHazardClassificationBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
       public int delete(VvMsdsHazardClassificationBean vvMsdsHazardClassificationBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("hazardClassification", "SearchCriterion.EQUALS",
     "" + vvMsdsHazardClassificationBean.getHazardClassification());
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
   public int delete(VvMsdsHazardClassificationBean vvMsdsHazardClassificationBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("hazardClassification", "SearchCriterion.EQUALS",
     "" + vvMsdsHazardClassificationBean.getHazardClassification());
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
       public int insert(VvMsdsHazardClassificationBean vvMsdsHazardClassificationBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(vvMsdsHazardClassificationBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(VvMsdsHazardClassificationBean vvMsdsHazardClassificationBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_HAZARD_CLASSIFICATION + "," +
     ATTRIBUTE_HAZARD_CLASSIFICATION_DESC + ")" +
     " values (" +
     SqlHandler.delimitString(vvMsdsHazardClassificationBean.getHazardClassification()) + "," +
     SqlHandler.delimitString(vvMsdsHazardClassificationBean.getHazardClassificationDesc()) + ")";
    return sqlManager.update(conn, query);
   }
//update
       public int update(VvMsdsHazardClassificationBean vvMsdsHazardClassificationBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(vvMsdsHazardClassificationBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(VvMsdsHazardClassificationBean vvMsdsHazardClassificationBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_HAZARD_CLASSIFICATION + "=" +
      SqlHandler.delimitString(vvMsdsHazardClassificationBean.getHazardClassification()) + "," +
     ATTRIBUTE_HAZARD_CLASSIFICATION_DESC + "=" +
      SqlHandler.delimitString(vvMsdsHazardClassificationBean.getHazardClassificationDesc()) + " " +
     "where " + ATTRIBUTE_HAZARD_CLASSIFICATION + "=" +
      vvMsdsHazardClassificationBean.getHazardClassification();
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
    Collection vvMsdsHazardClassificationBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      VvMsdsHazardClassificationBean vvMsdsHazardClassificationBean = new
          VvMsdsHazardClassificationBean();
      load(dataSetRow, vvMsdsHazardClassificationBean);
      vvMsdsHazardClassificationBeanColl.add(vvMsdsHazardClassificationBean);
    }
    return vvMsdsHazardClassificationBeanColl;
  }
}