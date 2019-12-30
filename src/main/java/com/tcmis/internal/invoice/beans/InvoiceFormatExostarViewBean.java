package com.tcmis.internal.invoice.beans;

import java.math.*;
import java.util.*;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: InvoiceFormatExostarViewBean <br>
 * @version: 1.0, Apr 28, 2006 <br>
 *****************************************************************************/

public class InvoiceFormatExostarViewBean
    extends BaseDataBean {

  private BigDecimal invoicePeriod;
  private BigDecimal invoice;
  private Date invoiceDate;
  private String poNumber;
  private BigDecimal invoiceAmount;
  private String requestorName;
  private BigDecimal prNumber;
  private String lineItem;
  private String catPartNo;
  private Date dateDelivered;
  private BigDecimal quantity;
  private String unitOfSale;
  private BigDecimal lineAmount;
  private BigDecimal freight;
  private String partDescription;
  private BigDecimal unitPrice;

  //constructor
  public InvoiceFormatExostarViewBean() {
  }

  //setters
  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public void setInvoiceAmount(BigDecimal invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
  }

  public void setRequestorName(String requestorName) {
    this.requestorName = requestorName;
  }

  public void setPrNumber(BigDecimal prNumber) {
    this.prNumber = prNumber;
  }

  public void setLineItem(String lineItem) {
    this.lineItem = lineItem;
  }

  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
  }

  public void setDateDelivered(Date dateDelivered) {
    this.dateDelivered = dateDelivered;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public void setUnitOfSale(String unitOfSale) {
    this.unitOfSale = unitOfSale;
  }

  public void setLineAmount(BigDecimal lineAmount) {
    this.lineAmount = lineAmount;
  }

  public void setFreight(BigDecimal freight) {
    this.freight = freight;
  }

  public void setPartDescription(String partDescription) {
    this.partDescription = partDescription;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  //getters
  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public BigDecimal getInvoice() {
    return invoice;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public BigDecimal getInvoiceAmount() {
    return invoiceAmount;
  }

  public String getRequestorName() {
    return requestorName;
  }

  public BigDecimal getPrNumber() {
    return prNumber;
  }

  public String getLineItem() {
    return lineItem;
  }

  public String getCatPartNo() {
    return catPartNo;
  }

  public Date getDateDelivered() {
    return dateDelivered;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public String getUnitOfSale() {
    return unitOfSale;
  }

  public BigDecimal getLineAmount() {
    return lineAmount;
  }

  public BigDecimal getFreight() {
    return freight;
  }

  public String getPartDescription() {
    return partDescription;
  }

  public BigDecimal getUnitPrice() {
    return this.unitPrice;
  }
}