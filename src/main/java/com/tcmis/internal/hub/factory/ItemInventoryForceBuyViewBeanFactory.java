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
import com.tcmis.internal.hub.beans.ItemInventoryForceBuyViewBean;


/******************************************************************************
 * CLASSNAME: ItemInventoryForceBuyViewBeanFactory <br>
 * @version: 1.0, Nov 4, 2009 <br>
 *****************************************************************************/


public class ItemInventoryForceBuyViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_STORAGE_AREA = "STORAGE_AREA";
	public String ATTRIBUTE_ISSUE_GENERATION = "ISSUE_GENERATION";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_RECEIPT_PROCESSING_METHOD = "RECEIPT_PROCESSING_METHOD";
	public String ATTRIBUTE_COUNT_UOM = "COUNT_UOM";
	public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_BILLING_METHOD = "BILLING_METHOD";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_PART_ON_HAND = "PART_ON_HAND";
	public String ATTRIBUTE_PART_IN_PURCHASING = "PART_IN_PURCHASING";
	public String ATTRIBUTE_ITEM_ON_HAND = "ITEM_ON_HAND";
	public String ATTRIBUTE_STOCKING_METHOD = "STOCKING_METHOD";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_PRIORITY = "PRIORITY";
	public String ATTRIBUTE_RECEIPT_PROCESSING_METHOD_DESC = "RECEIPT_PROCESSING_METHOD_DESC";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_TANK_NAME = "TANK_NAME";
	public String ATTRIBUTE_ALLOW_FORCE_BUY = "ALLOW_FORCE_BUY";
	public String ATTRIBUTE_REORDER_POINT = "REORDER_POINT";
	public String ATTRIBUTE_STOCKING_LEVEL = "STOCKING_LEVEL";
	public String ATTRIBUTE_REORDER_QUANTITY = "REORDER_QUANTITY";
	public String ATTRIBUTE_ITEM_PACKAGING = "ITEM_PACKAGING";
	public String ATTRIBUTE_PRICING = "PRICING";
	public String ATTRIBUTE_SPEC_LIST = "SPEC_LIST";

	//table name
	public String TABLE = "ITEM_INVENTORY_FORCE_BUY_VIEW";


	//constructor
	public ItemInventoryForceBuyViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("storageArea")) {
			return ATTRIBUTE_STORAGE_AREA;
		}
		else if(attributeName.equals("issueGeneration")) {
			return ATTRIBUTE_ISSUE_GENERATION;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("partGroupNo")) {
			return ATTRIBUTE_PART_GROUP_NO;
		}
		else if(attributeName.equals("receiptProcessingMethod")) {
			return ATTRIBUTE_RECEIPT_PROCESSING_METHOD;
		}
		else if(attributeName.equals("countUom")) {
			return ATTRIBUTE_COUNT_UOM;
		}
		else if(attributeName.equals("itemType")) {
			return ATTRIBUTE_ITEM_TYPE;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("billingMethod")) {
			return ATTRIBUTE_BILLING_METHOD;
		}
		else if(attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("partOnHand")) {
			return ATTRIBUTE_PART_ON_HAND;
		}
		else if(attributeName.equals("partInPurchasing")) {
			return ATTRIBUTE_PART_IN_PURCHASING;
		}
		else if(attributeName.equals("itemOnHand")) {
			return ATTRIBUTE_ITEM_ON_HAND;
		}
		else if(attributeName.equals("stockingMethod")) {
			return ATTRIBUTE_STOCKING_METHOD;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if(attributeName.equals("priority")) {
			return ATTRIBUTE_PRIORITY;
		}
		else if(attributeName.equals("receiptProcessingMethodDesc")) {
			return ATTRIBUTE_RECEIPT_PROCESSING_METHOD_DESC;
		}
		else if(attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if(attributeName.equals("tankName")) {
			return ATTRIBUTE_TANK_NAME;
		}
		else if(attributeName.equals("allowForceBuy")) {
			return ATTRIBUTE_ALLOW_FORCE_BUY;
		}
		else if(attributeName.equals("reorderPoint")) {
			return ATTRIBUTE_REORDER_POINT;
		}
		else if(attributeName.equals("stockingLevel")) {
			return ATTRIBUTE_STOCKING_LEVEL;
		}
		else if(attributeName.equals("reorderQuantity")) {
			return ATTRIBUTE_REORDER_QUANTITY;
		}
		else if(attributeName.equals("itemPackaging")) {
			return ATTRIBUTE_ITEM_PACKAGING;
		}
		else if(attributeName.equals("pricing")) {
			return ATTRIBUTE_PRICING;
		}
		else if(attributeName.equals("specList")) {
			return ATTRIBUTE_SPEC_LIST;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ItemInventoryForceBuyViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ItemInventoryForceBuyViewBean itemInventoryForceBuyViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
			"" + itemInventoryForceBuyViewBean.getInventoryGroup());

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


	public int delete(ItemInventoryForceBuyViewBean itemInventoryForceBuyViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
			"" + itemInventoryForceBuyViewBean.getInventoryGroup());

		return delete(criteria, conn);
	}
*/

	public int delete(SearchCriteria criteria)
		throws BaseException {

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


	public int delete(SearchCriteria criteria, Connection conn)
		throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " +
			getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//insert
	public int insert(ItemInventoryForceBuyViewBean itemInventoryForceBuyViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(itemInventoryForceBuyViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ItemInventoryForceBuyViewBean itemInventoryForceBuyViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_STORAGE_AREA + "," +
			ATTRIBUTE_ISSUE_GENERATION + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_PART_GROUP_NO + "," +
			ATTRIBUTE_RECEIPT_PROCESSING_METHOD + "," +
			ATTRIBUTE_COUNT_UOM + "," +
			ATTRIBUTE_ITEM_TYPE + "," +
			ATTRIBUTE_ITEM_DESC + "," +
			ATTRIBUTE_PACKAGING + "," +
			ATTRIBUTE_BILLING_METHOD + "," +
			ATTRIBUTE_CATALOG_COMPANY_ID + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_APPLICATION + "," +
			ATTRIBUTE_PART_ON_HAND + "," +
			ATTRIBUTE_PART_IN_PURCHASING + "," +
			ATTRIBUTE_ITEM_ON_HAND + "," +
			ATTRIBUTE_STOCKING_METHOD + "," +
			ATTRIBUTE_STATUS + "," +
			ATTRIBUTE_PRIORITY + "," +
			ATTRIBUTE_RECEIPT_PROCESSING_METHOD_DESC + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "," +
			ATTRIBUTE_TANK_NAME + "," +
			ATTRIBUTE_ALLOW_FORCE_BUY + "," +
			ATTRIBUTE_REORDER_POINT + "," +
			ATTRIBUTE_STOCKING_LEVEL + "," +
			ATTRIBUTE_REORDER_QUANTITY + "," +
			ATTRIBUTE_ITEM_PACKAGING + "," +
			ATTRIBUTE_PRICING + ")" +
			" values (" +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getStorageArea()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getIssueGeneration()) + "," +
			itemInventoryForceBuyViewBean.getItemId() + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getCatalogId()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getCatPartNo()) + "," +
			itemInventoryForceBuyViewBean.getPartGroupNo() + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getReceiptProcessingMethod()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getCountUom()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getItemType()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getItemDesc()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getPackaging()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getBillingMethod()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getCatalogCompanyId()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getFacilityId()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getApplication()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getPartOnHand()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getPartInPurchasing()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getItemOnHand()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getStockingMethod()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getStatus()) + "," +
			itemInventoryForceBuyViewBean.getPriority() + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getReceiptProcessingMethodDesc()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getInventoryGroupName()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getTankName()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getAllowForceBuy()) + "," +
			itemInventoryForceBuyViewBean.getReorderPoint() + "," +
			itemInventoryForceBuyViewBean.getStockingLevel() + "," +
			itemInventoryForceBuyViewBean.getReorderQuantity() + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getItemPackaging()) + "," +
			SqlHandler.delimitString(itemInventoryForceBuyViewBean.getPricing()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ItemInventoryForceBuyViewBean itemInventoryForceBuyViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(itemInventoryForceBuyViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ItemInventoryForceBuyViewBean itemInventoryForceBuyViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_STORAGE_AREA + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getStorageArea()) + "," +
			ATTRIBUTE_ISSUE_GENERATION + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getIssueGeneration()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(itemInventoryForceBuyViewBean.getItemId()) + "," +
			ATTRIBUTE_CATALOG_ID + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getCatalogId()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getCatPartNo()) + "," +
			ATTRIBUTE_PART_GROUP_NO + "=" + 
				StringHandler.nullIfZero(itemInventoryForceBuyViewBean.getPartGroupNo()) + "," +
			ATTRIBUTE_RECEIPT_PROCESSING_METHOD + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getReceiptProcessingMethod()) + "," +
			ATTRIBUTE_COUNT_UOM + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getCountUom()) + "," +
			ATTRIBUTE_ITEM_TYPE + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getItemType()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getItemDesc()) + "," +
			ATTRIBUTE_PACKAGING + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getPackaging()) + "," +
			ATTRIBUTE_BILLING_METHOD + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getBillingMethod()) + "," +
			ATTRIBUTE_CATALOG_COMPANY_ID + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getCatalogCompanyId()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getCompanyId()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getFacilityId()) + "," +
			ATTRIBUTE_APPLICATION + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getApplication()) + "," +
			ATTRIBUTE_PART_ON_HAND + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getPartOnHand()) + "," +
			ATTRIBUTE_PART_IN_PURCHASING + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getPartInPurchasing()) + "," +
			ATTRIBUTE_ITEM_ON_HAND + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getItemOnHand()) + "," +
			ATTRIBUTE_STOCKING_METHOD + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getStockingMethod()) + "," +
			ATTRIBUTE_STATUS + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getStatus()) + "," +
			ATTRIBUTE_PRIORITY + "=" + 
				StringHandler.nullIfZero(itemInventoryForceBuyViewBean.getPriority()) + "," +
			ATTRIBUTE_RECEIPT_PROCESSING_METHOD_DESC + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getReceiptProcessingMethodDesc()) + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getInventoryGroupName()) + "," +
			ATTRIBUTE_TANK_NAME + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getTankName()) + "," +
			ATTRIBUTE_ALLOW_FORCE_BUY + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getAllowForceBuy()) + "," +
			ATTRIBUTE_REORDER_POINT + "=" + 
				StringHandler.nullIfZero(itemInventoryForceBuyViewBean.getReorderPoint()) + "," +
			ATTRIBUTE_STOCKING_LEVEL + "=" + 
				StringHandler.nullIfZero(itemInventoryForceBuyViewBean.getStockingLevel()) + "," +
			ATTRIBUTE_REORDER_QUANTITY + "=" + 
				StringHandler.nullIfZero(itemInventoryForceBuyViewBean.getReorderQuantity()) + "," +
			ATTRIBUTE_ITEM_PACKAGING + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getItemPackaging()) + "," +
			ATTRIBUTE_PRICING + "=" + 
				SqlHandler.delimitString(itemInventoryForceBuyViewBean.getPricing()) + " " +
			"where " + ATTRIBUTE_INVENTORY_GROUP + "=" +
				itemInventoryForceBuyViewBean.getInventoryGroup();

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
		throws BaseException {

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
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection itemInventoryForceBuyViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ItemInventoryForceBuyViewBean itemInventoryForceBuyViewBean = new ItemInventoryForceBuyViewBean();
			load(dataSetRow, itemInventoryForceBuyViewBean);
			itemInventoryForceBuyViewBeanColl.add(itemInventoryForceBuyViewBean);
		}

		return itemInventoryForceBuyViewBeanColl;
	}
}