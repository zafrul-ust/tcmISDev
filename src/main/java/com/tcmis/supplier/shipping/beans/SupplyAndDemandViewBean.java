package com.tcmis.supplier.shipping.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;

public class SupplyAndDemandViewBean extends BaseDataBean {
	private String catalogCompanyId;
	private String catalogId;
	private String catPartNo;
	private String companyId;
	private String conversionGroup;
	private String gfp;
	private BigDecimal inventoryLevelId;
	private String locationDesc;
	private BigDecimal pendingBuyQty;
	private BigDecimal reorderPoint;
	private String shipFromLocationId;
	private BigDecimal stockingLevel;
	private String supplier;
	private BigDecimal totalAvailableQty;
	private BigDecimal totalMrQty;
	private BigDecimal totalOpenPoQty;
	private BigDecimal totalSupplyQty;
	
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
	
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}
	
	public String getCatalogId() {
		return catalogId;
	}
	
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	
	public String getCatPartNo() {
		return catPartNo;
	}
	
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String getGfp() {
		return gfp;
	}
	
	public void setGfp(String gfp) {
		this.gfp = gfp;
	}
	
	public BigDecimal getInventoryLevelId() {
		return inventoryLevelId;
	}
	
	public void setInventoryLevelId(BigDecimal inventoryLevelId) {
		this.inventoryLevelId = inventoryLevelId;
	}
	
	public String getLocationDesc() {
		return locationDesc;
	}
	
	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}
	
	public BigDecimal getPendingBuyQty() {
		return pendingBuyQty;
	}
	
	public void setPendingBuyQty(BigDecimal pendingBuyQty) {
		this.pendingBuyQty = pendingBuyQty;
	}
	
	public BigDecimal getReorderPoint() {
		return reorderPoint;
	}
	
	public void setReorderPoint(BigDecimal reorderPoint) {
		this.reorderPoint = reorderPoint;
	}
	
	public String getShipFromLocationId() {
		return shipFromLocationId;
	}
	
	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}
	
	public BigDecimal getStockingLevel() {
		return stockingLevel;
	}
	
	public void setStockingLevel(BigDecimal stockingLevel) {
		this.stockingLevel = stockingLevel;
	}
	
	public String getSupplier() {
		return supplier;
	}
	
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
	public BigDecimal getTotalAvailableQty() {
		return totalAvailableQty;
	}
	
	public void setTotalAvailableQty(BigDecimal totalAvailableQty) {
		this.totalAvailableQty = totalAvailableQty;
	}
	
	public BigDecimal getTotalMrQty() {
		return totalMrQty;
	}
	
	public void setTotalMrQty(BigDecimal totalMrQty) {
		this.totalMrQty = totalMrQty;
	}
	
	public BigDecimal getTotalOpenPoQty() {
		return totalOpenPoQty;
	}
	
	public void setTotalOpenPoQty(BigDecimal totalOpenPoQty) {
		this.totalOpenPoQty = totalOpenPoQty;
	}
	
	public BigDecimal getTotalSupplyQty() {
		return totalSupplyQty;
	}
	
	public void setTotalSupplyQty(BigDecimal totalSupplyQty) {
		this.totalSupplyQty = totalSupplyQty;
	}

	public String getConversionGroup() {
		return conversionGroup;
	}

	public void setConversionGroup(String conversionGroup) {
		this.conversionGroup = conversionGroup;
	}
}