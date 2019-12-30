package com.tcmis.client.utc.factory;


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
import com.tcmis.client.utc.beans.PwaMsdsViewBean;


/******************************************************************************
 * CLASSNAME: PwaMsdsViewBeanFactory <br>
 * @version: 1.0, Jul 25, 2006 <br>
 *****************************************************************************/


public class PwaMsdsViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_CATALOG_ITEM_ID = "CATALOG_ITEM_ID";
	public String ATTRIBUTE_CATALOG_COMPONENT_ID = "CATALOG_COMPONENT_ID";
	public String ATTRIBUTE_MFG_NAME = "MFG_NAME";
	public String ATTRIBUTE_URL_IN = "URL_IN";
	public String ATTRIBUTE_OUTPUT_FILENAME = "OUTPUT_FILENAME";
	public String ATTRIBUTE_REVISION_DATE = "REVISION_DATE";
	public String ATTRIBUTE_PWA_REVISION_DATE = "PWA_REVISION_DATE";
	public String ATTRIBUTE_CATALOG_COMPONENT_ITEM_ID = "CATALOG_COMPONENT_ITEM_ID";
        public String ATTRIBUTE_DIFFERENT_REV = "DIFFERENT_REV";
        public String ATTRIBUTE_CONTENT = "CONTENT";

	//table name
	public String TABLE = "PWA_MSDS_VIEW";


	//constructor
	public PwaMsdsViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("catalogItemId")) {
			return ATTRIBUTE_CATALOG_ITEM_ID;
		}
		else if(attributeName.equals("catalogComponentId")) {
			return ATTRIBUTE_CATALOG_COMPONENT_ID;
		}
		else if(attributeName.equals("mfgName")) {
			return ATTRIBUTE_MFG_NAME;
		}
		else if(attributeName.equals("urlIn")) {
			return ATTRIBUTE_URL_IN;
		}
		else if(attributeName.equals("outputFilename")) {
			return ATTRIBUTE_OUTPUT_FILENAME;
		}
		else if(attributeName.equals("revisionDate")) {
			return ATTRIBUTE_REVISION_DATE;
		}
		else if(attributeName.equals("pwaRevisionDate")) {
			return ATTRIBUTE_PWA_REVISION_DATE;
		}
                else if(attributeName.equals("catalogComponentItemId")) {
                  return ATTRIBUTE_CATALOG_COMPONENT_ITEM_ID;
                }
                else if(attributeName.equals("differentRev")) {
                  return ATTRIBUTE_DIFFERENT_REV;
                }else if(attributeName.equals("content")) {
                  return ATTRIBUTE_CONTENT;
                }
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PwaMsdsViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PwaMsdsViewBean pwaMsdsViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("catalogItemId", "SearchCriterion.EQUALS",
			"" + pwaMsdsViewBean.getCatalogItemId());

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


	public int delete(PwaMsdsViewBean pwaMsdsViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("catalogItemId", "SearchCriterion.EQUALS",
			"" + pwaMsdsViewBean.getCatalogItemId());

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
	public int insert(PwaMsdsViewBean pwaMsdsViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(pwaMsdsViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PwaMsdsViewBean pwaMsdsViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_CATALOG_ITEM_ID + "," +
			ATTRIBUTE_CATALOG_COMPONENT_ID + "," +
			ATTRIBUTE_MFG_NAME + "," +
			ATTRIBUTE_URL_IN + "," +
			ATTRIBUTE_OUTPUT_FILENAME + "," +
			ATTRIBUTE_REVISION_DATE + "," +
			ATTRIBUTE_PWA_REVISION_DATE + ")" +
			" values (" +
			SqlHandler.delimitString(pwaMsdsViewBean.getCatalogItemId()) + "," +
			SqlHandler.delimitString(pwaMsdsViewBean.getCatalogComponentId()) + "," +
			SqlHandler.delimitString(pwaMsdsViewBean.getMfgName()) + "," +
			SqlHandler.delimitString(pwaMsdsViewBean.getUrlIn()) + "," +
			SqlHandler.delimitString(pwaMsdsViewBean.getOutputFilename()) + "," +
			DateHandler.getOracleToDateFunction(pwaMsdsViewBean.getRevisionDate()) + "," +
			DateHandler.getOracleToDateFunction(pwaMsdsViewBean.getPwaRevisionDate()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PwaMsdsViewBean pwaMsdsViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(pwaMsdsViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PwaMsdsViewBean pwaMsdsViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_CATALOG_ITEM_ID + "=" +
				SqlHandler.delimitString(pwaMsdsViewBean.getCatalogItemId()) + "," +
			ATTRIBUTE_CATALOG_COMPONENT_ID + "=" +
				SqlHandler.delimitString(pwaMsdsViewBean.getCatalogComponentId()) + "," +
			ATTRIBUTE_MFG_NAME + "=" +
				SqlHandler.delimitString(pwaMsdsViewBean.getMfgName()) + "," +
			ATTRIBUTE_URL_IN + "=" +
				SqlHandler.delimitString(pwaMsdsViewBean.getUrlIn()) + "," +
			ATTRIBUTE_OUTPUT_FILENAME + "=" +
				SqlHandler.delimitString(pwaMsdsViewBean.getOutputFilename()) + "," +
			ATTRIBUTE_REVISION_DATE + "=" +
				DateHandler.getOracleToDateFunction(pwaMsdsViewBean.getRevisionDate()) + "," +
			ATTRIBUTE_PWA_REVISION_DATE + "=" +
				DateHandler.getOracleToDateFunction(pwaMsdsViewBean.getPwaRevisionDate()) + " " +
			"where " + ATTRIBUTE_CATALOG_ITEM_ID + "=" +
				pwaMsdsViewBean.getCatalogItemId();

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

		Collection pwaMsdsViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
                               getWhereClause(criteria);

                DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PwaMsdsViewBean pwaMsdsViewBean = new PwaMsdsViewBean();
			load(dataSetRow, pwaMsdsViewBean);
			pwaMsdsViewBeanColl.add(pwaMsdsViewBean);
		}

		return pwaMsdsViewBeanColl;
	}

        //this method will run cpig procedure to get data synch for running report
        public void updateCpigForPwa() throws Exception {
          Connection connection = this.getDbManager().getConnection();

          this.getDbManager().doProcedure("PKG_PWA_FEED.p_load_cpig",null);
          this.getDbManager().returnConnection(connection);
        } //end of method
} //end of class