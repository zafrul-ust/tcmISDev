package com.tcmis.internal.distribution.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: Customer.billToCustomerTaxExemptionBean <br>
 * @version: 1.0, Sep 2, 2009 <br>
 *****************************************************************************/


public class BillToCustomerTaxExemptionBean extends BaseDataBean {

	
	private static final long serialVersionUID = 8842637347194630705L;
	private BigDecimal customerId;
	private String stateAbbrev;
	private String taxExemptionCertificate;
	private String countryAbbrev;
// Larry Note: where is my indefinite code in BeanHandler??	
	private String expirationDateStr;
	private Date expirationDate;
	private String action;
	


	//constructor
	public BillToCustomerTaxExemptionBean() {
	}

	//setters
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setStateAbbrev(String stateAbbrev) {
		this.stateAbbrev = stateAbbrev;
	}
	public void setTaxExemptionCertificate(String taxExemptionCertificate) {
		this.taxExemptionCertificate = taxExemptionCertificate;
	}
	public void setCountryAbbrev(String countryAbbrev) {
		this.countryAbbrev = countryAbbrev;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}


	//getters
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public String getStateAbbrev() {
		return stateAbbrev;
	}
	public String getTaxExemptionCertificate() {
		return taxExemptionCertificate;
	}
	public String getCountryAbbrev() {
		return countryAbbrev;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}
	
	public String getExpirationDateStr() {
		return expirationDateStr;
	}

	public void setExpirationDateStr(String expirationDateStr) {
		this.expirationDateStr = expirationDateStr;
	}



}