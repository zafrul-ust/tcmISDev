package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseInputBean;

/******************************************************************************
 * CLASSNAME: HubCabinetViewBean <br>
 * 
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/

public class AllocationAnalysisInputBean extends BaseInputBean {

	private BigDecimal	csrPersonnelId;
	private BigDecimal	customerId;
	private BigDecimal	daySpan;
	private String		daySpanCriteria;
	private String		facilityId;
	private String		hub;
	private String		inventoryGroup;
	private BigDecimal	itemOrMr;
	private String		itemOrMrCriteria;
	private String		lotStatus;
	private String		progressStatus;
	private String		sortBy;
	private String		searchTypeNonScheduled;
	private String		searchTypeScheduled;
	private String		searchTypeTransfer;

	// constructor
	public AllocationAnalysisInputBean() {
	}

	public BigDecimal getCsrPersonnelId() {
		return csrPersonnelId;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public BigDecimal getDaySpan() {
		return this.daySpan;
	}

	public String getDaySpanCriteria() {
		return this.daySpanCriteria;
	}

	public String getFacilityId() {
		return facilityId;
	}

	// getters
	public String getHub() {
		return hub;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public BigDecimal getItemOrMr() {
		return this.itemOrMr;
	}

	public String getItemOrMrCriteria() {
		return this.itemOrMrCriteria;
	}

	public String getLotStatus() {
		return lotStatus;
	}

	public String getProgressStatus() {
		return progressStatus;
	}

	public String getSortBy() {
		return sortBy;
	}

	public String getSearchTypeNonScheduled() {
		return searchTypeNonScheduled;
	}

	public String getSearchTypeScheduled() {
		return searchTypeScheduled;
	}

	public String getSearchTypeTransfer() {
		return searchTypeTransfer;
	}

	public boolean hasFacilityId() {
		return facilityId != null;
	}

	public boolean hasHub() {
		return hub != null;
	}

	public boolean hasCustomerId() {
		return customerId != null;
	}
	
	public boolean hasCsrPersonnelId() {
		return csrPersonnelId != null;
	}

	public boolean hasInventoryGroup() {
		return inventoryGroup != null;
	}

	public boolean hasItemId() {
		return "itemid".equalsIgnoreCase(itemOrMrCriteria) && itemOrMr != null;
	}

	public boolean hasLotStatus() {
		return lotStatus != null;
	}

	public boolean hasMr() {
		return "mr".equalsIgnoreCase(itemOrMrCriteria) && itemOrMr != null;
	}

	public boolean hasNeededDaySpan() {
		return "needed".equals(daySpanCriteria) && daySpan != null;
	}

	public boolean hasOnTimeDaySpan() {
		return !"needed".equals(daySpanCriteria) && daySpan != null;
	}

	public boolean hasProgressStatus() {
		return progressStatus != null;
	}

	public boolean hasSortBy() {
		return sortBy != null;
	}

	public boolean hasSearchTypeNonScheduled() {
		return searchTypeNonScheduled != null;
	}

	public boolean hasSearchTypeScheduled() {
		return searchTypeScheduled != null;
	}

	public boolean hasSearchTypeTransfer() {
		return searchTypeTransfer != null;
	}

	public boolean isSearch() {
		return super.isSearch();
	}

	public void setCsrPersonnelId(BigDecimal csrPersonnelId) {
		this.csrPersonnelId = csrPersonnelId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public void setDaySpan(BigDecimal b) {
		this.daySpan = b;
	}

	public void setDaySpanCriteria(String s) {
		this.daySpanCriteria = s;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub

	}

	// setters
	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setItemOrMr(BigDecimal b) {
		this.itemOrMr = b;
	}

	public void setItemOrMrCriteria(String s) {
		this.itemOrMrCriteria = s;
	}

	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}

	public void setProgressStatus(String s) {
		this.progressStatus = s;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public void setSearchTypeNonScheduled(String searchTypeNonScheduled) {
		this.searchTypeNonScheduled = searchTypeNonScheduled;
	}

	public void setSearchTypeScheduled(String searchTypeScheduled) {
		this.searchTypeScheduled = searchTypeScheduled;
	}

	public void setSearchTypeTransfer(String searchTypeTransfer) {
		this.searchTypeTransfer = searchTypeTransfer;
	}
}