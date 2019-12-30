package com.tcmis.internal.report.beans;

import com.tcmis.common.framework.BaseDataBean;

public class CompanyFacility extends BaseDataBean {
	private String	companyId;
	private String	companyName;
	private String	facilityId;
	private String	facilityName;

	public String getCompanyId() {
		return companyId;
	}

	public String getCompanyName() {
		return companyName;
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

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
}
