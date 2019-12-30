package com.tcmis.internal.supply.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: NoSalesViewBean <br>
 * @version: 1.0, Jun 17, 2009 <br>
 *****************************************************************************/


public class SupplierReturnsInputBean extends BaseDataBean {

	private String inventoryGroup;
	private String hub;
	private String opsEntityId;
	private String searchField;
	private String searchMode;
	private String searchArgument;
	
	
	//constructor
	public SupplierReturnsInputBean() {
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


	public String getOpsEntityId() {
		return opsEntityId;
	}


	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
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




}