package com.tcmis.client.waste.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: WasteItemConvertViewBean <br>
 * @version: 1.0, Dec 19, 2006 <br>
 *****************************************************************************/


public class WasteItemConvertViewBean extends BaseDataBean {

	private BigDecimal fromWasteItemId;
	private String fromFacilityId;
	private String fromVendorProfileId;
	private String fromVendorId;
	private String fromCompanyName;
	private String fromWasteDescription;
	private String fromPackaging;
	private String fromWasteCategoryId;
	private String fromWasteTypeId;
	private String fromLabPack;
	private String fromCompanyId;
	private BigDecimal toWasteItemId;
	private String toFacilityId;
	private String toVendorProfileId;
	private String toVendorId;
	private String toCompanyName;
	private String toDescription;
	private String toPackaging;
	private String toWasteCategoryId;
	private String toWasteTypeId;
	private String toLabPack;
	private String toCompanyId;


	//constructor
	public WasteItemConvertViewBean() {
	}

	//setters
	public void setFromWasteItemId(BigDecimal fromWasteItemId) {
		this.fromWasteItemId = fromWasteItemId;
	}
	public void setFromFacilityId(String fromFacilityId) {
		this.fromFacilityId = fromFacilityId;
	}
	public void setFromVendorProfileId(String fromVendorProfileId) {
		this.fromVendorProfileId = fromVendorProfileId;
	}
	public void setFromVendorId(String fromVendorId) {
		this.fromVendorId = fromVendorId;
	}
	public void setFromCompanyName(String fromCompanyName) {
		this.fromCompanyName = fromCompanyName;
	}
	public void setFromWasteDescription(String fromWasteDescription) {
		this.fromWasteDescription = fromWasteDescription;
	}
	public void setFromPackaging(String fromPackaging) {
		this.fromPackaging = fromPackaging;
	}
	public void setFromWasteCategoryId(String fromWasteCategoryId) {
		this.fromWasteCategoryId = fromWasteCategoryId;
	}
	public void setFromWasteTypeId(String fromWasteTypeId) {
		this.fromWasteTypeId = fromWasteTypeId;
	}
	public void setFromLabPack(String fromLabPack) {
		this.fromLabPack = fromLabPack;
	}
	public void setFromCompanyId(String fromCompanyId) {
		this.fromCompanyId = fromCompanyId;
	}
	public void setToWasteItemId(BigDecimal toWasteItemId) {
		this.toWasteItemId = toWasteItemId;
	}
	public void setToFacilityId(String toFacilityId) {
		this.toFacilityId = toFacilityId;
	}
	public void setToVendorProfileId(String toVendorProfileId) {
		this.toVendorProfileId = toVendorProfileId;
	}
	public void setToVendorId(String toVendorId) {
		this.toVendorId = toVendorId;
	}
	public void setToCompanyName(String toCompanyName) {
		this.toCompanyName = toCompanyName;
	}
	public void setToDescription(String toDescription) {
		this.toDescription = toDescription;
	}
	public void setToPackaging(String toPackaging) {
		this.toPackaging = toPackaging;
	}
	public void setToWasteCategoryId(String toWasteCategoryId) {
		this.toWasteCategoryId = toWasteCategoryId;
	}
	public void setToWasteTypeId(String toWasteTypeId) {
		this.toWasteTypeId = toWasteTypeId;
	}
	public void setToLabPack(String toLabPack) {
		this.toLabPack = toLabPack;
	}
	public void setToCompanyId(String toCompanyId) {
		this.toCompanyId = toCompanyId;
	}


	//getters
	public BigDecimal getFromWasteItemId() {
		return fromWasteItemId;
	}
	public String getFromFacilityId() {
		return fromFacilityId;
	}
	public String getFromVendorProfileId() {
		return fromVendorProfileId;
	}
	public String getFromVendorId() {
		return fromVendorId;
	}
	public String getFromCompanyName() {
		return fromCompanyName;
	}
	public String getFromWasteDescription() {
		return fromWasteDescription;
	}
	public String getFromPackaging() {
		return fromPackaging;
	}
	public String getFromWasteCategoryId() {
		return fromWasteCategoryId;
	}
	public String getFromWasteTypeId() {
		return fromWasteTypeId;
	}
	public String getFromLabPack() {
		return fromLabPack;
	}
	public String getFromCompanyId() {
		return fromCompanyId;
	}
	public BigDecimal getToWasteItemId() {
		return toWasteItemId;
	}
	public String getToFacilityId() {
		return toFacilityId;
	}
	public String getToVendorProfileId() {
		return toVendorProfileId;
	}
	public String getToVendorId() {
		return toVendorId;
	}
	public String getToCompanyName() {
		return toCompanyName;
	}
	public String getToDescription() {
		return toDescription;
	}
	public String getToPackaging() {
		return toPackaging;
	}
	public String getToWasteCategoryId() {
		return toWasteCategoryId;
	}
	public String getToWasteTypeId() {
		return toWasteTypeId;
	}
	public String getToLabPack() {
		return toLabPack;
	}
	public String getToCompanyId() {
		return toCompanyId;
	}
}