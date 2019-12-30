package com.tcmis.client.common.factory;


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
import com.tcmis.client.common.beans.SizeUnitViewBean;


/******************************************************************************
 * CLASSNAME: SizeUnitViewBeanFactory <br>
 * @version: 1.0, Jan 9, 2008 <br>
 *****************************************************************************/


public class SizeUnitViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SIZE_UNIT = "SIZE_UNIT";
	public String ATTRIBUTE_NET_WT_REQUIRED = "NET_WT_REQUIRED";

	//table name
	public String TABLE = "SIZE_UNIT_VIEW";


	//constructor
	public SizeUnitViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("sizeUnit")) {
			return ATTRIBUTE_SIZE_UNIT;
		}
		else if(attributeName.equals("netWtRequired")) {
			return ATTRIBUTE_NET_WT_REQUIRED;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, SizeUnitViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(SizeUnitViewBean sizeUnitViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("sizeUnit", "SearchCriterion.EQUALS",
			"" + sizeUnitViewBean.getSizeUnit());

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


	public int delete(SizeUnitViewBean sizeUnitViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("sizeUnit", "SearchCriterion.EQUALS",
			"" + sizeUnitViewBean.getSizeUnit());

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
	public int insert(SizeUnitViewBean sizeUnitViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(sizeUnitViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(SizeUnitViewBean sizeUnitViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_SIZE_UNIT + "," +
			ATTRIBUTE_NET_WT_REQUIRED + ")" +
			" values (" +
			SqlHandler.delimitString(sizeUnitViewBean.getSizeUnit()) + "," +
			SqlHandler.delimitString(sizeUnitViewBean.getNetWtRequired()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(SizeUnitViewBean sizeUnitViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(sizeUnitViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(SizeUnitViewBean sizeUnitViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_SIZE_UNIT + "=" + 
				SqlHandler.delimitString(sizeUnitViewBean.getSizeUnit()) + "," +
			ATTRIBUTE_NET_WT_REQUIRED + "=" + 
				SqlHandler.delimitString(sizeUnitViewBean.getNetWtRequired()) + " " +
			"where " + ATTRIBUTE_SIZE_UNIT + "=" +
				sizeUnitViewBean.getSizeUnit();

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

		Collection sizeUnitViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			SizeUnitViewBean sizeUnitViewBean = new SizeUnitViewBean();
			load(dataSetRow, sizeUnitViewBean);
			sizeUnitViewBeanColl.add(sizeUnitViewBean);
		}

		return sizeUnitViewBeanColl;
	}
}