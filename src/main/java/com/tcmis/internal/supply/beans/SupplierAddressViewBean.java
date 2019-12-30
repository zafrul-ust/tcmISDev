package com.tcmis.internal.supply.beans;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SupplierAddressViewBean <br>
 * @version: 1.0, Aug 28, 2007 <br>
 *****************************************************************************/


public class SupplierAddressViewBean extends BaseDataBean {

	private String supplier;
	private String supplierName;
	private String countryAbbrev;
	private String stateAbbrev;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String city;
	private String zip;
	private String locationDesc;
	private String clientLocationCode;
	private String mainPhone;
	private String defaultPaymentTerms;
	private String status;
	private String formerSupplierName;
	private String qualificationLevel;
	private String email;
	private String supplierEmail;
	private String statusChangeComments;
	private String newSupplierId;
	private String newSupplierName;
	private String federalTaxId;
	private String eSupplierId;
	private String accountNumber;
	private String transport;
	private String comments;
	private String ok;	
	private String firstName;
	private String lastName;
	private String nickname;
	private String phone;
	private String phoneExtension;
	private String fax;
	private String smallBusiness;
	private String minorityBusiness;
	private String disadvantagedBusiness;
	private String billingLocationId;
	private String isoCertified;
	private String hubWithLocalCertified;
	private String mbeWithLocalCertified;
	private String wbeWithLocalCertified;
	private String sdbWithSbaCertified;
	private String category8aWithSbaCertified;
	private String hubWithSbaCertified;
	private String educationalInstitution;
	private String mbeBlackAmerican;
	private String mbeAsianPacificAmerican;
	private String mbeNativeAmerican;
	private String mbeAsianIndianAmerican;
	private String mbeOther;
	private String womanOwned;
	private String vietNamVeteranOwned;
	private String disabledVeteranOwned;
	private String nonProfitOrganization;
	private String laborSurplusArea;
	private String laborSurplusAndRuralArea;
	private String ruralArea;
	private String debarred;
	private String sapVendorCode;
	private String vatRegistrationNumber;
	private String typeOfPurchase;
	private String supplierNotes;
	private String opsEntityId;
	private String activePaymentTerms;
	private String existingSupplierPriceList;
	// do not auto-generate the getter and setter for the following property:
	private String e_SupplierId; // this is to work around the strange problem with the property named eSupplierId

    private String iso9001Certified;

    public String getIso9001Certified() {
        return iso9001Certified;
    }

    public void setIso9001Certified(String iso9001Certified) {
        this.iso9001Certified = iso9001Certified;
    }

    public String getExistingSupplierPriceList() {
		return existingSupplierPriceList;
	}

	public void setExistingSupplierPriceList(String existingSupplierPriceList) {
		this.existingSupplierPriceList = existingSupplierPriceList;
	}

	//constructor
	public SupplierAddressViewBean() {
	}

    public String getActivePaymentTerms() {
        return activePaymentTerms;
    }

    public void setActivePaymentTerms(String activePaymentTerms) {
        this.activePaymentTerms = activePaymentTerms;
    }

    // THE FOLLOWING 2 METHODS ARE NOT AUTO-GENERATED
	public String getE_SupplierId() {
		return eSupplierId;
	}

	public void setE_SupplierId(String supplierId) {
		eSupplierId = supplierId;
	}
	// 

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getBillingLocationId() {
		return billingLocationId;
	}

	public void setBillingLocationId(String billingLocationId) {
		this.billingLocationId = billingLocationId;
	}

	public String getCategory8aWithSbaCertified() {
		return category8aWithSbaCertified;
	}

