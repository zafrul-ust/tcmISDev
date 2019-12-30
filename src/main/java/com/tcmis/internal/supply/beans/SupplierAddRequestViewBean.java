package com.tcmis.internal.supply.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SupplierAddRequestViewBean <br>
 * @version: 1.0, Jul 13, 2009 <br>
 *****************************************************************************/


public class SupplierAddRequestViewBean extends BaseDataBean implements SQLData {

	private BigDecimal supplierRequestId;
	private String supplierName;
	private String supplierParent;
	private String federalTaxId;
	private String smallBusiness;
	private String minorityBusiness;
	private String disadvantagedBusiness;
	private String defaultPaymentTerms;
	private String supplierNotes;
	private String formerSupplierName;
	private String haasAccountNumber;
	private String countryAbbrev;
	private String stateAbbrev;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String city;
	private String zip;
	private String zipPlus;
	private String supplierMainPhone;
	private String supplierMainFax;
	private String supplierContactFirstName;
	private String supplierContactLastName;
	private String supplierContactNickname;
	private String supplierContactPhone;
	private String supplierContactPhoneExt;
	private String supplierContactFax;
	private String supplierContactEmail;
	private String eSupplierId;
	private BigDecimal requester;
	private String qualificationLevel;
	private String typeOfPurchase;
	private BigDecimal approver;
	private String supplierRequestStatus;
	private BigDecimal paymentTermApprover;
	private String approverName;
	private String paymentTermApproverName;
	private String requesterName;
	private String reasonForRejection;
	private String womanOwned;
	private Date transactionDate;
	private Date dateApproved;
	private Date datePaymentTermsApproved;
	private String stateTaxId;
	private String supplierRequestType;
	private String supplier;
	private String sapVendorCode;
	private String remitToCountryAbbrev;
	private String remitToStateAbbrev;
	private String remitToAddressLine1;
	private String remitToAddressLine2;
	private String remitToAddressLine3;
	private String remitToCity;
	private String remitToZip;
	private String remitToZipPlus;
	private String hubWithLocalCertified;
	private String mbeWithLocalCertified;
	private String wbeWithLocalCertified;
	private String sdbWithSbaCertified;
	private String category8aWithSbaCertified;
	private String hubWithSbaCertified;
	private String educationalInstitution;
	private String mbeBlackAmerican;
	private String mbeHispanicAmerican;
	private String mbeAsianPacificAmerican;
	private String mbeNativeAmerican;
	private String mbeAsianIndianAmerican;
	private String mbeOther;
	private String vietNamVeteranOwned;
	private String disabledVeteranOwned;
	private String nonProfitOrganization;
	private String debarred;
	private String iso9001Certified;
	private String as9100Certified;
	private String as9120Certified;
	private String vatRegistrationNumber;
	private String requesterPhone;
	private String requesterEmail;
	private String opsEntityId;
	private String ssnFlag;
	private String originalPaymentTerms;
	
	private String sqlType = "null";


	//constructor
	public SupplierAddRequestViewBean() {
	}

