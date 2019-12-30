package com.tcmis.internal.hub.beans;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseDataBean;
//import java.math.BigDecimal;
//import java.util.Date;
import com.tcmis.common.framework.HubBaseInputBean;

public class DotShippingNameInputBean  extends HubBaseInputBean 
{
	private String 		submitSearch;
	private String 		searchArgument;

	public DotShippingNameInputBean(ActionForm inputForm) {
		super(inputForm);
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

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
		
	}
}
