package com.tcmis.client.report.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: DailyPartReceiptSumViewBean <br>
 * @version: 1.0, Nov 16, 2005 <br>
 *****************************************************************************/

public class DailyPartReceiptSumViewBean
    extends BaseDataBean {

  private Date dailyDate;
  private String companyId;
  private String catalogId;
  private String catPartNo;
  private BigDecimal partGroupNo;
  private BigDecimal reorderPoint;
  private BigDecimal stockingLevel;
  private BigDecimal lowAlarm;
  private BigDecimal highAlarm;
  private BigDecimal capacity;
  private String inventoryGroup;
  private BigDecimal quantity;
  private String packaging;
  private String catalogCompanyId;

  //constructor
  public DailyPartReceiptSumViewBean() {
  }

  //setters
  public void setDailyDate(Date dailyDate) {
    this.dailyDate = dailyDate;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
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

  public void setReorderPoint(BigDecimal reorderPoint) {
    this.reorderPoint = reorderPoint;
  }

  public void setStockingLevel(BigDecimal stockingLevel) {
    this.stockingLevel = stockingLevel;
  }

  public void setLowAlarm(BigDecimal lowAlarm) {
    this.lowAlarm = lowAlarm;
  }

  public void setHighAlarm(BigDecimal highAlarm) {
    this.highAlarm = highAlarm;
  }

  public void setCapacity(BigDecimal capacity) {
    this.capacity = capacity;
  }

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public void setPackaging(String packaging) {
    this.packaging = packaging;
  }

  public void setCatalogCompanyId(String catalogCompanyId) {
	  this.catalogCompanyId = catalogCompanyId;
  }

  //getters
  public Date getDailyDate() {
    return dailyDate;
  }

  public String getCompanyId() {
    return companyId;
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

  public BigDecimal getReorderPoint() {
    return reorderPoint;
  }

  public BigDecimal getStockingLevel() {
    return stockingLevel;
  }

  public BigDecimal getLowAlarm() {
    return lowAlarm;
  }

  public BigDecimal getHighAlarm() {
    return highAlarm;
  }

  public BigDecimal getCapacity() {
    return capacity;
  }

  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public String getPackaging() {
    return packaging;
  }

  public String getCatalogCompanyId() {
	  return catalogCompanyId;
  }
}