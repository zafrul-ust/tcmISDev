package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: AppManagedUseApprovalBean <br>
 * @version: 1.0, Jul 18, 2006 <br>
 *****************************************************************************/


public class AppManagedUseApprovalBean extends BaseDataBean {

	private String facilityId;
	private String application;
	private String catalogId;
	private String catPartNo;
	private BigDecimal partGroupNo;
	private String userGroupId;
	private BigDecimal limitQuantityPeriod1;
	private BigDecimal daysPeriod1;
	private BigDecimal limitQuantityPeriod2;
	private BigDecimal daysPeriod2;
	private BigDecimal orderQuantity;
	private String orderQuantityRule;
	private BigDecimal estimateOrderQuantityPeriod;
	private String processDesc;
	private BigDecimal approvalId;
	private Date approvalDate;


	//constructor
	public AppManagedUseApprovalBean() {
	}

	//setters
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}
	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
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
	public void setOrderQuantity(BigDecimal orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public void setOrderQuantityRule(String orderQuantityRule) {
		this.orderQuantityRule = orderQuantityRule;
	}
	public void setEstimateOrderQuantityPeriod(BigDecimal estimateOrderQuantityPeriod) {
		this.estimateOrderQuantityPeriod = estimateOrderQuantityPeriod;
	}
	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}
	public void setApprovalId(BigDecimal approvalId) {
		this.approvalId = approvalId;
	}
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}


	//getters
	public String getFacilityId() {
		return facilityId;
	}
	public String getApplication() {
		return application;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}
	public String getUserGroupId() {
		return userGroupId;
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
	public BigDecimal getOrderQuantity() {
		return orderQuantity;
	}
	public String getOrderQuantityRule() {
		return orderQuantityRule;
	}
	public BigDecimal getEstimateOrderQuantityPeriod() {
		return estimateOrderQuantityPeriod;
	}
	public String getProcessDesc() {
		return processDesc;
	}
	public BigDecimal getApprovalId() {
		return approvalId;
	}
	public Date getApprovalDate() {
		return approvalDate;
	}
}