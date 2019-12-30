package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: FacLocAppPartCommentBean <br>
 * @version: 1.0, Jul 11, 2005 <br>
 *****************************************************************************/

public class FacLocAppPartCommentBean
 extends BaseDataBean {

 private BigDecimal commentId;
 private String facilityId;
 private String applicationId;
 private String application;
 private String catalogId;
 private String catPartNo;
 private BigDecimal partGroupNo;
 private Date dateEntered;
 private BigDecimal enteredBy;
 private String comments;
 private String deleteCheckBox;
 private String updateStatus;

 //constructor
 public FacLocAppPartCommentBean() {
 }

 //setters
 public void setCommentId(BigDecimal commentId) {
	this.commentId = commentId;
 }

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

 public void setDateEntered(Date dateEntered) {
	this.dateEntered = dateEntered;
 }

 public void setEnteredBy(BigDecimal enteredBy) {
	this.enteredBy = enteredBy;
 }

 public void setComments(String comments) {
	this.comments = comments;
 }

 //getters
 public BigDecimal getCommentId() {
	return commentId;
 }

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

 public Date getDateEntered() {
	return dateEntered;
 }

 public BigDecimal getEnteredBy() {
	return enteredBy;
 }

 public String getComments() {
	return comments;
 }

public String getDeleteCheckBox() {
	return deleteCheckBox;
}

public void setDeleteCheckBox(String deleteCheckBox) {
	this.deleteCheckBox = deleteCheckBox;
}

public String getUpdateStatus() {
	return updateStatus;
}

public void setUpdateStatus(String updateStatus) {
	this.updateStatus = updateStatus;
}

public String getApplicationId() {
	return applicationId;
}

public void setApplicationId(String applicationId) {
	this.applicationId = applicationId;
}
}