package com.tcmis.client.report.factory;


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
import com.tcmis.client.report.beans.ScaqmdLastUpdatedViewBean;


/******************************************************************************
 * CLASSNAME: ScaqmdLastUpdatedViewBeanFactory <br>
 * @version: 1.0, Feb 9, 2006 <br>
 *****************************************************************************/


public class ScaqmdLastUpdatedViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_LAST_UPDATED = "LAST_UPDATED";

	//table name
	public String TABLE = "SCAQMD_LAST_UPDATED_VIEW";


	//constructor
	public ScaqmdLastUpdatedViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("lastUpdated")) {
			return ATTRIBUTE_LAST_UPDATED;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ScaqmdLastUpdatedViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ScaqmdLastUpdatedViewBean scaqmdLastUpdatedViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("lastUpdated", "SearchCriterion.EQUALS",
			"" + scaqmdLastUpdatedViewBean.getLastUpdated());

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


	public int delete(ScaqmdLastUpdatedViewBean scaqmdLastUpdatedViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("lastUpdated", "SearchCriterion.EQUALS",
			"" + scaqmdLastUpdatedViewBean.getLastUpdated());

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
	public int insert(ScaqmdLastUpdatedViewBean scaqmdLastUpdatedViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(scaqmdLastUpdatedViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ScaqmdLastUpdatedViewBean scaqmdLastUpdatedViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_LAST_UPDATED + ")" +
			" values (" +
			DateHandler.getOracleToDateFunction(scaqmdLastUpdatedViewBean.getLastUpdated()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ScaqmdLastUpdatedViewBean scaqmdLastUpdatedViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(scaqmdLastUpdatedViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ScaqmdLastUpdatedViewBean scaqmdLastUpdatedViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_LAST_UPDATED + "=" + 
				DateHandler.getOracleToDateFunction(scaqmdLastUpdatedViewBean.getLastUpdated()) + " " +
			"where " + ATTRIBUTE_LAST_UPDATED + "=" +
				scaqmdLastUpdatedViewBean.getLastUpdated();

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

		Collection scaqmdLastUpdatedViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ScaqmdLastUpdatedViewBean scaqmdLastUpdatedViewBean = new ScaqmdLastUpdatedViewBean();
			load(dataSetRow, scaqmdLastUpdatedViewBean);
			scaqmdLastUpdatedViewBeanColl.add(scaqmdLastUpdatedViewBean);
		}

		return scaqmdLastUpdatedViewBeanColl;
	}
}