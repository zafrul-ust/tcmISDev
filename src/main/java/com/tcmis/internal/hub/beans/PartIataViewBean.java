package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PartIataViewBean <br>
 * @version: 1.0, Jul 8, 2008 <br>
 *****************************************************************************/


public class PartIataViewBean extends BaseDataBean {

	private BigDecimal itemId;
	private BigDecimal partId;
	private BigDecimal materialId;
	private String materialDesc;
	private String finalShippingName;
	private String packaging;
	private BigDecimal netSize;
	private String netSizeUnit;
	private String physicalState;
	private String lastUpdatedBy;
	private String lastUpdatedDate;
	private String iataProperShippingName;
	private String iataTechnicalName;
	private String hazardClass;
	private String packingGroup;
	private String identificationNumber;
	private String iataSubrisk;
	private String iataShippingRemark;
	private String iataMixtureSolution;


	//constructor
	public PartIataViewBean() {
	}

	//setters
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public void setFinalShippingName(String finalShippingName) {
		this.finalShippingName = finalShippingName;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setNetSize(BigDecimal netSize) {
		this.netSize = netSize;
	}
	public void setNetSizeUnit(String netSizeUnit) {
		this.netSizeUnit = netSizeUnit;
	}
	public void setPhysicalState(String physicalState) {
		this.physicalState = physicalState;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public void setIataProperShippingName(String iataProperShippingName) {
		this.iataProperShippingName = iataProperShippingName;
	}
	public void setIataTechnicalName(String iataTechnicalName) {
		this.iataTechnicalName = iataTechnicalName;
	}
	public void setHazardClass(String hazardClass) {
		this.hazardClass = hazardClass;
	}
	public void setPackingGroup(String packingGroup) {
		this.packingGroup = packingGroup;
	}
	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}
	public void setIataSubrisk(String iataSubrisk) {
		this.iataSubrisk = iataSubrisk;
	}
	public void setIataShippingRemark(String iataShippingRemark) {
		this.iataShippingRemark = iataShippingRemark;
	}
	public void setIataMixtureSolution(String iataMixtureSolution) {
		this.iataMixtureSolution = iataMixtureSolution;
	}


	//getters
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getPartId() {
		return partId;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public String getFinalShippingName() {
		return finalShippingName;
	}
	public String getPackaging() {
		return packaging;
	}
	public BigDecimal getNetSize() {
		return netSize;
	}
	public String getNetSizeUnit() {
		return netSizeUnit;
	}
	public String getPhysicalState() {
		return physicalState;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public String getIataProperShippingName() {
		return iataProperShippingName;
	}
	public String getIataTechnicalName() {
		return iataTechnicalName;
	}
	public String getHazardClass() {
		return hazardClass;
	}
	public String getPackingGroup() {
		return packingGroup;
	}
	public String getIdentificationNumber() {
		return identificationNumber;
	}
	public String getIataSubrisk() {
		return iataSubrisk;
	}
	public String getIataShippingRemark() {
		return iataShippingRemark;
	}
	public String getIataMixtureSolution() {
		return iataMixtureSolution;
	}
}