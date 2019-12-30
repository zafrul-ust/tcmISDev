package com.tcmis.internal.report.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: OpenPicksInputBean <br>
 * @version: 1.0, Aug 25, 2005 <br>
 *****************************************************************************/

public class OpenPicksInputBean    extends BaseDataBean {

	private static final long serialVersionUID = 2367843761629822224L;
	private String action;
	private String opsEntityId;
	private String hub;
	private String inventoryGroup;
	private String picklistId;
	
	//constructor
	public OpenPicksInputBean() {
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
	 * @param picklistId the picklistId to set
	 */
	public void setPicklistId(String picklistId) {
		this.picklistId = picklistId;
	}

	/**
	 * @return the picklistId
	 */
	public String getPicklistId() {
		return picklistId;
	}



	


}