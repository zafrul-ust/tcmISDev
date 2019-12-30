package com.tcmis.client.raytheon.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: XcblOrderDetailIdentifierBean <br>
 * @version: 1.0, Aug 1, 2005 <br>
 *****************************************************************************/

public class XcblOrderDetailIdentifierBean
    extends BaseDataBean {

  private BigDecimal id;
  private BigDecimal xcblOrderDetailId;
  private String key;
  private String value;

  //constructor
  public XcblOrderDetailIdentifierBean() {
  }

  //setters
  public void setId(BigDecimal id) {
    this.id = id;
  }

  public void setXcblOrderDetailId(BigDecimal xcblOrderDetailId) {
    this.xcblOrderDetailId = xcblOrderDetailId;
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

  public BigDecimal getXcblOrderDetailId() {
    return xcblOrderDetailId;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }
}