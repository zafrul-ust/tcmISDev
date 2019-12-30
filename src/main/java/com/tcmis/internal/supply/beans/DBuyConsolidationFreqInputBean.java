package com.tcmis.internal.supply.beans;

import com.tcmis.common.framework.BaseDataBean;

public class DBuyConsolidationFreqInputBean extends BaseDataBean {
	
	private String opsEntityId;
	private String hub;
	private String inventoryGroup;
	private String uAction;
	
	 public DBuyConsolidationFreqInputBean()
		{
		}
	
	
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public String getHub() {
		return hub;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public String getuAction() {
		return uAction;
	}
	public void setuAction(String uAction) {
		this.uAction = uAction;
	}
	
	
	
	
	
	
}
