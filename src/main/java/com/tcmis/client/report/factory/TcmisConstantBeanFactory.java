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
import com.tcmis.client.report.beans.TcmisConstantBean;


/******************************************************************************
 * CLASSNAME: TcmisConstantBeanFactory <br>
 * @version: 1.0, Nov 23, 2005 <br>
 *****************************************************************************/


public class TcmisConstantBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_CONSTANT = "CONSTANT";
	public String ATTRIBUTE_VALUE = "VALUE";

	//table name
	public String TABLE = "TCMIS_CONSTANT";


	//constructor
	public TcmisConstantBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("constant")) {
			return ATTRIBUTE_CONSTANT;
		}
		else if(attributeName.equals("value")) {
			return ATTRIBUTE_VALUE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, TcmisConstantBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(TcmisConstantBean tcmisConstantBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("constant", "SearchCriterion.EQUALS",
			"" + tcmisConstantBean.getConstant());

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


	public int delete(TcmisConstantBean tcmisConstantBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("constant", "SearchCriterion.EQUALS",
			"" + tcmisConstantBean.getConstant());

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
	public int insert(TcmisConstantBean tcmisConstantBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(tcmisConstantBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(TcmisConstantBean tcmisConstantBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_CONSTANT + "," +
			ATTRIBUTE_VALUE + ")" +
			" values (" +
			SqlHandler.delimitString(tcmisConstantBean.getConstant()) + "," +
			SqlHandler.delimitString(tcmisConstantBean.getValue()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(TcmisConstantBean tcmisConstantBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(tcmisConstantBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(TcmisConstantBean tcmisConstantBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_CONSTANT + "=" +
				SqlHandler.delimitString(tcmisConstantBean.getConstant()) + "," +
			ATTRIBUTE_VALUE + "=" +
				SqlHandler.delimitString(tcmisConstantBean.getValue()) + " " +
			"where " + ATTRIBUTE_CONSTANT + "=" +
				tcmisConstantBean.getConstant();

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

		Collection tcmisConstantBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			TcmisConstantBean tcmisConstantBean = new TcmisConstantBean();
			load(dataSetRow, tcmisConstantBean);
			tcmisConstantBeanColl.add(tcmisConstantBean);
		}

		return tcmisConstantBeanColl;
	}
}