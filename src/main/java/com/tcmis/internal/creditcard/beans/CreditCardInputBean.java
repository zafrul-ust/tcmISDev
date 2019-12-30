package com.tcmis.internal.creditcard.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: PersonnelBean <br>
 * @version: 1.0, Apr 2, 2004 <br>
 *****************************************************************************/

public class CreditCardInputBean
    extends BaseDataBean {


  private String ccHolderName;
  private String ccName;
  private String ccNumber;
  private BigDecimal ccExpirationYear;
  private BigDecimal ccExpirationMonth;
  private BigDecimal chargeAmount;
  private BigDecimal taxAmount;
  private String client;
  private String poNumber;
  private String invoice;
  private String transactionId;

  //constructor
  public CreditCardInputBean() {
    super();
  }

  //setters
  public void setCcHolderName(String s) {
    this.ccHolderName = s;
  }

  public void setCcName(String s) {
    this.ccName = s;
  }

  public void setCcNumber(String s) {
    this.ccNumber = s;
  }

  public void setCcExpirationYear(BigDecimal b) {
    this.ccExpirationYear = b;
  }

  public void setCcExpirationMonth(BigDecimal b) {
    this.ccExpirationMonth = b;
  }

  public void setChargeAmount(BigDecimal b) {
    this.chargeAmount = b;
  }

  public void setTaxAmount(BigDecimal b) {
    this.taxAmount = b;
  }

  public void setClient(String s) {
    this.client = s;
  }

  public void setPoNumber(String s) {
    this.poNumber = s;
  }

  public void setInvoice(String s) {
    this.invoice = s;
  }

  public void setTransactionId(String s) {
    this.transactionId = s;
  }

  //getters
  public String getCcHolderName() {
    return this.ccHolderName;
  }

  public String getCcName() {
    return this.ccName;
  }

  public String getCcNumber() {
    return this.ccNumber;
  }

  public BigDecimal getCcExpirationYear() {
    return this.ccExpirationYear;
  }

  public BigDecimal getCcExpirationMonth() {
    return this.ccExpirationMonth;
  }

  public BigDecimal getChargeAmount() {
    return this.chargeAmount;
  }

  public BigDecimal getTaxAmount() {
    return this.taxAmount;
  }

  public String getClient() {
    return this.client;
  }

  public String getPoNumber() {
    return this.poNumber;
  }

  public String getInvoice() {
    return this.invoice;
  }

  public String getTransactionId() {
    return this.transactionId;
  }
}