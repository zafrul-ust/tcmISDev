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
import com.tcmis.internal.hub.beans.ListItemDetailViewBean;


/******************************************************************************
 * CLASSNAME: ListItemDetailView BeanFactory <br>
 * @version: 1.0, Jan 11, 2008 <br>
 *****************************************************************************/


public class ListItemDetailViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_LIST_ITEM_ID = "LIST_ITEM_ID";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";

	//table name
	public String TABLE = "LIST_ITEM_DETAIL_VIEW ";


	//constructor
	public ListItemDetailViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("listItemId")) {
			return ATTRIBUTE_LIST_ITEM_ID;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("itemType")) {
			return ATTRIBUTE_ITEM_TYPE;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ListItemDetailViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ListItemDetailView Bean listItemDetailView Bean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("listItemId", "SearchCriterion.EQUALS",
			"" + listItemDetailView Bean.getListItemId());

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


	public int delete(ListItemDetailView Bean listItemDetailView Bean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("listItemId", "SearchCriterion.EQUALS",
			"" + listItemDetailView Bean.getListItemId());

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
	public int insert(ListItemDetailView Bean listItemDetailView Bean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(listItemDetailView Bean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ListItemDetailView Bean listItemDetailView Bean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_LIST_ITEM_ID + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_ITEM_DESC + "," +
			ATTRIBUTE_ITEM_TYPE + "," +
			ATTRIBUTE_PACKAGING + ")" +
			" values (" +
			listItemDetailView Bean.getListItemId() + "," +
			SqlHandler.delimitString(listItemDetailView Bean.getInventoryGroup()) + "," +
			listItemDetailView Bean.getItemId() + "," +
			SqlHandler.delimitString(listItemDetailView Bean.getItemDesc()) + "," +
			SqlHandler.delimitString(listItemDetailView Bean.getItemType()) + "," +
			SqlHandler.delimitString(listItemDetailView Bean.getPackaging()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ListItemDetailView Bean listItemDetailView Bean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(listItemDetailView Bean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ListItemDetailView Bean listItemDetailView Bean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_LIST_ITEM_ID + "=" + 
				StringHandler.nullIfZero(listItemDetailView Bean.getListItemId()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(listItemDetailView Bean.getInventoryGroup()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(listItemDetailView Bean.getItemId()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" + 
				SqlHandler.delimitString(listItemDetailView Bean.getItemDesc()) + "," +
			ATTRIBUTE_ITEM_TYPE + "=" + 
				SqlHandler.delimitString(listItemDetailView Bean.getItemType()) + "," +
			ATTRIBUTE_PACKAGING + "=" + 
				SqlHandler.delimitString(listItemDetailView Bean.getPackaging()) + " " +
			"where " + ATTRIBUTE_LIST_ITEM_ID + "=" +
				listItemDetailView Bean.getListItemId();

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

		Collection listItemDetailViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ListItemDetailViewBean listItemDetailViewBean = new ListItemDetailViewBean();
			load(dataSetRow, listItemDetailViewBean);
			listItemDetailViewBeanColl.add(listItemDetailViewBean);
		}

		return listItemDetailViewBeanColl;
	}
}