package com.tcmis.internal.catalog.beans;

import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;

public class ItemSequenceGeneratorBean extends BaseInputBean {

	private String uAction;
	private int howMany;
	private String searchLike;
	
	public ItemSequenceGeneratorBean() {
		
	}
	
	public ItemSequenceGeneratorBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}
	
	public String getuAction() {
		return uAction;
	}

	public void setuAction(String uAction) {
		this.uAction = uAction;
	}
	
	public boolean isGenerate() {
		return "generate".equals(uAction);
	}

	public int getHowMany() {
		return howMany;
	}
	
	public void setHowMany(int howMany) {
		this.howMany = howMany;
	}
	
	public String getSearchLike() {
		return searchLike;
	}
	
	public void setSearchLike(String searchLike) {
		this.searchLike = searchLike;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
	}
}
