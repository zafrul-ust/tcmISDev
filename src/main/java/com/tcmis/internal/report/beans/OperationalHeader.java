package com.tcmis.internal.report.beans;

import com.tcmis.common.framework.BaseDataBean;

public class OperationalHeader extends BaseDataBean {
	private String	companyId;
	private String	facilityId;
	private String	opsEntityId;
	private String	operatingEntityName;

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public String getOperatingEntityName() {
		return operatingEntityName;
	}

	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
}
