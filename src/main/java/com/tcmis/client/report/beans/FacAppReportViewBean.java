package com.tcmis.client.report.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: FacAppReportViewBean <br>
 * @version: 1.0, Mar 22, 2006 <br>
 *****************************************************************************/

public class FacAppReportViewBean
    extends BaseDataBean {

  private String companyId;
  private BigDecimal personnelId;
  private String facilityId;
  private String application;
  private String applicationDesc;
  private String facilityName;
  private String status;
  private String reportingEntityLabel;
  private String deliveryPoint;
  private String deliveryPointDesc;
  private String dockLocationId;
  private String dockDesc;
  private String reportingEntityId;
  private String reportingEntityDescription;

  private Collection reportingEntityBeanCollection = new Vector();
  private Collection applicationBeanCollection = new Vector();
  //constructor
  public FacAppReportViewBean() {
  }

  //setters
  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

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

  public void setReportingEntityLabel(String reportingEntityLabel) {
    this.reportingEntityLabel = reportingEntityLabel;
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

  public void setReportingEntityId(String reportingEntityId) {
    this.reportingEntityId = reportingEntityId;
  }

  public void setReportingEntityDescription(String reportingEntityDescription) {
    this.reportingEntityDescription = reportingEntityDescription;
  }

  public void setReportingEntityBeanCollection(Collection c) {
    this.reportingEntityBeanCollection = c;
  }

  public void setApplicationBeanCollection(Collection c) {
    this.applicationBeanCollection = c;
  }

  public void addReportingEntityBean(FacAppReportViewBean bean) {
    this.reportingEntityBeanCollection.add(bean);
  }

  public void addApplicationBean(FacAppReportViewBean bean) {
    this.applicationBeanCollection.add(bean);
  } 
  //getters
  public String getCompanyId() {
    return companyId;
  }

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

  public String getReportingEntityLabel() {
    return reportingEntityLabel;
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

  public String getReportingEntityId() {
    return reportingEntityId;
  }

  public String getReportingEntityDescription() {
    return reportingEntityDescription;
  }

  public Collection getReportingEntityBeanCollection() {
    return this.reportingEntityBeanCollection;
  }

  public Collection getApplicationBeanCollection() {
    return this.applicationBeanCollection;
  }
}