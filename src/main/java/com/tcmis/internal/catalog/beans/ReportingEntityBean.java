package com.tcmis.internal.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: ReportingEntityBean <br>
 * @version: 1.0, Jan 4, 2011 <br>
 *****************************************************************************/

public class ReportingEntityBean extends BaseDataBean {

	private String companyId;
	private String description;
	private String facilityId;
	private String facilityName;
	private String reportingEntityId;
	private boolean updated = false;

	//constructor
	public ReportingEntityBean() {
	}

	//getters
	public String getCompanyId() {
		return companyId;
	}

	public String getDescription() {
		return description;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public String getReportingEntityId() {
		return reportingEntityId;
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public void setReportingEntityId(String reportingEntityId) {
		this.reportingEntityId = reportingEntityId;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public boolean isNewGroup () {
		return StringHandler.isBlankString(reportingEntityId);
	}
}