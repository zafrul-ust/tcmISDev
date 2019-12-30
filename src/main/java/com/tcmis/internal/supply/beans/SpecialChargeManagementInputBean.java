package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.BeanHandler;

public class SpecialChargeManagementInputBean extends BaseInputBean {
	private Date fromDate;
	private Date toDate;
	private String searchField;
	private String searchMode;
	private String searchArgument;

	//constructor
	public SpecialChargeManagementInputBean(ActionForm form, Locale locale) throws Exception {
        try {
            BeanHandler.copyAttributes(form, this, locale);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

	@Override
	public void setHiddenFormFields() {
		addHiddenFormField("fromDate");
		addHiddenFormField("toDate");
		addHiddenFormField("searchField");
		addHiddenFormField("searchMode");
		addHiddenFormField("searchArgument");
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchMode() {
		return searchMode;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	public String getSearchArgument() {
		return searchArgument;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}
	
	public boolean isChangeStatus() {
		return this.uAction != null && this.uAction.equalsIgnoreCase("changeStatus");
	}
}