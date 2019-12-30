package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

@SuppressWarnings("serial")
public class NsnItemSearchViewBean extends BaseDataBean {

	private BigDecimal itemId;
	private String itemType;
	private String itemDesc;
	private String status;
	
	public BigDecimal getItemId() {
		return itemId;
	}
	
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	
	public String getItemType() {
		return itemType;
	}
	
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	public String getItemDesc() {
		return itemDesc;
	}
	
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}
