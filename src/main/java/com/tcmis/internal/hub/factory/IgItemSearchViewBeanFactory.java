package com.tcmis.internal.hub.factory;


import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.hub.beans.IgItemSearchViewBean;


/******************************************************************************
 * CLASSNAME: IgItemSearchViewBeanFactory <br>
 * @version: 1.0, Aug 11, 2009 <br>
 *****************************************************************************/


public class IgItemSearchViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_SEARCH_STRING = "SEARCH_STRING";

	//table name
	public String TABLE = "IG_ITEM_SEARCH_VIEW";


	//constructor
	public IgItemSearchViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("searchString")) {
			return ATTRIBUTE_SEARCH_STRING;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, IgItemSearchViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(IgItemSearchViewBean igItemSearchViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("itemId", "SearchCriterion.EQUALS",
			"" + igItemSearchViewBean.getItemId());

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


	public int delete(IgItemSearchViewBean igItemSearchViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("itemId", "SearchCriterion.EQUALS",
			"" + igItemSearchViewBean.getItemId());

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
	public int insert(IgItemSearchViewBean igItemSearchViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(igItemSearchViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(IgItemSearchViewBean igItemSearchViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_ITEM_DESC + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_SEARCH_STRING + ")" +
			" values (" +
			igItemSearchViewBean.getItemId() + "," +
			SqlHandler.delimitString(igItemSearchViewBean.getItemDesc()) + "," +
			SqlHandler.delimitString(igItemSearchViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(igItemSearchViewBean.getSearchString()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(IgItemSearchViewBean igItemSearchViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(igItemSearchViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(IgItemSearchViewBean igItemSearchViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(igItemSearchViewBean.getItemId()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" + 
				SqlHandler.delimitString(igItemSearchViewBean.getItemDesc()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(igItemSearchViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_SEARCH_STRING + "=" + 
				SqlHandler.delimitString(igItemSearchViewBean.getSearchString()) + " " +
			"where " + ATTRIBUTE_ITEM_ID + "=" +
				igItemSearchViewBean.getItemId();

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

		Collection igItemSearchViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			IgItemSearchViewBean igItemSearchViewBean = new IgItemSearchViewBean();
			load(dataSetRow, igItemSearchViewBean);
			igItemSearchViewBeanColl.add(igItemSearchViewBean);
		}

		return igItemSearchViewBeanColl;
	}
}