	public void setCategory8aWithSbaCertified(String category8aWithSbaCertified) {
		this.category8aWithSbaCertified = category8aWithSbaCertified;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getClientLocationCode() {
		return clientLocationCode;
	}

	public void setClientLocationCode(String clientLocationCode) {
		this.clientLocationCode = clientLocationCode;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCountryAbbrev() {
		return countryAbbrev;
	}

	public void setCountryAbbrev(String countryAbbrev) {
		this.countryAbbrev = countryAbbrev;
	}

	public String getDebarred() {
		return debarred;
	}

	public void setDebarred(String debarred) {
		this.debarred = debarred;
	}

	public String getDefaultPaymentTerms() {
		return defaultPaymentTerms;
	}

	public void setDefaultPaymentTerms(String defaultPaymentTerms) {
		this.defaultPaymentTerms = defaultPaymentTerms;
	}

	public String getDisabledVeteranOwned() {
		return disabledVeteranOwned;
	}

	public void setDisabledVeteranOwned(String disabledVeteranOwned) {
		this.disabledVeteranOwned = disabledVeteranOwned;
	}

	public String getDisadvantagedBusiness() {
		return disadvantagedBusiness;
	}

	public void setDisadvantagedBusiness(String disadvantagedBusiness) {
		this.disadvantagedBusiness = disadvantagedBusiness;
	}

	public String getEducationalInstitution() {
		return educationalInstitution;
	}

	public void setEducationalInstitution(String educationalInstitution) {
		this.educationalInstitution = educationalInstitution;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getESupplierId() {
		return eSupplierId;
	}

	public void setESupplierId(String supplierId) {
		eSupplierId = supplierId;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFederalTaxId() {
		return federalTaxId;
	}

	public void setFederalTaxId(String federalTaxId) {
		this.federalTaxId = federalTaxId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFormerSupplierName() {
		return formerSupplierName;
	}

	public void setFormerSupplierName(String formerSupplierName) {
		this.formerSupplierName = formerSupplierName;
	}

	public String getHubWithLocalCertified() {
		return hubWithLocalCertified;
	}

	public void setHubWithLocalCertified(String hubWithLocalCertified) {
		this.hubWithLocalCertified = hubWithLocalCertified;
	}

	public String getHubWithSbaCertified() {
		return hubWithSbaCertified;
	}

	public void setHubWithSbaCertified(String hubWithSbaCertified) {
		this.hubWithSbaCertified = hubWithSbaCertified;
	}

	public String getIsoCertified() {
		return isoCertified;
	}

	public void setIsoCertified(String isoCertified) {
		this.isoCertified = isoCertified;
	}

	public String getLaborSurplusAndRuralArea() {
		return laborSurplusAndRuralArea;
	}

	public void setLaborSurplusAndRuralArea(String laborSurplusAndRuralArea) {
		this.laborSurplusAndRuralArea = laborSurplusAndRuralArea;
	}

	public String getLaborSurplusArea() {
		return laborSurplusArea;
	}

	public void setLaborSurplusArea(String laborSurplusArea) {
		this.laborSurplusArea = laborSurplusArea;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLocationDesc() {
		return locationDesc;
	}

	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}

	public String getMainPhone() {
		return mainPhone;
	}

	public void setMainPhone(String mainPhone) {
		this.mainPhone = mainPhone;
	}

	public String getMbeAsianIndianAmerican() {
		return mbeAsianIndianAmerican;
	}

	public void setMbeAsianIndianAmerican(String mbeAsianIndianAmerican) {
		this.mbeAsianIndianAmerican = mbeAsianIndianAmerican;
	}

	public String getMbeAsianPacificAmerican() {
		return mbeAsianPacificAmerican;
	}

	public void setMbeAsianPacificAmerican(String mbeAsianPacificAmerican) {
		this.mbeAsianPacificAmerican = mbeAsianPacificAmerican;
	}

	public String getMbeBlackAmerican() {
		return mbeBlackAmerican;
	}

	public void setMbeBlackAmerican(String mbeBlackAmerican) {
		this.mbeBlackAmerican = mbeBlackAmerican;
	}

	public String getMbeNativeAmerican() {
		return mbeNativeAmerican;
	}

	public void setMbeNativeAmerican(String mbeNativeAmerican) {
		this.mbeNativeAmerican = mbeNativeAmerican;
	}

	public String getMbeOther() {
		return mbeOther;
	}

	public void setMbeOther(String mbeOther) {
		this.mbeOther = mbeOther;
	}

	public String getMbeWithLocalCertified() {
		return mbeWithLocalCertified;
	}

	public void setMbeWithLocalCertified(String mbeWithLocalCertified) {
		this.mbeWithLocalCertified = mbeWithLocalCertified;
	}

	public String getMinorityBusiness() {
		return minorityBusiness;
	}

	public void setMinorityBusiness(String minorityBusiness) {
		this.minorityBusiness = minorityBusiness;
	}

	public String getNewSupplierId() {
		return newSupplierId;
	}

	public void setNewSupplierId(String newSupplierId) {
		this.newSupplierId = newSupplierId;
	}

	public String getNewSupplierName() {
		return newSupplierName;
	}

	public void setNewSupplierName(String newSupplierName) {
		this.newSupplierName = newSupplierName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNonProfitOrganization() {
		return nonProfitOrganization;
	}

	public void setNonProfitOrganization(String nonProfitOrganization) {
		this.nonProfitOrganization = nonProfitOrganization;
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoneExtension() {
		return phoneExtension;
	}

	public void setPhoneExtension(String phoneExtension) {
		this.phoneExtension = phoneExtension;
	}

	public String getQualificationLevel() {
		return qualificationLevel;
	}

	public void setQualificationLevel(String qualificationLevel) {
		this.qualificationLevel = qualificationLevel;
	}

	public String getRuralArea() {
		return ruralArea;
	}

	public void setRuralArea(String ruralArea) {
		this.ruralArea = ruralArea;
	}

	public String getSapVendorCode() {
		return sapVendorCode;
	}

	public void setSapVendorCode(String sapVendorCode) {
		this.sapVendorCode = sapVendorCode;
	}

	public String getSdbWithSbaCertified() {
		return sdbWithSbaCertified;
	}

	public void setSdbWithSbaCertified(String sdbWithSbaCertified) {
		this.sdbWithSbaCertified = sdbWithSbaCertified;
	}

	public String getSmallBusiness() {
		return smallBusiness;
	}

	public void setSmallBusiness(String smallBusiness) {
		this.smallBusiness = smallBusiness;
	}

	public String getStateAbbrev() {
		return stateAbbrev;
	}

	public void setStateAbbrev(String stateAbbrev) {
		this.stateAbbrev = stateAbbrev;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusChangeComments() {
		return statusChangeComments;
	}

	public void setStatusChangeComments(String statusChangeComments) {
		this.statusChangeComments = statusChangeComments;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getSupplierEmail() {
		return supplierEmail;
	}

	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	public String getVatRegistrationNumber() {
		return vatRegistrationNumber;
	}

	public void setVatRegistrationNumber(String vatRegistrationNumber) {
		this.vatRegistrationNumber = vatRegistrationNumber;
	}

	public String getVietNamVeteranOwned() {
		return vietNamVeteranOwned;
	}

	public void setVietNamVeteranOwned(String vietNamVeteranOwned) {
		this.vietNamVeteranOwned = vietNamVeteranOwned;
	}

	public String getWbeWithLocalCertified() {
		return wbeWithLocalCertified;
	}

	public void setWbeWithLocalCertified(String wbeWithLocalCertified) {
		this.wbeWithLocalCertified = wbeWithLocalCertified;
	}

	public String getWomanOwned() {
		return womanOwned;
	}

	public void setWomanOwned(String womanOwned) {
		this.womanOwned = womanOwned;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getTypeOfPurchase() {
		return typeOfPurchase;
	}

	public void setTypeOfPurchase(String typeOfPurchase) {
		this.typeOfPurchase = typeOfPurchase;
	}

	public String getSupplierNotes() {
		return supplierNotes;
	}

	public void setSupplierNotes(String supplierNotes) {
		this.supplierNotes = supplierNotes;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
}

}