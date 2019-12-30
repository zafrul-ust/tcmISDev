package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

@SuppressWarnings("serial")
public class PickingStatusViewBean extends BaseDataBean {

	private String		allocateByMfgLot;
	private String		application;
	private String		applicationDesc;
	private String		catalogDesc;
	private String		catalogId;
	private String		deliveryPoint;
	private String		endUser;
	private String		facilityId;
	private String		facilityName;
	private String		facPartNo;
	private String		holdNote;
	private BigDecimal	itemId;
	private String		itemType;
	private boolean		labelOverride;
	private String		lineItem;
	private Date		needDate;
	private String		notes;
	private boolean		ok;
	private boolean		overrideOption;
	private boolean		packerOverride;
	private BigDecimal	pdoc;
	private String		pdocs;
	private Date		pickCreationDate;
	private BigDecimal	pickingGroupId;
	private String		pickingGroupName;
	private String		pickingState;
	private String		pickingStateDesc;
	private String		pickingStatusPgAssignable;
	private BigDecimal	pickingUnitId;
	private BigDecimal	picklistId;
	private String		pickNotes;
	private String		poNumber;
	private BigDecimal	prNumber;
	private String		qcNotes;
	private boolean		qtyOverride;
	private BigDecimal	quantity;
	private BigDecimal	receiptId;
	private String      rejectionComment;
	private BigDecimal	requestor;
	private String		requestorName;
	private String		dotOverride;
	private String		shipToLocationId;
	private boolean		showOptionalInvPick;
	private String		pickerName;
	private String		packerName;
	private Date		dateDelivered;
	private Date		pickDate;
	private Date		packDate;
	private Date		qcDate;
	private String		inventoryGroup;
	private BigDecimal	issueId;
	private String	tabletShipmentId;


