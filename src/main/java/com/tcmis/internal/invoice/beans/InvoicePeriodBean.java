package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoicePeriodBean <br>
 * @version: 1.0, Mar 9, 2005 <br>
 *****************************************************************************/

public class InvoicePeriodBean
    extends BaseDataBean {

  private BigDecimal invoicePeriod;
  private String companyId;
  private Date startDate;
  private Date endDate;
  private String invoiceGroup;
  private Date invoiceDate;

  //constructor
  public InvoicePeriodBean() {
  }

  //setters
  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setInvoiceGroup(String invoiceGroup) {
    this.invoiceGroup = invoiceGroup;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  //getters
  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public String getCompanyId() {
    return companyId;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public String getInvoiceGroup() {
    return invoiceGroup;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }
}