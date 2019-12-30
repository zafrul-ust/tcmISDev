package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CatalogAddItemBean <br>
 * @version: 1.0, Aug 19, 2010 <br>
 *****************************************************************************/

public class CatalogAddItemBean extends BaseDataBean {

	private BigDecimal caseQty;
	private String catalogComponentId;
	private String catalogComponentItemId;
	private String companyId;
	private BigDecimal componentsPerItem;
	private String customerMsdsNumber;
	private Date customerRevisionDate;
	private String dimension;
	private String grade;
	private BigDecimal itemApprovedBy;
	private Date itemApprovedOn;
	private BigDecimal itemId;
	private String labelColor;
	private BigDecimal lineItem;
	private String lineStatus;
	private String manufacturer;
	private String manufacturerContact;
	private String manufacturerNotes;
	private String manufacturerUrl;
	private BigDecimal materialApprovedBy;
	private Date materialApprovedOn;
	private String materialDesc;
	private BigDecimal materialId;
	private String materialType;
	private BigDecimal maxStorageTemp;
	private String mfgCatalogId;
	private BigDecimal mfgId;
	private String mfgTradeName;
	private BigDecimal minStorageTemp;
	private BigDecimal netwt;
	private String netwtUnit;
	private BigDecimal partId;
	private BigDecimal partSize;
	private String pkgStyle;
	private String possSize;
	private String possSpecialNote;
	private BigDecimal rejectedBy;
	private String rejectedComment;
	private Date rejectedDate;
	private BigDecimal requestId;
	private String sampleOnly;
	private String shelfLifeBasis;
	private BigDecimal shelfLifeDays;
	private String sizeUnit;
	private BigDecimal startingView;
	private String storageTemp;
	private String storageTempUnit;
	private String suggestedVendor;
	private String vendorContactEmail;
	private String vendorContactFax;
	private String vendorContactName;
	private String vendorContactPhone;

	//constructor
	public CatalogAddItemBean() {
	}

	public BigDecimal getCaseQty() {
		return caseQty;
	}

	public String getCatalogComponentId() {
		return catalogComponentId;
	}

