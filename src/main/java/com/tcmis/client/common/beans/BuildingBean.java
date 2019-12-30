package com.tcmis.client.common.beans;

import java.util.Collection;
import java.util.Collections;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: BuildingBean <br>
 * @version: 1.0, Jan 11, 2011 <br>
 *****************************************************************************/

public class BuildingBean extends BaseDataBean {

	private String buildingDescription;
	private String buildingId;
	private String buildingName;
	private String companyId;
	private String areaId;
	private Collection<RoomBean> rooms = Collections.EMPTY_LIST;

	//constructor
	public BuildingBean() {
	}

	public String getBuildingDescription() {
		return buildingDescription;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public String getBuildingDisplay() {
		if (hasDescription() && !buildingDescription.equals(buildingName)) {
			return buildingName + " - " + buildingDescription;
		}
		return buildingName;
	}

	//getters
	public String getCompanyId() {
		return companyId;
	}

	public String getAreaId() {
		return areaId;
	}

	public Collection<RoomBean> getRooms() {
		return rooms;
	}

	public boolean isNewBuilding() {
		return StringHandler.isBlankString(buildingId);
	}

	public boolean hasDescription() {
		return !StringHandler.isBlankString(buildingDescription);
	}
	
	public boolean hasName() {
		return !StringHandler.isBlankString(buildingName);
	}

	public void setBuildingDescription(String buildingDescription) {
		this.buildingDescription = buildingDescription;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public void setRooms(Collection<RoomBean> rooms) {
		this.rooms = rooms;
	}
}