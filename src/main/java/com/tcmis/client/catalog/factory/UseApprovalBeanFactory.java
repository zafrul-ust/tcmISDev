package com.tcmis.client.catalog.factory;

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
import com.tcmis.client.catalog.beans.UseApprovalBean;

/******************************************************************************
 * CLASSNAME: UseApprovalBeanFactory <br>
 * @version: 1.0, Oct 23, 2006 <br>
 *****************************************************************************/

public class UseApprovalBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_USER_GROUP_ID = "USER_GROUP_ID";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
  public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
  public String ATTRIBUTE_APPLICATION = "APPLICATION";
  public String ATTRIBUTE_APPROVAL_ID = "APPROVAL_ID";
  public String ATTRIBUTE_APPROVAL_STATUS = "APPROVAL_STATUS";
  public String ATTRIBUTE_REVIEWED_DATE = "REVIEWED_DATE";
  public String ATTRIBUTE_APP_GROUP = "APP_GROUP";
  public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
  public String ATTRIBUTE_LIMIT_QUANTITY_PERIOD1 = "LIMIT_QUANTITY_PERIOD1";
  public String ATTRIBUTE_DAYS_PERIOD1 = "DAYS_PERIOD1";
  public String ATTRIBUTE_LIMIT_QUANTITY_PERIOD2 = "LIMIT_QUANTITY_PERIOD2";
  public String ATTRIBUTE_DAYS_PERIOD2 = "DAYS_PERIOD2";
  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
  public String ATTRIBUTE_PROCESS_DESC = "PROCESS_DESC";
  public String ATTRIBUTE_ORDER_QUANTITY = "ORDER_QUANTITY";
  public String ATTRIBUTE_ORDER_QUANTITY_RULE = "ORDER_QUANTITY_RULE";
  public String ATTRIBUTE_ESTIMATE_ORDER_QUANTITY_PERIOD = "ESTIMATE_ORDER_QUANTITY_PERIOD";
  public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";

  //table name
  public String TABLE = "USE_APPROVAL";

  //constructor
  public UseApprovalBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("userGroupId")) {
      return ATTRIBUTE_USER_GROUP_ID;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("facPartNo")) {
      return ATTRIBUTE_FAC_PART_NO;
    }
    else if (attributeName.equals("expireDate")) {
      return ATTRIBUTE_EXPIRE_DATE;
    }
    else if (attributeName.equals("application")) {
      return ATTRIBUTE_APPLICATION;
    }
    else if (attributeName.equals("approvalId")) {
      return ATTRIBUTE_APPROVAL_ID;
    }
    else if (attributeName.equals("approvalStatus")) {
      return ATTRIBUTE_APPROVAL_STATUS;
    }
    else if (attributeName.equals("reviewedDate")) {
      return ATTRIBUTE_REVIEWED_DATE;
    }
    else if (attributeName.equals("appGroup")) {
      return ATTRIBUTE_APP_GROUP;
    }
    else if (attributeName.equals("catalogId")) {
      return ATTRIBUTE_CATALOG_ID;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("partGroupNo")) {
      return ATTRIBUTE_PART_GROUP_NO;
    }
    else if (attributeName.equals("limitQuantityPeriod1")) {
      return ATTRIBUTE_LIMIT_QUANTITY_PERIOD1;
    }
    else if (attributeName.equals("daysPeriod1")) {
      return ATTRIBUTE_DAYS_PERIOD1;
    }
    else if (attributeName.equals("limitQuantityPeriod2")) {
      return ATTRIBUTE_LIMIT_QUANTITY_PERIOD2;
    }
    else if (attributeName.equals("daysPeriod2")) {
      return ATTRIBUTE_DAYS_PERIOD2;
    }
    else if (attributeName.equals("inventoryGroup")) {
      return ATTRIBUTE_INVENTORY_GROUP;
    }
    else if (attributeName.equals("processDesc")) {
      return ATTRIBUTE_PROCESS_DESC;
    }
    else if (attributeName.equals("orderQuantity")) {
      return ATTRIBUTE_ORDER_QUANTITY;
    }
    else if (attributeName.equals("orderQuantityRule")) {
      return ATTRIBUTE_ORDER_QUANTITY_RULE;
    }
    else if (attributeName.equals("estimateOrderQuantityPeriod")) {
      return ATTRIBUTE_ESTIMATE_ORDER_QUANTITY_PERIOD;
    }
	 else if (attributeName.equals("catalogCompanyId")) {
      return ATTRIBUTE_CATALOG_COMPANY_ID;
    }
	 else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, UseApprovalBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(UseApprovalBean useApprovalBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("userGroupId", "SearchCriterion.EQUALS",
     "" + useApprovalBean.getUserGroupId());
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
   public int delete(UseApprovalBean useApprovalBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("userGroupId", "SearchCriterion.EQUALS",
     "" + useApprovalBean.getUserGroupId());
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
   public int insert(UseApprovalBean useApprovalBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(useApprovalBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(UseApprovalBean useApprovalBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_USER_GROUP_ID + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_FAC_PART_NO + "," +
     ATTRIBUTE_EXPIRE_DATE + "," +
     ATTRIBUTE_APPLICATION + "," +
     ATTRIBUTE_APPROVAL_ID + "," +
     ATTRIBUTE_APPROVAL_STATUS + "," +
     ATTRIBUTE_REVIEWED_DATE + "," +
     ATTRIBUTE_APP_GROUP + "," +
     ATTRIBUTE_CATALOG_ID + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_PART_GROUP_NO + "," +
     ATTRIBUTE_LIMIT_QUANTITY_PERIOD1 + "," +
     ATTRIBUTE_DAYS_PERIOD1 + "," +
     ATTRIBUTE_LIMIT_QUANTITY_PERIOD2 + "," +
     ATTRIBUTE_DAYS_PERIOD2 + "," +
     ATTRIBUTE_INVENTORY_GROUP + "," +
     ATTRIBUTE_PROCESS_DESC + "," +
     ATTRIBUTE_ORDER_QUANTITY + "," +
     ATTRIBUTE_ORDER_QUANTITY_RULE + "," +
     ATTRIBUTE_ESTIMATE_ORDER_QUANTITY_PERIOD + ")" +
     " values (" +
     SqlHandler.delimitString(useApprovalBean.getUserGroupId()) + "," +
     SqlHandler.delimitString(useApprovalBean.getFacilityId()) + "," +
     SqlHandler.delimitString(useApprovalBean.getFacPartNo()) + "," +
       DateHandler.getOracleToDateFunction(useApprovalBean.getExpireDate()) + "," +
     SqlHandler.delimitString(useApprovalBean.getApplication()) + "," +
     useApprovalBean.getApprovalId() + "," +
     SqlHandler.delimitString(useApprovalBean.getApprovalStatus()) + "," +
       DateHandler.getOracleToDateFunction(useApprovalBean.getReviewedDate()) + "," +
     SqlHandler.delimitString(useApprovalBean.getAppGroup()) + "," +
     SqlHandler.delimitString(useApprovalBean.getCatalogId()) + "," +
     SqlHandler.delimitString(useApprovalBean.getCompanyId()) + "," +
     useApprovalBean.getPartGroupNo() + "," +
     useApprovalBean.getLimitQuantityPeriod1() + "," +
     useApprovalBean.getDaysPeriod1() + "," +
     useApprovalBean.getLimitQuantityPeriod2() + "," +
     useApprovalBean.getDaysPeriod2() + "," +
     SqlHandler.delimitString(useApprovalBean.getInventoryGroup()) + "," +
     SqlHandler.delimitString(useApprovalBean.getProcessDesc()) + "," +
     useApprovalBean.getOrderQuantity() + "," +
     SqlHandler.delimitString(useApprovalBean.getOrderQuantityRule()) + "," +
     useApprovalBean.getEstimateOrderQuantityPeriod() + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(UseApprovalBean useApprovalBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(useApprovalBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(UseApprovalBean useApprovalBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_USER_GROUP_ID + "=" +
      SqlHandler.delimitString(useApprovalBean.getUserGroupId()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(useApprovalBean.getFacilityId()) + "," +
     ATTRIBUTE_FAC_PART_NO + "=" +
      SqlHandler.delimitString(useApprovalBean.getFacPartNo()) + "," +
     ATTRIBUTE_EXPIRE_DATE + "=" +
       DateHandler.getOracleToDateFunction(useApprovalBean.getExpireDate()) + "," +
     ATTRIBUTE_APPLICATION + "=" +
      SqlHandler.delimitString(useApprovalBean.getApplication()) + "," +
     ATTRIBUTE_APPROVAL_ID + "=" +
      StringHandler.nullIfZero(useApprovalBean.getApprovalId()) + "," +
     ATTRIBUTE_APPROVAL_STATUS + "=" +
      SqlHandler.delimitString(useApprovalBean.getApprovalStatus()) + "," +
     ATTRIBUTE_REVIEWED_DATE + "=" +
       DateHandler.getOracleToDateFunction(useApprovalBean.getReviewedDate()) + "," +
     ATTRIBUTE_APP_GROUP + "=" +
      SqlHandler.delimitString(useApprovalBean.getAppGroup()) + "," +
     ATTRIBUTE_CATALOG_ID + "=" +
      SqlHandler.delimitString(useApprovalBean.getCatalogId()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(useApprovalBean.getCompanyId()) + "," +
     ATTRIBUTE_PART_GROUP_NO + "=" +
      StringHandler.nullIfZero(useApprovalBean.getPartGroupNo()) + "," +
     ATTRIBUTE_LIMIT_QUANTITY_PERIOD1 + "=" +
       StringHandler.nullIfZero(useApprovalBean.getLimitQuantityPeriod1()) + "," +
     ATTRIBUTE_DAYS_PERIOD1 + "=" +
      StringHandler.nullIfZero(useApprovalBean.getDaysPeriod1()) + "," +
     ATTRIBUTE_LIMIT_QUANTITY_PERIOD2 + "=" +
       StringHandler.nullIfZero(useApprovalBean.getLimitQuantityPeriod2()) + "," +
     ATTRIBUTE_DAYS_PERIOD2 + "=" +
      StringHandler.nullIfZero(useApprovalBean.getDaysPeriod2()) + "," +
     ATTRIBUTE_INVENTORY_GROUP + "=" +
      SqlHandler.delimitString(useApprovalBean.getInventoryGroup()) + "," +
     ATTRIBUTE_PROCESS_DESC + "=" +
      SqlHandler.delimitString(useApprovalBean.getProcessDesc()) + "," +
     ATTRIBUTE_ORDER_QUANTITY + "=" +
      StringHandler.nullIfZero(useApprovalBean.getOrderQuantity()) + "," +
     ATTRIBUTE_ORDER_QUANTITY_RULE + "=" +
      SqlHandler.delimitString(useApprovalBean.getOrderQuantityRule()) + "," +
     ATTRIBUTE_ESTIMATE_ORDER_QUANTITY_PERIOD + "=" +
      StringHandler.nullIfZero(useApprovalBean.getEstimateOrderQuantityPeriod()) + " " +
     "where " + ATTRIBUTE_USER_GROUP_ID + "=" +
      useApprovalBean.getUserGroupId();
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
    Collection useApprovalBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      UseApprovalBean useApprovalBean = new UseApprovalBean();
      load(dataSetRow, useApprovalBean);
      useApprovalBeanColl.add(useApprovalBean);
    }
    return useApprovalBeanColl;
  }
}