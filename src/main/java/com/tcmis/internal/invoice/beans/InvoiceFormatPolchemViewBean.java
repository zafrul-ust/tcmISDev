package com.tcmis.internal.invoice.beans;

import java.util.Date;
import java.util.Collection;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: InvoiceFormatPolchemViewBean <br>
 * @version: 1.0, Oct 31, 2008 <br>
 *****************************************************************************/


public class InvoiceFormatPolchemViewBean extends BaseDataBean {

	private BigDecimal invoicePeriod;
	private BigDecimal invoice;
	private Date invoiceDate;
	private String poNumber;
	private String inventoryGroup;
	private String ric;
	private BigDecimal invoiceLine;
	private BigDecimal invoiceAmount;
	private String lineItem;
	private String nsn;
	private String saicSku;
	private Date dateShipped;
	private BigDecimal quantity;
	private BigDecimal invoiceUnitPrice;
	private BigDecimal catalogPrice;
	private BigDecimal netAmount;
	private String partDescription;
	private String itemType;
	private BigDecimal salesTax;
    private Collection<InvoiceFormatPolchemViewBean> detailCollection = new Vector();


    //constructor
	public InvoiceFormatPolchemViewBean() {
	}

	//setters
	public void setInvoicePeriod(BigDecimal invoicePeriod) {
		this.invoicePeriod = invoicePeriod;
	}
	public void setInvoice(BigDecimal invoice) {
		this.invoice = invoice;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setRic(String ric) {
		this.ric = ric;
	}
	public void setInvoiceLine(BigDecimal invoiceLine) {
		this.invoiceLine = invoiceLine;
	}
	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setNsn(String nsn) {
		this.nsn = nsn;
	}
	public void setSaicSku(String saicSku) {
		this.saicSku = saicSku;
	}
	public void setDateShipped(Date dateShipped) {
		this.dateShipped = dateShipped;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setInvoiceUnitPrice(BigDecimal invoiceUnitPrice) {
		this.invoiceUnitPrice = invoiceUnitPrice;
	}
	public void setCatalogPrice(BigDecimal catalogPrice) {
		this.catalogPrice = catalogPrice;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public void setSalesTax(BigDecimal salesTax) {
		this.salesTax = salesTax;
	}
    public void setDetailCollection(Collection<InvoiceFormatPolchemViewBean> c) {
		this.detailCollection = c;
	}

	//getters
	public BigDecimal getInvoicePeriod() {
		return invoicePeriod;
	}
	public BigDecimal getInvoice() {
		return invoice;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getRic() {
		return ric;
	}
	public BigDecimal getInvoiceLine() {
		return invoiceLine;
	}
	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getNsn() {
		return nsn;
	}
	public String getSaicSku() {
		return saicSku;
	}
	public Date getDateShipped() {
		return dateShipped;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public BigDecimal getInvoiceUnitPrice() {
		return invoiceUnitPrice;
	}
	public BigDecimal getCatalogPrice() {
		return catalogPrice;
	}
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public String getItemType() {
		return itemType;
	}
	public BigDecimal getSalesTax() {
		return salesTax;
	}
    public Collection<InvoiceFormatPolchemViewBean> getDetailCollection() {
		return detailCollection;
	}
}