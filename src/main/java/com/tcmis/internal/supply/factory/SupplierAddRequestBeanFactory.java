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
import java.util.Locale;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.internal.supply.beans.SupplierAddRequestBean;


/******************************************************************************
 * CLASSNAME: SupplierAddRequestBeanFactory <br>
 * @version: 1.0, Jun 24, 2009 <br>
 *****************************************************************************/


public class SupplierAddRequestBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SUPPLIER_REQUEST_ID = "SUPPLIER_REQUEST_ID";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_SUPPLIER_PARENT = "SUPPLIER_PARENT";
	public String ATTRIBUTE_FEDERAL_TAX_ID = "FEDERAL_TAX_ID";
	public String ATTRIBUTE_SMALL_BUSINESS = "SMALL_BUSINESS";
	public String ATTRIBUTE_MINORITY_BUSINESS = "MINORITY_BUSINESS";
	public String ATTRIBUTE_DISADVANTAGED_BUSINESS = "DISADVANTAGED_BUSINESS";
	public String ATTRIBUTE_DEFAULT_PAYMENT_TERMS = "DEFAULT_PAYMENT_TERMS";
	public String ATTRIBUTE_SUPPLIER_NOTES = "SUPPLIER_NOTES";
	public String ATTRIBUTE_FORMER_SUPPLIER_NAME = "FORMER_SUPPLIER_NAME";
	public String ATTRIBUTE_HAAS_ACCOUNT_NUMBER = "HAAS_ACCOUNT_NUMBER";
	public String ATTRIBUTE_COUNTRY_ABBREV = "COUNTRY_ABBREV";
	public String ATTRIBUTE_STATE_ABBREV = "STATE_ABBREV";
	public String ATTRIBUTE_ADDRESS_LINE_1 = "ADDRESS_LINE_1";
	public String ATTRIBUTE_ADDRESS_LINE_2 = "ADDRESS_LINE_2";
	public String ATTRIBUTE_ADDRESS_LINE_3 = "ADDRESS_LINE_3";
	public String ATTRIBUTE_CITY = "CITY";
	public String ATTRIBUTE_ZIP = "ZIP";
	public String ATTRIBUTE_ZIP_PLUS = "ZIP_PLUS";
	public String ATTRIBUTE_SUPPLIER_MAIN_PHONE = "SUPPLIER_MAIN_PHONE";
	public String ATTRIBUTE_SUPPLIER_MAIN_FAX = "SUPPLIER_MAIN_FAX";
	public String ATTRIBUTE_SUPPLIER_CONTACT_FIRST_NAME = "SUPPLIER_CONTACT_FIRST_NAME";
	public String ATTRIBUTE_SUPPLIER_CONTACT_LAST_NAME = "SUPPLIER_CONTACT_LAST_NAME";
	public String ATTRIBUTE_SUPPLIER_CONTACT_NICKNAME = "SUPPLIER_CONTACT_NICKNAME";
	public String ATTRIBUTE_SUPPLIER_CONTACT_PHONE = "SUPPLIER_CONTACT_PHONE";
	public String ATTRIBUTE_SUPPLIER_CONTACT_PHONE_EXT = "SUPPLIER_CONTACT_PHONE_EXT";
	public String ATTRIBUTE_SUPPLIER_CONTACT_FAX = "SUPPLIER_CONTACT_FAX";
	public String ATTRIBUTE_SUPPLIER_CONTACT_EMAIL = "SUPPLIER_CONTACT_EMAIL";
	public String ATTRIBUTE_E_SUPPLIER_ID = "E_SUPPLIER_ID";
	public String ATTRIBUTE_REQUESTER = "REQUESTER";
	public String ATTRIBUTE_QUALIFICATION_LEVEL = "QUALIFICATION_LEVEL";
	public String ATTRIBUTE_TYPE_OF_PURCHASE = "TYPE_OF_PURCHASE";
	public String ATTRIBUTE_APPROVER = "APPROVER";
	public String ATTRIBUTE_PAYMENT_TERM_APPROVER = "PAYMENT_TERM_APPROVER";
	public String ATTRIBUTE_SUPPLIER_REQUEST_STATUS = "SUPPLIER_REQUEST_STATUS";
	public String ATTRIBUTE_REASON_FOR_REJECTION = "REASON_FOR_REJECTION";
	public String ATTRIBUTE_WOMAN_OWNED = "WOMAN_OWNED";
	public String ATTRIBUTE_TRANSACTION_DATE = "TRANSACTION_DATE";
	public String ATTRIBUTE_DATE_APPROVED = "DATE_APPROVED";
	public String ATTRIBUTE_DATE_PAYMENT_TERMS_APPROVED = "DATE_PAYMENT_TERMS_APPROVED";
	public String ATTRIBUTE_STATE_TAX_ID = "STATE_TAX_ID";
	public String ATTRIBUTE_SUPPLIER_REQUEST_TYPE = "SUPPLIER_REQUEST_TYPE";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SAP_VENDOR_CODE = "SAP_VENDOR_CODE";
	public String ATTRIBUTE_REMIT_TO_COUNTRY_ABBREV = "REMIT_TO_COUNTRY_ABBREV";
	public String ATTRIBUTE_REMIT_TO_STATE_ABBREV = "REMIT_TO_STATE_ABBREV";
	public String ATTRIBUTE_REMIT_TO_ADDRESS_LINE_1 = "REMIT_TO_ADDRESS_LINE_1";
	public String ATTRIBUTE_REMIT_TO_ADDRESS_LINE_2 = "REMIT_TO_ADDRESS_LINE_2";
	public String ATTRIBUTE_REMIT_TO_ADDRESS_LINE_3 = "REMIT_TO_ADDRESS_LINE_3";
	public String ATTRIBUTE_REMIT_TO_CITY = "REMIT_TO_CITY";
	public String ATTRIBUTE_REMIT_TO_ZIP = "REMIT_TO_ZIP";
	public String ATTRIBUTE_REMIT_TO_ZIP_PLUS = "REMIT_TO_ZIP_PLUS";
	public String ATTRIBUTE_ISO_9001_CERTIFIED = "ISO_9001_CERTIFIED";
	public String ATTRIBUTE_HUB_WITH_LOCAL_CERTIFIED = "HUB_WITH_LOCAL_CERTIFIED";
	public String ATTRIBUTE_MBE_WITH_LOCAL_CERTIFIED = "MBE_WITH_LOCAL_CERTIFIED";
	public String ATTRIBUTE_WBE_WITH_LOCAL_CERTIFIED = "WBE_WITH_LOCAL_CERTIFIED";
	public String ATTRIBUTE_SDB_WITH_SBA_CERTIFIED = "SDB_WITH_SBA_CERTIFIED";
	public String ATTRIBUTE_CATEGORY_8A_WITH_SBA_CERTIFIED = "CATEGORY_8A_WITH_SBA_CERTIFIED";
	public String ATTRIBUTE_HUB_WITH_SBA_CERTIFIED = "HUB_WITH_SBA_CERTIFIED";
	public String ATTRIBUTE_EDUCATIONAL_INSTITUTION = "EDUCATIONAL_INSTITUTION";
	public String ATTRIBUTE_MBE_BLACK_AMERICAN = "MBE_BLACK_AMERICAN";
	public String ATTRIBUTE_MBE_HISPANIC_AMERICAN = "MBE_HISPANIC_AMERICAN";
	public String ATTRIBUTE_MBE_ASIAN_PACIFIC_AMERICAN = "MBE_ASIAN_PACIFIC_AMERICAN";
	public String ATTRIBUTE_MBE_NATIVE_AMERICAN = "MBE_NATIVE_AMERICAN";
	public String ATTRIBUTE_MBE_ASIAN_INDIAN_AMERICAN = "MBE_ASIAN_INDIAN_AMERICAN";
	public String ATTRIBUTE_MBE_OTHER = "MBE_OTHER";
	public String ATTRIBUTE_VIET_NAM_VETERAN_OWNED = "VIET_NAM_VETERAN_OWNED";
	public String ATTRIBUTE_DISABLED_VETERAN_OWNED = "DISABLED_VETERAN_OWNED";
	public String ATTRIBUTE_NON_PROFIT_ORGANIZATION = "NON_PROFIT_ORGANIZATION";
	public String ATTRIBUTE_DEBARRED = "DEBARRED";
	public String ATTRIBUTE_AS_9100_CERTIFIED = "AS_9100_CERTIFIED";
	public String ATTRIBUTE_AS_9120_CERTIFIED = "AS_9120_CERTIFIED";
	public String ATTRIBUTE_VAT_REGISTRATION_NUMBER = "VAT_REGISTRATION_NUMBER";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_SSN_FLAG = "SSN_FLAG";
	public String ATTRIBUTE_ORIGINAL_PAYMENT_TERMS = "ORIGINAL_PAYMENT_TERMS";

	//table name
	public String TABLE = "SUPPLIER_ADD_REQUEST";


	//constructor
	public SupplierAddRequestBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("supplierRequestId")) {
			return ATTRIBUTE_SUPPLIER_REQUEST_ID;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("supplierParent")) {
			return ATTRIBUTE_SUPPLIER_PARENT;
		}
		else if(attributeName.equals("federalTaxId")) {
			return ATTRIBUTE_FEDERAL_TAX_ID;
		}
		else if(attributeName.equals("smallBusiness")) {
			return ATTRIBUTE_SMALL_BUSINESS;
		}
		else if(attributeName.equals("minorityBusiness")) {
			return ATTRIBUTE_MINORITY_BUSINESS;
		}
		else if(attributeName.equals("disadvantagedBusiness")) {
			return ATTRIBUTE_DISADVANTAGED_BUSINESS;
		}
		else if(attributeName.equals("defaultPaymentTerms")) {
			return ATTRIBUTE_DEFAULT_PAYMENT_TERMS;
		}
		else if(attributeName.equals("supplierNotes")) {
			return ATTRIBUTE_SUPPLIER_NOTES;
		}
		else if(attributeName.equals("formerSupplierName")) {
			return ATTRIBUTE_FORMER_SUPPLIER_NAME;
		}
		else if(attributeName.equals("haasAccountNumber")) {
			return ATTRIBUTE_HAAS_ACCOUNT_NUMBER;
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
		else if(attributeName.equals("city")) {
			return ATTRIBUTE_CITY;
		}
		else if(attributeName.equals("zip")) {
			return ATTRIBUTE_ZIP;
		}
		else if(attributeName.equals("zipPlus")) {
			return ATTRIBUTE_ZIP_PLUS;
		}
		else if(attributeName.equals("supplierMainPhone")) {
			return ATTRIBUTE_SUPPLIER_MAIN_PHONE;
		}
		else if(attributeName.equals("supplierMainFax")) {
			return ATTRIBUTE_SUPPLIER_MAIN_FAX;
		}
		else if(attributeName.equals("supplierContactFirstName")) {
			return ATTRIBUTE_SUPPLIER_CONTACT_FIRST_NAME;
		}
		else if(attributeName.equals("supplierContactLastName")) {
			return ATTRIBUTE_SUPPLIER_CONTACT_LAST_NAME;
		}
		else if(attributeName.equals("supplierContactNickname")) {
			return ATTRIBUTE_SUPPLIER_CONTACT_NICKNAME;
		}
		else if(attributeName.equals("supplierContactPhone")) {
			return ATTRIBUTE_SUPPLIER_CONTACT_PHONE;
		}
		else if(attributeName.equals("supplierContactPhoneExt")) {
			return ATTRIBUTE_SUPPLIER_CONTACT_PHONE_EXT;
		}
		else if(attributeName.equals("supplierContactFax")) {
			return ATTRIBUTE_SUPPLIER_CONTACT_FAX;
		}
		else if(attributeName.equals("supplierContactEmail")) {
			return ATTRIBUTE_SUPPLIER_CONTACT_EMAIL;
		}
		else if(attributeName.equals("eSupplierId")) {
			return ATTRIBUTE_E_SUPPLIER_ID;
		}
		else if(attributeName.equals("requester")) {
			return ATTRIBUTE_REQUESTER;
		}
		else if(attributeName.equals("qualificationLevel")) {
			return ATTRIBUTE_QUALIFICATION_LEVEL;
		}
		else if(attributeName.equals("typeOfPurchase")) {
			return ATTRIBUTE_TYPE_OF_PURCHASE;
		}
		else if(attributeName.equals("approver")) {
			return ATTRIBUTE_APPROVER;
		}
		else if(attributeName.equals("paymentTermApprover")) {
			return ATTRIBUTE_PAYMENT_TERM_APPROVER;
		}
		else if(attributeName.equals("supplierRequestStatus")) {
			return ATTRIBUTE_SUPPLIER_REQUEST_STATUS;
		}
		else if(attributeName.equals("reasonForRejection")) {
			return ATTRIBUTE_REASON_FOR_REJECTION;
		}
		else if(attributeName.equals("womanOwned")) {
			return ATTRIBUTE_WOMAN_OWNED;
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
		else if(attributeName.equals("stateTaxId")) {
			return ATTRIBUTE_STATE_TAX_ID;
		}
		else if(attributeName.equals("supplierRequestType")) {
			return ATTRIBUTE_SUPPLIER_REQUEST_TYPE;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("sapVendorCode")) {
			return ATTRIBUTE_SAP_VENDOR_CODE;
		}
		else if(attributeName.equals("remitToCountryAbbrev")) {
			return ATTRIBUTE_REMIT_TO_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("remitToStateAbbrev")) {
			return ATTRIBUTE_REMIT_TO_STATE_ABBREV;
		}
		else if(attributeName.equals("remitToAddressLine1")) {
			return ATTRIBUTE_REMIT_TO_ADDRESS_LINE_1;
		}
		else if(attributeName.equals("remitToAddressLine2")) {
			return ATTRIBUTE_REMIT_TO_ADDRESS_LINE_2;
		}
		else if(attributeName.equals("remitToAddressLine3")) {
			return ATTRIBUTE_REMIT_TO_ADDRESS_LINE_3;
		}
		else if(attributeName.equals("remitToCity")) {
			return ATTRIBUTE_REMIT_TO_CITY;
		}
		else if(attributeName.equals("remitToZip")) {
			return ATTRIBUTE_REMIT_TO_ZIP;
		}
		else if(attributeName.equals("remitToZipPlus")) {
			return ATTRIBUTE_REMIT_TO_ZIP_PLUS;
		}
		else if(attributeName.equals("iso9001Certified")) {
			return ATTRIBUTE_ISO_9001_CERTIFIED;
		}
		else if(attributeName.equals("hubWithLocalCertified")) {
			return ATTRIBUTE_HUB_WITH_LOCAL_CERTIFIED;
		}
		else if(attributeName.equals("mbeWithLocalCertified")) {
			return ATTRIBUTE_MBE_WITH_LOCAL_CERTIFIED;
		}
		else if(attributeName.equals("wbeWithLocalCertified")) {
			return ATTRIBUTE_WBE_WITH_LOCAL_CERTIFIED;
		}
		else if(attributeName.equals("sdbWithSbaCertified")) {
			return ATTRIBUTE_SDB_WITH_SBA_CERTIFIED;
		}
		else if(attributeName.equals("category8aWithSbaCertified")) {
			return ATTRIBUTE_CATEGORY_8A_WITH_SBA_CERTIFIED;
		}
		else if(attributeName.equals("hubWithSbaCertified")) {
			return ATTRIBUTE_HUB_WITH_SBA_CERTIFIED;
		}
		else if(attributeName.equals("educationalInstitution")) {
			return ATTRIBUTE_EDUCATIONAL_INSTITUTION;
		}
		else if(attributeName.equals("mbeBlackAmerican")) {
			return ATTRIBUTE_MBE_BLACK_AMERICAN;
		}
		else if(attributeName.equals("mbeHispanicAmerican")) {
			return ATTRIBUTE_MBE_HISPANIC_AMERICAN;
		}
		else if(attributeName.equals("mbeAsianPacificAmerican")) {
			return ATTRIBUTE_MBE_ASIAN_PACIFIC_AMERICAN;
		}
		else if(attributeName.equals("mbeNativeAmerican")) {
			return ATTRIBUTE_MBE_NATIVE_AMERICAN;
		}
		else if(attributeName.equals("mbeAsianIndianAmerican")) {
			return ATTRIBUTE_MBE_ASIAN_INDIAN_AMERICAN;
		}
		else if(attributeName.equals("mbeOther")) {
			return ATTRIBUTE_MBE_OTHER;
		}
		else if(attributeName.equals("vietNamVeteranOwned")) {
			return ATTRIBUTE_VIET_NAM_VETERAN_OWNED;
		}
		else if(attributeName.equals("disabledVeteranOwned")) {
			return ATTRIBUTE_DISABLED_VETERAN_OWNED;
		}
		else if(attributeName.equals("nonProfitOrganization")) {
			return ATTRIBUTE_NON_PROFIT_ORGANIZATION;
		}
		else if(attributeName.equals("debarred")) {
			return ATTRIBUTE_DEBARRED;
		}
		else if(attributeName.equals("as9100Certified")) {
			return ATTRIBUTE_AS_9100_CERTIFIED;
		}
		else if(attributeName.equals("as9120Certified")) {
			return ATTRIBUTE_AS_9120_CERTIFIED;
		}
		else if(attributeName.equals("vatRegistrationNumber")) {
			return ATTRIBUTE_VAT_REGISTRATION_NUMBER;
		}
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, SupplierAddRequestBean.class);
	}


