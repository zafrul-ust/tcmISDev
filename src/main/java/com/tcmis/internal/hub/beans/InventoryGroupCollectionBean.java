package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InventoryGroupCollectionBean <br>
 * @version: 1.0, Oct 2, 2006 <br>
 *****************************************************************************/

public class InventoryGroupCollectionBean
    extends BaseDataBean {

  private String inventoryGroup;
  private String hub;
  private String inventoryGroupCollection;

  //constructor
  public InventoryGroupCollectionBean() {
  }

  //setters
  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setHub(String hub) {
    this.hub = hub;
  }

  public void setInventoryGroupCollection(String inventoryGroupCollection) {
    this.inventoryGroupCollection = inventoryGroupCollection;
  }

  //getters
  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public String getHub() {
    return hub;
  }

  public String getInventoryGroupCollection() {
    return inventoryGroupCollection;
  }
}