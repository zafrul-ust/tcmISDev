package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: CatalogAddEmailBean <br>
 * @version: 1.0, Jul 11, 2011 <br>
 *****************************************************************************/

public class CatalogAddEmailBean extends BaseDataBean {

    private String[] toAddress;
    private StringBuilder emailSubject;
    private StringBuilder emailMessage;
    private int messageCount;
    private StringBuilder additionalMessage;
    private String approvalRole;
    private String emailAddress;
    private StringBuilder messageToApprover;


    //constructor
	public CatalogAddEmailBean() {
	}

    public String[] getToAddress() {
        return toAddress;
    }

    public void setToAddress(String[] toAddress) {
        this.toAddress = toAddress;
    }

    public StringBuilder getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(StringBuilder emailSubject) {
        this.emailSubject = emailSubject;
    }

    public StringBuilder getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(StringBuilder emailMessage) {
        this.emailMessage = emailMessage;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public StringBuilder getAdditionalMessage() {
        return additionalMessage;
    }

    public void setAdditionalMessage(StringBuilder additionalMessage) {
        this.additionalMessage = additionalMessage;
    }

    public String getApprovalRole() {
        return approvalRole;
    }

    public void setApprovalRole(String approvalRole) {
        this.approvalRole = approvalRole;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public StringBuilder getMessageToApprover() {
        return messageToApprover;
    }

    public void setMessageToApprover(StringBuilder messageToApprover) {
        this.messageToApprover = messageToApprover;
    }
}