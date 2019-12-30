package com.tcmis.client.common.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PreviousOrderEngEvalBean <br>
 * @version: 1.0, May 4, 2009 <br>
 *****************************************************************************/


public class PreviousOrderEngEvalBean extends BaseDataBean {

	private String facilityId;
	private String facilityName;
	private String application;
	private String applicationDesc;
	private String status;
	private Date submitDate;
	private Date releaseDate;
	private String evalComment;
	private Date evalCompletedDate;
	private BigDecimal requestId;
	private BigDecimal itemId;
	private String itemDesc;
	private Date dateDelivered;
	private String requestorName;
	private String phone;
	private String email;
	private BigDecimal prNumber;
	private String lineItem;
	private BigDecimal requestor;

	//constructor
	public PreviousOrderEngEvalBean() {
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public void setEvalComment(String evalComment) {
		this.evalComment = evalComment;
	}

	public void setEvalCompletedDate(Date evalCompletedDate) {
		this.evalCompletedDate = evalCompletedDate;
	}

	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}

	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
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

	//getters
	public String getFacilityId() {
		return facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public String getApplication() {
		return application;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public String getStatus() {
		return status;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public BigDecimal getRequestId() {
		return requestId;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public String getEvalComment() {
		return evalComment;
	}

	public Date getEvalCompletedDate() {
		return evalCompletedDate;
	}

	public Date getDateDelivered() {
		return dateDelivered;
	}

	public String getRequestorName() {
		return requestorName;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
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
}