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
import com.tcmis.internal.supply.beans.VvBuypageSortBean;


/******************************************************************************
 * CLASSNAME: VvBuypageSortBeanFactory <br>
 * @version: 1.0, Mar 10, 2006 <br>
 *****************************************************************************/


public class VvBuypageSortBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SORT_ID = "SORT_ID";
	public String ATTRIBUTE_SORT_DESC = "SORT_DESC";

	//table name
	public String TABLE = "VV_BUYPAGE_SORT";


	//constructor
	public VvBuypageSortBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("sortId")) {
			return ATTRIBUTE_SORT_ID;
		}
		else if(attributeName.equals("sortDesc")) {
			return ATTRIBUTE_SORT_DESC;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, VvBuypageSortBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(VvBuypageSortBean vvBuypageSortBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("sortId", "SearchCriterion.EQUALS",
			"" + vvBuypageSortBean.getSortId());

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


	public int delete(VvBuypageSortBean vvBuypageSortBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("sortId", "SearchCriterion.EQUALS",
			"" + vvBuypageSortBean.getSortId());

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
	public int insert(VvBuypageSortBean vvBuypageSortBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(vvBuypageSortBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(VvBuypageSortBean vvBuypageSortBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_SORT_ID + "," +
			ATTRIBUTE_SORT_DESC + ")" +
			" values (" +
			SqlHandler.delimitString(vvBuypageSortBean.getSortId()) + "," +
			SqlHandler.delimitString(vvBuypageSortBean.getSortDesc()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(VvBuypageSortBean vvBuypageSortBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(vvBuypageSortBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(VvBuypageSortBean vvBuypageSortBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_SORT_ID + "=" +
				SqlHandler.delimitString(vvBuypageSortBean.getSortId()) + "," +
			ATTRIBUTE_SORT_DESC + "=" +
				SqlHandler.delimitString(vvBuypageSortBean.getSortDesc()) + " " +
			"where " + ATTRIBUTE_SORT_ID + "=" +
				vvBuypageSortBean.getSortId();

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

		 Collection vvBuypageSortBeanColl = new Vector();

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
			VvBuypageSortBean vvBuypageSortBean = new VvBuypageSortBean();
			load(dataSetRow, vvBuypageSortBean);
			vvBuypageSortBeanColl.add(vvBuypageSortBean);
		}

		return vvBuypageSortBeanColl;
	}
}