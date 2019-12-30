package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.internal.catalog.beans.CompositionBean;
import com.tcmis.internal.catalog.beans.GHSStatementsViewBean;
import com.tcmis.internal.catalog.beans.ManufacturerBean;
import com.tcmis.internal.catalog.beans.PictogramBean;

public class MsdsIndexingBean extends BaseDataBean {
	
	private ManufacturerBean mfg;
	private com.tcmis.internal.catalog.beans.MaterialBean material;
	private com.tcmis.internal.catalog.beans.MaterialBean localizedMaterial;
	
	private BigDecimal materialId;
	private Date revisionDate;
	private String revisionDateDisplay;
	private String localeCode;
	private String localeDisplay;
	private String companyId;
	
	private BigDecimal msdsAuthorId;
	private String msdsAuthorDesc;
	private String msdsAuthorCountryAbbrev;
	private String msdsAuthorNotes;
	private String content;
	private String idOnly;
	private BigDecimal msdsIndexingPriorityId;
	private boolean dataEntryComplete;
	private BigDecimal msdsDataEntryStandard;
	private boolean ghsCompliantImage;
	private String componentMsds;
	private String comments;
	
	private String signalWord;
	private String labelCompanyName;
	private String labelAddressLine1;
	private String labelAddressLine2;
	private String labelAddressLine3;
	private String labelAddressLine4;
	private String labelCity;
	private String labelCountryAbbrev;
	private String labelStateAbbrev;
	private String labelZip;
	private String labelPhone;
	private BigDecimal msdsId;
	private boolean ghsHazard;
	private Collection<GHSStatementsViewBean> hazardStmts;
	private Collection<GHSStatementsViewBean> precautionaryStmts;
	private Collection<PictogramBean> pictograms;
	
	private String health;
	private String flammability;
	private String reactivity;
	private String specificHazard;
	private String nfpaSource;
	
	private String hmisHealth;
	private String hmisFlammability;
	private String hmisReactivity;
	private String personalProtection;
	private String hmisChronic;
	private String hmisSource;
	
	private String phDetect;
	private BigDecimal ph;
	private BigDecimal phUpper;
	private String phDetail;
	private String phSource;
	
	private String physicalState;
	private String physicalStateSource;
	
	private String sara311312Acute;
	private String sara311312Chronic;
	private String sara311312Fire;
	private String sara311312Pressure;
	private String sara311312Reactivity;
	private String vaporDensity;
	private String evaporationRate;
	
	private String densityDetect;
	private BigDecimal density;
	private BigDecimal densityUpper;
	private String densityUnit;
	private String densitySource;

	private String flashPointDetect;
	private BigDecimal flashPointLower;
	private BigDecimal flashPointUpper;
	private String flashPointUnit;
	private String flashPointMethod;
	private String flashPointSource;
	
	private String boilingPointDetect;
	private BigDecimal boilingPointLower;
	private BigDecimal boilingPointUpper;
	private String boilingPointUnit;
	private String boilingPointDetail;
	private String boilingPointSource;
	
	private String specificGravityDetect;
	private BigDecimal specificGravityLower;
	private BigDecimal specificGravityUpper;
	private String specificGravityBasis;
	private String specificGravitySource;
	
	private String vaporPressureDetect;
	private BigDecimal vaporPressure;
	private BigDecimal vaporPressureUpper;
	private String vaporPressureUnit;
	private BigDecimal vaporPressureTemp;
	private String vaporPressureTempUnit;
	private String vaporPressureSource;
	
	private BigDecimal solids;
	private BigDecimal solidsLower;
	private BigDecimal solidsUpper;
	private String solidsUnit;
	private String solidsSource;
	
	private BigDecimal voc;
	private BigDecimal vocLower;
	private BigDecimal vocUpper;
	private String vocUnit;
	private String vocSource;
	
	private BigDecimal vocLessH2oExempt;
	private BigDecimal vocLessH2oExemptLower;
	private BigDecimal vocLessH2oExemptUpper;
	private String vocLessH2oExemptUnit;
	private String vocLessH2oExemptSource;
	
	private MsdsIndexingBean coData;
	
	private String alloy;
	private String carcinogen;
	private String chronic;
	private String corrosive;
	private String detonable;
	private String fireConditionsToAvoid;
	private String healthEffects;
	private String incompatible;
	private String lowVolumeExempt;
	private String miscible;
	private String oxidizer;
	private String ozoneDepletingCompound;
	private String polymerize;
	private String pyrophoric;
	private String spontaneouslyCombustible;
	private String stable;
	private String tscaStatement;
	private String waterReactive;
	private String asMixed;
	
	private Collection<CompositionBean> compositionData;
	
	private String remark;
	private String altDataSource;
	
	private String imageLocaleCode;
	private boolean saveCustomerOverride;
	private String onLine;
	private String reviewedBy;
	private BigDecimal verifiedBy;
	private Date verifiedOn;
	private Date insertDate;
	
	private MsdsIndexingQcBean qcData;
	
	private Date sourceRevisionDate;
	private String sourceRevisionDateDisplay;
	private String sourceContent;
	private String authoringType;
	private String sdsRequired;
	
	public MsdsIndexingBean() {
		this.mfg = new ManufacturerBean();
		this.material = new com.tcmis.internal.catalog.beans.MaterialBean();
		this.localizedMaterial = new com.tcmis.internal.catalog.beans.MaterialBean();
		this.qcData = new MsdsIndexingQcBean();
	}
	
	public ManufacturerBean getMfg() {
		return mfg;
	}

	public void setMfg(ManufacturerBean mfg) {
		this.mfg = mfg;
	}

	public com.tcmis.internal.catalog.beans.MaterialBean getMaterial() {
		return material;
	}

	public void setMaterial(com.tcmis.internal.catalog.beans.MaterialBean material) {
		this.material = material;
	}
	
	public com.tcmis.internal.catalog.beans.MaterialBean getLocalizedMaterial() {
		return localizedMaterial;
	}
	
