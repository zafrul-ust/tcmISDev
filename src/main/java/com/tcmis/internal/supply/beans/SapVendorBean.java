package com.tcmis.internal.supply.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SapVendorBean <br>
 * @version: 1.0, Jun 25, 2009 <br>
 *****************************************************************************/


public class SapVendorBean extends BaseDataBean {

	private String sapVendorCode;
	private String accountGroup;
	private String vendorName;
	private String vendorName2;
	private String searchTerm;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String region;
	private String zipCode;
	private String country;
	private String poBox;
	private String tax1;
	private String tax2;
	private Date lastModified;
	private BigDecimal lastModifiedBy;
	private String importStatus;
	private String importErrorMessage;

	//constructor
	public SapVendorBean() {
	}

	//setters
	public void setSapVendorCode(String sapVendorCode) {
		this.sapVendorCode = sapVendorCode;
	}
	public void setAccountGroup(String accountGroup) {
		this.accountGroup = accountGroup;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public void setVendorName2(String vendorName2) {
		this.vendorName2 = vendorName2;
	}
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
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
	public void setTax1(String tax1) {
		this.tax1 = tax1;
	}
	public void setTax2(String tax2) {
		this.tax2 = tax2;
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
	public String getSapVendorCode() {
		return sapVendorCode;
	}
	public String getAccountGroup() {
		return accountGroup;
	}
	public String getVendorName() {
		return vendorName;
	}
	public String getVendorName2() {
		return vendorName2;
	}
	public String getSearchTerm() {
		return searchTerm;
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
	public String getTax1() {
		return tax1;
	}
	public String getTax2() {
		return tax2;
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