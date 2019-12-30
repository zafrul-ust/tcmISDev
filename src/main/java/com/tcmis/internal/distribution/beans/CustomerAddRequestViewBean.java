package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerAddRequestViewBean <br>
 * @version: 1.0, Aug 21, 2009 <br>
 *****************************************************************************/


public class CustomerAddRequestViewBean extends BaseDataBean {

	private BigDecimal customerRequestId;
	private BigDecimal customerId;
	private String customerName;
	private String billToCompanyId;
	private String billToLocationId;
	private BigDecimal payerId;
	private BigDecimal industry;
	private String invoiceConsolidation;
	private String paymentTerms;
	private String currencyId;
	private String fixedCurrency;
	private BigDecimal creditLimit;
	private BigDecimal overdueLimit;
	private String overdueLimitBasis;
	private String creditStatus;
	private String priceGroupId;
	private BigDecimal shelfLifeRequired;
	private String shipComplete;
	private String accountSysId;
	private String chargeType;
	private String sapCustomerCode;
	private String taxRegistrationType;
	private String taxRegistrationNumber;
	private String taxRegistrationType2;
	private String taxRegistrationNumber2;
	private String taxRegistrationType3;
	private String taxRegistrationNumber3;
	private String taxRegistrationType4;
	private String taxRegistrationNumber4;
	private String taxCurrencyId;
	private String emailOrderAck;
	private String shipMfgCoc;
	private String shipMfgCoa;
	private String shipMsds;
	private String countryAbbrev;
	private String stateAbbrev;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String addressLine4;
	private String addressLine5;
	private String city;
	private String zip;
	private String zipPlus;
	private String locationDesc;
	private String customerNotes;
	private BigDecimal requester;
	private BigDecimal approver;
	private BigDecimal paymentTermsApprover;
	private String customerRequestStatus;
	private String reasonForRejection;
	private Date transactionDate;
	private Date dateApproved;
	private Date datePaymentTermsApproved;
	private String customerRequestType;
	private String opsEntityId;
	private String approverName;
	private String paymentTermApproverName;
	private String requesterName;
	private String requesterPhone;
	private String requesterEmail;
	private String newCompanyId;
	private String companyName;
	private String companyShortName;
	private String companyId;
	private String chargeFreight;
	private BigDecimal fieldSalesRepId;
	private String fieldSalesRepName;
	private String billToCompanyName;
    private String notes;
    
    private String legacyCustomerId;
    private String sapIndustryKey;
    private String exitLabelFormat;
    
    private String billingChange;
    private String otherChange;
    private String updatedBillingDataByName;
    private Date updatedBillingDataOn;
    private BigDecimal updatedBillingDataBy;
    private String updatedByName;
    private BigDecimal updatedBy;
    private Date updatedOn;
    
    private String abcClassification;
    private String taxNotes;
    private BigDecimal jdeCustomerBillTo;
    private String customerOrigin;
    
    
    private BigDecimal autoEmailBatchSize;
    private String autoEmailAddress;
    private String autoEmailInvoice;
    
	public String getExitLabelFormat() {
		return exitLabelFormat;
	}

	public void setExitLabelFormat(String exitLabelFormat) {
		this.exitLabelFormat = exitLabelFormat;
	}

	//constructor
	public CustomerAddRequestViewBean() {
	}

