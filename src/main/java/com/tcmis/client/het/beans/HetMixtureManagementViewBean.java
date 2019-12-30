package com.tcmis.client.het.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: HetMixtureManagementViewBean <br>
 * @version: 1.0, Jan 30, 2012 <br>
 *****************************************************************************/


public class HetMixtureManagementViewBean extends BaseDataBean {

	private String catPartNo;
	private String companyId;
	private String customerMsdsNumber;
	private String facilityId;
	private String manufacturer;
	private String materialDesc;
	private BigDecimal materialId;
	private String mfgDesc;
	private BigDecimal mixtureId;
	private String mixtureName;
	
	private String msdsNo;
	private BigDecimal originalMixtureId;
	
	private boolean readOnly;
	private boolean separable;
	private String status;

	//constructor
	public HetMixtureManagementViewBean() {
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	//getters
	public String getCompanyId() {
		return companyId;
	}

	public String getCustomerMsdsNumber() {
		return customerMsdsNumber;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getManufacturer() {
		return manufacturer;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public String getMfgDesc() {
		return mfgDesc;
	}
	public BigDecimal getMixtureId() {
		return mixtureId;
	}
	public String getMixtureName() {
		return mixtureName;
	}
	public String getMsdsNo() {
		return msdsNo;
	}
	public BigDecimal getOriginalMixtureId() {
		return originalMixtureId;
	}
	public String getStatus() {
		return status;
	}
	public boolean isReadOnly() {
		return readOnly;
	}


	public boolean isSeparable() {
		return separable;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCustomerMsdsNumber(String customerMsdsNumber) {
		this.customerMsdsNumber = customerMsdsNumber;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}

	public void setMixtureId(BigDecimal mixtureId) {
		this.mixtureId = mixtureId;
	}

	public void setMixtureName(String mixtureName) {
		this.mixtureName = mixtureName;
	}

	public void setMsdsNo(String msdsNo) {
		this.msdsNo = msdsNo;
	}

	public void setOriginalMixtureId(BigDecimal originalMixtureId) {
		this.originalMixtureId = originalMixtureId;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public void setSeparable(boolean separable) {
		this.separable = separable;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}