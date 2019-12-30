package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: IgItemSearchViewBean <br>
 * @version: 1.0, Aug 11, 2009 <br>
 *****************************************************************************/


public class IgItemSearchViewBean extends BaseDataBean {


	private static final long serialVersionUID = -793151415960803746L;
	
	private BigDecimal itemId;
	private String itemDesc;
	private String inventoryGroup;
	private String searchString;
	private String ok;

	//constructor
	public IgItemSearchViewBean() {
	}

	//setters
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}


	//getters
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getSearchString() {
		return searchString;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getOk() {
		return ok;
	}
}