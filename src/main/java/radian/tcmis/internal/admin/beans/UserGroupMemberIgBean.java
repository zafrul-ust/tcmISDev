package radian.tcmis.internal.admin.beans;

import radian.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: UserGroupMemberIgBean <br>
 * @version: 1.0, Sep 16, 2004 <br>
 *****************************************************************************/


public class UserGroupMemberIgBean extends BaseDataBean {

	private String userGroupId;
	private int personnelId;
	private String facilityId;
	private String companyId;
	private String inventoryGroup;


	//constructor
	public UserGroupMemberIgBean() {
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
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
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
	public String getInventoryGroup() {
		return inventoryGroup;
	}
}