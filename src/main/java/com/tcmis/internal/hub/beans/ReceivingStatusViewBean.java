package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

import com.tcmis.common.admin.beans.UserGroupMemberIgBean;
import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: ReceivingStatusViewBean <br>
 * 
 * @version: 1.0, May 30, 2013 <br>
 *****************************************************************************/

public class ReceivingStatusViewBean extends BaseDataBean {

	private static BigDecimal					UNASSIGNED			= new BigDecimal("-1");

	private boolean								allocated			= false;
	private String								arrivalScanUserName;
	private BigDecimal							assignedTo;
	private String								assignedToName;
	private Collection<UserGroupMemberIgBean>	assignees;
	private String								bin					= "UNK";
	private String								carrier;
	private BigDecimal							componentId;
	public String								critical;
	private BigDecimal							customerRmaId;
	private String								demandType;
	private String								displayPkgStyle;
	private BigDecimal							docNum;
	private boolean								docsExists			= false;
	private String								dot;
	public String								excess;
	private String								flashPoint;
	private String								hazardClass;
	private String								hub;
	private String								hubName;
	private String								imageUrl			= "item_image/image_not_found.jpg";
	private BigDecimal							inboundShipmentDetailId;
	private BigDecimal							inboundShipmentId;
	private Date								initialScanDate;
	private String								internalReceiptNotes;
	private String								inventoryGroup;
	private String								inventoryGroupName;
	private String								itemDesc;
	private BigDecimal							itemId;
	public String								itemType;
	private String								lastbin				= "N/A";
	private String								lotStatus;
	private String								manageKitsAsSingleUnit;
	private String								materialDesc;
	private BigDecimal							numberOfKits;
	private String								opsEntityId;
	private String								opsEntityName;
	private String								packaging;
	private String								ph;
	private Date								qcDate;
	private boolean								qualityControlItem;
	private BigDecimal							quantityReceived;
	private BigDecimal							radianPo;
	private BigDecimal							receiptId;
	private String								receivingNotes;
	private String								receivingStatus;
	private Date								receivingStatusDate;
	private BigDecimal							salesVelocity;
	private boolean								shipmentDocsOnline	= false;
	private String								storageTemp;
	private String								supplier;
	private String								supplierName;
	private String								trackingNotes;

	private String								trackingNumber;
	private BigDecimal							transferRequestId;

	// constructor
	public ReceivingStatusViewBean() {
	}

	public String getArrivalScanUserName() {
		return arrivalScanUserName;
	}

	public BigDecimal getAssignedTo() {
		return assignedTo != null ? assignedTo : UNASSIGNED;
	}

	public String getAssignedToName() {
		return assignedToName;
	}

	public Collection<UserGroupMemberIgBean> getAssignees() {
		return assignees;
	}

	public String getBin() {
		return bin;
	}

	public String getCarrier() {
		return carrier;
	}

	public BigDecimal getComponentId() {
		return componentId;
	}

	public String getCritical() {
		return critical;
	}

	public BigDecimal getCustomerRmaId() {
		return customerRmaId;
	}

	public String getDemandType() {
		return demandType;
	}

	public String getDisplayPkgStyle() {
		return displayPkgStyle;
	}

	public BigDecimal getDocNum() {
		return docNum;
	}

	public String getDot() {
		return dot;
	}

	public String getExcess() {
		return excess;
	}

	public String getFlashPoint() {
		return flashPoint;
	}

	public String getHazardClass() {
		return hazardClass;
	}

	public String getHub() {
		return hub;
	}

