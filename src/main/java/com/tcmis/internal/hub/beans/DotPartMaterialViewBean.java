package com.tcmis.internal.hub.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DotPartMaterialViewBean <br>
 * @version: 1.0, Nov 8, 2010 <br>
 *****************************************************************************/


public class DotPartMaterialViewBean extends BaseDataBean {

	private BigDecimal partId;
	private BigDecimal materialId;
	private BigDecimal itemId;
	private String pkgGtRq;
	private String testPkgGtRq;
	private String bulkPkgMarinePollutant;
	private String ormD;
	private BigDecimal weightLb;
	private String materialDesc;
	private String hazardClass;
	private String subsidiaryHazardClass;
	private String packingGroup;
	private String identificationNumber;
	private BigDecimal hazmatId;
	private String properShippingName;
	private String dryIce;
	private String erg;
	private String marinePollutant;
	private BigDecimal reportableQuantityLb;
	private String shippingInfoRemarks;
	private String packaging;
	private String lastChangedBy;
	private Date lastUpdated;
	private String hazmatTechnicalName;
	private String symbol;
	private String scheduleB;
	private String scheduleBDesc;
	private String eccn;
	private BigDecimal iataDgId;
	private String iataProperShippingName;
	private String iataSubrisk;
	private String iataTechnicalName;
	private BigDecimal iataChangeId;
	private Date iataTimestamp;
	private String iataShippingRemark;
	private String iataIdentificationNumber;
	private String iataProperShippingNameDesc;
	private String iataClassOrDivision;
	private String iataRegSubrisk;
	private String iataHazardLabel;
	private String iataPackingGroup;
	private String iataPcLtdQtyPkgInstr;
	private String iataPcLqMaxNetQtyPerPkg;
	private String iataPcPkgInstr;
	private String iataPcMaxNetQtyPerPkg;
	private String iataCOnlyPkgInstr;
	private String iataCOnlyMaxNetQtyPrPkg;
	private String iataSpecialProvision;
	private String iataErgCode;
	private String iataPcComment;
	private String iataCOnlyComment;
	private String iataTechnicalNameRequired;
	private String iataPickable;
	private BigDecimal iataPcLqMaxNetValPerPkg;
	private String iataPcLqMaxNetUntPerPkg;
	private BigDecimal iataPcMaxNetValPerPkg;
	private String iataPcMaxNetUnitPerPkg;
	private BigDecimal iataCOnlyMaxNetValPrPkg;
	private String iataCOnlyMaxNetUnitPPkg;
	private String iataPcLtdQtyG;
	private String iataPcG;
	private String iataMixtureSolution;
	private BigDecimal adrId;
	private String adrProperShippingName;
	private String adrShippingRemark;
	private String adrUnNo;
	private String adrClass;
	private String adrClassificationCode;
	private String adrPackingGroup;
	private String adrLimitedQuanity;
	private String adrTransportCategory;
	private String adrTunnelCode;
	private String imdgId;
	private String imdgProperShippingName;
	private String imdgShippingRemark;
	private String imdgSubrisk;
	private String imdgTechnicalName;
	private Date imdgTimestamp;
	private String imdgDescription;
	private String imdgClassOrDivision;
	private String imdgSubsidiaryRisk;
	private String imdgPackingGroup;
	private String imdgSpecialProvision;
	private String imdgLimitedQuantity;
	private String imdgPackingInstruction;
	private String imdgSpecialPackingProvision;
	private String imdgIbcSpecialProvision;
	private String imdgImoTankInstruction;
	private String imdgUnTankBulkContInstr;
	private String imdgTankSpecialProvision;
	private String imdgEms;
	private String imdgStowageAndSegregation;
	private String imdgProperty;
	private String imdgObservation;
	private String imdgStar;
	private String imdgMp;
	private String imdgState;
	private String imdgTechnicalNameRequired;
	private String itemDesc;
	
	private String imdgUnNumber;
	private String dotDeclaration;
	private String iataDeclaration;
	private String adrDeclaration;
	private String imdgDeclaration;

	private String adrTechnicalNameRequired;
	private String adrTechnicalName;
	private String adrSubrisk;
	private String uAction;
	
