package com.tcmis.internal.supply.beans;

import com.tcmis.common.framework.BaseDataBean;

import radian.tcmis.common.util.StringHandler;

public class POShipToInputBean extends BaseDataBean 
{
	private String 		submitSearch;
	private String 		searchArgument;
	private String		companyId;

	public POShipToInputBean()
	{		  
	}
	
	public boolean hasCompanyId() {
		return !StringHandler.isBlankString(companyId);
	}

	public String getSubmitSearch() {
		return submitSearch;
	}

	public void setSubmitSearch(String submitSearch) {
		this.submitSearch = submitSearch;
	}

	public String getSearchArgument() {
		return searchArgument;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
