package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class MsdsIndexingQcBean extends BaseDataBean {

	private BigDecimal materialId;
	private Date revisionDate;
	private String companyId;
	private Date insertDate;
	private Date submitDate;
	private BigDecimal submitBy;
	private Date approveDate;
	private BigDecimal approveBy;
	
	private BigDecimal alloyQcErrorType;
	private BigDecimal msdsAuthorQcErrorType;
	private BigDecimal carcinogenQcErrorType;
	private BigDecimal chronicQcErrorType;
	private BigDecimal compositionQcErrorType;
	private BigDecimal corrosiveQcErrorType;
	private BigDecimal materialDescriptionQcErrorType;
	private BigDecimal evaporationRateQcErrorType;
	private BigDecimal fireConditionsToAvoidQcErrorType;
	private BigDecimal labelAddressQcErrorType;
	private BigDecimal hazardStatementsQcErrorType;
	private BigDecimal healthEffectsQcErrorType;
	private BigDecimal contentQcErrorType;
	private BigDecimal incompatibleQcErrorType;
	private BigDecimal manufacturerQcErrorType;
	private BigDecimal precautionaryStatementsQcErrorType;
	private BigDecimal polymerizeQcErrorType;
	private BigDecimal productCodeQcErrorType;
	private BigDecimal revisionDateQcErrorType;
	private BigDecimal saraQcErrorType;
	private BigDecimal oxidizerQcErrorType;
	private BigDecimal signalWordQcErrorType;
	private BigDecimal stableQcErrorType;
	private BigDecimal tradeNameQcErrorType;
	private BigDecimal tscaQcErrorType;
	private BigDecimal vaporDensityQcErrorType;
	private BigDecimal waterReactiveQcErrorType;
	private BigDecimal ghsPictogramsQcErrorType;
	private BigDecimal nfpaQcErrorType;
	private BigDecimal hmisQcErrorType;
	private BigDecimal phQcErrorType;
	private BigDecimal physicalStateQcErrorType;
	private BigDecimal densityQcErrorType;
	private BigDecimal flashPointQcErrorType;
	private BigDecimal boilingPointQcErrorType;
	private BigDecimal specificGravityQcErrorType;
	private BigDecimal vaporPressureQcErrorType;
	private BigDecimal solidsQcErrorType;
	private BigDecimal vocQcErrorType;
	private BigDecimal vocLessH2oExemptQcErrorType;
	private BigDecimal detonableQcErrorType;
	private BigDecimal localizedMaterialDescQcErrorType;
	private BigDecimal localizedTradeNameQcErrorType;
	
	public BigDecimal getMaterialId() {
		return materialId;
	}
	
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public Date getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public BigDecimal getSubmitBy() {
		return submitBy;
	}

	public void setSubmitBy(BigDecimal submitBy) {
		this.submitBy = submitBy;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public BigDecimal getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(BigDecimal approveBy) {
		this.approveBy = approveBy;
	}

	public BigDecimal getGhsPictogramsQcErrorType() {
		return ghsPictogramsQcErrorType;
	}

	public void setGhsPictogramsQcErrorType(BigDecimal ghsPictogramsQcErrorType) {
		this.ghsPictogramsQcErrorType = ghsPictogramsQcErrorType;
	}

	public BigDecimal getNfpaQcErrorType() {
		return nfpaQcErrorType;
	}

	public void setNfpaQcErrorType(BigDecimal nfpaQcErrorType) {
		this.nfpaQcErrorType = nfpaQcErrorType;
	}

	public BigDecimal getHmisQcErrorType() {
		return hmisQcErrorType;
	}

	public void setHmisQcErrorType(BigDecimal hmisQcErrorType) {
		this.hmisQcErrorType = hmisQcErrorType;
	}

	public BigDecimal getPhQcErrorType() {
		return phQcErrorType;
	}

	public void setPhQcErrorType(BigDecimal phQcErrorType) {
		this.phQcErrorType = phQcErrorType;
	}

	public BigDecimal getPhysicalStateQcErrorType() {
		return physicalStateQcErrorType;
	}

	public void setPhysicalStateQcErrorType(BigDecimal physicalStateQcErrorType) {
		this.physicalStateQcErrorType = physicalStateQcErrorType;
	}

	public BigDecimal getDensityQcErrorType() {
		return densityQcErrorType;
	}

	public void setDensityQcErrorType(BigDecimal densityQcErrorType) {
		this.densityQcErrorType = densityQcErrorType;
	}

	public BigDecimal getFlashPointQcErrorType() {
		return flashPointQcErrorType;
	}

	public void setFlashPointQcErrorType(BigDecimal flashPointQcErrorType) {
		this.flashPointQcErrorType = flashPointQcErrorType;
	}

	public BigDecimal getBoilingPointQcErrorType() {
		return boilingPointQcErrorType;
	}

	public void setBoilingPointQcErrorType(BigDecimal boilingPointQcErrorType) {
		this.boilingPointQcErrorType = boilingPointQcErrorType;
	}

	public BigDecimal getSpecificGravityQcErrorType() {
		return specificGravityQcErrorType;
	}

	public void setSpecificGravityQcErrorType(BigDecimal specificGravityQcErrorType) {
		this.specificGravityQcErrorType = specificGravityQcErrorType;
	}

	public BigDecimal getVaporPressureQcErrorType() {
		return vaporPressureQcErrorType;
	}

	public void setVaporPressureQcErrorType(BigDecimal vaporPressureQcErrorType) {
		this.vaporPressureQcErrorType = vaporPressureQcErrorType;
	}

	public BigDecimal getSolidsQcErrorType() {
		return solidsQcErrorType;
	}

	public void setSolidsQcErrorType(BigDecimal solidsQcErrorType) {
		this.solidsQcErrorType = solidsQcErrorType;
	}

	public BigDecimal getVocQcErrorType() {
		return vocQcErrorType;
	}

	public void setVocQcErrorType(BigDecimal vocQcErrorType) {
		this.vocQcErrorType = vocQcErrorType;
	}

	public BigDecimal getVocLessH2oExemptQcErrorType() {
		return vocLessH2oExemptQcErrorType;
	}

	public void setVocLessH2oExemptQcErrorType(BigDecimal vocLessH2oExemptQcErrorType) {
		this.vocLessH2oExemptQcErrorType = vocLessH2oExemptQcErrorType;
	}

	public BigDecimal getAlloyQcErrorType() {
		return alloyQcErrorType;
	}

	public void setAlloyQcErrorType(BigDecimal alloyQcErrorType) {
		this.alloyQcErrorType = alloyQcErrorType;
	}

	public BigDecimal getMsdsAuthorQcErrorType() {
		return msdsAuthorQcErrorType;
	}

	public void setMsdsAuthorQcErrorType(BigDecimal msdsAuthorQcErrorType) {
		this.msdsAuthorQcErrorType = msdsAuthorQcErrorType;
	}

	public BigDecimal getCarcinogenQcErrorType() {
		return carcinogenQcErrorType;
	}

	public void setCarcinogenQcErrorType(BigDecimal carcinogenQcErrorType) {
		this.carcinogenQcErrorType = carcinogenQcErrorType;
	}

	public BigDecimal getChronicQcErrorType() {
		return chronicQcErrorType;
	}

	public void setChronicQcErrorType(BigDecimal chronicQcErrorType) {
		this.chronicQcErrorType = chronicQcErrorType;
	}

	public BigDecimal getCompositionQcErrorType() {
		return compositionQcErrorType;
	}

	public void setCompositionQcErrorType(BigDecimal compositionQcErrorType) {
		this.compositionQcErrorType = compositionQcErrorType;
	}

	public BigDecimal getCorrosiveQcErrorType() {
		return corrosiveQcErrorType;
	}

	public void setCorrosiveQcErrorType(BigDecimal corrosiveQcErrorType) {
		this.corrosiveQcErrorType = corrosiveQcErrorType;
	}

	public BigDecimal getMaterialDescriptionQcErrorType() {
		return materialDescriptionQcErrorType;
	}

	public void setMaterialDescriptionQcErrorType(BigDecimal materialDescriptionQcErrorType) {
		this.materialDescriptionQcErrorType = materialDescriptionQcErrorType;
	}

	public BigDecimal getEvaporationRateQcErrorType() {
		return evaporationRateQcErrorType;
	}

	public void setEvaporationRateQcErrorType(BigDecimal evaporationRateQcErrorType) {
		this.evaporationRateQcErrorType = evaporationRateQcErrorType;
	}

	public BigDecimal getFireConditionsToAvoidQcErrorType() {
		return fireConditionsToAvoidQcErrorType;
	}

	public void setFireConditionsToAvoidQcErrorType(BigDecimal fireConditionsToAvoidQcErrorType) {
		this.fireConditionsToAvoidQcErrorType = fireConditionsToAvoidQcErrorType;
	}

	public BigDecimal getLabelAddressQcErrorType() {
		return labelAddressQcErrorType;
	}

	public void setLabelAddressQcErrorType(BigDecimal labelAddressQcErrorType) {
		this.labelAddressQcErrorType = labelAddressQcErrorType;
	}

	public BigDecimal getHazardStatementsQcErrorType() {
		return hazardStatementsQcErrorType;
	}

	public void setHazardStatementsQcErrorType(BigDecimal hazardStatementsQcErrorType) {
		this.hazardStatementsQcErrorType = hazardStatementsQcErrorType;
	}

	public BigDecimal getHealthEffectsQcErrorType() {
		return healthEffectsQcErrorType;
	}

	public void setHealthEffectsQcErrorType(BigDecimal healthEffectsQcErrorType) {
		this.healthEffectsQcErrorType = healthEffectsQcErrorType;
	}

	public BigDecimal getContentQcErrorType() {
		return contentQcErrorType;
	}

	public void setContentQcErrorType(BigDecimal contentQcErrorType) {
		this.contentQcErrorType = contentQcErrorType;
	}

	public BigDecimal getIncompatibleQcErrorType() {
		return incompatibleQcErrorType;
	}

	public void setIncompatibleQcErrorType(BigDecimal incompatibleQcErrorType) {
		this.incompatibleQcErrorType = incompatibleQcErrorType;
	}

	public BigDecimal getManufacturerQcErrorType() {
		return manufacturerQcErrorType;
	}

	public void setManufacturerQcErrorType(BigDecimal manufacturerQcErrorType) {
		this.manufacturerQcErrorType = manufacturerQcErrorType;
	}

	public BigDecimal getPrecautionaryStatementsQcErrorType() {
		return precautionaryStatementsQcErrorType;
	}

	public void setPrecautionaryStatementsQcErrorType(BigDecimal precautionaryStatementsQcErrorType) {
		this.precautionaryStatementsQcErrorType = precautionaryStatementsQcErrorType;
	}

	public BigDecimal getPolymerizeQcErrorType() {
		return polymerizeQcErrorType;
	}

	public void setPolymerizeQcErrorType(BigDecimal polymerizeQcErrorType) {
		this.polymerizeQcErrorType = polymerizeQcErrorType;
	}

	public BigDecimal getProductCodeQcErrorType() {
		return productCodeQcErrorType;
	}

	public void setProductCodeQcErrorType(BigDecimal productCodeQcErrorType) {
		this.productCodeQcErrorType = productCodeQcErrorType;
	}

	public BigDecimal getRevisionDateQcErrorType() {
		return revisionDateQcErrorType;
	}

	public void setRevisionDateQcErrorType(BigDecimal revisionDateQcErrorType) {
		this.revisionDateQcErrorType = revisionDateQcErrorType;
	}

	public BigDecimal getSaraQcErrorType() {
		return saraQcErrorType;
	}

	public void setSaraQcErrorType(BigDecimal saraQcErrorType) {
		this.saraQcErrorType = saraQcErrorType;
	}

	public BigDecimal getOxidizerQcErrorType() {
		return oxidizerQcErrorType;
	}

	public void setOxidizerQcErrorType(BigDecimal oxidizerQcErrorType) {
		this.oxidizerQcErrorType = oxidizerQcErrorType;
	}

	public BigDecimal getSignalWordQcErrorType() {
		return signalWordQcErrorType;
	}

	public void setSignalWordQcErrorType(BigDecimal signalWordQcErrorType) {
		this.signalWordQcErrorType = signalWordQcErrorType;
	}

	public BigDecimal getStableQcErrorType() {
		return stableQcErrorType;
	}

	public void setStableQcErrorType(BigDecimal stableQcErrorType) {
		this.stableQcErrorType = stableQcErrorType;
	}

	public BigDecimal getTradeNameQcErrorType() {
		return tradeNameQcErrorType;
	}

	public void setTradeNameQcErrorType(BigDecimal tradeNameQcErrorType) {
		this.tradeNameQcErrorType = tradeNameQcErrorType;
	}

	public BigDecimal getTscaQcErrorType() {
		return tscaQcErrorType;
	}

	public void setTscaQcErrorType(BigDecimal tscaQcErrorType) {
		this.tscaQcErrorType = tscaQcErrorType;
	}

	public BigDecimal getVaporDensityQcErrorType() {
		return vaporDensityQcErrorType;
	}

	public void setVaporDensityQcErrorType(BigDecimal vaporDensityQcErrorType) {
		this.vaporDensityQcErrorType = vaporDensityQcErrorType;
	}

	public BigDecimal getWaterReactiveQcErrorType() {
		return waterReactiveQcErrorType;
	}

	public void setWaterReactiveQcErrorType(BigDecimal waterReactiveQcErrorType) {
		this.waterReactiveQcErrorType = waterReactiveQcErrorType;
	}

	public BigDecimal getDetonableQcErrorType() {
		return detonableQcErrorType;
	}

	public void setDetonableQcErrorType(BigDecimal detonableQcErrorType) {
		this.detonableQcErrorType = detonableQcErrorType;
	}

	public BigDecimal getLocalizedMaterialDescQcErrorType() {
		return localizedMaterialDescQcErrorType;
	}

	public void setLocalizedMaterialDescQcErrorType(BigDecimal localizedMaterialDescQcErrorType) {
		this.localizedMaterialDescQcErrorType = localizedMaterialDescQcErrorType;
	}

	public BigDecimal getLocalizedTradeNameQcErrorType() {
		return localizedTradeNameQcErrorType;
	}

	public void setLocalizedTradeNameQcErrorType(BigDecimal localizedTradeNameQcErrorType) {
		this.localizedTradeNameQcErrorType = localizedTradeNameQcErrorType;
	}
}
