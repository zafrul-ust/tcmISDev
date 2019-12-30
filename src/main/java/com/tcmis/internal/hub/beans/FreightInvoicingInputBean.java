package com.tcmis.internal.hub.beans;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.StringHandler;


public class FreightInvoicingInputBean extends BaseInputBean {

	private String searchField;
	private String searchMode;
	private String searchArgument;
	
	private String[] freightInvoiceStatus;
	
	//inputs to reprocess invoice lines
	private String reprocessLoadId;
	private String reprocessCarrier;
	private String reprocessCurrencyId;

	//constructor
	public FreightInvoicingInputBean() {
	}
	
	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
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
	
	public boolean isSearchByLoadId() {
		if("loadId".equals(getSearchField()))
			return true;
		
		return false;
	}

	public boolean isSearchByInvoiceNumber() {
		if("invoiceNumber".equals(getSearchField()))
			return true;
		
		return false;
	}
	
	public boolean isSearchByOrderNumber() {
		if("orderNumber".equals(getSearchField()))
			return true;
		
		return false;
	}
	
	public boolean isSearchEquals() {
		if("is".equals(getSearchMode()))
			return true;
		
		return false;
	}
	
	public boolean isSearchLike() {
		if("contains".equals(getSearchMode()))
			return true;
		
		return false;
	}

	public String[] getFreightInvoiceStatus() {
		return freightInvoiceStatus;
	}

	public void setFreightInvoiceStatus(String[] freightInvoiceStatus) {
		this.freightInvoiceStatus = freightInvoiceStatus;
	}

	public String getReprocessLoadId() {
		return reprocessLoadId;
	}

	public void setReprocessLoadId(String reprocessLoadId) {
		this.reprocessLoadId = reprocessLoadId;
	}

	public String getReprocessCarrier() {
		return reprocessCarrier;
	}

	public void setReprocessCarrier(String reprocessCarrier) {
		this.reprocessCarrier = reprocessCarrier;
	}

	public String getReprocessCurrencyId() {
		return reprocessCurrencyId;
	}

	public void setReprocessCurrencyId(String reprocessCurrencyId) {
		this.reprocessCurrencyId = reprocessCurrencyId;
	}
}