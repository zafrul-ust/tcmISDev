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
import com.tcmis.client.report.beans.MsdsRevisionViewBean;


/******************************************************************************
 * CLASSNAME: MsdsRevisionViewBeanFactory <br>
 * @version: 1.0, Apr 8, 2008 <br>
 *****************************************************************************/


public class MsdsRevisionViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FACILITY = "FACILITY";
	public String ATTRIBUTE_PART_NO = "PART_NO";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_PART_ID = "PART_ID";
	public String ATTRIBUTE_TRADE_NAME = "TRADE_NAME";
	public String ATTRIBUTE_MANUFACTURER = "MANUFACTURER";
	public String ATTRIBUTE_LAST_REVISION_DATE = "LAST_REVISION_DATE";
	public String ATTRIBUTE_LAST_REQUEST_DATE = "LAST_REQUEST_DATE";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";

	//table name
	public String TABLE = "MSDS_REVISION_VIEW";


	//constructor
	public MsdsRevisionViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("facility")) {
			return ATTRIBUTE_FACILITY;
		}
		else if(attributeName.equals("partNo")) {
			return ATTRIBUTE_PART_NO;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("partId")) {
			return ATTRIBUTE_PART_ID;
		}
		else if(attributeName.equals("tradeName")) {
			return ATTRIBUTE_TRADE_NAME;
		}
		else if(attributeName.equals("manufacturer")) {
			return ATTRIBUTE_MANUFACTURER;
		}
		else if(attributeName.equals("lastRevisionDate")) {
			return ATTRIBUTE_LAST_REVISION_DATE;
		}
		else if(attributeName.equals("lastRequestDate")) {
			return ATTRIBUTE_LAST_REQUEST_DATE;
		}
		else if(attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, MsdsRevisionViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(MsdsRevisionViewBean msdsRevisionViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + msdsRevisionViewBean.getCompanyId());

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


	public int delete(MsdsRevisionViewBean msdsRevisionViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + msdsRevisionViewBean.getCompanyId());

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
	public int insert(MsdsRevisionViewBean msdsRevisionViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(msdsRevisionViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(MsdsRevisionViewBean msdsRevisionViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_FACILITY + "," +
			ATTRIBUTE_PART_NO + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_PART_ID + "," +
			ATTRIBUTE_TRADE_NAME + "," +
			ATTRIBUTE_MANUFACTURER + "," +
			ATTRIBUTE_LAST_REVISION_DATE + "," +
			ATTRIBUTE_LAST_REQUEST_DATE + "," +
			ATTRIBUTE_CATALOG_COMPANY_ID + ")" +
			" values (" +
			SqlHandler.delimitString(msdsRevisionViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(msdsRevisionViewBean.getFacility()) + "," +
			SqlHandler.delimitString(msdsRevisionViewBean.getPartNo()) + "," +
			msdsRevisionViewBean.getItemId() + "," +
			msdsRevisionViewBean.getPartId() + "," +
			SqlHandler.delimitString(msdsRevisionViewBean.getTradeName()) + "," +
			SqlHandler.delimitString(msdsRevisionViewBean.getManufacturer()) + "," +
			DateHandler.getOracleToDateFunction(msdsRevisionViewBean.getLastRevisionDate()) + "," +
			DateHandler.getOracleToDateFunction(msdsRevisionViewBean.getLastRequestDate()) + "," +
			SqlHandler.delimitString(msdsRevisionViewBean.getCatalogCompanyId()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(MsdsRevisionViewBean msdsRevisionViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(msdsRevisionViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(MsdsRevisionViewBean msdsRevisionViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(msdsRevisionViewBean.getCompanyId()) + "," +
			ATTRIBUTE_FACILITY + "=" + 
				SqlHandler.delimitString(msdsRevisionViewBean.getFacility()) + "," +
			ATTRIBUTE_PART_NO + "=" + 
				SqlHandler.delimitString(msdsRevisionViewBean.getPartNo()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(msdsRevisionViewBean.getItemId()) + "," +
			ATTRIBUTE_PART_ID + "=" + 
				StringHandler.nullIfZero(msdsRevisionViewBean.getPartId()) + "," +
			ATTRIBUTE_TRADE_NAME + "=" + 
				SqlHandler.delimitString(msdsRevisionViewBean.getTradeName()) + "," +
			ATTRIBUTE_MANUFACTURER + "=" + 
				SqlHandler.delimitString(msdsRevisionViewBean.getManufacturer()) + "," +
			ATTRIBUTE_LAST_REVISION_DATE + "=" + 
				DateHandler.getOracleToDateFunction(msdsRevisionViewBean.getLastRevisionDate()) + "," +
			ATTRIBUTE_LAST_REQUEST_DATE + "=" + 
				DateHandler.getOracleToDateFunction(msdsRevisionViewBean.getLastRequestDate()) + "," +
			ATTRIBUTE_CATALOG_COMPANY_ID + "=" + 
				SqlHandler.delimitString(msdsRevisionViewBean.getCatalogCompanyId()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				msdsRevisionViewBean.getCompanyId();

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

		Collection msdsRevisionViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);
        if(log.isDebugEnabled()){
            log.debug("QUERY:" + query);
        }
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			MsdsRevisionViewBean msdsRevisionViewBean = new MsdsRevisionViewBean();
			load(dataSetRow, msdsRevisionViewBean);
			msdsRevisionViewBeanColl.add(msdsRevisionViewBean);
		}

		return msdsRevisionViewBeanColl;
	}
}