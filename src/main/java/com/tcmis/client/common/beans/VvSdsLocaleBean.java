package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class VvSdsLocaleBean extends BaseDataBean {

	private BigDecimal localeId;
	private Date revisionDate;
	private String localeCode;
	private String localeDisplay;
	public BigDecimal getLocaleId() {
		return localeId;
	}
	public void setLocaleId(BigDecimal localeId) {
		this.localeId = localeId;
	}
	public Date getRevisionDate() {
		return revisionDate;
	}
	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}
	public String getLocaleCode() {
		return localeCode;
	}
	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}
	public String getLocaleDisplay() {
		return localeDisplay;
	}
	public void setLocaleDisplay(String localeDisplay) {
		this.localeDisplay = localeDisplay;
	}
	
}
