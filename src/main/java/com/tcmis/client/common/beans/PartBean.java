package com.tcmis.client.common.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PartBean <br>
 * @version: 1.0, Jan 14, 2008 <br>
 *****************************************************************************/


public class PartBean extends BaseDataBean {

	private BigDecimal partId;
	private BigDecimal materialId;
	private BigDecimal itemId;
	private String grade;
	private String pkgStyle;
	private BigDecimal partSize;
	private String sizeUnit;
	private String techSpec;
	private String remark;
	private String aircraft;
	private String mfgPartNo;
	private String packingInstrCode;
	private String storageTemp;
	private String partDesc;
	private String netWtUnit;
	private BigDecimal netWt;
	private BigDecimal caseQty;
	private String dimension;
	private String recert;
	private String stockingType;
	private String sizeVerified;
	private BigDecimal componentsPerItem;
	private String sizeVaries;
	private String itemVerified;
	private String itemVerifiedBy;
	private Date dateItemVerified;
	private BigDecimal shelfLifeDays;
	private String shelfLifeBasis;
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
	//add on for ML Item - tngo
	private String materialDesc;
	private String manufacturer;

	//constructor
	public PartBean() {
	}

	//setters
	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public void setPkgStyle(String pkgStyle) {
		this.pkgStyle = pkgStyle;
	}
	public void setPartSize(BigDecimal partSize) {
		this.partSize = partSize;
	}
	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}
	public void setTechSpec(String techSpec) {
		this.techSpec = techSpec;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setAircraft(String aircraft) {
		this.aircraft = aircraft;
	}
	public void setMfgPartNo(String mfgPartNo) {
		this.mfgPartNo = mfgPartNo;
	}
	public void setPackingInstrCode(String packingInstrCode) {
		this.packingInstrCode = packingInstrCode;
	}
	public void setStorageTemp(String storageTemp) {
		this.storageTemp = storageTemp;
	}
	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}
	public void setNetWtUnit(String netWtUnit) {
		this.netWtUnit = netWtUnit;
	}
	public void setNetWt(BigDecimal netWt) {
		this.netWt = netWt;
	}
	public void setCaseQty(BigDecimal caseQty) {
		this.caseQty = caseQty;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public void setRecert(String recert) {
		this.recert = recert;
	}
	public void setStockingType(String stockingType) {
		this.stockingType = stockingType;
	}
	public void setSizeVerified(String sizeVerified) {
		this.sizeVerified = sizeVerified;
	}
	public void setComponentsPerItem(BigDecimal componentsPerItem) {
		this.componentsPerItem = componentsPerItem;
	}
	public void setSizeVaries(String sizeVaries) {
		this.sizeVaries = sizeVaries;
	}
	public void setItemVerified(String itemVerified) {
		this.itemVerified = itemVerified;
	}
	public void setItemVerifiedBy(String itemVerifiedBy) {
		this.itemVerifiedBy = itemVerifiedBy;
	}
	public void setDateItemVerified(Date dateItemVerified) {
		this.dateItemVerified = dateItemVerified;
	}
	public void setShelfLifeDays(BigDecimal shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}
	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}
	public void setOrmD(String ormDd) {
		this.ormDd = ormDd;
	}
	public void setPkgGtRq(String pkgGtRq) {
		this.pkgGtRq = pkgGtRq;
	}
	public void setBulkPkgMarinePollutant(String bulkPkgMarinePollutant) {
		this.bulkPkgMarinePollutant = bulkPkgMarinePollutant;
	}
	public void setShipChangeId(BigDecimal shipChangeId) {
		this.shipChangeId = shipChangeId;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
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
	public void setComponentItemId(BigDecimal componentItemId) {
		this.componentItemId = componentItemId;
	}
	public void setTempVerified(String tempVerified) {
		this.tempVerified = tempVerified;
	}
	public void setTempVerifiedBy(String tempVerifiedBy) {
		this.tempVerifiedBy = tempVerifiedBy;
	}
	public void setDateTempVerified(Date dateTempVerified) {
		this.dateTempVerified = dateTempVerified;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	//getters
	public BigDecimal getPartId() {
		return partId;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getGrade() {
		return grade;
	}
	public String getPkgStyle() {
		return pkgStyle;
	}
	public BigDecimal getPartSize() {
		return partSize;
	}
	public String getSizeUnit() {
		return sizeUnit;
	}
	public String getTechSpec() {
		return techSpec;
	}
	public String getRemark() {
		return remark;
	}
	public String getAircraft() {
		return aircraft;
	}
	public String getMfgPartNo() {
		return mfgPartNo;
	}
	public String getPackingInstrCode() {
		return packingInstrCode;
	}
	public String getStorageTemp() {
		return storageTemp;
	}
	public String getPartDesc() {
		return partDesc;
	}
	public String getNetWtUnit() {
		return netWtUnit;
	}
	public BigDecimal getNetWt() {
		return netWt;
	}
	public BigDecimal getCaseQty() {
		return caseQty;
	}
	public String getDimension() {
		return dimension;
	}
	public String getRecert() {
		return recert;
	}
	public String getStockingType() {
		return stockingType;
	}
	public String getSizeVerified() {
		return sizeVerified;
	}
	public BigDecimal getComponentsPerItem() {
		return componentsPerItem;
	}
	public String getSizeVaries() {
		return sizeVaries;
	}
	public String getItemVerified() {
		return itemVerified;
	}
	public String getItemVerifiedBy() {
		return itemVerifiedBy;
	}
	public Date getDateItemVerified() {
		return dateItemVerified;
	}
	public BigDecimal getShelfLifeDays() {
		return shelfLifeDays;
	}
	public String getShelfLifeBasis() {
		return shelfLifeBasis;
	}
	public String getOrmD() {
		return ormDd;
	}
	public String getPkgGtRq() {
		return pkgGtRq;
	}
	public String getBulkPkgMarinePollutant() {
		return bulkPkgMarinePollutant;
	}
	public BigDecimal getShipChangeId() {
		return shipChangeId;
	}
	public Date getInsertDate() {
		return insertDate;
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
	public BigDecimal getComponentItemId() {
		return componentItemId;
	}
	public String getTempVerified() {
		return tempVerified;
	}
	public String getTempVerifiedBy() {
		return tempVerifiedBy;
	}
	public Date getDateTempVerified() {
		return dateTempVerified;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}
	public String getManufacturer() {
		return manufacturer;
	}

}