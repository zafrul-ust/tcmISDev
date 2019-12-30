package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerCarrierAddRequestBean <br>
 * @version: 1.0, Aug 21, 2009 <br>
 *****************************************************************************/


public class CustomerCarrierAddRequestBean extends BaseDataBean {

	private BigDecimal customerRequestId;
	private BigDecimal customerId;
	private String billToCompanyId;
	private String carrierName;
	private String carrierAccount;
	private String inventoryGroup;
	private String transportationMode;
	private String carrierMethod;
	private String notes;
// from page
	private String changed;

	//constructor
	public CustomerCarrierAddRequestBean() {
	}

	//setters
	public void setCustomerRequestId(BigDecimal customerRequestId) {
		this.customerRequestId = customerRequestId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setBillToCompanyId(String billToCompanyId) {
		this.billToCompanyId = billToCompanyId;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setCarrierAccount(String carrierAccount) {
		this.carrierAccount = carrierAccount;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setTransportationMode(String transportationMode) {
		this.transportationMode = transportationMode;
	}
	public void setCarrierMethod(String carrierMethod) {
		this.carrierMethod = carrierMethod;
	}


	//getters
	public BigDecimal getCustomerRequestId() {
		return customerRequestId;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public String getBillToCompanyId() {
		return billToCompanyId;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getCarrierAccount() {
		return carrierAccount;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getTransportationMode() {
		return transportationMode;
	}
	public String getCarrierMethod() {
		return carrierMethod;
	}
	public String getChanged() {
		return changed;
	}
	public void setChanged(String changed) {
		this.changed = changed;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}


}