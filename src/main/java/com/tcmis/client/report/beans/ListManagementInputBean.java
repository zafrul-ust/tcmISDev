package com.tcmis.client.report.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;

public class ListManagementInputBean extends BaseDataBean{
	
	private String listId;
	private String listName;
	private String listDescription;
	private String reference;
	private String companyId;
	private String active;
	private String uAction;
	private BigDecimal maxData;
	
	//constructor
	public ListManagementInputBean() {
	}
	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public String getListDescription() {
		return listDescription;
	}
	public void setListDescription(String listDescription) {
		this.listDescription = listDescription;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getuAction() {
		return uAction;
	}
	public void setuAction(String uAction) {
		this.uAction = uAction;
	}
	public BigDecimal getMaxData() {
		return maxData;
	}
	public void setMaxData(BigDecimal maxData) {
		this.maxData = maxData;
	}
	
}
