package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: SurplusInventoryViewBean <br>
 * @version: 1.0, Mar 15, 2007 <br>
 *****************************************************************************/

public class SurplusInventoryViewBean
    extends BaseDataBean {

  private BigDecimal itemId;
  private BigDecimal receiptId;
  private String inventoryGroup;
  private String hub;
  private String mfgLot;
  private String bin;
  private BigDecimal quantityAvailable;
  private String lotStatus;
  private Date expireDate;

  //constructor
  public SurplusInventoryViewBean() {
  }

  //setters
  public void setItemId(BigDecimal itemId) {
    this.itemId = itemId;
  }

  public void setReceiptId(BigDecimal receiptId) {
    this.receiptId = receiptId;
  }

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setHub(String hub) {
    this.hub = hub;
  }

  public void setMfgLot(String mfgLot) {
    this.mfgLot = mfgLot;
  }

  public void setBin(String bin) {
    this.bin = bin;
  }

  public void setQuantityAvailable(BigDecimal quantityAvailable) {
    this.quantityAvailable = quantityAvailable;
  }

  public void setLotStatus(String lotStatus) {
    this.lotStatus = lotStatus;
  }

  public void setExpireDate(Date expireDate) {
    this.expireDate = expireDate;
  }

  //getters
  public BigDecimal getItemId() {
    return itemId;
  }

  public BigDecimal getReceiptId() {
    return receiptId;
  }

  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public String getHub() {
    return hub;
  }

  public String getMfgLot() {
    return mfgLot;
  }

  public String getBin() {
    return bin;
  }

  public BigDecimal getQuantityAvailable() {
    return quantityAvailable;
  }

  public String getLotStatus() {
    return lotStatus;
  }

  public Date getExpireDate() {
    return expireDate;
  }
}