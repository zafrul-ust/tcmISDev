package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: InvoiceCorrPrintViewBean <br>
 * @version: 1.0, May 12, 2010 <br>
 *****************************************************************************/


public class InvoiceCorrPrintViewBean extends BaseDataBean {

	private BigDecimal invoice;
	private Date invoiceDate;
	private Date paymentDueDate;
	private BigDecimal prNumber;
	private String opsEntityId;
	private String operatingEntityName;
	private String opsEntityLogoUrl;
	private String opsEntityWebsiteUrl;
	private String opsEntityTermsUrl;
	private String opsEntityAddressLine1;
	private String opsEntityAddressLine2;
	private String opsEntityAddressLine3;
	private String opsEntityAddressLine4;
	private String opsEntityAddressLine5;
	private String opsEntityAttention;
	private String opsEntityPhone;
	private String opsEntityFax;
	private String opsEntityEmail;
	private String opsEntityTaxType;
	private String opsEntityTaxId;
	private String opsEntityTaxType2;
	private String opsEntityTaxId2;
	private String opsEntityTaxType3;
	private String opsEntityTaxId3;
	private String opsEntityTaxType4;
	private String opsEntityTaxId4;
	private BigDecimal customerId;
	private String billToName;
	private String billToAddressLine1;
	private String billToAddressLine2;
	private String billToAddressLine3;
	private String billToAddressLine4;
	private String billToAddressLine5;
	private String taxRegistrationType;
	private String taxRegistrationNumber;
	private String shipToName;
	private String shipToDescription;
	private String paymentTerms;
	private String shipToAddressLine1;
	private String shipToAddressLine2;
	private String shipToAddressLine3;
	private String shipToAddressLine4;
	private String shipToAddressLine5;
	private String requestorName;
	private String requestorPhone;
	private String requestorFax;
	private String requestorEmail;
	private Date submittedDate;
	private String poNumber;
	private String endUser;
	private String incoTerms;
	private String carrierServiceType;
	private String submittedByName;
	private BigDecimal lastLineItem;
	private String currencyId;
	private BigDecimal totalGoods;
	private BigDecimal totalLineCharges;
	private BigDecimal totalHeaderCharges;
	private BigDecimal totalTaxAmount;
	private String remittanceInstructionLine1;
	private String remittanceInstructionLine2;
	private String remittanceInstructionLine3;
	private String remittanceInstructionLine4;
	private String remittanceInstructionLine5;
	private String remittanceInstructionLine6;
	private String remittanceInstructionLine7;
	private String remittanceInstructionLine8;
	private String remittanceInstructionLine9;
	private String remittanceInstructionLine10;
	private String homeCurrencyId;
	private BigDecimal homeExchangeRate;
	private String csrName;
	private String csrEmail;	
	private BigDecimal correctedInvoice;
	private String customerInvoice;	
	
	private String companyId;	


	//constructor
	public InvoiceCorrPrintViewBean() {
	}

