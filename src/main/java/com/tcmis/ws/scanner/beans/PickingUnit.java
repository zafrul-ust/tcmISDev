package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

public class PickingUnit extends BaseDataBean {

	private String				allocateByMfgLot;
	private String				application;
	private String				bin;
	private BigDecimal			boxCount;
	private String				carrierName;
	private String				catalogId;
	private String				companyId;
	private String				customerFacility;
	private BigDecimal			customerId;
	private String				customerPartNumber;
	private boolean				deleted;
	private String				deliveryPoint;
	private String				dotOverride;
	private BigDecimal			dryIceWeightLbs;
	private String				endUser;
	private String				facilityId;
	private String				facPartNo;
	private String				holdNote;
	private String				hub;
	private String				id;
	private String				inventoryGroup;
	private BigDecimal			itemId;
	private String				itemType;
	private Date				lastUpdated;
	private String				lineItem;
	private Date				needDate;
	private String				notes;
	private String				overrideNote;
	private PickingUnitOverride	overrides	= new PickingUnitOverride();
	private String				packageType;
	private Date				packDate;
	private String				pdoc;
	private BigDecimal			personnelId;
	private BigDecimal			pickingGroupId;
	private String				pickingState;
	private BigDecimal			pickingUnitId;
	private BigDecimal			picklistId;
	private BigDecimal			prNumber;
	private Date				qcDate;
	private BigDecimal			requestor;
	private String				requestorName;
	private boolean				scrap;
	private Date				shipDate;
	private String				shipTo;
	private String				shipToLocationId;
	private String				tabletShipmentId;
	private BigDecimal			totalWeight;
	private String				trackingNumbers;
	private boolean				useOverpackBox;

	public PickingUnit() {

	}

	public String getAllocateByMfgLot() {
		return allocateByMfgLot;
	}

	public String getApplication() {
		return application;
	}

	public String getBin() {
		return bin;
	}

	public BigDecimal getBoxCount() {
		return this.boxCount;
	}

	public String getCarrierName() {
		return this.carrierName;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getCustomerFacility() {
		return this.customerFacility;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public String getCustomerPartNumber() {
		return this.customerPartNumber;
	}

	public String getDeliveryPoint() {
		return deliveryPoint;
	}

	public String getDotOverride() {
		return this.dotOverride;
	}

	public BigDecimal getDryIceWeightLbs() {
		return this.dryIceWeightLbs;
	}

	public String getEndUser() {
		return endUser;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getFacPartNo() {
		return facPartNo;
	}

	public String getHoldNote() {
		return this.holdNote;
	}

	public String getHub() {
		return this.hub;
	}

	public String getId() {
		return this.id;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getItemType() {
		return itemType;
	}

	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public String getLineItem() {
		return lineItem;
	}

	public Date getNeedDate() {
		return needDate;
	}

	public String getNotes() {
		return notes;
	}

	public String getOverrideNote() {
		return this.overrideNote;
	}

	public PickingUnitOverride getOverrides() {
		return this.overrides;
	}

	public String getPackageType() {
		return this.packageType;
	}

	public Date getPackDate() {
		return this.packDate;
	}

	public String getPdoc() {
		return this.pdoc;
	}

	public BigDecimal getPersonnelId() {
		return this.personnelId;
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

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public Date getQcDate() {
		return this.qcDate;
	}

	public BigDecimal getRequestor() {
		return requestor;
	}

	public String getRequestorName() {
		return requestorName;
	}

	public Date getShipDate() {
		return this.shipDate;
	}

	public String getShipTo() {
		return this.shipTo;
	}

	public String getShipToLocationId() {
		return shipToLocationId;
	}

	public String getTabletShipmentId() {
		return this.tabletShipmentId;
	}

	public BigDecimal getTotalWeight() {
		return this.totalWeight;
	}

	public String getTrackingNumbers() {
		return this.trackingNumbers;
	}

	public boolean hasPersonnelId() {
		return personnelId != null;
	}

	public boolean hasPickingUnitId() {
		return pickingUnitId != null;
	}

	public boolean hasTabletShipmentId() {
		return StringUtils.isNotBlank(tabletShipmentId);
	}

	public boolean hasTrackingNumbers() {
		return StringUtils.isNotBlank(trackingNumbers);
	}

	public boolean isDeleted() {
		return this.deleted;
	}

	public boolean isScrap() {
		return this.scrap;
	}

	public boolean isTransfer() {
		return "0".equals(lineItem);
	}

	public boolean isUseOverpackBox() {
		return this.useOverpackBox;
	}

	public boolean isValidForUpdatingShipment() {
		return hasTabletShipmentId() && hasPickingUnitId();
	}

	public void setAllocateByMfgLot(String allocateByMfgLot) {
		this.allocateByMfgLot = allocateByMfgLot;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public void setBoxCount(BigDecimal boxCount) {
		this.boxCount = boxCount;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCustomerFacility(String customerFacility) {
		this.customerFacility = customerFacility;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public void setCustomerPartNumber(String customer_part_number) {
		this.customerPartNumber = customer_part_number;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}

	public void setDotOverride(String dotOverride) {
		this.dotOverride = dotOverride;
	}

	public void setDryIceWeightLbs(BigDecimal dryIceWeightLbs) {
		this.dryIceWeightLbs = dryIceWeightLbs;
	}

	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}

	public void setHoldNote(String holdNote) {
		this.holdNote = holdNote;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public void setLabelOverride(boolean labelOverride) {
		overrides.setLabelOverride(labelOverride);
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setOverrideNote(String overrideNote) {
		this.overrideNote = overrideNote;
	}

	public void setOverrides(PickingUnitOverride overrides) {
		this.overrides = overrides;
	}

	public void setPackageOverride(boolean packageOverride) {
		overrides.setPackageOverride(packageOverride);
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public void setPackDate(Date packDate) {
		this.packDate = packDate;
	}

	public void setPackerOverride(boolean packerOverride) {
		overrides.setPackerOverride(packerOverride);
	}

	public void setPackerQtyOverride(boolean packerQtyOverride) {
		overrides.setPackerQtyOverride(packerQtyOverride);
	}

	public void setPdoc(String pdoc) {
		this.pdoc = pdoc;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
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

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public void setQcDate(Date qcDate) {
		this.qcDate = qcDate;
	}

	public void setQtyOverride(boolean qtyOverride) {
		overrides.setQtyOverride(qtyOverride);
	}

	public void setRequestor(BigDecimal requestor) {
		this.requestor = requestor;
	}

	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}

	public void setRidOverride(boolean ridOverride) {
		overrides.setRidOverride(ridOverride);
	}

	public void setScrap(boolean scrap) {
		this.scrap = scrap;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	public void setShippingOverride(String shippingOverride) {
		overrides.setShippingOverride(shippingOverride);
	}

	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}

	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}

	public void setTabletShipmentId(String tabletShipmentId) {
		this.tabletShipmentId = tabletShipmentId;
	}

	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}

	public void setTrackingNumbers(String trackingNumbers) {
		this.trackingNumbers = trackingNumbers;
	}

	public void setUseOverpackBox(boolean useOverpackBox) {
		this.useOverpackBox = useOverpackBox;
	}

}
