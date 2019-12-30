package com.tcmis.client.openCustomer.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubPrefBean <br>
 * @version: 1.0, Jun 9, 2004 <br>
 *****************************************************************************/

public class OrderTrackingInputBean
 extends BaseDataBean {

 private int personnelId;
 private String companyId;
 private String requestorId;
 private String requestorName;
 private String needMyApproval;
 private String searchWhat;
 private String searchType;
 private String searchText;
 private String critical;
 private String sort;
 private String facilityId;
 private String applicationId;
 private String status;
 private String deliveredSinceDays;
 private String submitSearch;
 private String buttonCreateExcel;
 private String companyName;
 private String facilityName;
 private String applicationDesc;
 private String searchTypeDesc;
 private String searchWhatDesc;
 private String releasedSinceDays;
 private String cancelledSinceDays;

 //constructor
 public OrderTrackingInputBean() {
 }

 //setters
 public void setPersonnelId(int personnelId) {
	this.personnelId = personnelId;
 }

 public void setCompanyId(String companyId) {
	this.companyId = companyId;
 }

 public void setRequestorId(String requestorId) {
	this.requestorId = requestorId;
 }

 public void setRequestorName(String requestorName) {
	this.requestorName = requestorName;
 }

 public void setNeedMyApproval(String needMyApproval) {
	this.needMyApproval = needMyApproval;
 }

 public void setCritical(String critical) {
	this.critical = critical;
 }

 public void setSort(String sort) {
	this.sort = sort;
 }

 public void setSearchWhat(String searchWhat) {
	this.searchWhat = searchWhat;
 }

 public void setSearchType(String searchType) {
	this.searchType = searchType;
 }

 public void setSearchText(String searchText) {
	this.searchText = searchText;
 }

 public void setApplicationId(String applicationId) {
	this.applicationId = applicationId;
 }

 public void setSubmitSearch(String submitSearch) {
	this.submitSearch = submitSearch;
 }

 public void setButtonCreateExcel(String buttonCreateExcel) {
	this.buttonCreateExcel = buttonCreateExcel;
 }

 public void setStatus(String status) {
	this.status = status;
 }

 public void setDeliveredSinceDays(String deliveredSinceDays) {
	this.deliveredSinceDays = deliveredSinceDays;
 }

 public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
 }

 public void setCompanyName(String companyName) {
   this.companyName = companyName;
 }

 public void setFacilityName(String facilityName) {
   this.facilityName = facilityName;
 }

 public void setApplicationDesc(String applicationDesc) {
   this.applicationDesc = applicationDesc;
 }

 public void setSearchTypeDesc(String searchTypeDesc) {
   this.searchTypeDesc = searchTypeDesc;
 }

 public void setSearchWhatDesc(String searchWhatDesc) {
   this.searchWhatDesc = searchWhatDesc;
 }
 public void setReleasedSinceDays(String releasedSinceDays) {
   this.releasedSinceDays = releasedSinceDays;
 }
 public void setCancelledSinceDays(String cancelledSinceDays) {
   this.cancelledSinceDays = cancelledSinceDays;
 }

 //getters
 public int getPersonnelId() {
	return personnelId;
 }

 public String getCompanyId() {
	return companyId;
 }

 public String getRequestorId() {
	return requestorId;
 }

 public String getRequestorName() {
	return requestorName;
 }

 public String getNeedMyApproval() {
	return needMyApproval;
 }

 public String getCritical() {
	return critical;
 }

 public String getSort() {
	return sort;
 }

 public String getSearchWhat() {
	return searchWhat;
 }

 public String getSearchType() {
	return searchType;
 }

 public String getSearchText() {
	return searchText;
 }

 public String getApplicationId() {
	return applicationId;
 }

 public String getSubmitSearch() {
	return submitSearch;
 }

 public String getButtonCreateExcel() {
	return buttonCreateExcel;
 }

 public String getStatus() {
	return status;
 }

 public String getDeliveredSinceDays() {
	return deliveredSinceDays;
 }

 public String getFacilityId() {
	return facilityId;
 }

 public String getCompanyName() {
   return companyName;
 }

 public String getFacilityName() {
   return facilityName;
 }

 public String getApplicationDesc() {
   return applicationDesc;
 }

 public String getSearchTypeDesc() {
   return searchTypeDesc;
 }

 public String getSearchWhatDesc() {
   return searchWhatDesc;
 }
 public String getReleasedSinceDays() {
   return releasedSinceDays;
 }
 public String getCancelledSinceDays() {
   return cancelledSinceDays;
 }
}