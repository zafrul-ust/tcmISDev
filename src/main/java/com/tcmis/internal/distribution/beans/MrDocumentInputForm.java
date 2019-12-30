package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;

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
public class MrDocumentInputForm extends ActionForm {

	protected BigDecimal documentId;
	protected BigDecimal prNumber;
	protected String documentName;
	protected String documentType;
	protected String insertDate;
	protected String documentDate;
	protected String documentUrl;
	protected BigDecimal enteredBy;
	protected String enteredByName;
	protected String documentTypeDesc;
  protected String companyId;
	protected FormFile theFile;
	protected String fileName;
  protected String submitSave;
	protected String showDocuments;
	protected String updateDocuments;
	protected String inventoryGroup;
	protected String ok;
	private String launchedFrom;


	//constructor
	public MrDocumentInputForm() {
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
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
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
	public void setDocumentTypeDesc(String documentTypeDesc) {
		this.documentTypeDesc = documentTypeDesc;
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
	public void setInventoryGroup(String inventoryGroup) {
	 this.inventoryGroup = inventoryGroup;
	}
	public void setUpdateDocuments(String updateDocuments) {
	 this.updateDocuments = updateDocuments;
	}
	public void setOk(String ok) {
	 this.ok = ok;
	}
	public void setLaunchedFrom(String launchedFrom) {
		 this.launchedFrom = launchedFrom; 
	}
		
	//getters
	public String getLaunchedFrom() {
		return launchedFrom;
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
	public String getInsertDate() {
		return insertDate;
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
	public String getDocumentTypeDesc() {
		return documentTypeDesc;
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
	public String getInventoryGroup() {
	 return inventoryGroup;
	}
	public String getUpdateDocuments() {
	 return updateDocuments;
	}
	public String getOk() {
	 return ok;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	
	
}
