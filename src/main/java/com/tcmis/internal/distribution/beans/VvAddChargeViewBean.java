package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VvAddChargeViewBean <br>
 * @version: 1.0, Aug 11, 2009 <br>
 *****************************************************************************/


public class VvAddChargeViewBean extends BaseDataBean {

	private BigDecimal itemId;
	private String itemDesc;
	private String itemType;


	//constructor
	public VvAddChargeViewBean() {
	}

	//setters
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}


	//getters
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getItemType() {
		return itemType;
	}

}