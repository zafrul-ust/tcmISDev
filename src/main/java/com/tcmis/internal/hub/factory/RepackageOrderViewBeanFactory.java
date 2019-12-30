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
import com.tcmis.internal.hub.beans.RepackageOrderViewBean;


/******************************************************************************
 * CLASSNAME: RepackageOrderViewBeanFactory <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class RepackageOrderViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ORDER_QUANTITY = "ORDER_QUANTITY";
	public String ATTRIBUTE_ORIGINAL_ITEM_ID = "ORIGINAL_ITEM_ID";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_BIN = "BIN";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
	public String ATTRIBUTE_QUANTITY_AVAILABLE = "QUANTITY_AVAILABLE";
	public String ATTRIBUTE_REPACKAGE_QUANTITY_AVAILABLE = "REPACKAGE_QUANTITY_AVAILABLE";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_ITEM_PKG = "ITEM_PKG";
	public String ATTRIBUTE_MD_ITEM_PKG = "MD_ITEM_PKG";
	public String ATTRIBUTE_MA_ITEM_PKG = "MA_ITEM_PKG";
	public String ATTRIBUTE_SEARCH_TEXT = "SEARCH_TEXT";
	public String ATTRIBUTE_TAP_AVAILABLE = "TAP_AVAILABLE";
	public String ATTRIBUTE_CLOSEABLE = "CLOSEABLE";

	//table name
	public String TABLE = "REPACKAGE_ORDER_VIEW";


	//constructor
	public RepackageOrderViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("orderQuantity")) {
			return ATTRIBUTE_ORDER_QUANTITY;
		}
		else if(attributeName.equals("originalItemId")) {
			return ATTRIBUTE_ORIGINAL_ITEM_ID;
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
		else if(attributeName.equals("bin")) {
			return ATTRIBUTE_BIN;
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
		else if(attributeName.equals("repackageQuantityAvailable")) {
			return ATTRIBUTE_REPACKAGE_QUANTITY_AVAILABLE;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("itemPkg")) {
			return ATTRIBUTE_ITEM_PKG;
		}
		else if(attributeName.equals("mdItemPkg")) {
			return ATTRIBUTE_MD_ITEM_PKG;
		}
		else if(attributeName.equals("maItemPkg")) {
			return ATTRIBUTE_MA_ITEM_PKG;
		}
		else if(attributeName.equals("searchText")) {
			return ATTRIBUTE_SEARCH_TEXT;
		}
		else if(attributeName.equals("tapAvailable")) {
			return ATTRIBUTE_TAP_AVAILABLE;
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
		return super.getType(attributeName, RepackageOrderViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(RepackageOrderViewBean repackageOrderViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
			"" + repackageOrderViewBean.getPrNumber());

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


	public int delete(RepackageOrderViewBean repackageOrderViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
			"" + repackageOrderViewBean.getPrNumber());

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
	public int insert(RepackageOrderViewBean repackageOrderViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(repackageOrderViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(RepackageOrderViewBean repackageOrderViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_ORDER_QUANTITY + "," +
			ATTRIBUTE_ORIGINAL_ITEM_ID + "," +
			ATTRIBUTE_HUB + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_RECEIPT_ID + "," +
			ATTRIBUTE_BIN + "," +
			ATTRIBUTE_MFG_LOT + "," +
			ATTRIBUTE_EXPIRE_DATE + "," +
			ATTRIBUTE_QUANTITY_AVAILABLE + "," +
			ATTRIBUTE_REPACKAGE_QUANTITY_AVAILABLE + "," +
			ATTRIBUTE_ITEM_DESC + "," +
			ATTRIBUTE_ITEM_PKG + "," +
			ATTRIBUTE_MD_ITEM_PKG + "," +
			ATTRIBUTE_MA_ITEM_PKG + "," +
			ATTRIBUTE_SEARCH_TEXT + "," +
			ATTRIBUTE_TAP_AVAILABLE + "," +
			ATTRIBUTE_CLOSEABLE + ")" +
			" values (" +
			repackageOrderViewBean.getPrNumber() + "," +
			repackageOrderViewBean.getItemId() + "," +
			repackageOrderViewBean.getOrderQuantity() + "," +
			repackageOrderViewBean.getOriginalItemId() + "," +
			SqlHandler.delimitString(repackageOrderViewBean.getHub()) + "," +
			SqlHandler.delimitString(repackageOrderViewBean.getInventoryGroup()) + "," +
			repackageOrderViewBean.getReceiptId() + "," +
			SqlHandler.delimitString(repackageOrderViewBean.getBin()) + "," +
			SqlHandler.delimitString(repackageOrderViewBean.getMfgLot()) + "," +
			DateHandler.getOracleToDateFunction(repackageOrderViewBean.getExpireDate()) + "," +
			repackageOrderViewBean.getQuantityAvailable() + "," +
			repackageOrderViewBean.getRepackageQuantityAvailable() + "," +
			SqlHandler.delimitString(repackageOrderViewBean.getItemDesc()) + "," +
			SqlHandler.delimitString(repackageOrderViewBean.getItemPkg()) + "," +
			SqlHandler.delimitString(repackageOrderViewBean.getMdItemPkg()) + "," +
			SqlHandler.delimitString(repackageOrderViewBean.getMaItemPkg()) + "," +
			SqlHandler.delimitString(repackageOrderViewBean.getSearchText()) + "," +
			SqlHandler.delimitString(repackageOrderViewBean.getTapAvailable()) + "," +
			SqlHandler.delimitString(repackageOrderViewBean.getCloseable()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(RepackageOrderViewBean repackageOrderViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(repackageOrderViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(RepackageOrderViewBean repackageOrderViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PR_NUMBER + "=" + 
				StringHandler.nullIfZero(repackageOrderViewBean.getPrNumber()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(repackageOrderViewBean.getItemId()) + "," +
			ATTRIBUTE_ORDER_QUANTITY + "=" + 
				StringHandler.nullIfZero(repackageOrderViewBean.getOrderQuantity()) + "," +
			ATTRIBUTE_ORIGINAL_ITEM_ID + "=" + 
				StringHandler.nullIfZero(repackageOrderViewBean.getOriginalItemId()) + "," +
			ATTRIBUTE_HUB + "=" + 
				SqlHandler.delimitString(repackageOrderViewBean.getHub()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(repackageOrderViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_RECEIPT_ID + "=" + 
				StringHandler.nullIfZero(repackageOrderViewBean.getReceiptId()) + "," +
			ATTRIBUTE_BIN + "=" + 
				SqlHandler.delimitString(repackageOrderViewBean.getBin()) + "," +
			ATTRIBUTE_MFG_LOT + "=" + 
				SqlHandler.delimitString(repackageOrderViewBean.getMfgLot()) + "," +
			ATTRIBUTE_EXPIRE_DATE + "=" + 
				DateHandler.getOracleToDateFunction(repackageOrderViewBean.getExpireDate()) + "," +
			ATTRIBUTE_QUANTITY_AVAILABLE + "=" + 
				StringHandler.nullIfZero(repackageOrderViewBean.getQuantityAvailable()) + "," +
			ATTRIBUTE_REPACKAGE_QUANTITY_AVAILABLE + "=" + 
				StringHandler.nullIfZero(repackageOrderViewBean.getRepackageQuantityAvailable()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" + 
				SqlHandler.delimitString(repackageOrderViewBean.getItemDesc()) + "," +
			ATTRIBUTE_ITEM_PKG + "=" + 
				SqlHandler.delimitString(repackageOrderViewBean.getItemPkg()) + "," +
			ATTRIBUTE_MD_ITEM_PKG + "=" + 
				SqlHandler.delimitString(repackageOrderViewBean.getMdItemPkg()) + "," +
			ATTRIBUTE_MA_ITEM_PKG + "=" + 
				SqlHandler.delimitString(repackageOrderViewBean.getMaItemPkg()) + "," +
			ATTRIBUTE_SEARCH_TEXT + "=" + 
				SqlHandler.delimitString(repackageOrderViewBean.getSearchText()) + "," +
			ATTRIBUTE_TAP_AVAILABLE + "=" + 
				SqlHandler.delimitString(repackageOrderViewBean.getTapAvailable()) + "," +
			ATTRIBUTE_CLOSEABLE + "=" + 
				SqlHandler.delimitString(repackageOrderViewBean.getCloseable()) + " " +
			"where " + ATTRIBUTE_PR_NUMBER + "=" +
				repackageOrderViewBean.getPrNumber();

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

		Collection repackageOrderViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			RepackageOrderViewBean repackageOrderViewBean = new RepackageOrderViewBean();
			load(dataSetRow, repackageOrderViewBean);
			repackageOrderViewBeanColl.add(repackageOrderViewBean);
		}

		return repackageOrderViewBeanColl;
	}
}