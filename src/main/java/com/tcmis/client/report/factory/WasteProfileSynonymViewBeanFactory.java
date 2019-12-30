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
import com.tcmis.client.report.beans.WasteProfileSynonymViewBean;

/******************************************************************************
 * CLASSNAME: WasteProfileSynonymViewBeanFactory <br>
 * @version: 1.0, Mar 3, 2006 <br>
 *****************************************************************************/

public class WasteProfileSynonymViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_VENDOR_ID = "VENDOR_ID";
  public String ATTRIBUTE_VENDOR_PROFILE_ID = "VENDOR_PROFILE_ID";
  public String ATTRIBUTE_DESCRIPTION = "DESCRIPTION";
  public String ATTRIBUTE_SEARCH_STRING = "SEARCH_STRING";

  //table name
  public String TABLE = "WASTE_PROFILE_SYNONYM_VIEW";

  //constructor
  public WasteProfileSynonymViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("vendorId")) {
      return ATTRIBUTE_VENDOR_ID;
    }
    else if (attributeName.equals("vendorProfileId")) {
      return ATTRIBUTE_VENDOR_PROFILE_ID;
    }
    else if (attributeName.equals("description")) {
      return ATTRIBUTE_DESCRIPTION;
    }
    else if (attributeName.equals("searchString")) {
      return ATTRIBUTE_SEARCH_STRING;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, WasteProfileSynonymViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(WasteProfileSynonymViewBean wasteProfileSynonymViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("vendorId", "SearchCriterion.EQUALS",
     "" + wasteProfileSynonymViewBean.getVendorId());
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
   public int delete(WasteProfileSynonymViewBean wasteProfileSynonymViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("vendorId", "SearchCriterion.EQUALS",
     "" + wasteProfileSynonymViewBean.getVendorId());
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

//you need to verify the primary key(s) before uncommenting this
  /*
//insert
   public int insert(WasteProfileSynonymViewBean wasteProfileSynonymViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(wasteProfileSynonymViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(WasteProfileSynonymViewBean wasteProfileSynonymViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_VENDOR_ID + "," +
     ATTRIBUTE_VENDOR_PROFILE_ID + "," +
     ATTRIBUTE_DESCRIPTION + "," +
     ATTRIBUTE_SEARCH_STRING + ")" +
     " values (" +
     SqlHandler.delimitString(wasteProfileSynonymViewBean.getVendorId()) + "," +
     SqlHandler.delimitString(wasteProfileSynonymViewBean.getVendorProfileId()) + "," +
     SqlHandler.delimitString(wasteProfileSynonymViewBean.getDescription()) + "," +
     SqlHandler.delimitString(wasteProfileSynonymViewBean.getSearchString()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(WasteProfileSynonymViewBean wasteProfileSynonymViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(wasteProfileSynonymViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(WasteProfileSynonymViewBean wasteProfileSynonymViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_VENDOR_ID + "=" +
      SqlHandler.delimitString(wasteProfileSynonymViewBean.getVendorId()) + "," +
     ATTRIBUTE_VENDOR_PROFILE_ID + "=" +
      SqlHandler.delimitString(wasteProfileSynonymViewBean.getVendorProfileId()) + "," +
     ATTRIBUTE_DESCRIPTION + "=" +
      SqlHandler.delimitString(wasteProfileSynonymViewBean.getDescription()) + "," +
     ATTRIBUTE_SEARCH_STRING + "=" +
      SqlHandler.delimitString(wasteProfileSynonymViewBean.getSearchString()) + " " +
     "where " + ATTRIBUTE_VENDOR_ID + "=" +
      wasteProfileSynonymViewBean.getVendorId();
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
    Collection wasteProfileSynonymViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      WasteProfileSynonymViewBean wasteProfileSynonymViewBean = new
          WasteProfileSynonymViewBean();
      load(dataSetRow, wasteProfileSynonymViewBean);
      wasteProfileSynonymViewBeanColl.add(wasteProfileSynonymViewBean);
    }
    return wasteProfileSynonymViewBeanColl;
  }
}