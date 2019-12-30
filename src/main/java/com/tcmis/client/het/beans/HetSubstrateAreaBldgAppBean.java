package com.tcmis.client.het.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HetSubstrateAreaBldgAppBean <br>
 * 
 * @version: 1.0, Aug 11, 2011 <br>
 *****************************************************************************/

public class HetSubstrateAreaBldgAppBean extends BaseDataBean {

	private BigDecimal	applicationId;
	private BigDecimal	areaId;
	private BigDecimal	buildingId;
	private String		companyId;
	private String		facilityId;
	private BigDecimal	substrateId;

	// constructor
	public HetSubstrateAreaBldgAppBean() {
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
	public BigDecimal getSubstrateId() {
		return substrateId;
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
	public void setSubstrateId(BigDecimal substrateId) {
		this.substrateId = substrateId;
	}

}