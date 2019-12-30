package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CostReportAddChargeViewBean <br>
 * @version: 1.0, Jul 18, 2008 <br>
 *****************************************************************************/


public class CostReportAddChargeViewBean extends BaseDataBean {

	private String companyId;
	private BigDecimal invoice;
	private String commodity;
	private String facilityId;
	private String facilityDisplay;
	private String accountSysId;
	private Date invoiceDate;
	private BigDecimal invoicePeriod;
	private String invoicePeriodDesc;
	private BigDecimal invoiceLine;
	private String accountNumber;
	private String accountNumber2;
	private String poNumber;
	private String chargeType;
	private BigDecimal issueId;
	private String unitRebate;
	private BigDecimal quantity;
	private BigDecimal totalAddCharge;
	private BigDecimal totalSalesTax;
	private BigDecimal serviceFee;
	private String percentSplitCharge;
	private BigDecimal itemId;
	private Date dateDelivered;
	private String catPartNo;
	private String partDescription;
	private String itemType;
	private BigDecimal prNumber;
	private String lineItem;
	private String mrLine;
	private String application;
	private String applicationDesc;
	private String requestorName;
	private BigDecimal wasteOrderNo;
	private String wasteManifestId;
	private String mfgLot;
	private BigDecimal peiAmount;
	private BigDecimal netAmount;
	private String currencyId;
	private String invoiceUnitPrice;
	private String itemDesc;
	private BigDecimal categoryId;
	private String mfgId;
	private String mfgDesc;
	private BigDecimal receiptId;
	private BigDecimal radianPo;
	private String sourceHub;
	private String hubName;
	private String categoryDesc;
	private BigDecimal requestor;
	private String packaging;
	private String typeDesc;
	private String additionalChargeDesc;
	private BigDecimal additionalChargeAmount;
	private String referenceNumber;
	private String financeApproverName;


	//constructor
	public CostReportAddChargeViewBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setInvoice(BigDecimal invoice) {
		this.invoice = invoice;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setFacilityDisplay(String facilityDisplay) {
		this.facilityDisplay = facilityDisplay;
	}
	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public void setInvoicePeriod(BigDecimal invoicePeriod) {
		this.invoicePeriod = invoicePeriod;
	}
	public void setInvoicePeriodDesc(String invoicePeriodDesc) {
		this.invoicePeriodDesc = invoicePeriodDesc;
	}
	public void setInvoiceLine(BigDecimal invoiceLine) {
		this.invoiceLine = invoiceLine;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public void setAccountNumber2(String accountNumber2) {
		this.accountNumber2 = accountNumber2;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public void setIssueId(BigDecimal issueId) {
		this.issueId = issueId;
	}
	public void setUnitRebate(String unitRebate) {
		this.unitRebate = unitRebate;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setTotalAddCharge(BigDecimal totalAddCharge) {
		this.totalAddCharge = totalAddCharge;
	}
	public void setTotalSalesTax(BigDecimal totalSalesTax) {
		this.totalSalesTax = totalSalesTax;
	}
	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}
	public void setPercentSplitCharge(String percentSplitCharge) {
		this.percentSplitCharge = percentSplitCharge;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setMrLine(String mrLine) {
		this.mrLine = mrLine;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	public void setWasteOrderNo(BigDecimal wasteOrderNo) {
		this.wasteOrderNo = wasteOrderNo;
	}
	public void setWasteManifestId(String wasteManifestId) {
		this.wasteManifestId = wasteManifestId;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setPeiAmount(BigDecimal peiAmount) {
		this.peiAmount = peiAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setInvoiceUnitPrice(String invoiceUnitPrice) {
		this.invoiceUnitPrice = invoiceUnitPrice;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setCategoryId(BigDecimal categoryId) {
		this.categoryId = categoryId;
	}
	public void setMfgId(String mfgId) {
		this.mfgId = mfgId;
	}
	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setSourceHub(String sourceHub) {
		this.sourceHub = sourceHub;
	}
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
	public void setRequestor(BigDecimal requestor) {
		this.requestor = requestor;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	public void setAdditionalChargeDesc(String additionalChargeDesc) {
		this.additionalChargeDesc = additionalChargeDesc;
	}
	public void setAdditionalChargeAmount(BigDecimal additionalChargeAmount) {
		this.additionalChargeAmount = additionalChargeAmount;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public void setFinanceApproverName(String financeApproverName) {
		this.financeApproverName = financeApproverName;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getInvoice() {
		return invoice;
	}
	public String getCommodity() {
		return commodity;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getFacilityDisplay() {
		return facilityDisplay;
	}
	public String getAccountSysId() {
		return accountSysId;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public BigDecimal getInvoicePeriod() {
		return invoicePeriod;
	}
	public String getInvoicePeriodDesc() {
		return invoicePeriodDesc;
	}
	public BigDecimal getInvoiceLine() {
		return invoiceLine;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public String getAccountNumber2() {
		return accountNumber2;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public String getChargeType() {
		return chargeType;
	}
	public BigDecimal getIssueId() {
		return issueId;
	}
	public String getUnitRebate() {
		return unitRebate;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public BigDecimal getTotalAddCharge() {
		return totalAddCharge;
	}
	public BigDecimal getTotalSalesTax() {
		return totalSalesTax;
	}
	public BigDecimal getServiceFee() {
		return serviceFee;
	}
	public String getPercentSplitCharge() {
		return percentSplitCharge;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public Date getDateDelivered() {
		return dateDelivered;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public String getItemType() {
		return itemType;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getMrLine() {
		return mrLine;
	}
	public String getApplication() {
		return application;
	}
	public String getApplicationDesc() {
		return applicationDesc;
	}
	public String getRequestorName() {
		return requestorName;
	}
	public BigDecimal getWasteOrderNo() {
		return wasteOrderNo;
	}
	public String getWasteManifestId() {
		return wasteManifestId;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public BigDecimal getPeiAmount() {
		return peiAmount;
	}
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public String getInvoiceUnitPrice() {
		return invoiceUnitPrice;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public BigDecimal getCategoryId() {
		return categoryId;
	}
	public String getMfgId() {
		return mfgId;
	}
	public String getMfgDesc() {
		return mfgDesc;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public String getSourceHub() {
		return sourceHub;
	}
	public String getHubName() {
		return hubName;
	}
	public String getCategoryDesc() {
		return categoryDesc;
	}
	public BigDecimal getRequestor() {
		return requestor;
	}
	public String getPackaging() {
		return packaging;
	}
	public String getTypeDesc() {
		return typeDesc;
	}
	public String getAdditionalChargeDesc() {
		return additionalChargeDesc;
	}
	public BigDecimal getAdditionalChargeAmount() {
		return additionalChargeAmount;
	}
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public String getFinanceApproverName() {
		return financeApproverName;
	}
}