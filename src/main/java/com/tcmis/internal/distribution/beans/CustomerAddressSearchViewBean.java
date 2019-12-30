package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerAddressSearchViewBean <br>
 * @version: 1.0, Apr 7, 2009 <br>
 *****************************************************************************/


public class CustomerAddressSearchViewBean extends BaseDataBean {

	private BigDecimal customerId; // input 1
	private String customerName;   // input 2
	private String companyId;      // input 3
	private String facilityId;
	private String shipToLocationId;
	private String shipToCompanyId;
	private String locationShortName;
	private String locationDesc;
	private String locationType;
	private String billToAddress;   
	private String paymentTerms;	// input 4
	private BigDecimal creditLimit; // input 5
	private BigDecimal overdueLimit;// input 6    3% overdueLimit = '3'
	private String overdueLimitBasis; // input 7, 3% overdueLimitBasis = '%' '
	private String creditStatus;    // input 8
	private String currencyId;      // input 9
	private String fixedCurrency;   // input 10
	private String priceGroupId;	// input 11
	private BigDecimal shelfLifeRequired; // input 12
	private String shipComplete;	// input 13
	private String accountSysId;
	private String chargeType;
	private BigDecimal scratchPadId;
	private String shipToAddressLine1Display;
	private String shipToAddressLine2Display;
	private String shipToAddressLine3Display;
	private String shipToAddressLine4Display;
	private String shipToAddressLine5Display;
	private String shipToAddressSearch;
	private String billToLocationId;
	private String billToCompanyId;
	private String addressLine1Display;
	private String addressLine2Display;
	private String addressLine3Display;
	private String addressLine4Display;
	private String addressLine5Display;
	
	private String searchNameMode;
	private String searchNameArgument;
	private String searchCustomerIdMode;
	private BigDecimal searchCustomerIdArgument;
	private String searchBillToMode;
	private String searchBillToArgument;
	private String searchShipToMode;
	private String searchShipToArgument;
	private String searchSynonymMode;
	private String searchSynonymArgument;
//  new column	
	private String facilityPriceGroupId;
	private String facilityPriceGroupName;
	private String exitLabelFormat;
	private String defaultCurrencyId;
	private String defaultCurrencyName;
	private String msdsLocaleOverride;

	// need these for new changes
	private BigDecimal salesAgentId;
    private String opsEntityId;
	private BigDecimal fieldSalesRepId;
	private String shiptoNotes;
// above from current customer_address_view_bean
	private String city; //input 14
	private String state;//input 15
	private String zip;//input 16
	private String zipPlus;//input 17
	private String country;//input 18
	private String billToAddr1;//input 19
	private String billToAddr2;//input 20
	private String billToAddr3;//input 21
	private String billToAddr4;//input 22
	private String consolidationMethod;//input 23
	private String discountGroupId;//input 24
	private String emailOrderAck;//input 25
	private String doc1;//input 26
	private String doc2;//input 27
	private String doc3;//input 28
	private String doc4;//input 29
	private String mfgCoc;//input 30
	private String mfgCoa;//input 31
	private String taxCurrencyId;
	private String salesAgentName;
	private String inventoryGroupDefault;
	private String inventoryGroupName;
	private String fieldSalesRepName;
    private String notes;
    private String chargeFreight;
    private String opsCompanyId;
    private String opsEntityName;
    private BigDecimal availableCredit;
    private String legacyCustomerId;
    private String facilityName;
    private String status;
    private String shiptoStatus;
    
    private BigDecimal defaultCustomerContactId;
    private String defaultCustomerContactName;
    private String defaultCustomerContactPhone;
    private String defaultCustomerContactFax;
    private String defaultCustomerContactEmail;
    
    private String updatedByName;
    private Date updatedOn;
    private BigDecimal updatedBy;
	private boolean shipInfoChange;
	
	private String abcClassification;
	private BigDecimal jdeCustomerShipTo;

