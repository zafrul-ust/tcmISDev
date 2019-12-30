package com.tcmis.client.report.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.report.beans.DailyPartReceiptSumViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: DailyPartReceiptSumViewBeanFactory <br>
 * @version: 1.0, Nov 16, 2005 <br>
 *****************************************************************************/

public class DailyPartReceiptSumViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_DAILY_DATE = "DAILY_DATE";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
  public String ATTRIBUTE_REORDER_POINT = "REORDER_POINT";
  public String ATTRIBUTE_STOCKING_LEVEL = "STOCKING_LEVEL";
  public String ATTRIBUTE_LOW_ALARM = "LOW_ALARM";
  public String ATTRIBUTE_HIGH_ALARM = "HIGH_ALARM";
  public String ATTRIBUTE_CAPACITY = "CAPACITY";
  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
  public String ATTRIBUTE_QUANTITY = "QUANTITY";
  public String ATTRIBUTE_PACKAGING = "PACKAGING";
  public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";

  //table name
  public String TABLE = "DAILY_PART_RECEIPT_SUM_VIEW";

  //constructor
  public DailyPartReceiptSumViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("dailyDate")) {
      return ATTRIBUTE_DAILY_DATE;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("catalogId")) {
      return ATTRIBUTE_CATALOG_ID;
    }
    else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("partGroupNo")) {
      return ATTRIBUTE_PART_GROUP_NO;
    }
    else if (attributeName.equals("reorderPoint")) {
      return ATTRIBUTE_REORDER_POINT;
    }
    else if (attributeName.equals("stockingLevel")) {
      return ATTRIBUTE_STOCKING_LEVEL;
    }
    else if (attributeName.equals("lowAlarm")) {
      return ATTRIBUTE_LOW_ALARM;
    }
    else if (attributeName.equals("highAlarm")) {
      return ATTRIBUTE_HIGH_ALARM;
    }
    else if (attributeName.equals("capacity")) {
      return ATTRIBUTE_CAPACITY;
    }
    else if (attributeName.equals("inventoryGroup")) {
      return ATTRIBUTE_INVENTORY_GROUP;
    }
    else if (attributeName.equals("quantity")) {
      return ATTRIBUTE_QUANTITY;
    }
    else if (attributeName.equals("packaging")) {
      return ATTRIBUTE_PACKAGING;
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
    return super.getType(attributeName, DailyPartReceiptSumViewBean.class);
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

  public int delete(SearchCriteria criteria, Connection conn) throws BaseException {
    String sqlQuery = " delete from " + TABLE + " " +
        getWhereClause(criteria);
    return new SqlManager().update(conn, sqlQuery);
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

  public Collection select(SearchCriteria criteria, Connection conn) throws BaseException {
    Collection dailyPartReceiptSumViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      DailyPartReceiptSumViewBean dailyPartReceiptSumViewBean = new
          DailyPartReceiptSumViewBean();
      load(dataSetRow, dailyPartReceiptSumViewBean);
      dailyPartReceiptSumViewBeanColl.add(dailyPartReceiptSumViewBean);
    }
    return dailyPartReceiptSumViewBeanColl;
  }
}