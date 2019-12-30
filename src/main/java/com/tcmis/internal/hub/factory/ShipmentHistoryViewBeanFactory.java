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
import com.tcmis.internal.hub.beans.ShipmentHistoryViewBean;


/******************************************************************************
 * CLASSNAME: ShipmentHistoryViewBeanFactory <br>
 * @version: 1.0, May 13, 2010 <br>
 *****************************************************************************/


public class ShipmentHistoryViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
	public String ATTRIBUTE_TRACKING_NUMBER = "TRACKING_NUMBER";
	public String ATTRIBUTE_CARRIER_CODE = "CARRIER_CODE";
	public String ATTRIBUTE_ACCOUNT = "ACCOUNT";
	public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
	public String ATTRIBUTE_HAAS_VENDOR = "HAAS_VENDOR";
	public String ATTRIBUTE_CARRIER_OWNER = "CARRIER_OWNER";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
	public String ATTRIBUTE_HUB_NAME = "HUB_NAME";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
	public String ATTRIBUTE_SHIP_CONFIRM_DATE = "SHIP_CONFIRM_DATE";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_CARRIER_METHOD = "CARRIER_METHOD";
	public String ATTRIBUTE_ISSUE_ID = "ISSUE_ID";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_CUSTOMER_ID = "CUSTOMER_ID";
	public String ATTRIBUTE_CUSTOMER_NAME = "CUSTOMER_NAME";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_OPERATING_ENTITY_NAME = "OPERATING_ENTITY_NAME";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1 = "SHIP_TO_ADDRESS_LINE_1";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2 = "SHIP_TO_ADDRESS_LINE_2";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3 = "SHIP_TO_ADDRESS_LINE_3";
	public String ATTRIBUTE_SHIP_TO_CITY = "SHIP_TO_CITY";
	public String ATTRIBUTE_SHIP_TO_STATE = "SHIP_TO_STATE";
	public String ATTRIBUTE_SHIP_TO_ZIP = "SHIP_TO_ZIP";
	public String ATTRIBUTE_SHIP_TO_COUNTRY_ABBREV = "SHIP_TO_COUNTRY_ABBREV";

	//table name
	public String TABLE = "SHIPMENT_HISTORY_VIEW";


	//constructor
	public ShipmentHistoryViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("shipmentId")) {
			return ATTRIBUTE_SHIPMENT_ID;
		}
		else if(attributeName.equals("trackingNumber")) {
			return ATTRIBUTE_TRACKING_NUMBER;
		}
		else if(attributeName.equals("carrierCode")) {
			return ATTRIBUTE_CARRIER_CODE;
		}
		else if(attributeName.equals("account")) {
			return ATTRIBUTE_ACCOUNT;
		}
		else if(attributeName.equals("carrierName")) {
			return ATTRIBUTE_CARRIER_NAME;
		}
		else if(attributeName.equals("haasVendor")) {
			return ATTRIBUTE_HAAS_VENDOR;
		}
		else if(attributeName.equals("carrierOwner")) {
			return ATTRIBUTE_CARRIER_OWNER;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("branchPlant")) {
			return ATTRIBUTE_BRANCH_PLANT;
		}
		else if(attributeName.equals("hubName")) {
			return ATTRIBUTE_HUB_NAME;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("dateDelivered")) {
			return ATTRIBUTE_DATE_DELIVERED;
		}
		else if(attributeName.equals("shipConfirmDate")) {
			return ATTRIBUTE_SHIP_CONFIRM_DATE;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("carrierMethod")) {
			return ATTRIBUTE_CARRIER_METHOD;
		}
		else if(attributeName.equals("issueId")) {
			return ATTRIBUTE_ISSUE_ID;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("customerId")) {
			return ATTRIBUTE_CUSTOMER_ID;
		}
		else if(attributeName.equals("customerName")) {
			return ATTRIBUTE_CUSTOMER_NAME;
		}
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("operatingEntityName")) {
			return ATTRIBUTE_OPERATING_ENTITY_NAME;
		}
		else if(attributeName.equals("shipToAddressLine11")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1;
		}
		else if(attributeName.equals("shipToAddressLine22")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2;
		}
		else if(attributeName.equals("shipToAddressLine33")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3;
		}
		else if(attributeName.equals("shipToCity")) {
			return ATTRIBUTE_SHIP_TO_CITY;
		}
		else if(attributeName.equals("shipToState")) {
			return ATTRIBUTE_SHIP_TO_STATE;
		}
		else if(attributeName.equals("shipToZip")) {
			return ATTRIBUTE_SHIP_TO_ZIP;
		}
		else if(attributeName.equals("shipToCountryAbbrev")) {
			return ATTRIBUTE_SHIP_TO_COUNTRY_ABBREV;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ShipmentHistoryViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ShipmentHistoryViewBean shipmentHistoryViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("shipmentId", "SearchCriterion.EQUALS",
			"" + shipmentHistoryViewBean.getShipmentId());

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


	public int delete(ShipmentHistoryViewBean shipmentHistoryViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("shipmentId", "SearchCriterion.EQUALS",
			"" + shipmentHistoryViewBean.getShipmentId());

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
	public int insert(ShipmentHistoryViewBean shipmentHistoryViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(shipmentHistoryViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ShipmentHistoryViewBean shipmentHistoryViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_SHIPMENT_ID + "," +
			ATTRIBUTE_TRACKING_NUMBER + "," +
			ATTRIBUTE_CARRIER_CODE + "," +
			ATTRIBUTE_ACCOUNT + "," +
			ATTRIBUTE_CARRIER_NAME + "," +
			ATTRIBUTE_HAAS_VENDOR + "," +
			ATTRIBUTE_CARRIER_OWNER + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_BRANCH_PLANT + "," +
			ATTRIBUTE_HUB_NAME + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_DATE_DELIVERED + "," +
			ATTRIBUTE_SHIP_CONFIRM_DATE + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_CARRIER_METHOD + "," +
			ATTRIBUTE_ISSUE_ID + "," +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_CUSTOMER_ID + "," +
			ATTRIBUTE_CUSTOMER_NAME + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "," +
			ATTRIBUTE_OPERATING_ENTITY_NAME + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1 + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2 + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3 + "," +
			ATTRIBUTE_SHIP_TO_CITY + "," +
			ATTRIBUTE_SHIP_TO_STATE + "," +
			ATTRIBUTE_SHIP_TO_ZIP + "," +
			ATTRIBUTE_SHIP_TO_COUNTRY_ABBREV + ")" +
			" values (" +
			shipmentHistoryViewBean.getShipmentId() + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getTrackingNumber()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getCarrierCode()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getAccount()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getCarrierName()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getHaasVendor()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getCarrierOwner()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getBranchPlant()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getHubName()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getShipToLocationId()) + "," +
			DateHandler.getOracleToDateFunction(shipmentHistoryViewBean.getDateDelivered()) + "," +
			DateHandler.getOracleToDateFunction(shipmentHistoryViewBean.getShipConfirmDate()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getFacilityId()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getCarrierMethod()) + "," +
			shipmentHistoryViewBean.getIssueId() + "," +
			shipmentHistoryViewBean.getPrNumber() + "," +
			shipmentHistoryViewBean.getCustomerId() + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getCustomerName()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getOpsEntityId()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getOperatingEntityName()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getShipToAddressLine1()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getShipToAddressLine2()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getShipToAddressLine3()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getShipToCity()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getShipToState()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getShipToZip()) + "," +
			SqlHandler.delimitString(shipmentHistoryViewBean.getShipToCountryAbbrev()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ShipmentHistoryViewBean shipmentHistoryViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(shipmentHistoryViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ShipmentHistoryViewBean shipmentHistoryViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_SHIPMENT_ID + "=" + 
				StringHandler.nullIfZero(shipmentHistoryViewBean.getShipmentId()) + "," +
			ATTRIBUTE_TRACKING_NUMBER + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getTrackingNumber()) + "," +
			ATTRIBUTE_CARRIER_CODE + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getCarrierCode()) + "," +
			ATTRIBUTE_ACCOUNT + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getAccount()) + "," +
			ATTRIBUTE_CARRIER_NAME + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getCarrierName()) + "," +
			ATTRIBUTE_HAAS_VENDOR + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getHaasVendor()) + "," +
			ATTRIBUTE_CARRIER_OWNER + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getCarrierOwner()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_BRANCH_PLANT + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getBranchPlant()) + "," +
			ATTRIBUTE_HUB_NAME + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getHubName()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getCompanyId()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getShipToLocationId()) + "," +
			ATTRIBUTE_DATE_DELIVERED + "=" + 
				DateHandler.getOracleToDateFunction(shipmentHistoryViewBean.getDateDelivered()) + "," +
			ATTRIBUTE_SHIP_CONFIRM_DATE + "=" + 
				DateHandler.getOracleToDateFunction(shipmentHistoryViewBean.getShipConfirmDate()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getFacilityId()) + "," +
			ATTRIBUTE_CARRIER_METHOD + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getCarrierMethod()) + "," +
			ATTRIBUTE_ISSUE_ID + "=" + 
				StringHandler.nullIfZero(shipmentHistoryViewBean.getIssueId()) + "," +
			ATTRIBUTE_PR_NUMBER + "=" + 
				StringHandler.nullIfZero(shipmentHistoryViewBean.getPrNumber()) + "," +
			ATTRIBUTE_CUSTOMER_ID + "=" + 
				StringHandler.nullIfZero(shipmentHistoryViewBean.getCustomerId()) + "," +
			ATTRIBUTE_CUSTOMER_NAME + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getCustomerName()) + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getOpsEntityId()) + "," +
			ATTRIBUTE_OPERATING_ENTITY_NAME + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getOperatingEntityName()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1 + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getShipToAddressLine1()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2 + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getShipToAddressLine2()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3 + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getShipToAddressLine3()) + "," +
			ATTRIBUTE_SHIP_TO_CITY + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getShipToCity()) + "," +
			ATTRIBUTE_SHIP_TO_STATE + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getShipToState()) + "," +
			ATTRIBUTE_SHIP_TO_ZIP + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getShipToZip()) + "," +
			ATTRIBUTE_SHIP_TO_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(shipmentHistoryViewBean.getShipToCountryAbbrev()) + " " +
			"where " + ATTRIBUTE_SHIPMENT_ID + "=" +
				shipmentHistoryViewBean.getShipmentId();

		return new SqlManager().update(conn, query);
	}
*/
  //select
  public Collection select(SearchCriteria criteria) throws BaseException {
	  return select(criteria,(SortCriteria)null);
  }

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

		Collection shipmentHistoryViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ShipmentHistoryViewBean shipmentHistoryViewBean = new ShipmentHistoryViewBean();
			load(dataSetRow, shipmentHistoryViewBean);
			shipmentHistoryViewBeanColl.add(shipmentHistoryViewBean);
		}

		return shipmentHistoryViewBeanColl;
	}
}