package com.tcmis.client.cxml.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: DoManifestBean <br>
 * @version: 1.0, Jul 18, 2006 <br>
 *****************************************************************************/

public class DoManifestBean
    extends BaseDataBean {

  private String doNumber;
  private BigDecimal prNumber;
  private String lineItem;
  private BigDecimal quantity;
  private BigDecimal unitPrice;

  //constructor
  public DoManifestBean() {
  }

  //setters
  public void setDoNumber(String doNumber) {
    this.doNumber = doNumber;
  }

  public void setPrNumber(BigDecimal prNumber) {
    this.prNumber = prNumber;
  }

  public void setLineItem(String lineItem) {
    this.lineItem = lineItem;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  //getters
  public String getDoNumber() {
    return doNumber;
  }

  public BigDecimal getPrNumber() {
    return prNumber;
  }

  public String getLineItem() {
    return lineItem;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }
}