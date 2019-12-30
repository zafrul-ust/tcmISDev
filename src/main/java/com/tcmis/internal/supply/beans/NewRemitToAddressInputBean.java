package com.tcmis.internal.supply.beans;

import java.util.*;

import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SupplierAddRequestBean <br>
 * @version: 1.0, Jun 24, 2009 <br>
 *****************************************************************************/


public class NewRemitToAddressInputBean extends BaseDataBean {

//	private BigDecimal supplierRequestId;
	private String supplierName;
	private String supplier;
	private String sapVendorCode;
	private String remitToCountryAbbrev;
	private String remitToStateAbbrev;
	private String remitToAddressLine1;
	private String remitToAddressLine2;
	private String remitToAddressLine3;
	private String remitToCity;
	private String remitToZip;
	private String remitToZipPlus;

//	private String requestorName;

	//constructor
	public NewRemitToAddressInputBean() {
	}

	public String getRemitToAddressLine1() {
		return remitToAddressLine1;
	}

	public void setRemitToAddressLine1(String remitToAddressLine1) {
		this.remitToAddressLine1 = remitToAddressLine1;
	}

	public String getRemitToAddressLine2() {
		return remitToAddressLine2;
	}

	public void setRemitToAddressLine2(String remitToAddressLine2) {
		this.remitToAddressLine2 = remitToAddressLine2;
	}

	public String getRemitToAddressLine3() {
		return remitToAddressLine3;
	}

	public void setRemitToAddressLine3(String remitToAddressLine3) {
		this.remitToAddressLine3 = remitToAddressLine3;
	}

	public String getRemitToCity() {
		return remitToCity;
	}

	public void setRemitToCity(String remitToCity) {
		this.remitToCity = remitToCity;
	}

	public String getRemitToCountryAbbrev() {
		return remitToCountryAbbrev;
	}

	public void setRemitToCountryAbbrev(String remitToCountryAbbrev) {
		this.remitToCountryAbbrev = remitToCountryAbbrev;
	}

	public String getRemitToStateAbbrev() {
		return remitToStateAbbrev;
	}

	public void setRemitToStateAbbrev(String remitToStateAbbrev) {
		this.remitToStateAbbrev = remitToStateAbbrev;
	}

	public String getRemitToZip() {
		return remitToZip;
	}

	public void setRemitToZip(String remitToZip) {
		this.remitToZip = remitToZip;
	}

	public String getRemitToZipPlus() {
		return remitToZipPlus;
	}

	public void setRemitToZipPlus(String remitToZipPlus) {
		this.remitToZipPlus = remitToZipPlus;
	}

	public String getSapVendorCode() {
		return sapVendorCode;
	}

	public void setSapVendorCode(String sapVendorCode) {
		this.sapVendorCode = sapVendorCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}



}