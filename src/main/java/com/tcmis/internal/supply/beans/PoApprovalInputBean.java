package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.StringHandler;

public class PoApprovalInputBean extends BaseInputBean{

	private String uAction;
	private String opsEntityId;
	private String branchPlant;
	private String inventoryGroupId;
	private BigDecimal buyerId;
	private BigDecimal approverId;
	private Date createdFromDate;
	private Date createdToDate;
	private BigDecimal supplierId;
	private String searchWhat;
	private String searchType;
	private String searchText;
	private String status;
	private String opsEntityDesc;
	private String hubName;
	private String inventoryGroupDesc;
	private String selectedApprover;
	private String selectedBuyer;
	private String supplierName;
	private String selectedStatus;
	private String searchWhatDesc;
	private String searchTypeDesc;
	
	public String getuAction() {
		return uAction;
	}

	public void setuAction(String uAction) {
		this.uAction = uAction;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public String getBranchPlant() {
		return branchPlant;
	}

	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}

	public String getInventoryGroupId() {
		return inventoryGroupId;
	}

	public void setInventoryGroupId(String inventoryGroupId) {
		this.inventoryGroupId = inventoryGroupId;
	}

	public BigDecimal getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(BigDecimal buyerId) {
		this.buyerId = buyerId;
	}

	public BigDecimal getApproverId() {
		return approverId;
	}

	public void setApproverId(BigDecimal approverId) {
		this.approverId = approverId;
	}

	public Date getCreatedFromDate() {
		return createdFromDate;
	}

	public void setCreatedFromDate(Date confirmedFromDate) {
		this.createdFromDate = confirmedFromDate;
	}

	public Date getCreatedToDate() {
		return createdToDate;
	}

	public void setCreatedToDate(Date confirmedToDate) {
		this.createdToDate = confirmedToDate;
	}

	public BigDecimal getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(BigDecimal supplierId) {
		this.supplierId = supplierId;
	}

	public String getSearchWhat() {
		return searchWhat;
	}

	public void setSearchWhat(String searchWhat) {
		this.searchWhat = searchWhat;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public boolean hasInventoryGroup() {
		return ! StringHandler.isBlankString(inventoryGroupId);
	}
	
	public boolean hasHub() {
		return ! StringHandler.isBlankString(branchPlant);
	}
	
	public boolean hasSupplier() {
		return supplierId != null;
	}
	
	public boolean hasAllBuyers() {
		return ! hasNoBuyer() && buyerId.intValue() == 0;
	}
	
	public boolean hasNoBuyer() {
		return buyerId == null;
	}
	
	public boolean hasAllApprovers() {
		return ! hasNoApprover() && approverId.intValue() == 0;
	}
	
	public boolean hasNoApprover() {
		return approverId == null;
	}
	
	public boolean hasCreatedDateFloor() {
		return createdFromDate != null;
	}
	
	public boolean hasCreatedDateCeiling() {
		return createdToDate != null;
	}
	
	public boolean hasSearchText() {
		return ! StringHandler.isBlankString(searchText);
	}
	
	public boolean hasStatus() {
		return ! (StringHandler.isBlankString(status) || status.equalsIgnoreCase("ALL"));
	}
	
	public boolean isSearchContains() {
		return "CONTAINS".equals(searchType);
	}
	
	public boolean isSearchEquals() {
		return "EQUALS".equals(searchType);
	}
	
	public boolean isSearchStartsWith() {
		return "START_WITH".equals(searchType);
	}
	
	public boolean isSearchEndsWith() {
		return "END_WITH".equals(searchType);
	}
	
	public boolean isShowApprovalChain() {
		return "showApprovalChain".equals(uAction);
	}
	
	public boolean isRejectPo() {
		return "rejectPo".equalsIgnoreCase(uAction);
	}
	
	public boolean isApprovePo() {
		return "approvePo".equalsIgnoreCase(uAction);
	}
	
	public boolean isApproveAllPos() {
		return "approveAll".equalsIgnoreCase(uAction);
	}
	
	public boolean isSelectRejectionCode() {
		return "selectRejectionCode".equalsIgnoreCase(uAction);
	}

	public String getOpsEntityDesc() {
		return opsEntityDesc;
	}

	public void setOpsEntityDesc(String opsEntityDesc) {
		this.opsEntityDesc = opsEntityDesc;
	}

	public String getHubName() {
		return hubName;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	public String getInventoryGroupDesc() {
		return inventoryGroupDesc;
	}

	public void setInventoryGroupDesc(String inventoryGroupDesc) {
		this.inventoryGroupDesc = inventoryGroupDesc;
	}

	public String getSelectedApprover() {
		return selectedApprover;
	}

	public void setSelectedApprover(String selectedApprover) {
		this.selectedApprover = selectedApprover;
	}

	public String getSelectedBuyer() {
		return selectedBuyer;
	}

	public void setSelectedBuyer(String selectedBuyer) {
		this.selectedBuyer = selectedBuyer;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSelectedStatus() {
		return selectedStatus;
	}

	public void setSelectedStatus(String selectedStatus) {
		this.selectedStatus = selectedStatus;
	}

	public String getSearchWhatDesc() {
		return searchWhatDesc;
	}

	public void setSearchWhatDesc(String searchWhatDesc) {
		this.searchWhatDesc = searchWhatDesc;
	}

	public String getSearchTypeDesc() {
		return searchTypeDesc;
	}

	public void setSearchTypeDesc(String searchTypeDesc) {
		this.searchTypeDesc = searchTypeDesc;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
		
	}
}
