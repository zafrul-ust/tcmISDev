package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewRacCorrDetailBean <br>
 * @version: 1.0, Mar 18, 2005 <br>
 *****************************************************************************/

public class InvoiceViewRacCorrDetailBean
    extends BaseDataBean {

  private BigDecimal invoice;
  private BigDecimal invoiceLine;
  private String poNumber;
  private String catPartNo;
  private BigDecimal invoiceQuantity;
  private BigDecimal orderedQuantity;
  private Date dateDelivered;
  private BigDecimal price;
  private BigDecimal totalAddCharge;
  private BigDecimal netAmount;
  private BigDecimal invoiceAmount;
  private Date invoiceDate;
  private BigDecimal invoicePeriod;

  //constructor
  public InvoiceViewRacCorrDetailBean() {
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

  public void setInvoiceQuantity(BigDecimal invoiceQuantity) {
    this.invoiceQuantity = invoiceQuantity;
  }

  public void setOrderedQuantity(BigDecimal orderedQuantity) {
    this.orderedQuantity = orderedQuantity;
  }

  public void setDateDelivered(Date dateDelivered) {
    this.dateDelivered = dateDelivered;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public void setTotalAddCharge(BigDecimal totalAddCharge) {
    this.totalAddCharge = totalAddCharge;
  }

  public void setNetAmount(BigDecimal netAmount) {
    this.netAmount = netAmount;
  }

  public void setInvoiceAmount(BigDecimal invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
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

  public BigDecimal getInvoiceQuantity() {
    return invoiceQuantity;
  }

  public BigDecimal getOrderedQuantity() {
    return orderedQuantity;
  }

  public Date getDateDelivered() {
    return dateDelivered;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getTotalAddCharge() {
    return totalAddCharge;
  }

  public BigDecimal getNetAmount() {
    return netAmount;
  }

  public BigDecimal getInvoiceAmount() {
    return invoiceAmount;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }
}