package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class OrderEntryLookupSearchBean extends BaseDataBean {
	private static final long serialVersionUID = 1L;
	
	private String operatingEntityId;
	private String hub;
	private String inventoryGroup;
	private String customerName;
	private Date orderFromDate;
	private Date orderToDate;	
	private String searchArgument;	
	private String searchField;
    private String searchMode;
    private String personnelName;
    private BigDecimal personnelId;
    private BigDecimal customerId;
    private String[] orderTypeArray;
	private String[] orderStatus;
	private String mr ="N";
	private String quote ="N";
	private String blanket ="N";
	private String scratchPad ="N";
	
	public String getOperatingEntityId() {
		return operatingEntityId;
	}
	public void setOperatingEntityId(String operatingEntityId) {
		this.operatingEntityId = operatingEntityId;
	}
	public String getHub() {
		return hub;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}	
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Date getOrderFromDate() {
		return orderFromDate;
	}
	public void setOrderFromDate(Date orderFromDate) {
		this.orderFromDate = orderFromDate;
	}
	public Date getOrderToDate() {
		return orderToDate;
	}
	public void setOrderToDate(Date orderToDate) {
		this.orderToDate = orderToDate;
	}	
	public void setOrderStatus(String[] orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String[] getOrderStatus() {
		return orderStatus;
	}	
	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}
	public String getSearchArgument() {
		return searchArgument;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getSearchField() {
		return searchField;
	}
	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}
	public String getSearchMode() {
		return searchMode;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}
	public String getPersonnelName() {
		return personnelName;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setOrderTypeArray(String[] orderTypeArray) {
		this.orderTypeArray = orderTypeArray;
	}
	public String[] getOrderTypeArray() {
		return orderTypeArray;
	}
	/**
	 * @return the mr
	 */
	public String getMr() {
		return mr;
	}
	/**
	 * @param mr the mr to set
	 */
	public void setMr(String mr) {
		this.mr = mr;
	}
	/**
	 * @return the quote
	 */
	public String getQuote() {
		return quote;
	}
	/**
	 * @param quote the quote to set
	 */
	public void setQuote(String quote) {
		this.quote = quote;
	}
	public void setScratchPad(String scratchPad) {
		this.scratchPad = scratchPad;
	}
	public String getScratchPad() {
		return scratchPad;
	}
	public String getBlanket() {
		return blanket;
	}
	public void setBlanket(String blanket) {
		this.blanket = blanket;
	}

		
}
