package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.client.common.beans.WorkAreaSearchTemplateInputBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: HubCabinetViewBean <br>
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/

public class CabinetLabelInputBean extends WorkAreaSearchTemplateInputBean {

	private String application;
	private BigDecimal cabinetId;
	private String[] cabinetIdArray;
	private String facilityId;
	private String[] myCheckboxArray;
	private String preferredWarehouse;
	private String size;
	private String sortBy;

	//constructor
	public CabinetLabelInputBean() {
	}

	public CabinetLabelInputBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	public String getApplication() {
		if(getReportingEntityId() != null && getReportingEntityId().length() > 0)
			return getReportingEntityId();
		
		return application;
	}

	public BigDecimal getCabinetId() {
		return cabinetId;
	}

	public String[] getCabinetIdArray() {
		if(getApplicationId() != null && getApplicationId().length() > 0)
			return getApplicationId().split("\\|");
		
		return this.cabinetIdArray;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String[] getMyCheckboxArray() {
		return this.myCheckboxArray;
	}

	public String getPreferredWarehouse() {
		return preferredWarehouse;
	}

	public String getSize() {
		return size;
	}

	public String getSortBy() {
		return sortBy;
	}

	public boolean hasWarehouse() {
		return !StringHandler.isBlankString(this.preferredWarehouse);
	}

	public boolean hasFacility() {
		return !StringHandler.isBlankString(this.facilityId);
	}

	public boolean hasApplication() {
		return !StringHandler.isBlankString(this.application) || !StringHandler.isBlankString(getReportingEntityId());
	}

	public boolean hasCabinet() {
		return (this.cabinetIdArray != null && this.cabinetIdArray.length > 0) || (getApplicationId() != null && getApplicationId().length() > 0);
	}

	public boolean isSearchBin() {
		return "searchBin".equals(getuAction());
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setCabinetId(BigDecimal cabinetId) {
		this.cabinetId = cabinetId;
	}

	public void setCabinetIdArray(String[] b) {
		this.cabinetIdArray = b;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	@Override
	public void setHiddenFormFields() {
		addHiddenFormField("facilityId");
	}

	public void setMyCheckboxArray(String[] s) {
		this.myCheckboxArray = s;
	}

	public void setPreferredWarehouse(String preferredWarehouse) {
		this.preferredWarehouse = preferredWarehouse;
	}

	public void setSize(String s) {
		this.size = s;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
}