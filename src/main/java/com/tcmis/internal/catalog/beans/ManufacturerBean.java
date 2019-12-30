package com.tcmis.internal.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ManufacturerBean <br>
 * @version: 1.0, Jan 15, 2008 <br>
 *****************************************************************************/


public class ManufacturerBean extends BaseDataBean {

	private BigDecimal mfgId;
	private String mfgDesc;
	private String mfgUrl;
	private String phone;
	private String contact;
	private String email;
	private String notes;
	private String countryAbbrev;
    private String mfgShortName;


    //constructor
	public ManufacturerBean() {
	}

	//setters
	public void setMfgId(BigDecimal mfgId) {
		this.mfgId = mfgId;
	}
	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}
	public void setMfgUrl(String mfgUrl) {
		this.mfgUrl = mfgUrl;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public void setCountryAbbrev(String countryAbbrev) {
		this.countryAbbrev = countryAbbrev;
	}
	
	//getters
	public BigDecimal getMfgId() {
		return mfgId;
	}
	public String getMfgDesc() {
		return mfgDesc;
	}
	public String getMfgUrl() {
		return mfgUrl;
	}
	public String getPhone() {
		return phone;
	}
	public String getContact() {
		return contact;
	}
	public String getEmail() {
		return email;
	}
	public String getNotes() {
		return notes;
	}
	public String getCountryAbbrev() {
		return countryAbbrev;
	}	
    public String getMfgShortName() {
        return mfgShortName;
    }

    public void setMfgShortName(String mfgShortName) {
        this.mfgShortName = mfgShortName;
    }
} //end of class