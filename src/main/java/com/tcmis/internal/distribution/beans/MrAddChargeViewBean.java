package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: MrAddChargeViewBean <br>
 * 
 * @version: 1.0, Aug 11, 2009 <br>
 *****************************************************************************/

public class MrAddChargeViewBean extends BaseDataBean {

	private static final long serialVersionUID = -7944734943142749383L;
	// from page.
	private String changed;
	private String chargeRecurrence;
	private String companyId;
	private String currencyId;
	private String description;
	private String invoiced;
	private String itemDesc;
	private BigDecimal itemId;
	private String lineItem;
	private String OpsEntityId;
	private String orderType;
	private BigDecimal price;
	private BigDecimal prNumber;
	private BigDecimal quantity;
	private String taxExempt;

	private BigDecimal unitPrice;

	// constructor
	public MrAddChargeViewBean() {
	}

	public String getChanged() {
		return changed;
	}

	public String getChargeRecurrence() {
		return chargeRecurrence;
	}

	// getters
	public String getCompanyId() {
		return companyId;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public String getDescription() {
		return description;
	}

	public String getInvoiced() {
		return invoiced;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getLineItem() {
		return lineItem;
	}

	public String getOpsEntityId() {
		return OpsEntityId;
	}

	public String getOrderType() {
		return orderType;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public String getTaxExempt() {
		return taxExempt;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setChanged(String changed) {
		this.changed = changed;
	}

	public void setChargeRecurrence(String chargeRecurrence) {
		this.chargeRecurrence = chargeRecurrence;
	}

	// setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setInvoiced(String invoiced) {
		this.invoiced = invoiced;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public void setOpsEntityId(String opsEntityId) {
		OpsEntityId = opsEntityId;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void setTaxExempt(String taxExempt) {
		this.taxExempt = taxExempt;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

}