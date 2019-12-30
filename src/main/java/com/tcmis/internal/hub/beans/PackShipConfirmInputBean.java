package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class PackShipConfirmInputBean {
	private String boxId;
	private String boxTrackingNumber;
	private String carrierCode;
	private String consolidationNumber;
	private Date deliveredDate;
	private String differentPalletId;
	private String dot;
	private String facilityId;
	private BigDecimal fedexParcelHazardousDocId;
	private String hazardous;
	private String inventoryGroup;
	private String leadTrackingNumber;
	private String lineItem;
	private String newPalletId;
	private String oldLeadTrackingNumber;
	protected String opsEntityId;
	private String originInspectionRequired;
	private BigDecimal packingGroupId;
	private String palletId;
	private String prNumber;
	private String selected;
	private String sourceHub;
	private String transportationMode;
	private BigDecimal userId;

	// constructor
	public PackShipConfirmInputBean() {
	}

	public String getBoxId() {
		return boxId;
	}

	public String getBoxTrackingNumber() {
		return boxTrackingNumber;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public String getConsolidationNumber() {
		return consolidationNumber;
	}

	public Date getDeliveredDate() {
		return deliveredDate;
	}

	public String getDifferentPalletId() {
		return differentPalletId;
	}

	public String getDot() {
		return dot;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public BigDecimal getFedexParcelHazardousDocId() {
		return fedexParcelHazardousDocId;
	}

	public String getHazardous() {
		return hazardous;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getLeadTrackingNumber() {
		return leadTrackingNumber;
	}

	public String getLineItem() {
		return lineItem;
	}

	public String getNewPalletId() {
		return newPalletId;
	}

	public String getOldLeadTrackingNumber() {
		return oldLeadTrackingNumber;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public String getOriginInspectionRequired() {
		return originInspectionRequired;
	}

	public BigDecimal getPackingGroupId() {
		return packingGroupId;
	}

	public String getPalletId() {
		return palletId;
	}

	public String getPrNumber() {
		return prNumber;
	}

	public String getSelected() {
		return selected;
	}

	// getters
	public String getSourceHub() {
		return sourceHub;
	}

	public String getTransportationMode() {
		return transportationMode;
	}

	public BigDecimal getUserId() {
		return userId;
	}

	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}

	public void setBoxTrackingNumber(String boxTrackingNumber) {
		this.boxTrackingNumber = boxTrackingNumber;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public void setConsolidationNumber(String consolidationNumber) {
		this.consolidationNumber = consolidationNumber;
	}

	public void setDeliveredDate(Date deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	public void setDifferentPalletId(String differentPalletId) {
		this.differentPalletId = differentPalletId;
	}

	public void setDot(String dot) {
		this.dot = dot;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setFedexParcelHazardousDocId(BigDecimal fedexParcelHazardousDocId) {
		this.fedexParcelHazardousDocId = fedexParcelHazardousDocId;
	}

	public void setHazardous(String hazardous) {
		this.hazardous = hazardous;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setLeadTrackingNumber(String leadTrackingNumber) {
		this.leadTrackingNumber = leadTrackingNumber;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public void setNewPalletId(String newPalletId) {
		this.newPalletId = newPalletId;
	}

	public void setOldLeadTrackingNumber(String oldLeadTrackingNumber) {
		this.oldLeadTrackingNumber = oldLeadTrackingNumber;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public void setOriginInspectionRequired(String originInspectionRequired) {
		this.originInspectionRequired = originInspectionRequired;
	}

	public void setPackingGroupId(BigDecimal packingGroupId) {
		this.packingGroupId = packingGroupId;
	}

	public void setPalletId(String palletId) {
		this.palletId = palletId;
	}

	public void setPrNumber(String prNumber) {
		this.prNumber = prNumber;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	// setters
	public void setSourceHub(String sourceHub) {
		this.sourceHub = sourceHub;
	}

	public void setTransportationMode(String transportationMode) {
		this.transportationMode = transportationMode;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

}