package com.tcmis.internal.distribution.factory;


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
import java.util.Locale;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.internal.distribution.beans.CustomerAddRequestViewBean;


/******************************************************************************
 * CLASSNAME: CustomerAddRequestViewBeanFactory <br>
 * @version: 1.0, Jul 23, 2009 <br>
 *****************************************************************************/


public class CustomerAddRequestBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_CUSTOMER_REQUEST_ID = "CUSTOMER_REQUEST_ID";
	public String ATTRIBUTE_CUSTOMER_ID = "CUSTOMER_ID";
	public String ATTRIBUTE_CUSTOMER_NAME = "CUSTOMER_NAME";
	public String ATTRIBUTE_BILL_TO_COMPANY_ID = "BILL_TO_COMPANY_ID";
	public String ATTRIBUTE_BILL_TO_LOCATION_ID = "BILL_TO_LOCATION_ID";
	public String ATTRIBUTE_PAYER_ID = "PAYER_ID";
	public String ATTRIBUTE_INDUSTRY = "INDUSTRY";
	public String ATTRIBUTE_INVOICE_CONSOLIDATION = "INVOICE_CONSOLIDATION";
	public String ATTRIBUTE_PAYMENT_TERMS = "PAYMENT_TERMS";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_FIXED_CURRENCY = "FIXED_CURRENCY";
	public String ATTRIBUTE_CREDIT_LIMIT = "CREDIT_LIMIT";
	public String ATTRIBUTE_OVERDUE_LIMIT = "OVERDUE_LIMIT";
	public String ATTRIBUTE_OVERDUE_LIMIT_BASIS = "OVERDUE_LIMIT_BASIS";
	public String ATTRIBUTE_CREDIT_STATUS = "CREDIT_STATUS";
	public String ATTRIBUTE_PRICE_GROUP_ID = "PRICE_GROUP_ID";
	public String ATTRIBUTE_SHELF_LIFE_REQUIRED = "SHELF_LIFE_REQUIRED";
	public String ATTRIBUTE_SHIP_COMPLETE = "SHIP_COMPLETE";
	public String ATTRIBUTE_SAP_CUSTOMER_CODE = "SAP_CUSTOMER_CODE";
	public String ATTRIBUTE_TAX_REGISTRATION_TYPE = "TAX_REGISTRATION_TYPE";
	public String ATTRIBUTE_TAX_REGISTRATION_NUMBER = "TAX_REGISTRATION_NUMBER";
	public String ATTRIBUTE_TAX_REGISTRATION_TYPE_2 = "TAX_REGISTRATION_TYPE_2";
	public String ATTRIBUTE_TAX_REGISTRATION_NUMBER_2 = "TAX_REGISTRATION_NUMBER_2";
	public String ATTRIBUTE_TAX_REGISTRATION_TYPE_3 = "TAX_REGISTRATION_TYPE_3";
	public String ATTRIBUTE_TAX_REGISTRATION_NUMBER_3 = "TAX_REGISTRATION_NUMBER_3";
	public String ATTRIBUTE_TAX_REGISTRATION_TYPE_4 = "TAX_REGISTRATION_TYPE_4";
	public String ATTRIBUTE_TAX_REGISTRATION_NUMBER_4 = "TAX_REGISTRATION_NUMBER_4";
	public String ATTRIBUTE_TAX_CURRENCY_ID = "TAX_CURRENCY_ID";
	public String ATTRIBUTE_EXIT_LABEL_FORMAT = "EXIT_LABEL_FORMAT";
	public String ATTRIBUTE_EMAIL_ORDER_ACK = "EMAIL_ORDER_ACK";
	public String ATTRIBUTE_SHIP_MFG_COC = "SHIP_MFG_COC";
	public String ATTRIBUTE_SHIP_MFG_COA = "SHIP_MFG_COA";
	public String ATTRIBUTE_SHIP_MSDS = "SHIP_MSDS";
	public String ATTRIBUTE_COUNTRY_ABBREV = "COUNTRY_ABBREV";
	public String ATTRIBUTE_STATE_ABBREV = "STATE_ABBREV";
	public String ATTRIBUTE_ADDRESS_LINE_1 = "ADDRESS_LINE_1";
	public String ATTRIBUTE_ADDRESS_LINE_2 = "ADDRESS_LINE_2";
	public String ATTRIBUTE_ADDRESS_LINE_3 = "ADDRESS_LINE_3";
	public String ATTRIBUTE_ADDRESS_LINE_4 = "ADDRESS_LINE_4";
	public String ATTRIBUTE_ADDRESS_LINE_5 = "ADDRESS_LINE_5";
	public String ATTRIBUTE_CITY = "CITY";
	public String ATTRIBUTE_ZIP = "ZIP";
	public String ATTRIBUTE_ZIP_PLUS = "ZIP_PLUS";
	public String ATTRIBUTE_LOCATION_DESC = "LOCATION_DESC";
	public String ATTRIBUTE_CHARGE_FREIGHT = "CHARGE_FREIGHT";
	public String ATTRIBUTE_CONTACT_PERSONNEL_ID = "CONTACT_PERSONNEL_ID";
	public String ATTRIBUTE_FIELD_SALES_REP_ID = "FIELD_SALES_REP_ID"; //fieldSalesRepId
	public String ATTRIBUTE_CONTACT_TYPE = "CONTACT_TYPE";
	public String ATTRIBUTE_FIRST_NAME = "FIRST_NAME";
	public String ATTRIBUTE_LAST_NAME = "LAST_NAME";
	public String ATTRIBUTE_NICKNAME = "NICKNAME";
	public String ATTRIBUTE_PHONE = "PHONE";
	public String ATTRIBUTE_MOBILE = "MOBILE";
	public String ATTRIBUTE_FAX = "FAX";
	public String ATTRIBUTE_EMAIL = "EMAIL";
	public String ATTRIBUTE_PURCHASING = "PURCHASING";
	public String ATTRIBUTE_ACCOUNTS_PAYABLE = "ACCOUNTS_PAYABLE";
	public String ATTRIBUTE_RECEIVING = "RECEIVING";
	public String ATTRIBUTE_QUALITY_ASSURANCE = "QUALITY_ASSURANCE";
	public String ATTRIBUTE_MANAGEMENT = "MANAGEMENT";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1 = "SHIP_TO_ADDRESS_LINE_1";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2 = "SHIP_TO_ADDRESS_LINE_2";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3 = "SHIP_TO_ADDRESS_LINE_3";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4 = "SHIP_TO_ADDRESS_LINE_4";
	public String ATTRIBUTE_SHIP_TO_COUNTRY_ABBREV = "SHIP_TO_COUNTRY_ABBREV";
	public String ATTRIBUTE_SHIP_TO_STATE_ABBREV = "SHIP_TO_STATE_ABBREV";
	public String ATTRIBUTE_SHIP_TO_CITY = "SHIP_TO_CITY";
	public String ATTRIBUTE_SHIP_TO_ZIP = "SHIP_TO_ZIP";
	public String ATTRIBUTE_SHIP_TO_ZIP_PLUS = "SHIP_TO_ZIP_PLUS";
	public String ATTRIBUTE_FEDERAL_TAX_ID = "FEDERAL_TAX_ID";
	public String ATTRIBUTE_CUSTOMER_NOTES = "CUSTOMER_NOTES";
	public String ATTRIBUTE_REQUESTER = "REQUESTER";
	public String ATTRIBUTE_APPROVER = "APPROVER";
	public String ATTRIBUTE_PAYMENT_TERMS_APPROVER = "PAYMENT_TERMS_APPROVER";
	public String ATTRIBUTE_CUSTOMER_REQUEST_STATUS = "CUSTOMER_REQUEST_STATUS";
	public String ATTRIBUTE_REASON_FOR_REJECTION = "REASON_FOR_REJECTION";
	public String ATTRIBUTE_TRANSACTION_DATE = "TRANSACTION_DATE";
	public String ATTRIBUTE_DATE_APPROVED = "DATE_APPROVED";
	public String ATTRIBUTE_DATE_PAYMENT_TERMS_APPROVED = "DATE_PAYMENT_TERMS_APPROVED";
	public String ATTRIBUTE_CUSTOMER_REQUEST_TYPE = "CUSTOMER_REQUEST_TYPE";
	public String ATTRIBUTE_VAT_REGISTRATION_NUMBER = "VAT_REGISTRATION_NUMBER";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_NEW_COMPANY_ID = "NEW_COMPANY_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_COMPANY_NAME = "COMPANY_NAME";
	public String ATTRIBUTE_COMPANY_SHORT_NAME = "COMPANY_SHORT_NAME";
	public String ATTRIBUTE_LEGACY_CUSTOMER_ID = "LEGACY_CUSTOMER_ID";
	public String ATTRIBUTE_SAP_INDUSTRY_KEY = "SAP_INDUSTRY_KEY";
	public String ATTRIBUTE_ABC_CLASSIFICATION = "ABC_CLASSIFICATION";
	public String ATTRIBUTE_JDE_CUSTOMER_BILL_TO = "JDE_CUSTOMER_BILL_TO";
	public String ATTRIBUTE_AUTO_EMAIL_INVOICE = "AUTO_EMAIL_INVOICE";
	public String ATTRIBUTE_AUTO_EMAIL_ADDRESS = "AUTO_EMAIL_ADDRESS";
	public String ATTRIBUTE_AUTO_EMAIL_BATCH_SIZE = "AUTO_EMAIL_BATCH_SIZE";

	//table name
	public String TABLE = "CUSTOMER_ADD_REQUEST";


	//constructor
	public CustomerAddRequestBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("customerRequestId")) {
			return ATTRIBUTE_CUSTOMER_REQUEST_ID;
		}
		else if(attributeName.equals("customerId")) {
			return ATTRIBUTE_CUSTOMER_ID;
		}
		else if(attributeName.equals("customerName")) {
			return ATTRIBUTE_CUSTOMER_NAME;
		}
		else if(attributeName.equals("billToCompanyId")) {
			return ATTRIBUTE_BILL_TO_COMPANY_ID;
		}
		else if(attributeName.equals("billToLocationId")) {
			return ATTRIBUTE_BILL_TO_LOCATION_ID;
		}
		else if(attributeName.equals("payerId")) {
			return ATTRIBUTE_PAYER_ID;
		}
		else if(attributeName.equals("industry")) {
			return ATTRIBUTE_INDUSTRY;
		}
		else if(attributeName.equals("invoiceConsolidation")) {
			return ATTRIBUTE_INVOICE_CONSOLIDATION;
		}
		else if(attributeName.equals("paymentTerms")) {
			return ATTRIBUTE_PAYMENT_TERMS;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("fixedCurrency")) {
			return ATTRIBUTE_FIXED_CURRENCY;
		}
		else if(attributeName.equals("creditLimit")) {
			return ATTRIBUTE_CREDIT_LIMIT;
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
		else if(attributeName.equals("priceGroupId")) {
			return ATTRIBUTE_PRICE_GROUP_ID;
		}
		else if(attributeName.equals("shelfLifeRequired")) {
			return ATTRIBUTE_SHELF_LIFE_REQUIRED;
		}
		else if(attributeName.equals("shipComplete")) {
			return ATTRIBUTE_SHIP_COMPLETE;
		}
		else if(attributeName.equals("sapCustomerCode")) {
			return ATTRIBUTE_SAP_CUSTOMER_CODE;
		}
		else if(attributeName.equals("taxRegistrationType")) {
			return ATTRIBUTE_TAX_REGISTRATION_TYPE;
		}
		else if(attributeName.equals("taxRegistrationNumber")) {
			return ATTRIBUTE_TAX_REGISTRATION_NUMBER;
		}
		else if(attributeName.equals("taxRegistrationType2")) {
			return ATTRIBUTE_TAX_REGISTRATION_TYPE_2;
		}
		else if(attributeName.equals("taxRegistrationNumber2")) {
			return ATTRIBUTE_TAX_REGISTRATION_NUMBER_2;
		}
		else if(attributeName.equals("taxRegistrationType3")) {
			return ATTRIBUTE_TAX_REGISTRATION_TYPE_3;
		}
		else if(attributeName.equals("taxRegistrationNumber3")) {
			return ATTRIBUTE_TAX_REGISTRATION_NUMBER_3;
		}
		else if(attributeName.equals("taxRegistrationType4")) {
			return ATTRIBUTE_TAX_REGISTRATION_TYPE_4;
		}
		else if(attributeName.equals("taxRegistrationNumber4")) {
			return ATTRIBUTE_TAX_REGISTRATION_NUMBER_4;
		}
		else if(attributeName.equals("taxCurrencyId")) {
			return ATTRIBUTE_TAX_CURRENCY_ID;
		}
		else if(attributeName.equals("exitLabelFormat")) {
			return ATTRIBUTE_EXIT_LABEL_FORMAT;
		}
		else if(attributeName.equals("emailOrderAck")) {
			return ATTRIBUTE_EMAIL_ORDER_ACK;
		}
		else if(attributeName.equals("shipMfgCoc")) {
			return ATTRIBUTE_SHIP_MFG_COC;
		}
		else if(attributeName.equals("shipMfgCoa")) {
			return ATTRIBUTE_SHIP_MFG_COA;
		}
		else if(attributeName.equals("shipMsds")) {
			return ATTRIBUTE_SHIP_MSDS;
		}
		else if(attributeName.equals("countryAbbrev")) {
			return ATTRIBUTE_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("stateAbbrev")) {
			return ATTRIBUTE_STATE_ABBREV;
		}
		else if(attributeName.equals("addressLine1")) {
			return ATTRIBUTE_ADDRESS_LINE_1;
		}
		else if(attributeName.equals("addressLine2")) {
			return ATTRIBUTE_ADDRESS_LINE_2;
		}
		else if(attributeName.equals("addressLine3")) {
			return ATTRIBUTE_ADDRESS_LINE_3;
		}
		else if(attributeName.equals("addressLine4")) {
			return ATTRIBUTE_ADDRESS_LINE_4;
		}
		else if(attributeName.equals("city")) {
			return ATTRIBUTE_CITY;
		}
		else if(attributeName.equals("zip")) {
			return ATTRIBUTE_ZIP;
		}
		else if(attributeName.equals("zipPlus")) {
			return ATTRIBUTE_ZIP_PLUS;
		}
		else if(attributeName.equals("locationDesc")) {
			return ATTRIBUTE_LOCATION_DESC;
		}
		else if(attributeName.equals("contactPersonnelId")) {
			return ATTRIBUTE_CONTACT_PERSONNEL_ID;
		}
		else if(attributeName.equals("fieldSalesRepId")) {
			return ATTRIBUTE_FIELD_SALES_REP_ID;
		}
		else if(attributeName.equals("contactType")) {
			return ATTRIBUTE_CONTACT_TYPE;
		}
		else if(attributeName.equals("firstName")) {
			return ATTRIBUTE_FIRST_NAME;
		}
		else if(attributeName.equals("lastName")) {
			return ATTRIBUTE_LAST_NAME;
		}
		else if(attributeName.equals("nickname")) {
			return ATTRIBUTE_NICKNAME;
		}
		else if(attributeName.equals("phone")) {
			return ATTRIBUTE_PHONE;
		}
		else if(attributeName.equals("mobile")) {
			return ATTRIBUTE_MOBILE;
		}
		else if(attributeName.equals("fax")) {
			return ATTRIBUTE_FAX;
		}
		else if(attributeName.equals("email")) {
			return ATTRIBUTE_EMAIL;
		}
		else if(attributeName.equals("purchasing")) {
			return ATTRIBUTE_PURCHASING;
		}
		else if(attributeName.equals("accountsPayable")) {
			return ATTRIBUTE_ACCOUNTS_PAYABLE;
		}
		else if(attributeName.equals("receiving")) {
			return ATTRIBUTE_RECEIVING;
		}
		else if(attributeName.equals("qualityAssurance")) {
			return ATTRIBUTE_QUALITY_ASSURANCE;
		}
		else if(attributeName.equals("management")) {
			return ATTRIBUTE_MANAGEMENT;
		}
		else if(attributeName.equals("shipToAddressLine1")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1;
		}
		else if(attributeName.equals("shipToAddressLine2")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2;
		}
		else if(attributeName.equals("shipToAddressLine3")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3;
		}
		else if(attributeName.equals("shipToAddressLine4")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4;
		}
		else if(attributeName.equals("shipToCountryAbbrev")) {
			return ATTRIBUTE_SHIP_TO_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("shipToStateAbbrev")) {
			return ATTRIBUTE_SHIP_TO_STATE_ABBREV;
		}
		else if(attributeName.equals("shipToCity")) {
			return ATTRIBUTE_SHIP_TO_CITY;
		}
		else if(attributeName.equals("shipToZip")) {
			return ATTRIBUTE_SHIP_TO_ZIP;
		}
		else if(attributeName.equals("shipToZipPlus")) {
			return ATTRIBUTE_SHIP_TO_ZIP_PLUS;
		}
		else if(attributeName.equals("federalTaxId")) {
			return ATTRIBUTE_FEDERAL_TAX_ID;
		}
		else if(attributeName.equals("customerNotes")) {
			return ATTRIBUTE_CUSTOMER_NOTES;
		}
		else if(attributeName.equals("requester")) {
			return ATTRIBUTE_REQUESTER;
		}
		else if(attributeName.equals("approver")) {
			return ATTRIBUTE_APPROVER;
		}
		else if(attributeName.equals("paymentTermsApprover")) {
			return ATTRIBUTE_PAYMENT_TERMS_APPROVER;
		}
		else if(attributeName.equals("customerRequestStatus")) {
			return ATTRIBUTE_CUSTOMER_REQUEST_STATUS;
		}
		else if(attributeName.equals("reasonForRejection")) {
			return ATTRIBUTE_REASON_FOR_REJECTION;
		}
		else if(attributeName.equals("transactionDate")) {
			return ATTRIBUTE_TRANSACTION_DATE;
		}
		else if(attributeName.equals("dateApproved")) {
			return ATTRIBUTE_DATE_APPROVED;
		}
		else if(attributeName.equals("datePaymentTermsApproved")) {
			return ATTRIBUTE_DATE_PAYMENT_TERMS_APPROVED;
		}
		else if(attributeName.equals("customerRequestType")) {
			return ATTRIBUTE_CUSTOMER_REQUEST_TYPE;
		}
		else if(attributeName.equals("vatRegistrationNumber")) {
			return ATTRIBUTE_VAT_REGISTRATION_NUMBER;
		}
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("legacyCustomerId")) {
			return ATTRIBUTE_LEGACY_CUSTOMER_ID;
		}
		else if(attributeName.equals("sapIndustryKey")) {
			return ATTRIBUTE_SAP_INDUSTRY_KEY;
		}
		else if(attributeName.equals("abcClassification")) {
			return ATTRIBUTE_ABC_CLASSIFICATION;
		}
		else if(attributeName.equals("jdeCustomerBillTo")) {
			return ATTRIBUTE_JDE_CUSTOMER_BILL_TO;
		}
		
		else if(attributeName.equals("autoEmailInvoice")) {
			return ATTRIBUTE_AUTO_EMAIL_INVOICE;
		}
		else if(attributeName.equals("autoEmailAddress")) {
			return ATTRIBUTE_AUTO_EMAIL_ADDRESS;
		}
		else if(attributeName.equals("autoEmailBatchSize")) {
			return ATTRIBUTE_AUTO_EMAIL_BATCH_SIZE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CustomerAddRequestViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
	//delete
	public int delete(CustomerAddRequestViewBean CustomerAddRequestViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("customerRequestId", SearchCriterion.EQUALS,
			"" + CustomerAddRequestViewBean.getCustomerRequestId());

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


	public int delete(CustomerAddRequestViewBean CustomerAddRequestViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("customerRequestId", "SearchCriterion.EQUALS",
			"" + CustomerAddRequestViewBean.getCustomerRequestId());

		return delete(criteria, conn);
	}

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
		
		String localeQuery = " delete from " + TABLE + "_locale " +
			getWhereClause(criteria);
		
		new SqlManager().update(conn, localeQuery);
		
		String sqlQuery = " delete from " + TABLE + " " +
			getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}


//you need to verify the primary key(s) before uncommenting this
	//insert
	public int insert(CustomerAddRequestViewBean CustomerAddRequestViewBean,String locale)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(CustomerAddRequestViewBean, connection, locale);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(CustomerAddRequestViewBean customerAddRequestViewBean, Connection conn,String locale)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();
		
		if(!"en_US".equals(locale)) 
        	customerAddRequestViewBean.setCustomerName(null);
      
		
		customerAddRequestViewBean.setLocationDesc(customerAddRequestViewBean.getCustomerName());
		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_CUSTOMER_REQUEST_ID + "," +
			ATTRIBUTE_CUSTOMER_ID + "," +
			ATTRIBUTE_CUSTOMER_NAME + "," +
			ATTRIBUTE_BILL_TO_COMPANY_ID + "," +
			ATTRIBUTE_BILL_TO_LOCATION_ID + "," +
			ATTRIBUTE_PAYER_ID + "," +
			ATTRIBUTE_INDUSTRY + "," +
			ATTRIBUTE_INVOICE_CONSOLIDATION + "," +
			ATTRIBUTE_PAYMENT_TERMS + "," +
			ATTRIBUTE_CURRENCY_ID + "," +
			ATTRIBUTE_FIXED_CURRENCY + "," +
			ATTRIBUTE_CREDIT_LIMIT + "," +
			ATTRIBUTE_OVERDUE_LIMIT + "," +
			ATTRIBUTE_OVERDUE_LIMIT_BASIS + "," +
			ATTRIBUTE_CREDIT_STATUS + "," +
			ATTRIBUTE_PRICE_GROUP_ID + "," +
			ATTRIBUTE_SHELF_LIFE_REQUIRED + "," +
			ATTRIBUTE_SHIP_COMPLETE + "," +
			ATTRIBUTE_SAP_CUSTOMER_CODE + "," +
			ATTRIBUTE_TAX_REGISTRATION_TYPE + "," +
			ATTRIBUTE_TAX_REGISTRATION_NUMBER + "," +
			ATTRIBUTE_TAX_REGISTRATION_TYPE_2 + "," +
			ATTRIBUTE_TAX_REGISTRATION_NUMBER_2 + "," +
			ATTRIBUTE_TAX_REGISTRATION_TYPE_3 + "," +
			ATTRIBUTE_TAX_REGISTRATION_NUMBER_3 + "," +
			ATTRIBUTE_TAX_REGISTRATION_TYPE_4 + "," +
			ATTRIBUTE_TAX_REGISTRATION_NUMBER_4 + "," +
			ATTRIBUTE_TAX_CURRENCY_ID + "," +
			ATTRIBUTE_EXIT_LABEL_FORMAT + "," +
			ATTRIBUTE_EMAIL_ORDER_ACK + "," +
			ATTRIBUTE_SHIP_MFG_COC + "," +
			ATTRIBUTE_SHIP_MFG_COA + "," +
			ATTRIBUTE_SHIP_MSDS + "," +
			ATTRIBUTE_COUNTRY_ABBREV + "," +
			ATTRIBUTE_STATE_ABBREV + "," +
			ATTRIBUTE_ADDRESS_LINE_1 + "," +
			ATTRIBUTE_ADDRESS_LINE_2 + "," +
			ATTRIBUTE_ADDRESS_LINE_3 + "," +
			ATTRIBUTE_ADDRESS_LINE_4 + "," +
			ATTRIBUTE_CITY + "," +
			ATTRIBUTE_ZIP + "," +
			ATTRIBUTE_ZIP_PLUS + "," +
			ATTRIBUTE_LOCATION_DESC + "," +
			ATTRIBUTE_FIELD_SALES_REP_ID + "," +
			//ATTRIBUTE_CONTACT_PERSONNEL_ID + "," +
			//ATTRIBUTE_CONTACT_TYPE + "," +
			//ATTRIBUTE_FIRST_NAME + "," +
			//ATTRIBUTE_LAST_NAME + "," +
			//ATTRIBUTE_NICKNAME + "," +
			//ATTRIBUTE_PHONE + "," +
			//ATTRIBUTE_MOBILE + "," +
			//ATTRIBUTE_FAX + "," +
			//ATTRIBUTE_EMAIL + "," +
			//ATTRIBUTE_PURCHASING + "," +
			//ATTRIBUTE_ACCOUNTS_PAYABLE + "," +
			//ATTRIBUTE_RECEIVING + "," +
			//ATTRIBUTE_QUALITY_ASSURANCE + "," +
			//ATTRIBUTE_MANAGEMENT + "," +
			//ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1 + "," +
			//ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2 + "," +
			//ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3 + "," +
			//ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4 + "," +
			//ATTRIBUTE_SHIP_TO_COUNTRY_ABBREV + "," +
			//ATTRIBUTE_SHIP_TO_STATE_ABBREV + "," +
			//ATTRIBUTE_SHIP_TO_CITY + "," +
			//ATTRIBUTE_SHIP_TO_ZIP + "," +
			//ATTRIBUTE_SHIP_TO_ZIP_PLUS + "," +
			//ATTRIBUTE_FEDERAL_TAX_ID + "," +
			ATTRIBUTE_CUSTOMER_NOTES + "," +
			ATTRIBUTE_REQUESTER + "," +
			ATTRIBUTE_APPROVER + "," +
			ATTRIBUTE_PAYMENT_TERMS_APPROVER + "," +
			ATTRIBUTE_CUSTOMER_REQUEST_STATUS + "," +
			ATTRIBUTE_REASON_FOR_REJECTION + "," +
			ATTRIBUTE_TRANSACTION_DATE + "," +
			ATTRIBUTE_DATE_APPROVED + "," +
//			ATTRIBUTE_DATE_PAYMENT_TERMS_APPROVED + "," +
			ATTRIBUTE_CUSTOMER_REQUEST_TYPE + "," +
//			ATTRIBUTE_VAT_REGISTRATION_NUMBER + "," +
			ATTRIBUTE_NEW_COMPANY_ID + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_COMPANY_NAME + "," +
			ATTRIBUTE_COMPANY_SHORT_NAME + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "," +
			ATTRIBUTE_LEGACY_CUSTOMER_ID + "," +
			ATTRIBUTE_ABC_CLASSIFICATION + "," +
			ATTRIBUTE_SAP_INDUSTRY_KEY + "," +
			ATTRIBUTE_AUTO_EMAIL_INVOICE + "," +
			ATTRIBUTE_AUTO_EMAIL_ADDRESS + "," +
			ATTRIBUTE_AUTO_EMAIL_BATCH_SIZE + "," +
			ATTRIBUTE_JDE_CUSTOMER_BILL_TO + ")" +			
			" values (" +
			customerAddRequestViewBean.getCustomerRequestId() + "," +
			customerAddRequestViewBean.getCustomerId() + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getCustomerName()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getBillToCompanyId()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getBillToLocationId()) + "," +
			customerAddRequestViewBean.getPayerId() + "," +
			customerAddRequestViewBean.getIndustry() + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getInvoiceConsolidation()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getPaymentTerms()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getCurrencyId()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getFixedCurrency()) + "," +
			customerAddRequestViewBean.getCreditLimit() + "," +
			customerAddRequestViewBean.getOverdueLimit() + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getOverdueLimitBasis()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getCreditStatus()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getPriceGroupId()) + "," +
			customerAddRequestViewBean.getShelfLifeRequired() + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getShipComplete()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getSapCustomerCode()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getTaxRegistrationType()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getTaxRegistrationNumber()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getTaxRegistrationType2()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getTaxRegistrationNumber2()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getTaxRegistrationType3()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getTaxRegistrationNumber3()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getTaxRegistrationType4()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getTaxRegistrationNumber4()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getTaxCurrencyId()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getExitLabelFormat()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getEmailOrderAck()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getShipMfgCoc()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getShipMfgCoa()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getShipMsds()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getCountryAbbrev()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getStateAbbrev()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getAddressLine1()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getAddressLine2()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getAddressLine3()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getAddressLine4()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getCity()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getZip()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getZipPlus()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getLocationDesc()) + "," +
			customerAddRequestViewBean.getFieldSalesRepId() + "," +
			//customerAddRequestViewBean.getContactPersonnelId() + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getContactType()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getFirstName()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getLastName()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getNickname()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getPhone()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getMobile()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getFax()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getEmail()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getPurchasing()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getAccountsPayable()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getReceiving()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getQualityAssurance()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getManagement()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getShipToAddressLine1()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getShipToAddressLine2()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getShipToAddressLine3()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getShipToAddressLine4()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getShipToCountryAbbrev()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getShipToStateAbbrev()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getShipToCity()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getShipToZip()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getShipToZipPlus()) + "," +
			//SqlHandler.delimitString(customerAddRequestViewBean.getFederalTaxId()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getCustomerNotes()) + "," +
			customerAddRequestViewBean.getRequester() + "," +
			customerAddRequestViewBean.getApprover() + "," +
			customerAddRequestViewBean.getPaymentTermsApprover() + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getCustomerRequestStatus()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getReasonForRejection()) + "," +
			"sysdate" + "," +
