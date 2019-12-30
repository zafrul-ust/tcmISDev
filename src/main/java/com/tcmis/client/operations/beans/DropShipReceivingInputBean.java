package com.tcmis.client.operations.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: DropShipReceivingViewBean <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/

public class DropShipReceivingInputBean extends BaseDataBean {

	private static final long serialVersionUID = -6574092579116182219L;
	private String companyId;
	private String branchPlant;
	private String facilityId;
	private String inventoryGroup;
	private String dockId;
	private String searchWhat;
	private String searchType;
	private String searchText;
	private String sortBy;
	private BigDecimal expectedWithin;
	private String userAction;
	private String duplicateLine;
	private String duplicatePkgLine;
	private String duplicateKitLine;
	private String receivedReceipts;
	private String paperSize;
	private String skipKitLabels;
	private String justReceived;
	private String operatingEntityId;

	private BigDecimal customerId;

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	//constructor
	public DropShipReceivingInputBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setDockId(String dockId) {
		this.dockId = dockId;
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

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public void setExpectedWithin(BigDecimal expectedWithin) {
		this.expectedWithin = expectedWithin;
	}

	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}

	public void setDuplicateLine(String duplicateLine) {
		if (duplicateLine != null && this.doTrim) {
			this.duplicateLine = duplicateLine.trim();
		} else {
			this.duplicateLine = duplicateLine;
		}
	}

	public void setDuplicatePkgLine(String duplicatePkgLine) {
		if (duplicatePkgLine != null && this.doTrim) {
			this.duplicatePkgLine = duplicatePkgLine.trim();
		} else {
			this.duplicatePkgLine = duplicatePkgLine;
		}
	}

	public void setDuplicateKitLine(String duplicateKitLine) {
		if (duplicateKitLine != null && this.doTrim) {
			this.duplicateKitLine = duplicateKitLine.trim();
		} else {
			this.duplicateKitLine = duplicateKitLine;
		}
	}

	public void setReceivedReceipts(String receivedReceipts) {
		if (receivedReceipts != null && this.doTrim) {
			this.receivedReceipts = receivedReceipts.trim();
		} else {
			this.receivedReceipts = receivedReceipts;
		}
	}

	public void setPaperSize(String paperSize) {
		if (paperSize != null && this.doTrim) {
			this.paperSize = paperSize.trim();
		} else {
			this.paperSize = paperSize;
		}
	}

	public void setSkipKitLabels(String skipKitLabels) {
		if (skipKitLabels != null && this.doTrim) {
			this.skipKitLabels = skipKitLabels.trim();
		} else {
			this.skipKitLabels = skipKitLabels;
		}
	}

	public void setJustReceived(String justReceived) {
		this.justReceived = justReceived;
	}

	//getter
	public String getCompanyId() {
		return companyId;
	}
	public String getFacilityId() {
		return facilityId;
	}

	public String getDockId() {
		return dockId;
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

	public String getSortBy() {
		return sortBy;
	}

	public BigDecimal getExpectedWithin() {
		return expectedWithin;
	}

	public String getUserAction() {
		return userAction;
	}

	public String getDuplicateLine() {
		return duplicateLine;
	}

	public String getDuplicatePkgLine() {
		return duplicatePkgLine;
	}

	public String getDuplicateKitLine() {
		return duplicateKitLine;
	}

	public String getReceivedReceipts() {
		return receivedReceipts;
	}

	public String getPaperSize() {
		return this.paperSize;
	}

	public String getSkipKitLabels() {
		return this.skipKitLabels;
	}

	public String getJustReceived() {
		return this.justReceived;
	}

	public String getBranchPlant() {
		return branchPlant;
	}

	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setOperatingEntityId(String operatingEntityId) {
		this.operatingEntityId = operatingEntityId;
	}

	public String getOperatingEntityId() {
		return operatingEntityId;
	}

}