package com.tcmis.client.openCustomer.beans;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.StringHandler;

public class OrderMsdsInputBean extends BaseInputBean {
	private BigDecimal customerId;
	private String searchArgument;
	private BigDecimal monthsBack;

	public OrderMsdsInputBean (ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub

	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public String getSearchArgument() {
		return StringHandler.isBlankString(searchArgument) ? "" : searchArgument;
	}

	public BigDecimal getMonthsBack() {
		return monthsBack;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public void setSearchArgument(String searchText) {
		this.searchArgument = searchText;
	}

	public void setMonthsBack(BigDecimal monthsBack) {
		this.monthsBack = monthsBack;
	}

	public boolean hasMonthsBack() {
		return monthsBack != null;
	}
}
