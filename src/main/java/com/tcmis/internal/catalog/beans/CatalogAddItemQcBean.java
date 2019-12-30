package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CatalogAddItemBean <br>
 * @version: 1.0, Aug 19, 2010 <br>
 *****************************************************************************/

public class CatalogAddItemQcBean extends BaseDataBean implements SQLData {

	public static final String sqlType = "CATALOG_ADD_ITEM_QC_OBJ";
	private BigDecimal caseQty;
	private String companyId;
	private BigDecimal componentsPerItem;
	private String customerMsdsNumber;
	private String dimension;
	private String grade;
	private BigDecimal itemApprovedBy;
	private Date itemApprovedOn;
	private BigDecimal itemId;
	private String labelColor;
	private BigDecimal lineItem;
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
	private BigDecimal requestId;
	private String sampleOnly;
	private String sizeUnit;
	private BigDecimal startingView;
	private String storageTempUnit;
	private String suggestedVendor;
	private String vendorContactEmail;
	private String vendorContactFax;
	private String vendorContactName;
	private String vendorContactPhone;
	private String status;
	private Date statusDate;
	private BigDecimal mfgShelfLife;
	private String mfgShelfLifeBasis;
	private String mfgStorageTemp;
	private String comments;
	private String itemVerified;
	private BigDecimal minTemp;
	private BigDecimal maxTemp;
	private String tempUnits;
	private String description;
/*	private BigDecimal msdsAuthorId;
	private String msdsAuthorDesc;*/

	//constructor
	public CatalogAddItemQcBean() {
	}

