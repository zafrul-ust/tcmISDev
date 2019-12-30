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
import com.tcmis.internal.hub.beans.TappedInventoryViewBean;


/******************************************************************************
 * CLASSNAME: TappedInventoryViewBeanFactory <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class TappedInventoryViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ITEM_CONVERSION_ISSUE_ID = "ITEM_CONVERSION_ISSUE_ID";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_QUANTITY_RECEIVED = "QUANTITY_RECEIVED";
	public String ATTRIBUTE_QUANTITY_ISSUED = "QUANTITY_ISSUED";
	public String ATTRIBUTE_ITEM_PKG = "ITEM_PKG";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_SEARCH_TEXT = "SEARCH_TEXT";

	//table name
	public String TABLE = "TAPPED_INVENTORY_VIEW";


	//constructor
	public TappedInventoryViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("itemConversionIssueId")) {
			return ATTRIBUTE_ITEM_CONVERSION_ISSUE_ID;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("quantityReceived")) {
			return ATTRIBUTE_QUANTITY_RECEIVED;
		}
		else if(attributeName.equals("quantityIssued")) {
			return ATTRIBUTE_QUANTITY_ISSUED;
		}
		else if(attributeName.equals("itemPkg")) {
			return ATTRIBUTE_ITEM_PKG;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("searchText")) {
			return ATTRIBUTE_SEARCH_TEXT;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, TappedInventoryViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(TappedInventoryViewBean tappedInventoryViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("hub", "SearchCriterion.EQUALS",
			"" + tappedInventoryViewBean.getHub());

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


	public int delete(TappedInventoryViewBean tappedInventoryViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("hub", "SearchCriterion.EQUALS",
			"" + tappedInventoryViewBean.getHub());

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
	public int insert(TappedInventoryViewBean tappedInventoryViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(tappedInventoryViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(TappedInventoryViewBean tappedInventoryViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_HUB + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_ITEM_CONVERSION_ISSUE_ID + "," +
			ATTRIBUTE_RECEIPT_ID + "," +
			ATTRIBUTE_QUANTITY_RECEIVED + "," +
			ATTRIBUTE_QUANTITY_ISSUED + "," +
			ATTRIBUTE_ITEM_PKG + "," +
			ATTRIBUTE_ITEM_DESC + "," +
			ATTRIBUTE_SEARCH_TEXT + ")" +
			" values (" +
			SqlHandler.delimitString(tappedInventoryViewBean.getHub()) + "," +
			SqlHandler.delimitString(tappedInventoryViewBean.getInventoryGroup()) + "," +
			tappedInventoryViewBean.getItemId() + "," +
			tappedInventoryViewBean.getItemConversionIssueId() + "," +
			tappedInventoryViewBean.getReceiptId() + "," +
			tappedInventoryViewBean.getQuantityReceived() + "," +
			tappedInventoryViewBean.getQuantityIssued() + "," +
			SqlHandler.delimitString(tappedInventoryViewBean.getItemPkg()) + "," +
			SqlHandler.delimitString(tappedInventoryViewBean.getItemDesc()) + "," +
			SqlHandler.delimitString(tappedInventoryViewBean.getSearchText()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(TappedInventoryViewBean tappedInventoryViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(tappedInventoryViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(TappedInventoryViewBean tappedInventoryViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_HUB + "=" + 
				SqlHandler.delimitString(tappedInventoryViewBean.getHub()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(tappedInventoryViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(tappedInventoryViewBean.getItemId()) + "," +
			ATTRIBUTE_ITEM_CONVERSION_ISSUE_ID + "=" + 
				StringHandler.nullIfZero(tappedInventoryViewBean.getItemConversionIssueId()) + "," +
			ATTRIBUTE_RECEIPT_ID + "=" + 
				StringHandler.nullIfZero(tappedInventoryViewBean.getReceiptId()) + "," +
			ATTRIBUTE_QUANTITY_RECEIVED + "=" + 
				StringHandler.nullIfZero(tappedInventoryViewBean.getQuantityReceived()) + "," +
			ATTRIBUTE_QUANTITY_ISSUED + "=" + 
				StringHandler.nullIfZero(tappedInventoryViewBean.getQuantityIssued()) + "," +
			ATTRIBUTE_ITEM_PKG + "=" + 
				SqlHandler.delimitString(tappedInventoryViewBean.getItemPkg()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" + 
				SqlHandler.delimitString(tappedInventoryViewBean.getItemDesc()) + "," +
			ATTRIBUTE_SEARCH_TEXT + "=" + 
				SqlHandler.delimitString(tappedInventoryViewBean.getSearchText()) + " " +
			"where " + ATTRIBUTE_HUB + "=" +
				tappedInventoryViewBean.getHub();

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

		Collection tappedInventoryViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			TappedInventoryViewBean tappedInventoryViewBean = new TappedInventoryViewBean();
			load(dataSetRow, tappedInventoryViewBean);
			tappedInventoryViewBeanColl.add(tappedInventoryViewBean);
		}

		return tappedInventoryViewBeanColl;
	}
}