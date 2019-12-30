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
import com.tcmis.internal.supply.beans.VvBuyOrderStatusBean;


/******************************************************************************
 * CLASSNAME: VvBuyOrderStatusBeanFactory <br>
 * @version: 1.0, Mar 10, 2006 <br>
 *****************************************************************************/


public class VvBuyOrderStatusBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_BUYPAGE_ASSIGNABLE = "BUYPAGE_ASSIGNABLE";
	public String ATTRIBUTE_LOCK_STATUS = "LOCK_STATUS";
	public String ATTRIBUTE_DISPLAY_SORT = "DISPLAY_SORT";

	//table name
	public String TABLE = "VV_BUY_ORDER_STATUS";


	//constructor
	public VvBuyOrderStatusBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if(attributeName.equals("buypageAssignable")) {
			return ATTRIBUTE_BUYPAGE_ASSIGNABLE;
		}
		else if(attributeName.equals("lockStatus")) {
			return ATTRIBUTE_LOCK_STATUS;
		}
		else if(attributeName.equals("displaySort")) {
			return ATTRIBUTE_DISPLAY_SORT;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, VvBuyOrderStatusBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(VvBuyOrderStatusBean vvBuyOrderStatusBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("status", "SearchCriterion.EQUALS",
			"" + vvBuyOrderStatusBean.getStatus());

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


	public int delete(VvBuyOrderStatusBean vvBuyOrderStatusBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("status", "SearchCriterion.EQUALS",
			"" + vvBuyOrderStatusBean.getStatus());

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
	public int insert(VvBuyOrderStatusBean vvBuyOrderStatusBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(vvBuyOrderStatusBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(VvBuyOrderStatusBean vvBuyOrderStatusBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_STATUS + "," +
			ATTRIBUTE_BUYPAGE_ASSIGNABLE + "," +
			ATTRIBUTE_LOCK_STATUS + "," +
			ATTRIBUTE_DISPLAY_SORT + ")" +
			" values (" +
			SqlHandler.delimitString(vvBuyOrderStatusBean.getStatus()) + "," +
			SqlHandler.delimitString(vvBuyOrderStatusBean.getBuypageAssignable()) + "," +
			SqlHandler.delimitString(vvBuyOrderStatusBean.getLockStatus()) + "," +
			vvBuyOrderStatusBean.getDisplaySort() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(VvBuyOrderStatusBean vvBuyOrderStatusBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(vvBuyOrderStatusBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(VvBuyOrderStatusBean vvBuyOrderStatusBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_STATUS + "=" +
				SqlHandler.delimitString(vvBuyOrderStatusBean.getStatus()) + "," +
			ATTRIBUTE_BUYPAGE_ASSIGNABLE + "=" +
				SqlHandler.delimitString(vvBuyOrderStatusBean.getBuypageAssignable()) + "," +
			ATTRIBUTE_LOCK_STATUS + "=" +
				SqlHandler.delimitString(vvBuyOrderStatusBean.getLockStatus()) + "," +
			ATTRIBUTE_DISPLAY_SORT + "=" +
				StringHandler.nullIfZero(vvBuyOrderStatusBean.getDisplaySort()) + " " +
			"where " + ATTRIBUTE_STATUS + "=" +
				vvBuyOrderStatusBean.getStatus();

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

		 Collection vvBuyOrderStatusBeanColl = new Vector();

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
			VvBuyOrderStatusBean vvBuyOrderStatusBean = new VvBuyOrderStatusBean();
			load(dataSetRow, vvBuyOrderStatusBean);
			vvBuyOrderStatusBeanColl.add(vvBuyOrderStatusBean);
		}

		return vvBuyOrderStatusBeanColl;
	}
}