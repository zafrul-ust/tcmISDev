package com.tcmis.client.catalog.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ApprovalRequestOvBean <br>
 * @version: 1.0, Oct 2, 2007 <br>
 *****************************************************************************/

public class ApprovalRequestOvBean extends BaseDataBean {

	int reviewId;
	int processingOrder;
	String facilityId;
	String approvalRole;
	String companyId;
	String catalogId;
	String application;
	String catalogCompanyId;
	String ruleId;
	String argument1;
	String argument2;
	String stopOnFailure;
	String notifyOnly;
	String failStatement;
	String passStatement;
	String addlNotifyApprovalRole1;
	String addlNotifyApprovalMsg1;
	String addlNotifyApprovalRole2;
	String addlNotifyApprovalMsg2;
	String rejectOnFailure;
    String approveOnPass;
    String requestorMessage;
	String fail;
    String outputStatement;
	Collection<CaiLineObjBean> listCas;
    String lineItem;
    String itemId;
    String reviewRequired;
    String restrictedApproval;
    String onFailGotoReviewId;
    String smeNotificationType;
    Collection messageTextColl;


    private String sqlType = "PKG_APPROVAL_REVIEW.fx_tbl_request_review";


    //constructor
    public ApprovalRequestOvBean() {}
    
    public String getSmeNotificationType() {
		return smeNotificationType;
	}
    
    public void setSmeNotificationType(String smeNotificationType) {
		this.smeNotificationType = smeNotificationType;
	}
    	
    public String getOnFailGotoReviewId() {
		return onFailGotoReviewId;
	}

	public void setOnFailGotoReviewId(String onFailGotoReviewId) {
		this.onFailGotoReviewId = onFailGotoReviewId;
	}
	
    public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public int getProcessingOrder() {
		return processingOrder;
	}

	public void setProcessingOrder(int processingOrder) {
		this.processingOrder = processingOrder;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getApprovalRole() {
		return approvalRole;
	}

	public void setApprovalRole(String approvalRole) {
		this.approvalRole = approvalRole;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getArgument1() {
		return argument1;
	}

	public void setArgument1(String argument1) {
		this.argument1 = argument1;
	}

	public String getArgument2() {
		return argument2;
	}

	public void setArgument2(String argument2) {
		this.argument2 = argument2;
	}

	public String getStopOnFailure() {
		return stopOnFailure;
	}

	public void setStopOnFailure(String stopOnFailure) {
		this.stopOnFailure = stopOnFailure;
	}

	public String getNotifyOnly() {
		return notifyOnly;
	}

	public void setNotifyOnly(String notifyOnly) {
		this.notifyOnly = notifyOnly;
	}

	public String getFailStatement() {
		return failStatement;
	}

	public void setFailStatement(String failStatement) {
		this.failStatement = failStatement;
	}

	public String getPassStatement() {
		return passStatement;
	}

	public void setPassStatement(String passStatement) {
		this.passStatement = passStatement;
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

    public String getApproveOnPass() {
        return approveOnPass;
    }

    public void setApproveOnPass(String approveOnPass) {
        this.approveOnPass = approveOnPass;
    }

    public String getRequestorMessage() {
		return requestorMessage;
	}

	public void setRequestorMessage(String requestorMessage) {
		this.requestorMessage = requestorMessage;
	}

	public String getFail() {
		return fail;
	}

	public void setFail(String fail) {
		this.fail = fail;
	}

	public String getOutputStatement() {
		return outputStatement;
	}

	public void setOutputStatement(String outputStatement) {
		this.outputStatement = outputStatement;
	}

	public Collection<CaiLineObjBean> getListCas() {
		return listCas;
	}

	public void setListCas(Collection<CaiLineObjBean> listCas) {
		this.listCas = listCas;
	}

	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

    public String getLineItem() {
        return lineItem;
    }

    public void setLineItem(String lineItem) {
        this.lineItem = lineItem;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getReviewRequired() {
        return reviewRequired;
    }

    public void setReviewRequired(String reviewRequired) {
        this.reviewRequired = reviewRequired;
    }

    public String getRestrictedApproval() {
        return restrictedApproval;
    }

    public void setRestrictedApproval(String restrictedApproval) {
        this.restrictedApproval = restrictedApproval;
    }

    public Collection getMessageTextColl() {
        return messageTextColl;
    }

    public void setMessageTextColl(Collection messageTextColl) {
        this.messageTextColl = messageTextColl;
    }
}