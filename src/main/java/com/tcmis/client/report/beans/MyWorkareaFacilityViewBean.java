package com.tcmis.client.report.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: MyWorkareaFacilityViewBean <br>
 * @version: 1.0, Mar 1, 2006 <br>
 *****************************************************************************/

public class MyWorkareaFacilityViewBean
    extends BaseDataBean {

  private BigDecimal personnelId;
  private String facilityId;
  private String application;
  private String applicationDesc;
  private String facilityName;
  private String status;
  private String reportingEntityId;
  private String reportingEntityLabel;
  private String reportingEntityDescription;
  private String deliveryPoint;
  private String deliveryPointDesc;
  private String dockLocationId;
  private String dockDesc;

  //constructor
  public MyWorkareaFacilityViewBean() {
  }

  //setters
  public void setPersonnelId(BigDecimal personnelId) {
    this.personnelId = personnelId;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public void setApplicationDesc(String applicationDesc) {
    this.applicationDesc = applicationDesc;
  }

  public void setFacilityName(String facilityName) {
    this.facilityName = facilityName;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setReportingEntityId(String reportingEntityId) {
    this.reportingEntityId = reportingEntityId;
  }

  public void setReportingEntityLabel(String reportingEntityLabel) {
    this.reportingEntityLabel = reportingEntityLabel;
  }

  public void setReportingEntityDescription(String reportingEntityDescription) {
    this.reportingEntityDescription = reportingEntityDescription;
  }

  public void setDeliveryPoint(String deliveryPoint) {
    this.deliveryPoint = deliveryPoint;
  }

  public void setDeliveryPointDesc(String deliveryPointDesc) {
    this.deliveryPointDesc = deliveryPointDesc;
  }

  public void setDockLocationId(String dockLocationId) {
    this.dockLocationId = dockLocationId;
  }

  public void setDockDesc(String dockDesc) {
    this.dockDesc = dockDesc;
  }

  //getters
  public BigDecimal getPersonnelId() {
    return personnelId;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getApplication() {
    return application;
  }

  public String getApplicationDesc() {
    return applicationDesc;
  }

  public String getFacilityName() {
    return facilityName;
  }

  public String getStatus() {
    return status;
  }

  public String getReportingEntityId() {
    return reportingEntityId;
  }

  public String getReportingEntityLabel() {
    return reportingEntityLabel;
  }

  public String getReportingEntityDescription() {
    return reportingEntityDescription;
  }

  public String getDeliveryPoint() {
    return deliveryPoint;
  }

  public String getDeliveryPointDesc() {
    return deliveryPointDesc;
  }

  public String getDockLocationId() {
    return dockLocationId;
  }

  public String getDockDesc() {
    return dockDesc;
  }
}