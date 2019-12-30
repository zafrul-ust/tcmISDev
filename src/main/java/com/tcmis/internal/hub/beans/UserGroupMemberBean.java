package com.tcmis.internal.hub.beans;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: UserGroupMemberBean <br>
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/

public class UserGroupMemberBean
    extends BaseDataBean {

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