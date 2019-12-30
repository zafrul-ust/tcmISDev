package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: OrderTrackDetailHeaderViewBean <br>
 * @version: 1.0, May 6, 2005 <br>
 *****************************************************************************/

public class OrderTrackDetailHeaderViewBean
 extends BaseDataBean {

 private String companyId;
 private BigDecimal prNumber;
 private BigDecimal requestor;
 private String facilityId;
 private Date requestDate;
 private BigDecimal requestedFinanceApprover;
 private BigDecimal requestedReleaser;
 private Date submittedDate;
 private Date mrReleasedDate;
 private Date financeApprovedDate;
 private String requestorName;
 private String financeApproverName;
 private String releaserName;
 
 //constructor
 public OrderTrackDetailHeaderViewBean() {
 }

 //setters
 public void setCompanyId(String companyId) {
   this.companyId = companyId;
 }

 public void setPrNumber(BigDecimal prNumber) {
	this.prNumber = prNumber;
 }

 public void setRequestor(BigDecimal requestor) {
	this.requestor = requestor;
 }

 public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
 }

 public void setRequestDate(Date requestDate) {
	this.requestDate = requestDate;
 }

 public void setRequestedFinanceApprover(BigDecimal requestedFinanceApprover) {
	this.requestedFinanceApprover = requestedFinanceApprover;
 }

 public void setRequestedReleaser(BigDecimal requestedReleaser) {
	this.requestedReleaser = requestedReleaser;
 }

 public void setSubmittedDate(Date submittedDate) {
	this.submittedDate = submittedDate;
 }

 public void setMrReleasedDate(Date mrReleasedDate) {
	this.mrReleasedDate = mrReleasedDate;
 }

 public void setFinanceApprovedDate(Date financeApprovedDate) {
	this.financeApprovedDate = financeApprovedDate;
 }

 public void setRequestorName(String requestorName) {
	this.requestorName = requestorName;
 }

 public void setFinanceApproverName(String financeApproverName) {
	this.financeApproverName = financeApproverName;
 }

 public void setReleaserName(String releaserName) {
	this.releaserName = releaserName;
 }

 //getters
 public String getCompanyId() {
   return companyId;
 }

 public BigDecimal getPrNumber() {
	return prNumber;
 }

 public BigDecimal getRequestor() {
	return requestor;
 }

 public String getFacilityId() {
	return facilityId;
 }

 public Date getRequestDate() {
	return requestDate;
 }

 public BigDecimal getRequestedFinanceApprover() {
	return requestedFinanceApprover;
 }

 public BigDecimal getRequestedReleaser() {
	return requestedReleaser;
 }

 public Date getSubmittedDate() {
	return submittedDate;
 }

 public Date getMrReleasedDate() {
	return mrReleasedDate;
 }

 public Date getFinanceApprovedDate() {
	return financeApprovedDate;
 }

 public String getRequestorName() {
	return requestorName;
 }

 public String getFinanceApproverName() {
	return financeApproverName;
 }

 public String getReleaserName() {
	return releaserName;
 }

}