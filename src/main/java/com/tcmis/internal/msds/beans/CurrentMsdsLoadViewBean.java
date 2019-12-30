package com.tcmis.internal.msds.beans;

import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CurrentMsdsLoadViewBean <br>
 * @version: 1.0, May 23, 2005 <br>
 *****************************************************************************/

public class CurrentMsdsLoadViewBean
    extends BaseDataBean {

  private String customerMsdsNo;
  private String tradeName;
  private String search;
  private String status;
  private String manufacturerName;
  private Date revisionDate;
  private String content;
  private String onLine;
  private String facilityId;
  private String building;
  private String floor;
  private String department;

  //constructor
  public CurrentMsdsLoadViewBean() {
  }

  //setters
  public void setCustomerMsdsNo(String customerMsdsNo) {
    this.customerMsdsNo = customerMsdsNo;
  }

  public void setTradeName(String tradeName) {
    this.tradeName = tradeName;
  }

  public void setSearch(String search) {
    this.search = search;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setManufacturerName(String manufacturerName) {
    this.manufacturerName = manufacturerName;
  }

  public void setRevisionDate(Date revisionDate) {
    this.revisionDate = revisionDate;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setOnLine(String onLine) {
    this.onLine = onLine;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  //getters
  public String getCustomerMsdsNo() {
    return customerMsdsNo;
  }

  public String getTradeName() {
    return tradeName;
  }

  public String getSearch() {
    return search;
  }

  public String getStatus() {
    return status;
  }

  public String getManufacturerName() {
    return manufacturerName;
  }

  public Date getRevisionDate() {
    return revisionDate;
  }

  public String getContent() {
    return content;
  }

  public String getOnLine() {
    return onLine;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getBuilding() {
    return building;
  }

  public String getFloor() {
    return floor;
  }

  public String getDepartment() {
    return department;
  }
}