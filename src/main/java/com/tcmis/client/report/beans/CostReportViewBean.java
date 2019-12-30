package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CostReportViewBean <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class CostReportViewBean extends BaseDataBean {

	private String companyId;
	private BigDecimal invoice;
	private String customerInvoiceNo;
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
	private BigDecimal unitRebate;
	private BigDecimal quantity;
	private BigDecimal totalAddCharge;
	private BigDecimal totalSalesTax;
	private BigDecimal serviceFee;
	private BigDecimal percentSplitCharge;
	private BigDecimal itemId;
	private Date dateDelivered;
	private String catPartNo;
	private String partDescription;
	private String itemType;
	private BigDecimal prNumber;
	private String lineItem;
	private String mrLine;
	private BigDecimal wasteOrderNo;
	private String wasteManifestId;
	private String application;
	private String applicationDesc;
	private String requestorName;
	private String mfgLot;
	private BigDecimal peiAmount;
	private BigDecimal netAmount;
	private String currencyId;
	private BigDecimal invoiceUnitPrice;
	private String itemDesc;
	private BigDecimal categoryId;
	private String mfgId;
	private String mfgDesc;
	private String receiptId;
	private BigDecimal radianPo;
	private String sourceHub;
	private String hubName;
	private String categoryDesc;
	private BigDecimal requestor;
	private String packaging;
	private String typeDesc;
	private String referenceNumber;
	private String financeApproverName;
	private BigDecimal totalRebate;

	private String accountSysDesc;
	private String companyName;
	private String voucherUrl;

	private Date startDate;
	private Date endDate;
	private String unitOfSale;
	private BigDecimal unitOfSaleQuantityPerEach;
	private String itemUom;
	private BigDecimal totalFreightCharge;
	private String accountNumber3;
	private String accountNumber4;
	
	private String customerPartNo;
	private String shippingReference;

	private String		qualityId;
	private String		catPartAttribute;

	public String getQualityId() {
		return qualityId;
	}

	public void setQualityId(String qualityId) {
		this.qualityId = qualityId;
	}

	public String getCatPartAttribute() {
		return catPartAttribute;
	}

	public void setCatPartAttribute(String catPartAttribute) {
		this.catPartAttribute = catPartAttribute;
	}

	
	//constructor
	public CostReportViewBean() {
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
	public void setUnitRebate(BigDecimal unitRebate) {
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
	public void setPercentSplitCharge(BigDecimal percentSplitCharge) {
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
	public void setWasteOrderNo(BigDecimal wasteOrderNo) {
		this.wasteOrderNo = wasteOrderNo;
	}
	public void setWasteManifestId(String wasteManifestId) {
		this.wasteManifestId = wasteManifestId;
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
	public void setInvoiceUnitPrice(BigDecimal invoiceUnitPrice) {
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

    public void setReceiptId(String receiptId) {
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
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public void setFinanceApproverName(String financeApproverName) {
		this.financeApproverName = financeApproverName;
	}
	public void setTotalRebate(BigDecimal totalRebate) {
		this.totalRebate = totalRebate;
	}

	public void setAccountSysDesc(String accountSysDesc) {
		this.accountSysDesc = accountSysDesc;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setVoucherUrl(String voucherUrl) {
		this.voucherUrl = voucherUrl;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setUnitOfSale(String unitOfSale) {
		this.unitOfSale = unitOfSale;
	}

	public void setUnitOfSaleQuantityPerEach(BigDecimal unitOfSaleQuantityPerEach) {
		this.unitOfSaleQuantityPerEach = unitOfSaleQuantityPerEach;
	}

	public void setItemUom(String itemUom) {
		this.itemUom = itemUom;
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
	public BigDecimal getUnitRebate() {
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
	public BigDecimal getPercentSplitCharge() {
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
	public BigDecimal getWasteOrderNo() {
		return wasteOrderNo;
	}
	public String getWasteManifestId() {
		return wasteManifestId;
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
	public BigDecimal getInvoiceUnitPrice() {
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

    public String getReceiptId() {
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
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public String getFinanceApproverName() {
		return financeApproverName;
	}
	public BigDecimal getTotalRebate() {
		return totalRebate;
	}

	public String getAccountSysDesc() {
		return accountSysDesc;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getVoucherUrl() {
		return voucherUrl;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}


	public String getUnitOfSale() {
		return unitOfSale;
	}

	public BigDecimal getUnitOfSaleQuantityPerEach() {
		return unitOfSaleQuantityPerEach;
	}

	public String getItemUom() {
		return itemUom;
	}

	public BigDecimal getTotalFreightCharge() {
		return totalFreightCharge;
	}

	public void setTotalFreightCharge(BigDecimal totalFreightCharge) {
		this.totalFreightCharge = totalFreightCharge;
	}

	public String getAccountNumber3() {
		return accountNumber3;
	}

	public void setAccountNumber3(String accountNumber3) {
		this.accountNumber3 = accountNumber3;
	}

	public String getAccountNumber4() {
		return accountNumber4;
	}

	public void setAccountNumber4(String accountNumber4) {
		this.accountNumber4 = accountNumber4;
	}

	public String getCustomerInvoiceNo() {
		return customerInvoiceNo;
	}

	public void setCustomerInvoiceNo(String customerInvoiceNo) {
		this.customerInvoiceNo = customerInvoiceNo;
	}

	public String getCustomerPartNo() {
		return customerPartNo;
	}

	public void setCustomerPartNo(String customerPartNo) {
		this.customerPartNo = customerPartNo;
	}

	public String getShippingReference() {
		return shippingReference;
	}

	public void setShippingReference(String shippingReference) {
		this.shippingReference = shippingReference;
	}
}