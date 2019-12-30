package com.tcmis.client.report.beans;

import com.tcmis.common.framework.BaseDataBean;

public class OpenMaterialRequestsInputBean extends BaseDataBean {

	private String facilityId;
	private String uAction;
	private String companyId;
	private String searchText;
	
	public OpenMaterialRequestsInputBean() {
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getuAction() {
		return uAction;
	}

	public void setuAction(String uAction) {
		this.uAction = uAction;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
	
	
	
	

}
