package com.tcmis.internal.supply.beans;

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
public class ShipmentDocumentInputForm extends ActionForm {

	protected BigDecimal documentId;
	protected BigDecimal shipmentId;
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
	
	protected String ok;
	protected String confirmDelivery;

	//constructor
	public ShipmentDocumentInputForm() {
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
	
	public void setUpdateDocuments(String updateDocuments) {
	 this.updateDocuments = updateDocuments;
	}
	public void setOk(String ok) {
	 this.ok = ok;
	}

	//getters
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
	
	public String getUpdateDocuments() {
	 return updateDocuments;
	}
	public String getOk() {
	 return ok;
	}

	public BigDecimal getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}

	public String getConfirmDelivery() {
		return confirmDelivery;
	}

	public void setConfirmDelivery(String confirmDelivery) {
		this.confirmDelivery = confirmDelivery;
	}

	
	
}
