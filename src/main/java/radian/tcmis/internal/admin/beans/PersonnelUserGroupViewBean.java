package radian.tcmis.internal.admin.beans;

import radian.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PersonnelUserGroupViewBean <br>
 * @version: 1.0, Aug 13, 2004 <br>
 *****************************************************************************/


public class PersonnelUserGroupViewBean extends BaseDataBean {

	private int personnelId;
	private String userGroupId;
	private String facilityId;


	//constructor
	public PersonnelUserGroupViewBean() {
	}

	//setters
	public void setPersonnelId(int personnelId) {
		this.personnelId = personnelId;
	}
	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}


	//getters
	public int getPersonnelId() {
		return personnelId;
	}
	public String getUserGroupId() {
		return userGroupId;
	}
	public String getFacilityId() {
		return facilityId;
	}
}