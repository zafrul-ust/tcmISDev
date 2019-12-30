package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerReturnFeeBean <br>
 * @version: 1.0, Jul 22, 2009 <br>
 *****************************************************************************/


public class CustomerReturnFeeBean extends BaseDataBean {

	
	private static final long serialVersionUID = -4529266144877949962L;
	private BigDecimal customerRmaId;
	private BigDecimal itemId;
	private BigDecimal returnPrice;
	private String currencyId;
	private String feeDescription;


	//constructor
	public CustomerReturnFeeBean() {
	}


	/**
	 * @return the customerRmaId
	 */
	public BigDecimal getCustomerRmaId() {
		return customerRmaId;
	}


	/**
	 * @param customerRmaId the customerRmaId to set
	 */
	public void setCustomerRmaId(BigDecimal customerRmaId) {
		this.customerRmaId = customerRmaId;
	}


	/**
	 * @return the itemId
	 */
	public BigDecimal getItemId() {
		return itemId;
	}


	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}


	/**
	 * @return the returnPrice
	 */
	public BigDecimal getReturnPrice() {
		return returnPrice;
	}


	/**
	 * @param returnPrice the returnPrice to set
	 */
	public void setReturnPrice(BigDecimal returnPrice) {
		this.returnPrice = returnPrice;
	}


	/**
	 * @return the currencyId
	 */
	public String getCurrencyId() {
		return currencyId;
	}


	/**
	 * @param currencyId the currencyId to set
	 */
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}


	/**
	 * @return the feeDescription
	 */
	public String getFeeDescription() {
		return feeDescription;
	}


	/**
	 * @param feeDescription the feeDescription to set
	 */
	public void setFeeDescription(String feeDescription) {
		this.feeDescription = feeDescription;
	}

	
}