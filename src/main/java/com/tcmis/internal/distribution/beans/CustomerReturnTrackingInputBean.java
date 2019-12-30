package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerReturnRequestViewBean <br>
 * @version: 1.0, Jul 10, 2009 <br>
 *****************************************************************************/


public class CustomerReturnTrackingInputBean extends BaseDataBean {

	private String opsEntityId;
	private String inventoryGroup;
	private String hub;

	private BigDecimal customerId;
	private String customerName;
	private String searchField;
    private String searchMode;
    private String searchArgument;
    
	private String searchOption;
	private BigDecimal days;
	
	private String rmaStatus;


	//constructor
	public CustomerReturnTrackingInputBean() {
	}


	public BigDecimal getCustomerId() {
		return customerId;
	}


	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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


	public String getOpsEntityId() {
		return opsEntityId;
	}


	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
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


	public BigDecimal getDays() {
		return days;
	}


	public void setDays(BigDecimal days) {
		this.days = days;
	}


	public String getSearchOption() {
		return searchOption;
	}


	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}


	public String getRmaStatus() {
		return rmaStatus;
	}


	public void setRmaStatus(String rmaStatus) {
		this.rmaStatus = rmaStatus;
	}



}