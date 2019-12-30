package com.tcmis.client.het.beans;

import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.StringHandler;

public class PermitManagementInputBean extends BaseInputBean {

	private String areaId;
	private String buildingId;
	private String companyId;
	private String facilityId;
	private String workArea;
	private String workAreaGroup;

	private PermitManagementInputBean() {
		super();
	}

	public PermitManagementInputBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	public String getAreaId() {
		return areaId;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getWorkArea() {
		return workArea;
	}

	public String getWorkAreaGroup() {
		return workAreaGroup;
	}

	public boolean hasAreaId() {
		return !StringHandler.isBlankString(areaId);
	}

	public boolean hasBuildingId() {
		return !StringHandler.isBlankString(buildingId);
	}

	public boolean hasWorkArea() {
		return !StringHandler.isBlankString(workArea);
	}
	
	public boolean isDelete() {
		return "delete".equals(getuAction());
	}

	public boolean isSearchForAreas() {
		return "searchForAreas".equals(getuAction());
	}

	public boolean isSearchForBuildings() {
		return "searchForBuildings".equals(getuAction());
	}

	public boolean isSearchForWorkAreas() {
		return "searchForWorkAreas".equals(getuAction());
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
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
		addHiddenFormField("areaId");
		addHiddenFormField("buildingId");
		addHiddenFormField("workArea");
	}

	public void setWorkArea(String workArea) {
		this.workArea = workArea;
	}

	public void setWorkAreaGroup(String workAreaGroup) {
		this.workAreaGroup = workAreaGroup;
	}
}