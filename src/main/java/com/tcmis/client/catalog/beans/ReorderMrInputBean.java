package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.BeanHandler;


public class ReorderMrInputBean extends BaseInputBean {
	private String accountSysId;
	private String facilityId;
	private BigDecimal prNumber;
	private String applicationId;
	private String isFirstLoad;
	
	public ReorderMrInputBean() {}
    
	public ReorderMrInputBean(ActionForm form, Locale locale) throws Exception {
		try {
			BeanHandler.copyAttributes(form, this, locale);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public void setHiddenFormFields() {
		addHiddenFormField("accountSysId");
		addHiddenFormField("facilityId");
		addHiddenFormField("prNumber");
		addHiddenFormField("applicationId");
		addHiddenFormField("isFirstLoad");
	}
	
	public boolean isCheckOut() {
		return "checkOut".equalsIgnoreCase(uAction);
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getAccountSysId() {
		return accountSysId;
	}

	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}

	public String getIsFirstLoad() {
		return isFirstLoad;
	}

	public void setIsFirstLoad(String isFirstLoad) {
		this.isFirstLoad = isFirstLoad;
	}
	
}