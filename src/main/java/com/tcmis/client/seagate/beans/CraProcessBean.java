package com.tcmis.client.seagate.beans;

import java.math.BigDecimal;

public class CraProcessBean {

	 private String branchPlant;
	 private String inventoryGroupDefault;
	 private BigDecimal itemId;
	 private String facilityId;
	 private String catalogId;
	 private String catPartNo;
	 private String needPricing;
	 private BigDecimal baselinePrice;
	 private String specLibrary;
	 private String tradeName;
	 
		public String getBranchPlant() {
			return branchPlant;
		}

		public String getInventoryGroupDefault() {
			return inventoryGroupDefault;
		}

		public BigDecimal getItemId() {
			return itemId;
		}
		
		public String getCatalogId() {
			return catalogId;
		}
		
		public String getFacilityId() {
			return this.facilityId;
		}
		
		 public String getCatPartNo() {
			return catPartNo;
		}
		 
		 public String getNeedPricing() {
				return needPricing;
		}
		 
		public BigDecimal getBaselinePrice() {
				return baselinePrice;
		}
		public String getSpecLibrary() {
				return specLibrary;
		}

		public String getTradeName() {
			return tradeName;
		}
		
		public void setTradeName(String tradeName) {
			this.tradeName = tradeName;
		}

		public void setSpecLibrary(String specLibrary) {
			this.specLibrary = specLibrary;
		}
		
		public void setBaselinePrice(BigDecimal baselinePrice) {
			this.baselinePrice = baselinePrice;
		}
	 
		public void setNeedPricing(String needPricing) {
			this.needPricing = needPricing;
		}
		 
		public void setCatalogId(String catalogId) {
			this.catalogId = catalogId;
		}
		
		public void setCatPartNo(String catPartNo) {
				this.catPartNo = catPartNo;
		}
		public void setInventoryGroupDefault(String inventoryGroupDefault) {
			this.inventoryGroupDefault = inventoryGroupDefault;
		}

		public void setItemId(BigDecimal itemId) {
			this.itemId = itemId;
		}
		
		public void setBranchPlant(String branchPlant) {
			this.branchPlant = branchPlant;
		}
		
		 public void setFacilityId(String facilityId) {
			this.facilityId = facilityId;
		}
		
}
