package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerOpenInvoiceViewBean <br>
 * @version: 1.0, Oct 23, 2009 <br>
 *****************************************************************************/


public class CustomerOpenInvoiceViewBean extends BaseDataBean {

	
	private static final long serialVersionUID = -716133486171727223L;
	private BigDecimal customerId;
	private String opsEntityId;
	private String homeCurrencyId;
	private String paidToEntityName;
	private String creditTerms;
	private String invoiceNumber;
	private String referenceNumber;
	private BigDecimal homeCurrencyAmount;
	private Date invoiceDate;
	private Date paymentDueDate;
	private BigDecimal daysLate;


	//constructor
	public CustomerOpenInvoiceViewBean() {
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
	public void setCreditTerms(String creditTerms) {
		this.creditTerms = creditTerms;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public void setHomeCurrencyAmount(BigDecimal homeCurrencyAmount) {
		this.homeCurrencyAmount = homeCurrencyAmount;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public void setPaymentDueDate(Date paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}
	public void setDaysLate(BigDecimal daysLate) {
		this.daysLate = daysLate;
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
	public String getCreditTerms() {
		return creditTerms;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public BigDecimal getHomeCurrencyAmount() {
		return homeCurrencyAmount;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public Date getPaymentDueDate() {
		return paymentDueDate;
	}
	public BigDecimal getDaysLate() {
		return daysLate;
	}
}