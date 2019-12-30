package com.tcmis.internal.catalog.beans;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseInputBean;

public class ContactLogDocumentInputBean extends BaseInputBean {

	private BigDecimal documentId;
	private String contactDocumentType;
	private BigDecimal personnelId;
	private BigDecimal contactLogId;
	private String documentUrl;
	private Date documentDate;
	private Date insertDate;
	private String documentName;
	private File theFile;
	
	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public String getContactDocumentType() {
		return contactDocumentType;
	}
	
	public void setContactDocumentType(String contactDocumentType) {
		this.contactDocumentType = contactDocumentType;
	}
	
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	
	public BigDecimal getContactLogId() {
		return contactLogId;
	}
	
	public void setContactLogId(BigDecimal contactLogId) {
		this.contactLogId = contactLogId;
	}
	
	public String getDocumentUrl() {
		return documentUrl;
	}
	
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}
	
	public Date getDocumentDate() {
		return documentDate;
	}
	
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	
	public Date getInsertDate() {
		return insertDate;
	}
	
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	
	public String getDocumentName() {
		return documentName;
	}
	
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	
	public File getTheFile() {
		return theFile;
	}
	
	public void setTheFile(File theFile) {
		this.theFile = theFile;
	}
	
	public void setHiddenFormFields() {
		
	}
}
