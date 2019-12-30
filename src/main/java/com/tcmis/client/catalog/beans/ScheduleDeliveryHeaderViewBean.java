package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ScheduleDeliveryHeaderView Bean <br>
 * @version: 1.0, May 3, 2005 <br>
 *****************************************************************************/

public class ScheduleDeliveryHeaderViewBean
 extends BaseDataBean {

 private String companyId;
 private BigDecimal prNumber;
 private String lineItem;
 private BigDecimal requestor;
 private String prStatus;
 private BigDecimal quantity;
 private String itemType;
 private String facPartNo;
 private String partDescription;
 private String packaging;
 private String statusDesc;
 private String requestLineStatus;

 //constructor
 public ScheduleDeliveryHeaderViewBean() {
 }

 //setters
 public void setCompanyId(String companyId) {
   this.companyId = companyId;
 }

 public void setPrNumber(BigDecimal prNumber) {
	this.prNumber = prNumber;
 }

 public void setLineItem(String lineItem) {
	this.lineItem = lineItem;
 }

 public void setRequestor(BigDecimal requestor) {
	this.requestor = requestor;
 }

 public void setPrStatus(String prStatus) {
	this.prStatus = prStatus;
 }

 public void setQuantity(BigDecimal quantity) {
	this.quantity = quantity;
 }

 public void setItemType(String itemType) {
	this.itemType = itemType;
 }

 public void setFacPartNo(String facPartNo) {
	this.facPartNo = facPartNo;
 }

 public void setPartDescription(String partDescription) {
	this.partDescription = partDescription;
 }

 public void setPackaging(String packaging) {
        this.packaging = packaging;
 }

 public void setStatusDesc(String statusDesc) {
	this.statusDesc = statusDesc;
 }

 public void setRequestLineStatus(String requestLineStatus) {
   this.requestLineStatus = requestLineStatus;
 }

 //getters
 public String getCompanyId() {
   return companyId;
 }

 public BigDecimal getPrNumber() {
	return prNumber;
 }

 public String getLineItem() {
	return lineItem;
 }

 public BigDecimal getRequestor() {
	return requestor;
 }

 public String getPrStatus() {
	return prStatus;
 }

 public BigDecimal getQuantity() {
	return quantity;
 }

 public String getItemType() {
	return itemType;
 }

 public String getFacPartNo() {
	return facPartNo;
 }

 public String getPartDescription() {
	return partDescription;
 }

 public String getPackaging() {
        return packaging;
 }


 public String getStatusDesc() {
	return statusDesc;
 }

 public String getRequestLineStatus() {
   return requestLineStatus;
 }
}