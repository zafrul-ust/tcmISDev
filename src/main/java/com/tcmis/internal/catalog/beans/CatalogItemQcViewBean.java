package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

public class CatalogItemQcViewBean extends BaseDataBean {

	private final String NO_FAULT = "NO_FAULT";
	public static final String VENDOR_ERROR = "VENDOR_ERROR";
	
	private BigDecimal mfgId;
	private String manufacturer;
	private String mfgDesc;
	private String phone;
	private String contact;
	private String email;
	private String notes;
	private String mfgUrl;
	private BigDecimal materialId;
	private String materialDesc;
	private BigDecimal shelfLifeDays;
	private String mfgCatalogId;
	private String mfgTradeName;
	private String shelfLifeBasis;
	private String shelfLifeBasisDisp;
	private BigDecimal minTemp;
	private BigDecimal maxTemp;
	private String tempUnits;
	private String mfgStorageTemp;
	private String grade;
	private String mfgPartNo;
	private String labelColor;
	private BigDecimal componentsPerItem;
	private BigDecimal partSize;
	private String sizeUnit;
	private String pkgStyle;
	private String dimension;
	private BigDecimal netwt;
	private String netwtUnit;
	private String itemVerified;
	private String sampleSize;
	private String comments;
	private String recert;
	private String sizeVaries;
	private String stockingType;
	private BigDecimal partId;
	private String componentReversed;
	
	public BigDecimal getMfgId() {
		return mfgId;
	}
	public void setMfgId(BigDecimal mfgId) {
		this.mfgId = mfgId;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getMfgDesc() {
		return mfgDesc;
	}
	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getMfgUrl() {
		return mfgUrl;
	}
	public void setMfgUrl(String mfgUrl) {
		this.mfgUrl = mfgUrl;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public BigDecimal getShelfLifeDays() {
		return shelfLifeDays;
	}
	public void setShelfLifeDays(BigDecimal shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}
	public String getMfgCatalogId() {
		return mfgCatalogId;
	}
	public void setMfgCatalogId(String mfgCatalogId) {
		this.mfgCatalogId = mfgCatalogId;
	}
	public String getMfgTradeName() {
		return mfgTradeName;
	}
	public void setMfgTradeName(String mfgTradeName) {
		this.mfgTradeName = mfgTradeName;
	}
	public String getShelfLifeBasis() {
		return shelfLifeBasis;
	}
	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}
	public String getShelfLifeBasisDisp() {
		return shelfLifeBasisDisp;
	}
	public void setShelfLifeBasisDisp(String shelfLifeBasisDisp) {
		this.shelfLifeBasisDisp = shelfLifeBasisDisp;
	}
	public BigDecimal getMinTemp() {
		return minTemp;
	}
	public void setMinTemp(BigDecimal minTemp) {
		this.minTemp = minTemp;
	}
	public BigDecimal getMaxTemp() {
		return maxTemp;
	}
	public void setMaxTemp(BigDecimal maxTemp) {
		this.maxTemp = maxTemp;
	}
	public String getTempUnits() {
		return tempUnits;
	}
	public void setTempUnits(String tempUnits) {
		this.tempUnits = tempUnits;
	}
	public String getMfgStorageTemp() {
		return mfgStorageTemp;
	}
	public void setMfgStorageTemp(String mfgStorageTemp) {
		this.mfgStorageTemp = mfgStorageTemp;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getMfgPartNo() {
		return mfgPartNo;
	}
	public void setMfgPartNo(String mfgPartNo) {
		this.mfgPartNo = mfgPartNo;
	}
	public String getLabelColor() {
		return labelColor;
	}
	public void setLabelColor(String labelColor) {
		this.labelColor = labelColor;
	}
	public BigDecimal getComponentsPerItem() {
		return componentsPerItem;
	}
	public void setComponentsPerItem(BigDecimal componentsPerItem) {
		this.componentsPerItem = componentsPerItem;
	}
	public BigDecimal getPartSize() {
		return partSize;
	}
	public void setPartSize(BigDecimal partSize) {
		this.partSize = partSize;
	}
	public String getSizeUnit() {
		return sizeUnit;
	}
	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}
	public String getPkgStyle() {
		return pkgStyle;
	}
	public void setPkgStyle(String pkgStyle) {
		this.pkgStyle = pkgStyle;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public BigDecimal getNetwt() {
		return netwt;
	}
	public void setNetwt(BigDecimal netwt) {
		this.netwt = netwt;
	}
	public String getNetwtUnit() {
		return netwtUnit;
	}
	public void setNetwtUnit(String netwtUnit) {
		this.netwtUnit = netwtUnit;
	}
	public String getItemVerified() {
		return itemVerified;
	}
	public void setItemVerified(String itemVerified) {
		this.itemVerified = itemVerified;
	}
	public String getSampleSize() {
		return sampleSize;
	}
	public void setSampleSize(String sampleSize) {
		this.sampleSize = sampleSize;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getRecert() {
		return recert;
	}
	public void setRecert(String recert) {
		this.recert = recert;
	}
	public String getSizeVaries() {
		return sizeVaries;
	}
	public void setSizeVaries(String sizeVaries) {
		this.sizeVaries = sizeVaries;
	}
	public String getStockingType() {
		return stockingType;
	}
	public void setStockingType(String stockingType) {
		this.stockingType = stockingType;
	}
	public BigDecimal getPartId() {
		return partId;
	}
	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}
	public String getComponentReversed() {
		return componentReversed;
	}
	public void setComponentReversed(String componentReversed) {
		this.componentReversed = componentReversed;
	}
	public boolean isReversed() {
		return ! StringHandler.isBlankString(this.componentReversed);
	}
	public boolean isNoFault() {
		return NO_FAULT.equals(componentReversed);
	}
	public boolean isVendorError() {
		return VENDOR_ERROR.equals(componentReversed);
	}
}
