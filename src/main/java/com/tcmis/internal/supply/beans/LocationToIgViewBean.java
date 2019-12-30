package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: LocationToIgViewBean <br>
 * @version: 1.0, Oct 29, 2009 <br>
 *****************************************************************************/


public class LocationToIgViewBean extends BaseDataBean {

	private String inventoryGroup;
	private String inventoryGroupName;
	private String homeCurrencyId;
	private String locationId;


	//constructor
	public LocationToIgViewBean() {
	}

	//setters
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
	public void setHomeCurrencyId(String homeCurrencyId) {
		this.homeCurrencyId = homeCurrencyId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}


	//getters
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public String getHomeCurrencyId() {
		return homeCurrencyId;
	}
	public String getLocationId() {
		return locationId;
	}
}