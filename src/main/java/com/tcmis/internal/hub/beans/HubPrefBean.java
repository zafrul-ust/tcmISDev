package com.tcmis.internal.hub.beans;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: HubPrefBean <br>
 * @version: 1.0, Jun 9, 2004 <br>
 *****************************************************************************/

public class HubPrefBean
    extends BaseDataBean {

  private String companyId;
  private int personnelId;
  private String facilityId;
  private String hub;
  private int priority;

  //constructor
  public HubPrefBean() {
  }

  //setters
  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setPersonnelId(int personnelId) {
    this.personnelId = personnelId;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setHub(String hub) {
    this.hub = hub;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  //getters
  public String getCompanyId() {
    return companyId;
  }

  public int getPersonnelId() {
    return personnelId;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getHub() {
    return hub;
  }

  public int getPriority() {
    return priority;
  }
}