	public void setLocalizedMaterial(com.tcmis.internal.catalog.beans.MaterialBean material) {
		this.localizedMaterial = material;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}
	
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
		if (this.material != null) {
			material.setMaterialId(materialId);
		}
	}
	
	public Date getRevisionDate() {
		return revisionDate;
	}
	
	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}
	
	public String getRevisionDateDisplay() {
		return revisionDateDisplay;
	}

	public void setRevisionDateDisplay(String revisionDateDisplay) {
		this.revisionDateDisplay = revisionDateDisplay;
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getMsdsAuthorId() {
		return msdsAuthorId;
	}
	
	public void setMsdsAuthorId(BigDecimal msdsAuthorId) {
		this.msdsAuthorId = msdsAuthorId;
	}
	
	public String getMsdsAuthorDesc() {
		return msdsAuthorDesc;
	}
	
	public void setMsdsAuthorDesc(String msdsAuthorDesc) {
		this.msdsAuthorDesc = msdsAuthorDesc;
	}
	
	public String getMsdsAuthorCountryAbbrev() {
		return msdsAuthorCountryAbbrev;
	}
	
	public void setMsdsAuthorCountryAbbrev(String msdsAuthorCountryAbbrev) {
		this.msdsAuthorCountryAbbrev = msdsAuthorCountryAbbrev;
	}
	
	public String getMsdsAuthorNotes() {
		return msdsAuthorNotes;
	}
	
	public void setMsdsAuthorNotes(String msdsAuthorNotes) {
		this.msdsAuthorNotes = msdsAuthorNotes;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getIdOnly() {
		return idOnly;
	}
	
	public void setIdOnly(String idOnly) {
		this.idOnly = idOnly;
	}
	
	public BigDecimal getMsdsIndexingPriorityId() {
		return msdsIndexingPriorityId;
	}

	public void setMsdsIndexingPriorityId(BigDecimal msdsIndexingPriorityId) {
		this.msdsIndexingPriorityId = msdsIndexingPriorityId;
	}

	public boolean isDataEntryComplete() {
		return dataEntryComplete;
	}
	
	public void setDataEntryComplete(boolean dataEntryComplete) {
		this.dataEntryComplete = dataEntryComplete;
	}
	
	public BigDecimal getMsdsDataEntryStandard() {
		return msdsDataEntryStandard;
	}

	public void setMsdsDataEntryStandard(BigDecimal dataEntryStandard) {
		this.msdsDataEntryStandard = dataEntryStandard;
	}

	public boolean isGhsCompliantImage() {
		return ghsCompliantImage;
	}
	
	public void setGhsCompliantImage(boolean ghsCompliantImage) {
		this.ghsCompliantImage = ghsCompliantImage;
	}
	
	public String getComponentMsds() {
		return componentMsds;
	}

	public void setComponentMsds(String componentMsds) {
		this.componentMsds = componentMsds;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getSignalWord() {
		return signalWord;
	}

	public void setSignalWord(String signalWord) {
		this.signalWord = signalWord;
	}

	public String getLabelCompanyName() {
		return labelCompanyName;
	}

	public void setLabelCompanyName(String labelCompanyName) {
		this.labelCompanyName = labelCompanyName;
	}

	public String getLabelAddressLine1() {
		return labelAddressLine1;
	}

	public void setLabelAddressLine1(String labelAddressLine1) {
		this.labelAddressLine1 = labelAddressLine1;
	}

	public String getLabelAddressLine2() {
		return labelAddressLine2;
	}

	public void setLabelAddressLine2(String labelAddressLine2) {
		this.labelAddressLine2 = labelAddressLine2;
	}

	public String getLabelAddressLine3() {
		return labelAddressLine3;
	}

	public void setLabelAddressLine3(String labelAddressLine3) {
		this.labelAddressLine3 = labelAddressLine3;
	}

	public String getLabelAddressLine4() {
		return labelAddressLine4;
	}

	public void setLabelAddressLine4(String labelAddressLine4) {
		this.labelAddressLine4 = labelAddressLine4;
	}

	public String getLabelCity() {
		return labelCity;
	}

	public void setLabelCity(String labelCity) {
		this.labelCity = labelCity;
	}

	public String getLabelCountryAbbrev() {
		return labelCountryAbbrev;
	}

	public void setLabelCountryAbbrev(String labelCountryAbbrev) {
		this.labelCountryAbbrev = labelCountryAbbrev;
	}

	public String getLabelStateAbbrev() {
		return labelStateAbbrev;
	}

	public void setLabelStateAbbrev(String labelStateAbbrev) {
		this.labelStateAbbrev = labelStateAbbrev;
	}

	public String getLabelZip() {
		return labelZip;
	}

	public void setLabelZip(String labelZip) {
		this.labelZip = labelZip;
	}

	public String getLabelPhone() {
		return labelPhone;
	}

	public void setLabelPhone(String labelPhone) {
		this.labelPhone = labelPhone;
	}

	public BigDecimal getMsdsId() {
		return msdsId;
	}

	public void setMsdsId(BigDecimal msdsId) {
		this.msdsId = msdsId;
	}

	public boolean isGhsHazard() {
		return ghsHazard;
	}

	public void setGhsHazard(boolean ghsHazard) {
		this.ghsHazard = ghsHazard;
	}

	public Collection<GHSStatementsViewBean> getHazardStmts() {
		return hazardStmts;
	}

	public void setHazardStmts(Collection<GHSStatementsViewBean> hazardStmts) {
		this.hazardStmts = hazardStmts;
	}

	public Collection<GHSStatementsViewBean> getPrecautionaryStmts() {
		return precautionaryStmts;
	}

	public void setPrecautionaryStmts(
			Collection<GHSStatementsViewBean> precautionaryStmts) {
		this.precautionaryStmts = precautionaryStmts;
	}

	public Collection<PictogramBean> getPictograms() {
		return pictograms;
	}

	public void setPictograms(Collection<PictogramBean> pictograms) {
		this.pictograms = pictograms;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getFlammability() {
		return flammability;
	}

	public void setFlammability(String flammability) {
		this.flammability = flammability;
	}

	public String getReactivity() {
		return reactivity;
	}

	public void setReactivity(String reactivity) {
		this.reactivity = reactivity;
	}

	public String getSpecificHazard() {
		return specificHazard;
	}

	public void setSpecificHazard(String specificHazard) {
		this.specificHazard = specificHazard;
	}

	public String getNfpaSource() {
		return nfpaSource;
	}

	public void setNfpaSource(String nfpaSource) {
		this.nfpaSource = nfpaSource;
	}

	public String getHmisHealth() {
		return hmisHealth;
	}

	public void setHmisHealth(String hmisHealth) {
		this.hmisHealth = hmisHealth;
	}

	public String getHmisFlammability() {
		return hmisFlammability;
	}

	public void setHmisFlammability(String hmisFlammability) {
		this.hmisFlammability = hmisFlammability;
	}

	public String getHmisReactivity() {
		return hmisReactivity;
	}

	public void setHmisReactivity(String hmisReactivity) {
		this.hmisReactivity = hmisReactivity;
	}

	public String getPersonalProtection() {
		return personalProtection;
	}

	public void setPersonalProtection(String personalProtection) {
		this.personalProtection = personalProtection;
	}

	public String getHmisChronic() {
		return hmisChronic;
	}

	public void setHmisChronic(String hmisChronic) {
		this.hmisChronic = hmisChronic;
	}

	public String getHmisSource() {
		return hmisSource;
	}

	public void setHmisSource(String hmisSource) {
		this.hmisSource = hmisSource;
	}

	public String getPhDetect() {
		return phDetect;
	}

	public void setPhDetect(String phDetect) {
		this.phDetect = phDetect;
	}

	public BigDecimal getPh() {
		return ph;
	}

	public void setPh(BigDecimal ph) {
		this.ph = ph;
	}

	public BigDecimal getPhUpper() {
		return phUpper;
	}

	public void setPhUpper(BigDecimal phUpper) {
		this.phUpper = phUpper;
	}

	public String getPhDetail() {
		return phDetail;
	}

	public void setPhDetail(String phDetail) {
		this.phDetail = phDetail;
	}

	public String getPhSource() {
		return phSource;
	}

	public void setPhSource(String phSource) {
		this.phSource = phSource;
	}

	public String getPhysicalState() {
		return physicalState;
	}

	public void setPhysicalState(String physicalState) {
		this.physicalState = physicalState;
	}

	public String getPhysicalStateSource() {
		return physicalStateSource;
	}

	public void setPhysicalStateSource(String physicalStateSource) {
		this.physicalStateSource = physicalStateSource;
	}

	public String getSara311312Acute() {
		return sara311312Acute;
	}

	public void setSara311312Acute(String sara311312Acute) {
		this.sara311312Acute = sara311312Acute;
	}

	public String getSara311312Chronic() {
		return sara311312Chronic;
	}

	public void setSara311312Chronic(String sara311312Chronic) {
		this.sara311312Chronic = sara311312Chronic;
	}

	public String getSara311312Fire() {
		return sara311312Fire;
	}

	public void setSara311312Fire(String sara311312Fire) {
		this.sara311312Fire = sara311312Fire;
	}

	public String getSara311312Pressure() {
		return sara311312Pressure;
	}

	public void setSara311312Pressure(String sara311312Pressure) {
		this.sara311312Pressure = sara311312Pressure;
	}

	public String getSara311312Reactivity() {
		return sara311312Reactivity;
	}

	public void setSara311312Reactivity(String sara311312Reactivity) {
		this.sara311312Reactivity = sara311312Reactivity;
	}

	public String getVaporDensity() {
		return vaporDensity;
	}

	public void setVaporDensity(String vaporDensity) {
		this.vaporDensity = vaporDensity;
	}

	public String getEvaporationRate() {
		return evaporationRate;
	}

	public void setEvaporationRate(String evaporationRate) {
		this.evaporationRate = evaporationRate;
	}

	public String getDensityDetect() {
		return densityDetect;
	}

	public void setDensityDetect(String densityDetect) {
		this.densityDetect = densityDetect;
	}

	public BigDecimal getDensity() {
		return density;
	}

	public void setDensity(BigDecimal density) {
		this.density = density;
	}

	public BigDecimal getDensityUpper() {
		return densityUpper;
	}

	public void setDensityUpper(BigDecimal densityUpper) {
		this.densityUpper = densityUpper;
	}

	public String getDensityUnit() {
		return densityUnit;
	}

	public void setDensityUnit(String densityUnit) {
		this.densityUnit = densityUnit;
	}

	public String getDensitySource() {
		return densitySource;
	}

	public void setDensitySource(String densitySource) {
		this.densitySource = densitySource;
	}

	public String getFlashPointDetect() {
		return flashPointDetect;
	}

	public void setFlashPointDetect(String flashPointDetect) {
		this.flashPointDetect = flashPointDetect;
	}

	public BigDecimal getFlashPointLower() {
		return flashPointLower;
	}

	public void setFlashPointLower(BigDecimal flashPointLower) {
		this.flashPointLower = flashPointLower;
	}

	public BigDecimal getFlashPointUpper() {
		return flashPointUpper;
	}

	public void setFlashPointUpper(BigDecimal flashPointUpper) {
		this.flashPointUpper = flashPointUpper;
	}

	public String getFlashPointUnit() {
		return flashPointUnit;
	}

	public void setFlashPointUnit(String flashPointUnit) {
		this.flashPointUnit = flashPointUnit;
	}

	public String getFlashPointMethod() {
		return flashPointMethod;
	}

	public void setFlashPointMethod(String flashPointMethod) {
		this.flashPointMethod = flashPointMethod;
	}

	public String getFlashPointSource() {
		return flashPointSource;
	}

	public void setFlashPointSource(String flashPointSource) {
		this.flashPointSource = flashPointSource;
	}

	public String getBoilingPointDetect() {
		return boilingPointDetect;
	}

	public void setBoilingPointDetect(String boilingPointDetect) {
		this.boilingPointDetect = boilingPointDetect;
	}

	public BigDecimal getBoilingPointLower() {
		return boilingPointLower;
	}

	public void setBoilingPointLower(BigDecimal boilingPointLower) {
		this.boilingPointLower = boilingPointLower;
	}

	public BigDecimal getBoilingPointUpper() {
		return boilingPointUpper;
	}

	public void setBoilingPointUpper(BigDecimal boilingPointUpper) {
		this.boilingPointUpper = boilingPointUpper;
	}

	public String getBoilingPointUnit() {
		return boilingPointUnit;
	}

	public void setBoilingPointUnit(String boilingPointUnit) {
		this.boilingPointUnit = boilingPointUnit;
	}

	public String getBoilingPointDetail() {
		return boilingPointDetail;
	}

	public void setBoilingPointDetail(String boilingPointDetail) {
		this.boilingPointDetail = boilingPointDetail;
	}

	public String getBoilingPointSource() {
		return boilingPointSource;
	}

	public void setBoilingPointSource(String boilingPointSource) {
		this.boilingPointSource = boilingPointSource;
	}

	public String getSpecificGravityDetect() {
		return specificGravityDetect;
	}

	public void setSpecificGravityDetect(String specificGravityDetect) {
		this.specificGravityDetect = specificGravityDetect;
	}

	public BigDecimal getSpecificGravityLower() {
		return specificGravityLower;
	}

	public void setSpecificGravityLower(BigDecimal specificGravityLower) {
		this.specificGravityLower = specificGravityLower;
	}

	public BigDecimal getSpecificGravityUpper() {
		return specificGravityUpper;
	}

	public void setSpecificGravityUpper(BigDecimal specificGravityUpper) {
		this.specificGravityUpper = specificGravityUpper;
	}

	public String getSpecificGravityBasis() {
		return specificGravityBasis;
	}

	public void setSpecificGravityBasis(String specificGravityBasis) {
		this.specificGravityBasis = specificGravityBasis;
	}

	public String getSpecificGravitySource() {
		return specificGravitySource;
	}

	public void setSpecificGravitySource(String specificGravitySource) {
		this.specificGravitySource = specificGravitySource;
	}

	public String getVaporPressureDetect() {
		return vaporPressureDetect;
	}

	public void setVaporPressureDetect(String vaporPressureDetect) {
		this.vaporPressureDetect = vaporPressureDetect;
	}

	public BigDecimal getVaporPressure() {
		return vaporPressure;
	}

	public void setVaporPressure(BigDecimal vaporPressure) {
		this.vaporPressure = vaporPressure;
	}

	public BigDecimal getVaporPressureUpper() {
		return vaporPressureUpper;
	}

	public void setVaporPressureUpper(BigDecimal vaporPressureUpper) {
		this.vaporPressureUpper = vaporPressureUpper;
	}

	public String getVaporPressureUnit() {
		return vaporPressureUnit;
	}

	public void setVaporPressureUnit(String vaporPressureUnit) {
		this.vaporPressureUnit = vaporPressureUnit;
	}

	public BigDecimal getVaporPressureTemp() {
		return vaporPressureTemp;
	}

	public void setVaporPressureTemp(BigDecimal vaporPressureTemp) {
		this.vaporPressureTemp = vaporPressureTemp;
	}

	public String getVaporPressureTempUnit() {
		return vaporPressureTempUnit;
	}

	public void setVaporPressureTempUnit(String vaporPressureTempUnit) {
		this.vaporPressureTempUnit = vaporPressureTempUnit;
	}

	public String getVaporPressureSource() {
		return vaporPressureSource;
	}

	public void setVaporPressureSource(String vaporPressureSource) {
		this.vaporPressureSource = vaporPressureSource;
	}

	public BigDecimal getSolids() {
		return solids;
	}

	public void setSolids(BigDecimal solids) {
		this.solids = solids;
	}

	public BigDecimal getSolidsLower() {
		return solidsLower;
	}

	public void setSolidsLower(BigDecimal solidsLower) {
		this.solidsLower = solidsLower;
	}

	public BigDecimal getSolidsUpper() {
		return solidsUpper;
	}

	public void setSolidsUpper(BigDecimal solidsUpper) {
		this.solidsUpper = solidsUpper;
	}

	public String getSolidsUnit() {
		return solidsUnit;
	}

	public void setSolidsUnit(String solidsUnit) {
		this.solidsUnit = solidsUnit;
	}

	public String getSolidsSource() {
		return solidsSource;
	}

	public void setSolidsSource(String solidsSource) {
		this.solidsSource = solidsSource;
	}

	public BigDecimal getVoc() {
		return voc;
	}

	public void setVoc(BigDecimal voc) {
		this.voc = voc;
	}

	public BigDecimal getVocLower() {
		return vocLower;
	}

	public void setVocLower(BigDecimal vocLower) {
		this.vocLower = vocLower;
	}

	public BigDecimal getVocUpper() {
		return vocUpper;
	}

	public void setVocUpper(BigDecimal vocUpper) {
		this.vocUpper = vocUpper;
	}

	public String getVocUnit() {
		return vocUnit;
	}

	public void setVocUnit(String vocUnit) {
		this.vocUnit = vocUnit;
	}

	public String getVocSource() {
		return vocSource;
	}

	public void setVocSource(String vocSource) {
		this.vocSource = vocSource;
	}

	public BigDecimal getVocLessH2oExempt() {
		return vocLessH2oExempt;
	}

	public void setVocLessH2oExempt(BigDecimal vocLessH2oExempt) {
		this.vocLessH2oExempt = vocLessH2oExempt;
	}

	public BigDecimal getVocLessH2oExemptLower() {
		return vocLessH2oExemptLower;
	}

	public void setVocLessH2oExemptLower(BigDecimal vocLessH2oExemptLower) {
		this.vocLessH2oExemptLower = vocLessH2oExemptLower;
	}

	public BigDecimal getVocLessH2oExemptUpper() {
		return vocLessH2oExemptUpper;
	}

	public void setVocLessH2oExemptUpper(BigDecimal vocLessH2oExemptUpper) {
		this.vocLessH2oExemptUpper = vocLessH2oExemptUpper;
	}

	public String getVocLessH2oExemptUnit() {
		return vocLessH2oExemptUnit;
	}

	public void setVocLessH2oExemptUnit(String vocLessH2oExemptUnit) {
		this.vocLessH2oExemptUnit = vocLessH2oExemptUnit;
	}

	public String getVocLessH2oExemptSource() {
		return vocLessH2oExemptSource;
	}

	public void setVocLessH2oExemptSource(String vocLessH2oExemptSource) {
		this.vocLessH2oExemptSource = vocLessH2oExemptSource;
	}
	
	public MsdsIndexingBean getCoData() {
		return coData;
	}
	
	public void setCoData(MsdsIndexingBean coData) {
		this.coData = coData;
	}

	public Collection<CompositionBean> getCompositionData() {
		return compositionData;
	}

	public void setCompositionData(Collection<CompositionBean> compositionData) {
		this.compositionData = compositionData;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAltDataSource() {
		return altDataSource;
	}

	public void setAltDataSource(String altDataSource) {
		this.altDataSource = altDataSource;
	}

	public String getImageLocaleCode() {
		return imageLocaleCode;
	}

	public void setImageLocaleCode(String imageLocaleCode) {
		this.imageLocaleCode = imageLocaleCode;
	}

	public boolean isSaveCustomerOverride() {
		return saveCustomerOverride;
	}

	public void setSaveCustomerOverride(boolean saveCustomerOverride) {
		this.saveCustomerOverride = saveCustomerOverride;
	}

	public String getOnLine() {
		return onLine;
	}

	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}

    public boolean isOnline() {
        return "Y".equals(onLine);
    }

    public String getReviewedBy() {
		return reviewedBy;
	}

	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

	public BigDecimal getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(BigDecimal verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

	public Date getVerifiedOn() {
		return verifiedOn;
	}

	public void setVerifiedOn(Date verifiedOn) {
		this.verifiedOn = verifiedOn;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public MsdsIndexingQcBean getQcData() {
		return qcData;
	}

	public void setQcData(MsdsIndexingQcBean qcData) {
		this.qcData = qcData;
	}

	public String getAlloy() {
		return alloy;
	}

	public void setAlloy(String alloy) {
		this.alloy = alloy;
	}

	public String getCarcinogen() {
		return carcinogen;
	}

	public void setCarcinogen(String carcinogen) {
		this.carcinogen = carcinogen;
	}

	public String getChronic() {
		return chronic;
	}

	public void setChronic(String chronic) {
		this.chronic = chronic;
	}

	public String getCorrosive() {
		return corrosive;
	}

	public void setCorrosive(String corrosive) {
		this.corrosive = corrosive;
	}

	public String getDetonable() {
		return detonable;
	}

	public void setDetonable(String detonable) {
		this.detonable = detonable;
	}

	public String getFireConditionsToAvoid() {
		return fireConditionsToAvoid;
	}

	public void setFireConditionsToAvoid(String fireConditionsToAvoid) {
		this.fireConditionsToAvoid = fireConditionsToAvoid;
	}

	public String getHealthEffects() {
		return healthEffects;
	}

	public void setHealthEffects(String healthEffects) {
		this.healthEffects = healthEffects;
	}

	public String getIncompatible() {
		return incompatible;
	}

	public void setIncompatible(String incompatible) {
		this.incompatible = incompatible;
	}

	public String getLowVolumeExempt() {
		return lowVolumeExempt;
	}

	public void setLowVolumeExempt(String lowVolumeExempt) {
		this.lowVolumeExempt = lowVolumeExempt;
	}

	public String getMiscible() {
		return miscible;
	}

	public void setMiscible(String miscible) {
		this.miscible = miscible;
	}

	public String getOxidizer() {
		return oxidizer;
	}

	public void setOxidizer(String oxidizer) {
		this.oxidizer = oxidizer;
	}

	public String getOzoneDepletingCompound() {
		return ozoneDepletingCompound;
	}

	public void setOzoneDepletingCompound(String ozoneDepletingCompound) {
		this.ozoneDepletingCompound = ozoneDepletingCompound;
	}

	public String getPolymerize() {
		return polymerize;
	}

	public void setPolymerize(String polymerize) {
		this.polymerize = polymerize;
	}

	public String getPyrophoric() {
		return pyrophoric;
	}

	public void setPyrophoric(String pyrophoric) {
		this.pyrophoric = pyrophoric;
	}

	public String getSpontaneouslyCombustible() {
		return spontaneouslyCombustible;
	}

	public void setSpontaneouslyCombustible(String spontaneouslyCombustible) {
		this.spontaneouslyCombustible = spontaneouslyCombustible;
	}

	public String getStable() {
		return stable;
	}

	public void setStable(String stable) {
		this.stable = stable;
	}

	public String getTscaStatement() {
		return tscaStatement;
	}

	public void setTscaStatement(String tscaStatement) {
		this.tscaStatement = tscaStatement;
	}

	public String getWaterReactive() {
		return waterReactive;
	}

	public void setWaterReactive(String waterReactive) {
		this.waterReactive = waterReactive;
	}

	public String getAsMixed() {
		return asMixed;
	}

	public void setAsMixed(String asMixed) {
		this.asMixed = asMixed;
	}
	
	public void setCustomerMsdsNumber(String customerMsdsNumber) {
		this.componentMsds = customerMsdsNumber;
	}
	
	public void setMaterialDesc(String materialDesc) {
		if (this.material != null) {
			material.setMaterialDesc(materialDesc);
		}
	}
	
	public void setMfgTradeName(String mfgTradeName) {
		if (this.material != null) {
			material.setTradeName(mfgTradeName);
		}
	}
	
	public void setProductCode(String productCode) {
		if (this.material != null) {
			material.setProductCode(productCode);
		}
	}
	
	public void setCustomerOnlyMsds(String customerOnlyMsds) {
		if (this.material != null) {
			material.setCustomerOnlyMsds(customerOnlyMsds);
		}
	}
	
	public void setMfgId(BigDecimal mfgId) {
		if (this.mfg != null) {
			mfg.setMfgId(mfgId);
		}
	}
	
	public void setManufacturer(String manufacturer) {
		if (this.mfg != null) {
			mfg.setMfgDesc(manufacturer);
		}
	}
	
	public void setMfgShortName(String mfgShortName) {
		if (this.mfg != null) {
			mfg.setMfgShortName(mfgShortName);
		}
	}
	
	public void setMfgUrl(String mfgUrl) {
		if (this.mfg != null) {
			mfg.setMfgUrl(mfgUrl);
		}
	}
	
	public void setContact(String contact) {
		if (this.mfg != null) {
			mfg.setContact(contact);
		}
	}
	
	public void setPhone(String phone) {
		if (this.mfg != null) {
			mfg.setPhone(phone);
		}
	}
	
	public void setEmail(String email) {
		if (this.mfg != null) {
			mfg.setEmail(email);
		}
	}
	
	public void setNotes(String notes) {
		if (this.mfg != null) {
			mfg.setNotes(notes);
		}
	}
	
	public void setCoDataEntryComplete(boolean coDataEntryComplete) {		
		if (this.coData != null) {
			coData.setDataEntryComplete(coDataEntryComplete);
		}
	}
	
	public void setCoHealth(String coHealth) {
		if (this.coData != null) {
			coData.setHealth(coHealth);
		}
	}
	
	public void setCoFlammability(String coFlammability) {
		if (this.coData != null) {
			coData.setHealth(coFlammability);
		}
	}
	
	public void setCoReactivity(String coReactivity) {
		if (this.coData != null) {
			coData.setHealth(coReactivity);
		}
	}
	
	public void setCoSpecificHazard(String coSpecificHazard) {
		if (this.coData != null) {
			coData.setSpecificHazard(coSpecificHazard);
		}
	}
	
	public void setCoNfpaSource(String coNfpaSource) {
		if (this.coData != null) {
			coData.setNfpaSource(coNfpaSource);
		}
	}
	
	public void setCoHmisHealth(String coHmisHealth) {
		if (this.coData != null) {
			coData.setHmisHealth(coHmisHealth);
		}
	}
	
	public void setCoHmisFlammability(String coHmisFlammability) {
		if (this.coData != null) {
			coData.setHmisHealth(coHmisFlammability);
		}
	}
	
	public void setCoHmisReactivity(String coHmisReactivity) {
		if (this.coData != null) {
			coData.setHmisHealth(coHmisReactivity);
		}
	}
	
	public void setCoPersonalProtection(String coPersonalProtection) {
		if (this.coData != null) {
			coData.setPersonalProtection(coPersonalProtection);
		}
	}
	
	public void setCoHmisChronic(String coHmisChronic) {
		if (this.coData != null) {
			coData.setHmisChronic(coHmisChronic);
		}
	}
	
	public void setCoHmisSource(String coHmisSource) {
		if (this.coData != null) {
			coData.setHmisSource(coHmisSource);
		}
	}
	
	public void setCoPhDetect(String coPhDetect) {
		if (this.coData != null) {
			coData.setPhDetect(coPhDetect);
		}
	}
	
	public void setCoPh(BigDecimal coPh) {
		if (this.coData != null) {
			coData.setPh(coPh);
		}
	}
	
	public void setCoPhUpper(BigDecimal coPhUpper) {
		if (this.coData != null) {
			coData.setPhUpper(coPhUpper);
		}
	}
	
	public void setCoPhDetail(String coPhDetail) {
		if (this.coData != null) {
			coData.setPhDetail(coPhDetail);
		}
	}
	
	public void setCoPhSource(String coPhSource) {
		if (this.coData != null) {
			coData.setPhSource(coPhSource);
		}
	}
	
	public void setCoPhysicalState(String coPhysicalState) {
		if (this.coData != null) {
			coData.setPhysicalState(coPhysicalState);
		}
	}
	
	public void setCoPhysicalStateSource(String coPhysicalStateSource) {
		if (this.coData != null) {
			coData.setPhysicalStateSource(coPhysicalStateSource);
		}
	}
	
	public void setCoDensityDetect(String coDensityDetect) {
		if (this.coData != null) {
			coData.setDensityDetect(coDensityDetect);
		}
	}
	
	public void setCoDensity(BigDecimal coDensity) {
		if (this.coData != null) {
			coData.setDensity(coDensity);
		}
	}
	
	public void setCoDensityUpper(BigDecimal coDensityUpper) {
		if (this.coData != null) {
			coData.setDensityUpper(coDensityUpper);
		}
	}
	
	public void setCoDensityUnit(String coDensityUnit) {
		if (this.coData != null) {
			coData.setDensityUnit(coDensityUnit);
		}
	}
	
	public void setCoDensitySource(String coDensitySource) {
		if (this.coData != null) {
			coData.setDensitySource(coDensitySource);
		}
	}
	
	public void setCoFlashPointDetect(String coFlashPointDetect) {
		if (this.coData != null) {
			coData.setFlashPointDetect(coFlashPointDetect);
		}
	}
	
	public void setCoFlashPointLower(BigDecimal coFlashPointLower) {
		if (this.coData != null) {
			coData.setFlashPointLower(coFlashPointLower);
		}
	}
	
	public void setCoFlashPointUpper(BigDecimal coFlashPointUpper) {
		if (this.coData != null) {
			coData.setFlashPointUpper(coFlashPointUpper);
		}
	}
	
	public void setCoFlashPointUnit(String coFlashPointUnit) {
		if (this.coData != null) {
			coData.setFlashPointUnit(coFlashPointUnit);
		}
	}
	
	public void setCoFlashPointMethod(String coFlashPointMethod) {
		if (this.coData != null) {
			coData.setFlashPointMethod(coFlashPointMethod);
		}
	}
	
	public void setCoFlashPointSource(String coFlashPointSource) {
		if (this.coData != null) {
			coData.setFlashPointSource(coFlashPointSource);
		}
	}

	public void setCoBoilingPointDetect(String coBoilingPointDetect) {
		if (this.coData != null) {
			coData.setBoilingPointDetect(coBoilingPointDetect);
		}
	}
	
	public void setCoBoilingPointLower(BigDecimal coBoilingPointLower) {
		if (this.coData != null) {
			coData.setBoilingPointLower(coBoilingPointLower);
		}
	}
	
	public void setCoBoilingPointUpper(BigDecimal coBoilingPointUpper) {
		if (this.coData != null) {
			coData.setBoilingPointUpper(coBoilingPointUpper);
		}
	}
	
	public void setCoBoilingPointUnit(String coBoilingPointUnit) {
		if (this.coData != null) {
			coData.setBoilingPointUnit(coBoilingPointUnit);
		}
	}
	
	public void setCoBoilingPointSource(String coBoilingPointSource) {
		if (this.coData != null) {
			coData.setBoilingPointSource(coBoilingPointSource);
		}
	}

	public void setCoSpecificGravityDetect(String coSpecificGravityDetect) {
		if (this.coData != null) {
			coData.setSpecificGravityDetect(coSpecificGravityDetect);
		}
	}
	
	public void setCoSpecificGravityLower(BigDecimal coSpecificGravityLower) {
		if (this.coData != null) {
			coData.setSpecificGravityLower(coSpecificGravityLower);
		}
	}
	
	public void setCoSpecificGravityUpper(BigDecimal coSpecificGravityUpper) {
		if (this.coData != null) {
			coData.setSpecificGravityUpper(coSpecificGravityUpper);
		}
	}
	
	public void setCoSpecificGravityBasis(String coSpecificGravityBasis) {
		if (this.coData != null) {
			coData.setSpecificGravityBasis(coSpecificGravityBasis);
		}
	}
	
	public void setCoSpecificGravitySource(String coSpecificGravitySource) {
		if (this.coData != null) {
			coData.setSpecificGravitySource(coSpecificGravitySource);
		}
	}
	
	public void setCoVaporPressureDetect(String coVaporPressureDetect) {
		if (this.coData != null) {
			coData.setVaporPressureDetect(coVaporPressureDetect);
		}
	}
	
	public void setCoVaporPressure(BigDecimal coVaporPressure) {
		if (this.coData != null) {
			coData.setVaporPressure(coVaporPressure);
		}
	}
	
	public void setCoVaporPressureUpper(BigDecimal coVaporPressureUpper) {
		if (this.coData != null) {
			coData.setVaporPressureUpper(coVaporPressureUpper);
		}
	}
	
	public void setCoVaporPressureUnit(String coVaporPressureUnit) {
		if (this.coData != null) {
			coData.setVaporPressureUnit(coVaporPressureUnit);
		}
	}
	
	public void setCoVaporPressureSource(String coVaporPressureSource) {
		if (this.coData != null) {
			coData.setVaporPressureSource(coVaporPressureSource);
		}
	}
	
	public void setCoSolids(BigDecimal coSolids) {
		if (this.coData != null) {
			coData.setSolids(coSolids);
		}
	}
	
	public void setCoSolidsLower(BigDecimal coSolidsLower) {
		if (this.coData != null) {
			coData.setSolidsLower(coSolidsLower);
		}
	}
	
	public void setCoSolidsUpper(BigDecimal coSolidsUpper) {
		if (this.coData != null) {
			coData.setSolidsUpper(coSolidsUpper);
		}
	}
	
	public void setCoSolidsUnit(String coSolidsUnit) {
		if (this.coData != null) {
			coData.setSolidsUnit(coSolidsUnit);
		}
	}
	
	public void setCoSolidsSource(String coSolidsSource) {
		if (this.coData != null) {
			coData.setSolidsSource(coSolidsSource);
		}
	}
	
	public void setCoVoc(BigDecimal coVoc) {
		if (this.coData != null) {
			coData.setVoc(coVoc);
		}
	}
	
	public void setCoVocLower(BigDecimal coVocLower) {
		if (this.coData != null) {
			coData.setVocLower(coVocLower);
		}
	}
	
	public void setCoVocUpper(BigDecimal coVocUpper) {
		if (this.coData != null) {
			coData.setVocUpper(coVocUpper);
		}
	}
	
	public void setCoVocUnit(String coVocUnit) {
		if (this.coData != null) {
			coData.setVocUnit(coVocUnit);
		}
	}
	
	public void setCoVocSource(String coVocSource) {
		if (this.coData != null) {
			coData.setVocSource(coVocSource);
		}
	}
	
	public void setCoVocLessH2oExempt(BigDecimal coVocLessH2oExempt) {
		if (this.coData != null) {
			coData.setVocLessH2oExempt(coVocLessH2oExempt);
		}
	}
	
	public void setCoVocLessH2oExemptLower(BigDecimal coVocLessH2oExemptLower) {
		if (this.coData != null) {
			coData.setVocLessH2oExemptLower(coVocLessH2oExemptLower);
		}
	}
	
	public void setCoVocLessH2oExemptUpper(BigDecimal coVocLessH2oExemptUpper) {
		if (this.coData != null) {
			coData.setVocLessH2oExemptUpper(coVocLessH2oExemptUpper);
		}
	}
	
	public void setCoVocLessH2oExemptUnit(String coVocLessH2oExemptUnit) {
		if (this.coData != null) {
			coData.setVocLessH2oExemptUnit(coVocLessH2oExemptUnit);
		}
	}
	
	public void setCoVocLessH20ExemptSource(String coVocLessH20ExemptSource) {
		if (this.coData != null) {
			coData.setVocLessH2oExemptSource(coVocLessH20ExemptSource);
		}
	}
	
	public void setCompanyMsds(boolean companyMsds) {
		if (companyMsds && this.coData == null) {
			coData = new MsdsIndexingBean();
		}
	}
	
	public void setBoilingPointQcErrorType(BigDecimal boilingPointQcErrorType) {
		if (this.qcData != null) {
			qcData.setBoilingPointQcErrorType(boilingPointQcErrorType);
		}
	}
	
	public void setDensityQcErrorType(BigDecimal densityQcErrorType) {
		if (this.qcData != null) {
			qcData.setDensityQcErrorType(densityQcErrorType);
		}
	}
	
	public void setFlashPointQcErrorType(BigDecimal flashPointQcErrorType) {
		if (this.qcData != null) {
			qcData.setFlashPointQcErrorType(flashPointQcErrorType);
		}
	}
	
	public void setGhsPictogramsQcErrorType(BigDecimal ghsPictogramsQcErrorType) {
		if (this.qcData != null) {
			qcData.setGhsPictogramsQcErrorType(ghsPictogramsQcErrorType);
		}
	}
	
	public void setHmisQcErrorType(BigDecimal hmisQcErrorType) {
		if (this.qcData != null) {
			qcData.setHmisQcErrorType(hmisQcErrorType);
		}
	}
	
	public void setNfpaQcErrorType(BigDecimal nfpaQcErrorType) {
		if (this.qcData != null) {
			qcData.setNfpaQcErrorType(nfpaQcErrorType);
		}
	}
	
	public void setPhQcErrorType(BigDecimal phQcErrorType) {
		if (this.qcData != null) {
			qcData.setPhQcErrorType(phQcErrorType);
		}
	}
	
	public void setPhysicalStateQcErrorType(BigDecimal physicalStateQcErrorType) {
		if (this.qcData != null) {
			qcData.setPhysicalStateQcErrorType(physicalStateQcErrorType);
		}
	}
	
	public void setSolidsQcErrorType(BigDecimal solidsQcErrorType) {
		if (this.qcData != null) {
			qcData.setSolidsQcErrorType(solidsQcErrorType);
		}
	}
	
	public void setSpecificGravityQcErrorType(BigDecimal specificGravityQcErrorType) {
		if (this.qcData != null) {
			qcData.setSpecificGravityQcErrorType(specificGravityQcErrorType);
		}
	}

	public void setVaporPressureQcErrorType(BigDecimal vaporPressureQcErrorType) {
		if (this.qcData != null) {
			qcData.setVaporPressureQcErrorType(vaporPressureQcErrorType);
		}
	}
	
	public void setVocQcErrorType(BigDecimal vocQcErrorType) {
		if (this.qcData != null) {
			qcData.setVocQcErrorType(vocQcErrorType);
		}
	}
	
	public void setVocLessH2oExemptQcErrorType(BigDecimal vocLessH2oExemptQcErrorType) {
		if (this.qcData != null) {
			qcData.setVocLessH2oExemptQcErrorType(vocLessH2oExemptQcErrorType);
		}
	}
	
	public void setAlloyQcErrorType(BigDecimal alloyQcErrorType) {
		if (this.qcData != null) {
			qcData.setAlloyQcErrorType(alloyQcErrorType);
		}
	}
	
	public void setMsdsAuthorQcErrorType(BigDecimal msdsAuthorQcErrorType) {
		if (this.qcData != null) {
			qcData.setMsdsAuthorQcErrorType(msdsAuthorQcErrorType);
		}
	}
	
	public void setCarcinogenQcErrorType(BigDecimal carcinogenQcErrorType) {
		if (this.qcData != null) {
			qcData.setCarcinogenQcErrorType(carcinogenQcErrorType);
		}
	}
	
	public void setChronicQcErrorType(BigDecimal chronicQcErrorType) {
		if (this.qcData != null) {
			qcData.setChronicQcErrorType(chronicQcErrorType);
		}
	}
	
	public void setCompositionQcErrorType(BigDecimal compositionQcErrorType) {
		if (this.qcData != null) {
			qcData.setCompositionQcErrorType(compositionQcErrorType);
		}
	}
	
	public void setCorrosiveQcErrorType(BigDecimal corrosiveQcErrorType) {
		if (this.qcData != null) {
			qcData.setCorrosiveQcErrorType(corrosiveQcErrorType);
		}
	}
	
	public void setMaterialDescriptionQcErrorType(BigDecimal materialDescriptionQcErrorType) {
		if (this.qcData != null) {
			qcData.setMaterialDescriptionQcErrorType(materialDescriptionQcErrorType);
		}
	}
	
	public void setEvaporationRateQcErrorType(BigDecimal evaporationRateQcErrorType) {
		if (this.qcData != null) {
			qcData.setEvaporationRateQcErrorType(evaporationRateQcErrorType);
		}
	}
	
	public void setFireConditionsToAvoidQcErrorType(BigDecimal fireConditionsToAvoidQcErrorType) {
		if (this.qcData != null) {
			qcData.setFireConditionsToAvoidQcErrorType(fireConditionsToAvoidQcErrorType);
		}
	}
	
	public void setLabelAddressQcErrorType(BigDecimal labelAddressQcErrorType) {
		if (this.qcData != null) {
			qcData.setLabelAddressQcErrorType(labelAddressQcErrorType);
		}
	}
	
	public void setHealthEffectsQcErrorType(BigDecimal healthEffectsQcErrorType) {
		if (this.qcData != null) {
			qcData.setHealthEffectsQcErrorType(healthEffectsQcErrorType);
		}
	}
	
	public void setContentQcErrorType(BigDecimal contentQcErrorType) {
		if (this.qcData != null) {
			qcData.setContentQcErrorType(contentQcErrorType);
		}
	}
	
	public void setIncompatibleQcErrorType(BigDecimal incompatibleQcErrorType) {
		if (this.qcData != null) {
			qcData.setIncompatibleQcErrorType(incompatibleQcErrorType);
		}
	}
	
	public void setManufacturerQcErrorType(BigDecimal manufacturerQcErrorType) {
		if (this.qcData != null) {
			qcData.setManufacturerQcErrorType(manufacturerQcErrorType);
		}
	}
	
	public void setOxidizerQcErrorType(BigDecimal oxidizerQcErrorType) {
		if (this.qcData != null) {
			qcData.setOxidizerQcErrorType(oxidizerQcErrorType);
		}
	}
	
	public void setPolymerizeQcErrorType(BigDecimal polymerizeQcErrorType) {
		if (this.qcData != null) {
			qcData.setPolymerizeQcErrorType(polymerizeQcErrorType);
		}
	}
	
	public void setProductCodeQcErrorType(BigDecimal productCodeQcErrorType) {
		if (this.qcData != null) {
			qcData.setProductCodeQcErrorType(productCodeQcErrorType);
		}
	}
	
	public void setRevisionDateQcErrorType(BigDecimal revisionDateQcErrorType) {
		if (this.qcData != null) {
			qcData.setRevisionDateQcErrorType(revisionDateQcErrorType);
		}
	}
	
	public void setSaraQcErrorType(BigDecimal saraQcErrorType) {
		if (this.qcData != null) {
			qcData.setSaraQcErrorType(saraQcErrorType);
		}
	}
	
	public void setSignalWordQcErrorType(BigDecimal signalWordQcErrorType) {
		if (this.qcData != null) {
			qcData.setSignalWordQcErrorType(signalWordQcErrorType);
		}
	}
	
	public void setStableQcErrorType(BigDecimal stableQcErrorType) {
		if (this.qcData != null) {
			qcData.setStableQcErrorType(stableQcErrorType);
		}
	}
	
	public void setTradeNameQcErrorType(BigDecimal tradeNameQcErrorType) {
		if (this.qcData != null) {
			qcData.setTradeNameQcErrorType(tradeNameQcErrorType);
		}
	}
	
	public void setTscaQcErrorType(BigDecimal tscaQcErrorType) {
		if (this.qcData != null) {
			qcData.setTscaQcErrorType(tscaQcErrorType);
		}
	}
	
	public void setVaporDensityQcErrorType(BigDecimal vaporDensityQcErrorType) {
		if (this.qcData != null) {
			qcData.setVaporDensityQcErrorType(vaporDensityQcErrorType);
		}
	}
	
	public void setWaterReactiveQcErrorType(BigDecimal waterReactiveQcErrorType) {
		if (this.qcData != null) {
			qcData.setWaterReactiveQcErrorType(waterReactiveQcErrorType);
		}
	}
	
	public void setHazardStatementsQcErrorType(BigDecimal hazardStatementsQcErrorType) {
		if (this.qcData != null) {
			qcData.setHazardStatementsQcErrorType(hazardStatementsQcErrorType);
		}
	}
	
	public void setPrecautionaryStatementsQcErrorType(BigDecimal precautionaryStatementsQcErrorType) {
		if (this.qcData != null) {
			qcData.setPrecautionaryStatementsQcErrorType(precautionaryStatementsQcErrorType);
		}
	}
	
	public void setDetonableQcErrorType(BigDecimal detonableQcErrorType) {
		if (this.qcData != null) {
			qcData.setDetonableQcErrorType(detonableQcErrorType);
		}
	}

	public Date getSourceRevisionDate() {
		return sourceRevisionDate;
	}

	public void setSourceRevisionDate(Date sourceRevisionDate) {
		this.sourceRevisionDate = sourceRevisionDate;
	}

	public String getSourceRevisionDateDisplay() {
		return sourceRevisionDateDisplay;
	}

	public void setSourceRevisionDateDisplay(String sourceRevisionDateDisplay) {
		this.sourceRevisionDateDisplay = sourceRevisionDateDisplay;
	}

	public String getSourceContent() {
		return sourceContent;
	}

	public void setSourceContent(String sourceContent) {
		this.sourceContent = sourceContent;
	}

	public String getAuthoringType() {
		return authoringType;
	}

	public void setAuthoringType(String authoringType) {
		this.authoringType = authoringType;
	}
	
	public void setLocalizedMaterialDesc(String localizedMaterialDesc) {
		if (this.localizedMaterial != null) {
			this.localizedMaterial.setMaterialDesc(localizedMaterialDesc);
		}
	}
	
	public void setLocalizedMfgTradeName(String localizedMfgTradeName) {
		if (this.localizedMaterial != null) {
			this.localizedMaterial.setTradeName(localizedMfgTradeName);
		}
	}
	
	public void setLocalizedMaterialDescQcErrorType(BigDecimal localizedMaterialDescQcErrorType) {
		if (this.qcData != null) {
			qcData.setLocalizedMaterialDescQcErrorType(localizedMaterialDescQcErrorType);
		}
	}
	
	public void setLocalizedTradeNameQcErrorType(BigDecimal localizedMfgTradeNameQcErrorType) {
		if (this.qcData != null) {
			qcData.setLocalizedTradeNameQcErrorType(localizedMfgTradeNameQcErrorType);
		}
	}
	
	public boolean isAuthoringTypeAuthoring() {
		return "Authoring".equals(authoringType);
	}

	public String getSdsRequired() {
		return sdsRequired;
	}

	public void setSdsRequired(String sdsRequired) {
		this.sdsRequired = sdsRequired;
	}
}
