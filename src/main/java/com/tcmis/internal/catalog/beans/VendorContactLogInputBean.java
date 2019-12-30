package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseInputBean;

public class VendorContactLogInputBean extends BaseInputBean {

	private final String VIEW_DOCUMENT_ACTION = "viewDocuments";
	
	private BigDecimal qId;
	private BigDecimal contactLogId;
	
	private String contactStatus;
	private String contactPurpose;
	private String contactLogType;
	private String contactReference;
	private String contactName;
	private String draftStatus;
	
	public BigDecimal getqId() {
		return qId;
	}
	public void setqId(BigDecimal qId) {
		this.qId = qId;
	}
	public BigDecimal getContactLogId() {
		return contactLogId;
	}
	public void setContactLogId(BigDecimal contactLogId) {
		this.contactLogId = contactLogId;
	}
	public String getContactStatus() {
		return contactStatus;
	}
	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
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
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getDraftStatus() {
		return draftStatus;
	}
	public void setDraftStatus(String draftStatus) {
		this.draftStatus = draftStatus;
	}
	public boolean isViewDocumentAction() {
		return VIEW_DOCUMENT_ACTION.equals(uAction);
	}
	public boolean hasContactLogId() {
		return contactLogId != null;
	}
	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
		
	}
}
