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
import com.tcmis.internal.hub.beans.ItemCountDisplayViewBean;


/******************************************************************************
 * CLASSNAME: ItemCountDisplayViewBeanFactory <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class ItemCountDisplayViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PICK_DATE = "PICK_DATE";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_COUNT_ID = "COUNT_ID";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_INVENTORY_QUANTITY = "INVENTORY_QUANTITY";
	public String ATTRIBUTE_COUNTED_QUANTITY = "COUNTED_QUANTITY";
	public String ATTRIBUTE_UOM = "UOM";
	public String ATTRIBUTE_CLOSED = "CLOSED";
	public String ATTRIBUTE_DATE_COUNTED = "DATE_COUNTED";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_DESCRIPTION = "DESCRIPTION";
	public String ATTRIBUTE_RECEIPT_PROCESSING_METHOD = "RECEIPT_PROCESSING_METHOD";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_CREDIT_AT_OVER_COUNT = "CREDIT_AT_OVER_COUNT";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_COUNT_TYPE = "COUNT_TYPE";
	public String ATTRIBUTE_LAST_DATE_OF_RECEIPT = "LAST_DATE_OF_RECEIPT";
	public String ATTRIBUTE_LAST_RECEIPT_QC_DATE = "LAST_RECEIPT_QC_DATE";
	public String ATTRIBUTE_START_DATE = "START_DATE";
	public String ATTRIBUTE_COUNT_STATUS = "COUNT_STATUS";
	public String ATTRIBUTE_PROCESS_STATEMENT = "PROCESS_STATEMENT";
	public String ATTRIBUTE_LAST_DATE_COUNTED = "LAST_DATE_COUNTED";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_IN_PURCHASING = "IN_PURCHASING";
	public String ATTRIBUTE_LAST_BIN = "LAST_BIN";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";

	//table name
	public String TABLE = "ITEM_COUNT_DISPLAY_VIEW";


	//constructor
	public ItemCountDisplayViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("pickDate")) {
			return ATTRIBUTE_PICK_DATE;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("countId")) {
			return ATTRIBUTE_COUNT_ID;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("inventoryQuantity")) {
			return ATTRIBUTE_INVENTORY_QUANTITY;
		}
		else if(attributeName.equals("countedQuantity")) {
			return ATTRIBUTE_COUNTED_QUANTITY;
		}
		else if(attributeName.equals("uom")) {
			return ATTRIBUTE_UOM;
		}
		else if(attributeName.equals("closed")) {
			return ATTRIBUTE_CLOSED;
		}
		else if(attributeName.equals("dateCounted")) {
			return ATTRIBUTE_DATE_COUNTED;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("description")) {
			return ATTRIBUTE_DESCRIPTION;
		}
		else if(attributeName.equals("receiptProcessingMethod")) {
			return ATTRIBUTE_RECEIPT_PROCESSING_METHOD;
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
		else if(attributeName.equals("creditAtOverCount")) {
			return ATTRIBUTE_CREDIT_AT_OVER_COUNT;
		}
		else if(attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("countType")) {
			return ATTRIBUTE_COUNT_TYPE;
		}
		else if(attributeName.equals("lastDateOfReceipt")) {
			return ATTRIBUTE_LAST_DATE_OF_RECEIPT;
		}
		else if(attributeName.equals("lastReceiptQcDate")) {
			return ATTRIBUTE_LAST_RECEIPT_QC_DATE;
		}
		else if(attributeName.equals("startDate")) {
			return ATTRIBUTE_START_DATE;
		}
		else if(attributeName.equals("countStatus")) {
			return ATTRIBUTE_COUNT_STATUS;
		}
		else if(attributeName.equals("processStatement")) {
			return ATTRIBUTE_PROCESS_STATEMENT;
		}
		else if(attributeName.equals("lastDateCounted")) {
			return ATTRIBUTE_LAST_DATE_COUNTED;
		}
		else if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("inPurchasing")) {
			return ATTRIBUTE_IN_PURCHASING;
		}
		else if(attributeName.equals("lastBin")) {
			return ATTRIBUTE_LAST_BIN;
		}
		else if(attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ItemCountDisplayViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ItemCountDisplayViewBean itemCountDisplayViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("pickDate", "SearchCriterion.EQUALS",
			"" + itemCountDisplayViewBean.getPickDate());

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


	public int delete(ItemCountDisplayViewBean itemCountDisplayViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("pickDate", "SearchCriterion.EQUALS",
			"" + itemCountDisplayViewBean.getPickDate());

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
	public int insert(ItemCountDisplayViewBean itemCountDisplayViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(itemCountDisplayViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ItemCountDisplayViewBean itemCountDisplayViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_PICK_DATE + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_COUNT_ID + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_INVENTORY_QUANTITY + "," +
			ATTRIBUTE_COUNTED_QUANTITY + "," +
			ATTRIBUTE_UOM + "," +
			ATTRIBUTE_CLOSED + "," +
			ATTRIBUTE_DATE_COUNTED + "," +
			ATTRIBUTE_PACKAGING + "," +
			ATTRIBUTE_DESCRIPTION + "," +
			ATTRIBUTE_RECEIPT_PROCESSING_METHOD + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_PART_GROUP_NO + "," +
			ATTRIBUTE_CREDIT_AT_OVER_COUNT + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_COUNT_TYPE + "," +
			ATTRIBUTE_LAST_DATE_OF_RECEIPT + "," +
			ATTRIBUTE_LAST_RECEIPT_QC_DATE + "," +
			ATTRIBUTE_START_DATE + "," +
			ATTRIBUTE_COUNT_STATUS + "," +
			ATTRIBUTE_PROCESS_STATEMENT + "," +
			ATTRIBUTE_LAST_DATE_COUNTED + "," +
			ATTRIBUTE_HUB + "," +
			ATTRIBUTE_IN_PURCHASING + "," +
			ATTRIBUTE_LAST_BIN + ")" +
			" values (" +
			DateHandler.getOracleToDateFunction(itemCountDisplayViewBean.getPickDate()) + "," +
			SqlHandler.delimitString(itemCountDisplayViewBean.getInventoryGroup()) + "," +
			itemCountDisplayViewBean.getCountId() + "," +
			SqlHandler.delimitString(itemCountDisplayViewBean.getItemId()) + "," +
			itemCountDisplayViewBean.getInventoryQuantity() + "," +
			itemCountDisplayViewBean.getCountedQuantity() + "," +
			SqlHandler.delimitString(itemCountDisplayViewBean.getUom()) + "," +
			SqlHandler.delimitString(itemCountDisplayViewBean.getClosed()) + "," +
			DateHandler.getOracleToDateFunction(itemCountDisplayViewBean.getDateCounted()) + "," +
			SqlHandler.delimitString(itemCountDisplayViewBean.getPackaging()) + "," +
			SqlHandler.delimitString(itemCountDisplayViewBean.getDescription()) + "," +
			SqlHandler.delimitString(itemCountDisplayViewBean.getReceiptProcessingMethod()) + "," +
			SqlHandler.delimitString(itemCountDisplayViewBean.getCatalogId()) + "," +
			SqlHandler.delimitString(itemCountDisplayViewBean.getCatPartNo()) + "," +
			itemCountDisplayViewBean.getPartGroupNo() + "," +
			SqlHandler.delimitString(itemCountDisplayViewBean.getCreditAtOverCount()) + "," +
			SqlHandler.delimitString(itemCountDisplayViewBean.getInventoryGroupName()) + "," +
			SqlHandler.delimitString(itemCountDisplayViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(itemCountDisplayViewBean.getCountType()) + "," +
			DateHandler.getOracleToDateFunction(itemCountDisplayViewBean.getLastDateOfReceipt()) + "," +
			DateHandler.getOracleToDateFunction(itemCountDisplayViewBean.getLastReceiptQcDate()) + "," +
			DateHandler.getOracleToDateFunction(itemCountDisplayViewBean.getStartDate()) + "," +
			SqlHandler.delimitString(itemCountDisplayViewBean.getCountStatus()) + "," +
			SqlHandler.delimitString(itemCountDisplayViewBean.getProcessStatement()) + "," +
			DateHandler.getOracleToDateFunction(itemCountDisplayViewBean.getLastDateCounted()) + "," +
			SqlHandler.delimitString(itemCountDisplayViewBean.getHub()) + "," +
			itemCountDisplayViewBean.getInPurchasing() + "," +
			SqlHandler.delimitString(itemCountDisplayViewBean.getLastBin()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ItemCountDisplayViewBean itemCountDisplayViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(itemCountDisplayViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ItemCountDisplayViewBean itemCountDisplayViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PICK_DATE + "=" + 
				DateHandler.getOracleToDateFunction(itemCountDisplayViewBean.getPickDate()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(itemCountDisplayViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_COUNT_ID + "=" + 
				StringHandler.nullIfZero(itemCountDisplayViewBean.getCountId()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				SqlHandler.delimitString(itemCountDisplayViewBean.getItemId()) + "," +
			ATTRIBUTE_INVENTORY_QUANTITY + "=" + 
				StringHandler.nullIfZero(itemCountDisplayViewBean.getInventoryQuantity()) + "," +
			ATTRIBUTE_COUNTED_QUANTITY + "=" + 
				StringHandler.nullIfZero(itemCountDisplayViewBean.getCountedQuantity()) + "," +
			ATTRIBUTE_UOM + "=" + 
				SqlHandler.delimitString(itemCountDisplayViewBean.getUom()) + "," +
			ATTRIBUTE_CLOSED + "=" + 
				SqlHandler.delimitString(itemCountDisplayViewBean.getClosed()) + "," +
			ATTRIBUTE_DATE_COUNTED + "=" + 
				DateHandler.getOracleToDateFunction(itemCountDisplayViewBean.getDateCounted()) + "," +
			ATTRIBUTE_PACKAGING + "=" + 
				SqlHandler.delimitString(itemCountDisplayViewBean.getPackaging()) + "," +
			ATTRIBUTE_DESCRIPTION + "=" + 
				SqlHandler.delimitString(itemCountDisplayViewBean.getDescription()) + "," +
			ATTRIBUTE_RECEIPT_PROCESSING_METHOD + "=" + 
				SqlHandler.delimitString(itemCountDisplayViewBean.getReceiptProcessingMethod()) + "," +
			ATTRIBUTE_CATALOG_ID + "=" + 
				SqlHandler.delimitString(itemCountDisplayViewBean.getCatalogId()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(itemCountDisplayViewBean.getCatPartNo()) + "," +
			ATTRIBUTE_PART_GROUP_NO + "=" + 
				StringHandler.nullIfZero(itemCountDisplayViewBean.getPartGroupNo()) + "," +
			ATTRIBUTE_CREDIT_AT_OVER_COUNT + "=" + 
				SqlHandler.delimitString(itemCountDisplayViewBean.getCreditAtOverCount()) + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "=" + 
				SqlHandler.delimitString(itemCountDisplayViewBean.getInventoryGroupName()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(itemCountDisplayViewBean.getCompanyId()) + "," +
			ATTRIBUTE_COUNT_TYPE + "=" + 
				SqlHandler.delimitString(itemCountDisplayViewBean.getCountType()) + "," +
			ATTRIBUTE_LAST_DATE_OF_RECEIPT + "=" + 
				DateHandler.getOracleToDateFunction(itemCountDisplayViewBean.getLastDateOfReceipt()) + "," +
			ATTRIBUTE_LAST_RECEIPT_QC_DATE + "=" + 
				DateHandler.getOracleToDateFunction(itemCountDisplayViewBean.getLastReceiptQcDate()) + "," +
			ATTRIBUTE_START_DATE + "=" + 
				DateHandler.getOracleToDateFunction(itemCountDisplayViewBean.getStartDate()) + "," +
			ATTRIBUTE_COUNT_STATUS + "=" + 
				SqlHandler.delimitString(itemCountDisplayViewBean.getCountStatus()) + "," +
			ATTRIBUTE_PROCESS_STATEMENT + "=" + 
				SqlHandler.delimitString(itemCountDisplayViewBean.getProcessStatement()) + "," +
			ATTRIBUTE_LAST_DATE_COUNTED + "=" + 
				DateHandler.getOracleToDateFunction(itemCountDisplayViewBean.getLastDateCounted()) + "," +
			ATTRIBUTE_HUB + "=" + 
				SqlHandler.delimitString(itemCountDisplayViewBean.getHub()) + "," +
			ATTRIBUTE_IN_PURCHASING + "=" + 
				StringHandler.nullIfZero(itemCountDisplayViewBean.getInPurchasing()) + "," +
			ATTRIBUTE_LAST_BIN + "=" + 
				SqlHandler.delimitString(itemCountDisplayViewBean.getLastBin()) + " " +
			"where " + ATTRIBUTE_PICK_DATE + "=" +
				itemCountDisplayViewBean.getPickDate();

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

		Collection itemCountDisplayViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ItemCountDisplayViewBean itemCountDisplayViewBean = new ItemCountDisplayViewBean();
			load(dataSetRow, itemCountDisplayViewBean);
			itemCountDisplayViewBeanColl.add(itemCountDisplayViewBean);
		}

		return itemCountDisplayViewBeanColl;
	}
}