package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class SalesOrderSearchBean extends BaseDataBean {
	private static final long serialVersionUID = 1L;
	
	private String operatingEntityId;
	private String hub;
	private String inventoryGroup;
	private BigDecimal customerId;
	private String customerName;
	private Date orderFromDate;
	private Date orderToDate;
	private Date promiseShipFromDate;
	private Date promiseShipToDate;
	private String showOnlyLateOrders="No";
	private String showGrossMarginExceptions="No";
	private String backOrdersOnly="No";
	private String orderStatusList;
	private String personnelName;
    private BigDecimal personnelId;
	
	public String getPersonnelName() {
		return personnelName;
	}
	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	private String[] orderStatus;
	
	private String searchField;
	private String searchMode;
	private String searchArgument;
	
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public String getOrderStatusList() {
		return orderStatusList;
	}
	public void setOrderStatusList(String orderStatusList) {
		this.orderStatusList = orderStatusList;
	}
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
	public Date getPromiseShipFromDate() {
		return promiseShipFromDate;
	}
	public void setPromiseShipFromDate(Date promiseShipFromDate) {
		this.promiseShipFromDate = promiseShipFromDate;
	}
	public Date getPromiseShipToDate() {
		return promiseShipToDate;
	}
	public void setPromiseShipToDate(Date promiseShipToDate) {
		this.promiseShipToDate = promiseShipToDate;
	}
	public String getShowOnlyLateOrders() {
		return showOnlyLateOrders;
	}
	public void setShowOnlyLateOrders(String showOnlyLateOrders) {
		this.showOnlyLateOrders = showOnlyLateOrders;
	}	
	public void setShowGrossMarginExceptions(String showGrossMarginExceptions) {
		this.showGrossMarginExceptions = showGrossMarginExceptions;
	}	
	public String getShowGrossMarginExceptions() {
		return showGrossMarginExceptions;
	}
	public void setOrderStatus(String[] orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String[] getOrderStatus() {
		return orderStatus;
	}
	public void setBackOrdersOnly(String backOrdersOnly) {
		this.backOrdersOnly = backOrdersOnly;
	}
	public String getBackOrdersOnly() {
		return backOrdersOnly;
	}
	public String getSearchArgument() {
		return searchArgument;
	}
	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getSearchMode() {
		return searchMode;
	}
	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}	
}
