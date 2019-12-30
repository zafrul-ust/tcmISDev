package com.tcmis.internal.supply.beans;

import com.tcmis.common.framework.HubBaseInputBean;

public class EditNeedByToleranceInputBean extends HubBaseInputBean {
	
	private String uAction;
	private String inventoryGroupName;
	private String needByTolerance;
	

	public EditNeedByToleranceInputBean() {
	}
	
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public String getNeedByTolerance() {
		return needByTolerance;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public void setNeedByTolerance(String needByTolerance) {
		this.needByTolerance = needByTolerance;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
		
	}


}
