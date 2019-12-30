package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class SalesOrderTotalsBean extends BaseDataBean {
	 private BigDecimal openedExtPrice = new BigDecimal("0");
	 private BigDecimal extPrice = new BigDecimal("0");
	 private String currencyId;
	 
	 public SalesOrderTotalsBean() {
	 }

	 public BigDecimal getOpenedExtPrice() {
			return openedExtPrice;
		  }

		  public BigDecimal getExtPrice() {
			return extPrice;
		  }
		  
		  public void setOpenedExtPrice(BigDecimal openedExtPrice) {
				if (openedExtPrice != null) {
				 this.openedExtPrice = this.openedExtPrice.add(openedExtPrice);
				}
			 }

			 public void setExtPrice(BigDecimal extPrice) {
				if (extPrice != null) {
				 this.extPrice = this.extPrice.add(extPrice);
				}
			 }
			 public void setCurrencyId(String currencyId) {
					this.currencyId = currencyId;
				 }
			 public String getCurrencyId() {
					return currencyId;
				  }

}
