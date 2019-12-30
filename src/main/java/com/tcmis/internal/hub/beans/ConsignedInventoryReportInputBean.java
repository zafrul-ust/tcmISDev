package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: HubCabinetViewBean <br>
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/

public class ConsignedInventoryReportInputBean
    extends BaseDataBean {

  private String hub;
  private String inventoryGroup;
  private String partNumber;
  private BigDecimal itemId;
  private Date beginDate;
  private Date endDate;

  //constructor
  public ConsignedInventoryReportInputBean() {
  }

  //setters
  public void setHub(String hub) {
    this.hub = hub;
  }

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setPartNumber(String s) {
    this.partNumber = s;
  }

  public void setItemId(BigDecimal b) {
    this.itemId = b;
  }

  public void setBeginDate(Date d) {
    this.beginDate = d;
  }

  public void setEndDate(Date d) {
    this.endDate = d;
  }
  //getters
  public String getHub() {
    return hub;
  }

  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public String getPartNumber() {
    return partNumber;
  }

  public BigDecimal getItemId() {
    return itemId;
  }

  public Date getBeginDate() {
    return beginDate;
  }

   public Date getEndDate() {
    return endDate;
  }
}
