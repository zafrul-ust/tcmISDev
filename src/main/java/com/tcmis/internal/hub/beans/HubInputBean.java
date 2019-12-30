package com.tcmis.internal.hub.beans;

import com.tcmis.common.framework.*;

public class HubInputBean
    extends BaseDataBean {
  public HubInputBean() {
  }

  private String hub;
  private String facility;
  private String workArea;
  private String sortBy;

  //setters
  public void setHub(String hub) {
    this.hub = hub;
  }

  public void setFacility(String facility) {
    this.facility = facility;
  }

  public void setWorkArea(String workArea) {
    this.workArea = workArea;
  }

  public void setSortBy(String sortBy) {
    this.sortBy = sortBy;
  }

//getters
  public String getHub() {
    return this.hub;
  }

  public String getFacility() {
    return this.facility;
  }

  public String getWorkArea() {
    return this.workArea;
  }

  public String getSortBy() {
    return this.sortBy;
  }

}