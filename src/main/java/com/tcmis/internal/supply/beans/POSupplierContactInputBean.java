package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class POSupplierContactInputBean extends BaseDataBean 
{
	private static final long serialVersionUID = -4939655731723190142L;
	private String action;	
	private String supplier;
	private String firstName;
	private String lastName;
	private String nickname;
	private String phone;
	private String phoneExtension;
	private String fax;
	private String email;
	private BigDecimal contactId;
	private String contactType;
    private String fromSupplierPriceList;
    private String fromQuickSupplierView;

    public POSupplierContactInputBean()
	{		  
	}


    public String getFromSupplierPriceList() {
        return fromSupplierPriceList;
    }

    public void setFromSupplierPriceList(String fromSupplierPriceList) {
        this.fromSupplierPriceList = fromSupplierPriceList;
    }

    public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}



	public void setAction(String action) {
		this.action = action;
	}



	public String getAction() {
		return action;
	}



	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}



	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}



	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}



	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}



	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}



	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}



	/**
	 * @return the phoneExtension
	 */
	public String getPhoneExtension() {
		return phoneExtension;
	}



	/**
	 * @param phoneExtension the phoneExtension to set
	 */
	public void setPhoneExtension(String phoneExtension) {
		this.phoneExtension = phoneExtension;
	}



	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}



	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}



	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}



	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}



	/**
	 * @return the contactId
	 */
	public BigDecimal getContactId() {
		return contactId;
	}



	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(BigDecimal contactId) {
		this.contactId = contactId;
	}



	/**
	 * @return the contactType
	 */
	public String getContactType() {
		return contactType;
	}



	/**
	 * @param contactType the contactType to set
	 */
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}


	public String getFromQuickSupplierView() {
		return fromQuickSupplierView;
	}


	public void setFromQuickSupplierView(String fromQuickSupplierView) {
		this.fromQuickSupplierView = fromQuickSupplierView;
	}
}
