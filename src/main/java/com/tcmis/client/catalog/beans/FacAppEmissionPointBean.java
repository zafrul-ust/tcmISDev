package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

public class FacAppEmissionPointBean extends BaseDataBean {
	private String active;
	private String application;
	private String appEmissionPoint;
	private String appEmissionPointDesc;
	private String companyId;
	private String facilityId;
	private String facEmissionPoint;
	
	public FacAppEmissionPointBean() {
		
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getAppEmissionPoint() {
		return appEmissionPoint;
	}

	public void setAppEmissionPoint(String appEmissionPoint) {
		this.appEmissionPoint = appEmissionPoint;
	}

	public String getAppEmissionPointDesc() {
		return appEmissionPointDesc;
	}

	public void setAppEmissionPointDesc(String appEmissionPointDesc) {
		this.appEmissionPointDesc = appEmissionPointDesc;
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

	public String getFacEmissionPoint() {
		return facEmissionPoint;
	}

	public void setFacEmissionPoint(String facEmissionPoint) {
		this.facEmissionPoint = facEmissionPoint;
	}
	
}