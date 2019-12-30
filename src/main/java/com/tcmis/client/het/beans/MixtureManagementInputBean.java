package com.tcmis.client.het.beans;

import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.StringHandler;

public class MixtureManagementInputBean extends BaseInputBean {

	private String companyId;
	private String facilityId;
	private String searchArgument;
	private String searchField;
	private String searchMode;

	private MixtureManagementInputBean() {
		super();
	}

	public MixtureManagementInputBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getSearchArgument() {
		return searchArgument;
	}

	public String getSearchField() {
		return searchField;
	}

	public String getSearchMode() {
		return searchMode;
	}

	public boolean hasSearchArgument() {
		return !StringHandler.isBlankString(searchArgument);
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	@Override
	public void setHiddenFormFields() {
		addHiddenFormField("companyId");
		addHiddenFormField("facilityId");
		addHiddenFormField("searchField");
		addHiddenFormField("searchMode");
		addHiddenFormField("searchArgument");
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

}