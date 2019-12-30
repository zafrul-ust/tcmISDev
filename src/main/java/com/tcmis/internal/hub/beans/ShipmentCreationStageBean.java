package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ShipmentCreationStageBean <br>
 * @version: 1.0, Feb 19, 2007 <br>
 *****************************************************************************/

public class ShipmentCreationStageBean
    extends BaseDataBean {

  private BigDecimal prNumber;
  private String lineItem;
  private BigDecimal picklistId;
  private BigDecimal shipmentId;
  private String action;
  private Date dateInserted;

  //constructor
  public ShipmentCreationStageBean() {
  }

  //setters
  public void setPrNumber(BigDecimal prNumber) {
    this.prNumber = prNumber;
  }

  public void setLineItem(String lineItem) {
    this.lineItem = lineItem;
  }

  public void setPicklistId(BigDecimal picklistId) {
    this.picklistId = picklistId;
  }

  public void setShipmentId(BigDecimal shipmentId) {
    this.shipmentId = shipmentId;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public void setDateInserted(Date dateInserted) {
    this.dateInserted = dateInserted;
  }

  //getters
  public BigDecimal getPrNumber() {
    return prNumber;
  }

  public String getLineItem() {
    return lineItem;
  }

  public BigDecimal getPicklistId() {
    return picklistId;
  }

  public BigDecimal getShipmentId() {
    return shipmentId;
  }

  public String getAction() {
    return action;
  }

  public Date getDateInserted() {
    return dateInserted;
  }
}