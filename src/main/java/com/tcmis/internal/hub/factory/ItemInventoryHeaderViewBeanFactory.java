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
import com.tcmis.internal.hub.beans.ItemInventoryHeaderViewBean;


/******************************************************************************
 * CLASSNAME: ItemInventoryHeaderViewBeanFactory <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class ItemInventoryHeaderViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
	public String ATTRIBUTE_PREFERRED_WAREHOUSE = "PREFERRED_WAREHOUSE";
	public String ATTRIBUTE_PLANT_ID = "PLANT_ID";
	public String ATTRIBUTE_BLDG_ID = "BLDG_ID";
	public String ATTRIBUTE_STORAGE_AREA = "STORAGE_AREA";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_COLLECTION = "COLLECTION";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FACILITY_NAME = "FACILITY_NAME";
	public String ATTRIBUTE_HUB_NAME = "HUB_NAME";
	public String ATTRIBUTE_LOCATION_ID = "LOCATION_ID";
	public String ATTRIBUTE_LOCATION_DESC = "LOCATION_DESC";
	public String ATTRIBUTE_ADDRESS_LINE_1 = "ADDRESS_LINE_1";
	public String ATTRIBUTE_ADDRESS_LINE_2 = "ADDRESS_LINE_2";
	public String ATTRIBUTE_ADDRESS_LINE_3 = "ADDRESS_LINE_3";
	public String ATTRIBUTE_CITY = "CITY";
	public String ATTRIBUTE_STATE_ABBREV = "STATE_ABBREV";
	public String ATTRIBUTE_ZIP = "ZIP";
	public String ATTRIBUTE_COUNTRY_ABBREV = "COUNTRY_ABBREV";
	public String ATTRIBUTE_ATTENTION = "ATTENTION";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";

	//table name
	public String TABLE = "ITEM_INVENTORY_HEADER_VIEW";


	//constructor
	public ItemInventoryHeaderViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("branchPlant")) {
			return ATTRIBUTE_BRANCH_PLANT;
		}
		else if(attributeName.equals("preferredWarehouse")) {
			return ATTRIBUTE_PREFERRED_WAREHOUSE;
		}
		else if(attributeName.equals("plantId")) {
			return ATTRIBUTE_PLANT_ID;
		}
		else if(attributeName.equals("bldgId")) {
			return ATTRIBUTE_BLDG_ID;
		}
		else if(attributeName.equals("storageArea")) {
			return ATTRIBUTE_STORAGE_AREA;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if(attributeName.equals("collection")) {
			return ATTRIBUTE_COLLECTION;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("facilityName")) {
			return ATTRIBUTE_FACILITY_NAME;
		}
		else if(attributeName.equals("hubName")) {
			return ATTRIBUTE_HUB_NAME;
		}
		else if(attributeName.equals("locationId")) {
			return ATTRIBUTE_LOCATION_ID;
		}
		else if(attributeName.equals("locationDesc")) {
			return ATTRIBUTE_LOCATION_DESC;
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
		else if(attributeName.equals("stateAbbrev")) {
			return ATTRIBUTE_STATE_ABBREV;
		}
		else if(attributeName.equals("zip")) {
			return ATTRIBUTE_ZIP;
		}
		else if(attributeName.equals("countryAbbrev")) {
			return ATTRIBUTE_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("attention")) {
			return ATTRIBUTE_ATTENTION;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ItemInventoryHeaderViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ItemInventoryHeaderViewBean itemInventoryHeaderViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("branchPlant", "SearchCriterion.EQUALS",
			"" + itemInventoryHeaderViewBean.getBranchPlant());

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


	public int delete(ItemInventoryHeaderViewBean itemInventoryHeaderViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("branchPlant", "SearchCriterion.EQUALS",
			"" + itemInventoryHeaderViewBean.getBranchPlant());

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
	public int insert(ItemInventoryHeaderViewBean itemInventoryHeaderViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(itemInventoryHeaderViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ItemInventoryHeaderViewBean itemInventoryHeaderViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_BRANCH_PLANT + "," +
			ATTRIBUTE_PREFERRED_WAREHOUSE + "," +
			ATTRIBUTE_PLANT_ID + "," +
			ATTRIBUTE_BLDG_ID + "," +
			ATTRIBUTE_STORAGE_AREA + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "," +
			ATTRIBUTE_COLLECTION + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_FACILITY_NAME + "," +
			ATTRIBUTE_HUB_NAME + "," +
			ATTRIBUTE_LOCATION_ID + "," +
			ATTRIBUTE_LOCATION_DESC + "," +
			ATTRIBUTE_ADDRESS_LINE_1 + "," +
			ATTRIBUTE_ADDRESS_LINE_2 + "," +
			ATTRIBUTE_ADDRESS_LINE_3 + "," +
			ATTRIBUTE_CITY + "," +
			ATTRIBUTE_STATE_ABBREV + "," +
			ATTRIBUTE_ZIP + "," +
			ATTRIBUTE_COUNTRY_ABBREV + "," +
			ATTRIBUTE_ATTENTION + "," +
			ATTRIBUTE_FACILITY_ID + ")" +
			" values (" +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getBranchPlant()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getPreferredWarehouse()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getPlantId()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getBldgId()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getStorageArea()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getInventoryGroupName()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getCollection()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getFacilityName()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getHubName()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getLocationId()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getLocationDesc()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getAddressLine1()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getAddressLine2()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getAddressLine3()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getCity()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getStateAbbrev()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getZip()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getCountryAbbrev()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getAttention()) + "," +
			SqlHandler.delimitString(itemInventoryHeaderViewBean.getFacilityId()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ItemInventoryHeaderViewBean itemInventoryHeaderViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(itemInventoryHeaderViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ItemInventoryHeaderViewBean itemInventoryHeaderViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_BRANCH_PLANT + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getBranchPlant()) + "," +
			ATTRIBUTE_PREFERRED_WAREHOUSE + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getPreferredWarehouse()) + "," +
			ATTRIBUTE_PLANT_ID + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getPlantId()) + "," +
			ATTRIBUTE_BLDG_ID + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getBldgId()) + "," +
			ATTRIBUTE_STORAGE_AREA + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getStorageArea()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getInventoryGroupName()) + "," +
			ATTRIBUTE_COLLECTION + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getCollection()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getCompanyId()) + "," +
			ATTRIBUTE_FACILITY_NAME + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getFacilityName()) + "," +
			ATTRIBUTE_HUB_NAME + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getHubName()) + "," +
			ATTRIBUTE_LOCATION_ID + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getLocationId()) + "," +
			ATTRIBUTE_LOCATION_DESC + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getLocationDesc()) + "," +
			ATTRIBUTE_ADDRESS_LINE_1 + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getAddressLine1()) + "," +
			ATTRIBUTE_ADDRESS_LINE_2 + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getAddressLine2()) + "," +
			ATTRIBUTE_ADDRESS_LINE_3 + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getAddressLine3()) + "," +
			ATTRIBUTE_CITY + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getCity()) + "," +
			ATTRIBUTE_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getStateAbbrev()) + "," +
			ATTRIBUTE_ZIP + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getZip()) + "," +
			ATTRIBUTE_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getCountryAbbrev()) + "," +
			ATTRIBUTE_ATTENTION + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getAttention()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" + 
				SqlHandler.delimitString(itemInventoryHeaderViewBean.getFacilityId()) + " " +
			"where " + ATTRIBUTE_BRANCH_PLANT + "=" +
				itemInventoryHeaderViewBean.getBranchPlant();

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

		Collection itemInventoryHeaderViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ItemInventoryHeaderViewBean itemInventoryHeaderViewBean = new ItemInventoryHeaderViewBean();
			load(dataSetRow, itemInventoryHeaderViewBean);
			itemInventoryHeaderViewBeanColl.add(itemInventoryHeaderViewBean);
		}

		return itemInventoryHeaderViewBeanColl;
	}
}