//			DateHandler.getOracleToDateFunction(customerAddRequestViewBean.getDateApproved()) + "," +
			DateHandler.getOracleToDateFunction(customerAddRequestViewBean.getDatePaymentTermsApproved()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getCustomerRequestType()) + "," +
//			SqlHandler.delimitString(customerAddRequestViewBean.getVatRegistrationNumber()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getNewCompanyId()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getCompanyName()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getCompanyShortName()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getOpsEntityId()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getLegacyCustomerId()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getAbcClassification()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getSapIndustryKey()) + "," +
	        SqlHandler.delimitString(customerAddRequestViewBean.getAutoEmailInvoice()) + "," +
	    	SqlHandler.delimitString(customerAddRequestViewBean.getAutoEmailAddress()) + "," +
			StringHandler.nullIfZero(customerAddRequestViewBean.getAutoEmailBatchSize()) + "," +
			StringHandler.nullIfZero(customerAddRequestViewBean.getJdeCustomerBillTo()) + ")";
		
		int result = sqlManager.update(conn, query);
		
		if(!"en_US".equals(locale)) {
			query = "insert into " + TABLE + "_locale (" + 
			ATTRIBUTE_CUSTOMER_REQUEST_ID + "," +
			ATTRIBUTE_CUSTOMER_NAME + "," +
			ATTRIBUTE_COMPANY_NAME + "," +
			ATTRIBUTE_COMPANY_SHORT_NAME + ", locale_code)"+ 
			" values (" +
			customerAddRequestViewBean.getCustomerRequestId() + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getCustomerName()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getCompanyName()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getCompanyShortName()) + ",'" + locale + "')";
	
			sqlManager.update(conn, query);
		}
	
		return result;
	}

	//update
	public int update(CustomerAddRequestViewBean CustomerAddRequestViewBean,boolean updateCompanyApproveDate,String locale)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(CustomerAddRequestViewBean, connection,updateCompanyApproveDate,locale);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}
	//update payment terms
	
	public int updatePaymentTerms(CustomerAddRequestViewBean CustomerAddRequestViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = updatePaymentTerms(CustomerAddRequestViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int updatePaymentTerms(CustomerAddRequestViewBean CustomerAddRequestViewBean, Connection conn)
	throws BaseException {

    String query  = "update " + TABLE + " set " +
		ATTRIBUTE_PAYMENT_TERMS + "=" + 
			SqlHandler.delimitString(CustomerAddRequestViewBean.getPaymentTerms()) + "," +
		ATTRIBUTE_CUSTOMER_NOTES + "=" + 
			SqlHandler.delimitString(CustomerAddRequestViewBean.getCustomerNotes()) + "," +
//		ATTRIBUTE_PAYMENT_TERM_APPROVER + "=" + 
//			StringHandler.nullIfZero(CustomerAddRequestViewBean.getPaymentTermApprover()) + "," +
		ATTRIBUTE_CUSTOMER_REQUEST_STATUS + "=" + 
			SqlHandler.delimitString(CustomerAddRequestViewBean.getCustomerRequestStatus()) +
//		ATTRIBUTE_DATE_PAYMENT_TERMS_APPROVED + "=" + 
//			DateHandler.getOracleToDateFunction(CustomerAddRequestViewBean.getDatePaymentTermsApproved()) +
		" where " + ATTRIBUTE_CUSTOMER_REQUEST_ID + "=" +
			CustomerAddRequestViewBean.getCustomerRequestId();

	return new SqlManager().update(conn, query);
}

	//approve payment terms direct
	
	public int approvePaymentTermsDirect(CustomerAddRequestViewBean CustomerAddRequestViewBean,boolean updateCustomerFlag)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = approvePaymentTermsDirect(CustomerAddRequestViewBean, connection,updateCustomerFlag);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int approvePaymentTermsDirect(CustomerAddRequestViewBean CustomerAddRequestViewBean, Connection conn, boolean updateCustomerFlag)
	throws BaseException {

    String query  = "update " + TABLE + " set " +
		ATTRIBUTE_PAYMENT_TERMS + "=" + 
			SqlHandler.delimitString(CustomerAddRequestViewBean.getPaymentTerms()) + "," +
		ATTRIBUTE_CUSTOMER_NOTES + "=" + 
			SqlHandler.delimitString(CustomerAddRequestViewBean.getCustomerNotes()) + "," +
		ATTRIBUTE_PAYMENT_TERMS_APPROVER + "=" + 
			StringHandler.nullIfZero(CustomerAddRequestViewBean.getPaymentTermsApprover()) + "," +
		ATTRIBUTE_CUSTOMER_REQUEST_STATUS + "=" + 
			SqlHandler.delimitString(CustomerAddRequestViewBean.getCustomerRequestStatus()) + "," +
		ATTRIBUTE_DATE_PAYMENT_TERMS_APPROVED + "=" + 
			DateHandler.getOracleToDateFunction(CustomerAddRequestViewBean.getDatePaymentTermsApproved()) +
		" where " + ATTRIBUTE_CUSTOMER_REQUEST_ID + "=" +
			CustomerAddRequestViewBean.getCustomerRequestId();

    int r1 = new SqlManager().update(conn, query);
    if( updateCustomerFlag ) {
    	query = "update customer set default_payment_terms = " + 
    	SqlHandler.delimitString(CustomerAddRequestViewBean.getPaymentTerms()) + 
    	" where customer = " + CustomerAddRequestViewBean.getCustomerId() ;
    	int r2 = new SqlManager().update(conn, query);
    }
    return r1;
}

	
	public int update(CustomerAddRequestViewBean customerAddRequestViewBean, Connection conn,boolean updatePaymentApproveDate, String locale) throws BaseException {
		customerAddRequestViewBean.setLocationDesc(customerAddRequestViewBean.getCustomerName());
        SqlManager sqlManager = new SqlManager();

        if (customerAddRequestViewBean.getStateAbbrev() == null ||
                "None".equalsIgnoreCase(customerAddRequestViewBean.getStateAbbrev()) ||
                customerAddRequestViewBean.getStateAbbrev().length() ==0)
	    {
		  customerAddRequestViewBean.setStateAbbrev("");
	    }
        
        if(!"en_US".equals(locale)) {
            //first delete current data
            String query = "delete from " + TABLE + "_locale where locale_code = '" + locale + "' and " + ATTRIBUTE_CUSTOMER_REQUEST_ID + "=" +customerAddRequestViewBean.getCustomerRequestId();
            sqlManager.update(conn, query);
            //insert new data
            query = "insert into " + TABLE + "_locale (" +
			ATTRIBUTE_CUSTOMER_REQUEST_ID + "," +
			ATTRIBUTE_CUSTOMER_NAME + "," +
			ATTRIBUTE_COMPANY_NAME + "," +
			ATTRIBUTE_COMPANY_SHORT_NAME + ", locale_code)"+
			" values (" +
			customerAddRequestViewBean.getCustomerRequestId() + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getCustomerName()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getCompanyName()) + "," +
			SqlHandler.delimitString(customerAddRequestViewBean.getCompanyShortName()) + ",'" +
			locale + "')";
            sqlManager.update(conn, query);
        	customerAddRequestViewBean.setCustomerName(null);
        }

         String s1 = ""; // for date payment terms approved
         String s2 = ""; // for transaction date

         if( updatePaymentApproveDate ){
        	s1 = ATTRIBUTE_DATE_PAYMENT_TERMS_APPROVED + "=" + 
			DateHandler.getOracleToDateFunction(customerAddRequestViewBean.getDatePaymentTermsApproved()) + ","+
				ATTRIBUTE_PAYMENT_TERMS_APPROVER + "=" + 
					customerAddRequestViewBean.getPaymentTermsApprover() + ",";

        	//	ATTRIBUTE_TRANSACTION_DATE + "=" + 
		//	DateHandler.getOracleToDateFunction(customerAddRequestViewBean.getTransactionDate()) + "," +
        
        }
        String query  = "update " + TABLE + " set " +
	ATTRIBUTE_CUSTOMER_REQUEST_ID + "=" + 
		StringHandler.nullIfZero(customerAddRequestViewBean.getCustomerRequestId()) + "," +
	ATTRIBUTE_CUSTOMER_ID + "=" + 
		StringHandler.nullIfZero(customerAddRequestViewBean.getCustomerId()) + "," +
    ATTRIBUTE_BILL_TO_COMPANY_ID + "=" +
		SqlHandler.delimitString(customerAddRequestViewBean.getBillToCompanyId()) + "," +
	ATTRIBUTE_BILL_TO_LOCATION_ID + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getBillToLocationId()) + "," +
	ATTRIBUTE_PAYER_ID + "=" + 
		StringHandler.nullIfZero(customerAddRequestViewBean.getPayerId()) + "," +
	ATTRIBUTE_INDUSTRY + "=" + 
		StringHandler.nullIfZero(customerAddRequestViewBean.getIndustry()) + "," +
	ATTRIBUTE_INVOICE_CONSOLIDATION + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getInvoiceConsolidation()) + "," +
	ATTRIBUTE_PAYMENT_TERMS + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getPaymentTerms()) + "," +
	ATTRIBUTE_CURRENCY_ID + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getCurrencyId()) + "," +
	ATTRIBUTE_FIXED_CURRENCY + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getFixedCurrency()) + "," +
	ATTRIBUTE_CREDIT_LIMIT + "=" + 
		customerAddRequestViewBean.getCreditLimit() + "," +
	ATTRIBUTE_OVERDUE_LIMIT + "=" + 
		customerAddRequestViewBean.getOverdueLimit() + "," +
	ATTRIBUTE_OVERDUE_LIMIT_BASIS + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getOverdueLimitBasis()) + "," +
	ATTRIBUTE_CREDIT_STATUS + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getCreditStatus()) + "," +
	ATTRIBUTE_PRICE_GROUP_ID + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getPriceGroupId()) + "," +
	ATTRIBUTE_SHELF_LIFE_REQUIRED + "=" + 
		customerAddRequestViewBean.getShelfLifeRequired() + "," +
	ATTRIBUTE_SHIP_COMPLETE + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getShipComplete()) + "," +
	ATTRIBUTE_SAP_CUSTOMER_CODE + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getSapCustomerCode()) + "," +
	ATTRIBUTE_TAX_REGISTRATION_TYPE + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getTaxRegistrationType()) + "," +
	ATTRIBUTE_TAX_REGISTRATION_NUMBER + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getTaxRegistrationNumber()) + "," +
	ATTRIBUTE_TAX_REGISTRATION_TYPE_2 + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getTaxRegistrationType2()) + "," +
	ATTRIBUTE_TAX_REGISTRATION_NUMBER_2 + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getTaxRegistrationNumber2()) + "," +
	ATTRIBUTE_TAX_REGISTRATION_TYPE_3 + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getTaxRegistrationType3()) + "," +
	ATTRIBUTE_TAX_REGISTRATION_NUMBER_3 + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getTaxRegistrationNumber3()) + "," +
	ATTRIBUTE_TAX_REGISTRATION_TYPE_4 + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getTaxRegistrationType4()) + "," +
	ATTRIBUTE_TAX_REGISTRATION_NUMBER_4 + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getTaxRegistrationNumber4()) + "," +
	ATTRIBUTE_TAX_CURRENCY_ID + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getTaxCurrencyId()) + "," +
	ATTRIBUTE_EXIT_LABEL_FORMAT + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getExitLabelFormat()) + "," +
	ATTRIBUTE_EMAIL_ORDER_ACK + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getEmailOrderAck()) + "," +
	ATTRIBUTE_SHIP_MFG_COC + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getShipMfgCoc()) + "," +
	ATTRIBUTE_SHIP_MFG_COA + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getShipMfgCoa()) + "," +
	ATTRIBUTE_SHIP_MSDS + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getShipMsds()) + "," +
	ATTRIBUTE_COUNTRY_ABBREV + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getCountryAbbrev()) + "," +
	ATTRIBUTE_STATE_ABBREV + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getStateAbbrev()) + "," +
	ATTRIBUTE_ADDRESS_LINE_1 + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getAddressLine1()) + "," +
	ATTRIBUTE_ADDRESS_LINE_2 + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getAddressLine2()) + "," +
	ATTRIBUTE_ADDRESS_LINE_3 + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getAddressLine3()) + "," +
	ATTRIBUTE_ADDRESS_LINE_4 + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getAddressLine4()) + "," +
	ATTRIBUTE_ADDRESS_LINE_5 + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getAddressLine5()) + "," +
	ATTRIBUTE_CITY + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getCity()) + "," +
	ATTRIBUTE_ZIP + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getZip()) + "," +
	ATTRIBUTE_ZIP_PLUS + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getZipPlus()) + "," +
	ATTRIBUTE_LOCATION_DESC + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getLocationDesc()) + "," +
	ATTRIBUTE_CHARGE_FREIGHT + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getChargeFreight()) + "," +
	ATTRIBUTE_FIELD_SALES_REP_ID + "=" + 
		StringHandler.nullIfZero(customerAddRequestViewBean.getFieldSalesRepId()) + "," +
