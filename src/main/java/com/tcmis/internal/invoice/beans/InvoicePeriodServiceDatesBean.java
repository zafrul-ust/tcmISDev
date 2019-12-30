package com.tcmis.internal.invoice.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoicePeriodServiceDatesBean <br>
 * @version: 1.0, May 8, 2006 <br>
 *****************************************************************************/

public class InvoicePeriodServiceDatesBean
    extends BaseDataBean {

  private BigDecimal invoicePeriod;
  private Date startDate;
  private Date endDate;

  //constructor
  public InvoicePeriodServiceDatesBean() {
  }

  //setters
  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  //getters
  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }
}