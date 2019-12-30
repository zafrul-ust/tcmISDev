package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewRacCorrBean <br>
 * @version: 1.0, Mar 18, 2005 <br>
 *****************************************************************************/

public class InvoiceViewRacCorrBean
    extends BaseDataBean {

  private BigDecimal invoice;
  private BigDecimal sum;
  private BigDecimal invoicePeriod;

  //constructor
  public InvoiceViewRacCorrBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setSum(BigDecimal sum) {
    this.sum = sum;
  }

  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  //getters
  public BigDecimal getInvoice() {
    return invoice;
  }

  public BigDecimal getSum() {
    return sum;
  }

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }
}