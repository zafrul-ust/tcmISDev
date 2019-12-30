package com.tcmis.ws.scanner.beans;

import com.tcmis.common.framework.BaseDataBean;

public class Facility extends BaseDataBean {
	String	companyId;
	String	facilityId;
	String	facilityName;

	public String getCompanyId() {
		return companyId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
}
