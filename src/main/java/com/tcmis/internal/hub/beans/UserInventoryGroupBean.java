package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubPrefBean <br>
 * @version: 1.0, Jun 9, 2004 <br>
 *****************************************************************************/

public class UserInventoryGroupBean
 extends BaseDataBean {

 private String companyId;
 private BigDecimal personnelId;
 private String hub;
 private String inventoryGroup;
 
 //constructor
 public UserInventoryGroupBean() {
 }

public String getCompanyId() {
	return companyId;
}

public void setCompanyId(String companyId) {
	this.companyId = companyId;
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

public BigDecimal getPersonnelId() {
	return personnelId;
}

public void setPersonnelId(BigDecimal personnelId) {
	this.personnelId = personnelId;
}

}