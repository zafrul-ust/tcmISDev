package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

public class DocumentBean {

	private BigDecimal documentId;
	private String documentName;
	private String documentType;
	private Date documentDate;
	private String documentUrl;
	private Date enteredOn;
	private BigDecimal enteredBy;
	private String enteredByName;
	private Date deletedOn;
	private BigDecimal deletedBy;
	private String deletedByName;
	private String status;
	
	public DocumentBean(){}

	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public String getDocumentUrl() {
		return documentUrl;
	}

	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	public Date getEnteredOn() {
		return enteredOn;
	}

	public void setEnteredOn(Date enteredOn) {
		this.enteredOn = enteredOn;
	}

	public BigDecimal getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(BigDecimal enteredBy) {
		this.enteredBy = enteredBy;
	}

	public Date getDeletedOn() {
		return deletedOn;
	}

	public void setDeletedOn(Date deletedOn) {
		this.deletedOn = deletedOn;
	}

	public BigDecimal getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(BigDecimal deletedBy) {
		this.deletedBy = deletedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEnteredByName() {
		return enteredByName;
	}

	public void setEnteredByName(String enteredByName) {
		this.enteredByName = enteredByName;
	}

	public String getDeletedByName() {
		return deletedByName;
	}

	public void setDeletedByName(String deletedByName) {
		this.deletedByName = deletedByName;
	}
}
