package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseInputBean;

@SuppressWarnings("serial")
public class PickingGroupMgmtInputBean extends BaseInputBean {

	private String opsEntityId;
	private String companyId;
	private String sourceHub;
	private String uAction;
	private BigDecimal pickingGroupId;
	private String pickingState;
	private boolean activeOnly;
	
	public boolean isInactivateAction() {
		return "inactivate".equals(uAction);
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getSourceHub() {
		return sourceHub;
	}

	public void setSourceHub(String sourceHub) {
		this.sourceHub = sourceHub;
	}

	public String getuAction() {
		return uAction;
	}

	public void setuAction(String uAction) {
		this.uAction = uAction;
	}

	public BigDecimal getPickingGroupId() {
		return pickingGroupId;
	}

	public void setPickingGroupId(BigDecimal pickingGroupId) {
		this.pickingGroupId = pickingGroupId;
	}

	public String getPickingState() {
		return pickingState;
	}

	public void setPickingState(String pickingState) {
		this.pickingState = pickingState;
	}

	public boolean isActiveOnly() {
		return activeOnly;
	}

	public void setActiveOnly(boolean activeOnly) {
		this.activeOnly = activeOnly;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
		
	}
}
