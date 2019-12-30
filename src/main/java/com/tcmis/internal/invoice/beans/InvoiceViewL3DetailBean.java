package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewBaeDetailBean <br>
 * @version: 1.0, Feb 28, 2005 <br>
 *****************************************************************************/

public class InvoiceViewL3DetailBean
    extends BaseDataBean {

  private BigDecimal invoice;
  private BigDecimal invoiceLine;
  private String accountNumber;
  private String catPartNo;
  private BigDecimal quantity;
  private BigDecimal totalPrice;
  private BigDecimal totalRebate;
  private BigDecimal netAmount;
  private BigDecimal totalAddCharge;
  private BigDecimal serviceFee;
  private String mrNumber;
  private java.util.Date dateDelivered;
  private java.sql.Date dateDeliveredSql;
  private BigDecimal radianPo;
  private BigDecimal invoiceUnitPrice;
  private BigDecimal unitRebate;
  private BigDecimal percentSplitCharge;
  private String description;
  private BigDecimal lineCount;

  //constructor
  public InvoiceViewL3DetailBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setInvoiceLine(BigDecimal invoiceLine) {
    this.invoiceLine = invoiceLine;
  }

  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public void setTotalRebate(BigDecimal totalRebate) {
    this.totalRebate = totalRebate;
  }

  public void setNetAmount(BigDecimal netAmount) {
    this.netAmount = netAmount;
  }

  public void setTotalAddCharge(BigDecimal totalAddCharge) {
    this.totalAddCharge = totalAddCharge;
  }

  public void setServiceFee(BigDecimal serviceFee) {
    this.serviceFee = serviceFee;
  }

  public void setMrNumber(String mrNumber) {
    this.mrNumber = mrNumber;
  }

  public void setDateDelivered(java.util.Date dateDelivered) {
    this.dateDelivered = dateDelivered;
    //this.setDateDeliveredSql(new java.sql.Date(dateDelivered.getTime()));
  }

  private void setDateDeliveredSql(java.sql.Date dateDeliveredSql) {
    this.dateDeliveredSql = dateDeliveredSql;
    this.setDateDelivered(new java.util.Date(dateDeliveredSql.getTime()));
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

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setPercentSplitCharge(BigDecimal percentSplitCharge) {
    this.percentSplitCharge = percentSplitCharge;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setLineCount(BigDecimal lineCount) {
    this.lineCount = lineCount;
  }
  //getters
  public BigDecimal getInvoice() {
    return invoice;
  }

  public BigDecimal getInvoiceLine() {
    return invoiceLine;
  }

  public String getCatPartNo() {
    return catPartNo;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public BigDecimal getTotalRebate() {
    return totalRebate;
  }

  public BigDecimal getNetAmount() {
    return netAmount;
  }

  public BigDecimal getTotalAddCharge() {
    return totalAddCharge;
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

  public java.sql.Date getDateDeliveredSql() {
    return dateDeliveredSql;
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



  public String getAccountNumber() {
    return accountNumber;
  }

  public BigDecimal getPercentSplitCharge() {
    return percentSplitCharge;
  }

  public String getDescription() {
    return description;
  }

  public BigDecimal getLineCount() {
    return lineCount;
  }



}
