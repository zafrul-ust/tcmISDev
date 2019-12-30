package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: TempDeliverySummary Bean <br>
 * @version: 1.0, May 3, 2005 <br>
 *****************************************************************************/

public class DeliverySummaryBean
 extends BaseDataBean {

 private String companyId;
 private BigDecimal prNumber;
 private String lineItem;
 private Date requestedDateToDeliver;
 private BigDecimal requestedQty;
 private BigDecimal refQuantity;
 private Date refDate;
 private String status;
 //additional columns, because this bean is also used as inputBean
 private BigDecimal revisedQuantity;
 //rowType has four type [null,"new","revised"]
 //null - no change;
 private String rowType;
 private String rowDeleted;
 private BigDecimal requestor;
 private String requestLineStatus;
 private Collection deliveredInfoColl;
 private BigDecimal openQty;
 private String userChangedData;
 private String facilityId;
 private String userViewType;
 private String source;
 private String calendarAction;

 //constructor
 public DeliverySummaryBean() {
 }

public String getCalendarAction() {
    return calendarAction;
}

public void setCalendarAction(String calendarAction) {
    this.calendarAction = calendarAction;
}

public String getSource() {
    return source;
}

public void setSource(String source) {
    this.source = source;
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

 public void setRequestedDateToDeliver(Date requestedDateToDeliver) {
	this.requestedDateToDeliver = requestedDateToDeliver;
 }

 public void setRequestedQty(BigDecimal requestedQty) {
	this.requestedQty = requestedQty;
 }

 public void setRefQuantity(BigDecimal refQuantity) {
	this.refQuantity = refQuantity;
 }

 public void setRefDate(Date refDate) {
	this.refDate = refDate;
 }

 public void setStatus(String status) {
   this.status = status;
 }

 public void setRevisedQuantity(BigDecimal revisedQuantity) {
        this.revisedQuantity = revisedQuantity;
 }
 public void setRowType(String rowType) {
   this.rowType = rowType;
 }
 public void setRowDeleted(String rowDeleted) {
   this.rowDeleted = rowDeleted;
 }

 public void setRequestor(BigDecimal requestor) {
   this.requestor = requestor;
 }
 public void setRequestLineStatus(String requestLineStatus) {
        this.requestLineStatus = requestLineStatus;
 }
 public void setDeliveredInfoColl(Collection col) {
   this.deliveredInfoColl = col;
 }
 public void setOpenQty(BigDecimal openQty) {
   this.openQty = openQty;
 }
 public void setUserChangedData(String userChangedData) {
   this.userChangedData = userChangedData;
 }
 public void setFacilityId(String facilityId) {
   this.facilityId = facilityId;
 }
 public void setUserViewType(String userViewType) {
   this.userViewType = userViewType;
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

 public Date getRequestedDateToDeliver() {
	return requestedDateToDeliver;
 }

 public BigDecimal getRequestedQty() {
	return requestedQty;
 }

 public BigDecimal getRefQuantity() {
	return refQuantity;
 }

 public Date getRefDate() {
	return refDate;
 }

 public String getStatus() {
   return status;
 }

 public BigDecimal getRevisedQuantity() {
        return revisedQuantity;
 }
 public String getRowType() {
   return rowType;
 }
 public String getRowDeleted() {
   return rowDeleted;
 }
 public BigDecimal getRequestor() {
   return requestor;
 }

 public String getRequestLineStatus() {
        return requestLineStatus;
 }
 public Collection getDeliveredInfoColl() {
   return deliveredInfoColl;
 }
 public BigDecimal getOpenQty() {
   return openQty;
 }
 public String getUserChangedData() {
   return userChangedData;
 }
 public String getFacilityId() {
   return facilityId;
 }
 public String getUserViewType() {
   return userViewType;
 }

} //end of class