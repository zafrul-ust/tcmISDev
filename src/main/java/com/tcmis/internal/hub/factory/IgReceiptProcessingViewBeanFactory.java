package com.tcmis.internal.hub.factory;

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
import com.tcmis.internal.hub.beans.IgReceiptProcessingViewBean;

/******************************************************************************
 * CLASSNAME: IgReceiptProcessingViewBeanFactory <br>
 * @version: 1.0, Nov 6, 2006 <br>
 *****************************************************************************/

public class IgReceiptProcessingViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
  public String ATTRIBUTE_RECEIPT_PROCESSING_METHOD =
      "RECEIPT_PROCESSING_METHOD";
  public String ATTRIBUTE_HUB = "HUB";
  public String ATTRIBUTE_ISSUE_GENERATION = "ISSUE_GENERATION";
  public String ATTRIBUTE_HOME_COMPANY_ID = "HOME_COMPANY_ID";
  public String ATTRIBUTE_HOME_COMPANY_SHORT_NAME = "HOME_COMPANY_SHORT_NAME";
  public String ATTRIBUTE_RECEIPT_PROCESSING_METHOD_DESC =
      "RECEIPT_PROCESSING_METHOD_DESC";

  //table name
  public String TABLE = "IG_RECEIPT_PROCESSING_VIEW";

  //constructor
  public IgReceiptProcessingViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("inventoryGroup")) {
      return ATTRIBUTE_INVENTORY_GROUP;
    }
    else if (attributeName.equals("receiptProcessingMethod")) {
      return ATTRIBUTE_RECEIPT_PROCESSING_METHOD;
    }
    else if (attributeName.equals("hub")) {
      return ATTRIBUTE_HUB;
    }
    else if (attributeName.equals("issueGeneration")) {
      return ATTRIBUTE_ISSUE_GENERATION;
    }
    else if (attributeName.equals("homeCompanyId")) {
      return ATTRIBUTE_HOME_COMPANY_ID;
    }
    else if (attributeName.equals("homeCompanyShortName")) {
      return ATTRIBUTE_HOME_COMPANY_SHORT_NAME;
    }
    else if (attributeName.equals("receiptProcessingMethodDesc")) {
      return ATTRIBUTE_RECEIPT_PROCESSING_METHOD_DESC;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, IgReceiptProcessingViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(IgReceiptProcessingViewBean igReceiptProcessingViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
     "" + igReceiptProcessingViewBean.getInventoryGroup());
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
   public int delete(IgReceiptProcessingViewBean igReceiptProcessingViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
     "" + igReceiptProcessingViewBean.getInventoryGroup());
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
   public int insert(IgReceiptProcessingViewBean igReceiptProcessingViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(igReceiptProcessingViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(IgReceiptProcessingViewBean igReceiptProcessingViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVENTORY_GROUP + "," +
     ATTRIBUTE_RECEIPT_PROCESSING_METHOD + "," +
     ATTRIBUTE_HUB + "," +
     ATTRIBUTE_ISSUE_GENERATION + "," +
     ATTRIBUTE_HOME_COMPANY_ID + "," +
     ATTRIBUTE_HOME_COMPANY_SHORT_NAME + "," +
     ATTRIBUTE_RECEIPT_PROCESSING_METHOD_DESC + ")" +
     " values (" +
     SqlHandler.delimitString(igReceiptProcessingViewBean.getInventoryGroup()) + "," +
     SqlHandler.delimitString(igReceiptProcessingViewBean.getReceiptProcessingMethod()) + "," +
     SqlHandler.delimitString(igReceiptProcessingViewBean.getHub()) + "," +
     SqlHandler.delimitString(igReceiptProcessingViewBean.getIssueGeneration()) + "," +
       SqlHandler.delimitString(igReceiptProcessingViewBean.getHomeCompanyId()) + "," +
     SqlHandler.delimitString(igReceiptProcessingViewBean.getHomeCompanyShortName()) + "," +
     SqlHandler.delimitString(igReceiptProcessingViewBean.getReceiptProcessingMethodDesc()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(IgReceiptProcessingViewBean igReceiptProcessingViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(igReceiptProcessingViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(IgReceiptProcessingViewBean igReceiptProcessingViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVENTORY_GROUP + "=" +
      SqlHandler.delimitString(igReceiptProcessingViewBean.getInventoryGroup()) + "," +
     ATTRIBUTE_RECEIPT_PROCESSING_METHOD + "=" +
      SqlHandler.delimitString(igReceiptProcessingViewBean.getReceiptProcessingMethod()) + "," +
     ATTRIBUTE_HUB + "=" +
      SqlHandler.delimitString(igReceiptProcessingViewBean.getHub()) + "," +
     ATTRIBUTE_ISSUE_GENERATION + "=" +
      SqlHandler.delimitString(igReceiptProcessingViewBean.getIssueGeneration()) + "," +
     ATTRIBUTE_HOME_COMPANY_ID + "=" +
       SqlHandler.delimitString(igReceiptProcessingViewBean.getHomeCompanyId()) + "," +
     ATTRIBUTE_HOME_COMPANY_SHORT_NAME + "=" +
      SqlHandler.delimitString(igReceiptProcessingViewBean.getHomeCompanyShortName()) + "," +
     ATTRIBUTE_RECEIPT_PROCESSING_METHOD_DESC + "=" +
      SqlHandler.delimitString(igReceiptProcessingViewBean.getReceiptProcessingMethodDesc()) + " " +
     "where " + ATTRIBUTE_INVENTORY_GROUP + "=" +
      igReceiptProcessingViewBean.getInventoryGroup();
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
    Collection igReceiptProcessingViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      IgReceiptProcessingViewBean igReceiptProcessingViewBean = new
          IgReceiptProcessingViewBean();
      load(dataSetRow, igReceiptProcessingViewBean);
      igReceiptProcessingViewBeanColl.add(igReceiptProcessingViewBean);
    }
    return igReceiptProcessingViewBeanColl;
  }

  public Collection selectMinMax(SearchCriteria criteria) throws BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectMinMax(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectMinMax(SearchCriteria criteria, Connection conn) throws
      BaseException {
    Collection igReceiptProcessingViewBeanColl = new Vector();
    String query = "select distinct INVENTORY_GROUP,RECEIPT_PROCESSING_METHOD, RECEIPT_PROCESSING_METHOD_DESC from " + TABLE + " " +
        getWhereClause(criteria) + " order by INVENTORY_GROUP";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      IgReceiptProcessingViewBean igReceiptProcessingViewBean = new
          IgReceiptProcessingViewBean();
      load(dataSetRow, igReceiptProcessingViewBean);
      igReceiptProcessingViewBeanColl.add(igReceiptProcessingViewBean);
    }
    return igReceiptProcessingViewBeanColl;
  }
}