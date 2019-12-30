package com.tcmis.client.report.beans;

import com.tcmis.common.framework.BaseDataBean;

public class FacilityCatalogBean extends BaseDataBean {
	private String facilityId;
	private String catalogId;
	private String catalogCompanyId;
	private String catalogDesc;
	
//	constructor
	public FacilityCatalogBean() {
	}

	public String getCatalogDesc() {
		return catalogDesc;
	}

	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}
	
	
}