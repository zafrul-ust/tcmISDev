package com.tcmis.internal.invoice.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class InvoiceViewSiacBean extends BaseDataBean{
	
	private BigDecimal invoicePeriod;
	private String invoiceGroup;
	private BigDecimal invoiceAmount;
	private String poNumber;
	private String notes;
	private BigDecimal invoice;
	private Date invoiceDate;
	private String mfgLot;
	private BigDecimal labCost;
	private BigDecimal markUp;
	private BigDecimal netAmount;
	
	//constructor
	public InvoiceViewSiacBean() {
	}

	public BigDecimal getInvoicePeriod() {
		return invoicePeriod;
	}

	public void setInvoicePeriod(BigDecimal invoicePeriod) {
		this.invoicePeriod = invoicePeriod;
	}

	public String getInvoiceGroup() {
		return invoiceGroup;
	}

	public void setInvoiceGroup(String invoiceGroup) {
		this.invoiceGroup = invoiceGroup;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public BigDecimal getInvoice() {
		return invoice;
	}

	public void setInvoice(BigDecimal invoice) {
		this.invoice = invoice;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getMfgLot() {
		return mfgLot;
	}

	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}

	public BigDecimal getLabCost() {
		return labCost;
	}

	public void setLabCost(BigDecimal labCost) {
		this.labCost = labCost;
	}

	public BigDecimal getMarkUp() {
		return markUp;
	}

	public void setMarkUp(BigDecimal markUp) {
		this.markUp = markUp;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	
	

}
