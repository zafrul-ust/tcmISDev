package com.tcmis.client.het.beans;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: HubCabinetViewBean <br>
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/

public class CartManagementInputBean extends BaseInputBean {

	private String companyId;
	private String facilityId;
	private String searchArgument;
	private String searchField;
	private String searchMode;
	private BigDecimal workArea;
	private String workAreaName;
	private String workAreaGroup;


	//constructor
	public CartManagementInputBean() {
	}

	public CartManagementInputBean(ActionForm inputForm, Locale locale) {
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

	public String getSearchCriterion () {
		if ("startswith".equalsIgnoreCase(searchMode)) {
			return SearchCriterion.STARTS_WITH;
		}
		else if ("contains".equalsIgnoreCase(searchMode)) {
			return SearchCriterion.LIKE;
		}
		else {
			return SearchCriterion.EQUALS;
		}
	}

	public String getSearchField() {
		return searchField;
	}

	public String getSearchMode() {
		return searchMode;
	}

	public BigDecimal getWorkArea() {
		return workArea;
	}

	public String getWorkAreaGroup() {
		return workAreaGroup;
	}

	public boolean hasFacility() {
		return !StringHandler.isBlankString(this.facilityId);
	}

	public boolean hasSearchArgument() {
		return !StringHandler.isBlankString(this.searchArgument);
	}

	public boolean hasWorkArea() {
		return this.workArea != null;
	}

	public boolean hasWorkAreaGroup() {
		return this.workAreaGroup != null;
	}

	public boolean isSearchFieldContainer() {
		return "reportingEntityId".equals(searchField);
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
		addHiddenFormField("workAreaGroup");
		addHiddenFormField("facilityId");
		addHiddenFormField("workArea");
		addHiddenFormField("workAreaName");
		addHiddenFormField("searchArgument");
		addHiddenFormField("searchField");
		addHiddenFormField("searchMode");
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

	public void setWorkArea(BigDecimal workArea) {
		this.workArea = workArea;
	}

	public void setWorkAreaGroup(String application) {
		this.workAreaGroup = application;
	}

	public String getWorkAreaName() {
		return workAreaName;
	}

	public void setWorkAreaName(String workAreaName) {
		this.workAreaName = workAreaName;
	}


}