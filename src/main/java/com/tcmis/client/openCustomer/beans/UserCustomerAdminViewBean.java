package com.tcmis.client.openCustomer.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: UserCustomerAdminViewBean <br>
 * @version: 1.0, Jan 25, 2011 <br>
 *****************************************************************************/


public class UserCustomerAdminViewBean extends BaseDataBean {

	private BigDecimal userId;
	private String userAccess;
	private BigDecimal personnelId;
	private BigDecimal customerId;
	private String customerAccess;
	private String oldCustomerAccess;
	private String adminRole;
	private String oldAdminRole;
	private String customerName;
	private String modified;
	
//	constructor
	public UserCustomerAdminViewBean() {
	}
	
	public String getAdminRole() {
		return adminRole;
	}
	public void setAdminRole(String adminRole) {
		this.adminRole = adminRole;
	}
	public String getCustomerAccess() {
		return customerAccess;
	}
	public void setCustomerAccess(String customerAccess) {
		this.customerAccess = customerAccess;
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
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	public String getOldAdminRole() {
		return oldAdminRole;
	}
	public void setOldAdminRole(String oldAdminRole) {
		this.oldAdminRole = oldAdminRole;
	}
	public String getOldCustomerAccess() {
		return oldCustomerAccess;
	}
	public void setOldCustomerAccess(String oldCustomerAccess) {
		this.oldCustomerAccess = oldCustomerAccess;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public String getUserAccess() {
		return userAccess;
	}
	public void setUserAccess(String userAccess) {
		this.userAccess = userAccess;
	}
	public BigDecimal getUserId() {
		return userId;
	}
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
	
}