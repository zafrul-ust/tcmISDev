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
import com.tcmis.internal.supply.beans.PoEmailViewBean;


/******************************************************************************
 * CLASSNAME: PoEmailViewBeanFactory <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class PoEmailViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_USER_ID = "USER_ID";
	public String ATTRIBUTE_EMAIL_DATE = "EMAIL_DATE";
	public String ATTRIBUTE_EMAIL = "EMAIL";
	public String ATTRIBUTE_EMAIL_USER_NAME = "EMAIL_USER_NAME";

	//table name
	public String TABLE = "PO_EMAIL_VIEW";


	//constructor
	public PoEmailViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("userId")) {
			return ATTRIBUTE_USER_ID;
		}
		else if(attributeName.equals("emailDate")) {
			return ATTRIBUTE_EMAIL_DATE;
		}
		else if(attributeName.equals("email")) {
			return ATTRIBUTE_EMAIL;
		}
		else if(attributeName.equals("emailUserName")) {
			return ATTRIBUTE_EMAIL_USER_NAME;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PoEmailViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PoEmailViewBean poEmailViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + poEmailViewBean.getRadianPo());

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


	public int delete(PoEmailViewBean poEmailViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + poEmailViewBean.getRadianPo());

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
	public int insert(PoEmailViewBean poEmailViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(poEmailViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PoEmailViewBean poEmailViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_USER_ID + "," +
			ATTRIBUTE_EMAIL_DATE + "," +
			ATTRIBUTE_EMAIL + "," +
			ATTRIBUTE_EMAIL_USER_NAME + ")" +
			" values (" +
			poEmailViewBean.getRadianPo() + "," +
			poEmailViewBean.getUserId() + "," +
			DateHandler.getOracleToDateFunction(poEmailViewBean.getEmailDate()) + "," +
			SqlHandler.delimitString(poEmailViewBean.getEmail()) + "," +
			SqlHandler.delimitString(poEmailViewBean.getEmailUserName()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PoEmailViewBean poEmailViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(poEmailViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PoEmailViewBean poEmailViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(poEmailViewBean.getRadianPo()) + "," +
			ATTRIBUTE_USER_ID + "=" + 
				StringHandler.nullIfZero(poEmailViewBean.getUserId()) + "," +
			ATTRIBUTE_EMAIL_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poEmailViewBean.getEmailDate()) + "," +
			ATTRIBUTE_EMAIL + "=" + 
				SqlHandler.delimitString(poEmailViewBean.getEmail()) + "," +
			ATTRIBUTE_EMAIL_USER_NAME + "=" + 
				SqlHandler.delimitString(poEmailViewBean.getEmailUserName()) + " " +
			"where " + ATTRIBUTE_RADIAN_PO + "=" +
				poEmailViewBean.getRadianPo();

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

		Collection poEmailViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PoEmailViewBean poEmailViewBean = new PoEmailViewBean();
			load(dataSetRow, poEmailViewBean);
			poEmailViewBeanColl.add(poEmailViewBean);
		}

		return poEmailViewBeanColl;
	}
}