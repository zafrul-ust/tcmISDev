package com.tcmis.internal.hub.beans;

//import java.math.BigDecimal;
//import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: TempShipmentHistoryViewBean <br>
 * @version: 1.0, Apr 13, 2005 <br>
 *****************************************************************************/

public class ForceBuyInputBean
extends BaseDataBean {

	private String  hub;
	private String  inventoryGroup;
	
	private String searchField;
	private String searchMode;
	private String searchArgument;
	private String showMinMaxOnly;
	
	//constructor
	public ForceBuyInputBean() {
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

	public String getSearchArgument() {
		return searchArgument;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchMode() {
		return searchMode;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	public String getShowMinMaxOnly() {
		return showMinMaxOnly;
	}

	public void setShowMinMaxOnly(String showMinMaxOnly) {
		this.showMinMaxOnly = showMinMaxOnly;
	}

	
}