	public String getHubName() {
		return hubName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public BigDecimal getInboundShipmentDetailId() {
		return inboundShipmentDetailId;
	}

	// getters
	public BigDecimal getInboundShipmentId() {
		return inboundShipmentId;
	}

	public Date getInitialScanDate() {
		return initialScanDate;
	}

	public String getInternalReceiptNotes() {
		return internalReceiptNotes;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getItemType() {
		return itemType;
	}

	public String getLastbin() {
		return lastbin;
	}

	public String getLotStatus() {
		return lotStatus;
	}

	public String getManageKitsAsSingleUnit() {
		return manageKitsAsSingleUnit;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public BigDecimal getNumberOfKits() {
		return numberOfKits;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public String getOpsEntityName() {
		return opsEntityName;
	}

	public String getPackaging() {
		return packaging;
	}

	public String getPh() {
		return ph;
	}

	public Date getQcDate() {
		return qcDate;
	}

	public BigDecimal getQuantityReceived() {
		return quantityReceived;
	}

	public BigDecimal getRadianPo() {
		return radianPo;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public String getReceivingNotes() {
		return receivingNotes;
	}

	public String getReceivingStatus() {
		return receivingStatus;
	}

	public Date getReceivingStatusDate() {
		return receivingStatusDate;
	}

	public BigDecimal getSalesVelocity() {
		return salesVelocity;
	}

	public String getStorageTemp() {
		return storageTemp;
	}

	public String getSupplier() {
		return supplier;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public String getTrackingNotes() {
		return trackingNotes;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public String getTransactionNumber() {
		if (transferRequestId != null && !BigDecimal.ZERO.equals(transferRequestId)) {
			return "IT " + transferRequestId;
		}
		if (docNum != null) {
			return "CITR " + docNum;
		}
		if (customerRmaId != null) {
			return "RMA " + customerRmaId;
		}
		else {
			return "PO " + radianPo;
		}
	}

	public BigDecimal getTransferRequestId() {
		return transferRequestId;
	}

	public boolean isAllocated() {
		return this.allocated;
	}

	public boolean isAssingedToUnassigned() {
		return assignedTo == null || UNASSIGNED.compareTo(assignedTo) == 0;
	}

	public boolean isDemandTypeMinMax() {
		return "MinMax".equals(demandType);
	}

	public boolean isDemandTypeOOR() {
		return "OOR".equals(demandType);
	}

	public boolean isDocsExists() {
		return docsExists;
	}

	public boolean isLotStatusNotReadyForPick() {
		return !"Available".equals(lotStatus) && !"Cert/Not Pickable".equals(lotStatus);
	}

	public boolean isQualityControlItem() {
		return qualityControlItem;
	}

	public boolean isReceivingQCDone() {
		return (!StringHandler.isBlankString(receivingStatus) && receivingStatus.equalsIgnoreCase("Binned")) || (StringHandler.isBlankString(receivingStatus) && qcDate == null)
				|| (StringHandler.isBlankString(receivingStatus) && qcDate != null);
	}

	public boolean isShipmentDocsOnline() {
		return shipmentDocsOnline;
	}

	public boolean isStatusAllocatedToOpenPO() {
		return allocated;
	}

	public boolean isStatusCritical() {
		return !StringHandler.isBlankString(critical) && critical.toUpperCase().startsWith("Y");
	}

	public boolean isStatusExcess() {
		return !StringHandler.isBlankString(excess) && excess.toUpperCase().startsWith("Y");
	}

	public boolean isStatusML() {
		return "ML".equals(itemType);
	}

	public boolean isStatusSuperCritical() {
		return !StringHandler.isBlankString(critical) && critical.toUpperCase().startsWith("S");
	}

	public void setAllocated(boolean allocated) {
		this.allocated = allocated;
	}

	public void setArrivalScanUserName(String arrivalScanUserName) {
		this.arrivalScanUserName = arrivalScanUserName;
	}

	public void setAssignedTo(BigDecimal assignedTo) {
		this.assignedTo = assignedTo;
	}

	public void setAssignedToName(String assignedToName) {
		this.assignedToName = assignedToName;
	}

	public void setAssignees(Collection<UserGroupMemberIgBean> assignees) {
		if (this.assignees == null) {
			this.assignees = new Vector<UserGroupMemberIgBean>();
		}
		for (UserGroupMemberIgBean assignee : assignees) {
			if (assignee.getInventoryGroup().equals(inventoryGroup)) {
				this.assignees.add(assignee);
			}
		}
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public void setComponentId(BigDecimal partId) {
		this.componentId = partId;
	}

	public void setCritical(String critical) {
		this.critical = critical;
	}

	public void setCustomerRmaId(BigDecimal customerRmaId) {
		this.customerRmaId = customerRmaId;
	}

	public void setDemandType(String demandType) {
		this.demandType = demandType;
	}

	public void setDisplayPkgStyle(String displayPkgStyle) {
		this.displayPkgStyle = displayPkgStyle;
	}

	public void setDocNum(BigDecimal docNum) {
		this.docNum = docNum;
	}

	public void setDocsExists(boolean docsExist) {
		this.docsExists = docsExist;
	}

	public void setDot(String dot) {
		this.dot = dot;
	}

	public void setExcess(String excess) {
		this.excess = excess;
	}

	public void setFlashPoint(String flashPoint) {
		this.flashPoint = flashPoint;
	}

	public void setHazardClass(String hazardClass) {
		this.hazardClass = hazardClass;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	public void setImageUrl(String imageUrl) {
		if (!StringHandler.isBlankString(imageUrl)) {
			this.imageUrl = imageUrl;
		}
	}

	public void setInboundShipmentDetailId(BigDecimal inboundShipmentDetailId) {
		this.inboundShipmentDetailId = inboundShipmentDetailId;
	}

	// setters
	public void setInboundShipmentId(BigDecimal inboundShipmentId) {
		this.inboundShipmentId = inboundShipmentId;
	}

	public void setInitialScanDate(Date initialScanDate) {
		this.initialScanDate = initialScanDate;
	}

	public void setInternalReceiptNotes(String internalReceiptNotes) {
		this.internalReceiptNotes = internalReceiptNotes;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public void setLastbin(String lastbin) {
		this.lastbin = lastbin;
	}

	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}

	public void setManageKitsAsSingleUnit(String manageKitsAsSingleUnit) {
		this.manageKitsAsSingleUnit = manageKitsAsSingleUnit;
	}

	public void setMaterialDesc(String materialDescription) {
		this.materialDesc = materialDescription;
	}

	public void setNumberOfKits(BigDecimal numberOfKits) {
		this.numberOfKits = numberOfKits;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public void setOpsEntityName(String opsEntityName) {
		this.opsEntityName = opsEntityName;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public void setQcDate(Date qcDate) {
		this.qcDate = qcDate;
	}

	public void setQualityControlItem(boolean qualityControlItem) {
		this.qualityControlItem = qualityControlItem;
	}

	public void setQuantityReceived(BigDecimal quantityReceived) {
		this.quantityReceived = quantityReceived;
	}

	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public void setReceivingNotes(String receivingNotes) {
		this.receivingNotes = receivingNotes;
	}

	public void setReceivingStatus(String receivingStatus) {
		this.receivingStatus = receivingStatus;
	}

	public void setReceivingStatusDate(Date receivingStatusDate) {
		this.receivingStatusDate = receivingStatusDate;
	}

	public void setSalesVelocity(BigDecimal salesVelocity) {
		this.salesVelocity = salesVelocity;
	}

	public void setShipmentDocsOnline(boolean shipmentDocsOnline) {
		this.shipmentDocsOnline = shipmentDocsOnline;
	}

	public void setStorageTemp(String storageTemp) {
		this.storageTemp = storageTemp;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public void setTrackingNotes(String trackingNotes) {
		this.trackingNotes = trackingNotes;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public void setTransferRequestId(BigDecimal transferRequestId) {
		this.transferRequestId = transferRequestId;
	}
}