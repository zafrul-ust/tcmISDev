package com.tcmis.client.common.beans;

import java.util.Date;
import java.math.BigDecimal;
import java.io.File;
import com.tcmis.common.framework.BaseDataBean;


public class MsdsDocumentInputBean extends BaseDataBean {

	private BigDecimal documentId;
	private BigDecimal materialId;
	private String documentName;
	private String documentType;
	private String documentTypeSource;
	private String companyId;
	private Date documentDate;
	private String documentUrl;
	private BigDecimal enteredBy;
	private String enteredByName;
	private String documentTypeName;
    private File theFile;
	private String fileName;
	private String submitSave;
	private String showDocuments;
	private String updateDocuments;
	private String ok;
	private Date deletedOn;
    private BigDecimal deletedBy;  
    private String deletedByName;
    private String includeDeleted;
    private String calledFrom;
    private String customerMixtureNumber;
	private BigDecimal mixtureId;
	private String mixtureRevisionDate;
	private String documentSource;

    //constructor
	public MsdsDocumentInputBean() {
	}

	//setters
	
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}
	
	public void setEnteredBy(BigDecimal enteredBy) {
		this.enteredBy = enteredBy;
	}
	
	public void setEnteredByName(String enteredByName) {
		this.enteredByName = enteredByName;
	}
		
	public void setTheFile(File file) {
		this.theFile = file;
	}
	
	 public void setFileName(String fileName) {
		if(fileName != null && this.doTrim) {
			this.fileName = fileName.trim();
		}
		else {
			this.fileName = fileName;
		}
	}
	 
    public void setSubmitSave(String submitSave) {
	 this.submitSave = submitSave;
	}
    
	public void setShowDocuments(String showDocuments) {
	 this.showDocuments = showDocuments;
	}
	
	public void setUpdateDocuments(String updateDocuments) {
	 this.updateDocuments = updateDocuments;
	}
	
	public void setOk(String ok) {
	 this.ok = ok;
	}
	public void setCalledFrom(String calledFrom) {
		 this.calledFrom = calledFrom;
	}	
	public void setCustomerMixtureNumber(String customerMixtureNumber) {
		 this.customerMixtureNumber = customerMixtureNumber;
	}
	public void setMixtureId(BigDecimal mixtureId) {
		 this.mixtureId = mixtureId;
	}
	public void setMixtureRevisionDate(String mixtureRevisionDate) {
		 this.mixtureRevisionDate = mixtureRevisionDate;
	}
	//getters
	public String getMixtureRevisionDate() {
		return mixtureRevisionDate;
	}	
	public BigDecimal getMixtureId() {
		return mixtureId;
	}	
	public String getCustomerMixtureNumber() {
		return customerMixtureNumber;
	}
	public String getCalledFrom() {
		return calledFrom;
	}
	public BigDecimal getDocumentId() {
		return documentId;
	}
	
	public String getDocumentName() {
		return documentName;
	}
	
	public String getDocumentType() {
		return documentType;
	}
		
	public Date getDocumentDate() {
		return documentDate;
	}
	
	public String getDocumentUrl() {
		return documentUrl;
	}
	
	public BigDecimal getEnteredBy() {
		return enteredBy;
	}
	
	public String getEnteredByName() {
		return enteredByName;
	}
		
	public File getTheFile() {
	 return this.theFile;
	}
	
	public String getFileName() {
			return this.fileName;
	}
	
	public String getSubmitSave() {
	 return this.submitSave;
	}
	
	public String getShowDocuments() {
	 return this.showDocuments;
	}
	
	public String getUpdateDocuments() {
	 return updateDocuments;
	}
	
	public String getOk() {
	 return ok;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
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

	public String getDeletedByName() {
		return deletedByName;
	}

	public void setDeletedByName(String deletedByName) {
		this.deletedByName = deletedByName;
	}

	public String getIncludeDeleted() {
		return includeDeleted;
	}

	public void setIncludeDeleted(String includeDeleted) {
		this.includeDeleted = includeDeleted;
	}

	public String getDocumentTypeSource() {
		return documentTypeSource;
	}

	public void setDocumentTypeSource(String documentTypeSource) {
		this.documentTypeSource = documentTypeSource;
	}

	public String getDocumentSource() {
		return documentSource;
	}

	public void setDocumentSource(String documentSource) {
		this.documentSource = documentSource;
	}
}