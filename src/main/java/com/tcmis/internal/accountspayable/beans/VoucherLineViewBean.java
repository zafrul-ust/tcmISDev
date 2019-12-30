package com.tcmis.internal.accountspayable.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VoucherLineViewBean <br>
 * @version: 1.0, Mar 10, 2006 <br>
 *****************************************************************************/


public class VoucherLineViewBean extends BaseDataBean {

	private BigDecimal voucherId;
	private BigDecimal voucherLine;
	private BigDecimal itemId;
	private String itemDesc;
	private BigDecimal quantityInvoiced;
	private BigDecimal invoiceUnitPrice;
	private Date transactionDate;
	private Date recQuantityMatchDate;
	private Date recUnitPriceMatchDate;


	//constructor
	public VoucherLineViewBean() {
	}

	//setters
	public void setVoucherId(BigDecimal voucherId) {
		this.voucherId = voucherId;
	}
	public void setVoucherLine(BigDecimal voucherLine) {
		this.voucherLine = voucherLine;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setQuantityInvoiced(BigDecimal quantityInvoiced) {
		this.quantityInvoiced = quantityInvoiced;
	}
	public void setInvoiceUnitPrice(BigDecimal invoiceUnitPrice) {
		this.invoiceUnitPrice = invoiceUnitPrice;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public void setRecQuantityMatchDate(Date recQuantityMatchDate) {
		this.recQuantityMatchDate = recQuantityMatchDate;
	}
	public void setRecUnitPriceMatchDate(Date recUnitPriceMatchDate) {
		this.recUnitPriceMatchDate = recUnitPriceMatchDate;
	}


	//getters
	public BigDecimal getVoucherId() {
		return voucherId;
	}
	public BigDecimal getVoucherLine() {
		return voucherLine;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public BigDecimal getQuantityInvoiced() {
		return quantityInvoiced;
	}
	public BigDecimal getInvoiceUnitPrice() {
		return invoiceUnitPrice;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public Date getRecQuantityMatchDate() {
		return recQuantityMatchDate;
	}
	public Date getRecUnitPriceMatchDate() {
		return recUnitPriceMatchDate;
	}
}