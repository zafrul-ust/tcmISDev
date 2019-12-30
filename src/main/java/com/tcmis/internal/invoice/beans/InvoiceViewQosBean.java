package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: QosInvoiceViewBean <br>
 * @version: 1.0, Oct 26, 2005 <br>
 *****************************************************************************/

public class InvoiceViewQosBean
    extends BaseDataBean {

  private BigDecimal invoice;
  private BigDecimal invoiceLine;
  private String poNumber;
  private String catPartNo;
  private String partDescription;
  private BigDecimal quantity;
  private BigDecimal extPrice;
  private BigDecimal netLineAmount;
  private BigDecimal additionalCharges;
  private BigDecimal serviceFee;
  private BigDecimal percentSplitCharge;
  private String mrNumber;
  private Date dateDelivered;
  private BigDecimal invoiceUnitPrice;
  private String accountNumber;
  private BigDecimal salesTax;

  //constructor
  public InvoiceViewQosBean() {
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

  public void setPartDescription(String partDescription) {
    this.partDescription = partDescription;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public void setExtPrice(BigDecimal extPrice) {
    this.extPrice = extPrice;
  }

  public void setNetLineAmount(BigDecimal netLineAmount) {
    this.netLineAmount = netLineAmount;
  }

  public void setAdditionalCharges(BigDecimal additionalCharges) {
    this.additionalCharges = additionalCharges;
  }

  public void setServiceFee(BigDecimal serviceFee) {
    this.serviceFee = serviceFee;
  }

  public void setPercentSplitCharge(BigDecimal percentSplitCharge) {
    this.percentSplitCharge = percentSplitCharge;
  }

  public void setMrNumber(String mrNumber) {
    this.mrNumber = mrNumber;
  }

  public void setDateDelivered(Date dateDelivered) {
    this.dateDelivered = dateDelivered;
  }

  public void setInvoiceUnitPrice(BigDecimal invoiceUnitPrice) {
    this.invoiceUnitPrice = invoiceUnitPrice;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setSalesTax(BigDecimal salesTax) {
    this.salesTax = salesTax;
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

  public String getPartDescription() {
    return partDescription;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getExtPrice() {
    return extPrice;
  }

  public BigDecimal getNetLineAmount() {
    return netLineAmount;
  }

  public BigDecimal getAdditionalCharges() {
    return additionalCharges;
  }

  public BigDecimal getServiceFee() {
    return serviceFee;
  }

  public BigDecimal getPercentSplitCharge() {
    return percentSplitCharge;
  }

  public String getMrNumber() {
    return mrNumber;
  }

  public Date getDateDelivered() {
    return dateDelivered;
  }

  public BigDecimal getInvoiceUnitPrice() {
    return invoiceUnitPrice;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public BigDecimal getSalesTax() {
    return salesTax;
  }
}