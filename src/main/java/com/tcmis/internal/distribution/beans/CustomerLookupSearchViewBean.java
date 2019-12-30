package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerAddressSearchViewBean <br>
 * @version: 1.0, Apr 7, 2009 <br>
 *****************************************************************************/


public class CustomerLookupSearchViewBean extends BaseDataBean {

	private BigDecimal customerId; // input 1
	private String customerName;   // input 2
	private String companyId;      // input 3
	private String facilityId;
	private String shipToLocationId;
	private String locationShortName;
	private String locationDesc;
	private String locationType;
	private String billToAddress;   
	private String shipToAddress;
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
	private String legacyCustomerId;
	
// above from current page.	
	
	//constructor
	public CustomerLookupSearchViewBean() {
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
	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
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


	//getters
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
	public String getShipToAddress() {
		return shipToAddress;
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

	public void setLegacyCustomerId(String legacyCustomerId) {
		this.legacyCustomerId = legacyCustomerId;
	}

	public String getLegacyCustomerId() {
		return legacyCustomerId;
	}

}