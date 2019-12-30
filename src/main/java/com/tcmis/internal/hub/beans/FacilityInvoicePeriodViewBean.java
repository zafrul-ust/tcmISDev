package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FacilityInvoicePeriodViewBean <br>
 * @version: 1.0, Feb 3, 2005 <br>
 *****************************************************************************/


public class FacilityInvoicePeriodViewBean extends BaseDataBean {

	private String inventoryGroup;
	private String hub;
	private String companyId;
	private String invoiceGroup;
	private String facilityId;
	private BigDecimal invoicePeriod;
	private Date endDate;


	//constructor
	public FacilityInvoicePeriodViewBean() {
	}

	//setters
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setInvoiceGroup(String invoiceGroup) {
		this.invoiceGroup = invoiceGroup;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setInvoicePeriod(BigDecimal invoicePeriod) {
		this.invoicePeriod = invoicePeriod;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	//getters
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getHub() {
		return hub;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getInvoiceGroup() {
		return invoiceGroup;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public BigDecimal getInvoicePeriod() {
		return invoicePeriod;
	}
	public Date getEndDate() {
		return endDate;
	}
}