package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ItemBean <br>
 * @version: 1.0, Jun 12, 2009 <br>
 *****************************************************************************/

public class ItemBean
 extends BaseDataBean {

	private BigDecimal itemId;
	private String itemDesc;
	private BigDecimal categoryId;
	private String revisionComments;
	private BigDecimal replacementItem;
	private String stockingType;
	private BigDecimal msrp;
	private String priceUnit;
	private String storageTemp;
	private BigDecimal shelfLifeDays;
	private String shelfLifeBasis;
	private String sampleOnly;
	private String itemType;
	private BigDecimal caseQty;
	private String ctPetroleumTax;
	private String inseparableKit;
	private String itemPurchasingDesc;
	private String imageContent;
	private String mfgLiteratureContent;
	private String harmonizedTradeCode;
	private BigDecimal grossWeightLbs;
	private BigDecimal cubicFeet;
	private BigDecimal estimatedGrossWeightLbs;
	private BigDecimal estimatedCubicFeet;
	private String ok;
	
	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	//constructor
	public ItemBean() {
	}

	//setters
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setCategoryId(BigDecimal categoryId) {
		this.categoryId = categoryId;
	}
	public void setRevisionComments(String revisionComments) {
		this.revisionComments = revisionComments;
	}
	public void setReplacementItem(BigDecimal replacementItem) {
		this.replacementItem = replacementItem;
	}
	public void setStockingType(String stockingType) {
		this.stockingType = stockingType;
	}
	public void setMsrp(BigDecimal msrp) {
		this.msrp = msrp;
	}
	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}
	public void setStorageTemp(String storageTemp) {
		this.storageTemp = storageTemp;
	}
	public void setShelfLifeDays(BigDecimal shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}
	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}
	public void setSampleOnly(String sampleOnly) {
		this.sampleOnly = sampleOnly;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public void setCaseQty(BigDecimal caseQty) {
		this.caseQty = caseQty;
	}
	public void setCtPetroleumTax(String ctPetroleumTax) {
		this.ctPetroleumTax = ctPetroleumTax;
	}
	public void setInseparableKit(String inseparableKit) {
		this.inseparableKit = inseparableKit;
	}
	public void setItemPurchasingDesc(String itemPurchasingDesc) {
		this.itemPurchasingDesc = itemPurchasingDesc;
	}
	public void setImageContent(String imageContent) {
		this.imageContent = imageContent;
	}
	public void setMfgLiteratureContent(String mfgLiteratureContent) {
		this.mfgLiteratureContent = mfgLiteratureContent;
	}
	public void setHarmonizedTradeCode(String harmonizedTradeCode) {
		this.harmonizedTradeCode = harmonizedTradeCode;
	}
	public void setGrossWeightLbs(BigDecimal grossWeightLbs) {
		this.grossWeightLbs = grossWeightLbs;
	}
	public void setCubicFeet(BigDecimal cubicFeet) {
		this.cubicFeet = cubicFeet;
	}
	public void setEstimatedGrossWeightLbs(BigDecimal estimatedGrossWeightLbs) {
		this.estimatedGrossWeightLbs = estimatedGrossWeightLbs;
	}
	public void setEstimatedCubicFeet(BigDecimal estimatedCubicFeet) {
		this.estimatedCubicFeet = estimatedCubicFeet;
	}


	//getters
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public BigDecimal getCategoryId() {
		return categoryId;
	}
	public String getRevisionComments() {
		return revisionComments;
	}
	public BigDecimal getReplacementItem() {
		return replacementItem;
	}
	public String getStockingType() {
		return stockingType;
	}
	public BigDecimal getMsrp() {
		return msrp;
	}
	public String getPriceUnit() {
		return priceUnit;
	}
	public String getStorageTemp() {
		return storageTemp;
	}
	public BigDecimal getShelfLifeDays() {
		return shelfLifeDays;
	}
	public String getShelfLifeBasis() {
		return shelfLifeBasis;
	}
	public String getSampleOnly() {
		return sampleOnly;
	}
	public String getItemType() {
		return itemType;
	}
	public BigDecimal getCaseQty() {
		return caseQty;
	}
	public String getCtPetroleumTax() {
		return ctPetroleumTax;
	}
	public String getInseparableKit() {
		return inseparableKit;
	}
	public String getItemPurchasingDesc() {
		return itemPurchasingDesc;
	}
	public String getImageContent() {
		return imageContent;
	}
	public String getMfgLiteratureContent() {
		return mfgLiteratureContent;
	}
	public String getHarmonizedTradeCode() {
		return harmonizedTradeCode;
	}
	public BigDecimal getGrossWeightLbs() {
		return grossWeightLbs;
	}
	public BigDecimal getCubicFeet() {
		return cubicFeet;
	}
	public BigDecimal getEstimatedGrossWeightLbs() {
		return estimatedGrossWeightLbs;
	}
	public BigDecimal getEstimatedCubicFeet() {
		return estimatedCubicFeet;
	}
}