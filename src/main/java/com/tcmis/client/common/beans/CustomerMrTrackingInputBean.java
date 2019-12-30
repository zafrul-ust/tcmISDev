package com.tcmis.client.common.beans;

import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.StringHandler;

public class CustomerMrTrackingInputBean extends BaseInputBean {

	private String mrslist;

	public CustomerMrTrackingInputBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub

	}

	public String getMrslist() {
		return mrslist;
	}

	public boolean hasPrNumbers() {
		return !StringHandler.isBlankString(mrslist);
	}

	public String[] getPrNumbers() {
		return hasPrNumbers() ? mrslist.split(",") : new String[0];
	}

	public void setMrslist(String mrslist) {
		this.mrslist = mrslist;
	}


}
