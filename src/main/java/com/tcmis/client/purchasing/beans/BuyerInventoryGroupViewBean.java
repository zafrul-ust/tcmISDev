package com.tcmis.client.purchasing.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: BuyerInventoryGroupViewBean <br>
 * @version: 1.0, Apr 9, 2008 <br>
 *****************************************************************************/


public class BuyerInventoryGroupViewBean extends BaseDataBean {

	private String companyId;
	private BigDecimal personnelId;
	private String inventoryGroup;
	private String inventoryGroupName;
	private String hub;


	//constructor
	public BuyerInventoryGroupViewBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public String getHub() {
		return hub;
	}
}