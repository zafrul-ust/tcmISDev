package com.tcmis.client.common.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


public class MsdsDocumentViewBean extends BaseDataBean {

	private BigDecimal documentId;
	private BigDecimal materialId;
	private String documentName;
	private String documentType;
	private Date documentDate;
	private String documentUrl;
	private BigDecimal enteredBy;
	private String enteredByName;
	private String documentTypeName;
    private String companyId;
    private Date enteredOn;
    private Date deletedOn;
    private BigDecimal deletedBy;  
    private String deletedByName;
    
	//constructor
	public MsdsDocumentViewBean() {
	}

	//setters
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}
	
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
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
	
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
		//getters
	public BigDecimal getDocumentId() {
		return documentId;
	}
		
	public BigDecimal getMaterialId() {
		return materialId;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
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
		
	public String getCompanyId() {
	 return companyId;
	}

	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}

	public Date getEnteredOn() {
		return enteredOn;
	}

	public void setEnteredOn(Date enteredOn) {
		this.enteredOn = enteredOn;
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

	
	
				
}