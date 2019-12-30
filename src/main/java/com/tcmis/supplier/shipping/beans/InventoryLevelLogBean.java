package com.tcmis.supplier.shipping.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class InventoryLevelLogBean extends BaseDataBean {
	private BigDecimal inventoryLevelId;
	private BigDecimal logId;
	private BigDecimal newReorderPoint;
	private BigDecimal newStockingLevel;
	private BigDecimal oldReorderPoint;
	private BigDecimal oldStockingLevel;
	private BigDecimal updatedBy;
	private String updatedByName;
	private Date updatedDate;
	
	public BigDecimal getInventoryLevelId() {
		return inventoryLevelId;
	}
	
	public void setInventoryLevelId(BigDecimal inventoryLevelId) {
		this.inventoryLevelId = inventoryLevelId;
	}
	
	public BigDecimal getLogId() {
		return logId;
	}
	
	public void setLogId(BigDecimal logId) {
		this.logId = logId;
	}

	public BigDecimal getNewReorderPoint() {
		return newReorderPoint;
	}

	public void setNewReorderPoint(BigDecimal newReorderPoint) {
		this.newReorderPoint = newReorderPoint;
	}

	public BigDecimal getNewStockingLevel() {
		return newStockingLevel;
	}

	public void setNewStockingLevel(BigDecimal newStockingLevel) {
		this.newStockingLevel = newStockingLevel;
	}

	public BigDecimal getOldReorderPoint() {
		return oldReorderPoint;
	}

	public void setOldReorderPoint(BigDecimal oldReorderPoint) {
		this.oldReorderPoint = oldReorderPoint;
	}

	public BigDecimal getOldStockingLevel() {
		return oldStockingLevel;
	}

	public void setOldStockingLevel(BigDecimal oldStockingLevel) {
		this.oldStockingLevel = oldStockingLevel;
	}

	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedByName() {
		return updatedByName;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}
	
}