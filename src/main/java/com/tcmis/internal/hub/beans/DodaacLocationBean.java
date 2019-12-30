package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DodaacLocationBean <br>
 * @version: 1.0, Nov 21, 2008 <br>
 *****************************************************************************/


public class DodaacLocationBean extends BaseDataBean {

	private String dodaac;
	private String companyId;
	private String locationId;
	private String hazmatCapable;
	private String dodaacType;


	//constructor
	public DodaacLocationBean() {
	}

	//setters
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
}