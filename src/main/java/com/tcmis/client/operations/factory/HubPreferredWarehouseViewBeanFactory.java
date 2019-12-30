package com.tcmis.client.operations.factory;


import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.operations.beans.HubPreferredWarehouseViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;


/******************************************************************************
 * CLASSNAME: HubPreferredWarehouseViewBeanFactory <br>
 * @version: 1.0, Aug 3, 2009 <br>
 *****************************************************************************/


public class HubPreferredWarehouseViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
	public String ATTRIBUTE_PREFERRED_WAREHOUSE = "PREFERRED_WAREHOUSE";

	//table name
	public String TABLE = "HUB_PREFERRED_WAREHOUSE_VIEW";


	//constructor
	public HubPreferredWarehouseViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("branchPlant")) {
			return ATTRIBUTE_BRANCH_PLANT;
		}
		else if(attributeName.equals("preferredWarehouse")) {
			return ATTRIBUTE_PREFERRED_WAREHOUSE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, HubPreferredWarehouseViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(HubPreferredWarehouseViewBean hubPreferredWarehouseViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("branchPlant", "SearchCriterion.EQUALS",
			"" + hubPreferredWarehouseViewBean.getBranchPlant());

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


	public int delete(HubPreferredWarehouseViewBean hubPreferredWarehouseViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("branchPlant", "SearchCriterion.EQUALS",
			"" + hubPreferredWarehouseViewBean.getBranchPlant());

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
	public int insert(HubPreferredWarehouseViewBean hubPreferredWarehouseViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(hubPreferredWarehouseViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(HubPreferredWarehouseViewBean hubPreferredWarehouseViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_BRANCH_PLANT + "," +
			ATTRIBUTE_PREFERRED_WAREHOUSE + ")" +
			" values (" +
			SqlHandler.delimitString(hubPreferredWarehouseViewBean.getBranchPlant()) + "," +
			SqlHandler.delimitString(hubPreferredWarehouseViewBean.getPreferredWarehouse()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(HubPreferredWarehouseViewBean hubPreferredWarehouseViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(hubPreferredWarehouseViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(HubPreferredWarehouseViewBean hubPreferredWarehouseViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_BRANCH_PLANT + "=" + 
				SqlHandler.delimitString(hubPreferredWarehouseViewBean.getBranchPlant()) + "," +
			ATTRIBUTE_PREFERRED_WAREHOUSE + "=" + 
				SqlHandler.delimitString(hubPreferredWarehouseViewBean.getPreferredWarehouse()) + " " +
			"where " + ATTRIBUTE_BRANCH_PLANT + "=" +
				hubPreferredWarehouseViewBean.getBranchPlant();

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

		Collection hubPreferredWarehouseViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			HubPreferredWarehouseViewBean hubPreferredWarehouseViewBean = new HubPreferredWarehouseViewBean();
			load(dataSetRow, hubPreferredWarehouseViewBean);
			hubPreferredWarehouseViewBeanColl.add(hubPreferredWarehouseViewBean);
		}

		return hubPreferredWarehouseViewBeanColl;
	}
}