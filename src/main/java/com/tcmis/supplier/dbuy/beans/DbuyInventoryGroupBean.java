package com.tcmis.supplier.dbuy.beans;

import java.util.Date; 
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DbuyInventoryGroupBean <br>
 * @version: 1.0, May 5, 2006 <br>
 *****************************************************************************/


public class DbuyInventoryGroupBean extends BaseDataBean {

	private String inventoryGroup;


	//constructor
	public DbuyInventoryGroupBean() {
	}

	//setters
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}


	//getters
	public String getInventoryGroup() {
		return inventoryGroup;
	}
}