package com.tcmis.internal.distribution.beans;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.HubBaseInputBean;

public class AdditionalChargeInputBean extends HubBaseInputBean {

	private String headerChargesOnly;

	private String lineChargesOnly;
	private String searchField;
	  private String searchMode;
	  private String searchArgument;

	public AdditionalChargeInputBean(ActionForm inputForm) {
		super(inputForm);
	}

	public String getHeaderChargesOnly() {
		return headerChargesOnly;
	}

	public String getLineChargesOnly() {
		return lineChargesOnly;
	}

	public boolean isHeaderOnly() {
		return "on".equals(headerChargesOnly);
	}

	public boolean isLineOnly() {
		return "on".equals(lineChargesOnly);
	}

	public void setHeaderChargesOnly(String headerChargesOnly) {
		this.headerChargesOnly = headerChargesOnly;
	}

	public void setLineChargesOnly(String lineChargesOnly) {
		this.lineChargesOnly = lineChargesOnly;
	}

	@Override
	public void setHiddenFormFields() {
		removeHiddenFormField("hub");
		removeHiddenFormField("inventoryGroup");
		addHiddenFormField("headerChargesOnly");
		addHiddenFormField("lineChargesOnly");
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

	public String getSearchArgument() {
		return searchArgument;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}
	

}