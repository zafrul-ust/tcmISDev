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
import com.tcmis.internal.hub.beans.MinMaxLevelLogViewBean;

/******************************************************************************
 * CLASSNAME: MinMaxLevelLogViewBeanFactory <br>
 * @version: 1.0, Nov 3, 2006 <br>
 *****************************************************************************/

public class MinMaxLevelLogViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_HUB = "HUB";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
  public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_OLD_ORDER_POINT = "OLD_ORDER_POINT";
  public String ATTRIBUTE_OLD_STOCKING_LEVEL = "OLD_STOCKING_LEVEL";
  public String ATTRIBUTE_NEW_ORDER_POINT = "NEW_ORDER_POINT";
  public String ATTRIBUTE_NEW_STOCKING_LEVEL = "NEW_STOCKING_LEVEL";
  public String ATTRIBUTE_DATE_MODIFIED = "DATE_MODIFIED";
  public String ATTRIBUTE_MODIFIED_BY = "MODIFIED_BY";
  public String ATTRIBUTE_OLD_STOCKED = "OLD_STOCKED";
  public String ATTRIBUTE_NEW_STOCKED = "NEW_STOCKED";
  public String ATTRIBUTE_REMARKS = "REMARKS";
  public String ATTRIBUTE_OLD_LOOK_AHEAD = "OLD_LOOK_AHEAD";
  public String ATTRIBUTE_NEW_LOOK_AHEAD = "NEW_LOOK_AHEAD";
  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
  public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
  public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
  public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
  public String ATTRIBUTE_OLD_REORDER_QUANTITY = "OLD_REORDER_QUANTITY";
  public String ATTRIBUTE_NEW_REORDER_QUANTITY = "NEW_REORDER_QUANTITY";
  public String ATTRIBUTE_OLD_RECEIPT_PROCESSING_METHOD = "OLD_RECEIPT_PROCESSING_METHOD";
  public String ATTRIBUTE_NEW_RECEIPT_PROCESSING_METHOD = "NEW_RECEIPT_PROCESSING_METHOD";
  public String ATTRIBUTE_OLD_BILLING_METHOD = "OLD_BILLING_METHOD";
  public String ATTRIBUTE_NEW_BILLING_METHOD = "NEW_BILLING_METHOD";
  public String ATTRIBUTE_OLD_COUNT_UOM = "OLD_COUNT_UOM";
  public String ATTRIBUTE_NEW_COUNT_UOM = "NEW_COUNT_UOM";
  public String ATTRIBUTE_OLD_ALLOW_FORCE_BY = "OLD_ALLOW_FORCE_BY";
  public String ATTRIBUTE_NEW_ALLOW_FORCE_BY = "NEW_ALLOW_FORCE_BY";
  public String ATTRIBUTE_OLD_SPEC_CHECK_REQUIRED = "OLD_SPEC_CHECK_REQUIRED";
  public String ATTRIBUTE_NEW_SPEC_CHECK_REQUIRED = "NEW_SPEC_CHECK_REQUIRED";
  public String ATTRIBUTE_OLD_QUALITY_CONTROL = "OLD_QUALITY_CONTROL";
  public String ATTRIBUTE_NEW_QUALITY_CONTROL = "NEW_QUALITY_CONTROL";
  public String ATTRIBUTE_OLD_DROP_SHIP_OVERRIDE = "OLD_DROP_SHIP_OVERRIDE";
  public String ATTRIBUTE_NEW_DROP_SHIP_OVERRIDE = "NEW_DROP_SHIP_OVERRIDE";
  public String ATTRIBUTE_SPEC_LIST = "SPEC_LIST";
  public String ATTRIBUTE_MODIFIED_BY_NAME = "MODIFIED_BY_NAME";
  public String ATTRIBUTE_ISSUE_GENERATION = "ISSUE_GENERATION";
  public String ATTRIBUTE_COMPANY_NAME = "COMPANY_NAME";
  public String ATTRIBUTE_CATALOG_DESC = "CATALOG_DESC";
  
  public String ATTRIBUTE_OLD_LEVEL_HOLD_END_DATE = "OLD_LEVEL_HOLD_END_DATE";
  public String ATTRIBUTE_NEW_LEVEL_HOLD_END_DATE = "NEW_LEVEL_HOLD_END_DATE";
  public String ATTRIBUTE_OLD_PROJECTED_LEAD_TIME = "OLD_PROJECTED_LEAD_TIME";
  public String ATTRIBUTE_NEW_PROJECTED_LEAD_TIME = "NEW_PROJECTED_LEAD_TIME";

  //table name
  public String TABLE = "MIN_MAX_LEVEL_LOG_VIEW";

  //constructor
  public MinMaxLevelLogViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("hub")) {
      return ATTRIBUTE_HUB;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("catalogCompanyId")) {
      return ATTRIBUTE_CATALOG_COMPANY_ID;
    }
    else if (attributeName.equals("catalogId")) {
      return ATTRIBUTE_CATALOG_ID;
    }
    else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("oldOrderPoint")) {
      return ATTRIBUTE_OLD_ORDER_POINT;
    }
    else if (attributeName.equals("oldStockingLevel")) {
      return ATTRIBUTE_OLD_STOCKING_LEVEL;
    }
    else if (attributeName.equals("newOrderPoint")) {
      return ATTRIBUTE_NEW_ORDER_POINT;
    }
    else if (attributeName.equals("newStockingLevel")) {
      return ATTRIBUTE_NEW_STOCKING_LEVEL;
    }
    else if (attributeName.equals("dateModified")) {
      return ATTRIBUTE_DATE_MODIFIED;
    }
    else if (attributeName.equals("modifiedBy")) {
      return ATTRIBUTE_MODIFIED_BY;
    }
    else if (attributeName.equals("oldStocked")) {
      return ATTRIBUTE_OLD_STOCKED;
    }
    else if (attributeName.equals("newStocked")) {
      return ATTRIBUTE_NEW_STOCKED;
    }
    else if (attributeName.equals("remarks")) {
      return ATTRIBUTE_REMARKS;
    }
    else if (attributeName.equals("oldLookAhead")) {
      return ATTRIBUTE_OLD_LOOK_AHEAD;
    }
    else if (attributeName.equals("newLookAhead")) {
      return ATTRIBUTE_NEW_LOOK_AHEAD;
    }
    else if (attributeName.equals("inventoryGroup")) {
      return ATTRIBUTE_INVENTORY_GROUP;
    }
    else if (attributeName.equals("inventoryGroupName")) {
      return ATTRIBUTE_INVENTORY_GROUP_NAME;
    }
    else if (attributeName.equals("partGroupNo")) {
      return ATTRIBUTE_PART_GROUP_NO;
    }
    else if (attributeName.equals("itemId")) {
      return ATTRIBUTE_ITEM_ID;
    }
    else if (attributeName.equals("oldReorderQuantity")) {
      return ATTRIBUTE_OLD_REORDER_QUANTITY;
    }
    else if (attributeName.equals("newReorderQuantity")) {
      return ATTRIBUTE_NEW_REORDER_QUANTITY;
    }
    else if (attributeName.equals("oldReceiptProcessingMethod")) {
      return ATTRIBUTE_OLD_RECEIPT_PROCESSING_METHOD;
    }
    else if (attributeName.equals("newReceiptProcessingMethod")) {
      return ATTRIBUTE_NEW_RECEIPT_PROCESSING_METHOD;
    }
    else if (attributeName.equals("oldBillingMethod")) {
      return ATTRIBUTE_OLD_BILLING_METHOD;
    }
    else if (attributeName.equals("newBillingMethod")) {
      return ATTRIBUTE_NEW_BILLING_METHOD;
    }
    else if (attributeName.equals("oldCountUom")) {
      return ATTRIBUTE_OLD_COUNT_UOM;
    }
    else if (attributeName.equals("newCountUom")) {
      return ATTRIBUTE_NEW_COUNT_UOM;
    }
    else if (attributeName.equals("oldAllowForceBy")) {
      return ATTRIBUTE_OLD_ALLOW_FORCE_BY;
    }
    else if (attributeName.equals("newAllowForceBy")) {
      return ATTRIBUTE_NEW_ALLOW_FORCE_BY;
    }
    else if (attributeName.equals("oldSpecCheckRequired")) {
      return ATTRIBUTE_OLD_SPEC_CHECK_REQUIRED;
    }
    else if (attributeName.equals("newSpecCheckRequired")) {
      return ATTRIBUTE_NEW_SPEC_CHECK_REQUIRED;
    }
    else if (attributeName.equals("oldQualityControl")) {
      return ATTRIBUTE_OLD_QUALITY_CONTROL;
    }
    else if (attributeName.equals("newQualityControl")) {
      return ATTRIBUTE_NEW_QUALITY_CONTROL;
    }
    else if (attributeName.equals("oldDropshipOverride")) {
      return ATTRIBUTE_OLD_DROP_SHIP_OVERRIDE;
    }
    else if (attributeName.equals("newDropshipOverride")) {
      return ATTRIBUTE_NEW_DROP_SHIP_OVERRIDE;
    }
    else if (attributeName.equals("specList")) {
      return ATTRIBUTE_SPEC_LIST;
    }
    else if (attributeName.equals("modifiedByName")) {
      return ATTRIBUTE_MODIFIED_BY_NAME;
    }
    else if (attributeName.equals("issueGeneration")) {
      return ATTRIBUTE_ISSUE_GENERATION;
    }
    else if (attributeName.equals("companyName")) {
      return ATTRIBUTE_COMPANY_NAME;
    }
    else if (attributeName.equals("catalogDesc")) {
      return ATTRIBUTE_CATALOG_DESC;
    }
    else if (attributeName.equals("oldLevelHoldEndDate")) {
        return ATTRIBUTE_OLD_LEVEL_HOLD_END_DATE;
      }
    else if (attributeName.equals("newLevelHoldEndDate")) {
        return ATTRIBUTE_NEW_LEVEL_HOLD_END_DATE;
      }
    else if (attributeName.equals("oldProjectedLeadTime")) {
        return ATTRIBUTE_OLD_PROJECTED_LEAD_TIME;
      }
    else if (attributeName.equals("newProjectedLeadTime")) {
        return ATTRIBUTE_NEW_PROJECTED_LEAD_TIME;
      }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, MinMaxLevelLogViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(MinMaxLevelLogViewBean minMaxLevelLogViewBean)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("hub", "SearchCriterion.EQUALS",
     "" + minMaxLevelLogViewBean.getHub());
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
   public int delete(MinMaxLevelLogViewBean minMaxLevelLogViewBean, Connection conn)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("hub", "SearchCriterion.EQUALS",
     "" + minMaxLevelLogViewBean.getHub());
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

