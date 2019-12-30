package com.tcmis.internal.invoice.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: FssManualInvoicePeriodBean <br>
 * @version: 1.0, Feb 2, 2010 <br>
 *****************************************************************************/

public class FssManualInvoicePeriodBean extends BaseDataBean {

	private BigDecimal invoicePeriod;
	private String status;
	private Date processDate;

  //constructor
  public FssManualInvoicePeriodBean() {
  }

	public BigDecimal getInvoicePeriod() {
		return invoicePeriod;
	}

	public void setInvoicePeriod(BigDecimal invoicePeriod) {
		this.invoicePeriod = invoicePeriod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
}