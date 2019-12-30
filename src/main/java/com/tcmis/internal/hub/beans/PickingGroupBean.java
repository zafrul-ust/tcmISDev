package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

public class PickingGroupBean {

	private BigDecimal pickingGroupId;
	private String companyId;
	private String hub;
	private String pickingGroupName;
	private String autoState;
	private String pickingStateDesc;
	private Date lastUpdated;
	private BigDecimal lastUpdatedBy;
	private String lastUpdatedByName;
	private String status;
	private boolean active;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getPickingStateDesc() {
		return pickingStateDesc;
	}

	public void setPickingStateDesc(String pickingStateDesc) {
		this.pickingStateDesc = pickingStateDesc;
	}
}
