package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;
import java.io.File;
import com.tcmis.common.framework.BaseDataBean;


public class ShipmentDocumentInputBean extends BaseDataBean {

	private BigDecimal shipmentId;
	private BigDecimal documentId;
	private String documentName;
	private String documentType;
	private String companyId;
	private Date insertDate;
	private Date documentDate;
	private String documentUrl;
	private BigDecimal enteredBy;
	private String enteredByName;
	private String documentTypeDesc;
    private File theFile;
	private String fileName;
	private String submitSave;
	private String showDocuments;
	private String updateDocuments;
	private String ok;
	private String trackingNumber;
	private String confirmDelivery;

	//constructor
	public ShipmentDocumentInputBean() {
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
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
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
	public void setDocumentTypeDesc(String documentTypeDesc) {
		this.documentTypeDesc = documentTypeDesc;
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
	public Date getInsertDate() {
		return insertDate;
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
	public String getDocumentTypeDesc() {
		return documentTypeDesc;
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

	public BigDecimal getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public String getConfirmDelivery() {
		return confirmDelivery;
	}

	public void setConfirmDelivery(String confirmDelivery) {
		this.confirmDelivery = confirmDelivery;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	
}