package  com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SupplierEntitySearchViewBean <br>
 * @version: 1.0, Oct 26, 2009 <br>
 *****************************************************************************/


public class SupplierEntitySearchViewBean extends BaseDataBean {

	private String supplier;
	private String supplierName;
	private String countryAbbrev;
	private String stateAbbrev;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String zip;
	private String locationDesc;
	private String mainPhone;
	private String defaultPaymentTerms;
	private String status;
	private String formerSupplierName;
	private String qualificationLevel;
	private String email;
	private String supplierEmail;
	private String firstName;
	private String lastName;
	private String nickname;
	private String phone;
	private String phoneExtension;
	private String fax;
	private String debarred;
	private String typeOfPurchase;
	private String supplierNotes;
	private String opsEntityId;
	private String paymentTerms;
	private BigDecimal approvedBy;
	private Date approvedOn;
	private String paymentTermStatus;
	
	private String addressLine3;
	private String newSupplierId;
	private String federalTaxId;
	private String vatRegistrationNumber;
	private String eSupplierId;
	private String e_SupplierId; // this is to work around the strange problem with the property named eSupplierId
	private String accountNumber;
	private String activePaymentTerms;
	private String existingSupplierPriceList;
	private String statusChangeComments;
	
	private String sapVendorCode;
	private String ok;


	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	//constructor
	public SupplierEntitySearchViewBean() {
	}
	
//	 THE FOLLOWING 2 METHODS ARE NOT AUTO-GENERATED
	public String getE_SupplierId() {
		return eSupplierId;
	}

	public void setE_SupplierId(String supplierId) {
		eSupplierId = supplierId;
	}

	//setters
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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
	public void setCity(String city) {
		this.city = city;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}
	public void setMainPhone(String mainPhone) {
		this.mainPhone = mainPhone;
	}
	public void setDefaultPaymentTerms(String defaultPaymentTerms) {
		this.defaultPaymentTerms = defaultPaymentTerms;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setFormerSupplierName(String formerSupplierName) {
		this.formerSupplierName = formerSupplierName;
	}
	public void setQualificationLevel(String qualificationLevel) {
		this.qualificationLevel = qualificationLevel;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setPhoneExtension(String phoneExtension) {
		this.phoneExtension = phoneExtension;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public void setDebarred(String debarred) {
		this.debarred = debarred;
	}
	public void setTypeOfPurchase(String typeOfPurchase) {
		this.typeOfPurchase = typeOfPurchase;
	}
	public void setSupplierNotes(String supplierNotes) {
		this.supplierNotes = supplierNotes;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public void setApprovedBy(BigDecimal approvedBy) {
		this.approvedBy = approvedBy;
	}
	public void setApprovedOn(Date approvedOn) {
		this.approvedOn = approvedOn;
	}
	public void setPaymentTermStatus(String paymentTermStatus) {
		this.paymentTermStatus = paymentTermStatus;
	}


	//getters
	public String getSupplier() {
		return supplier;
	}
	public String getSupplierName() {
		return supplierName;
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
	public String getCity() {
		return city;
	}
	public String getZip() {
		return zip;
	}
	public String getLocationDesc() {
		return locationDesc;
	}
	public String getMainPhone() {
		return mainPhone;
	}
	public String getDefaultPaymentTerms() {
		return defaultPaymentTerms;
	}
	public String getStatus() {
		return status;
	}
	public String getFormerSupplierName() {
		return formerSupplierName;
	}
	public String getQualificationLevel() {
		return qualificationLevel;
	}
	public String getEmail() {
		return email;
	}
	public String getSupplierEmail() {
		return supplierEmail;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getNickname() {
		return nickname;
	}
	public String getPhone() {
		return phone;
	}
	public String getPhoneExtension() {
		return phoneExtension;
	}
	public String getFax() {
		return fax;
	}
	public String getDebarred() {
		return debarred;
	}
	public String getTypeOfPurchase() {
		return typeOfPurchase;
	}
	public String getSupplierNotes() {
		return supplierNotes;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public BigDecimal getApprovedBy() {
		return approvedBy;
	}
	public Date getApprovedOn() {
		return approvedOn;
	}
	public String getPaymentTermStatus() {
		return paymentTermStatus;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getActivePaymentTerms() {
		return activePaymentTerms;
	}

	public void setActivePaymentTerms(String activePaymentTerms) {
		this.activePaymentTerms = activePaymentTerms;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getESupplierId() {
		return eSupplierId;
	}

	public void setESupplierId(String supplierId) {
		eSupplierId = supplierId;
	}

	public String getExistingSupplierPriceList() {
		return existingSupplierPriceList;
	}

	public void setExistingSupplierPriceList(String existingSupplierPriceList) {
		this.existingSupplierPriceList = existingSupplierPriceList;
	}

	public String getFederalTaxId() {
		return federalTaxId;
	}

	public void setFederalTaxId(String federalTaxId) {
		this.federalTaxId = federalTaxId;
	}

	public String getNewSupplierId() {
		return newSupplierId;
	}

	public void setNewSupplierId(String newSupplierId) {
		this.newSupplierId = newSupplierId;
	}

	public String getVatRegistrationNumber() {
		return vatRegistrationNumber;
	}

	public void setVatRegistrationNumber(String vatRegistrationNumber) {
		this.vatRegistrationNumber = vatRegistrationNumber;
	}

	public String getStatusChangeComments() {
		return statusChangeComments;
	}

	public void setStatusChangeComments(String statusChangeComments) {
		this.statusChangeComments = statusChangeComments;
	}

	public String getSapVendorCode() {
		return sapVendorCode;
	}

	public void setSapVendorCode(String sapVendorCode) {
		this.sapVendorCode = sapVendorCode;
	}
}