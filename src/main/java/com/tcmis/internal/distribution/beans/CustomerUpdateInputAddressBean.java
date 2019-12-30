package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.framework.HubBaseInputBean;


/******************************************************************************
 * CLASSNAME: CustomerUnappliedCashViewBean <br>
 * @version: 1.0, Oct 23, 2009 <br>
 *****************************************************************************/


public class CustomerUpdateInputAddressBean extends HubBaseInputBean {

	

	private String addressLine1Display;
	private String addressLine2Display;
	private String addressLine3Display;
	private String addressLine4Display;
	private String addressLine5Display;
	private String city;
	private String zip;
	private String countryAbbrev;
	private String stateAbbrev;
	private String billToCompanyId;
	private String locationId;


	//constructor
	public CustomerUpdateInputAddressBean(ActionForm inputForm) {
		super(inputForm);
	}	
	public CustomerUpdateInputAddressBean() {
	}	
	public void setAddressLine1Display(String addressLine1Display) {
		this.addressLine1Display = addressLine1Display;
	}
	public void setAddressLine2Display(String addressLine2Display) {
		this.addressLine2Display = addressLine2Display;
	}
	public void setAddressLine3Display(String addressLine3Display) {
		this.addressLine3Display = addressLine3Display;
	}
	public void setAddressLine4Display(String addressLine4Display) {
		this.addressLine4Display = addressLine4Display;
	}
	public void setAddressLine5Display(String addressLine5Display) {
		this.addressLine5Display = addressLine5Display;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setCountryAbbrev(String countryAbbrev) {
		this.countryAbbrev = countryAbbrev;
	}
	public void setStateAbbrev(String stateAbbrev) {
		this.stateAbbrev = stateAbbrev;
	}
	public void setBillToCompanyId(String billToCompanyId) {
		this.billToCompanyId = billToCompanyId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	
	public String getLocationId() {
		return locationId;
	}
	public String getBillToCompanyId() {
		return billToCompanyId;
	}
	public String getAddressLine1Display() {
		return addressLine1Display;
	}
	public String getAddressLine2Display() {
		return addressLine2Display;
	}
	public String getAddressLine3Display() {
		return addressLine3Display;
	}
	public String getAddressLine4Display() {
		return addressLine4Display;
	}
	public String getAddressLine5Display() {
		return addressLine5Display;
	}
	public String getCity() {
		return city;
	}
	public String getZip() {
		return zip;
	}
	public String getCountryAbbrev() {
		return countryAbbrev;
	}
	public String getStateAbbrev() {
		return stateAbbrev;
	}
	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
		
	}

}