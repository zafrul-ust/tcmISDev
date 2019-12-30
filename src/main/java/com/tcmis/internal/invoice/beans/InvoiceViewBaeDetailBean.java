package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewBaeDetailBean <br>
 * @version: 1.0, Feb 28, 2005 <br>
 *****************************************************************************/

public class InvoiceViewBaeDetailBean
    extends BaseDataBean {

  private BigDecimal invoice;
  private BigDecimal invoiceLine;
  private String poNumber;
  private String catPartNo;
  private BigDecimal quantity;
  private BigDecimal netPrice;
  private BigDecimal totalRebate;
  private BigDecimal netAmount;
  private BigDecimal additionalCharge;
  private BigDecimal serviceFee;
  private String mrNumber;
  private java.util.Date dateDelivered;
  private BigDecimal radianPo;
  private BigDecimal invoiceUnitPrice;
  private BigDecimal unitRebate;
  private BigDecimal netLineAmount;
  private String accountNumber;
  private BigDecimal percentSplitCharge;

  //constructor
  public InvoiceViewBaeDetailBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setInvoiceLine(BigDecimal invoiceLine) {
    this.invoiceLine = invoiceLine;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public void setNetPrice(BigDecimal netPrice) {
    this.netPrice = netPrice;
  }

  public void setTotalRebate(BigDecimal totalRebate) {
    this.totalRebate = totalRebate;
  }

  public void setNetAmount(BigDecimal netAmount) {
    this.netAmount = netAmount;
  }

  public void setAdditionalCharge(BigDecimal additionalCharge) {
    this.additionalCharge = additionalCharge;
  }

  public void setServiceFee(BigDecimal serviceFee) {
    this.serviceFee = serviceFee;
  }

  public void setMrNumber(String mrNumber) {
    this.mrNumber = mrNumber;
  }

  public void setDateDelivered(java.util.Date dateDelivered) {
    this.dateDelivered = dateDelivered;
  }

  public void setRadianPo(BigDecimal radianPo) {
    this.radianPo = radianPo;
  }

  public void setInvoiceUnitPrice(BigDecimal invoiceUnitPrice) {
    this.invoiceUnitPrice = invoiceUnitPrice;
  }

  public void setUnitRebate(BigDecimal unitRebate) {
    this.unitRebate = unitRebate;
  }

  public void setNetLineAmount(BigDecimal netLineAmount) {
    this.netLineAmount = netLineAmount;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setPercentSplitCharge(BigDecimal percentSplitCharge) {
    this.percentSplitCharge = percentSplitCharge;
  }

  //getters
  public BigDecimal getInvoice() {
    return invoice;
  }

  public BigDecimal getInvoiceLine() {
    return invoiceLine;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public String getCatPartNo() {
    return catPartNo;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getNetPrice() {
    return netPrice;
  }

  public BigDecimal getTotalRebate() {
    return totalRebate;
  }

  public BigDecimal getNetAmount() {
    return netAmount;
  }

  public BigDecimal getAdditionalCharge() {
    return additionalCharge;
  }

  public BigDecimal getServiceFee() {
    return serviceFee;
  }

  public String getMrNumber() {
    return mrNumber;
  }

  public java.util.Date getDateDelivered() {
    return dateDelivered;
  }


  public BigDecimal getRadianPo() {
    return radianPo;
  }

  public BigDecimal getInvoiceUnitPrice() {
    return invoiceUnitPrice;
  }

  public BigDecimal getUnitRebate() {
    return unitRebate;
  }

  public BigDecimal getNetLineAmount() {
    return netLineAmount;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public BigDecimal getPercentSplitCharge() {
    return percentSplitCharge;
  }

}