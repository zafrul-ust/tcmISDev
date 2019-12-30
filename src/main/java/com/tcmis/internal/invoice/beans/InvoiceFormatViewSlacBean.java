package com.tcmis.internal.invoice.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: InvoiceFormatViewSlacBean <br>
 * @version: 1.0, Sep 18, 2008 <br>
 *****************************************************************************/


public class InvoiceFormatViewSlacBean extends BaseDataBean {

	private String facilityId;
	private String application;
	private String chargeType;
	private String commodity;
	private String itemType;
	private BigDecimal invoice;
	private Date invoiceDate;
	private String accountNumber;
	private String accountNumber2;
	private String requestorName;
	private String approver;
	private BigDecimal prNumber;
	private String lineItem;
	private String partDescription;
	private String packaging;
	private BigDecimal receiptId;
	private Date dateDelivered;
	private BigDecimal quantity;
	private String catalogPrice;
	private String invoiceUnitPrice;
	private String totalAddCharge;
	private String netAmount;
	private String priceFlag;
	private BigDecimal invoicePeriod;
	private BigDecimal priceDifference;
	private String addChargeDesc;


	//constructor
	public InvoiceFormatViewSlacBean() {
	}

	//setters
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public void setInvoice(BigDecimal invoice) {
		this.invoice = invoice;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public void setAccountNumber2(String accountNumber2) {
		this.accountNumber2 = accountNumber2;
	}
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setCatalogPrice(String catalogPrice) {
		this.catalogPrice = catalogPrice;
	}
	public void setInvoiceUnitPrice(String invoiceUnitPrice) {
		this.invoiceUnitPrice = invoiceUnitPrice;
	}
	public void setTotalAddCharge(String totalAddCharge) {
		this.totalAddCharge = totalAddCharge;
	}
	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}
	public void setPriceFlag(String priceFlag) {
		this.priceFlag = priceFlag;
	}
	public void setInvoicePeriod(BigDecimal invoicePeriod) {
		this.invoicePeriod = invoicePeriod;
	}
	public void setPriceDifference(BigDecimal priceDifference) {
		this.priceDifference = priceDifference;
	}
	public void setAddChargeDesc(String addChargeDesc) {
		this.addChargeDesc = addChargeDesc;
	}


	//getters
	public String getFacilityId() {
		return facilityId;
	}
	public String getApplication() {
		return application;
	}
	public String getChargeType() {
		return chargeType;
	}
	public String getCommodity() {
		return commodity;
	}
	public String getItemType() {
		return itemType;
	}
	public BigDecimal getInvoice() {
		return invoice;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public String getAccountNumber2() {
		return accountNumber2;
	}
	public String getRequestorName() {
		return requestorName;
	}
	public String getApprover() {
		return approver;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public String getPackaging() {
		return packaging;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public Date getDateDelivered() {
		return dateDelivered;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getCatalogPrice() {
		return catalogPrice;
	}
	public String getInvoiceUnitPrice() {
		return invoiceUnitPrice;
	}
	public String getTotalAddCharge() {
		return totalAddCharge;
	}
	public String getNetAmount() {
		return netAmount;
	}
	public String getPriceFlag() {
		return priceFlag;
	}
	public BigDecimal getInvoicePeriod() {
		return invoicePeriod;
	}
	public BigDecimal getPriceDifference() {
		return priceDifference;
	}
	public String getAddChargeDesc() {
		return addChargeDesc;
	}
}