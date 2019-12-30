package com.tcmis.internal.distribution.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: BillToCustomerTaxExemptVwBean <br>
 * @version: 1.0, Sep 2, 2009 <br>
 *****************************************************************************/


public class BillToCustomerTaxExemptVwBean extends BaseDataBean {


	private static final long serialVersionUID = -7137404692764618442L;
	private BigDecimal customerId;
	private String stateAbbrev;
	private String state;
	private String countryAbbrev;
	private String country;
	private String taxExemptionCertificate;
	private Date expirationDate;


	//constructor
	public BillToCustomerTaxExemptVwBean() {
	}

	//setters
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setStateAbbrev(String stateAbbrev) {
		this.stateAbbrev = stateAbbrev;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setCountryAbbrev(String countryAbbrev) {
		this.countryAbbrev = countryAbbrev;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setTaxExemptionCertificate(String taxExemptionCertificate) {
		this.taxExemptionCertificate = taxExemptionCertificate;
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
	public String getState() {
		return state;
	}
	public String getCountryAbbrev() {
		return countryAbbrev;
	}
	public String getCountry() {
		return country;
	}
	public String getTaxExemptionCertificate() {
		return taxExemptionCertificate;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
}