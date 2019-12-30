package com.tcmis.internal.distribution.beans;

import com.tcmis.common.framework.BaseDataBean;

public class CatalogItemSynonymInputBean extends BaseDataBean {
	
	private String searchArgument;
	private String searchField;
    private String searchMode;
    private String uAction;
    
    
	public String getSearchArgument() {
		return searchArgument;
	}
	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getSearchMode() {
		return searchMode;
	}
	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}
	public String getuAction() {
		return uAction;
	}
	public void setuAction(String uAction) {
		this.uAction = uAction;
	}

}
