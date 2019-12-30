package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class PickingUnitIssueBean extends BaseDataBean {

	private String allocateByMfgLot;
	private String application;
	private String applicationDesc;
	private String catalogDesc;
	private String catalogId;
	private String companyId;
	private String deleted;
	private String deliveryPoint;
	private String description;
	private String endUser;
	private Date expireDate;
	private String facilityId;
	private String facilityName;
	private String facPartNo;
	private String inventoryGroup;
	private BigDecimal issueId;
	private BigDecimal itemId;
	private String itemType;
	private Date lastUpdated;
	private BigDecimal lastUpdatedBy;
	private String lineItem;
	private String lotStatus;
	private String mfgLot;
	private Date needDate;
	private String notes;
	private String packaging;
	private BigDecimal pdoc;
	private BigDecimal pickingGroupId;
	private String pickingState;
	private BigDecimal pickingUnitId;
	private BigDecimal picklistId;
	private String poNumber;
	private BigDecimal prNumber;
	private BigDecimal quantity;
	private BigDecimal receiptId;
	private BigDecimal requestor;
	private String requestorName;
	private String shipToLocationId;
	private String bin;
	private boolean showOptionalInvPick;
	
	public PickingUnitIssueBean() {
		
	}

	public String getAllocateByMfgLot() {
		return allocateByMfgLot;
	}

	public String getApplication() {
		return application;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public String getCatalogDesc() {
		return catalogDesc;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getDeleted() {
		return deleted;
	}

	public String getDeliveryPoint() {
		return deliveryPoint;
	}

	public String getDescription() {
		return description;
	}

	public String getEndUser() {
		return endUser;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public String getFacPartNo() {
		return facPartNo;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public BigDecimal getIssueId() {
		return issueId;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getItemType() {
		return itemType;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public BigDecimal getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public String getLineItem() {
		return lineItem;
	}

	public String getLotStatus() {
		return lotStatus;
	}

	public String getMfgLot() {
		return mfgLot;
	}

	public Date getNeedDate() {
		return needDate;
	}

	public String getNotes() {
		return notes;
	}

	public String getPackaging() {
		return packaging;
	}

	public BigDecimal getPdoc() {
		return pdoc;
	}

	public BigDecimal getPickingGroupId() {
		return pickingGroupId;
	}

	public String getPickingState() {
		return pickingState;
	}

	public BigDecimal getPickingUnitId() {
		return pickingUnitId;
	}

	public BigDecimal getPicklistId() {
		return picklistId;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public BigDecimal getRequestor() {
		return requestor;
	}

	public String getRequestorName() {
		return requestorName;
	}

	public String getShipToLocationId() {
		return shipToLocationId;
	}

	public void setAllocateByMfgLot(String allocateByMfgLot) {
		this.allocateByMfgLot = allocateByMfgLot;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setIssueId(BigDecimal issueId) {
		this.issueId = issueId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setLastUpdatedBy(BigDecimal lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}

	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}

	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setPdoc(BigDecimal pdoc) {
		this.pdoc = pdoc;
	}

	public void setPickingGroupId(BigDecimal pickingGroupId) {
		this.pickingGroupId = pickingGroupId;
	}

	public void setPickingState(String pickingState) {
		this.pickingState = pickingState;
	}

	public void setPickingUnitId(BigDecimal pickingUnitId) {
		this.pickingUnitId = pickingUnitId;
	}

	public void setPicklistId(BigDecimal picklistId) {
		this.picklistId = picklistId;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public void setRequestor(BigDecimal requestor) {
		this.requestor = requestor;
	}

	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}

	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}
	
	public boolean isShowOptionalInvPick() {
		return showOptionalInvPick;
	}
	
	public void setShowOptionalInvPick(boolean showOptionalInvPick) {
		this.showOptionalInvPick = showOptionalInvPick;
	}
}
