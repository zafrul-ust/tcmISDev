package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

public class PwcPoLookupViewBean {

	private BigDecimal radianPo;
	private BigDecimal poLine;
	private BigDecimal itemId;
	private BigDecimal quantity;
	private BigDecimal unitPrice;
	private Date dateConfirmed;
	private String currencyId;
	private String inventoryGroup;
	private String opsEntityId;
	private BigDecimal priceExchangeRate;
	private BigDecimal currentExchangeRate;
	private String customerPoNo;
	private String orderType;
	private String ackSent;
	private BigDecimal dropshipMarkup;
	private BigDecimal oorMarkup;
	private BigDecimal mmMarkup;
	private String editPoPrice;
	
	
	public PwcPoLookupViewBean() {
		
	}

	public BigDecimal getRadianPo() {
		return radianPo;
	}

	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}

	public BigDecimal getPoLine() {
		return poLine;
	}

	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Date getDateConfirmed() {
		return dateConfirmed;
	}

	public void setDateConfirmed(Date dateConfirmed) {
		this.dateConfirmed = dateConfirmed;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public BigDecimal getPriceExchangeRate() {
		return priceExchangeRate;
	}

	public void setPriceExchangeRate(BigDecimal priceExchangeRate) {
		this.priceExchangeRate = priceExchangeRate;
	}

	public BigDecimal getCurrentExchangeRate() {
		return currentExchangeRate;
	}

	public void setCurrentExchangeRate(BigDecimal currentExchangeRate) {
		this.currentExchangeRate = currentExchangeRate;
	}

	public String getCustomerPoNo() {
		return customerPoNo;
	}

	public void setCustomerPoNo(String customerPoNo) {
		this.customerPoNo = customerPoNo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getAckSent() {
		return ackSent;
	}

	public void setAckSent(String ackSent) {
		this.ackSent = ackSent;
	}

	public BigDecimal getDropshipMarkup() {
		return dropshipMarkup;
	}

	public void setDropshipMarkup(BigDecimal dropshipMarkup) {
		this.dropshipMarkup = dropshipMarkup;
	}

	public BigDecimal getOorMarkup() {
		return oorMarkup;
	}

	public void setOorMarkup(BigDecimal oorMarkup) {
		this.oorMarkup = oorMarkup;
	}

	public BigDecimal getMmMarkup() {
		return mmMarkup;
	}

	public void setMmMarkup(BigDecimal mmMarkup) {
		this.mmMarkup = mmMarkup;
	}

	public String getEditPoPrice() {
		return editPoPrice;
	}

	public void setEditPoPrice(String editPoPrice) {
		this.editPoPrice = editPoPrice;
	}
}
