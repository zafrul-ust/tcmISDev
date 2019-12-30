package com.tcmis.client.peiprojects.factory;


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
import com.tcmis.client.peiprojects.beans.PeiProjectKeywordBean;


/******************************************************************************
 * CLASSNAME: PeiProjectKeywordBeanFactory <br>
 * @version: 1.0, Dec 14, 2005 <br>
 *****************************************************************************/


public class PeiProjectKeywordBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PROJECT_ID = "PROJECT_ID";
	public String ATTRIBUTE_KEYWORD_ID = "KEYWORD_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";

	//table name
	public String TABLE = "PEI_PROJECT_KEYWORD";


	//constructor
	public PeiProjectKeywordBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("projectId")) {
			return ATTRIBUTE_PROJECT_ID;
		}
		else if(attributeName.equals("keywordId")) {
			return ATTRIBUTE_KEYWORD_ID;
		}
		else if(attributeName.equals("companyId")) {
		 return ATTRIBUTE_COMPANY_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PeiProjectKeywordBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PeiProjectKeywordBean peiProjectKeywordBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("projectId", "SearchCriterion.EQUALS",
			"" + peiProjectKeywordBean.getProjectId());

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


	public int delete(PeiProjectKeywordBean peiProjectKeywordBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("projectId", "SearchCriterion.EQUALS",
			"" + peiProjectKeywordBean.getProjectId());

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
	//insert
	public int insert(PeiProjectKeywordBean peiProjectKeywordBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(peiProjectKeywordBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PeiProjectKeywordBean peiProjectKeywordBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_PROJECT_ID + "," +
			ATTRIBUTE_KEYWORD_ID + ")" +
			" values (" +
			SqlHandler.delimitString(peiProjectKeywordBean.getCompanyId()) + "," +
			peiProjectKeywordBean.getProjectId() + "," +
			SqlHandler.delimitString(peiProjectKeywordBean.getKeywordId()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PeiProjectKeywordBean peiProjectKeywordBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(peiProjectKeywordBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PeiProjectKeywordBean peiProjectKeywordBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PROJECT_ID + "=" +
				StringHandler.nullIfZero(peiProjectKeywordBean.getProjectId()) + "," +
			ATTRIBUTE_KEYWORD_ID + "=" +
				SqlHandler.delimitString(peiProjectKeywordBean.getKeywordId()) + " " +
			"where " + ATTRIBUTE_PROJECT_ID + "=" +
				peiProjectKeywordBean.getProjectId();

		return new SqlManager().update(conn, query);
	}


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

		Collection peiProjectKeywordBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PeiProjectKeywordBean peiProjectKeywordBean = new PeiProjectKeywordBean();
			load(dataSetRow, peiProjectKeywordBean);
			peiProjectKeywordBeanColl.add(peiProjectKeywordBean);
		}

		return peiProjectKeywordBeanColl;
	}
}