package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerContactBean <br>
 * @version: 1.0, Jul 24, 2009 <br>
 *****************************************************************************/


public class CustomerCreditBean extends BaseDataBean {

	private String currencyId;
	private BigDecimal openOrderAmount;
	private BigDecimal openArAmount;
	private BigDecimal creditLimit;
	private BigDecimal availableCredit;
	private BigDecimal greatestOverdueDays;
	private BigDecimal gracePeriodDays;
	private BigDecimal pastDueAmount030Days;
	private BigDecimal pastDueAmount3160Days;
	private BigDecimal pastDueAmount6190Days;
	private BigDecimal pastDueAmount90Days;


	//constructor
	public CustomerCreditBean() {
	}


	public BigDecimal getAvailableCredit() {
		return availableCredit;
	}


	public void setAvailableCredit(BigDecimal availableCredit) {
		this.availableCredit = availableCredit;
	}


	public BigDecimal getCreditLimit() {
		return creditLimit;
	}


	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}


	public String getCurrencyId() {
		return currencyId;
	}


	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}


	public BigDecimal getGracePeriodDays() {
		return gracePeriodDays;
	}


	public void setGracePeriodDays(BigDecimal gracePeriodDays) {
		this.gracePeriodDays = gracePeriodDays;
	}


	public BigDecimal getGreatestOverdueDays() {
		return greatestOverdueDays;
	}


	public void setGreatestOverdueDays(BigDecimal greatestOverdueDays) {
		this.greatestOverdueDays = greatestOverdueDays;
	}


	public BigDecimal getOpenArAmount() {
		return openArAmount;
	}


	public void setOpenArAmount(BigDecimal openArAmount) {
		this.openArAmount = openArAmount;
	}


	public BigDecimal getOpenOrderAmount() {
		return openOrderAmount;
	}


	public void setOpenOrderAmount(BigDecimal openOrderAmount) {
		this.openOrderAmount = openOrderAmount;
	}


	public BigDecimal getPastDueAmount030Days() {
		return pastDueAmount030Days;
	}


	public void setPastDueAmount030Days(BigDecimal pastDueAmount030Days) {
		this.pastDueAmount030Days = pastDueAmount030Days;
	}


	public BigDecimal getPastDueAmount3160Days() {
		return pastDueAmount3160Days;
	}


	public void setPastDueAmount3160Days(BigDecimal pastDueAmount3160Days) {
		this.pastDueAmount3160Days = pastDueAmount3160Days;
	}


	public BigDecimal getPastDueAmount6190Days() {
		return pastDueAmount6190Days;
	}


	public void setPastDueAmount6190Days(BigDecimal pastDueAmount6190Days) {
		this.pastDueAmount6190Days = pastDueAmount6190Days;
	}


	public BigDecimal getPastDueAmount90Days() {
		return pastDueAmount90Days;
	}


	public void setPastDueAmount90Days(BigDecimal pastDueAmount90Days) {
		this.pastDueAmount90Days = pastDueAmount90Days;
	}

}