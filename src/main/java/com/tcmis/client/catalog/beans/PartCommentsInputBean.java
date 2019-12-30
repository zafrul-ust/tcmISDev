package com.tcmis.client.catalog.beans;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PartCommentsInputBean {

 private String catPartNo;
 private String partGroupNo;
 private String facilityId;
 private String applicationId;
 private String catalogId;
 private String[] commentId;
 private String[] comments;
 private String[] deleteCheckBox;
 private String[] updateStatus;

 //constructor
 public PartCommentsInputBean() {
 }

 //setters
 public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
 }

 public void setApplicationId(String applicationId) {
	this.applicationId = applicationId;
 }

 public void setCatPartNo(String catPartNo) {
	this.catPartNo = catPartNo;
 }

 public void setPartGroupNo(String partGroupNo) {
	this.partGroupNo = partGroupNo;
 }

 public void setCatalogId(String catalogId) {
	this.catalogId = catalogId;
 }

 public void setCommentId(String[] commentId) {
	this.commentId = commentId;
 }

 public void setComments(String[] comments) {
	this.comments = comments;
 }

 public void setDeleteCheckBox(String[] deleteCheckBox) {
	this.deleteCheckBox = deleteCheckBox;
 }

 public void setUpdateStatus(String[] updateStatus) {
	this.updateStatus = updateStatus;
 }

 //getters
 public String getFacilityId() {
	return this.facilityId;
 }

 public String getApplicationId() {
	return this.applicationId;
 }

 public String getCatPartNo() {
	return this.catPartNo;
 }

 public String getPartGroupNo() {
	return this.partGroupNo;
 }

 public String getCatalogId() {
	return this.catalogId;
 }

 public String[] getCommentId() {
	return commentId;
 }

 public String[] getComments() {
	return comments;
 }

 public String[] getDeleteCheckBox() {
	return deleteCheckBox;
 }

 public String[] getUpdateStatus() {
	return updateStatus;
 }

}