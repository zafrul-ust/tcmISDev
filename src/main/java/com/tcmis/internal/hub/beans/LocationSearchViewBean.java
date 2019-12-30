package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: LocationSearchViewBean <br>
 * @version: 1.0, Jul 7, 2009 <br>
 *****************************************************************************/


public class LocationSearchViewBean extends BaseDataBean {
    private String ok;
    private String oldDodaacType;
    private String dodaac;
	private String companyId;
	private String locationId;
	private String hazmatCapable;
	private String dodaacType;
	private String addressLine1Display;
	private String addressLine2Display;
	private String addressLine3Display;
	private String addressLine4Display;
	private String search;
	private String city;
	private String sentToAirgas;
	private String countryAbbrev;
	private String stateAbbrev;

	//constructor
	public LocationSearchViewBean() {
	}

    //setters
    public String getOldDodaacType() {
        return oldDodaacType;
    }

    public void setOldDodaacType(String oldDodaacType) {
        this.oldDodaacType = oldDodaacType;
    }

    public String getOk() {
          return ok;
      }
      public void setOk(String ok) {
          this.ok = ok;
      }

    public void setDodaac(String dodaac) {
		this.dodaac = dodaac;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public void setHazmatCapable(String hazmatCapable) {
		this.hazmatCapable = hazmatCapable;
	}
	public void setDodaacType(String dodaacType) {
		this.dodaacType = dodaacType;
	}
	public void setAddressLine1Display(String addressLine11Display) {
		this.addressLine1Display = addressLine11Display;
	}
	public void setAddressLine2Display(String addressLine22Display) {
		this.addressLine2Display = addressLine22Display;
	}
	public void setAddressLine3Display(String addressLine33Display) {
		this.addressLine3Display = addressLine33Display;
	}
	public void setAddressLine4Display(String addressLine44Display) {
		this.addressLine4Display = addressLine44Display;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setSentToAirgas(String sentToAirgas) {
		this.sentToAirgas = sentToAirgas;
	}

	//getters
	public String getDodaac() {
		return dodaac;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getLocationId() {
		return locationId;
	}
	public String getHazmatCapable() {
		return hazmatCapable;
	}
	public String getDodaacType() {
		return dodaacType;
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
	public String getSearch() {
		return search;
	}
	public String getCity() {
		return city;
	}
  public String getSentToAirgas() {
		return sentToAirgas;
	}

public void setCountryAbbrev(String countryAbbrev) {
	this.countryAbbrev = countryAbbrev;
}

public String getCountryAbbrev() {
	return countryAbbrev;
}

public void setStateAbbrev(String stateAbbrev) {
	this.stateAbbrev = stateAbbrev;
}

public String getStateAbbrev() {
	return stateAbbrev;
}
}