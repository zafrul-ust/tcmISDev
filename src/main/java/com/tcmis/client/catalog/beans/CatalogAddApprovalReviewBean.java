package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CatalogAddApprovalReviewBean <br>
 * @version: 1.0, Aug 2, 2011 <br>
 *****************************************************************************/


public class CatalogAddApprovalReviewBean extends BaseDataBean {

	private BigDecimal reviewId;
	private BigDecimal processingOrder;
	private String facilityId;
	private String approvalRole;
	private String companyId;
	private String catalogId;
	private String application;
	private String catalogCompanyId;
	private String ruleId;
	private String argument1;
	private String argument2;
	private String stopOnFailure;
	private String notifyOnly;
	private String failStatement;
	private String passStatement;
	private String fail;
    private String addlNotifyApprovalRole1;
    private String addlNotifyApprovalMsg1;
    private String addlNotifyApprovalRole2;
    private String addlNotifyApprovalMsg2;
    private String rejectOnFailure;
    private String requestorMessage;
    private String outputStatement;
    private String approveOnPass;
    private String restrictedApproval;

    //constructor
	public CatalogAddApprovalReviewBean() {
	}

	//setters
	public void setReviewId(BigDecimal reviewId) {
		this.reviewId = reviewId;
	}
	public void setProcessingOrder(BigDecimal processingOrder) {
		this.processingOrder = processingOrder;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setApprovalRole(String approvalRole) {
		this.approvalRole = approvalRole;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public void setArgument1(String argument1) {
		this.argument1 = argument1;
	}
	public void setArgument2(String argument2) {
		this.argument2 = argument2;
	}
	public void setStopOnFailure(String stopOnFailure) {
		this.stopOnFailure = stopOnFailure;
	}
	public void setNotifyOnly(String notifyOnly) {
		this.notifyOnly = notifyOnly;
	}
	public void setFailStatement(String failStatement) {
		this.failStatement = failStatement;
	}
	public void setPassStatement(String passStatement) {
		this.passStatement = passStatement;
	}
	public void setFail(String fail) {
		this.fail = fail;
	}


	//getters
	public BigDecimal getReviewId() {
		return reviewId;
	}
	public BigDecimal getProcessingOrder() {
		return processingOrder;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getApprovalRole() {
		return approvalRole;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getApplication() {
		return application;
	}
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
	public String getRuleId() {
		return ruleId;
	}
	public String getArgument1() {
		return argument1;
	}
	public String getArgument2() {
		return argument2;
	}
	public String getStopOnFailure() {
		return stopOnFailure;
	}
	public String getNotifyOnly() {
		return notifyOnly;
	}
	public String getFailStatement() {
		return failStatement;
	}
	public String getPassStatement() {
		return passStatement;
	}
	public String getFail() {
		return fail;
	}

    public String getAddlNotifyApprovalRole1() {
        return addlNotifyApprovalRole1;
    }

    public void setAddlNotifyApprovalRole1(String addlNotifyApprovalRole1) {
        this.addlNotifyApprovalRole1 = addlNotifyApprovalRole1;
    }

    public String getAddlNotifyApprovalMsg1() {
        return addlNotifyApprovalMsg1;
    }

    public void setAddlNotifyApprovalMsg1(String addlNotifyApprovalMsg1) {
        this.addlNotifyApprovalMsg1 = addlNotifyApprovalMsg1;
    }

    public String getAddlNotifyApprovalRole2() {
        return addlNotifyApprovalRole2;
    }

    public void setAddlNotifyApprovalRole2(String addlNotifyApprovalRole2) {
        this.addlNotifyApprovalRole2 = addlNotifyApprovalRole2;
    }

    public String getAddlNotifyApprovalMsg2() {
        return addlNotifyApprovalMsg2;
    }

    public void setAddlNotifyApprovalMsg2(String addlNotifyApprovalMsg2) {
        this.addlNotifyApprovalMsg2 = addlNotifyApprovalMsg2;
    }

    public String getRejectOnFailure() {
        return rejectOnFailure;
    }

    public void setRejectOnFailure(String rejectOnFailure) {
        this.rejectOnFailure = rejectOnFailure;
    }

    public String getRequestorMessage() {
        return requestorMessage;
    }

    public void setRequestorMessage(String requestorMessage) {
        this.requestorMessage = requestorMessage;
    }

    public String getOutputStatement() {
        return outputStatement;
    }

    public void setOutputStatement(String outputStatement) {
        this.outputStatement = outputStatement;
    }

    public String getApproveOnPass() {
        return approveOnPass;
    }

    public void setApproveOnPass(String approveOnPass) {
        this.approveOnPass = approveOnPass;
    }

    public String getRestrictedApproval() {
        return restrictedApproval;
    }

    public void setRestrictedApproval(String restrictedApproval) {
        this.restrictedApproval = restrictedApproval;
    }
}