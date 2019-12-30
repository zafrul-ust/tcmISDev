package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerCarrierInfo Bean <br>
 * @version: 1.0, Aug 17, 2009 <br>
 *****************************************************************************/


public class CustomerCarrierInfoBean extends BaseDataBean {
	
	private static final long serialVersionUID = 6076861624023841934L;
	private String carrierCode;
	private String account;
	private String notes;
	private String carrierName;
	private String status;
	private BigDecimal customerId;
	private String carrierServiceType;
	private String inventoryGroup;
	private String action;
	private String searchArgument;	
	private String searchField;
    private String searchMode;
    private String transportationMode;
	private String carrierMethod;
	private String showCustomerCarriersOnly;
// need this to compile	
	private String fromCustomerDetail;
	
	//constructor
	public CustomerCarrierInfoBean() {
	}

	//setters
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setCarrierServiceType(String carrierServiceType) {
		this.carrierServiceType = carrierServiceType;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}


	//getters
	public String getCarrierCode() {
		return carrierCode;
	}
	public String getAccount() {
		return account;
	}
	public String getNotes() {
		return notes;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getStatus() {
		return status;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public String getCarrierServiceType() {
		return carrierServiceType;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
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

	public void setTransportationMode(String transportationMode) {
		this.transportationMode = transportationMode;
	}

	public String getTransportationMode() {
		return transportationMode;
	}

	public void setCarrierMethod(String carrierMethod) {
		this.carrierMethod = carrierMethod;
	}

	public String getCarrierMethod() {
		return carrierMethod;
	}

	public void setShowCustomerCarriersOnly(String showCustomerCarriersOnly) {
		this.showCustomerCarriersOnly = showCustomerCarriersOnly;
	}

	public String getShowCustomerCarriersOnly() {
		return showCustomerCarriersOnly;
	}

	public String getFromCustomerDetail() {
		return fromCustomerDetail;
	}

	public void setFromCustomerDetail(String fromCustomerDetail) {
		this.fromCustomerDetail = fromCustomerDetail;
	}

}