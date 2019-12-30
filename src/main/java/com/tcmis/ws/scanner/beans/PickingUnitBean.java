package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class PickingUnitBean extends BaseDataBean {

	private BigDecimal pickingUnitId;
	private BigDecimal pickingGroupId;
	private String pickingState;
	private BigDecimal picklistId;
	private BigDecimal prNumber;
	private String lineItem;
	private String catalogId;
	private String facPartNo;
	private String poNumber;
	private String deliveryPoint;
	private String endUser;
	private String facilityId;
	private String allocateByMfgLot;
	private String notes;
	private Date needDate;
	private String itemType;
	private BigDecimal itemId;
	private BigDecimal requestor;
	private String requestorName;
	private String shipToLocationId;
	private String application;
	private String inventoryGroup;
	private String bin;
	
	
	public PickingUnitBean() {
		
	}

	public BigDecimal getPickingUnitId() {
		return pickingUnitId;
	}

	public void setPickingUnitId(BigDecimal pickingUnitId) {
		this.pickingUnitId = pickingUnitId;
	}

	public BigDecimal getPickingGroupId() {
		return pickingGroupId;
	}

	public void setPickingGroupId(BigDecimal pickingGroupId) {
		this.pickingGroupId = pickingGroupId;
	}

	public String getPickingState() {
		return pickingState;
	}

	public void setPickingState(String pickingState) {
		this.pickingState = pickingState;
	}

	public BigDecimal getPicklistId() {
		return picklistId;
	}

	public void setPicklistId(BigDecimal picklistId) {
		this.picklistId = picklistId;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public String getLineItem() {
		return lineItem;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getFacPartNo() {
		return facPartNo;
	}

	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getDeliveryPoint() {
		return deliveryPoint;
	}

	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}

	public String getEndUser() {
		return endUser;
	}

	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getAllocateByMfgLot() {
		return allocateByMfgLot;
	}

	public void setAllocateByMfgLot(String allocateByMfgLot) {
		this.allocateByMfgLot = allocateByMfgLot;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getNeedDate() {
		return needDate;
	}

	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getRequestor() {
		return requestor;
	}

	public void setRequestor(BigDecimal requestor) {
		this.requestor = requestor;
	}

	public String getShipToLocationId() {
		return shipToLocationId;
	}

	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public String getRequestorName() {
		return requestorName;
	}

	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}
}
