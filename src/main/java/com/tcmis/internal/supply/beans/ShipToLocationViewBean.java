package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ShipToLocationViewBean <br>
 * @version: 1.0, Aug 28, 2007 <br>
 *****************************************************************************/


public class ShipToLocationViewBean extends BaseDataBean {

	private String locationId;
	private String countryAbbrev;
	private String stateAbbrev;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String city;
	private String zip;
	private String locationDesc;
	private String clientLocationCode;
	private String companyId;
	private String facilityId;
	private String search;


	//constructor
	public ShipToLocationViewBean() {
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
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
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
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
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
		return addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public String getAddressLine3() {
		return addressLine3;
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
	public String getCompanyId() {
		return companyId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getSearch() {
		return search;
	}
}