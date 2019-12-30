package com.tcmis.internal.supply.factory;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.DbuyContractPriceOvBean;


/******************************************************************************
 * CLASSNAME: DbuyContractPriceOvBeanFactory <br>
 * @version: 1.0, Nov 13, 2009 <br>
 *****************************************************************************/


public class DbuyContractPriceOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_OPS_COMPANY_ID = "OPS_COMPANY_ID";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_OPERATING_ENTITY_NAME = "OPERATING_ENTITY_NAME";
	public String ATTRIBUTE_DBUY_CONTRACT_ID = "DBUY_CONTRACT_ID";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_SHIP_TO_COMPANY_ID = "SHIP_TO_COMPANY_ID";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_PRIORITY = "PRIORITY";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_CARRIER = "CARRIER";
	public String ATTRIBUTE_BUYER = "BUYER";
	public String ATTRIBUTE_SOURCER = "SOURCER";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_FREIGHT_ON_BOARD = "FREIGHT_ON_BOARD";
	public String ATTRIBUTE_MULTIPLE_OF = "MULTIPLE_OF";
	public String ATTRIBUTE_SUPPLIER_PART_NO = "SUPPLIER_PART_NO";
	public String ATTRIBUTE_SUPPLIER_CONTACT_ID = "SUPPLIER_CONTACT_ID";
	public String ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT = "REMAINING_SHELF_LIFE_PERCENT";
	public String ATTRIBUTE_CRITICAL_ORDER_CARRIER = "CRITICAL_ORDER_CARRIER";
	public String ATTRIBUTE_REF_CLIENT_PART_NO = "REF_CLIENT_PART_NO";
	public String ATTRIBUTE_REF_CLIENT_PO_NO = "REF_CLIENT_PO_NO";
	public String ATTRIBUTE_ROUND_TO_MULTIPLE = "ROUND_TO_MULTIPLE";
	public String ATTRIBUTE_CONSIGNMENT = "CONSIGNMENT";
	public String ATTRIBUTE_LEAD_TIME_DAYS = "LEAD_TIME_DAYS";
	public String ATTRIBUTE_TRANSIT_TIME_IN_DAYS = "TRANSIT_TIME_IN_DAYS";
	public String ATTRIBUTE_CONTRACT_REFERENCE_COMMENTS = "CONTRACT_REFERENCE_COMMENTS";
	public String ATTRIBUTE_LOADING_COMMENTS = "LOADING_COMMENTS";
	public String ATTRIBUTE_PRICING_TYPE = "PRICING_TYPE";
	public String ATTRIBUTE_SUPPLY_PATH = "SUPPLY_PATH";
	public String ATTRIBUTE_LOADING_DATE = "LOADING_DATE";
	public String ATTRIBUTE_BUYER_COMPANY_ID = "BUYER_COMPANY_ID";
	public String ATTRIBUTE_REF_DELIVERY_POINT = "REF_DELIVERY_POINT";
	public String ATTRIBUTE_REF_MATERIAL_REQUEST = "REF_MATERIAL_REQUEST";
	public String ATTRIBUTE_REF_CUSTOMER_CONTACT_INFO = "REF_CUSTOMER_CONTACT_INFO";
	public String ATTRIBUTE_ADDITIONAL_LINE_NOTES = "ADDITIONAL_LINE_NOTES";
	public String ATTRIBUTE_PURCHASER_REVIEW = "PURCHASER_REVIEW";
	public String ATTRIBUTE_UPDATED_BY = "UPDATED_BY";
	public String ATTRIBUTE_UPDATED_DATE = "UPDATED_DATE";
	public String ATTRIBUTE_DEFAULT_SHIPMENT_ORIGIN_STATE = "DEFAULT_SHIPMENT_ORIGIN_STATE";
	public String ATTRIBUTE_START_DATE = "START_DATE";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_PRICE_COMMENTS = "PRICE_COMMENTS";
	public String ATTRIBUTE_PRICE_BREAK_COLLECTION = "PRICE_BREAK_COLLECTION";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_INVENTORY_GROUP_HUB = "INVENTORY_GROUP_HUB";
	public String ATTRIBUTE_UPDATE_BY_NAME = "UPDATE_BY_NAME";
	public String ATTRIBUTE_MIN_BUY_QUANTITY = "MIN_BUY_QUANTITY";
	public String ATTRIBUTE_MIN_BUY_VALUE = "MIN_BUY_VALUE";
	public String ATTRIBUTE_PRICE_UPDATED_BY = "PRICE_UPDATED_BY";
	public String ATTRIBUTE_PRICE_UPDATED_BY_NAME = "PRICE_UPDATED_BY_NAME";
	public String ATTRIBUTE_PRICE_UPDATED_DATE = "PRICE_UPDATED_DATE";
	public String ATTRIBUTE_CRITICAL_ORDER_CARRIER_NAME = "CRITICAL_ORDER_CARRIER_NAME";
	public String ATTRIBUTE_BUY_TYPE_FLAG = "BUY_TYPE_FLAG";
	public String ATTRIBUTE_BUY_TYPE = "BUY_TYPE";

	//table name
	public String TABLE = "DBUY_CONTRACT_PRICE_OV";
    public String PRICE_HISTORY_TABLE = "DBUY_CONTRACT_PRICE_HISTORY_OV";


    //constructor
	public DbuyContractPriceOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("opsCompanyId")) {
			return ATTRIBUTE_OPS_COMPANY_ID;
		}
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("operatingEntityName")) {
			return ATTRIBUTE_OPERATING_ENTITY_NAME;
		}
		else if(attributeName.equals("dbuyContractId")) {
			return ATTRIBUTE_DBUY_CONTRACT_ID;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if(attributeName.equals("shipToCompanyId")) {
			return ATTRIBUTE_SHIP_TO_COMPANY_ID;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("priority")) {
			return ATTRIBUTE_PRIORITY;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("carrier")) {
			return ATTRIBUTE_CARRIER;
		}
		else if(attributeName.equals("buyer")) {
			return ATTRIBUTE_BUYER;
		}
		else if(attributeName.equals("sourcer")) {
			return ATTRIBUTE_SOURCER;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if(attributeName.equals("freightOnBoard")) {
			return ATTRIBUTE_FREIGHT_ON_BOARD;
		}
		else if(attributeName.equals("multipleOf")) {
			return ATTRIBUTE_MULTIPLE_OF;
		}
		else if(attributeName.equals("supplierPartNo")) {
			return ATTRIBUTE_SUPPLIER_PART_NO;
		}
		else if(attributeName.equals("supplierContactId")) {
			return ATTRIBUTE_SUPPLIER_CONTACT_ID;
		}
		else if(attributeName.equals("remainingShelfLifePercent")) {
			return ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT;
		}
		else if(attributeName.equals("criticalOrderCarrier")) {
			return ATTRIBUTE_CRITICAL_ORDER_CARRIER;
		}
		else if(attributeName.equals("refClientPartNo")) {
			return ATTRIBUTE_REF_CLIENT_PART_NO;
		}
		else if(attributeName.equals("refClientPoNo")) {
			return ATTRIBUTE_REF_CLIENT_PO_NO;
		}
		else if(attributeName.equals("roundToMultiple")) {
			return ATTRIBUTE_ROUND_TO_MULTIPLE;
		}
		else if(attributeName.equals("consignment")) {
			return ATTRIBUTE_CONSIGNMENT;
		}
		else if(attributeName.equals("leadTimeDays")) {
			return ATTRIBUTE_LEAD_TIME_DAYS;
		}
		else if(attributeName.equals("transitTimeInDays")) {
			return ATTRIBUTE_TRANSIT_TIME_IN_DAYS;
		}
		else if(attributeName.equals("contractReferenceComments")) {
			return ATTRIBUTE_CONTRACT_REFERENCE_COMMENTS;
		}
		else if(attributeName.equals("loadingComments")) {
			return ATTRIBUTE_LOADING_COMMENTS;
		}
		else if(attributeName.equals("pricingType")) {
			return ATTRIBUTE_PRICING_TYPE;
		}
		else if(attributeName.equals("supplyPath")) {
			return ATTRIBUTE_SUPPLY_PATH;
		}
		else if(attributeName.equals("loadingDate")) {
			return ATTRIBUTE_LOADING_DATE;
		}
		else if(attributeName.equals("buyerCompanyId")) {
			return ATTRIBUTE_BUYER_COMPANY_ID;
		}
		else if(attributeName.equals("refDeliveryPoint")) {
			return ATTRIBUTE_REF_DELIVERY_POINT;
		}
		else if(attributeName.equals("refMaterialRequest")) {
			return ATTRIBUTE_REF_MATERIAL_REQUEST;
		}
		else if(attributeName.equals("refCustomerContactInfo")) {
			return ATTRIBUTE_REF_CUSTOMER_CONTACT_INFO;
		}
		else if(attributeName.equals("additionalLineNotes")) {
			return ATTRIBUTE_ADDITIONAL_LINE_NOTES;
		}
		else if(attributeName.equals("purchaserReview")) {
			return ATTRIBUTE_PURCHASER_REVIEW;
		}
		else if(attributeName.equals("updatedBy")) {
			return ATTRIBUTE_UPDATED_BY;
		}
		else if(attributeName.equals("updatedDate")) {
			return ATTRIBUTE_UPDATED_DATE;
		}
		else if(attributeName.equals("defaultShipmentOriginState")) {
			return ATTRIBUTE_DEFAULT_SHIPMENT_ORIGIN_STATE;
		}
		else if(attributeName.equals("startDate")) {
			return ATTRIBUTE_START_DATE;
		}
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("priceComments")) {
			return ATTRIBUTE_PRICE_COMMENTS;
		}
		else if(attributeName.equals("priceBreakCollection")) {
			return ATTRIBUTE_PRICE_BREAK_COLLECTION;
		}
		else if(attributeName.equals("inventoryGroupHub")) {
			return ATTRIBUTE_INVENTORY_GROUP_HUB;
		}
		else if(attributeName.equals("updateByName")) {
			return ATTRIBUTE_UPDATE_BY_NAME;
		}
		else if(attributeName.equals("minBuyQuantity")) {
			return ATTRIBUTE_MIN_BUY_QUANTITY;
		}
		else if(attributeName.equals("minBuyValue")) {
			return ATTRIBUTE_MIN_BUY_VALUE;
		}
		else if(attributeName.equals("priceUpdatedBy")) {
			return ATTRIBUTE_PRICE_UPDATED_BY;
		}
		else if(attributeName.equals("priceUpdatedByName")) {
			return ATTRIBUTE_PRICE_UPDATED_BY_NAME;
		}
		else if(attributeName.equals("priceUpdatedDate")) {
			return ATTRIBUTE_PRICE_UPDATED_DATE;
		}
		else if(attributeName.equals("criticalOrderCarrierName")) {
			return ATTRIBUTE_CRITICAL_ORDER_CARRIER_NAME;
		}
		else if(attributeName.equals("buyTypeFlag")) {
			return ATTRIBUTE_BUY_TYPE_FLAG;
		}
		else if(attributeName.equals("buyType")) {
			return ATTRIBUTE_BUY_TYPE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, DbuyContractPriceOvBean.class);
	}


	//you need to verify the primary key(s) before uncommenting this
	/*
	//delete
	public int delete(DbuyContractPriceOvBean dbuyContractPriceOvBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("opsCompanyId", "SearchCriterion.EQUALS",
			"" + dbuyContractPriceOvBean.getOpsCompanyId());

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


	public int delete(DbuyContractPriceOvBean dbuyContractPriceOvBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("opsCompanyId", "SearchCriterion.EQUALS",
			"" + dbuyContractPriceOvBean.getOpsCompanyId());

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
	public int insert(DbuyContractPriceOvBean dbuyContractPriceOvBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(dbuyContractPriceOvBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(DbuyContractPriceOvBean dbuyContractPriceOvBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_OPS_COMPANY_ID + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "," +
			ATTRIBUTE_OPERATING_ENTITY_NAME + "," +
			ATTRIBUTE_DBUY_CONTRACT_ID + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_PRIORITY + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_CARRIER + "," +
			ATTRIBUTE_BUYER + "," +
			ATTRIBUTE_SOURCER + "," +
			ATTRIBUTE_STATUS + "," +
			ATTRIBUTE_FREIGHT_ON_BOARD + "," +
			ATTRIBUTE_MULTIPLE_OF + "," +
			ATTRIBUTE_SUPPLIER_PART_NO + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_ID + "," +
			ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT + "," +
			ATTRIBUTE_CRITICAL_ORDER_CARRIER + "," +
			ATTRIBUTE_REF_CLIENT_PART_NO + "," +
			ATTRIBUTE_REF_CLIENT_PO_NO + "," +
			ATTRIBUTE_ROUND_TO_MULTIPLE + "," +
			ATTRIBUTE_CONSIGNMENT + "," +
			ATTRIBUTE_LEAD_TIME_DAYS + "," +
			ATTRIBUTE_TRANSIT_TIME_IN_DAYS + "," +
			ATTRIBUTE_CONTRACT_REFERENCE_COMMENTS + "," +
			ATTRIBUTE_LOADING_COMMENTS + "," +
			ATTRIBUTE_PRICING_TYPE + "," +
			ATTRIBUTE_SUPPLY_PATH + "," +
			ATTRIBUTE_LOADING_DATE + "," +
			ATTRIBUTE_BUYER_COMPANY_ID + "," +
			ATTRIBUTE_REF_DELIVERY_POINT + "," +
			ATTRIBUTE_REF_MATERIAL_REQUEST + "," +
			ATTRIBUTE_REF_CUSTOMER_CONTACT_INFO + "," +
			ATTRIBUTE_ADDITIONAL_LINE_NOTES + "," +
			ATTRIBUTE_PURCHASER_REVIEW + "," +
			ATTRIBUTE_UPDATED_BY + "," +
			ATTRIBUTE_UPDATED_DATE + "," +
			ATTRIBUTE_DEFAULT_SHIPMENT_ORIGIN_STATE + "," +
			ATTRIBUTE_START_DATE + "," +
			ATTRIBUTE_UNIT_PRICE + "," +
			ATTRIBUTE_CURRENCY_ID + "," +
			ATTRIBUTE_PRICE_COMMENTS + "," +
			ATTRIBUTE_PRICE_BREAK_COLLECTION + ")" +
			" values (" +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getOpsCompanyId()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getOpsEntityId()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getOperatingEntityName()) + "," +
			dbuyContractPriceOvBean.getDbuyContractId() + "," +
			dbuyContractPriceOvBean.getItemId() + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getInventoryGroupName()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getShipToCompanyId()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getShipToLocationId()) + "," +
			dbuyContractPriceOvBean.getPriority() + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getSupplier()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getCarrier()) + "," +
			dbuyContractPriceOvBean.getBuyer() + "," +
			dbuyContractPriceOvBean.getSourcer() + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getStatus()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getFreightOnBoard()) + "," +
			dbuyContractPriceOvBean.getMultipleOf() + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getSupplierPartNo()) + "," +
			dbuyContractPriceOvBean.getSupplierContactId() + "," +
			dbuyContractPriceOvBean.getRemainingShelfLifePercent() + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getCriticalOrderCarrier()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getRefClientPartNo()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getRefClientPoNo()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getRoundToMultiple()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getConsignment()) + "," +
			dbuyContractPriceOvBean.getLeadTimeDays() + "," +
			dbuyContractPriceOvBean.getTransitTimeInDays() + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getContractReferenceComments()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getLoadingComments()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getPricingType()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getSupplyPath()) + "," +
			DateHandler.getOracleToDateFunction(dbuyContractPriceOvBean.getLoadingDate()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getBuyerCompanyId()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getRefDeliveryPoint()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getRefMaterialRequest()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getRefCustomerContactInfo()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getAdditionalLineNotes()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getPurchaserReview()) + "," +
			dbuyContractPriceOvBean.getUpdatedBy() + "," +
			DateHandler.getOracleToDateFunction(dbuyContractPriceOvBean.getUpdatedDate()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getDefaultShipmentOriginState()) + "," +
			DateHandler.getOracleToDateFunction(dbuyContractPriceOvBean.getStartDate()) + "," +
			dbuyContractPriceOvBean.getUnitPrice() + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getCurrencyId()) + "," +
			SqlHandler.delimitString(dbuyContractPriceOvBean.getPriceComments()) + "," +
			dbuyContractPriceOvBean.getPriceBreakCollection() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(DbuyContractPriceOvBean dbuyContractPriceOvBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(dbuyContractPriceOvBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(DbuyContractPriceOvBean dbuyContractPriceOvBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_OPS_COMPANY_ID + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getOpsCompanyId()) + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getOpsEntityId()) + "," +
			ATTRIBUTE_OPERATING_ENTITY_NAME + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getOperatingEntityName()) + "," +
			ATTRIBUTE_DBUY_CONTRACT_ID + "=" +
				StringHandler.nullIfZero(dbuyContractPriceOvBean.getDbuyContractId()) + "," +
			ATTRIBUTE_ITEM_ID + "=" +
				StringHandler.nullIfZero(dbuyContractPriceOvBean.getItemId()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getInventoryGroup()) + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getInventoryGroupName()) + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getShipToCompanyId()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getShipToLocationId()) + "," +
			ATTRIBUTE_PRIORITY + "=" +
				StringHandler.nullIfZero(dbuyContractPriceOvBean.getPriority()) + "," +
			ATTRIBUTE_SUPPLIER + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getSupplier()) + "," +
			ATTRIBUTE_CARRIER + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getCarrier()) + "," +
			ATTRIBUTE_BUYER + "=" +
				StringHandler.nullIfZero(dbuyContractPriceOvBean.getBuyer()) + "," +
			ATTRIBUTE_SOURCER + "=" +
				StringHandler.nullIfZero(dbuyContractPriceOvBean.getSourcer()) + "," +
			ATTRIBUTE_STATUS + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getStatus()) + "," +
			ATTRIBUTE_FREIGHT_ON_BOARD + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getFreightOnBoard()) + "," +
			ATTRIBUTE_MULTIPLE_OF + "=" +
				StringHandler.nullIfZero(dbuyContractPriceOvBean.getMultipleOf()) + "," +
			ATTRIBUTE_SUPPLIER_PART_NO + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getSupplierPartNo()) + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_ID + "=" +
				StringHandler.nullIfZero(dbuyContractPriceOvBean.getSupplierContactId()) + "," +
			ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT + "=" +
				StringHandler.nullIfZero(dbuyContractPriceOvBean.getRemainingShelfLifePercent()) + "," +
			ATTRIBUTE_CRITICAL_ORDER_CARRIER + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getCriticalOrderCarrier()) + "," +
			ATTRIBUTE_REF_CLIENT_PART_NO + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getRefClientPartNo()) + "," +
			ATTRIBUTE_REF_CLIENT_PO_NO + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getRefClientPoNo()) + "," +
			ATTRIBUTE_ROUND_TO_MULTIPLE + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getRoundToMultiple()) + "," +
			ATTRIBUTE_CONSIGNMENT + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getConsignment()) + "," +
			ATTRIBUTE_LEAD_TIME_DAYS + "=" +
				StringHandler.nullIfZero(dbuyContractPriceOvBean.getLeadTimeDays()) + "," +
			ATTRIBUTE_TRANSIT_TIME_IN_DAYS + "=" +
				StringHandler.nullIfZero(dbuyContractPriceOvBean.getTransitTimeInDays()) + "," +
			ATTRIBUTE_CONTRACT_REFERENCE_COMMENTS + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getContractReferenceComments()) + "," +
			ATTRIBUTE_LOADING_COMMENTS + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getLoadingComments()) + "," +
			ATTRIBUTE_PRICING_TYPE + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getPricingType()) + "," +
			ATTRIBUTE_SUPPLY_PATH + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getSupplyPath()) + "," +
			ATTRIBUTE_LOADING_DATE + "=" +
				DateHandler.getOracleToDateFunction(dbuyContractPriceOvBean.getLoadingDate()) + "," +
			ATTRIBUTE_BUYER_COMPANY_ID + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getBuyerCompanyId()) + "," +
			ATTRIBUTE_REF_DELIVERY_POINT + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getRefDeliveryPoint()) + "," +
			ATTRIBUTE_REF_MATERIAL_REQUEST + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getRefMaterialRequest()) + "," +
			ATTRIBUTE_REF_CUSTOMER_CONTACT_INFO + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getRefCustomerContactInfo()) + "," +
			ATTRIBUTE_ADDITIONAL_LINE_NOTES + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getAdditionalLineNotes()) + "," +
			ATTRIBUTE_PURCHASER_REVIEW + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getPurchaserReview()) + "," +
			ATTRIBUTE_UPDATED_BY + "=" +
				StringHandler.nullIfZero(dbuyContractPriceOvBean.getUpdatedBy()) + "," +
			ATTRIBUTE_UPDATED_DATE + "=" +
				DateHandler.getOracleToDateFunction(dbuyContractPriceOvBean.getUpdatedDate()) + "," +
			ATTRIBUTE_DEFAULT_SHIPMENT_ORIGIN_STATE + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getDefaultShipmentOriginState()) + "," +
			ATTRIBUTE_START_DATE + "=" +
				DateHandler.getOracleToDateFunction(dbuyContractPriceOvBean.getStartDate()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" +
				StringHandler.nullIfZero(dbuyContractPriceOvBean.getUnitPrice()) + "," +
			ATTRIBUTE_CURRENCY_ID + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getCurrencyId()) + "," +
			ATTRIBUTE_PRICE_COMMENTS + "=" +
				SqlHandler.delimitString(dbuyContractPriceOvBean.getPriceComments()) + "," +
			ATTRIBUTE_PRICE_BREAK_COLLECTION + "=" +
				StringHandler.nullIfZero(dbuyContractPriceOvBean.getPriceBreakCollection()) + " " +
			"where " + ATTRIBUTE_OPS_COMPANY_ID + "=" +
				dbuyContractPriceOvBean.getOpsCompanyId();

		return new SqlManager().update(conn, query);
	}
	 */

	//select
	public Collection select(SearchCriteria criteria)
	throws BaseException {

		return select(criteria,null);

	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
	throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria,sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
	throws BaseException {

		Collection dbuyContractPriceOvBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DbuyContractPriceOvBean dbuyContractPriceOvBean = new DbuyContractPriceOvBean();
			load(dataSetRow, dbuyContractPriceOvBean);
			dbuyContractPriceOvBeanColl.add(dbuyContractPriceOvBean);
		}

		return dbuyContractPriceOvBeanColl;
	}

	public Collection selectObject(SearchCriteria criteria) throws BaseException {
		return selectObject(criteria,null,"","Y");
	}

	public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria, String additionalWhere,String showPriceHistory) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			java.util.Map map = connection.getTypeMap();
			map.put("TCM_OPS.DBUY_CONTRACT_PRICE_BREAK_OBJ",
					Class.forName(
					"com.tcmis.internal.supply.beans.DbuyContractPriceBreakObjBean"));
			map.put("TCM_OPS.DBUY_CONTRACT_PRICES_OBJ",
					Class.forName(
					"com.tcmis.internal.supply.beans.DbuyContractPriceOvBean"));

			c = selectObject(criteria, sortCriteria,connection, additionalWhere,showPriceHistory);
		}
		catch (Exception e) {
			log.error("selectObject error:" + e.getMessage());
			DbSelectException ex = new DbSelectException("error.db.select");
			ex.setRootCause(e);
			throw ex;
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn, String additionalWhere, String showPriceHistory) throws
	BaseException {
		Collection dbuyContractPriceOvBeanColl = new Vector();
        String viewToQuery = TABLE;
        if ("Y".equalsIgnoreCase(com.tcmis.common.util.StringHandler.emptyIfNull(showPriceHistory)))
        {
            viewToQuery = PRICE_HISTORY_TABLE;
        }
        String query = "select value(p) from " + viewToQuery + " p " +getWhereClause(criteria);
		if (!StringHandler.isBlankString(additionalWhere)) {
			query += additionalWhere;
		}
		query += getOrderByClause(sortCriteria);
		if(log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				DbuyContractPriceOvBean b = (DbuyContractPriceOvBean) rs.getObject(1);
				dbuyContractPriceOvBeanColl.add(b);
			}
			rs.close();
			st.close();
		}
		catch (SQLException e) {
			log.error("selectObject error:" + e.getMessage());
			DbSelectException ex = new DbSelectException("error.db.select");
			ex.setRootCause(e);
			throw ex;
		}
		return dbuyContractPriceOvBeanColl;
	}

}