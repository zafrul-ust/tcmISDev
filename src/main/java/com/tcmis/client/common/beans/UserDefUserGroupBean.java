package com.tcmis.client.common.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;


/******************************************************************************
 * CLASSNAME: UserDefUserGroupBean <br>
 * @version: 1.0, Feb 10, 2011 <br>
 *****************************************************************************/


public class UserDefUserGroupBean extends BaseDataBean {

	private String companyId;
	private String facilityId;
	private String userGroupId;
	private String userGroupDesc;
	private String userGroupType;
	private BigDecimal personnelId;
	private String memberName;
	private String role;

	//constructor
	public UserDefUserGroupBean() {
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

	public String getUserGroupType() {
		return userGroupType;
	}

	public void setUserGroupType(String userGroupType) {
		this.userGroupType = userGroupType;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}