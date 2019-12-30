package com.tcmis.supplier.shipping.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class CatPartSearchViewBean extends BaseDataBean {
	private String catalogCompanyId;
	private String catalogId;
	private String catPartNo;
	private String companyId;
	private String conversionGroup;
	private String domRequired;
	private String gfp;
	private BigDecimal inventoryLevelId;
	private BigDecimal reorderPoint;
	private String shipFromLocationId;
	private BigDecimal stockingLevel;
	private String supplier;
	
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
	
	public String getDomRequired() {
		return domRequired;
	}
	
	public void setDomRequired(String domRequired) {
		this.domRequired = domRequired;
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

	public BigDecimal getInventoryLevelId() {
		return inventoryLevelId;
	}

	public void setInventoryLevelId(BigDecimal inventoryLevelId) {
		this.inventoryLevelId = inventoryLevelId;
	}

	public String getGfp() {
		return gfp;
	}

	public void setGfp(String gfp) {
		this.gfp = gfp;
	}

	public String getConversionGroup() {
		return conversionGroup;
	}

	public void setConversionGroup(String conversionGroup) {
		this.conversionGroup = conversionGroup;
	}
}