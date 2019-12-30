package com.tcmis.internal.print.factory;


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
import com.tcmis.internal.print.beans.UsgovLabelViewBean;
import com.tcmis.internal.hub.beans.LabelInputBean;


/******************************************************************************
 * CLASSNAME: UsgovLabelViewBeanFactory <br>
 * @version: 1.0, Feb 12, 2008 <br>
 *****************************************************************************/


public class UsgovLabelViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_BOX_LABEL_ID = "BOX_LABEL_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_SHIP_FROM_LOCATION_ID = "SHIP_FROM_LOCATION_ID";
	public String ATTRIBUTE_SHIP_FROM_LOCATION_NAME = "SHIP_FROM_LOCATION_NAME";
	public String ATTRIBUTE_SHIP_TO_LOCATION_NAME = "SHIP_TO_LOCATION_NAME";
	public String ATTRIBUTE_SHIP_TO_CITY_COMMA_STATE = "SHIP_TO_CITY_COMMA_STATE";
	public String ATTRIBUTE_SHIP_TO_ZIPCODE = "SHIP_TO_ZIPCODE";
	public String ATTRIBUTE_PICKLIST_ID = "PICKLIST_ID";
	public String ATTRIBUTE_PICKLIST_PRINT_DATE = "PICKLIST_PRINT_DATE";
	public String ATTRIBUTE_ULTIMATE_SHIP_TO_DODAAC = "ULTIMATE_SHIP_TO_DODAAC";
	public String ATTRIBUTE_PACKING_GROUP_ID = "PACKING_GROUP_ID";
	public String ATTRIBUTE_ISSUE_ID = "ISSUE_ID";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_NSN = "NSN";
	public String ATTRIBUTE_CUSTOMER_PO = "CUSTOMER_PO";
	public String ATTRIBUTE_CAGE_CODE = "CAGE_CODE";
	public String ATTRIBUTE_FORMATTED_NSN = "FORMATTED_NSN";
	public String ATTRIBUTE_PID_PART_NO = "PID_PART_NO";
	public String ATTRIBUTE_FORMATTED_CUSTOMER_PO = "FORMATTED_CUSTOMER_PO";
	public String ATTRIBUTE_PART_SHORT_NAME = "PART_SHORT_NAME";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_QUANTITY_IN_UNIT_OF_SALE = "QUANTITY_IN_UNIT_OF_SALE";
	public String ATTRIBUTE_GROSS_WEIGHT = "GROSS_WEIGHT";
	public String ATTRIBUTE_DATE_OF_MANUFACTURE = "DATE_OF_MANUFACTURE";
	public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
	public String ATTRIBUTE_BOX_ID = "BOX_ID";
	public String ATTRIBUTE_PALLET_ID = "PALLET_ID";
	public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
	public String ATTRIBUTE_TRACKING_NUMBER = "TRACKING_NUMBER";
	public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
	public String ATTRIBUTE_DELIVERY_TICKET = "DELIVERY_TICKET";
	public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
	public String ATTRIBUTE_DATE_SHIPPED = "DATE_SHIPPED";
	public String ATTRIBUTE_UN_NUMBER_AND_PROPER_SHIP_NAME = "UN_NUMBER_AND_PROPER_SHIP_NAME";
	public String ATTRIBUTE_PRIORITY_RATING = "PRIORITY_RATING";
	public String ATTRIBUTE_DESIRED_SHIP_DATE = "DESIRED_SHIP_DATE";
	public String ATTRIBUTE_DESIRED_DELIVERY_DATE = "DESIRED_DELIVERY_DATE";
	public String ATTRIBUTE_SHIPPED_AS_SINGLE = "SHIPPED_AS_SINGLE";
	public String ATTRIBUTE_REQUIRES_OVERPACK = "REQUIRES_OVERPACK";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_QUANTITY_IN_UNIT_OF_ISSUE = "QUANTITY_IN_UNIT_OF_ISSUE";
	public String ATTRIBUTE_UNIT_OF_ISSUE = "UNIT_OF_ISSUE";
	public String ATTRIBUTE_PROJECT_CODE = "PROJECT_CODE";
	public String ATTRIBUTE_FLASHPOINT_INFO = "FLASHPOINT_INFO";
	public String ATTRIBUTE_REQUIRED_DELIVERY_DATE = "REQUIRED_DELIVERY_DATE";
	public String ATTRIBUTE_UNIT_LABEL_PRINTED = "UNIT_LABEL_PRINTED";
	public String ATTRIBUTE_OWNER_COMPANY_ID = "OWNER_COMPANY_ID";
	public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";
	public String ATTRIBUTE_BOX_GROSS_WEIGHT = "BOX_GROSS_WEIGHT";
	public String ATTRIBUTE_TRACK_SERIAL_NUMBER = "TRACK_SERIAL_NUMBER";
	public String ATTRIBUTE_SERIAL_NUMBER = "SERIAL_NUMBER";

  //table name
	public String TABLE = "USGOV_LABEL_VIEW";


	//constructor
	public UsgovLabelViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("boxLabelId")) {
			return ATTRIBUTE_BOX_LABEL_ID;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("shipFromLocationId")) {
			return ATTRIBUTE_SHIP_FROM_LOCATION_ID;
		}
		else if(attributeName.equals("shipFromLocationName")) {
			return ATTRIBUTE_SHIP_FROM_LOCATION_NAME;
		}
		else if(attributeName.equals("shipToLocationName")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_NAME;
		}
		else if(attributeName.equals("shipToCityCommaState")) {
			return ATTRIBUTE_SHIP_TO_CITY_COMMA_STATE;
		}
		else if(attributeName.equals("shipToZipcode")) {
			return ATTRIBUTE_SHIP_TO_ZIPCODE;
		}
		else if(attributeName.equals("picklistId")) {
			return ATTRIBUTE_PICKLIST_ID;
		}
		else if(attributeName.equals("picklistPrintDate")) {
			return ATTRIBUTE_PICKLIST_PRINT_DATE;
		}
		else if(attributeName.equals("ultimateShipToDodaac")) {
			return ATTRIBUTE_ULTIMATE_SHIP_TO_DODAAC;
		}
		else if(attributeName.equals("packingGroupId")) {
			return ATTRIBUTE_PACKING_GROUP_ID;
		}
		else if(attributeName.equals("issueId")) {
			return ATTRIBUTE_ISSUE_ID;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("nsn")) {
			return ATTRIBUTE_NSN;
		}
		else if(attributeName.equals("customerPo")) {
			return ATTRIBUTE_CUSTOMER_PO;
		}
		else if(attributeName.equals("cageCode")) {
			return ATTRIBUTE_CAGE_CODE;
		}
		else if(attributeName.equals("formattedNsn")) {
			return ATTRIBUTE_FORMATTED_NSN;
		}
		else if(attributeName.equals("pidPartNo")) {
			return ATTRIBUTE_PID_PART_NO;
		}
		else if(attributeName.equals("formattedCustomerPo")) {
			return ATTRIBUTE_FORMATTED_CUSTOMER_PO;
		}
		else if(attributeName.equals("partShortName")) {
			return ATTRIBUTE_PART_SHORT_NAME;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("quantityInUnitOfSale")) {
			return ATTRIBUTE_QUANTITY_IN_UNIT_OF_SALE;
		}
		else if(attributeName.equals("grossWeight")) {
			return ATTRIBUTE_GROSS_WEIGHT;
		}
		else if(attributeName.equals("dateOfManufacture")) {
			return ATTRIBUTE_DATE_OF_MANUFACTURE;
		}
		else if(attributeName.equals("expireDate")) {
			return ATTRIBUTE_EXPIRE_DATE;
		}
		else if(attributeName.equals("boxId")) {
			return ATTRIBUTE_BOX_ID;
		}
		else if(attributeName.equals("palletId")) {
			return ATTRIBUTE_PALLET_ID;
		}
		else if(attributeName.equals("shipmentId")) {
			return ATTRIBUTE_SHIPMENT_ID;
		}
		else if(attributeName.equals("trackingNumber")) {
			return ATTRIBUTE_TRACKING_NUMBER;
		}
		else if(attributeName.equals("carrierName")) {
			return ATTRIBUTE_CARRIER_NAME;
		}
		else if(attributeName.equals("deliveryTicket")) {
			return ATTRIBUTE_DELIVERY_TICKET;
		}
		else if(attributeName.equals("dateDelivered")) {
			return ATTRIBUTE_DATE_DELIVERED;
		}
		else if(attributeName.equals("dateShipped")) {
			return ATTRIBUTE_DATE_SHIPPED;
		}
		else if(attributeName.equals("unNumberAndProperShipName")) {
			return ATTRIBUTE_UN_NUMBER_AND_PROPER_SHIP_NAME;
		}
		else if(attributeName.equals("priorityRating")) {
			return ATTRIBUTE_PRIORITY_RATING;
		}
		else if(attributeName.equals("desiredShipDate")) {
			return ATTRIBUTE_DESIRED_SHIP_DATE;
		}
		else if(attributeName.equals("desiredDeliveryDate")) {
			return ATTRIBUTE_DESIRED_DELIVERY_DATE;
		}
		else if(attributeName.equals("shippedAsSingle")) {
			return ATTRIBUTE_SHIPPED_AS_SINGLE;
		}
		else if(attributeName.equals("requiresOverpack")) {
			return ATTRIBUTE_REQUIRES_OVERPACK;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("quantityInUnitOfIssue")) {
			return ATTRIBUTE_QUANTITY_IN_UNIT_OF_ISSUE;
		}
		else if(attributeName.equals("unitOfIssue")) {
			return ATTRIBUTE_UNIT_OF_ISSUE;
		}
		else if(attributeName.equals("projectCode")) {
			return ATTRIBUTE_PROJECT_CODE;
		}
		else if(attributeName.equals("flashpointInfo")) {
			return ATTRIBUTE_FLASHPOINT_INFO;
		}
		else if(attributeName.equals("requiredDeliveryDate")) {
			return ATTRIBUTE_REQUIRED_DELIVERY_DATE;
		}
		else if(attributeName.equals("unitLabelPrinted")) {
			return ATTRIBUTE_UNIT_LABEL_PRINTED;
		}
		else if(attributeName.equals("ownerCompanyId")) {
			return ATTRIBUTE_OWNER_COMPANY_ID;
		}
		else if(attributeName.equals("unitOfSale")) {
			return ATTRIBUTE_UNIT_OF_SALE;
		}
		else if(attributeName.equals("boxGrossWeight")) {
			return ATTRIBUTE_BOX_GROSS_WEIGHT;
		}  
		else if(attributeName.equals("trackSerialNumber")) {
			return ATTRIBUTE_TRACK_SERIAL_NUMBER;
		}
		else if(attributeName.equals("serialNumber")) {
			return ATTRIBUTE_SERIAL_NUMBER;
		}
    else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, UsgovLabelViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(UsgovLabelViewBean usgovLabelViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("boxLabelId", "SearchCriterion.EQUALS",
			"" + usgovLabelViewBean.getBoxLabelId());

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


	public int delete(UsgovLabelViewBean usgovLabelViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("boxLabelId", "SearchCriterion.EQUALS",
			"" + usgovLabelViewBean.getBoxLabelId());

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
	public int insert(UsgovLabelViewBean usgovLabelViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(usgovLabelViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(UsgovLabelViewBean usgovLabelViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_BOX_LABEL_ID + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_NAME + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_NAME + "," +
			ATTRIBUTE_SHIP_TO_CITY_COMMA_STATE + "," +
			ATTRIBUTE_SHIP_TO_ZIPCODE + "," +
			ATTRIBUTE_PICKLIST_ID + "," +
			ATTRIBUTE_PICKLIST_PRINT_DATE + "," +
			ATTRIBUTE_ULTIMATE_SHIP_TO_DODAAC + "," +
			ATTRIBUTE_PACKING_GROUP_ID + "," +
			ATTRIBUTE_ISSUE_ID + "," +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_RECEIPT_ID + "," +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_PO_LINE + "," +
			ATTRIBUTE_NSN + "," +
			ATTRIBUTE_CUSTOMER_PO + "," +
			ATTRIBUTE_CAGE_CODE + "," +
			ATTRIBUTE_FORMATTED_NSN + "," +
			ATTRIBUTE_PID_PART_NO + "," +
			ATTRIBUTE_FORMATTED_CUSTOMER_PO + "," +
			ATTRIBUTE_PART_SHORT_NAME + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_ITEM_DESC + "," +
			ATTRIBUTE_PACKAGING + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_QUANTITY_IN_UNIT_OF_SALE + "," +
			ATTRIBUTE_GROSS_WEIGHT + "," +
			ATTRIBUTE_DATE_OF_MANUFACTURE + "," +
			ATTRIBUTE_EXPIRE_DATE + "," +
			ATTRIBUTE_BOX_ID + "," +
			ATTRIBUTE_PALLET_ID + "," +
			ATTRIBUTE_SHIPMENT_ID + "," +
			ATTRIBUTE_TRACKING_NUMBER + "," +
			ATTRIBUTE_CARRIER_NAME + "," +
			ATTRIBUTE_DELIVERY_TICKET + "," +
			ATTRIBUTE_DATE_DELIVERED + "," +
			ATTRIBUTE_DATE_SHIPPED + "," +
			ATTRIBUTE_UN_NUMBER_AND_PROPER_SHIP_NAME + "," +
			ATTRIBUTE_PRIORITY_RATING + "," +
			ATTRIBUTE_DESIRED_SHIP_DATE + "," +
			ATTRIBUTE_DESIRED_DELIVERY_DATE + "," +
			ATTRIBUTE_SHIPPED_AS_SINGLE + "," +
			ATTRIBUTE_REQUIRES_OVERPACK + "," +
			ATTRIBUTE_INVENTORY_GROUP + ")" +
			" values (" +
			SqlHandler.delimitString(usgovLabelViewBean.getBoxLabelId()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getShipFromLocationId()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getShipFromLocationName()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getShipToLocationName()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getShipToCityCommaState()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getShipToZipcode()) + "," +
			usgovLabelViewBean.getPicklistId() + "," +
			DateHandler.getOracleToDateFunction(usgovLabelViewBean.getPicklistPrintDate()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getUltimateShipToDodaac()) + "," +
			usgovLabelViewBean.getPackingGroupId() + "," +
			usgovLabelViewBean.getIssueId() + "," +
			usgovLabelViewBean.getPrNumber() + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getLineItem()) + "," +
			usgovLabelViewBean.getReceiptId() + "," +
			usgovLabelViewBean.getRadianPo() + "," +
			usgovLabelViewBean.getPoLine() + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getNsn()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getCustomerPo()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getCageCode()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getFormattedNsn()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getPidPartNo()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getFormattedCustomerPo()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getPartShortName()) + "," +
			usgovLabelViewBean.getItemId() + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getItemDesc()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getPackaging()) + "," +
			usgovLabelViewBean.getQuantity() + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getQuantityInUnitOfSale()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getGrossWeight()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getDateOfManufacture()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getExpireDate()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getBoxId()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getPalletId()) + "," +
			usgovLabelViewBean.getShipmentId() + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getTrackingNumber()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getCarrierName()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getDeliveryTicket()) + "," +
			DateHandler.getOracleToDateFunction(usgovLabelViewBean.getDateDelivered()) + "," +
			DateHandler.getOracleToDateFunction(usgovLabelViewBean.getDateShipped()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getUnNumberAndProperShipName()) + "," +
			usgovLabelViewBean.getPriorityRating() + "," +
			DateHandler.getOracleToDateFunction(usgovLabelViewBean.getDesiredShipDate()) + "," +
			DateHandler.getOracleToDateFunction(usgovLabelViewBean.getDesiredDeliveryDate()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getShippedAsSingle()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getRequiresOverpack()) + "," +
			SqlHandler.delimitString(usgovLabelViewBean.getInventoryGroup()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(UsgovLabelViewBean usgovLabelViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(usgovLabelViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(UsgovLabelViewBean usgovLabelViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_BOX_LABEL_ID + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getBoxLabelId()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getCompanyId()) + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getShipFromLocationId()) + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_NAME + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getShipFromLocationName()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_NAME + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getShipToLocationName()) + "," +
			ATTRIBUTE_SHIP_TO_CITY_COMMA_STATE + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getShipToCityCommaState()) + "," +
			ATTRIBUTE_SHIP_TO_ZIPCODE + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getShipToZipcode()) + "," +
			ATTRIBUTE_PICKLIST_ID + "=" + 
				StringHandler.nullIfZero(usgovLabelViewBean.getPicklistId()) + "," +
			ATTRIBUTE_PICKLIST_PRINT_DATE + "=" + 
				DateHandler.getOracleToDateFunction(usgovLabelViewBean.getPicklistPrintDate()) + "," +
			ATTRIBUTE_ULTIMATE_SHIP_TO_DODAAC + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getUltimateShipToDodaac()) + "," +
			ATTRIBUTE_PACKING_GROUP_ID + "=" + 
				StringHandler.nullIfZero(usgovLabelViewBean.getPackingGroupId()) + "," +
			ATTRIBUTE_ISSUE_ID + "=" + 
				StringHandler.nullIfZero(usgovLabelViewBean.getIssueId()) + "," +
			ATTRIBUTE_PR_NUMBER + "=" + 
				StringHandler.nullIfZero(usgovLabelViewBean.getPrNumber()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getLineItem()) + "," +
			ATTRIBUTE_RECEIPT_ID + "=" + 
				StringHandler.nullIfZero(usgovLabelViewBean.getReceiptId()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(usgovLabelViewBean.getRadianPo()) + "," +
			ATTRIBUTE_PO_LINE + "=" + 
				StringHandler.nullIfZero(usgovLabelViewBean.getPoLine()) + "," +
			ATTRIBUTE_NSN + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getNsn()) + "," +
			ATTRIBUTE_CUSTOMER_PO + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getCustomerPo()) + "," +
			ATTRIBUTE_CAGE_CODE + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getCageCode()) + "," +
			ATTRIBUTE_FORMATTED_NSN + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getFormattedNsn()) + "," +
			ATTRIBUTE_PID_PART_NO + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getPidPartNo()) + "," +
			ATTRIBUTE_FORMATTED_CUSTOMER_PO + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getFormattedCustomerPo()) + "," +
			ATTRIBUTE_PART_SHORT_NAME + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getPartShortName()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(usgovLabelViewBean.getItemId()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getItemDesc()) + "," +
			ATTRIBUTE_PACKAGING + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getPackaging()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(usgovLabelViewBean.getQuantity()) + "," +
			ATTRIBUTE_QUANTITY_IN_UNIT_OF_SALE + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getQuantityInUnitOfSale()) + "," +
			ATTRIBUTE_GROSS_WEIGHT + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getGrossWeight()) + "," +
			ATTRIBUTE_DATE_OF_MANUFACTURE + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getDateOfManufacture()) + "," +
			ATTRIBUTE_EXPIRE_DATE + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getExpireDate()) + "," +
			ATTRIBUTE_BOX_ID + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getBoxId()) + "," +
			ATTRIBUTE_PALLET_ID + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getPalletId()) + "," +
			ATTRIBUTE_SHIPMENT_ID + "=" + 
				StringHandler.nullIfZero(usgovLabelViewBean.getShipmentId()) + "," +
			ATTRIBUTE_TRACKING_NUMBER + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getTrackingNumber()) + "," +
			ATTRIBUTE_CARRIER_NAME + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getCarrierName()) + "," +
			ATTRIBUTE_DELIVERY_TICKET + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getDeliveryTicket()) + "," +
			ATTRIBUTE_DATE_DELIVERED + "=" + 
				DateHandler.getOracleToDateFunction(usgovLabelViewBean.getDateDelivered()) + "," +
			ATTRIBUTE_DATE_SHIPPED + "=" + 
				DateHandler.getOracleToDateFunction(usgovLabelViewBean.getDateShipped()) + "," +
			ATTRIBUTE_UN_NUMBER_AND_PROPER_SHIP_NAME + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getUnNumberAndProperShipName()) + "," +
			ATTRIBUTE_PRIORITY_RATING + "=" + 
				StringHandler.nullIfZero(usgovLabelViewBean.getPriorityRating()) + "," +
			ATTRIBUTE_DESIRED_SHIP_DATE + "=" + 
				DateHandler.getOracleToDateFunction(usgovLabelViewBean.getDesiredShipDate()) + "," +
			ATTRIBUTE_DESIRED_DELIVERY_DATE + "=" + 
				DateHandler.getOracleToDateFunction(usgovLabelViewBean.getDesiredDeliveryDate()) + "," +
			ATTRIBUTE_SHIPPED_AS_SINGLE + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getShippedAsSingle()) + "," +
			ATTRIBUTE_REQUIRES_OVERPACK + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getRequiresOverpack()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(usgovLabelViewBean.getInventoryGroup()) + " " +
			"where " + ATTRIBUTE_BOX_LABEL_ID + "=" +
				usgovLabelViewBean.getBoxLabelId();

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

		Collection usgovLabelViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			UsgovLabelViewBean usgovLabelViewBean = new UsgovLabelViewBean();
			load(dataSetRow, usgovLabelViewBean);
			usgovLabelViewBeanColl.add(usgovLabelViewBean);
		}

		return usgovLabelViewBeanColl;
	}

  //select for pallet Ext label
	public Collection selectPalletExt(SearchCriteria criteria, SortCriteria sortCriteria,LabelInputBean labelInputBean)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectPalletExt(criteria, sortCriteria, labelInputBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection selectPalletExt(SearchCriteria criteria, SortCriteria sortCriteria, LabelInputBean labelInputBean,Connection conn)
		throws BaseException {
		Collection usgovLabelViewBeanColl = new Vector();
		
		boolean isDlaGases = false;
		if(labelInputBean.getFacilityId() !=null && labelInputBean.getFacilityId().equalsIgnoreCase("DLA Gases"))
			isDlaGases = true;
		
		if (isDlaGases) {
			TABLE = "USGOV_HAAS_GASES_LABEL_VIEW";
		}
		else
		{
			TABLE = "USGOV_LABEL_VIEW";
		}
		
		String query = "select "+ATTRIBUTE_INVENTORY_GROUP+","+ATTRIBUTE_PACKING_GROUP_ID+","+ATTRIBUTE_ISSUE_ID+","+ATTRIBUTE_PR_NUMBER+",";
			   query += ATTRIBUTE_LINE_ITEM+","+ATTRIBUTE_RECEIPT_ID+","+ATTRIBUTE_NSN+","+ATTRIBUTE_CUSTOMER_PO+",";
			   query += ATTRIBUTE_CAGE_CODE+","+ATTRIBUTE_FORMATTED_NSN+","+ATTRIBUTE_PID_PART_NO+","+ATTRIBUTE_FORMATTED_CUSTOMER_PO+",";
			   query += "sum("+ATTRIBUTE_QUANTITY+") ||' '|| "+ATTRIBUTE_UNIT_OF_SALE+" "+ATTRIBUTE_QUANTITY_IN_UNIT_OF_SALE+","+ATTRIBUTE_UNIT_OF_SALE+",";
			   query += "'WT ' || sum("+ATTRIBUTE_BOX_GROSS_WEIGHT+") "+ATTRIBUTE_GROSS_WEIGHT+","+ATTRIBUTE_DATE_OF_MANUFACTURE+","+ATTRIBUTE_EXPIRE_DATE+",";
			   query += ATTRIBUTE_PALLET_ID+","+ATTRIBUTE_DATE_SHIPPED+","+ATTRIBUTE_UN_NUMBER_AND_PROPER_SHIP_NAME;
			   
			   if(isDlaGases)
				   query += "," + ATTRIBUTE_TRACK_SERIAL_NUMBER;
			   
			   query += " from " + TABLE + " ";
			   
			   query += getWhereClause(criteria);
			   
			   query += "group by "+ATTRIBUTE_INVENTORY_GROUP+","+ATTRIBUTE_PACKING_GROUP_ID+","+ATTRIBUTE_ISSUE_ID+","+ATTRIBUTE_PR_NUMBER+",";
			   query += ATTRIBUTE_LINE_ITEM+","+ATTRIBUTE_RECEIPT_ID+","+ATTRIBUTE_NSN+","+ATTRIBUTE_CUSTOMER_PO+",";
			   query += ATTRIBUTE_CAGE_CODE+","+ATTRIBUTE_FORMATTED_NSN+","+ATTRIBUTE_PID_PART_NO+","+ATTRIBUTE_FORMATTED_CUSTOMER_PO+",";
			   query += ATTRIBUTE_UNIT_OF_SALE+","+ATTRIBUTE_DATE_OF_MANUFACTURE+","+ATTRIBUTE_EXPIRE_DATE+",";
			   query += ATTRIBUTE_PALLET_ID+","+ATTRIBUTE_DATE_SHIPPED+","+ATTRIBUTE_UN_NUMBER_AND_PROPER_SHIP_NAME;
			   
			   if(isDlaGases)
				   query += ","+ATTRIBUTE_TRACK_SERIAL_NUMBER;
    
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			UsgovLabelViewBean usgovLabelViewBean = new UsgovLabelViewBean();
			load(dataSetRow, usgovLabelViewBean);
			usgovLabelViewBeanColl.add(usgovLabelViewBean);
		}

		return usgovLabelViewBeanColl;
	}
}