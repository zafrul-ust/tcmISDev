package com.tcmis.client.cxml.beans;

import java.util.Date;
import java.util.HashMap;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CxmlOrderRequestBean <br>
 * @version: 1.0, Jun 9, 2006 <br>
 *****************************************************************************/

public class TaxBean
    extends BaseDataBean {

  private String taxCurrency;
  private String taxDescription;
  private String taxCategory;
  private String taxPercent;
  private String taxAmount;
  private String taxLocation;

  //constructor
  public TaxBean() {
  }

  //setters
  public void setTaxCurrency(String s) {
    this.taxCurrency = s;
  }

  public void setTaxDescription(String s) {
    this.taxDescription = s;
  }

  public void setTaxCategory(String s) {
    this.taxCategory = s;
  }

  public void setTaxPercent(String s) {
    this.taxPercent = s;
  }

  public void setTaxAmount(String s) {
    this.taxAmount = s;
  }

  public void setTaxLocation(String s) {
    this.taxLocation = s;
  }

  //getters
  public String getTaxCurrency() {
    return this.taxCurrency;
  }

  public String getTaxDescription() {
    return this.taxDescription;
  }

  public String getTaxCategory() {
    return this.taxCategory;
  }

  public String getTaxPercent() {
    return this.taxPercent;
  }

  public String getTaxAmount() {
    return this.taxAmount;
  }

  public String getTaxLocation() {
    return this.taxLocation;
  }
}