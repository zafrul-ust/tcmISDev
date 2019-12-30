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
import com.tcmis.internal.supply.beans.ItemBuyerCommentsBean;


/******************************************************************************
 * CLASSNAME: ItemBuyerCommentsBeanFactory <br>
 * @version: 1.0, Oct 24, 2006 <br>
 *****************************************************************************/


public class ItemBuyerCommentsBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_BUYER_ID = "BUYER_ID";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";
	public String ATTRIBUTE_TRANSACTION_DATE = "TRANSACTION_DATE";
	public String ATTRIBUTE_RECORD_NO = "RECORD_NO";
	public String ATTRIBUTE_LAST_UPDATED = "LAST_UPDATED";

	//table name
	public String TABLE = "ITEM_BUYER_COMMENTS";


	//constructor
	public ItemBuyerCommentsBeanFactory(DbManager dbManager) {
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
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ItemBuyerCommentsBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ItemBuyerCommentsBean itemBuyerCommentsBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("buyerId", "SearchCriterion.EQUALS",
			"" + itemBuyerCommentsBean.getBuyerId());

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


	public int delete(ItemBuyerCommentsBean itemBuyerCommentsBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("buyerId", "SearchCriterion.EQUALS",
			"" + itemBuyerCommentsBean.getBuyerId());

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


	//insert
	public int insert(ItemBuyerCommentsBean itemBuyerCommentsBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(itemBuyerCommentsBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ItemBuyerCommentsBean itemBuyerCommentsBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_BUYER_ID + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_COMMENTS + "," +
			ATTRIBUTE_TRANSACTION_DATE + "," +
			ATTRIBUTE_LAST_UPDATED + ")" +
                        " values (" +
			StringHandler.nullIfZero(itemBuyerCommentsBean.getBuyerId()) + "," +
			StringHandler.nullIfZero(itemBuyerCommentsBean.getItemId()) + "," +
			SqlHandler.delimitString(itemBuyerCommentsBean.getComments()) + "," +
//			(new Timestamp(itemBuyerCommentsBean.getTransactionDate().getTime() ) )+ "," +
//			(new Timestamp(itemBuyerCommentsBean.getLastUpdated().getTime() ))+ ")";
			DateHandler.getOracleToDateFunction(itemBuyerCommentsBean.getTransactionDate()) + "," +
			DateHandler.getOracleToDateFunction(itemBuyerCommentsBean.getLastUpdated()) + ")";

		return sqlManager.update(conn, query);
	}

//you need to verify the primary key(s) before uncommenting this

	//update
	public int update(ItemBuyerCommentsBean itemBuyerCommentsBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(itemBuyerCommentsBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ItemBuyerCommentsBean itemBuyerCommentsBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMMENTS + "=" +
				SqlHandler.delimitString(itemBuyerCommentsBean.getComments()) + "," +
			ATTRIBUTE_LAST_UPDATED + "=" +
//			(new Timestamp(itemBuyerCommentsBean.getLastUpdated().getTime() )) + " " +
				DateHandler.getOracleToDateFunction(itemBuyerCommentsBean.getLastUpdated()) + " " +
			"where " + ATTRIBUTE_RECORD_NO + "=" +
				StringHandler.nullIfZero(itemBuyerCommentsBean.getRecordNo());

		return new SqlManager().update(conn, query);
	}

 //select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

		Connection connection = this.getDbManager().getConnection();
		Collection c = select(criteria,null, connection);
		this.getDbManager().returnConnection(connection);
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

		 Collection itemBuyerCommentsBeanColl = new Vector();

		 String query = "select * from " + TABLE + " " +
			 getWhereClause(criteria);
		 if (sortCriteria !=null)
		 {
				query += getOrderByClause(sortCriteria);
		 }

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ItemBuyerCommentsBean itemBuyerCommentsBean = new ItemBuyerCommentsBean();
			load(dataSetRow, itemBuyerCommentsBean);
			itemBuyerCommentsBeanColl.add(itemBuyerCommentsBean);
		}

		return itemBuyerCommentsBeanColl;
	}

}