	//setters
	public void setCustomerRequestId(BigDecimal customerRequestId) {
		this.customerRequestId = customerRequestId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
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
	public void setPayerId(BigDecimal payerId) {
		this.payerId = payerId;
	}
	public void setIndustry(BigDecimal industry) {
		this.industry = industry;
	}
	public void setInvoiceConsolidation(String invoiceConsolidation) {
		this.invoiceConsolidation = invoiceConsolidation;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setFixedCurrency(String fixedCurrency) {
		this.fixedCurrency = fixedCurrency;
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
	public void setSapCustomerCode(String sapCustomerCode) {
		this.sapCustomerCode = sapCustomerCode;
	}
	public void setTaxRegistrationType(String taxRegistrationType) {
		this.taxRegistrationType = taxRegistrationType;
	}
	public void setTaxRegistrationNumber(String taxRegistrationNumber) {
		this.taxRegistrationNumber = taxRegistrationNumber;
	}
	public void setTaxRegistrationType2(String taxRegistrationType2) {
		this.taxRegistrationType2 = taxRegistrationType2;
	}
	public void setTaxRegistrationNumber2(String taxRegistrationNumber2) {
		this.taxRegistrationNumber2 = taxRegistrationNumber2;
	}
	public void setTaxRegistrationType3(String taxRegistrationType3) {
		this.taxRegistrationType3 = taxRegistrationType3;
	}
	public void setTaxRegistrationNumber3(String taxRegistrationNumber3) {
		this.taxRegistrationNumber3 = taxRegistrationNumber3;
	}
	public void setTaxRegistrationType4(String taxRegistrationType4) {
		this.taxRegistrationType4 = taxRegistrationType4;
	}
	public void setTaxRegistrationNumber4(String taxRegistrationNumber4) {
		this.taxRegistrationNumber4 = taxRegistrationNumber4;
	}
	public void setTaxCurrencyId(String taxCurrencyId) {
		this.taxCurrencyId = taxCurrencyId;
	}
	public void setEmailOrderAck(String emailOrderAck) {
		this.emailOrderAck = emailOrderAck;
	}
	public void setShipMfgCoc(String shipMfgCoc) {
		this.shipMfgCoc = shipMfgCoc;
	}
	public void setShipMfgCoa(String shipMfgCoa) {
		this.shipMfgCoa = shipMfgCoa;
	}
	public void setShipMsds(String shipMsds) {
		this.shipMsds = shipMsds;
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
	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
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
	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}
	public void setCustomerNotes(String customerNotes) {
		this.customerNotes = customerNotes;
	}
	public void setRequester(BigDecimal requester) {
		this.requester = requester;
	}
	public void setApprover(BigDecimal approver) {
		this.approver = approver;
	}
	public void setPaymentTermsApprover(BigDecimal paymentTermsApprover) {
		this.paymentTermsApprover = paymentTermsApprover;
	}
	public void setCustomerRequestStatus(String customerRequestStatus) {
		this.customerRequestStatus = customerRequestStatus;
	}
	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
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
	public void setCustomerRequestType(String customerRequestType) {
		this.customerRequestType = customerRequestType;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
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
	public void setRequesterPhone(String requesterPhone) {
		this.requesterPhone = requesterPhone;
	}
	public void setRequesterEmail(String requesterEmail) {
		this.requesterEmail = requesterEmail;
	}
	public void setNewCompanyId(String newCompanyId) {
		this.newCompanyId = newCompanyId;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setCompanyShortName(String companyShortName) {
		this.companyShortName = companyShortName;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setChargeFreight(String chargeFreight) {
		this.chargeFreight = chargeFreight;
	}
	public void setFieldSalesRepId(BigDecimal fieldSalesRepId) {
		this.fieldSalesRepId = fieldSalesRepId;
	}
	public void setFieldSalesRepName(String fieldSalesRepName) {
		this.fieldSalesRepName = fieldSalesRepName;
	}	
	public void setBillingChange(String billingChange) {
		this.billingChange = billingChange;
	}	
	public void setUpdatedBillingDataByName(String updatedBillingDataByName) {
		this.updatedBillingDataByName = updatedBillingDataByName;
	}
	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}
	public void setUpdatedBillingDataOn(Date updatedBillingDataOn) {
		this.updatedBillingDataOn = updatedBillingDataOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public void setOtherChange(String otherChange) {
		this.otherChange = otherChange;
	}

	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}
	public void setUpdatedBillingDataBy(BigDecimal updatedBillingDataBy) {
		this.updatedBillingDataBy = updatedBillingDataBy;
	}
	public void setCustomerOrigin(String customerOrigin) {
		this.customerOrigin = customerOrigin;
	}
	public void setAutoEmailBatchSize(BigDecimal autoEmailBatchSize) {
		this.autoEmailBatchSize = autoEmailBatchSize;
	}
	public void setAutoEmailAddress(String autoEmailAddress) {
		this.autoEmailAddress = autoEmailAddress;
	}
	public void setAutoEmailInvoice(String autoEmailInvoice) {
		this.autoEmailInvoice = autoEmailInvoice;
	}
	
	//getters
	public BigDecimal getAutoEmailBatchSize() {
		return autoEmailBatchSize;
	}
	public String getAutoEmailAddress() {
		return autoEmailAddress;
	}
	public String getAutoEmailInvoice() {
		return autoEmailInvoice;
	}
	public BigDecimal getUpdatedBillingDataBy() {
		return updatedBillingDataBy;
	}
	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}
	public String getOtherChange() {
		return otherChange;
	}
	public Date getUpdatedBillingDataOn() {
		return updatedBillingDataOn;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public String getUpdatedByName() {
		return updatedByName;
	}
	public String getUpdatedBillingDataByName() {
		return updatedBillingDataByName;
	}
	public String getBillingChange() {
		return billingChange;
	}
	public BigDecimal getCustomerRequestId() {
		return customerRequestId;
	}
	public BigDecimal getCustomerId() {
		return customerId;
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
	public BigDecimal getPayerId() {
		return payerId;
	}
	public BigDecimal getIndustry() {
		return industry;
	}
	public String getInvoiceConsolidation() {
		return invoiceConsolidation;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public String getFixedCurrency() {
		return fixedCurrency;
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
	public String getSapCustomerCode() {
		return sapCustomerCode;
	}
	public String getTaxRegistrationType() {
		return taxRegistrationType;
	}
	public String getTaxRegistrationNumber() {
		return taxRegistrationNumber;
	}
	public String getTaxRegistrationType2() {
		return taxRegistrationType2;
	}
	public String getTaxRegistrationNumber2() {
		return taxRegistrationNumber2;
	}
	public String getTaxRegistrationType3() {
		return taxRegistrationType3;
	}
	public String getTaxRegistrationNumber3() {
		return taxRegistrationNumber3;
	}
	public String getTaxRegistrationType4() {
		return taxRegistrationType4;
	}
	public String getTaxRegistrationNumber4() {
		return taxRegistrationNumber4;
	}
	public String getTaxCurrencyId() {
		return taxCurrencyId;
	}
	public String getEmailOrderAck() {
		return emailOrderAck;
	}
	public String getShipMfgCoc() {
		return shipMfgCoc;
	}
	public String getShipMfgCoa() {
		return shipMfgCoa;
	}
	public String getShipMsds() {
		return shipMsds;
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
	public String getAddressLine4() {
		return addressLine4;
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
	public String getLocationDesc() {
		return locationDesc;
	}
	public String getCustomerNotes() {
		return customerNotes;
	}
	public BigDecimal getRequester() {
		return requester;
	}
	public BigDecimal getApprover() {
		return approver;
	}
	public BigDecimal getPaymentTermsApprover() {
		return paymentTermsApprover;
	}
	public String getCustomerRequestStatus() {
		return customerRequestStatus;
	}
	public String getReasonForRejection() {
		return reasonForRejection;
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
	public String getCustomerRequestType() {
		return customerRequestType;
	}
	public String getOpsEntityId() {
		return opsEntityId;
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
	public String getRequesterPhone() {
		return requesterPhone;
	}
	public String getRequesterEmail() {
		return requesterEmail;
	}
	public String getNewCompanyId() {
		return newCompanyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getCompanyShortName() {
		return companyShortName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getChargeFreight() {
		return chargeFreight;
	}
	public BigDecimal getFieldSalesRepId() {
		return fieldSalesRepId;
	}
	public String getFieldSalesRepName() {
		return fieldSalesRepName;
	}
	public String getAddressLine5() {
		return addressLine5;
	}
	public void setAddressLine5(String addressLine5) {
		this.addressLine5 = addressLine5;
	}

	public void setBillToCompanyName(String billToCompanyName) {
		this.billToCompanyName = billToCompanyName;
	}

	public String getBillToCompanyName() {
		return billToCompanyName;
	}

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

	public String getLegacyCustomerId() {
		return legacyCustomerId;
	}

	public void setLegacyCustomerId(String legacyCustomerId) {
		this.legacyCustomerId = legacyCustomerId;
	}

	public String getSapIndustryKey() {
		return sapIndustryKey;
	}

	public void setSapIndustryKey(String sapIndustryKey) {
		this.sapIndustryKey = sapIndustryKey;
	}

	public String getAbcClassification() {
		return abcClassification;
	}

	public void setAbcClassification(String abcClassification) {
		this.abcClassification = abcClassification;
	}

	public String getTaxNotes() {
		return taxNotes;
	}

	public void setTaxNotes(String taxNotes) {
		this.taxNotes = taxNotes;
	}

	public BigDecimal getJdeCustomerBillTo() {
		return jdeCustomerBillTo;
	}

	public void setJdeCustomerBillTo(BigDecimal jdeCustomerBillTo) {
		this.jdeCustomerBillTo = jdeCustomerBillTo;
	}
	public String getCustomerOrigin() {
		return customerOrigin;
	}

}