package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: PrCatalogScreenSearchViewBean <br>
 * @version: 1.0, May 31, 2005 <br>
 *****************************************************************************/

public class PrCatalogScreenSearchComponentBean
 extends BaseDataBean {

 /*private String currencyId;
	 private String comments;
	 private String catalogId;
	 private String catPartNo;
	 private BigDecimal partGroupNo;
	 private String inventoryGroup;
	 private String partGroup;
	 private String partItemGroup;
	 private String storageTemp;
	 private String shelfLife;
	 private String shelfLifeBasis;
	 private BigDecimal itemId;
	 private String bundle;
	 private BigDecimal minCatalogPrice;
	 private BigDecimal maxCatalogPrice;
	 private BigDecimal minUnitPrice;
	 private BigDecimal maxUnitPrice;
	 private BigDecimal minBuy;
	 private String partDescription;
	 private BigDecimal quantityPerBundle;
	 private BigDecimal partId;
	 private BigDecimal componentsPerItem;
	 private String sizeVaries;*/
 private String materialDesc;
 private String mfgDesc;
 private BigDecimal materialId;
 private String packaging;
 private String grade;
 private String dimension;
 private String mfgPartNo;
 private String msdsOnLine;
 /*private String approvalStatus;
	 private BigDecimal limitQuantityPeriod1;
	 private BigDecimal daysPeriod1;
	 private BigDecimal limitQuantityPeriod2;
	 private BigDecimal daysPeriod2;
	 private String sourceHub;
	 private String stockingMethod;
	 private String specs;
		private BigDecimal rowSpan;*/

 //constructor
 public PrCatalogScreenSearchComponentBean() {
 }

 //setters
 /*public void setCurrencyId(String currencyId) {
	this.currencyId = currencyId;
	 }
	 public void setComments(String comments) {
	this.comments = comments;
	 }
	 public void setCatalogId(String catalogId) {
	this.catalogId = catalogId;
	 }
	 public void setCatPartNo(String catPartNo) {
	this.catPartNo = catPartNo;
	 }
	 public void setPartGroupNo(BigDecimal partGroupNo) {
	this.partGroupNo = partGroupNo;
	 }
	 public void setInventoryGroup(String inventoryGroup) {
	this.inventoryGroup = inventoryGroup;
	 }
	 public void setPartGroup(String partGroup) {
	this.partGroup = partGroup;
	 }
	 public void setPartItemGroup(String partItemGroup) {
	this.partItemGroup = partItemGroup;
	 }
	 public void setStorageTemp(String storageTemp) {
	this.storageTemp = storageTemp;
	 }
	 public void setShelfLife(String shelfLife) {
	this.shelfLife = shelfLife;
	 }
	 public void setShelfLifeBasis(String shelfLifeBasis) {
	this.shelfLifeBasis = shelfLifeBasis;
	 }
	 public void setItemId(BigDecimal itemId) {
	this.itemId = itemId;
	 }
	 public void setBundle(String bundle) {
	this.bundle = bundle;
	 }
	 public void setMinCatalogPrice(BigDecimal minCatalogPrice) {
	this.minCatalogPrice = minCatalogPrice;
	 }
	 public void setMaxCatalogPrice(BigDecimal maxCatalogPrice) {
	this.maxCatalogPrice = maxCatalogPrice;
	 }
	 public void setMinUnitPrice(BigDecimal minUnitPrice) {
	this.minUnitPrice = minUnitPrice;
	 }
	 public void setMaxUnitPrice(BigDecimal maxUnitPrice) {
	this.maxUnitPrice = maxUnitPrice;
	 }
	 public void setMinBuy(BigDecimal minBuy) {
	this.minBuy = minBuy;
	 }
	 public void setPartDescription(String partDescription) {
	this.partDescription = partDescription;
	 }
	 public void setQuantityPerBundle(BigDecimal quantityPerBundle) {
	this.quantityPerBundle = quantityPerBundle;
	 }
	 public void setPartId(BigDecimal partId) {
	this.partId = partId;
	 }
	 public void setComponentsPerItem(BigDecimal componentsPerItem) {
	this.componentsPerItem = componentsPerItem;
	 }
	 public void setSizeVaries(String sizeVaries) {
	this.sizeVaries = sizeVaries;
	 }*/
 public void setMaterialDesc(String materialDesc) {
	this.materialDesc = materialDesc;
 }

 public void setMfgDesc(String mfgDesc) {
	this.mfgDesc = mfgDesc;
 }

 public void setMaterialId(BigDecimal materialId) {
	this.materialId = materialId;
 }

 public void setPackaging(String packaging) {
	this.packaging = packaging;
 }

 public void setGrade(String grade) {
	this.grade = grade;
 }

 public void setDimension(String dimension) {
	this.dimension = dimension;
 }

 public void setMfgPartNo(String mfgPartNo) {
	this.mfgPartNo = mfgPartNo;
 }

 public void setMsdsOnLine(String msdsOnLine) {
	this.msdsOnLine = msdsOnLine;
 }

 /*public void setApprovalStatus(String approvalStatus) {
	this.approvalStatus = approvalStatus;
	 }
	 public void setLimitQuantityPeriod1(BigDecimal limitQuantityPeriod1) {
	this.limitQuantityPeriod1 = limitQuantityPeriod1;
	 }
	 public void setDaysPeriod1(BigDecimal daysPeriod1) {
	this.daysPeriod1 = daysPeriod1;
	 }
	 public void setLimitQuantityPeriod2(BigDecimal limitQuantityPeriod2) {
	this.limitQuantityPeriod2 = limitQuantityPeriod2;
	 }
	 public void setDaysPeriod2(BigDecimal daysPeriod2) {
	this.daysPeriod2 = daysPeriod2;
	 }
	 public void setSourceHub(String sourceHub) {
	this.sourceHub = sourceHub;
	 }
	 public void setStockingMethod(String stockingMethod) {
	this.stockingMethod = stockingMethod;
	 }
	 public void setSpecs(String specs) {
	this.specs = specs;
	 }
	 public void setRowSpan(BigDecimal rowSpan) {
	this.rowSpan = rowSpan;
		}*/

 //getters
 /*public String getCurrencyId() {
	return currencyId;
	 }
	 public String getComments() {
	return comments;
	 }
	 public String getCatalogId() {
	return catalogId;
	 }
	 public String getCatPartNo() {
	return catPartNo;
	 }
	 public BigDecimal getPartGroupNo() {
	return partGroupNo;
	 }
	 public String getInventoryGroup() {
	return inventoryGroup;
	 }
	 public String getPartGroup() {
	return partGroup;
	 }
	 public String getPartItemGroup() {
	return partItemGroup;
	 }
	 public String getStorageTemp() {
	return storageTemp;
	 }
	 public String getShelfLife() {
	return shelfLife;
	 }
	 public String getShelfLifeBasis() {
	return shelfLifeBasis;
	 }
	 public BigDecimal getItemId() {
	return itemId;
	 }
	 public String getBundle() {
	return bundle;
	 }
	 public BigDecimal getMinCatalogPrice() {
	return minCatalogPrice;
	 }
	 public BigDecimal getMaxCatalogPrice() {
	return maxCatalogPrice;
	 }
	 public BigDecimal getMinUnitPrice() {
	return minUnitPrice;
	 }
	 public BigDecimal getMaxUnitPrice() {
	return maxUnitPrice;
	 }
	 public BigDecimal getMinBuy() {
	return minBuy;
	 }
	 public String getPartDescription() {
	return partDescription;
	 }
	 public BigDecimal getQuantityPerBundle() {
	return quantityPerBundle;
	 }
	 public BigDecimal getPartId() {
	return partId;
	 }
	 public BigDecimal getComponentsPerItem() {
	return componentsPerItem;
	 }
	 public String getSizeVaries() {
	return sizeVaries;
	 }*/
 public String getMaterialDesc() {
	return materialDesc;
 }

 public String getMfgDesc() {
	return mfgDesc;
 }

 public BigDecimal getMaterialId() {
	return materialId;
 }

 public String getPackaging() {
	return packaging;
 }

 public String getGrade() {
	return grade;
 }

 public String getDimension() {
	return dimension;
 }

 public String getMfgPartNo() {
	return mfgPartNo;
 }

 public String getMsdsOnLine() {
	return msdsOnLine;
 }
 /*public String getApprovalStatus() {
	return approvalStatus;
	 }
	 public BigDecimal getLimitQuantityPeriod1() {
	return limitQuantityPeriod1;
	 }
	 public BigDecimal getDaysPeriod1() {
	return daysPeriod1;
	 }
	 public BigDecimal getLimitQuantityPeriod2() {
	return limitQuantityPeriod2;
	 }
	 public BigDecimal getDaysPeriod2() {
	return daysPeriod2;
	 }
	 public String getSourceHub() {
	return sourceHub;
	 }
	 public String getStockingMethod() {
	return stockingMethod;
	 }
	 public String getSpecs() {
	return specs;
	 }
	 public BigDecimal getRowSpan() {
		 return rowSpan;
		}*/

}