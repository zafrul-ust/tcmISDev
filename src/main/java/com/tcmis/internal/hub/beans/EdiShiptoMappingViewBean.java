package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: EdiShiptoMappingViewBean <br>
 * @version: 1.0, Jul 15, 2005 <br> 
 *****************************************************************************/


public class EdiShiptoMappingViewBean extends BaseDataBean {

	private String companyId;
	private String shiptoPartyId;
	private String haasShiptoCompanyId;
	private String haasShiptoLocationId;
	private String defaultFacilityId;
	private String defaultApplication;
	private String defaultCatalogId;
	private String defaultDeliveryPoint;
	private String shiptoFirstLetter;


	//constructor
	public EdiShiptoMappingViewBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setShiptoPartyId(String shiptoPartyId) {
		this.shiptoPartyId = shiptoPartyId;
	}
	public void setHaasShiptoCompanyId(String haasShiptoCompanyId) {
		this.haasShiptoCompanyId = haasShiptoCompanyId;
	}
	public void setHaasShiptoLocationId(String haasShiptoLocationId) {
		this.haasShiptoLocationId = haasShiptoLocationId;
	}
	public void setDefaultFacilityId(String defaultFacilityId) {
		this.defaultFacilityId = defaultFacilityId;
	}
	public void setDefaultApplication(String defaultApplication) {
		this.defaultApplication = defaultApplication;
	}
	public void setDefaultCatalogId(String defaultCatalogId) {
		this.defaultCatalogId = defaultCatalogId;
	}
	public void setDefaultDeliveryPoint(String defaultDeliveryPoint) {
		this.defaultDeliveryPoint = defaultDeliveryPoint;
	}
	public void setShiptoFirstLetter(String shiptoFirstLetter) {
		this.shiptoFirstLetter = shiptoFirstLetter;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getShiptoPartyId() {
		return shiptoPartyId;
	}
	public String getHaasShiptoCompanyId() {
		return haasShiptoCompanyId;
	}
	public String getHaasShiptoLocationId() {
		return haasShiptoLocationId;
	}
	public String getDefaultFacilityId() {
		return defaultFacilityId;
	}
	public String getDefaultApplication() {
		return defaultApplication;
	}
	public String getDefaultCatalogId() {
		return defaultCatalogId;
	}
	public String getDefaultDeliveryPoint() {
		return defaultDeliveryPoint;
	}
	public String getShiptoFirstLetter() {
		return shiptoFirstLetter;
	}
}