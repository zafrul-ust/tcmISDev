package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class AddItemToHubCountInputBean extends BaseDataBean 
{

	private static final long serialVersionUID = 3668983642404942728L;
	private String action;	
	private String hub;
	private String searchArgument;
	private String inventoryGroup;
	private BigDecimal itemId;
	private BigDecimal countId;
	public AddItemToHubCountInputBean()
	{		  
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public String getHub() {
		return hub;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public String getSearchArgument() {
		return searchArgument;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setCountId(BigDecimal countId) {
		this.countId = countId;
	}

	public BigDecimal getCountId() {
		return countId;
	}


}
