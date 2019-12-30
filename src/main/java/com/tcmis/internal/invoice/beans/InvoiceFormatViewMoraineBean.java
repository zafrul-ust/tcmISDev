package com.tcmis.internal.invoice.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceFormatViewMoraineBean <br>
 * @version: 1.0, Jun 28, 2007 <br>
 *****************************************************************************/

public class InvoiceFormatViewMoraineBean
    extends BaseDataBean {

  private String invoiceGroup;
  private BigDecimal invoicePeriod;
  private BigDecimal invoice;
  private Date invoiceDate;
  private String poNumber;
  private String category;
  private String qtyOrdered;
  private String qtyShipped;
  private String uom;
  private BigDecimal unitPrice;
  private BigDecimal extendedPrice;

  //constructor
  public InvoiceFormatViewMoraineBean() {
  }

  //setters
  public void setInvoiceGroup(String invoiceGroup) {
    this.invoiceGroup = invoiceGroup;
  }

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

  public void setCategory(String category) {
    this.category = category;
  }

  public void setQtyOrdered(String qtyOrdered) {
    this.qtyOrdered = qtyOrdered;
  }

  public void setQtyShipped(String qtyShipped) {
    this.qtyShipped = qtyShipped;
  }

  public void setUom(String uom) {
    this.uom = uom;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  public void setExtendedPrice(BigDecimal extendedPrice) {
    this.extendedPrice = extendedPrice;
  }

  //getters
  public String getInvoiceGroup() {
    return invoiceGroup;
  }

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

  public String getCategory() {
    return category;
  }

  public String getQtyOrdered() {
    return qtyOrdered;
  }

  public String getQtyShipped() {
    return qtyShipped;
  }

  public String getUom() {
    return uom;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public BigDecimal getExtendedPrice() {
    return extendedPrice;
  }
}