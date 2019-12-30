package com.tcmis.internal.hub.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InventoryAdjustmentsInputBean <br>
 * @version: 1.0, Aug 25, 2005 <br>
 *****************************************************************************/

public class InventoryAdjustmentsInputBean    extends BaseDataBean {


	private static final long serialVersionUID = 6662741581997068096L;
	private String action;
	private String opsEntityId;
	private String hub;
	private String inventoryGroup;
	private String lotStatusSelectBox;
	
	private String searchArgument;
	private String searchMode;
	private String searchField;
	
	//constructor
	public InventoryAdjustmentsInputBean() {
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

	/**
	 * @param opsEntityId the opsEntityId to set
	 */
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	/**
	 * @return the opsEntityId
	 */
	public String getOpsEntityId() {
		return opsEntityId;
	}

	/**
	 * @param hub the hub to set
	 */
	public void setHub(String hub) {
		this.hub = hub;
	}

	/**
	 * @return the hub
	 */
	public String getHub() {
		return hub;
	}

	/**
	 * @param inventoryGroup the inventoryGroup to set
	 */
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	/**
	 * @return the inventoryGroup
	 */
	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setLotStatusSelectBox(String lotStatusSelectBox) {
		this.lotStatusSelectBox = lotStatusSelectBox;
	}

	public String getLotStatusSelectBox() {
		return lotStatusSelectBox;
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

}