	private String customerOrigin;
	
	
// above from current page.	
	
    public void setShipInfoChange(boolean shipInfoChange) {
		this.shipInfoChange = shipInfoChange;
	}

	public boolean getShipInfoChange() {
		return shipInfoChange;
	}
	
	public String getDefaultCustomerContactEmail() {
		return defaultCustomerContactEmail;
	}

	public void setDefaultCustomerContactEmail(String defaultCustomerContactEmail) {
		this.defaultCustomerContactEmail = defaultCustomerContactEmail;
	}

	public String getDefaultCustomerContactFax() {
		return defaultCustomerContactFax;
	}

	public void setDefaultCustomerContactFax(String defaultCustomerContactFax) {
		this.defaultCustomerContactFax = defaultCustomerContactFax;
	}

	public BigDecimal getDefaultCustomerContactId() {
		return defaultCustomerContactId;
	}

	public void setDefaultCustomerContactId(BigDecimal defaultCustomerContactId) {
		this.defaultCustomerContactId = defaultCustomerContactId;
	}

	public String getDefaultCustomerContactName() {
		return defaultCustomerContactName;
	}

	public void setDefaultCustomerContactName(String defaultCustomerContactName) {
		this.defaultCustomerContactName = defaultCustomerContactName;
	}

	public String getDefaultCustomerContactPhone() {
		return defaultCustomerContactPhone;
	}

	public void setDefaultCustomerContactPhone(String defaultCustomerContactPhone) {
		this.defaultCustomerContactPhone = defaultCustomerContactPhone;
	}

	//constructor
	public CustomerAddressSearchViewBean() {
	}

	//setters
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setLocationShortName(String locationShortName) {
		this.locationShortName = locationShortName;
	}
	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public void setBillToAddress(String billToAddress) {
		this.billToAddress = billToAddress;
	}
	public void setShipToAddressSearch(String shipToAddressSearch) {
		this.shipToAddressSearch = shipToAddressSearch;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}
	public void setOverdueLimit(BigDecimal overdueLimit) {
		this.overdueLimit = overdueLimit;
	}
	public void setOverdueLimitBasis(String overdueLimitBasis) {
		this.overdueLimitBasis = overdueLimitBasis;
	}
	public void setCreditStatus(String creditStatus) {
		this.creditStatus = creditStatus;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setFixedCurrency(String fixedCurrency) {
		this.fixedCurrency = fixedCurrency;
	}
	public void setPriceGroupId(String priceGroupId) {
		this.priceGroupId = priceGroupId;
	}
	public void setShelfLifeRequired(BigDecimal shelfLifeRequired) {
		this.shelfLifeRequired = shelfLifeRequired;
	}
	public void setShipComplete(String shipComplete) {
		this.shipComplete = shipComplete;
	}
	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}

