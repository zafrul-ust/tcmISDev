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
import com.tcmis.internal.hub.beans.PicklistReprintViewBean;


/******************************************************************************
 * CLASSNAME: PicklistReprintViewBeanFactory <br>
 * @version: 1.0, Feb 13, 2008 <br>
 *****************************************************************************/


public class PicklistReprintViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PICKLIST_ID = "PICKLIST_ID";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_STOCKING_METHOD = "STOCKING_METHOD";
	public String ATTRIBUTE_DELIVERY_TYPE = "DELIVERY_TYPE";
	public String ATTRIBUTE_NEED_DATE_PREFIX = "NEED_DATE_PREFIX";
	public String ATTRIBUTE_NEED_DATE = "NEED_DATE";
	public String ATTRIBUTE_PICK_QTY = "PICK_QTY";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_DELIVERY_POINT = "DELIVERY_POINT";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_REQUESTOR = "REQUESTOR";
	public String ATTRIBUTE_MR_NOTES = "MR_NOTES";
	public String ATTRIBUTE_CRITICAL = "CRITICAL";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_CARRIER_CODE = "CARRIER_CODE";
	public String ATTRIBUTE_PICKUP_TIME = "PICKUP_TIME";
	public String ATTRIBUTE_STOP_NUMBER = "STOP_NUMBER";
	public String ATTRIBUTE_TRAILER_NUMBER = "TRAILER_NUMBER";
	public String ATTRIBUTE_PACKING_GROUP_ID = "PACKING_GROUP_ID";
	public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
	public String ATTRIBUTE_TRANSPORTATION_MODE = "TRANSPORTATION_MODE";
	public String ATTRIBUTE_REQUIRES_OVERPACK = "REQUIRES_OVERPACK";
	public String ATTRIBUTE_SHIPPED_AS_SINGLE = "SHIPPED_AS_SINGLE";
	public String ATTRIBUTE_SHIP_TO_LOCATION_DESC = "SHIP_TO_LOCATION_DESC";
	public String ATTRIBUTE_SHIP_TO_CITY = "SHIP_TO_CITY";
	public String ATTRIBUTE_SHIP_TO_STATE_ABBREV = "SHIP_TO_STATE_ABBREV";
	public String ATTRIBUTE_SHIP_TO_ZIP = "SHIP_TO_ZIP";
	public String ATTRIBUTE_SHIP_TO_DODAAC = "SHIP_TO_DODAAC";
	public String ATTRIBUTE_PACKAGED_AS = "PACKAGED_AS";
	public String ATTRIBUTE_MAX_UNIT_OF_ISSUE_PER_BOX = "MAX_UNIT_OF_ISSUE_PER_BOX";
	public String ATTRIBUTE_MAX_UNIT_OF_ISSUE_PER_PALLET = "MAX_UNIT_OF_ISSUE_PER_PALLET";
	public String ATTRIBUTE_CONSOLIDATION_NUMBER = "CONSOLIDATION_NUMBER";
	public String ATTRIBUTE_OCONUS_FLAG = "OCONUS_FLAG";
	public String ATTRIBUTE_BIN = "BIN";
	public String ATTRIBUTE_MR_COUNT = "MR_COUNT";
	public String ATTRIBUTE_TRANSPORTATION_PRIORITY = "TRANSPORTATION_PRIORITY";
	public String ATTRIBUTE_HAZARDOUS = "HAZARDOUS";
	public String ATTRIBUTE_SPLIT_TCN = "SPLIT_TCN";
	public String ATTRIBUTE_AIR_GROUND_INDICATOR = "AIR_GROUND_INDICATOR";
	public String ATTRIBUTE_CMS = "CMS";
	public String ATTRIBUTE_DISTRIBUTION = "DISTRIBUTION";
	public String ATTRIBUTE_SHIPPING_REFERENCE = "SHIPPING_REFERENCE";	
	public String ATTRIBUTE_TRACK_SERIAL_NUMBER = "TRACK_SERIAL_NUMBER";
	public String ATTRIBUTE_ISSUE_ID = "ISSUE_ID";

	//table name
	public String TABLE = "PICKLIST_REPRINT_VIEW";


	//constructor
	public PicklistReprintViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("picklistId")) {
			return ATTRIBUTE_PICKLIST_ID;
		}
		else if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("stockingMethod")) {
			return ATTRIBUTE_STOCKING_METHOD;
		}
		else if(attributeName.equals("deliveryType")) {
			return ATTRIBUTE_DELIVERY_TYPE;
		}
		else if(attributeName.equals("needDatePrefix")) {
			return ATTRIBUTE_NEED_DATE_PREFIX;
		}
		else if(attributeName.equals("needDate")) {
			return ATTRIBUTE_NEED_DATE;
		}
		else if(attributeName.equals("pickQty")) {
			return ATTRIBUTE_PICK_QTY;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("partDescription")) {
			return ATTRIBUTE_PART_DESCRIPTION;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("partGroupNo")) {
			return ATTRIBUTE_PART_GROUP_NO;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("deliveryPoint")) {
			return ATTRIBUTE_DELIVERY_POINT;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("requestor")) {
			return ATTRIBUTE_REQUESTOR;
		}
		else if(attributeName.equals("mrNotes")) {
			return ATTRIBUTE_MR_NOTES;
		}
		else if(attributeName.equals("critical")) {
			return ATTRIBUTE_CRITICAL;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("carrierCode")) {
			return ATTRIBUTE_CARRIER_CODE;
		}
		else if(attributeName.equals("pickupTime")) {
			return ATTRIBUTE_PICKUP_TIME;
		}
		else if(attributeName.equals("stopNumber")) {
			return ATTRIBUTE_STOP_NUMBER;
		}
		else if(attributeName.equals("trailerNumber")) {
			return ATTRIBUTE_TRAILER_NUMBER;
		}
		else if(attributeName.equals("packingGroupId")) {
			return ATTRIBUTE_PACKING_GROUP_ID;
		}
		else if(attributeName.equals("carrierName")) {
			return ATTRIBUTE_CARRIER_NAME;
		}
		else if(attributeName.equals("transportationMode")) {
			return ATTRIBUTE_TRANSPORTATION_MODE;
		}
		else if(attributeName.equals("requiresOverpack")) {
			return ATTRIBUTE_REQUIRES_OVERPACK;
		}
		else if(attributeName.equals("shippedAsSingle")) {
			return ATTRIBUTE_SHIPPED_AS_SINGLE;
		}
		else if(attributeName.equals("shipToLocationDesc")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_DESC;
		}
		else if(attributeName.equals("shipToCity")) {
			return ATTRIBUTE_SHIP_TO_CITY;
		}
		else if(attributeName.equals("shipToStateAbbrev")) {
			return ATTRIBUTE_SHIP_TO_STATE_ABBREV;
		}
		else if(attributeName.equals("shipToZip")) {
			return ATTRIBUTE_SHIP_TO_ZIP;
		}
		else if(attributeName.equals("shipToDodaac")) {
			return ATTRIBUTE_SHIP_TO_DODAAC;
		}
		else if(attributeName.equals("packagedAs")) {
			return ATTRIBUTE_PACKAGED_AS;
		}
		else if(attributeName.equals("maxUnitOfIssuePerBox")) {
			return ATTRIBUTE_MAX_UNIT_OF_ISSUE_PER_BOX;
		}
		else if(attributeName.equals("maxUnitOfIssuePerPallet")) {
			return ATTRIBUTE_MAX_UNIT_OF_ISSUE_PER_PALLET;
		}
		else if(attributeName.equals("consolidationNumber")) {
			return ATTRIBUTE_CONSOLIDATION_NUMBER;
		}
		else if(attributeName.equals("oconusFlag")) {
			return ATTRIBUTE_OCONUS_FLAG;
		}   
		else if(attributeName.equals("bin")) {
			return ATTRIBUTE_BIN;
		}
		else if(attributeName.equals("mrCount")) {
			return ATTRIBUTE_MR_COUNT;
		}
		else if(attributeName.equals("transportationPriority")) {
			  return ATTRIBUTE_TRANSPORTATION_PRIORITY;
		  }
   	    else if(attributeName.equals("hazardous")) {
			  return ATTRIBUTE_HAZARDOUS;
		  }
   	    else if(attributeName.equals("splitTcn")) {
			return ATTRIBUTE_SPLIT_TCN;
		}
   	    else if(attributeName.equals("airGroundIndicator")) {
			return ATTRIBUTE_AIR_GROUND_INDICATOR;
		}  
	   	else if(attributeName.equals("cms")) {
			return ATTRIBUTE_CMS;
		}  
	   	else if(attributeName.equals("distribution")) {
			return ATTRIBUTE_DISTRIBUTION;
		}  
	   	else if(attributeName.equals("shippingReference")) {
			return ATTRIBUTE_SHIPPING_REFERENCE;
		}  
	   	else if(attributeName.equals("trackSerialNumber")) {
			return ATTRIBUTE_TRACK_SERIAL_NUMBER;
		} 
	   	else if(attributeName.equals("issueId")) {
	   		return ATTRIBUTE_ISSUE_ID;
	   	}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PicklistReprintViewBean.class);
	}


