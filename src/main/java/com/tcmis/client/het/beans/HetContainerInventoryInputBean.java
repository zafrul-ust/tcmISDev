package com.tcmis.client.het.beans;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.StringHandler;

public class HetContainerInventoryInputBean extends BaseInputBean {

	private static final BigDecimal negOne = new BigDecimal("-1");
	private String companyId;
	private String facilityId;
	private BigDecimal materialId = null;
	private String searchArgument;
	private String searchField;
	private String searchMode;
	private BigDecimal workArea = null;
	private String workAreaGroup;

	//constructor
	public HetContainerInventoryInputBean() {
	}

	public HetContainerInventoryInputBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public BigDecimal getMaterialId() {
		return materialId;
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
		return !StringHandler.isBlankString(this.facilityId) ;
	}

	public boolean hasSearchArgument() {
		return !StringHandler.isBlankString(this.searchArgument);
	}

	public boolean hasWorkArea() {
		return this.workArea != null && !negOne.equals(this.workArea);
	}

	public boolean hasWorkAreaGroup() {
		return !StringHandler.isBlankString(this.workAreaGroup) && !"-1".equals(this.workAreaGroup);
	}

	public boolean isDelete() {
		return "Delete".equalsIgnoreCase(uAction);
	}

	public boolean isGetTransferWorkAreas() {
		return "getTransferWorkAreas".equalsIgnoreCase(uAction);
	}

	public boolean isSearchFieldContainer() {
		return "reportingEntityId".equals(searchField);
	}

	public boolean isTransfer() {
		return "transfer".equalsIgnoreCase(uAction);
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

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
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
}
