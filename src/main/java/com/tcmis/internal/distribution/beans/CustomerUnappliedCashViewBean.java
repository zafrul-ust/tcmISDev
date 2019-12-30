package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerUnappliedCashViewBean <br>
 * @version: 1.0, Oct 23, 2009 <br>
 *****************************************************************************/


public class CustomerUnappliedCashViewBean extends BaseDataBean {

	
	private static final long serialVersionUID = 7373429895667624527L;
	private BigDecimal customerId;
	private String opsEntityId;
	private String homeCurrencyId;
	private String paidToEntityName;
	private BigDecimal homeCurrencyAmount;


	//constructor
	public CustomerUnappliedCashViewBean() {
	}

	//setters
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setHomeCurrencyId(String homeCurrencyId) {
		this.homeCurrencyId = homeCurrencyId;
	}
	public void setPaidToEntityName(String paidToEntityName) {
		this.paidToEntityName = paidToEntityName;
	}
	public void setHomeCurrencyAmount(BigDecimal homeCurrencyAmount) {
		this.homeCurrencyAmount = homeCurrencyAmount;
	}


	//getters
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getHomeCurrencyId() {
		return homeCurrencyId;
	}
	public String getPaidToEntityName() {
		return paidToEntityName;
	}
	public BigDecimal getHomeCurrencyAmount() {
		return homeCurrencyAmount;
	}
}