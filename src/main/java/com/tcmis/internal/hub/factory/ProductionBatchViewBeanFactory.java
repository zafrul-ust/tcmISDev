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
import com.tcmis.internal.hub.beans.ProductionBatchViewBean;


/******************************************************************************
 * CLASSNAME: ProductionBatchViewBeanFactory <br>
 * @version: 1.0, Oct 3, 2007 <br>
 *****************************************************************************/


public class ProductionBatchViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_BATCH_ID = "BATCH_ID";
	public String ATTRIBUTE_RECIPE_ID = "RECIPE_ID";
	public String ATTRIBUTE_VESSEL_ID = "VESSEL_ID";
	public String ATTRIBUTE_PLANNED_YIELD_AMOUNT = "PLANNED_YIELD_AMOUNT";
	public String ATTRIBUTE_ACTUAL_YIELD_AMOUNT = "ACTUAL_YIELD_AMOUNT";
	public String ATTRIBUTE_YIELD_AMOUNT_UNIT = "YIELD_AMOUNT_UNIT";
	public String ATTRIBUTE_YIELD_PERCENTAGE = "YIELD_PERCENTAGE";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_PRODUCTION_BATCH_COST = "PRODUCTION_BATCH_COST";
	public String ATTRIBUTE_BATCH_START_DATE = "BATCH_START_DATE";
	public String ATTRIBUTE_BATCH_START_USER_ID = "BATCH_START_USER_ID";
	public String ATTRIBUTE_BATCH_START_USERNAME = "BATCH_START_USERNAME";
	public String ATTRIBUTE_PRODUCTION_DATE = "PRODUCTION_DATE";
	public String ATTRIBUTE_LOT_APPROVAL_DATE = "LOT_APPROVAL_DATE";
	public String ATTRIBUTE_LOT_APPROVAL_USERNAME = "LOT_APPROVAL_USERNAME";
	public String ATTRIBUTE_BATCH_CLOSE_DATE = "BATCH_CLOSE_DATE";
	public String ATTRIBUTE_BATCH_CLOSE_USERNAME = "BATCH_CLOSE_USERNAME";
	public String ATTRIBUTE_TRADE_NAME = "TRADE_NAME";
	public String ATTRIBUTE_MATERIAL_DESC = "MATERIAL_DESC";
	public String ATTRIBUTE_RECIPE_NAME = "RECIPE_NAME";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_VESSEL_NAME = "VESSEL_NAME";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_HUB_NAME = "HUB_NAME";
	public String ATTRIBUTE_HOME_COMPANY_ID = "HOME_COMPANY_ID";
	public String ATTRIBUTE_PICKLIST_ID = "PICKLIST_ID";
	public String ATTRIBUTE_QC_DATE = "QC_DATE";

	//table name
	public String TABLE = "PRODUCTION_BATCH_VIEW";


	//constructor
	public ProductionBatchViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("batchId")) {
			return ATTRIBUTE_BATCH_ID;
		}
		else if(attributeName.equals("recipeId")) {
			return ATTRIBUTE_RECIPE_ID;
		}
		else if(attributeName.equals("vesselId")) {
			return ATTRIBUTE_VESSEL_ID;
		}
		else if(attributeName.equals("plannedYieldAmount")) {
			return ATTRIBUTE_PLANNED_YIELD_AMOUNT;
		}
		else if(attributeName.equals("actualYieldAmount")) {
			return ATTRIBUTE_ACTUAL_YIELD_AMOUNT;
		}
		else if(attributeName.equals("yieldAmountUnit")) {
			return ATTRIBUTE_YIELD_AMOUNT_UNIT;
		}
		else if(attributeName.equals("yieldPercentage")) {
			return ATTRIBUTE_YIELD_PERCENTAGE;
		}
		else if(attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("productionBatchCost")) {
			return ATTRIBUTE_PRODUCTION_BATCH_COST;
		}
		else if(attributeName.equals("batchStartDate")) {
			return ATTRIBUTE_BATCH_START_DATE;
		}
		else if(attributeName.equals("batchStartUserId")) {
			return ATTRIBUTE_BATCH_START_USER_ID;
		}
		else if(attributeName.equals("batchStartUsername")) {
			return ATTRIBUTE_BATCH_START_USERNAME;
		}
		else if(attributeName.equals("productionDate")) {
			return ATTRIBUTE_PRODUCTION_DATE;
		}
		else if(attributeName.equals("lotApprovalDate")) {
			return ATTRIBUTE_LOT_APPROVAL_DATE;
		}
		else if(attributeName.equals("lotApprovalUsername")) {
			return ATTRIBUTE_LOT_APPROVAL_USERNAME;
		}
		else if(attributeName.equals("batchCloseDate")) {
			return ATTRIBUTE_BATCH_CLOSE_DATE;
		}
		else if(attributeName.equals("batchCloseUsername")) {
			return ATTRIBUTE_BATCH_CLOSE_USERNAME;
		}
		else if(attributeName.equals("tradeName")) {
			return ATTRIBUTE_TRADE_NAME;
		}
		else if(attributeName.equals("materialDesc")) {
			return ATTRIBUTE_MATERIAL_DESC;
		}
		else if(attributeName.equals("recipeName")) {
			return ATTRIBUTE_RECIPE_NAME;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("vesselName")) {
			return ATTRIBUTE_VESSEL_NAME;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("hubName")) {
			return ATTRIBUTE_HUB_NAME;
		}
		else if(attributeName.equals("homeCompanyId")) {
			return ATTRIBUTE_HOME_COMPANY_ID;
		}
		else if(attributeName.equals("picklistId")) {
			return ATTRIBUTE_PICKLIST_ID;
		}
		else if(attributeName.equals("qcDate")) {
			return ATTRIBUTE_QC_DATE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ProductionBatchViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ProductionBatchViewBean productionBatchViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("batchId", "SearchCriterion.EQUALS",
			"" + productionBatchViewBean.getBatchId());

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


	public int delete(ProductionBatchViewBean productionBatchViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("batchId", "SearchCriterion.EQUALS",
			"" + productionBatchViewBean.getBatchId());

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
	public int insert(ProductionBatchViewBean productionBatchViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(productionBatchViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ProductionBatchViewBean productionBatchViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_BATCH_ID + "," +
			ATTRIBUTE_RECIPE_ID + "," +
			ATTRIBUTE_VESSEL_ID + "," +
			ATTRIBUTE_PLANNED_YIELD_AMOUNT + "," +
			ATTRIBUTE_ACTUAL_YIELD_AMOUNT + "," +
			ATTRIBUTE_YIELD_AMOUNT_UNIT + "," +
			ATTRIBUTE_YIELD_PERCENTAGE + "," +
			ATTRIBUTE_MFG_LOT + "," +
			ATTRIBUTE_RECEIPT_ID + "," +
			ATTRIBUTE_PRODUCTION_BATCH_COST + "," +
			ATTRIBUTE_BATCH_START_DATE + "," +
			ATTRIBUTE_BATCH_START_USER_ID + "," +
			ATTRIBUTE_BATCH_START_USERNAME + "," +
			ATTRIBUTE_PRODUCTION_DATE + "," +
			ATTRIBUTE_LOT_APPROVAL_DATE + "," +
			ATTRIBUTE_LOT_APPROVAL_USERNAME + "," +
			ATTRIBUTE_BATCH_CLOSE_DATE + "," +
			ATTRIBUTE_BATCH_CLOSE_USERNAME + "," +
			ATTRIBUTE_TRADE_NAME + "," +
			ATTRIBUTE_MATERIAL_DESC + "," +
			ATTRIBUTE_RECIPE_NAME + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_VESSEL_NAME + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_HUB + "," +
			ATTRIBUTE_HUB_NAME + "," +
			ATTRIBUTE_HOME_COMPANY_ID + "," +
			ATTRIBUTE_PICKLIST_ID + "," +
			ATTRIBUTE_QC_DATE + ")" +
			" values (" +
			productionBatchViewBean.getBatchId() + "," +
			productionBatchViewBean.getRecipeId() + "," +
			productionBatchViewBean.getVesselId() + "," +
			productionBatchViewBean.getPlannedYieldAmount() + "," +
			productionBatchViewBean.getActualYieldAmount() + "," +
			SqlHandler.delimitString(productionBatchViewBean.getYieldAmountUnit()) + "," +
			productionBatchViewBean.getYieldPercentage() + "," +
			SqlHandler.delimitString(productionBatchViewBean.getMfgLot()) + "," +
			productionBatchViewBean.getReceiptId() + "," +
			productionBatchViewBean.getProductionBatchCost() + "," +
			DateHandler.getOracleToDateFunction(productionBatchViewBean.getBatchStartDate()) + "," +
			productionBatchViewBean.getBatchStartUserId() + "," +
			SqlHandler.delimitString(productionBatchViewBean.getBatchStartUsername()) + "," +
			DateHandler.getOracleToDateFunction(productionBatchViewBean.getProductionDate()) + "," +
			DateHandler.getOracleToDateFunction(productionBatchViewBean.getLotApprovalDate()) + "," +
			SqlHandler.delimitString(productionBatchViewBean.getLotApprovalUsername()) + "," +
			DateHandler.getOracleToDateFunction(productionBatchViewBean.getBatchCloseDate()) + "," +
			SqlHandler.delimitString(productionBatchViewBean.getBatchCloseUsername()) + "," +
			SqlHandler.delimitString(productionBatchViewBean.getTradeName()) + "," +
			SqlHandler.delimitString(productionBatchViewBean.getMaterialDesc()) + "," +
			SqlHandler.delimitString(productionBatchViewBean.getRecipeName()) + "," +
			productionBatchViewBean.getItemId() + "," +
			SqlHandler.delimitString(productionBatchViewBean.getVesselName()) + "," +
			SqlHandler.delimitString(productionBatchViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(productionBatchViewBean.getHub()) + "," +
			SqlHandler.delimitString(productionBatchViewBean.getHubName()) + "," +
			SqlHandler.delimitString(productionBatchViewBean.getHomeCompanyId()) + "," +
			productionBatchViewBean.getPicklistId() + "," +
			DateHandler.getOracleToDateFunction(productionBatchViewBean.getQcDate()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ProductionBatchViewBean productionBatchViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(productionBatchViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ProductionBatchViewBean productionBatchViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_BATCH_ID + "=" + 
				StringHandler.nullIfZero(productionBatchViewBean.getBatchId()) + "," +
			ATTRIBUTE_RECIPE_ID + "=" + 
				StringHandler.nullIfZero(productionBatchViewBean.getRecipeId()) + "," +
			ATTRIBUTE_VESSEL_ID + "=" + 
				StringHandler.nullIfZero(productionBatchViewBean.getVesselId()) + "," +
			ATTRIBUTE_PLANNED_YIELD_AMOUNT + "=" + 
				StringHandler.nullIfZero(productionBatchViewBean.getPlannedYieldAmount()) + "," +
			ATTRIBUTE_ACTUAL_YIELD_AMOUNT + "=" + 
				StringHandler.nullIfZero(productionBatchViewBean.getActualYieldAmount()) + "," +
			ATTRIBUTE_YIELD_AMOUNT_UNIT + "=" + 
				SqlHandler.delimitString(productionBatchViewBean.getYieldAmountUnit()) + "," +
			ATTRIBUTE_YIELD_PERCENTAGE + "=" + 
				StringHandler.nullIfZero(productionBatchViewBean.getYieldPercentage()) + "," +
			ATTRIBUTE_MFG_LOT + "=" + 
				SqlHandler.delimitString(productionBatchViewBean.getMfgLot()) + "," +
			ATTRIBUTE_RECEIPT_ID + "=" + 
				StringHandler.nullIfZero(productionBatchViewBean.getReceiptId()) + "," +
			ATTRIBUTE_PRODUCTION_BATCH_COST + "=" + 
				StringHandler.nullIfZero(productionBatchViewBean.getProductionBatchCost()) + "," +
			ATTRIBUTE_BATCH_START_DATE + "=" + 
				DateHandler.getOracleToDateFunction(productionBatchViewBean.getBatchStartDate()) + "," +
			ATTRIBUTE_BATCH_START_USER_ID + "=" + 
				StringHandler.nullIfZero(productionBatchViewBean.getBatchStartUserId()) + "," +
			ATTRIBUTE_BATCH_START_USERNAME + "=" + 
				SqlHandler.delimitString(productionBatchViewBean.getBatchStartUsername()) + "," +
			ATTRIBUTE_PRODUCTION_DATE + "=" + 
				DateHandler.getOracleToDateFunction(productionBatchViewBean.getProductionDate()) + "," +
			ATTRIBUTE_LOT_APPROVAL_DATE + "=" + 
				DateHandler.getOracleToDateFunction(productionBatchViewBean.getLotApprovalDate()) + "," +
			ATTRIBUTE_LOT_APPROVAL_USERNAME + "=" + 
				SqlHandler.delimitString(productionBatchViewBean.getLotApprovalUsername()) + "," +
			ATTRIBUTE_BATCH_CLOSE_DATE + "=" + 
				DateHandler.getOracleToDateFunction(productionBatchViewBean.getBatchCloseDate()) + "," +
			ATTRIBUTE_BATCH_CLOSE_USERNAME + "=" + 
				SqlHandler.delimitString(productionBatchViewBean.getBatchCloseUsername()) + "," +
			ATTRIBUTE_TRADE_NAME + "=" + 
				SqlHandler.delimitString(productionBatchViewBean.getTradeName()) + "," +
			ATTRIBUTE_MATERIAL_DESC + "=" + 
				SqlHandler.delimitString(productionBatchViewBean.getMaterialDesc()) + "," +
			ATTRIBUTE_RECIPE_NAME + "=" + 
				SqlHandler.delimitString(productionBatchViewBean.getRecipeName()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(productionBatchViewBean.getItemId()) + "," +
			ATTRIBUTE_VESSEL_NAME + "=" + 
				SqlHandler.delimitString(productionBatchViewBean.getVesselName()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(productionBatchViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_HUB + "=" + 
				SqlHandler.delimitString(productionBatchViewBean.getHub()) + "," +
			ATTRIBUTE_HUB_NAME + "=" + 
				SqlHandler.delimitString(productionBatchViewBean.getHubName()) + "," +
			ATTRIBUTE_HOME_COMPANY_ID + "=" + 
				SqlHandler.delimitString(productionBatchViewBean.getHomeCompanyId()) + "," +
			ATTRIBUTE_PICKLIST_ID + "=" + 
				StringHandler.nullIfZero(productionBatchViewBean.getPicklistId()) + "," +
			ATTRIBUTE_QC_DATE + "=" + 
				DateHandler.getOracleToDateFunction(productionBatchViewBean.getQcDate()) + " " +
			"where " + ATTRIBUTE_BATCH_ID + "=" +
				productionBatchViewBean.getBatchId();

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

		Collection productionBatchViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ProductionBatchViewBean productionBatchViewBean = new ProductionBatchViewBean();
			load(dataSetRow, productionBatchViewBean);
			productionBatchViewBeanColl.add(productionBatchViewBean);
		}

		return productionBatchViewBeanColl;
	}
}