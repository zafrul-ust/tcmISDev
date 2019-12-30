package com.tcmis.internal.hub.factory;


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
import com.tcmis.internal.hub.beans.GenericLocationViewBean;


/******************************************************************************
 * CLASSNAME: GenericLocationViewBeanFactory <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class GenericLocationViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_LOCATION_ID = "LOCATION_ID";
	public String ATTRIBUTE_COUNTRY_ABBREV = "COUNTRY_ABBREV";
	public String ATTRIBUTE_STATE_ABBREV = "STATE_ABBREV";
	public String ATTRIBUTE_ADDRESS_LINE_1 = "ADDRESS_LINE_1";
	public String ATTRIBUTE_ADDRESS_LINE_2 = "ADDRESS_LINE_2";
	public String ATTRIBUTE_ADDRESS_LINE_3 = "ADDRESS_LINE_3";
	public String ATTRIBUTE_CITY = "CITY";
	public String ATTRIBUTE_ZIP = "ZIP";
	public String ATTRIBUTE_LOCATION_DESC = "LOCATION_DESC";
	public String ATTRIBUTE_CLIENT_LOCATION_CODE = "CLIENT_LOCATION_CODE";
	public String ATTRIBUTE_SEARCH = "SEARCH";

	//table name
	public String TABLE = "GENERIC_LOCATION_VIEW";


	//constructor
	public GenericLocationViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("locationId")) {
			return ATTRIBUTE_LOCATION_ID;
		}
		else if(attributeName.equals("countryAbbrev")) {
			return ATTRIBUTE_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("stateAbbrev")) {
			return ATTRIBUTE_STATE_ABBREV;
		}
		else if(attributeName.equals("addressLine11")) {
			return ATTRIBUTE_ADDRESS_LINE_1;
		}
		else if(attributeName.equals("addressLine22")) {
			return ATTRIBUTE_ADDRESS_LINE_2;
		}
		else if(attributeName.equals("addressLine33")) {
			return ATTRIBUTE_ADDRESS_LINE_3;
		}
		else if(attributeName.equals("city")) {
			return ATTRIBUTE_CITY;
		}
		else if(attributeName.equals("zip")) {
			return ATTRIBUTE_ZIP;
		}
		else if(attributeName.equals("locationDesc")) {
			return ATTRIBUTE_LOCATION_DESC;
		}
		else if(attributeName.equals("clientLocationCode")) {
			return ATTRIBUTE_CLIENT_LOCATION_CODE;
		}
		else if(attributeName.equals("search")) {
			return ATTRIBUTE_SEARCH;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, GenericLocationViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(GenericLocationViewBean genericLocationViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("locationId", "SearchCriterion.EQUALS",
			"" + genericLocationViewBean.getLocationId());

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


	public int delete(GenericLocationViewBean genericLocationViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("locationId", "SearchCriterion.EQUALS",
			"" + genericLocationViewBean.getLocationId());

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
	public int insert(GenericLocationViewBean genericLocationViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(genericLocationViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(GenericLocationViewBean genericLocationViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_LOCATION_ID + "," +
			ATTRIBUTE_COUNTRY_ABBREV + "," +
			ATTRIBUTE_STATE_ABBREV + "," +
			ATTRIBUTE_ADDRESS_LINE_1 + "," +
			ATTRIBUTE_ADDRESS_LINE_2 + "," +
			ATTRIBUTE_ADDRESS_LINE_3 + "," +
			ATTRIBUTE_CITY + "," +
			ATTRIBUTE_ZIP + "," +
			ATTRIBUTE_LOCATION_DESC + "," +
			ATTRIBUTE_CLIENT_LOCATION_CODE + "," +
			ATTRIBUTE_SEARCH + ")" +
			" values (" +
			SqlHandler.delimitString(genericLocationViewBean.getLocationId()) + "," +
			SqlHandler.delimitString(genericLocationViewBean.getCountryAbbrev()) + "," +
			SqlHandler.delimitString(genericLocationViewBean.getStateAbbrev()) + "," +
			SqlHandler.delimitString(genericLocationViewBean.getAddressLine1()) + "," +
			SqlHandler.delimitString(genericLocationViewBean.getAddressLine2()) + "," +
			SqlHandler.delimitString(genericLocationViewBean.getAddressLine3()) + "," +
			SqlHandler.delimitString(genericLocationViewBean.getCity()) + "," +
			SqlHandler.delimitString(genericLocationViewBean.getZip()) + "," +
			SqlHandler.delimitString(genericLocationViewBean.getLocationDesc()) + "," +
			SqlHandler.delimitString(genericLocationViewBean.getClientLocationCode()) + "," +
			SqlHandler.delimitString(genericLocationViewBean.getSearch()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(GenericLocationViewBean genericLocationViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(genericLocationViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(GenericLocationViewBean genericLocationViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_LOCATION_ID + "=" + 
				SqlHandler.delimitString(genericLocationViewBean.getLocationId()) + "," +
			ATTRIBUTE_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(genericLocationViewBean.getCountryAbbrev()) + "," +
			ATTRIBUTE_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(genericLocationViewBean.getStateAbbrev()) + "," +
			ATTRIBUTE_ADDRESS_LINE_1 + "=" + 
				SqlHandler.delimitString(genericLocationViewBean.getAddressLine1()) + "," +
			ATTRIBUTE_ADDRESS_LINE_2 + "=" + 
				SqlHandler.delimitString(genericLocationViewBean.getAddressLine2()) + "," +
			ATTRIBUTE_ADDRESS_LINE_3 + "=" + 
				SqlHandler.delimitString(genericLocationViewBean.getAddressLine3()) + "," +
			ATTRIBUTE_CITY + "=" + 
				SqlHandler.delimitString(genericLocationViewBean.getCity()) + "," +
			ATTRIBUTE_ZIP + "=" + 
				SqlHandler.delimitString(genericLocationViewBean.getZip()) + "," +
			ATTRIBUTE_LOCATION_DESC + "=" + 
				SqlHandler.delimitString(genericLocationViewBean.getLocationDesc()) + "," +
			ATTRIBUTE_CLIENT_LOCATION_CODE + "=" + 
				SqlHandler.delimitString(genericLocationViewBean.getClientLocationCode()) + "," +
			ATTRIBUTE_SEARCH + "=" + 
				SqlHandler.delimitString(genericLocationViewBean.getSearch()) + " " +
			"where " + ATTRIBUTE_LOCATION_ID + "=" +
				genericLocationViewBean.getLocationId();

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

		Collection genericLocationViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			GenericLocationViewBean genericLocationViewBean = new GenericLocationViewBean();
			load(dataSetRow, genericLocationViewBean);
			genericLocationViewBeanColl.add(genericLocationViewBean);
		}

		return genericLocationViewBeanColl;
	}
}