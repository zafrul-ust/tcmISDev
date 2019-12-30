package com.tcmis.internal.invoice.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: InvoiceViewSwaBean <br>
 * @version: 1.0, May 26, 2005 <br>
 *****************************************************************************/


public class InvoiceViewSwaBean extends BaseDataBean {

	private BigDecimal invoice;
	private BigDecimal invoiceLine;
	private String poNumber;
	private BigDecimal invoicePeriod;
	private String catPartNo;
	private BigDecimal invoiceQuantity;
	private BigDecimal quantityOrdered;
	private BigDecimal invoiceUnitPrice;
	private BigDecimal unitDiscount;
	private BigDecimal extendedPrice;
	private BigDecimal totalDiscount;
	private BigDecimal netAmount;
	private BigDecimal totalAdditionalCharge;
	private BigDecimal serviceFee;
	private String partDescription;
	private String requestorName;
	private String haasRequestNumber;
	private BigDecimal finalExtendedPrice;
	private BigDecimal totalAmount;
	private BigDecimal invoiceAmount;
	private String manufacturerDesc;
	private String invoiceSupplier;
	private Date invoiceDate;


	//constructor
	public InvoiceViewSwaBean() {
	}

	//setters
	public void setInvoice(BigDecimal invoice) {
		this.invoice = invoice;
	}
	public void setInvoiceLine(BigDecimal invoiceLine) {
		this.invoiceLine = invoiceLine;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setInvoicePeriod(BigDecimal invoicePeriod) {
		this.invoicePeriod = invoicePeriod;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setInvoiceQuantity(BigDecimal invoiceQuantity) {
		this.invoiceQuantity = invoiceQuantity;
	}
	public void setQuantityOrdered(BigDecimal quantityOrdered) {
		this.quantityOrdered = quantityOrdered;
	}
	public void setInvoiceUnitPrice(BigDecimal invoiceUnitPrice) {
		this.invoiceUnitPrice = invoiceUnitPrice;
	}
	public void setUnitDiscount(BigDecimal unitDiscount) {
		this.unitDiscount = unitDiscount;
	}
	public void setExtendedPrice(BigDecimal extendedPrice) {
		this.extendedPrice = extendedPrice;
	}
	public void setTotalDiscount(BigDecimal totalDiscount) {
		this.totalDiscount = totalDiscount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	public void setTotalAdditionalCharge(BigDecimal totalAdditionalCharge) {
		this.totalAdditionalCharge = totalAdditionalCharge;
	}
	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	public void setHaasRequestNumber(String haasRequestNumber) {
		this.haasRequestNumber = haasRequestNumber;
	}
	public void setFinalExtendedPrice(BigDecimal finalExtendedPrice) {
		this.finalExtendedPrice = finalExtendedPrice;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public void setManufacturerDesc(String manufacturerDesc) {
		this.manufacturerDesc = manufacturerDesc;
	}
	public void setInvoiceSupplier(String invoiceSupplier) {
		this.invoiceSupplier = invoiceSupplier;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}


	//getters
	public BigDecimal getInvoice() {
		return invoice;
	}
	public BigDecimal getInvoiceLine() {
		return invoiceLine;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public BigDecimal getInvoicePeriod() {
		return invoicePeriod;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public BigDecimal getInvoiceQuantity() {
		return invoiceQuantity;
	}
	public BigDecimal getQuantityOrdered() {
		return quantityOrdered;
	}
	public BigDecimal getInvoiceUnitPrice() {
		return invoiceUnitPrice;
	}
	public BigDecimal getUnitDiscount() {
		return unitDiscount;
	}
	public BigDecimal getExtendedPrice() {
		return extendedPrice;
	}
	public BigDecimal getTotalDiscount() {
		return totalDiscount;
	}
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public BigDecimal getTotalAdditionalCharge() {
		return totalAdditionalCharge;
	}
	public BigDecimal getServiceFee() {
		return serviceFee;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public String getRequestorName() {
		return requestorName;
	}
	public String getHaasRequestNumber() {
		return haasRequestNumber;
	}
	public BigDecimal getFinalExtendedPrice() {
		return finalExtendedPrice;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}
	public String getManufacturerDesc() {
		return manufacturerDesc;
	}
	public String getInvoiceSupplier() {
		return invoiceSupplier;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
}