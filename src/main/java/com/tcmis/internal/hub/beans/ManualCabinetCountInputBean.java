package com.tcmis.internal.hub.beans;

//import java.math.BigDecimal;
//import java.util.Date;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.client.common.beans.WorkAreaSearchTemplateInputBean;

public class ManualCabinetCountInputBean extends WorkAreaSearchTemplateInputBean {
	private String application;
	private String branchPlant;
	private String[] cabinetIdArray;
	private String companyId;
	private String facilityId;
	public String hubName;
	private String searchArgument;
	private String searchField;
	private String searchMode;
	private String areaId;
	private String bulidingId;
	private String deptId;
	private BigDecimal dateProcessed;
	private boolean includeAckDisburse;
	
	public ManualCabinetCountInputBean() {
	}

	public ManualCabinetCountInputBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	public String getApplication() {
		if(getReportingEntityId() != null && getReportingEntityId().length() > 0)
			return getReportingEntityId();
		
		return application;
	}

	public String getBranchPlant() {
		return branchPlant;
	}

	public String[] getCabinetIdArray() {
		if(getApplicationId() != null && getApplicationId().length() > 0)
			return getApplicationId().split("\\|");
		
		return cabinetIdArray;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getHubName() {
		return hubName;
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
	
	public boolean isIncludeAckDisburse() {
		return includeAckDisburse;
	}
	
	public void setIncludeAckDisburse(boolean includeAckDisburse) {
		this.includeAckDisburse = includeAckDisburse;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}

	public void setCabinetIdArray(String[] cabinetIdArray) {
		this.cabinetIdArray = cabinetIdArray;
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
		addHiddenFormField("applicationId");
		addHiddenFormField("areaId");
        addHiddenFormField("buildingId");
        addHiddenFormField("deptId");
        addHiddenFormField("searchField");
        addHiddenFormField("searchMode");
        addHiddenFormField("searchArgument");
    }

	public void setHubName(String hubName) {
		this.hubName = hubName;
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

	public void setDateProcessed(BigDecimal dateProcessed) {
		this.dateProcessed = dateProcessed;
	}
	
	public BigDecimal getDateProcessed() {
		return dateProcessed;
	}
	
	public String getAreaId() {
		return areaId;
	}

	public String getBulidingId() {
		return bulidingId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public void setBulidingId(String bulidingId) {
		this.bulidingId = bulidingId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

}