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
import com.tcmis.internal.hub.beans.EdiOrderErrorViewBean;


/******************************************************************************
 * CLASSNAME: EdiOrderErrorViewBeanFactory <br>
 * @version: 1.0, May 9, 2008 <br>
 *****************************************************************************/


public class EdiOrderErrorViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_TRADING_PARTNER_ID = "TRADING_PARTNER_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CUSTOMER_PO_NO = "CUSTOMER_PO_NO";
	public String ATTRIBUTE_CUSTOMER_PO_LINE_NO_TRIM = "CUSTOMER_PO_LINE_NO_TRIM";
	public String ATTRIBUTE_CUSTOMER_PO_LINE_NO = "CUSTOMER_PO_LINE_NO";
	public String ATTRIBUTE_TRANSACTION_TYPE = "TRANSACTION_TYPE";
	public String ATTRIBUTE_TRANSACTION_TYPE_DISPLAY = "TRANSACTION_TYPE_DISPLAY";
	public String ATTRIBUTE_LINE_SEQUENCE = "LINE_SEQUENCE";
	public String ATTRIBUTE_CHANGE_ORDER_SEQUENCE = "CHANGE_ORDER_SEQUENCE";
	public String ATTRIBUTE_ERROR_DETAIL = "ERROR_DETAIL";
	public String ATTRIBUTE_ORDERED_QTY = "ORDERED_QTY";
	public String ATTRIBUTE_ORDERED_UOM = "ORDERED_UOM";
	public String ATTRIBUTE_CATALOG_UOS = "CATALOG_UOS";
	public String ATTRIBUTE_UOS_PER_PACKAGING = "UOS_PER_PACKAGING";
	public String ATTRIBUTE_MR_QTY = "MR_QTY";
	public String ATTRIBUTE_UNIT_PRICE_ON_ORDER = "UNIT_PRICE_ON_ORDER";
	public String ATTRIBUTE_CURRENT_ORDER_STATUS = "CURRENT_ORDER_STATUS";
	public String ATTRIBUTE_CAT_PART_NO_ON_ORDER = "CAT_PART_NO_ON_ORDER";
	public String ATTRIBUTE_MANUFACTURER_PART_NUM = "MANUFACTURER_PART_NUM";
	public String ATTRIBUTE_HAAS_ITEM_NO = "HAAS_ITEM_NO";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_DATE_ISSUED = "DATE_ISSUED";
	public String ATTRIBUTE_ITEM_DESCRIPTION_ON_ORDER = "ITEM_DESCRIPTION_ON_ORDER";
	public String ATTRIBUTE_CUSTOMER_PO_LINE_NOTE = "CUSTOMER_PO_LINE_NOTE";
	public String ATTRIBUTE_REQUESTED_DELIVERY_DATE = "REQUESTED_DELIVERY_DATE";
	public String ATTRIBUTE_ESTIMATED_DOCK_DATE = "ESTIMATED_DOCK_DATE";
	public String ATTRIBUTE_DATE_INSERTED = "DATE_INSERTED";
	public String ATTRIBUTE_BUYER_PHONE = "BUYER_PHONE";
	public String ATTRIBUTE_CUSTOMER_PO_NOTE = "CUSTOMER_PO_NOTE";
	public String ATTRIBUTE_BUYER_NAME_ON_PO = "BUYER_NAME_ON_PO";
	public String ATTRIBUTE_SHIPTO_PARTY_NAME = "SHIPTO_PARTY_NAME";
	public String ATTRIBUTE_SHIPTO_PARTY_ID = "SHIPTO_PARTY_ID";
	public String ATTRIBUTE_STATUS_BEFORE_ERROR = "STATUS_BEFORE_ERROR";
	public String ATTRIBUTE_MR_LINE = "MR_LINE";
	public String ATTRIBUTE_HAAS_PO = "HAAS_PO";
	public String ATTRIBUTE_CHANGE_SUB_TYPE_CODE = "CHANGE_SUB_TYPE_CODE";
	public String ATTRIBUTE_SHIPTO_FIRST_LETTER = "SHIPTO_FIRST_LETTER";
	public String ATTRIBUTE_LOAD_ID = "LOAD_ID";
	public String ATTRIBUTE_LOAD_LINE = "LOAD_LINE";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO_ORIG = "CAT_PART_NO_ORIG";
	public String ATTRIBUTE_MULTIPLE_PART = "MULTIPLE_PART";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_UNIT_PRICE_ORIG = "UNIT_PRICE_ORIG";
	public String ATTRIBUTE_RELEASED = "RELEASED";
	public String ATTRIBUTE_SHIPPING_NOTES = "SHIPPING_NOTES";
	public String ATTRIBUTE_SHIP_TO_DODAAC = "SHIP_TO_DODAAC";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_SHIP_TO_ADDRESS = "SHIP_TO_ADDRESS";
	public String ATTRIBUTE_MARK_FOR_DODAAC = "MARK_FOR_DODAAC";
	public String ATTRIBUTE_MARK_FOR_LOCATION_ID = "MARK_FOR_LOCATION_ID";
	public String ATTRIBUTE_MARK_FOR_ADDRESS = "MARK_FOR_ADDRESS";
	public String ATTRIBUTE_ADDRESS_CHANGE_REQUEST_ID = "ADDRESS_CHANGE_REQUEST_ID";
	public String ATTRIBUTE_ADDRESS_CHANGE_ALLOWED = "ADDRESS_CHANGE_ALLOWED";
	public String ATTRIBUTE_ADDRESS_CHANGE_TYPE = "ADDRESS_CHANGE_TYPE";
	public String ATTRIBUTE_TRANSACTION_REF_NUM = "TRANSACTION_REF_NUM";
    public String ATTRIBUTE_SENT_TO_DPMS = "SENT_TO_DPMS";
    public String ATTRIBUTE_CONTRACT_OWNER = "CONTRACT_OWNER";

	//table name
	public String TABLE = "EDI_ORDER_ERROR_VIEW";


	//constructor
	public EdiOrderErrorViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("tradingPartnerId")) {
			return ATTRIBUTE_TRADING_PARTNER_ID;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("customerPoNo")) {
			return ATTRIBUTE_CUSTOMER_PO_NO;
		}
		else if(attributeName.equals("customerPoLineNoTrim")) {
			return ATTRIBUTE_CUSTOMER_PO_LINE_NO_TRIM;
		}
		else if(attributeName.equals("customerPoLineNo")) {
			return ATTRIBUTE_CUSTOMER_PO_LINE_NO;
		}
		else if(attributeName.equals("transactionType")) {
			return ATTRIBUTE_TRANSACTION_TYPE;
		}
		else if(attributeName.equals("transactionTypeDisplay")) {
			return ATTRIBUTE_TRANSACTION_TYPE_DISPLAY;
		}
		else if(attributeName.equals("lineSequence")) {
			return ATTRIBUTE_LINE_SEQUENCE;
		}
		else if(attributeName.equals("changeOrderSequence")) {
			return ATTRIBUTE_CHANGE_ORDER_SEQUENCE;
		}
		else if(attributeName.equals("errorDetail")) {
			return ATTRIBUTE_ERROR_DETAIL;
		}
		else if(attributeName.equals("orderedQty")) {
			return ATTRIBUTE_ORDERED_QTY;
		}
		else if(attributeName.equals("orderedUom")) {
			return ATTRIBUTE_ORDERED_UOM;
		}
		else if(attributeName.equals("catalogUos")) {
			return ATTRIBUTE_CATALOG_UOS;
		}
		else if(attributeName.equals("uosPerPackaging")) {
			return ATTRIBUTE_UOS_PER_PACKAGING;
		}
		else if(attributeName.equals("mrQty")) {
			return ATTRIBUTE_MR_QTY;
		}
		else if(attributeName.equals("unitPriceOnOrder")) {
			return ATTRIBUTE_UNIT_PRICE_ON_ORDER;
		}
		else if(attributeName.equals("currentOrderStatus")) {
			return ATTRIBUTE_CURRENT_ORDER_STATUS;
		}
		else if(attributeName.equals("catPartNoOnOrder")) {
			return ATTRIBUTE_CAT_PART_NO_ON_ORDER;
		}
		else if(attributeName.equals("manufacturerPartNum")) {
			return ATTRIBUTE_MANUFACTURER_PART_NUM;
		}
		else if(attributeName.equals("haasItemNo")) {
			return ATTRIBUTE_HAAS_ITEM_NO;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("dateIssued")) {
			return ATTRIBUTE_DATE_ISSUED;
		}
		else if(attributeName.equals("itemDescriptionOnOrder")) {
			return ATTRIBUTE_ITEM_DESCRIPTION_ON_ORDER;
		}
		else if(attributeName.equals("customerPoLineNote")) {
			return ATTRIBUTE_CUSTOMER_PO_LINE_NOTE;
		}
		else if(attributeName.equals("requestedDeliveryDate")) {
			return ATTRIBUTE_REQUESTED_DELIVERY_DATE;
		}
		else if(attributeName.equals("estimatedDockDate")) {
			return ATTRIBUTE_ESTIMATED_DOCK_DATE;
		}
		else if(attributeName.equals("dateInserted")) {
			return ATTRIBUTE_DATE_INSERTED;
		}
		else if(attributeName.equals("buyerPhone")) {
			return ATTRIBUTE_BUYER_PHONE;
		}
		else if(attributeName.equals("customerPoNote")) {
			return ATTRIBUTE_CUSTOMER_PO_NOTE;
		}
		else if(attributeName.equals("buyerNameOnPo")) {
			return ATTRIBUTE_BUYER_NAME_ON_PO;
		}
		else if(attributeName.equals("shiptoPartyName")) {
			return ATTRIBUTE_SHIPTO_PARTY_NAME;
		}
		else if(attributeName.equals("shiptoPartyId")) {
			return ATTRIBUTE_SHIPTO_PARTY_ID;
		}
		else if(attributeName.equals("statusBeforeError")) {
			return ATTRIBUTE_STATUS_BEFORE_ERROR;
		}
		else if(attributeName.equals("mrLine")) {
			return ATTRIBUTE_MR_LINE;
		}
		else if(attributeName.equals("haasPo")) {
			return ATTRIBUTE_HAAS_PO;
		}
		else if(attributeName.equals("changeSubTypeCode")) {
			return ATTRIBUTE_CHANGE_SUB_TYPE_CODE;
		}
		else if(attributeName.equals("shiptoFirstLetter")) {
			return ATTRIBUTE_SHIPTO_FIRST_LETTER;
		}
		else if(attributeName.equals("loadId")) {
			return ATTRIBUTE_LOAD_ID;
		}
		else if(attributeName.equals("loadLine")) {
			return ATTRIBUTE_LOAD_LINE;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("catPartNoOrig")) {
			return ATTRIBUTE_CAT_PART_NO_ORIG;
		}
		else if(attributeName.equals("multiplePart")) {
			return ATTRIBUTE_MULTIPLE_PART;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("unitPriceOrig")) {
			return ATTRIBUTE_UNIT_PRICE_ORIG;
		}
		else if(attributeName.equals("released")) {
			return ATTRIBUTE_RELEASED;
		}
		else if(attributeName.equals("shippingNotes")) {
			return ATTRIBUTE_SHIPPING_NOTES;
		}
		else if(attributeName.equals("shipToDodaac")) {
			return ATTRIBUTE_SHIP_TO_DODAAC;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("shipToAddress")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS;
		}
		else if(attributeName.equals("markForDodaac")) {
			return ATTRIBUTE_MARK_FOR_DODAAC;
		}
		else if(attributeName.equals("markForLocationId")) {
			return ATTRIBUTE_MARK_FOR_LOCATION_ID;
		}
		else if(attributeName.equals("markForAddress")) {
			return ATTRIBUTE_MARK_FOR_ADDRESS;
		}
		else if(attributeName.equals("addressChangeRequestId")) {
			return ATTRIBUTE_ADDRESS_CHANGE_REQUEST_ID;
		}
		else if(attributeName.equals("addressChangeAllowed")) {
			return ATTRIBUTE_ADDRESS_CHANGE_ALLOWED;
		}
		else if(attributeName.equals("addressChangeType")) {
			return ATTRIBUTE_ADDRESS_CHANGE_TYPE;
		}
		else if(attributeName.equals("transactionRefNum")) {
			return ATTRIBUTE_TRANSACTION_REF_NUM;
		}
		else if(attributeName.equals("sentToDpms")) {
			return ATTRIBUTE_SENT_TO_DPMS;
		}
		else if(attributeName.equals("contractOwner")) {
			return ATTRIBUTE_CONTRACT_OWNER;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, EdiOrderErrorViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(EdiOrderErrorViewBean ediOrderErrorViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("tradingPartnerId", "SearchCriterion.EQUALS",
			"" + ediOrderErrorViewBean.getTradingPartnerId());

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


	public int delete(EdiOrderErrorViewBean ediOrderErrorViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("tradingPartnerId", "SearchCriterion.EQUALS",
			"" + ediOrderErrorViewBean.getTradingPartnerId());

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
	public int insert(EdiOrderErrorViewBean ediOrderErrorViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(ediOrderErrorViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(EdiOrderErrorViewBean ediOrderErrorViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_TRADING_PARTNER_ID + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_CUSTOMER_PO_NO + "," +
			ATTRIBUTE_CUSTOMER_PO_LINE_NO_TRIM + "," +
			ATTRIBUTE_CUSTOMER_PO_LINE_NO + "," +
			ATTRIBUTE_TRANSACTION_TYPE + "," +
			ATTRIBUTE_TRANSACTION_TYPE_DISPLAY + "," +
			ATTRIBUTE_LINE_SEQUENCE + "," +
			ATTRIBUTE_CHANGE_ORDER_SEQUENCE + "," +
			ATTRIBUTE_ERROR_DETAIL + "," +
			ATTRIBUTE_ORDERED_QTY + "," +
			ATTRIBUTE_ORDERED_UOM + "," +
			ATTRIBUTE_CATALOG_UOS + "," +
			ATTRIBUTE_UOS_PER_PACKAGING + "," +
			ATTRIBUTE_MR_QTY + "," +
			ATTRIBUTE_UNIT_PRICE_ON_ORDER + "," +
			ATTRIBUTE_CURRENT_ORDER_STATUS + "," +
			ATTRIBUTE_CAT_PART_NO_ON_ORDER + "," +
			ATTRIBUTE_MANUFACTURER_PART_NUM + "," +
			ATTRIBUTE_HAAS_ITEM_NO + "," +
			ATTRIBUTE_PACKAGING + "," +
			ATTRIBUTE_DATE_ISSUED + "," +
			ATTRIBUTE_ITEM_DESCRIPTION_ON_ORDER + "," +
			ATTRIBUTE_CUSTOMER_PO_LINE_NOTE + "," +
			ATTRIBUTE_REQUESTED_DELIVERY_DATE + "," +
			ATTRIBUTE_ESTIMATED_DOCK_DATE + "," +
			ATTRIBUTE_DATE_INSERTED + "," +
			ATTRIBUTE_BUYER_PHONE + "," +
			ATTRIBUTE_CUSTOMER_PO_NOTE + "," +
			ATTRIBUTE_BUYER_NAME_ON_PO + "," +
			ATTRIBUTE_SHIPTO_PARTY_NAME + "," +
			ATTRIBUTE_SHIPTO_PARTY_ID + "," +
			ATTRIBUTE_STATUS_BEFORE_ERROR + "," +
			ATTRIBUTE_MR_LINE + "," +
			ATTRIBUTE_HAAS_PO + "," +
			ATTRIBUTE_CHANGE_SUB_TYPE_CODE + "," +
			ATTRIBUTE_SHIPTO_FIRST_LETTER + "," +
			ATTRIBUTE_LOAD_ID + "," +
			ATTRIBUTE_LOAD_LINE + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_CAT_PART_NO_ORIG + "," +
			ATTRIBUTE_MULTIPLE_PART + "," +
			ATTRIBUTE_CURRENCY_ID + "," +
			ATTRIBUTE_UNIT_PRICE_ORIG + "," +
			ATTRIBUTE_RELEASED + "," +
			ATTRIBUTE_SHIPPING_NOTES + "," +
			ATTRIBUTE_SHIP_TO_DODAAC + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS + "," +
			ATTRIBUTE_MARK_FOR_DODAAC + "," +
			ATTRIBUTE_MARK_FOR_LOCATION_ID + "," +
			ATTRIBUTE_MARK_FOR_ADDRESS + "," +
			ATTRIBUTE_ADDRESS_CHANGE_REQUEST_ID + "," +
			ATTRIBUTE_ADDRESS_CHANGE_ALLOWED + "," +
			ATTRIBUTE_ADDRESS_CHANGE_TYPE + "," +
			ATTRIBUTE_TRANSACTION_REF_NUM + "," +
                        ATTRIBUTE_SENT_TO_DPMS + ") " +
 values (
			SqlHandler.delimitString(ediOrderErrorViewBean.getTradingPartnerId()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getFacilityId()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getCustomerPoNo()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getCustomerPoLineNoTrim()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getCustomerPoLineNo()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getTransactionType()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getTransactionTypeDisplay()) + "," +
			StringHandler.nullIfZero(ediOrderErrorViewBean.getLineSequence()) + "," +
			StringHandler.nullIfZero(ediOrderErrorViewBean.getChangeOrderSequence()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getErrorDetail()) + "," +
			StringHandler.nullIfZero(ediOrderErrorViewBean.getOrderedQty()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getOrderedUom()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getCatalogUos()) + "," +
			StringHandler.nullIfZero(ediOrderErrorViewBean.getUosPerPackaging()) + "," +
			StringHandler.nullIfZero(ediOrderErrorViewBean.getMrQty()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getUnitPriceOnOrder()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getCurrentOrderStatus()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getCatPartNoOnOrder()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getManufacturerPartNum()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getHaasItemNo()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getPackaging()) + "," +
			DateHandler.getOracleToDateFunction(ediOrderErrorViewBean.getDateIssued()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getItemDescriptionOnOrder()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getCustomerPoLineNote()) + "," +
			DateHandler.getOracleToDateFunction(ediOrderErrorViewBean.getRequestedDeliveryDate()) + "," +
			DateHandler.getOracleToDateFunction(ediOrderErrorViewBean.getEstimatedDockDate()) + "," +
			DateHandler.getOracleToDateFunction(ediOrderErrorViewBean.getDateInserted()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getBuyerPhone()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getCustomerPoNote()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getBuyerNameOnPo()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getShiptoPartyName()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getShiptoPartyId()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getStatusBeforeError()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getMrLine()) + "," +
			StringHandler.nullIfZero(ediOrderErrorViewBean.getHaasPo()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getChangeSubTypeCode()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getShiptoFirstLetter()) + "," +
			StringHandler.nullIfZero(ediOrderErrorViewBean.getLoadId()) + "," +
			StringHandler.nullIfZero(ediOrderErrorViewBean.getLoadLine()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getCatalogId()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getCatPartNoOrig()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getMultiplePart()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getCurrencyId()) + "," +
			StringHandler.nullIfZero(ediOrderErrorViewBean.getUnitPriceOrig()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getReleased()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getShippingNotes()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getShipToDodaac()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getShipToLocationId()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getShipToAddress()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getMarkForDodaac()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getMarkForLocationId()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getMarkForAddress()) + "," +
			StringHandler.nullIfZero(ediOrderErrorViewBean.getAddressChangeRequestId()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getAddressChangeAllowed()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getAddressChangeType()) + "," +
			SqlHandler.delimitString(ediOrderErrorViewBean.getTransactionRefNum()) + "," + 
                        SqlHandler.delimitString(ediOrderErrorViewBean.getSentToDpms()")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(EdiOrderErrorViewBean ediOrderErrorViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(ediOrderErrorViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(EdiOrderErrorViewBean ediOrderErrorViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_TRADING_PARTNER_ID + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getTradingPartnerId()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getFacilityId()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getCompanyId()) + "," +
			ATTRIBUTE_CUSTOMER_PO_NO + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getCustomerPoNo()) + "," +
			ATTRIBUTE_CUSTOMER_PO_LINE_NO_TRIM + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getCustomerPoLineNoTrim()) + "," +
			ATTRIBUTE_CUSTOMER_PO_LINE_NO + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getCustomerPoLineNo()) + "," +
			ATTRIBUTE_TRANSACTION_TYPE + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getTransactionType()) + "," +
			ATTRIBUTE_TRANSACTION_TYPE_DISPLAY + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getTransactionTypeDisplay()) + "," +
			ATTRIBUTE_LINE_SEQUENCE + "=" + 
				StringHandler.nullIfZero(ediOrderErrorViewBean.getLineSequence()) + "," +
			ATTRIBUTE_CHANGE_ORDER_SEQUENCE + "=" + 
				StringHandler.nullIfZero(ediOrderErrorViewBean.getChangeOrderSequence()) + "," +
			ATTRIBUTE_ERROR_DETAIL + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getErrorDetail()) + "," +
			ATTRIBUTE_ORDERED_QTY + "=" + 
				StringHandler.nullIfZero(ediOrderErrorViewBean.getOrderedQty()) + "," +
			ATTRIBUTE_ORDERED_UOM + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getOrderedUom()) + "," +
			ATTRIBUTE_CATALOG_UOS + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getCatalogUos()) + "," +
			ATTRIBUTE_UOS_PER_PACKAGING + "=" + 
				StringHandler.nullIfZero(ediOrderErrorViewBean.getUosPerPackaging()) + "," +
			ATTRIBUTE_MR_QTY + "=" + 
				StringHandler.nullIfZero(ediOrderErrorViewBean.getMrQty()) + "," +
			ATTRIBUTE_UNIT_PRICE_ON_ORDER + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getUnitPriceOnOrder()) + "," +
			ATTRIBUTE_CURRENT_ORDER_STATUS + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getCurrentOrderStatus()) + "," +
			ATTRIBUTE_CAT_PART_NO_ON_ORDER + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getCatPartNoOnOrder()) + "," +
			ATTRIBUTE_MANUFACTURER_PART_NUM + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getManufacturerPartNum()) + "," +
			ATTRIBUTE_HAAS_ITEM_NO + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getHaasItemNo()) + "," +
			ATTRIBUTE_PACKAGING + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getPackaging()) + "," +
			ATTRIBUTE_DATE_ISSUED + "=" + 
				DateHandler.getOracleToDateFunction(ediOrderErrorViewBean.getDateIssued()) + "," +
			ATTRIBUTE_ITEM_DESCRIPTION_ON_ORDER + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getItemDescriptionOnOrder()) + "," +
			ATTRIBUTE_CUSTOMER_PO_LINE_NOTE + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getCustomerPoLineNote()) + "," +
			ATTRIBUTE_REQUESTED_DELIVERY_DATE + "=" + 
				DateHandler.getOracleToDateFunction(ediOrderErrorViewBean.getRequestedDeliveryDate()) + "," +
			ATTRIBUTE_ESTIMATED_DOCK_DATE + "=" + 
				DateHandler.getOracleToDateFunction(ediOrderErrorViewBean.getEstimatedDockDate()) + "," +
			ATTRIBUTE_DATE_INSERTED + "=" + 
				DateHandler.getOracleToDateFunction(ediOrderErrorViewBean.getDateInserted()) + "," +
			ATTRIBUTE_BUYER_PHONE + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getBuyerPhone()) + "," +
			ATTRIBUTE_CUSTOMER_PO_NOTE + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getCustomerPoNote()) + "," +
			ATTRIBUTE_BUYER_NAME_ON_PO + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getBuyerNameOnPo()) + "," +
			ATTRIBUTE_SHIPTO_PARTY_NAME + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getShiptoPartyName()) + "," +
			ATTRIBUTE_SHIPTO_PARTY_ID + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getShiptoPartyId()) + "," +
			ATTRIBUTE_STATUS_BEFORE_ERROR + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getStatusBeforeError()) + "," +
			ATTRIBUTE_MR_LINE + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getMrLine()) + "," +
			ATTRIBUTE_HAAS_PO + "=" + 
				StringHandler.nullIfZero(ediOrderErrorViewBean.getHaasPo()) + "," +
			ATTRIBUTE_CHANGE_SUB_TYPE_CODE + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getChangeSubTypeCode()) + "," +
			ATTRIBUTE_SHIPTO_FIRST_LETTER + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getShiptoFirstLetter()) + "," +
			ATTRIBUTE_LOAD_ID + "=" + 
				StringHandler.nullIfZero(ediOrderErrorViewBean.getLoadId()) + "," +
			ATTRIBUTE_LOAD_LINE + "=" + 
				StringHandler.nullIfZero(ediOrderErrorViewBean.getLoadLine()) + "," +
			ATTRIBUTE_CATALOG_ID + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getCatalogId()) + "," +
			ATTRIBUTE_CAT_PART_NO_ORIG + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getCatPartNoOrig()) + "," +
			ATTRIBUTE_MULTIPLE_PART + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getMultiplePart()) + "," +
			ATTRIBUTE_CURRENCY_ID + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getCurrencyId()) + "," +
			ATTRIBUTE_UNIT_PRICE_ORIG + "=" + 
				StringHandler.nullIfZero(ediOrderErrorViewBean.getUnitPriceOrig()) + "," +
			ATTRIBUTE_RELEASED + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getReleased()) + "," +
			ATTRIBUTE_SHIPPING_NOTES + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getShippingNotes()) + "," +
			ATTRIBUTE_SHIP_TO_DODAAC + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getShipToDodaac()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getShipToLocationId()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getShipToAddress()) + "," +
			ATTRIBUTE_MARK_FOR_DODAAC + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getMarkForDodaac()) + "," +
			ATTRIBUTE_MARK_FOR_LOCATION_ID + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getMarkForLocationId()) + "," +
			ATTRIBUTE_MARK_FOR_ADDRESS + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getMarkForAddress()) + "," +
			ATTRIBUTE_ADDRESS_CHANGE_REQUEST_ID + "=" + 
				StringHandler.nullIfZero(ediOrderErrorViewBean.getAddressChangeRequestId()) + "," +
			ATTRIBUTE_ADDRESS_CHANGE_ALLOWED + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getAddressChangeAllowed()) + "," +
			ATTRIBUTE_ADDRESS_CHANGE_TYPE + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getAddressChangeType()) + "," +
			ATTRIBUTE_TRANSACTION_REF_NUM + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getTransactionRefNum()) + " " +
			ATTRIBUTE_TRANSACTION_SENT_TO_DPMS + "=" + 
				SqlHandler.delimitString(ediOrderErrorViewBean.getSentToDpms()) + " " +
			"where " + ATTRIBUTE_TRADING_PARTNER_ID + "=" +
				StringHandler.nullIfZero(ediOrderErrorViewBean.getTradingPartnerId());

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

		Collection ediOrderErrorViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			EdiOrderErrorViewBean ediOrderErrorViewBean = new EdiOrderErrorViewBean();
			load(dataSetRow, ediOrderErrorViewBean);
			ediOrderErrorViewBeanColl.add(ediOrderErrorViewBean);
		}

		return ediOrderErrorViewBeanColl;
	}
}