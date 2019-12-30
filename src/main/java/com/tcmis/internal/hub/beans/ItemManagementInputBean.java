package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class ItemManagementInputBean
    extends BaseDataBean 
{
	private String 	 hub;
    private String   plantId;
    private String   bldgId;	
    private String 	 inventoryGroup;
	private String 	 searchField;
	private String   searchMode;    
	private String   searchArgument;    
    private String	 showActiveOnly;
    private String	 showMinMaxOnly;
    
    private String 	 submitSearch;
    private String 	 createExcel;    
    private String   userAction;	
    
	public ItemManagementInputBean() 
	{
	}

	public String getHub() {
		return hub;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public String getPlantId() {
		return plantId;
	}

	public void setPlantId(String plantId) {
		this.plantId = plantId;
	}

	public String getBldgId() {
		return bldgId;
	}

	public void setBldgId(String bldgId) {
		this.bldgId = bldgId;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
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

	public String getSearchArgument() {
		return searchArgument;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public String getSubmitSearch() {
		return submitSearch;
	}

	public void setSubmitSearch(String submitSearch) {
		this.submitSearch = submitSearch;
	}

	public String getCreateExcel() {
		return createExcel;
	}

	public void setCreateExcel(String createExcel) {
		this.createExcel = createExcel;
	}

	public String getShowActiveOnly() {
		return showActiveOnly;
	}

	public void setShowActiveOnly(String showActiveOnly) {
		this.showActiveOnly = showActiveOnly;
	}

	public String getShowMinMaxOnly() {
		return showMinMaxOnly;
	}

	public void setShowMinMaxOnly(String showMinMaxOnly) {
		this.showMinMaxOnly = showMinMaxOnly;
	}

	public String getUserAction() {
		return userAction;
	}

	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}

}