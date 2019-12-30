package com.tcmis.client.het.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: HetMixtureSearchViewBean <br>
 * @version: 1.0, Feb 9, 2012 <br>
 *****************************************************************************/


public class HetMixtureSearchViewBean extends BaseDataBean {

	private String companyId;
	private String facilityId;
	private String catPartNo;
	private String customerMsdsNumber;
	private BigDecimal materialId;
	private String materialDesc;
	private String manufacturer;
	
	public String getCatPartNo() {
		return catPartNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCustomerMsdsNumber() {
		return customerMsdsNumber;
	}

	public void setCustomerMsdsNumber(String customerMsdsNumber) {
		this.customerMsdsNumber = customerMsdsNumber;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	//constructor
	public HetMixtureSearchViewBean() {
	}

}