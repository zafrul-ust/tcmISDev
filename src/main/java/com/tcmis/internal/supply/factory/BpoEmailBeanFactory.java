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
 * CLASSNAME: BpoEmailBeanFactory <br>
 * @version: 1.0, Nov 19, 2007 <br>
 *****************************************************************************/


public class BpoEmailBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_BPO = "BPO";
	public String ATTRIBUTE_USER_ID = "USER_ID";
	public String ATTRIBUTE_EMAIL_DATE = "EMAIL_DATE";
	public String ATTRIBUTE_EMAIL = "EMAIL";

	//table name
	public String TABLE = "BPO_EMAIL";

	//constructor
	public BpoEmailBeanFactory(DbManager dbManager) {
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
	public int delete(BpoEmailBean bpoEmailBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("bpo", "SearchCriterion.EQUALS",
			"" + bpoEmailBean.getBpo());

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


	public int delete(BpoEmailBean bpoEmailBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("bpo", "SearchCriterion.EQUALS",
			"" + bpoEmailBean.getBpo());

		return delete(criteria, conn);
	}
*/
/*
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

	public int insert(BpoEmailViewBean bpoEmailBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(bpoEmailBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(BpoEmailViewBean bpoEmailBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_BPO + "," +
			ATTRIBUTE_USER_ID + "," +
			ATTRIBUTE_EMAIL_DATE + "," +
			ATTRIBUTE_EMAIL + ")" +
			" values (" +
			bpoEmailBean.getBpo() + "," +
			bpoEmailBean.getUserId() + "," +
			"sysdate, " +
			// DateHandler.getOracleToDateFunction(bpoEmailBean.getEmailDate()) + "," +
			SqlHandler.delimitString(bpoEmailBean.getEmail()) + ")";

		return sqlManager.update(conn, query);
	}

/*
	//update
	public int update(BpoEmailBean bpoEmailBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(bpoEmailBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(BpoEmailBean bpoEmailBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_BPO + "=" + 
				StringHandler.nullIfZero(bpoEmailBean.getBpo()) + "," +
			ATTRIBUTE_USER_ID + "=" + 
				StringHandler.nullIfZero(bpoEmailBean.getUserId()) + "," +
			ATTRIBUTE_EMAIL_DATE + "=" + 
				DateHandler.getOracleToDateFunction(bpoEmailBean.getEmailDate()) + "," +
			ATTRIBUTE_EMAIL + "=" + 
				SqlHandler.delimitString(bpoEmailBean.getEmail()) + " " +
			"where " + ATTRIBUTE_BPO + "=" +
				bpoEmailBean.getBpo();

		return new SqlManager().update(conn, query);
	}
*/
/*
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

		Collection bpoEmailBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			BpoEmailBean bpoEmailBean = new BpoEmailBean();
			load(dataSetRow, bpoEmailBean);
			bpoEmailBeanColl.add(bpoEmailBean);
		}

		return bpoEmailBeanColl;
	}
*/
}