package com.tcmis.client.het.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HetBuildingApplicationViewBean <br>
 * @version: 1.0, Aug 10, 2011 <br>
 *****************************************************************************/

public class HetBuildingApplicationViewBean extends BaseDataBean {

	private String application;
	private String applicationDesc;
	private BigDecimal applicationId;
	private BigDecimal areaId;
	private BigDecimal buildingId;
	private String companyId;
	private String facilityId;

	//constructor
	public HetBuildingApplicationViewBean() {
	}

	public String getApplication() {
		return application;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	//getters
	public BigDecimal getApplicationId() {
		return applicationId;
	}

	public BigDecimal getAreaId() {
		return areaId;
	}

	public BigDecimal getBuildingId() {
		return buildingId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	//setters
	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}

	public void setAreaId(BigDecimal areaId) {
		this.areaId = areaId;
	}

	public void setBuildingId(BigDecimal buildingId) {
		this.buildingId = buildingId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
}