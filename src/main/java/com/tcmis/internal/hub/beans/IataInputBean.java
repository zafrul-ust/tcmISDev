package com.tcmis.internal.hub.beans;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Jul 8, 2008
 * Time: 10:56:29 AM
 * To change this template use File | Settings | File Templates.
 */

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: IataInputBean <br>
 *****************************************************************************/

public class IataInputBean
    extends BaseDataBean {

  private String hub;
  private String inventoryGroup;
  private String partNumber;
  private BigDecimal itemId;
  private Date beginDate;
  private Date endDate;

  //constructor
  public IataInputBean() {
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