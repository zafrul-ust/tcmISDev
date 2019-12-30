package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: IgCountInventoryViewBean <br>
 * @version: 1.0, Jun 12, 2007 <br>
 *****************************************************************************/

public class IgCountInventoryViewBean
    extends BaseDataBean {

  private String companyId;
  private String inventoryGroup;
  private String inventoryGroupCollection;
  private String tankName;
  private String catPartNo;
  private BigDecimal reorderPoint;
  private BigDecimal reorderQuantity;
  private BigDecimal lowAlarm;
  private BigDecimal highAlarm;
  private BigDecimal capacity;
  private String countUom;
  private String status;
  private BigDecimal inventory;
  private String inventoryUom;
  private BigDecimal itemId;
  private Date lastCountDate;
  private Date nextDeliveryDate;
  private BigDecimal inventoryFraction;
  private BigDecimal lowAlarmFraction;
  private BigDecimal highAlarmFraction;
  private BigDecimal tankDivisions;
  private BigDecimal inventoryCountUom;
  private BigDecimal lowAlarmCountUom;
  private BigDecimal highAlarmCountUom;
  private BigDecimal capacityCountUom;

  private String fileName;

  //constructor
  public IgCountInventoryViewBean() {
  }

  //setters
  public void setFileName(String s) {
    this.fileName = s;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setInventoryGroupCollection(String inventoryGroupCollection) {
    this.inventoryGroupCollection = inventoryGroupCollection;
  }

  public void setTankName(String tankName) {
    this.tankName = tankName;
  }

  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
  }

  public void setReorderPoint(BigDecimal reorderPoint) {
    this.reorderPoint = reorderPoint;
  }

  public void setReorderQuantity(BigDecimal reorderQuantity) {
    this.reorderQuantity = reorderQuantity;
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

  public void setCountUom(String countUom) {
    this.countUom = countUom;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setInventory(BigDecimal inventory) {
    this.inventory = inventory;
  }

  public void setInventoryUom(String inventoryUom) {
    this.inventoryUom = inventoryUom;
  }

  public void setItemId(BigDecimal itemId) {
    this.itemId = itemId;
  }

  public void setLastCountDate(Date lastCountDate) {
    this.lastCountDate = lastCountDate;
  }

  public void setNextDeliveryDate(Date nextDeliveryDate) {
    this.nextDeliveryDate = nextDeliveryDate;
  }

  public void setInventoryFraction(BigDecimal inventoryFraction) {
    this.inventoryFraction = inventoryFraction;
  }

  public void setLowAlarmFraction(BigDecimal lowAlarmFraction) {
    this.lowAlarmFraction = lowAlarmFraction;
  }

  public void setHighAlarmFraction(BigDecimal highAlarmFraction) {
    this.highAlarmFraction = highAlarmFraction;
  }

  public void setTankDivisions(BigDecimal tankDivisions) {
    this.tankDivisions = tankDivisions;
  }

  public void setInventoryCountUom(BigDecimal inventoryCountUom) {
    this.inventoryCountUom = inventoryCountUom;
  }

  public void setLowAlarmCountUom(BigDecimal lowAlarmCountUom) {
    this.lowAlarmCountUom = lowAlarmCountUom;
  }

  public void setHighAlarmCountUom(BigDecimal highAlarmCountUom) {
    this.highAlarmCountUom = highAlarmCountUom;
  }

  public void setCapacityCountUom(BigDecimal capacityCountUom) {
    this.capacityCountUom = capacityCountUom;
  }

  //getters
  public String getFileName() {
    return this.fileName;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public String getInventoryGroupCollection() {
    return inventoryGroupCollection;
  }

  public String getTankName() {
    return tankName;
  }

  public String getCatPartNo() {
    return catPartNo;
  }

  public BigDecimal getReorderPoint() {
    return reorderPoint;
  }

  public BigDecimal getReorderQuantity() {
    return reorderQuantity;
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

  public String getCountUom() {
    return countUom;
  }

  public String getStatus() {
    return status;
  }

  public BigDecimal getInventory() {
    return inventory;
  }

  public String getInventoryUom() {
    return inventoryUom;
  }

  public BigDecimal getItemId() {
    return itemId;
  }

  public Date getLastCountDate() {
    return lastCountDate;
  }

  public Date getNextDeliveryDate() {
    return nextDeliveryDate;
  }

  public BigDecimal getInventoryFraction() {
    return inventoryFraction;
  }

  public BigDecimal getLowAlarmFraction() {
    return lowAlarmFraction;
  }

  public BigDecimal getHighAlarmFraction() {
    return highAlarmFraction;
  }

  public BigDecimal getTankDivisions() {
    return tankDivisions;
  }

  public BigDecimal getInventoryCountUom() {
    return inventoryCountUom;
  }

  public BigDecimal getLowAlarmCountUom() {
    return lowAlarmCountUom;
  }

  public BigDecimal getHighAlarmCountUom() {
    return highAlarmCountUom;
  }

  public BigDecimal getCapacityCountUom() {
    return capacityCountUom;
  }
}