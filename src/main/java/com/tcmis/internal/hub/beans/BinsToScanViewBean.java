package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: BinsToScanViewBean <br>
 * @version: 1.0, Sep 13, 2006 <br>
 *****************************************************************************/

public class BinsToScanViewBean
    extends BaseDataBean {

  private String branchPlant;
  private String bin;
  private String room;
  private BigDecimal itemId;
  private Date countDatetime;
  private BigDecimal inventoryCost;
  private BigDecimal unitPrice;
  private Date datePicked;

  //constructor
  public BinsToScanViewBean() {
  }

  //setters
  public void setBranchPlant(String branchPlant) {
    this.branchPlant = branchPlant;
  }

  public void setBin(String bin) {
    this.bin = bin;
  }

  public void setRoom(String room) {
    this.room = room;
  }

  public void setItemId(BigDecimal itemId) {
    this.itemId = itemId;
  }

  public void setCountDatetime(Date countDatetime) {
    this.countDatetime = countDatetime;
  }

  public void setInventoryCost(BigDecimal inventoryCost) {
    this.inventoryCost = inventoryCost;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  public void setDatePicked(Date datePicked) {
    this.datePicked = datePicked;
  }

  //getters
  public String getBranchPlant() {
    return branchPlant;
  }

  public String getBin() {
    return bin;
  }

  public String getRoom() {
    return room;
  }

  public BigDecimal getItemId() {
    return itemId;
  }

  public Date getCountDatetime() {
    return countDatetime;
  }

  public BigDecimal getInventoryCost() {
    return inventoryCost;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public Date getDatePicked() {
    return datePicked;
  }
}