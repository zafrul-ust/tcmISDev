package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: MaxInvoicePeriodViewBean <br>
 * @version: 1.0, Dec 7, 2005 <br>
 *****************************************************************************/

public class MaxInvoicePeriodViewBean
    extends BaseDataBean {

  private String companyId;
  private String invoiceGroup;
  private BigDecimal maxPeriod;

  //constructor
  public MaxInvoicePeriodViewBean() {
  }

  //setters
  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setInvoiceGroup(String invoiceGroup) {
    this.invoiceGroup = invoiceGroup;
  }

  public void setMaxPeriod(BigDecimal maxPeriod) {
    this.maxPeriod = maxPeriod;
  }

  //getters
  public String getCompanyId() {
    return companyId;
  }

  public String getInvoiceGroup() {
    return invoiceGroup;
  }

  public BigDecimal getMaxPeriod() {
    return maxPeriod;
  }
}