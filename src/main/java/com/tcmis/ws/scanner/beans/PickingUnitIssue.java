package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

public class PickingUnitIssue extends BaseDataBean {

	private String		allocateByMfgLot;
	private String		application;
	private String		applicationDesc;
	private String		bin;
	private String		catalogDesc;
	private String		catalogId;
	private String		catPartComment;
	private String		companyId;
	private String		customerFacility;
	private BigDecimal	customerId;
	private String		customerPartNumber;
	private String		deleted;
	private String		deliveryPoint;
	private String		description;
	private BigDecimal	dryIceWeightLbs;
	private String		endUser;
	private Date		expireDate;
	private String		facilityId;
	private String		facilityName;
	private String		facLocAppPartComment;
	private String		facPartNo;
	private String		holdNote;
	private String		internalNote;
	private String		inventoryGroup;
	private BigDecimal	issueId;
	private BigDecimal	itemId;
	private String		itemType;
	private boolean		labelOverride;
	private Date		lastUpdated;
	private BigDecimal	lastUpdatedBy;
	private String		lineItem;
	private String		lotStatus;
	private String		mfgLot;
	private Date		needDate;
	private String		notes;
	private String		orderShiptoNote;
	private boolean		packageOverride;
	private String		packaging;
	private boolean		packerOverride;
	private boolean		packerQtyOverride;
	private BigDecimal	pdoc;
	private BigDecimal	pickingGroupId;
	private String		pickingState;
	private BigDecimal	pickingUnitId;
	private BigDecimal	picklistId;
	private String		poNumber;
	private BigDecimal	prNumber;
	private boolean		qtyOverride;
	private BigDecimal	quantity;
	private String		rddComment;
	private BigDecimal	receiptId;
	private BigDecimal	requestor;
	private String		requestorName;
	private boolean		ridOverride;
	private String		rliNotes;
	private boolean		scrap;
	private String		dotOverride;
	private String		shipTo;
	private String		shipToLocationId;
	private boolean		showOptionalInvPick;
	private String		specialInstructions;
	private String		transferComment;
	private boolean		useOverpackBox;

	public PickingUnitIssue() {

	}

	private void addToNotes(String text) {
		if (StringUtils.isNotBlank(text)) {
			synchronized (this) {
				if (StringUtils.isBlank(this.notes)) {
					this.notes = text;
				}
				else {
					notes += "; " + text;
				}
			}
		}
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

	public String getBin() {
		return bin;
	}

	public String getCatalogDesc() {
		return catalogDesc;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCatPartComment() {
		return this.catPartComment;
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

	public String getDeleted() {
		return deleted;
	}

	public String getDeliveryPoint() {
		return deliveryPoint;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getDryIceWeightLbs() {
		return this.dryIceWeightLbs;
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

	public String getFacLocAppPartComment() {
		return this.facLocAppPartComment;
	}

	public String getFacPartNo() {
		return facPartNo;
	}

	public String getHoldNote() {
		return this.holdNote;
	}

	public String getInternalNote() {
		return this.internalNote;
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

	public String getOrderShiptoNote() {
		return this.orderShiptoNote;
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

	public String getRddComment() {
		return this.rddComment;
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

	public String getRliNotes() {
		return this.rliNotes;
	}

	public String getShipTo() {
		return this.shipTo;
	}

	public String getShipToLocationId() {
		return shipToLocationId;
	}

	public String getSpecialInstructions() {
		return this.specialInstructions;
	}

	public String getTransferComment() {
		return this.transferComment;
	}

	public boolean isLabelOverride() {
		return this.labelOverride;
	}

	public boolean isPackageOverride() {
		return this.packageOverride;
	}

	public boolean isPackerOverride() {
		return this.packerOverride;
	}

	public boolean isPackerQtyOverride() {
		return packerQtyOverride;
	}

	public boolean isQtyOverride() {
		return this.qtyOverride;
	}

	public boolean isRidOverride() {
		return ridOverride;
	}

	public boolean isScrap() {
		return this.scrap;
	}

	public String getDotOverride() {
		return this.dotOverride;
	}

	public boolean isShowOptionalInvPick() {
		return showOptionalInvPick;
	}

	public boolean isUseOverpackBox() {
		return this.useOverpackBox;
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

	public void setBin(String bin) {
		this.bin = bin;
	}

	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatPartComment(String catPartComment) {
		this.catPartComment = catPartComment;
		addToNotes(catPartComment);
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

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDryIceWeightLbs(BigDecimal dryIceWeightLbs) {
		this.dryIceWeightLbs = dryIceWeightLbs;
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

	public void setFacLocAppPartComment(String facLocAppPartComment) {
		this.facLocAppPartComment = facLocAppPartComment;
		addToNotes(facLocAppPartComment);
	}

	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}

	public void setHoldNote(String holdNote) {
		this.holdNote = holdNote;
	}

	public void setInternalNote(String internalNote) {
		this.internalNote = internalNote;
		addToNotes(internalNote);
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

	public void setLabelOverride(boolean labelOverride) {
		this.labelOverride = labelOverride;
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

	public void setOrderShiptoNote(String orderShiptoNote) {
		this.orderShiptoNote = orderShiptoNote;
		addToNotes(orderShiptoNote);
	}

	public void setPackageOverride(boolean packageOverride) {
		this.packageOverride = packageOverride;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setPackerOverride(boolean packerOverride) {
		this.packerOverride = packerOverride;
	}

	public void setPackerQtyOverride(boolean packerQtyOverride) {
		this.packerQtyOverride = packerQtyOverride;
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

	public void setQtyOverride(boolean qtyOverride) {
		this.qtyOverride = qtyOverride;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void setRddComment(String rddComment) {
		this.rddComment = rddComment;
		addToNotes(rddComment);
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

	public void setRidOverride(boolean ridOverride) {
		this.ridOverride = ridOverride;
	}

	public void setRliNotes(String rliNotes) {
		this.rliNotes = rliNotes;
		addToNotes(rliNotes);
	}

	public void setScrap(boolean scrap) {
		this.scrap = scrap;
	}

	public void setDotOverride(String dotOverride) {
		this.dotOverride = dotOverride;
	}

	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}

	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}

	public void setShowOptionalInvPick(boolean showOptionalInvPick) {
		this.showOptionalInvPick = showOptionalInvPick;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
		addToNotes(specialInstructions);
	}

	public void setTransferComment(String transferComment) {
		this.transferComment = transferComment;
		addToNotes(transferComment);
	}

	public void setUseOverpackBox(boolean useOverpackBox) {
		this.useOverpackBox = useOverpackBox;
	}

}
