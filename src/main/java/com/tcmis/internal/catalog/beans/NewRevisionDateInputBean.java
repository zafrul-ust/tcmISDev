package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;

public class NewRevisionDateInputBean extends BaseInputBean {

	private String uAction;
	private String materialId;
	private String mfgId;
	private Date revDate;
	

	public NewRevisionDateInputBean() {
		super();
	}

	public NewRevisionDateInputBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	public NewRevisionDateInputBean(ActionForm inputForm, Locale locale, String dateformat) {
		super(inputForm, locale, dateformat);
	}
	
	public String getuAction() {
		return uAction;
	}

	public String getMaterialId() {
		return materialId;
	}
	
	public String getMfgId() {
		return mfgId;
	}

	public Date getRevDate() {
		return revDate;
	}

	public void setuAction(String uAction) {
		this.uAction = uAction;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	
	public void setMfgId(String mfgId) {
		this.mfgId = mfgId;
	}

	public void setRevDate(Date revDate) {
		this.revDate = revDate;
	}

	@Override
	public void setHiddenFormFields() {
		hiddenFormFieldList.add("uAction");
		hiddenFormFieldList.add("revDate");
		hiddenFormFieldList.add("materialId");
		hiddenFormFieldList.add("mfgId");
	}
}
