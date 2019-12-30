package com.tcmis.internal.report.beans;

import com.tcmis.common.framework.BaseDataBean;

public class InvoicingHeader extends BaseDataBean {
	private String	billingEntity;
	private String	companyName;
	private String	currencyId;
	private String	facilityName;
	private String	priceGroup;

	public String getBillingEntity() {
		return billingEntity;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public String getPriceGroup() {
		return priceGroup;
	}

	public void setBillingEntity(String billingEntity) {
		this.billingEntity = billingEntity;
	}

	public void setCompanyName(String companyId) {
		this.companyName = companyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public void setPriceGroup(String priceGroup) {
		this.priceGroup = priceGroup;
	}

}
