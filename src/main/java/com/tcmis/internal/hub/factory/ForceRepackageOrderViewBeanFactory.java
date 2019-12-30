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
import com.tcmis.internal.hub.beans.ForceRepackageOrderViewBean;


/******************************************************************************
 * CLASSNAME: ForceRepackageOrderViewBeanFactory <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class ForceRepackageOrderViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_ITEM_PKG = "ITEM_PKG";
	public String ATTRIBUTE_SEARCH_TEXT = "SEARCH_TEXT";

	//table name
	public String TABLE = "FORCE_REPACKAGE_ORDER_VIEW";


	//constructor
	public ForceRepackageOrderViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("itemPkg")) {
			return ATTRIBUTE_ITEM_PKG;
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
		return super.getType(attributeName, ForceRepackageOrderViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ForceRepackageOrderViewBean forceRepackageOrderViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("itemId", "SearchCriterion.EQUALS",
			"" + forceRepackageOrderViewBean.getItemId());

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


	public int delete(ForceRepackageOrderViewBean forceRepackageOrderViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("itemId", "SearchCriterion.EQUALS",
			"" + forceRepackageOrderViewBean.getItemId());

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
	public int insert(ForceRepackageOrderViewBean forceRepackageOrderViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(forceRepackageOrderViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ForceRepackageOrderViewBean forceRepackageOrderViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_HUB + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_ITEM_DESC + "," +
			ATTRIBUTE_ITEM_PKG + "," +
			ATTRIBUTE_SEARCH_TEXT + ")" +
			" values (" +
			forceRepackageOrderViewBean.getItemId() + "," +
			SqlHandler.delimitString(forceRepackageOrderViewBean.getHub()) + "," +
			SqlHandler.delimitString(forceRepackageOrderViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(forceRepackageOrderViewBean.getItemDesc()) + "," +
			SqlHandler.delimitString(forceRepackageOrderViewBean.getItemPkg()) + "," +
			SqlHandler.delimitString(forceRepackageOrderViewBean.getSearchText()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ForceRepackageOrderViewBean forceRepackageOrderViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(forceRepackageOrderViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ForceRepackageOrderViewBean forceRepackageOrderViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(forceRepackageOrderViewBean.getItemId()) + "," +
			ATTRIBUTE_HUB + "=" + 
				SqlHandler.delimitString(forceRepackageOrderViewBean.getHub()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(forceRepackageOrderViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" + 
				SqlHandler.delimitString(forceRepackageOrderViewBean.getItemDesc()) + "," +
			ATTRIBUTE_ITEM_PKG + "=" + 
				SqlHandler.delimitString(forceRepackageOrderViewBean.getItemPkg()) + "," +
			ATTRIBUTE_SEARCH_TEXT + "=" + 
				SqlHandler.delimitString(forceRepackageOrderViewBean.getSearchText()) + " " +
			"where " + ATTRIBUTE_ITEM_ID + "=" +
				forceRepackageOrderViewBean.getItemId();

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

		Collection forceRepackageOrderViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ForceRepackageOrderViewBean forceRepackageOrderViewBean = new ForceRepackageOrderViewBean();
			load(dataSetRow, forceRepackageOrderViewBean);
			forceRepackageOrderViewBeanColl.add(forceRepackageOrderViewBean);
		}

		return forceRepackageOrderViewBeanColl;
	}
}