	public BigDecimal getCaseQty() {
		return caseQty;
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

	//getters
	public BigDecimal getRequestId() {
		return requestId;
	}

	public String getSampleOnly() {
		return sampleOnly;
	}

	public String getSizeUnit() {
		return sizeUnit;
	}

	public String getSQLTypeName() throws SQLException {
		return sqlType;
	}

	public BigDecimal getStartingView() {
		return startingView;
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

/*	public BigDecimal getMsdsAuthorId() {
		return msdsAuthorId;
	}

	public String getMsdsAuthorDesc() {
		return msdsAuthorDesc;
	}*/

	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		try {
			setRequestId(stream.readBigDecimal());
			setMaterialDesc(stream.readString());
			setManufacturer(stream.readString());
			setMaterialId(stream.readBigDecimal());
			setPartSize(stream.readBigDecimal());
			setSizeUnit(stream.readString());
			setPkgStyle(stream.readString());
			setMaterialApprovedBy(stream.readBigDecimal());
			setMaterialApprovedOn(stream.readDate());
			setItemApprovedBy(stream.readBigDecimal());
			setItemApprovedOn(stream.readDate());
			setMfgCatalogId(stream.readString());
			setPartId(stream.readBigDecimal());
			setMaterialType(stream.readString());
			setCaseQty(stream.readBigDecimal());
			setDimension(stream.readString());
			setGrade(stream.readString());
			setMfgTradeName(stream.readString());
			setNetwt(stream.readBigDecimal());
			setNetwtUnit(stream.readString());
			setCompanyId(stream.readString());
			setCustomerMsdsNumber(stream.readString());
			setComponentsPerItem(stream.readBigDecimal());
			setSampleOnly(stream.readString());
			setLabelColor(stream.readString());
			setMfgId(stream.readBigDecimal());
			setManufacturerContact(stream.readString());
			setManufacturerUrl(stream.readString());
			setManufacturerNotes(stream.readString());
			/*setMsdsAuthorId(stream.readBigDecimal());
			setMsdsAuthorDesc(stream.readString());*/
			setLineItem(stream.readBigDecimal());
			setVendorContactName(stream.readString());
			setVendorContactEmail(stream.readString());
			setVendorContactPhone(stream.readString());
			setVendorContactFax(stream.readString());
			setSuggestedVendor(stream.readString());
			setItemId(stream.readBigDecimal());
			setStartingView(stream.readBigDecimal());
			setMinStorageTemp(stream.readBigDecimal());
			setMaxStorageTemp(stream.readBigDecimal());
			setStorageTempUnit(stream.readString());
			setStatus(stream.readString());
			setStatusDate(stream.readDate());
			setMfgShelfLife(stream.readBigDecimal());
			setMfgShelfLifeBasis(stream.readString());
			setMfgStorageTemp(stream.readString());
			setComments(stream.readString());
			setItemVerified(stream.readString());
			setMinTemp(stream.readBigDecimal());
			setMaxTemp(stream.readBigDecimal());
			setTempUnits(stream.readString());
			setDescription(stream.readString());
		}
		catch (SQLException e) {
			throw e;
		}
		catch (Exception e) {
			Log log = LogFactory.getLog(this.getClass());
			log.error("readSQL method failed", e);
		}

	}

	public void setCaseQty(BigDecimal caseQty) {
		this.caseQty = caseQty;
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

	//setters
	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public void setSampleOnly(String sampleOnly) {
		this.sampleOnly = sampleOnly;
	}

	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}

	public void setStartingView(BigDecimal startingView) {
		this.startingView = startingView;
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

/*	public void setMsdsAuthorId(BigDecimal msdsAuthorId) {
		this.msdsAuthorId = msdsAuthorId;
	}

	public void setMsdsAuthorDesc(String msdsAuthorDesc) {
		this.msdsAuthorDesc = msdsAuthorDesc;
	}*/

	public void writeSQL(SQLOutput stream) throws SQLException {
		try {
			stream.writeBigDecimal(getRequestId());
			stream.writeString(getMaterialDesc());
			stream.writeString(getManufacturer());
			stream.writeBigDecimal(getMaterialId());
			stream.writeBigDecimal(getPartSize());
			stream.writeString(getSizeUnit());
			stream.writeString(getPkgStyle());
			stream.writeBigDecimal(getMaterialApprovedBy());
			stream.writeDate(new java.sql.Date(getMaterialApprovedOn().getTime()));
			stream.writeBigDecimal(getItemApprovedBy());
			stream.writeDate(new java.sql.Date(getItemApprovedOn().getTime()));
			stream.writeString(getMfgCatalogId());
			stream.writeBigDecimal(getPartId());
			stream.writeString(getMaterialType());
			stream.writeBigDecimal(getCaseQty());
			stream.writeString(getDimension());
			stream.writeString(getGrade());
			stream.writeString(getMfgTradeName());
			stream.writeBigDecimal(getNetwt());
			stream.writeString(getNetwtUnit());
			stream.writeString(getCompanyId());
			stream.writeString(getCustomerMsdsNumber());
			stream.writeBigDecimal(getComponentsPerItem());
			stream.writeString(getSampleOnly());
			stream.writeString(getLabelColor());
			stream.writeBigDecimal(getMfgId());
			stream.writeBigDecimal(getLineItem());
			stream.writeString(getVendorContactName());
			stream.writeString(getVendorContactEmail());
			stream.writeString(getVendorContactPhone());
			stream.writeString(getVendorContactFax());
			stream.writeString(getSuggestedVendor());
			stream.writeBigDecimal(getItemId());
			stream.writeBigDecimal(getStartingView());
			stream.writeString(getManufacturerContact());
			stream.writeString(getManufacturerUrl());
			stream.writeString(getManufacturerNotes());
			stream.writeBigDecimal(getMinStorageTemp());
			stream.writeBigDecimal(getMaxStorageTemp());
			stream.writeString(getStorageTempUnit());
			stream.writeString(getStatus());
			stream.writeDate(new java.sql.Date(getStatusDate().getTime()));
			stream.writeBigDecimal(getMfgShelfLife());
			stream.writeString(getMfgShelfLifeBasis());
			stream.writeString(getMfgStorageTemp());
			stream.writeString(getComments());
			stream.writeString(getItemVerified());
			stream.writeBigDecimal(getMinTemp());
			stream.writeBigDecimal(getMaxTemp());
			stream.writeString(getTempUnits());
			stream.writeString(getDescription());
		}
		catch (SQLException e) {
			throw e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").initCause(e);
		}
	}

	public String getStatus() {
		return status;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public BigDecimal getMfgShelfLife() {
		return mfgShelfLife;
	}

	public String getMfgShelfLifeBasis() {
		return mfgShelfLifeBasis;
	}

	public String getMfgStorageTemp() {
		return mfgStorageTemp;
	}

	public String getComments() {
		return comments;
	}

	public String getItemVerified() {
		return itemVerified;
	}

	public BigDecimal getMinTemp() {
		return minTemp;
	}

	public BigDecimal getMaxTemp() {
		return maxTemp;
	}

	public String getTempUnits() {
		return tempUnits;
	}

	public String getDescription() {
		return description;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public void setMfgShelfLife(BigDecimal mfgShelfLife) {
		this.mfgShelfLife = mfgShelfLife;
	}

	public void setMfgShelfLifeBasis(String mfgShelfLifeBasis) {
		this.mfgShelfLifeBasis = mfgShelfLifeBasis;
	}

	public void setMfgStorageTemp(String mfgStorageTemp) {
		this.mfgStorageTemp = mfgStorageTemp;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setItemVerified(String itemVerified) {
		this.itemVerified = itemVerified;
	}

	public void setMinTemp(BigDecimal minTemp) {
		this.minTemp = minTemp;
	}

	public void setMaxTemp(BigDecimal maxTemp) {
		this.maxTemp = maxTemp;
	}

	public void setTempUnits(String tempUnits) {
		this.tempUnits = tempUnits;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}