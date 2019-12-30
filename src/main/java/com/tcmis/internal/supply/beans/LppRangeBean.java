package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class LppRangeBean extends BaseDataBean {

	private String companyId; //COMPANY_ID,
	private String catalogId; //CATALOG_ID,
	private String catPartNo; //CAT_PART_NO,
	private BigDecimal partGroupNo; //PART_GROUP_NO,
	private String priceGroupId; //PRICE_GROUP_ID,
	private BigDecimal baselinePrice; //BASELINE_PRICE,
	private BigDecimal mrNumber; //MR_NUMBER
	private String mrLineItem; //MR_LINE_ITEM
	private BigDecimal itemId; //ITEM_ID
	private String currency; //CURRENCY
	private String inventoryGroup;
	private String opsEntityId;
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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
	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}
	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}
	public BigDecimal getBaselinePrice() {
		return baselinePrice;
	}
	public void setBaselinePrice(BigDecimal baselinePrice) {
		this.baselinePrice = baselinePrice;
	}
	public BigDecimal getMrNumber() {
		return mrNumber;
	}
	public void setMrNumber(BigDecimal mrNumber) {
		this.mrNumber = mrNumber;
	}
	public String getMrLineItem() {
		return mrLineItem;
	}
	public void setMrLineItem(String mrLineItem) {
		this.mrLineItem = mrLineItem;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public String getPriceGroupId() {
		return priceGroupId;
	}
	public void setPriceGroupId(String priceGroupId) {
		this.priceGroupId = priceGroupId;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
		
}
