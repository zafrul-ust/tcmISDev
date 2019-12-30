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
import com.tcmis.internal.supply.beans.PoHeaderViewBean;


/******************************************************************************
 * CLASSNAME: PoHeaderViewBeanFactory <br>
 * @version: 1.0, May 2, 2014 <br>
 *****************************************************************************/


public class PoHeaderViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_HUB_NAME = "HUB_NAME";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_BO = "BO";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SUPPLIER_CONTACT_ID = "SUPPLIER_CONTACT_ID";
	public String ATTRIBUTE_BUYER = "BUYER";
	public String ATTRIBUTE_SHIP_TO_COMPANY_ID = "SHIP_TO_COMPANY_ID";
	public String ATTRIBUTE_SHIP_TO_FACILITY_ID = "SHIP_TO_FACILITY_ID";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
	public String ATTRIBUTE_FREIGHT_ON_BOARD = "FREIGHT_ON_BOARD";
	public String ATTRIBUTE_PAYMENT_TERMS = "PAYMENT_TERMS";
	public String ATTRIBUTE_CARRIER = "CARRIER";
	public String ATTRIBUTE_DATE_SENT = "DATE_SENT";
	public String ATTRIBUTE_DATE_ACCEPTED = "DATE_ACCEPTED";
	public String ATTRIBUTE_TERMS_AND_CONDITIONS = "TERMS_AND_CONDITIONS";
	public String ATTRIBUTE_BO_START_DATE = "BO_START_DATE";
	public String ATTRIBUTE_BO_END_DATE = "BO_END_DATE";
	public String ATTRIBUTE_CUSTOMER_PO = "CUSTOMER_PO";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_SUPPLIER_CONTACT_NAME = "SUPPLIER_CONTACT_NAME";
	public String ATTRIBUTE_BUYER_NAME = "BUYER_NAME";
	public String ATTRIBUTE_BUYER_FIRST_NAME = "BUYER_FIRST_NAME";
	public String ATTRIBUTE_BUYER_LAST_NAME = "BUYER_LAST_NAME";
	public String ATTRIBUTE_BUYER_FAX = "BUYER_FAX";
	public String ATTRIBUTE_BUYER_EMAIL = "BUYER_EMAIL";
	public String ATTRIBUTE_BUYER_PHONE = "BUYER_PHONE";
	public String ATTRIBUTE_EVER_CONFIRMED = "EVER_CONFIRMED";
	public String ATTRIBUTE_CARRIER_COMPANY_ID = "CARRIER_COMPANY_ID";
	public String ATTRIBUTE_CARRIER_ACCOUNT = "CARRIER_ACCOUNT";
	public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
	public String ATTRIBUTE_CARRIER_HUB = "CARRIER_HUB";
	public String ATTRIBUTE_SUPPLIER_COUNTRY_ABBREV = "SUPPLIER_COUNTRY_ABBREV";
	public String ATTRIBUTE_SUPPLIER_STATE_ABBREV = "SUPPLIER_STATE_ABBREV";
	public String ATTRIBUTE_SUPPLIER_ADDRESS_LINE_1 = "SUPPLIER_ADDRESS_LINE_1";
	public String ATTRIBUTE_SUPPLIER_ADDRESS_LINE_2 = "SUPPLIER_ADDRESS_LINE_2";
	public String ATTRIBUTE_SUPPLIER_ADDRESS_LINE_3 = "SUPPLIER_ADDRESS_LINE_3";
	public String ATTRIBUTE_SUPPLIER_CITY = "SUPPLIER_CITY";
	public String ATTRIBUTE_SUPPLIER_ZIP = "SUPPLIER_ZIP";
	public String ATTRIBUTE_SUPPLIER_LOCATION_DESC = "SUPPLIER_LOCATION_DESC";
	public String ATTRIBUTE_SHIPTO_COUNTRY_ABBREV = "SHIPTO_COUNTRY_ABBREV";
	public String ATTRIBUTE_SHIPTO_STATE_ABBREV = "SHIPTO_STATE_ABBREV";
	public String ATTRIBUTE_SHIPTO_ADDRESS_LINE_1 = "SHIPTO_ADDRESS_LINE_1";
	public String ATTRIBUTE_SHIPTO_ADDRESS_LINE_2 = "SHIPTO_ADDRESS_LINE_2";
	public String ATTRIBUTE_SHIPTO_ADDRESS_LINE_3 = "SHIPTO_ADDRESS_LINE_3";
	public String ATTRIBUTE_SHIPTO_CITY = "SHIPTO_CITY";
	public String ATTRIBUTE_SHIPTO_ZIP = "SHIPTO_ZIP";
	public String ATTRIBUTE_SHIPTO_LOCATION_DESC = "SHIPTO_LOCATION_DESC";
	public String ATTRIBUTE_SUPPLIER_PHONE = "SUPPLIER_PHONE";
	public String ATTRIBUTE_SUPPLIER_CONTACT_PHONE = "SUPPLIER_CONTACT_PHONE";
	public String ATTRIBUTE_SUPPLIER_CONTACT_FAX = "SUPPLIER_CONTACT_FAX";
	public String ATTRIBUTE_CRITICAL = "CRITICAL";
	public String ATTRIBUTE_QUALIFICATION_LEVEL = "QUALIFICATION_LEVEL";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_ATTACHED_PR = "ATTACHED_PR";
	public String ATTRIBUTE_HAAS_CARRIER = "HAAS_CARRIER";
	public String ATTRIBUTE_CARRIER_SUPPLIER_ID = "CARRIER_SUPPLIER_ID";
	public String ATTRIBUTE_CONSIGNED_PO = "CONSIGNED_PO";
	public String ATTRIBUTE_VOUCHERED = "VOUCHERED";
	public String ATTRIBUTE_DBUY_LOCK_STATUS = "DBUY_LOCK_STATUS";
	public String ATTRIBUTE_RECEIVED_STATUS = "RECEIVED_STATUS";
	public String ATTRIBUTE_EMAIL = "EMAIL";
	public String ATTRIBUTE_SUPPLIER_EMAIL = "SUPPLIER_EMAIL";
	public String ATTRIBUTE_PO_PRICE = "PO_PRICE";
	public String ATTRIBUTE_PO_PRICE_OE = "PO_PRICE_OE";
	public String ATTRIBUTE_TRANSPORT = "TRANSPORT";
	public String ATTRIBUTE_PO_IMAGE_URL = "PO_IMAGE_URL";
	public String ATTRIBUTE_OPERATING_ENTITY_NAME = "OPERATING_ENTITY_NAME";
	public String ATTRIBUTE_BILL_TO_COMPANY_ID = "BILL_TO_COMPANY_ID";
	public String ATTRIBUTE_BILL_TO_LOCATION_ID = "BILL_TO_LOCATION_ID";
	public String ATTRIBUTE_OPS_ENTITY_ALIAS = "OPS_ENTITY_ALIAS";
	public String ATTRIBUTE_DATE_LAST_ACKNOWLEDGEMENT = "DATE_LAST_ACKNOWLEDGEMENT";
	public String ATTRIBUTE_DAYS_SINCE_LAST_STATUS = "DAYS_SINCE_LAST_STATUS";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_PO_TERMS_AND_CONDITIONS = "PO_TERMS_AND_CONDITIONS";
	public String ATTRIBUTE_ASSIGN_CHARGES = "ASSIGN_CHARGES";
	public String ATTRIBUTE_CHANGE_SUPPLIER = "CHANGE_SUPPLIER";
	public String ATTRIBUTE_BUYER_COMPANY_ID = "BUYER_COMPANY_ID";
	public String ATTRIBUTE_NONINTEGER_RECEIVING = "NONINTEGER_RECEIVING";
	public String ATTRIBUTE_STATE_TAX_ID = "STATE_TAX_ID";
	public String ATTRIBUTE_FEDERAL_TAX_ID = "FEDERAL_TAX_ID";
	public String ATTRIBUTE_HOME_COMPANY_TAX_ID = "HOME_COMPANY_TAX_ID";
	public String ATTRIBUTE_INVENTORY_GROUP_STATE_TAX_ID = "INVENTORY_GROUP_STATE_TAX_ID";
	public String ATTRIBUTE_OPERATIONAL_TAX_ID = "OPERATIONAL_TAX_ID";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_CODE = "SHIP_TO_ADDRESS_CODE";
	public String ATTRIBUTE_SUPPLIER_DEFAULT_PAYMENT_TERMS = "SUPPLIER_DEFAULT_PAYMENT_TERMS";
	public String ATTRIBUTE_SHIP_TO_DISPLAY_ADDRESS = "SHIP_TO_DISPLAY_ADDRESS";
	public String ATTRIBUTE_SUPPLIER_DISPLAY_ADDRESS = "SUPPLIER_DISPLAY_ADDRESS";
	public String ATTRIBUTE_INTERCOMPANY_MR = "INTERCOMPANY_MR";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1_DISPLAY = "SHIP_TO_ADDRESS_LINE_1_DISPLAY";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2_DISPLAY = "SHIP_TO_ADDRESS_LINE_2_DISPLAY";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3_DISPLAY = "SHIP_TO_ADDRESS_LINE_3_DISPLAY";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4_DISPLAY = "SHIP_TO_ADDRESS_LINE_4_DISPLAY";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_5_DISPLAY = "SHIP_TO_ADDRESS_LINE_5_DISPLAY";
	public String ATTRIBUTE_SUPPLR_ADDRESS_LINE_1_DISPLAY = "SUPPLR_ADDRESS_LINE_1_DISPLAY";
	public String ATTRIBUTE_SUPPLR_ADDRESS_LINE_2_DISPLAY = "SUPPLR_ADDRESS_LINE_2_DISPLAY";
	public String ATTRIBUTE_SUPPLR_ADDRESS_LINE_3_DISPLAY = "SUPPLR_ADDRESS_LINE_3_DISPLAY";
	public String ATTRIBUTE_SUPPLR_ADDRESS_LINE_4_DISPLAY = "SUPPLR_ADDRESS_LINE_4_DISPLAY";
	public String ATTRIBUTE_SUPPLR_ADDRESS_LINE_5_DISPLAY = "SUPPLR_ADDRESS_LINE_5_DISPLAY";
	public String ATTRIBUTE_REQUIRES_FINANCIAL_APPROVAL = "REQUIRES_FINANCIAL_APPROVAL";
	public String ATTRIBUTE_PO_APPROVAL_STATUS = "PO_APPROVAL_STATUS";

	//table name
	public String TABLE = "PO_HEADER_VIEW";


	//constructor
	public PoHeaderViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("hubName")) {
			return ATTRIBUTE_HUB_NAME;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("bo")) {
			return ATTRIBUTE_BO;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("supplierContactId")) {
			return ATTRIBUTE_SUPPLIER_CONTACT_ID;
		}
		else if(attributeName.equals("buyer")) {
			return ATTRIBUTE_BUYER;
		}
		else if(attributeName.equals("shipToCompanyId")) {
			return ATTRIBUTE_SHIP_TO_COMPANY_ID;
		}
		else if(attributeName.equals("shipToFacilityId")) {
			return ATTRIBUTE_SHIP_TO_FACILITY_ID;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("branchPlant")) {
			return ATTRIBUTE_BRANCH_PLANT;
		}
		else if(attributeName.equals("freightOnBoard")) {
			return ATTRIBUTE_FREIGHT_ON_BOARD;
		}
		else if(attributeName.equals("paymentTerms")) {
			return ATTRIBUTE_PAYMENT_TERMS;
		}
		else if(attributeName.equals("carrier")) {
			return ATTRIBUTE_CARRIER;
		}
		else if(attributeName.equals("dateSent")) {
			return ATTRIBUTE_DATE_SENT;
		}
		else if(attributeName.equals("dateAccepted")) {
			return ATTRIBUTE_DATE_ACCEPTED;
		}
		else if(attributeName.equals("termsAndConditions")) {
			return ATTRIBUTE_TERMS_AND_CONDITIONS;
		}
		else if(attributeName.equals("boStartDate")) {
			return ATTRIBUTE_BO_START_DATE;
		}
		else if(attributeName.equals("boEndDate")) {
			return ATTRIBUTE_BO_END_DATE;
		}
		else if(attributeName.equals("customerPo")) {
			return ATTRIBUTE_CUSTOMER_PO;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("supplierContactName")) {
			return ATTRIBUTE_SUPPLIER_CONTACT_NAME;
		}
		else if(attributeName.equals("buyerName")) {
			return ATTRIBUTE_BUYER_NAME;
		}
		else if(attributeName.equals("buyerFirstName")) {
			return ATTRIBUTE_BUYER_FIRST_NAME;
		}
		else if(attributeName.equals("buyerLastName")) {
			return ATTRIBUTE_BUYER_LAST_NAME;
		}
		else if(attributeName.equals("buyerFax")) {
			return ATTRIBUTE_BUYER_FAX;
		}
		else if(attributeName.equals("buyerEmail")) {
			return ATTRIBUTE_BUYER_EMAIL;
		}
		else if(attributeName.equals("buyerPhone")) {
			return ATTRIBUTE_BUYER_PHONE;
		}
		else if(attributeName.equals("everConfirmed")) {
			return ATTRIBUTE_EVER_CONFIRMED;
		}
		else if(attributeName.equals("carrierCompanyId")) {
			return ATTRIBUTE_CARRIER_COMPANY_ID;
		}
		else if(attributeName.equals("carrierAccount")) {
			return ATTRIBUTE_CARRIER_ACCOUNT;
		}
		else if(attributeName.equals("carrierName")) {
			return ATTRIBUTE_CARRIER_NAME;
		}
		else if(attributeName.equals("carrierHub")) {
			return ATTRIBUTE_CARRIER_HUB;
		}
		else if(attributeName.equals("supplierCountryAbbrev")) {
			return ATTRIBUTE_SUPPLIER_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("supplierStateAbbrev")) {
			return ATTRIBUTE_SUPPLIER_STATE_ABBREV;
		}
		else if(attributeName.equals("supplierAddressLine1")) {
			return ATTRIBUTE_SUPPLIER_ADDRESS_LINE_1;
		}
		else if(attributeName.equals("supplierAddressLine2")) {
			return ATTRIBUTE_SUPPLIER_ADDRESS_LINE_2;
		}
		else if(attributeName.equals("supplierAddressLine3")) {
			return ATTRIBUTE_SUPPLIER_ADDRESS_LINE_3;
		}
		else if(attributeName.equals("supplierCity")) {
			return ATTRIBUTE_SUPPLIER_CITY;
		}
		else if(attributeName.equals("supplierZip")) {
			return ATTRIBUTE_SUPPLIER_ZIP;
		}
		else if(attributeName.equals("supplierLocationDesc")) {
			return ATTRIBUTE_SUPPLIER_LOCATION_DESC;
		}
		else if(attributeName.equals("shiptoCountryAbbrev")) {
			return ATTRIBUTE_SHIPTO_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("shiptoStateAbbrev")) {
			return ATTRIBUTE_SHIPTO_STATE_ABBREV;
		}
		else if(attributeName.equals("shiptoAddressLine1")) {
			return ATTRIBUTE_SHIPTO_ADDRESS_LINE_1;
		}
		else if(attributeName.equals("shiptoAddressLine2")) {
			return ATTRIBUTE_SHIPTO_ADDRESS_LINE_2;
		}
		else if(attributeName.equals("shiptoAddressLine3")) {
			return ATTRIBUTE_SHIPTO_ADDRESS_LINE_3;
		}
		else if(attributeName.equals("shiptoCity")) {
			return ATTRIBUTE_SHIPTO_CITY;
		}
		else if(attributeName.equals("shiptoZip")) {
			return ATTRIBUTE_SHIPTO_ZIP;
		}
		else if(attributeName.equals("shiptoLocationDesc")) {
			return ATTRIBUTE_SHIPTO_LOCATION_DESC;
		}
		else if(attributeName.equals("supplierPhone")) {
			return ATTRIBUTE_SUPPLIER_PHONE;
		}
		else if(attributeName.equals("supplierContactPhone")) {
			return ATTRIBUTE_SUPPLIER_CONTACT_PHONE;
		}
		else if(attributeName.equals("supplierContactFax")) {
			return ATTRIBUTE_SUPPLIER_CONTACT_FAX;
		}
		else if(attributeName.equals("critical")) {
			return ATTRIBUTE_CRITICAL;
		}
		else if(attributeName.equals("qualificationLevel")) {
			return ATTRIBUTE_QUALIFICATION_LEVEL;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("attachedPr")) {
			return ATTRIBUTE_ATTACHED_PR;
		}
		else if(attributeName.equals("haasCarrier")) {
			return ATTRIBUTE_HAAS_CARRIER;
		}
		else if(attributeName.equals("carrierSupplierId")) {
			return ATTRIBUTE_CARRIER_SUPPLIER_ID;
		}
		else if(attributeName.equals("consignedPo")) {
			return ATTRIBUTE_CONSIGNED_PO;
		}
		else if(attributeName.equals("vouchered")) {
			return ATTRIBUTE_VOUCHERED;
		}
		else if(attributeName.equals("dbuyLockStatus")) {
			return ATTRIBUTE_DBUY_LOCK_STATUS;
		}
		else if(attributeName.equals("receivedStatus")) {
			return ATTRIBUTE_RECEIVED_STATUS;
		}
		else if(attributeName.equals("email")) {
			return ATTRIBUTE_EMAIL;
		}
		else if(attributeName.equals("supplierEmail")) {
			return ATTRIBUTE_SUPPLIER_EMAIL;
		}
		else if(attributeName.equals("poPrice")) {
			return ATTRIBUTE_PO_PRICE;
		}
		else if(attributeName.equals("poPriceOe")) {
			return ATTRIBUTE_PO_PRICE_OE;
		}
		else if(attributeName.equals("transport")) {
			return ATTRIBUTE_TRANSPORT;
		}
		else if(attributeName.equals("poImageUrl")) {
			return ATTRIBUTE_PO_IMAGE_URL;
		}
		else if(attributeName.equals("operatingEntityName")) {
			return ATTRIBUTE_OPERATING_ENTITY_NAME;
		}
		else if(attributeName.equals("billToCompanyId")) {
			return ATTRIBUTE_BILL_TO_COMPANY_ID;
		}
		else if(attributeName.equals("billToLocationId")) {
			return ATTRIBUTE_BILL_TO_LOCATION_ID;
		}
		else if(attributeName.equals("opsEntityAlias")) {
			return ATTRIBUTE_OPS_ENTITY_ALIAS;
		}
		else if(attributeName.equals("dateLastAcknowledgement")) {
			return ATTRIBUTE_DATE_LAST_ACKNOWLEDGEMENT;
		}
		else if(attributeName.equals("daysSinceLastStatus")) {
			return ATTRIBUTE_DAYS_SINCE_LAST_STATUS;
		}
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("poTermsAndConditions")) {
			return ATTRIBUTE_PO_TERMS_AND_CONDITIONS;
		}
		else if(attributeName.equals("assignCharges")) {
			return ATTRIBUTE_ASSIGN_CHARGES;
		}
		else if(attributeName.equals("changeSupplier")) {
			return ATTRIBUTE_CHANGE_SUPPLIER;
		}
		else if(attributeName.equals("buyerCompanyId")) {
			return ATTRIBUTE_BUYER_COMPANY_ID;
		}
		else if(attributeName.equals("nonintegerReceiving")) {
			return ATTRIBUTE_NONINTEGER_RECEIVING;
		}
		else if(attributeName.equals("stateTaxId")) {
			return ATTRIBUTE_STATE_TAX_ID;
		}
		else if(attributeName.equals("federalTaxId")) {
			return ATTRIBUTE_FEDERAL_TAX_ID;
		}
		else if(attributeName.equals("homeCompanyTaxId")) {
			return ATTRIBUTE_HOME_COMPANY_TAX_ID;
		}
		else if(attributeName.equals("inventoryGroupStateTaxId")) {
			return ATTRIBUTE_INVENTORY_GROUP_STATE_TAX_ID;
		}
		else if(attributeName.equals("operationalTaxId")) {
			return ATTRIBUTE_OPERATIONAL_TAX_ID;
		}
		else if(attributeName.equals("shipToAddressCode")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_CODE;
		}
		else if(attributeName.equals("supplierDefaultPaymentTerms")) {
			return ATTRIBUTE_SUPPLIER_DEFAULT_PAYMENT_TERMS;
		}
		else if(attributeName.equals("shipToDisplayAddress")) {
			return ATTRIBUTE_SHIP_TO_DISPLAY_ADDRESS;
		}
		else if(attributeName.equals("supplierDisplayAddress")) {
			return ATTRIBUTE_SUPPLIER_DISPLAY_ADDRESS;
		}
		else if(attributeName.equals("intercompanyMr")) {
			return ATTRIBUTE_INTERCOMPANY_MR;
		}
		else if(attributeName.equals("shipToAddressLine1Display")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1_DISPLAY;
		}
		else if(attributeName.equals("shipToAddressLine2Display")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2_DISPLAY;
		}
		else if(attributeName.equals("shipToAddressLine3Display")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3_DISPLAY;
		}
		else if(attributeName.equals("shipToAddressLine4Display")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4_DISPLAY;
		}
		else if(attributeName.equals("shipToAddressLine5Display")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_5_DISPLAY;
		}
		else if(attributeName.equals("supplrAddressLine1Display")) {
			return ATTRIBUTE_SUPPLR_ADDRESS_LINE_1_DISPLAY;
		}
		else if(attributeName.equals("supplrAddressLine2Display")) {
			return ATTRIBUTE_SUPPLR_ADDRESS_LINE_2_DISPLAY;
		}
		else if(attributeName.equals("supplrAddressLine3Display")) {
			return ATTRIBUTE_SUPPLR_ADDRESS_LINE_3_DISPLAY;
		}
		else if(attributeName.equals("supplrAddressLine4Display")) {
			return ATTRIBUTE_SUPPLR_ADDRESS_LINE_4_DISPLAY;
		}
		else if(attributeName.equals("supplrAddressLine5Display")) {
			return ATTRIBUTE_SUPPLR_ADDRESS_LINE_5_DISPLAY;
		}
		else if(attributeName.equals("requiresFinancialApproval")) {
			return ATTRIBUTE_REQUIRES_FINANCIAL_APPROVAL;
		}
		else if (attributeName.equals("poApprovalStatus")) {
			return ATTRIBUTE_PO_APPROVAL_STATUS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PoHeaderViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PoHeaderViewBean poHeaderViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("hubName", "SearchCriterion.EQUALS",
			"" + poHeaderViewBean.getHubName());

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


	public int delete(PoHeaderViewBean poHeaderViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("hubName", "SearchCriterion.EQUALS",
			"" + poHeaderViewBean.getHubName());

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

    //update call p_po_allocate
  public void allocatePOLine(PoHeaderViewBean inputBean)
  throws BaseException
  {
    Connection connection = this.getDbManager().getConnection();
		Collection cin = new Vector();

    cin.add(inputBean.getRadianPo());
   
    this.getDbManager().doProcedure("p_po_allocate", cin);
		this.getDbManager().returnConnection(connection);
  }
//you need to verify the primary key(s) before uncommenting this
/*
	//insert
	public int insert(PoHeaderViewBean poHeaderViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(poHeaderViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PoHeaderViewBean poHeaderViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_HUB_NAME + "," +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_BO + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_ID + "," +
			ATTRIBUTE_BUYER + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "," +
			ATTRIBUTE_SHIP_TO_FACILITY_ID + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_BRANCH_PLANT + "," +
			ATTRIBUTE_FREIGHT_ON_BOARD + "," +
			ATTRIBUTE_PAYMENT_TERMS + "," +
			ATTRIBUTE_CARRIER + "," +
			ATTRIBUTE_DATE_SENT + "," +
			ATTRIBUTE_DATE_ACCEPTED + "," +
			ATTRIBUTE_TERMS_AND_CONDITIONS + "," +
			ATTRIBUTE_BO_START_DATE + "," +
			ATTRIBUTE_BO_END_DATE + "," +
			ATTRIBUTE_CUSTOMER_PO + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_NAME + "," +
			ATTRIBUTE_BUYER_NAME + "," +
			ATTRIBUTE_BUYER_FIRST_NAME + "," +
			ATTRIBUTE_BUYER_LAST_NAME + "," +
			ATTRIBUTE_BUYER_FAX + "," +
			ATTRIBUTE_BUYER_EMAIL + "," +
			ATTRIBUTE_BUYER_PHONE + "," +
			ATTRIBUTE_EVER_CONFIRMED + "," +
			ATTRIBUTE_CARRIER_COMPANY_ID + "," +
			ATTRIBUTE_CARRIER_ACCOUNT + "," +
			ATTRIBUTE_CARRIER_NAME + "," +
			ATTRIBUTE_CARRIER_HUB + "," +
			ATTRIBUTE_SUPPLIER_COUNTRY_ABBREV + "," +
			ATTRIBUTE_SUPPLIER_STATE_ABBREV + "," +
			ATTRIBUTE_SUPPLIER_ADDRESS_LINE_1 + "," +
			ATTRIBUTE_SUPPLIER_ADDRESS_LINE_2 + "," +
			ATTRIBUTE_SUPPLIER_ADDRESS_LINE_3 + "," +
			ATTRIBUTE_SUPPLIER_CITY + "," +
			ATTRIBUTE_SUPPLIER_ZIP + "," +
			ATTRIBUTE_SUPPLIER_LOCATION_DESC + "," +
			ATTRIBUTE_SHIPTO_COUNTRY_ABBREV + "," +
			ATTRIBUTE_SHIPTO_STATE_ABBREV + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_1 + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_2 + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_3 + "," +
			ATTRIBUTE_SHIPTO_CITY + "," +
			ATTRIBUTE_SHIPTO_ZIP + "," +
			ATTRIBUTE_SHIPTO_LOCATION_DESC + "," +
			ATTRIBUTE_SUPPLIER_PHONE + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_PHONE + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_FAX + "," +
			ATTRIBUTE_CRITICAL + "," +
			ATTRIBUTE_QUALIFICATION_LEVEL + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_ATTACHED_PR + "," +
			ATTRIBUTE_HAAS_CARRIER + "," +
			ATTRIBUTE_CARRIER_SUPPLIER_ID + "," +
			ATTRIBUTE_CONSIGNED_PO + "," +
			ATTRIBUTE_VOUCHERED + "," +
			ATTRIBUTE_DBUY_LOCK_STATUS + "," +
			ATTRIBUTE_RECEIVED_STATUS + "," +
			ATTRIBUTE_EMAIL + "," +
			ATTRIBUTE_SUPPLIER_EMAIL + "," +
			ATTRIBUTE_PO_PRICE + "," +
			ATTRIBUTE_PO_PRICE_OE + "," +
			ATTRIBUTE_TRANSPORT + "," +
			ATTRIBUTE_PO_IMAGE_URL + "," +
			ATTRIBUTE_OPERATING_ENTITY_NAME + "," +
			ATTRIBUTE_BILL_TO_COMPANY_ID + "," +
			ATTRIBUTE_BILL_TO_LOCATION_ID + "," +
			ATTRIBUTE_OPS_ENTITY_ALIAS + "," +
			ATTRIBUTE_DATE_LAST_ACKNOWLEDGEMENT + "," +
			ATTRIBUTE_DAYS_SINCE_LAST_STATUS + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "," +
			ATTRIBUTE_PO_TERMS_AND_CONDITIONS + "," +
			ATTRIBUTE_ASSIGN_CHARGES + "," +
			ATTRIBUTE_CHANGE_SUPPLIER + "," +
			ATTRIBUTE_BUYER_COMPANY_ID + "," +
			ATTRIBUTE_NONINTEGER_RECEIVING + "," +
			ATTRIBUTE_STATE_TAX_ID + "," +
			ATTRIBUTE_FEDERAL_TAX_ID + "," +
			ATTRIBUTE_HOME_COMPANY_TAX_ID + "," +
			ATTRIBUTE_INVENTORY_GROUP_STATE_TAX_ID + "," +
			ATTRIBUTE_OPERATIONAL_TAX_ID + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_CODE + "," +
			ATTRIBUTE_SUPPLIER_DEFAULT_PAYMENT_TERMS + "," +
			ATTRIBUTE_SHIP_TO_DISPLAY_ADDRESS + "," +
			ATTRIBUTE_SUPPLIER_DISPLAY_ADDRESS + "," +
			ATTRIBUTE_INTERCOMPANY_MR + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1_DISPLAY + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2_DISPLAY + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3_DISPLAY + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4_DISPLAY + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_5_DISPLAY + "," +
			ATTRIBUTE_SUPPLR_ADDRESS_LINE_1_DISPLAY + "," +
			ATTRIBUTE_SUPPLR_ADDRESS_LINE_2_DISPLAY + "," +
			ATTRIBUTE_SUPPLR_ADDRESS_LINE_3_DISPLAY + "," +
			ATTRIBUTE_SUPPLR_ADDRESS_LINE_4_DISPLAY + "," +
			ATTRIBUTE_SUPPLR_ADDRESS_LINE_5_DISPLAY + ")" +
			" values (" +
			SqlHandler.delimitString(poHeaderViewBean.getHubName()) + "," +
			poHeaderViewBean.getRadianPo() + "," +
			poHeaderViewBean.getBo() + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplier()) + "," +
			poHeaderViewBean.getSupplierContactId() + "," +
			poHeaderViewBean.getBuyer() + "," +
			SqlHandler.delimitString(poHeaderViewBean.getShipToCompanyId()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getShipToFacilityId()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getShipToLocationId()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getBranchPlant()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getFreightOnBoard()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getPaymentTerms()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getCarrier()) + "," +
			DateHandler.getOracleToDateFunction(poHeaderViewBean.getDateSent()) + "," +
			DateHandler.getOracleToDateFunction(poHeaderViewBean.getDateAccepted()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getTermsAndConditions()) + "," +
			DateHandler.getOracleToDateFunction(poHeaderViewBean.getBoStartDate()) + "," +
			DateHandler.getOracleToDateFunction(poHeaderViewBean.getBoEndDate()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getCustomerPo()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplierName()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplierContactName()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getBuyerName()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getBuyerFirstName()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getBuyerLastName()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getBuyerFax()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getBuyerEmail()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getBuyerPhone()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getEverConfirmed()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getCarrierCompanyId()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getCarrierAccount()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getCarrierName()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getCarrierHub()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplierCountryAbbrev()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplierStateAbbrev()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplierAddressLine1()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplierAddressLine2()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplierAddressLine3()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplierCity()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplierZip()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplierLocationDesc()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getShiptoCountryAbbrev()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getShiptoStateAbbrev()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getShiptoAddressLine1()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getShiptoAddressLine2()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getShiptoAddressLine3()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getShiptoCity()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getShiptoZip()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getShiptoLocationDesc()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplierPhone()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplierContactPhone()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplierContactFax()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getCritical()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getQualificationLevel()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getAttachedPr()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getHaasCarrier()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getCarrierSupplierId()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getConsignedPo()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getVouchered()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getDbuyLockStatus()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getReceivedStatus()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getEmail()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplierEmail()) + "," +
			poHeaderViewBean.getPoPrice() + "," +
			poHeaderViewBean.getPoPriceOe() + "," +
			SqlHandler.delimitString(poHeaderViewBean.getTransport()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getPoImageUrl()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getOperatingEntityName()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getBillToCompanyId()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getBillToLocationId()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getOpsEntityAlias()) + "," +
			DateHandler.getOracleToDateFunction(poHeaderViewBean.getDateLastAcknowledgement()) + "," +
			poHeaderViewBean.getDaysSinceLastStatus() + "," +
			SqlHandler.delimitString(poHeaderViewBean.getOpsEntityId()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getPoTermsAndConditions()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getAssignCharges()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getChangeSupplier()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getBuyerCompanyId()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getNonintegerReceiving()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getStateTaxId()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getFederalTaxId()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getHomeCompanyTaxId()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getInventoryGroupStateTaxId()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getOperationalTaxId()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getShipToAddressCode()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplierDefaultPaymentTerms()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getShipToDisplayAddress()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplierDisplayAddress()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getIntercompanyMr()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getShipToAddressLine1Display()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getShipToAddressLine2Display()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getShipToAddressLine3Display()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getShipToAddressLine4Display()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getShipToAddressLine5Display()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplrAddressLine1Display()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplrAddressLine2Display()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplrAddressLine3Display()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplrAddressLine4Display()) + "," +
			SqlHandler.delimitString(poHeaderViewBean.getSupplrAddressLine5Display()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PoHeaderViewBean poHeaderViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(poHeaderViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PoHeaderViewBean poHeaderViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_HUB_NAME + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getHubName()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(poHeaderViewBean.getRadianPo()) + "," +
			ATTRIBUTE_BO + "=" + 
				StringHandler.nullIfZero(poHeaderViewBean.getBo()) + "," +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplier()) + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_ID + "=" + 
				StringHandler.nullIfZero(poHeaderViewBean.getSupplierContactId()) + "," +
			ATTRIBUTE_BUYER + "=" + 
				StringHandler.nullIfZero(poHeaderViewBean.getBuyer()) + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getShipToCompanyId()) + "," +
			ATTRIBUTE_SHIP_TO_FACILITY_ID + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getShipToFacilityId()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getShipToLocationId()) + "," +
			ATTRIBUTE_BRANCH_PLANT + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getBranchPlant()) + "," +
			ATTRIBUTE_FREIGHT_ON_BOARD + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getFreightOnBoard()) + "," +
			ATTRIBUTE_PAYMENT_TERMS + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getPaymentTerms()) + "," +
			ATTRIBUTE_CARRIER + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getCarrier()) + "," +
			ATTRIBUTE_DATE_SENT + "=" + 
				DateHandler.getOracleToDateFunction(poHeaderViewBean.getDateSent()) + "," +
			ATTRIBUTE_DATE_ACCEPTED + "=" + 
				DateHandler.getOracleToDateFunction(poHeaderViewBean.getDateAccepted()) + "," +
			ATTRIBUTE_TERMS_AND_CONDITIONS + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getTermsAndConditions()) + "," +
			ATTRIBUTE_BO_START_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poHeaderViewBean.getBoStartDate()) + "," +
			ATTRIBUTE_BO_END_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poHeaderViewBean.getBoEndDate()) + "," +
			ATTRIBUTE_CUSTOMER_PO + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getCustomerPo()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplierName()) + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_NAME + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplierContactName()) + "," +
			ATTRIBUTE_BUYER_NAME + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getBuyerName()) + "," +
			ATTRIBUTE_BUYER_FIRST_NAME + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getBuyerFirstName()) + "," +
			ATTRIBUTE_BUYER_LAST_NAME + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getBuyerLastName()) + "," +
			ATTRIBUTE_BUYER_FAX + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getBuyerFax()) + "," +
			ATTRIBUTE_BUYER_EMAIL + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getBuyerEmail()) + "," +
			ATTRIBUTE_BUYER_PHONE + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getBuyerPhone()) + "," +
			ATTRIBUTE_EVER_CONFIRMED + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getEverConfirmed()) + "," +
			ATTRIBUTE_CARRIER_COMPANY_ID + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getCarrierCompanyId()) + "," +
			ATTRIBUTE_CARRIER_ACCOUNT + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getCarrierAccount()) + "," +
			ATTRIBUTE_CARRIER_NAME + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getCarrierName()) + "," +
			ATTRIBUTE_CARRIER_HUB + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getCarrierHub()) + "," +
			ATTRIBUTE_SUPPLIER_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplierCountryAbbrev()) + "," +
			ATTRIBUTE_SUPPLIER_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplierStateAbbrev()) + "," +
			ATTRIBUTE_SUPPLIER_ADDRESS_LINE_1 + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplierAddressLine1()) + "," +
			ATTRIBUTE_SUPPLIER_ADDRESS_LINE_2 + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplierAddressLine2()) + "," +
			ATTRIBUTE_SUPPLIER_ADDRESS_LINE_3 + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplierAddressLine3()) + "," +
			ATTRIBUTE_SUPPLIER_CITY + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplierCity()) + "," +
			ATTRIBUTE_SUPPLIER_ZIP + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplierZip()) + "," +
			ATTRIBUTE_SUPPLIER_LOCATION_DESC + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplierLocationDesc()) + "," +
			ATTRIBUTE_SHIPTO_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getShiptoCountryAbbrev()) + "," +
			ATTRIBUTE_SHIPTO_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getShiptoStateAbbrev()) + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_1 + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getShiptoAddressLine1()) + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_2 + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getShiptoAddressLine2()) + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_3 + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getShiptoAddressLine3()) + "," +
			ATTRIBUTE_SHIPTO_CITY + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getShiptoCity()) + "," +
			ATTRIBUTE_SHIPTO_ZIP + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getShiptoZip()) + "," +
			ATTRIBUTE_SHIPTO_LOCATION_DESC + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getShiptoLocationDesc()) + "," +
			ATTRIBUTE_SUPPLIER_PHONE + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplierPhone()) + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_PHONE + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplierContactPhone()) + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_FAX + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplierContactFax()) + "," +
			ATTRIBUTE_CRITICAL + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getCritical()) + "," +
			ATTRIBUTE_QUALIFICATION_LEVEL + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getQualificationLevel()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_ATTACHED_PR + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getAttachedPr()) + "," +
			ATTRIBUTE_HAAS_CARRIER + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getHaasCarrier()) + "," +
			ATTRIBUTE_CARRIER_SUPPLIER_ID + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getCarrierSupplierId()) + "," +
			ATTRIBUTE_CONSIGNED_PO + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getConsignedPo()) + "," +
			ATTRIBUTE_VOUCHERED + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getVouchered()) + "," +
			ATTRIBUTE_DBUY_LOCK_STATUS + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getDbuyLockStatus()) + "," +
			ATTRIBUTE_RECEIVED_STATUS + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getReceivedStatus()) + "," +
			ATTRIBUTE_EMAIL + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getEmail()) + "," +
			ATTRIBUTE_SUPPLIER_EMAIL + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplierEmail()) + "," +
			ATTRIBUTE_PO_PRICE + "=" + 
				StringHandler.nullIfZero(poHeaderViewBean.getPoPrice()) + "," +
			ATTRIBUTE_PO_PRICE_OE + "=" + 
				StringHandler.nullIfZero(poHeaderViewBean.getPoPriceOe()) + "," +
			ATTRIBUTE_TRANSPORT + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getTransport()) + "," +
			ATTRIBUTE_PO_IMAGE_URL + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getPoImageUrl()) + "," +
			ATTRIBUTE_OPERATING_ENTITY_NAME + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getOperatingEntityName()) + "," +
			ATTRIBUTE_BILL_TO_COMPANY_ID + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getBillToCompanyId()) + "," +
			ATTRIBUTE_BILL_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getBillToLocationId()) + "," +
			ATTRIBUTE_OPS_ENTITY_ALIAS + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getOpsEntityAlias()) + "," +
			ATTRIBUTE_DATE_LAST_ACKNOWLEDGEMENT + "=" + 
				DateHandler.getOracleToDateFunction(poHeaderViewBean.getDateLastAcknowledgement()) + "," +
			ATTRIBUTE_DAYS_SINCE_LAST_STATUS + "=" + 
				StringHandler.nullIfZero(poHeaderViewBean.getDaysSinceLastStatus()) + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getOpsEntityId()) + "," +
			ATTRIBUTE_PO_TERMS_AND_CONDITIONS + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getPoTermsAndConditions()) + "," +
			ATTRIBUTE_ASSIGN_CHARGES + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getAssignCharges()) + "," +
			ATTRIBUTE_CHANGE_SUPPLIER + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getChangeSupplier()) + "," +
			ATTRIBUTE_BUYER_COMPANY_ID + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getBuyerCompanyId()) + "," +
			ATTRIBUTE_NONINTEGER_RECEIVING + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getNonintegerReceiving()) + "," +
			ATTRIBUTE_STATE_TAX_ID + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getStateTaxId()) + "," +
			ATTRIBUTE_FEDERAL_TAX_ID + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getFederalTaxId()) + "," +
			ATTRIBUTE_HOME_COMPANY_TAX_ID + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getHomeCompanyTaxId()) + "," +
			ATTRIBUTE_INVENTORY_GROUP_STATE_TAX_ID + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getInventoryGroupStateTaxId()) + "," +
			ATTRIBUTE_OPERATIONAL_TAX_ID + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getOperationalTaxId()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_CODE + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getShipToAddressCode()) + "," +
			ATTRIBUTE_SUPPLIER_DEFAULT_PAYMENT_TERMS + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplierDefaultPaymentTerms()) + "," +
			ATTRIBUTE_SHIP_TO_DISPLAY_ADDRESS + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getShipToDisplayAddress()) + "," +
			ATTRIBUTE_SUPPLIER_DISPLAY_ADDRESS + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplierDisplayAddress()) + "," +
			ATTRIBUTE_INTERCOMPANY_MR + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getIntercompanyMr()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1_DISPLAY + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getShipToAddressLine1Display()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2_DISPLAY + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getShipToAddressLine2Display()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3_DISPLAY + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getShipToAddressLine3Display()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4_DISPLAY + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getShipToAddressLine4Display()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_5_DISPLAY + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getShipToAddressLine5Display()) + "," +
			ATTRIBUTE_SUPPLR_ADDRESS_LINE_1_DISPLAY + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplrAddressLine1Display()) + "," +
			ATTRIBUTE_SUPPLR_ADDRESS_LINE_2_DISPLAY + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplrAddressLine2Display()) + "," +
			ATTRIBUTE_SUPPLR_ADDRESS_LINE_3_DISPLAY + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplrAddressLine3Display()) + "," +
			ATTRIBUTE_SUPPLR_ADDRESS_LINE_4_DISPLAY + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplrAddressLine4Display()) + "," +
			ATTRIBUTE_SUPPLR_ADDRESS_LINE_5_DISPLAY + "=" + 
				SqlHandler.delimitString(poHeaderViewBean.getSupplrAddressLine5Display()) + " " +
			"where " + ATTRIBUTE_HUB_NAME + "=" +
				poHeaderViewBean.getHubName();

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

		Collection poHeaderViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PoHeaderViewBean poHeaderViewBean = new PoHeaderViewBean();
			load(dataSetRow, poHeaderViewBean);
			poHeaderViewBeanColl.add(poHeaderViewBean);
		}

		return poHeaderViewBeanColl;
	}
	
	 //select
public Collection selectSplitPoData(SearchCriteria criteria)
	throws BaseException {
	Connection connection = null;
	Collection c = null;
	try {
	 connection = this.getDbManager().getConnection();
	 c = selectSplitPoData(criteria,null, connection);
	}
	finally {
	 this.getDbManager().returnConnection(connection);
	}
	return c;
}


public Collection selectSplitPoData(SearchCriteria criteria,SortCriteria sortCriteria, Connection conn)
	throws BaseException {
	Collection poHeaderViewBeanColl = new Vector();

	String query = "select * from " + TABLE + " " +
	 getWhereClause(criteria);
	if (sortCriteria !=null)
	{
	 query += getOrderByClause(sortCriteria);
	}

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();
	PoLineDetailViewBeanFactory poLineFactory = new
	 PoLineDetailViewBeanFactory(getDbManager());
	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow)dataIter.next();
	 PoHeaderViewBean poHeaderViewBean = new PoHeaderViewBean();
	 load(dataSetRow, poHeaderViewBean);
	 //get collection of po line beans
	 SearchCriteria childCriteria = new SearchCriteria();
	 childCriteria.addCriterion("radianPo", SearchCriterion.EQUALS,
		"" + poHeaderViewBean.getRadianPo().intValue()+ "");

  SortCriteria poLineSortCriteria = new SortCriteria();
	 poLineSortCriteria.addCriterion("poLine");

  poHeaderViewBean.setPoLineCollection(poLineFactory.select(childCriteria,poLineSortCriteria));
	 poHeaderViewBeanColl.add(poHeaderViewBean);
	}
	return poHeaderViewBeanColl;
}
}