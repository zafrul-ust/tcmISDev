package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewBaeBean <br>
 * @version: 1.0, Feb 28, 2005 <br>
 *****************************************************************************/

public class InvoiceViewBaeBean extends BaseDataBean {

  private BigDecimal invoice;
  private BigDecimal invoiceCount;
  private String accountNumber;
  private BigDecimal invoicePeriod;
  private String invoiceGroup;
  private Date invoiceDate;
  private Date startDate;
  private Date endDate;
  private Collection detail;

  //constructor
  public InvoiceViewBaeBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setInvoiceCount(BigDecimal invoiceCount) {
    this.invoiceCount = invoiceCount;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setInvoiceGroup(String invoiceGroup) {
    this.invoiceGroup = invoiceGroup;
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

  public void setDetail(Collection c) {
    this.detail = c;
  }

  //getters
  public BigDecimal getInvoiceCount() {
    return invoiceCount;
  }

  public BigDecimal getInvoice() {
    return invoice;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public String getInvoiceGroup() {
    return invoiceGroup;
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

  public Collection getDetail() {
    return this.detail;
  }



}