//	ATTRIBUTE_CONTACT_PERSONNEL_ID + "=" + 
//		StringHandler.nullIfZero(customerAddRequestViewBean.getContactPersonnelId()) + "," +
//	ATTRIBUTE_CONTACT_TYPE + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getContactType()) + "," +
//	ATTRIBUTE_FIRST_NAME + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getFirstName()) + "," +
//	ATTRIBUTE_LAST_NAME + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getLastName()) + "," +
//	ATTRIBUTE_NICKNAME + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getNickname()) + "," +
//	ATTRIBUTE_PHONE + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getPhone()) + "," +
//	ATTRIBUTE_MOBILE + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getMobile()) + "," +
//	ATTRIBUTE_FAX + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getFax()) + "," +
//	ATTRIBUTE_EMAIL + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getEmail()) + "," +
//	ATTRIBUTE_PURCHASING + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getPurchasing()) + "," +
//	ATTRIBUTE_ACCOUNTS_PAYABLE + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getAccountsPayable()) + "," +
//	ATTRIBUTE_RECEIVING + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getReceiving()) + "," +
//	ATTRIBUTE_QUALITY_ASSURANCE + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getQualityAssurance()) + "," +
//	ATTRIBUTE_MANAGEMENT + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getManagement()) + "," +
//	ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1 + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getShipToAddressLine1()) + "," +
//	ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2 + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getShipToAddressLine2()) + "," +
//	ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3 + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getShipToAddressLine3()) + "," +
//	ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4 + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getShipToAddressLine4()) + "," +
//	ATTRIBUTE_SHIP_TO_COUNTRY_ABBREV + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getShipToCountryAbbrev()) + "," +
//	ATTRIBUTE_SHIP_TO_STATE_ABBREV + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getShipToStateAbbrev()) + "," +
//	ATTRIBUTE_SHIP_TO_CITY + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getShipToCity()) + "," +
//	ATTRIBUTE_SHIP_TO_ZIP + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getShipToZip()) + "," +
//	ATTRIBUTE_SHIP_TO_ZIP_PLUS + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getShipToZipPlus()) + "," +
//	ATTRIBUTE_FEDERAL_TAX_ID + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getFederalTaxId()) + "," +
	ATTRIBUTE_CUSTOMER_NOTES + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getCustomerNotes()) + "," +
