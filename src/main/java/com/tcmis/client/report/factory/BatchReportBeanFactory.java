package com.tcmis.client.report.factory;

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
import com.tcmis.client.report.beans.BatchReportBean;

/******************************************************************************
 * CLASSNAME: BatchReportBeanFactory <br>
 * @version: 1.0, Sep 13, 2006 <br>
 *****************************************************************************/

public class BatchReportBeanFactory extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_REPORT_DATE = "REPORT_DATE";
  public String ATTRIBUTE_REPORT_NAME = "REPORT_NAME";
  public String ATTRIBUTE_CONTENT = "CONTENT";
  public String ATTRIBUTE_STATUS = "STATUS";

  //table name
  public String TABLE = "BATCH_REPORT";

  //constructor
  public BatchReportBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("personnelId")) {
      return ATTRIBUTE_PERSONNEL_ID;
    } else if (attributeName.equals("reportDate")) {
      return ATTRIBUTE_REPORT_DATE;
    } else if (attributeName.equals("reportName")) {
      return ATTRIBUTE_REPORT_NAME;
    } else if (attributeName.equals("content")) {
      return ATTRIBUTE_CONTENT;
    } else if (attributeName.equals("status")) {
      return ATTRIBUTE_STATUS;
    } else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, BatchReportBean.class);
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

  public void updateDelete(String whereClause) throws BaseException {

    Connection connection = null;
    try {
      connection = getDbManager().getConnection();
      updateDelete(whereClause, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
  }

  public void updateDelete(String whereClause, Connection conn) throws BaseException {

    String sqlQuery = " update " + TABLE + " set status = 'delete' where " +whereClause;

    new SqlManager().update(conn, sqlQuery);
  }

  //select
  public Collection select(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(criteria,null, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(criteria, sortCriteria, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

    Collection batchReportBeanColl = new Vector();

    String query = "select * from " + TABLE + " " + getWhereClause(criteria);
    if (sortCriteria != null) {
      query += getOrderByClause(sortCriteria);
    }

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      BatchReportBean batchReportBean = new BatchReportBean();
      load(dataSetRow, batchReportBean);
      batchReportBeanColl.add(batchReportBean);
    }

    return batchReportBeanColl;
  }

} //end of class