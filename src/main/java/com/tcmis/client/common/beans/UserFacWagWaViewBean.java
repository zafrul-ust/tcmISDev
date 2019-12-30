package com.tcmis.client.common.beans;

import java.util.Date;
import java.util.Collection;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: UserFacWagWaViewBean <br>
 * @version: 1.0, Jan 6, 2011 <br>
 *****************************************************************************/


public class UserFacWagWaViewBean extends BaseDataBean {

	private BigDecimal personnelId;
	private String companyId;
	private String facilityId;
	private String facilityName;
	private String reportingEntityId;
	private String reportingEntityDesc;
	private String application;
	private String applicationDesc;
	private String status;
	private String manualMrCreation;
	private String allowStocking;
	private Collection workAreaGroupColl;
	private Collection workAreaColl;
	private Collection facilityWorkAreaColl;
	private String reportingEntityStatus;
	private BigDecimal applicationId;


	//constructor
	public UserFacWagWaViewBean() {
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getReportingEntityId() {
		return reportingEntityId;
	}

	public void setReportingEntityId(String reportingEntityId) {
		this.reportingEntityId = reportingEntityId;
	}

	public String getReportingEntityDesc() {
		return reportingEntityDesc;
	}

	public void setReportingEntityDesc(String reportingEntityDesc) {
		this.reportingEntityDesc = reportingEntityDesc;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getManualMrCreation() {
		return manualMrCreation;
	}

	public void setManualMrCreation(String manualMrCreation) {
		this.manualMrCreation = manualMrCreation;
	}

	public String getAllowStocking() {
		return allowStocking;
	}

	public void setAllowStocking(String allowStocking) {
		this.allowStocking = allowStocking;
	}

	public Collection getWorkAreaGroupColl() {
		return workAreaGroupColl;
	}

	public void setWorkAreaGroupColl(Collection workAreaGroupColl) {
		this.workAreaGroupColl = workAreaGroupColl;
	}

	public Collection getWorkAreaColl() {
		return workAreaColl;
	}

	public void setWorkAreaColl(Collection workAreaColl) {
		this.workAreaColl = workAreaColl;
	}

	public Collection getFacilityWorkAreaColl() {
		return facilityWorkAreaColl;
	}

	public void setFacilityWorkAreaColl(Collection facilityWorkAreaColl) {
		this.facilityWorkAreaColl = facilityWorkAreaColl;
	}

	public String getReportingEntityStatus() {
		return reportingEntityStatus;
	}

	public void setReportingEntityStatus(String reportingEntityStatus) {
		this.reportingEntityStatus = reportingEntityStatus;
	}

	public BigDecimal getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}
}