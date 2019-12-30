package com.tcmis.internal.currency.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CurrencyExchangeRateBean <br>
 * @version: 1.0, Nov 24, 2004 <br>
 *****************************************************************************/

public class CurrencyExchangeRateBean
extends BaseDataBean {

	private String currencyId;
	private BigDecimal exchangeRate;
	private Date startDate;
	private Date endDate;
	private String exchangeRateSource;
	private BigDecimal unit;
	private String currencyDisplay;

	//constructor
	public CurrencyExchangeRateBean() {
	}

	//setters
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	//getters
	public String getCurrencyId() {
		return currencyId;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getExchangeRateSource() {
		return exchangeRateSource;
	}

	public void setExchangeRateSource(String exchangeRateSource) {
		this.exchangeRateSource = exchangeRateSource;
	}
	public BigDecimal getUnit() {
		return unit;
	}

	public void setUnit(BigDecimal unit) {
		this.unit = unit;
	}

	public String getCurrencyDisplay() {
		return currencyDisplay;
	}

	public void setCurrencyDisplay(String currencyDisplay) {
		this.currencyDisplay = currencyDisplay;
	}

}