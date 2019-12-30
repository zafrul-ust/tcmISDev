package com.tcmis.internal.hub.beans;

import com.tcmis.common.framework.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NewItemInputBean extends BaseDataBean {

	private String requestorId;
  private String companyId;
	private String facilityId;
	private String catalogCompanyId;
	private String catalogId;
	private String catalogDesc;
	private String inventoryGroup;
	private String receiptId;
	private String catPartNo;
	private String partDesc;
	private String component;
	private String size;
	private String unit;
	private String pkgStyle;
	private String netWtVol;
	private String netWtVolUnit;
	private String dimension;
	private String materialDesc;
	private String grade;
	private String manufacturer;
	private String mfgPartNo;
	private String netWtRequired;
	private String newChemPackagingChange;
	private String newComponent;  //new;deleted

  //constructor
  public NewItemInputBean() {
  }

//setter
public void setRequestorId(String requestorId) {
	this.requestorId = requestorId;
}
public void setCompanyId(String companyId) {
	this.companyId = companyId;
}

public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
}

public void setCatalogCompanyId(String catalogCompanyId) {
	this.catalogCompanyId = catalogCompanyId;
}

public void setCatalogId(String catalogId) {
	this.catalogId = catalogId;
}

public void setCatalogDesc(String catalogDesc) {
	this.catalogDesc = catalogDesc;
}

public void setInventoryGroup(String inventoryGroup) {
	this.inventoryGroup = inventoryGroup;
}

public void setReceiptId(String receiptId) {
	this.receiptId = receiptId;
}

public void setCatPartNo(String catPartNo) {
	this.catPartNo = catPartNo;
}

public void setPartDesc(String partDesc) {
	this.partDesc = partDesc;
}

public void setComponent(String component) {
	this.component = component;
}

public void setSize(String size) {
	this.size = size;
}

public void setUnit(String unit) {
	this.unit = unit;
}

public void setPkgStyle(String pkgStyle) {
	this.pkgStyle = pkgStyle;
}

public void setNetWtVol(String netWtVol) {
	this.netWtVol = netWtVol;
}

public void setNetWtVolUnit(String netWtVolUnit) {
	this.netWtVolUnit = netWtVolUnit;
}

public void setDimension(String dimension) {
	this.dimension = dimension;
}

public void setMaterialDesc(String materialDesc) {
	this.materialDesc = materialDesc;
}

public void setGrade(String grade) {
	this.grade = grade;
}

public void setManufacturer(String manufacturer) {
	this.manufacturer = manufacturer;
}

public void setMfgPartNo(String mfgPartNo) {
	this.mfgPartNo = mfgPartNo;
}

public void setNetWtRequired(String netWtRequired) {
	this.netWtRequired = netWtRequired;
}

public void setNewChemPackagingChange(String newChemPackagingChange) {
	this.newChemPackagingChange = newChemPackagingChange;
}

public void setNewComponent(String newComponent) {
	this.newComponent = newComponent;
}

//getter
public String getRequestorId() {
	return requestorId;
}
public String getCompanyId() {
	return companyId;
}

public String getFacilityId() {
	return facilityId;
}

public String getCatalogCompanyId() {
	return catalogCompanyId;
}

public String getCatalogId() {
	return catalogId;
}

public String getCatalogDesc() {
	return catalogDesc;
}

public String getInventoryGroup() {
	return inventoryGroup;
}

public String getReceiptId() {
	return receiptId;
}

public String getCatPartNo() {
	return catPartNo;
}

public String getPartDesc() {
	return partDesc;
}

public String getComponent() {
	return component;
}

public String getSize() {
	return size;
}

public String getUnit() {
	return unit;
}

public String getPkgStyle() {
	return pkgStyle;
}

public String getNetWtVol() {
	return netWtVol;
}

public String getNetWtVolUnit() {
	return netWtVolUnit;
}

public String getDimension() {
	return dimension;
}

public String getMaterialDesc() {
	return materialDesc;
}

public String getGrade() {
	return grade;
}

public String getManufacturer() {
	return manufacturer;
}

public String getMfgPartNo() {
	return mfgPartNo;
}

public String getNetWtRequired() {
	return netWtRequired;
}

public String getNewChemPackagingChange() {
	return newChemPackagingChange;
}

public String getNewComponent() {
	return newComponent;
}

}

