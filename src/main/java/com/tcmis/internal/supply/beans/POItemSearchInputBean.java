package com.tcmis.internal.supply.beans;

//import java.util.Date;
//import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class POItemSearchInputBean extends BaseDataBean 
{
	private String validBPO;
	private String companyId;	
	private String shipToId;
	private String inventoryGroup;
    private String searchArgument_1;
    private String searchArgument_2;
	private String sortBy;
    private String mode;
	private String userAction;    
	private String submitSearch;    

	public POItemSearchInputBean() 
	{
	}

	public String getSubmitSearch() {
		return submitSearch;
	}

	public void setSubmitSearch(String submitSearch) {
		this.submitSearch = submitSearch;
	}

	public String getValidBPO() {
		return validBPO;
	}

	public void setValidBPO(String validBPO) {
		this.validBPO = validBPO;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getShipToId() {
		return shipToId;
	}

	public void setShipToId(String shipToId) {
		this.shipToId = shipToId;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public String getSearchArgument_1() {
		return searchArgument_1;
	}

	public void setSearchArgument_1(String searchArgument_1) {
		this.searchArgument_1 = searchArgument_1;
	}

	public String getSearchArgument_2() {
		return searchArgument_2;
	}

	public void setSearchArgument_2(String searchArgument_2) {
		this.searchArgument_2 = searchArgument_2;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getUserAction() {
		return userAction;
	}

	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}

}