//	you need to verify the primary key(s) before uncommenting this

	//delete
	public int delete(SupplierAddRequestBean supplierAddRequestBean)
	throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplierRequestId", SearchCriterion.EQUALS,
				"" + supplierAddRequestBean.getSupplierRequestId());

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


	public int delete(SupplierAddRequestBean supplierAddRequestBean, Connection conn)
	throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplierRequestId", "SearchCriterion.EQUALS",
				"" + supplierAddRequestBean.getSupplierRequestId());

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

		String sqlQuery = " delete from " + TABLE + "_locale " +
			getWhereClause(criteria);
		
		new SqlManager().update(conn, sqlQuery);

		sqlQuery = " delete from " + TABLE + " " +
			getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}


	//insert
	public int insert(SupplierAddRequestBean supplierAddRequestBean,String locale)
	throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(supplierAddRequestBean, connection, locale);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(SupplierAddRequestBean supplierAddRequestBean, Connection conn, String locale)
	throws BaseException {

		SqlManager sqlManager = new SqlManager();

		if (supplierAddRequestBean.getStateAbbrev() == null ||
				"None".equalsIgnoreCase(supplierAddRequestBean.getStateAbbrev()) ||
				supplierAddRequestBean.getStateAbbrev().length() ==0)
		{
			supplierAddRequestBean.setStateAbbrev("");
		}

		if (supplierAddRequestBean.getRemitToStateAbbrev() == null ||
				"None".equalsIgnoreCase(supplierAddRequestBean.getRemitToStateAbbrev()) ||
				supplierAddRequestBean.getRemitToStateAbbrev().length() ==0)
		{
			supplierAddRequestBean.setRemitToStateAbbrev("");
		}
//	log.debug("locale"+locale);
		String supplierName = supplierAddRequestBean.getSupplierName();
		if(!"en_US".equals(locale)) 
			supplierName = " ";

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_SUPPLIER_REQUEST_ID + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_SUPPLIER_PARENT + "," +
			ATTRIBUTE_FEDERAL_TAX_ID + "," +
			ATTRIBUTE_SMALL_BUSINESS + "," +
			ATTRIBUTE_MINORITY_BUSINESS + "," +
			ATTRIBUTE_DISADVANTAGED_BUSINESS + "," +
			ATTRIBUTE_DEFAULT_PAYMENT_TERMS + "," +
			ATTRIBUTE_SUPPLIER_NOTES + "," +
			ATTRIBUTE_FORMER_SUPPLIER_NAME + "," +
			ATTRIBUTE_HAAS_ACCOUNT_NUMBER + "," +
			ATTRIBUTE_COUNTRY_ABBREV + "," +
			ATTRIBUTE_STATE_ABBREV + "," +
			ATTRIBUTE_ADDRESS_LINE_1 + "," +
			ATTRIBUTE_ADDRESS_LINE_2 + "," +
			ATTRIBUTE_ADDRESS_LINE_3 + "," +
			ATTRIBUTE_CITY + "," +
			ATTRIBUTE_ZIP + "," +
			ATTRIBUTE_ZIP_PLUS + "," +
			ATTRIBUTE_SUPPLIER_MAIN_PHONE + "," +
			ATTRIBUTE_SUPPLIER_MAIN_FAX + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_FIRST_NAME + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_LAST_NAME + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_NICKNAME + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_PHONE + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_PHONE_EXT + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_FAX + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_EMAIL + "," +
			ATTRIBUTE_E_SUPPLIER_ID + "," +
			ATTRIBUTE_REQUESTER + "," +
			ATTRIBUTE_QUALIFICATION_LEVEL + "," +
			ATTRIBUTE_TYPE_OF_PURCHASE + "," +
			ATTRIBUTE_APPROVER + "," +
			ATTRIBUTE_PAYMENT_TERM_APPROVER + "," +
			ATTRIBUTE_SUPPLIER_REQUEST_STATUS + "," +
			ATTRIBUTE_REASON_FOR_REJECTION + "," +
			ATTRIBUTE_WOMAN_OWNED + "," +
			ATTRIBUTE_TRANSACTION_DATE + "," +
			ATTRIBUTE_DATE_APPROVED + "," +
			ATTRIBUTE_DATE_PAYMENT_TERMS_APPROVED + "," +
			ATTRIBUTE_STATE_TAX_ID + "," +
			ATTRIBUTE_SUPPLIER_REQUEST_TYPE + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_SAP_VENDOR_CODE + "," +
			ATTRIBUTE_REMIT_TO_COUNTRY_ABBREV + "," +
			ATTRIBUTE_REMIT_TO_STATE_ABBREV + "," +
			ATTRIBUTE_REMIT_TO_ADDRESS_LINE_1 + "," +
			ATTRIBUTE_REMIT_TO_ADDRESS_LINE_2 + "," +
			ATTRIBUTE_REMIT_TO_ADDRESS_LINE_3 + "," +
			ATTRIBUTE_REMIT_TO_CITY + "," +
			ATTRIBUTE_REMIT_TO_ZIP + "," +
			ATTRIBUTE_REMIT_TO_ZIP_PLUS + "," +
			ATTRIBUTE_ISO_9001_CERTIFIED + "," +
			ATTRIBUTE_HUB_WITH_LOCAL_CERTIFIED + "," +
			ATTRIBUTE_MBE_WITH_LOCAL_CERTIFIED + "," +
			ATTRIBUTE_WBE_WITH_LOCAL_CERTIFIED + "," +
			ATTRIBUTE_SDB_WITH_SBA_CERTIFIED + "," +
			ATTRIBUTE_CATEGORY_8A_WITH_SBA_CERTIFIED + "," +
			ATTRIBUTE_HUB_WITH_SBA_CERTIFIED + "," +
			ATTRIBUTE_EDUCATIONAL_INSTITUTION + "," +
			ATTRIBUTE_MBE_BLACK_AMERICAN + "," +
			ATTRIBUTE_MBE_HISPANIC_AMERICAN + "," +
			ATTRIBUTE_MBE_ASIAN_PACIFIC_AMERICAN + "," +
			ATTRIBUTE_MBE_NATIVE_AMERICAN + "," +
			ATTRIBUTE_MBE_ASIAN_INDIAN_AMERICAN + "," +
			ATTRIBUTE_MBE_OTHER + "," +
			ATTRIBUTE_VIET_NAM_VETERAN_OWNED + "," +
			ATTRIBUTE_DISABLED_VETERAN_OWNED + "," +
			ATTRIBUTE_NON_PROFIT_ORGANIZATION + "," +
			ATTRIBUTE_DEBARRED + "," +
			ATTRIBUTE_AS_9100_CERTIFIED + "," +
			ATTRIBUTE_AS_9120_CERTIFIED + "," +
			ATTRIBUTE_VAT_REGISTRATION_NUMBER + "," +
			ATTRIBUTE_SSN_FLAG + "," +
			ATTRIBUTE_ORIGINAL_PAYMENT_TERMS + "," +
			ATTRIBUTE_OPS_ENTITY_ID + ")" +
			" values (" +
			supplierAddRequestBean.getSupplierRequestId() + "," +
			SqlHandler.delimitString(supplierName) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getSupplierParent()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getFederalTaxId()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getSmallBusiness()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getMinorityBusiness()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getDisadvantagedBusiness()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getDefaultPaymentTerms()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getSupplierNotes()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getFormerSupplierName()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getHaasAccountNumber()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getCountryAbbrev()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getStateAbbrev()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getAddressLine1()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getAddressLine2()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getAddressLine3()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getCity()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getZip()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getZipPlus()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getSupplierMainPhone()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getSupplierMainFax()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getSupplierContactFirstName()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getSupplierContactLastName()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getSupplierContactNickname()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getSupplierContactPhone()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getSupplierContactPhoneExt()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getSupplierContactFax()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getSupplierContactEmail()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getESupplierId()) + "," +
			supplierAddRequestBean.getRequester() + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getQualificationLevel()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getTypeOfPurchase()) + "," +
			supplierAddRequestBean.getApprover() + "," +
			supplierAddRequestBean.getPaymentTermApprover() + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getSupplierRequestStatus()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getReasonForRejection()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getWomanOwned()) + "," +
