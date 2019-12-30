package com.tcmis.internal.supply.beans;

import com.tcmis.common.framework.BaseDataBean;

public class InventoryGroupSelectionBean extends BaseDataBean{
	private String homeCurrencyId;
	private String inventoryGroup;
	private String inventoryGroupName;
	private String locationId;
	private String opsEntityId;
	
	public String getHomeCurrencyId() {
		return homeCurrencyId;
	}
	public void setHomeCurrencyId(String homeCurrencyId) {
		this.homeCurrencyId = homeCurrencyId;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	
	
}