	//setters
	public void setInvoice(BigDecimal invoice) {
		this.invoice = invoice;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public void setPaymentDueDate(Date paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}
	public void setOpsEntityLogoUrl(String opsEntityLogoUrl) {
		this.opsEntityLogoUrl = opsEntityLogoUrl;
	}
	public void setOpsEntityWebsiteUrl(String opsEntityWebsiteUrl) {
		this.opsEntityWebsiteUrl = opsEntityWebsiteUrl;
	}
	public void setOpsEntityTermsUrl(String opsEntityTermsUrl) {
		this.opsEntityTermsUrl = opsEntityTermsUrl;
	}
	public void setOpsEntityAddressLine1(String opsEntityAddressLine1) {
		this.opsEntityAddressLine1 = opsEntityAddressLine1;
	}
	public void setOpsEntityAddressLine2(String opsEntityAddressLine2) {
		this.opsEntityAddressLine2 = opsEntityAddressLine2;
	}
	public void setOpsEntityAddressLine3(String opsEntityAddressLine3) {
		this.opsEntityAddressLine3 = opsEntityAddressLine3;
	}
	public void setOpsEntityAddressLine4(String opsEntityAddressLine4) {
		this.opsEntityAddressLine4 = opsEntityAddressLine4;
	}
	public void setOpsEntityAddressLine5(String opsEntityAddressLine5) {
		this.opsEntityAddressLine5 = opsEntityAddressLine5;
	}
	public void setOpsEntityAttention(String opsEntityAttention) {
		this.opsEntityAttention = opsEntityAttention;
	}
	public void setOpsEntityPhone(String opsEntityPhone) {
		this.opsEntityPhone = opsEntityPhone;
	}
	public void setOpsEntityFax(String opsEntityFax) {
		this.opsEntityFax = opsEntityFax;
	}
	public void setOpsEntityEmail(String opsEntityEmail) {
		this.opsEntityEmail = opsEntityEmail;
	}
	public void setOpsEntityTaxType(String opsEntityTaxType) {
		this.opsEntityTaxType = opsEntityTaxType;
	}
	public void setOpsEntityTaxId(String opsEntityTaxId) {
		this.opsEntityTaxId = opsEntityTaxId;
	}
	public void setOpsEntityTaxType2(String opsEntityTaxType2) {
		this.opsEntityTaxType2 = opsEntityTaxType2;
	}
	public void setOpsEntityTaxId2(String opsEntityTaxId2) {
		this.opsEntityTaxId2 = opsEntityTaxId2;
	}
	public void setOpsEntityTaxType3(String opsEntityTaxType3) {
		this.opsEntityTaxType3 = opsEntityTaxType3;
	}
	public void setOpsEntityTaxId3(String opsEntityTaxId3) {
		this.opsEntityTaxId3 = opsEntityTaxId3;
	}
	public void setOpsEntityTaxType4(String opsEntityTaxType4) {
		this.opsEntityTaxType4 = opsEntityTaxType4;
	}
	public void setOpsEntityTaxId4(String opsEntityTaxId4) {
		this.opsEntityTaxId4 = opsEntityTaxId4;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setBillToName(String billToName) {
		this.billToName = billToName;
	}
	public void setBillToAddressLine1(String billToAddressLine1) {
		this.billToAddressLine1 = billToAddressLine1;
	}
	public void setBillToAddressLine2(String billToAddressLine2) {
		this.billToAddressLine2 = billToAddressLine2;
	}
	public void setBillToAddressLine3(String billToAddressLine3) {
		this.billToAddressLine3 = billToAddressLine3;
	}
	public void setBillToAddressLine4(String billToAddressLine4) {
		this.billToAddressLine4 = billToAddressLine4;
	}
	public void setBillToAddressLine5(String billToAddressLine5) {
		this.billToAddressLine5 = billToAddressLine5;
	}
	public void setTaxRegistrationType(String taxRegistrationType) {
		this.taxRegistrationType = taxRegistrationType;
	}
	public void setTaxRegistrationNumber(String taxRegistrationNumber) {
		this.taxRegistrationNumber = taxRegistrationNumber;
	}
	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}
	public void setShipToDescription(String shipToDescription) {
		this.shipToDescription = shipToDescription;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public void setShipToAddressLine1(String shipToAddressLine1) {
		this.shipToAddressLine1 = shipToAddressLine1;
	}
	public void setShipToAddressLine2(String shipToAddressLine2) {
		this.shipToAddressLine2 = shipToAddressLine2;
	}
	public void setShipToAddressLine3(String shipToAddressLine3) {
		this.shipToAddressLine3 = shipToAddressLine3;
	}
	public void setShipToAddressLine4(String shipToAddressLine4) {
		this.shipToAddressLine4 = shipToAddressLine4;
	}
	public void setShipToAddressLine5(String shipToAddressLine5) {
		this.shipToAddressLine5 = shipToAddressLine5;
	}
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	public void setRequestorPhone(String requestorPhone) {
		this.requestorPhone = requestorPhone;
	}
	public void setRequestorFax(String requestorFax) {
		this.requestorFax = requestorFax;
	}
	public void setRequestorEmail(String requestorEmail) {
		this.requestorEmail = requestorEmail;
	}
	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}
	public void setIncoTerms(String incoTerms) {
		this.incoTerms = incoTerms;
	}
	public void setCarrierServiceType(String carrierServiceType) {
		this.carrierServiceType = carrierServiceType;
	}
	public void setSubmittedByName(String submittedByName) {
		this.submittedByName = submittedByName;
	}
	public void setLastLineItem(BigDecimal lastLineItem) {
		this.lastLineItem = lastLineItem;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setTotalGoods(BigDecimal totalGoods) {
		this.totalGoods = totalGoods;
	}
	public void setTotalLineCharges(BigDecimal totalLineCharges) {
		this.totalLineCharges = totalLineCharges;
	}
	public void setTotalHeaderCharges(BigDecimal totalHeaderCharges) {
		this.totalHeaderCharges = totalHeaderCharges;
	}
	public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
		this.totalTaxAmount = totalTaxAmount;
	}
	public void setRemittanceInstructionLine1(String remittanceInstructionLine1) {
		this.remittanceInstructionLine1 = remittanceInstructionLine1;
	}
	public void setRemittanceInstructionLine2(String remittanceInstructionLine2) {
		this.remittanceInstructionLine2 = remittanceInstructionLine2;
	}
	public void setRemittanceInstructionLine3(String remittanceInstructionLine3) {
		this.remittanceInstructionLine3 = remittanceInstructionLine3;
	}
	public void setRemittanceInstructionLine4(String remittanceInstructionLine4) {
		this.remittanceInstructionLine4 = remittanceInstructionLine4;
	}
	public void setRemittanceInstructionLine5(String remittanceInstructionLine5) {
		this.remittanceInstructionLine5 = remittanceInstructionLine5;
	}
	public void setRemittanceInstructionLine6(String remittanceInstructionLine6) {
		this.remittanceInstructionLine6 = remittanceInstructionLine6;
	}
	public void setRemittanceInstructionLine7(String remittanceInstructionLine7) {
		this.remittanceInstructionLine7 = remittanceInstructionLine7;
	}
	public void setRemittanceInstructionLine8(String remittanceInstructionLine8) {
		this.remittanceInstructionLine8 = remittanceInstructionLine8;
	}
	public void setRemittanceInstructionLine9(String remittanceInstructionLine9) {
		this.remittanceInstructionLine9 = remittanceInstructionLine9;
	}
	public void setRemittanceInstructionLine10(String remittanceInstructionLine10) {
		this.remittanceInstructionLine10 = remittanceInstructionLine10;
	}
	public void setHomeCurrencyId(String homeCurrencyId) {
		this.homeCurrencyId = homeCurrencyId;
	}
	public void setHomeExchangeRate(BigDecimal homeExchangeRate) {
		this.homeExchangeRate = homeExchangeRate;
	}
	public void setCsrName(String csrName) {
		this.csrName = csrName;
	}
	public void setCsrEmail(String csrEmail) {
		this.csrEmail = csrEmail;
	}


	//getters
	public BigDecimal getInvoice() {
		return invoice;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public Date getPaymentDueDate() {
		return paymentDueDate;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getOperatingEntityName() {
		return operatingEntityName;
	}
	public String getOpsEntityLogoUrl() {
		return opsEntityLogoUrl;
	}
	public String getOpsEntityWebsiteUrl() {
		return opsEntityWebsiteUrl;
	}
	public String getOpsEntityTermsUrl() {
		return opsEntityTermsUrl;
	}
	public String getOpsEntityAddressLine1() {
		return opsEntityAddressLine1;
	}
	public String getOpsEntityAddressLine2() {
		return opsEntityAddressLine2;
	}
	public String getOpsEntityAddressLine3() {
		return opsEntityAddressLine3;
	}
	public String getOpsEntityAddressLine4() {
		return opsEntityAddressLine4;
	}
	public String getOpsEntityAddressLine5() {
		return opsEntityAddressLine5;
	}
	public String getOpsEntityAttention() {
		return opsEntityAttention;
	}
	public String getOpsEntityPhone() {
		return opsEntityPhone;
	}
	public String getOpsEntityFax() {
		return opsEntityFax;
	}
	public String getOpsEntityEmail() {
		return opsEntityEmail;
	}
	public String getOpsEntityTaxType() {
		return opsEntityTaxType;
	}
	public String getOpsEntityTaxId() {
		return opsEntityTaxId;
	}
	public String getOpsEntityTaxType2() {
		return opsEntityTaxType2;
	}
	public String getOpsEntityTaxId2() {
		return opsEntityTaxId2;
	}
	public String getOpsEntityTaxType3() {
		return opsEntityTaxType3;
	}
	public String getOpsEntityTaxId3() {
		return opsEntityTaxId3;
	}
	public String getOpsEntityTaxType4() {
		return opsEntityTaxType4;
	}
	public String getOpsEntityTaxId4() {
		return opsEntityTaxId4;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public String getBillToName() {
		return billToName;
	}
	public String getBillToAddressLine1() {
		return billToAddressLine1;
	}
	public String getBillToAddressLine2() {
		return billToAddressLine2;
	}
	public String getBillToAddressLine3() {
		return billToAddressLine3;
	}
	public String getBillToAddressLine4() {
		return billToAddressLine4;
	}
	public String getBillToAddressLine5() {
		return billToAddressLine5;
	}
	public String getTaxRegistrationType() {
		return taxRegistrationType;
	}
	public String getTaxRegistrationNumber() {
		return taxRegistrationNumber;
	}
	public String getShipToName() {
		return shipToName;
	}
	public String getShipToDescription() {
		return shipToDescription;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public String getShipToAddressLine1() {
		return shipToAddressLine1;
	}
	public String getShipToAddressLine2() {
		return shipToAddressLine2;
	}
	public String getShipToAddressLine3() {
		return shipToAddressLine3;
	}
	public String getShipToAddressLine4() {
		return shipToAddressLine4;
	}
	public String getShipToAddressLine5() {
		return shipToAddressLine5;
	}
	public String getRequestorName() {
		return requestorName;
	}
	public String getRequestorPhone() {
		return requestorPhone;
	}
	public String getRequestorFax() {
		return requestorFax;
	}
	public String getRequestorEmail() {
		return requestorEmail;
	}
	public Date getSubmittedDate() {
		return submittedDate;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public String getEndUser() {
		return endUser;
	}
	public String getIncoTerms() {
		return incoTerms;
	}
	public String getCarrierServiceType() {
		return carrierServiceType;
	}
	public String getSubmittedByName() {
		return submittedByName;
	}
	public BigDecimal getLastLineItem() {
		return lastLineItem;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public BigDecimal getTotalGoods() {
		return totalGoods;
	}
	public BigDecimal getTotalLineCharges() {
		return totalLineCharges;
	}
	public BigDecimal getTotalHeaderCharges() {
		return totalHeaderCharges;
	}
	public BigDecimal getTotalTaxAmount() {
		return totalTaxAmount;
	}
	public String getRemittanceInstructionLine1() {
		return remittanceInstructionLine1;
	}
	public String getRemittanceInstructionLine2() {
		return remittanceInstructionLine2;
	}
	public String getRemittanceInstructionLine3() {
		return remittanceInstructionLine3;
	}
	public String getRemittanceInstructionLine4() {
		return remittanceInstructionLine4;
	}
	public String getRemittanceInstructionLine5() {
		return remittanceInstructionLine5;
	}
	public String getRemittanceInstructionLine6() {
		return remittanceInstructionLine6;
	}
	public String getRemittanceInstructionLine7() {
		return remittanceInstructionLine7;
	}
	public String getRemittanceInstructionLine8() {
		return remittanceInstructionLine8;
	}
	public String getRemittanceInstructionLine9() {
		return remittanceInstructionLine9;
	}
	public String getRemittanceInstructionLine10() {
		return remittanceInstructionLine10;
	}
	public String getHomeCurrencyId() {
		return homeCurrencyId;
	}
	public BigDecimal getHomeExchangeRate() {
		return homeExchangeRate;
	}
	public String getCsrName() {
		return csrName;
	}
	public String getCsrEmail() {
		return csrEmail;
	}
	public BigDecimal getCorrectedInvoice() {
		return correctedInvoice;
	}
	public void setCorrectedInvoice(BigDecimal correctedInvoice) {
		this.correctedInvoice = correctedInvoice;
	}

	public String getCustomerInvoice() {
		return customerInvoice;
	}

	public void setCustomerInvoice(String customerInvoice) {
		this.customerInvoice = customerInvoice;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	

}