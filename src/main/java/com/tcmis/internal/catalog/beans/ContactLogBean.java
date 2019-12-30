package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class ContactLogBean extends BaseDataBean {
	
	private String uAction;
	
	private BigDecimal contactLogId;
	private BigDecimal materialId;
	private Date revisionDate;
	private BigDecimal currentMaterialId;
	private Date currentRevisionDate;
	
	private String contactStatus;
	private String contactPurpose;
	private String contactLogType;
	private String contactReference;
	private String notes;
	private String contactName;
	private String draftStatus;
	
	private Collection<ContactLogMaterialBean> materialDataColl;
	private Collection<ContactLogHistoryBean> logHistoryDataColl;
	private Collection<ContactLogDocumentInputBean> documentColl;
	private Collection<String> contactPurposeColl;
	private Collection<String> contactTypeColl;
	private Collection<String> contactStatusColl;
	
	// This column is for passing the revisionDate String from ViewMsds page
	private String revisionDateString;
	
	public BigDecimal getContactLogId() {
		return contactLogId;
	}

	public void setContactLogId(BigDecimal contactLogId) {
		this.contactLogId = contactLogId;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}
	
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	
	public Date getRevisionDate() {
		return revisionDate;
	}
	
	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}

	public Collection<ContactLogMaterialBean> getMaterialDataColl() {
		return materialDataColl;
	}

	public void setMaterialDataColl(
			Collection<ContactLogMaterialBean> materialDataColl) {
		this.materialDataColl = materialDataColl;
	}

	public Collection<ContactLogHistoryBean> getLogHistoryDataColl() {
		return logHistoryDataColl;
	}

	public void setLogHistoryDataColl(
			Collection<ContactLogHistoryBean> logHistoryDataColl) {
		this.logHistoryDataColl = logHistoryDataColl;
	}

	public String getuAction() {
		return uAction;
	}

	public void setuAction(String uAction) {
		this.uAction = uAction;
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

	public Collection<ContactLogDocumentInputBean> getDocumentColl() {
		return documentColl;
	}

	public void setDocumentColl(Collection<ContactLogDocumentInputBean> documentColl) {
		this.documentColl = documentColl;
	}

	public Collection<String> getContactPurposeColl() {
		return contactPurposeColl;
	}

	public void setContactPurposeColl(Collection<String> contactPurposeColl) {
		this.contactPurposeColl = contactPurposeColl;
	}

	public Collection<String> getContactTypeColl() {
		return contactTypeColl;
	}

	public void setContactTypeColl(Collection<String> contactTypeColl) {
		this.contactTypeColl = contactTypeColl;
	}

	public Collection<String> getContactStatusColl() {
		return contactStatusColl;
	}

	public void setContactStatusColl(Collection<String> contactStatusColl) {
		this.contactStatusColl = contactStatusColl;
	}

	public BigDecimal getCurrentMaterialId() {
		return currentMaterialId;
	}

	public void setCurrentMaterialId(BigDecimal currentMaterialId) {
		this.currentMaterialId = currentMaterialId;
	}

	public Date getCurrentRevisionDate() {
		return currentRevisionDate;
	}

	public void setCurrentRevisionDate(Date currentRevisionDate) {
		this.currentRevisionDate = currentRevisionDate;
	}

	public String getDraftStatus() {
		return draftStatus;
	}

	public void setDraftStatus(String draftStatus) {
		this.draftStatus = draftStatus;
	}
	
	public String getRevisionDateString() {
		return revisionDateString;
	}

	public void setRevisionDateString(String revisionDateString) {
		this.revisionDateString = revisionDateString;
	}

}
