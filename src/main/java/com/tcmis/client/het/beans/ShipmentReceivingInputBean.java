package com.tcmis.client.het.beans;

import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;

public class ShipmentReceivingInputBean extends BaseInputBean {

	private String companyId;
	private String facilityId;
	private String searchArgument;
	private String searchField;
	private String workArea;
	private String workAreaGroup;

	//constructor
	public ShipmentReceivingInputBean() {
		super();
	}

	public ShipmentReceivingInputBean(ActionForm inputForm, Locale locale) {
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

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getWorkArea() {
		return workArea;
	}

	public void setWorkArea(String workArea) {
		this.workArea = workArea;
	}

	public String getWorkAreaGroup() {
		return workAreaGroup;
	}

	public void setWorkAreaGroup(String workAreaGroup) {
		this.workAreaGroup = workAreaGroup;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub

	}


}
