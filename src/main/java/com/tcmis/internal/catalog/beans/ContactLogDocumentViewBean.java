package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class ContactLogDocumentViewBean extends BaseDataBean {

	private BigDecimal documentId;
	private BigDecimal contactLogId;
	private BigDecimal personnelId;
	private String personnelName;
	private Date documentDate;
	private Date insertDate;
	private String contactDocumentType;
	private String documentUrl;
	private String documentName;
	
	public BigDecimal getDocumentId() {
		return documentId;
	
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	
	}
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
	
	public String getPersonnelName() {
		return personnelName;
	}
	
	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
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
	
	public String getContactDocumentType() {
		return contactDocumentType;
	}
	
	public void setContactDocumentType(String contactDocumentType) {
		this.contactDocumentType = contactDocumentType;
	}
	
	public String getDocumentUrl() {
		return documentUrl;
	}
	
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}
	
	public String getDocumentName() {
		return documentName;
	}
	
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
}
