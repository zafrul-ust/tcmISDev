package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DailyInventoryItemViewBean <br>
 * @version: 1.0, May 2, 2006 <br>
 *****************************************************************************/


public class DailyInventoryReportInputBean extends BaseDataBean {

	private Date dailyDate;
//	private String dailyDate;
	private String inventoryGroup;
	private BigDecimal itemId;
	private BigDecimal quantity;
	private BigDecimal materialValue;
	private BigDecimal fullValue;
	private String catPartNo;
	private String packaging;
	private String itemDesc;
	private String submitSearch;
  private String buttonCreateExcel;
  private String hub;
	private String sort;
	//constructor
	public DailyInventoryReportInputBean() {
	}

	//setters
	public void setDailyDate(Date dailyDate) {
//	public void setDailyDate(String dailyDate) {
		this.dailyDate = dailyDate;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setMaterialValue(BigDecimal materialValue) {
		this.materialValue = materialValue;
	}
	public void setFullValue(BigDecimal fullValue) {
		this.fullValue = fullValue;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
  public void setSubmitSearch(String submitSearch) {
	 this.submitSearch = submitSearch;
	}
	public void setButtonCreateExcel(String buttonCreateExcel) {
	 this.buttonCreateExcel = buttonCreateExcel;
	}
	public void setHub(String hub) {
	this.hub = hub;
	}
  public void setSort(String sort) {
	 this.sort = sort;
	}

	//getters
	public Date getDailyDate() {
//	public String getDailyDate() {
		return dailyDate;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public BigDecimal getMaterialValue() {
		return materialValue;
	}
	public BigDecimal getFullValue() {
		return fullValue;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getPackaging() {
		return packaging;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getSubmitSearch() {
	 return submitSearch;
	}
	public String getButtonCreateExcel() {
	 return buttonCreateExcel;
	}
	public String getHub() {
	 return hub;
	}
	public String getSort() {
	 return sort;
	}
}