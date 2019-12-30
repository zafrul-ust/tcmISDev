package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: GenericLocationViewBean <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class GenericLocationViewBean extends BaseDataBean {

	private String locationId;
	private String countryAbbrev;
	private String stateAbbrev;
	private String addressLine11;
	private String addressLine22;
	private String addressLine33;
	private String city;
	private String zip;
	private String locationDesc;
	private String clientLocationCode;
	private String search;


	//constructor
	public GenericLocationViewBean() {
	}

	//setters
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public void setCountryAbbrev(String countryAbbrev) {
		this.countryAbbrev = countryAbbrev;
	}
	public void setStateAbbrev(String stateAbbrev) {
		this.stateAbbrev = stateAbbrev;
	}
	public void setAddressLine1(String addressLine11) {
		this.addressLine11 = addressLine11;
	}
	public void setAddressLine2(String addressLine22) {
		this.addressLine22 = addressLine22;
	}
	public void setAddressLine3(String addressLine33) {
		this.addressLine33 = addressLine33;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}
	public void setClientLocationCode(String clientLocationCode) {
		this.clientLocationCode = clientLocationCode;
	}
	public void setSearch(String search) {
		this.search = search;
	}


	//getters
	public String getLocationId() {
		return locationId;
	}
	public String getCountryAbbrev() {
		return countryAbbrev;
	}
	public String getStateAbbrev() {
		return stateAbbrev;
	}
	public String getAddressLine1() {
		return addressLine11;
	}
	public String getAddressLine2() {
		return addressLine22;
	}
	public String getAddressLine3() {
		return addressLine33;
	}
	public String getCity() {
		return city;
	}
	public String getZip() {
		return zip;
	}
	public String getLocationDesc() {
		return locationDesc;
	}
	public String getClientLocationCode() {
		return clientLocationCode;
	}
	public String getSearch() {
		return search;
	}
}