package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class PickingGroup extends BaseDataBean {

	private BigDecimal pickingGroupId;
	private String companyId;
	private String hub;
	private String pickingGroupName;
	private String autoState;
	private Date lastUpdated;
	private BigDecimal lastUpdatedBy;
	private String lastUpdatedByName;
	
	public PickingGroup() {
		
	}

	public BigDecimal getPickingGroupId() {
		return pickingGroupId;
	}

	public void setPickingGroupId(BigDecimal pickingGroupId) {
		this.pickingGroupId = pickingGroupId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getHub() {
		return hub;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public String getPickingGroupName() {
		return pickingGroupName;
	}

	public void setPickingGroupName(String pickingGroupName) {
		this.pickingGroupName = pickingGroupName;
	}

	public String getAutoState() {
		return autoState;
	}

	public void setAutoState(String autoState) {
		this.autoState = autoState;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public BigDecimal getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(BigDecimal lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdatedByName() {
		return lastUpdatedByName;
	}

	public void setLastUpdatedByName(String lastUpdatedByName) {
		this.lastUpdatedByName = lastUpdatedByName;
	}
}
