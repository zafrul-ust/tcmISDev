package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: MrReturnChargeViewBean <br>
 * @version: 1.0, Jul 22, 2009 <br>
 *****************************************************************************/


public class MrReturnChargeViewBean extends BaseDataBean {
	
	
	private static final long serialVersionUID = -1932485841009059638L;
	private BigDecimal customerRmaId;
	private String feeItemId;
	private BigDecimal feeReturnPrice;
	private String feeCurrency;
	private String itemDesc;
	private String feeDescription;
	private BigDecimal returnPrice;
	private String currencyId;
	private BigDecimal itemId;
	

	//constructor
	public MrReturnChargeViewBean() {
	}

	//setters
	public void setCustomerRmaId(BigDecimal customerRmaId) {
		this.customerRmaId = customerRmaId;
	}
	public void setFeeItemId(String feeItemId) {
		this.feeItemId = feeItemId;
	}
	public void setFeeReturnPrice(BigDecimal feeReturnPrice) {
		this.feeReturnPrice = feeReturnPrice;
	}
	public void setFeeCurrency(String feeCurrency) {
		this.feeCurrency = feeCurrency;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}


	//getters
	public BigDecimal getCustomerRmaId() {
		return customerRmaId;
	}
	public String getFeeItemId() {
		return feeItemId;
	}
	public BigDecimal getFeeReturnPrice() {
		return feeReturnPrice;
	}
	public String getFeeCurrency() {
		return feeCurrency;
	}
	public String getItemDesc() {
		return itemDesc;
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
}