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
import com.tcmis.client.peiprojects.beans.PeiProjectSavingsBean;


/******************************************************************************
 * CLASSNAME: PeiProjectSavingsBeanFactory <br>
 * @version: 1.0, Dec 15, 2005 <br>
 *****************************************************************************/


public class PeiProjectSavingsBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PROJECT_ID = "PROJECT_ID";
	public String ATTRIBUTE_PERIOD_ID = "PERIOD_ID";
	public String ATTRIBUTE_PROJECTED_PERIOD_SAVINGS = "PROJECTED_PERIOD_SAVINGS";
	public String ATTRIBUTE_ACTUAL_PERIOD_SAVINGS = "ACTUAL_PERIOD_SAVINGS";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_PERIOD_NAME = "PERIOD_NAME";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";

	//table name
	public String TABLE = "PEI_PROJECT_SAVINGS";


	//constructor
	public PeiProjectSavingsBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("projectId")) {
			return ATTRIBUTE_PROJECT_ID;
		}
		else if(attributeName.equals("periodId")) {
			return ATTRIBUTE_PERIOD_ID;
		}
		else if(attributeName.equals("projectedPeriodSavings")) {
			return ATTRIBUTE_PROJECTED_PERIOD_SAVINGS;
		}
		else if(attributeName.equals("actualPeriodSavings")) {
			return ATTRIBUTE_ACTUAL_PERIOD_SAVINGS;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("periodName")) {
			return ATTRIBUTE_PERIOD_NAME;
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
		return super.getType(attributeName, PeiProjectSavingsBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PeiProjectSavingsBean peiProjectSavingsBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("projectId", "SearchCriterion.EQUALS",
			"" + peiProjectSavingsBean.getProjectId());

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


	public int delete(PeiProjectSavingsBean peiProjectSavingsBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("projectId", "SearchCriterion.EQUALS",
			"" + peiProjectSavingsBean.getProjectId());

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
	public int insert(PeiProjectSavingsBean peiProjectSavingsBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(peiProjectSavingsBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PeiProjectSavingsBean peiProjectSavingsBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_PROJECT_ID + "," +
			ATTRIBUTE_PERIOD_ID + "," +
			ATTRIBUTE_PROJECTED_PERIOD_SAVINGS + "," +
			ATTRIBUTE_ACTUAL_PERIOD_SAVINGS + "," +
			ATTRIBUTE_CURRENCY_ID + "," +
			ATTRIBUTE_PERIOD_NAME + ")" +
			" values (" +
			SqlHandler.delimitString(peiProjectSavingsBean.getCompanyId()) + "," +
			peiProjectSavingsBean.getProjectId() + "," +
			peiProjectSavingsBean.getPeriodId() + "," +
			peiProjectSavingsBean.getProjectedPeriodSavings() + "," +
			peiProjectSavingsBean.getActualPeriodSavings() + "," +
			SqlHandler.delimitString(peiProjectSavingsBean.getCurrencyId()) + "," +
			SqlHandler.delimitString(peiProjectSavingsBean.getPeriodName()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PeiProjectSavingsBean peiProjectSavingsBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(peiProjectSavingsBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PeiProjectSavingsBean peiProjectSavingsBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			/*ATTRIBUTE_PROJECT_ID + "=" +
				StringHandler.nullIfZero(peiProjectSavingsBean.getProjectId()) + "," +
			ATTRIBUTE_PERIOD_ID + "=" +
				StringHandler.nullIfZero(peiProjectSavingsBean.getPeriodId()) + "," +*/
			ATTRIBUTE_PROJECTED_PERIOD_SAVINGS + "=" +
				StringHandler.nullIfZero(peiProjectSavingsBean.getProjectedPeriodSavings()) + "," +
			ATTRIBUTE_ACTUAL_PERIOD_SAVINGS + "=" +
				StringHandler.nullIfZero(peiProjectSavingsBean.getActualPeriodSavings()) + "," +
			ATTRIBUTE_CURRENCY_ID + "=" +
				SqlHandler.delimitString(peiProjectSavingsBean.getCurrencyId()) + "," +
			ATTRIBUTE_PERIOD_NAME + "=" +
				SqlHandler.delimitString(peiProjectSavingsBean.getPeriodName()) + " " +
			"where " + ATTRIBUTE_PROJECT_ID + "=" +
				peiProjectSavingsBean.getProjectId() + " and " + ATTRIBUTE_PERIOD_ID + "=" +
				peiProjectSavingsBean.getPeriodId();

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

		Collection peiProjectSavingsBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PeiProjectSavingsBean peiProjectSavingsBean = new PeiProjectSavingsBean();
			load(dataSetRow, peiProjectSavingsBean);
			peiProjectSavingsBeanColl.add(peiProjectSavingsBean);
		}

		return peiProjectSavingsBeanColl;
	}
}