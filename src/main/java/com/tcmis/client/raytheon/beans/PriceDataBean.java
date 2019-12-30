package com.tcmis.client.raytheon.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: PriceDataBean <br>
 * @version: 1.0, Jun 29, 2005 <br>
 *****************************************************************************/

public class PriceDataBean
    extends BaseDataBean {

  private java.lang.String unitPriceValue;
  private java.lang.String currencyCoded;
  private java.lang.String quantityValue;
  private java.lang.String uomCoded;

  //constructor
  public PriceDataBean() {
  }

  //setters
  public void setUnitPriceValue(java.lang.String unitPriceValue) {
    this.unitPriceValue = unitPriceValue;
  }

  public void setCurrencyCoded(java.lang.String currencyCoded) {
    this.currencyCoded = currencyCoded;
  }

  public void setQuantityValue(java.lang.String quantityValue) {
    this.quantityValue = quantityValue;
  }

  public void setUomCoded(java.lang.String uomCoded) {
    this.uomCoded = uomCoded;
  }

  //getters
  public java.lang.String getUnitPriceValue() {
    return unitPriceValue;
  }

  public java.lang.String getCurrencyCoded() {
    return currencyCoded;
  }

  public java.lang.String getQuantityValue() {
    return quantityValue;
  }

  public java.lang.String getUomCoded() {
    return uomCoded;
  }
}