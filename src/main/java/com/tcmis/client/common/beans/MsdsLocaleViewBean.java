package com.tcmis.client.common.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: MsdsLocaleViewBean <br>
 * @version: 1.0, Jul 11, 2011 <br>
 *****************************************************************************/


public class MsdsLocaleViewBean extends BaseDataBean {

	private String companyId;
	private BigDecimal materialId;
	private Date revisionDate;
	private String content;
	private String emergencyPhone;
	private String specificGravity;
	private String health;
	private String flammability;
	private String reactivity;
	private String specificHazard;
	private String compatibility;
	private String storageTemp;
	private String ppe;
	private String signalWord;
	private String targetOrgan;
	private String routeOfEntry;
	private String skin;
	private String eyes;
	private String inhalation;
	private String injection;
	private String boilingPoint;
	private String ph;
	private String freezingPoint;
	private String ingestion;
	private String onLine;
	private String density;
	private String densityUnit;
	private String physicalState;
	private String tsca12b;
	private String sara311312Acute;
	private String sara311312Chronic;
	private String sara311312Fire;
	private String sara311312Pressure;
	private String sara311312Reactivity;
	private String oshaHazard;
	private String tscaList;
	private String mixture;
	private String voc;
	private BigDecimal vocLower;
	private BigDecimal vocUpper;
	private String vocUnit;
	private String reviewedBy;
	private Date reviewDate;
	private String remark;
	private String flashPoint;
	private String flashPointUnit;
	private String solids;
	private BigDecimal solidsLower;
	private BigDecimal solidsUpper;
	private String solidsUnit;
	private BigDecimal solidsPercent;
	private BigDecimal solidsLowerPercent;
	private BigDecimal solidsUpperPercent;
	private BigDecimal vocLowerPercent;
	private BigDecimal vocUpperPercent;
	private BigDecimal vocPercent;
	private String altDataSource;
	private String personalProtection;
	private String hmisHealth;
	private String hmisFlammability;
	private String hmisReactivity;
	private String vocLessH2oExempt;
	private String vocLessH2oExemptUnit;
	private BigDecimal vocLbPerSolidLb;
	private String vocSource;
	private String vaporPressureSource;
	private String vocLessH20ExemptSource;
	private String vocLbPerSolidLbSource;
	private BigDecimal vocLessH2oExemptLower;
	private BigDecimal vocLessH2oExemptUpper;
	private BigDecimal vocLessH2oExemptLowerCalc;
	private BigDecimal vocLessH2oExemptCalc;
	private BigDecimal vocLessH2oExemptUpperCalc;
	private String vocLessH2oExemptUnitCalc;
	private BigDecimal vocLbPerSolidLbLowCalc;
	private BigDecimal vocLbPerSolidLbCalc;
	private BigDecimal vocLbPerSolidLbUpCalc;
	private BigDecimal vocLbPerSolidLbLow;
	private BigDecimal vocLbPerSolidLbUp;
	private String solidsSource;
	private String vaporPressure;
	private String vaporPressureUnit;
	private String vaporPressureDetect;
	private BigDecimal vaporPressureTemp;
	private String vaporPressureTempUnit;
	private BigDecimal vaporPressureLower;
	private BigDecimal vaporPressureUpper;
	private String frenchContent;
	private BigDecimal vocCompVaporPressureMmhg;
	private String localeDisplay;
	private BigDecimal localeId;
	private String tradeName;
	private String mfgDesc;
	private String revisionDateDisplay;
	private Date revisionDateDisplayShort;
	private String localeCode;
	private String chronic;
	private String lfcCode;
	private String polymerize;
	private String incompatible;
	private String corrosive;
	private String healthEffects;
	private String stable;
	private String vaporDensity;
	private String evaporationRate;
	private String fireConditionsToAvoid;
	private String alloy;
	private String phDetail;
	private String oriductCode;
	private String waterReactive;
	private String oxidizer;
	private String carcinogen;
	private String nfpaFromCustomer;
	private String productCode;
	
