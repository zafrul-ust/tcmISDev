package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseInputBean;

public class DeliveryConfirmInputBean extends BaseInputBean {

	private BigDecimal shipmentId;
	private String facilityId;
	private String application;
	private Date shipFromDate;
	private Date shipToDate;
	private String showConfirmed;
	private String searchArgument;
	private String searchField;
	private String searchMode;
	
	
	public String getSearchArgument() {
		return searchArgument;
	}

	public String getSearchField() {
		return searchField;
	}

	public String getSearchMode() {
		return searchMode;
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

	public BigDecimal getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public Date getShipFromDate() {
		return shipFromDate;
	}

	public void setShipFromDate(Date shipFromDate) {
		this.shipFromDate = shipFromDate;
	}

	public Date getShipToDate() {
		return shipToDate;
	}

	public void setShipToDate(Date shipToDate) {
		this.shipToDate = shipToDate;
	}

	public String getShowConfirmed() {
		return showConfirmed;
	}

	public void setShowConfirmed(String showConfirmed) {
		this.showConfirmed = showConfirmed;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	@Override
	public void setHiddenFormFields() {
		addHiddenFormField("facilityId");
		addHiddenFormField("application");
		addHiddenFormField("shipFromDate");
		addHiddenFormField("shipToDate");
		addHiddenFormField("searchField");
		addHiddenFormField("searchMode");
		addHiddenFormField("searchArgument");
		addHiddenFormField("showConfirmed");
		
		removeHiddenFormField("uAction");
		removeHiddenFormField("totalLines");
	}
}
