package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: HubCabinetViewBean <br>
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/

public class ItemCountScanSheetInputBean
    extends BaseDataBean {

  private String hub;
  private String inventoryGroup;
  private String itemType;

  //constructor
  public ItemCountScanSheetInputBean() {
  }

  //setters
  public void setHub(String hub) {
    this.hub = hub;
  }

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }


  //getters
  public String getHub() {
    return hub;
  }

  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public String getItemType() {
    return itemType;
  }
}