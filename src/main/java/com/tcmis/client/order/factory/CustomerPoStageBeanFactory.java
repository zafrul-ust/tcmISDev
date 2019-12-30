package com.tcmis.client.order.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
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
import com.tcmis.client.order.beans.CustomerPoStageBean;


/******************************************************************************
 * CLASSNAME: CustomerPoStageBeanFactory <br>
 * @version: 1.0, Mar 19, 2009 <br>
 *****************************************************************************/


public class CustomerPoStageBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());
        
        private final String STATUS_IGNORE = "IGNORE";
        private final String STATUS_ERROR = "ERROR";

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CUSTOMER_PO_NO = "CUSTOMER_PO_NO";
	public String ATTRIBUTE_CUSTOMER_PO_LINE_NO = "CUSTOMER_PO_LINE_NO";
	public String ATTRIBUTE_TRANSACTION_TYPE = "TRANSACTION_TYPE";
	public String ATTRIBUTE_LINE_SEQUENCE = "LINE_SEQUENCE";
	public String ATTRIBUTE_CHANGE_ORDER_SEQUENCE = "CHANGE_ORDER_SEQUENCE";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_QUANTITY_LEFT = "QUANTITY_LEFT";
	public String ATTRIBUTE_UOM = "UOM";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_DATE_ISSUED = "DATE_ISSUED";
	public String ATTRIBUTE_HAAS_ITEM_NO = "HAAS_ITEM_NO";
	public String ATTRIBUTE_MANUFACTURER_PART_NUM = "MANUFACTURER_PART_NUM";
	public String ATTRIBUTE_ITEM_DESCRIPTION = "ITEM_DESCRIPTION";
	public String ATTRIBUTE_CUSTOMER_PO_LINE_NOTE = "CUSTOMER_PO_LINE_NOTE";
	public String ATTRIBUTE_REQUESTED_DELIVERY_DATE = "REQUESTED_DELIVERY_DATE";
	public String ATTRIBUTE_ESTIMATED_DOCK_DATE = "ESTIMATED_DOCK_DATE";
	public String ATTRIBUTE_TOTAL_AMOUNT_ON_PO = "TOTAL_AMOUNT_ON_PO";
	public String ATTRIBUTE_DATE_INSERTED = "DATE_INSERTED";
	public String ATTRIBUTE_UNIT_PRICE_CODE = "UNIT_PRICE_CODE";
	public String ATTRIBUTE_ACCEPTANCE_CODE = "ACCEPTANCE_CODE";
	public String ATTRIBUTE_CHANGE_TYPE_CODE = "CHANGE_TYPE_CODE";
	public String ATTRIBUTE_CHANGE_SUB_TYPE_CODE = "CHANGE_SUB_TYPE_CODE";
	public String ATTRIBUTE_BUYER_PHONE = "BUYER_PHONE";
	public String ATTRIBUTE_BUYER_FAX = "BUYER_FAX";
	public String ATTRIBUTE_DOCUMENT_CONTROL_NUMBER = "DOCUMENT_CONTROL_NUMBER";
	public String ATTRIBUTE_TRANSPORT = "TRANSPORT";
	public String ATTRIBUTE_TRANSPORTER_ACCOUNT = "TRANSPORTER_ACCOUNT";
	public String ATTRIBUTE_TRADING_PARTNER = "TRADING_PARTNER";
	public String ATTRIBUTE_TRADING_PARTNER_ID = "TRADING_PARTNER_ID";
	public String ATTRIBUTE_CUSTOMER_HAAS_CONTRACT_ID = "CUSTOMER_HAAS_CONTRACT_ID";
	public String ATTRIBUTE_CUSTOMER_HAAS_ACCOUNT_NO = "CUSTOMER_HAAS_ACCOUNT_NO";
	public String ATTRIBUTE_CUSTOMER_PAYMENT_TERMS = "CUSTOMER_PAYMENT_TERMS";
	public String ATTRIBUTE_CUSTOMER_PO_NOTE = "CUSTOMER_PO_NOTE";
	public String ATTRIBUTE_BUYER_NAME_ON_PO = "BUYER_NAME_ON_PO";
	public String ATTRIBUTE_BUYER_ADDRESS1 = "BUYER_ADDRESS1";
	public String ATTRIBUTE_BUYER_ADDRESS2 = "BUYER_ADDRESS2";
	public String ATTRIBUTE_BUYER_CITY = "BUYER_CITY";
	public String ATTRIBUTE_BUYER_STATE = "BUYER_STATE";
	public String ATTRIBUTE_BUYER_ZIP = "BUYER_ZIP";
	public String ATTRIBUTE_BUYER_COUNTRY = "BUYER_COUNTRY";
	public String ATTRIBUTE_SELLER_NAME_ON_PO = "SELLER_NAME_ON_PO";
	public String ATTRIBUTE_SELLER_ID_ON_PO = "SELLER_ID_ON_PO";
	public String ATTRIBUTE_SELLER_ADDRESS1 = "SELLER_ADDRESS1";
	public String ATTRIBUTE_SELLER_ADDRESS2 = "SELLER_ADDRESS2";
	public String ATTRIBUTE_SELLER_CITY = "SELLER_CITY";
	public String ATTRIBUTE_SELLER_STATE = "SELLER_STATE";
	public String ATTRIBUTE_SELLER_ZIP = "SELLER_ZIP";
	public String ATTRIBUTE_SELLER_COUNTRY = "SELLER_COUNTRY";
	public String ATTRIBUTE_SHIPTO_PARTY_NAME = "SHIPTO_PARTY_NAME";
	public String ATTRIBUTE_SHIPTO_PARTY_ID = "SHIPTO_PARTY_ID";
	public String ATTRIBUTE_SHIPTO_ADDRESS1 = "SHIPTO_ADDRESS1";
	public String ATTRIBUTE_SHIPTO_ADDRESS2 = "SHIPTO_ADDRESS2";
	public String ATTRIBUTE_SHIPTO_ADDRESS3 = "SHIPTO_ADDRESS3";
	public String ATTRIBUTE_SHIPTO_CITY = "SHIPTO_CITY";
	public String ATTRIBUTE_SHIPTO_STATE = "SHIPTO_STATE";
	public String ATTRIBUTE_SHIPTO_ZIP = "SHIPTO_ZIP";
	public String ATTRIBUTE_SHIPTO_COUNTRY = "SHIPTO_COUNTRY";
	public String ATTRIBUTE_BILLTO_PARTY = "BILLTO_PARTY";
	public String ATTRIBUTE_BILLTO_PARTY_ID = "BILLTO_PARTY_ID";
	public String ATTRIBUTE_BILLTO_NAME2 = "BILLTO_NAME2";
	public String ATTRIBUTE_BILLTO_ADDRESS1 = "BILLTO_ADDRESS1";
	public String ATTRIBUTE_BILLTO_ADDRESS2 = "BILLTO_ADDRESS2";
	public String ATTRIBUTE_BILLTO_ADDRESS3 = "BILLTO_ADDRESS3";
	public String ATTRIBUTE_BILLTO_CITY = "BILLTO_CITY";
	public String ATTRIBUTE_BILLTO_STATE = "BILLTO_STATE";
	public String ATTRIBUTE_BILLTO_ZIP = "BILLTO_ZIP";
	public String ATTRIBUTE_BILLTO_COUNTRY = "BILLTO_COUNTRY";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_SUGGESTED_DBUY_CONTRACT_ID = "SUGGESTED_DBUY_CONTRACT_ID";
	public String ATTRIBUTE_SUGGESTED_QUANTITY = "SUGGESTED_QUANTITY";
	public String ATTRIBUTE_ACCOUNT_SYS_ID = "ACCOUNT_SYS_ID";
	public String ATTRIBUTE_CHARGE_TYPE = "CHARGE_TYPE";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_INTERCHANGE_CONTROL_NUMBER = "INTERCHANGE_CONTROL_NUMBER";
	public String ATTRIBUTE_ERROR_DETAIL = "ERROR_DETAIL";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_PO_LINE_AMENDMENT = "PO_LINE_AMENDMENT";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_STATUS_BEFORE_ERROR = "STATUS_BEFORE_ERROR";
	public String ATTRIBUTE_SOURCE = "SOURCE";
	public String ATTRIBUTE_CURRENT_TRANSACTION = "CURRENT_TRANSACTION";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_LOAD_ID = "LOAD_ID";
	public String ATTRIBUTE_LOAD_LINE = "LOAD_LINE";
	public String ATTRIBUTE_PROCESSING_COMMENTS = "PROCESSING_COMMENTS";
	public String ATTRIBUTE_PO_TYPE_CODE = "PO_TYPE_CODE";
	public String ATTRIBUTE_LAST_UPDATED_BY = "LAST_UPDATED_BY";
	public String ATTRIBUTE_LAST_UPDATED_ON = "LAST_UPDATED_ON";
	public String ATTRIBUTE_BUYER_PARTY_NAME = "BUYER_PARTY_NAME";
	public String ATTRIBUTE_SELLER_PARTY_NAME = "SELLER_PARTY_NAME";
	public String ATTRIBUTE_SHIPTO_CONTACT_NAME = "SHIPTO_CONTACT_NAME";
	public String ATTRIBUTE_PRICE_BASIS_UOM = "PRICE_BASIS_UOM";
	public String ATTRIBUTE_PRICE_BASIS_QUANTITY = "PRICE_BASIS_QUANTITY";
	public String ATTRIBUTE_REQUESTOR_NAME = "REQUESTOR_NAME";
	public String ATTRIBUTE_PARTIAL_SHIPMENT = "PARTIAL_SHIPMENT";
	public String ATTRIBUTE_BUYER_REGION = "BUYER_REGION";
	public String ATTRIBUTE_SELLER_REGION = "SELLER_REGION";
	public String ATTRIBUTE_BILLTO_REGION = "BILLTO_REGION";
	public String ATTRIBUTE_SHIPTO_REGION = "SHIPTO_REGION";
	public String ATTRIBUTE_CARRIER = "CARRIER";
	public String ATTRIBUTE_PO_UPDATE_DATE = "PO_UPDATE_DATE";
	public String ATTRIBUTE_FREIGHT_ON_BOARD = "FREIGHT_ON_BOARD";
	public String ATTRIBUTE_PAYMENT_TERMS = "PAYMENT_TERMS";
	public String ATTRIBUTE_FREIGHT_ON_BOARD_NOTES = "FREIGHT_ON_BOARD_NOTES";
	public String ATTRIBUTE_PAYMENT_TERMS_NOTES = "PAYMENT_TERMS_NOTES";
	public String ATTRIBUTE_FROM_LOAD_ID = "FROM_LOAD_ID";
	public String ATTRIBUTE_FROM_LOAD_LINE = "FROM_LOAD_LINE";
	public String ATTRIBUTE_CAT_PART_NO_ORIG = "CAT_PART_NO_ORIG";
	public String ATTRIBUTE_CHARGE_NUMBER1 = "CHARGE_NUMBER1";
	public String ATTRIBUTE_MULTIPLE_CLEAR_FLAG = "MULTIPLE_CLEAR_FLAG";
	public String ATTRIBUTE_RELEASED = "RELEASED";
	public String ATTRIBUTE_REQUISITION_NUMBER = "REQUISITION_NUMBER";
	public String ATTRIBUTE_TOTAL_PO_NOT_TO_EXCEED_AMOUNT = "TOTAL_PO_NOT_TO_EXCEED_AMOUNT";
	public String ATTRIBUTE_RELEASE_NUM = "RELEASE_NUM";
	public String ATTRIBUTE_PAYOFFICE_NAME = "PAYOFFICE_NAME";
	public String ATTRIBUTE_PAYOFFICE_ID = "PAYOFFICE_ID";
	public String ATTRIBUTE_BUYER_ID_ON_PO = "BUYER_ID_ON_PO";
	public String ATTRIBUTE_SHIP_TO_DODAAC = "SHIP_TO_DODAAC";
	public String ATTRIBUTE_MARK_FOR_DODAAC = "MARK_FOR_DODAAC";
	public String ATTRIBUTE_ACCT_DEPT = "ACCT_DEPT";
	public String ATTRIBUTE_ACCT_FUNDS_SUBDIV = "ACCT_FUNDS_SUBDIV";
	public String ATTRIBUTE_ACCT_FUNDS_TYPE = "ACCT_FUNDS_TYPE";
	public String ATTRIBUTE_ACCT_INSTALLATION_NUM = "ACCT_INSTALLATION_NUM";
	public String ATTRIBUTE_ACCT_OBJECT_CLASS = "ACCT_OBJECT_CLASS";
	public String ATTRIBUTE_ACRN = "ACRN";
	public String ATTRIBUTE_ALLOTMENT_SERIAL_NUM = "ALLOTMENT_SERIAL_NUM";
	public String ATTRIBUTE_BUYER_EMAIL = "BUYER_EMAIL";
	public String ATTRIBUTE_CONTRACT_OFFICE = "CONTRACT_OFFICE";
	public String ATTRIBUTE_CONTRACT_OFFICER_EMAIL = "CONTRACT_OFFICER_EMAIL";
	public String ATTRIBUTE_CONTRACT_OFFICER_NAME = "CONTRACT_OFFICER_NAME";
	public String ATTRIBUTE_CONTRACT_OFFICER_PHONE = "CONTRACT_OFFICER_PHONE";
	public String ATTRIBUTE_CONTRACT_OFFICE_CODE = "CONTRACT_OFFICE_CODE";
	public String ATTRIBUTE_DELIVERY_TYPE_CODE = "DELIVERY_TYPE_CODE";
	public String ATTRIBUTE_DPAS_RATING = "DPAS_RATING";
	public String ATTRIBUTE_LEAD_TIME = "LEAD_TIME";
	public String ATTRIBUTE_LEAD_TIME_CODE = "LEAD_TIME_CODE";
	public String ATTRIBUTE_MANUFACTURER_CAGE_CODE = "MANUFACTURER_CAGE_CODE";
	public String ATTRIBUTE_MARK_FOR_ADDRESS1 = "MARK_FOR_ADDRESS1";
	public String ATTRIBUTE_MARK_FOR_ADDRESS2 = "MARK_FOR_ADDRESS2";
	public String ATTRIBUTE_MARK_FOR_ADDRESS3 = "MARK_FOR_ADDRESS3";
	public String ATTRIBUTE_MARK_FOR_CITY = "MARK_FOR_CITY";
	public String ATTRIBUTE_MARK_FOR_COUNTRY = "MARK_FOR_COUNTRY";
	public String ATTRIBUTE_MARK_FOR_PARTY_ID = "MARK_FOR_PARTY_ID";
	public String ATTRIBUTE_MARK_FOR_PARTY_NAME = "MARK_FOR_PARTY_NAME";
	public String ATTRIBUTE_MARK_FOR_STATE = "MARK_FOR_STATE";
	public String ATTRIBUTE_MARK_FOR_ZIP = "MARK_FOR_ZIP";
	public String ATTRIBUTE_MFG_PLANT_ID = "MFG_PLANT_ID";
	public String ATTRIBUTE_MFG_PLANT_NAME = "MFG_PLANT_NAME";
	public String ATTRIBUTE_MILSTRIP_CODE = "MILSTRIP_CODE";
	public String ATTRIBUTE_MODEL_YEAR_NUM = "MODEL_YEAR_NUM";
	public String ATTRIBUTE_ORIGINAL_DATE_ISSUED = "ORIGINAL_DATE_ISSUED";
	public String ATTRIBUTE_ORIGINAL_QTY = "ORIGINAL_QTY";
	public String ATTRIBUTE_ORIGINAL_UOM = "ORIGINAL_UOM";
	public String ATTRIBUTE_PART_SHORT_DESC = "PART_SHORT_DESC";
	public String ATTRIBUTE_PAYER_ADDR = "PAYER_ADDR";
	public String ATTRIBUTE_PAYER_CODE = "PAYER_CODE";
	public String ATTRIBUTE_PAYER_NAME = "PAYER_NAME";
	public String ATTRIBUTE_POSTAL_SHIPTO_ADDR1 = "POSTAL_SHIPTO_ADDR1";
	public String ATTRIBUTE_POSTAL_SHIPTO_ADDR2 = "POSTAL_SHIPTO_ADDR2";
	public String ATTRIBUTE_POSTAL_SHIPTO_ADDR3 = "POSTAL_SHIPTO_ADDR3";
	public String ATTRIBUTE_POSTAL_SHIPTO_CITY = "POSTAL_SHIPTO_CITY";
	public String ATTRIBUTE_POSTAL_SHIPTO_CODE = "POSTAL_SHIPTO_CODE";
	public String ATTRIBUTE_POSTAL_SHIPTO_NAME = "POSTAL_SHIPTO_NAME";
	public String ATTRIBUTE_POSTAL_SHIPTO_STATE = "POSTAL_SHIPTO_STATE";
	public String ATTRIBUTE_POSTAL_SHIPTO_ZIP = "POSTAL_SHIPTO_ZIP";
	public String ATTRIBUTE_PO_SUFFIX = "PO_SUFFIX";
	public String ATTRIBUTE_PRODUCT_CHANGE_NOTICE_NUM = "PRODUCT_CHANGE_NOTICE_NUM";
	public String ATTRIBUTE_PRODUCT_GROUP = "PRODUCT_GROUP";
	public String ATTRIBUTE_PURCHASE_REQUISITION_NUM = "PURCHASE_REQUISITION_NUM";
	public String ATTRIBUTE_RECEIVING_LOCATION_ID = "RECEIVING_LOCATION_ID";
	public String ATTRIBUTE_RECEIVING_LOCATION_NAME = "RECEIVING_LOCATION_NAME";
	public String ATTRIBUTE_RECEIVING_PARTY = "RECEIVING_PARTY";
	public String ATTRIBUTE_RECEIVING_PARTY_CODE = "RECEIVING_PARTY_CODE";
	public String ATTRIBUTE_REQUESTED_DELIVERY_DATE_HEADER = "REQUESTED_DELIVERY_DATE_HEADER";
	public String ATTRIBUTE_SCHEDULE_NOTES = "SCHEDULE_NOTES";
	public String ATTRIBUTE_SCHEDULE_QUANTITY = "SCHEDULE_QUANTITY";
	public String ATTRIBUTE_SCHEDULE_UOM = "SCHEDULE_UOM";
	public String ATTRIBUTE_SHIPPING_NOTES = "SHIPPING_NOTES";
	public String ATTRIBUTE_SHIP_NOTICE_ADDRESS1 = "SHIP_NOTICE_ADDRESS1";
	public String ATTRIBUTE_SHIP_NOTICE_ADDRESS2 = "SHIP_NOTICE_ADDRESS2";
	public String ATTRIBUTE_SHIP_NOTICE_ADDRESS3 = "SHIP_NOTICE_ADDRESS3";
	public String ATTRIBUTE_SHIP_NOTICE_CITY = "SHIP_NOTICE_CITY";
	public String ATTRIBUTE_SHIP_NOTICE_PARTY_ID = "SHIP_NOTICE_PARTY_ID";
	public String ATTRIBUTE_SHIP_NOTICE_PARTY_NAME = "SHIP_NOTICE_PARTY_NAME";
	public String ATTRIBUTE_SHIP_NOTICE_STATE = "SHIP_NOTICE_STATE";
	public String ATTRIBUTE_SHIP_NOTICE_ZIP = "SHIP_NOTICE_ZIP";
	public String ATTRIBUTE_SUPPLEMENTARY_ADDR_CODE = "SUPPLEMENTARY_ADDR_CODE";
	public String ATTRIBUTE_TOTAL_LINE_AMT = "TOTAL_LINE_AMT";
	public String ATTRIBUTE_TRANSPORTATION_PRIORITY = "TRANSPORTATION_PRIORITY";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_SHIP_VIA_LOCATION_ID = "SHIP_VIA_LOCATION_ID";
	public String ATTRIBUTE_SKIP_DODAAC_ADDRESS_CHECK = "SKIP_DODAAC_ADDRESS_CHECK";
	public String ATTRIBUTE_BUYER_ADDITIONAL_NAME = "BUYER_ADDITIONAL_NAME";
	public String ATTRIBUTE_CONTRACT_OFFICE_ALTERNATE_NAME = "CONTRACT_OFFICE_ALTERNATE_NAME";
	public String ATTRIBUTE_CONTRACT_OFFICE_ADDRESS1 = "CONTRACT_OFFICE_ADDRESS1";
	public String ATTRIBUTE_CONTRACT_OFFICE_ADDRESS2 = "CONTRACT_OFFICE_ADDRESS2";
	public String ATTRIBUTE_CONTRACT_OFFICE_CITY = "CONTRACT_OFFICE_CITY";
	public String ATTRIBUTE_CONTRACT_OFFICE_STATE = "CONTRACT_OFFICE_STATE";
	public String ATTRIBUTE_CONTRACT_OFFICE_ZIP = "CONTRACT_OFFICE_ZIP";
	public String ATTRIBUTE_CONTRACT_OFFICE_COUNTRY = "CONTRACT_OFFICE_COUNTRY";
	public String ATTRIBUTE_PAYOFFICE_ADDITIONAL_NAME = "PAYOFFICE_ADDITIONAL_NAME";
	public String ATTRIBUTE_PAYOFFICE_ADDRESS1 = "PAYOFFICE_ADDRESS1";
	public String ATTRIBUTE_PAYOFFICE_ADDRESS2 = "PAYOFFICE_ADDRESS2";
	public String ATTRIBUTE_PAYOFFICE_CITY = "PAYOFFICE_CITY";
	public String ATTRIBUTE_PAYOFFICE_STATE = "PAYOFFICE_STATE";
	public String ATTRIBUTE_PAYOFFICE_ZIP = "PAYOFFICE_ZIP";
	public String ATTRIBUTE_PAYOFFICE_COUNTRY = "PAYOFFICE_COUNTRY";
	public String ATTRIBUTE_PROJECT_CODE = "PROJECT_CODE";
	public String ATTRIBUTE_PRIORITY_RATING = "PRIORITY_RATING";
	public String ATTRIBUTE_FMS_CASE_NUM = "FMS_CASE_NUM";
	public String ATTRIBUTE_PORT_OF_EMBARKATION = "PORT_OF_EMBARKATION";
	public String ATTRIBUTE_PORT_OF_DEBARKATION = "PORT_OF_DEBARKATION";
	public String ATTRIBUTE_TAC = "TAC";
	public String ATTRIBUTE_TRANSACTION_REF_NUM = "TRANSACTION_REF_NUM";
	public String ATTRIBUTE_TRANSPORTATION_CONTROL_NUM = "TRANSPORTATION_CONTROL_NUM";
	public String ATTRIBUTE_ADDRESS_CHANGE_REQUEST_ID = "ADDRESS_CHANGE_REQUEST_ID";

	//table name
	public String TABLE = "CUSTOMER_PO_STAGE";


	//constructor
	public CustomerPoStageBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("customerPoNo")) {
			return ATTRIBUTE_CUSTOMER_PO_NO;
		}
		else if(attributeName.equals("customerPoLineNo")) {
			return ATTRIBUTE_CUSTOMER_PO_LINE_NO;
		}
		else if(attributeName.equals("transactionType")) {
			return ATTRIBUTE_TRANSACTION_TYPE;
		}
		else if(attributeName.equals("lineSequence")) {
			return ATTRIBUTE_LINE_SEQUENCE;
		}
		else if(attributeName.equals("changeOrderSequence")) {
			return ATTRIBUTE_CHANGE_ORDER_SEQUENCE;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("quantityLeft")) {
			return ATTRIBUTE_QUANTITY_LEFT;
		}
		else if(attributeName.equals("uom")) {
			return ATTRIBUTE_UOM;
		}
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
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
		else if(attributeName.equals("dateIssued")) {
			return ATTRIBUTE_DATE_ISSUED;
		}
		else if(attributeName.equals("haasItemNo")) {
			return ATTRIBUTE_HAAS_ITEM_NO;
		}
		else if(attributeName.equals("manufacturerPartNum")) {
			return ATTRIBUTE_MANUFACTURER_PART_NUM;
		}
		else if(attributeName.equals("itemDescription")) {
			return ATTRIBUTE_ITEM_DESCRIPTION;
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
		else if(attributeName.equals("totalAmountOnPo")) {
			return ATTRIBUTE_TOTAL_AMOUNT_ON_PO;
		}
		else if(attributeName.equals("dateInserted")) {
			return ATTRIBUTE_DATE_INSERTED;
		}
		else if(attributeName.equals("unitPriceCode")) {
			return ATTRIBUTE_UNIT_PRICE_CODE;
		}
		else if(attributeName.equals("acceptanceCode")) {
			return ATTRIBUTE_ACCEPTANCE_CODE;
		}
		else if(attributeName.equals("changeTypeCode")) {
			return ATTRIBUTE_CHANGE_TYPE_CODE;
		}
		else if(attributeName.equals("changeSubTypeCode")) {
			return ATTRIBUTE_CHANGE_SUB_TYPE_CODE;
		}
		else if(attributeName.equals("buyerPhone")) {
			return ATTRIBUTE_BUYER_PHONE;
		}
		else if(attributeName.equals("buyerFax")) {
			return ATTRIBUTE_BUYER_FAX;
		}
		else if(attributeName.equals("documentControlNumber")) {
			return ATTRIBUTE_DOCUMENT_CONTROL_NUMBER;
		}
		else if(attributeName.equals("transport")) {
			return ATTRIBUTE_TRANSPORT;
		}
		else if(attributeName.equals("transporterAccount")) {
			return ATTRIBUTE_TRANSPORTER_ACCOUNT;
		}
		else if(attributeName.equals("tradingPartner")) {
			return ATTRIBUTE_TRADING_PARTNER;
		}
		else if(attributeName.equals("tradingPartnerId")) {
			return ATTRIBUTE_TRADING_PARTNER_ID;
		}
		else if(attributeName.equals("customerHaasContractId")) {
			return ATTRIBUTE_CUSTOMER_HAAS_CONTRACT_ID;
		}
		else if(attributeName.equals("customerHaasAccountNo")) {
			return ATTRIBUTE_CUSTOMER_HAAS_ACCOUNT_NO;
		}
		else if(attributeName.equals("customerPaymentTerms")) {
			return ATTRIBUTE_CUSTOMER_PAYMENT_TERMS;
		}
		else if(attributeName.equals("customerPoNote")) {
			return ATTRIBUTE_CUSTOMER_PO_NOTE;
		}
		else if(attributeName.equals("buyerNameOnPo")) {
			return ATTRIBUTE_BUYER_NAME_ON_PO;
		}
		else if(attributeName.equals("buyerAddress1")) {
			return ATTRIBUTE_BUYER_ADDRESS1;
		}
		else if(attributeName.equals("buyerAddress2")) {
			return ATTRIBUTE_BUYER_ADDRESS2;
		}
		else if(attributeName.equals("buyerCity")) {
			return ATTRIBUTE_BUYER_CITY;
		}
		else if(attributeName.equals("buyerState")) {
			return ATTRIBUTE_BUYER_STATE;
		}
		else if(attributeName.equals("buyerZip")) {
			return ATTRIBUTE_BUYER_ZIP;
		}
		else if(attributeName.equals("buyerCountry")) {
			return ATTRIBUTE_BUYER_COUNTRY;
		}
		else if(attributeName.equals("sellerNameOnPo")) {
			return ATTRIBUTE_SELLER_NAME_ON_PO;
		}
		else if(attributeName.equals("sellerIdOnPo")) {
			return ATTRIBUTE_SELLER_ID_ON_PO;
		}
		else if(attributeName.equals("sellerAddress1")) {
			return ATTRIBUTE_SELLER_ADDRESS1;
		}
		else if(attributeName.equals("sellerAddress2")) {
			return ATTRIBUTE_SELLER_ADDRESS2;
		}
		else if(attributeName.equals("sellerCity")) {
			return ATTRIBUTE_SELLER_CITY;
		}
		else if(attributeName.equals("sellerState")) {
			return ATTRIBUTE_SELLER_STATE;
		}
		else if(attributeName.equals("sellerZip")) {
			return ATTRIBUTE_SELLER_ZIP;
		}
		else if(attributeName.equals("sellerCountry")) {
			return ATTRIBUTE_SELLER_COUNTRY;
		}
		else if(attributeName.equals("shiptoPartyName")) {
			return ATTRIBUTE_SHIPTO_PARTY_NAME;
		}
		else if(attributeName.equals("shiptoPartyId")) {
			return ATTRIBUTE_SHIPTO_PARTY_ID;
		}
		else if(attributeName.equals("shiptoAddress1")) {
			return ATTRIBUTE_SHIPTO_ADDRESS1;
		}
		else if(attributeName.equals("shiptoAddress2")) {
			return ATTRIBUTE_SHIPTO_ADDRESS2;
		}
		else if(attributeName.equals("shiptoAddress3")) {
			return ATTRIBUTE_SHIPTO_ADDRESS3;
		}
		else if(attributeName.equals("shiptoCity")) {
			return ATTRIBUTE_SHIPTO_CITY;
		}
		else if(attributeName.equals("shiptoState")) {
			return ATTRIBUTE_SHIPTO_STATE;
		}
		else if(attributeName.equals("shiptoZip")) {
			return ATTRIBUTE_SHIPTO_ZIP;
		}
		else if(attributeName.equals("shiptoCountry")) {
			return ATTRIBUTE_SHIPTO_COUNTRY;
		}
		else if(attributeName.equals("billtoParty")) {
			return ATTRIBUTE_BILLTO_PARTY;
		}
		else if(attributeName.equals("billtoPartyId")) {
			return ATTRIBUTE_BILLTO_PARTY_ID;
		}
		else if(attributeName.equals("billtoName2")) {
			return ATTRIBUTE_BILLTO_NAME2;
		}
		else if(attributeName.equals("billtoAddress1")) {
			return ATTRIBUTE_BILLTO_ADDRESS1;
		}
		else if(attributeName.equals("billtoAddress2")) {
			return ATTRIBUTE_BILLTO_ADDRESS2;
		}
		else if(attributeName.equals("billtoAddress3")) {
			return ATTRIBUTE_BILLTO_ADDRESS3;
		}
		else if(attributeName.equals("billtoCity")) {
			return ATTRIBUTE_BILLTO_CITY;
		}
		else if(attributeName.equals("billtoState")) {
			return ATTRIBUTE_BILLTO_STATE;
		}
		else if(attributeName.equals("billtoZip")) {
			return ATTRIBUTE_BILLTO_ZIP;
		}
		else if(attributeName.equals("billtoCountry")) {
			return ATTRIBUTE_BILLTO_COUNTRY;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("suggestedDbuyContractId")) {
			return ATTRIBUTE_SUGGESTED_DBUY_CONTRACT_ID;
		}
		else if(attributeName.equals("suggestedQuantity")) {
			return ATTRIBUTE_SUGGESTED_QUANTITY;
		}
		else if(attributeName.equals("accountSysId")) {
			return ATTRIBUTE_ACCOUNT_SYS_ID;
		}
		else if(attributeName.equals("chargeType")) {
			return ATTRIBUTE_CHARGE_TYPE;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("interchangeControlNumber")) {
			return ATTRIBUTE_INTERCHANGE_CONTROL_NUMBER;
		}
		else if(attributeName.equals("errorDetail")) {
			return ATTRIBUTE_ERROR_DETAIL;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("poLineAmendment")) {
			return ATTRIBUTE_PO_LINE_AMENDMENT;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("statusBeforeError")) {
			return ATTRIBUTE_STATUS_BEFORE_ERROR;
		}
		else if(attributeName.equals("source")) {
			return ATTRIBUTE_SOURCE;
		}
		else if(attributeName.equals("currentTransaction")) {
			return ATTRIBUTE_CURRENT_TRANSACTION;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("loadId")) {
			return ATTRIBUTE_LOAD_ID;
		}
		else if(attributeName.equals("loadLine")) {
			return ATTRIBUTE_LOAD_LINE;
		}
		else if(attributeName.equals("processingComments")) {
			return ATTRIBUTE_PROCESSING_COMMENTS;
		}
		else if(attributeName.equals("poTypeCode")) {
			return ATTRIBUTE_PO_TYPE_CODE;
		}
		else if(attributeName.equals("lastUpdatedBy")) {
			return ATTRIBUTE_LAST_UPDATED_BY;
		}
		else if(attributeName.equals("lastUpdatedOn")) {
			return ATTRIBUTE_LAST_UPDATED_ON;
		}
		else if(attributeName.equals("buyerPartyName")) {
			return ATTRIBUTE_BUYER_PARTY_NAME;
		}
		else if(attributeName.equals("sellerPartyName")) {
			return ATTRIBUTE_SELLER_PARTY_NAME;
		}
		else if(attributeName.equals("shiptoContactName")) {
			return ATTRIBUTE_SHIPTO_CONTACT_NAME;
		}
		else if(attributeName.equals("priceBasisUom")) {
			return ATTRIBUTE_PRICE_BASIS_UOM;
		}
		else if(attributeName.equals("priceBasisQuantity")) {
			return ATTRIBUTE_PRICE_BASIS_QUANTITY;
		}
		else if(attributeName.equals("requestorName")) {
			return ATTRIBUTE_REQUESTOR_NAME;
		}
		else if(attributeName.equals("partialShipment")) {
			return ATTRIBUTE_PARTIAL_SHIPMENT;
		}
		else if(attributeName.equals("buyerRegion")) {
			return ATTRIBUTE_BUYER_REGION;
		}
		else if(attributeName.equals("sellerRegion")) {
			return ATTRIBUTE_SELLER_REGION;
		}
		else if(attributeName.equals("billtoRegion")) {
			return ATTRIBUTE_BILLTO_REGION;
		}
		else if(attributeName.equals("shiptoRegion")) {
			return ATTRIBUTE_SHIPTO_REGION;
		}
		else if(attributeName.equals("carrier")) {
			return ATTRIBUTE_CARRIER;
		}
		else if(attributeName.equals("poUpdateDate")) {
			return ATTRIBUTE_PO_UPDATE_DATE;
		}
		else if(attributeName.equals("freightOnBoard")) {
			return ATTRIBUTE_FREIGHT_ON_BOARD;
		}
		else if(attributeName.equals("paymentTerms")) {
			return ATTRIBUTE_PAYMENT_TERMS;
		}
		else if(attributeName.equals("freightOnBoardNotes")) {
			return ATTRIBUTE_FREIGHT_ON_BOARD_NOTES;
		}
		else if(attributeName.equals("paymentTermsNotes")) {
			return ATTRIBUTE_PAYMENT_TERMS_NOTES;
		}
		else if(attributeName.equals("fromLoadId")) {
			return ATTRIBUTE_FROM_LOAD_ID;
		}
		else if(attributeName.equals("fromLoadLine")) {
			return ATTRIBUTE_FROM_LOAD_LINE;
		}
		else if(attributeName.equals("catPartNoOrig")) {
			return ATTRIBUTE_CAT_PART_NO_ORIG;
		}
		else if(attributeName.equals("chargeNumber1")) {
			return ATTRIBUTE_CHARGE_NUMBER1;
		}
		else if(attributeName.equals("multipleClearFlag")) {
			return ATTRIBUTE_MULTIPLE_CLEAR_FLAG;
		}
		else if(attributeName.equals("released")) {
			return ATTRIBUTE_RELEASED;
		}
		else if(attributeName.equals("requisitionNumber")) {
			return ATTRIBUTE_REQUISITION_NUMBER;
		}
		else if(attributeName.equals("totalPoNotToExceedAmount")) {
			return ATTRIBUTE_TOTAL_PO_NOT_TO_EXCEED_AMOUNT;
		}
		else if(attributeName.equals("releaseNum")) {
			return ATTRIBUTE_RELEASE_NUM;
		}
		else if(attributeName.equals("payofficeName")) {
			return ATTRIBUTE_PAYOFFICE_NAME;
		}
		else if(attributeName.equals("payofficeId")) {
			return ATTRIBUTE_PAYOFFICE_ID;
		}
		else if(attributeName.equals("buyerIdOnPo")) {
			return ATTRIBUTE_BUYER_ID_ON_PO;
		}
		else if(attributeName.equals("shipToDodaac")) {
			return ATTRIBUTE_SHIP_TO_DODAAC;
		}
		else if(attributeName.equals("markForDodaac")) {
			return ATTRIBUTE_MARK_FOR_DODAAC;
		}
		else if(attributeName.equals("acctDept")) {
			return ATTRIBUTE_ACCT_DEPT;
		}
		else if(attributeName.equals("acctFundsSubdiv")) {
			return ATTRIBUTE_ACCT_FUNDS_SUBDIV;
		}
		else if(attributeName.equals("acctFundsType")) {
			return ATTRIBUTE_ACCT_FUNDS_TYPE;
		}
		else if(attributeName.equals("acctInstallationNum")) {
			return ATTRIBUTE_ACCT_INSTALLATION_NUM;
		}
		else if(attributeName.equals("acctObjectClass")) {
			return ATTRIBUTE_ACCT_OBJECT_CLASS;
		}
		else if(attributeName.equals("acrn")) {
			return ATTRIBUTE_ACRN;
		}
		else if(attributeName.equals("allotmentSerialNum")) {
			return ATTRIBUTE_ALLOTMENT_SERIAL_NUM;
		}
		else if(attributeName.equals("buyerEmail")) {
			return ATTRIBUTE_BUYER_EMAIL;
		}
		else if(attributeName.equals("contractOffice")) {
			return ATTRIBUTE_CONTRACT_OFFICE;
		}
		else if(attributeName.equals("contractOfficerEmail")) {
			return ATTRIBUTE_CONTRACT_OFFICER_EMAIL;
		}
		else if(attributeName.equals("contractOfficerName")) {
			return ATTRIBUTE_CONTRACT_OFFICER_NAME;
		}
		else if(attributeName.equals("contractOfficerPhone")) {
			return ATTRIBUTE_CONTRACT_OFFICER_PHONE;
		}
		else if(attributeName.equals("contractOfficeCode")) {
			return ATTRIBUTE_CONTRACT_OFFICE_CODE;
		}
		else if(attributeName.equals("deliveryTypeCode")) {
			return ATTRIBUTE_DELIVERY_TYPE_CODE;
		}
		else if(attributeName.equals("dpasRating")) {
			return ATTRIBUTE_DPAS_RATING;
		}
		else if(attributeName.equals("leadTime")) {
			return ATTRIBUTE_LEAD_TIME;
		}
		else if(attributeName.equals("leadTimeCode")) {
			return ATTRIBUTE_LEAD_TIME_CODE;
		}
		else if(attributeName.equals("manufacturerCageCode")) {
			return ATTRIBUTE_MANUFACTURER_CAGE_CODE;
		}
		else if(attributeName.equals("markForAddress1")) {
			return ATTRIBUTE_MARK_FOR_ADDRESS1;
		}
		else if(attributeName.equals("markForAddress2")) {
			return ATTRIBUTE_MARK_FOR_ADDRESS2;
		}
		else if(attributeName.equals("markForAddress3")) {
			return ATTRIBUTE_MARK_FOR_ADDRESS3;
		}
		else if(attributeName.equals("markForCity")) {
			return ATTRIBUTE_MARK_FOR_CITY;
		}
		else if(attributeName.equals("markForCountry")) {
			return ATTRIBUTE_MARK_FOR_COUNTRY;
		}
		else if(attributeName.equals("markForPartyId")) {
			return ATTRIBUTE_MARK_FOR_PARTY_ID;
		}
		else if(attributeName.equals("markForPartyName")) {
			return ATTRIBUTE_MARK_FOR_PARTY_NAME;
		}
		else if(attributeName.equals("markForState")) {
			return ATTRIBUTE_MARK_FOR_STATE;
		}
		else if(attributeName.equals("markForZip")) {
			return ATTRIBUTE_MARK_FOR_ZIP;
		}
		else if(attributeName.equals("mfgPlantId")) {
			return ATTRIBUTE_MFG_PLANT_ID;
		}
		else if(attributeName.equals("mfgPlantName")) {
			return ATTRIBUTE_MFG_PLANT_NAME;
		}
		else if(attributeName.equals("milstripCode")) {
			return ATTRIBUTE_MILSTRIP_CODE;
		}
		else if(attributeName.equals("modelYearNum")) {
			return ATTRIBUTE_MODEL_YEAR_NUM;
		}
		else if(attributeName.equals("originalDateIssued")) {
			return ATTRIBUTE_ORIGINAL_DATE_ISSUED;
		}
		else if(attributeName.equals("originalQty")) {
			return ATTRIBUTE_ORIGINAL_QTY;
		}
		else if(attributeName.equals("originalUom")) {
			return ATTRIBUTE_ORIGINAL_UOM;
		}
		else if(attributeName.equals("partShortDesc")) {
			return ATTRIBUTE_PART_SHORT_DESC;
		}
		else if(attributeName.equals("payerAddr")) {
			return ATTRIBUTE_PAYER_ADDR;
		}
		else if(attributeName.equals("payerCode")) {
			return ATTRIBUTE_PAYER_CODE;
		}
		else if(attributeName.equals("payerName")) {
			return ATTRIBUTE_PAYER_NAME;
		}
		else if(attributeName.equals("postalShiptoAddr1")) {
			return ATTRIBUTE_POSTAL_SHIPTO_ADDR1;
		}
		else if(attributeName.equals("postalShiptoAddr2")) {
			return ATTRIBUTE_POSTAL_SHIPTO_ADDR2;
		}
		else if(attributeName.equals("postalShiptoAddr3")) {
			return ATTRIBUTE_POSTAL_SHIPTO_ADDR3;
		}
		else if(attributeName.equals("postalShiptoCity")) {
			return ATTRIBUTE_POSTAL_SHIPTO_CITY;
		}
		else if(attributeName.equals("postalShiptoCode")) {
			return ATTRIBUTE_POSTAL_SHIPTO_CODE;
		}
		else if(attributeName.equals("postalShiptoName")) {
			return ATTRIBUTE_POSTAL_SHIPTO_NAME;
		}
		else if(attributeName.equals("postalShiptoState")) {
			return ATTRIBUTE_POSTAL_SHIPTO_STATE;
		}
		else if(attributeName.equals("postalShiptoZip")) {
			return ATTRIBUTE_POSTAL_SHIPTO_ZIP;
		}
		else if(attributeName.equals("poSuffix")) {
			return ATTRIBUTE_PO_SUFFIX;
		}
		else if(attributeName.equals("productChangeNoticeNum")) {
			return ATTRIBUTE_PRODUCT_CHANGE_NOTICE_NUM;
		}
		else if(attributeName.equals("productGroup")) {
			return ATTRIBUTE_PRODUCT_GROUP;
		}
		else if(attributeName.equals("purchaseRequisitionNum")) {
			return ATTRIBUTE_PURCHASE_REQUISITION_NUM;
		}
		else if(attributeName.equals("receivingLocationId")) {
			return ATTRIBUTE_RECEIVING_LOCATION_ID;
		}
		else if(attributeName.equals("receivingLocationName")) {
			return ATTRIBUTE_RECEIVING_LOCATION_NAME;
		}
		else if(attributeName.equals("receivingParty")) {
			return ATTRIBUTE_RECEIVING_PARTY;
		}
		else if(attributeName.equals("receivingPartyCode")) {
			return ATTRIBUTE_RECEIVING_PARTY_CODE;
		}
		else if(attributeName.equals("requestedDeliveryDateHeader")) {
			return ATTRIBUTE_REQUESTED_DELIVERY_DATE_HEADER;
		}
		else if(attributeName.equals("scheduleNotes")) {
			return ATTRIBUTE_SCHEDULE_NOTES;
		}
		else if(attributeName.equals("scheduleQuantity")) {
			return ATTRIBUTE_SCHEDULE_QUANTITY;
		}
		else if(attributeName.equals("scheduleUom")) {
			return ATTRIBUTE_SCHEDULE_UOM;
		}
		else if(attributeName.equals("shippingNotes")) {
			return ATTRIBUTE_SHIPPING_NOTES;
		}
		else if(attributeName.equals("shipNoticeAddress1")) {
			return ATTRIBUTE_SHIP_NOTICE_ADDRESS1;
		}
		else if(attributeName.equals("shipNoticeAddress2")) {
			return ATTRIBUTE_SHIP_NOTICE_ADDRESS2;
		}
		else if(attributeName.equals("shipNoticeAddress3")) {
			return ATTRIBUTE_SHIP_NOTICE_ADDRESS3;
		}
		else if(attributeName.equals("shipNoticeCity")) {
			return ATTRIBUTE_SHIP_NOTICE_CITY;
		}
		else if(attributeName.equals("shipNoticePartyId")) {
			return ATTRIBUTE_SHIP_NOTICE_PARTY_ID;
		}
		else if(attributeName.equals("shipNoticePartyName")) {
			return ATTRIBUTE_SHIP_NOTICE_PARTY_NAME;
		}
		else if(attributeName.equals("shipNoticeState")) {
			return ATTRIBUTE_SHIP_NOTICE_STATE;
		}
		else if(attributeName.equals("shipNoticeZip")) {
			return ATTRIBUTE_SHIP_NOTICE_ZIP;
		}
		else if(attributeName.equals("supplementaryAddrCode")) {
			return ATTRIBUTE_SUPPLEMENTARY_ADDR_CODE;
		}
		else if(attributeName.equals("totalLineAmt")) {
			return ATTRIBUTE_TOTAL_LINE_AMT;
		}
		else if(attributeName.equals("transportationPriority")) {
			return ATTRIBUTE_TRANSPORTATION_PRIORITY;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("shipViaLocationId")) {
			return ATTRIBUTE_SHIP_VIA_LOCATION_ID;
		}
		else if(attributeName.equals("skipDodaacAddressCheck")) {
			return ATTRIBUTE_SKIP_DODAAC_ADDRESS_CHECK;
		}
		else if(attributeName.equals("buyerAdditionalName")) {
			return ATTRIBUTE_BUYER_ADDITIONAL_NAME;
		}
		else if(attributeName.equals("contractOfficeAlternateName")) {
			return ATTRIBUTE_CONTRACT_OFFICE_ALTERNATE_NAME;
		}
		else if(attributeName.equals("contractOfficeAddress1")) {
			return ATTRIBUTE_CONTRACT_OFFICE_ADDRESS1;
		}
		else if(attributeName.equals("contractOfficeAddress2")) {
			return ATTRIBUTE_CONTRACT_OFFICE_ADDRESS2;
		}
		else if(attributeName.equals("contractOfficeCity")) {
			return ATTRIBUTE_CONTRACT_OFFICE_CITY;
		}
		else if(attributeName.equals("contractOfficeState")) {
			return ATTRIBUTE_CONTRACT_OFFICE_STATE;
		}
		else if(attributeName.equals("contractOfficeZip")) {
			return ATTRIBUTE_CONTRACT_OFFICE_ZIP;
		}
		else if(attributeName.equals("contractOfficeCountry")) {
			return ATTRIBUTE_CONTRACT_OFFICE_COUNTRY;
		}
		else if(attributeName.equals("payofficeAdditionalName")) {
			return ATTRIBUTE_PAYOFFICE_ADDITIONAL_NAME;
		}
		else if(attributeName.equals("payofficeAddress1")) {
			return ATTRIBUTE_PAYOFFICE_ADDRESS1;
		}
		else if(attributeName.equals("payofficeAddress2")) {
			return ATTRIBUTE_PAYOFFICE_ADDRESS2;
		}
		else if(attributeName.equals("payofficeCity")) {
			return ATTRIBUTE_PAYOFFICE_CITY;
		}
		else if(attributeName.equals("payofficeState")) {
			return ATTRIBUTE_PAYOFFICE_STATE;
		}
		else if(attributeName.equals("payofficeZip")) {
			return ATTRIBUTE_PAYOFFICE_ZIP;
		}
		else if(attributeName.equals("payofficeCountry")) {
			return ATTRIBUTE_PAYOFFICE_COUNTRY;
		}
		else if(attributeName.equals("projectCode")) {
			return ATTRIBUTE_PROJECT_CODE;
		}
		else if(attributeName.equals("priorityRating")) {
			return ATTRIBUTE_PRIORITY_RATING;
		}
		else if(attributeName.equals("fmsCaseNum")) {
			return ATTRIBUTE_FMS_CASE_NUM;
		}
		else if(attributeName.equals("portOfEmbarkation")) {
			return ATTRIBUTE_PORT_OF_EMBARKATION;
		}
		else if(attributeName.equals("portOfDebarkation")) {
			return ATTRIBUTE_PORT_OF_DEBARKATION;
		}
		else if(attributeName.equals("tac")) {
			return ATTRIBUTE_TAC;
		}
		else if(attributeName.equals("transactionRefNum")) {
			return ATTRIBUTE_TRANSACTION_REF_NUM;
		}
		else if(attributeName.equals("transportationControlNum")) {
			return ATTRIBUTE_TRANSPORTATION_CONTROL_NUM;
		}
		else if(attributeName.equals("addressChangeRequestId")) {
			return ATTRIBUTE_ADDRESS_CHANGE_REQUEST_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CustomerPoStageBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(CustomerPoStageBean customerPoStageBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + customerPoStageBean.getCompanyId());

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


	public int delete(CustomerPoStageBean customerPoStageBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + customerPoStageBean.getCompanyId());

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
	public int insert(CustomerPoStageBean customerPoStageBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(customerPoStageBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(CustomerPoStageBean customerPoStageBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_CUSTOMER_PO_NO + "," +
			ATTRIBUTE_CUSTOMER_PO_LINE_NO + "," +
			ATTRIBUTE_TRANSACTION_TYPE + "," +
			ATTRIBUTE_LINE_SEQUENCE + "," +
			ATTRIBUTE_CHANGE_ORDER_SEQUENCE + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_QUANTITY_LEFT + "," +
			ATTRIBUTE_UOM + "," +
			ATTRIBUTE_UNIT_PRICE + "," +
			ATTRIBUTE_STATUS + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_PART_GROUP_NO + "," +
			ATTRIBUTE_DATE_ISSUED + "," +
			ATTRIBUTE_HAAS_ITEM_NO + "," +
			ATTRIBUTE_MANUFACTURER_PART_NUM + "," +
			ATTRIBUTE_ITEM_DESCRIPTION + "," +
			ATTRIBUTE_CUSTOMER_PO_LINE_NOTE + "," +
			ATTRIBUTE_REQUESTED_DELIVERY_DATE + "," +
			ATTRIBUTE_ESTIMATED_DOCK_DATE + "," +
			ATTRIBUTE_TOTAL_AMOUNT_ON_PO + "," +
			ATTRIBUTE_DATE_INSERTED + "," +
			ATTRIBUTE_UNIT_PRICE_CODE + "," +
			ATTRIBUTE_ACCEPTANCE_CODE + "," +
			ATTRIBUTE_CHANGE_TYPE_CODE + "," +
			ATTRIBUTE_CHANGE_SUB_TYPE_CODE + "," +
			ATTRIBUTE_BUYER_PHONE + "," +
			ATTRIBUTE_BUYER_FAX + "," +
			ATTRIBUTE_DOCUMENT_CONTROL_NUMBER + "," +
			ATTRIBUTE_TRANSPORT + "," +
			ATTRIBUTE_TRANSPORTER_ACCOUNT + "," +
			ATTRIBUTE_TRADING_PARTNER + "," +
			ATTRIBUTE_TRADING_PARTNER_ID + "," +
			ATTRIBUTE_CUSTOMER_HAAS_CONTRACT_ID + "," +
			ATTRIBUTE_CUSTOMER_HAAS_ACCOUNT_NO + "," +
			ATTRIBUTE_CUSTOMER_PAYMENT_TERMS + "," +
			ATTRIBUTE_CUSTOMER_PO_NOTE + "," +
			ATTRIBUTE_BUYER_NAME_ON_PO + "," +
			ATTRIBUTE_BUYER_ADDRESS1 + "," +
			ATTRIBUTE_BUYER_ADDRESS2 + "," +
			ATTRIBUTE_BUYER_CITY + "," +
			ATTRIBUTE_BUYER_STATE + "," +
			ATTRIBUTE_BUYER_ZIP + "," +
			ATTRIBUTE_BUYER_COUNTRY + "," +
			ATTRIBUTE_SELLER_NAME_ON_PO + "," +
			ATTRIBUTE_SELLER_ID_ON_PO + "," +
			ATTRIBUTE_SELLER_ADDRESS1 + "," +
			ATTRIBUTE_SELLER_ADDRESS2 + "," +
			ATTRIBUTE_SELLER_CITY + "," +
			ATTRIBUTE_SELLER_STATE + "," +
			ATTRIBUTE_SELLER_ZIP + "," +
			ATTRIBUTE_SELLER_COUNTRY + "," +
			ATTRIBUTE_SHIPTO_PARTY_NAME + "," +
			ATTRIBUTE_SHIPTO_PARTY_ID + "," +
			ATTRIBUTE_SHIPTO_ADDRESS1 + "," +
			ATTRIBUTE_SHIPTO_ADDRESS2 + "," +
			ATTRIBUTE_SHIPTO_ADDRESS3 + "," +
			ATTRIBUTE_SHIPTO_CITY + "," +
			ATTRIBUTE_SHIPTO_STATE + "," +
			ATTRIBUTE_SHIPTO_ZIP + "," +
			ATTRIBUTE_SHIPTO_COUNTRY + "," +
			ATTRIBUTE_BILLTO_PARTY + "," +
			ATTRIBUTE_BILLTO_PARTY_ID + "," +
			ATTRIBUTE_BILLTO_NAME2 + "," +
			ATTRIBUTE_BILLTO_ADDRESS1 + "," +
			ATTRIBUTE_BILLTO_ADDRESS2 + "," +
			ATTRIBUTE_BILLTO_ADDRESS3 + "," +
			ATTRIBUTE_BILLTO_CITY + "," +
			ATTRIBUTE_BILLTO_STATE + "," +
			ATTRIBUTE_BILLTO_ZIP + "," +
			ATTRIBUTE_BILLTO_COUNTRY + "," +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_SUGGESTED_DBUY_CONTRACT_ID + "," +
			ATTRIBUTE_SUGGESTED_QUANTITY + "," +
			ATTRIBUTE_ACCOUNT_SYS_ID + "," +
			ATTRIBUTE_CHARGE_TYPE + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_APPLICATION + "," +
			ATTRIBUTE_INTERCHANGE_CONTROL_NUMBER + "," +
			ATTRIBUTE_ERROR_DETAIL + "," +
			ATTRIBUTE_PO_LINE + "," +
			ATTRIBUTE_PO_LINE_AMENDMENT + "," +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_STATUS_BEFORE_ERROR + "," +
			ATTRIBUTE_SOURCE + "," +
			ATTRIBUTE_CURRENT_TRANSACTION + "," +
			ATTRIBUTE_CURRENCY_ID + "," +
			ATTRIBUTE_LOAD_ID + "," +
			ATTRIBUTE_LOAD_LINE + "," +
			ATTRIBUTE_PROCESSING_COMMENTS + "," +
			ATTRIBUTE_PO_TYPE_CODE + "," +
			ATTRIBUTE_LAST_UPDATED_BY + "," +
			ATTRIBUTE_LAST_UPDATED_ON + "," +
			ATTRIBUTE_BUYER_PARTY_NAME + "," +
			ATTRIBUTE_SELLER_PARTY_NAME + "," +
			ATTRIBUTE_SHIPTO_CONTACT_NAME + "," +
			ATTRIBUTE_PRICE_BASIS_UOM + "," +
			ATTRIBUTE_PRICE_BASIS_QUANTITY + "," +
			ATTRIBUTE_REQUESTOR_NAME + "," +
			ATTRIBUTE_PARTIAL_SHIPMENT + "," +
			ATTRIBUTE_BUYER_REGION + "," +
			ATTRIBUTE_SELLER_REGION + "," +
			ATTRIBUTE_BILLTO_REGION + "," +
			ATTRIBUTE_SHIPTO_REGION + "," +
			ATTRIBUTE_CARRIER + "," +
			ATTRIBUTE_PO_UPDATE_DATE + "," +
			ATTRIBUTE_FREIGHT_ON_BOARD + "," +
			ATTRIBUTE_PAYMENT_TERMS + "," +
			ATTRIBUTE_FREIGHT_ON_BOARD_NOTES + "," +
			ATTRIBUTE_PAYMENT_TERMS_NOTES + "," +
			ATTRIBUTE_FROM_LOAD_ID + "," +
			ATTRIBUTE_FROM_LOAD_LINE + "," +
			ATTRIBUTE_CAT_PART_NO_ORIG + "," +
			ATTRIBUTE_CHARGE_NUMBER1 + "," +
			ATTRIBUTE_MULTIPLE_CLEAR_FLAG + "," +
			ATTRIBUTE_RELEASED + "," +
			ATTRIBUTE_REQUISITION_NUMBER + "," +
			ATTRIBUTE_TOTAL_PO_NOT_TO_EXCEED_AMOUNT + "," +
			ATTRIBUTE_RELEASE_NUM + "," +
			ATTRIBUTE_PAYOFFICE_NAME + "," +
			ATTRIBUTE_PAYOFFICE_ID + "," +
			ATTRIBUTE_BUYER_ID_ON_PO + "," +
			ATTRIBUTE_SHIP_TO_DODAAC + "," +
			ATTRIBUTE_MARK_FOR_DODAAC + "," +
			ATTRIBUTE_ACCT_DEPT + "," +
			ATTRIBUTE_ACCT_FUNDS_SUBDIV + "," +
			ATTRIBUTE_ACCT_FUNDS_TYPE + "," +
			ATTRIBUTE_ACCT_INSTALLATION_NUM + "," +
			ATTRIBUTE_ACCT_OBJECT_CLASS + "," +
			ATTRIBUTE_ACRN + "," +
			ATTRIBUTE_ALLOTMENT_SERIAL_NUM + "," +
			ATTRIBUTE_BUYER_EMAIL + "," +
			ATTRIBUTE_CONTRACT_OFFICE + "," +
			ATTRIBUTE_CONTRACT_OFFICER_EMAIL + "," +
			ATTRIBUTE_CONTRACT_OFFICER_NAME + "," +
			ATTRIBUTE_CONTRACT_OFFICER_PHONE + "," +
			ATTRIBUTE_CONTRACT_OFFICE_CODE + "," +
			ATTRIBUTE_DELIVERY_TYPE_CODE + "," +
			ATTRIBUTE_DPAS_RATING + "," +
			ATTRIBUTE_LEAD_TIME + "," +
			ATTRIBUTE_LEAD_TIME_CODE + "," +
			ATTRIBUTE_MANUFACTURER_CAGE_CODE + "," +
			ATTRIBUTE_MARK_FOR_ADDRESS1 + "," +
			ATTRIBUTE_MARK_FOR_ADDRESS2 + "," +
			ATTRIBUTE_MARK_FOR_ADDRESS3 + "," +
			ATTRIBUTE_MARK_FOR_CITY + "," +
			ATTRIBUTE_MARK_FOR_COUNTRY + "," +
			ATTRIBUTE_MARK_FOR_PARTY_ID + "," +
			ATTRIBUTE_MARK_FOR_PARTY_NAME + "," +
			ATTRIBUTE_MARK_FOR_STATE + "," +
			ATTRIBUTE_MARK_FOR_ZIP + "," +
			ATTRIBUTE_MFG_PLANT_ID + "," +
			ATTRIBUTE_MFG_PLANT_NAME + "," +
			ATTRIBUTE_MILSTRIP_CODE + "," +
			ATTRIBUTE_MODEL_YEAR_NUM + "," +
			ATTRIBUTE_ORIGINAL_DATE_ISSUED + "," +
			ATTRIBUTE_ORIGINAL_QTY + "," +
			ATTRIBUTE_ORIGINAL_UOM + "," +
			ATTRIBUTE_PART_SHORT_DESC + "," +
			ATTRIBUTE_PAYER_ADDR + "," +
			ATTRIBUTE_PAYER_CODE + "," +
			ATTRIBUTE_PAYER_NAME + "," +
			ATTRIBUTE_POSTAL_SHIPTO_ADDR1 + "," +
			ATTRIBUTE_POSTAL_SHIPTO_ADDR2 + "," +
			ATTRIBUTE_POSTAL_SHIPTO_ADDR3 + "," +
			ATTRIBUTE_POSTAL_SHIPTO_CITY + "," +
			ATTRIBUTE_POSTAL_SHIPTO_CODE + "," +
			ATTRIBUTE_POSTAL_SHIPTO_NAME + "," +
			ATTRIBUTE_POSTAL_SHIPTO_STATE + "," +
			ATTRIBUTE_POSTAL_SHIPTO_ZIP + "," +
			ATTRIBUTE_PO_SUFFIX + "," +
			ATTRIBUTE_PRODUCT_CHANGE_NOTICE_NUM + "," +
			ATTRIBUTE_PRODUCT_GROUP + "," +
			ATTRIBUTE_PURCHASE_REQUISITION_NUM + "," +
			ATTRIBUTE_RECEIVING_LOCATION_ID + "," +
			ATTRIBUTE_RECEIVING_LOCATION_NAME + "," +
			ATTRIBUTE_RECEIVING_PARTY + "," +
			ATTRIBUTE_RECEIVING_PARTY_CODE + "," +
			ATTRIBUTE_REQUESTED_DELIVERY_DATE_HEADER + "," +
			ATTRIBUTE_SCHEDULE_NOTES + "," +
			ATTRIBUTE_SCHEDULE_QUANTITY + "," +
			ATTRIBUTE_SCHEDULE_UOM + "," +
			ATTRIBUTE_SHIPPING_NOTES + "," +
			ATTRIBUTE_SHIP_NOTICE_ADDRESS1 + "," +
			ATTRIBUTE_SHIP_NOTICE_ADDRESS2 + "," +
			ATTRIBUTE_SHIP_NOTICE_ADDRESS3 + "," +
			ATTRIBUTE_SHIP_NOTICE_CITY + "," +
			ATTRIBUTE_SHIP_NOTICE_PARTY_ID + "," +
			ATTRIBUTE_SHIP_NOTICE_PARTY_NAME + "," +
			ATTRIBUTE_SHIP_NOTICE_STATE + "," +
			ATTRIBUTE_SHIP_NOTICE_ZIP + "," +
			ATTRIBUTE_SUPPLEMENTARY_ADDR_CODE + "," +
			ATTRIBUTE_TOTAL_LINE_AMT + "," +
			ATTRIBUTE_TRANSPORTATION_PRIORITY + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_SHIP_VIA_LOCATION_ID + "," +
			ATTRIBUTE_SKIP_DODAAC_ADDRESS_CHECK + "," +
			ATTRIBUTE_BUYER_ADDITIONAL_NAME + "," +
			ATTRIBUTE_CONTRACT_OFFICE_ALTERNATE_NAME + "," +
			ATTRIBUTE_CONTRACT_OFFICE_ADDRESS1 + "," +
			ATTRIBUTE_CONTRACT_OFFICE_ADDRESS2 + "," +
			ATTRIBUTE_CONTRACT_OFFICE_CITY + "," +
			ATTRIBUTE_CONTRACT_OFFICE_STATE + "," +
			ATTRIBUTE_CONTRACT_OFFICE_ZIP + "," +
			ATTRIBUTE_CONTRACT_OFFICE_COUNTRY + "," +
			ATTRIBUTE_PAYOFFICE_ADDITIONAL_NAME + "," +
			ATTRIBUTE_PAYOFFICE_ADDRESS1 + "," +
			ATTRIBUTE_PAYOFFICE_ADDRESS2 + "," +
			ATTRIBUTE_PAYOFFICE_CITY + "," +
			ATTRIBUTE_PAYOFFICE_STATE + "," +
			ATTRIBUTE_PAYOFFICE_ZIP + "," +
			ATTRIBUTE_PAYOFFICE_COUNTRY + "," +
			ATTRIBUTE_PROJECT_CODE + "," +
			ATTRIBUTE_PRIORITY_RATING + "," +
			ATTRIBUTE_FMS_CASE_NUM + "," +
			ATTRIBUTE_PORT_OF_EMBARKATION + "," +
			ATTRIBUTE_PORT_OF_DEBARKATION + "," +
			ATTRIBUTE_TAC + "," +
			ATTRIBUTE_TRANSACTION_REF_NUM + "," +
			ATTRIBUTE_TRANSPORTATION_CONTROL_NUM + "," +
			ATTRIBUTE_ADDRESS_CHANGE_REQUEST_ID + ")" +
			" values (" +
			SqlHandler.delimitString(customerPoStageBean.getCompanyId()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getCustomerPoNo()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getCustomerPoLineNo()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getTransactionType()) + "," +
			customerPoStageBean.getLineSequence() + "," +
			customerPoStageBean.getChangeOrderSequence() + "," +
			customerPoStageBean.getQuantity() + "," +
			customerPoStageBean.getQuantityLeft() + "," +
			SqlHandler.delimitString(customerPoStageBean.getUom()) + "," +
			customerPoStageBean.getUnitPrice() + "," +
			SqlHandler.delimitString(customerPoStageBean.getStatus()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getCatalogId()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getCatPartNo()) + "," +
			customerPoStageBean.getPartGroupNo() + "," +
			DateHandler.getOracleToDateFunction(customerPoStageBean.getDateIssued()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getHaasItemNo()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getManufacturerPartNum()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getItemDescription()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getCustomerPoLineNote()) + "," +
			DateHandler.getOracleToDateFunction(customerPoStageBean.getRequestedDeliveryDate()) + "," +
			DateHandler.getOracleToDateFunction(customerPoStageBean.getEstimatedDockDate()) + "," +
			customerPoStageBean.getTotalAmountOnPo() + "," +
			DateHandler.getOracleToDateFunction(customerPoStageBean.getDateInserted()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getUnitPriceCode()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getAcceptanceCode()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getChangeTypeCode()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getChangeSubTypeCode()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBuyerPhone()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBuyerFax()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getDocumentControlNumber()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getTransport()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getTransporterAccount()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getTradingPartner()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getTradingPartnerId()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getCustomerHaasContractId()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getCustomerHaasAccountNo()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getCustomerPaymentTerms()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getCustomerPoNote()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBuyerNameOnPo()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBuyerAddress1()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBuyerAddress2()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBuyerCity()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBuyerState()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBuyerZip()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBuyerCountry()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getSellerNameOnPo()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getSellerIdOnPo()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getSellerAddress1()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getSellerAddress2()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getSellerCity()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getSellerState()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getSellerZip()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getSellerCountry()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShiptoPartyName()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShiptoPartyId()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShiptoAddress1()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShiptoAddress2()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShiptoAddress3()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShiptoCity()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShiptoState()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShiptoZip()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShiptoCountry()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBilltoParty()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBilltoPartyId()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBilltoName2()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBilltoAddress1()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBilltoAddress2()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBilltoAddress3()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBilltoCity()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBilltoState()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBilltoZip()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBilltoCountry()) + "," +
			customerPoStageBean.getPrNumber() + "," +
			SqlHandler.delimitString(customerPoStageBean.getLineItem()) + "," +
			customerPoStageBean.getSuggestedDbuyContractId() + "," +
			customerPoStageBean.getSuggestedQuantity() + "," +
			SqlHandler.delimitString(customerPoStageBean.getAccountSysId()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getChargeType()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getFacilityId()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getApplication()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getInterchangeControlNumber()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getErrorDetail()) + "," +
			customerPoStageBean.getPoLine() + "," +
			customerPoStageBean.getPoLineAmendment() + "," +
			customerPoStageBean.getRadianPo() + "," +
			SqlHandler.delimitString(customerPoStageBean.getStatusBeforeError()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getSource()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getCurrentTransaction()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getCurrencyId()) + "," +
			customerPoStageBean.getLoadId() + "," +
			customerPoStageBean.getLoadLine() + "," +
			SqlHandler.delimitString(customerPoStageBean.getProcessingComments()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPoTypeCode()) + "," +
			customerPoStageBean.getLastUpdatedBy() + "," +
			DateHandler.getOracleToDateFunction(customerPoStageBean.getLastUpdatedOn()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBuyerPartyName()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getSellerPartyName()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShiptoContactName()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPriceBasisUom()) + "," +
			customerPoStageBean.getPriceBasisQuantity() + "," +
			SqlHandler.delimitString(customerPoStageBean.getRequestorName()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPartialShipment()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBuyerRegion()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getSellerRegion()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBilltoRegion()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShiptoRegion()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getCarrier()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPoUpdateDate()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getFreightOnBoard()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPaymentTerms()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getFreightOnBoardNotes()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPaymentTermsNotes()) + "," +
			customerPoStageBean.getFromLoadId() + "," +
			customerPoStageBean.getFromLoadLine() + "," +
			SqlHandler.delimitString(customerPoStageBean.getCatPartNoOrig()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getChargeNumber1()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getMultipleClearFlag()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getReleased()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getRequisitionNumber()) + "," +
			customerPoStageBean.getTotalPoNotToExceedAmount() + "," +
			SqlHandler.delimitString(customerPoStageBean.getReleaseNum()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPayofficeName()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPayofficeId()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBuyerIdOnPo()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShipToDodaac()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getMarkForDodaac()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getAcctDept()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getAcctFundsSubdiv()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getAcctFundsType()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getAcctInstallationNum()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getAcctObjectClass()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getAcrn()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getAllotmentSerialNum()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBuyerEmail()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getContractOffice()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getContractOfficerEmail()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getContractOfficerName()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getContractOfficerPhone()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getContractOfficeCode()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getDeliveryTypeCode()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getDpasRating()) + "," +
			customerPoStageBean.getLeadTime() + "," +
			SqlHandler.delimitString(customerPoStageBean.getLeadTimeCode()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getManufacturerCageCode()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getMarkForAddress1()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getMarkForAddress2()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getMarkForAddress3()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getMarkForCity()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getMarkForCountry()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getMarkForPartyId()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getMarkForPartyName()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getMarkForState()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getMarkForZip()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getMfgPlantId()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getMfgPlantName()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getMilstripCode()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getModelYearNum()) + "," +
			DateHandler.getOracleToDateFunction(customerPoStageBean.getOriginalDateIssued()) + "," +
			customerPoStageBean.getOriginalQty() + "," +
			SqlHandler.delimitString(customerPoStageBean.getOriginalUom()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPartShortDesc()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPayerAddr()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPayerCode()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPayerName()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPostalShiptoAddr1()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPostalShiptoAddr2()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPostalShiptoAddr3()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPostalShiptoCity()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPostalShiptoCode()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPostalShiptoName()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPostalShiptoState()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPostalShiptoZip()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPoSuffix()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getProductChangeNoticeNum()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getProductGroup()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPurchaseRequisitionNum()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getReceivingLocationId()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getReceivingLocationName()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getReceivingParty()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getReceivingPartyCode()) + "," +
			DateHandler.getOracleToDateFunction(customerPoStageBean.getRequestedDeliveryDateHeader()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getScheduleNotes()) + "," +
			customerPoStageBean.getScheduleQuantity() + "," +
			SqlHandler.delimitString(customerPoStageBean.getScheduleUom()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShippingNotes()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShipNoticeAddress1()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShipNoticeAddress2()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShipNoticeAddress3()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShipNoticeCity()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShipNoticePartyId()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShipNoticePartyName()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShipNoticeState()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShipNoticeZip()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getSupplementaryAddrCode()) + "," +
			customerPoStageBean.getTotalLineAmt() + "," +
			SqlHandler.delimitString(customerPoStageBean.getTransportationPriority()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShipToLocationId()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getShipViaLocationId()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getSkipDodaacAddressCheck()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getBuyerAdditionalName()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getContractOfficeAlternateName()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getContractOfficeAddress1()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getContractOfficeAddress2()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getContractOfficeCity()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getContractOfficeState()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getContractOfficeZip()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getContractOfficeCountry()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPayofficeAdditionalName()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPayofficeAddress1()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPayofficeAddress2()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPayofficeCity()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPayofficeState()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPayofficeZip()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPayofficeCountry()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getProjectCode()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPriorityRating()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getFmsCaseNum()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPortOfEmbarkation()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getPortOfDebarkation()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getTac()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getTransactionRefNum()) + "," +
			SqlHandler.delimitString(customerPoStageBean.getTransportationControlNum()) + "," +
			customerPoStageBean.getAddressChangeRequestId() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(CustomerPoStageBean customerPoStageBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(customerPoStageBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(CustomerPoStageBean customerPoStageBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getCompanyId()) + "," +
			ATTRIBUTE_CUSTOMER_PO_NO + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getCustomerPoNo()) + "," +
			ATTRIBUTE_CUSTOMER_PO_LINE_NO + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getCustomerPoLineNo()) + "," +
			ATTRIBUTE_TRANSACTION_TYPE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getTransactionType()) + "," +
			ATTRIBUTE_LINE_SEQUENCE + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getLineSequence()) + "," +
			ATTRIBUTE_CHANGE_ORDER_SEQUENCE + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getChangeOrderSequence()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getQuantity()) + "," +
			ATTRIBUTE_QUANTITY_LEFT + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getQuantityLeft()) + "," +
			ATTRIBUTE_UOM + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getUom()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getUnitPrice()) + "," +
			ATTRIBUTE_STATUS + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getStatus()) + "," +
			ATTRIBUTE_CATALOG_ID + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getCatalogId()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getCatPartNo()) + "," +
			ATTRIBUTE_PART_GROUP_NO + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getPartGroupNo()) + "," +
			ATTRIBUTE_DATE_ISSUED + "=" + 
				DateHandler.getOracleToDateFunction(customerPoStageBean.getDateIssued()) + "," +
			ATTRIBUTE_HAAS_ITEM_NO + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getHaasItemNo()) + "," +
			ATTRIBUTE_MANUFACTURER_PART_NUM + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getManufacturerPartNum()) + "," +
			ATTRIBUTE_ITEM_DESCRIPTION + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getItemDescription()) + "," +
			ATTRIBUTE_CUSTOMER_PO_LINE_NOTE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getCustomerPoLineNote()) + "," +
			ATTRIBUTE_REQUESTED_DELIVERY_DATE + "=" + 
				DateHandler.getOracleToDateFunction(customerPoStageBean.getRequestedDeliveryDate()) + "," +
			ATTRIBUTE_ESTIMATED_DOCK_DATE + "=" + 
				DateHandler.getOracleToDateFunction(customerPoStageBean.getEstimatedDockDate()) + "," +
			ATTRIBUTE_TOTAL_AMOUNT_ON_PO + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getTotalAmountOnPo()) + "," +
			ATTRIBUTE_DATE_INSERTED + "=" + 
				DateHandler.getOracleToDateFunction(customerPoStageBean.getDateInserted()) + "," +
			ATTRIBUTE_UNIT_PRICE_CODE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getUnitPriceCode()) + "," +
			ATTRIBUTE_ACCEPTANCE_CODE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getAcceptanceCode()) + "," +
			ATTRIBUTE_CHANGE_TYPE_CODE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getChangeTypeCode()) + "," +
			ATTRIBUTE_CHANGE_SUB_TYPE_CODE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getChangeSubTypeCode()) + "," +
			ATTRIBUTE_BUYER_PHONE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBuyerPhone()) + "," +
			ATTRIBUTE_BUYER_FAX + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBuyerFax()) + "," +
			ATTRIBUTE_DOCUMENT_CONTROL_NUMBER + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getDocumentControlNumber()) + "," +
			ATTRIBUTE_TRANSPORT + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getTransport()) + "," +
			ATTRIBUTE_TRANSPORTER_ACCOUNT + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getTransporterAccount()) + "," +
			ATTRIBUTE_TRADING_PARTNER + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getTradingPartner()) + "," +
			ATTRIBUTE_TRADING_PARTNER_ID + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getTradingPartnerId()) + "," +
			ATTRIBUTE_CUSTOMER_HAAS_CONTRACT_ID + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getCustomerHaasContractId()) + "," +
			ATTRIBUTE_CUSTOMER_HAAS_ACCOUNT_NO + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getCustomerHaasAccountNo()) + "," +
			ATTRIBUTE_CUSTOMER_PAYMENT_TERMS + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getCustomerPaymentTerms()) + "," +
			ATTRIBUTE_CUSTOMER_PO_NOTE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getCustomerPoNote()) + "," +
			ATTRIBUTE_BUYER_NAME_ON_PO + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBuyerNameOnPo()) + "," +
			ATTRIBUTE_BUYER_ADDRESS1 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBuyerAddress1()) + "," +
			ATTRIBUTE_BUYER_ADDRESS2 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBuyerAddress2()) + "," +
			ATTRIBUTE_BUYER_CITY + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBuyerCity()) + "," +
			ATTRIBUTE_BUYER_STATE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBuyerState()) + "," +
			ATTRIBUTE_BUYER_ZIP + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBuyerZip()) + "," +
			ATTRIBUTE_BUYER_COUNTRY + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBuyerCountry()) + "," +
			ATTRIBUTE_SELLER_NAME_ON_PO + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getSellerNameOnPo()) + "," +
			ATTRIBUTE_SELLER_ID_ON_PO + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getSellerIdOnPo()) + "," +
			ATTRIBUTE_SELLER_ADDRESS1 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getSellerAddress1()) + "," +
			ATTRIBUTE_SELLER_ADDRESS2 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getSellerAddress2()) + "," +
			ATTRIBUTE_SELLER_CITY + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getSellerCity()) + "," +
			ATTRIBUTE_SELLER_STATE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getSellerState()) + "," +
			ATTRIBUTE_SELLER_ZIP + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getSellerZip()) + "," +
			ATTRIBUTE_SELLER_COUNTRY + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getSellerCountry()) + "," +
			ATTRIBUTE_SHIPTO_PARTY_NAME + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShiptoPartyName()) + "," +
			ATTRIBUTE_SHIPTO_PARTY_ID + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShiptoPartyId()) + "," +
			ATTRIBUTE_SHIPTO_ADDRESS1 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShiptoAddress1()) + "," +
			ATTRIBUTE_SHIPTO_ADDRESS2 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShiptoAddress2()) + "," +
			ATTRIBUTE_SHIPTO_ADDRESS3 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShiptoAddress3()) + "," +
			ATTRIBUTE_SHIPTO_CITY + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShiptoCity()) + "," +
			ATTRIBUTE_SHIPTO_STATE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShiptoState()) + "," +
			ATTRIBUTE_SHIPTO_ZIP + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShiptoZip()) + "," +
			ATTRIBUTE_SHIPTO_COUNTRY + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShiptoCountry()) + "," +
			ATTRIBUTE_BILLTO_PARTY + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBilltoParty()) + "," +
			ATTRIBUTE_BILLTO_PARTY_ID + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBilltoPartyId()) + "," +
			ATTRIBUTE_BILLTO_NAME2 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBilltoName2()) + "," +
			ATTRIBUTE_BILLTO_ADDRESS1 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBilltoAddress1()) + "," +
			ATTRIBUTE_BILLTO_ADDRESS2 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBilltoAddress2()) + "," +
			ATTRIBUTE_BILLTO_ADDRESS3 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBilltoAddress3()) + "," +
			ATTRIBUTE_BILLTO_CITY + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBilltoCity()) + "," +
			ATTRIBUTE_BILLTO_STATE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBilltoState()) + "," +
			ATTRIBUTE_BILLTO_ZIP + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBilltoZip()) + "," +
			ATTRIBUTE_BILLTO_COUNTRY + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBilltoCountry()) + "," +
			ATTRIBUTE_PR_NUMBER + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getPrNumber()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getLineItem()) + "," +
			ATTRIBUTE_SUGGESTED_DBUY_CONTRACT_ID + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getSuggestedDbuyContractId()) + "," +
			ATTRIBUTE_SUGGESTED_QUANTITY + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getSuggestedQuantity()) + "," +
			ATTRIBUTE_ACCOUNT_SYS_ID + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getAccountSysId()) + "," +
			ATTRIBUTE_CHARGE_TYPE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getChargeType()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getFacilityId()) + "," +
			ATTRIBUTE_APPLICATION + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getApplication()) + "," +
			ATTRIBUTE_INTERCHANGE_CONTROL_NUMBER + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getInterchangeControlNumber()) + "," +
			ATTRIBUTE_ERROR_DETAIL + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getErrorDetail()) + "," +
			ATTRIBUTE_PO_LINE + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getPoLine()) + "," +
			ATTRIBUTE_PO_LINE_AMENDMENT + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getPoLineAmendment()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getRadianPo()) + "," +
			ATTRIBUTE_STATUS_BEFORE_ERROR + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getStatusBeforeError()) + "," +
			ATTRIBUTE_SOURCE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getSource()) + "," +
			ATTRIBUTE_CURRENT_TRANSACTION + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getCurrentTransaction()) + "," +
			ATTRIBUTE_CURRENCY_ID + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getCurrencyId()) + "," +
			ATTRIBUTE_LOAD_ID + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getLoadId()) + "," +
			ATTRIBUTE_LOAD_LINE + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getLoadLine()) + "," +
			ATTRIBUTE_PROCESSING_COMMENTS + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getProcessingComments()) + "," +
			ATTRIBUTE_PO_TYPE_CODE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPoTypeCode()) + "," +
			ATTRIBUTE_LAST_UPDATED_BY + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getLastUpdatedBy()) + "," +
			ATTRIBUTE_LAST_UPDATED_ON + "=" + 
				DateHandler.getOracleToDateFunction(customerPoStageBean.getLastUpdatedOn()) + "," +
			ATTRIBUTE_BUYER_PARTY_NAME + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBuyerPartyName()) + "," +
			ATTRIBUTE_SELLER_PARTY_NAME + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getSellerPartyName()) + "," +
			ATTRIBUTE_SHIPTO_CONTACT_NAME + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShiptoContactName()) + "," +
			ATTRIBUTE_PRICE_BASIS_UOM + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPriceBasisUom()) + "," +
			ATTRIBUTE_PRICE_BASIS_QUANTITY + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getPriceBasisQuantity()) + "," +
			ATTRIBUTE_REQUESTOR_NAME + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getRequestorName()) + "," +
			ATTRIBUTE_PARTIAL_SHIPMENT + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPartialShipment()) + "," +
			ATTRIBUTE_BUYER_REGION + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBuyerRegion()) + "," +
			ATTRIBUTE_SELLER_REGION + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getSellerRegion()) + "," +
			ATTRIBUTE_BILLTO_REGION + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBilltoRegion()) + "," +
			ATTRIBUTE_SHIPTO_REGION + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShiptoRegion()) + "," +
			ATTRIBUTE_CARRIER + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getCarrier()) + "," +
			ATTRIBUTE_PO_UPDATE_DATE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPoUpdateDate()) + "," +
			ATTRIBUTE_FREIGHT_ON_BOARD + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getFreightOnBoard()) + "," +
			ATTRIBUTE_PAYMENT_TERMS + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPaymentTerms()) + "," +
			ATTRIBUTE_FREIGHT_ON_BOARD_NOTES + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getFreightOnBoardNotes()) + "," +
			ATTRIBUTE_PAYMENT_TERMS_NOTES + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPaymentTermsNotes()) + "," +
			ATTRIBUTE_FROM_LOAD_ID + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getFromLoadId()) + "," +
			ATTRIBUTE_FROM_LOAD_LINE + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getFromLoadLine()) + "," +
			ATTRIBUTE_CAT_PART_NO_ORIG + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getCatPartNoOrig()) + "," +
			ATTRIBUTE_CHARGE_NUMBER1 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getChargeNumber1()) + "," +
			ATTRIBUTE_MULTIPLE_CLEAR_FLAG + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getMultipleClearFlag()) + "," +
			ATTRIBUTE_RELEASED + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getReleased()) + "," +
			ATTRIBUTE_REQUISITION_NUMBER + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getRequisitionNumber()) + "," +
			ATTRIBUTE_TOTAL_PO_NOT_TO_EXCEED_AMOUNT + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getTotalPoNotToExceedAmount()) + "," +
			ATTRIBUTE_RELEASE_NUM + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getReleaseNum()) + "," +
			ATTRIBUTE_PAYOFFICE_NAME + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPayofficeName()) + "," +
			ATTRIBUTE_PAYOFFICE_ID + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPayofficeId()) + "," +
			ATTRIBUTE_BUYER_ID_ON_PO + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBuyerIdOnPo()) + "," +
			ATTRIBUTE_SHIP_TO_DODAAC + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShipToDodaac()) + "," +
			ATTRIBUTE_MARK_FOR_DODAAC + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getMarkForDodaac()) + "," +
			ATTRIBUTE_ACCT_DEPT + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getAcctDept()) + "," +
			ATTRIBUTE_ACCT_FUNDS_SUBDIV + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getAcctFundsSubdiv()) + "," +
			ATTRIBUTE_ACCT_FUNDS_TYPE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getAcctFundsType()) + "," +
			ATTRIBUTE_ACCT_INSTALLATION_NUM + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getAcctInstallationNum()) + "," +
			ATTRIBUTE_ACCT_OBJECT_CLASS + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getAcctObjectClass()) + "," +
			ATTRIBUTE_ACRN + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getAcrn()) + "," +
			ATTRIBUTE_ALLOTMENT_SERIAL_NUM + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getAllotmentSerialNum()) + "," +
			ATTRIBUTE_BUYER_EMAIL + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBuyerEmail()) + "," +
			ATTRIBUTE_CONTRACT_OFFICE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getContractOffice()) + "," +
			ATTRIBUTE_CONTRACT_OFFICER_EMAIL + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getContractOfficerEmail()) + "," +
			ATTRIBUTE_CONTRACT_OFFICER_NAME + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getContractOfficerName()) + "," +
			ATTRIBUTE_CONTRACT_OFFICER_PHONE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getContractOfficerPhone()) + "," +
			ATTRIBUTE_CONTRACT_OFFICE_CODE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getContractOfficeCode()) + "," +
			ATTRIBUTE_DELIVERY_TYPE_CODE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getDeliveryTypeCode()) + "," +
			ATTRIBUTE_DPAS_RATING + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getDpasRating()) + "," +
			ATTRIBUTE_LEAD_TIME + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getLeadTime()) + "," +
			ATTRIBUTE_LEAD_TIME_CODE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getLeadTimeCode()) + "," +
			ATTRIBUTE_MANUFACTURER_CAGE_CODE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getManufacturerCageCode()) + "," +
			ATTRIBUTE_MARK_FOR_ADDRESS1 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getMarkForAddress1()) + "," +
			ATTRIBUTE_MARK_FOR_ADDRESS2 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getMarkForAddress2()) + "," +
			ATTRIBUTE_MARK_FOR_ADDRESS3 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getMarkForAddress3()) + "," +
			ATTRIBUTE_MARK_FOR_CITY + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getMarkForCity()) + "," +
			ATTRIBUTE_MARK_FOR_COUNTRY + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getMarkForCountry()) + "," +
			ATTRIBUTE_MARK_FOR_PARTY_ID + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getMarkForPartyId()) + "," +
			ATTRIBUTE_MARK_FOR_PARTY_NAME + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getMarkForPartyName()) + "," +
			ATTRIBUTE_MARK_FOR_STATE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getMarkForState()) + "," +
			ATTRIBUTE_MARK_FOR_ZIP + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getMarkForZip()) + "," +
			ATTRIBUTE_MFG_PLANT_ID + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getMfgPlantId()) + "," +
			ATTRIBUTE_MFG_PLANT_NAME + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getMfgPlantName()) + "," +
			ATTRIBUTE_MILSTRIP_CODE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getMilstripCode()) + "," +
			ATTRIBUTE_MODEL_YEAR_NUM + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getModelYearNum()) + "," +
			ATTRIBUTE_ORIGINAL_DATE_ISSUED + "=" + 
				DateHandler.getOracleToDateFunction(customerPoStageBean.getOriginalDateIssued()) + "," +
			ATTRIBUTE_ORIGINAL_QTY + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getOriginalQty()) + "," +
			ATTRIBUTE_ORIGINAL_UOM + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getOriginalUom()) + "," +
			ATTRIBUTE_PART_SHORT_DESC + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPartShortDesc()) + "," +
			ATTRIBUTE_PAYER_ADDR + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPayerAddr()) + "," +
			ATTRIBUTE_PAYER_CODE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPayerCode()) + "," +
			ATTRIBUTE_PAYER_NAME + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPayerName()) + "," +
			ATTRIBUTE_POSTAL_SHIPTO_ADDR1 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPostalShiptoAddr1()) + "," +
			ATTRIBUTE_POSTAL_SHIPTO_ADDR2 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPostalShiptoAddr2()) + "," +
			ATTRIBUTE_POSTAL_SHIPTO_ADDR3 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPostalShiptoAddr3()) + "," +
			ATTRIBUTE_POSTAL_SHIPTO_CITY + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPostalShiptoCity()) + "," +
			ATTRIBUTE_POSTAL_SHIPTO_CODE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPostalShiptoCode()) + "," +
			ATTRIBUTE_POSTAL_SHIPTO_NAME + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPostalShiptoName()) + "," +
			ATTRIBUTE_POSTAL_SHIPTO_STATE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPostalShiptoState()) + "," +
			ATTRIBUTE_POSTAL_SHIPTO_ZIP + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPostalShiptoZip()) + "," +
			ATTRIBUTE_PO_SUFFIX + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPoSuffix()) + "," +
			ATTRIBUTE_PRODUCT_CHANGE_NOTICE_NUM + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getProductChangeNoticeNum()) + "," +
			ATTRIBUTE_PRODUCT_GROUP + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getProductGroup()) + "," +
			ATTRIBUTE_PURCHASE_REQUISITION_NUM + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPurchaseRequisitionNum()) + "," +
			ATTRIBUTE_RECEIVING_LOCATION_ID + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getReceivingLocationId()) + "," +
			ATTRIBUTE_RECEIVING_LOCATION_NAME + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getReceivingLocationName()) + "," +
			ATTRIBUTE_RECEIVING_PARTY + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getReceivingParty()) + "," +
			ATTRIBUTE_RECEIVING_PARTY_CODE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getReceivingPartyCode()) + "," +
			ATTRIBUTE_REQUESTED_DELIVERY_DATE_HEADER + "=" + 
				DateHandler.getOracleToDateFunction(customerPoStageBean.getRequestedDeliveryDateHeader()) + "," +
			ATTRIBUTE_SCHEDULE_NOTES + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getScheduleNotes()) + "," +
			ATTRIBUTE_SCHEDULE_QUANTITY + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getScheduleQuantity()) + "," +
			ATTRIBUTE_SCHEDULE_UOM + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getScheduleUom()) + "," +
			ATTRIBUTE_SHIPPING_NOTES + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShippingNotes()) + "," +
			ATTRIBUTE_SHIP_NOTICE_ADDRESS1 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShipNoticeAddress1()) + "," +
			ATTRIBUTE_SHIP_NOTICE_ADDRESS2 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShipNoticeAddress2()) + "," +
			ATTRIBUTE_SHIP_NOTICE_ADDRESS3 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShipNoticeAddress3()) + "," +
			ATTRIBUTE_SHIP_NOTICE_CITY + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShipNoticeCity()) + "," +
			ATTRIBUTE_SHIP_NOTICE_PARTY_ID + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShipNoticePartyId()) + "," +
			ATTRIBUTE_SHIP_NOTICE_PARTY_NAME + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShipNoticePartyName()) + "," +
			ATTRIBUTE_SHIP_NOTICE_STATE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShipNoticeState()) + "," +
			ATTRIBUTE_SHIP_NOTICE_ZIP + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShipNoticeZip()) + "," +
			ATTRIBUTE_SUPPLEMENTARY_ADDR_CODE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getSupplementaryAddrCode()) + "," +
			ATTRIBUTE_TOTAL_LINE_AMT + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getTotalLineAmt()) + "," +
			ATTRIBUTE_TRANSPORTATION_PRIORITY + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getTransportationPriority()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShipToLocationId()) + "," +
			ATTRIBUTE_SHIP_VIA_LOCATION_ID + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getShipViaLocationId()) + "," +
			ATTRIBUTE_SKIP_DODAAC_ADDRESS_CHECK + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getSkipDodaacAddressCheck()) + "," +
			ATTRIBUTE_BUYER_ADDITIONAL_NAME + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getBuyerAdditionalName()) + "," +
			ATTRIBUTE_CONTRACT_OFFICE_ALTERNATE_NAME + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getContractOfficeAlternateName()) + "," +
			ATTRIBUTE_CONTRACT_OFFICE_ADDRESS1 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getContractOfficeAddress1()) + "," +
			ATTRIBUTE_CONTRACT_OFFICE_ADDRESS2 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getContractOfficeAddress2()) + "," +
			ATTRIBUTE_CONTRACT_OFFICE_CITY + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getContractOfficeCity()) + "," +
			ATTRIBUTE_CONTRACT_OFFICE_STATE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getContractOfficeState()) + "," +
			ATTRIBUTE_CONTRACT_OFFICE_ZIP + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getContractOfficeZip()) + "," +
			ATTRIBUTE_CONTRACT_OFFICE_COUNTRY + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getContractOfficeCountry()) + "," +
			ATTRIBUTE_PAYOFFICE_ADDITIONAL_NAME + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPayofficeAdditionalName()) + "," +
			ATTRIBUTE_PAYOFFICE_ADDRESS1 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPayofficeAddress1()) + "," +
			ATTRIBUTE_PAYOFFICE_ADDRESS2 + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPayofficeAddress2()) + "," +
			ATTRIBUTE_PAYOFFICE_CITY + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPayofficeCity()) + "," +
			ATTRIBUTE_PAYOFFICE_STATE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPayofficeState()) + "," +
			ATTRIBUTE_PAYOFFICE_ZIP + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPayofficeZip()) + "," +
			ATTRIBUTE_PAYOFFICE_COUNTRY + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPayofficeCountry()) + "," +
			ATTRIBUTE_PROJECT_CODE + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getProjectCode()) + "," +
			ATTRIBUTE_PRIORITY_RATING + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPriorityRating()) + "," +
			ATTRIBUTE_FMS_CASE_NUM + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getFmsCaseNum()) + "," +
			ATTRIBUTE_PORT_OF_EMBARKATION + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPortOfEmbarkation()) + "," +
			ATTRIBUTE_PORT_OF_DEBARKATION + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getPortOfDebarkation()) + "," +
			ATTRIBUTE_TAC + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getTac()) + "," +
			ATTRIBUTE_TRANSACTION_REF_NUM + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getTransactionRefNum()) + "," +
			ATTRIBUTE_TRANSPORTATION_CONTROL_NUM + "=" + 
				SqlHandler.delimitString(customerPoStageBean.getTransportationControlNum()) + "," +
			ATTRIBUTE_ADDRESS_CHANGE_REQUEST_ID + "=" + 
				StringHandler.nullIfZero(customerPoStageBean.getAddressChangeRequestId()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				customerPoStageBean.getCompanyId();

		return new SqlManager().update(conn, query);
	}
