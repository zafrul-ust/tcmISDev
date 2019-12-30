package com.tcmis.client.het.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FxMixtureIdMgmtSearchBean <br>
 * @version: 1.0, Feb 10, 2012 <br>
 *****************************************************************************/


public class FxMixtureIdMgmtSearchBean extends BaseDataBean {

	private String companyId;
	private String facilityId;
	private BigDecimal mixtureId;
	private String mixtureName;
	private BigDecimal applicationId;
	private String applicationDesc;
	private BigDecimal areaId;
	private String areaName;
	private BigDecimal buildingId;
	private String buildingName;
	private String reportingEntityId;
	
	private BigDecimal originalAreaId;
	private BigDecimal originalBuildingId;
	private BigDecimal originalApplicationId;
	private String status;
	

	//constructor
	public FxMixtureIdMgmtSearchBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}
	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
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
	public void setReportingEntityId(String reportingEntityId) {
		this.reportingEntityId = reportingEntityId;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public BigDecimal getApplicationId() {
		return applicationId;
	}
	public String getApplicationDesc() {
		return applicationDesc;
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
	public String getReportingEntityId() {
		return reportingEntityId;
	}

	public BigDecimal getMixtureId() {
		return mixtureId;
	}

	public void setMixtureId(BigDecimal mixtureId) {
		this.mixtureId = mixtureId;
	}

	public String getMixtureName() {
		return mixtureName;
	}

	public void setMixtureName(String mixtureName) {
		this.mixtureName = mixtureName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getOriginalApplicationId() {
		return originalApplicationId;
	}

	public void setOriginalApplicationId(BigDecimal originalApplicationId) {
		this.originalApplicationId = originalApplicationId;
	}

	public BigDecimal getOriginalAreaId() {
		return originalAreaId;
	}

	public void setOriginalAreaId(BigDecimal originalAreaId) {
		this.originalAreaId = originalAreaId;
	}

	public BigDecimal getOriginalBuildingId() {
		return originalBuildingId;
	}

	public void setOriginalBuildingId(BigDecimal originalBuildingId) {
		this.originalBuildingId = originalBuildingId;
	}

}