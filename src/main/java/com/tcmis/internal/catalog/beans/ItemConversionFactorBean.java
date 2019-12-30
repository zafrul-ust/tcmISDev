package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;

public class ItemConversionFactorBean {

	private BigDecimal itemId;
	private BigDecimal originalItemId;
	private BigDecimal quantityPerOriginalItem;
	private String itemType;
	private String originalItemType;
	private BigDecimal materialId;
	private BigDecimal partId;
	private BigDecimal conversionFactorCount;

	public BigDecimal getItemId() {
		return itemId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public BigDecimal getOriginalItemId() {
		return originalItemId;
	}
	public void setOriginalItemId(BigDecimal originalItemId) {
		this.originalItemId = originalItemId;
	}
	public BigDecimal getQuantityPerOriginalItem() {
		return quantityPerOriginalItem;
	}
	public void setQuantityPerOriginalItem(BigDecimal quantityPerOriginalItem) {
		this.quantityPerOriginalItem = quantityPerOriginalItem;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getOriginalItemType() {
		return originalItemType;
	}
	public void setOriginalItemType(String originalItemType) {
		this.originalItemType = originalItemType;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public BigDecimal getPartId() {
		return partId;
	}
	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}
	public BigDecimal getConversionFactorCount() {
		return conversionFactorCount;
	}
	public void setConversionFactorCount(BigDecimal conversionFactorCount) {
		this.conversionFactorCount = conversionFactorCount;
	}
}
