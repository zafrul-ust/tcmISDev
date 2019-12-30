package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class ItemMatcherBean extends BaseDataBean {

	private String sizeVerified;
	private String stockingType;
	private String itemVerified;
	private BigDecimal itemId;
	private BigDecimal partId;
	private BigDecimal materialId;
	private String grade;
	private String packaging;
	private BigDecimal shelfLifeDays;
	private String shelfLifeBasis;
	private String shelfLifeBasisDisp;
	private BigDecimal minTemp;
	private BigDecimal maxTemp;
	private String tempUnits;
	private String recert;
	private String sizeVaries;
	private String mfgPartNo;
	private String mfgPartNoExtension;
	private BigDecimal netWt;
	private String netWtUnit;
	private BigDecimal caseQty;
	private String itemType;
	private String revisionComments;
	private String materialDesc;
	private String dimension;
	
	public String getSizeVerified() {
		return sizeVerified;
	}
	public void setSizeVerified(String sizeVerified) {
		this.sizeVerified = sizeVerified;
	}
	public String getStockingType() {
		return stockingType;
	}
	public void setStockingType(String stockingType) {
		this.stockingType = stockingType;
	}
	public String getItemVerified() {
		return itemVerified;
	}
	public void setItemVerified(String itemVerified) {
		this.itemVerified = itemVerified;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
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
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getPackaging() {
		return packaging;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
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
	public String getMfgPartNo() {
		return mfgPartNo;
	}
	public void setMfgPartNo(String mfgPartNo) {
		this.mfgPartNo = mfgPartNo;
	}
	public String getMfgPartNoExtension() {
		return mfgPartNoExtension;
	}
	public void setMfgPartNoExtension(String mfgPartNoExtension) {
		this.mfgPartNoExtension = mfgPartNoExtension;
	}
	public BigDecimal getNetWt() {
		return netWt;
	}
	public void setNetWt(BigDecimal netWt) {
		this.netWt = netWt;
	}
	public String getNetWtUnit() {
		return netWtUnit;
	}
	public void setNetWtUnit(String netWtUnit) {
		this.netWtUnit = netWtUnit;
	}
	public BigDecimal getCaseQty() {
		return caseQty;
	}
	public void setCaseQty(BigDecimal caseQty) {
		this.caseQty = caseQty;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getRevisionComments() {
		return revisionComments;
	}
	public void setRevisionComments(String revisionComments) {
		this.revisionComments = revisionComments;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
}
