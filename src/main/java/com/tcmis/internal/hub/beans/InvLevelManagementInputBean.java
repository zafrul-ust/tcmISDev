package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: HubCabinetViewBean <br>
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/
public class InvLevelManagementInputBean
    extends BaseDataBean 
{
	private String 	 hub;
	private String   hubName;
    private String 	 inventoryGroup;
	private String 	 searchField;
	private String   matchingMode;    
	private String   searchArgument;    
    private String   doLimitToRecent;
    private String   daysSinceIssuedLimit;
    private String 	 submitSearch;
    private String 	 createExcel;
    private String	 showOnlyOnHand;
    private BigDecimal personnelId;
    
    private String opsEntityId;
	
  //constructor
	public InvLevelManagementInputBean() 
	{
	}

    public BigDecimal getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(BigDecimal personnelId) {
        this.personnelId = personnelId;
    }

    public String getHub() {
		return hub;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public String getHubName() {
		return hubName;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
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

	public String getMatchingMode() {
		return matchingMode;
	}

	public void setMatchingMode(String matchingMode) {
		this.matchingMode = matchingMode;
	}

	public String getSearchArgument() {
		return searchArgument;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public String getDoLimitToRecent() {
		return doLimitToRecent;
	}

	public void setDoLimitToRecent(String doLimitToRecent) {
		this.doLimitToRecent = doLimitToRecent;
	}

	public String getDaysSinceIssuedLimit() {
		return daysSinceIssuedLimit;
	}

	public void setDaysSinceIssuedLimit(String daysSinceIssuedLimit) {
		this.daysSinceIssuedLimit = daysSinceIssuedLimit;
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

	public String getShowOnlyOnHand() {
		return showOnlyOnHand;
	}

	public void setShowOnlyOnHand(String showOnlyOnHand) {
		this.showOnlyOnHand = showOnlyOnHand;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}


}