//	ATTRIBUTE_REQUESTER + "=" + 
//		StringHandler.nullIfZero(customerAddRequestViewBean.getRequester()) + "," +
	ATTRIBUTE_APPROVER + "=" + 
		StringHandler.nullIfZero(customerAddRequestViewBean.getApprover()) + "," +
	ATTRIBUTE_CUSTOMER_REQUEST_STATUS + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getCustomerRequestStatus()) + "," +
	ATTRIBUTE_REASON_FOR_REJECTION + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getReasonForRejection()) + "," +
		s2 +
		ATTRIBUTE_DATE_APPROVED + "=" + 
			DateHandler.getOracleToDateFunction(customerAddRequestViewBean.getDateApproved()) + "," +
		s1 +
		ATTRIBUTE_CUSTOMER_REQUEST_TYPE + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getCustomerRequestType()) + "," +
//		ATTRIBUTE_VAT_REGISTRATION_NUMBER + "=" + 
//		SqlHandler.delimitString(customerAddRequestViewBean.getVatRegistrationNumber()) + "," +
		ATTRIBUTE_NEW_COMPANY_ID + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getNewCompanyId()) + "," +
		ATTRIBUTE_COMPANY_ID + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getCompanyId()) + "," +
		ATTRIBUTE_COMPANY_NAME + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getCompanyName()) + "," +
		ATTRIBUTE_COMPANY_SHORT_NAME + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getCompanyShortName()) + "," +
		ATTRIBUTE_OPS_ENTITY_ID + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getOpsEntityId()) + "," +
		ATTRIBUTE_SAP_INDUSTRY_KEY + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getSapIndustryKey()) + "," +
		ATTRIBUTE_ABC_CLASSIFICATION + "=" + 
		SqlHandler.delimitString(customerAddRequestViewBean.getAbcClassification()) + "," +
		ATTRIBUTE_JDE_CUSTOMER_BILL_TO + "=" +
		StringHandler.nullIfZero(customerAddRequestViewBean.getJdeCustomerBillTo()) + "," +
        ATTRIBUTE_AUTO_EMAIL_INVOICE + "=" +
        SqlHandler.delimitString(customerAddRequestViewBean.getAutoEmailInvoice()) + "," +
    	ATTRIBUTE_AUTO_EMAIL_ADDRESS + "=" +
    	SqlHandler.delimitString(customerAddRequestViewBean.getAutoEmailAddress()) + "," +
    	ATTRIBUTE_AUTO_EMAIL_BATCH_SIZE + "=" +
		StringHandler.nullIfZero(customerAddRequestViewBean.getAutoEmailBatchSize());
    	
        //only update customer name if it is not null
        if (!StringHandler.isBlankString(customerAddRequestViewBean.getCustomerName())) {
            query += ","+ATTRIBUTE_CUSTOMER_NAME + "=" + SqlHandler.delimitString(customerAddRequestViewBean.getCustomerName());
        }
        
        query += " where " + ATTRIBUTE_CUSTOMER_REQUEST_ID + "=" +customerAddRequestViewBean.getCustomerRequestId();
		return sqlManager.update(conn, query);
	}

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

		Collection CustomerAddRequestViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CustomerAddRequestViewBean CustomerAddRequestViewBean = new CustomerAddRequestViewBean();
			load(dataSetRow, CustomerAddRequestViewBean);
			CustomerAddRequestViewBeanColl.add(CustomerAddRequestViewBean);
		}

		return CustomerAddRequestViewBeanColl;
	}
}