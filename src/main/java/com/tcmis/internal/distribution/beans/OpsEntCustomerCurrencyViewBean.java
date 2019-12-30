package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class OpsEntCustomerCurrencyViewBean extends BaseDataBean 
{
	private BigDecimal customerId;
	private String customerName;
	private String opsEntityId;
	private String operatingEntityName;
	private BigDecimal enteredBy;
	private String enteredByName;
	private Date enteredDate;
	private String defaultCurrency;
	private String currencyId;
	private String currencyName;
	private String currencyDisplay;
	private String deleteCurrency;
	private String remittanceId;

	public OpsEntCustomerCurrencyViewBean()
	{
	}


	public String getCurrencyDisplay() {
		return currencyDisplay;
	}


	public void setCurrencyDisplay(String currencyDisplay) {
		this.currencyDisplay = currencyDisplay;
	}


	public String getCurrencyId() {
		return currencyId;
	}


	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}


	public String getCurrencyName() {
		return currencyName;
	}


	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}


	public BigDecimal getCustomerId() {
		return customerId;
	}


	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getDefaultCurrency() {
		return defaultCurrency;
	}


	public void setDefaultCurrency(String defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}


	public String getDeleteCurrency() {
		return deleteCurrency;
	}


	public void setDeleteCurrency(String deleteCurrency) {
		this.deleteCurrency = deleteCurrency;
	}


	public BigDecimal getEnteredBy() {
		return enteredBy;
	}


	public void setEnteredBy(BigDecimal enteredBy) {
		this.enteredBy = enteredBy;
	}


	public String getEnteredByName() {
		return enteredByName;
	}


	public void setEnteredByName(String enteredByName) {
		this.enteredByName = enteredByName;
	}


	public String getOperatingEntityName() {
		return operatingEntityName;
	}


	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}


	public String getOpsEntityId() {
		return opsEntityId;
	}


	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}


	public Date getEnteredDate() {
		return enteredDate;
	}


	public void setEnteredDate(Date enteredDate) {
		this.enteredDate = enteredDate;
	}
	
	public String getRemittanceId() {
		return remittanceId;
	}

	public void setRemittanceId(String remittanceId) {
		this.remittanceId = remittanceId;
	}



}