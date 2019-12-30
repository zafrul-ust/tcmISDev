package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: Ledger.sapCustomerBean <br>
 * @version: 1.0, Jul 23, 2009 <br>
 *****************************************************************************/


public class SapCustomerBean extends BaseDataBean {

	private String sapCustomerCode;
	private String sapAccountGroup;
	private String customerName1;
	private String customerName2;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String region;
	private String zipCode;
	private String country;
	private String poBox;
	private String industryKey;
	private Date lastModified;
	private BigDecimal lastModifiedBy;
	private String importStatus;
	private String importErrorMessage;


	//constructor
	public SapCustomerBean() {
	}

	//setters
	public void setSapCustomerCode(String sapCustomerCode) {
		this.sapCustomerCode = sapCustomerCode;
	}
	public void setSapAccountGroup(String sapAccountGroup) {
		this.sapAccountGroup = sapAccountGroup;
	}
	public void setCustomerName1(String customerName1) {
		this.customerName1 = customerName1;
	}
	public void setCustomerName2(String customerName2) {
		this.customerName2 = customerName2;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setPoBox(String poBox) {
		this.poBox = poBox;
	}
	public void setIndustryKey(String industryKey) {
		this.industryKey = industryKey;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public void setLastModifiedBy(BigDecimal lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}
	public void setImportErrorMessage(String importErrorMessage) {
		this.importErrorMessage = importErrorMessage;
	}


	//getters
	public String getSapCustomerCode() {
		return sapCustomerCode;
	}
	public String getSapAccountGroup() {
		return sapAccountGroup;
	}
	public String getCustomerName1() {
		return customerName1;
	}
	public String getCustomerName2() {
		return customerName2;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public String getCity() {
		return city;
	}
	public String getRegion() {
		return region;
	}
	public String getZipCode() {
		return zipCode;
	}
	public String getCountry() {
		return country;
	}
	public String getPoBox() {
		return poBox;
	}
	public String getIndustryKey() {
		return industryKey;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public BigDecimal getLastModifiedBy() {
		return lastModifiedBy;
	}
	public String getImportStatus() {
		return importStatus;
	}
	public String getImportErrorMessage() {
		return importErrorMessage;
	}

}