package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewGoletaewBean <br>
 * @version: 1.0, Mar 4, 2005 <br>
 *****************************************************************************/

public class InvoiceViewGoletaewBean
    extends BaseDataBean {

  private BigDecimal invoice;
  private BigDecimal quantity;
  private BigDecimal invoicePeriod;

  //constructor
  public InvoiceViewGoletaewBean() {
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