package com.tcmis.internal.hub.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.util.Date;


/******************************************************************************
 * CLASSNAME: ReceivingReportInputBean <br>
 * @version: 1.0, Aug 5, 2008 <br>
 *****************************************************************************/


public class ReceivingReportInputBean extends BaseDataBean {

	private String opsEntityId;
	private String hub;
	private String hubName;
	private String inventoryGroup;
	private String inventoryGroupName;
	private String companyId;
	private String facilityId;
	private String facilityName;
	private String searchWhat;
	private String searchWhatDesc;
	private String searchType;
	private String searchTypeDesc;
	private String searchText;
	private Date beginDate;
	private Date endDate;
	private String sortBy;
	private String sortByDesc;
	private String unitOfMessure;
	private String unitOfMessureDesc;

	//constructor
	public ReceivingReportInputBean() {
	}

	//setters
	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setSearchWhat(String searchWhat) {
		this.searchWhat = searchWhat;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public void setSearchWhatDesc(String searchWhatDesc) {
		this.searchWhatDesc = searchWhatDesc;
	}

	public void setSortByDesc(String sortByDesc) {
		this.sortByDesc = sortByDesc;
	}

	public void setSearchTypeDesc(String searchTypeDesc) {
		this.searchTypeDesc = searchTypeDesc;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setUnitOfMessure(String unitOfMessure) {
		this.unitOfMessure = unitOfMessure;
	}

	public void setUnitOfMessureDesc(String unitOfMessureDesc) {
		this.unitOfMessureDesc = unitOfMessureDesc;
	}

	//getters
	public String getHub() {
		return hub;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getSearchWhat() {
		return searchWhat;
	}

	public String getSearchType() {
		return searchType;
	}

	public String getSearchText() {
		return searchText;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getSortBy() {
		return sortBy;
	}

	public String getHubName() {
		return hubName;
	}

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public String getSearchWhatDesc() {
		return searchWhatDesc;
	}

	public String getSortByDesc() {
		return sortByDesc;
	}

	public String getSearchTypeDesc() {
		return searchTypeDesc;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getUnitOfMessure() {
		return unitOfMessure;
	}

	public String getUnitOfMessureDesc() {
		return unitOfMessureDesc;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
}