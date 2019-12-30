package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerContactAddRequestBean <br>
 * @version: 1.0, Aug 20, 2009 <br>
 *****************************************************************************/


public class CustomerContactAddRequestBean extends BaseDataBean {

	private BigDecimal customerRequestId;
	private BigDecimal customerId;
	private String billToCompanyId;
	private BigDecimal contactPersonnelId;
	private String contactType;
	private String firstName;
	private String lastName;
	private String nickname;
	private String phone;
	private String mobile;
	private String fax;
	private String email;
	private String purchasing;
	private String accountsPayable;
	private String receiving;
	private String qualityAssurance;
	private String management;
	private String defaultContact;

// from page
	private String changed;
// for other function
	private String status;
	public String getChanged() {
		return changed;
	}

	public void setChanged(String changed) {
		this.changed = changed;
	}

	//constructor
	public CustomerContactAddRequestBean() {
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
	public void setContactPersonnelId(BigDecimal contactPersonnelId) {
		this.contactPersonnelId = contactPersonnelId;
	}
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPurchasing(String purchasing) {
		this.purchasing = purchasing;
	}
	public void setAccountsPayable(String accountsPayable) {
		this.accountsPayable = accountsPayable;
	}
	public void setReceiving(String receiving) {
		this.receiving = receiving;
	}
	public void setQualityAssurance(String qualityAssurance) {
		this.qualityAssurance = qualityAssurance;
	}
	public void setManagement(String management) {
		this.management = management;
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
	public BigDecimal getContactPersonnelId() {
		return contactPersonnelId;
	}
	public String getContactType() {
		return contactType;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getNickname() {
		return nickname;
	}
	public String getPhone() {
		return phone;
	}
	public String getMobile() {
		return mobile;
	}
	public String getFax() {
		return fax;
	}
	public String getEmail() {
		return email;
	}
	public String getPurchasing() {
		return purchasing;
	}
	public String getAccountsPayable() {
		return accountsPayable;
	}
	public String getReceiving() {
		return receiving;
	}
	public String getQualityAssurance() {
		return qualityAssurance;
	}
	public String getManagement() {
		return management;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getDefaultContact() {
		return defaultContact;
	}

	public void setDefaultContact(String defaultContact) {
		this.defaultContact = defaultContact;
	}
}