package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: FacLocAppPartCommentsViewBean <br>
 * @version: 1.0, Jul 19, 2005 <br>
 *****************************************************************************/

public class FacLocAppPartCommentsViewBean
 extends BaseDataBean {

 private BigDecimal commentId;
 private String companyId;
 private String facilityId;
 private String application;
 private String catalogId;
 private String catPartNo;
 private BigDecimal partGroupNo;
 private Date dateEntered;
 private BigDecimal enteredBy;
 private String comments;
 private String fullName;
 private String catalogCompanyId;

 //constructor
 public FacLocAppPartCommentsViewBean() {
 }

 //setters
 public void setCommentId(BigDecimal commentId) {
	this.commentId = commentId;
 }

 public void setCompanyId(String companyId) {
	this.companyId = companyId;
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

 public void setFullName(String fullName) {
	this.fullName = fullName;
 }

 //getters
 public BigDecimal getCommentId() {
	return commentId;
 }

 public String getCompanyId() {
	return companyId;
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

 public String getFullName() {
	return fullName;
 }

public String getCatalogCompanyId() {
	return catalogCompanyId;
}

public void setCatalogCompanyId(String catalogCompanyId) {
	this.catalogCompanyId = catalogCompanyId;
}
}