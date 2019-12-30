package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerTaxExemptAddReqBean <br>
 * @version: 1.0, Sep 2, 2009 <br>
 *****************************************************************************/


public class CustomerTaxExemptAddReqBean extends BaseDataBean {

	private BigDecimal customerRequestId;
	private BigDecimal customerId;
	private String billToCompanyId;
	private String stateAbbrev;
	private String taxExemptionCertificate;

	private String changed;
	private String state;
	private String countryAbbrev;
	private String country;
	private Date expirationDate;

	//constructor

	public CustomerTaxExemptAddReqBean() {
	}

	//setters
	public void setCustomerRequestId(BigDecimal customerRequestId) {
		this.customerRequestId = customerRequestId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setBillToCompanyId(String billToCompanyId) {
		this.billToCompanyId = billToCompanyId;
	}
	public void setStateAbbrev(String stateAbbrev) {
		this.stateAbbrev = stateAbbrev;
	}
	public void setTaxExemptionCertificate(String taxExemptionCertificate) {
		this.taxExemptionCertificate = taxExemptionCertificate;
	}


	//getters
	public BigDecimal getCustomerRequestId() {
		return customerRequestId;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public String getBillToCompanyId() {
		return billToCompanyId;
	}
	public String getStateAbbrev() {
		return stateAbbrev;
	}
	public String getTaxExemptionCertificate() {
		return taxExemptionCertificate;
	}
	public String getChanged() {
		return changed;
	}
	public void setChanged(String changed) {
		this.changed = changed;
	}
	public String getCountryAbbrev() {
		return countryAbbrev;
	}
	public void setCountryAbbrev(String countryAbbrev) {
		this.countryAbbrev = countryAbbrev;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

}