package com.tcmis.client.report.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: UserReportCategoryViewBean <br>
 * @version: 1.0, Mar 1, 2006 <br>
 *****************************************************************************/

public class UserReportCategoryViewBean
    extends BaseDataBean {

  private BigDecimal personnelId;
  private String facilityId;
  private String application;
  private String rootCategory;
  private String location;
  private BigDecimal applicationOrder;
  private BigDecimal categoryOrder;
  private BigDecimal locationOrder;

  //constructor
  public UserReportCategoryViewBean() {
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

  public void setRootCategory(String rootCategory) {
    this.rootCategory = rootCategory;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setApplicationOrder(BigDecimal applicationOrder) {
    this.applicationOrder = applicationOrder;
  }

  public void setCategoryOrder(BigDecimal categoryOrder) {
    this.categoryOrder = categoryOrder;
  }

  public void setLocationOrder(BigDecimal locationOrder) {
    this.locationOrder = locationOrder;
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

  public String getRootCategory() {
    return rootCategory;
  }

  public String getLocation() {
    return location;
  }

  public BigDecimal getApplicationOrder() {
    return applicationOrder;
  }

  public BigDecimal getCategoryOrder() {
    return categoryOrder;
  }

  public BigDecimal getLocationOrder() {
    return locationOrder;
  }
}