package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SupplierContactBean <br>
 * @version: 1.0, Aug 28, 2007 <br>
 *****************************************************************************/


public class SupplierContactBean extends BaseDataBean {

	
	private static final long serialVersionUID = -1158377940779664240L;
	private String supplier;
	private BigDecimal contactId;
	private String contactType;
	private String firstName;
	private String lastName;
	private String nickname;
	private String phone;
	private String phoneExtension;
	private String fax;
	private String email;
	private String password;
	private String status;


	//constructor
	public SupplierContactBean() {
	}

	//setters
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setContactId(BigDecimal contactId) {
		this.contactId = contactId;
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
	public void setPhoneExtension(String phoneExtension) {
		this.phoneExtension = phoneExtension;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	//getters
	public String getSupplier() {
		return supplier;
	}
	public BigDecimal getContactId() {
		return contactId;
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
	public String getPhoneExtension() {
		return phoneExtension;
	}
	public String getFax() {
		return fax;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public String getStatus() {
		return status;
	}
}