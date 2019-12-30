package com.tcmis.internal.hub.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;
import java.math.BigDecimal;
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
import com.tcmis.internal.hub.beans.CurrentMinMaxLevelViewBean;
import com.tcmis.internal.hub.factory.IgBillingMethodViewBeanFactory;
import com.tcmis.internal.hub.factory.IgReceiptProcessingViewBeanFactory;

/******************************************************************************
 * CLASSNAME: CurrentMinMaxLevelViewBeanFactory <br>
 * @version: 1.0, Oct 27, 2006 <br>
 *****************************************************************************/

public class CurrentMinMaxLevelViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_HUB = "HUB";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
  public String ATTRIBUTE_REORDER_POINT = "REORDER_POINT";
  public String ATTRIBUTE_STOCKING_LEVEL = "STOCKING_LEVEL";
  public String ATTRIBUTE_ESTABLISHED_STOCK_FLAG = "ESTABLISHED_STOCK_FLAG";
  public String ATTRIBUTE_STOCKING_METHOD = "STOCKING_METHOD";
  public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
  public String ATTRIBUTE_LOOK_AHEAD_DAYS = "LOOK_AHEAD_DAYS";
  public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
  public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
  public String ATTRIBUTE_CURRENT_STOCKING_METHOD = "CURRENT_STOCKING_METHOD";
  public String ATTRIBUTE_RECEIPT_PROCESSING_METHOD = "RECEIPT_PROCESSING_METHOD";
  public String ATTRIBUTE_REORDER_QUANTITY = "REORDER_QUANTITY";
  public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
  public String ATTRIBUTE_BILLING_METHOD = "BILLING_METHOD";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
  public String ATTRIBUTE_ISSUE_GENERATION = "ISSUE_GENERATION";
  public String ATTRIBUTE_SPEC_LIST = "SPEC_LIST";
  public String ATTRIBUTE_DROP_SHIP_OVERRIDE = "DROP_SHIP_OVERRIDE";
  public String ATTRIBUTE_COMPANY_NAME = "COMPANY_NAME";
  public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
  public String ATTRIBUTE_CATALOG_DESC = "CATALOG_DESC";
  public String ATTRIBUTE_LEVEL_HOLD_END_DATE = "LEVEL_HOLD_END_DATE";
  public String ATTRIBUTE_PROJECTED_LEAD_TIME = "PROJECTED_LEAD_TIME";

  //table name
  public String TABLE = "CURRENT_MIN_MAX_LEVEL_VIEW";

  //constructor
  public CurrentMinMaxLevelViewBeanFactory(DbManager dbManager) {
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
    else if (attributeName.equals("catalogId")) {
      return ATTRIBUTE_CATALOG_ID;
    }
    else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("inventoryGroup")) {
      return ATTRIBUTE_INVENTORY_GROUP;
    }
    else if (attributeName.equals("reorderPoint")) {
      return ATTRIBUTE_REORDER_POINT;
    }
    else if (attributeName.equals("stockingLevel")) {
      return ATTRIBUTE_STOCKING_LEVEL;
    }
    else if (attributeName.equals("establishedStockFlag")) {
      return ATTRIBUTE_ESTABLISHED_STOCK_FLAG;
    }
    else if (attributeName.equals("stockingMethod")) {
      return ATTRIBUTE_STOCKING_METHOD;
    }
    else if (attributeName.equals("partGroupNo")) {
      return ATTRIBUTE_PART_GROUP_NO;
    }
    else if (attributeName.equals("lookAheadDays")) {
      return ATTRIBUTE_LOOK_AHEAD_DAYS;
    }
    else if (attributeName.equals("itemId")) {
      return ATTRIBUTE_ITEM_ID;
    }
    else if (attributeName.equals("itemDesc")) {
      return ATTRIBUTE_ITEM_DESC;
    }
    else if (attributeName.equals("currentStockingMethod")) {
      return ATTRIBUTE_CURRENT_STOCKING_METHOD;
    }
    else if (attributeName.equals("receiptProcessingMethod")) {
      return ATTRIBUTE_RECEIPT_PROCESSING_METHOD;
    }
    else if (attributeName.equals("reorderQuantity")) {
      return ATTRIBUTE_REORDER_QUANTITY;
    }
    else if (attributeName.equals("inventoryGroupName")) {
      return ATTRIBUTE_INVENTORY_GROUP_NAME;
    }
    else if (attributeName.equals("billingMethod")) {
      return ATTRIBUTE_BILLING_METHOD;
    }
    else if (attributeName.equals("partDescription")) {
      return ATTRIBUTE_PART_DESCRIPTION;
    }
    else if (attributeName.equals("issueGeneration")) {
        return ATTRIBUTE_ISSUE_GENERATION;
    }
    else if (attributeName.equals("specList")) {
        return ATTRIBUTE_SPEC_LIST;
    }
    else if (attributeName.equals("dropShipOverride")) {
        return ATTRIBUTE_DROP_SHIP_OVERRIDE;
    }
    else if (attributeName.equals("companyName")) {
        return ATTRIBUTE_COMPANY_NAME;
    }
	else if (attributeName.equals("catalogCompanyId")) {
		 return ATTRIBUTE_CATALOG_COMPANY_ID;
    } 
	else if (attributeName.equals("catalogDesc")) {
		 return ATTRIBUTE_CATALOG_DESC;
    } 
	else if (attributeName.equals("levelHoldEndDate")){
		return ATTRIBUTE_LEVEL_HOLD_END_DATE;
	}
	else if (attributeName.equals("projectedLeadTime")) {
		return ATTRIBUTE_PROJECTED_LEAD_TIME;
	}
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, CurrentMinMaxLevelViewBean.class);
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
    Collection currentMinMaxLevelViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CurrentMinMaxLevelViewBean currentMinMaxLevelViewBean = new
          CurrentMinMaxLevelViewBean();
      load(dataSetRow, currentMinMaxLevelViewBean);
      currentMinMaxLevelViewBeanColl.add(currentMinMaxLevelViewBean);
    }
    return currentMinMaxLevelViewBeanColl;
  }

	//select
	public Collection selectWithChildren(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectWithChildren(criteria, sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	 public Collection selectWithChildren(SearchCriteria criteria,
		SortCriteria sortCriteria, Connection conn) throws
		BaseException {
		Collection currentMinMaxLevelViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		IgBillingMethodViewBeanFactory igBillingMethodViewBeanFactory = new
		 IgBillingMethodViewBeanFactory(getDbManager());
		IgReceiptProcessingViewBeanFactory igReceiptProcessingViewBeanFactory = new
		 IgReceiptProcessingViewBeanFactory(getDbManager());

    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CurrentMinMaxLevelViewBean currentMinMaxLevelViewBean = new
          CurrentMinMaxLevelViewBean();
      load(dataSetRow, currentMinMaxLevelViewBean);
      if("Item Counting".equalsIgnoreCase(currentMinMaxLevelViewBean.getIssueGeneration())) {
        SearchCriteria crit = new SearchCriteria("hub", SearchCriterion.EQUALS,
                                                 currentMinMaxLevelViewBean.getHub());
        crit.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
                          currentMinMaxLevelViewBean.getInventoryGroup());

        currentMinMaxLevelViewBean.setIgBillingMethodViewBeanCollection(
            igBillingMethodViewBeanFactory.selectMinMax(crit));
        currentMinMaxLevelViewBean.setIgReceiptProcessingViewBeanCollection(
            igReceiptProcessingViewBeanFactory.selectMinMax(crit));
      }
      currentMinMaxLevelViewBeanColl.add(currentMinMaxLevelViewBean);
    }

    return currentMinMaxLevelViewBeanColl;
  }
}