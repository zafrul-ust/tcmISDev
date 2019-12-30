package com.tcmis.internal.supply.beans;

import com.tcmis.common.framework.BaseDataBean;
//import java.math.BigDecimal;
//import java.util.Date;

public class POCarrierInputBean extends BaseDataBean 
{
	private String 		searchField;
	private String 		searchMode;
	private String 		searchArgument;

	public POCarrierInputBean()
	{		  
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchArgument() {
		return searchArgument;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public String getSearchMode() {
		return searchMode;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}


}