	public String getCatalogComponentItemId() {
		return catalogComponentItemId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public BigDecimal getComponentsPerItem() {
		return componentsPerItem;
	}

	public String getCustomerMsdsNumber() {
		return customerMsdsNumber;
	}

	public Date getCustomerRevisionDate() {
		return customerRevisionDate;
	}

	public String getDimension() {
		return dimension;
	}

	public String getGrade() {
		return grade;
	}

	public BigDecimal getItemApprovedBy() {
		return itemApprovedBy;
	}

	public Date getItemApprovedOn() {
		return itemApprovedOn;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getLabelColor() {
		return labelColor;
	}

	public BigDecimal getLineItem() {
		return lineItem;
	}

	public String getLineStatus() {
		return lineStatus;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getManufacturerContact() {
		return manufacturerContact;
	}

	public String getManufacturerNotes() {
		return manufacturerNotes;
	}

	public String getManufacturerUrl() {
		return manufacturerUrl;
	}

	public BigDecimal getMaterialApprovedBy() {
		return materialApprovedBy;
	}

	public Date getMaterialApprovedOn() {
		return materialApprovedOn;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public String getMaterialType() {
		return materialType;
	}

	public BigDecimal getMaxStorageTemp() {
		return maxStorageTemp;
	}

	public String getMfgCatalogId() {
		return mfgCatalogId;
	}

	public BigDecimal getMfgId() {
		return mfgId;
	}

	public String getMfgTradeName() {
		return mfgTradeName;
	}

	public BigDecimal getMinStorageTemp() {
		return minStorageTemp;
	}

	public BigDecimal getNetwt() {
		return netwt;
	}

	public String getNetwtUnit() {
		return netwtUnit;
	}

	public BigDecimal getPartId() {
		return partId;
	}

	public BigDecimal getPartSize() {
		return partSize;
	}

	public String getPkgStyle() {
		return pkgStyle;
	}

	public String getPossSize() {
		return possSize;
	}

	public String getPossSpecialNote() {
		return possSpecialNote;
	}

	public BigDecimal getRejectedBy() {
		return rejectedBy;
	}

	public String getRejectedComment() {
		return rejectedComment;
	}

	public Date getRejectedDate() {
		return rejectedDate;
	}

	//getters
	public BigDecimal getRequestId() {
		return requestId;
	}

	public String getSampleOnly() {
		return sampleOnly;
	}

	public String getShelfLifeBasis() {
		return shelfLifeBasis;
	}

	public BigDecimal getShelfLifeDays() {
		return shelfLifeDays;
	}

	public String getSizeUnit() {
		return sizeUnit;
	}

	public BigDecimal getStartingView() {
		return startingView;
	}

	public String getStorageTemp() {
		return storageTemp;
	}

	public String getStorageTempUnit() {
		return storageTempUnit;
	}

	public String getSuggestedVendor() {
		return suggestedVendor;
	}

	public String getVendorContactEmail() {
		return vendorContactEmail;
	}

	public String getVendorContactFax() {
		return vendorContactFax;
	}

	public String getVendorContactName() {
		return vendorContactName;
	}

	public String getVendorContactPhone() {
		return vendorContactPhone;
	}

	public void setCaseQty(BigDecimal caseQty) {
		this.caseQty = caseQty;
	}

	public void setCatalogComponentId(String catalogComponentId) {
		this.catalogComponentId = catalogComponentId;
	}

	public void setCatalogComponentItemId(String catalogComponentItemId) {
		this.catalogComponentItemId = catalogComponentItemId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setComponentsPerItem(BigDecimal componentsPerItem) {
		this.componentsPerItem = componentsPerItem;
	}

	public void setCustomerMsdsNumber(String customerMsdsNumber) {
		this.customerMsdsNumber = customerMsdsNumber;
	}

	public void setCustomerRevisionDate(Date customerRevisionDate) {
		this.customerRevisionDate = customerRevisionDate;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public void setItemApprovedBy(BigDecimal itemApprovedBy) {
		this.itemApprovedBy = itemApprovedBy;
	}

	public void setItemApprovedOn(Date itemApprovedOn) {
		this.itemApprovedOn = itemApprovedOn;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setLabelColor(String labelColor) {
		this.labelColor = labelColor;
	}

	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}

	public void setLineStatus(String lineStatus) {
		this.lineStatus = lineStatus;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setManufacturerContact(String manufacturerContact) {
		this.manufacturerContact = manufacturerContact;
	}

	public void setManufacturerNotes(String manufacturerNotes) {
		this.manufacturerNotes = manufacturerNotes;
	}

	public void setManufacturerUrl(String manufacturerUrl) {
		this.manufacturerUrl = manufacturerUrl;
	}

	public void setMaterialApprovedBy(BigDecimal materialApprovedBy) {
		this.materialApprovedBy = materialApprovedBy;
	}

	public void setMaterialApprovedOn(Date materialApprovedOn) {
		this.materialApprovedOn = materialApprovedOn;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public void setMaxStorageTemp(BigDecimal maxStorageTemp) {
		this.maxStorageTemp = maxStorageTemp;
	}

	public void setMfgCatalogId(String mfgCatalogId) {
		this.mfgCatalogId = mfgCatalogId;
	}

	public void setMfgId(BigDecimal mfgId) {
		this.mfgId = mfgId;
	}

	public void setMfgTradeName(String mfgTradeName) {
		this.mfgTradeName = mfgTradeName;
	}

	public void setMinStorageTemp(BigDecimal minStorageTemp) {
		this.minStorageTemp = minStorageTemp;
	}

	public void setNetwt(BigDecimal netwt) {
		this.netwt = netwt;
	}

	public void setNetwtUnit(String netwtUnit) {
		this.netwtUnit = netwtUnit;
	}

	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}

	public void setPartSize(BigDecimal partSize) {
		this.partSize = partSize;
	}

	public void setPkgStyle(String pkgStyle) {
		this.pkgStyle = pkgStyle;
	}

	public void setPossSize(String possSize) {
		this.possSize = possSize;
	}

	public void setPossSpecialNote(String possSpecialNote) {
		this.possSpecialNote = possSpecialNote;
	}

	public void setRejectedBy(BigDecimal rejectedBy) {
		this.rejectedBy = rejectedBy;
	}

	public void setRejectedComment(String rejectedComment) {
		this.rejectedComment = rejectedComment;
	}

	public void setRejectedDate(Date rejectedDate) {
		this.rejectedDate = rejectedDate;
	}

	//setters
	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public void setSampleOnly(String sampleOnly) {
		this.sampleOnly = sampleOnly;
	}

	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}

	public void setShelfLifeDays(BigDecimal shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}

	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}

	public void setStartingView(BigDecimal startingView) {
		this.startingView = startingView;
	}

	public void setStorageTemp(String storageTemp) {
		this.storageTemp = storageTemp;
	}

	public void setStorageTempUnit(String storageTempUnit) {
		this.storageTempUnit = storageTempUnit;
	}

	public void setSuggestedVendor(String suggestedVendor) {
		this.suggestedVendor = suggestedVendor;
	}

	public void setVendorContactEmail(String vendorContactEmail) {
		this.vendorContactEmail = vendorContactEmail;
	}

	public void setVendorContactFax(String vendorContactFax) {
		this.vendorContactFax = vendorContactFax;
	}

	public void setVendorContactName(String vendorContactName) {
		this.vendorContactName = vendorContactName;
	}

	public void setVendorContactPhone(String vendorContactPhone) {
		this.vendorContactPhone = vendorContactPhone;
	}

}