package com.tcmis.internal.distribution.factory;


import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.distribution.beans.SalesQuoteViewBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;


/******************************************************************************
 * CLASSNAME: SalesQuoteViewBeanFactory <br>
 * @version: 1.0, Oct 1, 2009 <br>
 *****************************************************************************/


public class SalesQuoteViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_CUSTOMER_ID = "CUSTOMER_ID";
	public String ATTRIBUTE_CUSTOMER_NAME = "CUSTOMER_NAME";
	public String ATTRIBUTE_BILL_TO_ADDRESS_LINE_1 = "BILL_TO_ADDRESS_LINE_1";
	public String ATTRIBUTE_BILL_TO_ADDRESS_LINE_2 = "BILL_TO_ADDRESS_LINE_2";
	public String ATTRIBUTE_BILL_TO_ADDRESS_LINE_3 = "BILL_TO_ADDRESS_LINE_3";
	public String ATTRIBUTE_BILL_TO_ADDRESS_LINE_4 = "BILL_TO_ADDRESS_LINE_4";
	public String ATTRIBUTE_BILL_TO_ADDRESS_LINE_5 = "BILL_TO_ADDRESS_LINE_5";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_LOCATION_SHORT_NAME = "LOCATION_SHORT_NAME";
	public String ATTRIBUTE_LOCATION_DESC = "LOCATION_DESC";
	public String ATTRIBUTE_PAYMENT_TERMS = "PAYMENT_TERMS";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1 = "SHIP_TO_ADDRESS_LINE_1";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2 = "SHIP_TO_ADDRESS_LINE_2";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3 = "SHIP_TO_ADDRESS_LINE_3";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4 = "SHIP_TO_ADDRESS_LINE_4";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_5 = "SHIP_TO_ADDRESS_LINE_5";
	public String ATTRIBUTE_CREDIT_LIMIT_IN_HOME_CURRENCY = "CREDIT_LIMIT_IN_HOME_CURRENCY";
	public String ATTRIBUTE_CREDIT_LIMIT_IN_ORDER_CURRENCY = "CREDIT_LIMIT_IN_ORDER_CURRENCY";
	public String ATTRIBUTE_OVERDUE_LIMIT = "OVERDUE_LIMIT";
	public String ATTRIBUTE_OVERDUE_LIMIT_BASIS = "OVERDUE_LIMIT_BASIS";
	public String ATTRIBUTE_CREDIT_STATUS = "CREDIT_STATUS";
	public String ATTRIBUTE_HOME_CURRENCY_ID = "HOME_CURRENCY_ID";
	public String ATTRIBUTE_ORDER_CURRENCY_ID = "ORDER_CURRENCY_ID";
	public String ATTRIBUTE_ORDER_TO_HOME_CONVERSION_RATE = "ORDER_TO_HOME_CONVERSION_RATE";
	public String ATTRIBUTE_PRICE_GROUP_ID = "PRICE_GROUP_ID";
	public String ATTRIBUTE_SHELF_LIFE_REQUIRED = "SHELF_LIFE_REQUIRED";
	public String ATTRIBUTE_SHIP_COMPLETE = "SHIP_COMPLETE";
	public String ATTRIBUTE_ACCOUNT_SYS_ID = "ACCOUNT_SYS_ID";
	public String ATTRIBUTE_CHARGE_TYPE = "CHARGE_TYPE";
	public String ATTRIBUTE_REQUESTOR = "REQUESTOR";
	public String ATTRIBUTE_REQUESTOR_NAME = "REQUESTOR_NAME";
	public String ATTRIBUTE_REQUESTOR_PHONE = "REQUESTOR_PHONE";
	public String ATTRIBUTE_REQUESTOR_FAX = "REQUESTOR_FAX";
	public String ATTRIBUTE_REQUESTOR_EMAIL = "REQUESTOR_EMAIL";
	public String ATTRIBUTE_ORIGINAL_SALES_QUOTE_ID = "ORIGINAL_SALES_QUOTE_ID";
	public String ATTRIBUTE_SUBMITTED_DATE = "SUBMITTED_DATE";
	public String ATTRIBUTE_SUBMITTED_BY = "SUBMITTED_BY";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_OPS_ENTITY_NAME = "OPS_ENTITY_NAME";
	public String ATTRIBUTE_OPS_COMPANY_ID = "OPS_COMPANY_ID";
	public String ATTRIBUTE_CUSTOMER_SERVICE_REP_ID = "CUSTOMER_SERVICE_REP_ID";
	public String ATTRIBUTE_END_USER = "END_USER";
	public String ATTRIBUTE_CHARGE_FREIGHT = "CHARGE_FREIGHT";
	public String ATTRIBUTE_FIELD_SALES_REP_ID = "FIELD_SALES_REP_ID";
	public String ATTRIBUTE_SALES_AGENT_ID = "SALES_AGENT_ID";
	public String ATTRIBUTE_SPECIAL_INSTRUCTIONS = "SPECIAL_INSTRUCTIONS";
	public String ATTRIBUTE_CARRIER_ACCOUNT_ID = "CARRIER_ACCOUNT_ID";
	public String ATTRIBUTE_CARRIER_CONTACT = "CARRIER_CONTACT";
	public String ATTRIBUTE_CARRIER_SERVICE_TYPE = "CARRIER_SERVICE_TYPE";
	public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
	public String ATTRIBUTE_CARRIER_ACCOUNT_NUMBER = "CARRIER_ACCOUNT_NUMBER";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_QUOTE_EXPIRE_DATE = "QUOTE_EXPIRE_DATE";
	public String ATTRIBUTE_SUBMITTED_BY_NAME = "SUBMITTED_BY_NAME";
	public String ATTRIBUTE_CUSTOMER_SERVICE_REP_NAME = "CUSTOMER_SERVICE_REP_NAME";
	public String ATTRIBUTE_FIELD_SALES_REP_NAME = "FIELD_SALES_REP_NAME";
	public String ATTRIBUTE_SALES_AGENT_NAME = "SALES_AGENT_NAME";
	public String ATTRIBUTE_TAX_REGISTRATION_TYPE = "TAX_REGISTRATION_TYPE";
	public String ATTRIBUTE_TAX_REGISTRATION_NUMBER = "TAX_REGISTRATION_NUMBER";
	public String ATTRIBUTE_ORDER_TYPE = "ORDER_TYPE";
	public String ATTRIBUTE_OUTSTANDING_PAYMENTS = "OUTSTANDING_PAYMENTS";
	public String ATTRIBUTE_SHIP_TO_COMPANY_ID = "SHIP_TO_COMPANY_ID";
	public String ATTRIBUTE_PRINTED_IMAGE_URL = "PRINTED_IMAGE_URL";
	public String ATTRIBUTE_NUMBER_OF_LINES = "NUMBER_OF_LINES";
	public String ATTRIBUTE_CAT_PART_LIST = "CAT_PART_LIST";
	public String ATTRIBUTE_ORDER_STATUS = "ORDER_STATUS";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_HUB_NAME = "HUB_NAME";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_OPERATING_ENTITY_NAME = "OPERATING_ENTITY_NAME";
	public String ATTRIBUTE_TOTAL_HEADER_CHARGE = "TOTAL_HEADER_CHARGE";
	public String ATTRIBUTE_PRICE_GROUP_NAME = "PRICE_GROUP_NAME";
	public String ATTRIBUTE_BILL_TO_COMPANY_ID = "BILL_TO_COMPANY_ID";
	public String ATTRIBUTE_BILL_TO_LOCATION_ID = "BILL_TO_LOCATION_ID";
	public String ATTRIBUTE_INCO_TERMS = "INCO_TERMS";
	public String ATTRIBUTE_SHIP_TO_UPDATABLE = "SHIP_TO_UPDATABLE";
	public String ATTRIBUTE_BILL_TO_UPDATABLE = "BILL_TO_UPDATABLE";
	public String ATTRIBUTE_SHIP_TO_CITY = "SHIP_TO_CITY";
	public String ATTRIBUTE_SHIP_TO_STATE = "SHIP_TO_STATE";
	public String ATTRIBUTE_SHIP_TO_ZIP = "SHIP_TO_ZIP";
	public String ATTRIBUTE_SHIP_TO_COUNTRY = "SHIP_TO_COUNTRY";
	public String ATTRIBUTE_BILL_TO_CITY = "BILL_TO_CITY";
	public String ATTRIBUTE_BILL_TO_STATE = "BILL_TO_STATE";
	public String ATTRIBUTE_BILL_TO_ZIP = "BILL_TO_ZIP";
	public String ATTRIBUTE_BILL_TO_COUNTRY = "BILL_TO_COUNTRY";
	public String ATTRIBUTE_TOTAL_EXTENDED_PRICE = "TOTAL_EXTENDED_PRICE";
	public String ATTRIBUTE_AVAILABLE_CREDIT = "AVAILABLE_CREDIT";
	public String ATTRIBUTE_WITHIN_TERMS = "WITHIN_TERMS";
	public String ATTRIBUTE_RELEASE_STATUS = "RELEASE_STATUS";

	//table name
	public String TABLE = "SALES_QUOTE_VIEW";


	//constructor
	public SalesQuoteViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if(attributeName.equals("customerId")) {
			return ATTRIBUTE_CUSTOMER_ID;
		}
		else if(attributeName.equals("customerName")) {
			return ATTRIBUTE_CUSTOMER_NAME;
		}
		else if(attributeName.equals("billToAddressLine11")) {
			return ATTRIBUTE_BILL_TO_ADDRESS_LINE_1;
		}
		else if(attributeName.equals("billToAddressLine22")) {
			return ATTRIBUTE_BILL_TO_ADDRESS_LINE_2;
		}
		else if(attributeName.equals("billToAddressLine33")) {
			return ATTRIBUTE_BILL_TO_ADDRESS_LINE_3;
		}
		else if(attributeName.equals("billToAddressLine44")) {
			return ATTRIBUTE_BILL_TO_ADDRESS_LINE_4;
		}
		else if(attributeName.equals("billToAddressLine55")) {
			return ATTRIBUTE_BILL_TO_ADDRESS_LINE_5;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("locationShortName")) {
			return ATTRIBUTE_LOCATION_SHORT_NAME;
		}
		else if(attributeName.equals("locationDesc")) {
			return ATTRIBUTE_LOCATION_DESC;
		}
		else if(attributeName.equals("paymentTerms")) {
			return ATTRIBUTE_PAYMENT_TERMS;
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
		else if(attributeName.equals("shipToAddressLine44")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4;
		}
		else if(attributeName.equals("shipToAddressLine55")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_5;
		}
		else if(attributeName.equals("creditLimitInHomeCurrency")) {
			return ATTRIBUTE_CREDIT_LIMIT_IN_HOME_CURRENCY;
		}
		else if(attributeName.equals("creditLimitInOrderCurrency")) {
			return ATTRIBUTE_CREDIT_LIMIT_IN_ORDER_CURRENCY;
		}
		else if(attributeName.equals("overdueLimit")) {
			return ATTRIBUTE_OVERDUE_LIMIT;
		}
		else if(attributeName.equals("overdueLimitBasis")) {
			return ATTRIBUTE_OVERDUE_LIMIT_BASIS;
		}
		else if(attributeName.equals("creditStatus")) {
			return ATTRIBUTE_CREDIT_STATUS;
		}
		else if(attributeName.equals("homeCurrencyId")) {
			return ATTRIBUTE_HOME_CURRENCY_ID;
		}
		else if(attributeName.equals("orderCurrencyId")) {
			return ATTRIBUTE_ORDER_CURRENCY_ID;
		}
		else if(attributeName.equals("orderToHomeConversionRate")) {
			return ATTRIBUTE_ORDER_TO_HOME_CONVERSION_RATE;
		}
		else if(attributeName.equals("priceGroupId")) {
			return ATTRIBUTE_PRICE_GROUP_ID;
		}
		else if(attributeName.equals("shelfLifeRequired")) {
			return ATTRIBUTE_SHELF_LIFE_REQUIRED;
		}
		else if(attributeName.equals("shipComplete")) {
			return ATTRIBUTE_SHIP_COMPLETE;
		}
		else if(attributeName.equals("accountSysId")) {
			return ATTRIBUTE_ACCOUNT_SYS_ID;
		}
		else if(attributeName.equals("chargeType")) {
			return ATTRIBUTE_CHARGE_TYPE;
		}
		else if(attributeName.equals("requestor")) {
			return ATTRIBUTE_REQUESTOR;
		}
		else if(attributeName.equals("requestorName")) {
			return ATTRIBUTE_REQUESTOR_NAME;
		}
		else if(attributeName.equals("requestorPhone")) {
			return ATTRIBUTE_REQUESTOR_PHONE;
		}
		else if(attributeName.equals("requestorFax")) {
			return ATTRIBUTE_REQUESTOR_FAX;
		}
		else if(attributeName.equals("requestorEmail")) {
			return ATTRIBUTE_REQUESTOR_EMAIL;
		}
		else if(attributeName.equals("originalSalesQuoteId")) {
			return ATTRIBUTE_ORIGINAL_SALES_QUOTE_ID;
		}
		else if(attributeName.equals("submittedDate")) {
			return ATTRIBUTE_SUBMITTED_DATE;
		}
		else if(attributeName.equals("submittedBy")) {
			return ATTRIBUTE_SUBMITTED_BY;
		}
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("opsEntityName")) {
			return ATTRIBUTE_OPS_ENTITY_NAME;
		}
		else if(attributeName.equals("opsCompanyId")) {
			return ATTRIBUTE_OPS_COMPANY_ID;
		}
		else if(attributeName.equals("customerServiceRepId")) {
			return ATTRIBUTE_CUSTOMER_SERVICE_REP_ID;
		}
		else if(attributeName.equals("endUser")) {
			return ATTRIBUTE_END_USER;
		}
		else if(attributeName.equals("chargeFreight")) {
			return ATTRIBUTE_CHARGE_FREIGHT;
		}
		else if(attributeName.equals("fieldSalesRepId")) {
			return ATTRIBUTE_FIELD_SALES_REP_ID;
		}
		else if(attributeName.equals("salesAgentId")) {
			return ATTRIBUTE_SALES_AGENT_ID;
		}
		else if(attributeName.equals("specialInstructions")) {
			return ATTRIBUTE_SPECIAL_INSTRUCTIONS;
		}
		else if(attributeName.equals("carrierAccountId")) {
			return ATTRIBUTE_CARRIER_ACCOUNT_ID;
		}
		else if(attributeName.equals("carrierContact")) {
			return ATTRIBUTE_CARRIER_CONTACT;
		}
		else if(attributeName.equals("carrierServiceType")) {
			return ATTRIBUTE_CARRIER_SERVICE_TYPE;
		}
		else if(attributeName.equals("carrierName")) {
			return ATTRIBUTE_CARRIER_NAME;
		}
		else if(attributeName.equals("carrierAccountNumber")) {
			return ATTRIBUTE_CARRIER_ACCOUNT_NUMBER;
		}
		else if(attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("quoteExpireDate")) {
			return ATTRIBUTE_QUOTE_EXPIRE_DATE;
		}
		else if(attributeName.equals("submittedByName")) {
			return ATTRIBUTE_SUBMITTED_BY_NAME;
		}
		else if(attributeName.equals("customerServiceRepName")) {
			return ATTRIBUTE_CUSTOMER_SERVICE_REP_NAME;
		}
		else if(attributeName.equals("fieldSalesRepName")) {
			return ATTRIBUTE_FIELD_SALES_REP_NAME;
		}
		else if(attributeName.equals("salesAgentName")) {
			return ATTRIBUTE_SALES_AGENT_NAME;
		}
		else if(attributeName.equals("taxRegistrationType")) {
			return ATTRIBUTE_TAX_REGISTRATION_TYPE;
		}
		else if(attributeName.equals("taxRegistrationNumber")) {
			return ATTRIBUTE_TAX_REGISTRATION_NUMBER;
		}
		else if(attributeName.equals("orderType")) {
			return ATTRIBUTE_ORDER_TYPE;
		}
		else if(attributeName.equals("outstandingPayments")) {
			return ATTRIBUTE_OUTSTANDING_PAYMENTS;
		}
		else if(attributeName.equals("shipToCompanyId")) {
			return ATTRIBUTE_SHIP_TO_COMPANY_ID;
		}
		else if(attributeName.equals("printedImageUrl")) {
			return ATTRIBUTE_PRINTED_IMAGE_URL;
		}
		else if(attributeName.equals("numberOfLines")) {
			return ATTRIBUTE_NUMBER_OF_LINES;
		}
		else if(attributeName.equals("catPartList")) {
			return ATTRIBUTE_CAT_PART_LIST;
		}
		else if(attributeName.equals("orderStatus")) {
			return ATTRIBUTE_ORDER_STATUS;
		}
		else if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("hubName")) {
			return ATTRIBUTE_HUB_NAME;
		}
		else if(attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if(attributeName.equals("operatingEntityName")) {
			return ATTRIBUTE_OPERATING_ENTITY_NAME;
		}
		else if(attributeName.equals("totalHeaderCharge")) {
			return ATTRIBUTE_TOTAL_HEADER_CHARGE;
		}
		else if(attributeName.equals("priceGroupName")) {
			return ATTRIBUTE_PRICE_GROUP_NAME;
		}
		else if(attributeName.equals("billToCompanyId")) {
			return ATTRIBUTE_BILL_TO_COMPANY_ID;
		}
		else if(attributeName.equals("billToLocationId")) {
			return ATTRIBUTE_BILL_TO_LOCATION_ID;
		}
		else if(attributeName.equals("incoTerms")) {
			return ATTRIBUTE_INCO_TERMS;
		}
		else if(attributeName.equals("shipToUpdatable")) {
			return ATTRIBUTE_SHIP_TO_UPDATABLE;
		}
		else if(attributeName.equals("billToUpdatable")) {
			return ATTRIBUTE_BILL_TO_UPDATABLE;
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
		else if(attributeName.equals("shipToCountry")) {
			return ATTRIBUTE_SHIP_TO_COUNTRY;
		}
		else if(attributeName.equals("billToCity")) {
			return ATTRIBUTE_BILL_TO_CITY;
		}
		else if(attributeName.equals("billToState")) {
			return ATTRIBUTE_BILL_TO_STATE;
		}
		else if(attributeName.equals("billToZip")) {
			return ATTRIBUTE_BILL_TO_ZIP;
		}
		else if(attributeName.equals("billToCountry")) {
			return ATTRIBUTE_BILL_TO_COUNTRY;
		}
		else if(attributeName.equals("totalExtendedPrice")) {
			return ATTRIBUTE_TOTAL_EXTENDED_PRICE;
		}
		else if(attributeName.equals("availableCredit")) {
			return ATTRIBUTE_AVAILABLE_CREDIT;
		}
		else if(attributeName.equals("withinTerms")) {
			return ATTRIBUTE_WITHIN_TERMS;
		}
		else if(attributeName.equals("releaseStatus")) {
			return ATTRIBUTE_RELEASE_STATUS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, SalesQuoteViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(SalesQuoteViewBean salesQuoteViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
			"" + salesQuoteViewBean.getPrNumber());

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


	public int delete(SalesQuoteViewBean salesQuoteViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
			"" + salesQuoteViewBean.getPrNumber());

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
	public int insert(SalesQuoteViewBean salesQuoteViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(salesQuoteViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(SalesQuoteViewBean salesQuoteViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_STATUS + "," +
			ATTRIBUTE_CUSTOMER_ID + "," +
			ATTRIBUTE_CUSTOMER_NAME + "," +
			ATTRIBUTE_BILL_TO_ADDRESS_LINE_1 + "," +
			ATTRIBUTE_BILL_TO_ADDRESS_LINE_2 + "," +
			ATTRIBUTE_BILL_TO_ADDRESS_LINE_3 + "," +
			ATTRIBUTE_BILL_TO_ADDRESS_LINE_4 + "," +
			ATTRIBUTE_BILL_TO_ADDRESS_LINE_5 + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_LOCATION_SHORT_NAME + "," +
			ATTRIBUTE_LOCATION_DESC + "," +
			ATTRIBUTE_PAYMENT_TERMS + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1 + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2 + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3 + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4 + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_5 + "," +
			ATTRIBUTE_CREDIT_LIMIT_IN_HOME_CURRENCY + "," +
			ATTRIBUTE_CREDIT_LIMIT_IN_ORDER_CURRENCY + "," +
			ATTRIBUTE_OVERDUE_LIMIT + "," +
			ATTRIBUTE_OVERDUE_LIMIT_BASIS + "," +
			ATTRIBUTE_CREDIT_STATUS + "," +
			ATTRIBUTE_HOME_CURRENCY_ID + "," +
			ATTRIBUTE_ORDER_CURRENCY_ID + "," +
			ATTRIBUTE_ORDER_TO_HOME_CONVERSION_RATE + "," +
			ATTRIBUTE_PRICE_GROUP_ID + "," +
			ATTRIBUTE_SHELF_LIFE_REQUIRED + "," +
			ATTRIBUTE_SHIP_COMPLETE + "," +
			ATTRIBUTE_ACCOUNT_SYS_ID + "," +
			ATTRIBUTE_CHARGE_TYPE + "," +
			ATTRIBUTE_REQUESTOR + "," +
			ATTRIBUTE_REQUESTOR_NAME + "," +
			ATTRIBUTE_REQUESTOR_PHONE + "," +
			ATTRIBUTE_REQUESTOR_FAX + "," +
			ATTRIBUTE_REQUESTOR_EMAIL + "," +
			ATTRIBUTE_ORIGINAL_SALES_QUOTE_ID + "," +
			ATTRIBUTE_SUBMITTED_DATE + "," +
			ATTRIBUTE_SUBMITTED_BY + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "," +
			ATTRIBUTE_OPS_COMPANY_ID + "," +
			ATTRIBUTE_CUSTOMER_SERVICE_REP_ID + "," +
			ATTRIBUTE_END_USER + "," +
			ATTRIBUTE_CHARGE_FREIGHT + "," +
			ATTRIBUTE_FIELD_SALES_REP_ID + "," +
			ATTRIBUTE_SALES_AGENT_ID + "," +
			ATTRIBUTE_SPECIAL_INSTRUCTIONS + "," +
			ATTRIBUTE_CARRIER_ACCOUNT_ID + "," +
			ATTRIBUTE_CARRIER_CONTACT + "," +
			ATTRIBUTE_CARRIER_SERVICE_TYPE + "," +
			ATTRIBUTE_CARRIER_NAME + "," +
			ATTRIBUTE_CARRIER_ACCOUNT_NUMBER + "," +
			ATTRIBUTE_PO_NUMBER + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_QUOTE_EXPIRE_DATE + "," +
			ATTRIBUTE_SUBMITTED_BY_NAME + "," +
			ATTRIBUTE_CUSTOMER_SERVICE_REP_NAME + "," +
			ATTRIBUTE_FIELD_SALES_REP_NAME + "," +
			ATTRIBUTE_SALES_AGENT_NAME + "," +
			ATTRIBUTE_TAX_REGISTRATION_TYPE + "," +
			ATTRIBUTE_TAX_REGISTRATION_NUMBER + "," +
			ATTRIBUTE_ORDER_TYPE + "," +
			ATTRIBUTE_OUTSTANDING_PAYMENTS + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "," +
			ATTRIBUTE_PRINTED_IMAGE_URL + "," +
			ATTRIBUTE_NUMBER_OF_LINES + "," +
			ATTRIBUTE_CAT_PART_LIST + "," +
			ATTRIBUTE_ORDER_STATUS + "," +
			ATTRIBUTE_HUB + "," +
			ATTRIBUTE_HUB_NAME + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "," +
			ATTRIBUTE_OPERATING_ENTITY_NAME + "," +
			ATTRIBUTE_TOTAL_HEADER_CHARGE + "," +
			ATTRIBUTE_PRICE_GROUP_NAME + "," +
			ATTRIBUTE_BILL_TO_COMPANY_ID + "," +
			ATTRIBUTE_BILL_TO_LOCATION_ID + "," +
			ATTRIBUTE_INCO_TERMS + "," +
			ATTRIBUTE_SHIP_TO_UPDATABLE + "," +
			ATTRIBUTE_BILL_TO_UPDATABLE + "," +
			ATTRIBUTE_SHIP_TO_CITY + "," +
			ATTRIBUTE_SHIP_TO_STATE + "," +
			ATTRIBUTE_SHIP_TO_ZIP + "," +
			ATTRIBUTE_SHIP_TO_COUNTRY + "," +
			ATTRIBUTE_BILL_TO_CITY + "," +
			ATTRIBUTE_BILL_TO_STATE + "," +
			ATTRIBUTE_BILL_TO_ZIP + "," +
			ATTRIBUTE_BILL_TO_COUNTRY + "," +
			ATTRIBUTE_TOTAL_EXTENDED_PRICE + "," +
			ATTRIBUTE_AVAILABLE_CREDIT + "," +
			ATTRIBUTE_WITHIN_TERMS + "," +
			ATTRIBUTE_RELEASE_STATUS + ")" +
			" values (" +
			salesQuoteViewBean.getPrNumber() + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getStatus()) + "," +
			salesQuoteViewBean.getCustomerId() + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getCustomerName()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getBillToAddressLine1()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getBillToAddressLine2()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getBillToAddressLine3()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getBillToAddressLine4()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getBillToAddressLine5()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getFacilityId()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getShipToLocationId()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getLocationShortName()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getLocationDesc()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getPaymentTerms()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getShipToAddressLine1()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getShipToAddressLine2()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getShipToAddressLine3()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getShipToAddressLine4()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getShipToAddressLine5()) + "," +
			salesQuoteViewBean.getCreditLimitInHomeCurrency() + "," +
			salesQuoteViewBean.getCreditLimitInOrderCurrency() + "," +
			salesQuoteViewBean.getOverdueLimit() + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getOverdueLimitBasis()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getCreditStatus()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getHomeCurrencyId()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getOrderCurrencyId()) + "," +
			salesQuoteViewBean.getOrderToHomeConversionRate() + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getPriceGroupId()) + "," +
			salesQuoteViewBean.getShelfLifeRequired() + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getShipComplete()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getAccountSysId()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getChargeType()) + "," +
			salesQuoteViewBean.getRequestor() + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getRequestorName()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getRequestorPhone()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getRequestorFax()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getRequestorEmail()) + "," +
			salesQuoteViewBean.getOriginalSalesQuoteId() + "," +
			DateHandler.getOracleToDateFunction(salesQuoteViewBean.getSubmittedDate()) + "," +
			salesQuoteViewBean.getSubmittedBy() + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getOpsEntityId()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getOpsCompanyId()) + "," +
			salesQuoteViewBean.getCustomerServiceRepId() + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getEndUser()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getChargeFreight()) + "," +
			salesQuoteViewBean.getFieldSalesRepId() + "," +
			salesQuoteViewBean.getSalesAgentId() + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getSpecialInstructions()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getCarrierAccountId()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getCarrierContact()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getCarrierServiceType()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getCarrierName()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getCarrierAccountNumber()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getPoNumber()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getInventoryGroup()) + "," +
			DateHandler.getOracleToDateFunction(salesQuoteViewBean.getQuoteExpireDate()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getSubmittedByName()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getCustomerServiceRepName()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getFieldSalesRepName()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getSalesAgentName()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getTaxRegistrationType()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getTaxRegistrationNumber()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getOrderType()) + "," +
			salesQuoteViewBean.getOutstandingPayments() + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getShipToCompanyId()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getPrintedImageUrl()) + "," +
			salesQuoteViewBean.getNumberOfLines() + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getCatPartList()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getOrderStatus()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getHub()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getHubName()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getInventoryGroupName()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getOperatingEntityName()) + "," +
			salesQuoteViewBean.getTotalHeaderCharge() + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getPriceGroupName()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getBillToCompanyId()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getBillToLocationId()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getIncoTerms()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getShipToUpdatable()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getBillToUpdatable()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getShipToCity()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getShipToState()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getShipToZip()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getShipToCountry()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getBillToCity()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getBillToState()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getBillToZip()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getBillToCountry()) + "," +
			salesQuoteViewBean.getTotalExtendedPrice() + "," +
			salesQuoteViewBean.getAvailableCredit() + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getWithinTerms()) + "," +
			SqlHandler.delimitString(salesQuoteViewBean.getReleaseStatus()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(SalesQuoteViewBean salesQuoteViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(salesQuoteViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(SalesQuoteViewBean salesQuoteViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PR_NUMBER + "=" +
				StringHandler.nullIfZero(salesQuoteViewBean.getPrNumber()) + "," +
			ATTRIBUTE_STATUS + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getStatus()) + "," +
			ATTRIBUTE_CUSTOMER_ID + "=" +
				StringHandler.nullIfZero(salesQuoteViewBean.getCustomerId()) + "," +
			ATTRIBUTE_CUSTOMER_NAME + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getCustomerName()) + "," +
			ATTRIBUTE_BILL_TO_ADDRESS_LINE_1 + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getBillToAddressLine1()) + "," +
			ATTRIBUTE_BILL_TO_ADDRESS_LINE_2 + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getBillToAddressLine2()) + "," +
			ATTRIBUTE_BILL_TO_ADDRESS_LINE_3 + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getBillToAddressLine3()) + "," +
			ATTRIBUTE_BILL_TO_ADDRESS_LINE_4 + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getBillToAddressLine4()) + "," +
			ATTRIBUTE_BILL_TO_ADDRESS_LINE_5 + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getBillToAddressLine5()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getCompanyId()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getFacilityId()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getShipToLocationId()) + "," +
			ATTRIBUTE_LOCATION_SHORT_NAME + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getLocationShortName()) + "," +
			ATTRIBUTE_LOCATION_DESC + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getLocationDesc()) + "," +
			ATTRIBUTE_PAYMENT_TERMS + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getPaymentTerms()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1 + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getShipToAddressLine1()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2 + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getShipToAddressLine2()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3 + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getShipToAddressLine3()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4 + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getShipToAddressLine4()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_5 + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getShipToAddressLine5()) + "," +
			ATTRIBUTE_CREDIT_LIMIT_IN_HOME_CURRENCY + "=" +
				StringHandler.nullIfZero(salesQuoteViewBean.getCreditLimitInHomeCurrency()) + "," +
			ATTRIBUTE_CREDIT_LIMIT_IN_ORDER_CURRENCY + "=" +
				StringHandler.nullIfZero(salesQuoteViewBean.getCreditLimitInOrderCurrency()) + "," +
			ATTRIBUTE_OVERDUE_LIMIT + "=" +
				StringHandler.nullIfZero(salesQuoteViewBean.getOverdueLimit()) + "," +
			ATTRIBUTE_OVERDUE_LIMIT_BASIS + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getOverdueLimitBasis()) + "," +
			ATTRIBUTE_CREDIT_STATUS + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getCreditStatus()) + "," +
			ATTRIBUTE_HOME_CURRENCY_ID + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getHomeCurrencyId()) + "," +
			ATTRIBUTE_ORDER_CURRENCY_ID + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getOrderCurrencyId()) + "," +
			ATTRIBUTE_ORDER_TO_HOME_CONVERSION_RATE + "=" +
				StringHandler.nullIfZero(salesQuoteViewBean.getOrderToHomeConversionRate()) + "," +
			ATTRIBUTE_PRICE_GROUP_ID + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getPriceGroupId()) + "," +
			ATTRIBUTE_SHELF_LIFE_REQUIRED + "=" +
				StringHandler.nullIfZero(salesQuoteViewBean.getShelfLifeRequired()) + "," +
			ATTRIBUTE_SHIP_COMPLETE + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getShipComplete()) + "," +
			ATTRIBUTE_ACCOUNT_SYS_ID + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getAccountSysId()) + "," +
			ATTRIBUTE_CHARGE_TYPE + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getChargeType()) + "," +
			ATTRIBUTE_REQUESTOR + "=" +
				StringHandler.nullIfZero(salesQuoteViewBean.getRequestor()) + "," +
			ATTRIBUTE_REQUESTOR_NAME + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getRequestorName()) + "," +
			ATTRIBUTE_REQUESTOR_PHONE + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getRequestorPhone()) + "," +
			ATTRIBUTE_REQUESTOR_FAX + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getRequestorFax()) + "," +
			ATTRIBUTE_REQUESTOR_EMAIL + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getRequestorEmail()) + "," +
			ATTRIBUTE_ORIGINAL_SALES_QUOTE_ID + "=" +
				StringHandler.nullIfZero(salesQuoteViewBean.getOriginalSalesQuoteId()) + "," +
			ATTRIBUTE_SUBMITTED_DATE + "=" +
				DateHandler.getOracleToDateFunction(salesQuoteViewBean.getSubmittedDate()) + "," +
			ATTRIBUTE_SUBMITTED_BY + "=" +
				StringHandler.nullIfZero(salesQuoteViewBean.getSubmittedBy()) + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getOpsEntityId()) + "," +
			ATTRIBUTE_OPS_COMPANY_ID + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getOpsCompanyId()) + "," +
			ATTRIBUTE_CUSTOMER_SERVICE_REP_ID + "=" +
				StringHandler.nullIfZero(salesQuoteViewBean.getCustomerServiceRepId()) + "," +
			ATTRIBUTE_END_USER + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getEndUser()) + "," +
			ATTRIBUTE_CHARGE_FREIGHT + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getChargeFreight()) + "," +
			ATTRIBUTE_FIELD_SALES_REP_ID + "=" +
				StringHandler.nullIfZero(salesQuoteViewBean.getFieldSalesRepId()) + "," +
			ATTRIBUTE_SALES_AGENT_ID + "=" +
				StringHandler.nullIfZero(salesQuoteViewBean.getSalesAgentId()) + "," +
			ATTRIBUTE_SPECIAL_INSTRUCTIONS + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getSpecialInstructions()) + "," +
			ATTRIBUTE_CARRIER_ACCOUNT_ID + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getCarrierAccountId()) + "," +
			ATTRIBUTE_CARRIER_CONTACT + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getCarrierContact()) + "," +
			ATTRIBUTE_CARRIER_SERVICE_TYPE + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getCarrierServiceType()) + "," +
			ATTRIBUTE_CARRIER_NAME + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getCarrierName()) + "," +
			ATTRIBUTE_CARRIER_ACCOUNT_NUMBER + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getCarrierAccountNumber()) + "," +
			ATTRIBUTE_PO_NUMBER + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getPoNumber()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_QUOTE_EXPIRE_DATE + "=" +
				DateHandler.getOracleToDateFunction(salesQuoteViewBean.getQuoteExpireDate()) + "," +
			ATTRIBUTE_SUBMITTED_BY_NAME + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getSubmittedByName()) + "," +
			ATTRIBUTE_CUSTOMER_SERVICE_REP_NAME + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getCustomerServiceRepName()) + "," +
			ATTRIBUTE_FIELD_SALES_REP_NAME + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getFieldSalesRepName()) + "," +
			ATTRIBUTE_SALES_AGENT_NAME + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getSalesAgentName()) + "," +
			ATTRIBUTE_TAX_REGISTRATION_TYPE + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getTaxRegistrationType()) + "," +
			ATTRIBUTE_TAX_REGISTRATION_NUMBER + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getTaxRegistrationNumber()) + "," +
			ATTRIBUTE_ORDER_TYPE + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getOrderType()) + "," +
			ATTRIBUTE_OUTSTANDING_PAYMENTS + "=" +
				StringHandler.nullIfZero(salesQuoteViewBean.getOutstandingPayments()) + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getShipToCompanyId()) + "," +
			ATTRIBUTE_PRINTED_IMAGE_URL + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getPrintedImageUrl()) + "," +
			ATTRIBUTE_NUMBER_OF_LINES + "=" +
				StringHandler.nullIfZero(salesQuoteViewBean.getNumberOfLines()) + "," +
			ATTRIBUTE_CAT_PART_LIST + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getCatPartList()) + "," +
			ATTRIBUTE_ORDER_STATUS + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getOrderStatus()) + "," +
			ATTRIBUTE_HUB + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getHub()) + "," +
			ATTRIBUTE_HUB_NAME + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getHubName()) + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getInventoryGroupName()) + "," +
			ATTRIBUTE_OPERATING_ENTITY_NAME + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getOperatingEntityName()) + "," +
			ATTRIBUTE_TOTAL_HEADER_CHARGE + "=" +
				StringHandler.nullIfZero(salesQuoteViewBean.getTotalHeaderCharge()) + "," +
			ATTRIBUTE_PRICE_GROUP_NAME + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getPriceGroupName()) + "," +
			ATTRIBUTE_BILL_TO_COMPANY_ID + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getBillToCompanyId()) + "," +
			ATTRIBUTE_BILL_TO_LOCATION_ID + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getBillToLocationId()) + "," +
			ATTRIBUTE_INCO_TERMS + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getIncoTerms()) + "," +
			ATTRIBUTE_SHIP_TO_UPDATABLE + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getShipToUpdatable()) + "," +
			ATTRIBUTE_BILL_TO_UPDATABLE + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getBillToUpdatable()) + "," +
			ATTRIBUTE_SHIP_TO_CITY + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getShipToCity()) + "," +
			ATTRIBUTE_SHIP_TO_STATE + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getShipToState()) + "," +
			ATTRIBUTE_SHIP_TO_ZIP + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getShipToZip()) + "," +
			ATTRIBUTE_SHIP_TO_COUNTRY + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getShipToCountry()) + "," +
			ATTRIBUTE_BILL_TO_CITY + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getBillToCity()) + "," +
			ATTRIBUTE_BILL_TO_STATE + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getBillToState()) + "," +
			ATTRIBUTE_BILL_TO_ZIP + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getBillToZip()) + "," +
			ATTRIBUTE_BILL_TO_COUNTRY + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getBillToCountry()) + "," +
			ATTRIBUTE_TOTAL_EXTENDED_PRICE + "=" +
				StringHandler.nullIfZero(salesQuoteViewBean.getTotalExtendedPrice()) + "," +
			ATTRIBUTE_AVAILABLE_CREDIT + "=" +
				StringHandler.nullIfZero(salesQuoteViewBean.getAvailableCredit()) + "," +
			ATTRIBUTE_WITHIN_TERMS + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getWithinTerms()) + "," +
			ATTRIBUTE_RELEASE_STATUS + "=" +
				SqlHandler.delimitString(salesQuoteViewBean.getReleaseStatus()) + " " +
			"where " + ATTRIBUTE_PR_NUMBER + "=" +
				salesQuoteViewBean.getPrNumber();

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

		Collection salesQuoteViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			SalesQuoteViewBean salesQuoteViewBean = new SalesQuoteViewBean();
			load(dataSetRow, salesQuoteViewBean);
			salesQuoteViewBeanColl.add(salesQuoteViewBean);
		}

		return salesQuoteViewBeanColl;
	}
}