package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewMillerDetailBean <br>
 * @version: 1.0, Mar 17, 2005 <br>
 *****************************************************************************/

public class InvoiceViewMillerDetailBean
    extends BaseDataBean {

  private BigDecimal invoice;
  private BigDecimal invoiceLine;
  private String poNumber;
  private String catPartNo;
  private BigDecimal quantityDelivered;
  private Date dateDelivered;
  private BigDecimal quantityOrdered;
  private BigDecimal totalAmount;
  private BigDecimal extPrice;
  private BigDecimal additionalCharges;
  private BigDecimal serviceFee;
  private String partDescription;
  private Date invoiceDate;
  private BigDecimal invoiceUnitPrice;
  private String packaging;
  private BigDecimal originalInvoice;
  private Collection addChargeColl;


  //constructor
  public InvoiceViewMillerDetailBean() {
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

  public void setQuantityDelivered(BigDecimal quantityDelivered) {
    this.quantityDelivered = quantityDelivered;
  }

  public void setDateDelivered(Date dateDelivered) {
    this.dateDelivered = dateDelivered;
  }

  public void setQuantityOrdered(BigDecimal quantityOrdered) {
    this.quantityOrdered = quantityOrdered;
  }

  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }

  public void setExtPrice(BigDecimal extPrice) {
    this.extPrice = extPrice;
  }

  public void setAdditionalCharges(BigDecimal additionalCharges) {
    this.additionalCharges = additionalCharges;
  }

  public void setServiceFee(BigDecimal serviceFee) {
    this.serviceFee = serviceFee;
  }

  public void setPartDescription(String partDescription) {
    this.partDescription = partDescription;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public void setInvoiceUnitPrice(BigDecimal invoiceUnitPrice) {
    this.invoiceUnitPrice = invoiceUnitPrice;
  }

  public void setPackaging(String packaging) {
    this.packaging = packaging;
  }

  public void setOriginalInvoice(BigDecimal originalInvoice) {
    this.originalInvoice = originalInvoice;
  }

  public void setAddChargeColl(Collection c) {
    this.addChargeColl = c;
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

  public BigDecimal getQuantityDelivered() {
    return quantityDelivered;
  }

  public Date getDateDelivered() {
    return dateDelivered;
  }

  public BigDecimal getQuantityOrdered() {
    return quantityOrdered;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public BigDecimal getExtPrice() {
    return extPrice;
  }

  public BigDecimal getAdditionalCharges() {
    return additionalCharges;
  }

  public BigDecimal getServiceFee() {
    return serviceFee;
  }

  public String getPartDescription() {
    return partDescription;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public BigDecimal getInvoiceUnitPrice() {
    return invoiceUnitPrice;
  }

  public String getPackaging() {
    return packaging;
  }

  public BigDecimal getOriginalInvoice() {
    return originalInvoice;
  }

  public Collection getAddChargeColl() {
    return this.addChargeColl;
  }
}