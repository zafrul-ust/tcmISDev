package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ItemInventoryHeaderViewBean <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class ItemInventoryHeaderViewBean extends BaseDataBean {

	private String branchPlant;
	private String preferredWarehouse;
	private String plantId;
	private String bldgId;
	private String storageArea;
	private String inventoryGroup;
	private String inventoryGroupName;
	private String collection;
	private String companyId;
	private String facilityName;
	private String hubName;
	private String locationId;
	private String locationDesc;
	private String addressLine11;
	private String addressLine22;
	private String addressLine33;
	private String city;
	private String stateAbbrev;
	private String zip;
	private String countryAbbrev;
	private String attention;
	private String facilityId;


	//constructor
	public ItemInventoryHeaderViewBean() {
	}

	//setters
	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}
	public void setPreferredWarehouse(String preferredWarehouse) {
		this.preferredWarehouse = preferredWarehouse;
	}
	public void setPlantId(String plantId) {
		this.plantId = plantId;
	}
	public void setBldgId(String bldgId) {
		this.bldgId = bldgId;
	}
	public void setStorageArea(String storageArea) {
		this.storageArea = storageArea;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
	public void setCollection(String collection) {
		this.collection = collection;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
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
	public void setStateAbbrev(String stateAbbrev) {
		this.stateAbbrev = stateAbbrev;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public void setCountryAbbrev(String countryAbbrev) {
		this.countryAbbrev = countryAbbrev;
	}
	public void setAttention(String attention) {
		this.attention = attention;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}


	//getters
	public String getBranchPlant() {
		return branchPlant;
	}
	public String getPreferredWarehouse() {
		return preferredWarehouse;
	}
	public String getPlantId() {
		return plantId;
	}
	public String getBldgId() {
		return bldgId;
	}
	public String getStorageArea() {
		return storageArea;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public String getCollection() {
		return collection;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public String getHubName() {
		return hubName;
	}
	public String getLocationId() {
		return locationId;
	}
	public String getLocationDesc() {
		return locationDesc;
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
	public String getStateAbbrev() {
		return stateAbbrev;
	}
	public String getZip() {
		return zip;
	}
	public String getCountryAbbrev() {
		return countryAbbrev;
	}
	public String getAttention() {
		return attention;
	}
	public String getFacilityId() {
		return facilityId;
	}
}