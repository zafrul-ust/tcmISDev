package com.tcmis.client.api.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class InvoicePrtRollinsLineViewBean extends BaseDataBean {
	
	private String cabinet;
	private String cabinetDesc;
	private String currencyId;
	private String customerPartNo;
	private String customerPo;
	private Date customerPoDate;
	private String customerPoLine;
	private Date dateDelivered;
	private String departmentDesc;
	private String inventoryGroup;
	private BigDecimal invoice;
	private String invoiceGroup;
	private String invoiceId;
	private BigDecimal invoiceLine;
	private BigDecimal invoicePeriod;
	private BigDecimal itemId;
	private String lineItem;
	private BigDecimal materialAmount;
	private String partDescription;
	private String payloadId;
	private String periodEndDate;
	private String periodStartDate;
	private BigDecimal prNumber;
	private BigDecimal serviceFee;
	private BigDecimal totalAddCharge;
	private BigDecimal totalSalesTax;
	private String unitOfSale;
	private BigDecimal unitOfSalePrice;
	private BigDecimal unitOfSaleQuantity;
	
	// constructor
	public InvoicePrtRollinsLineViewBean() {
		super();
	}

	public String getCabinet() {
		return this.cabinet;
	}

	public void setCabinet(String cabinet) {
		this.cabinet = cabinet;
	}

	public String getCabinetDesc() {
		return this.cabinetDesc;
	}

	public void setCabinetDesc(String cabinetDesc) {
		this.cabinetDesc = cabinetDesc;
	}

	public String getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getCustomerPartNo() {
		return this.customerPartNo;
	}

	public void setCustomerPartNo(String customerPartNo) {
		this.customerPartNo = customerPartNo;
	}

	public String getCustomerPo() {
		return this.customerPo;
	}

	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	
	public Date getCustomerPoDate() {
		return this.customerPoDate;
	}

	public void setCustomerPoDate(Date customerPoDate) {
		this.customerPoDate = customerPoDate;
	}

	public String getCustomerPoLine() {
		return this.customerPoLine;
	}

	public void setCustomerPoLine(String customerPoLine) {
		this.customerPoLine = customerPoLine;
	}

	public Date getDateDelivered() {
		return this.dateDelivered;
	}

	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}

	public String getDepartmentDesc() {
		return this.departmentDesc;
	}

	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}

	public String getInventoryGroup() {
		return this.inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public BigDecimal getInvoice() {
		return this.invoice;
	}

	public void setInvoice(BigDecimal invoice) {
		this.invoice = invoice;
	}

	public String getInvoiceGroup() {
		return this.invoiceGroup;
	}

	public void setInvoiceGroup(String invoiceGroup) {
		this.invoiceGroup = invoiceGroup;
	}

	public String getInvoiceId() {
		return this.invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public BigDecimal getInvoiceLine() {
		return this.invoiceLine;
	}

	public void setInvoiceLine(BigDecimal invoiceLine) {
		this.invoiceLine = invoiceLine;
	}

	public BigDecimal getInvoicePeriod() {
		return this.invoicePeriod;
	}

	public void setInvoicePeriod(BigDecimal invoicePeriod) {
		this.invoicePeriod = invoicePeriod;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public String getLineItem() {
		return this.lineItem;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public BigDecimal getMaterialAmount() {
		return this.materialAmount;
	}

	public void setMaterialAmount(BigDecimal materialAmount) {
		this.materialAmount = materialAmount;
	}

	public String getPartDescription() {
		return this.partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public String getPayloadId() {
		return payloadId;
	}

	public void setPayloadId(String payloadId) {
		this.payloadId = payloadId;
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

	public BigDecimal getPrNumber() {
		return this.prNumber;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public BigDecimal getServiceFee() {
		return this.serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public BigDecimal getTotalAddCharge() {
		return this.totalAddCharge;
	}

	public void setTotalAddCharge(BigDecimal totalAddCharge) {
		this.totalAddCharge = totalAddCharge;
	}

	public BigDecimal getTotalSalesTax() {
		return this.totalSalesTax;
	}

	public void setTotalSalesTax(BigDecimal totalSalesTax) {
		this.totalSalesTax = totalSalesTax;
	}

	public String getUnitOfSale() {
		return this.unitOfSale;
	}

	public void setUnitOfSale(String unitOfSale) {
		this.unitOfSale = unitOfSale;
	}

	public BigDecimal getUnitOfSalePrice() {
		return this.unitOfSalePrice;
	}

	public void setUnitOfSalePrice(BigDecimal unitOfSalePrice) {
		this.unitOfSalePrice = unitOfSalePrice;
	}

	public BigDecimal getUnitOfSaleQuantity() {
		return this.unitOfSaleQuantity;
	}

	public void setUnitOfSaleQuantity(BigDecimal unitOfSaleQuantity) {
		this.unitOfSaleQuantity = unitOfSaleQuantity;
	}
}
