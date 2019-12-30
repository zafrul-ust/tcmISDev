package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class QualityCheckOriginalViewBean extends BaseDataBean {

	private BigDecimal requestId;
	private String materialDesc;
	private String manufacturer;
	private BigDecimal materialId;
	private BigDecimal partSize;
	private String sizeUnit;
	private String pkgStyle;
	private String mfgCatalogId;
	private BigDecimal caseQty;
	private String dimension;
	private String grade;
	private String mfgTradeName;
	private BigDecimal netwt;
	private String netwtUnit;
	private BigDecimal componentsPerItem;
	private String sampleOnly;
	private String labelColor;
	
	public QualityCheckOriginalViewBean() {
		
	}

	public BigDecimal getRequestId() {
		return requestId;
	}

	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
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

	public String getMfgCatalogId() {
		return mfgCatalogId;
	}

	public void setMfgCatalogId(String mfgCatalogId) {
		this.mfgCatalogId = mfgCatalogId;
	}

	public BigDecimal getCaseQty() {
		return caseQty;
	}

	public void setCaseQty(BigDecimal caseQty) {
		this.caseQty = caseQty;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getMfgTradeName() {
		return mfgTradeName;
	}

	public void setMfgTradeName(String mfgTradeName) {
		this.mfgTradeName = mfgTradeName;
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

	public BigDecimal getComponentsPerItem() {
		return componentsPerItem;
	}

	public void setComponentsPerItem(BigDecimal componentsPerItem) {
		this.componentsPerItem = componentsPerItem;
	}

	public String getSampleOnly() {
		return sampleOnly;
	}

	public void setSampleOnly(String sampleOnly) {
		this.sampleOnly = sampleOnly;
	}

	public String getLabelColor() {
		return labelColor;
	}

	public void setLabelColor(String labelColor) {
		this.labelColor = labelColor;
	}
}
