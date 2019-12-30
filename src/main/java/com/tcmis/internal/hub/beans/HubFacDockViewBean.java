package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubFacDockViewBean <br>
 * @version: 1.0, Apr 27, 2005 <br>
 *****************************************************************************/

public class HubFacDockViewBean
    extends BaseDataBean {

  private BigDecimal personnelId;
  private String companyId;
  private String facilityId;
  private String dockLocationId;
  private String branchPlant;
  private String hubName;

  //constructor
  public HubFacDockViewBean() {
  }

  //setters
  public void setPersonnelId(BigDecimal personnelId) {
    this.personnelId = personnelId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setDockLocationId(String dockLocationId) {
    this.dockLocationId = dockLocationId;
  }

  public void setBranchPlant(String branchPlant) {
    this.branchPlant = branchPlant;
  }

  public void setHubName(String hubName) {
    this.hubName = hubName;
  }

  //getters
  public BigDecimal getPersonnelId() {
    return personnelId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getDockLocationId() {
    return dockLocationId;
  }

  public String getBranchPlant() {
    return branchPlant;
  }

  public String getHubName() {
    return hubName;
  }
}