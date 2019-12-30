package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: MsdsAuthorBean <br>
 * @version: 1.0, Jul 02, 2012 <br>
 *****************************************************************************/

public class MsdsAuthorBean extends BaseDataBean {

	private BigDecimal msdsAuthorId;
	private String msdsAuthorDesc;
	private String authorUrl;
	private String phone;
	private String contact;
	private String email;
	private String url;
	private String notes;
	private String countryAbbrev;
	private String directCall;

	//constructor
	public MsdsAuthorBean() {
	}

	public BigDecimal getMsdsAuthorId() {
		return msdsAuthorId;
	}

	public String getMsdsAuthorDesc() {
		return msdsAuthorDesc;
	}
	
	public String getAuthorUrl() {
		return authorUrl;
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
	
	public String getUrl() {
		return url;
	}

	public String getNotes() {
		return notes;
	}

	public String getCountryAbbrev() {
		return countryAbbrev;
	}

	public void setMsdsAuthorId(BigDecimal msdsAuthorId) {
		this.msdsAuthorId = msdsAuthorId;
	}

	public void setMsdsAuthorDesc(String msdsAuthorDesc) {
		this.msdsAuthorDesc = msdsAuthorDesc;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setCountryAbbrev(String countryAbbrev) {
		this.countryAbbrev = countryAbbrev;
	}

	public String getDirectCall() {
		return directCall;
	}

	public void setDirectCall(String directCall) {
		this.directCall = directCall;
	}
	
	public boolean isDirectCallSet() {
		return "Y".equals(directCall);
	}
}	