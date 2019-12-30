package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ReceiptDocumentViewBean <br>
 * @version: 1.0, Oct 19, 2005 <br>
 *****************************************************************************/


public class PoDocumentViewBean extends BaseDataBean {

	private BigDecimal documentId;
	private BigDecimal radianPo;
	private String documentName;
	private String documentType;
	private Date insertDate;
	private Date documentDate;
	private String documentUrl;
	private BigDecimal enteredBy;
	private String enteredByName;
	private String documentTypeDesc;
    private String companyId;
    private String companyName;
    private String opsEntityId;
    private String jspLabel;

	//constructor
	public PoDocumentViewBean() {
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
	 public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	 
	 public void setJspLabel(String jspLabel) {
			this.jspLabel = jspLabel;
	 }
	 

	/*public BigDecimal getPoLine() {
		return PoLine;
	}

	public void setPoLine(BigDecimal poLine) {
		PoLine = poLine;
	}*/
	 
	 
	public String getJspLabel() {
		return jspLabel;
	}

	//getters
	public BigDecimal getDocumentId() {
		return documentId;
	}
	
	public BigDecimal getRadianPo() {
		return radianPo;
	}

	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
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
	public String getCompanyId() {
	 return companyId;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}