//insert
  public int insert(MinMaxLevelLogViewBean minMaxLevelLogViewBean) throws
      BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(minMaxLevelLogViewBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(MinMaxLevelLogViewBean minMaxLevelLogViewBean,
                    Connection conn) throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_HUB + "," +
        ATTRIBUTE_COMPANY_ID + "," +
        ATTRIBUTE_CATALOG_ID + "," +
        ATTRIBUTE_CAT_PART_NO + "," +
        ATTRIBUTE_OLD_ORDER_POINT + "," +
        ATTRIBUTE_OLD_STOCKING_LEVEL + "," +
        ATTRIBUTE_NEW_ORDER_POINT + "," +
        ATTRIBUTE_NEW_STOCKING_LEVEL + "," +
        ATTRIBUTE_DATE_MODIFIED + "," +
        ATTRIBUTE_MODIFIED_BY + "," +
        ATTRIBUTE_OLD_STOCKED + "," +
        ATTRIBUTE_NEW_STOCKED + "," +
        ATTRIBUTE_REMARKS + "," +
        ATTRIBUTE_OLD_LOOK_AHEAD + "," +
        ATTRIBUTE_NEW_LOOK_AHEAD + "," +
        ATTRIBUTE_INVENTORY_GROUP + "," +
        ATTRIBUTE_INVENTORY_GROUP_NAME + "," +
        ATTRIBUTE_PART_GROUP_NO + "," +
        ATTRIBUTE_ITEM_ID + "," +
        ATTRIBUTE_OLD_REORDER_QUANTITY + "," +
        ATTRIBUTE_NEW_REORDER_QUANTITY + "," +
        ATTRIBUTE_OLD_RECEIPT_PROCESSING_METHOD + "," +
        ATTRIBUTE_NEW_RECEIPT_PROCESSING_METHOD + "," +
        ATTRIBUTE_OLD_BILLING_METHOD + "," +
        ATTRIBUTE_NEW_BILLING_METHOD + "," +
        ATTRIBUTE_OLD_LEVEL_HOLD_END_DATE + "," +
        ATTRIBUTE_NEW_LEVEL_HOLD_END_DATE + "," +
        ATTRIBUTE_OLD_PROJECTED_LEAD_TIME + "," +
        ATTRIBUTE_NEW_PROJECTED_LEAD_TIME + "," +
        ATTRIBUTE_MODIFIED_BY_NAME + ")" +
        " values (" +
        SqlHandler.delimitString(minMaxLevelLogViewBean.getHub()) + "," +
        SqlHandler.delimitString(minMaxLevelLogViewBean.getCompanyId()) + "," +
        SqlHandler.delimitString(minMaxLevelLogViewBean.getCatalogId()) + "," +
        SqlHandler.delimitString(minMaxLevelLogViewBean.getCatPartNo()) + "," +
        minMaxLevelLogViewBean.getOldOrderPoint() + "," +
        minMaxLevelLogViewBean.getOldStockingLevel() + "," +
        minMaxLevelLogViewBean.getNewOrderPoint() + "," +
        minMaxLevelLogViewBean.getNewStockingLevel() + "," +
        DateHandler.getOracleToDateFunction(minMaxLevelLogViewBean.
                                            getDateModified()) + "," +
        minMaxLevelLogViewBean.getModifiedBy() + "," +
        SqlHandler.delimitString(minMaxLevelLogViewBean.getOldStocked()) + "," +
        SqlHandler.delimitString(minMaxLevelLogViewBean.getNewStocked()) + "," +
        SqlHandler.delimitString(minMaxLevelLogViewBean.getRemarks()) + "," +
        minMaxLevelLogViewBean.getOldLookAhead() + "," +
        minMaxLevelLogViewBean.getNewLookAhead() + "," +
        SqlHandler.delimitString(minMaxLevelLogViewBean.getInventoryGroup()) +
        "," +
        SqlHandler.delimitString(minMaxLevelLogViewBean.getInventoryGroupName()) +
        "," +
        minMaxLevelLogViewBean.getPartGroupNo() + "," +
        minMaxLevelLogViewBean.getItemId() + "," +
        minMaxLevelLogViewBean.getOldReorderQuantity() + "," +
        minMaxLevelLogViewBean.getNewReorderQuantity() + "," +
        SqlHandler.delimitString(minMaxLevelLogViewBean.
                                 getOldReceiptProcessingMethod()) + "," +
        SqlHandler.delimitString(minMaxLevelLogViewBean.
                                 getNewReceiptProcessingMethod()) + "," +
        SqlHandler.delimitString(minMaxLevelLogViewBean.getOldBillingMethod()) +
        "," +
        SqlHandler.delimitString(minMaxLevelLogViewBean.getNewBillingMethod()) +
        "," +
        minMaxLevelLogViewBean.getOldLevelHoldEndDate() + "," +
        minMaxLevelLogViewBean.getNewLevelHoldEndDate() + "," +
        minMaxLevelLogViewBean.getOldProjectedLeadTime() + "," +
        minMaxLevelLogViewBean.getNewProjectedLeadTime() + "," +
        SqlHandler.delimitString(minMaxLevelLogViewBean.getModifiedByName()) +
        ")";
    return sqlManager.update(conn, query);
  }

  /*
//update
     public int update(MinMaxLevelLogViewBean minMaxLevelLogViewBean)
      throws BaseException {
      Connection connection = null;
      int result = 0;
      try {
       connection = getDbManager().getConnection();
       result = update(minMaxLevelLogViewBean, connection);
      }
      finally {
       this.getDbManager().returnConnection(connection);
      }
      return result;
     }
     public int update(MinMaxLevelLogViewBean minMaxLevelLogViewBean, Connection conn)
      throws BaseException {
      String query  = "update " + TABLE + " set " +
       ATTRIBUTE_HUB + "=" +
        SqlHandler.delimitString(minMaxLevelLogViewBean.getHub()) + "," +
       ATTRIBUTE_COMPANY_ID + "=" +
        SqlHandler.delimitString(minMaxLevelLogViewBean.getCompanyId()) + "," +
       ATTRIBUTE_CATALOG_ID + "=" +
        SqlHandler.delimitString(minMaxLevelLogViewBean.getCatalogId()) + "," +
       ATTRIBUTE_CAT_PART_NO + "=" +
        SqlHandler.delimitString(minMaxLevelLogViewBean.getCatPartNo()) + "," +
       ATTRIBUTE_OLD_ORDER_POINT + "=" +
       StringHandler.nullIfZero(minMaxLevelLogViewBean.getOldOrderPoint()) + "," +
       ATTRIBUTE_OLD_STOCKING_LEVEL + "=" +
       StringHandler.nullIfZero(minMaxLevelLogViewBean.getOldStockingLevel()) + "," +
       ATTRIBUTE_NEW_ORDER_POINT + "=" +
       StringHandler.nullIfZero(minMaxLevelLogViewBean.getNewOrderPoint()) + "," +
       ATTRIBUTE_NEW_STOCKING_LEVEL + "=" +
       StringHandler.nullIfZero(minMaxLevelLogViewBean.getNewStockingLevel()) + "," +
       ATTRIBUTE_DATE_MODIFIED + "=" +
        DateHandler.getOracleToDateFunction(minMaxLevelLogViewBean.getDateModified()) + "," +
       ATTRIBUTE_MODIFIED_BY + "=" +
       StringHandler.nullIfZero(minMaxLevelLogViewBean.getModifiedBy()) + "," +
       ATTRIBUTE_OLD_STOCKED + "=" +
       SqlHandler.delimitString(minMaxLevelLogViewBean.getOldStocked()) + "," +
       ATTRIBUTE_NEW_STOCKED + "=" +
       SqlHandler.delimitString(minMaxLevelLogViewBean.getNewStocked()) + "," +
       ATTRIBUTE_REMARKS + "=" +
        SqlHandler.delimitString(minMaxLevelLogViewBean.getRemarks()) + "," +
       ATTRIBUTE_OLD_LOOK_AHEAD + "=" +
       StringHandler.nullIfZero(minMaxLevelLogViewBean.getOldLookAhead()) + "," +
       ATTRIBUTE_NEW_LOOK_AHEAD + "=" +
       StringHandler.nullIfZero(minMaxLevelLogViewBean.getNewLookAhead()) + "," +
       ATTRIBUTE_INVENTORY_GROUP + "=" +
       SqlHandler.delimitString(minMaxLevelLogViewBean.getInventoryGroup()) + "," +
       ATTRIBUTE_INVENTORY_GROUP_NAME + "=" +
       SqlHandler.delimitString(minMaxLevelLogViewBean.getInventoryGroupName()) + "," +
       ATTRIBUTE_PART_GROUP_NO + "=" +
       StringHandler.nullIfZero(minMaxLevelLogViewBean.getPartGroupNo()) + "," +
       ATTRIBUTE_ITEM_ID + "=" +
        StringHandler.nullIfZero(minMaxLevelLogViewBean.getItemId()) + "," +
       ATTRIBUTE_OLD_REORDER_QUANTITY + "=" +
       StringHandler.nullIfZero(minMaxLevelLogViewBean.getOldReorderQuantity()) + "," +
       ATTRIBUTE_NEW_REORDER_QUANTITY + "=" +
       StringHandler.nullIfZero(minMaxLevelLogViewBean.getNewReorderQuantity()) + "," +
       ATTRIBUTE_OLD_RECEIPT_PROCESSING_METHOD + "=" +
        SqlHandler.delimitString(minMaxLevelLogViewBean.getOldReceiptProcessingMethod()) + "," +
       ATTRIBUTE_NEW_RECEIPT_PROCESSING_METHOD + "=" +
        SqlHandler.delimitString(minMaxLevelLogViewBean.getNewReceiptProcessingMethod()) + "," +
       ATTRIBUTE_OLD_BILLING_METHOD + "=" +
       SqlHandler.delimitString(minMaxLevelLogViewBean.getOldBillingMethod()) + "," +
       ATTRIBUTE_NEW_BILLING_METHOD + "=" +
       SqlHandler.delimitString(minMaxLevelLogViewBean.getNewBillingMethod()) + "," +
       ATTRIBUTE_MODIFIED_BY_NAME + "=" +
       SqlHandler.delimitString(minMaxLevelLogViewBean.getModifiedByName()) + " " +
       "where " + ATTRIBUTE_HUB + "=" +
        minMaxLevelLogViewBean.getHub();
      return new SqlManager().update(conn, query);
     }
   */

  //select
  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(criteria, sortCriteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws
      BaseException {
    Collection minMaxLevelLogViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      MinMaxLevelLogViewBean minMaxLevelLogViewBean = new
          MinMaxLevelLogViewBean();
      load(dataSetRow, minMaxLevelLogViewBean);
      minMaxLevelLogViewBeanColl.add(minMaxLevelLogViewBean);
    }
    return minMaxLevelLogViewBeanColl;
  }
}