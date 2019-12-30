package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: PkgWebPrOrderTrackDetailBean <br>
 * @version: 1.0, May 6, 2005 <br>
 *****************************************************************************/

public class PkgOrderTrackWebNeededBean
 extends BaseDataBean {

 /*private BigDecimal useApprover;
	 private String useApproverName;
	 private String facPartNo;
	 private BigDecimal prNumber;
	 private String lineItem;
	 private BigDecimal allocatedQuantity;
	 private String status;
	 private String ref;
	 private String referenceDate;
	 private String notes;
	 private String application;*/
 private BigDecimal orderQuantity;
 /*private String applicationDesc;
	 private String itemType;*/
// private String requiredDatetime;
 private Date requiredDatetime;
 //private String deliveryType;
 /*private String allocationReferenceDate;
	 private String mfgLot;
	 private String lotStatus;
	 private Date expireDate;
	 private Date releaseDate;
	 private String packaging;*/
 private Collection allocationCollection;

 //constructor
 public PkgOrderTrackWebNeededBean() {
 }

 //setters
 /*public void setUseApprover(BigDecimal useApprover) {
	this.useApprover = useApprover;
	 }
	 public void setUseApproverName(String useApproverName) {
	this.useApproverName = useApproverName;
	 }
	 public void setFacPartNo(String facPartNo) {
	this.facPartNo = facPartNo;
	 }
	 public void setPrNumber(BigDecimal prNumber) {
	this.prNumber = prNumber;
	 }
	 public void setLineItem(String lineItem) {
	this.lineItem = lineItem;
	 }
	 public void setAllocatedQuantity(BigDecimal allocatedQuantity) {
	this.allocatedQuantity = allocatedQuantity;
	 }
	 public void setStatus(String status) {
	this.status = status;
	 }
	 public void setRef(String ref) {
	this.ref = ref;
	 }
	 public void setReferenceDate(String referenceDate) {
	this.referenceDate = referenceDate;
	 }
	 public void setNotes(String notes) {
	this.notes = notes;
	 }
	 public void setApplication(String application) {
	this.application = application;
	 }*/
 public void setOrderQuantity(BigDecimal orderQuantity) {
	this.orderQuantity = orderQuantity;
 }

 /*public void setApplicationDesc(String applicationDesc) {
	this.applicationDesc = applicationDesc;
	 }
	 public void setItemType(String itemType) {
	this.itemType = itemType;
	 }*/
 public void setRequiredDatetime(Date requiredDatetime) {
	this.requiredDatetime = requiredDatetime;
 }

 /*public void setDeliveryType(String deliveryType) {
	this.deliveryType = deliveryType;
	 }
	 public void setAllocationReferenceDate(String allocationReferenceDate) {
	this.allocationReferenceDate = allocationReferenceDate;
	 }
	 public void setMfgLot(String mfgLot) {
	this.mfgLot = mfgLot;
	 }
	 public void setLotStatus(String lotStatus) {
	this.lotStatus = lotStatus;
	 }
	 public void setExpireDate(Date expireDate) {
	this.expireDate = expireDate;
	 }
	 public void setReleaseDate(Date releaseDate) {
	 this.releaseDate = releaseDate;
		}
	 public void setPackaging(String packaging) {
	 this.packaging = packaging;
	 }*/
 public void setAllocationCollection(Collection allocationCollection) {
	this.allocationCollection = allocationCollection;
 }

 //getters
 /*public BigDecimal getUseApprover() {
	return useApprover;
	 }
	 public String getUseApproverName() {
	return useApproverName;
	 }
	 public String getFacPartNo() {
	return facPartNo;
	 }
	 public BigDecimal getPrNumber() {
	return prNumber;
	 }
	 public String getLineItem() {
	return lineItem;
	 }
	 public BigDecimal getAllocatedQuantity() {
	return allocatedQuantity;
	 }
	 public String getStatus() {
	return status;
	 }
	 public String getRef() {
	return ref;
	 }
	 public String getReferenceDate() {
	return referenceDate;
	 }
	 public String getNotes() {
	return notes;
	 }
	 public String getApplication() {
	return application;
	 }*/
 public BigDecimal getOrderQuantity() {
	return orderQuantity;
 }

 /*public String getApplicationDesc() {
	return applicationDesc;
	 }
	 public String getItemType() {
	return itemType;
	 }*/
 public Date getRequiredDatetime() {
	return requiredDatetime;
 }

 /*public String getDeliveryType() {
	return deliveryType;
	 }
	 public String getAllocationReferenceDate() {
	return allocationReferenceDate;
	 }
	 public String getMfgLot() {
	return mfgLot;
	 }
	 public String getLotStatus() {
	return lotStatus;
	 }
	 public Date getExpireDate() {
	return expireDate;
	 }
	 public Date getReleaseDate() {
	 return releaseDate;
		}
	 public String getPackaging() {
	 return packaging;
	 }*/
 public Collection getAllocationCollection() {
	return allocationCollection;
 }
}