//	you need to verify the primary key(s) before uncommenting this
	/*
	//delete
	public int delete(PicklistReprintViewBean picklistReprintViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("picklistId", "SearchCriterion.EQUALS",
			"" + picklistReprintViewBean.getPicklistId());

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


	public int delete(PicklistReprintViewBean picklistReprintViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("picklistId", "SearchCriterion.EQUALS",
			"" + picklistReprintViewBean.getPicklistId());

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


//	you need to verify the primary key(s) before uncommenting this
	/*
	//insert
	public int insert(PicklistReprintViewBean picklistReprintViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(picklistReprintViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PicklistReprintViewBean picklistReprintViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_PICKLIST_ID + "," +
			ATTRIBUTE_HUB + "," +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_STOCKING_METHOD + "," +
			ATTRIBUTE_DELIVERY_TYPE + "," +
			ATTRIBUTE_NEED_DATE_PREFIX + "," +
			ATTRIBUTE_NEED_DATE + "," +
			ATTRIBUTE_PICK_QTY + "," +
			ATTRIBUTE_APPLICATION + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_PART_DESCRIPTION + "," +
			ATTRIBUTE_PACKAGING + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_PART_GROUP_NO + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_DELIVERY_POINT + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_REQUESTOR + "," +
			ATTRIBUTE_MR_NOTES + "," +
			ATTRIBUTE_CRITICAL + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_CARRIER_CODE + "," +
			ATTRIBUTE_PICKUP_TIME + "," +
			ATTRIBUTE_STOP_NUMBER + "," +
			ATTRIBUTE_TRAILER_NUMBER + "," +
			ATTRIBUTE_PACKING_GROUP_ID + "," +
			ATTRIBUTE_CARRIER_NAME + "," +
			ATTRIBUTE_TRANSPORTATION_MODE + "," +
			ATTRIBUTE_REQUIRES_OVERPACK + "," +
			ATTRIBUTE_SHIPPED_AS_SINGLE + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_DESC + "," +
			ATTRIBUTE_SHIP_TO_CITY + "," +
			ATTRIBUTE_SHIP_TO_STATE_ABBREV + "," +
			ATTRIBUTE_SHIP_TO_ZIP + ")" +
			" values (" +
			picklistReprintViewBean.getPicklistId() + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getHub()) + "," +
			picklistReprintViewBean.getPrNumber() + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getLineItem()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getStockingMethod()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getDeliveryType()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getNeedDatePrefix()) + "," +
			DateHandler.getOracleToDateFunction(picklistReprintViewBean.getNeedDate()) + "," +
			picklistReprintViewBean.getPickQty() + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getApplication()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getFacilityId()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getPartDescription()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getPackaging()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getCatalogId()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getCatPartNo()) + "," +
			picklistReprintViewBean.getPartGroupNo() + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getDeliveryPoint()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getShipToLocationId()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getRequestor()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getMrNotes()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getCritical()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getCarrierCode()) + "," +
			DateHandler.getOracleToDateFunction(picklistReprintViewBean.getPickupTime()) + "," +
			picklistReprintViewBean.getStopNumber() + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getTrailerNumber()) + "," +
			picklistReprintViewBean.getPackingGroupId() + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getCarrierName()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getTransportationMode()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getRequiresOverpack()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getShippedAsSingle()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getShipToLocationDesc()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getShipToCity()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getShipToStateAbbrev()) + "," +
			SqlHandler.delimitString(picklistReprintViewBean.getShipToZip()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PicklistReprintViewBean picklistReprintViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(picklistReprintViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PicklistReprintViewBean picklistReprintViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PICKLIST_ID + "=" + 
				StringHandler.nullIfZero(picklistReprintViewBean.getPicklistId()) + "," +
			ATTRIBUTE_HUB + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getHub()) + "," +
			ATTRIBUTE_PR_NUMBER + "=" + 
				StringHandler.nullIfZero(picklistReprintViewBean.getPrNumber()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getLineItem()) + "," +
			ATTRIBUTE_STOCKING_METHOD + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getStockingMethod()) + "," +
			ATTRIBUTE_DELIVERY_TYPE + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getDeliveryType()) + "," +
			ATTRIBUTE_NEED_DATE_PREFIX + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getNeedDatePrefix()) + "," +
			ATTRIBUTE_NEED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(picklistReprintViewBean.getNeedDate()) + "," +
			ATTRIBUTE_PICK_QTY + "=" + 
				StringHandler.nullIfZero(picklistReprintViewBean.getPickQty()) + "," +
			ATTRIBUTE_APPLICATION + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getApplication()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getFacilityId()) + "," +
			ATTRIBUTE_PART_DESCRIPTION + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getPartDescription()) + "," +
			ATTRIBUTE_PACKAGING + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getPackaging()) + "," +
			ATTRIBUTE_CATALOG_ID + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getCatalogId()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getCatPartNo()) + "," +
			ATTRIBUTE_PART_GROUP_NO + "=" + 
				StringHandler.nullIfZero(picklistReprintViewBean.getPartGroupNo()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getCompanyId()) + "," +
			ATTRIBUTE_DELIVERY_POINT + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getDeliveryPoint()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getShipToLocationId()) + "," +
			ATTRIBUTE_REQUESTOR + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getRequestor()) + "," +
			ATTRIBUTE_MR_NOTES + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getMrNotes()) + "," +
			ATTRIBUTE_CRITICAL + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getCritical()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_CARRIER_CODE + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getCarrierCode()) + "," +
			ATTRIBUTE_PICKUP_TIME + "=" + 
				DateHandler.getOracleToDateFunction(picklistReprintViewBean.getPickupTime()) + "," +
			ATTRIBUTE_STOP_NUMBER + "=" + 
				StringHandler.nullIfZero(picklistReprintViewBean.getStopNumber()) + "," +
			ATTRIBUTE_TRAILER_NUMBER + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getTrailerNumber()) + "," +
			ATTRIBUTE_PACKING_GROUP_ID + "=" + 
				StringHandler.nullIfZero(picklistReprintViewBean.getPackingGroupId()) + "," +
			ATTRIBUTE_CARRIER_NAME + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getCarrierName()) + "," +
			ATTRIBUTE_TRANSPORTATION_MODE + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getTransportationMode()) + "," +
			ATTRIBUTE_REQUIRES_OVERPACK + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getRequiresOverpack()) + "," +
			ATTRIBUTE_SHIPPED_AS_SINGLE + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getShippedAsSingle()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_DESC + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getShipToLocationDesc()) + "," +
			ATTRIBUTE_SHIP_TO_CITY + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getShipToCity()) + "," +
			ATTRIBUTE_SHIP_TO_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getShipToStateAbbrev()) + "," +
			ATTRIBUTE_SHIP_TO_ZIP + "=" + 
				SqlHandler.delimitString(picklistReprintViewBean.getShipToZip()) + " " +
			"where " + ATTRIBUTE_PICKLIST_ID + "=" +
				picklistReprintViewBean.getPicklistId();

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

		Collection picklistReprintViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PicklistReprintViewBean picklistReprintViewBean = new PicklistReprintViewBean();
			load(dataSetRow, picklistReprintViewBean);
			picklistReprintViewBeanColl.add(picklistReprintViewBean);
		}

		return picklistReprintViewBeanColl;
	}
}