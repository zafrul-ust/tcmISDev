package com.tcmis.internal.invoice.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: InvoiceViewSyracuseBean <br>
 * @version: 1.0, May 26, 2005 <br>
 *****************************************************************************/


public class InvoiceViewSyracuseBean extends BaseDataBean {

	private BigDecimal invoice;
	private String poNumber;
	private BigDecimal invoicePeriod;


	//constructor
	public InvoiceViewSyracuseBean() {
	}

	//setters
	public void setInvoice(BigDecimal invoice) {
		this.invoice = invoice;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
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
	public BigDecimal getInvoicePeriod() {
		return invoicePeriod;
	}
}