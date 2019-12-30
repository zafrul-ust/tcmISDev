package com.tcmis.client.het.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HetPermitAreaBldgAppBean <br>
 * 
 * @version: 1.0, Aug 9, 2011 <br>
 *****************************************************************************/

public class HetPermitAreaBldgAppBean extends BaseDataBean {

	private BigDecimal	applicationId;
	private BigDecimal	areaId;
	private BigDecimal	buildingId;
	private String		companyId;
	private String		facilityId;
	private BigDecimal	permitId;

	// constructor
	public HetPermitAreaBldgAppBean() {
	}

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

	// getters
	public BigDecimal getPermitId() {
		return permitId;
	}

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

	// setters
	public void setPermitId(BigDecimal permitId) {
		this.permitId = permitId;
	}
}