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
import com.tcmis.client.report.beans.TcmisFeatureBean;


/******************************************************************************
 * CLASSNAME: TcmisFeatureBeanFactory <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class TcmisFeatureBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FEATURE = "FEATURE";
	public String ATTRIBUTE_FEATURE_MODE = "FEATURE_MODE";
	public String ATTRIBUTE_FEATURE_MESSAGE = "FEATURE_MESSAGE";

	//table name
	public String TABLE = "TCMIS_FEATURE";


	//constructor
	public TcmisFeatureBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("feature")) {
			return ATTRIBUTE_FEATURE;
		}
		else if(attributeName.equals("featureMode")) {
			return ATTRIBUTE_FEATURE_MODE;
		}
		else if(attributeName.equals("featureMessage")) {
			return ATTRIBUTE_FEATURE_MESSAGE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, TcmisFeatureBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(TcmisFeatureBean tcmisFeatureBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + tcmisFeatureBean.getCompanyId());

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


	public int delete(TcmisFeatureBean tcmisFeatureBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + tcmisFeatureBean.getCompanyId());

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
	public int insert(TcmisFeatureBean tcmisFeatureBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(tcmisFeatureBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(TcmisFeatureBean tcmisFeatureBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_FEATURE + "," +
			ATTRIBUTE_FEATURE_MODE + "," +
			ATTRIBUTE_FEATURE_MESSAGE + ")" +
			" values (" +
			SqlHandler.delimitString(tcmisFeatureBean.getCompanyId()) + "," +
			SqlHandler.delimitString(tcmisFeatureBean.getFeature()) + "," +
			tcmisFeatureBean.getFeatureMode() + "," +
			SqlHandler.delimitString(tcmisFeatureBean.getFeatureMessage()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(TcmisFeatureBean tcmisFeatureBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(tcmisFeatureBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(TcmisFeatureBean tcmisFeatureBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(tcmisFeatureBean.getCompanyId()) + "," +
			ATTRIBUTE_FEATURE + "=" + 
				SqlHandler.delimitString(tcmisFeatureBean.getFeature()) + "," +
			ATTRIBUTE_FEATURE_MODE + "=" + 
				StringHandler.nullIfZero(tcmisFeatureBean.getFeatureMode()) + "," +
			ATTRIBUTE_FEATURE_MESSAGE + "=" + 
				SqlHandler.delimitString(tcmisFeatureBean.getFeatureMessage()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				tcmisFeatureBean.getCompanyId();

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

		Collection tcmisFeatureBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			TcmisFeatureBean tcmisFeatureBean = new TcmisFeatureBean();
			load(dataSetRow, tcmisFeatureBean);
			tcmisFeatureBeanColl.add(tcmisFeatureBean);
		}

		return tcmisFeatureBeanColl;
	}
	//select
	public boolean selectExist(SearchCriteria criteria) throws BaseException {

		Connection connection = null;
		boolean result = false;
		try {
			connection = this.getDbManager().getConnection();
			result = selectExist(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}
	public boolean selectExist(SearchCriteria criteria, Connection conn) throws BaseException {

		boolean result = false;
		String query = "select * from " + TABLE + " " +getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);
		if (dataSet.size() > 0) {
			result = true;
		}
		return result;
	}

} //end of class