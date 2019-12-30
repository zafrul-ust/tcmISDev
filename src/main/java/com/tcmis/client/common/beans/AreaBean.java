package com.tcmis.client.common.beans;

import java.util.Collection;
import java.util.Collections;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: BuildingBean <br>
 * @version: 1.0, Jan 11, 2011 <br>
 *****************************************************************************/

public class AreaBean extends BaseDataBean {

	private String facilityId;
	private String areaId;
	private String companyId;
	private String areaName;
	private String areaDescription;
	private String organization;
	private Collection<BuildingBean> buildings = Collections.EMPTY_LIST;

	//constructor
	public AreaBean() {
	}

	public String getFacilityId() {
		return facilityId;
	}

	//getters
	public String getAreaId() {
		return areaId;
	}

	public String getAreaName() {
		return areaName;
	}
	
	public String getAreaDescription() {
		return areaDescription;
	}
	
	public String getCompanyId() {
		return companyId;
	}

	public String getOrganization() {
		return organization;
	}

	public Collection<BuildingBean> getBuildings() {
		return buildings;
	}

	public boolean isNewArea() {
		return StringHandler.isBlankString(areaId);
	}

	public boolean hasDescription() {
		return !StringHandler.isBlankString(areaDescription);
	}
	
	public boolean hasName() {
		return !StringHandler.isBlankString(areaName);
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	public void setAreaDescription(String areaDescription) {
		this.areaDescription = areaDescription;
	}
	
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public void setBuildings(Collection<BuildingBean> buildings) {
		this.buildings = buildings;
	}
}