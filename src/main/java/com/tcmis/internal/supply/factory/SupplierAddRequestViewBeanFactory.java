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
import com.tcmis.internal.supply.beans.SupplierAddRequestViewBean;


/******************************************************************************
 * CLASSNAME: SupplierAddRequestViewBeanFactory <br>
 * @version: 1.0, Jul 13, 2009 <br>
 *****************************************************************************/


public class SupplierAddRequestViewBeanFactory extends BaseBeanFactory {

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
	public String ATTRIBUTE_SUPPLIER_REQUEST_STATUS = "SUPPLIER_REQUEST_STATUS";
	public String ATTRIBUTE_PAYMENT_TERM_APPROVER = "PAYMENT_TERM_APPROVER";
	public String ATTRIBUTE_APPROVER_NAME = "APPROVER_NAME";
	public String ATTRIBUTE_PAYMENT_TERM_APPROVER_NAME = "PAYMENT_TERM_APPROVER_NAME";
	public String ATTRIBUTE_REQUESTER_NAME = "REQUESTER_NAME";
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
	public String ATTRIBUTE_ISO_9001_CERTIFIED = "ISO_9001_CERTIFIED";
	public String ATTRIBUTE_AS_9100_CERTIFIED = "AS_9100_CERTIFIED";
	public String ATTRIBUTE_AS_9120_CERTIFIED = "AS_9120_CERTIFIED";
	public String ATTRIBUTE_VAT_REGISTRATION_NUMBER = "VAT_REGISTRATION_NUMBER";
	public String ATTRIBUTE_REQUESTER_PHONE = "REQUESTER_PHONE";
	public String ATTRIBUTE_REQUESTER_EMAIL = "REQUESTER_EMAIL";

	//table name
	public String TABLE = "SUPPLIER_ADD_REQUEST_VIEW";


