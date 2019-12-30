package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: IgItemCompanyBean <br>
 * @version: 1.0, Sep 6, 2006 <br>
 *****************************************************************************/


public class IgItemCompanyBean extends BaseDataBean {

	private String inventoryGroup;
	private BigDecimal itemId;
	private String companyId;
	private String companyName;


	//constructor
	public IgItemCompanyBean() {
	}

	//setters
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	//getters
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
}