//			DateHandler.getOracleToDateFunction(supplierAddRequestBean.getTransactionDate()) + "," +
			"sysdate" + "," +
			DateHandler.getOracleToDateFunction(supplierAddRequestBean.getDateApproved()) + "," +
			DateHandler.getOracleToDateFunction(supplierAddRequestBean.getDatePaymentTermsApproved()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getStateTaxId()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getSupplierRequestType()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getSupplier()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getSapVendorCode()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getRemitToCountryAbbrev()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getRemitToStateAbbrev()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getRemitToAddressLine1()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getRemitToAddressLine2()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getRemitToAddressLine3()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getRemitToCity()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getRemitToZip()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getRemitToZipPlus()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getIso9001Certified()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getHubWithLocalCertified()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getMbeWithLocalCertified()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getWbeWithLocalCertified()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getSdbWithSbaCertified()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getCategory8aWithSbaCertified()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getHubWithSbaCertified()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getEducationalInstitution()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getMbeBlackAmerican()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getMbeHispanicAmerican()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getMbeAsianPacificAmerican()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getMbeNativeAmerican()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getMbeAsianIndianAmerican()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getMbeOther()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getVietNamVeteranOwned()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getDisabledVeteranOwned()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getNonProfitOrganization()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getDebarred()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getAs9100Certified()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getAs9120Certified()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getVatRegistrationNumber()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getSsnFlag()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getOriginalPaymentTerms()) + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getOpsEntityId()) + ")";
		
		int result = sqlManager.update(conn, query);
		
		if(!"en_US".equals(locale)) {
			query = "insert into " + TABLE + "_locale (" + 
			ATTRIBUTE_SUPPLIER_REQUEST_ID + "," +
			ATTRIBUTE_SUPPLIER_NAME + ", locale_code)"+ 
			" values (" +
			supplierAddRequestBean.getSupplierRequestId() + "," +
			SqlHandler.delimitString(supplierAddRequestBean.getSupplierName()) + ",'" + locale + "')";
	
			sqlManager.update(conn, query);
		}
	
		return result;
	}


	//update
	public int update(SupplierAddRequestBean supplierAddRequestBean,boolean updatePaymentApproveDate, String locale)
	throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(supplierAddRequestBean, connection,updatePaymentApproveDate,locale);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}
	//update payment terms

	public int updatePaymentTerms(SupplierAddRequestBean supplierAddRequestBean)
	throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = updatePaymentTerms(supplierAddRequestBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int updatePaymentTerms(SupplierAddRequestBean supplierAddRequestBean, Connection conn)
	throws BaseException {

		String query  = "update " + TABLE + " set " +
		ATTRIBUTE_DEFAULT_PAYMENT_TERMS + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getDefaultPaymentTerms()) + "," +
		ATTRIBUTE_SUPPLIER_NOTES + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSupplierNotes()) + "," +
