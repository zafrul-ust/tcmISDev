package com.tcmis.internal.invoice.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: DrsInvoiceDetailViewBean <br>
 * @version: 1.0, Jul 10, 2007 <br>
 *****************************************************************************/

public class DrsInvoiceDetailViewBean
    extends BaseDataBean {

  private BigDecimal invoice;
  private Date invoiceDate;
  private BigDecimal invoicePeriod;
  private String accountNumber;
  private BigDecimal materialCost;
  private BigDecimal materialRebate;
  private BigDecimal wasteCost;
  private BigDecimal wasteRebate;
  private BigDecimal gasCost;
  private BigDecimal cylinderRentalCost;
  private BigDecimal serviceFee;
  private BigDecimal totalDue;

  //constructor
  public DrsInvoiceDetailViewBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setMaterialCost(BigDecimal materialCost) {
    this.materialCost = materialCost;
  }

  public void setMaterialRebate(BigDecimal materialRebate) {
    this.materialRebate = materialRebate;
  }

  public void setWasteCost(BigDecimal wasteCost) {
    this.wasteCost = wasteCost;
  }

  public void setWasteRebate(BigDecimal wasteRebate) {
    this.wasteRebate = wasteRebate;
  }

  public void setGasCost(BigDecimal gasCost) {
    this.gasCost = gasCost;
  }

  public void setCylinderRentalCost(BigDecimal cylinderRentalCost) {
    this.cylinderRentalCost = cylinderRentalCost;
  }

  public void setServiceFee(BigDecimal serviceFee) {
    this.serviceFee = serviceFee;
  }

  public void setTotalDue(BigDecimal totalDue) {
    this.totalDue = totalDue;
  }

  //getters
  public BigDecimal getInvoice() {
    return invoice;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public BigDecimal getMaterialCost() {
    return materialCost;
  }

  public BigDecimal getMaterialRebate() {
    return materialRebate;
  }

  public BigDecimal getWasteCost() {
    return wasteCost;
  }

  public BigDecimal getWasteRebate() {
    return wasteRebate;
  }

  public BigDecimal getGasCost() {
    return gasCost;
  }

  public BigDecimal getCylinderRentalCost() {
    return cylinderRentalCost;
  }

  public BigDecimal getServiceFee() {
    return serviceFee;
  }

  public BigDecimal getTotalDue() {
    return totalDue;
  }
}