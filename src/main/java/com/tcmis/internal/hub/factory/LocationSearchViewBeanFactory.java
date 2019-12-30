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
import com.tcmis.internal.hub.beans.LocationSearchViewBean;


/******************************************************************************
 * CLASSNAME: LocationSearchViewBeanFactory <br>
 * @version: 1.0, Jul 7, 2009 <br>
 *****************************************************************************/


public class LocationSearchViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_DODAAC = "DODAAC";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_LOCATION_ID = "LOCATION_ID";
	public String ATTRIBUTE_HAZMAT_CAPABLE = "HAZMAT_CAPABLE";
	public String ATTRIBUTE_DODAAC_TYPE = "DODAAC_TYPE";
	public String ATTRIBUTE_ADDRESS_LINE_1_DISPLAY = "ADDRESS_LINE_1_DISPLAY";
	public String ATTRIBUTE_ADDRESS_LINE_2_DISPLAY = "ADDRESS_LINE_2_DISPLAY";
	public String ATTRIBUTE_ADDRESS_LINE_3_DISPLAY = "ADDRESS_LINE_3_DISPLAY";
	public String ATTRIBUTE_ADDRESS_LINE_4_DISPLAY = "ADDRESS_LINE_4_DISPLAY";
	public String ATTRIBUTE_SEARCH = "SEARCH";
	public String ATTRIBUTE_CITY = "CITY";
	public String ATTRIBUTE_SENT_TO_AIRGAS = "SENT_TO_AIRGAS";
	public String ATTRIBUTE_COUNTRY_ABBREV = "COUNTRY_ABBREV";
	public String ATTRIBUTE_STATE_ABBREV = "STATE_ABBREV";

	//table name
	public String TABLE = "LOCATION_SEARCH_VIEW";


	//constructor
	public LocationSearchViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("dodaac")) {
			return ATTRIBUTE_DODAAC;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("locationId")) {
			return ATTRIBUTE_LOCATION_ID;
		}
		else if(attributeName.equals("hazmatCapable")) {
			return ATTRIBUTE_HAZMAT_CAPABLE;
		}
		else if(attributeName.equals("dodaacType")) {
			return ATTRIBUTE_DODAAC_TYPE;
		}
		else if(attributeName.equals("addressLine1Display")) {
			return ATTRIBUTE_ADDRESS_LINE_1_DISPLAY;
		}
		else if(attributeName.equals("addressLine2Display")) {
			return ATTRIBUTE_ADDRESS_LINE_2_DISPLAY;
		}
		else if(attributeName.equals("addressLine3Display")) {
			return ATTRIBUTE_ADDRESS_LINE_3_DISPLAY;
		}
		else if(attributeName.equals("addressLine4Display")) {
			return ATTRIBUTE_ADDRESS_LINE_4_DISPLAY;
		}
		else if(attributeName.equals("search")) {
			return ATTRIBUTE_SEARCH;
		}
		else if(attributeName.equals("city")) {
			return ATTRIBUTE_CITY;
		}
		else if(attributeName.equals("sentToAirgas")) {
			return ATTRIBUTE_SENT_TO_AIRGAS;
		}
		else if(attributeName.equals("countryAbbrev")) {
			return ATTRIBUTE_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("stateAbbrev")) {
			return ATTRIBUTE_STATE_ABBREV;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, LocationSearchViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(LocationSearchViewBean locationSearchViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("dodaac", "SearchCriterion.EQUALS",
			"" + locationSearchViewBean.getDodaac());

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


	public int delete(LocationSearchViewBean locationSearchViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("dodaac", "SearchCriterion.EQUALS",
			"" + locationSearchViewBean.getDodaac());

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
	public int insert(LocationSearchViewBean locationSearchViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(locationSearchViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(LocationSearchViewBean locationSearchViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_DODAAC + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_LOCATION_ID + "," +
			ATTRIBUTE_HAZMAT_CAPABLE + "," +
			ATTRIBUTE_DODAAC_TYPE + "," +
			ATTRIBUTE_ADDRESS_LINE_1_DISPLAY + "," +
			ATTRIBUTE_ADDRESS_LINE_2_DISPLAY + "," +
			ATTRIBUTE_ADDRESS_LINE_3_DISPLAY + "," +
			ATTRIBUTE_ADDRESS_LINE_4_DISPLAY + "," +
			ATTRIBUTE_SEARCH + "," +
			ATTRIBUTE_CITY + "," +
			ATTRIBUTE_SENT_TO_AIRGAS + "," +
			ATTRIBUTE_COUNTRY_ABBREV + "," +
			ATTRIBUTE_STATE_ABBREV + ")" +
			" values (" +
			SqlHandler.delimitString(locationSearchViewBean.getDodaac()) + "," +
			SqlHandler.delimitString(locationSearchViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(locationSearchViewBean.getLocationId()) + "," +
			SqlHandler.delimitString(locationSearchViewBean.getHazmatCapable()) + "," +
			SqlHandler.delimitString(locationSearchViewBean.getDodaacType()) + "," +
			SqlHandler.delimitString(locationSearchViewBean.getAddressLine1Display()) + "," +
			SqlHandler.delimitString(locationSearchViewBean.getAddressLine2Display()) + "," +
			SqlHandler.delimitString(locationSearchViewBean.getAddressLine3Display()) + "," +
			SqlHandler.delimitString(locationSearchViewBean.getAddressLine4Display()) + "," +
			SqlHandler.delimitString(locationSearchViewBean.getSearch()) + "," +
			SqlHandler.delimitString(locationSearchViewBean.getCity()) + "," +
			SqlHandler.delimitString(locationSearchViewBean.getSentToAirgas()) + "," +
			SqlHandler.delimitString(locationSearchViewBean.getCountryAbbrev()) + "," +
			SqlHandler.delimitString(locationSearchViewBean.getStateAbbrev()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(LocationSearchViewBean locationSearchViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(locationSearchViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(LocationSearchViewBean locationSearchViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_DODAAC + "=" + 
				SqlHandler.delimitString(locationSearchViewBean.getDodaac()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(locationSearchViewBean.getCompanyId()) + "," +
			ATTRIBUTE_LOCATION_ID + "=" + 
				SqlHandler.delimitString(locationSearchViewBean.getLocationId()) + "," +
			ATTRIBUTE_HAZMAT_CAPABLE + "=" + 
				SqlHandler.delimitString(locationSearchViewBean.getHazmatCapable()) + "," +
			ATTRIBUTE_DODAAC_TYPE + "=" + 
				SqlHandler.delimitString(locationSearchViewBean.getDodaacType()) + "," +
			ATTRIBUTE_ADDRESS_LINE_1_DISPLAY + "=" + 
				SqlHandler.delimitString(locationSearchViewBean.getAddressLine1Display()) + "," +
			ATTRIBUTE_ADDRESS_LINE_2_DISPLAY + "=" + 
				SqlHandler.delimitString(locationSearchViewBean.getAddressLine2Display()) + "," +
			ATTRIBUTE_ADDRESS_LINE_3_DISPLAY + "=" + 
				SqlHandler.delimitString(locationSearchViewBean.getAddressLine3Display()) + "," +
			ATTRIBUTE_ADDRESS_LINE_4_DISPLAY + "=" + 
				SqlHandler.delimitString(locationSearchViewBean.getAddressLine4Display()) + "," +
			ATTRIBUTE_SEARCH + "=" + 
				SqlHandler.delimitString(locationSearchViewBean.getSearch()) + "," +
			ATTRIBUTE_CITY + "=" + 
				SqlHandler.delimitString(locationSearchViewBean.getCity()) + "," +
			ATTRIBUTE_SENT_TO_AIRGAS + "=" + 
				SqlHandler.delimitString(locationSearchViewBean.getSentToAirgas()) + "," +
			ATTRIBUTE_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(locationSearchViewBean.getCountryAbbrev()) + "," +
			ATTRIBUTE_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(locationSearchViewBean.getStateAbbrev()) + " " +
			"where " + ATTRIBUTE_DODAAC + "=" +
				locationSearchViewBean.getDodaac();

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

		Collection locationSearchViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);
        if(log.isDebugEnabled()){
            log.debug("QUERY:" + query);
        }
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			LocationSearchViewBean locationSearchViewBean = new LocationSearchViewBean();
			load(dataSetRow, locationSearchViewBean);
			locationSearchViewBeanColl.add(locationSearchViewBean);
		}

		return locationSearchViewBeanColl;
	}
}