*/
        
   public int updateStatusIgnore(String companyId, String customerPoNo, String customerPoLineNo, String loadId, String loadLine, String processComments)     
        throws BaseException {           
      Connection connection = null;
      int result = 0;
      try {
        connection = getDbManager().getConnection();
        result = updateStatusIgnore(companyId, customerPoNo, customerPoLineNo, loadId, loadLine, processComments, connection);
      }
      finally {
        this.getDbManager().returnConnection(connection);
      }
      return result;
   }
        
   private int updateStatusIgnore(String companyId, String customerPoNo, String customerPoLineNo, String loadId, String loadLine, String processComments, Connection conn)
    throws BaseException {
     String query  = "update " + TABLE + " set " + ATTRIBUTE_STATUS + "= '" + STATUS_IGNORE+ "', " +
                     ATTRIBUTE_PROCESSING_COMMENTS + "= " +  SqlHandler.delimitString(processComments) +
                     " where " + ATTRIBUTE_COMPANY_ID  + "= '" + companyId + "' and " +
                     ATTRIBUTE_CUSTOMER_PO_NO + "= '" + customerPoNo + "' and " + 
                     ATTRIBUTE_CUSTOMER_PO_LINE_NO + "= '" + customerPoLineNo + "' and " + 
                     ATTRIBUTE_LOAD_ID + "= '" + loadId + "' and " + 
                     ATTRIBUTE_LOAD_LINE + "= '" + loadLine + "' and " +
                     ATTRIBUTE_STATUS + "= '" + STATUS_ERROR + "'";
    return new SqlManager().update(conn, query);
       
   }
   
   public int updateMilstrip(String milstripCode, BigDecimal addressChangeRequestId)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = updateMilstrip(milstripCode, addressChangeRequestId, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }


   public int updateMilstrip(String milstripCode, BigDecimal addressChangeRequestId, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_MILSTRIP_CODE + "=" +
      SqlHandler.delimitString(milstripCode) + " " +
     "where " + ATTRIBUTE_ADDRESS_CHANGE_REQUEST_ID  + "=" +
      addressChangeRequestId + " and " + ATTRIBUTE_MILSTRIP_CODE + " is null";
    return new SqlManager().update(conn, query);
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

		Collection customerPoStageBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CustomerPoStageBean customerPoStageBean = new CustomerPoStageBean();
			load(dataSetRow, customerPoStageBean);
			customerPoStageBeanColl.add(customerPoStageBean);
		}

		return customerPoStageBeanColl;
	}
}