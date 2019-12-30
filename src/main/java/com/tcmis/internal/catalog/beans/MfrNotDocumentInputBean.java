package com.tcmis.internal.catalog.beans;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseInputBean;

@SuppressWarnings("serial")
public class MfrNotDocumentInputBean extends BaseInputBean {

	private BigDecimal documentId;
	private BigDecimal mfrReqCategoryId;
	private BigDecimal notificationId;
	private Date documentDate;
	private String documentName;
	private File theFile;
	private boolean showDeleted;
	
	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}
	
	public Date getDocumentDate() {
		return documentDate;
	}
	
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
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
	
	public BigDecimal getMfrReqCategoryId() {
		return mfrReqCategoryId;
	}

	public void setMfrReqCategoryId(BigDecimal mfrReqCategoryId) {
		this.mfrReqCategoryId = mfrReqCategoryId;
	}

	public BigDecimal getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(BigDecimal notificationId) {
		this.notificationId = notificationId;
	}

	public boolean isShowDeleted() {
		return showDeleted;
	}

	public void setShowDeleted(boolean showDeleted) {
		this.showDeleted = showDeleted;
	}

	public void setHiddenFormFields() {
		
	}
}
