package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ForceRepackageOrderViewBean <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class ForceRepackageOrderViewBean extends BaseDataBean {

	private BigDecimal itemId;
	private String hub;
	private String inventoryGroup;
	private String itemDesc;
	private String itemPkg;
	private String searchText;


	//constructor
	public ForceRepackageOrderViewBean() {
	}

	//setters
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setItemPkg(String itemPkg) {
		this.itemPkg = itemPkg;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}


	//getters
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getHub() {
		return hub;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getItemPkg() {
		return itemPkg;
	}
	public String getSearchText() {
		return searchText;
	}
}