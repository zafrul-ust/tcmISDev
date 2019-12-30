package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * This class is a placeholder for form values.  In a multipart request, files are represented by
 * set and get methods that use the class org.apache.struts.upload.FormFile, an interface with
 * basic methods to retrieve file information.  The actual structure of the FormFile is dependant
 * on the underlying impelementation of multipart request handling.  The default implementation
 * that struts uses is org.apache.struts.upload.CommonsMultipartRequestHandler.
 *
 * @version $Revision: 1.3 $ $Date: 2006/03/14 17:15:37 $
 */
public class MsdsDocumentInputForm extends ActionForm {

	protected BigDecimal documentId;
	protected BigDecimal materialId;
	protected String documentName;
	protected String documentType;
	protected String documentDate;
	protected String documentUrl;
	protected BigDecimal enteredBy;
	protected String enteredByName;
	protected String documentTypeName;
    protected String companyId;
	protected FormFile theFile;
	protected String fileName;
    protected String submitSave;
	protected String showDocuments;
	protected String updateDocuments;
	protected String ok;
	protected String calledFrom;
	protected String customerMixtureNumber;
	protected BigDecimal mixtureId;
	protected String mixtureRevisionDate;
	protected String documentTypeSource;



	//constructor
	public MsdsDocumentInputForm() {
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
	public void setDocumentDate(String documentDate) {
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
	public void setTheFile(FormFile file) {
	 this.theFile = file;
	}
	public void setFileName(String fileName) {
		if(fileName != null) {
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
	public String getDocumentDate() {
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
	public FormFile getTheFile() {
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

	public void setUpdateDocuments(String updateDocuments) {
		this.updateDocuments = updateDocuments;
	}

	public void setDocumentTypeSource(String documentTypeSource) {
		this.documentTypeSource = documentTypeSource;
	}
	
	public String getDocumentTypeSource() {
		return documentTypeSource;
	}
}