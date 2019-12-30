package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class PrintInvoiceTotalsBean extends BaseDataBean {
	 private BigDecimal total = new BigDecimal("0");
	
	 private String currencyId;
	 
	 public PrintInvoiceTotalsBean() {
	 }

	 

		  public BigDecimal getTotal() {
			return total;
		  }
		  
		  public void setTotal(BigDecimal total) {
				if (total != null) {
				 this.total = this.total.add(total);
				}
			 }
			 public void setCurrencyId(String currencyId) {
					this.currencyId = currencyId;
				 }
			 public String getCurrencyId() {
					return currencyId;
				  }

}
