package com.tcmis.client.het.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: HetPermitBean <br>
 * 
 * @version: 1.0, May 31, 2011 <br>
 *****************************************************************************/

public class HetPermitBean extends BaseDataBean {

	private BigDecimal	areaId;
	private String		areaName;
	private BigDecimal	buildingId;
	private String		buildingName;
	private String		companyId;
	private String		controlDevice;
	private String		description;
	private boolean 	displayStatus;
	private String		facilityId;
	private BigDecimal	permitId;
	private String		permitName;
	private String		status;
	
	// constructor
	public HetPermitBean() {
	}

	public BigDecimal getAreaId() {
		return areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public BigDecimal getBuildingId() {
		return buildingId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	// getters
	public String getCompanyId() {
		return companyId;
	}

	public String getControlDevice() {
		return controlDevice;
	}

	public String getDescription() {
		return description;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public BigDecimal getPermitId() {
		return permitId;
	}

	public String getPermitName() {
		return permitName;
	}

	public String getStatus() {
		return status;
	}

	public boolean isControlDevicePresent() {
		return !StringHandler.isBlankString(controlDevice);
	}

	public boolean isDisplayStatus() {
		return displayStatus;
	}
	
	public boolean isNewPermit() {
		return permitId == null ;
	}

	public void setAreaId(BigDecimal areaId) {
		this.areaId = areaId;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public void setBuildingId(BigDecimal buildingId) {
		this.buildingId = buildingId;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setControlDevice(String controlDevice) {
		this.controlDevice = controlDevice;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDisplayStatus(boolean displayStatus) {
		this.displayStatus = displayStatus;
		status = displayStatus ? "A" : "I";
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setPermitId(BigDecimal permitId) {
		this.permitId = permitId;
	}

	public void setPermitName(String permitName) {
		this.permitName = permitName;
	}

	public void setStatus(String status) {
		this.status = status;
		if ("A".equals(status)){
			displayStatus = true;
		}
		else {
			displayStatus = false;
		}
	}

	
}