//		ATTRIBUTE_PAYMENT_TERM_APPROVER + "=" + 
//		StringHandler.nullIfZero(supplierAddRequestBean.getPaymentTermApprover()) + "," +
		ATTRIBUTE_SUPPLIER_REQUEST_STATUS + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSupplierRequestStatus()) +
//		ATTRIBUTE_DATE_PAYMENT_TERMS_APPROVED + "=" + 
//		DateHandler.getOracleToDateFunction(supplierAddRequestBean.getDatePaymentTermsApproved()) +
		" where " + ATTRIBUTE_SUPPLIER_REQUEST_ID + "=" +
		supplierAddRequestBean.getSupplierRequestId();

		return new SqlManager().update(conn, query);
	}

	//approve payment terms direct

	public int approvePaymentTermsDirect(SupplierAddRequestBean supplierAddRequestBean,boolean updateSupplierFlag)
	throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = approvePaymentTermsDirect(supplierAddRequestBean, connection,updateSupplierFlag);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int approvePaymentTermsDirect(SupplierAddRequestBean supplierAddRequestBean, Connection conn, boolean updateSupplierFlag)
	throws BaseException {

		String query  = "update " + TABLE + " set " +
		ATTRIBUTE_DEFAULT_PAYMENT_TERMS + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getDefaultPaymentTerms()) + "," +
		ATTRIBUTE_SUPPLIER_NOTES + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSupplierNotes()) + "," +
		ATTRIBUTE_PAYMENT_TERM_APPROVER + "=" + 
		StringHandler.nullIfZero(supplierAddRequestBean.getPaymentTermApprover()) + "," +
		ATTRIBUTE_SUPPLIER_REQUEST_STATUS + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSupplierRequestStatus()) + "," +
		ATTRIBUTE_DATE_PAYMENT_TERMS_APPROVED + "=" + 
		DateHandler.getOracleToDateFunction(supplierAddRequestBean.getDatePaymentTermsApproved()) +
		" where " + ATTRIBUTE_SUPPLIER_REQUEST_ID + "=" +
		supplierAddRequestBean.getSupplierRequestId();

		int r1 = new SqlManager().update(conn, query);
		if( updateSupplierFlag ) {
//			query = "update supplier set default_payment_terms = " + 
//			SqlHandler.delimitString(supplierAddRequestBean.getDefaultPaymentTerms()) + 
//			" where supplier = " + SqlHandler.delimitString(supplierAddRequestBean.getSupplier()) ;
//			int r2 = new SqlManager().update(conn, query);
		}
		return r1;
	}


	public int update(SupplierAddRequestBean supplierAddRequestBean, Connection conn,boolean updatePaymentApproveDate, String locale)
	throws BaseException {

        SqlManager sqlManager = new SqlManager();
        if (supplierAddRequestBean.getStateAbbrev() == null ||
				"None".equalsIgnoreCase(supplierAddRequestBean.getStateAbbrev()) ||
				supplierAddRequestBean.getStateAbbrev().length() ==0)
		{
			supplierAddRequestBean.setStateAbbrev("");
		}

		if (supplierAddRequestBean.getRemitToStateAbbrev() == null ||
				"None".equalsIgnoreCase(supplierAddRequestBean.getRemitToStateAbbrev()) ||
				supplierAddRequestBean.getRemitToStateAbbrev().length() ==0)
		{
			supplierAddRequestBean.setRemitToStateAbbrev("");
		}
		
		if(!"en_US".equals(locale)) {
            //first delete current data
            String query = "delete from " + TABLE + "_locale where locale_code = '" + locale + "' and " + ATTRIBUTE_SUPPLIER_REQUEST_ID + "=" +supplierAddRequestBean.getSupplierRequestId();
            sqlManager.update(conn, query);
            //insert new data
            query = "insert into " + TABLE + "_locale (" +
			ATTRIBUTE_SUPPLIER_REQUEST_ID + "," +
			ATTRIBUTE_SUPPLIER_NAME + ", locale_code)"+
			" values (" +
			supplierAddRequestBean.getSupplierRequestId() + "," +SqlHandler.delimitString(supplierAddRequestBean.getSupplierName()) + ",'" + locale + "')";
            sqlManager.update(conn, query);

			supplierAddRequestBean.setSupplierName(" ");
		}	
		
		String s1 = ""; // for date payment terms approved
		String s2 = ""; // for transaction date
		if( updatePaymentApproveDate ){
			s1 = ATTRIBUTE_DATE_PAYMENT_TERMS_APPROVED + "=" + 
			DateHandler.getOracleToDateFunction(supplierAddRequestBean.getDatePaymentTermsApproved()) + ",";
			//	ATTRIBUTE_TRANSACTION_DATE + "=" + 
			//	DateHandler.getOracleToDateFunction(supplierAddRequestBean.getTransactionDate()) + "," +

		}
		String query  = "update " + TABLE + " set " +
		ATTRIBUTE_SUPPLIER_REQUEST_ID + "=" + 
		StringHandler.nullIfZero(supplierAddRequestBean.getSupplierRequestId()) + "," +
		ATTRIBUTE_SUPPLIER_PARENT + "=" +
		SqlHandler.delimitString(supplierAddRequestBean.getSupplierParent()) + "," +
		ATTRIBUTE_FEDERAL_TAX_ID + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getFederalTaxId()) + "," +
		ATTRIBUTE_SMALL_BUSINESS + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSmallBusiness()) + "," +
		ATTRIBUTE_MINORITY_BUSINESS + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getMinorityBusiness()) + "," +
		ATTRIBUTE_DISADVANTAGED_BUSINESS + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getDisadvantagedBusiness()) + "," +
		ATTRIBUTE_DEFAULT_PAYMENT_TERMS + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getDefaultPaymentTerms()) + "," +
		ATTRIBUTE_SUPPLIER_NOTES + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSupplierNotes()) + "," +
		ATTRIBUTE_FORMER_SUPPLIER_NAME + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getFormerSupplierName()) + "," +
		ATTRIBUTE_HAAS_ACCOUNT_NUMBER + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getHaasAccountNumber()) + "," +
		ATTRIBUTE_COUNTRY_ABBREV + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getCountryAbbrev()) + "," +
		ATTRIBUTE_STATE_ABBREV + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getStateAbbrev()) + "," +
		ATTRIBUTE_ADDRESS_LINE_1 + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getAddressLine1()) + "," +
		ATTRIBUTE_ADDRESS_LINE_2 + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getAddressLine2()) + "," +
		ATTRIBUTE_ADDRESS_LINE_3 + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getAddressLine3()) + "," +
		ATTRIBUTE_CITY + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getCity()) + "," +
		ATTRIBUTE_ZIP + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getZip()) + "," +
		ATTRIBUTE_ZIP_PLUS + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getZipPlus()) + "," +
		ATTRIBUTE_SUPPLIER_MAIN_PHONE + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSupplierMainPhone()) + "," +
		ATTRIBUTE_SUPPLIER_MAIN_FAX + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSupplierMainFax()) + "," +
		ATTRIBUTE_SUPPLIER_CONTACT_FIRST_NAME + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSupplierContactFirstName()) + "," +
		ATTRIBUTE_SUPPLIER_CONTACT_LAST_NAME + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSupplierContactLastName()) + "," +
		ATTRIBUTE_SUPPLIER_CONTACT_NICKNAME + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSupplierContactNickname()) + "," +
		ATTRIBUTE_SUPPLIER_CONTACT_PHONE + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSupplierContactPhone()) + "," +
		ATTRIBUTE_SUPPLIER_CONTACT_PHONE_EXT + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSupplierContactPhoneExt()) + "," +
		ATTRIBUTE_SUPPLIER_CONTACT_FAX + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSupplierContactFax()) + "," +
		ATTRIBUTE_SUPPLIER_CONTACT_EMAIL + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSupplierContactEmail()) + "," +
		ATTRIBUTE_E_SUPPLIER_ID + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getESupplierId()) + "," +
		ATTRIBUTE_REQUESTER + "=" + 
		StringHandler.nullIfZero(supplierAddRequestBean.getRequester()) + "," +
		ATTRIBUTE_QUALIFICATION_LEVEL + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getQualificationLevel()) + "," +
		ATTRIBUTE_TYPE_OF_PURCHASE + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getTypeOfPurchase()) + "," +
		ATTRIBUTE_APPROVER + "=" + 
		StringHandler.nullIfZero(supplierAddRequestBean.getApprover()) + "," +
		ATTRIBUTE_PAYMENT_TERM_APPROVER + "=" + 
		StringHandler.nullIfZero(supplierAddRequestBean.getPaymentTermApprover()) + "," +
		ATTRIBUTE_SUPPLIER_REQUEST_STATUS + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSupplierRequestStatus()) + "," +
		ATTRIBUTE_REASON_FOR_REJECTION + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getReasonForRejection()) + "," +
		ATTRIBUTE_WOMAN_OWNED + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getWomanOwned()) + "," +
		s2 +
		ATTRIBUTE_DATE_APPROVED + "=" + 
		DateHandler.getOracleToDateFunction(supplierAddRequestBean.getDateApproved()) + "," +
		s1 +
		ATTRIBUTE_STATE_TAX_ID + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getStateTaxId()) + "," +
		ATTRIBUTE_SUPPLIER_REQUEST_TYPE + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSupplierRequestType()) + "," +
		ATTRIBUTE_SUPPLIER + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSupplier()) + "," +
		ATTRIBUTE_SAP_VENDOR_CODE + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSapVendorCode()) + "," +
		ATTRIBUTE_REMIT_TO_COUNTRY_ABBREV + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getRemitToCountryAbbrev()) + "," +
		ATTRIBUTE_REMIT_TO_STATE_ABBREV + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getRemitToStateAbbrev()) + "," +
		ATTRIBUTE_REMIT_TO_ADDRESS_LINE_1 + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getRemitToAddressLine1()) + "," +
		ATTRIBUTE_REMIT_TO_ADDRESS_LINE_2 + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getRemitToAddressLine2()) + "," +
		ATTRIBUTE_REMIT_TO_ADDRESS_LINE_3 + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getRemitToAddressLine3()) + "," +
		ATTRIBUTE_REMIT_TO_CITY + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getRemitToCity()) + "," +
		ATTRIBUTE_REMIT_TO_ZIP + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getRemitToZip()) + "," +
		ATTRIBUTE_REMIT_TO_ZIP_PLUS + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getRemitToZipPlus()) + "," +
		ATTRIBUTE_ISO_9001_CERTIFIED + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getIso9001Certified()) + "," +
		ATTRIBUTE_HUB_WITH_LOCAL_CERTIFIED + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getHubWithLocalCertified()) + "," +
		ATTRIBUTE_MBE_WITH_LOCAL_CERTIFIED + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getMbeWithLocalCertified()) + "," +
		ATTRIBUTE_WBE_WITH_LOCAL_CERTIFIED + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getWbeWithLocalCertified()) + "," +
		ATTRIBUTE_SDB_WITH_SBA_CERTIFIED + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSdbWithSbaCertified()) + "," +
		ATTRIBUTE_CATEGORY_8A_WITH_SBA_CERTIFIED + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getCategory8aWithSbaCertified()) + "," +
		ATTRIBUTE_HUB_WITH_SBA_CERTIFIED + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getHubWithSbaCertified()) + "," +
		ATTRIBUTE_EDUCATIONAL_INSTITUTION + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getEducationalInstitution()) + "," +
		ATTRIBUTE_MBE_BLACK_AMERICAN + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getMbeBlackAmerican()) + "," +
		ATTRIBUTE_MBE_HISPANIC_AMERICAN + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getMbeHispanicAmerican()) + "," +
		ATTRIBUTE_MBE_ASIAN_PACIFIC_AMERICAN + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getMbeAsianPacificAmerican()) + "," +
		ATTRIBUTE_MBE_NATIVE_AMERICAN + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getMbeNativeAmerican()) + "," +
		ATTRIBUTE_MBE_ASIAN_INDIAN_AMERICAN + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getMbeAsianIndianAmerican()) + "," +
		ATTRIBUTE_MBE_OTHER + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getMbeOther()) + "," +
		ATTRIBUTE_VIET_NAM_VETERAN_OWNED + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getVietNamVeteranOwned()) + "," +
		ATTRIBUTE_DISABLED_VETERAN_OWNED + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getDisabledVeteranOwned()) + "," +
		ATTRIBUTE_NON_PROFIT_ORGANIZATION + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getNonProfitOrganization()) + "," +
		ATTRIBUTE_DEBARRED + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getDebarred()) + "," +
		ATTRIBUTE_AS_9100_CERTIFIED + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getAs9100Certified()) + "," +
		ATTRIBUTE_AS_9120_CERTIFIED + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getAs9120Certified()) + "," +
		ATTRIBUTE_VAT_REGISTRATION_NUMBER + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getVatRegistrationNumber()) + "," +
		ATTRIBUTE_SSN_FLAG + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getSsnFlag()) + "," +
		ATTRIBUTE_ORIGINAL_PAYMENT_TERMS + "=" + 
		SqlHandler.delimitString(supplierAddRequestBean.getOriginalPaymentTerms()) + "," +
		ATTRIBUTE_OPS_ENTITY_ID + "=" +SqlHandler.delimitString(supplierAddRequestBean.getOpsEntityId());
        //only update supplier name if it is not null
        if (!StringHandler.isBlankString(supplierAddRequestBean.getSupplierName())) {
            query += ","+ATTRIBUTE_SUPPLIER_NAME + "=" +SqlHandler.delimitString(supplierAddRequestBean.getSupplierName());
        }
        query += " where " + ATTRIBUTE_SUPPLIER_REQUEST_ID + "="+supplierAddRequestBean.getSupplierRequestId();
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

		Collection supplierAddRequestBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			SupplierAddRequestBean supplierAddRequestBean = new SupplierAddRequestBean();
			load(dataSetRow, supplierAddRequestBean);
			supplierAddRequestBeanColl.add(supplierAddRequestBean);
		}

		return supplierAddRequestBeanColl;
	}
}