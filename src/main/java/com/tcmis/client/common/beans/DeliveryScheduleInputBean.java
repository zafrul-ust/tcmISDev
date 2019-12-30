package com.tcmis.client.common.beans;

import com.tcmis.common.framework.BaseInputBean;

public class DeliveryScheduleInputBean extends BaseInputBean{

	private String company;
	private String facility;
	private String reviewer;
	private String approvedMrsOnly;
	private String searchTerms;
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getFacility() {
		return facility;
	}
	
	public void setFacility(String facility) {
		this.facility = facility;
	}
	
	public String getReviewer() {
		return reviewer;
	}
	
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	
	public String getApprovedMrsOnly() {
		return approvedMrsOnly;
	}
	
	public void setApprovedMrsOnly(String approvedMrsOnly) {
		this.approvedMrsOnly = approvedMrsOnly;
	}
	
	public String getSearchTerms() {
		return searchTerms;
	}

	public void setSearchTerms(String searchTerms) {
		this.searchTerms = searchTerms;
	}

	public void setHiddenFormFields() {
		
	}
}
