package com.tcmis.internal.hub.beans;

import java.util.*;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: HubBean <br>
 * @version: 1.0, Jun 1, 2004 <br>
 *****************************************************************************/

public class HubBean
    extends BaseDataBean {

  private String companyId;
  private String branchPlant;
  private String eGlSegment2;
  private String hubName;
  private int testingDays;
  private String locationId;
  private String facilityId;
  private String bldgId;
  private String storageId;
  private Collection facilityBeanCollection;

  //constructor
  public HubBean() {
  }

  //setters
  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setBranchPlant(String branchPlant) {
    this.branchPlant = branchPlant;
  }

  public void setEGlSegment2(String eGlSegment2) {
    this.eGlSegment2 = eGlSegment2;
  }

  public void setHubName(String hubName) {
    this.hubName = hubName;
  }

  public void setTestingDays(int testingDays) {
    this.testingDays = testingDays;
  }

  public void setLocationId(String locationId) {
    this.locationId = locationId;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setBldgId(String bldgId) {
    this.bldgId = bldgId;
  }

  public void setStorageId(String storageId) {
    this.storageId = storageId;
  }

  public void setFacilityBeanCollection(Collection c) {
    this.facilityBeanCollection = c;
  }

  //getters
  public String getCompanyId() {
    return companyId;
  }

  public String getBranchPlant() {
    return branchPlant;
  }

  public String getEGlSegment2() {
    return eGlSegment2;
  }

  public String getHubName() {
    return hubName;
  }

  public int getTestingDays() {
    return testingDays;
  }

  public String getLocationId() {
    return locationId;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getBldgId() {
    return bldgId;
  }

  public String getStorageId() {
    return storageId;
  }

  public Collection getFacilityBeanCollection() {
    return facilityBeanCollection;
  }

}