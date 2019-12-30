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
import com.tcmis.internal.hub.beans.ReceivingViewBean;
import com.tcmis.internal.hub.beans.RepackageBatchViewBean;


/******************************************************************************
 * CLASSNAME: RepackageBatchViewBeanFactory <br>
 * @version: 1.0, Oct 4, 2007 <br>
 *****************************************************************************/


public class RepackageBatchViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_BATCH_ID = "BATCH_ID";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_PLANNED_YIELD_AMOUNT = "PLANNED_YIELD_AMOUNT";
	public String ATTRIBUTE_YIELD_AMOUNT_UNIT = "YIELD_AMOUNT_UNIT";
	public String ATTRIBUTE_ORIGINAL_ITEM_ID = "ORIGINAL_ITEM_ID";
	public String ATTRIBUTE_VESSEL_NAME = "VESSEL_NAME";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
	public String ATTRIBUTE_QUANTITY_AVAILABLE = "QUANTITY_AVAILABLE";
	public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
	public String ATTRIBUTE_PURCHASING_UNITS_PER_ITEM = "PURCHASING_UNITS_PER_ITEM";
	public String ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE = "PURCHASING_UNIT_OF_MEASURE";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_ITEM_PKG = "ITEM_PKG";
	public String ATTRIBUTE_MY_ITEM_PKG = "MY_ITEM_PKG";
	public String ATTRIBUTE_CAPACITY = "CAPACITY";
	public String ATTRIBUTE_CAPACITY_UNIT = "CAPACITY_UNIT";
	public String ATTRIBUTE_CLOSEABLE = "CLOSEABLE";

	//table name
	public String TABLE = "REPACKAGE_BATCH_VIEW";


	//constructor
	public RepackageBatchViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("batchId")) {
			return ATTRIBUTE_BATCH_ID;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("plannedYieldAmount")) {
			return ATTRIBUTE_PLANNED_YIELD_AMOUNT;
		}
		else if(attributeName.equals("yieldAmountUnit")) {
			return ATTRIBUTE_YIELD_AMOUNT_UNIT;
		}
		else if(attributeName.equals("originalItemId")) {
			return ATTRIBUTE_ORIGINAL_ITEM_ID;
		}
		else if(attributeName.equals("vesselName")) {
			return ATTRIBUTE_VESSEL_NAME;
		}
		else if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if(attributeName.equals("expireDate")) {
			return ATTRIBUTE_EXPIRE_DATE;
		}
		else if(attributeName.equals("quantityAvailable")) {
			return ATTRIBUTE_QUANTITY_AVAILABLE;
		}
		else if(attributeName.equals("itemType")) {
			return ATTRIBUTE_ITEM_TYPE;
		}
		else if(attributeName.equals("purchasingUnitsPerItem")) {
			return ATTRIBUTE_PURCHASING_UNITS_PER_ITEM;
		}
		else if(attributeName.equals("purchasingUnitOfMeasure")) {
			return ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("itemPkg")) {
			return ATTRIBUTE_ITEM_PKG;
		}
		else if(attributeName.equals("myItemPkg")) {
			return ATTRIBUTE_MY_ITEM_PKG;
		}
		else if(attributeName.equals("capacity")) {
			return ATTRIBUTE_CAPACITY;
		}
		else if(attributeName.equals("capacityUnit")) {
			return ATTRIBUTE_CAPACITY_UNIT;
		}
		else if(attributeName.equals("closeable")) {
			return ATTRIBUTE_CLOSEABLE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, RepackageBatchViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(RepackageBatchViewBean repackageBatchViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("batchId", "SearchCriterion.EQUALS",
			"" + repackageBatchViewBean.getBatchId());

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


	public int delete(RepackageBatchViewBean repackageBatchViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("batchId", "SearchCriterion.EQUALS",
			"" + repackageBatchViewBean.getBatchId());

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
	public int insert(RepackageBatchViewBean repackageBatchViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(repackageBatchViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(RepackageBatchViewBean repackageBatchViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_BATCH_ID + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_PLANNED_YIELD_AMOUNT + "," +
			ATTRIBUTE_YIELD_AMOUNT_UNIT + "," +
			ATTRIBUTE_ORIGINAL_ITEM_ID + "," +
			ATTRIBUTE_VESSEL_NAME + "," +
			ATTRIBUTE_HUB + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_RECEIPT_ID + "," +
			ATTRIBUTE_MFG_LOT + "," +
			ATTRIBUTE_EXPIRE_DATE + "," +
			ATTRIBUTE_QUANTITY_AVAILABLE + "," +
			ATTRIBUTE_ITEM_TYPE + "," +
			ATTRIBUTE_PURCHASING_UNITS_PER_ITEM + "," +
			ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE + "," +
			ATTRIBUTE_ITEM_DESC + "," +
			ATTRIBUTE_ITEM_PKG + "," +
			ATTRIBUTE_MY_ITEM_PKG + "," +
			ATTRIBUTE_CAPACITY + "," +
			ATTRIBUTE_CAPACITY_UNIT + "," +
			ATTRIBUTE_CLOSEABLE + ")" +
			" values (" +
			repackageBatchViewBean.getBatchId() + "," +
			repackageBatchViewBean.getItemId() + "," +
			repackageBatchViewBean.getPlannedYieldAmount() + "," +
			SqlHandler.delimitString(repackageBatchViewBean.getYieldAmountUnit()) + "," +
			repackageBatchViewBean.getOriginalItemId() + "," +
			SqlHandler.delimitString(repackageBatchViewBean.getVesselName()) + "," +
			SqlHandler.delimitString(repackageBatchViewBean.getHub()) + "," +
			SqlHandler.delimitString(repackageBatchViewBean.getInventoryGroup()) + "," +
			repackageBatchViewBean.getReceiptId() + "," +
			SqlHandler.delimitString(repackageBatchViewBean.getMfgLot()) + "," +
			DateHandler.getOracleToDateFunction(repackageBatchViewBean.getExpireDate()) + "," +
			repackageBatchViewBean.getQuantityAvailable() + "," +
			SqlHandler.delimitString(repackageBatchViewBean.getItemType()) + "," +
			repackageBatchViewBean.getPurchasingUnitsPerItem() + "," +
			SqlHandler.delimitString(repackageBatchViewBean.getPurchasingUnitOfMeasure()) + "," +
			SqlHandler.delimitString(repackageBatchViewBean.getItemDesc()) + "," +
			SqlHandler.delimitString(repackageBatchViewBean.getItemPkg()) + "," +
			SqlHandler.delimitString(repackageBatchViewBean.getMyItemPkg()) + "," +
			repackageBatchViewBean.getCapacity() + "," +
			SqlHandler.delimitString(repackageBatchViewBean.getCapacityUnit()) + "," +
			SqlHandler.delimitString(repackageBatchViewBean.getCloseable()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(RepackageBatchViewBean repackageBatchViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(repackageBatchViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(RepackageBatchViewBean repackageBatchViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_BATCH_ID + "=" + 
				StringHandler.nullIfZero(repackageBatchViewBean.getBatchId()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(repackageBatchViewBean.getItemId()) + "," +
			ATTRIBUTE_PLANNED_YIELD_AMOUNT + "=" + 
				StringHandler.nullIfZero(repackageBatchViewBean.getPlannedYieldAmount()) + "," +
			ATTRIBUTE_YIELD_AMOUNT_UNIT + "=" + 
				SqlHandler.delimitString(repackageBatchViewBean.getYieldAmountUnit()) + "," +
			ATTRIBUTE_ORIGINAL_ITEM_ID + "=" + 
				StringHandler.nullIfZero(repackageBatchViewBean.getOriginalItemId()) + "," +
			ATTRIBUTE_VESSEL_NAME + "=" + 
				SqlHandler.delimitString(repackageBatchViewBean.getVesselName()) + "," +
			ATTRIBUTE_HUB + "=" + 
				SqlHandler.delimitString(repackageBatchViewBean.getHub()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(repackageBatchViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_RECEIPT_ID + "=" + 
				StringHandler.nullIfZero(repackageBatchViewBean.getReceiptId()) + "," +
			ATTRIBUTE_MFG_LOT + "=" + 
				SqlHandler.delimitString(repackageBatchViewBean.getMfgLot()) + "," +
			ATTRIBUTE_EXPIRE_DATE + "=" + 
				DateHandler.getOracleToDateFunction(repackageBatchViewBean.getExpireDate()) + "," +
			ATTRIBUTE_QUANTITY_AVAILABLE + "=" + 
				StringHandler.nullIfZero(repackageBatchViewBean.getQuantityAvailable()) + "," +
			ATTRIBUTE_ITEM_TYPE + "=" + 
				SqlHandler.delimitString(repackageBatchViewBean.getItemType()) + "," +
			ATTRIBUTE_PURCHASING_UNITS_PER_ITEM + "=" + 
				StringHandler.nullIfZero(repackageBatchViewBean.getPurchasingUnitsPerItem()) + "," +
			ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE + "=" + 
				SqlHandler.delimitString(repackageBatchViewBean.getPurchasingUnitOfMeasure()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" + 
				SqlHandler.delimitString(repackageBatchViewBean.getItemDesc()) + "," +
			ATTRIBUTE_ITEM_PKG + "=" + 
				SqlHandler.delimitString(repackageBatchViewBean.getItemPkg()) + "," +
			ATTRIBUTE_MY_ITEM_PKG + "=" + 
				SqlHandler.delimitString(repackageBatchViewBean.getMyItemPkg()) + "," +
			ATTRIBUTE_CAPACITY + "=" + 
				StringHandler.nullIfZero(repackageBatchViewBean.getCapacity()) + "," +
			ATTRIBUTE_CAPACITY_UNIT + "=" + 
				SqlHandler.delimitString(repackageBatchViewBean.getCapacityUnit()) + "," +
			ATTRIBUTE_CLOSEABLE + "=" + 
				SqlHandler.delimitString(repackageBatchViewBean.getCloseable()) + " " +
			"where " + ATTRIBUTE_BATCH_ID + "=" +
				repackageBatchViewBean.getBatchId();

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

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
	public Collection select(SearchCriteria criteria, Connection conn)
		throws BaseException {

		Collection repackageBatchViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();
		ReceiptItemPriorBinViewBeanFactory factory = new
		 ReceiptItemPriorBinViewBeanFactory(getDbManager());
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			RepackageBatchViewBean repackageBatchViewBean = new RepackageBatchViewBean();
			load(dataSetRow, repackageBatchViewBean);
//			get collection of ReceiptItemPriorViewBeans
			 SearchCriteria childCriteria = new SearchCriteria();
			 childCriteria.addCriterion("itemId", SearchCriterion.EQUALS,
				"" + repackageBatchViewBean.getItemId().intValue() + "");
			 childCriteria.addCriterion("hub", SearchCriterion.EQUALS,
					 repackageBatchViewBean.getHub());
			 childCriteria.addCriterion("status", SearchCriterion.EQUALS, "A");
			 childCriteria.addCriterion("onSite", SearchCriterion.EQUALS, "Y");
			 repackageBatchViewBean.setReceiptItemPriorBinViewBeanCollection(factory.select(
				childCriteria));
			repackageBatchViewBeanColl.add(repackageBatchViewBean);
		}
		
		return repackageBatchViewBeanColl;
	}
}