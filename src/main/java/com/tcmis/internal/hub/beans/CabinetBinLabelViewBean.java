package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CabinetBinLabelViewBean <br>
 * @version: 1.0, Sep 20, 2006 <br>
 *****************************************************************************/

public class CabinetBinLabelViewBean
    extends BaseDataBean {

  private BigDecimal cabinetId;
  private String binName;
  private BigDecimal binId;
  private BigDecimal itemId;
  private String catPartNo;
  private String partDescription;
  private BigDecimal numCatParts;
  private String packaging;
  private String materialIdString;
  private String mfgDesc;
  private String qcDoc;
  private String cabinetName;
  private BigDecimal reorderPoint;
  private BigDecimal stockingLevel;
  private String inventoryGroup;

  //constructor
  public CabinetBinLabelViewBean() {
  }

  //setters
  public void setCabinetId(BigDecimal cabinetId) {
    this.cabinetId = cabinetId;
  }

  public void setBinName(String binName) {
    this.binName = binName;
  }

  public void setBinId(BigDecimal binId) {
    this.binId = binId;
  }

  public void setItemId(BigDecimal itemId) {
    this.itemId = itemId;
  }

  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
  }

  public void setPartDescription(String partDescription) {
    this.partDescription = partDescription;
  }

  public void setNumCatParts(BigDecimal numCatParts) {
    this.numCatParts = numCatParts;
  }

  public void setPackaging(String packaging) {
    this.packaging = packaging;
  }

  public void setMaterialIdString(String materialIdString) {
    this.materialIdString = materialIdString;
  }

  public void setMfgDesc(String mfgDesc) {
    this.mfgDesc = mfgDesc;
  }

  public void setQcDoc(String qcDoc) {
    this.qcDoc = qcDoc;
  }

  public void setCabinetName(String cabinetName) {
    this.cabinetName = cabinetName;
  }

  public void setReorderPoint(BigDecimal reorderPoint) {
    this.reorderPoint = reorderPoint;
  }

  public void setStockingLevel(BigDecimal stockingLevel) {
    this.stockingLevel = stockingLevel;
  }

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  //getters
  public BigDecimal getCabinetId() {
    return cabinetId;
  }

  public String getBinName() {
    return binName;
  }

  public BigDecimal getBinId() {
    return binId;
  }

  public BigDecimal getItemId() {
    return itemId;
  }

  public String getCatPartNo() {
    return catPartNo;
  }

  public String getPartDescription() {
    return partDescription;
  }

  public BigDecimal getNumCatParts() {
    return numCatParts;
  }

  public String getPackaging() {
    return packaging;
  }

  public String getMaterialIdString() {
    return materialIdString;
  }

  public String getMfgDesc() {
    return mfgDesc;
  }

  public String getQcDoc() {
    return qcDoc;
  }

  public String getCabinetName() {
    return cabinetName;
  }

  public BigDecimal getReorderPoint() {
    return reorderPoint;
  }

  public BigDecimal getStockingLevel() {
    return stockingLevel;
  }

  public String getInventoryGroup() {
    return inventoryGroup;
  }
}