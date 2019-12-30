package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerContactBean <br>
 * @version: 1.0, Jul 24, 2009 <br>
 *****************************************************************************/


public class CustomerContactBean extends BaseDataBean {

	private BigDecimal customerId;
	private BigDecimal contactPersonnelId;
	private String contactType;
	private String firstName;
	private String lastName;
	private String nickname;
	private String phone;
	private String mobile;
	private String fax;
	private String email;
	private String status;
	private String purchasing;
	private String accountsPayable;
	private String receiving;
	private String qualityAssurance;
	private String management;
	private String other;
	private String billToCompanyId;
	// this is from page.
	private String note;
	private String companyId;
	private String defaultContact;
	private boolean contactInfoChange;

	public String getDefaultContact() {
		return defaultContact;
	}

	public void setDefaultContact(String defaultContact) {
		this.defaultContact = defaultContact;
	}

	//constructor
	public CustomerContactBean() {
	}

	//setters
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
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
	public void setStatus(String status) {
		this.status = status;
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
	
	public void setContactInfoChange(boolean contactInfoChange) {
		this.contactInfoChange = contactInfoChange;
	}

	//getters
	public boolean getContactInfoChange() {
		return contactInfoChange;
	}

	public BigDecimal getCustomerId() {
		return customerId;
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
	public String getStatus() {
		return status;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setBillToCompanyId(String billToCompanyId) {
		this.billToCompanyId = billToCompanyId;
	}

	public String getBillToCompanyId() {
		return billToCompanyId;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

}