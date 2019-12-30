package com.tcmis.internal.hub.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: BinLabelsInputBean <br>
 * 
 * @version: 1.0, Aug 5, 2005 <br>
 *****************************************************************************/

public class BinLabelsInputBean extends BaseDataBean {
	
	private static final long serialVersionUID = 1385927450047025499L;
	private String branchPlant;
	private String room;
	private String submitSearch;
	private String generateLabel;
	private String showActiveOnly;
	private String action;
	private String oldStatus;
    private String searchField;
    private String searchMode;
    private String searchArgument;
    private String sourceHubName;
    private String binType;

	// constructor
	public BinLabelsInputBean() {
	}

	// setters
	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public void setSubmitSearch(String submitSearch) {
		this.submitSearch = submitSearch;
	}

	public void setGenerateLabel(String generateLabel) {
		this.generateLabel = generateLabel;
	}

	// getters
	public String getBranchPlant() {
		return branchPlant;
	}

	public String getRoom() {
		return room;
	}

	public String getSubmitSearch() {
		return submitSearch;
	}

	public String getGenerateLabel() {
		return generateLabel;
	}

	public String getShowActiveOnly() {
		return showActiveOnly;
	}

	public void setShowActiveOnly(String showActiveOnly) {
		this.showActiveOnly = showActiveOnly;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	/**
	 * @return the searchField
	 */
	public String getSearchField() {
		return searchField;
	}

	/**
	 * @param searchField the searchField to set
	 */
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	/**
	 * @return the searchMode
	 */
	public String getSearchMode() {
		return searchMode;
	}

	/**
	 * @param searchMode the searchMode to set
	 */
	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	/**
	 * @return the searchArgument
	 */
	public String getSearchArgument() {
		return searchArgument;
	}

	/**
	 * @param searchArgument the searchArgument to set
	 */
	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public void setSourceHubName(String sourceHubName) {
		this.sourceHubName = sourceHubName;
	}

	public String getSourceHubName() {
		return sourceHubName;
	}

	public String getBinType() {
		return binType;
	}

	public void setBinType(String binType) {
		this.binType = binType;
	}
	
	public boolean isSearch() {
		return "search".equalsIgnoreCase(action);
	}
	
	public boolean isUpdate() {
		return "update".equalsIgnoreCase(action);
	}
	
	public boolean isGenerateLabels() {
		return "generateBinLabels".equalsIgnoreCase(action);
	}

}