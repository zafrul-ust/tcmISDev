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
import com.tcmis.internal.supply.beans.ShipToLocationViewBean;
import com.tcmis.internal.supply.beans.SupplierAddressViewBean;

/******************************************************************************
 * CLASSNAME: ShipToLocationViewBeanFactory <br>
 * @version: 1.0, Aug 28, 2007 <br>
 *****************************************************************************/

public class ShipToLocationViewBeanFactory extends BaseBeanFactory 
{
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
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_SEARCH = "SEARCH";

	//table name
	public String TABLE = "SHIP_TO_LOCATION_VIEW";

	//constructor
	public ShipToLocationViewBeanFactory(DbManager dbManager) 
	{
		super(dbManager);
	}

	//get column names
	public String getColumnName(String attributeName) 
	{
		if(attributeName.equals("locationId")) {
			return ATTRIBUTE_LOCATION_ID;
		}
		else if(attributeName.equals("countryAbbrev")) {
			return ATTRIBUTE_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("stateAbbrev")) {
			return ATTRIBUTE_STATE_ABBREV;
		}
		else if(attributeName.equals("addressLine1")) {
			return ATTRIBUTE_ADDRESS_LINE_1;
		}
		else if(attributeName.equals("addressLine2")) {
			return ATTRIBUTE_ADDRESS_LINE_2;
		}
		else if(attributeName.equals("addressLine3")) {
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
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("search")) {
			return ATTRIBUTE_SEARCH;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) 
	{
		return super.getType(attributeName, ShipToLocationViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ShipToLocationViewBean shipToLocationViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("locationId", "SearchCriterion.EQUALS",
			"" + shipToLocationViewBean.getLocationId());

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


	public int delete(ShipToLocationViewBean shipToLocationViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("locationId", "SearchCriterion.EQUALS",
			"" + shipToLocationViewBean.getLocationId());

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
	public int insert(ShipToLocationViewBean shipToLocationViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(shipToLocationViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ShipToLocationViewBean shipToLocationViewBean, Connection conn)
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
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_SEARCH + ")" +
			" values (" +
			SqlHandler.delimitString(shipToLocationViewBean.getLocationId()) + "," +
			SqlHandler.delimitString(shipToLocationViewBean.getCountryAbbrev()) + "," +
			SqlHandler.delimitString(shipToLocationViewBean.getStateAbbrev()) + "," +
			SqlHandler.delimitString(shipToLocationViewBean.getAddressLine1()) + "," +
			SqlHandler.delimitString(shipToLocationViewBean.getAddressLine2()) + "," +
			SqlHandler.delimitString(shipToLocationViewBean.getAddressLine3()) + "," +
			SqlHandler.delimitString(shipToLocationViewBean.getCity()) + "," +
			SqlHandler.delimitString(shipToLocationViewBean.getZip()) + "," +
			SqlHandler.delimitString(shipToLocationViewBean.getLocationDesc()) + "," +
			SqlHandler.delimitString(shipToLocationViewBean.getClientLocationCode()) + "," +
			SqlHandler.delimitString(shipToLocationViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(shipToLocationViewBean.getFacilityId()) + "," +
			SqlHandler.delimitString(shipToLocationViewBean.getSearch()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ShipToLocationViewBean shipToLocationViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(shipToLocationViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ShipToLocationViewBean shipToLocationViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_LOCATION_ID + "=" + 
				SqlHandler.delimitString(shipToLocationViewBean.getLocationId()) + "," +
			ATTRIBUTE_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(shipToLocationViewBean.getCountryAbbrev()) + "," +
			ATTRIBUTE_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(shipToLocationViewBean.getStateAbbrev()) + "," +
			ATTRIBUTE_ADDRESS_LINE_1 + "=" + 
				SqlHandler.delimitString(shipToLocationViewBean.getAddressLine1()) + "," +
			ATTRIBUTE_ADDRESS_LINE_2 + "=" + 
				SqlHandler.delimitString(shipToLocationViewBean.getAddressLine2()) + "," +
			ATTRIBUTE_ADDRESS_LINE_3 + "=" + 
				SqlHandler.delimitString(shipToLocationViewBean.getAddressLine3()) + "," +
			ATTRIBUTE_CITY + "=" + 
				SqlHandler.delimitString(shipToLocationViewBean.getCity()) + "," +
			ATTRIBUTE_ZIP + "=" + 
				SqlHandler.delimitString(shipToLocationViewBean.getZip()) + "," +
			ATTRIBUTE_LOCATION_DESC + "=" + 
				SqlHandler.delimitString(shipToLocationViewBean.getLocationDesc()) + "," +
			ATTRIBUTE_CLIENT_LOCATION_CODE + "=" + 
				SqlHandler.delimitString(shipToLocationViewBean.getClientLocationCode()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(shipToLocationViewBean.getCompanyId()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" + 
				SqlHandler.delimitString(shipToLocationViewBean.getFacilityId()) + "," +
			ATTRIBUTE_SEARCH + "=" + 
				SqlHandler.delimitString(shipToLocationViewBean.getSearch()) + " " +
			"where " + ATTRIBUTE_LOCATION_ID + "=" +
				shipToLocationViewBean.getLocationId();

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
		throws BaseException 
	{

		Connection connection = null;
		Collection c = null;
		try 
		{
			connection = this.getDbManager().getConnection();
			c = select(criteria, sortCriteria, connection);
		}
		finally 
		{
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException 
	{

		Collection shipToLocationViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) 
		{
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ShipToLocationViewBean shipToLocationViewBean = new ShipToLocationViewBean();
			load(dataSetRow, shipToLocationViewBean);
			shipToLocationViewBeanColl.add(shipToLocationViewBean);
		}

		return shipToLocationViewBeanColl;
	}
	
	public Collection selectDistinct(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try 
		{
			connection = this.getDbManager().getConnection();
			c = selectDistinct(criteria, sortCriteria, connection);
		}
		finally
		{
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
		
	public Collection selectDistinct(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {
		Collection shipToLocationViewBeanColl = new Vector();
		
		String columnsToSelect = ATTRIBUTE_LOCATION_ID + "," +
				ATTRIBUTE_COUNTRY_ABBREV + "," +
				ATTRIBUTE_STATE_ABBREV + "," +
				ATTRIBUTE_ADDRESS_LINE_1 + "," +
				ATTRIBUTE_ADDRESS_LINE_2 + "," +
				ATTRIBUTE_ADDRESS_LINE_3 + "," +
				ATTRIBUTE_CITY + "," +
				ATTRIBUTE_ZIP + "," +
				ATTRIBUTE_LOCATION_DESC + "," +
				ATTRIBUTE_CLIENT_LOCATION_CODE + "," +
				ATTRIBUTE_COMPANY_ID + "," +
				ATTRIBUTE_FACILITY_ID;
		String query = "select distinct " + columnsToSelect + " from " + TABLE + " " +
		getWhereClause(criteria) + getOrderByClause(sortCriteria);
		
		DataSet dataSet = new SqlManager().select(conn, query);
		
		Iterator dataIter = dataSet.iterator();
		
		while (dataIter.hasNext()) 
		{
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ShipToLocationViewBean shipToLocationViewBean = new ShipToLocationViewBean();
			load(dataSetRow, shipToLocationViewBean);
			shipToLocationViewBeanColl.add(shipToLocationViewBean);
		}
		
		return shipToLocationViewBeanColl;
	}
}