	public Date getDateDelivered() {
		return dateDelivered;
	}

	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}

	public String getPickerName() {
		return pickerName;
	}

	public void setPickerName(String pickerName) {
		this.pickerName = pickerName;
	}

	public String getPackerName() {
		return packerName;
	}

	public void setPackerName(String packerName) {
		this.packerName = packerName;
	}

	public PickingStatusViewBean() {
	}

	public void addPdoc(BigDecimal pdoc) {
		if (pdoc != null) {
			if (this.pdocs == null) {
				this.pdocs = "" + pdoc;
			}
			else {
				this.pdocs += ", " + pdoc;
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

	public String getCatalogDesc() {
		return catalogDesc;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getDeliveryPoint() {
		return deliveryPoint;
	}

	public String getEndUser() {
		return endUser;
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

	public String getHoldComments() {
		String comments = "";
		if (StringUtils.isNotBlank(holdNote)) {
			comments += holdNote + " ";
		}
		if (StringUtils.isNotBlank(pickNotes)) {
			comments += pickNotes + " ";
		}
		if (StringUtils.isNotBlank(qcNotes)) {
			comments += qcNotes + " ";
		}
		return comments;
	}

	public String getHoldNote() {
		return holdNote;
	}
	
	public BigDecimal getItemId() {
		return itemId;
	}

	public String getItemType() {
		return itemType;
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

	public BigDecimal getPdoc() {
		return this.pdoc;
	}

	public String getPdocs() {
		return this.pdocs;
	}

	public Date getPickCreationDate() {
		return this.pickCreationDate;
	}

	public BigDecimal getPickingGroupId() {
		return pickingGroupId;
	}

	public String getPickingGroupName() {
		return pickingGroupName;
	}

	public String getPickingState() {
		return pickingState;
	}

	public String getPickingStateDesc() {
		return pickingStateDesc;
	}

	public String getPickingStatusPgAssignable() {
		return pickingStatusPgAssignable;
	}

	public BigDecimal getPickingUnitId() {
		return pickingUnitId;
	}

	public BigDecimal getPicklistId() {
		return picklistId;
	}

	public String getPickNotes() {
		return pickNotes;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public String getQcNotes() {
		return qcNotes;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public String getRejectionComment() {
		return rejectionComment;
	}

	public BigDecimal getRequestor() {
		return requestor;
	}

	public String getRequestorName() {
		return requestorName;
	}

	public String getDotOverride() {
		return this.dotOverride;
	}

	public String getShipToLocationId() {
		return shipToLocationId;
	}

	public boolean isLabelOverride() {
		return labelOverride;
	}

	public boolean isOk() {
		return ok;
	}

	public boolean isOverrideOption() {
		return overrideOption;
	}

	public boolean isPackerOverride() {
		return packerOverride;
	}

	public boolean isPickingStateRejected() {
		return "REJECTED".equals(pickingState);
	}

	public boolean isQtyOverride() {
		return qtyOverride;
	}

	public boolean isShowOptionalInvPick() {
		return showOptionalInvPick;
	}

	public boolean matches(PickingStatusViewBean other) {
		return this.pickingUnitId.equals(other.getPickingUnitId());
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

	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}

	public void setEndUser(String endUser) {
		this.endUser = endUser;
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

	public void setHoldNote(String holdNote) {
		this.holdNote = holdNote;
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

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public void setOverrideOption(boolean overrideOption) {
		this.overrideOption = overrideOption;
	}

	public void setPackerOverride(boolean packerOverride) {
		this.packerOverride = packerOverride;
	}

	public void setPdoc(BigDecimal pdoc) {
		this.pdoc = pdoc;
		addPdoc(pdoc);
	}

	public void setPdocs(String pdocs) {
		this.pdocs = pdocs;
	}

	public void setPickCreationDate(Date pickCreationDate) {
		this.pickCreationDate = pickCreationDate;
	}

	public void setPickingGroupId(BigDecimal pickingGroupId) {
		this.pickingGroupId = pickingGroupId;
	}

	public void setPickingGroupName(String pickingGroupName) {
		this.pickingGroupName = pickingGroupName;
	}

	public void setPickingState(String pickingState) {
		this.pickingState = pickingState;
	}

	public void setPickingStateDesc(String pickingStateDesc) {
		this.pickingStateDesc = pickingStateDesc;
	}

	public void setPickingStatusPgAssignable(String pickingStatusPgAssignable) {
		this.pickingStatusPgAssignable = pickingStatusPgAssignable;
	}

	public void setPickingUnitId(BigDecimal pickingUnitId) {
		this.pickingUnitId = pickingUnitId;
	}

	public void setPicklistId(BigDecimal picklistId) {
		this.picklistId = picklistId;
	}

	public void setPickNotes(String pickNotes) {
		this.pickNotes = pickNotes;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public void setQcNotes(String qcNotes) {
		this.qcNotes = qcNotes;
	}

	public void setQtyOverride(boolean qtyOverride) {
		this.qtyOverride = qtyOverride;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public void setRejectionComment(String rejectionComment) {
		this.rejectionComment = rejectionComment;
	}

	public void setRequestor(BigDecimal requestor) {
		this.requestor = requestor;
	}

	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}

	public void setDotOverride(String dotOverride) {
		this.dotOverride = dotOverride;
	}

	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}

	public void setShowOptionalInvPick(boolean showOptionalInvPick) {
		this.showOptionalInvPick = showOptionalInvPick;
	}

	public Date getPickDate() {
		return pickDate;
	}

	public void setPickDate(Date pickDate) {
		this.pickDate = pickDate;
	}

	public Date getPackDate() {
		return packDate;
	}

	public void setPackDate(Date packDate) {
		this.packDate = packDate;
	}

	public Date getQcDate() {
		return qcDate;
	}

	public void setQcDate(Date qcDate) {
		this.qcDate = qcDate;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public BigDecimal getIssueId() {
		return issueId;
	}

	public void setIssueId(BigDecimal issueId) {
		this.issueId = issueId;
	}

	public String getTabletShipmentId() {
		return tabletShipmentId;
	}

	public void setTabletShipmentId(String tabletShipmentId) {
		this.tabletShipmentId = tabletShipmentId;
	}
}
