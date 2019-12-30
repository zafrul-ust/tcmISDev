package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewSwaCorrBean <br>
 * @version: 1.0, Mar 22, 2005 <br>
 *****************************************************************************/

public class InvoiceViewSwaCorrBean
    extends BaseDataBean {

  private BigDecimal invoice;
  private BigDecimal issueId;
  private BigDecimal quantity;
  private BigDecimal invoicePeriod;

  //constructor
  public InvoiceViewSwaCorrBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setIssueId(BigDecimal issueId) {
    this.issueId = issueId;
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

  public BigDecimal getIssueId() {
    return issueId;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }
}