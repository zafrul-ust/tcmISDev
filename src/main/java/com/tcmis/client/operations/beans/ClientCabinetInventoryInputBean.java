package com.tcmis.client.operations.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubCabinetViewBean <br>
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/
public class ClientCabinetInventoryInputBean extends BaseDataBean 
{
	
	private static final long serialVersionUID = -4781510944836138476L;
	private String   branchPlant;
	private String   facilityName;
	private String   facilityId;
	private String   application;
	private String   sortBy;
	private String   searchArgument;
	private String   matchingMode;
	private String 	 searchField;
	private String[] cabinetIdArray;
	private String   submitSearch;
	private String   action;
	private String   hubName;
	private String   cabinetName;	
	private BigDecimal   expireInFrom;
	private BigDecimal   expireInTo;
	private String   companyId;
	private String   positiveQ;

	public ClientCabinetInventoryInputBean() 
	{
	}

	public String getBranchPlant() {
		return branchPlant;
	}

	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSearchArgument() {
		return searchArgument;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public String getMatchingMode() {
		return matchingMode;
	}

	public void setMatchingMode(String matchingMode) {
		this.matchingMode = matchingMode;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String[] getCabinetIdArray() {
		return cabinetIdArray;
	}

	public void setCabinetIdArray(String[] cabinetIdArray) {
		this.cabinetIdArray = cabinetIdArray;
	}

	public String getSubmitSearch() {
		return submitSearch;
	}

	public void setSubmitSearch(String submitSearch) {
		this.submitSearch = submitSearch;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getHubName() {
		return hubName;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	public String getCabinetName() {
		return cabinetName;
	}

	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
	}

	public BigDecimal getExpireInFrom() {
		return expireInFrom;
	}

	public void setExpireInFrom(BigDecimal expireInFrom) {
		this.expireInFrom = expireInFrom;
	}

	public BigDecimal getExpireInTo() {
		return expireInTo;
	}

	public void setExpireInTo(BigDecimal expireInTo) {
		this.expireInTo = expireInTo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getPositiveQ() {
		return positiveQ;
	}

	public void setPositiveQ(String positiveQ) {
		this.positiveQ = positiveQ;
	}

	
}