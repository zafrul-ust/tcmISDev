package com.tcmis.client.common.beans;

import java.util.Date;
import java.util.Collection;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: RequestLineItemApproverBean <br>
 * @version: 1.0, May 5, 2009 <br>
 *****************************************************************************/


public class RequestLineItemApproverBean extends BaseDataBean {
	
	private String userApprovalComment;
	private String phone;
	private String email;
	private String fullName;
	private String rejectionReason;
	private String approvalStatus;
	private Date approvalDate;
	private String approvalComment;

	//constructor
	public RequestLineItemApproverBean() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserApprovalComment() {
		return userApprovalComment;
	}

	public void setUserApprovalComment(String userApprovalComment) {
		this.userApprovalComment = userApprovalComment;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public void setApprovalComment(String approvalComment) {
		this.approvalComment = approvalComment;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public String getApprovalComment() {
		return approvalComment;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}
}