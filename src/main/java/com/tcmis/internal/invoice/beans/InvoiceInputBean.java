package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CurrencyExchangeRateBean <br>
 * @version: 1.0, Nov 24, 2004 <br>
 *****************************************************************************/

public class InvoiceInputBean
    extends BaseDataBean {

  private BigDecimal invoicePeriod;
  private String client;
  private String division;
  private BigDecimal invoiceNumber;
  private String action;
  private String reportType;

  //constructor
  public InvoiceInputBean() {
  }

  public String getAction() {
      return action;
  }

  public void setAction(String action) {
      this.action = action;
  }

  //setters
  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setClient(String client) {
    this.client = client;
  }

  public void setDivision(String division) {
    this.division = division;
  }

  public void setInvoiceNumber(BigDecimal invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

  //getters
  public BigDecimal getInvoicePeriod() {
    return this.invoicePeriod;
  }

  public String getClient() {
    return this.client;
  }

  public String getDivision() {
    return this.division;
  }

  public BigDecimal getInvoiceNumber() {
    return this.invoiceNumber;
  }

	public String getReportType() {
		return reportType;
	}
	
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

  
  
}
