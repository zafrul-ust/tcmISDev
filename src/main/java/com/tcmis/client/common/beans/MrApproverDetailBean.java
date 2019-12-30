package com.tcmis.client.common.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.util.Date;

/******************************************************************************
 * CLASSNAME: MrApproverDetailBean <br>
 * @version: 1.0, Jan 31, 2012 <br>
 *****************************************************************************/

public class MrApproverDetailBean extends BaseDataBean {

	private String mrLine;
    private String approvalType;
    private String approvalStatus;
    private String approverName;
    private Date approvalDate;
    private String approvalComment;
    private String approvalEmail;
    private String approvalPhone;

    //constructor
	public MrApproverDetailBean() {
	}

    public String getMrLine() {
        return mrLine;
    }

    public void setMrLine(String mrLine) {
        this.mrLine = mrLine;
    }

    public String getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(String approvalType) {
        this.approvalType = approvalType;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getApprovalComment() {
        return approvalComment;
    }

    public void setApprovalComment(String approvalComment) {
        this.approvalComment = approvalComment;
    }

    public String getApprovalEmail() {
        return approvalEmail;
    }

    public void setApprovalEmail(String approvalEmail) {
        this.approvalEmail = approvalEmail;
    }

    public String getApprovalPhone() {
        return approvalPhone;
    }

    public void setApprovalPhone(String approvalPhone) {
        this.approvalPhone = approvalPhone;
    }
}