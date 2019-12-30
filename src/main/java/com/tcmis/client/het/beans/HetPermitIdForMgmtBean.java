package com.tcmis.client.het.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HetPermitIdForMgmtBean <br>
 * @version: 1.0, Aug 8, 2011 <br>
 *****************************************************************************/

public class HetPermitIdForMgmtBean extends BaseDataBean {

	private String applicationDesc;
	private BigDecimal applicationId;
	private BigDecimal areaId;
	private String areaName;
	private BigDecimal buildingId;
	private String buildingName;
	private String companyId;
	private String controlDevice;
	private boolean displayStatus = false;
	private String facilityId;
	private BigDecimal permitId;
	private String permitName;
	private String reportingEntityId;
	private String status;

	//constructor
	public HetPermitIdForMgmtBean() {
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public BigDecimal getApplicationId() {
		return applicationId;
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

	//getters
	public String getCompanyId() {
		return companyId;
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

	public String getReportingEntityId() {
		return reportingEntityId;
	}

	public String getStatus() {
		return status;
	}

	public String getControlDevice() {
		return controlDevice;
	}

	public boolean isDisplayStatus() {
		return displayStatus;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
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

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setControlDevice(String controlDevice) {
		this.controlDevice = controlDevice;
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

	public void setReportingEntityId(String reportingEntityId) {
		this.reportingEntityId = reportingEntityId;
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