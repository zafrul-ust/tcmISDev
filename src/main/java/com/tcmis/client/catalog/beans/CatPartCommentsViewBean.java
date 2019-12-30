package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CatPartCommentsViewBean <br>
 * @version: 1.0, Jul 19, 2005 <br>
 *****************************************************************************/

public class CatPartCommentsViewBean
 extends BaseDataBean {

 private BigDecimal commentId;
 private String companyId;
 private String catalogId;
 private String catPartNo;
 private Date dateEntered;
 private BigDecimal enteredBy;
 private String comments;
 private String fullName;
 private String catalogCompanyId;

 //constructor
 public CatPartCommentsViewBean() {
 }

 //setters
 public void setCommentId(BigDecimal commentId) {
	this.commentId = commentId;
 }

 public void setCompanyId(String companyId) {
	this.companyId = companyId;
 }

 public void setCatalogId(String catalogId) {
	this.catalogId = catalogId;
 }

 public void setCatPartNo(String catPartNo) {
	this.catPartNo = catPartNo;
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

 public String getCatalogId() {
	return catalogId;
 }

 public String getCatPartNo() {
	return catPartNo;
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