package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

import radian.tcmis.common.util.StringHandler;

public class ContactLogHistoryBean extends BaseDataBean {
	
	private BigDecimal contactLogId;
	private BigDecimal personnelId;
	private Date contactDatetime;
	private String personnelName;
	private String contactStatus;
	private String contactPurpose;
	private String contactLogType;
	private String contactReference;
	private String notes;
	private String contactName;
	private Collection<ContactLogDocumentViewBean> documentColl;
	private String documentName;
	private String documentUrl;
    private BigDecimal recertAttemptId;
	private String finalStatus;

    public BigDecimal getContactLogId() {
		return contactLogId;
	}

	public void setContactLogId(BigDecimal contactLogId) {
		this.contactLogId = contactLogId;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	
	public Date getContactDatetime() {
		return contactDatetime;
	}
	
	public void setContactDatetime(Date contactDatetime) {
		this.contactDatetime = contactDatetime;
	}
	
	public String getPersonnelName() {
		return personnelName;
	}
	
	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}
	
	public String getContactStatus() {
		return contactStatus;
	}
	
	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
	}
	
	public boolean isFinalStatus() {
		return ! StringHandler.isBlankString(contactStatus);
	}

	public String getContactPurpose() {
		return contactPurpose;
	}
	
	public void setContactPurpose(String contactPurpose) {
		this.contactPurpose = contactPurpose;
	}
	
	public String getContactLogType() {
		return contactLogType;
	}
	
	public void setContactLogType(String contactLogType) {
		this.contactLogType = contactLogType;
	}
	
	public String getContactReference() {
		return contactReference;
	}
	
	public void setContactReference(String contactReference) {
		this.contactReference = contactReference;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public Collection<ContactLogDocumentViewBean> getDocumentColl() {
		return documentColl;
	}

	public void setDocumentColl(Collection<ContactLogDocumentViewBean> documentColl) {
		this.documentColl = documentColl;
	}

    public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentUrl() {
		return documentUrl;
	}

	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	public BigDecimal getRecertAttemptId() {
        return recertAttemptId;
    }

    public void setRecertAttemptId(BigDecimal recertAttemptId) {
        this.recertAttemptId = recertAttemptId;
    }

	public String getFinalStatus() {
		return finalStatus;
	}

	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}
}
