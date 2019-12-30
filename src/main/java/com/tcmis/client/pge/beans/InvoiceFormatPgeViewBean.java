package com.tcmis.client.pge.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: InvoiceFormatPgeViewBean <br>
 * @version: 1.0, Nov 15, 2005 <br>
 *****************************************************************************/


public class InvoiceFormatPgeViewBean extends BaseDataBean {

	private String companyId;
	private String billToName;
	private String billToAddr1;
	private String billToCity;
	private String billToState;
	private String billToZip;
	private String remitToName;
	private String remitToAddr1;
	private String remitToCity;
	private String remitToState;
	private String remitToZip;
	private String shipToName;
	private String shipToAddr1;
	private String shipToAddr2;
	private String shipToCity;
	private String shipToState;
	private String shipToZip;
	private BigDecimal invoice;
	private Date invoiceDate;
	private String poNumber;
	private BigDecimal poLineNumber;
	private String poLineSchedule;
	private String catPartNo;
	private String partDescription;
	private BigDecimal lineItemQuantity;
	private String uom;
	private String unitPrice;
	private BigDecimal totalSalesTax;
	private BigDecimal totalFreight;
	private BigDecimal totalSurcharge;
	private String comments;
	private String netDays;
	private BigDecimal invoiceAmount;
	private String currencyId;


	//constructor
	public InvoiceFormatPgeViewBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setBillToName(String billToName) {
		this.billToName = billToName;
	}
	public void setBillToAddr1(String billToAddr1) {
		this.billToAddr1 = billToAddr1;
	}
	public void setBillToCity(String billToCity) {
		this.billToCity = billToCity;
	}
	public void setBillToState(String billToState) {
		this.billToState = billToState;
	}
	public void setBillToZip(String billToZip) {
		this.billToZip = billToZip;
	}
	public void setRemitToName(String remitToName) {
		this.remitToName = remitToName;
	}
	public void setRemitToAddr1(String remitToAddr1) {
		this.remitToAddr1 = remitToAddr1;
	}
	public void setRemitToCity(String remitToCity) {
		this.remitToCity = remitToCity;
	}
	public void setRemitToState(String remitToState) {
		this.remitToState = remitToState;
	}
	public void setRemitToZip(String remitToZip) {
		this.remitToZip = remitToZip;
	}
	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}
	public void setShipToAddr1(String shipToAddr1) {
		this.shipToAddr1 = shipToAddr1;
	}
	public void setShipToAddr2(String shipToAddr2) {
		this.shipToAddr2 = shipToAddr2;
	}
	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}
	public void setShipToState(String shipToState) {
		this.shipToState = shipToState;
	}
	public void setShipToZip(String shipToZip) {
		this.shipToZip = shipToZip;
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
	public void setPoLineNumber(BigDecimal poLineNumber) {
		this.poLineNumber = poLineNumber;
	}
	public void setPoLineSchedule(String poLineSchedule) {
		this.poLineSchedule = poLineSchedule;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setLineItemQuantity(BigDecimal lineItemQuantity) {
		this.lineItemQuantity = lineItemQuantity;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setTotalSalesTax(BigDecimal totalSalesTax) {
		this.totalSalesTax = totalSalesTax;
	}
	public void setTotalFreight(BigDecimal totalFreight) {
		this.totalFreight = totalFreight;
	}
	public void setTotalSurcharge(BigDecimal totalSurcharge) {
		this.totalSurcharge = totalSurcharge;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setNetDays(String netDays) {
		this.netDays = netDays;
	}
	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getBillToName() {
		return billToName;
	}
	public String getBillToAddr1() {
		return billToAddr1;
	}
	public String getBillToCity() {
		return billToCity;
	}
	public String getBillToState() {
		return billToState;
	}
	public String getBillToZip() {
		return billToZip;
	}
	public String getRemitToName() {
		return remitToName;
	}
	public String getRemitToAddr1() {
		return remitToAddr1;
	}
	public String getRemitToCity() {
		return remitToCity;
	}
	public String getRemitToState() {
		return remitToState;
	}
	public String getRemitToZip() {
		return remitToZip;
	}
	public String getShipToName() {
		return shipToName;
	}
	public String getShipToAddr1() {
		return shipToAddr1;
	}
	public String getShipToAddr2() {
		return shipToAddr2;
	}
	public String getShipToCity() {
		return shipToCity;
	}
	public String getShipToState() {
		return shipToState;
	}
	public String getShipToZip() {
		return shipToZip;
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
	public BigDecimal getPoLineNumber() {
		return poLineNumber;
	}
	public String getPoLineSchedule() {
		return poLineSchedule;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public BigDecimal getLineItemQuantity() {
		return lineItemQuantity;
	}
	public String getUom() {
		return uom;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public BigDecimal getTotalSalesTax() {
		return totalSalesTax;
	}
	public BigDecimal getTotalFreight() {
		return totalFreight;
	}
	public BigDecimal getTotalSurcharge() {
		return totalSurcharge;
	}
	public String getComments() {
		return comments;
	}
	public String getNetDays() {
		return netDays;
	}
	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	
}