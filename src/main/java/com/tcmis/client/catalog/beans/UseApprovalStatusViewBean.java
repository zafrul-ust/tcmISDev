package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: UseApprovalStatusViewBean <br>
 * @version: 1.0, Feb 13, 2006 <br>
 *****************************************************************************/

public class UseApprovalStatusViewBean
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
 private BigDecimal partGroupNo;
 private BigDecimal limitQuantityPeriod1;
 private BigDecimal daysPeriod1;
 private BigDecimal limitQuantityPeriod2;
 private BigDecimal daysPeriod2;
 private String haasShiptoCompanyId;
 private String dockLocationId;
 private String dockDeliveryPoint;
 private String deliveryPointBarcode;
 private String customerDeliverTo;
 private BigDecimal barcodeRequester;
 private String barcodeRequesterName;
 private String processDesc;
 private String inventoryGroup;
 private String partDescription;
 private String packaging;
 private BigDecimal mwLimitQuantityPeriod1;
 private BigDecimal mwDaysPeriod1;
 private BigDecimal mwLimitQuantityPeriod2;
 private BigDecimal mwDaysPeriod2;
 private BigDecimal mwOrderQuantity;
 private String mwOrderQuantityRule;
 private BigDecimal mwEstimateOrderQuantityPrd;
 private String mwProcessDesc;
 private BigDecimal mwApprovalId;
 private Date mwApprovalDate;
 private String ok;
 private String rowNumber;
 private String useApprovalChanged;
 private String automatedFeedChanged;
 private String catalogCompanyId;
 private String companyId;

 //constructor
 public UseApprovalStatusViewBean() {
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
	public void setProcessDesc(String processDesc) {
		this.processDesc = com.tcmis.common.util.StringHandler.replace(processDesc,"\n","<br>");
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = com.tcmis.common.util.StringHandler.replace(partDescription,"\n","<br>");
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setMwLimitQuantityPeriod1(BigDecimal mwLimitQuantityPeriod1) {
		this.mwLimitQuantityPeriod1 = mwLimitQuantityPeriod1;
	}
	public void setMwDaysPeriod1(BigDecimal mwDaysPeriod1) {
		this.mwDaysPeriod1 = mwDaysPeriod1;
	}
	public void setMwLimitQuantityPeriod2(BigDecimal mwLimitQuantityPeriod2) {
		this.mwLimitQuantityPeriod2 = mwLimitQuantityPeriod2;
	}
	public void setMwDaysPeriod2(BigDecimal mwDaysPeriod2) {
		this.mwDaysPeriod2 = mwDaysPeriod2;
	}
	public void setMwOrderQuantity(BigDecimal mwOrderQuantity) {
		this.mwOrderQuantity = mwOrderQuantity;
	}
	public void setMwOrderQuantityRule(String mwOrderQuantityRule) {
		this.mwOrderQuantityRule = mwOrderQuantityRule;
	}
	public void setMwEstimateOrderQuantityPrd(BigDecimal mwEstimateOrderQuantityPrd) {
		this.mwEstimateOrderQuantityPrd = mwEstimateOrderQuantityPrd;
	}
	public void setMwProcessDesc(String mwProcessDesc, boolean forUpdate) {
		if (forUpdate) {
		 this.mwProcessDesc = com.tcmis.common.util.StringHandler.replace(mwProcessDesc,"\n","<br>");
	}
		else {
		 this.mwProcessDesc = com.tcmis.common.util.StringHandler.replace(mwProcessDesc,"\n","<br>");
	}
	}
	public void setMwProcessDesc(String mwProcessDesc) {
		setMwProcessDesc(mwProcessDesc,false);
	}
	public void setMwApprovalId(BigDecimal mwApprovalId) {
		this.mwApprovalId = mwApprovalId;
	}
	public void setMwApprovalDate(Date mwApprovalDate) {
		this.mwApprovalDate = mwApprovalDate;
	}

	 public void setOk(String ok) {
		this.ok = ok;
	 }

	 public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	 }

 public void setUseApprovalChanged(String useApprovalChanged) {
	this.useApprovalChanged = useApprovalChanged;
 }

 public void setAutomatedFeedChanged(String automatedFeedChanged) {
	this.automatedFeedChanged = automatedFeedChanged;
 }

 public void setCatalogCompanyId(String catalogCompanyId) {
	 this.catalogCompanyId = catalogCompanyId;
 }

 public void setCompanyId(String companyId) {
	 this.companyId = companyId;
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
	public String getProcessDesc() {
		return processDesc;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public String getPackaging() {
		return packaging;
	}
	public BigDecimal getMwLimitQuantityPeriod1() {
		return mwLimitQuantityPeriod1;
	}
	public BigDecimal getMwDaysPeriod1() {
		return mwDaysPeriod1;
	}
	public BigDecimal getMwLimitQuantityPeriod2() {
		return mwLimitQuantityPeriod2;
	}
	public BigDecimal getMwDaysPeriod2() {
		return mwDaysPeriod2;
	}
	public BigDecimal getMwOrderQuantity() {
		return mwOrderQuantity;
	}
	public String getMwOrderQuantityRule() {
		return mwOrderQuantityRule;
	}
	public BigDecimal getMwEstimateOrderQuantityPrd() {
		return mwEstimateOrderQuantityPrd;
	}
	public String getMwProcessDesc() {
		return mwProcessDesc;
	}
	public BigDecimal getMwApprovalId() {
		return mwApprovalId;
	}
	public Date getMwApprovalDate() {
		return mwApprovalDate;
	}

 public String getOk() {
	return ok;
 }

 public String getRowNumber() {
	return rowNumber;
 }

 public String getUseApprovalChanged() {
	return useApprovalChanged;
 }

 public String getAutomatedFeedChanged() {
	return automatedFeedChanged;
 }

 public String getCatalogCompanyId() {
	 return catalogCompanyId;
 }

 public String getCompanyId() {
	 return companyId;
 }
}