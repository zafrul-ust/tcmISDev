package com.tcmis.internal.distribution.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerCarrierInfoViewBean <br>
 * @version: 1.0, Aug 27, 2009 <br>
 *****************************************************************************/


public class CustomerCarrierInfoViewBean extends BaseDataBean {

	private String carrierCode;
	private String account;
	private String notes;
	private String carrierName;
	private String status;
	private BigDecimal customerId;
	private String carrierServiceType;
	private String inventoryGroup;
	private String inventoryGroupName;


	//constructor
	public CustomerCarrierInfoViewBean() {
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
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
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
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
}