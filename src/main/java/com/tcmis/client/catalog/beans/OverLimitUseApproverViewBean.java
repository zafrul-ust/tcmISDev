package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: OverLimitUseApproverViewBean <br>
 * @version: 1.0, Aug 22, 2006 <br>
 *****************************************************************************/


public class OverLimitUseApproverViewBean extends BaseDataBean {

	private String facilityId;
	private String application;
	private BigDecimal personnelId;
	private String useApprover;
	private String useApproverEmail;
	private String useApproverPhone;
	private String applicationDesc;


	//constructor
	public OverLimitUseApproverViewBean() {
	}

	//setters
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setUseApprover(String useApprover) {
		this.useApprover = useApprover;
	}
	public void setUseApproverEmail(String useApproverEmail) {
		this.useApproverEmail = useApproverEmail;
	}
	public void setUseApproverPhone(String useApproverPhone) {
		this.useApproverPhone = useApproverPhone;
	}
	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}


	//getters
	public String getFacilityId() {
		return facilityId;
	}
	public String getApplication() {
		return application;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public String getUseApprover() {
		return useApprover;
	}
	public String getUseApproverEmail() {
		return useApproverEmail;
	}
	public String getUseApproverPhone() {
		return useApproverPhone;
	}
	public String getApplicationDesc() {
		return applicationDesc;
	}
}