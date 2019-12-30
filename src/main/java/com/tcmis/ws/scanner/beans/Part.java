package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

public class Part extends BaseDataBean {

	private BigDecimal	caseQty;
	private BigDecimal	componentsPerItem;
	private String		dimension;
	private String		dot;
	private String		grade;
	private String		hazardClass;
	private String		imageFileName;
	public boolean		inseparableKit;
	private String		itemDesc;
	private BigDecimal	itemId;
	private Date		lastModified;
	private String		materialDesc;
	private BigDecimal	materialId;
	private BigDecimal	maxTemp;
	private BigDecimal	mfgId;
	private String		mfgName;
	private String		mfgPartNo;
	private BigDecimal	minTemp;
	private boolean		mvItem;
	private String		name;
	private BigDecimal	partId;
	private BigDecimal	partSize;
	private String		pkgStyle;
	private String		shelfLifeBasis;
	private BigDecimal	shelfLifeDays;
	private String		sizeUnit;
	private String		storageTemp;
	private String		tempUnits;

	public String getAltStorageTemp() {
		if (hasStorageTemp()) {
			return storageTemp;
		}
		else if (null == minTemp) {
			if (null == maxTemp) {
				return "N/A";
			}
			else {
				return "Max " + maxTemp + getTempUnits();
			}
		}
		else if (null == maxTemp) {
			return "Min " + minTemp + getTempUnits();
		}
		return minTemp + "-" + maxTemp + getTempUnits();
	}

	public BigDecimal getCaseQty() {
		return this.caseQty;
	}

	public BigDecimal getComponentsPerItem() {
		return this.componentsPerItem;
	}

	public String getDimension() {
		return this.dimension;
	}

	public String getDot() {
		return this.dot;
	}

	public String getGrade() {
		return grade;
	}

	public String getHazardClass() {
		return hazardClass;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public BigDecimal getMaxTemp() {
		return this.maxTemp;
	}

	public BigDecimal getMfgId() {
		return mfgId;
	}

	public String getMfgName() {
		return mfgName;
	}

	public String getMfgPartNo() {
		return mfgPartNo;
	}

	public BigDecimal getMinTemp() {
		return this.minTemp;
	}

	public String getName() {
		return name;
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

	public String getShelfLifeBasis() {
		return this.shelfLifeBasis;
	}

	public BigDecimal getShelfLifeDays() {
		return this.shelfLifeDays;
	}

	public String getSizeUnit() {
		return sizeUnit;
	}

	public String getStorageTemp() {
		return storageTemp;
	}

	public String getTempUnits() {
		return (StringUtils.isNotBlank(tempUnits) ? tempUnits : "");
	}

	public boolean hasStorageTemp() {
		return StringUtils.isNotBlank(storageTemp);
	}

	public boolean isInseparableKit() {
		return inseparableKit;
	}

	public boolean isMvItem() {
		return mvItem;
	}

	public void setCaseQty(BigDecimal caseQty) {
		this.caseQty = caseQty;
	}

	public void setComponentsPerItem(BigDecimal componentsPerItem) {
		this.componentsPerItem = componentsPerItem;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public void setDot(String dot) {
		this.dot = dot;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public void setHazardClass(String hazardClass) {
		this.hazardClass = hazardClass;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public void setInseparableKit(boolean inseperableKit) {
		this.inseparableKit = inseperableKit;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setLastModified(Date dateItemVerified) {
		this.lastModified = dateItemVerified;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public void setMaxTemp(BigDecimal maxTemp) {
		this.maxTemp = maxTemp;
	}

	public void setMfgId(BigDecimal mfgId) {
		this.mfgId = mfgId;
	}

	public void setMfgName(String mfgName) {
		this.mfgName = mfgName;
	}

	public void setMfgPartNo(String mfgPartNo) {
		this.mfgPartNo = mfgPartNo;
	}

	public void setMinTemp(BigDecimal minTemp) {
		this.minTemp = minTemp;
	}

	public void setMvItem(boolean mvItem) {
		this.mvItem = mvItem;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}

	public void setShelfLifeDays(BigDecimal shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}

	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}

	public void setStorageTemp(String storageTemp) {
		this.storageTemp = storageTemp;
	}

	public void setTempUnits(String tempUnits) {
		this.tempUnits = tempUnits;
	}
}