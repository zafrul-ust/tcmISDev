package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: NoSalesViewBean <br>
 * @version: 1.0, Jun 17, 2009 <br>
 *****************************************************************************/


public class CatalogEnquiryInputBean extends BaseDataBean {

	private String inventoryGroup;
	private String hub;
	private String opsEntityId;
	
	private String itemSearchMode;
	private String itemSearchArgument;
	private String descSearchMode;
	private String descSearchArgument;
	private String categorySearchMode;
	private String categorySearchArgument;
	
	
	//constructor
	public CatalogEnquiryInputBean() {
	}


	public String getCategorySearchArgument() {
		return categorySearchArgument;
	}


	public void setCategorySearchArgument(String categorySearchArgument) {
		this.categorySearchArgument = categorySearchArgument;
	}


	public String getCategorySearchMode() {
		return categorySearchMode;
	}


	public void setCategorySearchMode(String categorySearchMode) {
		this.categorySearchMode = categorySearchMode;
	}


	public String getDescSearchArgument() {
		return descSearchArgument;
	}


	public void setDescSearchArgument(String descSearchArgument) {
		this.descSearchArgument = descSearchArgument;
	}


	public String getDescSearchMode() {
		return descSearchMode;
	}


	public void setDescSearchMode(String descSearchMode) {
		this.descSearchMode = descSearchMode;
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


	public String getItemSearchArgument() {
		return itemSearchArgument;
	}


	public void setItemSearchArgument(String itemSearchArgument) {
		this.itemSearchArgument = itemSearchArgument;
	}


	public String getItemSearchMode() {
		return itemSearchMode;
	}


	public void setItemSearchMode(String itemSearchMode) {
		this.itemSearchMode = itemSearchMode;
	}


	public String getOpsEntityId() {
		return opsEntityId;
	}


	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

}