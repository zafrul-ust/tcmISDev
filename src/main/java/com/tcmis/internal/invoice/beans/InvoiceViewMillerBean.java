package com.tcmis.internal.invoice.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: InvoiceViewMillerBean <br>
 * @version: 1.0, May 26, 2005 <br>
 *****************************************************************************/


public class InvoiceViewMillerBean extends BaseDataBean {

	private BigDecimal invoice;
	private String poNumber;
	private Date invoiceDate;
	private String facilityId;
	private BigDecimal invoicePeriod;


	//constructor
	public InvoiceViewMillerBean() {
	}

	//setters
	public void setInvoice(BigDecimal invoice) {
		this.invoice = invoice;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setInvoicePeriod(BigDecimal invoicePeriod) {
		this.invoicePeriod = invoicePeriod;
	}


	//getters
	public BigDecimal getInvoice() {
		return invoice;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public BigDecimal getInvoicePeriod() {
		return invoicePeriod;
	}
}