package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: InventoryGroupLabelColorBean <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class InventoryGroupLabelColorBean extends BaseDataBean {

	private String inventoryGroup;
	private String labelColor;


	//constructor
	public InventoryGroupLabelColorBean() {
	}

	//setters
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setLabelColor(String labelColor) {
		this.labelColor = labelColor;
	}


	//getters
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getLabelColor() {
		return labelColor;
	}
}