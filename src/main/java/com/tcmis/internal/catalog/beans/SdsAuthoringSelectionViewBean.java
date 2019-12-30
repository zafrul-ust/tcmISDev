package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class SdsAuthoringSelectionViewBean extends BaseDataBean {

	private boolean available;
	private boolean grab;
	private String localeCode;
	private BigDecimal localeId;
	private String localeDisplay;
	private String companyId;
	private String facilityId;
	
	public boolean isAvailable() {
		return available;
	}
	
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	public boolean isGrab() {
		return grab;
	}
	
	public void setGrab(boolean grab) {
		this.grab = grab;
	}
	
	public String getLocaleCode() {
		return localeCode;
	}
	
	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public BigDecimal getLocaleId() {
		return localeId;
	}

	public void setLocaleId(BigDecimal localeId) {
		this.localeId = localeId;
	}

	public String getLocaleDisplay() {
		return localeDisplay;
	}

	public void setLocaleDisplay(String localeDisplay) {
		this.localeDisplay = localeDisplay;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
}
