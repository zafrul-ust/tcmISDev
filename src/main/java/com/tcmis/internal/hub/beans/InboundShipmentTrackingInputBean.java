package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.HubBaseInputBean;
import com.tcmis.common.util.StringHandler;

public class InboundShipmentTrackingInputBean extends HubBaseInputBean {

	private Date	actualDeliveryFromDate;
	private Date	actualDeliveryToDate;
	private Date	estimatedDeliveryFromDate;
	private Date	estimatedDeliveryToDate;
	private boolean	onlyNotDelivered	= false;
	private String	searchArgument;
	private String	searchField;
	private String	searchMode;

	public InboundShipmentTrackingInputBean() {
		super();
	}

	public InboundShipmentTrackingInputBean(ActionForm inputForm) {
		super(inputForm);
	}

	public InboundShipmentTrackingInputBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	public Date getActualDeliveryFromDate() {
		return actualDeliveryFromDate;
	}

	public Date getActualDeliveryToDate() {
		return actualDeliveryToDate;
	}

	public Date getEstimatedDeliveryFromDate() {
		return estimatedDeliveryFromDate;
	}

	public Date getEstimatedDeliveryToDate() {
		return estimatedDeliveryToDate;
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

	public boolean hasEstimatedDeliveryFromDate() {
		return null != estimatedDeliveryFromDate;
	}

	public boolean hasEstimatedDeliveryToDate() {
		return null != estimatedDeliveryToDate;
	}
	
	public boolean hasActualDeliveryFromDate() {
		return null != actualDeliveryFromDate;
	}

	public boolean hasActualDeliveryToDate() {
		return null != actualDeliveryToDate;
	}

	public boolean hasActualDate() {
		return hasActualDeliveryFromDate() || hasActualDeliveryToDate(); 
	}

	public boolean hasSearchArgument() {
		return !StringHandler.isBlankString(searchArgument);
	}

	public boolean isOnlyNotDelivered() {
		return onlyNotDelivered;
	}

	public void setActualDeliveryFromDate(Date actualDeliveryFromDate) {
		this.actualDeliveryFromDate = actualDeliveryFromDate;
	}

	public void setActualDeliveryToDate(Date actualDeliveryToDate) {
		this.actualDeliveryToDate = actualDeliveryToDate;
	}

	public void setEstimatedDeliveryFromDate(Date estimatedDeliveryFromDate) {
		this.estimatedDeliveryFromDate = estimatedDeliveryFromDate;
	}

	public void setEstimatedDeliveryToDate(Date estimatedDeliveryToDate) {
		this.estimatedDeliveryToDate = estimatedDeliveryToDate;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub

	}

	public void setOnlyNotDelivered(boolean onlyNotDelivered) {
		this.onlyNotDelivered = onlyNotDelivered;
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

}
