package com.tcmis.client.seagate.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ShoppingCartItemTypeViewBean <br>
 * @version: 1.0, Jul 21, 2006 <br>
 *****************************************************************************/

public class ShoppingCartItemTypeViewBean
    extends BaseDataBean {

  private BigDecimal prNumber;
  private String lineItem;
  private String itemType;
  private String cancelStatus;
  private String prStatus;
  private String requestLineStatus;
  private BigDecimal entryId;

  //constructor
  public ShoppingCartItemTypeViewBean() {
  }

  //setters
  public void setPrNumber(BigDecimal prNumber) {
    this.prNumber = prNumber;
  }

  public void setLineItem(String lineItem) {
    this.lineItem = lineItem;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }

  public void setCancelStatus(String cancelStatus) {
    this.cancelStatus = cancelStatus;
  }

  public void setPrStatus(String prStatus) {
    this.prStatus = prStatus;
  }

  public void setRequestLineStatus(String requestLineStatus) {
    this.requestLineStatus = requestLineStatus;
  }

  public void setEntryId(BigDecimal entryId) {
    this.entryId = entryId;
  }

  //getters
  public BigDecimal getPrNumber() {
    return prNumber;
  }

  public String getLineItem() {
    return lineItem;
  }

  public String getItemType() {
    return itemType;
  }

  public String getCancelStatus() {
    return cancelStatus;
  }

  public String getPrStatus() {
    return prStatus;
  }

  public String getRequestLineStatus() {
    return requestLineStatus;
  }

  public BigDecimal getEntryId() {
    return entryId;
  }
}