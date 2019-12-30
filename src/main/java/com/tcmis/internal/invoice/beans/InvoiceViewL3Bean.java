package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewL3Bean <br>
 * @version: 1.0, May 26, 2005 <br>
 *****************************************************************************/

public class InvoiceViewL3Bean
    extends BaseDataBean {

  private BigDecimal invoice;
  private BigDecimal invoiceCount;
  private BigDecimal invoiceNumber;
  private String accountNumber;
  private BigDecimal invoicePeriod;
  private Date startDate;
  private Date endDate;
  private Date invoiceDate;
  private Collection detail;

  //constructor
  public InvoiceViewL3Bean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setInvoiceCount(BigDecimal invoiceCount) {
    this.invoiceCount = invoiceCount;
  }

  public void setInvoiceNumber(BigDecimal invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setDetail(Collection c) {
    this.detail = c;
  }

  public void setInvoiceDate(Date date) {
    this.invoiceDate = date;
  }

  //getters
  public BigDecimal getInvoice() {
    return invoice;
  }

  public BigDecimal getInvoiceCount() {
    return invoiceCount;
  }

  public BigDecimal getInvoiceNumber() {
    return invoiceNumber;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public Collection getDetail() {
    return detail;
  }

}