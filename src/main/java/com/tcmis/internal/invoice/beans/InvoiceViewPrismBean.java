package com.tcmis.internal.invoice.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewPrismBean <br>
 * @version: 1.0, May 16, 2007 <br>
 *****************************************************************************/

public class InvoiceViewPrismBean
    extends BaseDataBean {

  private BigDecimal invoice;
  private BigDecimal quantity;
  private BigDecimal invoicePeriod;

  //constructor
  public InvoiceViewPrismBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  //getters
  public BigDecimal getInvoice() {
    return invoice;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }
}