package com.tcmis.client.het.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.StringHandler;

public class HetContainerEntryInputBean extends BaseInputBean {
	private String companyId;
	private String facilityId;
	private String searchArgument;
	private String searchField;
	private String searchMode;
	private boolean nonHaasPurchased;
	private BigDecimal workArea;
	private String workAreaGroup;
	private Collection<VvHetApplicationMethodBean> applicationMethods;
	private Collection<HetPermitBean> permits;
	private Collection<VvHetSubstrateBean> substrates;

	//constructor
	public HetContainerEntryInputBean() {
	}

	public HetContainerEntryInputBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	public Collection<VvHetApplicationMethodBean> getApplicationMethods() {
		return applicationMethods;
	}
	public Collection<HetPermitBean> getPermits() {
		return permits;
	}
	public Collection<VvHetSubstrateBean> getSubstrates() {
		return substrates;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getSearchArgument() {
		if (isSearchFieldContainer() && hasSearchArgument()) {
			if (searchArgument.contains("-")) {
				return searchArgument.substring(0, searchArgument.indexOf("-"));
			}
		}
		return searchArgument;
	}

	public String getSearchField() {
		return searchField;
	}

	public boolean isNonHaasPurchased() {
		return nonHaasPurchased;
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
		return this.workArea != null && this.workArea.intValue() != -1;
	}

	public boolean hasWorkAreaGroup() {
		return this.workAreaGroup != null;
	}

	public boolean isSearchFieldContainer() {
		return "RECEIPT_ID".equals(searchField);
	}

	public void setApplicationMethods(Collection<VvHetApplicationMethodBean> applicationMethods) {
		this.applicationMethods = applicationMethods;
	}

	public void setPermits(Collection<HetPermitBean> permits) {
		this.permits = permits;
	}
	public void setSubstrates(Collection<VvHetSubstrateBean> substrates) {
		this.substrates = substrates;
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

	public void setNonHaasPurchased(boolean nonHaasPurchased) {
		this.nonHaasPurchased = nonHaasPurchased;
	}
}
