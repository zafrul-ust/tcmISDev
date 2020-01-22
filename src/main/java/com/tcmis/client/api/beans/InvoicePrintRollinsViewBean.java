package com.tcmis.client.api.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class InvoicePrintRollinsViewBean extends BaseDataBean {
	
	private String billToAddressLine1;
	private String billToAddressLine2;
	private String billToAddressLine3;
	private String billToAddressLine4;
	private String billToAddressLine5;
	private String billToCity;
	private String billToCountry;
	private String billToName;
	private String billToState;
	private String billToZip;
	private String currencyId;
	private BigDecimal customerId;
	private String homeCurrencyId;
	private BigDecimal homeExchangeRate;
	private BigDecimal invoice;
	private String invoiceId;
	private BigDecimal invoiceAmount;
	private Date invoiceDate;
	private String invoiceGroup;
	private BigDecimal invoicePeriod;
	private String invoicePurpose;
	private String operatingEntityName;
	private String opsEntityAddressLine1;
	private String opsEntityAddressLine2;
	private String opsEntityAddressLine3;
	private String opsEntityAddressLine4;
	private String opsEntityAddressLine5;
	private String opsEntityAttention;
	private String opsEntityCity;
	private String opsEntityCountry;
	private String opsEntityEmail;
	private String opsEntityFax;
	private String opsEntityId;
	private String opsEntityLogoUrl;
	private String opsEntityPhone;
	private String opsEntityState;
	private String opsEntityTaxAuthCurrency;
	private BigDecimal opsEntityTaxExchRate;
	private String opsEntityTaxRegistration;
	private String opsEntityTermsUrl;
	private String opsEntityWebsiteUrl;
	private String opsEntityZip;
	private Date paymentDueDate;
	private String paymentTerms;
	private String periodEndDate;
	private String periodStartDate;
	private String poNumber;
	private String remittanceInstructionLine1;
	private String remittanceInstructionLine10;
	private String remittanceInstructionLine2;
	private String remittanceInstructionLine3;
	private String remittanceInstructionLine4;
	private String remittanceInstructionLine5;
	private String remittanceInstructionLine6;
	private String remittanceInstructionLine7;
	private String remittanceInstructionLine8;
	private String remittanceInstructionLine9;
	private String shipFromAddressLine1;
	private String shipFromAddressLine2;
	private String shipFromAddressLine3;
	private String shipFromAddressLine4;
	private String shipFromAddressLine5;
	private String shipFromCity;
	private String shipFromCountry;
	private String shipFromDescription;
	private String shipFromName;
	private String shipFromState;
	private String shipFromZip;
	private String shipToAddressLine1;
	private String shipToAddressLine2;
	private String shipToAddressLine3;
	private String shipToAddressLine4;
	private String shipToAddressLine5;
	private String shipToCity;
	private String shipToCountry;
	private String shipToDescription;
	private String shipToName;
	private String shipToState;
	private String shipToZip;
	private String taxCategory;
	private String taxCurrency_id;
	private String taxDescription;
	private String taxDescription2;
	private BigDecimal taxExchangeRate;
	private String taxJurisdictionText;
	private String taxPurpose;
	private String taxRegistrationNumber;
	private String taxRegistrationType;
	private String taxType;
	private BigDecimal totalAddCharges;
	private BigDecimal totalMaterialAmount;
	private BigDecimal totalServiceFee;
	private BigDecimal totalTaxAmount;
	
	private Collection<InvoicePrtRollinsLineViewBean> invoiceLines;
	
	// constructor
	public InvoicePrintRollinsViewBean() {
		super();
	}

	public String getBillToAddressLine1() {
		return this.billToAddressLine1;
	}

	public void setBillToAddressLine1(String billToAddressLine1) {
		this.billToAddressLine1 = billToAddressLine1;
	}

	public String getBillToAddressLine2() {
		return this.billToAddressLine2;
	}

	public void setBillToAddressLine2(String billToAddressLine2) {
		this.billToAddressLine2 = billToAddressLine2;
	}

	public String getBillToAddressLine3() {
		return this.billToAddressLine3;
	}

	public void setBillToAddressLine3(String billToAddressLine3) {
		this.billToAddressLine3 = billToAddressLine3;
	}

	public String getBillToAddressLine4() {
		return this.billToAddressLine4;
	}

	public void setBillToAddressLine4(String billToAddressLine4) {
		this.billToAddressLine4 = billToAddressLine4;
	}

	public String getBillToAddressLine5() {
		return this.billToAddressLine5;
	}

	public void setBillToAddressLine5(String billToAddressLine5) {
		this.billToAddressLine5 = billToAddressLine5;
	}

	public String getBillToCity() {
		return this.billToCity;
	}

	public void setBillToCity(String billToCity) {
		this.billToCity = billToCity;
	}

	public String getBillToCountry() {
		return this.billToCountry;
	}

	public void setBillToCountry(String billToCountry) {
		this.billToCountry = billToCountry;
	}

	public String getBillToName() {
		return this.billToName;
	}

	public void setBillToName(String billToName) {
		this.billToName = billToName;
	}

	public String getBillToState() {
		return this.billToState;
	}

	public void setBillToState(String billToState) {
		this.billToState = billToState;
	}

	public String getBillToZip() {
		return this.billToZip;
	}

	public void setBillToZip(String billToZip) {
		this.billToZip = billToZip;
	}

	public String getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public String getHomeCurrencyId() {
		return this.homeCurrencyId;
	}

	public void setHomeCurrencyId(String homeCurrencyId) {
		this.homeCurrencyId = homeCurrencyId;
	}

	public BigDecimal getHomeExchangeRate() {
		return this.homeExchangeRate;
	}

	public void setHomeExchangeRate(BigDecimal homeExchangeRate) {
		this.homeExchangeRate = homeExchangeRate;
	}

	public BigDecimal getInvoice() {
		return this.invoice;
	}

	public void setInvoice(BigDecimal invoice) {
		this.invoice = invoice;
	}

	public String getInvoiceId() {
		return this.invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public BigDecimal getInvoiceAmount() {
		return this.invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Date getInvoiceDate() {
		return this.invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceGroup() {
		return this.invoiceGroup;
	}

	public void setInvoiceGroup(String invoiceGroup) {
		this.invoiceGroup = invoiceGroup;
	}

	public BigDecimal getInvoicePeriod() {
		return this.invoicePeriod;
	}

	public void setInvoicePeriod(BigDecimal invoicePeriod) {
		this.invoicePeriod = invoicePeriod;
	}

	public String getInvoicePurpose() {
		return this.invoicePurpose;
	}

	public void setInvoicePurpose(String invoicePurpose) {
		this.invoicePurpose = invoicePurpose;
	}

	public String getOperatingEntityName() {
		return this.operatingEntityName;
	}

	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}

	public String getOpsEntityAddressLine1() {
		return this.opsEntityAddressLine1;
	}

	public void setOpsEntityAddressLine1(String opsEntityAddressLine1) {
		this.opsEntityAddressLine1 = opsEntityAddressLine1;
	}

	public String getOpsEntityAddressLine2() {
		return this.opsEntityAddressLine2;
	}

	public void setOpsEntityAddressLine2(String opsEntityAddressLine2) {
		this.opsEntityAddressLine2 = opsEntityAddressLine2;
	}

	public String getOpsEntityAddressLine3() {
		return this.opsEntityAddressLine3;
	}

	public void setOpsEntityAddressLine3(String opsEntityAddressLine3) {
		this.opsEntityAddressLine3 = opsEntityAddressLine3;
	}

	public String getOpsEntityAddressLine4() {
		return this.opsEntityAddressLine4;
	}

	public void setOpsEntityAddressLine4(String opsEntityAddressLine4) {
		this.opsEntityAddressLine4 = opsEntityAddressLine4;
	}

	public String getOpsEntityAddressLine5() {
		return this.opsEntityAddressLine5;
	}

	public void setOpsEntityAddressLine5(String opsEntityAddressLine5) {
		this.opsEntityAddressLine5 = opsEntityAddressLine5;
	}

	public String getOpsEntityAttention() {
		return this.opsEntityAttention;
	}

	public void setOpsEntityAttention(String opsEntityAttention) {
		this.opsEntityAttention = opsEntityAttention;
	}

	public String getOpsEntityCity() {
		return this.opsEntityCity;
	}

	public void setOpsEntityCity(String opsEntityCity) {
		this.opsEntityCity = opsEntityCity;
	}

	public String getOpsEntityCountry() {
		return this.opsEntityCountry;
	}

	public void setOpsEntityCountry(String opsEntityCountry) {
		this.opsEntityCountry = opsEntityCountry;
	}

	public String getOpsEntityEmail() {
		return this.opsEntityEmail;
	}

	public void setOpsEntityEmail(String opsEntityEmail) {
		this.opsEntityEmail = opsEntityEmail;
	}

	public String getOpsEntityFax() {
		return this.opsEntityFax;
	}

	public void setOpsEntityFax(String opsEntityFax) {
		this.opsEntityFax = opsEntityFax;
	}

	public String getOpsEntityId() {
		return this.opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public String getOpsEntityLogoUrl() {
		return this.opsEntityLogoUrl;
	}

	public void setOpsEntityLogoUrl(String opsEntityLogoUrl) {
		this.opsEntityLogoUrl = opsEntityLogoUrl;
	}

	public String getOpsEntityPhone() {
		return this.opsEntityPhone;
	}

	public void setOpsEntityPhone(String opsEntityPhone) {
		this.opsEntityPhone = opsEntityPhone;
	}

	public String getOpsEntityState() {
		return this.opsEntityState;
	}

	public void setOpsEntityState(String opsEntityState) {
		this.opsEntityState = opsEntityState;
	}

	public String getOpsEntityTaxAuthCurrency() {
		return this.opsEntityTaxAuthCurrency;
	}

	public void setOpsEntityTaxAuthCurrency(String opsEntityTaxAuthCurrency) {
		this.opsEntityTaxAuthCurrency = opsEntityTaxAuthCurrency;
	}

	public BigDecimal getOpsEntityTaxExchRate() {
		return this.opsEntityTaxExchRate;
	}

	public void setOpsEntityTaxExchRate(BigDecimal opsEntityTaxExchRate) {
		this.opsEntityTaxExchRate = opsEntityTaxExchRate;
	}

	public String getOpsEntityTaxRegistration() {
		return this.opsEntityTaxRegistration;
	}

	public void setOpsEntityTaxRegistration(String opsEntityTaxRegistration) {
		this.opsEntityTaxRegistration = opsEntityTaxRegistration;
	}

	public String getOpsEntityTermsUrl() {
		return this.opsEntityTermsUrl;
	}

	public void setOpsEntityTermsUrl(String opsEntityTermsUrl) {
		this.opsEntityTermsUrl = opsEntityTermsUrl;
	}

	public String getOpsEntityWebsiteUrl() {
		return this.opsEntityWebsiteUrl;
	}

	public void setOpsEntityWebsiteUrl(String opsEntityWebsiteUrl) {
		this.opsEntityWebsiteUrl = opsEntityWebsiteUrl;
	}

	public String getOpsEntityZip() {
		return this.opsEntityZip;
	}

	public void setOpsEntityZip(String opsEntityZip) {
		this.opsEntityZip = opsEntityZip;
	}

	public Date getPaymentDueDate() {
		return this.paymentDueDate;
	}

	public void setPaymentDueDate(Date paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}

	public String getPaymentTerms() {
		return this.paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getPeriodEndDate() {
		return this.periodEndDate;
	}

	public void setPeriodEndDate(String periodEndDate) {
		this.periodEndDate = periodEndDate;
	}

	public String getPeriodStartDate() {
		return this.periodStartDate;
	}

	public void setPeriodStartDate(String periodStartDate) {
		this.periodStartDate = periodStartDate;
	}

	public String getPoNumber() {
		return this.poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getRemittanceInstructionLine1() {
		return this.remittanceInstructionLine1;
	}

	public void setRemittanceInstructionLine1(String remittanceInstructionLine1) {
		this.remittanceInstructionLine1 = remittanceInstructionLine1;
	}

	public String getRemittanceInstructionLine10() {
		return this.remittanceInstructionLine10;
	}

	public void setRemittanceInstructionLine10(String remittanceInstructionLine10) {
		this.remittanceInstructionLine10 = remittanceInstructionLine10;
	}

	public String getRemittanceInstructionLine2() {
		return this.remittanceInstructionLine2;
	}

	public void setRemittanceInstructionLine2(String remittanceInstructionLine2) {
		this.remittanceInstructionLine2 = remittanceInstructionLine2;
	}

	public String getRemittanceInstructionLine3() {
		return this.remittanceInstructionLine3;
	}

	public void setRemittanceInstructionLine3(String remittanceInstructionLine3) {
		this.remittanceInstructionLine3 = remittanceInstructionLine3;
	}

	public String getRemittanceInstructionLine4() {
		return this.remittanceInstructionLine4;
	}

	public void setRemittanceInstructionLine4(String remittanceInstructionLine4) {
		this.remittanceInstructionLine4 = remittanceInstructionLine4;
	}

	public String getRemittanceInstructionLine5() {
		return this.remittanceInstructionLine5;
	}

	public void setRemittanceInstructionLine5(String remittanceInstructionLine5) {
		this.remittanceInstructionLine5 = remittanceInstructionLine5;
	}

	public String getRemittanceInstructionLine6() {
		return this.remittanceInstructionLine6;
	}

	public void setRemittanceInstructionLine6(String remittanceInstructionLine6) {
		this.remittanceInstructionLine6 = remittanceInstructionLine6;
	}

	public String getRemittanceInstructionLine7() {
		return this.remittanceInstructionLine7;
	}

	public void setRemittanceInstructionLine7(String remittanceInstructionLine7) {
		this.remittanceInstructionLine7 = remittanceInstructionLine7;
	}

	public String getRemittanceInstructionLine8() {
		return this.remittanceInstructionLine8;
	}

	public void setRemittanceInstructionLine8(String remittanceInstructionLine8) {
		this.remittanceInstructionLine8 = remittanceInstructionLine8;
	}

	public String getRemittanceInstructionLine9() {
		return this.remittanceInstructionLine9;
	}

	public void setRemittanceInstructionLine9(String remittanceInstructionLine9) {
		this.remittanceInstructionLine9 = remittanceInstructionLine9;
	}

	public String getShipFromAddressLine1() {
		return this.shipFromAddressLine1;
	}

	public void setShipFromAddressLine1(String shipFromAddressLine1) {
		this.shipFromAddressLine1 = shipFromAddressLine1;
	}

	public String getShipFromAddressLine2() {
		return this.shipFromAddressLine2;
	}

	public void setShipFromAddressLine2(String shipFromAddressLine2) {
		this.shipFromAddressLine2 = shipFromAddressLine2;
	}

	public String getShipFromAddressLine3() {
		return this.shipFromAddressLine3;
	}

	public void setShipFromAddressLine3(String shipFromAddressLine3) {
		this.shipFromAddressLine3 = shipFromAddressLine3;
	}

	public String getShipFromAddressLine4() {
		return this.shipFromAddressLine4;
	}

	public void setShipFromAddressLine4(String shipFromAddressLine4) {
		this.shipFromAddressLine4 = shipFromAddressLine4;
	}

	public String getShipFromAddressLine5() {
		return this.shipFromAddressLine5;
	}

	public void setShipFromAddressLine5(String shipFromAddressLine5) {
		this.shipFromAddressLine5 = shipFromAddressLine5;
	}

	public String getShipFromCity() {
		return this.shipFromCity;
	}

	public void setShipFromCity(String shipFromCity) {
		this.shipFromCity = shipFromCity;
	}

	public String getShipFromCountry() {
		return this.shipFromCountry;
	}

	public void setShipFromCountry(String shipFromCountry) {
		this.shipFromCountry = shipFromCountry;
	}

	public String getShipFromDescription() {
		return this.shipFromDescription;
	}

	public void setShipFromDescription(String shipFromDescription) {
		this.shipFromDescription = shipFromDescription;
	}

	public String getShipFromName() {
		return this.shipFromName;
	}

	public void setShipFromName(String shipFromName) {
		this.shipFromName = shipFromName;
	}

	public String getShipFromState() {
		return this.shipFromState;
	}

	public void setShipFromState(String shipFromState) {
		this.shipFromState = shipFromState;
	}

	public String getShipFromZip() {
		return this.shipFromZip;
	}

	public void setShipFromZip(String shipFromZip) {
		this.shipFromZip = shipFromZip;
	}

	public String getShipToAddressLine1() {
		return this.shipToAddressLine1;
	}

	public void setShipToAddressLine1(String shipToAddressLine1) {
		this.shipToAddressLine1 = shipToAddressLine1;
	}

	public String getShipToAddressLine2() {
		return this.shipToAddressLine2;
	}

	public void setShipToAddressLine2(String shipToAddressLine2) {
		this.shipToAddressLine2 = shipToAddressLine2;
	}

	public String getShipToAddressLine3() {
		return this.shipToAddressLine3;
	}

	public void setShipToAddressLine3(String shipToAddressLine3) {
		this.shipToAddressLine3 = shipToAddressLine3;
	}

	public String getShipToAddressLine4() {
		return this.shipToAddressLine4;
	}

	public void setShipToAddressLine4(String shipToAddressLine4) {
		this.shipToAddressLine4 = shipToAddressLine4;
	}

	public String getShipToAddressLine5() {
		return this.shipToAddressLine5;
	}

	public void setShipToAddressLine5(String shipToAddressLine5) {
		this.shipToAddressLine5 = shipToAddressLine5;
	}

	public String getShipToCity() {
		return this.shipToCity;
	}

	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}

	public String getShipToCountry() {
		return this.shipToCountry;
	}

	public void setShipToCountry(String shipToCountry) {
		this.shipToCountry = shipToCountry;
	}

	public String getShipToDescription() {
		return this.shipToDescription;
	}

	public void setShipToDescription(String shipToDescription) {
		this.shipToDescription = shipToDescription;
	}

	public String getShipToName() {
		return this.shipToName;
	}

	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}

	public String getShipToState() {
		return this.shipToState;
	}

	public void setShipToState(String shipToState) {
		this.shipToState = shipToState;
	}

	public String getShipToZip() {
		return this.shipToZip;
	}

	public void setShipToZip(String shipToZip) {
		this.shipToZip = shipToZip;
	}

	public String getTaxCategory() {
		return this.taxCategory;
	}

	public void setTaxCategory(String taxCategory) {
		this.taxCategory = taxCategory;
	}

	public String getTaxCurrency_id() {
		return this.taxCurrency_id;
	}

	public void setTaxCurrency_id(String taxCurrency_id) {
		this.taxCurrency_id = taxCurrency_id;
	}

	public String getTaxDescription() {
		return this.taxDescription;
	}

	public void setTaxDescription(String taxDescription) {
		this.taxDescription = taxDescription;
	}

	public String getTaxDescription2() {
		return this.taxDescription2;
	}

	public void setTaxDescription2(String taxDescription2) {
		this.taxDescription2 = taxDescription2;
	}

	public BigDecimal getTaxExchangeRate() {
		return this.taxExchangeRate;
	}

	public void setTaxExchangeRate(BigDecimal taxExchangeRate) {
		this.taxExchangeRate = taxExchangeRate;
	}

	public String getTaxJurisdictionText() {
		return this.taxJurisdictionText;
	}

	public void setTaxJurisdictionText(String taxJurisdictionText) {
		this.taxJurisdictionText = taxJurisdictionText;
	}

	public String getTaxPurpose() {
		return this.taxPurpose;
	}

	public void setTaxPurpose(String taxPurpose) {
		this.taxPurpose = taxPurpose;
	}

	public String getTaxRegistrationNumber() {
		return this.taxRegistrationNumber;
	}

	public void setTaxRegistrationNumber(String taxRegistrationNumber) {
		this.taxRegistrationNumber = taxRegistrationNumber;
	}

	public String getTaxRegistrationType() {
		return this.taxRegistrationType;
	}

	public void setTaxRegistrationType(String taxRegistrationType) {
		this.taxRegistrationType = taxRegistrationType;
	}

	public String getTaxType() {
		return this.taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public BigDecimal getTotalAddCharges() {
		return this.totalAddCharges;
	}

	public void setTotalAddCharges(BigDecimal totalAddCharges) {
		this.totalAddCharges = totalAddCharges;
	}

	public BigDecimal getTotalMaterialAmount() {
		return this.totalMaterialAmount;
	}

	public void setTotalMaterialAmount(BigDecimal totalMaterialAmount) {
		this.totalMaterialAmount = totalMaterialAmount;
	}

	public BigDecimal getTotalServiceFee() {
		return this.totalServiceFee;
	}

	public void setTotalServiceFee(BigDecimal totalServiceFee) {
		this.totalServiceFee = totalServiceFee;
	}

	public BigDecimal getTotalTaxAmount() {
		return this.totalTaxAmount;
	}

	public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
		this.totalTaxAmount = totalTaxAmount;
	}

	public Collection<InvoicePrtRollinsLineViewBean> getInvoiceLines() {
		return this.invoiceLines;
	}

	public void setInvoiceLines(Collection<InvoicePrtRollinsLineViewBean> invoiceLines) {
		this.invoiceLines = invoiceLines;
	}
}
