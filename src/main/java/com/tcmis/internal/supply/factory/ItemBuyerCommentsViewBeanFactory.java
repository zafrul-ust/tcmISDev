package com.tcmis.internal.supply.factory;


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
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.supply.beans.ItemBuyerCommentsViewBean;


/******************************************************************************
 * CLASSNAME: ItemBuyerCommentsViewBeanFactory <br>
 * @version: 1.0, Oct 24, 2006 <br>
 *****************************************************************************/


public class ItemBuyerCommentsViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_BUYER_ID = "BUYER_ID";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";
	public String ATTRIBUTE_TRANSACTION_DATE = "TRANSACTION_DATE";
	public String ATTRIBUTE_RECORD_NO = "RECORD_NO";
	public String ATTRIBUTE_LAST_UPDATED = "LAST_UPDATED";
	public String ATTRIBUTE_ENTERED_BY_NAME = "ENTERED_BY_NAME";

	//table name
	public String TABLE = "ITEM_BUYER_COMMENTS_VIEW";


	//constructor
	public ItemBuyerCommentsViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("buyerId")) {
			return ATTRIBUTE_BUYER_ID;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("comments")) {
			return ATTRIBUTE_COMMENTS;
		}
		else if(attributeName.equals("transactionDate")) {
			return ATTRIBUTE_TRANSACTION_DATE;
		}
		else if(attributeName.equals("recordNo")) {
			return ATTRIBUTE_RECORD_NO;
		}
		else if(attributeName.equals("lastUpdated")) {
			return ATTRIBUTE_LAST_UPDATED;
		}
		else if(attributeName.equals("enteredByName")) {
			return ATTRIBUTE_ENTERED_BY_NAME;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ItemBuyerCommentsViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ItemBuyerCommentsViewBean itemBuyerCommentsViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("buyerId", "SearchCriterion.EQUALS",
			"" + itemBuyerCommentsViewBean.getBuyerId());

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


	public int delete(ItemBuyerCommentsViewBean itemBuyerCommentsViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("buyerId", "SearchCriterion.EQUALS",
			"" + itemBuyerCommentsViewBean.getBuyerId());

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
	public int insert(ItemBuyerCommentsViewBean itemBuyerCommentsViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(itemBuyerCommentsViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ItemBuyerCommentsViewBean itemBuyerCommentsViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_BUYER_ID + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_COMMENTS + "," +
			ATTRIBUTE_TRANSACTION_DATE + "," +
			ATTRIBUTE_RECORD_NO + "," +
			ATTRIBUTE_LAST_UPDATED + "," +
			ATTRIBUTE_ENTERED_BY_NAME + ")" +
 values (
			StringHandler.nullIfZero(itemBuyerCommentsViewBean.getBuyerId()) + "," +
			StringHandler.nullIfZero(itemBuyerCommentsViewBean.getItemId()) + "," +
			SqlHandler.delimitString(itemBuyerCommentsViewBean.getComments()) + "," +
			DateHandler.getOracleToDateFunction(itemBuyerCommentsViewBean.getTransactionDate()) + "," +
			StringHandler.nullIfZero(itemBuyerCommentsViewBean.getRecordNo()) + "," +
			DateHandler.getOracleToDateFunction(itemBuyerCommentsViewBean.getLastUpdated()) + "," +
			SqlHandler.delimitString(itemBuyerCommentsViewBean.getEnteredByName()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ItemBuyerCommentsViewBean itemBuyerCommentsViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(itemBuyerCommentsViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ItemBuyerCommentsViewBean itemBuyerCommentsViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_BUYER_ID + "=" +
				StringHandler.nullIfZero(itemBuyerCommentsViewBean.getBuyerId()) + "," +
			ATTRIBUTE_ITEM_ID + "=" +
				StringHandler.nullIfZero(itemBuyerCommentsViewBean.getItemId()) + "," +
			ATTRIBUTE_COMMENTS + "=" +
				SqlHandler.delimitString(itemBuyerCommentsViewBean.getComments()) + "," +
			ATTRIBUTE_TRANSACTION_DATE + "=" +
				DateHandler.getOracleToDateFunction(itemBuyerCommentsViewBean.getTransactionDate()) + "," +
			ATTRIBUTE_RECORD_NO + "=" +
				StringHandler.nullIfZero(itemBuyerCommentsViewBean.getRecordNo()) + "," +
			ATTRIBUTE_LAST_UPDATED + "=" +
				DateHandler.getOracleToDateFunction(itemBuyerCommentsViewBean.getLastUpdated()) + "," +
			ATTRIBUTE_ENTERED_BY_NAME + "=" +
				SqlHandler.delimitString(itemBuyerCommentsViewBean.getEnteredByName()) + " " +
			"where " + ATTRIBUTE_BUYER_ID + "=" +
				StringHandler.nullIfZero(itemBuyerCommentsViewBean.getBuyerId());

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
			c = select(criteria,null, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

 public Collection select(SearchCriteria criteria,SortCriteria sortCriteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria,sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection select(SearchCriteria criteria,SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection itemBuyerCommentsViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		 if (sortCriteria != null) {
							query += getOrderByClause(sortCriteria);
					 }

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ItemBuyerCommentsViewBean itemBuyerCommentsViewBean = new ItemBuyerCommentsViewBean();
			load(dataSetRow, itemBuyerCommentsViewBean);
			itemBuyerCommentsViewBeanColl.add(itemBuyerCommentsViewBean);
		}

		return itemBuyerCommentsViewBeanColl;
	}
        
        
}