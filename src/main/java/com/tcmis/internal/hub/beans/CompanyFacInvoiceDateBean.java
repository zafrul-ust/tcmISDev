package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import com.tcmis.common.framework.BaseDataBean;

public class CompanyFacInvoiceDateBean extends BaseDataBean {
	private String companyId;
	private String companyName;
	private Date endDate;
	private Collection facilityColl;
	private String facilityId;
	private String facilityName;
	private String inventoryGroup;
	private String inventoryGroupName;
	private String invoiceGroup;
	private BigDecimal invoicePeriod;
	private Collection invoicePeriodColl;
	private BigDecimal personnelId;

	// constructor
	public CompanyFacInvoiceDateBean() {
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public Date getEndDate() {
		return endDate;
	}

	public Collection getFacilityColl() {
		return facilityColl;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public String getInvoiceGroup() {
		return invoiceGroup;
	}

	public BigDecimal getInvoicePeriod() {
		return invoicePeriod;
	}

	public Collection getInvoicePeriodColl() {
		return invoicePeriodColl;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setFacilityColl(Collection facilityColl) {
		this.facilityColl = facilityColl;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public void setInvoiceGroup(String invoiceGroup) {
		this.invoiceGroup = invoiceGroup;
	}

	public void setInvoicePeriod(BigDecimal invoicePeriod) {
		this.invoicePeriod = invoicePeriod;
	}

	public void setInvoicePeriodColl(Collection invoicePeriodColl) {
		this.invoicePeriodColl = invoicePeriodColl;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
}