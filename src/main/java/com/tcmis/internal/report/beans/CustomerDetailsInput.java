package com.tcmis.internal.report.beans;

import java.util.Locale;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import com.tcmis.common.framework.BaseInputBean;

public class CustomerDetailsInput extends BaseInputBean {
	private String	companyId;
	private String	facilityId;

	public CustomerDetailsInput(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	
	public boolean isValid() {
		return StringUtils.isNotBlank(companyId) && StringUtils.isNotBlank(facilityId);
	}
	
	public boolean isGetOperationalHeader () {
		return "getOperationalHeader".equals(getAction());
	}
	
	public boolean isGetcatalogs() {
		return "getCatalogs".equals(getAction());
	}
	
	public boolean isGetInventoryGroups() {
		return "getInventoryGroups".equals(getAction());
	}
	
	public boolean isGetInvoiceHeader () {
		return "getInvoiceHeader".equals(getAction());
	}
	
	public boolean isGetInvoiceGroups () {
		return "getInvoiceGroups".equals(getAction());
	}
	
	public boolean isGetShippingAddresses() {
		return "getShippingAddresses".equals(getAction());
	}
	
	public boolean isGetServiceFees() {
		return "getServiceFees".equals(getAction());
	}
	
	public boolean isGetMarkupRules() {
		return "getMarkupRules".equals(getAction());
	}
	
	public boolean isGetBillingAddresses() {
		return "getBillingAddresses".equals(getAction());
	}

	@Override
	public void setHiddenFormFields() {
	}
}
