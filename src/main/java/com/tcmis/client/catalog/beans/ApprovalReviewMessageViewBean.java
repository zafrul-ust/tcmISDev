package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ApprovalReviewMessageViewBean <br>
 * @version: 1.0, Nov 4, 2014 <br>
 *****************************************************************************/

public class ApprovalReviewMessageViewBean extends BaseDataBean {

    private BigDecimal reviewId;
    private BigDecimal displayOrder;
    private String messageText;
    private String approvalRole;
    private BigDecimal processingOrder;
    private BigDecimal requestId;

    //constructor
    public ApprovalReviewMessageViewBean() {
    }

    public BigDecimal getReviewId() {
        return reviewId;
    }

    public void setReviewId(BigDecimal reviewId) {
        this.reviewId = reviewId;
    }

    public BigDecimal getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(BigDecimal displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getApprovalRole() {
        return approvalRole;
    }

    public void setApprovalRole(String approvalRole) {
        this.approvalRole = approvalRole;
    }

    public BigDecimal getProcessingOrder() {
        return processingOrder;
    }

    public void setProcessingOrder(BigDecimal processingOrder) {
        this.processingOrder = processingOrder;
    }

    public BigDecimal getRequestId() {
        return requestId;
    }

    public void setRequestId(BigDecimal requestId) {
        this.requestId = requestId;
    }
}