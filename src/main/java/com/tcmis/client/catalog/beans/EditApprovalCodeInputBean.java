package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseInputBean;

public class EditApprovalCodeInputBean extends BaseInputBean {

	private String companyId;
	private String facilityId;
	private String catalogCompanyId;
	private String catalogId;
	private String catPartNo;
	private BigDecimal partGroupNo;
	private String customerMsdsDb;
	private BigDecimal usageSubcategoryId;
	private String customerMsdsOrMixtureNo;
	private Date startDate;
	private Date endDate;
	
	public EditApprovalCodeInputBean() {
		
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}

	public String getCustomerMsdsDb() {
		return customerMsdsDb;
	}

	public void setCustomerMsdsDb(String customerMsdsDb) {
		this.customerMsdsDb = customerMsdsDb;
	}

	public BigDecimal getUsageSubcategoryId() {
		return usageSubcategoryId;
	}

	public void setUsageSubcategoryId(BigDecimal usageSubcategoryId) {
		this.usageSubcategoryId = usageSubcategoryId;
	}

	public String getCustomerMsdsOrMixtureNo() {
		return customerMsdsOrMixtureNo;
	}

	public void setCustomerMsdsOrMixtureNo(String customerMsdsOrMixtureNo) {
		this.customerMsdsOrMixtureNo = customerMsdsOrMixtureNo;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
	}
}
