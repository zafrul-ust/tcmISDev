package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewCalBean <br>
 * @version: 1.0, Mar 3, 2005 <br>
 *****************************************************************************/

public class InvoiceViewCalBean
    extends BaseDataBean {

  private BigDecimal invoice;
  private BigDecimal invoiceAmount;
  private String accountNumber;
  private Date invoiceDate;
  private Date startDate;
  private Date endDate;
  private BigDecimal invoicePeriod;

  //constructor
  public InvoiceViewCalBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setInvoiceAmount(BigDecimal invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  //getters
  public BigDecimal getInvoice() {
    return invoice;
  }

  public BigDecimal getInvoiceAmount() {
    return invoiceAmount;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }
}