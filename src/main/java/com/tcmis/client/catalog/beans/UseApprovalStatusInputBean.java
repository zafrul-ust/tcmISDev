package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.framework.BaseInputBean;

/******************************************************************************
 * CLASSNAME: UseApprovalStatusViewBean <br>
 * @version: 1.0, Feb 13, 2006 <br>
 *****************************************************************************/

public class UseApprovalStatusInputBean
 extends BaseInputBean {

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
 private BigDecimal partGroupNo;
 private BigDecimal limitQuantityPeriod1;
 private BigDecimal daysPeriod1;
 private BigDecimal limitQuantityPeriod2;
 private BigDecimal daysPeriod2;
 private BigDecimal orderQuantity;
 private String orderQuantityRule;
 private BigDecimal itemId;
 private String packaging;
 private String haasShiptoCompanyId;
 private String dockLocationId;
 private String dockDeliveryPoint;
 private String deliveryPointBarcode;
 private String customerDeliverTo;
 private BigDecimal barcodeRequester;
 private String barcodeRequesterName;
 private String searchText;
 private String showApprovedOnly;
 private String submitSearch;
 private String submitUpdate;
 private String sortBy;
 private String updateManagedUseApproval;
 private String submitShowUseApprovers;
 private String showActiveOnly;
 private String updateAllRows;
 private String buttonCreateExcel;
 private String showOnlyWithLimits;
 private String catalogCompanyId;
 private String uAction;

 //constructor
 public UseApprovalStatusInputBean() {
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

 public void setOrderQuantity(BigDecimal orderQuantity) {
	this.orderQuantity = orderQuantity;
 }

 public void setOrderQuantityRule(String orderQuantityRule) {
	this.orderQuantityRule = orderQuantityRule;
 }

 public void setItemId(BigDecimal itemId) {
	this.itemId = itemId;
 }

 public void setPackaging(String packaging) {
	this.packaging = packaging;
 }

 public void setHaasShiptoCompanyId(String haasShiptoCompanyId) {
	this.haasShiptoCompanyId = haasShiptoCompanyId;
 }

 public void setDockLocationId(String dockLocationId) {
	this.dockLocationId = dockLocationId;
 }

 public void setDockDeliveryPoint(String dockDeliveryPoint) {
	this.dockDeliveryPoint = dockDeliveryPoint;
 }

 public void setDeliveryPointBarcode(String deliveryPointBarcode) {
	this.deliveryPointBarcode = deliveryPointBarcode;
 }

 public void setCustomerDeliverTo(String customerDeliverTo) {
	this.customerDeliverTo = customerDeliverTo;
 }

 public void setBarcodeRequester(BigDecimal barcodeRequester) {
	this.barcodeRequester = barcodeRequester;
 }

 public void setBarcodeRequesterName(String barcodeRequesterName) {
	this.barcodeRequesterName = barcodeRequesterName;
 }

 public void setSearchText(String searchText) {
	this.searchText = searchText;
 }

 public void setShowApprovedOnly(String showApprovedOnly) {
	this.showApprovedOnly = showApprovedOnly;
 }

 public void setSubmitSearch(String submitSearch) {
	this.submitSearch = submitSearch;
 }

 public void setSubmitUpdate(String submitUpdate) {
	this.submitUpdate = submitUpdate;
 }

 public void setSortBy(String sortBy) {
	this.sortBy = sortBy;
 }

 public void setUpdateManagedUseApproval(String updateManagedUseApproval) {
	this.updateManagedUseApproval = updateManagedUseApproval;
 }

 public void setSubmitShowUseApprovers(String submitShowUseApprovers) {
	this.submitShowUseApprovers = submitShowUseApprovers;
 }

 public void setShowActiveOnly(String showActiveOnly) {
	this.showActiveOnly = showActiveOnly;
 }

 public void setUpdateAllRows(String updateAllRows) {
	this.updateAllRows = updateAllRows;
 }

 public void setButtonCreateExcel(String buttonCreateExcel) {
	this.buttonCreateExcel = buttonCreateExcel;
 }

 public void setShowOnlyWithLimits(String showOnlyWithLimits) {
	this.showOnlyWithLimits = showOnlyWithLimits;
 }

 public void setCatalogCompanyId(String catalogCompanyId) {
	 this.catalogCompanyId = catalogCompanyId;
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

 public BigDecimal getOrderQuantity() {
	return orderQuantity;
 }

 public String getOrderQuantityRule() {
	return orderQuantityRule;
 }

 public BigDecimal getItemId() {
	return itemId;
 }

 public String getPackaging() {
	return packaging;
 }

 public String getHaasShiptoCompanyId() {
	return haasShiptoCompanyId;
 }

 public String getDockLocationId() {
	return dockLocationId;
 }

 public String getDockDeliveryPoint() {
	return dockDeliveryPoint;
 }

 public String getDeliveryPointBarcode() {
	return deliveryPointBarcode;
 }

 public String getCustomerDeliverTo() {
	return customerDeliverTo;
 }

 public BigDecimal getBarcodeRequester() {
	return barcodeRequester;
 }

 public String getBarcodeRequesterName() {
	return barcodeRequesterName;
 }

 public String getSearchText() {
	return searchText;
 }

 public String getShowApprovedOnly() {
	return showApprovedOnly;
 }

 public String getSubmitSearch() {
	return submitSearch;
 }

 public String getSubmitUpdate() {
	return submitUpdate;
 }

 public String getSortBy() {
	return sortBy;
 }

 public String getUpdateManagedUseApproval() {
	return updateManagedUseApproval;
 }

 public String getSubmitShowUseApprovers() {
	return submitShowUseApprovers;
 }

 public String getShowActiveOnly() {
 return showActiveOnly;
 }

 public String getUpdateAllRows() {
 return updateAllRows;
 }

 public String getButtonCreateExcel() {
	return buttonCreateExcel;
 }

 public String getShowOnlyWithLimits() {
	return showOnlyWithLimits;
 }

 public String getCatalogCompanyId() {
	 return catalogCompanyId;
 }

public String getuAction() {
	return uAction;
}

public void setuAction(String uAction) {
	this.uAction = uAction;
}

@Override
public void setHiddenFormFields() {
	// TODO Auto-generated method stub
	
}
 
 

}