package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

public class TabletPickableUnitViewBean {

	private String		bin;
	private String		hub;
	private String		lineItem;
	private BigDecimal	pickingGroupId;
	private String		pickingGroupName;
	private String		pickingState;
	private String		pickingStateDesc;
	private BigDecimal	pickingUnitId;
	private BigDecimal	picklistId;
	private BigDecimal	prNumber;
	private String		room;
	private String		shipToLocationDesc;
	private boolean		update;

	public String getBin() {
		return this.bin;
	}

	public String getHub() {
		return hub;
	}

	public String getLineItem() {
		return lineItem;
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

	public BigDecimal getPickingUnitId() {
		return pickingUnitId;
	}

	public BigDecimal getPicklistId() {
		return picklistId;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public String getRoom() {
		return this.room;
	}

	public String getShipToLocationDesc() {
		return this.shipToLocationDesc;
	}

	public boolean isUpdate() {
		return update;
	}

	public boolean matches(TabletPickableUnitViewBean pickingUnit) {
		return pickingUnit.getPickingUnitId().compareTo(pickingUnitId) == 0;
	}

	public void setBin(String bin) {
		if (StringUtils.isNotBlank(this.bin)) {
			if (!this.bin.contains(bin)) {
				this.bin += ", " + bin;
			}
		}
		else {
			this.bin = bin;
		}
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
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

	public void setPickingUnitId(BigDecimal pickingUnitId) {
		this.pickingUnitId = pickingUnitId;
	}

	public void setPicklistId(BigDecimal picklistId) {
		this.picklistId = picklistId;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public void setRoom(String room) {
		if (StringUtils.isNotBlank(this.room)) {
			if (!this.room.contains(room)) {
				this.room += ", " + room;
			}
		}
		else {
			this.room = room;
		}
	}

	public void setShipToLocationDesc(String shipToLocationDesc) {
		this.shipToLocationDesc = shipToLocationDesc;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}
}
