package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: BinsToScanViewBean <br>
 * @version: 1.0, Sep 13, 2006 <br>
 *****************************************************************************/

public class BinsToScanInputBean
    extends BaseDataBean {

  private String branchPlant;
  private String room;
  private BigDecimal itemId;
  private BigDecimal inventoryValueMin;
  private BigDecimal inventoryValueMax;
  private BigDecimal unitCostMin;
  private BigDecimal unitCostMax;
  private BigDecimal receiptDaySpan;
  private BigDecimal binCountDays;

  //constructor
  public BinsToScanInputBean() {
  }

  //setters
  public void setBranchPlant(String branchPlant) {
    this.branchPlant = branchPlant;
  }

  public void setRoom(String room) {
    this.room = room;
  }

  public void setItemId(BigDecimal itemId) {
    this.itemId = itemId;
  }

  public void setInventoryValueMin(BigDecimal b) {
    this.inventoryValueMin = b;
  }

  public void setInventoryValueMax(BigDecimal b) {
    this.inventoryValueMax = b;
  }

  public void setUnitCostMin(BigDecimal b) {
    this.unitCostMin = b;
  }

  public void setUnitCostMax(BigDecimal b) {
    this.unitCostMax = b;
  }

  public void setReceiptDaySpan(BigDecimal b) {
    this.receiptDaySpan = b;
  }

  public void setBinCountDays(BigDecimal b) {
    this.binCountDays = b;
  }

  //getters
  public String getBranchPlant() {
    return branchPlant;
  }

  public String getRoom() {
    return room;
  }

  public BigDecimal getItemId() {
    return itemId;
  }

  public BigDecimal getInventoryValueMin() {
    return inventoryValueMin;
  }

  public BigDecimal getInventoryValueMax() {
    return inventoryValueMax;
  }

  public BigDecimal getUnitCostMin() {
    return unitCostMin;
  }

  public BigDecimal getUnitCostMax() {
    return unitCostMax;
  }

  public BigDecimal getReceiptDaySpan() {
    return receiptDaySpan;
  }

  public BigDecimal getBinCountDays() {
    return binCountDays;
  }
}