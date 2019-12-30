package com.tcmis.internal.hub.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: LocationLabelPrinterBean <br>
 * @version: 1.0, Aug 25, 2005 <br>
 *****************************************************************************/

public class NWeekInventoryInputBean    extends BaseDataBean {


	private static final long serialVersionUID = 7922209148017908196L;
	private String action;
	private String opsEntityId;
	private String hub;
	private String inventoryGroup;
	private String searchArgument;
	
	//constructor
	public NWeekInventoryInputBean() {
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

	/**
	 * @param searchArgument the searchArgument to set
	 */
	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	/**
	 * @return the searchArgument
	 */
	public String getSearchArgument() {
		return searchArgument;
	}


}