	//constructor
	public SupplierAddRequestViewBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("supplierRequestStatus")) {
			return ATTRIBUTE_SUPPLIER_REQUEST_STATUS;
		}
		else if(attributeName.equals("paymentTermApprover")) {
			return ATTRIBUTE_PAYMENT_TERM_APPROVER;
		}
		else if(attributeName.equals("approverName")) {
			return ATTRIBUTE_APPROVER_NAME;
		}
		else if(attributeName.equals("paymentTermApproverName")) {
			return ATTRIBUTE_PAYMENT_TERM_APPROVER_NAME;
		}
		else if(attributeName.equals("requesterName")) {
			return ATTRIBUTE_REQUESTER_NAME;
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
		else if(attributeName.equals("iso9001Certified")) {
			return ATTRIBUTE_ISO_9001_CERTIFIED;
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
		else if(attributeName.equals("requesterPhone")) {
			return ATTRIBUTE_REQUESTER_PHONE;
		}
		else if(attributeName.equals("requesterEmail")) {
			return ATTRIBUTE_REQUESTER_EMAIL;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, SupplierAddRequestViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(SupplierAddRequestViewBean supplierAddRequestViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplierRequestId", "SearchCriterion.EQUALS",
			"" + supplierAddRequestViewBean.getSupplierRequestId());

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


	public int delete(SupplierAddRequestViewBean supplierAddRequestViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplierRequestId", "SearchCriterion.EQUALS",
			"" + supplierAddRequestViewBean.getSupplierRequestId());

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
	public int insert(SupplierAddRequestViewBean supplierAddRequestViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(supplierAddRequestViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(SupplierAddRequestViewBean supplierAddRequestViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

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
			ATTRIBUTE_SUPPLIER_REQUEST_STATUS + "," +
			ATTRIBUTE_PAYMENT_TERM_APPROVER + "," +
			ATTRIBUTE_APPROVER_NAME + "," +
			ATTRIBUTE_PAYMENT_TERM_APPROVER_NAME + "," +
			ATTRIBUTE_REQUESTER_NAME + "," +
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
			ATTRIBUTE_ISO_9001_CERTIFIED + "," +
			ATTRIBUTE_AS_9100_CERTIFIED + "," +
			ATTRIBUTE_AS_9120_CERTIFIED + "," +
			ATTRIBUTE_VAT_REGISTRATION_NUMBER + "," +
			ATTRIBUTE_REQUESTER_PHONE + "," +
			ATTRIBUTE_REQUESTER_EMAIL + ")" +
			" values (" +
			supplierAddRequestViewBean.getSupplierRequestId() + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierName()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierParent()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getFederalTaxId()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getSmallBusiness()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getMinorityBusiness()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getDisadvantagedBusiness()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getDefaultPaymentTerms()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierNotes()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getFormerSupplierName()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getHaasAccountNumber()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getCountryAbbrev()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getStateAbbrev()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getAddressLine1()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getAddressLine2()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getAddressLine3()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getCity()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getZip()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getZipPlus()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierMainPhone()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierMainFax()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierContactFirstName()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierContactLastName()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierContactNickname()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierContactPhone()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierContactPhoneExt()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierContactFax()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierContactEmail()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getESupplierId()) + "," +
			supplierAddRequestViewBean.getRequester() + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getQualificationLevel()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getTypeOfPurchase()) + "," +
			supplierAddRequestViewBean.getApprover() + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierRequestStatus()) + "," +
			supplierAddRequestViewBean.getPaymentTermApprover() + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getApproverName()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getPaymentTermApproverName()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getRequesterName()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getReasonForRejection()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getWomanOwned()) + "," +
			DateHandler.getOracleToDateFunction(supplierAddRequestViewBean.getTransactionDate()) + "," +
			DateHandler.getOracleToDateFunction(supplierAddRequestViewBean.getDateApproved()) + "," +
			DateHandler.getOracleToDateFunction(supplierAddRequestViewBean.getDatePaymentTermsApproved()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getStateTaxId()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierRequestType()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getSupplier()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getSapVendorCode()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getRemitToCountryAbbrev()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getRemitToStateAbbrev()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getRemitToAddressLine1()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getRemitToAddressLine2()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getRemitToAddressLine3()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getRemitToCity()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getRemitToZip()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getRemitToZipPlus()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getHubWithLocalCertified()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getMbeWithLocalCertified()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getWbeWithLocalCertified()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getSdbWithSbaCertified()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getCategory8aWithSbaCertified()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getHubWithSbaCertified()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getEducationalInstitution()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getMbeBlackAmerican()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getMbeHispanicAmerican()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getMbeAsianPacificAmerican()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getMbeNativeAmerican()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getMbeAsianIndianAmerican()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getMbeOther()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getVietNamVeteranOwned()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getDisabledVeteranOwned()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getNonProfitOrganization()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getDebarred()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getIso9001Certified()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getAs9100Certified()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getAs9120Certified()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getVatRegistrationNumber()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getRequesterPhone()) + "," +
			SqlHandler.delimitString(supplierAddRequestViewBean.getRequesterEmail()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(SupplierAddRequestViewBean supplierAddRequestViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(supplierAddRequestViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(SupplierAddRequestViewBean supplierAddRequestViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_SUPPLIER_REQUEST_ID + "=" + 
				StringHandler.nullIfZero(supplierAddRequestViewBean.getSupplierRequestId()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierName()) + "," +
			ATTRIBUTE_SUPPLIER_PARENT + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierParent()) + "," +
			ATTRIBUTE_FEDERAL_TAX_ID + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getFederalTaxId()) + "," +
			ATTRIBUTE_SMALL_BUSINESS + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getSmallBusiness()) + "," +
			ATTRIBUTE_MINORITY_BUSINESS + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getMinorityBusiness()) + "," +
			ATTRIBUTE_DISADVANTAGED_BUSINESS + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getDisadvantagedBusiness()) + "," +
			ATTRIBUTE_DEFAULT_PAYMENT_TERMS + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getDefaultPaymentTerms()) + "," +
			ATTRIBUTE_SUPPLIER_NOTES + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierNotes()) + "," +
			ATTRIBUTE_FORMER_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getFormerSupplierName()) + "," +
			ATTRIBUTE_HAAS_ACCOUNT_NUMBER + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getHaasAccountNumber()) + "," +
			ATTRIBUTE_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getCountryAbbrev()) + "," +
			ATTRIBUTE_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getStateAbbrev()) + "," +
			ATTRIBUTE_ADDRESS_LINE_1 + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getAddressLine1()) + "," +
			ATTRIBUTE_ADDRESS_LINE_2 + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getAddressLine2()) + "," +
			ATTRIBUTE_ADDRESS_LINE_3 + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getAddressLine3()) + "," +
			ATTRIBUTE_CITY + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getCity()) + "," +
			ATTRIBUTE_ZIP + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getZip()) + "," +
			ATTRIBUTE_ZIP_PLUS + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getZipPlus()) + "," +
			ATTRIBUTE_SUPPLIER_MAIN_PHONE + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierMainPhone()) + "," +
			ATTRIBUTE_SUPPLIER_MAIN_FAX + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierMainFax()) + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_FIRST_NAME + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierContactFirstName()) + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_LAST_NAME + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierContactLastName()) + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_NICKNAME + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierContactNickname()) + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_PHONE + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierContactPhone()) + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_PHONE_EXT + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierContactPhoneExt()) + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_FAX + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierContactFax()) + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_EMAIL + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierContactEmail()) + "," +
			ATTRIBUTE_E_SUPPLIER_ID + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getESupplierId()) + "," +
			ATTRIBUTE_REQUESTER + "=" + 
				StringHandler.nullIfZero(supplierAddRequestViewBean.getRequester()) + "," +
			ATTRIBUTE_QUALIFICATION_LEVEL + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getQualificationLevel()) + "," +
			ATTRIBUTE_TYPE_OF_PURCHASE + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getTypeOfPurchase()) + "," +
			ATTRIBUTE_APPROVER + "=" + 
				StringHandler.nullIfZero(supplierAddRequestViewBean.getApprover()) + "," +
			ATTRIBUTE_SUPPLIER_REQUEST_STATUS + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierRequestStatus()) + "," +
			ATTRIBUTE_PAYMENT_TERM_APPROVER + "=" + 
				StringHandler.nullIfZero(supplierAddRequestViewBean.getPaymentTermApprover()) + "," +
			ATTRIBUTE_APPROVER_NAME + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getApproverName()) + "," +
			ATTRIBUTE_PAYMENT_TERM_APPROVER_NAME + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getPaymentTermApproverName()) + "," +
			ATTRIBUTE_REQUESTER_NAME + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getRequesterName()) + "," +
			ATTRIBUTE_REASON_FOR_REJECTION + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getReasonForRejection()) + "," +
			ATTRIBUTE_WOMAN_OWNED + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getWomanOwned()) + "," +
			ATTRIBUTE_TRANSACTION_DATE + "=" + 
				DateHandler.getOracleToDateFunction(supplierAddRequestViewBean.getTransactionDate()) + "," +
			ATTRIBUTE_DATE_APPROVED + "=" + 
				DateHandler.getOracleToDateFunction(supplierAddRequestViewBean.getDateApproved()) + "," +
			ATTRIBUTE_DATE_PAYMENT_TERMS_APPROVED + "=" + 
				DateHandler.getOracleToDateFunction(supplierAddRequestViewBean.getDatePaymentTermsApproved()) + "," +
			ATTRIBUTE_STATE_TAX_ID + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getStateTaxId()) + "," +
			ATTRIBUTE_SUPPLIER_REQUEST_TYPE + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getSupplierRequestType()) + "," +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getSupplier()) + "," +
			ATTRIBUTE_SAP_VENDOR_CODE + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getSapVendorCode()) + "," +
			ATTRIBUTE_REMIT_TO_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getRemitToCountryAbbrev()) + "," +
			ATTRIBUTE_REMIT_TO_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getRemitToStateAbbrev()) + "," +
			ATTRIBUTE_REMIT_TO_ADDRESS_LINE_1 + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getRemitToAddressLine1()) + "," +
			ATTRIBUTE_REMIT_TO_ADDRESS_LINE_2 + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getRemitToAddressLine2()) + "," +
			ATTRIBUTE_REMIT_TO_ADDRESS_LINE_3 + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getRemitToAddressLine3()) + "," +
			ATTRIBUTE_REMIT_TO_CITY + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getRemitToCity()) + "," +
			ATTRIBUTE_REMIT_TO_ZIP + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getRemitToZip()) + "," +
			ATTRIBUTE_REMIT_TO_ZIP_PLUS + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getRemitToZipPlus()) + "," +
			ATTRIBUTE_HUB_WITH_LOCAL_CERTIFIED + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getHubWithLocalCertified()) + "," +
			ATTRIBUTE_MBE_WITH_LOCAL_CERTIFIED + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getMbeWithLocalCertified()) + "," +
			ATTRIBUTE_WBE_WITH_LOCAL_CERTIFIED + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getWbeWithLocalCertified()) + "," +
			ATTRIBUTE_SDB_WITH_SBA_CERTIFIED + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getSdbWithSbaCertified()) + "," +
			ATTRIBUTE_CATEGORY_8A_WITH_SBA_CERTIFIED + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getCategory8aWithSbaCertified()) + "," +
			ATTRIBUTE_HUB_WITH_SBA_CERTIFIED + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getHubWithSbaCertified()) + "," +
			ATTRIBUTE_EDUCATIONAL_INSTITUTION + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getEducationalInstitution()) + "," +
			ATTRIBUTE_MBE_BLACK_AMERICAN + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getMbeBlackAmerican()) + "," +
			ATTRIBUTE_MBE_HISPANIC_AMERICAN + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getMbeHispanicAmerican()) + "," +
			ATTRIBUTE_MBE_ASIAN_PACIFIC_AMERICAN + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getMbeAsianPacificAmerican()) + "," +
			ATTRIBUTE_MBE_NATIVE_AMERICAN + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getMbeNativeAmerican()) + "," +
			ATTRIBUTE_MBE_ASIAN_INDIAN_AMERICAN + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getMbeAsianIndianAmerican()) + "," +
			ATTRIBUTE_MBE_OTHER + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getMbeOther()) + "," +
			ATTRIBUTE_VIET_NAM_VETERAN_OWNED + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getVietNamVeteranOwned()) + "," +
			ATTRIBUTE_DISABLED_VETERAN_OWNED + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getDisabledVeteranOwned()) + "," +
			ATTRIBUTE_NON_PROFIT_ORGANIZATION + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getNonProfitOrganization()) + "," +
			ATTRIBUTE_DEBARRED + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getDebarred()) + "," +
			ATTRIBUTE_ISO_9001_CERTIFIED + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getIso9001Certified()) + "," +
			ATTRIBUTE_AS_9100_CERTIFIED + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getAs9100Certified()) + "," +
			ATTRIBUTE_AS_9120_CERTIFIED + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getAs9120Certified()) + "," +
			ATTRIBUTE_VAT_REGISTRATION_NUMBER + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getVatRegistrationNumber()) + "," +
			ATTRIBUTE_REQUESTER_PHONE + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getRequesterPhone()) + "," +
			ATTRIBUTE_REQUESTER_EMAIL + "=" + 
				SqlHandler.delimitString(supplierAddRequestViewBean.getRequesterEmail()) + " " +
			"where " + ATTRIBUTE_SUPPLIER_REQUEST_ID + "=" +
				supplierAddRequestViewBean.getSupplierRequestId();

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

		Collection supplierAddRequestViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			SupplierAddRequestViewBean supplierAddRequestViewBean = new SupplierAddRequestViewBean();
			load(dataSetRow, supplierAddRequestViewBean);
			supplierAddRequestViewBeanColl.add(supplierAddRequestViewBean);
		}

		return supplierAddRequestViewBeanColl;
	}
}