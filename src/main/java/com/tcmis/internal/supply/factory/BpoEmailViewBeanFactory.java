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
import com.tcmis.internal.supply.beans.BpoEmailViewBean;


/******************************************************************************
 * CLASSNAME: BpoEmailViewBeanFactory <br>
 * @version: 1.0, Nov 15, 2007 <br>
 *****************************************************************************/

public class BpoEmailViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_BPO = "BPO";
	public String ATTRIBUTE_USER_ID = "USER_ID";
	public String ATTRIBUTE_EMAIL_DATE = "EMAIL_DATE";
	public String ATTRIBUTE_EMAIL = "EMAIL";
	public String ATTRIBUTE_EMAIL_USER_NAME = "EMAIL_USER_NAME";

	//table name
	public String TABLE = "BPO_EMAIL_VIEW";


	//constructor
	public BpoEmailViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("bpo")) {
			return ATTRIBUTE_BPO;
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
		return super.getType(attributeName, BpoEmailViewBean.class);
	}

//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(BpoEmailViewBean bpoEmailViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("bpo", "SearchCriterion.EQUALS",
			"" + bpoEmailViewBean.getBpo());

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


	public int delete(BpoEmailViewBean bpoEmailViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("bpo", "SearchCriterion.EQUALS",
			"" + bpoEmailViewBean.getBpo());

		return delete(criteria, conn);
	}


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

*/
//you need to verify the primary key(s) before uncommenting this

	//insert
	public int insert(BpoEmailViewBean bpoEmailViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(bpoEmailViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(BpoEmailViewBean bpoEmailViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_BPO + "," +
			ATTRIBUTE_USER_ID + "," +
			ATTRIBUTE_EMAIL_DATE + "," +
			ATTRIBUTE_EMAIL + "," +
			ATTRIBUTE_EMAIL_USER_NAME + ")" +
			" values (" +
			bpoEmailViewBean.getBpo() + "," +
			bpoEmailViewBean.getUserId() + "," +
			DateHandler.getOracleToDateFunction(bpoEmailViewBean.getEmailDate()) + "," +
			SqlHandler.delimitString(bpoEmailViewBean.getEmail()) + "," +
			SqlHandler.delimitString(bpoEmailViewBean.getEmailUserName()) + ")";

		return sqlManager.update(conn, query);
	}
	//update
	public int update(BpoEmailViewBean bpoEmailViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(bpoEmailViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(BpoEmailViewBean bpoEmailViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_BPO + "=" + 
				StringHandler.nullIfZero(bpoEmailViewBean.getBpo()) + "," +
			ATTRIBUTE_USER_ID + "=" + 
				StringHandler.nullIfZero(bpoEmailViewBean.getUserId()) + "," +
			ATTRIBUTE_EMAIL_DATE + "=" + 
				DateHandler.getOracleToDateFunction(bpoEmailViewBean.getEmailDate()) + "," +
			ATTRIBUTE_EMAIL + "=" + 
				SqlHandler.delimitString(bpoEmailViewBean.getEmail()) + "," +
			ATTRIBUTE_EMAIL_USER_NAME + "=" + 
				SqlHandler.delimitString(bpoEmailViewBean.getEmailUserName()) + " " +
			"where " + ATTRIBUTE_BPO + "=" +
				bpoEmailViewBean.getBpo();

		return new SqlManager().update(conn, query);
	}
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

		Collection bpoEmailViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			BpoEmailViewBean bpoEmailViewBean = new BpoEmailViewBean();
			load(dataSetRow, bpoEmailViewBean);
			bpoEmailViewBeanColl.add(bpoEmailViewBean);
		}

		return bpoEmailViewBeanColl;
	}
}