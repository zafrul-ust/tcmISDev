package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerOpenOrdersViewBean <br>
 * @version: 1.0, Oct 23, 2009 <br>
 *****************************************************************************/


public class CustomerOpenOrdersViewBean extends BaseDataBean {

	
	private static final long serialVersionUID = -5403443257858580623L;
	private BigDecimal customerId;
	private String opsEntityId;
	private String homeCurrencyId;
	private String orderedFromEntityName;
	private String paymentTerms;
	private BigDecimal prNumber;
	private String poNumber;
	private BigDecimal homeCurrencyAmount;
	private Date submittedDate;


	//constructor
	public CustomerOpenOrdersViewBean() {
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
	public void setOrderedFromEntityName(String orderedFromEntityName) {
		this.orderedFromEntityName = orderedFromEntityName;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setHomeCurrencyAmount(BigDecimal homeCurrencyAmount) {
		this.homeCurrencyAmount = homeCurrencyAmount;
	}
	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
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
	public String getOrderedFromEntityName() {
		return orderedFromEntityName;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public BigDecimal getHomeCurrencyAmount() {
		return homeCurrencyAmount;
	}
	public Date getSubmittedDate() {
		return submittedDate;
	}
}