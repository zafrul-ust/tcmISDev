package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class ItemDirectSupplyViewBean extends BaseDataBean {
	
	private String branchPlant;
	private String sourceHub;
	private String hub;
	private BigDecimal itemId;
	private String itemDesc;
	private String inventoryGroup;
	private String inventoryGroupName;
	private String opsEntityId;
	private String operatingentityName;
	private String sourceInventoryGroup;
	private String sourceInventoryGroupName;
	private String sourceOpsEntityId;
	private String sourceOpsEntityName;
	private BigDecimal enteredBy;
	private String enteredByName;
	private Date enteredDate;
	private BigDecimal updatedBy;
	private String updatedByName;
	private Date updatedDate;
	private String packaging;
	private boolean okDoUpdate;
	
	public String getBranchPlant() {
		return branchPlant;
	}
	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}
	public String getSourceHub() {
		return sourceHub;
	}
	public void setSourceHub(String sourceHub) {
		this.sourceHub = sourceHub;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
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
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public String getOperatingentityName() {
		return operatingentityName;
	}
	public void setOperatingentityName(String operatingentityName) {
		this.operatingentityName = operatingentityName;
	}
	public String getSourceInventoryGroup() {
		return sourceInventoryGroup;
	}
	public void setSourceInventoryGroup(String sourceInventoryGroup) {
		this.sourceInventoryGroup = sourceInventoryGroup;
	}
	public String getSourceInventoryGroupName() {
		return sourceInventoryGroupName;
	}
	public void setSourceInventoryGroupName(String sourceInventoryGroupName) {
		this.sourceInventoryGroupName = sourceInventoryGroupName;
	}
	public String getSourceOpsEntityId() {
		return sourceOpsEntityId;
	}
	public void setSourceOpsEntityId(String sourceOpsEntityId) {
		this.sourceOpsEntityId = sourceOpsEntityId;
	}
	public String getSourceOpsEntityName() {
		return sourceOpsEntityName;
	}
	public void setSourceOpsEntityName(String sourceOpsEntityName) {
		this.sourceOpsEntityName = sourceOpsEntityName;
	}
	public BigDecimal getEnteredBy() {
		return enteredBy;
	}
	public void setEnteredBy(BigDecimal enteredBy) {
		this.enteredBy = enteredBy;
	}
	public Date getEnteredDate() {
		return enteredDate;
	}
	public void setEnteredDate(Date enteredDate) {
		this.enteredDate = enteredDate;
	}
	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUpdatedByName() {
		return updatedByName;
	}
	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getHub() {
		return hub;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public String getPackaging() {
		return packaging;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public String getEnteredByName() {
		return enteredByName;
	}
	public void setEnteredByName(String enteredByName) {
		this.enteredByName = enteredByName;
	}
	public boolean isOkDoUpdate() {
		return okDoUpdate;
	}

	public void setOkDoUpdate(boolean okDoUpdate) {
		this.okDoUpdate = okDoUpdate;
	}
		
	

}
