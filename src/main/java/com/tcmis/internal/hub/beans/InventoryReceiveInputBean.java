package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

/******************************************************************************
 * CLASSNAME: InventoryReceiveInputBean <br>
 * @version: 1.0, Jul 1, 2008 <br>
 *****************************************************************************/

public class InventoryReceiveInputBean {
	private String inventoryGroup;
	private String opsEntityId;
	private String searchField;
	private String searchMode;
	private String searchArgument;
	private String hub;
	private Date expDeliveryFromDate;
	private Date expDeliveryToDate;
	private String showAvailable;
	
	
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public String getSearchArgument() {
		return searchArgument;
	}
	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getSearchMode() {
		return searchMode;
	}
	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}
	public String getHub() {
		return hub;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public Date getExpDeliveryFromDate() {
		return expDeliveryFromDate;
	}
	public void setExpDeliveryFromDate(Date expDeliveryFromDate) {
		this.expDeliveryFromDate = expDeliveryFromDate;
	}
	public Date getExpDeliveryToDate() {
		return expDeliveryToDate;
	}
	public void setExpDeliveryToDate(Date expDeliveryToDate) {
		this.expDeliveryToDate = expDeliveryToDate;
	}
	public String getShowAvailable() {
		return showAvailable;
	}
	public void setShowAvailable(String showAvailable) {
		this.showAvailable = showAvailable;
	}
	
	
	
	
}