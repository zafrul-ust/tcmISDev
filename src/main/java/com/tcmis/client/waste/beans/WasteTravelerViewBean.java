package com.tcmis.client.waste.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: WasteTravelerViewBean <br>
 * @version: 1.0, Feb 12, 2007 <br>
 *****************************************************************************/


public class WasteTravelerViewBean extends BaseDataBean {

	private String facilityId;
	private String generationPoint;
	private String vendorId;
	private String companyName;
	private String vendorProfileId;
	private String managementOptionDesc;
	private String description;
	private String wasteCategoryId;
	private BigDecimal travelerId;
	private String sealDate;
	private BigDecimal containerId;
	private String properShippingName;
	private String hazardClass;
	private String shippingId;
	private String packingGroup;
	private String fromLocation;
	private String toLocation;
	private String dotDescription;
	private String stateWasteCodes;
	private String rcraClassification;
        private String lpadContainerId;
        private String wasteRequestIdLineItem;

	//constructor
	public WasteTravelerViewBean() {
	}

	//setters
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setGenerationPoint(String generationPoint) {
		this.generationPoint = generationPoint;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setVendorProfileId(String vendorProfileId) {
		this.vendorProfileId = vendorProfileId;
	}
	public void setManagementOptionDesc(String managementOptionDesc) {
		this.managementOptionDesc = managementOptionDesc;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setWasteCategoryId(String wasteCategoryId) {
		this.wasteCategoryId = wasteCategoryId;
	}
	public void setTravelerId(BigDecimal travelerId) {
		this.travelerId = travelerId;
	}
	public void setSealDate(String sealDate) {
		this.sealDate = sealDate;
	}
	public void setContainerId(BigDecimal containerId) {
		this.containerId = containerId;
	}
	public void setProperShippingName(String properShippingName) {
		this.properShippingName = properShippingName;
	}
	public void setHazardClass(String hazardClass) {
		this.hazardClass = hazardClass;
	}
	public void setShippingId(String shippingId) {
		this.shippingId = shippingId;
	}
	public void setPackingGroup(String packingGroup) {
		this.packingGroup = packingGroup;
	}
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}
	public void setDotDescription(String dotDescription) {
		this.dotDescription = dotDescription;
	}
	public void setStateWasteCodes(String stateWasteCodes) {
		this.stateWasteCodes = stateWasteCodes;
	}
	public void setRcraClassification(String rcraClassification) {
		this.rcraClassification = rcraClassification;
	}
        public void setLpadContainerId(String lpadContainerId) {
                this.lpadContainerId = lpadContainerId;
        }
        public void setWasteRequestIdLineItem(String wasteRequestIdLineItem) {
                this.wasteRequestIdLineItem = wasteRequestIdLineItem;
        }

	//getters
	public String getFacilityId() {
		return facilityId;
	}
	public String getGenerationPoint() {
		return generationPoint;
	}
	public String getVendorId() {
		return vendorId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getVendorProfileId() {
		return vendorProfileId;
	}
	public String getManagementOptionDesc() {
		return managementOptionDesc;
	}
	public String getDescription() {
		return description;
	}
	public String getWasteCategoryId() {
		return wasteCategoryId;
	}
	public BigDecimal getTravelerId() {
		return travelerId;
	}
	public String getSealDate() {
		return sealDate;
	}
	public BigDecimal getContainerId() {
		return containerId;
	}
	public String getProperShippingName() {
		return properShippingName;
	}
	public String getHazardClass() {
		return hazardClass;
	}
	public String getShippingId() {
		return shippingId;
	}
	public String getPackingGroup() {
		return packingGroup;
	}
	public String getFromLocation() {
		return fromLocation;
	}
	public String getToLocation() {
		return toLocation;
	}
	public String getDotDescription() {
		return dotDescription;
	}
	public String getStateWasteCodes() {
		return stateWasteCodes;
	}
	public String getRcraClassification() {
		return rcraClassification;
	}
        public String getLpadContainerId() {
                return lpadContainerId;
        }
        public String getWasteRequestIdLineItem() {
                return wasteRequestIdLineItem;
        }
}