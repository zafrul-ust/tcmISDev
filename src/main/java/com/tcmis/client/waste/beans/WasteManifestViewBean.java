package com.tcmis.client.waste.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: WasteManifestViewBean <br>
 * @version: 1.0, Mar 21, 2007 <br>
 *****************************************************************************/


public class WasteManifestViewBean extends BaseDataBean {

	private BigDecimal shipmentId;
	private String manifestId;
	private BigDecimal orderNo;
	private String facilityId;
	private String storageLocationId;
	private String vendorId;
	private String vendorName;
	private Date orderSumbmitDate;
	private Date actualShipDate;
	private String tsdf;
	private String storageLocationEpaId;
	private String locationGroup;
	private String shipToSearchString;
	private String companyId;


	//constructor
	public WasteManifestViewBean() {
	}

	//setters
	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}
	public void setManifestId(String manifestId) {
		this.manifestId = manifestId;
	}
	public void setOrderNo(BigDecimal orderNo) {
		this.orderNo = orderNo;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setStorageLocationId(String storageLocationId) {
		this.storageLocationId = storageLocationId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public void setOrderSumbmitDate(Date orderSumbmitDate) {
		this.orderSumbmitDate = orderSumbmitDate;
	}
	public void setActualShipDate(Date actualShipDate) {
		this.actualShipDate = actualShipDate;
	}
	public void setTsdf(String tsdf) {
		this.tsdf = tsdf;
	}
	public void setStorageLocationEpaId(String storageLocationEpaId) {
		this.storageLocationEpaId = storageLocationEpaId;
	}
	public void setLocationGroup(String locationGroup) {
		this.locationGroup = locationGroup;
	}
	public void setShipToSearchString(String shipToSearchString) {
		this.shipToSearchString = shipToSearchString;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	//getters
	public BigDecimal getShipmentId() {
		return shipmentId;
	}
	public String getManifestId() {
		return manifestId;
	}
	public BigDecimal getOrderNo() {
		return orderNo;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getStorageLocationId() {
		return storageLocationId;
	}
	public String getVendorId() {
		return vendorId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public Date getOrderSumbmitDate() {
		return orderSumbmitDate;
	}
	public Date getActualShipDate() {
		return actualShipDate;
	}
	public String getTsdf() {
		return tsdf;
	}
	public String getStorageLocationEpaId() {
		return storageLocationEpaId;
	}
	public String getLocationGroup() {
		return locationGroup;
	}
	public String getShipToSearchString() {
		return shipToSearchString;
	}
	public String getCompanyId() {
		return companyId;
	}
}