package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InboundShipmentBean <br>
 * 
 * @version: 1.0, Mar 5, 2013 <br>
 *****************************************************************************/

public class InboundShipmentTrackingViewBean extends BaseDataBean {

	private Date		arrivalScanDate;
	private String		arrivalScanUser;
	private String		carrierParentShortName;
	private Date		dateOfReceipt;
	private Date		estimatedDeliveryDate;
	private String		hub;
	private String		hubName;
	private BigDecimal	inboundShipmentDetailId;
	private BigDecimal	inboundShipmentId;
	private String		inventoryGroup;
	private String		inventoryGroupName;
	private String		operatingEntityName;
	private String		receivingPriority;
	private String		receivingStatus;
	private String		shipmentStatus;
	private String		trackingNotes;
	private String		trackingNumber;
	private BigDecimal	transactionNumber;
	private String		transactionType;

	// constructor
	public InboundShipmentTrackingViewBean() {
	}

	public Date getArrivalScanDate() {
		return arrivalScanDate;
	}

	public String getArrivalScanUser() {
		return arrivalScanUser;
	}

	public String getCarrierParentShortName() {
		return carrierParentShortName;
	}

	public Date getDateOfReceipt() {
		return dateOfReceipt;
	}

	public Date getEstimatedDeliveryDate() {
		return estimatedDeliveryDate;
	}

	public String getHub() {
		return hub;
	}

	public String getHubName() {
		return hubName;
	}

	public BigDecimal getInboundShipmentDetailId() {
		return inboundShipmentDetailId;
	}

	public BigDecimal getInboundShipmentId() {
		return inboundShipmentId;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public String getOperatingEntityName() {
		return operatingEntityName;
	}

	public String getReceivingPriority() {
		return receivingPriority;
	}

	public String getReceivingStatus() {
		return receivingStatus;
	}

	public String getShipmentStatus() {
		return shipmentStatus;
	}

	public String getTrackingNotes() {
		return trackingNotes;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public BigDecimal getTransactionNumber() {
		return transactionNumber;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setArrivalScanDate(Date arrivalScanDate) {
		this.arrivalScanDate = arrivalScanDate;
	}

	public void setArrivalScanUser(String arrivalScanUser) {
		this.arrivalScanUser = arrivalScanUser;
	}

	public void setCarrierParentShortName(String carrierParentShortName) {
		this.carrierParentShortName = carrierParentShortName;
	}

	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}

	public void setEstimatedDeliveryDate(Date estimatedDeliveryDate) {
		this.estimatedDeliveryDate = estimatedDeliveryDate;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	public void setInboundShipmentDetailId(BigDecimal inboundShipmentDetailId) {
		this.inboundShipmentDetailId = inboundShipmentDetailId;
	}

	public void setInboundShipmentId(BigDecimal inboundShipmentId) {
		this.inboundShipmentId = inboundShipmentId;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}

	public void setReceivingPriority(String receivingPriority) {
		this.receivingPriority = receivingPriority;
	}

	public void setReceivingStatus(String receivingStatus) {
		this.receivingStatus = receivingStatus;
	}

	public void setShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}

	public void setTrackingNotes(String trackingNotes) {
		this.trackingNotes = trackingNotes;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public void setTransactionNumber(BigDecimal transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

}