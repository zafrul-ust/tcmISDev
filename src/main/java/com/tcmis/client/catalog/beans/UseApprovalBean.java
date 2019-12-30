package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: UseApprovalBean <br>
 * @version: 1.0, Oct 23, 2006 <br>
 *****************************************************************************/

public class UseApprovalBean
    extends BaseDataBean {

  private String userGroupId;
  private String facilityId;
  private String facPartNo;
  private Date expireDate;
  private String application;
  private BigDecimal approvalId;
  private String approvalStatus;
  private Date reviewedDate;
  private String appGroup;
  private String catalogId;
  private String companyId;
  private BigDecimal partGroupNo;
  private BigDecimal limitQuantityPeriod1;
  private BigDecimal daysPeriod1;
  private BigDecimal limitQuantityPeriod2;
  private BigDecimal daysPeriod2;
  private String inventoryGroup;
  private String processDesc;
  private BigDecimal orderQuantity;
  private String orderQuantityRule;
  private BigDecimal estimateOrderQuantityPeriod;
  private String catalogCompanyId;

  //constructor
  public UseApprovalBean() {
  }

  //setters
  public void setUserGroupId(String userGroupId) {
    this.userGroupId = userGroupId;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setFacPartNo(String facPartNo) {
    this.facPartNo = facPartNo;
  }

  public void setExpireDate(Date expireDate) {
    this.expireDate = expireDate;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public void setApprovalId(BigDecimal approvalId) {
    this.approvalId = approvalId;
  }

  public void setApprovalStatus(String approvalStatus) {
    this.approvalStatus = approvalStatus;
  }

  public void setReviewedDate(Date reviewedDate) {
    this.reviewedDate = reviewedDate;
  }

  public void setAppGroup(String appGroup) {
    this.appGroup = appGroup;
  }

  public void setCatalogId(String catalogId) {
    this.catalogId = catalogId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setPartGroupNo(BigDecimal partGroupNo) {
    this.partGroupNo = partGroupNo;
  }

  public void setLimitQuantityPeriod1(BigDecimal limitQuantityPeriod1) {
    this.limitQuantityPeriod1 = limitQuantityPeriod1;
  }

  public void setDaysPeriod1(BigDecimal daysPeriod1) {
    this.daysPeriod1 = daysPeriod1;
  }

  public void setLimitQuantityPeriod2(BigDecimal limitQuantityPeriod2) {
    this.limitQuantityPeriod2 = limitQuantityPeriod2;
  }

  public void setDaysPeriod2(BigDecimal daysPeriod2) {
    this.daysPeriod2 = daysPeriod2;
  }

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setProcessDesc(String processDesc) {
    this.processDesc = processDesc;
  }

  public void setOrderQuantity(BigDecimal orderQuantity) {
    this.orderQuantity = orderQuantity;
  }

  public void setOrderQuantityRule(String orderQuantityRule) {
    this.orderQuantityRule = orderQuantityRule;
  }

  public void setEstimateOrderQuantityPeriod(BigDecimal
                                             estimateOrderQuantityPeriod) {
    this.estimateOrderQuantityPeriod = estimateOrderQuantityPeriod;
  }

  //getters
  public String getUserGroupId() {
    return userGroupId;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getFacPartNo() {
    return facPartNo;
  }

  public Date getExpireDate() {
    return expireDate;
  }

  public String getApplication() {
    return application;
  }

  public BigDecimal getApprovalId() {
    return approvalId;
  }

  public String getApprovalStatus() {
    return approvalStatus;
  }

  public Date getReviewedDate() {
    return reviewedDate;
  }

  public String getAppGroup() {
    return appGroup;
  }

  public String getCatalogId() {
    return catalogId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public BigDecimal getPartGroupNo() {
    return partGroupNo;
  }

  public BigDecimal getLimitQuantityPeriod1() {
    return limitQuantityPeriod1;
  }

  public BigDecimal getDaysPeriod1() {
    return daysPeriod1;
  }

  public BigDecimal getLimitQuantityPeriod2() {
    return limitQuantityPeriod2;
  }

  public BigDecimal getDaysPeriod2() {
    return daysPeriod2;
  }

  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public String getProcessDesc() {
    return processDesc;
  }

  public BigDecimal getOrderQuantity() {
    return orderQuantity;
  }

  public String getOrderQuantityRule() {
    return orderQuantityRule;
  }

  public BigDecimal getEstimateOrderQuantityPeriod() {
    return estimateOrderQuantityPeriod;
  }

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}
}