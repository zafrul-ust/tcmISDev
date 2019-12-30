package com.tcmis.internal.catalog.beans;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;

import radian.tcmis.common.util.StringHandler;

public class HmirsMgmtInputBean extends BaseInputBean {

	private Date loadDateFrom;
	private Date loadDateTo;
	private String searchWhat;
	private String searchType;
	private String searchText;
	private String searchWhatDesc;
	private String searchTypeDesc;
	private boolean displayOnlyUnmappedNsns;
	
	public HmirsMgmtInputBean() {
		super();
	}
	
	public HmirsMgmtInputBean(ActionForm form) {
		super(form);
	}
	
	public HmirsMgmtInputBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}
	
	public HmirsMgmtInputBean(ActionForm inputForm, Locale locale, String dateFormat) {
		super(inputForm, locale, dateFormat);
	}
	
	public Date getLoadDateFrom() {
		return loadDateFrom;
	}

	public void setLoadDateFrom(Date loadDateFrom) {
		this.loadDateFrom = loadDateFrom;
	}

	public Date getLoadDateTo() {
		return loadDateTo;
	}

	public void setLoadDateTo(Date loadDateTo) {
		this.loadDateTo = loadDateTo;
	}

	public String getSearchWhat() {
		return searchWhat;
	}

	public void setSearchWhat(String searchWhat) {
		this.searchWhat = searchWhat;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public boolean isDisplayOnlyUnmappedNsns() {
		return displayOnlyUnmappedNsns;
	}

	public void setDisplayOnlyUnmappedNsns(boolean displayOnlyUnmappedNsns) {
		this.displayOnlyUnmappedNsns = displayOnlyUnmappedNsns;
	}
	
	public boolean isNsnSearch() {
		return "nsn".equals(searchWhat);
	}
	
	public boolean isItemSearch() {
		return "itemId".equals(searchWhat);
	}
	
	public boolean isMfgNameSearch() {
		return "mfgName".equals(searchWhat);
	}
	
	public boolean hasSearchText() {
		return ! StringHandler.isBlankString(searchText);
	}
	
	public boolean hasLoadDateFrom() {
		return loadDateFrom != null;
	}
	
	public boolean hasLoadDateTo() {
		return loadDateTo != null;
	}

	public String getSearchWhatDesc() {
		return searchWhatDesc;
	}

	public void setSearchWhatDesc(String searchWhatDesc) {
		this.searchWhatDesc = searchWhatDesc;
	}

	public String getSearchTypeDesc() {
		return searchTypeDesc;
	}

	public void setSearchTypeDesc(String searchTypeDesc) {
		this.searchTypeDesc = searchTypeDesc;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
		
	}
}
