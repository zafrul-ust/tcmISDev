package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

public class MfrNotificationMgmtViewBean {

	private BigDecimal notificationId;
	private String status;
	private BigDecimal mfrReqCategoryId;
	private String mfrReqCategoryDesc;
	private BigDecimal qcAssignedTo;
	private String requester;
	private Date dateSubmitted;
	private String comments;
	private String internalComments;
	
	public MfrNotificationMgmtViewBean(){}

	public BigDecimal getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(BigDecimal notificationId) {
		this.notificationId = notificationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getMfrReqCategoryId() {
		return mfrReqCategoryId;
	}

	public void setMfrReqCategoryId(BigDecimal mfrReqCategoryId) {
		this.mfrReqCategoryId = mfrReqCategoryId;
	}

	public String getMfrReqCategoryDesc() {
		return mfrReqCategoryDesc;
	}

	public void setMfrReqCategoryDesc(String mfrReqCategoryDesc) {
		this.mfrReqCategoryDesc = mfrReqCategoryDesc;
	}

	public BigDecimal getQcAssignedTo() {
		return qcAssignedTo;
	}

	public void setQcAssignedTo(BigDecimal qcAssignedTo) {
		this.qcAssignedTo = qcAssignedTo;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public Date getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(Date dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getInternalComments() {
		return internalComments;
	}

	public void setInternalComments(String internalComments) {
		this.internalComments = internalComments;
	}
}