	private String wmsAcidBase;
	private String wmsCorrosive;
	private String physicalState;
	private String wmsAerosol;
	private String wmsFlammable;
	private String wmsToxic;
	private String wmsOxidizer;
	private String wmsReactive;
	private String wmsWaterReactive;
	private String wmsOrganicPeroxide;
	private String wmsContainer;
	private String wmsPyrophoric;
	private String wmsContPressureRelieving;
	private String wmsWaterMiscible;
	//constructor
	public DotPartMaterialViewBean() {
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
	public void setPkgGtRq(String pkgGtRq) {
		this.pkgGtRq = pkgGtRq;
	}
	public void setTestPkgGtRq(String testPkgGtRq) {
		this.testPkgGtRq = testPkgGtRq;
	}
	public void setBulkPkgMarinePollutant(String bulkPkgMarinePollutant) {
		this.bulkPkgMarinePollutant = bulkPkgMarinePollutant;
	}
	public void setOrmD(String ormD) {
		this.ormD = ormD;
	}
	public void setWeightLb(BigDecimal weightLb) {
		this.weightLb = weightLb;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public void setHazardClass(String hazardClass) {
		this.hazardClass = hazardClass;
	}
	public void setSubsidiaryHazardClass(String subsidiaryHazardClass) {
		this.subsidiaryHazardClass = subsidiaryHazardClass;
	}
	public void setPackingGroup(String packingGroup) {
		this.packingGroup = packingGroup;
	}
	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}
	public void setHazmatId(BigDecimal hazmatId) {
		this.hazmatId = hazmatId;
	}
	public void setProperShippingName(String properShippingName) {
		this.properShippingName = properShippingName;
	}
	public void setDryIce(String dryIce) {
		this.dryIce = dryIce;
	}
	public void setErg(String erg) {
		this.erg = erg;
	}
	public void setMarinePollutant(String marinePollutant) {
		this.marinePollutant = marinePollutant;
	}
	public void setReportableQuantityLb(BigDecimal reportableQuantityLb) {
		this.reportableQuantityLb = reportableQuantityLb;
	}
	public void setShippingInfoRemarks(String shippingInfoRemarks) {
		this.shippingInfoRemarks = shippingInfoRemarks;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setLastChangedBy(String lastChangedBy) {
		this.lastChangedBy = lastChangedBy;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public void setHazmatTechnicalName(String hazmatTechnicalName) {
		this.hazmatTechnicalName = hazmatTechnicalName;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public void setScheduleB(String scheduleB) {
		this.scheduleB = scheduleB;
	}
	public void setScheduleBDesc(String scheduleBDesc) {
		this.scheduleBDesc = scheduleBDesc;
	}
	public void setEccn(String eccn) {
		this.eccn = eccn;
	}
	public void setIataDgId(BigDecimal iataDgId) {
		this.iataDgId = iataDgId;
	}
	public void setIataProperShippingName(String iataProperShippingName) {
		this.iataProperShippingName = iataProperShippingName;
	}
	public void setIataSubrisk(String iataSubrisk) {
		this.iataSubrisk = iataSubrisk;
	}
	public void setIataTechnicalName(String iataTechnicalName) {
		this.iataTechnicalName = iataTechnicalName;
	}
	public void setIataChangeId(BigDecimal iataChangeId) {
		this.iataChangeId = iataChangeId;
	}
	public void setIataTimestamp(Date iataTimestamp) {
		this.iataTimestamp = iataTimestamp;
	}
	public void setIataShippingRemark(String iataShippingRemark) {
		this.iataShippingRemark = iataShippingRemark;
	}
	public void setIataIdentificationNumber(String iataIdentificationNumber) {
		this.iataIdentificationNumber = iataIdentificationNumber;
	}
	public void setIataProperShippingNameDesc(String iataProperShippingNameDesc) {
		this.iataProperShippingNameDesc = iataProperShippingNameDesc;
	}
	public void setIataClassOrDivision(String iataClassOrDivision) {
		this.iataClassOrDivision = iataClassOrDivision;
	}
	public void setIataRegSubrisk(String iataRegSubrisk) {
		this.iataRegSubrisk = iataRegSubrisk;
	}
	public void setIataHazardLabel(String iataHazardLabel) {
		this.iataHazardLabel = iataHazardLabel;
	}
	public void setIataPackingGroup(String iataPackingGroup) {
		this.iataPackingGroup = iataPackingGroup;
	}
	public void setIataPcLtdQtyPkgInstr(String iataPcLtdQtyPkgInstr) {
		this.iataPcLtdQtyPkgInstr = iataPcLtdQtyPkgInstr;
	}
	public void setIataPcLqMaxNetQtyPerPkg(String iataPcLqMaxNetQtyPerPkg) {
		this.iataPcLqMaxNetQtyPerPkg = iataPcLqMaxNetQtyPerPkg;
	}
	public void setIataPcPkgInstr(String iataPcPkgInstr) {
		this.iataPcPkgInstr = iataPcPkgInstr;
	}
	public void setIataPcMaxNetQtyPerPkg(String iataPcMaxNetQtyPerPkg) {
		this.iataPcMaxNetQtyPerPkg = iataPcMaxNetQtyPerPkg;
	}
	public void setIataCOnlyPkgInstr(String iataCOnlyPkgInstr) {
		this.iataCOnlyPkgInstr = iataCOnlyPkgInstr;
	}
	public void setIataCOnlyMaxNetQtyPrPkg(String iataCOnlyMaxNetQtyPrPkg) {
		this.iataCOnlyMaxNetQtyPrPkg = iataCOnlyMaxNetQtyPrPkg;
	}
	public void setIataSpecialProvision(String iataSpecialProvision) {
		this.iataSpecialProvision = iataSpecialProvision;
	}
	public void setIataErgCode(String iataErgCode) {
		this.iataErgCode = iataErgCode;
	}
	public void setIataPcComment(String iataPcComment) {
		this.iataPcComment = iataPcComment;
	}
	public void setIataCOnlyComment(String iataCOnlyComment) {
		this.iataCOnlyComment = iataCOnlyComment;
	}
	public void setIataTechnicalNameRequired(String iataTechnicalNameRequired) {
		this.iataTechnicalNameRequired = iataTechnicalNameRequired;
	}
	public void setIataPickable(String iataPickable) {
		this.iataPickable = iataPickable;
	}
	public void setIataPcLqMaxNetValPerPkg(BigDecimal iataPcLqMaxNetValPerPkg) {
		this.iataPcLqMaxNetValPerPkg = iataPcLqMaxNetValPerPkg;
	}
	public void setIataPcLqMaxNetUntPerPkg(String iataPcLqMaxNetUntPerPkg) {
		this.iataPcLqMaxNetUntPerPkg = iataPcLqMaxNetUntPerPkg;
	}
	public void setIataPcMaxNetValPerPkg(BigDecimal iataPcMaxNetValPerPkg) {
		this.iataPcMaxNetValPerPkg = iataPcMaxNetValPerPkg;
	}
	public void setIataPcMaxNetUnitPerPkg(String iataPcMaxNetUnitPerPkg) {
		this.iataPcMaxNetUnitPerPkg = iataPcMaxNetUnitPerPkg;
	}
	public void setIataCOnlyMaxNetValPrPkg(BigDecimal iataCOnlyMaxNetValPrPkg) {
		this.iataCOnlyMaxNetValPrPkg = iataCOnlyMaxNetValPrPkg;
	}
	public void setIataCOnlyMaxNetUnitPPkg(String iataCOnlyMaxNetUnitPPkg) {
		this.iataCOnlyMaxNetUnitPPkg = iataCOnlyMaxNetUnitPPkg;
	}
	public void setIataPcLtdQtyG(String iataPcLtdQtyG) {
		this.iataPcLtdQtyG = iataPcLtdQtyG;
	}
	public void setIataPcG(String iataPcG) {
		this.iataPcG = iataPcG;
	}
	public void setAdrId(BigDecimal adrId) {
		this.adrId = adrId;
	}
	public void setAdrProperShippingName(String adrProperShippingName) {
		this.adrProperShippingName = adrProperShippingName;
	}
	public void setAdrShippingRemark(String adrShippingRemark) {
		this.adrShippingRemark = adrShippingRemark;
	}
	public void setAdrClass(String adrClass) {
		this.adrClass = adrClass;
	}
	public void setAdrClassificationCode(String adrClassificationCode) {
		this.adrClassificationCode = adrClassificationCode;
	}
	public void setAdrPackingGroup(String adrPackingGroup) {
		this.adrPackingGroup = adrPackingGroup;
	}
	public void setAdrLimitedQuanity(String adrLimitedQuanity) {
		this.adrLimitedQuanity = adrLimitedQuanity;
	}
	public void setAdrTransportCategory(String adrTransportCategory) {
		this.adrTransportCategory = adrTransportCategory;
	}
	public void setAdrTunnelCode(String adrTunnelCode) {
		this.adrTunnelCode = adrTunnelCode;
	}
	public void setImdgId(String imdgId) {
		this.imdgId = imdgId;
	}
	public void setImdgProperShippingName(String imdgProperShippingName) {
		this.imdgProperShippingName = imdgProperShippingName;
	}
	public void setImdgShippingRemark(String imdgShippingRemark) {
		this.imdgShippingRemark = imdgShippingRemark;
	}
	public void setImdgSubrisk(String imdgSubrisk) {
		this.imdgSubrisk = imdgSubrisk;
	}
	public void setImdgTechnicalName(String imdgTechnicalName) {
		this.imdgTechnicalName = imdgTechnicalName;
	}
	public void setImdgTimestamp(Date imdgTimestamp) {
		this.imdgTimestamp = imdgTimestamp;
	}
	public void setImdgDescription(String imdgDescription) {
		this.imdgDescription = imdgDescription;
	}
	public void setImdgClassOrDivision(String imdgClassOrDivision) {
		this.imdgClassOrDivision = imdgClassOrDivision;
	}
	public void setImdgSubsidiaryRisk(String imdgSubsidiaryRisk) {
		this.imdgSubsidiaryRisk = imdgSubsidiaryRisk;
	}
	public void setImdgPackingGroup(String imdgPackingGroup) {
		this.imdgPackingGroup = imdgPackingGroup;
	}
	public void setImdgSpecialProvision(String imdgSpecialProvision) {
		this.imdgSpecialProvision = imdgSpecialProvision;
	}
	public void setImdgLimitedQuantity(String imdgLimitedQuantity) {
		this.imdgLimitedQuantity = imdgLimitedQuantity;
	}
	public void setImdgPackingInstruction(String imdgPackingInstruction) {
		this.imdgPackingInstruction = imdgPackingInstruction;
	}
	public void setImdgSpecialPackingProvision(String imdgSpecialPackingProvision) {
		this.imdgSpecialPackingProvision = imdgSpecialPackingProvision;
	}
	public void setImdgIbcSpecialProvision(String imdgIbcSpecialProvision) {
		this.imdgIbcSpecialProvision = imdgIbcSpecialProvision;
	}
	public void setImdgImoTankInstruction(String imdgImoTankInstruction) {
		this.imdgImoTankInstruction = imdgImoTankInstruction;
	}
	public void setImdgUnTankBulkContInstr(String imdgUnTankBulkContInstr) {
		this.imdgUnTankBulkContInstr = imdgUnTankBulkContInstr;
	}
	public void setImdgTankSpecialProvision(String imdgTankSpecialProvision) {
		this.imdgTankSpecialProvision = imdgTankSpecialProvision;
	}
	public void setImdgEms(String imdgEms) {
		this.imdgEms = imdgEms;
	}
	public void setImdgStowageAndSegregation(String imdgStowageAndSegregation) {
		this.imdgStowageAndSegregation = imdgStowageAndSegregation;
	}
	public void setImdgProperty(String imdgProperty) {
		this.imdgProperty = imdgProperty;
	}
	public void setImdgObservation(String imdgObservation) {
		this.imdgObservation = imdgObservation;
	}
	public void setImdgStar(String imdgStar) {
		this.imdgStar = imdgStar;
	}
	public void setImdgMp(String imdgMp) {
		this.imdgMp = imdgMp;
	}
	public void setImdgState(String imdgState) {
		this.imdgState = imdgState;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
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
	public String getPkgGtRq() {
		return pkgGtRq;
	}
	public String getTestPkgGtRq() {
		return testPkgGtRq;
	}
	public String getBulkPkgMarinePollutant() {
		return bulkPkgMarinePollutant;
	}
	public String getOrmD() {
		return ormD;
	}
	public BigDecimal getWeightLb() {
		return weightLb;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public String getHazardClass() {
		return hazardClass;
	}
	public String getSubsidiaryHazardClass() {
		return subsidiaryHazardClass;
	}
	public String getPackingGroup() {
		return packingGroup;
	}
	public String getIdentificationNumber() {
		return identificationNumber;
	}
	public BigDecimal getHazmatId() {
		return hazmatId;
	}
	public String getProperShippingName() {
		return properShippingName;
	}
	public String getDryIce() {
		return dryIce;
	}
	public String getErg() {
		return erg;
	}
	public String getMarinePollutant() {
		return marinePollutant;
	}
	public BigDecimal getReportableQuantityLb() {
		return reportableQuantityLb;
	}
	public String getShippingInfoRemarks() {
		return shippingInfoRemarks;
	}
	public String getPackaging() {
		return packaging;
	}
	public String getLastChangedBy() {
		return lastChangedBy;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public String getHazmatTechnicalName() {
		return hazmatTechnicalName;
	}
	public String getSymbol() {
		return symbol;
	}
	public String getScheduleB() {
		return scheduleB;
	}
	public String getScheduleBDesc() {
		return scheduleBDesc;
	}
	public String getEccn() {
		return eccn;
	}
	public BigDecimal getIataDgId() {
		return iataDgId;
	}
	public String getIataProperShippingName() {
		return iataProperShippingName;
	}
	public String getIataSubrisk() {
		return iataSubrisk;
	}
	public String getIataTechnicalName() {
		return iataTechnicalName;
	}
	public BigDecimal getIataChangeId() {
		return iataChangeId;
	}
	public Date getIataTimestamp() {
		return iataTimestamp;
	}
	public String getIataShippingRemark() {
		return iataShippingRemark;
	}
	public String getIataIdentificationNumber() {
		return iataIdentificationNumber;
	}
	public String getIataProperShippingNameDesc() {
		return iataProperShippingNameDesc;
	}
	public String getIataClassOrDivision() {
		return iataClassOrDivision;
	}
	public String getIataRegSubrisk() {
		return iataRegSubrisk;
	}
	public String getIataHazardLabel() {
		return iataHazardLabel;
	}
	public String getIataPackingGroup() {
		return iataPackingGroup;
	}
	public String getIataPcLtdQtyPkgInstr() {
		return iataPcLtdQtyPkgInstr;
	}
	public String getIataPcLqMaxNetQtyPerPkg() {
		return iataPcLqMaxNetQtyPerPkg;
	}
	public String getIataPcPkgInstr() {
		return iataPcPkgInstr;
	}
	public String getIataPcMaxNetQtyPerPkg() {
		return iataPcMaxNetQtyPerPkg;
	}
	public String getIataCOnlyPkgInstr() {
		return iataCOnlyPkgInstr;
	}
	public String getIataCOnlyMaxNetQtyPrPkg() {
		return iataCOnlyMaxNetQtyPrPkg;
	}
	public String getIataSpecialProvision() {
		return iataSpecialProvision;
	}
	public String getIataErgCode() {
		return iataErgCode;
	}
	public String getIataPcComment() {
		return iataPcComment;
	}
	public String getIataCOnlyComment() {
		return iataCOnlyComment;
	}
	public String getIataTechnicalNameRequired() {
		return iataTechnicalNameRequired;
	}
	public String getIataPickable() {
		return iataPickable;
	}
	public BigDecimal getIataPcLqMaxNetValPerPkg() {
		return iataPcLqMaxNetValPerPkg;
	}
	public String getIataPcLqMaxNetUntPerPkg() {
		return iataPcLqMaxNetUntPerPkg;
	}
	public BigDecimal getIataPcMaxNetValPerPkg() {
		return iataPcMaxNetValPerPkg;
	}
	public String getIataPcMaxNetUnitPerPkg() {
		return iataPcMaxNetUnitPerPkg;
	}
	public BigDecimal getIataCOnlyMaxNetValPrPkg() {
		return iataCOnlyMaxNetValPrPkg;
	}
	public String getIataCOnlyMaxNetUnitPPkg() {
		return iataCOnlyMaxNetUnitPPkg;
	}
	public String getIataPcLtdQtyG() {
		return iataPcLtdQtyG;
	}
	public String getIataPcG() {
		return iataPcG;
	}
	public BigDecimal getAdrId() {
		return adrId;
	}
	public String getAdrProperShippingName() {
		return adrProperShippingName;
	}
	public String getAdrShippingRemark() {
		return adrShippingRemark;
	}
	public String getAdrUnNo() {
		return adrUnNo;
	}

	public void setAdrUnNo(String adrUnNo) {
		this.adrUnNo = adrUnNo;
	}

	public String getAdrClass() {
		return adrClass;
	}
	public String getAdrClassificationCode() {
		return adrClassificationCode;
	}
	public String getAdrPackingGroup() {
		return adrPackingGroup;
	}
	public String getAdrLimitedQuanity() {
		return adrLimitedQuanity;
	}
	public String getAdrTransportCategory() {
		return adrTransportCategory;
	}
	public String getAdrTunnelCode() {
		return adrTunnelCode;
	}
	public String getImdgId() {
		return imdgId;
	}
	public String getImdgProperShippingName() {
		return imdgProperShippingName;
	}
	public String getImdgShippingRemark() {
		return imdgShippingRemark;
	}
	public String getImdgSubrisk() {
		return imdgSubrisk;
	}
	public String getImdgTechnicalName() {
		return imdgTechnicalName;
	}
	public Date getImdgTimestamp() {
		return imdgTimestamp;
	}
	public String getImdgDescription() {
		return imdgDescription;
	}
	public String getImdgClassOrDivision() {
		return imdgClassOrDivision;
	}
	public String getImdgSubsidiaryRisk() {
		return imdgSubsidiaryRisk;
	}
	public String getImdgPackingGroup() {
		return imdgPackingGroup;
	}
	public String getImdgSpecialProvision() {
		return imdgSpecialProvision;
	}
	public String getImdgLimitedQuantity() {
		return imdgLimitedQuantity;
	}
	public String getImdgPackingInstruction() {
		return imdgPackingInstruction;
	}
	public String getImdgSpecialPackingProvision() {
		return imdgSpecialPackingProvision;
	}
	public String getImdgIbcSpecialProvision() {
		return imdgIbcSpecialProvision;
	}
	public String getImdgImoTankInstruction() {
		return imdgImoTankInstruction;
	}
	public String getImdgUnTankBulkContInstr() {
		return imdgUnTankBulkContInstr;
	}
	public String getImdgTankSpecialProvision() {
		return imdgTankSpecialProvision;
	}
	public String getImdgEms() {
		return imdgEms;
	}
	public String getImdgStowageAndSegregation() {
		return imdgStowageAndSegregation;
	}
	public String getImdgProperty() {
		return imdgProperty;
	}
	public String getImdgObservation() {
		return imdgObservation;
	}
	public String getImdgStar() {
		return imdgStar;
	}
	public String getImdgMp() {
		return imdgMp;
	}
	public String getImdgState() {
		return imdgState;
	}
	public String getItemDesc() {
		return itemDesc;
	}

	public String getIataMixtureSolution() {
		return iataMixtureSolution;
	}

	public void setIataMixtureSolution(String iataMixtureSolution) {
		this.iataMixtureSolution = iataMixtureSolution;
	}

	public String getImdgTechnicalNameRequired() {
		return imdgTechnicalNameRequired;
	}

	public void setImdgTechnicalNameRequired(String imdgTechnicalNameRequired) {
		this.imdgTechnicalNameRequired = imdgTechnicalNameRequired;
	}

	public String getAdrDeclaration() {
		return adrDeclaration;
	}

	public void setAdrDeclaration(String adrDeclaration) {
		this.adrDeclaration = adrDeclaration;
	}

	public String getDotDeclaration() {
		return dotDeclaration;
	}

	public void setDotDeclaration(String dotDeclaration) {
		this.dotDeclaration = dotDeclaration;
	}

	public String getIataDeclaration() {
		return iataDeclaration;
	}

	public void setIataDeclaration(String iataDeclaration) {
		this.iataDeclaration = iataDeclaration;
	}

	public String getImdgDeclaration() {
		return imdgDeclaration;
	}

	public void setImdgDeclaration(String imdgDeclaration) {
		this.imdgDeclaration = imdgDeclaration;
	}

	public String getImdgUnNumber() {
		return imdgUnNumber;
	}

	public void setImdgUnNumber(String imdgUnNumber) {
		this.imdgUnNumber = imdgUnNumber;
	}

	public String getuAction() {
		return uAction;
	}
	public void setuAction(String uAction) {
		this.uAction = uAction;
	}
	public String getAdrTechnicalNameRequired() {
		return adrTechnicalNameRequired;
	}
	public void setAdrTechnicalNameRequired(String adrTechnicalNameRequired) {
		this.adrTechnicalNameRequired = adrTechnicalNameRequired;
	}
	public String getAdrTechnicalName() {
		return adrTechnicalName;
	}
	public void setAdrTechnicalName(String adrTechnicalName) {
		this.adrTechnicalName = adrTechnicalName;
	}
	public String getAdrSubrisk() {
		return adrSubrisk;
	}
	public void setAdrSubrisk(String adrSubrisk) {
		this.adrSubrisk = adrSubrisk;
	}

	public String getWmsAcidBase() {
		return wmsAcidBase;
	}

	public void setWmsAcidBase(String wmsAcidBase) {
		this.wmsAcidBase = wmsAcidBase;
	}

	public String getWmsCorrosive() {
		return wmsCorrosive;
	}

	public void setWmsCorrosive(String wmsCorrosive) {
		this.wmsCorrosive = wmsCorrosive;
	}

	public String getPhysicalState() {
		return physicalState;
	}

	public void setPhysicalState(String physicalState) {
		this.physicalState = physicalState;
	}

	public String getWmsAerosol() {
		return wmsAerosol;
	}

	public void setWmsAerosol(String wmsAerosol) {
		this.wmsAerosol = wmsAerosol;
	}

	public String getWmsFlammable() {
		return wmsFlammable;
	}

	public void setWmsFlammable(String wmsFlammable) {
		this.wmsFlammable = wmsFlammable;
	}

	public String getWmsToxic() {
		return wmsToxic;
	}

	public void setWmsToxic(String wmsToxic) {
		this.wmsToxic = wmsToxic;
	}

	public String getWmsOxidizer() {
		return wmsOxidizer;
	}

	public void setWmsOxidizer(String wmsOxidizer) {
		this.wmsOxidizer = wmsOxidizer;
	}

	public String getWmsReactive() {
		return wmsReactive;
	}

	public void setWmsReactive(String wmsReactive) {
		this.wmsReactive = wmsReactive;
	}

	public String getWmsWaterReactive() {
		return wmsWaterReactive;
	}

	public void setWmsWaterReactive(String wmsWaterReactive) {
		this.wmsWaterReactive = wmsWaterReactive;
	}

	public String getWmsOrganicPeroxide() {
		return wmsOrganicPeroxide;
	}

	public void setWmsOrganicPeroxide(String wmsOrganicPeroxide) {
		this.wmsOrganicPeroxide = wmsOrganicPeroxide;
	}

	public String getWmsContainer() {
		return wmsContainer;
	}

	public void setWmsContainer(String wmsContainer) {
		this.wmsContainer = wmsContainer;
	}

	public String getWmsPyrophoric() {
		return wmsPyrophoric;
	}

	public void setWmsPyrophoric(String wmsPyrophoric) {
		this.wmsPyrophoric = wmsPyrophoric;
	}

	public String getWmsContPressureRelieving() {
		return wmsContPressureRelieving;
	}

	public void setWmsContPressureRelieving(String wmsContPressureRelieving) {
		this.wmsContPressureRelieving = wmsContPressureRelieving;
	}

	public String getWmsWaterMiscible() {
		return wmsWaterMiscible;
	}

	public void setWmsWaterMiscible(String wmsWaterMiscible) {
		this.wmsWaterMiscible = wmsWaterMiscible;
	}

}