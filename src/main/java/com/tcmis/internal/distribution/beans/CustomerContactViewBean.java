package com.tcmis.internal.distribution.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerContactViewBean <br>
 * @version: 1.0, Aug 27, 2009 <br>
 *****************************************************************************/


public class CustomerContactViewBean extends BaseDataBean {

	private BigDecimal customerId;
	private BigDecimal contactPersonnelId;
	private String contactType;
	private String nickname;
	private String status;
	private String purchasing;
	private String accountsPayable;
	private String receiving;
	private String qualityAssurance;
	private String management;
	private String firstName;
	private String lastName;
	private String midInitial;
	private String email;
	private String phone;
	private String altPhone;
	private String pager;
	private String fax;
	private String title;
	private String defaultContact;


	//constructor
	public CustomerContactViewBean() {
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
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setMidInitial(String midInitial) {
		this.midInitial = midInitial;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setAltPhone(String altPhone) {
		this.altPhone = altPhone;
	}
	public void setPager(String pager) {
		this.pager = pager;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public void setTitle(String title) {
		this.title = title;
	}


	//getters
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public BigDecimal getContactPersonnelId() {
		return contactPersonnelId;
	}
	public String getContactType() {
		return contactType;
	}
	public String getNickname() {
		return nickname;
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
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getMidInitial() {
		return midInitial;
	}
	public String getEmail() {
		return email;
	}
	public String getPhone() {
		return phone;
	}
	public String getAltPhone() {
		return altPhone;
	}
	public String getPager() {
		return pager;
	}
	public String getFax() {
		return fax;
	}
	public String getTitle() {
		return title;
	}

	public String getDefaultContact() {
		return defaultContact;
	}

	public void setDefaultContact(String defaultContact) {
		this.defaultContact = defaultContact;
	}
}