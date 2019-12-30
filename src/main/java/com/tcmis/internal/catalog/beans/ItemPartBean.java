package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ItemPartBean <br>
 * @version: 1.0, 1/22/2008 <br>
 *****************************************************************************/

public class ItemPartBean
 	extends BaseDataBean 
{
	// fields from Item table:

	private BigDecimal itemId;
	private String itemDesc;
	private BigDecimal categoryId;
	private String revisionComments;
	private BigDecimal replacementItem;
	private String stockingType;
	private BigDecimal msrp;
	private String priceUnit;
	private String storageTemp;
	private BigDecimal shelfLifeDays;
	private String shelfLifeBasis;
	private String sampleOnly;
	private String itemType;
	private BigDecimal caseQty;
	private String ctPetroleumTax;
	private String inseparableKit;
 
// fields from Part table:
 
	private BigDecimal partId;
	private BigDecimal materialId;
	//private BigDecimal itemId;
	private String grade;
	private String pkgStyle;
	private BigDecimal partSize;
	private String sizeUnit;
	private String techSpec;
	private String remark;
	private String aircraft;
	private String mfgPartNo;
	private String packingInstrCode;
	//private String storageTemp;
	private String partDesc;
	private String netWtUnit;
	private BigDecimal netWt;
	//private BigDecimal caseQty;
	private String dimension;
	private String recert;
	//private String stockingType;
	private String sizeVerified;
	private BigDecimal componentsPerItem;
	private String sizeVaries;
	private String itemVerified;
	private String itemVerifiedBy;
	private Date dateItemVerified;
	//private BigDecimal shelfLifeDays;
	//private String shelfLifeBasis;
	private String ormDd;
	private String pkgGtRq;
	private String bulkPkgMarinePollutant;
	private BigDecimal shipChangeId;
	private Date insertDate;
	private BigDecimal minTemp;
	private BigDecimal maxTemp;
	private String tempUnits;
	private BigDecimal componentItemId;
	private String tempVerified;
	private String tempVerifiedBy;
	private Date dateTempVerified;


 //constructor
	public ItemPartBean() {
	}


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


	public BigDecimal getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(BigDecimal categoryId) {
		this.categoryId = categoryId;
	}


	public String getRevisionComments() {
		return revisionComments;
	}


	public void setRevisionComments(String revisionComments) {
		this.revisionComments = revisionComments;
	}


	public BigDecimal getReplacementItem() {
		return replacementItem;
	}


	public void setReplacementItem(BigDecimal replacementItem) {
		this.replacementItem = replacementItem;
	}


	public String getStockingType() {
		return stockingType;
	}


	public void setStockingType(String stockingType) {
		this.stockingType = stockingType;
	}


	public BigDecimal getMsrp() {
		return msrp;
	}


	public void setMsrp(BigDecimal msrp) {
		this.msrp = msrp;
	}


	public String getPriceUnit() {
		return priceUnit;
	}


	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}


	public String getStorageTemp() {
		return storageTemp;
	}


	public void setStorageTemp(String storageTemp) {
		this.storageTemp = storageTemp;
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


	public String getSampleOnly() {
		return sampleOnly;
	}


	public void setSampleOnly(String sampleOnly) {
		this.sampleOnly = sampleOnly;
	}


	public String getItemType() {
		return itemType;
	}


	public void setItemType(String itemType) {
		this.itemType = itemType;
	}


	public BigDecimal getCaseQty() {
		return caseQty;
	}


	public void setCaseQty(BigDecimal caseQty) {
		this.caseQty = caseQty;
	}


	public String getCtPetroleumTax() {
		return ctPetroleumTax;
	}


	public void setCtPetroleumTax(String ctPetroleumTax) {
		this.ctPetroleumTax = ctPetroleumTax;
	}


	public String getInseparableKit() {
		return inseparableKit;
	}


	public void setInseparableKit(String inseparableKit) {
		this.inseparableKit = inseparableKit;
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


	public String getPkgStyle() {
		return pkgStyle;
	}


	public void setPkgStyle(String pkgStyle) {
		this.pkgStyle = pkgStyle;
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


	public String getTechSpec() {
		return techSpec;
	}


	public void setTechSpec(String techSpec) {
		this.techSpec = techSpec;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getAircraft() {
		return aircraft;
	}


	public void setAircraft(String aircraft) {
		this.aircraft = aircraft;
	}


	public String getMfgPartNo() {
		return mfgPartNo;
	}


	public void setMfgPartNo(String mfgPartNo) {
		this.mfgPartNo = mfgPartNo;
	}


	public String getPackingInstrCode() {
		return packingInstrCode;
	}


	public void setPackingInstrCode(String packingInstrCode) {
		this.packingInstrCode = packingInstrCode;
	}


	public String getPartDesc() {
		return partDesc;
	}


	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}


	public String getNetWtUnit() {
		return netWtUnit;
	}


	public void setNetWtUnit(String netWtUnit) {
		this.netWtUnit = netWtUnit;
	}


	public BigDecimal getNetWt() {
		return netWt;
	}


	public void setNetWt(BigDecimal netWt) {
		this.netWt = netWt;
	}


	public String getDimension() {
		return dimension;
	}


	public void setDimension(String dimension) {
		this.dimension = dimension;
	}


	public String getRecert() {
		return recert;
	}


	public void setRecert(String recert) {
		this.recert = recert;
	}


	public String getSizeVerified() {
		return sizeVerified;
	}


	public void setSizeVerified(String sizeVerified) {
		this.sizeVerified = sizeVerified;
	}


	public BigDecimal getComponentsPerItem() {
		return componentsPerItem;
	}


	public void setComponentsPerItem(BigDecimal componentsPerItem) {
		this.componentsPerItem = componentsPerItem;
	}


	public String getSizeVaries() {
		return sizeVaries;
	}


	public void setSizeVaries(String sizeVaries) {
		this.sizeVaries = sizeVaries;
	}


	public String getItemVerified() {
		return itemVerified;
	}


	public void setItemVerified(String itemVerified) {
		this.itemVerified = itemVerified;
	}


	public String getItemVerifiedBy() {
		return itemVerifiedBy;
	}


	public void setItemVerifiedBy(String itemVerifiedBy) {
		this.itemVerifiedBy = itemVerifiedBy;
	}


	public Date getDateItemVerified() {
		return dateItemVerified;
	}


	public void setDateItemVerified(Date dateItemVerified) {
		this.dateItemVerified = dateItemVerified;
	}


	public String getOrmDd() {
		return ormDd;
	}


	public void setOrmDd(String ormDd) {
		this.ormDd = ormDd;
	}


	public String getPkgGtRq() {
		return pkgGtRq;
	}


	public void setPkgGtRq(String pkgGtRq) {
		this.pkgGtRq = pkgGtRq;
	}


	public String getBulkPkgMarinePollutant() {
		return bulkPkgMarinePollutant;
	}


	public void setBulkPkgMarinePollutant(String bulkPkgMarinePollutant) {
		this.bulkPkgMarinePollutant = bulkPkgMarinePollutant;
	}


	public BigDecimal getShipChangeId() {
		return shipChangeId;
	}


	public void setShipChangeId(BigDecimal shipChangeId) {
		this.shipChangeId = shipChangeId;
	}


	public Date getInsertDate() {
		return insertDate;
	}


	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
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


	public BigDecimal getComponentItemId() {
		return componentItemId;
	}


	public void setComponentItemId(BigDecimal componentItemId) {
		this.componentItemId = componentItemId;
	}


	public String getTempVerified() {
		return tempVerified;
	}


	public void setTempVerified(String tempVerified) {
		this.tempVerified = tempVerified;
	}


	public String getTempVerifiedBy() {
		return tempVerifiedBy;
	}


	public void setTempVerifiedBy(String tempVerifiedBy) {
		this.tempVerifiedBy = tempVerifiedBy;
	}


	public Date getDateTempVerified() {
		return dateTempVerified;
	}


	public void setDateTempVerified(Date dateTempVerified) {
		this.dateTempVerified = dateTempVerified;
	}
	
}