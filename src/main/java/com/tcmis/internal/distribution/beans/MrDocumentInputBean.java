package com.tcmis.internal.distribution.beans;

import java.util.Date;
import java.math.BigDecimal;
import java.io.File;
import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ReceiptDocumentViewBean <br>
 * @version: 1.0, Oct 19, 2005 <br>
 *****************************************************************************/


public class MrDocumentInputBean extends BaseDataBean {

	private BigDecimal documentId;
	private BigDecimal prNumber;
	//private BigDecimal poLine;
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
	private String inventoryGroup;
	private String ok;
	private String launchedFrom;


	//constructor
	public MrDocumentInputBean() {
	}

	//setters
	
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	
	
	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
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

	/*public BigDecimal getPoLine() {
		return poLine;
	}

	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}*/

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
	
	
	public boolean isLaunchedFromSupplier() {
		return "supplier".equalsIgnoreCase(launchedFrom);
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
	public String getInventoryGroup() {
	 return inventoryGroup;
	}
	public String getUpdateDocuments() {
	 return updateDocuments;
	}
	public String getOk() {
	 return ok;
	}
}