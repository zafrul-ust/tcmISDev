package com.tcmis.supplier.dbuy.factory;


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
import com.tcmis.supplier.dbuy.beans.DbuyViewBean;


/******************************************************************************
 * CLASSNAME: DbuyViewBeanFactory <br>
 * @version: 1.0, Nov 16, 2005 <br>
 *****************************************************************************/


public class DbuyViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_SUPPLIER_CONTACT_NAME = "SUPPLIER_CONTACT_NAME";
	public String ATTRIBUTE_SUPPLIER_COUNTRY_ABBREV = "SUPPLIER_COUNTRY_ABBREV";
	public String ATTRIBUTE_SUPPLIER_STATE_ABBREV = "SUPPLIER_STATE_ABBREV";
	public String ATTRIBUTE_SUPPLIER_ADDRESS_LINE_1 = "SUPPLIER_ADDRESS_LINE_1";
	public String ATTRIBUTE_SUPPLIER_ADDRESS_LINE_2 = "SUPPLIER_ADDRESS_LINE_2";
	public String ATTRIBUTE_SUPPLIER_ADDRESS_LINE_3 = "SUPPLIER_ADDRESS_LINE_3";
	public String ATTRIBUTE_SUPPLIER_CITY = "SUPPLIER_CITY";
	public String ATTRIBUTE_SUPPLIER_ZIP = "SUPPLIER_ZIP";
	public String ATTRIBUTE_SHIP_TO_COMPANY_ID = "SHIP_TO_COMPANY_ID";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_SHIPTO_COUNTRY_ABBREV = "SHIPTO_COUNTRY_ABBREV";
	public String ATTRIBUTE_SHIPTO_STATE_ABBREV = "SHIPTO_STATE_ABBREV";
	public String ATTRIBUTE_SHIPTO_ADDRESS_LINE_1 = "SHIPTO_ADDRESS_LINE_1";
	public String ATTRIBUTE_SHIPTO_ADDRESS_LINE_2 = "SHIPTO_ADDRESS_LINE_2";
	public String ATTRIBUTE_SHIPTO_ADDRESS_LINE_3 = "SHIPTO_ADDRESS_LINE_3";
	public String ATTRIBUTE_SHIPTO_CITY = "SHIPTO_CITY";
	public String ATTRIBUTE_SHIPTO_ZIP = "SHIPTO_ZIP";
	public String ATTRIBUTE_BUYER_NAME = "BUYER_NAME";
	public String ATTRIBUTE_BUYER_EMAIL = "BUYER_EMAIL";
	public String ATTRIBUTE_BUYER_PHONE = "BUYER_PHONE";
	public String ATTRIBUTE_MR_NUMBER = "MR_NUMBER";
	public String ATTRIBUTE_SUPPLIER_SHIP_TO_CODE = "SUPPLIER_SHIP_TO_CODE";
	public String ATTRIBUTE_NUMBER_OF_LINES = "NUMBER_OF_LINES";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_ITEM_SHORT_DESC = "ITEM_SHORT_DESC";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_SUPPLIER_PART_NO = "SUPPLIER_PART_NO";
	public String ATTRIBUTE_EDI_UOM = "EDI_UOM";
	public String ATTRIBUTE_SPEC_FLOWDOWN_NAME = "SPEC_FLOWDOWN_NAME";
	public String ATTRIBUTE_SPEC_FLOWDOWN_TYPE = "SPEC_FLOWDOWN_TYPE";
	public String ATTRIBUTE_CRITICAL = "CRITICAL";
	public String ATTRIBUTE_NEED_BY_DATE = "NEED_BY_DATE";
	public String ATTRIBUTE_PO_LINE_NOTE = "PO_LINE_NOTE";
	public String ATTRIBUTE_EDI_DOCUMENT_CONTROL_NUMBER = "EDI_DOCUMENT_CONTROL_NUMBER";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
	public String ATTRIBUTE_CARRIER_CODE = "CARRIER_CODE";
	public String ATTRIBUTE_CARRIER_ACCOUNT = "CARRIER_ACCOUNT";
	public String ATTRIBUTE_SHIP_TO_ID = "SHIP_TO_ID";
	public String ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT = "REMAINING_SHELF_LIFE_PERCENT";
	public String ATTRIBUTE_CARRIER_BILL_COMPANY = "CARRIER_BILL_COMPANY";
	public String ATTRIBUTE_CARRIER_BILL_NAME = "CARRIER_BILL_NAME";
	public String ATTRIBUTE_CARRIER_BILL_COUNTRY_ABBREV = "CARRIER_BILL_COUNTRY_ABBREV";
	public String ATTRIBUTE_CARRIER_BILL_STATE_ABBREV = "CARRIER_BILL_STATE_ABBREV";
	public String ATTRIBUTE_CARRIER_BILL_ADDRESS_LINE_1 = "CARRIER_BILL_ADDRESS_LINE_1";
	public String ATTRIBUTE_CARRIER_BILL_ADDRESS_LINE_2 = "CARRIER_BILL_ADDRESS_LINE_2";
	public String ATTRIBUTE_CARRIER_BILL_ADDRESS_LINE_3 = "CARRIER_BILL_ADDRESS_LINE_3";
	public String ATTRIBUTE_CARRIER_BILL_CITY = "CARRIER_BILL_CITY";
	public String ATTRIBUTE_CARRIER_BILL_ZIP = "CARRIER_BILL_ZIP";
	public String ATTRIBUTE_BILL_COUNTRY_ABBREV = "BILL_COUNTRY_ABBREV";
	public String ATTRIBUTE_BILL_STATE_ABBREV = "BILL_STATE_ABBREV";
	public String ATTRIBUTE_BILL_ADDRESS_LINE_1 = "BILL_ADDRESS_LINE_1";
	public String ATTRIBUTE_BILL_ADDRESS_LINE_2 = "BILL_ADDRESS_LINE_2";
	public String ATTRIBUTE_BILL_ADDRESS_LINE_3 = "BILL_ADDRESS_LINE_3";
	public String ATTRIBUTE_BILL_CITY = "BILL_CITY";
	public String ATTRIBUTE_BILL_ZIP = "BILL_ZIP";
	public String ATTRIBUTE_PAYMENT_TERMS = "PAYMENT_TERMS";
	public String ATTRIBUTE_PO_NOTES = "PO_NOTES";
	public String ATTRIBUTE_NOTES_SPLITED = "NOTES_SPLITED";
	public String ATTRIBUTE_PO_NOTES_ORDER_ID = "PO_NOTES_ORDER_ID";
	public String ATTRIBUTE_PO_LINE_NOTE_ORDER_ID = "PO_LINE_NOTE_ORDER_ID";
	public String ATTRIBUTE_TRANSACTOR_ID = "TRANSACTOR_ID";
	public String ATTRIBUTE_TRANSACTOR_MAIL_BOX_ADDRESS = "TRANSACTOR_MAIL_BOX_ADDRESS";
	public String ATTRIBUTE_ITEM_DESC_ORDER_ID = "ITEM_DESC_ORDER_ID";
	public String ATTRIBUTE_TRANSACTOR_QUALIFIER = "TRANSACTOR_QUALIFIER";
	public String ATTRIBUTE_TRADE_TERMS = "TRADE_TERMS";
	public String ATTRIBUTE_PAYMENT_METHOD_CODE = "PAYMENT_METHOD_CODE";
	public String ATTRIBUTE_DEFAULT_SHIPMENT_ORIGIN_STATE = "DEFAULT_SHIPMENT_ORIGIN_STATE";
	public String ATTRIBUTE_CUSTOMER_PO = "CUSTOMER_PO";
	public String ATTRIBUTE_SHIP_TO_CONTACT_NAME = "SHIP_TO_CONTACT_NAME";
	public String ATTRIBUTE_SHIP_TO_CONTACT_PHONE = "SHIP_TO_CONTACT_PHONE";

	//table name
	public String TABLE = "DBUY_VIEW";


	//constructor
	public DbuyViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("supplierContactName")) {
			return ATTRIBUTE_SUPPLIER_CONTACT_NAME;
		}
		else if(attributeName.equals("supplierCountryAbbrev")) {
			return ATTRIBUTE_SUPPLIER_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("supplierStateAbbrev")) {
			return ATTRIBUTE_SUPPLIER_STATE_ABBREV;
		}
		else if(attributeName.equals("supplierAddressLine11")) {
			return ATTRIBUTE_SUPPLIER_ADDRESS_LINE_1;
		}
		else if(attributeName.equals("supplierAddressLine22")) {
			return ATTRIBUTE_SUPPLIER_ADDRESS_LINE_2;
		}
		else if(attributeName.equals("supplierAddressLine33")) {
			return ATTRIBUTE_SUPPLIER_ADDRESS_LINE_3;
		}
		else if(attributeName.equals("supplierCity")) {
			return ATTRIBUTE_SUPPLIER_CITY;
		}
		else if(attributeName.equals("supplierZip")) {
			return ATTRIBUTE_SUPPLIER_ZIP;
		}
		else if(attributeName.equals("shipToCompanyId")) {
			return ATTRIBUTE_SHIP_TO_COMPANY_ID;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("shiptoCountryAbbrev")) {
			return ATTRIBUTE_SHIPTO_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("shiptoStateAbbrev")) {
			return ATTRIBUTE_SHIPTO_STATE_ABBREV;
		}
		else if(attributeName.equals("shiptoAddressLine11")) {
			return ATTRIBUTE_SHIPTO_ADDRESS_LINE_1;
		}
		else if(attributeName.equals("shiptoAddressLine22")) {
			return ATTRIBUTE_SHIPTO_ADDRESS_LINE_2;
		}
		else if(attributeName.equals("shiptoAddressLine33")) {
			return ATTRIBUTE_SHIPTO_ADDRESS_LINE_3;
		}
		else if(attributeName.equals("shiptoCity")) {
			return ATTRIBUTE_SHIPTO_CITY;
		}
		else if(attributeName.equals("shiptoZip")) {
			return ATTRIBUTE_SHIPTO_ZIP;
		}
		else if(attributeName.equals("buyerName")) {
			return ATTRIBUTE_BUYER_NAME;
		}
		else if(attributeName.equals("buyerEmail")) {
			return ATTRIBUTE_BUYER_EMAIL;
		}
		else if(attributeName.equals("buyerPhone")) {
			return ATTRIBUTE_BUYER_PHONE;
		}
		else if(attributeName.equals("mrNumber")) {
			return ATTRIBUTE_MR_NUMBER;
		}
		else if(attributeName.equals("supplierShipToCode")) {
			return ATTRIBUTE_SUPPLIER_SHIP_TO_CODE;
		}
		else if(attributeName.equals("numberOfLines")) {
			return ATTRIBUTE_NUMBER_OF_LINES;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("itemShortDesc")) {
			return ATTRIBUTE_ITEM_SHORT_DESC;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("supplierPartNo")) {
			return ATTRIBUTE_SUPPLIER_PART_NO;
		}
		else if(attributeName.equals("ediUom")) {
			return ATTRIBUTE_EDI_UOM;
		}
		else if(attributeName.equals("specFlowdownName")) {
			return ATTRIBUTE_SPEC_FLOWDOWN_NAME;
		}
		else if(attributeName.equals("specFlowdownType")) {
			return ATTRIBUTE_SPEC_FLOWDOWN_TYPE;
		}
		else if(attributeName.equals("critical")) {
			return ATTRIBUTE_CRITICAL;
		}
		else if(attributeName.equals("needByDate")) {
			return ATTRIBUTE_NEED_BY_DATE;
		}
		else if(attributeName.equals("poLineNote")) {
			return ATTRIBUTE_PO_LINE_NOTE;
		}
		else if(attributeName.equals("ediDocumentControlNumber")) {
			return ATTRIBUTE_EDI_DOCUMENT_CONTROL_NUMBER;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("carrierName")) {
			return ATTRIBUTE_CARRIER_NAME;
		}
		else if(attributeName.equals("carrierCode")) {
			return ATTRIBUTE_CARRIER_CODE;
		}
		else if(attributeName.equals("carrierAccount")) {
			return ATTRIBUTE_CARRIER_ACCOUNT;
		}
		else if(attributeName.equals("shipToId")) {
			return ATTRIBUTE_SHIP_TO_ID;
		}
		else if(attributeName.equals("remainingShelfLifePercent")) {
			return ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT;
		}
		else if(attributeName.equals("carrierBillCompany")) {
			return ATTRIBUTE_CARRIER_BILL_COMPANY;
		}
		else if(attributeName.equals("carrierBillName")) {
			return ATTRIBUTE_CARRIER_BILL_NAME;
		}
		else if(attributeName.equals("carrierBillCountryAbbrev")) {
			return ATTRIBUTE_CARRIER_BILL_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("carrierBillStateAbbrev")) {
			return ATTRIBUTE_CARRIER_BILL_STATE_ABBREV;
		}
		else if(attributeName.equals("carrierBillAddressLine11")) {
			return ATTRIBUTE_CARRIER_BILL_ADDRESS_LINE_1;
		}
		else if(attributeName.equals("carrierBillAddressLine22")) {
			return ATTRIBUTE_CARRIER_BILL_ADDRESS_LINE_2;
		}
		else if(attributeName.equals("carrierBillAddressLine33")) {
			return ATTRIBUTE_CARRIER_BILL_ADDRESS_LINE_3;
		}
		else if(attributeName.equals("carrierBillCity")) {
			return ATTRIBUTE_CARRIER_BILL_CITY;
		}
		else if(attributeName.equals("carrierBillZip")) {
			return ATTRIBUTE_CARRIER_BILL_ZIP;
		}
		else if(attributeName.equals("billCountryAbbrev")) {
			return ATTRIBUTE_BILL_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("billStateAbbrev")) {
			return ATTRIBUTE_BILL_STATE_ABBREV;
		}
		else if(attributeName.equals("billAddressLine11")) {
			return ATTRIBUTE_BILL_ADDRESS_LINE_1;
		}
		else if(attributeName.equals("billAddressLine22")) {
			return ATTRIBUTE_BILL_ADDRESS_LINE_2;
		}
		else if(attributeName.equals("billAddressLine33")) {
			return ATTRIBUTE_BILL_ADDRESS_LINE_3;
		}
		else if(attributeName.equals("billCity")) {
			return ATTRIBUTE_BILL_CITY;
		}
		else if(attributeName.equals("billZip")) {
			return ATTRIBUTE_BILL_ZIP;
		}
		else if(attributeName.equals("paymentTerms")) {
			return ATTRIBUTE_PAYMENT_TERMS;
		}
		else if(attributeName.equals("poNotes")) {
			return ATTRIBUTE_PO_NOTES;
		}
		else if(attributeName.equals("notesSplited")) {
			return ATTRIBUTE_NOTES_SPLITED;
		}
		else if(attributeName.equals("poNotesOrderId")) {
			return ATTRIBUTE_PO_NOTES_ORDER_ID;
		}
		else if(attributeName.equals("poLineNoteOrderId")) {
			return ATTRIBUTE_PO_LINE_NOTE_ORDER_ID;
		}
		else if(attributeName.equals("transactorId")) {
			return ATTRIBUTE_TRANSACTOR_ID;
		}
		else if(attributeName.equals("transactorMailBoxAddress")) {
			return ATTRIBUTE_TRANSACTOR_MAIL_BOX_ADDRESS;
		}
		else if(attributeName.equals("itemDescOrderId")) {
			return ATTRIBUTE_ITEM_DESC_ORDER_ID;
		}
		else if(attributeName.equals("transactorQualifier")) {
			return ATTRIBUTE_TRANSACTOR_QUALIFIER;
		}
		else if(attributeName.equals("tradeTerms")) {
			return ATTRIBUTE_TRADE_TERMS;
		}
		else if(attributeName.equals("paymentMethodCode")) {
			return ATTRIBUTE_PAYMENT_METHOD_CODE;
		}
		else if(attributeName.equals("defaultShipmentOriginState")) {
			return ATTRIBUTE_DEFAULT_SHIPMENT_ORIGIN_STATE;
		}
		else if(attributeName.equals("customerPo")) {
			return ATTRIBUTE_CUSTOMER_PO;
		}
		else if(attributeName.equals("shipToContactName")) {
			return ATTRIBUTE_SHIP_TO_CONTACT_NAME;
		}
		else if(attributeName.equals("shipToContactPhone")) {
			return ATTRIBUTE_SHIP_TO_CONTACT_PHONE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DbuyViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(DbuyViewBean dbuyViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + dbuyViewBean.getRadianPo());

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


	public int delete(DbuyViewBean dbuyViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + dbuyViewBean.getRadianPo());

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
	public int insert(DbuyViewBean dbuyViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(dbuyViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(DbuyViewBean dbuyViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_PO_LINE + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_NAME + "," +
			ATTRIBUTE_SUPPLIER_COUNTRY_ABBREV + "," +
			ATTRIBUTE_SUPPLIER_STATE_ABBREV + "," +
			ATTRIBUTE_SUPPLIER_ADDRESS_LINE_1 + "," +
			ATTRIBUTE_SUPPLIER_ADDRESS_LINE_2 + "," +
			ATTRIBUTE_SUPPLIER_ADDRESS_LINE_3 + "," +
			ATTRIBUTE_SUPPLIER_CITY + "," +
			ATTRIBUTE_SUPPLIER_ZIP + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_SHIPTO_COUNTRY_ABBREV + "," +
			ATTRIBUTE_SHIPTO_STATE_ABBREV + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_1 + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_2 + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_3 + "," +
			ATTRIBUTE_SHIPTO_CITY + "," +
			ATTRIBUTE_SHIPTO_ZIP + "," +
			ATTRIBUTE_BUYER_NAME + "," +
			ATTRIBUTE_BUYER_EMAIL + "," +
			ATTRIBUTE_BUYER_PHONE + "," +
			ATTRIBUTE_MR_NUMBER + "," +
			ATTRIBUTE_SUPPLIER_SHIP_TO_CODE + "," +
			ATTRIBUTE_NUMBER_OF_LINES + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_UNIT_PRICE + "," +
			ATTRIBUTE_ITEM_SHORT_DESC + "," +
			ATTRIBUTE_PACKAGING + "," +
			ATTRIBUTE_SUPPLIER_PART_NO + "," +
			ATTRIBUTE_EDI_UOM + "," +
			ATTRIBUTE_SPEC_FLOWDOWN_NAME + "," +
			ATTRIBUTE_SPEC_FLOWDOWN_TYPE + "," +
			ATTRIBUTE_CRITICAL + "," +
			ATTRIBUTE_NEED_BY_DATE + "," +
			ATTRIBUTE_PO_LINE_NOTE + "," +
			ATTRIBUTE_EDI_DOCUMENT_CONTROL_NUMBER + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_CARRIER_NAME + "," +
			ATTRIBUTE_CARRIER_CODE + "," +
			ATTRIBUTE_CARRIER_ACCOUNT + "," +
			ATTRIBUTE_SHIP_TO_ID + "," +
			ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT + "," +
			ATTRIBUTE_CARRIER_BILL_COMPANY + "," +
			ATTRIBUTE_CARRIER_BILL_NAME + "," +
			ATTRIBUTE_CARRIER_BILL_COUNTRY_ABBREV + "," +
			ATTRIBUTE_CARRIER_BILL_STATE_ABBREV + "," +
			ATTRIBUTE_CARRIER_BILL_ADDRESS_LINE_1 + "," +
			ATTRIBUTE_CARRIER_BILL_ADDRESS_LINE_2 + "," +
			ATTRIBUTE_CARRIER_BILL_ADDRESS_LINE_3 + "," +
			ATTRIBUTE_CARRIER_BILL_CITY + "," +
			ATTRIBUTE_CARRIER_BILL_ZIP + "," +
			ATTRIBUTE_BILL_COUNTRY_ABBREV + "," +
			ATTRIBUTE_BILL_STATE_ABBREV + "," +
			ATTRIBUTE_BILL_ADDRESS_LINE_1 + "," +
			ATTRIBUTE_BILL_ADDRESS_LINE_2 + "," +
			ATTRIBUTE_BILL_ADDRESS_LINE_3 + "," +
			ATTRIBUTE_BILL_CITY + "," +
			ATTRIBUTE_BILL_ZIP + "," +
			ATTRIBUTE_PAYMENT_TERMS + "," +
			ATTRIBUTE_PO_NOTES + "," +
			ATTRIBUTE_NOTES_SPLITED + "," +
			ATTRIBUTE_PO_NOTES_ORDER_ID + "," +
			ATTRIBUTE_PO_LINE_NOTE_ORDER_ID + "," +
			ATTRIBUTE_TRANSACTOR_ID + "," +
			ATTRIBUTE_TRANSACTOR_MAIL_BOX_ADDRESS + "," +
			ATTRIBUTE_ITEM_DESC_ORDER_ID + "," +
			ATTRIBUTE_TRANSACTOR_QUALIFIER + "," +
			ATTRIBUTE_TRADE_TERMS + "," +
			ATTRIBUTE_PAYMENT_METHOD_CODE + "," +
			ATTRIBUTE_DEFAULT_SHIPMENT_ORIGIN_STATE + "," +
			ATTRIBUTE_CUSTOMER_PO + "," +
			ATTRIBUTE_SHIP_TO_CONTACT_NAME + "," +
			ATTRIBUTE_SHIP_TO_CONTACT_PHONE + ")" +
 values (
			StringHandler.nullIfZero(dbuyViewBean.getRadianPo()) + "," +
			StringHandler.nullIfZero(dbuyViewBean.getPoLine()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getSupplierName()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getSupplierContactName()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getSupplierCountryAbbrev()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getSupplierStateAbbrev()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getSupplierAddressLine1()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getSupplierAddressLine2()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getSupplierAddressLine3()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getSupplierCity()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getSupplierZip()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getShipToCompanyId()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getShipToLocationId()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getShiptoCountryAbbrev()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getShiptoStateAbbrev()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getShiptoAddressLine1()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getShiptoAddressLine2()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getShiptoAddressLine3()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getShiptoCity()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getShiptoZip()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getBuyerName()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getBuyerEmail()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getBuyerPhone()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getMrNumber()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getSupplierShipToCode()) + "," +
			StringHandler.nullIfZero(dbuyViewBean.getNumberOfLines()) + "," +
			StringHandler.nullIfZero(dbuyViewBean.getItemId()) + "," +
			StringHandler.nullIfZero(dbuyViewBean.getQuantity()) + "," +
			StringHandler.nullIfZero(dbuyViewBean.getUnitPrice()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getItemShortDesc()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getPackaging()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getSupplierPartNo()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getEdiUom()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getSpecFlowdownName()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getSpecFlowdownType()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getCritical()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getNeedByDate()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getPoLineNote()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getEdiDocumentControlNumber()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getSupplier()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getCarrierName()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getCarrierCode()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getCarrierAccount()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getShipToId()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getRemainingShelfLifePercent()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getCarrierBillCompany()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getCarrierBillName()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getCarrierBillCountryAbbrev()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getCarrierBillStateAbbrev()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getCarrierBillAddressLine1()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getCarrierBillAddressLine2()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getCarrierBillAddressLine3()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getCarrierBillCity()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getCarrierBillZip()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getBillCountryAbbrev()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getBillStateAbbrev()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getBillAddressLine1()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getBillAddressLine2()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getBillAddressLine3()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getBillCity()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getBillZip()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getPaymentTerms()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getPoNotes()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getNotesSplited()) + "," +
			StringHandler.nullIfZero(dbuyViewBean.getPoNotesOrderId()) + "," +
			StringHandler.nullIfZero(dbuyViewBean.getPoLineNoteOrderId()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getTransactorId()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getTransactorMailBoxAddress()) + "," +
			StringHandler.nullIfZero(dbuyViewBean.getItemDescOrderId()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getTransactorQualifier()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getTradeTerms()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getPaymentMethodCode()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getDefaultShipmentOriginState()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getCustomerPo()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getShipToContactName()) + "," +
			SqlHandler.delimitString(dbuyViewBean.getShipToContactPhone()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(DbuyViewBean dbuyViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(dbuyViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(DbuyViewBean dbuyViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(dbuyViewBean.getRadianPo()) + "," +
			ATTRIBUTE_PO_LINE + "=" + 
				StringHandler.nullIfZero(dbuyViewBean.getPoLine()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getSupplierName()) + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_NAME + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getSupplierContactName()) + "," +
			ATTRIBUTE_SUPPLIER_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getSupplierCountryAbbrev()) + "," +
			ATTRIBUTE_SUPPLIER_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getSupplierStateAbbrev()) + "," +
			ATTRIBUTE_SUPPLIER_ADDRESS_LINE_1 + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getSupplierAddressLine1()) + "," +
			ATTRIBUTE_SUPPLIER_ADDRESS_LINE_2 + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getSupplierAddressLine2()) + "," +
			ATTRIBUTE_SUPPLIER_ADDRESS_LINE_3 + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getSupplierAddressLine3()) + "," +
			ATTRIBUTE_SUPPLIER_CITY + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getSupplierCity()) + "," +
			ATTRIBUTE_SUPPLIER_ZIP + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getSupplierZip()) + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getShipToCompanyId()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getShipToLocationId()) + "," +
			ATTRIBUTE_SHIPTO_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getShiptoCountryAbbrev()) + "," +
			ATTRIBUTE_SHIPTO_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getShiptoStateAbbrev()) + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_1 + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getShiptoAddressLine1()) + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_2 + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getShiptoAddressLine2()) + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_3 + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getShiptoAddressLine3()) + "," +
			ATTRIBUTE_SHIPTO_CITY + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getShiptoCity()) + "," +
			ATTRIBUTE_SHIPTO_ZIP + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getShiptoZip()) + "," +
			ATTRIBUTE_BUYER_NAME + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getBuyerName()) + "," +
			ATTRIBUTE_BUYER_EMAIL + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getBuyerEmail()) + "," +
			ATTRIBUTE_BUYER_PHONE + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getBuyerPhone()) + "," +
			ATTRIBUTE_MR_NUMBER + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getMrNumber()) + "," +
			ATTRIBUTE_SUPPLIER_SHIP_TO_CODE + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getSupplierShipToCode()) + "," +
			ATTRIBUTE_NUMBER_OF_LINES + "=" + 
				StringHandler.nullIfZero(dbuyViewBean.getNumberOfLines()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(dbuyViewBean.getItemId()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(dbuyViewBean.getQuantity()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(dbuyViewBean.getUnitPrice()) + "," +
			ATTRIBUTE_ITEM_SHORT_DESC + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getItemShortDesc()) + "," +
			ATTRIBUTE_PACKAGING + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getPackaging()) + "," +
			ATTRIBUTE_SUPPLIER_PART_NO + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getSupplierPartNo()) + "," +
			ATTRIBUTE_EDI_UOM + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getEdiUom()) + "," +
			ATTRIBUTE_SPEC_FLOWDOWN_NAME + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getSpecFlowdownName()) + "," +
			ATTRIBUTE_SPEC_FLOWDOWN_TYPE + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getSpecFlowdownType()) + "," +
			ATTRIBUTE_CRITICAL + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getCritical()) + "," +
			ATTRIBUTE_NEED_BY_DATE + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getNeedByDate()) + "," +
			ATTRIBUTE_PO_LINE_NOTE + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getPoLineNote()) + "," +
			ATTRIBUTE_EDI_DOCUMENT_CONTROL_NUMBER + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getEdiDocumentControlNumber()) + "," +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getSupplier()) + "," +
			ATTRIBUTE_CARRIER_NAME + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getCarrierName()) + "," +
			ATTRIBUTE_CARRIER_CODE + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getCarrierCode()) + "," +
			ATTRIBUTE_CARRIER_ACCOUNT + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getCarrierAccount()) + "," +
			ATTRIBUTE_SHIP_TO_ID + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getShipToId()) + "," +
			ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getRemainingShelfLifePercent()) + "," +
			ATTRIBUTE_CARRIER_BILL_COMPANY + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getCarrierBillCompany()) + "," +
			ATTRIBUTE_CARRIER_BILL_NAME + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getCarrierBillName()) + "," +
			ATTRIBUTE_CARRIER_BILL_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getCarrierBillCountryAbbrev()) + "," +
			ATTRIBUTE_CARRIER_BILL_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getCarrierBillStateAbbrev()) + "," +
			ATTRIBUTE_CARRIER_BILL_ADDRESS_LINE_1 + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getCarrierBillAddressLine1()) + "," +
			ATTRIBUTE_CARRIER_BILL_ADDRESS_LINE_2 + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getCarrierBillAddressLine2()) + "," +
			ATTRIBUTE_CARRIER_BILL_ADDRESS_LINE_3 + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getCarrierBillAddressLine3()) + "," +
			ATTRIBUTE_CARRIER_BILL_CITY + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getCarrierBillCity()) + "," +
			ATTRIBUTE_CARRIER_BILL_ZIP + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getCarrierBillZip()) + "," +
			ATTRIBUTE_BILL_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getBillCountryAbbrev()) + "," +
			ATTRIBUTE_BILL_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getBillStateAbbrev()) + "," +
			ATTRIBUTE_BILL_ADDRESS_LINE_1 + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getBillAddressLine1()) + "," +
			ATTRIBUTE_BILL_ADDRESS_LINE_2 + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getBillAddressLine2()) + "," +
			ATTRIBUTE_BILL_ADDRESS_LINE_3 + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getBillAddressLine3()) + "," +
			ATTRIBUTE_BILL_CITY + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getBillCity()) + "," +
			ATTRIBUTE_BILL_ZIP + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getBillZip()) + "," +
			ATTRIBUTE_PAYMENT_TERMS + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getPaymentTerms()) + "," +
			ATTRIBUTE_PO_NOTES + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getPoNotes()) + "," +
			ATTRIBUTE_NOTES_SPLITED + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getNotesSplited()) + "," +
			ATTRIBUTE_PO_NOTES_ORDER_ID + "=" + 
				StringHandler.nullIfZero(dbuyViewBean.getPoNotesOrderId()) + "," +
			ATTRIBUTE_PO_LINE_NOTE_ORDER_ID + "=" + 
				StringHandler.nullIfZero(dbuyViewBean.getPoLineNoteOrderId()) + "," +
			ATTRIBUTE_TRANSACTOR_ID + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getTransactorId()) + "," +
			ATTRIBUTE_TRANSACTOR_MAIL_BOX_ADDRESS + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getTransactorMailBoxAddress()) + "," +
			ATTRIBUTE_ITEM_DESC_ORDER_ID + "=" + 
				StringHandler.nullIfZero(dbuyViewBean.getItemDescOrderId()) + "," +
			ATTRIBUTE_TRANSACTOR_QUALIFIER + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getTransactorQualifier()) + "," +
			ATTRIBUTE_TRADE_TERMS + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getTradeTerms()) + "," +
			ATTRIBUTE_PAYMENT_METHOD_CODE + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getPaymentMethodCode()) + "," +
			ATTRIBUTE_DEFAULT_SHIPMENT_ORIGIN_STATE + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getDefaultShipmentOriginState()) + "," +
			ATTRIBUTE_CUSTOMER_PO + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getCustomerPo()) + "," +
			ATTRIBUTE_SHIP_TO_CONTACT_NAME + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getShipToContactName()) + "," +
			ATTRIBUTE_SHIP_TO_CONTACT_PHONE + "=" + 
				SqlHandler.delimitString(dbuyViewBean.getShipToContactPhone()) + " " +
			"where " + ATTRIBUTE_RADIAN_PO + "=" +
				StringHandler.nullIfZero(dbuyViewBean.getRadianPo());

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

		Collection dbuyViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DbuyViewBean dbuyViewBean = new DbuyViewBean();
			load(dataSetRow, dbuyViewBean);
			dbuyViewBeanColl.add(dbuyViewBean);
		}

		return dbuyViewBeanColl;
	}
}