package com.tcmis.internal.hub.beans;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.SqlHandler;

public class CMSPricelistInputBean extends BaseInputBean {

	private String	catalogId;
	private String	catPartNos;
	private String	companyId;
	private String	defaultCurrencyId;
	private Date	endDate;
	private String	priceGroupId;
	private String	searchArgument;
	private String	searchField;
	private String	searchMode;
	private boolean	showExpired;
	private Date	startDate;

	public CMSPricelistInputBean() {
		super();
	}

	public CMSPricelistInputBean(ActionForm inputForm) {
		super(inputForm);
	}

	public CMSPricelistInputBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	public Collection<String> getAllCatPartNos() {
		return StringUtils.isBlank(catPartNos) ? Collections.EMPTY_LIST : Arrays.asList(catPartNos.split("||"));
	}

	public String getCatalogId() {
		return this.catalogId;
	}

	public String getCatPartNos() {
		return this.catPartNos;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public String getDefaultCurrencyId() {
		return this.defaultCurrencyId;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public String getPriceGroupId() {
		return this.priceGroupId;
	}

	public String getSearchArgument() {
		return searchArgument;
	}

	public String getSearchField() {
		return searchField;
	}

	public String getSearchMode() {
		return searchMode;
	}

	public String getSearchSQL() {
		String modifiedSearchArg;
		boolean useLike = true;
		if ("contains".equals(searchMode)) {
			modifiedSearchArg = "%" + searchArgument + "%";
		}
		else if ("startsWith".equals(searchMode)) {
			modifiedSearchArg = searchArgument + "%";
		}
		else if ("endsWith".equals(searchMode)) {
			modifiedSearchArg = "%" + searchArgument;
		}
		else {
			useLike = false;
			modifiedSearchArg = searchArgument;
		}
		if ("catPartNo".equals(searchField)) {
			return "cat_part_no" + (useLike ? " LIKE " : " = ") + SqlHandler.delimitString(modifiedSearchArg);
		}
		else {
			// Else Item_ID
			return "exists (select null from catalog_part_item_group cpig where cpig.company_id = CPP.COMPANY_ID AND cpig.catalog_id = cpp.catalog_id"
					+ " AND cpig.cat_part_no = cpp.cat_part_no AND cpig.status = 'A' AND cpig.item_id " + (useLike ? " LIKE " : " = ") + SqlHandler.delimitString(modifiedSearchArg)
					+ " )";
		}
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public boolean hasEndDate() {
		return endDate != null;
	}

	public boolean hasSearchArgument() {
		return StringUtils.isNotBlank(searchArgument);
	}

	public boolean hasStartAndEndDates() {
		return hasStartDate() && hasEndDate();
	}

	public boolean hasStartDate() {
		return startDate != null;
	}

	public boolean isShowExpired() {
		return this.showExpired;
	}

	public boolean isSearchAddPart() {
		return "searchAddPart".equals(getuAction());
	}

	public boolean isShowAddPart() {
		return "showAddPart".equals(getuAction());
	}

	public boolean isShowHistory() {
		return "showHistory".equals(getuAction());
	}

	public boolean isGetCSV() {
		return "getCSV".equals(getuAction());
	}
	
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatPartNos(String catPartNos) {
		this.catPartNos = catPartNos;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setDefaultCurrencyId(String defaultCurrencyId) {
		this.defaultCurrencyId = defaultCurrencyId;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public void setHiddenFormFields() {
		addHiddenFormField("searchArgument");
		addHiddenFormField("searchField");
		addHiddenFormField("searchMode");
		addHiddenFormField("companyId");
		addHiddenFormField("priceGroupId");
		addHiddenFormField("showExpired");
		addHiddenFormField("startDate");
		addHiddenFormField("endDate");
		addHiddenFormField("catalogId");
		addHiddenFormField("defaultCurrencyId");
	}

	public void setPriceGroupId(String priceGroupId) {
		this.priceGroupId = priceGroupId;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	public void setShowExpired(boolean showExpired) {
		this.showExpired = showExpired;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}