	//setters
	public void setSupplierRequestId(BigDecimal supplierRequestId) {
		this.supplierRequestId = supplierRequestId;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setSupplierParent(String supplierParent) {
		this.supplierParent = supplierParent;
	}
	public void setFederalTaxId(String federalTaxId) {
		this.federalTaxId = federalTaxId;
	}
	public void setSmallBusiness(String smallBusiness) {
		this.smallBusiness = smallBusiness;
	}
	public void setMinorityBusiness(String minorityBusiness) {
		this.minorityBusiness = minorityBusiness;
	}
	public void setDisadvantagedBusiness(String disadvantagedBusiness) {
		this.disadvantagedBusiness = disadvantagedBusiness;
	}
	public void setDefaultPaymentTerms(String defaultPaymentTerms) {
		this.defaultPaymentTerms = defaultPaymentTerms;
	}
	public void setSupplierNotes(String supplierNotes) {
		this.supplierNotes = supplierNotes;
	}
	public void setFormerSupplierName(String formerSupplierName) {
		this.formerSupplierName = formerSupplierName;
	}
	public void setHaasAccountNumber(String haasAccountNumber) {
		this.haasAccountNumber = haasAccountNumber;
	}
	public void setCountryAbbrev(String countryAbbrev) {
		this.countryAbbrev = countryAbbrev;
	}
	public void setStateAbbrev(String stateAbbrev) {
		this.stateAbbrev = stateAbbrev;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public void setZipPlus(String zipPlus) {
		this.zipPlus = zipPlus;
	}
	public void setSupplierMainPhone(String supplierMainPhone) {
		this.supplierMainPhone = supplierMainPhone;
	}
	public void setSupplierMainFax(String supplierMainFax) {
		this.supplierMainFax = supplierMainFax;
	}
	public void setSupplierContactFirstName(String supplierContactFirstName) {
		this.supplierContactFirstName = supplierContactFirstName;
	}
	public void setSupplierContactLastName(String supplierContactLastName) {
		this.supplierContactLastName = supplierContactLastName;
	}
	public void setSupplierContactNickname(String supplierContactNickname) {
		this.supplierContactNickname = supplierContactNickname;
	}
	public void setSupplierContactPhone(String supplierContactPhone) {
		this.supplierContactPhone = supplierContactPhone;
	}
	public void setSupplierContactPhoneExt(String supplierContactPhoneExt) {
		this.supplierContactPhoneExt = supplierContactPhoneExt;
	}
	public void setSupplierContactFax(String supplierContactFax) {
		this.supplierContactFax = supplierContactFax;
	}
	public void setSupplierContactEmail(String supplierContactEmail) {
		this.supplierContactEmail = supplierContactEmail;
	}
	public void setESupplierId(String eSupplierId) {
		this.eSupplierId = eSupplierId;
	}
	public void setRequester(BigDecimal requester) {
		this.requester = requester;
	}
	public void setQualificationLevel(String qualificationLevel) {
		this.qualificationLevel = qualificationLevel;
	}
	public void setTypeOfPurchase(String typeOfPurchase) {
		this.typeOfPurchase = typeOfPurchase;
	}
	public void setApprover(BigDecimal approver) {
		this.approver = approver;
	}
	public void setSupplierRequestStatus(String supplierRequestStatus) {
		this.supplierRequestStatus = supplierRequestStatus;
	}
	public void setPaymentTermApprover(BigDecimal paymentTermApprover) {
		this.paymentTermApprover = paymentTermApprover;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public void setPaymentTermApproverName(String paymentTermApproverName) {
		this.paymentTermApproverName = paymentTermApproverName;
	}
	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}
	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}
	public void setWomanOwned(String womanOwned) {
		this.womanOwned = womanOwned;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public void setDateApproved(Date dateApproved) {
		this.dateApproved = dateApproved;
	}
	public void setDatePaymentTermsApproved(Date datePaymentTermsApproved) {
		this.datePaymentTermsApproved = datePaymentTermsApproved;
	}
	public void setStateTaxId(String stateTaxId) {
		this.stateTaxId = stateTaxId;
	}
	public void setSupplierRequestType(String supplierRequestType) {
		this.supplierRequestType = supplierRequestType;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setSapVendorCode(String sapVendorCode) {
		this.sapVendorCode = sapVendorCode;
	}
	public void setRemitToCountryAbbrev(String remitToCountryAbbrev) {
		this.remitToCountryAbbrev = remitToCountryAbbrev;
	}
	public void setRemitToStateAbbrev(String remitToStateAbbrev) {
		this.remitToStateAbbrev = remitToStateAbbrev;
	}
	public void setRemitToAddressLine1(String remitToAddressLine1) {
		this.remitToAddressLine1 = remitToAddressLine1;
	}
	public void setRemitToAddressLine2(String remitToAddressLine2) {
		this.remitToAddressLine2 = remitToAddressLine2;
	}
	public void setRemitToAddressLine3(String remitToAddressLine3) {
		this.remitToAddressLine3 = remitToAddressLine3;
	}
	public void setRemitToCity(String remitToCity) {
		this.remitToCity = remitToCity;
	}
	public void setRemitToZip(String remitToZip) {
		this.remitToZip = remitToZip;
	}
	public void setRemitToZipPlus(String remitToZipPlus) {
		this.remitToZipPlus = remitToZipPlus;
	}
	public void setHubWithLocalCertified(String hubWithLocalCertified) {
		this.hubWithLocalCertified = hubWithLocalCertified;
	}
	public void setMbeWithLocalCertified(String mbeWithLocalCertified) {
		this.mbeWithLocalCertified = mbeWithLocalCertified;
	}
	public void setWbeWithLocalCertified(String wbeWithLocalCertified) {
		this.wbeWithLocalCertified = wbeWithLocalCertified;
	}
	public void setSdbWithSbaCertified(String sdbWithSbaCertified) {
		this.sdbWithSbaCertified = sdbWithSbaCertified;
	}
	public void setCategory8aWithSbaCertified(String category8aWithSbaCertified) {
		this.category8aWithSbaCertified = category8aWithSbaCertified;
	}
	public void setHubWithSbaCertified(String hubWithSbaCertified) {
		this.hubWithSbaCertified = hubWithSbaCertified;
	}
	public void setEducationalInstitution(String educationalInstitution) {
		this.educationalInstitution = educationalInstitution;
	}
	public void setMbeBlackAmerican(String mbeBlackAmerican) {
		this.mbeBlackAmerican = mbeBlackAmerican;
	}
	public void setMbeHispanicAmerican(String mbeHispanicAmerican) {
		this.mbeHispanicAmerican = mbeHispanicAmerican;
	}
	public void setMbeAsianPacificAmerican(String mbeAsianPacificAmerican) {
		this.mbeAsianPacificAmerican = mbeAsianPacificAmerican;
	}
	public void setMbeNativeAmerican(String mbeNativeAmerican) {
		this.mbeNativeAmerican = mbeNativeAmerican;
	}
	public void setMbeAsianIndianAmerican(String mbeAsianIndianAmerican) {
		this.mbeAsianIndianAmerican = mbeAsianIndianAmerican;
	}
	public void setMbeOther(String mbeOther) {
		this.mbeOther = mbeOther;
	}
	public void setVietNamVeteranOwned(String vietNamVeteranOwned) {
		this.vietNamVeteranOwned = vietNamVeteranOwned;
	}
	public void setDisabledVeteranOwned(String disabledVeteranOwned) {
		this.disabledVeteranOwned = disabledVeteranOwned;
	}
	public void setNonProfitOrganization(String nonProfitOrganization) {
		this.nonProfitOrganization = nonProfitOrganization;
	}
	public void setDebarred(String debarred) {
		this.debarred = debarred;
	}
	public void setIso9001Certified(String iso9001Certified) {
		this.iso9001Certified = iso9001Certified;
	}
	public void setAs9100Certified(String as9100Certified) {
		this.as9100Certified = as9100Certified;
	}
	public void setAs9120Certified(String as9120Certified) {
		this.as9120Certified = as9120Certified;
	}
	public void setVatRegistrationNumber(String vatRegistrationNumber) {
		this.vatRegistrationNumber = vatRegistrationNumber;
	}
	public void setRequesterPhone(String requesterPhone) {
		this.requesterPhone = requesterPhone;
	}
	public void setRequesterEmail(String requesterEmail) {
		this.requesterEmail = requesterEmail;
	}


	//getters
	public BigDecimal getSupplierRequestId() {
		return supplierRequestId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getSupplierParent() {
		return supplierParent;
	}
	public String getFederalTaxId() {
		return federalTaxId;
	}
	public String getSmallBusiness() {
		return smallBusiness;
	}
	public String getMinorityBusiness() {
		return minorityBusiness;
	}
	public String getDisadvantagedBusiness() {
		return disadvantagedBusiness;
	}
	public String getDefaultPaymentTerms() {
		return defaultPaymentTerms;
	}
	public String getSupplierNotes() {
		return supplierNotes;
	}
	public String getFormerSupplierName() {
		return formerSupplierName;
	}
	public String getHaasAccountNumber() {
		return haasAccountNumber;
	}
	public String getCountryAbbrev() {
		return countryAbbrev;
	}
	public String getStateAbbrev() {
		return stateAbbrev;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public String getAddressLine3() {
		return addressLine3;
	}
	public String getCity() {
		return city;
	}
	public String getZip() {
		return zip;
	}
	public String getZipPlus() {
		return zipPlus;
	}
	public String getSupplierMainPhone() {
		return supplierMainPhone;
	}
	public String getSupplierMainFax() {
		return supplierMainFax;
	}
	public String getSupplierContactFirstName() {
		return supplierContactFirstName;
	}
	public String getSupplierContactLastName() {
		return supplierContactLastName;
	}
	public String getSupplierContactNickname() {
		return supplierContactNickname;
	}
	public String getSupplierContactPhone() {
		return supplierContactPhone;
	}
	public String getSupplierContactPhoneExt() {
		return supplierContactPhoneExt;
	}
	public String getSupplierContactFax() {
		return supplierContactFax;
	}
	public String getSupplierContactEmail() {
		return supplierContactEmail;
	}
	public String getESupplierId() {
		return eSupplierId;
	}
	public BigDecimal getRequester() {
		return requester;
	}
	public String getQualificationLevel() {
		return qualificationLevel;
	}
	public String getTypeOfPurchase() {
		return typeOfPurchase;
	}
	public BigDecimal getApprover() {
		return approver;
	}
	public String getSupplierRequestStatus() {
		return supplierRequestStatus;
	}
	public BigDecimal getPaymentTermApprover() {
		return paymentTermApprover;
	}
	public String getApproverName() {
		return approverName;
	}
	public String getPaymentTermApproverName() {
		return paymentTermApproverName;
	}
	public String getRequesterName() {
		return requesterName;
	}
	public String getReasonForRejection() {
		return reasonForRejection;
	}
	public String getWomanOwned() {
		return womanOwned;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public Date getDateApproved() {
		return dateApproved;
	}
	public Date getDatePaymentTermsApproved() {
		return datePaymentTermsApproved;
	}
	public String getStateTaxId() {
		return stateTaxId;
	}
	public String getSupplierRequestType() {
		return supplierRequestType;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getSapVendorCode() {
		return sapVendorCode;
	}
	public String getRemitToCountryAbbrev() {
		return remitToCountryAbbrev;
	}
	public String getRemitToStateAbbrev() {
		return remitToStateAbbrev;
	}
	public String getRemitToAddressLine1() {
		return remitToAddressLine1;
	}
	public String getRemitToAddressLine2() {
		return remitToAddressLine2;
	}
	public String getRemitToAddressLine3() {
		return remitToAddressLine3;
	}
	public String getRemitToCity() {
		return remitToCity;
	}
	public String getRemitToZip() {
		return remitToZip;
	}
	public String getRemitToZipPlus() {
		return remitToZipPlus;
	}
	public String getHubWithLocalCertified() {
		return hubWithLocalCertified;
	}
	public String getMbeWithLocalCertified() {
		return mbeWithLocalCertified;
	}
	public String getWbeWithLocalCertified() {
		return wbeWithLocalCertified;
	}
	public String getSdbWithSbaCertified() {
		return sdbWithSbaCertified;
	}
	public String getCategory8aWithSbaCertified() {
		return category8aWithSbaCertified;
	}
	public String getHubWithSbaCertified() {
		return hubWithSbaCertified;
	}
	public String getEducationalInstitution() {
		return educationalInstitution;
	}
	public String getMbeBlackAmerican() {
		return mbeBlackAmerican;
	}
	public String getMbeHispanicAmerican() {
		return mbeHispanicAmerican;
	}
	public String getMbeAsianPacificAmerican() {
		return mbeAsianPacificAmerican;
	}
	public String getMbeNativeAmerican() {
		return mbeNativeAmerican;
	}
	public String getMbeAsianIndianAmerican() {
		return mbeAsianIndianAmerican;
	}
	public String getMbeOther() {
		return mbeOther;
	}
	public String getVietNamVeteranOwned() {
		return vietNamVeteranOwned;
	}
	public String getDisabledVeteranOwned() {
		return disabledVeteranOwned;
	}
	public String getNonProfitOrganization() {
		return nonProfitOrganization;
	}
	public String getDebarred() {
		return debarred;
	}
	public String getIso9001Certified() {
		return iso9001Certified;
	}
	public String getAs9100Certified() {
		return as9100Certified;
	}
	public String getAs9120Certified() {
		return as9120Certified;
	}
	public String getVatRegistrationNumber() {
		return vatRegistrationNumber;
	}
	public String getRequesterPhone() {
		return requesterPhone;
	}
	public String getRequesterEmail() {
		return requesterEmail;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setSupplierRequestId(stream.readBigDecimal());
			this.setSupplierName(stream.readString());
			this.setSupplierParent(stream.readString());
			this.setFederalTaxId(stream.readString());
			this.setSmallBusiness(stream.readString());
			this.setMinorityBusiness(stream.readString());
			this.setDisadvantagedBusiness(stream.readString());
			this.setDefaultPaymentTerms(stream.readString());
			this.setSupplierNotes(stream.readString());
			this.setFormerSupplierName(stream.readString());
			this.setHaasAccountNumber(stream.readString());
			this.setCountryAbbrev(stream.readString());
			this.setStateAbbrev(stream.readString());
			this.setAddressLine1(stream.readString());
			this.setAddressLine2(stream.readString());
			this.setAddressLine3(stream.readString());
			this.setCity(stream.readString());
			this.setZip(stream.readString());
			this.setZipPlus(stream.readString());
			this.setSupplierMainPhone(stream.readString());
			this.setSupplierMainFax(stream.readString());
			this.setSupplierContactFirstName(stream.readString());
			this.setSupplierContactLastName(stream.readString());
			this.setSupplierContactNickname(stream.readString());
			this.setSupplierContactPhone(stream.readString());
			this.setSupplierContactPhoneExt(stream.readString());
			this.setSupplierContactFax(stream.readString());
			this.setSupplierContactEmail(stream.readString());
			this.setESupplierId(stream.readString());
			this.setRequester(stream.readBigDecimal());
			this.setQualificationLevel(stream.readString());
			this.setTypeOfPurchase(stream.readString());
			this.setApprover(stream.readBigDecimal());
			this.setSupplierRequestStatus(stream.readString());
			this.setPaymentTermApprover(stream.readBigDecimal());
			this.setApproverName(stream.readString());
			this.setPaymentTermApproverName(stream.readString());
			this.setRequesterName(stream.readString());
			this.setReasonForRejection(stream.readString());
			this.setWomanOwned(stream.readString());
			this.setTransactionDate(new Date(stream.readDate().getTime()));
			this.setDateApproved(new Date(stream.readDate().getTime()));
			this.setDatePaymentTermsApproved(new Date(stream.readDate().getTime()));
			this.setStateTaxId(stream.readString());
			this.setSupplierRequestType(stream.readString());
			this.setSupplier(stream.readString());
			this.setSapVendorCode(stream.readString());
			this.setRemitToCountryAbbrev(stream.readString());
			this.setRemitToStateAbbrev(stream.readString());
			this.setRemitToAddressLine1(stream.readString());
			this.setRemitToAddressLine2(stream.readString());
			this.setRemitToAddressLine3(stream.readString());
			this.setRemitToCity(stream.readString());
			this.setRemitToZip(stream.readString());
			this.setRemitToZipPlus(stream.readString());
			this.setHubWithLocalCertified(stream.readString());
			this.setMbeWithLocalCertified(stream.readString());
			this.setWbeWithLocalCertified(stream.readString());
			this.setSdbWithSbaCertified(stream.readString());
			this.setCategory8aWithSbaCertified(stream.readString());
			this.setHubWithSbaCertified(stream.readString());
			this.setEducationalInstitution(stream.readString());
			this.setMbeBlackAmerican(stream.readString());
			this.setMbeHispanicAmerican(stream.readString());
			this.setMbeAsianPacificAmerican(stream.readString());
			this.setMbeNativeAmerican(stream.readString());
			this.setMbeAsianIndianAmerican(stream.readString());
			this.setMbeOther(stream.readString());
			this.setVietNamVeteranOwned(stream.readString());
			this.setDisabledVeteranOwned(stream.readString());
			this.setNonProfitOrganization(stream.readString());
			this.setDebarred(stream.readString());
			this.setIso9001Certified(stream.readString());
			this.setAs9100Certified(stream.readString());
			this.setAs9120Certified(stream.readString());
			this.setVatRegistrationNumber(stream.readString());
			this.setRequesterPhone(stream.readString());
			this.setRequesterEmail(stream.readString());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".readSQL method failed").
			initCause(e);
		}
	}
	public void writeSQL(SQLOutput stream) throws SQLException {
		try {
			stream.writeBigDecimal(this.getSupplierRequestId());
			stream.writeString(this.getSupplierName());
			stream.writeString(this.getSupplierParent());
			stream.writeString(this.getFederalTaxId());
			stream.writeString(this.getSmallBusiness());
			stream.writeString(this.getMinorityBusiness());
			stream.writeString(this.getDisadvantagedBusiness());
			stream.writeString(this.getDefaultPaymentTerms());
			stream.writeString(this.getSupplierNotes());
			stream.writeString(this.getFormerSupplierName());
			stream.writeString(this.getHaasAccountNumber());
			stream.writeString(this.getCountryAbbrev());
			stream.writeString(this.getStateAbbrev());
			stream.writeString(this.getAddressLine1());
			stream.writeString(this.getAddressLine2());
			stream.writeString(this.getAddressLine3());
			stream.writeString(this.getCity());
			stream.writeString(this.getZip());
			stream.writeString(this.getZipPlus());
			stream.writeString(this.getSupplierMainPhone());
			stream.writeString(this.getSupplierMainFax());
			stream.writeString(this.getSupplierContactFirstName());
			stream.writeString(this.getSupplierContactLastName());
			stream.writeString(this.getSupplierContactNickname());
			stream.writeString(this.getSupplierContactPhone());
			stream.writeString(this.getSupplierContactPhoneExt());
			stream.writeString(this.getSupplierContactFax());
			stream.writeString(this.getSupplierContactEmail());
			stream.writeString(this.getESupplierId());
			stream.writeBigDecimal(this.getRequester());
			stream.writeString(this.getQualificationLevel());
			stream.writeString(this.getTypeOfPurchase());
			stream.writeBigDecimal(this.getApprover());
			stream.writeString(this.getSupplierRequestStatus());
			stream.writeBigDecimal(this.getPaymentTermApprover());
			stream.writeString(this.getApproverName());
			stream.writeString(this.getPaymentTermApproverName());
			stream.writeString(this.getRequesterName());
			stream.writeString(this.getReasonForRejection());
			stream.writeString(this.getWomanOwned());
			stream.writeDate(new java.sql.Date(this.getTransactionDate().getTime()));
			stream.writeDate(new java.sql.Date(this.getDateApproved().getTime()));
			stream.writeDate(new java.sql.Date(this.getDatePaymentTermsApproved().getTime()));
			stream.writeString(this.getStateTaxId());
			stream.writeString(this.getSupplierRequestType());
			stream.writeString(this.getSupplier());
			stream.writeString(this.getSapVendorCode());
			stream.writeString(this.getRemitToCountryAbbrev());
			stream.writeString(this.getRemitToStateAbbrev());
			stream.writeString(this.getRemitToAddressLine1());
			stream.writeString(this.getRemitToAddressLine2());
			stream.writeString(this.getRemitToAddressLine3());
			stream.writeString(this.getRemitToCity());
			stream.writeString(this.getRemitToZip());
			stream.writeString(this.getRemitToZipPlus());
			stream.writeString(this.getHubWithLocalCertified());
			stream.writeString(this.getMbeWithLocalCertified());
			stream.writeString(this.getWbeWithLocalCertified());
			stream.writeString(this.getSdbWithSbaCertified());
			stream.writeString(this.getCategory8aWithSbaCertified());
			stream.writeString(this.getHubWithSbaCertified());
			stream.writeString(this.getEducationalInstitution());
			stream.writeString(this.getMbeBlackAmerican());
			stream.writeString(this.getMbeHispanicAmerican());
			stream.writeString(this.getMbeAsianPacificAmerican());
			stream.writeString(this.getMbeNativeAmerican());
			stream.writeString(this.getMbeAsianIndianAmerican());
			stream.writeString(this.getMbeOther());
			stream.writeString(this.getVietNamVeteranOwned());
			stream.writeString(this.getDisabledVeteranOwned());
			stream.writeString(this.getNonProfitOrganization());
			stream.writeString(this.getDebarred());
			stream.writeString(this.getIso9001Certified());
			stream.writeString(this.getAs9100Certified());
			stream.writeString(this.getAs9120Certified());
			stream.writeString(this.getVatRegistrationNumber());
			stream.writeString(this.getRequesterPhone());
			stream.writeString(this.getRequesterEmail());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").
			initCause(e);
		}
	}

	public String getOpsEntityId() {
		return opsEntityId;
}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public String getOriginalPaymentTerms() {
		return originalPaymentTerms;
}
	public void setOriginalPaymentTerms(String originalPaymentTerms) {
		this.originalPaymentTerms = originalPaymentTerms;
	}

	public String getSsnFlag() {
		return ssnFlag;
	}

	public void setSsnFlag(String ssnFlag) {
		this.ssnFlag = ssnFlag;
	}
}