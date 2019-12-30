package com.tcmis.internal.supply.beans;

import com.tcmis.common.framework.BaseDataBean;

public class PoApprovalCode extends BaseDataBean {

	private String opsEntityId;
	private String poApprovalStatus;
	private String poApprovalCode;
	private String poApprovalDescription;

	public String getPoApprovalStatus() {
		return poApprovalStatus;
	}

	public void setPoApprovalStatus(String poApprovalStatus) {
		this.poApprovalStatus = poApprovalStatus;
	}

	public String getPoApprovalCode() {
		return poApprovalCode;
	}

	public void setPoApprovalCode(String poApprovalCode) {
		this.poApprovalCode = poApprovalCode;
	}

	public String getPoApprovalDescription() {
		return poApprovalDescription;
	}

	public void setPoApprovalDescription(String poApprovalDescription) {
		this.poApprovalDescription = poApprovalDescription;
	}
	
	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
}
