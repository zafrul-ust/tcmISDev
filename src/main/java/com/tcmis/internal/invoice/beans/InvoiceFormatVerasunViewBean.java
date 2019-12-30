package com.tcmis.internal.invoice.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceFormatVerasunViewBean <br>
 * @version: 1.0, Jun 11, 2007 <br>
 *****************************************************************************/

public class InvoiceFormatVerasunViewBean
    extends BaseDataBean {

  private BigDecimal invoicePeriod;
  private BigDecimal invoice;
  private BigDecimal invoiceAmount;
  private Date invoiceDate;
  private String accountNumber;
  private BigDecimal materialCost;
  private BigDecimal wasteCost;
  private BigDecimal gasCost;
  private BigDecimal cylinderRentalCost;
  private BigDecimal serviceFee;
  private BigDecimal salesTax;
  private BigDecimal totalDue;
  private Date maxDateDelivered;
  private String poNumber;

  private String facilityId;
  private String partDescription;

  //constructor
  public InvoiceFormatVerasunViewBean() {
  }

  //setters
  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setInvoiceAmount(BigDecimal invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setMaterialCost(BigDecimal materialCost) {
    this.materialCost = materialCost;
  }

  public void setWasteCost(BigDecimal wasteCost) {
    this.wasteCost = wasteCost;
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

  public void setSalesTax(BigDecimal salesTax) {
    this.salesTax = salesTax;
  }

  public void setTotalDue(BigDecimal totalDue) {
    this.totalDue = totalDue;
  }

  public void setMaxDateDelivered(Date maxDateDelivered) {
    this.maxDateDelivered = maxDateDelivered;
  }

  public void setPoNumber(String s) {
    this.poNumber = s;
  }

  //getters
  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public BigDecimal getInvoice() {
    return invoice;
  }

  public BigDecimal getInvoiceAmount() {
    return invoiceAmount;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public BigDecimal getMaterialCost() {
    return materialCost;
  }

  public BigDecimal getWasteCost() {
    return wasteCost;
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

  public BigDecimal getSalesTax() {
    return salesTax;
  }

  public BigDecimal getTotalDue() {
    return totalDue;
  }

  public Date getMaxDateDelivered() {
    return this.maxDateDelivered;
  }

  public void setPartDescription(String s) {
    this.partDescription = s;
  }
  public String getPartDescription() {
    return partDescription;
  }
  public void setFacilityId(String s) {
    this.facilityId = s;
  }
  public String getFacilityId() {
    return facilityId;
  }
  public String getPoNumber() {
    return poNumber;
  }
}