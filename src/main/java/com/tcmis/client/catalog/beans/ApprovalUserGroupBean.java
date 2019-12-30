package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ApprovalUserGroupBean <br>
 * @version: 1.0, Jun 9, 2010 <br>
 *****************************************************************************/

public class ApprovalUserGroupBean extends BaseDataBean {

	private String companyId;
 	private String facilityId;
 	private String userGroupId;
	private String userGroupDesc;

 	//constructor
 	public ApprovalUserGroupBean() {
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

	public String getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}

	public String getUserGroupDesc() {
		return userGroupDesc;
	}

	public void setUserGroupDesc(String userGroupDesc) {
		this.userGroupDesc = userGroupDesc;
	}
}