	private String revisionDateWithFormat;
	private String msdsNo;
	
	private String federalHazardClass;
	private String nanoMaterial;
	private String radioactive;
	private String radiaactiveCuries;
	private String hydrocarbon;
	private String miscible;
	private String pyrophoric;
	private String detonable;
	private String photoreactive;
	private String spontaneouslyCombustible;
	private String dataEntryComplete;
	private Date dateEntered;
	private String materialDesc;
	

	//constructor
	public MsdsLocaleViewBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setEmergencyPhone(String emergencyPhone) {
		this.emergencyPhone = emergencyPhone;
	}
	public void setSpecificGravity(String specificGravity) {
		this.specificGravity = specificGravity;
	}
	public void setHealth(String health) {
		this.health = health;
	}
	public void setFlammability(String flammability) {
		this.flammability = flammability;
	}
	public void setReactivity(String reactivity) {
		this.reactivity = reactivity;
	}
	public void setSpecificHazard(String specificHazard) {
		this.specificHazard = specificHazard;
	}
	public void setCompatibility(String compatibility) {
		this.compatibility = compatibility;
	}
	public void setStorageTemp(String storageTemp) {
		this.storageTemp = storageTemp;
	}
	public void setPpe(String ppe) {
		this.ppe = ppe;
	}
	public void setSignalWord(String signalWord) {
		this.signalWord = signalWord;
	}
	public void setTargetOrgan(String targetOrgan) {
		this.targetOrgan = targetOrgan;
	}
	public void setRouteOfEntry(String routeOfEntry) {
		this.routeOfEntry = routeOfEntry;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	public void setEyes(String eyes) {
		this.eyes = eyes;
	}
	public void setInhalation(String inhalation) {
		this.inhalation = inhalation;
	}
	public void setInjection(String injection) {
		this.injection = injection;
	}
	public void setBoilingPoint(String boilingPoint) {
		this.boilingPoint = boilingPoint;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public void setFreezingPoint(String freezingPoint) {
		this.freezingPoint = freezingPoint;
	}
	public void setIngestion(String ingestion) {
		this.ingestion = ingestion;
	}
	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}
	public void setDensity(String density) {
		this.density = density;
	}
	public void setDensityUnit(String densityUnit) {
		this.densityUnit = densityUnit;
	}
	public void setPhysicalState(String physicalState) {
		this.physicalState = physicalState;
	}
	public void setTsca12b(String tsca12b) {
		this.tsca12b = tsca12b;
	}
	public void setSara311312Acute(String sara311312Acute) {
		this.sara311312Acute = sara311312Acute;
	}
	public void setSara311312Chronic(String sara311312Chronic) {
		this.sara311312Chronic = sara311312Chronic;
	}
	public void setSara311312Fire(String sara311312Fire) {
		this.sara311312Fire = sara311312Fire;
	}
	public void setSara311312Pressure(String sara311312Pressure) {
		this.sara311312Pressure = sara311312Pressure;
	}
	public void setSara311312Reactivity(String sara311312Reactivity) {
		this.sara311312Reactivity = sara311312Reactivity;
	}
	public void setOshaHazard(String oshaHazard) {
		this.oshaHazard = oshaHazard;
	}
	public void setTscaList(String tscaList) {
		this.tscaList = tscaList;
	}
	public void setMixture(String mixture) {
		this.mixture = mixture;
	}
	public void setVoc(String voc) {
		this.voc = voc;
	}
	public void setVocLower(BigDecimal vocLower) {
		this.vocLower = vocLower;
	}
	public void setVocUpper(BigDecimal vocUpper) {
		this.vocUpper = vocUpper;
	}
	public void setVocUnit(String vocUnit) {
		this.vocUnit = vocUnit;
	}
	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setFlashPoint(String flashPoint) {
		this.flashPoint = flashPoint;
	}
	public void setFlashPointUnit(String flashPointUnit) {
		this.flashPointUnit = flashPointUnit;
	}
	public void setSolids(String solids) {
		this.solids = solids;
	}
	public void setSolidsLower(BigDecimal solidsLower) {
		this.solidsLower = solidsLower;
	}
	public void setSolidsUpper(BigDecimal solidsUpper) {
		this.solidsUpper = solidsUpper;
	}
	public void setSolidsUnit(String solidsUnit) {
		this.solidsUnit = solidsUnit;
	}
	public void setSolidsPercent(BigDecimal solidsPercent) {
		this.solidsPercent = solidsPercent;
	}
	public void setSolidsLowerPercent(BigDecimal solidsLowerPercent) {
		this.solidsLowerPercent = solidsLowerPercent;
	}
	public void setSolidsUpperPercent(BigDecimal solidsUpperPercent) {
		this.solidsUpperPercent = solidsUpperPercent;
	}
	public void setVocLowerPercent(BigDecimal vocLowerPercent) {
		this.vocLowerPercent = vocLowerPercent;
	}
	public void setVocUpperPercent(BigDecimal vocUpperPercent) {
		this.vocUpperPercent = vocUpperPercent;
	}
	public void setVocPercent(BigDecimal vocPercent) {
		this.vocPercent = vocPercent;
	}
	public void setAltDataSource(String altDataSource) {
		this.altDataSource = altDataSource;
	}
	public void setPersonalProtection(String personalProtection) {
		this.personalProtection = personalProtection;
	}
	public void setHmisHealth(String hmisHealth) {
		this.hmisHealth = hmisHealth;
	}
	public void setHmisFlammability(String hmisFlammability) {
		this.hmisFlammability = hmisFlammability;
	}
	public void setHmisReactivity(String hmisReactivity) {
		this.hmisReactivity = hmisReactivity;
	}
	public void setVocLessH2oExempt(String vocLessH2oExempt) {
		this.vocLessH2oExempt = vocLessH2oExempt;
	}
	public void setVocLessH2oExemptUnit(String vocLessH2oExemptUnit) {
		this.vocLessH2oExemptUnit = vocLessH2oExemptUnit;
	}
	public void setVocLbPerSolidLb(BigDecimal vocLbPerSolidLb) {
		this.vocLbPerSolidLb = vocLbPerSolidLb;
	}
	public void setVocSource(String vocSource) {
		this.vocSource = vocSource;
	}
	public void setVaporPressureSource(String vaporPressureSource) {
		this.vaporPressureSource = vaporPressureSource;
	}
	public void setVocLessH20ExemptSource(String vocLessH20ExemptSource) {
		this.vocLessH20ExemptSource = vocLessH20ExemptSource;
	}
	public void setVocLbPerSolidLbSource(String vocLbPerSolidLbSource) {
		this.vocLbPerSolidLbSource = vocLbPerSolidLbSource;
	}
	public void setVocLessH2oExemptLower(BigDecimal vocLessH2oExemptLower) {
		this.vocLessH2oExemptLower = vocLessH2oExemptLower;
	}
	public void setVocLessH2oExemptUpper(BigDecimal vocLessH2oExemptUpper) {
		this.vocLessH2oExemptUpper = vocLessH2oExemptUpper;
	}
	public void setVocLessH2oExemptLowerCalc(BigDecimal vocLessH2oExemptLowerCalc) {
		this.vocLessH2oExemptLowerCalc = vocLessH2oExemptLowerCalc;
	}
	public void setVocLessH2oExemptCalc(BigDecimal vocLessH2oExemptCalc) {
		this.vocLessH2oExemptCalc = vocLessH2oExemptCalc;
	}
	public void setVocLessH2oExemptUpperCalc(BigDecimal vocLessH2oExemptUpperCalc) {
		this.vocLessH2oExemptUpperCalc = vocLessH2oExemptUpperCalc;
	}
	public void setVocLessH2oExemptUnitCalc(String vocLessH2oExemptUnitCalc) {
		this.vocLessH2oExemptUnitCalc = vocLessH2oExemptUnitCalc;
	}
	public void setVocLbPerSolidLbLowCalc(BigDecimal vocLbPerSolidLbLowCalc) {
		this.vocLbPerSolidLbLowCalc = vocLbPerSolidLbLowCalc;
	}
	public void setVocLbPerSolidLbCalc(BigDecimal vocLbPerSolidLbCalc) {
		this.vocLbPerSolidLbCalc = vocLbPerSolidLbCalc;
	}
	public void setVocLbPerSolidLbUpCalc(BigDecimal vocLbPerSolidLbUpCalc) {
		this.vocLbPerSolidLbUpCalc = vocLbPerSolidLbUpCalc;
	}
	public void setSolidsSource(String solidsSource) {
		this.solidsSource = solidsSource;
	}
	public void setVaporPressure(String vaporPressure) {
		this.vaporPressure = vaporPressure;
	}
	public void setVaporPressureUnit(String vaporPressureUnit) {
		this.vaporPressureUnit = vaporPressureUnit;
	}
	public void setVaporPressureDetect(String vaporPressureDetect) {
		this.vaporPressureDetect = vaporPressureDetect;
	}
	public void setVaporPressureTemp(BigDecimal vaporPressureTemp) {
		this.vaporPressureTemp = vaporPressureTemp;
	}
	public void setVaporPressureTempUnit(String vaporPressureTempUnit) {
		this.vaporPressureTempUnit = vaporPressureTempUnit;
	}
	public void setVaporPressureLower(BigDecimal vaporPressureLower) {
		this.vaporPressureLower = vaporPressureLower;
	}
	public void setVaporPressureUpper(BigDecimal vaporPressureUpper) {
		this.vaporPressureUpper = vaporPressureUpper;
	}
	public void setFrenchContent(String frenchContent) {
		this.frenchContent = frenchContent;
	}
	public void setVocCompVaporPressureMmhg(BigDecimal vocCompVaporPressureMmhg) {
		this.vocCompVaporPressureMmhg = vocCompVaporPressureMmhg;
	}
	public void setLocaleDisplay(String localeDisplay) {
		this.localeDisplay = localeDisplay;
	}
	public void setLocaleId(BigDecimal localeId) {
		this.localeId = localeId;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}
	public void setRevisionDateDisplay(String revisionDateDisplay) {
		this.revisionDateDisplay = revisionDateDisplay;
	}
	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}
	public void setChronic(String chronic) {
		this.chronic = chronic;
	}
	public void setLfcCode(String lfcCode) {
		this.lfcCode = lfcCode;
	}
	public void setPolymerize(String polymerize) {
		this.polymerize = polymerize;
	}
	public void setIncompatible(String incompatible) {
		this.incompatible = incompatible;
	}
	public void setCorrosive(String corrosive) {
		this.corrosive = corrosive;
	}
	public void setHealthEffects(String healthEffects) {
		this.healthEffects = healthEffects;
	}
	public void setStable(String stable) {
		this.stable = stable;
	}
	public void setVaporDensity(String vaporDensity) {
		this.vaporDensity = vaporDensity;
	}
	public void setEvaporationRate(String evaporationRate) {
		this.evaporationRate = evaporationRate;
	}
	public void setFireConditionsToAvoid(String fireConditionsToAvoid) {
		this.fireConditionsToAvoid = fireConditionsToAvoid;
	}
	public void setAlloy(String alloy) {
		this.alloy = alloy;
	}
	public void setPhDetail(String phDetail) {
		this.phDetail = phDetail;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public Date getRevisionDate() {
		return revisionDate;
	}
	public String getContent() {
		return content;
	}
	public String getEmergencyPhone() {
		return emergencyPhone;
	}
	public String getSpecificGravity() {
		return specificGravity;
	}
	public String getHealth() {
		return health;
	}
	public String getFlammability() {
		return flammability;
	}
	public String getReactivity() {
		return reactivity;
	}
	public String getSpecificHazard() {
		return specificHazard;
	}
	public String getCompatibility() {
		return compatibility;
	}
	public String getStorageTemp() {
		return storageTemp;
	}
	public String getPpe() {
		return ppe;
	}
	public String getSignalWord() {
		return signalWord;
	}
	public String getTargetOrgan() {
		return targetOrgan;
	}
	public String getRouteOfEntry() {
		return routeOfEntry;
	}
	public String getSkin() {
		return skin;
	}
	public String getEyes() {
		return eyes;
	}
	public String getInhalation() {
		return inhalation;
	}
	public String getInjection() {
		return injection;
	}
	public String getBoilingPoint() {
		return boilingPoint;
	}
	public String getPh() {
		return ph;
	}
	public String getFreezingPoint() {
		return freezingPoint;
	}
	public String getIngestion() {
		return ingestion;
	}
	public String getOnLine() {
		return onLine;
	}
	public String getDensity() {
		return density;
	}
	public String getDensityUnit() {
		return densityUnit;
	}
	public String getPhysicalState() {
		return physicalState;
	}
	public String getTsca12b() {
		return tsca12b;
	}
	public String getSara311312Acute() {
		return sara311312Acute;
	}
	public String getSara311312Chronic() {
		return sara311312Chronic;
	}
	public String getSara311312Fire() {
		return sara311312Fire;
	}
	public String getSara311312Pressure() {
		return sara311312Pressure;
	}
	public String getSara311312Reactivity() {
		return sara311312Reactivity;
	}
	public String getOshaHazard() {
		return oshaHazard;
	}
	public String getTscaList() {
		return tscaList;
	}
	public String getMixture() {
		return mixture;
	}
	public String getVoc() {
		return voc;
	}
	public BigDecimal getVocLower() {
		return vocLower;
	}
	public BigDecimal getVocUpper() {
		return vocUpper;
	}
	public String getVocUnit() {
		return vocUnit;
	}
	public String getReviewedBy() {
		return reviewedBy;
	}
	public Date getReviewDate() {
		return reviewDate;
	}
	public String getRemark() {
		return remark;
	}
	public String getFlashPoint() {
		return flashPoint;
	}
	public String getFlashPointUnit() {
		return flashPointUnit;
	}
	public String getSolids() {
		return solids;
	}
	public BigDecimal getSolidsLower() {
		return solidsLower;
	}
	public BigDecimal getSolidsUpper() {
		return solidsUpper;
	}
	public String getSolidsUnit() {
		return solidsUnit;
	}
	public BigDecimal getSolidsPercent() {
		return solidsPercent;
	}
	public BigDecimal getSolidsLowerPercent() {
		return solidsLowerPercent;
	}
	public BigDecimal getSolidsUpperPercent() {
		return solidsUpperPercent;
	}
	public BigDecimal getVocLowerPercent() {
		return vocLowerPercent;
	}
	public BigDecimal getVocUpperPercent() {
		return vocUpperPercent;
	}
	public BigDecimal getVocPercent() {
		return vocPercent;
	}
	public String getAltDataSource() {
		return altDataSource;
	}
	public String getPersonalProtection() {
		return personalProtection;
	}
	public String getHmisHealth() {
		return hmisHealth;
	}
	public String getHmisFlammability() {
		return hmisFlammability;
	}
	public String getHmisReactivity() {
		return hmisReactivity;
	}
	public String getVocLessH2oExempt() {
		return vocLessH2oExempt;
	}
	public String getVocLessH2oExemptUnit() {
		return vocLessH2oExemptUnit;
	}
	public BigDecimal getVocLbPerSolidLb() {
		return vocLbPerSolidLb;
	}
	public String getVocSource() {
		return vocSource;
	}
	public String getVaporPressureSource() {
		return vaporPressureSource;
	}
	public String getVocLessH20ExemptSource() {
		return vocLessH20ExemptSource;
	}
	public String getVocLbPerSolidLbSource() {
		return vocLbPerSolidLbSource;
	}
	public BigDecimal getVocLessH2oExemptLower() {
		return vocLessH2oExemptLower;
	}
	public BigDecimal getVocLessH2oExemptUpper() {
		return vocLessH2oExemptUpper;
	}
	public BigDecimal getVocLessH2oExemptLowerCalc() {
		return vocLessH2oExemptLowerCalc;
	}
	public BigDecimal getVocLessH2oExemptCalc() {
		return vocLessH2oExemptCalc;
	}
	public BigDecimal getVocLessH2oExemptUpperCalc() {
		return vocLessH2oExemptUpperCalc;
	}
	public String getVocLessH2oExemptUnitCalc() {
		return vocLessH2oExemptUnitCalc;
	}
	public BigDecimal getVocLbPerSolidLbLowCalc() {
		return vocLbPerSolidLbLowCalc;
	}
	public BigDecimal getVocLbPerSolidLbCalc() {
		return vocLbPerSolidLbCalc;
	}
	public BigDecimal getVocLbPerSolidLbUpCalc() {
		return vocLbPerSolidLbUpCalc;
	}
	public String getSolidsSource() {
		return solidsSource;
	}
	public String getVaporPressure() {
		return vaporPressure;
	}
	public String getVaporPressureUnit() {
		return vaporPressureUnit;
	}
	public String getVaporPressureDetect() {
		return vaporPressureDetect;
	}
	public BigDecimal getVaporPressureTemp() {
		return vaporPressureTemp;
	}
	public String getVaporPressureTempUnit() {
		return vaporPressureTempUnit;
	}
	public BigDecimal getVaporPressureLower() {
		return vaporPressureLower;
	}
	public BigDecimal getVaporPressureUpper() {
		return vaporPressureUpper;
	}
	public String getFrenchContent() {
		return frenchContent;
	}
	public BigDecimal getVocCompVaporPressureMmhg() {
		return vocCompVaporPressureMmhg;
	}
	public String getLocaleDisplay() {
		return localeDisplay;
	}
	public BigDecimal getLocaleId() {
		return localeId;
	}
	public String getTradeName() {
		return tradeName;
	}
	public String getMfgDesc() {
		return mfgDesc;
	}
	public String getRevisionDateDisplay() {
		return revisionDateDisplay;
	}
	public String getLocaleCode() {
		return localeCode;
	}
	public String getChronic() {
		return chronic;
	}
	public String getLfcCode() {
		return lfcCode;
	}
	public String getPolymerize() {
		return polymerize;
	}
	public String getIncompatible() {
		return incompatible;
	}
	public String getCorrosive() {
		return corrosive;
	}
	public String getHealthEffects() {
		return healthEffects;
	}
	public String getStable() {
		return stable;
	}
	public String getVaporDensity() {
		return vaporDensity;
	}
	public String getEvaporationRate() {
		return evaporationRate;
	}
	public String getFireConditionsToAvoid() {
		return fireConditionsToAvoid;
	}
	public String getAlloy() {
		return alloy;
	}
	public String getPhDetail() {
		return phDetail;
	}

	public String getRevisionDateWithFormat() {
		return revisionDateWithFormat;
	}

	public void setRevisionDateWithFormat(String revisionDateWithFormat) {
		this.revisionDateWithFormat = revisionDateWithFormat;
	}

	public String getMsdsNo() {
		return msdsNo;
	}

	public void setMsdsNo(String msdsNo) {
		this.msdsNo = msdsNo;
	}

	public String getCarcinogen() {
		return carcinogen;
	}

	public void setCarcinogen(String carcinogen) {
		this.carcinogen = carcinogen;
	}

	public String getOriductCode() {
		return oriductCode;
	}

	public void setOriductCode(String oriductCode) {
		this.oriductCode = oriductCode;
	}

	public String getOxidizer() {
		return oxidizer;
	}

	public void setOxidizer(String oxidizer) {
		this.oxidizer = oxidizer;
	}

	public String getWaterReactive() {
		return waterReactive;
	}

	public void setWaterReactive(String waterReactive) {
		this.waterReactive = waterReactive;
	}

	public String getNfpaFromCustomer() {
		return nfpaFromCustomer;
	}

	public void setNfpaFromCustomer(String nfpaFromCustomer) {
		this.nfpaFromCustomer = nfpaFromCustomer;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public BigDecimal getVocLbPerSolidLbLow() {
		return vocLbPerSolidLbLow;
	}

	public void setVocLbPerSolidLbLow(BigDecimal vocLbPerSolidLbLow) {
		this.vocLbPerSolidLbLow = vocLbPerSolidLbLow;
	}

	public BigDecimal getVocLbPerSolidLbUp() {
		return vocLbPerSolidLbUp;
	}

	public void setVocLbPerSolidLbUp(BigDecimal vocLbPerSolidLbUp) {
		this.vocLbPerSolidLbUp = vocLbPerSolidLbUp;
	}

	public String getDataEntryComplete() {
		return dataEntryComplete;
	}

	public void setDataEntryComplete(String dataEntryComplete) {
		this.dataEntryComplete = dataEntryComplete;
	}

	public Date getDateEntered() {
		return dateEntered;
	}

	public void setDateEntered(Date dateEntered) {
		this.dateEntered = dateEntered;
	}

	public String getDetonable() {
		return detonable;
	}

	public void setDetonable(String detonable) {
		this.detonable = detonable;
	}

	public String getFederalHazardClass() {
		return federalHazardClass;
	}

	public void setFederalHazardClass(String federalHazardClass) {
		this.federalHazardClass = federalHazardClass;
	}

	public String getHydrocarbon() {
		return hydrocarbon;
	}

	public void setHydrocarbon(String hydrocarbon) {
		this.hydrocarbon = hydrocarbon;
	}

	public String getMiscible() {
		return miscible;
	}

	public void setMiscible(String miscible) {
		this.miscible = miscible;
	}

	public String getNanoMaterial() {
		return nanoMaterial;
	}

	public void setNanoMaterial(String nanoMaterial) {
		this.nanoMaterial = nanoMaterial;
	}

	public String getPhotoreactive() {
		return photoreactive;
	}

	public void setPhotoreactive(String photoreactive) {
		this.photoreactive = photoreactive;
	}

	public String getPyrophoric() {
		return pyrophoric;
	}

	public void setPyrophoric(String pyrophoric) {
		this.pyrophoric = pyrophoric;
	}

	public String getRadiaactiveCuries() {
		return radiaactiveCuries;
	}

	public void setRadiaactiveCuries(String radiaactiveCuries) {
		this.radiaactiveCuries = radiaactiveCuries;
	}

	public String getRadioactive() {
		return radioactive;
	}

	public void setRadioactive(String radioactive) {
		this.radioactive = radioactive;
	}

	public String getSpontaneouslyCombustible() {
		return spontaneouslyCombustible;
	}

	public void setSpontaneouslyCombustible(String spontaneouslyCombustible) {
		this.spontaneouslyCombustible = spontaneouslyCombustible;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public Date getRevisionDateDisplayShort() {
		return revisionDateDisplayShort;
	}

	public void setRevisionDateDisplayShort(Date revisionDateDisplayShort) {
		this.revisionDateDisplayShort = revisionDateDisplayShort;
	}

}