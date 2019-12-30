package radian.tcmis.internal.admin.beans;

import radian.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: UserGroupMemberBean <br>
 * @version: 1.0, Nov 12, 2004 <br>
 *****************************************************************************/


public class UserGroupMemberBean extends BaseDataBean {

	private String userGroupId;
	private int personnelId;
	private String facilityId;
	private String companyId;


	//constructor
	public UserGroupMemberBean() {
	}

	//setters
	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}
	public void setPersonnelId(int personnelId) {
		this.personnelId = personnelId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	//getters
	public String getUserGroupId() {
		return userGroupId;
	}
	public int getPersonnelId() {
		return personnelId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getCompanyId() {
		return companyId;
	}
}