	//getters
	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}
	public String getUpdatedByName() {
		return updatedByName;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getLocationShortName() {
		return locationShortName;
	}
	public String getLocationDesc() {
		return locationDesc;
	}
	public String getLocationType() {
		return locationType;
	}
	public String getBillToAddress() {
		return billToAddress;
	}
	public String getShipToAddressSearch() {
		return shipToAddressSearch;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public BigDecimal getCreditLimit() {
		return creditLimit;
	}
	public BigDecimal getOverdueLimit() {
		return overdueLimit;
	}
	public String getOverdueLimitBasis() {
		return overdueLimitBasis;
	}
	public String getCreditStatus() {
		return creditStatus;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public String getFixedCurrency() {
		return fixedCurrency;
	}
	public String getPriceGroupId() {
		return priceGroupId;
	}
	public BigDecimal getShelfLifeRequired() {
		return shelfLifeRequired;
	}
	public String getShipComplete() {
		return shipComplete;
	}
	public String getAccountSysId() {
		return accountSysId;
	}
	public String getChargeType() {
		return chargeType;
	}

	public String getSearchBillToArgument() {
		return searchBillToArgument;
	}

	public void setSearchBillToArgument(String searchBillToArgument) {
		this.searchBillToArgument = searchBillToArgument;
	}

	public String getSearchBillToMode() {
		return searchBillToMode;
	}

	public void setSearchBillToMode(String searchBillToMode) {
		this.searchBillToMode = searchBillToMode;
	}

	public BigDecimal getSearchCustomerIdArgument() {
		return searchCustomerIdArgument;
	}

	public void setSearchCustomerIdArgument(BigDecimal searchCustomerIdArgument) {
		this.searchCustomerIdArgument = searchCustomerIdArgument;
	}

	public String getSearchCustomerIdMode() {
		return searchCustomerIdMode;
	}

	public void setSearchCustomerIdMode(String searchCustomerIdMode) {
		this.searchCustomerIdMode = searchCustomerIdMode;
	}

	public String getSearchNameArgument() {
		return searchNameArgument;
	}

	public void setSearchNameArgument(String searchNameArgument) {
		this.searchNameArgument = searchNameArgument;
	}

	public String getSearchNameMode() {
		return searchNameMode;
	}

	public void setSearchNameMode(String searchNameMode) {
		this.searchNameMode = searchNameMode;
	}

	public String getSearchShipToArgument() {
		return searchShipToArgument;
	}

	public void setSearchShipToArgument(String searchShipToArgument) {
		this.searchShipToArgument = searchShipToArgument;
	}

	public String getSearchShipToMode() {
		return searchShipToMode;
	}

	public void setSearchShipToMode(String searchShipToMode) {
		this.searchShipToMode = searchShipToMode;
	}

	public BigDecimal getScratchPadId() {
		return scratchPadId;
	}

	public void setScratchPadId(BigDecimal scratchPadId) {
		this.scratchPadId = scratchPadId;
	}

	public String getShipToAddressLine1Display() {
		return shipToAddressLine1Display;
	}

	public void setShipToAddressLine1Display(String shipToAddressLine1Display) {
		this.shipToAddressLine1Display = shipToAddressLine1Display;
	}

	public String getShipToAddressLine2Display() {
		return shipToAddressLine2Display;
	}

	public void setShipToAddressLine2Display(String shipToAddressLine2Display) {
		this.shipToAddressLine2Display = shipToAddressLine2Display;
	}

	public String getShipToAddressLine3Display() {
		return shipToAddressLine3Display;
	}

	public void setShipToAddressLine3Display(String shipToAddressLine3Display) {
		this.shipToAddressLine3Display = shipToAddressLine3Display;
	}

	public String getShipToAddressLine4Display() {
		return shipToAddressLine4Display;
	}

	public void setShipToAddressLine4Display(String shipToAddressLine4Display) {
		this.shipToAddressLine4Display = shipToAddressLine4Display;
	}

	public String getBillToAddr1() {
		return billToAddr1;
	}

	public void setBillToAddr1(String billToAddr1) {
		this.billToAddr1 = billToAddr1;
	}

	public String getBillToAddr2() {
		return billToAddr2;
	}

	public void setBillToAddr2(String billToAddr2) {
		this.billToAddr2 = billToAddr2;
	}

	public String getBillToAddr3() {
		return billToAddr3;
	}

	public void setBillToAddr3(String billToAddr3) {
		this.billToAddr3 = billToAddr3;
	}

	public String getBillToAddr4() {
		return billToAddr4;
	}

	public void setBillToAddr4(String billToAddr4) {
		this.billToAddr4 = billToAddr4;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getConsolidationMethod() {
		return consolidationMethod;
	}

	public void setConsolidationMethod(String consolidationMethod) {
		this.consolidationMethod = consolidationMethod;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDiscountGroupId() {
		return discountGroupId;
	}

	public void setDiscountGroupId(String discountGroupId) {
		this.discountGroupId = discountGroupId;
	}

	public String getDoc1() {
		return doc1;
	}

	public void setDoc1(String doc1) {
		this.doc1 = doc1;
	}

	public String getDoc2() {
		return doc2;
	}

	public void setDoc2(String doc2) {
		this.doc2 = doc2;
	}

	public String getDoc3() {
		return doc3;
	}

	public void setDoc3(String doc3) {
		this.doc3 = doc3;
	}

	public String getDoc4() {
		return doc4;
	}

	public void setDoc4(String doc4) {
		this.doc4 = doc4;
	}

	public String getEmailOrderAck() {
		return emailOrderAck;
	}

	public void setEmailOrderAck(String emailOrderAck) {
		this.emailOrderAck = emailOrderAck;
	}

	public String getMfgCoa() {
		return mfgCoa;
	}

	public void setMfgCoa(String mfgCoa) {
		this.mfgCoa = mfgCoa;
	}

	public String getMfgCoc() {
		return mfgCoc;
	}

	public void setMfgCoc(String mfgCoc) {
		this.mfgCoc = mfgCoc;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getZipPlus() {
		return zipPlus;
	}

	public void setZipPlus(String zipPlus) {
		this.zipPlus = zipPlus;
	}

	public String getTaxCurrencyId() {
		return taxCurrencyId;
	}

	public void setTaxCurrencyId(String taxCurrencyId) {
		this.taxCurrencyId = taxCurrencyId;
	}

	public String getShipToAddressLine5Display() {
		return shipToAddressLine5Display;
	}

	public void setShipToAddressLine5Display(String shipToAddressLine5Display) {
		this.shipToAddressLine5Display = shipToAddressLine5Display;
	}

	public String getAddressLine1Display() {
		return addressLine1Display;
	}

	public void setAddressLine1Display(String addressLine1Display) {
		this.addressLine1Display = addressLine1Display;
	}

	public String getAddressLine2Display() {
		return addressLine2Display;
	}

	public void setAddressLine2Display(String addressLine2Display) {
		this.addressLine2Display = addressLine2Display;
	}

	public String getAddressLine3Display() {
		return addressLine3Display;
	}

	public void setAddressLine3Display(String addressLine3Display) {
		this.addressLine3Display = addressLine3Display;
	}

	public String getAddressLine4Display() {
		return addressLine4Display;
	}

	public void setAddressLine4Display(String addressLine4Display) {
		this.addressLine4Display = addressLine4Display;
	}

	public String getAddressLine5Display() {
		return addressLine5Display;
	}

	public void setAddressLine5Display(String addressLine5Display) {
		this.addressLine5Display = addressLine5Display;
	}

	public void setInventoryGroupDefault(String inventoryGroupDefault) {
		this.inventoryGroupDefault = inventoryGroupDefault;
	}

	public String getInventoryGroupDefault() {
		return inventoryGroupDefault;
	}

	public void setFieldSalesRepId(BigDecimal fieldSalesRepId) {
		this.fieldSalesRepId = fieldSalesRepId;
	}

	public BigDecimal getFieldSalesRepId() {
		return fieldSalesRepId;
	}

	public void setSalesAgentId(BigDecimal salesAgentId) {
		this.salesAgentId = salesAgentId;
	}

	public BigDecimal getSalesAgentId() {
		return salesAgentId;
	}

	public void setSalesAgentName(String salesAgentName) {
		this.salesAgentName = salesAgentName;
	}

	public String getSalesAgentName() {
		return salesAgentName;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public void setFieldSalesRepName(String fieldSalesRepName) {
		this.fieldSalesRepName = fieldSalesRepName;
	}

	public String getFieldSalesRepName() {
		return fieldSalesRepName;
	}

    public String getNotes() {
		return notes;
	}

	public BigDecimal getAvailableCredit() {
		return availableCredit;
	}

	public void setAvailableCredit(BigDecimal availableCredit) {
		this.availableCredit = availableCredit;
	}

	public String getChargeFreight() {
		return chargeFreight;
	}

	public void setChargeFreight(String chargeFreight) {
		this.chargeFreight = chargeFreight;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public String getOpsEntityName() {
		return opsEntityName;
	}

	public void setOpsEntityName(String opsEntityName) {
		this.opsEntityName = opsEntityName;
	}

	public String getShipToCompanyId() {
		return shipToCompanyId;
	}

	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}

	public String getOpsCompanyId() {
		return opsCompanyId;
	}

	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}

	public String getBillToLocationId() {
		return billToLocationId;
	}

	public void setBillToLocationId(String billToLocationId) {
		this.billToLocationId = billToLocationId;
	}

	public String getBillToCompanyId() {
		return billToCompanyId;
	}

	public void setBillToCompanyId(String billToCompanyId) {
		this.billToCompanyId = billToCompanyId;
	}

	public void setLegacyCustomerId(String legacyCustomerId) {
		this.legacyCustomerId = legacyCustomerId;
	}

	public String getLegacyCustomerId() {
		return legacyCustomerId;
	}

	public void setSearchSynonymMode(String searchSynonymMode) {
		this.searchSynonymMode = searchSynonymMode;
	}

	public String getSearchSynonymMode() {
		return searchSynonymMode;
	}

	public void setSearchSynonymArgument(String searchSynonymArgument) {
		this.searchSynonymArgument = searchSynonymArgument;
	}

	public String getSearchSynonymArgument() {
		return searchSynonymArgument;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getShiptoStatus() {
		return shiptoStatus;
	}

	public void setShiptoStatus(String shiptoStatus) {

		this.shiptoStatus = shiptoStatus;
	}
	public String getShiptoNotes() {
		return shiptoNotes;
	}
	public void setShiptoNotes(String shiptoNotes) {
		this.shiptoNotes = shiptoNotes;
	}
	public String getFacilityPriceGroupId() {
		return facilityPriceGroupId;
	}
	public void setFacilityPriceGroupId(String facilityPriceGroupId) {
		this.facilityPriceGroupId = facilityPriceGroupId;
	}
	public String getFacilityPriceGroupName() {
		return facilityPriceGroupName;
	}
	public void setFacilityPriceGroupName(String facilityPriceGroupName) {
		this.facilityPriceGroupName = facilityPriceGroupName;
	}

	public String getDefaultCurrencyId() {
		return defaultCurrencyId;
	}

	public void setDefaultCurrencyId(String defaultCurrencyId) {
		this.defaultCurrencyId = defaultCurrencyId;
	}

	public String getDefaultCurrencyName() {
		return defaultCurrencyName;
	}

	public void setDefaultCurrencyName(String defaultCurrencyName) {
		this.defaultCurrencyName = defaultCurrencyName;
	}

	public String getExitLabelFormat() {
		return exitLabelFormat;
	}

	public void setExitLabelFormat(String exitLabelFormat) {
		this.exitLabelFormat = exitLabelFormat;
	}

	public String getMsdsLocaleOverride() {
		return msdsLocaleOverride;
	}
	public void setMsdsLocaleOverride(String msdsLocaleOverride) {
		this.msdsLocaleOverride = msdsLocaleOverride;
	}

	public String getAbcClassification() {
		return abcClassification;
	}

	public void setAbcClassification(String abcClassification) {
		this.abcClassification = abcClassification;
	}

	public BigDecimal getJdeCustomerShipTo() {
		return jdeCustomerShipTo;
	}

	public void setJdeCustomerShipTo(BigDecimal jdeCustomerShipTo) {
		this.jdeCustomerShipTo = jdeCustomerShipTo;
	}
	public String getCustomerOrigin() {
		return customerOrigin;
	}
	public void setCustomerOrigin(String customerOrigin) {
		this.customerOrigin = customerOrigin;
	}	
}