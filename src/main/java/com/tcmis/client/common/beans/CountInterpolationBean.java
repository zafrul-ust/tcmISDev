package com.tcmis.client.common.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class CountInterpolationBean extends BaseDataBean {

	private String companyId;
	private BigDecimal binId;
	private BigDecimal countQuantity; //COUNT_QUANTITY
	private BigDecimal inventoryQuantity; //INVENTORY_QUANTITY
	private String updated;
	private String newrow;
	private BigDecimal oldCountQuantity;
	private String delete;
	
	public BigDecimal getBinId() {
		return binId;
	}
	public void setBinId(BigDecimal binId) {
		this.binId = binId;
	}
	public BigDecimal getCountQuantity() {
		return countQuantity;
	}
	public void setCountQuantity(BigDecimal countQuantity) {
		this.countQuantity = countQuantity;
	}
	public BigDecimal getInventoryQuantity() {
		return inventoryQuantity;
	}
	public void setInventoryQuantity(BigDecimal inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getNewrow() {
		return newrow;
	}
	public void setNewrow(String newrow) {
		this.newrow = newrow;
	}
	public BigDecimal getOldCountQuantity() {
		return oldCountQuantity;
	}
	public void setOldCountQuantity(BigDecimal oldCountQuantity) {
		this.oldCountQuantity = oldCountQuantity;
	}
	public boolean isNewCountInterpolation() {
		return (newrow != null && newrow.equalsIgnoreCase("Y"));
	}
	public String getDelete() {
		return delete;
	}
	public void setDelete(String delete) {
		this.delete = delete;
	}
	public boolean isDeleteCountInterpolation() {
		boolean blnDelete = false;
		if (this.delete != null && !this.delete.equalsIgnoreCase("")){
			blnDelete = (this.delete.equalsIgnoreCase("true")?true:false);
		}
		return blnDelete;
	}
}
