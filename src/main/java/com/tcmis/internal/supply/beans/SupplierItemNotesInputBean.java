package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class SupplierItemNotesInputBean extends BaseDataBean {
	
	private String opsEntityId;
	private String searchArgument;
	private String searchField;
    private String searchMode;
    private String uAction;
    private String supplierName;
    private String supplier;
    
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public String getSearchArgument() {
		return searchArgument;
	}
	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getSearchMode() {
		return searchMode;
	}
	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}
	public String getuAction() {
		return uAction;
	}
	public void setuAction(String uAction) {
		this.uAction = uAction;
	}
}
