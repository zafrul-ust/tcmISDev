package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class UpdateItemShelfLifeBean extends BaseDataBean {

	private String inventoryGroup;
	private String inventoryGroupName;
	private String itemDesc;
	private String materialDesc;
	private BigDecimal partId;
	private BigDecimal itemId;
	private BigDecimal shelfLifeDays;
	private String storageTemp;
	private String shelfLifeBasis;
	private String shelfLifeBasisDesc;
	private BigDecimal lastUpdatedBy;
	private String lastUpdatedByName;
	private Date lastUpdatedOn;
	private String labelColor;
	private String source;
	private String comments;
	private String packaging;
	
	public UpdateItemShelfLifeBean() {
		
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getShelfLifeDays() {
		return shelfLifeDays;
	}

	public void setShelfLifeDays(BigDecimal shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}

	public String getStorageTemp() {
		return storageTemp;
	}

	public void setStorageTemp(String storageTemp) {
		this.storageTemp = storageTemp;
	}

	public String getShelfLifeBasis() {
		return shelfLifeBasis;
	}

	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}

	public String getShelfLifeBasisDesc() {
		return shelfLifeBasisDesc;
	}

	public void setShelfLifeBasisDesc(String shelfLifeBasisDesc) {
		this.shelfLifeBasisDesc = shelfLifeBasisDesc;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public BigDecimal getPartId() {
		return partId;
	}

	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}

	public BigDecimal getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(BigDecimal lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdatedByName() {
		return lastUpdatedByName;
	}

	public void setLastUpdatedByName(String lastUpdatedByName) {
		this.lastUpdatedByName = lastUpdatedByName;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getLabelColor() {
		return labelColor;
	}

	public void setLabelColor(String labelColor) {
		this.labelColor = labelColor;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
