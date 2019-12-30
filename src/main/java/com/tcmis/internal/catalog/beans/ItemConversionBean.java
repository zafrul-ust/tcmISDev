package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;

public class ItemConversionBean {

	private BigDecimal itemId;
	private String inventoryGroup;
	private BigDecimal blanketMr;
	private BigDecimal originalItemId;
	private String autoTap;
	private BigDecimal priority;
	
	public BigDecimal getItemId() {
		return itemId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public BigDecimal getBlanketMr() {
		return blanketMr;
	}
	public void setBlanketMr(BigDecimal blanketMr) {
		this.blanketMr = blanketMr;
	}
	public BigDecimal getOriginalItemId() {
		return originalItemId;
	}
	public void setOriginalItemId(BigDecimal originalItemId) {
		this.originalItemId = originalItemId;
	}
	public String getAutoTap() {
		return autoTap;
	}
	public void setAutoTap(String autoTap) {
		this.autoTap = autoTap;
	}
	public BigDecimal getPriority() {
		return priority;
	}
	public void setPriority(BigDecimal priority) {
		this.priority = priority;
	}
}
