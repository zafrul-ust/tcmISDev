package com.tcmis.client.peiprojects.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PeiProjectDocumentBean <br>
 * @version: 1.0, Dec 15, 2005 <br>
 *****************************************************************************/


public class PeiProjectDocumentBean extends BaseDataBean {

	private BigDecimal projectId;
	private BigDecimal documentId;
	private String documentName;
	private String url;
	private String customerDisplay;
	private String delete;
	private String companyId;
	//constructor
	public PeiProjectDocumentBean() {
	}

	//setters
	public void setProjectId(BigDecimal projectId) {
		this.projectId = projectId;
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setCustomerDisplay(String customerDisplay) {
		this.customerDisplay = customerDisplay;
	}
	public void setDelete(String delete) {
	 this.delete = delete;
	}
	public void setCompanyId(String companyId) {
	 this.companyId = companyId;
	}

	//getters
	public BigDecimal getProjectId() {
		return projectId;
	}
	public BigDecimal getDocumentId() {
		return documentId;
	}
	public String getDocumentName() {
		return documentName;
	}
	public String getUrl() {
		return url;
	}
	public String getCustomerDisplay() {
		return customerDisplay;
	}
  public String getDelete() {
	 return delete;
	}
	public String getCompanyId() {
	 return companyId;
	}
}