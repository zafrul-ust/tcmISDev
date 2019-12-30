package com.tcmis.internal.accountspayable.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: UnfinishedInvoiceViewBean <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class UnfinishedInvoiceViewBean extends BaseDataBean {

	private BigDecimal voucherId;
	private BigDecimal radianPo;
	private String supplierInvoiceId;
	private String voucherStatus;
	private BigDecimal remainingPrice;


	//constructor
	public UnfinishedInvoiceViewBean() {
	}

	//setters
	public void setVoucherId(BigDecimal voucherId) {
		this.voucherId = voucherId;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setSupplierInvoiceId(String supplierInvoiceId) {
		this.supplierInvoiceId = supplierInvoiceId;
	}
	public void setVoucherStatus(String voucherStatus) {
		this.voucherStatus = voucherStatus;
	}
	public void setRemainingPrice(BigDecimal remainingPrice) {
		this.remainingPrice = remainingPrice;
	}


	//getters
	public BigDecimal getVoucherId() {
		return voucherId;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public String getSupplierInvoiceId() {
		return supplierInvoiceId;
	}
	public String getVoucherStatus() {
		return voucherStatus;
	}
	public BigDecimal getRemainingPrice() {
		return remainingPrice;
	}
}