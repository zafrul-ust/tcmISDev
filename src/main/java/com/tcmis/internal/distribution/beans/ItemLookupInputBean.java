package com.tcmis.internal.distribution.beans;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.HubBaseInputBean;

public class ItemLookupInputBean extends HubBaseInputBean {

	private String approvedForGroup;
	private String inCatalog;
	private String searchText;

	public ItemLookupInputBean(ActionForm inputForm) {
		super(inputForm);
	}

	public String getApprovedForGroup() {
		return approvedForGroup;
	}

	public String getInCatalog() {
		return inCatalog;
	}

	public String getSearchText() {
		return searchText;
	}

	public boolean isRestrictedToApprovedForGroup() {
		return "true".equals(approvedForGroup);
	}

	public boolean isRestrictedToInCatalog() {
		return "true".equals(inCatalog);
	}

	public boolean isStartNewCount() {
		return "startCount".equals(getuAction());
	}

	public void setApprovedForGroup(String approvedForGroup) {
		this.approvedForGroup = approvedForGroup;
	}

	public void setHiddenFormFields() {
		// TODO Auto-generated method stub

	}

	public void setInCatalog(String inCatalog) {
		this.inCatalog = inCatalog;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}


}