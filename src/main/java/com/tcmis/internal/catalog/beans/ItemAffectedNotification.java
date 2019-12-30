package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

import radian.tcmis.common.util.StringHandler;

@SuppressWarnings("serial")
public class ItemAffectedNotification extends BaseDataBean {

	private BigDecimal itemId;
	private String itemDesc;
	private String packaging;
	private String pkgStyle;
	private String revisionComments;
	private String stockingType;
	private String itemType;
	private BigDecimal partId;
	private BigDecimal materialId;
	private String mfgPartNo;
	private String mfgPartNoExtension;
	private String localeMfgPartNo;
	private String localeMfgPartNoExtension;
	private String grade;
	private String localeGrade;
	private BigDecimal minTemp;
	private BigDecimal maxTemp;
	private String tempUnits;
	private BigDecimal shelfLifeDays;
	private String shelfLifeBasis;
	private String shelfLifeBasisDispLabel;
	private BigDecimal localeMinTemp;
	private BigDecimal localeMaxTemp;
	private String localeTempUnits;
	private String localeCode;
	private String localeDisplay;
	private boolean grab;
	private BigDecimal mfgId;
	public BigDecimal getItemId() {
		return itemId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getPackaging() {
		return packaging;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public String getPkgStyle() {
		return pkgStyle;
	}
	public void setPkgStyle(String pkgStyle) {
		this.pkgStyle = pkgStyle;
	}
	public String getRevisionComments() {
		return revisionComments;
	}
	public void setRevisionComments(String revisionComments) {
		this.revisionComments = revisionComments;
	}
	public String getStockingType() {
		return stockingType;
	}
	public void setStockingType(String stockingType) {
		this.stockingType = stockingType;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public BigDecimal getPartId() {
		return partId;
	}
	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public String getMfgPartNo() {
		return getExtendedString(mfgPartNo, mfgPartNoExtension);
	}
	public void setMfgPartNo(String mfgPartNo) {
		this.mfgPartNo = mfgPartNo;
	}
	public String getLocaleMfgPartNo() {
		return getExtendedString(localeMfgPartNo, localeMfgPartNoExtension);
	}
	public void setLocaleMfgPartNo(String localeMfgPartNo) {
		this.localeMfgPartNo = localeMfgPartNo;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getLocaleGrade() {
		return localeGrade;
	}
	public void setLocaleGrade(String localeGrade) {
		this.localeGrade = localeGrade;
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
	public BigDecimal getShelfLifeDays() {
		return shelfLifeDays;
	}
	public void setShelfLifeDays(BigDecimal shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}
	public String getShelfLifeBasis() {
		return shelfLifeBasis;
	}
	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}
	public String getShelfLifeBasisDispLabel() {
		return shelfLifeBasisDispLabel;
	}
	public void setShelfLifeBasisDispLabel(String shelfLifeBasisDispLabel) {
		this.shelfLifeBasisDispLabel = shelfLifeBasisDispLabel;
	}
	public BigDecimal getLocaleMinTemp() {
		return localeMinTemp;
	}
	public void setLocaleMinTemp(BigDecimal localeMinTemp) {
		this.localeMinTemp = localeMinTemp;
	}
	public BigDecimal getLocaleMaxTemp() {
		return localeMaxTemp;
	}
	public void setLocaleMaxTemp(BigDecimal localeMaxTemp) {
		this.localeMaxTemp = localeMaxTemp;
	}
	public String getLocaleTempUnits() {
		return localeTempUnits;
	}
	public void setLocaleTempUnits(String localeTempUnits) {
		this.localeTempUnits = localeTempUnits;
	}
	public String getLocaleCode() {
		return localeCode;
	}
	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}
	public String getLocaleDisplay() {
		return localeDisplay;
	}
	public void setLocaleDisplay(String localeDisplay) {
		this.localeDisplay = localeDisplay;
	}
	public boolean isGrab() {
		return grab;
	}
	public void setGrab(boolean grab) {
		this.grab = grab;
	}
	public String getMfgPartNoExtension() {
		return mfgPartNoExtension;
	}
	public void setMfgPartNoExtension(String mfgPartNoExtension) {
		this.mfgPartNoExtension = mfgPartNoExtension;
	}
	public String getLocaleMfgPartNoExtension() {
		return localeMfgPartNoExtension;
	}
	public void setLocaleMfgPartNoExtension(String localeMfgPartNoExtension) {
		this.localeMfgPartNoExtension = localeMfgPartNoExtension;
	}
	public BigDecimal getMfgId() {
		return mfgId;
	}
	public void setMfgId(BigDecimal mfgId) {
		this.mfgId = mfgId;
	}
	private String getExtendedString(String orig, String ext) {
		StringBuilder builder = new StringBuilder();
		if ( ! StringHandler.isBlankString(ext)) {
			if ( ! StringHandler.isBlankString(orig)) {
				builder.append(orig).append(" ").append(ext);
			}
			else {
				builder.append(ext);
			}
		}
		else if ( ! StringHandler.isBlankString(orig)) {
			builder.append(orig);
		}
		return builder.toString();
	}
	
}
