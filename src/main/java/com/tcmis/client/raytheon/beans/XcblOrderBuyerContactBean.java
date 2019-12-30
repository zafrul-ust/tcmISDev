package com.tcmis.client.raytheon.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: XcblOrderBuyerContactBean <br>
 * @version: 1.0, Aug 2, 2005 <br>
 *****************************************************************************/

public class XcblOrderBuyerContactBean
    extends BaseDataBean {

  private BigDecimal id;
  private BigDecimal xcblOrderId;
  private String key;
  private String value;

  //constructor
  public XcblOrderBuyerContactBean() {
  }

  //setters
  public void setId(BigDecimal id) {
    this.id = id;
  }

  public void setXcblOrderId(BigDecimal xcblOrderId) {
    this.xcblOrderId = xcblOrderId;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setValue(String value) {
    this.value = value;
  }

  //getters
  public BigDecimal getId() {
    return id;
  }

  public BigDecimal getXcblOrderId() {
    return xcblOrderId;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }
}