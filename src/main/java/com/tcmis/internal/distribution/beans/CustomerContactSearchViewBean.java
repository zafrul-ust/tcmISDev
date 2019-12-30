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
 * CLASSNAME: CustomerContactSearchViewBean <br>
 * @version: 1.0, Mar 17, 2009 <br>
 *****************************************************************************/


public class CustomerContactSearchViewBean extends BaseDataBean {

	private BigDecimal customerId;
	private BigDecimal contactId;
	private String contactType;
	private String firstName;
	private String lastName;
	private String nickname;
	private String phone;
	private String mobile;
	private String fax;
	private String email;
	private String status;
	private String purchasing;
	private String accountsPayable;
	private String receiving;
	private String qualityAssurance;
	private String management;
	private String customerName;
	private String billToCompanyId;
	private String billToLocationId;
	private String addressLine1Display;
	private String addressLine2Display;
	private String addressLine3Display;
	private String addressLine4Display;
	private String addressLine5Display;
	private BigDecimal fieldSalesRepId;
	private String fieldSalesRepName;
	private String paymentTerms;
	private BigDecimal creditLimit;
	private BigDecimal overdueLimit;
	private String overdueLimitBasis;
	private String creditStatus;
	private String currencyId;
	private String fixedCurrency;
	private String priceGroupId;
	private BigDecimal shelfLifeRequired;
	private String shipComplete;
	private String accountSysId;
	private String chargeType;
	private String notes;
    private String chargeFreight;
    private BigDecimal availableCredit;
    private String legacyCustomerId;
    private String contactName;
    
    private String abcClassification;
	//constructor
	public CustomerContactSearchViewBean() {
	}

//	setters
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setContactId(BigDecimal contactId) {
		this.contactId = contactId;
	}
	public void setContactType(String contactType) {
		this.contactType = contactType;
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
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setPurchasing(String purchasing) {
		this.purchasing = purchasing;
	}
	public void setAccountsPayable(String accountsPayable) {
		this.accountsPayable = accountsPayable;
	}
	public void setReceiving(String receiving) {
		this.receiving = receiving;
	}
	public void setQualityAssurance(String qualityAssurance) {
		this.qualityAssurance = qualityAssurance;
	}
	public void setManagement(String management) {
		this.management = management;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setBillToCompanyId(String billToCompanyId) {
		this.billToCompanyId = billToCompanyId;
	}
	public void setBillToLocationId(String billToLocationId) {
		this.billToLocationId = billToLocationId;
	}
	public void setCreditStatus(String creditStatus) {
		this.creditStatus = creditStatus;
	}
	public void setAddressLine1Display(String addressLine1Display) {
		this.addressLine1Display = addressLine1Display;
	}
	public void setAddressLine2Display(String addressLine2Display) {
		this.addressLine2Display = addressLine2Display;
	}
	public void setAddressLine3Display(String addressLine3Display) {
		this.addressLine3Display = addressLine3Display;
	}
	public void setAddressLine4Display(String addressLine4Display) {
		this.addressLine4Display = addressLine4Display;
	}
	public void setAddressLine5Display(String addressLine5Display) {
		this.addressLine5Display = addressLine5Display;
	}


	//getters
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public BigDecimal getContactId() {
		return contactId;
	}
	public String getContactType() {
		return contactType;
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
	public String getMobile() {
		return mobile;
	}
	public String getFax() {
		return fax;
	}
	public String getEmail() {
		return email;
	}
	public String getStatus() {
		return status;
	}
	public String getPurchasing() {
		return purchasing;
	}
	public String getAccountsPayable() {
		return accountsPayable;
	}
	public String getReceiving() {
		return receiving;
	}
	public String getQualityAssurance() {
		return qualityAssurance;
	}
	public String getManagement() {
		return management;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getBillToCompanyId() {
		return billToCompanyId;
	}
	public String getBillToLocationId() {
		return billToLocationId;
	}
	public String getCreditStatus() {
		return creditStatus;
	}
	public String getAddressLine1Display() {
		return addressLine1Display;
	}
	public String getAddressLine2Display() {
		return addressLine2Display;
	}
	public String getAddressLine3Display() {
		return addressLine3Display;
	}
	public String getAddressLine4Display() {
		return addressLine4Display;
	}
	public String getAddressLine5Display() {
		return addressLine5Display;
	}

	public String getAccountSysId() {
		return accountSysId;
	}

	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
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

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public BigDecimal getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getFieldSalesRepId() {
		return fieldSalesRepId;
	}

	public void setFieldSalesRepId(BigDecimal fieldSalesRepId) {
		this.fieldSalesRepId = fieldSalesRepId;
	}

	public String getFieldSalesRepName() {
		return fieldSalesRepName;
	}

	public void setFieldSalesRepName(String fieldSalesRepName) {
		this.fieldSalesRepName = fieldSalesRepName;
	}

	public String getFixedCurrency() {
		return fixedCurrency;
	}

	public void setFixedCurrency(String fixedCurrency) {
		this.fixedCurrency = fixedCurrency;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public BigDecimal getOverdueLimit() {
		return overdueLimit;
	}

	public void setOverdueLimit(BigDecimal overdueLimit) {
		this.overdueLimit = overdueLimit;
	}

	public String getOverdueLimitBasis() {
		return overdueLimitBasis;
	}

	public void setOverdueLimitBasis(String overdueLimitBasis) {
		this.overdueLimitBasis = overdueLimitBasis;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getPriceGroupId() {
		return priceGroupId;
	}

	public void setPriceGroupId(String priceGroupId) {
		this.priceGroupId = priceGroupId;
	}

	public BigDecimal getShelfLifeRequired() {
		return shelfLifeRequired;
	}

	public void setShelfLifeRequired(BigDecimal shelfLifeRequired) {
		this.shelfLifeRequired = shelfLifeRequired;
	}

	public String getShipComplete() {
		return shipComplete;
	}

	public void setShipComplete(String shipComplete) {
		this.shipComplete = shipComplete;
	}

	public void setLegacyCustomerId(String legacyCustomerId) {
		this.legacyCustomerId = legacyCustomerId;
	}

	public String getLegacyCustomerId() {
		return legacyCustomerId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getAbcClassification() {
		return abcClassification;
	}

	public void setAbcClassification(String abcClassification) {
		this.abcClassification = abcClassification;
	}



}