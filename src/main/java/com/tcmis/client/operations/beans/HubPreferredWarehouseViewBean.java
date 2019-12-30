package com.tcmis.client.operations.beans;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: HubPreferredWarehouseViewBean <br>
 * @version: 1.0, Aug 3, 2009 <br>
 *****************************************************************************/


public class HubPreferredWarehouseViewBean extends BaseDataBean {
	
	private static final long serialVersionUID = 3791249062785383564L;
	private String branchPlant;
	private String preferredWarehouse;


	//constructor
	public HubPreferredWarehouseViewBean() {
	}

	//setters
	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}
	public void setPreferredWarehouse(String preferredWarehouse) {
		this.preferredWarehouse = preferredWarehouse;
	}


	//getters
	public String getBranchPlant() {
		return branchPlant;
	}
	public String getPreferredWarehouse() {
		return preferredWarehouse;
	}
}