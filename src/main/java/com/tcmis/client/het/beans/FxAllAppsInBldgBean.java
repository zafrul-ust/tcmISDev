package com.tcmis.client.het.beans;

import java.math.BigDecimal;
import java.util.Collection;

import com.tcmis.common.framework.BaseDataBean;

public class FxAllAppsInBldgBean extends BaseDataBean  {

	private BigDecimal buildingId;
	private BigDecimal applicationId;
	private String applicationDesc;

	//constructor
	public FxAllAppsInBldgBean() {
	}
	
	public FxAllAppsInBldgBean(HetContainerInventoryViewBean container) {
		this.applicationDesc = container.getApplicationDesc();
		this.applicationId = container.getApplicationId();
	}
	
	public String getApplicationDesc() {
		return applicationDesc;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public BigDecimal getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}

	public BigDecimal getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(BigDecimal buildingId) {
		this.buildingId = buildingId;
	}


	
}
