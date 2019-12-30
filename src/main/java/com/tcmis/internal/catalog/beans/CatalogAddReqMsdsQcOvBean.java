package com.tcmis.internal.catalog.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.math.*;

import org.apache.commons.lang.StringUtils;

import oracle.sql.STRUCT;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.SqlHandler;


/******************************************************************************
 * CLASSNAME: CatalogAddReqMsdsQcOvBean <br>
 * @version: 1.0, Jul 15, 2011 <br>
 *****************************************************************************/


public class CatalogAddReqMsdsQcOvBean extends BaseDataBean implements SQLData {

	private BigDecimal requestId;
	private BigDecimal lineItem;
	private BigDecimal partId;
	private BigDecimal materialId;
	private Date revisionDate;
	private String materialDesc;
	private String manufacturer;
	private BigDecimal mfgId;
	private BigDecimal msdsAuthorId;
	private String msdsAuthorDesc;
	private String msdsAuthorCountryAbbrev;
	private String msdsAuthorNotes;
	private String mfgTradeName;
	private String comments;
	private String mfgUrl;
	private String contact;
	private String phone;
	private String email;
	private String notes;
	private String content;
	private String specificGravityDetect;
	private BigDecimal specificGravityLower;
	private BigDecimal specificGravityUpper;
	private String specificGravityBasis;
	private String specificGravitySource;
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
	private String flashPointDetect;
	private BigDecimal flashPointLower;
	private BigDecimal flashPointUpper;
	private String flashPointUnit;
	private String flashPointMethod;
	private String flashPointSource;
	private String densityDetect;
	private BigDecimal density;
	private BigDecimal densityUpper;
	private String densityUnit;
	private String densitySource;
	private String physicalState;
	private String vocUnit;
	private BigDecimal voc;
	private BigDecimal vocLower;
	private BigDecimal vocUpper;
	private String remark;
	private BigDecimal solids;
	private BigDecimal solidsLower;
	private BigDecimal solidsUpper;
	private String solidsUnit;
	private String solidsSource;
	private BigDecimal vocLowerPercent;
	private BigDecimal vocUpperPercent;
	private BigDecimal vocPercent;
	private String vocSource;
	private String altDataSource;
	private String vaporPressureDetect;
	private BigDecimal vaporPressure;
	private BigDecimal vaporPressureUpper;
	private String vaporPressureUnit;
	private BigDecimal vaporPressureTemp;
	private String vaporPressureTempUnit;
	private String vaporPressureSource;
	private BigDecimal vocLessH2oExempt;
	private String vocLessH2oExemptUnit;
	private BigDecimal vocLessH2oExemptLower;
	private BigDecimal vocLessH2oExemptUpper;
	private String vocLessH2oExemptSource;
	private BigDecimal vocLbPerSolidLb;
	private String vocLbPerSolidLbSource;
	private BigDecimal vocLbPerSolidLbLower;
	private BigDecimal vocLbPerSolidLbUpper;
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
	private String waterReactive;
	private String oxidizer;
	private String carcinogen;
	private String alloy;
	private String boilingPointDetect;
	private BigDecimal boilingPointLower;
	private BigDecimal boilingPointUpper;
	private String boilingPointUnit;
	private String boilingPointSource;
	private String boilingPointDetail;
	private BigDecimal ph;
	private String phDetail;
	private String phDetect;
	private String phSource;
	private BigDecimal phUpper;
	private String coAlloy;
	private String coAltDataSource;
	private String coBoilingPointDetect;
	private BigDecimal coBoilingPointLower;
	private BigDecimal coBoilingPointUpper;
	private String coBoilingPointUnit;
	private String coBoilingPointSource;
	private String coBoilingPointDetail;
	private String coCarcinogen;
	private String coChronic;
	private String coCompatibility;
	private String coCompositionChanged;
	private String coContent;
	private String coCorrosive;
	private BigDecimal coDensity;
	private String coDensityDetect;
	private String coDensityUnit;
	private BigDecimal coDensityUpper;
	private String coEmergencyPhone;
	private String coEvaporationRate;
	private String coEyes;
	private String coFireConditionsToAvoid;
	private String coFlammability;
	private String coFlashPoint;
	private String coFlashPointDetect;
	private BigDecimal coFlashPointLower;
	private String coFlashPointMethod;
	private String coFlashPointSource;
	private String coFlashPointUnit;
	private BigDecimal coFlashPointUpper;
	private String coFreezingPoint;
	private String coFrenchContent;
	private String coHealth;
	private String coHealthEffects;
	private String coHmisChronic;
	private String coHmisFlammability;
	private String coHmisHealth;
	private String coHmisReactivity;
	private String coHmisSource;
	private String coIncompatible;
	private String coIngestion;
	private String coInhalation;
	private String coInjection;
	private Date coInsertDate;
	private String coLfcCode;
	private BigDecimal coMaterialId;
	private String coMixture;
	private String coNfpaSource;
	private String coOnLine;
	private String coOshaHazard;
	private String coOxidizer;
	private String coPersonalProtection;
	private BigDecimal coPh;
	private String coPhysicalState;
	private String coPhDetail;
	private String coPhDetect;
	private String coPhSource;
	private BigDecimal coPhUpper;
	private String coPolymerize;
	private String coPpe;
	private String coReactivity;
	private String coRemark;
	private String coReviewedBy;
	private Date coReviewDate;
	private String coRouteOfEntry;
	private String coSara311312Acute;
	private String coSara311312Chronic;
	private String coSara311312Fire;
	private String coSara311312Pressure;
	private String coSara311312Reactivity;
	private String coSignalWord;
	private String coSkin;
	private BigDecimal coSolids;
	private BigDecimal coSolidsLower;
	private BigDecimal coSolidsLowerPercent;
	private BigDecimal coSolidsPercent;
	private String coSolidsSource;
	private String coSolidsUnit;
	private BigDecimal coSolidsUpper;
	private BigDecimal coSolidsUpperPercent;
	private BigDecimal coSpecificGravity;
	private String coSpecificGravityBasis;
	private String coSpecificGravityDetect;
	private BigDecimal coSpecificGravityLower;
	private BigDecimal coSpecificGravityUpper;
	private String coSpecificHazard;
	private String coStable;
	private String coStorageTemp;
	private String coTargetOrgan;
	private String coTsca12b;
	private String coTscaList;
	private String coVaporDensity;
	private BigDecimal coVaporPressure;
	private String coVaporPressureDetect;
	private String coVaporPressureSource;
	private BigDecimal coVaporPressureTemp;
	private String coVaporPressureTempUnit;
	private String coVaporPressureUnit;
	private BigDecimal coVaporPressureUpper;
	private BigDecimal coVoc;
	private BigDecimal coVocLbPerSolidLb;
	private BigDecimal coVocLbPerSolidLbLower;
	private String coVocLbPerSolidLbSource;
	private BigDecimal coVocLbPerSolidLbUpper;
	private String coVocLessH20ExemptSource;
	private BigDecimal coVocLessH2oExempt;
	private BigDecimal coVocLessH2oExemptLower;
	private String coVocLessH2oExemptSource;
	private String coVocLessH2oExemptUnit;
	private BigDecimal coVocLessH2oExemptUpper;
	private BigDecimal coVocLower;
	private BigDecimal coVocLowerPercent;
	private BigDecimal coVocPercent;
	private String coVocSource;
	private String coVocUnit;
	private BigDecimal coVocUpper;
	private BigDecimal coVocUpperPercent;
	private String coWaterReactive;
	private String coSpecificGravitySource;
	private String coDensitySource;
	private String productCode;
	private Collection<CatalogAddReqCompQcObjBean> compositionVar;
	private Array compositionVarArray;
	private Collection<MsdsBean> availableRevisionDates;
	private String sqlType = "CATALOG_ADD_REQ_MSDS_QC_OV";
	private String labelCompanyName;
	private String labelPhone;
	private String signalWord;
	private String labelAddressLine1;
	private String labelAddressLine2;
	private String labelAddressLine3;
	private String labelAddressLine4;
	private String labelCity;
	private String labelStateAbbrev;
	private String labelZip;
	private String labelCountryAbbrev;
	private Collection<GHSStatementsViewBean> hazardStmts;
	private Collection<GHSStatementsViewBean> precautionaryStmts;
	private Collection<PictogramBean> pictogram;
	private boolean ghsCompliantImage;
	private BigDecimal msdsId;
	private boolean ghsHazard;

	private String countryAbbrev;
    private String mfgShortName;
    private String msdsVerified;
    private BigDecimal verifiedBy;
    private String verifiedByName;
    private Date verifiedDate;
    private String kitMsds;
    private String componentMsds;
    private String idOnly;
    //private boolean onLine;
    private String ozoneDepletingCompound;
    private String lowVolumeExempt;
    private String coOzoneDepletingCompound;
    private String coLowVolumeExempt;
    private boolean companyMsds;
    private boolean dataEntryComplete;
    private boolean coDataEntryComplete;
    private boolean saveCustomerOverride; 
    private String detonable;
    private String miscible;
    private String pyrophoric;
    private String spontaneouslyCombustible;
    private String tscaStatement; 
    private String coDetonable;
    private String coMiscible;
    private String coPyrophoric;
    private String coSpontaneouslyCombustible;
    private String coTscaStatement;
    private String physicalStateSource;
    private String coPhysicalStateSource;
    private BigDecimal msdsIndexingPriorityId;
    //this column is for tracking what locale code user
    //selected to upload an image
    private String imageLocaleCode;
    private String sara311312Acute;
	private String sara311312Chronic;
	private String sara311312Fire;
	private String sara311312Pressure;
	private String sara311312Reactivity;
	private boolean customerOnlyMsds;
	private String asMixed;
	private String mixtureDesc;
	private String mixtureManufacturer;
	private String mixtureProductCode;
	private String mixturePhysicalState;
	private String mixturePhysicalStateSource;
	private String mixtureContent;
	private BigDecimal mixtureVoc;
	private BigDecimal mixtureVocLower;
	private BigDecimal mixtureVocUpper;
	private String mixtureVocUnit;
	private String mixtureVocSource;
	private BigDecimal mixtureVocLwes;
	private BigDecimal mixtureVocLwesLower;
	private BigDecimal mixtureVocLwesUpper;
	private String mixtureVocLwesUnit;
	private String mixtureVocLwesSource;
	private BigDecimal mixtureMfgId;
    private String documentUrl;
    private boolean allowMixture;
    private String facilityId;
	private String customerMixtureNumber;

    //constructor
	public CatalogAddReqMsdsQcOvBean() {
	}
	
	public void setAvailableRevisionDates(Collection<MsdsBean> availableRevisionDates) {
		this.availableRevisionDates = availableRevisionDates;
	}
	public Collection<MsdsBean> getAvailableRevisionDates() {
		return availableRevisionDates;
	}
	
	//setters
	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}
	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}
	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public void setMfgId(BigDecimal mfgId) {
		this.mfgId = mfgId;
	}
	public void setMfgTradeName(String mfgTradeName) {
		this.mfgTradeName = mfgTradeName;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setMfgUrl(String mfgUrl) {
		this.mfgUrl = mfgUrl;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setSpecificGravityDetect(String specificGravityDetect) {
		this.specificGravityDetect = specificGravityDetect;
	}
	public void setSpecificGravityLower(BigDecimal specificGravityLower) {
		this.specificGravityLower = specificGravityLower;
	}
	public void setSpecificGravityUpper(BigDecimal specificGravityUpper) {
		this.specificGravityUpper = specificGravityUpper;
	}
	public void setSpecificGravityBasis(String specificGravityBasis) {
		this.specificGravityBasis = specificGravityBasis;
	}
	public void setSpecificGravitySource(String specificGravitySource) {
		this.specificGravitySource = specificGravitySource;
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
	public void setNfpaSource(String nfpaSource) {
		this.nfpaSource = nfpaSource;
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
	public void setPersonalProtection(String personalProtection) {
		this.personalProtection = personalProtection;
	}
	public void setHmisChronic(String hmisChronic) {
		this.hmisChronic = hmisChronic;
	}
	public void setHmisSource(String hmisSource) {
		this.hmisSource = hmisSource;
	}
	public void setFlashPointDetect(String flashPointDetect) {
		this.flashPointDetect = flashPointDetect;
	}
	public void setFlashPointLower(BigDecimal flashPointLower) {
		this.flashPointLower = flashPointLower;
	}
	public void setFlashPointUpper(BigDecimal flashPointUpper) {
		this.flashPointUpper = flashPointUpper;
	}
	public void setFlashPointUnit(String flashPointUnit) {
		this.flashPointUnit = flashPointUnit;
	}
	public void setFlashPointMethod(String flashPointMethod) {
		this.flashPointMethod = flashPointMethod;
	}
	public void setFlashPointSource(String flashPointSource) {
		this.flashPointSource = flashPointSource;
	}
	public void setDensityDetect(String densityDetect) {
		this.densityDetect = densityDetect;
	}
	public void setDensity(BigDecimal density) {
		this.density = density;
	}
	public void setDensityUpper(BigDecimal densityUpper) {
		this.densityUpper = densityUpper;
	}
	public void setDensityUnit(String densityUnit) {
		this.densityUnit = densityUnit;
	}
	public void setDensitySource(String densitySource) {
		this.densitySource = densitySource;
	}
	public void setPhysicalState(String physicalState) {
		this.physicalState = physicalState;
	}
	public void setVocUnit(String vocUnit) {
		this.vocUnit = vocUnit;
	}
	public void setVoc(BigDecimal voc) {
		this.voc = voc;
	}
	public void setVocLower(BigDecimal vocLower) {
		this.vocLower = vocLower;
	}
	public void setVocUpper(BigDecimal vocUpper) {
		this.vocUpper = vocUpper;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setSolids(BigDecimal solids) {
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
	public void setSolidsSource(String solidsSource) {
		this.solidsSource = solidsSource;
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
	public void setVocSource(String vocSource) {
		this.vocSource = vocSource;
	}
	public void setAltDataSource(String altDataSource) {
		this.altDataSource = altDataSource;
	}
	public void setVaporPressureDetect(String vaporPressureDetect) {
		this.vaporPressureDetect = vaporPressureDetect;
	}
	public void setVaporPressure(BigDecimal vaporPressure) {
		this.vaporPressure = vaporPressure;
	}
	public void setVaporPressureUpper(BigDecimal vaporPressureUpper) {
		this.vaporPressureUpper = vaporPressureUpper;
	}
	public void setVaporPressureUnit(String vaporPressureUnit) {
		this.vaporPressureUnit = vaporPressureUnit;
	}
	public void setVaporPressureTemp(BigDecimal vaporPressureTemp) {
		this.vaporPressureTemp = vaporPressureTemp;
	}
	public void setVaporPressureTempUnit(String vaporPressureTempUnit) {
		this.vaporPressureTempUnit = vaporPressureTempUnit;
	}
	public void setVaporPressureSource(String vaporPressureSource) {
		this.vaporPressureSource = vaporPressureSource;
	}
	public void setVocLessH2oExempt(BigDecimal vocLessH2oExempt) {
		this.vocLessH2oExempt = vocLessH2oExempt;
	}
	public void setVocLessH2oExemptUnit(String vocLessH2oExemptUnit) {
		this.vocLessH2oExemptUnit = vocLessH2oExemptUnit;
	}
	public void setVocLessH2oExemptLower(BigDecimal vocLessH2oExemptLower) {
		this.vocLessH2oExemptLower = vocLessH2oExemptLower;
	}
	public void setVocLessH2oExemptUpper(BigDecimal vocLessH2oExemptUpper) {
		this.vocLessH2oExemptUpper = vocLessH2oExemptUpper;
	}
	public void setVocLessH2oExemptSource(String vocLessH2oExemptSource) {
		this.vocLessH2oExemptSource = vocLessH2oExemptSource;
	}
	public void setVocLbPerSolidLb(BigDecimal vocLbPerSolidLb) {
		this.vocLbPerSolidLb = vocLbPerSolidLb;
	}
	public void setVocLbPerSolidLbSource(String vocLbPerSolidLbSource) {
		this.vocLbPerSolidLbSource = vocLbPerSolidLbSource;
	}
	public void setVocLbPerSolidLbLower(BigDecimal vocLbPerSolidLbLower) {
		this.vocLbPerSolidLbLower = vocLbPerSolidLbLower;
	}
	public void setVocLbPerSolidLbUpper(BigDecimal vocLbPerSolidLbUpper) {
		this.vocLbPerSolidLbUpper = vocLbPerSolidLbUpper;
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
	public void setWaterReactive(String waterReactive) {
		this.waterReactive = waterReactive;
	}
	public void setOxidizer(String oxidizer) {
		this.oxidizer = oxidizer;
	}
	public void setCarcinogen(String carcinogen) {
		this.carcinogen = carcinogen;
	}
	public void setAlloy(String alloy) {
		this.alloy = alloy;
	}
	public void setBoilingPointDetect(String boilingPointDetect) {
		this.boilingPointDetect = boilingPointDetect;
	}
	public void setBoilingPointLower(BigDecimal boilingPointLower) {
		this.boilingPointLower = boilingPointLower;
	}
	public void setBoilingPointUpper(BigDecimal boilingPointUpper) {
		this.boilingPointUpper = boilingPointUpper;
	}
	public void setBoilingPointUnit(String boilingPointUnit) {
		this.boilingPointUnit = boilingPointUnit;
	}
	public void setBoilingPointSource(String boilingPointSource) {
		this.boilingPointSource = boilingPointSource;
	}
	public void setBoilingPointDetail(String boilingPointDetail) {
		this.boilingPointDetail = boilingPointDetail;
	}
	public void setPh(BigDecimal ph) {
		this.ph = ph;
	}
	public void setPhDetail(String phDetail) {
		this.phDetail = phDetail;
	}
	public void setPhDetect(String phDetect) {
		this.phDetect = phDetect;
	}
	public void setPhSource(String phSource) {
		this.phSource = phSource;
	}
	public void setPhUpper(BigDecimal phUpper) {
		this.phUpper = phUpper;
	}
	public void setCoAlloy(String coAlloy) {
		this.coAlloy = coAlloy;
	}
	public void setCoAltDataSource(String coAltDataSource) {
		this.coAltDataSource = coAltDataSource;
	}
	public void setCoBoilingPointDetect(String coBoilingPointDetect) {
		this.coBoilingPointDetect = coBoilingPointDetect;
	}
	public void setCoBoilingPointLower(BigDecimal coBoilingPointLower) {
		this.coBoilingPointLower = coBoilingPointLower;
	}
	public void setCoBoilingPointUpper(BigDecimal coBoilingPointUpper) {
		this.coBoilingPointUpper = coBoilingPointUpper;
	}
	public void setCoBoilingPointUnit(String coBoilingPointUnit) {
		this.coBoilingPointUnit = coBoilingPointUnit;
	}
	public void setCoBoilingPointSource(String coBoilingPointSource) {
		this.coBoilingPointSource = coBoilingPointSource;
	}
	public void setCoBoilingPointDetail(String coBoilingPointDetail) {
		this.coBoilingPointDetail = coBoilingPointDetail;
	}
	public void setCoCarcinogen(String coCarcinogen) {
		this.coCarcinogen = coCarcinogen;
	}
	public void setCoChronic(String coChronic) {
		this.coChronic = coChronic;
	}
	public void setCoCompatibility(String coCompatibility) {
		this.coCompatibility = coCompatibility;
	}
	public void setCoCompositionChanged(String coCompositionChanged) {
		this.coCompositionChanged = coCompositionChanged;
	}
	public void setCoContent(String coContent) {
		this.coContent = coContent;
	}
	public void setCoCorrosive(String coCorrosive) {
		this.coCorrosive = coCorrosive;
	}
	public void setCoDensity(BigDecimal coDensity) {
		this.coDensity = coDensity;
	}
	public void setCoDensityDetect(String coDensityDetect) {
		this.coDensityDetect = coDensityDetect;
	}
	public void setCoDensityUnit(String coDensityUnit) {
		this.coDensityUnit = coDensityUnit;
	}
	public void setCoDensityUpper(BigDecimal coDensityUpper) {
		this.coDensityUpper = coDensityUpper;
	}
	public void setCoEmergencyPhone(String coEmergencyPhone) {
		this.coEmergencyPhone = coEmergencyPhone;
	}
	public void setCoEvaporationRate(String coEvaporationRate) {
		this.coEvaporationRate = coEvaporationRate;
	}
	public void setCoEyes(String coEyes) {
		this.coEyes = coEyes;
	}
	public void setCoFireConditionsToAvoid(String coFireConditionsToAvoid) {
		this.coFireConditionsToAvoid = coFireConditionsToAvoid;
	}
	public void setCoFlammability(String coFlammability) {
		this.coFlammability = coFlammability;
	}
	public void setCoFlashPoint(String coFlashPoint) {
		this.coFlashPoint = coFlashPoint;
	}
	public void setCoFlashPointDetect(String coFlashPointDetect) {
		this.coFlashPointDetect = coFlashPointDetect;
	}
	public void setCoFlashPointLower(BigDecimal coFlashPointLower) {
		this.coFlashPointLower = coFlashPointLower;
	}
	public void setCoFlashPointMethod(String coFlashPointMethod) {
		this.coFlashPointMethod = coFlashPointMethod;
	}
	public void setCoFlashPointSource(String coFlashPointSource) {
		this.coFlashPointSource = coFlashPointSource;
	}
	public void setCoFlashPointUnit(String coFlashPointUnit) {
		this.coFlashPointUnit = coFlashPointUnit;
	}
	public void setCoFlashPointUpper(BigDecimal coFlashPointUpper) {
		this.coFlashPointUpper = coFlashPointUpper;
	}
	public void setCoFreezingPoint(String coFreezingPoint) {
		this.coFreezingPoint = coFreezingPoint;
	}
	public void setCoFrenchContent(String coFrenchContent) {
		this.coFrenchContent = coFrenchContent;
	}
	public void setCoHealth(String coHealth) {
		this.coHealth = coHealth;
	}
	public void setCoHealthEffects(String coHealthEffects) {
		this.coHealthEffects = coHealthEffects;
	}
	public void setCoHmisChronic(String coHmisChronic) {
		this.coHmisChronic = coHmisChronic;
	}
	public void setCoHmisFlammability(String coHmisFlammability) {
		this.coHmisFlammability = coHmisFlammability;
	}
	public void setCoHmisHealth(String coHmisHealth) {
		this.coHmisHealth = coHmisHealth;
	}
	public void setCoHmisReactivity(String coHmisReactivity) {
		this.coHmisReactivity = coHmisReactivity;
	}
	public void setCoHmisSource(String coHmisSource) {
		this.coHmisSource = coHmisSource;
	}
	public void setCoIncompatible(String coIncompatible) {
		this.coIncompatible = coIncompatible;
	}
	public void setCoIngestion(String coIngestion) {
		this.coIngestion = coIngestion;
	}
	public void setCoInhalation(String coInhalation) {
		this.coInhalation = coInhalation;
	}
	public void setCoInjection(String coInjection) {
		this.coInjection = coInjection;
	}
	public void setCoInsertDate(Date coInsertDate) {
		this.coInsertDate = coInsertDate;
	}
	public void setCoLfcCode(String coLfcCode) {
		this.coLfcCode = coLfcCode;
	}
	public void setCoMaterialId(BigDecimal coMaterialId) {
		this.coMaterialId = coMaterialId;
	}
	public void setCoMixture(String coMixture) {
		this.coMixture = coMixture;
	}
	public void setCoNfpaSource(String coNfpaSource) {
		this.coNfpaSource = coNfpaSource;
	}
	public void setCoOnLine(String coOnLine) {
		this.coOnLine = coOnLine;
	}
	public void setCoOshaHazard(String coOshaHazard) {
		this.coOshaHazard = coOshaHazard;
	}
	public void setCoOxidizer(String coOxidizer) {
		this.coOxidizer = coOxidizer;
	}
	public void setCoPersonalProtection(String coPersonalProtection) {
		this.coPersonalProtection = coPersonalProtection;
	}
	public void setCoPh(BigDecimal coPh) {
		this.coPh = coPh;
	}
	public void setCoPhysicalState(String coPhysicalState) {
		this.coPhysicalState = coPhysicalState;
	}
	public void setCoPhDetail(String coPhDetail) {
		this.coPhDetail = coPhDetail;
	}
	public void setCoPhDetect(String coPhDetect) {
		this.coPhDetect = coPhDetect;
	}
	public void setCoPhSource(String coPhSource) {
		this.coPhSource = coPhSource;
	}
	public void setCoPhUpper(BigDecimal coPhUpper) {
		this.coPhUpper = coPhUpper;
	}
	public void setCoPolymerize(String coPolymerize) {
		this.coPolymerize = coPolymerize;
	}
	public void setCoPpe(String coPpe) {
		this.coPpe = coPpe;
	}
	public void setCoReactivity(String coReactivity) {
		this.coReactivity = coReactivity;
	}
	public void setCoRemark(String coRemark) {
		this.coRemark = coRemark;
	}
	public void setCoReviewedBy(String coReviewedBy) {
		this.coReviewedBy = coReviewedBy;
	}
	public void setCoReviewDate(Date coReviewDate) {
		this.coReviewDate = coReviewDate;
	}
	public void setCoRouteOfEntry(String coRouteOfEntry) {
		this.coRouteOfEntry = coRouteOfEntry;
	}
	public void setCoSara311312Acute(String coSara311312Acute) {
		this.coSara311312Acute = coSara311312Acute;
	}
	public void setCoSara311312Chronic(String coSara311312Chronic) {
		this.coSara311312Chronic = coSara311312Chronic;
	}
	public void setCoSara311312Fire(String coSara311312Fire) {
		this.coSara311312Fire = coSara311312Fire;
	}
	public void setCoSara311312Pressure(String coSara311312Pressure) {
		this.coSara311312Pressure = coSara311312Pressure;
	}
	public void setCoSara311312Reactivity(String coSara311312Reactivity) {
		this.coSara311312Reactivity = coSara311312Reactivity;
	}
	public void setCoSignalWord(String coSignalWord) {
		this.coSignalWord = coSignalWord;
	}
	public void setCoSkin(String coSkin) {
		this.coSkin = coSkin;
	}
	public void setCoSolids(BigDecimal coSolids) {
		this.coSolids = coSolids;
	}
	public void setCoSolidsLower(BigDecimal coSolidsLower) {
		this.coSolidsLower = coSolidsLower;
	}
	public void setCoSolidsLowerPercent(BigDecimal coSolidsLowerPercent) {
		this.coSolidsLowerPercent = coSolidsLowerPercent;
	}
	public void setCoSolidsPercent(BigDecimal coSolidsPercent) {
		this.coSolidsPercent = coSolidsPercent;
	}
	public void setCoSolidsSource(String coSolidsSource) {
		this.coSolidsSource = coSolidsSource;
	}
	public void setCoSolidsUnit(String coSolidsUnit) {
		this.coSolidsUnit = coSolidsUnit;
	}
	public void setCoSolidsUpper(BigDecimal coSolidsUpper) {
		this.coSolidsUpper = coSolidsUpper;
	}
	public void setCoSolidsUpperPercent(BigDecimal coSolidsUpperPercent) {
		this.coSolidsUpperPercent = coSolidsUpperPercent;
	}
	public void setCoSpecificGravity(BigDecimal coSpecificGravity) {
		this.coSpecificGravity = coSpecificGravity;
	}
	public void setCoSpecificGravityBasis(String coSpecificGravityBasis) {
		this.coSpecificGravityBasis = coSpecificGravityBasis;
	}
	public void setCoSpecificGravityDetect(String coSpecificGravityDetect) {
		this.coSpecificGravityDetect = coSpecificGravityDetect;
	}
	public void setCoSpecificGravityLower(BigDecimal coSpecificGravityLower) {
		this.coSpecificGravityLower = coSpecificGravityLower;
	}
	public void setCoSpecificGravityUpper(BigDecimal coSpecificGravityUpper) {
		this.coSpecificGravityUpper = coSpecificGravityUpper;
	}
	public void setCoSpecificHazard(String coSpecificHazard) {
		this.coSpecificHazard = coSpecificHazard;
	}
	public void setCoStable(String coStable) {
		this.coStable = coStable;
	}
	public void setCoStorageTemp(String coStorageTemp) {
		this.coStorageTemp = coStorageTemp;
	}
	public void setCoTargetOrgan(String coTargetOrgan) {
		this.coTargetOrgan = coTargetOrgan;
	}
	public void setCoTsca12b(String coTsca12b) {
		this.coTsca12b = coTsca12b;
	}
	public void setCoTscaList(String coTscaList) {
		this.coTscaList = coTscaList;
	}
	public void setCoVaporDensity(String coVaporDensity) {
		this.coVaporDensity = coVaporDensity;
	}
	public void setCoVaporPressure(BigDecimal coVaporPressure) {
		this.coVaporPressure = coVaporPressure;
	}
	public void setCoVaporPressureDetect(String coVaporPressureDetect) {
		this.coVaporPressureDetect = coVaporPressureDetect;
	}
	public void setCoVaporPressureSource(String coVaporPressureSource) {
		this.coVaporPressureSource = coVaporPressureSource;
	}
	public void setCoVaporPressureTemp(BigDecimal coVaporPressureTemp) {
		this.coVaporPressureTemp = coVaporPressureTemp;
	}
	public void setCoVaporPressureTempUnit(String coVaporPressureTempUnit) {
		this.coVaporPressureTempUnit = coVaporPressureTempUnit;
	}
	public void setCoVaporPressureUnit(String coVaporPressureUnit) {
		this.coVaporPressureUnit = coVaporPressureUnit;
	}
	public void setCoVaporPressureUpper(BigDecimal coVaporPressureUpper) {
		this.coVaporPressureUpper = coVaporPressureUpper;
	}
	public void setCoVoc(BigDecimal coVoc) {
		this.coVoc = coVoc;
	}
	public void setCoVocLbPerSolidLb(BigDecimal coVocLbPerSolidLb) {
		this.coVocLbPerSolidLb = coVocLbPerSolidLb;
	}
	public void setCoVocLbPerSolidLbLower(BigDecimal coVocLbPerSolidLbLower) {
		this.coVocLbPerSolidLbLower = coVocLbPerSolidLbLower;
	}
	public void setCoVocLbPerSolidLbSource(String coVocLbPerSolidLbSource) {
		this.coVocLbPerSolidLbSource = coVocLbPerSolidLbSource;
	}
	public void setCoVocLbPerSolidLbUpper(BigDecimal coVocLbPerSolidLbUpper) {
		this.coVocLbPerSolidLbUpper = coVocLbPerSolidLbUpper;
	}
	public void setCoVocLessH20ExemptSource(String coVocLessH20ExemptSource) {
		this.coVocLessH20ExemptSource = coVocLessH20ExemptSource;
	}
	public void setCoVocLessH2oExempt(BigDecimal coVocLessH2oExempt) {
		this.coVocLessH2oExempt = coVocLessH2oExempt;
	}
	public void setCoVocLessH2oExemptLower(BigDecimal coVocLessH2oExemptLower) {
		this.coVocLessH2oExemptLower = coVocLessH2oExemptLower;
	}
	public void setCoVocLessH2oExemptSource(String coVocLessH2oExemptSource) {
		this.coVocLessH2oExemptSource = coVocLessH2oExemptSource;
	}
	public void setCoVocLessH2oExemptUnit(String coVocLessH2oExemptUnit) {
		this.coVocLessH2oExemptUnit = coVocLessH2oExemptUnit;
	}
	public void setCoVocLessH2oExemptUpper(BigDecimal coVocLessH2oExemptUpper) {
		this.coVocLessH2oExemptUpper = coVocLessH2oExemptUpper;
	}
	public void setCoVocLower(BigDecimal coVocLower) {
		this.coVocLower = coVocLower;
	}
	public void setCoVocLowerPercent(BigDecimal coVocLowerPercent) {
		this.coVocLowerPercent = coVocLowerPercent;
	}
	public void setCoVocPercent(BigDecimal coVocPercent) {
		this.coVocPercent = coVocPercent;
	}
	public void setCoVocSource(String coVocSource) {
		this.coVocSource = coVocSource;
	}
	public void setCoVocUnit(String coVocUnit) {
		this.coVocUnit = coVocUnit;
	}
	public void setCoVocUpper(BigDecimal coVocUpper) {
		this.coVocUpper = coVocUpper;
	}
	public void setCoVocUpperPercent(BigDecimal coVocUpperPercent) {
		this.coVocUpperPercent = coVocUpperPercent;
	}
	public void setCoWaterReactive(String coWaterReactive) {
		this.coWaterReactive = coWaterReactive;
	}
	public void setCoSpecificGravitySource(String coSpecificGravitySource) {
		this.coSpecificGravitySource = coSpecificGravitySource;
	}
	public void setCoDensitySource(String coDensitySource) {
		this.coDensitySource = coDensitySource;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public void setCompositionVar(Collection coll) {
		this.compositionVar = coll;
	}
	public void setCompositionVarArray(Array compositionVarArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) compositionVarArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setCompositionVar(list);
	}
	public void setIdOnly(String idOnly) {
		this.idOnly = idOnly;
	}

	public void setCompanyMsds(boolean companyMsds) {
		this.companyMsds = companyMsds;
	}

	public void setOzoneDepletingCompound(String ozoneDepletingCompound) {
		this.ozoneDepletingCompound = ozoneDepletingCompound;
	}

	public void setLowVolumeExempt(String lowVolumeExempt) {
		this.lowVolumeExempt = lowVolumeExempt;
	}	
	public void setCustomerOnlyMsds(boolean customerOnlyMsds) {
		this.customerOnlyMsds = customerOnlyMsds;
	}
	public void setAsMixed(String asMixed) {
		this.asMixed = asMixed;
	}
	
	public void setMixtureDesc(String mixtureDesc) {
		this.mixtureDesc = mixtureDesc;
	}

	public void setMixtureManufacturer(String mixtureManufacturer) {
		this.mixtureManufacturer = mixtureManufacturer;
	}

	public void setMixtureProductCode(String mixtureProductCode) {
		this.mixtureProductCode = mixtureProductCode;
	}

	public void setMixturePhysicalState(String mixturePhysicalState) {
		this.mixturePhysicalState = mixturePhysicalState;
	}

	public void setMixturePhysicalStateSource(String mixturePhysicalStateSource) {
		this.mixturePhysicalStateSource = mixturePhysicalStateSource;
	}

	public void setMixtureContent(String mixtureContent) {
		this.mixtureContent = mixtureContent;
	}

	public void setMixtureVoc(BigDecimal mixtureVoc) {
		this.mixtureVoc = mixtureVoc;
	}

	public void setMixtureVocLower(BigDecimal mixtureVocLower) {
		this.mixtureVocLower = mixtureVocLower;
	}

	public void setMixtureVocUpper(BigDecimal mixtureVocUpper) {
		this.mixtureVocUpper = mixtureVocUpper;
	}

	public void setMixtureVocUnit(String mixtureVocUnit) {
		this.mixtureVocUnit = mixtureVocUnit;
	}

	public void setMixtureVocSource(String mixtureVocSource) {
		this.mixtureVocSource = mixtureVocSource;
	}

	public void setMixtureVocLwes(BigDecimal mixtureVocLwes) {
		this.mixtureVocLwes = mixtureVocLwes;
	}

	public void setMixtureVocLwesLower(BigDecimal mixtureVocLwesLower) {
		this.mixtureVocLwesLower = mixtureVocLwesLower;
	}

	public void setMixtureVocLwesUpper(BigDecimal mixtureVocLwesUpper) {
		this.mixtureVocLwesUpper = mixtureVocLwesUpper;
	}

	public void setMixtureVocLwesUnit(String mixtureVocLwesUnit) {
		this.mixtureVocLwesUnit = mixtureVocLwesUnit;
	}

	public void setMixtureVocLwesSource(String mixtureVocLwesSource) {
		this.mixtureVocLwesSource = mixtureVocLwesSource;
	}	
	
	public void setMixtureMfgId(BigDecimal mixtureMfgId) {
		this.mixtureMfgId = mixtureMfgId;
	}	

	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}	
	
	public void setAllowMixture(boolean allowMixture) {
		this.allowMixture = allowMixture;
	}

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    //getters
    public String getFacilityId() {
        return facilityId;
    }

    public boolean getAllowMixture() {
		return allowMixture;
	}
	
	public String getDocumentUrl() {
		return documentUrl;
	}

	public BigDecimal getMixtureMfgId() {
		return mixtureMfgId;
	}
	
	public String getMixtureDesc() {
		return mixtureDesc;
	}

	public String getMixtureManufacturer() {
		return mixtureManufacturer;
	}

	public String getMixtureProductCode() {
		return mixtureProductCode;
	}

	public String getMixturePhysicalState() {
		return mixturePhysicalState;
	}

	public String getMixturePhysicalStateSource() {
		return mixturePhysicalStateSource;
	}

	public String getMixtureContent() {
		return mixtureContent;
	}

	public BigDecimal getMixtureVoc() {
		return mixtureVoc;
	}

	public BigDecimal getMixtureVocLower() {
		return mixtureVocLower;
	}

	public BigDecimal getMixtureVocUpper() {
		return mixtureVocUpper;
	}

	public String getMixtureVocUnit() {
		return mixtureVocUnit;
	}

	public String getMixtureVocSource() {
		return mixtureVocSource;
	}

	public BigDecimal getMixtureVocLwes() {
		return mixtureVocLwes;
	}

	public BigDecimal getMixtureVocLwesLower() {
		return mixtureVocLwesLower;
	}

	public BigDecimal getMixtureVocLwesUpper() {
		return mixtureVocLwesUpper;
	}
	
	public String getMixtureVocLwesUnit() {
		return mixtureVocLwesUnit;
	}

	public String getMixtureVocLwesSource() {
		return mixtureVocLwesSource;
	}

	public String getAsMixed() {
		return asMixed;
	}
	public boolean getCustomerOnlyMsds() {
		return customerOnlyMsds;
	}
	public BigDecimal getRequestId() {
		return requestId;
	}
	public BigDecimal getLineItem() {
		return lineItem;
	}
	public BigDecimal getPartId() {
		return partId;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public Date getRevisionDate() {
		return revisionDate;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public BigDecimal getMfgId() {
		return mfgId;
	}
	public String getMfgTradeName() {
		return mfgTradeName;
	}
	public String getComments() {
		return comments;
	}
	public String getMfgUrl() {
		return mfgUrl;
	}
	public String getContact() {
		return contact;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	public String getNotes() {
		return notes;
	}
	public String getContent() {
		return content;
	}
	public String getSpecificGravityDetect() {
		return specificGravityDetect;
	}
	public BigDecimal getSpecificGravityLower() {
		return specificGravityLower;
	}
	public BigDecimal getSpecificGravityUpper() {
		return specificGravityUpper;
	}
	public String getSpecificGravityBasis() {
		return specificGravityBasis;
	}
	public String getSpecificGravitySource() {
		return specificGravitySource;
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
	public String getNfpaSource() {
		return nfpaSource;
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
	public String getPersonalProtection() {
		return personalProtection;
	}
	public String getHmisChronic() {
		return hmisChronic;
	}
	public String getHmisSource() {
		return hmisSource;
	}
	public String getFlashPointDetect() {
		return flashPointDetect;
	}
	public BigDecimal getFlashPointLower() {
		return flashPointLower;
	}
	public BigDecimal getFlashPointUpper() {
		return flashPointUpper;
	}
	public String getFlashPointUnit() {
		return flashPointUnit;
	}
	public String getFlashPointMethod() {
		return flashPointMethod;
	}
	public String getFlashPointSource() {
		return flashPointSource;
	}
	public String getDensityDetect() {
		return densityDetect;
	}
	public BigDecimal getDensity() {
		return density;
	}
	public BigDecimal getDensityUpper() {
		return densityUpper;
	}
	public String getDensityUnit() {
		return densityUnit;
	}
	public String getDensitySource() {
		return densitySource;
	}
	public String getPhysicalState() {
		return physicalState;
	}
	public String getVocUnit() {
		return vocUnit;
	}
	public BigDecimal getVoc() {
		return voc;
	}
	public BigDecimal getVocLower() {
		return vocLower;
	}
	public BigDecimal getVocUpper() {
		return vocUpper;
	}
	public String getRemark() {
		return remark;
	}
	public BigDecimal getSolids() {
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
	public String getSolidsSource() {
		return solidsSource;
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
	public String getVocSource() {
		return vocSource;
	}
	public String getAltDataSource() {
		return altDataSource;
	}
	public String getVaporPressureDetect() {
		return vaporPressureDetect;
	}
	public BigDecimal getVaporPressure() {
		return vaporPressure;
	}
	public BigDecimal getVaporPressureUpper() {
		return vaporPressureUpper;
	}
	public String getVaporPressureUnit() {
		return vaporPressureUnit;
	}
	public BigDecimal getVaporPressureTemp() {
		return vaporPressureTemp;
	}
	public String getVaporPressureTempUnit() {
		return vaporPressureTempUnit;
	}
	public String getVaporPressureSource() {
		return vaporPressureSource;
	}
	public BigDecimal getVocLessH2oExempt() {
		return vocLessH2oExempt;
	}
	public String getVocLessH2oExemptUnit() {
		return vocLessH2oExemptUnit;
	}
	public BigDecimal getVocLessH2oExemptLower() {
		return vocLessH2oExemptLower;
	}
	public BigDecimal getVocLessH2oExemptUpper() {
		return vocLessH2oExemptUpper;
	}
	public String getVocLessH2oExemptSource() {
		return vocLessH2oExemptSource;
	}
	public BigDecimal getVocLbPerSolidLb() {
		return vocLbPerSolidLb;
	}
	public String getVocLbPerSolidLbSource() {
		return vocLbPerSolidLbSource;
	}
	public BigDecimal getVocLbPerSolidLbLower() {
		return vocLbPerSolidLbLower;
	}
	public BigDecimal getVocLbPerSolidLbUpper() {
		return vocLbPerSolidLbUpper;
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
	public String getWaterReactive() {
		return waterReactive;
	}
	public String getOxidizer() {
		return oxidizer;
	}
	public String getCarcinogen() {
		return carcinogen;
	}
	public String getAlloy() {
		return alloy;
	}
	public String getBoilingPointDetect() {
		return boilingPointDetect;
	}
	public BigDecimal getBoilingPointLower() {
		return boilingPointLower;
	}
	public BigDecimal getBoilingPointUpper() {
		return boilingPointUpper;
	}
	public String getBoilingPointUnit() {
		return boilingPointUnit;
	}
	public String getBoilingPointSource() {
		return boilingPointSource;
	}
	public String getBoilingPointDetail() {
		return boilingPointDetail;
	}
	public BigDecimal getPh() {
		return ph;
	}
	public String getPhDetail() {
		return phDetail;
	}
	public String getPhDetect() {
		return phDetect;
	}
	public String getPhSource() {
		return phSource;
	}
	public BigDecimal getPhUpper() {
		return phUpper;
	}
	public String getCoAlloy() {
		return coAlloy;
	}
	public String getCoAltDataSource() {
		return coAltDataSource;
	}
	public String getCoBoilingPointDetect() {
		return coBoilingPointDetect;
	}
	public BigDecimal getCoBoilingPointLower() {
		return coBoilingPointLower;
	}
	public BigDecimal getCoBoilingPointUpper() {
		return coBoilingPointUpper;
	}
	public String getCoBoilingPointUnit() {
		return coBoilingPointUnit;
	}
	public String getCoBoilingPointSource() {
		return coBoilingPointSource;
	}
	public String getCoBoilingPointDetail() {
		return coBoilingPointDetail;
	}
	public String getCoCarcinogen() {
		return coCarcinogen;
	}
	public String getCoChronic() {
		return coChronic;
	}
	public String getCoCompatibility() {
		return coCompatibility;
	}
	public String getCoCompositionChanged() {
		return coCompositionChanged;
	}
	public String getCoContent() {
		return coContent;
	}
	public String getCoCorrosive() {
		return coCorrosive;
	}
	public BigDecimal getCoDensity() {
		return coDensity;
	}
	public String getCoDensityDetect() {
		return coDensityDetect;
	}
	public String getCoDensityUnit() {
		return coDensityUnit;
	}
	public BigDecimal getCoDensityUpper() {
		return coDensityUpper;
	}
	public String getCoEmergencyPhone() {
		return coEmergencyPhone;
	}
	public String getCoEvaporationRate() {
		return coEvaporationRate;
	}
	public String getCoEyes() {
		return coEyes;
	}
	public String getCoFireConditionsToAvoid() {
		return coFireConditionsToAvoid;
	}
	public String getCoFlammability() {
		return coFlammability;
	}
	public String getCoFlashPoint() {
		return coFlashPoint;
	}
	public String getCoFlashPointDetect() {
		return coFlashPointDetect;
	}
	public BigDecimal getCoFlashPointLower() {
		return coFlashPointLower;
	}
	public String getCoFlashPointMethod() {
		return coFlashPointMethod;
	}
	public String getCoFlashPointSource() {
		return coFlashPointSource;
	}
	public String getCoFlashPointUnit() {
		return coFlashPointUnit;
	}
	public BigDecimal getCoFlashPointUpper() {
		return coFlashPointUpper;
	}
	public String getCoFreezingPoint() {
		return coFreezingPoint;
	}
	public String getCoFrenchContent() {
		return coFrenchContent;
	}
	public String getCoHealth() {
		return coHealth;
	}
	public String getCoHealthEffects() {
		return coHealthEffects;
	}
	public String getCoHmisChronic() {
		return coHmisChronic;
	}
	public String getCoHmisFlammability() {
		return coHmisFlammability;
	}
	public String getCoHmisHealth() {
		return coHmisHealth;
	}
	public String getCoHmisReactivity() {
		return coHmisReactivity;
	}
	public String getCoHmisSource() {
		return coHmisSource;
	}
	public String getCoIncompatible() {
		return coIncompatible;
	}
	public String getCoIngestion() {
		return coIngestion;
	}
	public String getCoInhalation() {
		return coInhalation;
	}
	public String getCoInjection() {
		return coInjection;
	}
	public Date getCoInsertDate() {
		return coInsertDate;
	}
	public String getCoLfcCode() {
		return coLfcCode;
	}
	public BigDecimal getCoMaterialId() {
		return coMaterialId;
	}
	public String getCoMixture() {
		return coMixture;
	}
	public String getCoNfpaSource() {
		return coNfpaSource;
	}
	public String getCoOnLine() {
		return coOnLine;
	}
	public String getCoOshaHazard() {
		return coOshaHazard;
	}
	public String getCoOxidizer() {
		return coOxidizer;
	}
	public String getCoPersonalProtection() {
		return coPersonalProtection;
	}
	public BigDecimal getCoPh() {
		return coPh;
	}
	public String getCoPhysicalState() {
		return coPhysicalState;
	}
	public String getCoPhDetail() {
		return coPhDetail;
	}
	public String getCoPhDetect() {
		return coPhDetect;
	}
	public String getCoPhSource() {
		return coPhSource;
	}
	public BigDecimal getCoPhUpper() {
		return coPhUpper;
	}
	public String getCoPolymerize() {
		return coPolymerize;
	}
	public String getCoPpe() {
		return coPpe;
	}
	public String getCoReactivity() {
		return coReactivity;
	}
	public String getCoRemark() {
		return coRemark;
	}
	public String getCoReviewedBy() {
		return coReviewedBy;
	}
	public Date getCoReviewDate() {
		return coReviewDate;
	}
	public String getCoRouteOfEntry() {
		return coRouteOfEntry;
	}
	public String getCoSara311312Acute() {
		return coSara311312Acute;
	}
	public String getCoSara311312Chronic() {
		return coSara311312Chronic;
	}
	public String getCoSara311312Fire() {
		return coSara311312Fire;
	}
	public String getCoSara311312Pressure() {
		return coSara311312Pressure;
	}
	public String getCoSara311312Reactivity() {
		return coSara311312Reactivity;
	}
	public String getCoSignalWord() {
		return coSignalWord;
	}
	public String getCoSkin() {
		return coSkin;
	}
	public BigDecimal getCoSolids() {
		return coSolids;
	}
	public BigDecimal getCoSolidsLower() {
		return coSolidsLower;
	}
	public BigDecimal getCoSolidsLowerPercent() {
		return coSolidsLowerPercent;
	}
	public BigDecimal getCoSolidsPercent() {
		return coSolidsPercent;
	}
	public String getCoSolidsSource() {
		return coSolidsSource;
	}
	public String getCoSolidsUnit() {
		return coSolidsUnit;
	}
	public BigDecimal getCoSolidsUpper() {
		return coSolidsUpper;
	}
	public BigDecimal getCoSolidsUpperPercent() {
		return coSolidsUpperPercent;
	}
	public BigDecimal getCoSpecificGravity() {
		return coSpecificGravity;
	}
	public String getCoSpecificGravityBasis() {
		return coSpecificGravityBasis;
	}
	public String getCoSpecificGravityDetect() {
		return coSpecificGravityDetect;
	}
	public BigDecimal getCoSpecificGravityLower() {
		return coSpecificGravityLower;
	}
	public BigDecimal getCoSpecificGravityUpper() {
		return coSpecificGravityUpper;
	}
	public String getCoSpecificHazard() {
		return coSpecificHazard;
	}
	public String getCoStable() {
		return coStable;
	}
	public String getCoStorageTemp() {
		return coStorageTemp;
	}
	public String getCoTargetOrgan() {
		return coTargetOrgan;
	}
	public String getCoTsca12b() {
		return coTsca12b;
	}
	public String getCoTscaList() {
		return coTscaList;
	}
	public String getCoVaporDensity() {
		return coVaporDensity;
	}
	public BigDecimal getCoVaporPressure() {
		return coVaporPressure;
	}
	public String getCoVaporPressureDetect() {
		return coVaporPressureDetect;
	}
	public String getCoVaporPressureSource() {
		return coVaporPressureSource;
	}
	public BigDecimal getCoVaporPressureTemp() {
		return coVaporPressureTemp;
	}
	public String getCoVaporPressureTempUnit() {
		return coVaporPressureTempUnit;
	}
	public String getCoVaporPressureUnit() {
		return coVaporPressureUnit;
	}
	public BigDecimal getCoVaporPressureUpper() {
		return coVaporPressureUpper;
	}
	public BigDecimal getCoVoc() {
		return coVoc;
	}
	public BigDecimal getCoVocLbPerSolidLb() {
		return coVocLbPerSolidLb;
	}
	public BigDecimal getCoVocLbPerSolidLbLower() {
		return coVocLbPerSolidLbLower;
	}
	public String getCoVocLbPerSolidLbSource() {
		return coVocLbPerSolidLbSource;
	}
	public BigDecimal getCoVocLbPerSolidLbUpper() {
		return coVocLbPerSolidLbUpper;
	}
	public String getCoVocLessH20ExemptSource() {
		return coVocLessH20ExemptSource;
	}
	public BigDecimal getCoVocLessH2oExempt() {
		return coVocLessH2oExempt;
	}
	public BigDecimal getCoVocLessH2oExemptLower() {
		return coVocLessH2oExemptLower;
	}
	public String getCoVocLessH2oExemptSource() {
		return coVocLessH2oExemptSource;
	}
	public String getCoVocLessH2oExemptUnit() {
		return coVocLessH2oExemptUnit;
	}
	public BigDecimal getCoVocLessH2oExemptUpper() {
		return coVocLessH2oExemptUpper;
	}
	public BigDecimal getCoVocLower() {
		return coVocLower;
	}
	public BigDecimal getCoVocLowerPercent() {
		return coVocLowerPercent;
	}
	public BigDecimal getCoVocPercent() {
		return coVocPercent;
	}
	public String getCoVocSource() {
		return coVocSource;
	}
	public String getCoVocUnit() {
		return coVocUnit;
	}
	public BigDecimal getCoVocUpper() {
		return coVocUpper;
	}
	public BigDecimal getCoVocUpperPercent() {
		return coVocUpperPercent;
	}
	public String getCoWaterReactive() {
		return coWaterReactive;
	}
	public String getCoSpecificGravitySource() {
		return coSpecificGravitySource;
	}
	public String getCoDensitySource() {
		return coDensitySource;
	}
	public String getProductCode() {
		return productCode;
	}
	public Collection <CatalogAddReqCompQcObjBean> getCompositionVar() {
		return this.compositionVar;
	}
	public Array getCompositionVarArray() {
		return compositionVarArray;
	}

	public BigDecimal getMsdsAuthorId() {
		return msdsAuthorId;
	}
	
	public String getMsdsAuthorDesc() {
		return msdsAuthorDesc;
	}
	
	public String getIdOnly() {
		return idOnly;
	}

	public boolean isCompanyMsds() {
		return companyMsds;
	}

	public String getOzoneDepletingCompound() {
		return ozoneDepletingCompound;
	}

	public String getLowVolumeExempt() {
		return lowVolumeExempt;
	}
	
	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try { Date dd = null;
			this.setRequestId(stream.readBigDecimal());
			this.setLineItem(stream.readBigDecimal());
			this.setPartId(stream.readBigDecimal());
			this.setMaterialId(stream.readBigDecimal());
			 dd = stream.readDate();			if( dd != null ) this.setRevisionDate(new Date(dd.getTime()));
			this.setMaterialDesc(stream.readString());
			this.setManufacturer(stream.readString());
			this.setMfgId(stream.readBigDecimal());
			this.setMsdsAuthorId(stream.readBigDecimal());
			this.setMsdsAuthorDesc(stream.readString());
			this.setMsdsAuthorCountryAbbrev(stream.readString());
			this.setMsdsAuthorNotes(stream.readString());
			this.setMfgTradeName(stream.readString());
			this.setComments(stream.readString());
			this.setMfgUrl(stream.readString());
			this.setContact(stream.readString());
			this.setPhone(stream.readString());
			this.setEmail(stream.readString());
			this.setNotes(stream.readString());
			this.setContent(stream.readString());
			this.setSpecificGravityDetect(stream.readString());
			this.setSpecificGravityLower(stream.readBigDecimal());
			this.setSpecificGravityUpper(stream.readBigDecimal());
			this.setSpecificGravityBasis(stream.readString());
			this.setSpecificGravitySource(stream.readString());
			this.setHealth(stream.readString());
			this.setFlammability(stream.readString());
			this.setReactivity(stream.readString());
			this.setSpecificHazard(stream.readString());
			this.setNfpaSource(stream.readString());
			this.setHmisHealth(stream.readString());
			this.setHmisFlammability(stream.readString());
			this.setHmisReactivity(stream.readString());
			this.setPersonalProtection(stream.readString());
			this.setHmisChronic(stream.readString());
			this.setHmisSource(stream.readString());
			this.setFlashPointDetect(stream.readString());
			this.setFlashPointLower(stream.readBigDecimal());
			this.setFlashPointUpper(stream.readBigDecimal());
			this.setFlashPointUnit(stream.readString());
			this.setFlashPointMethod(stream.readString());
			this.setFlashPointSource(stream.readString());
			this.setDensityDetect(stream.readString());
			this.setDensity(stream.readBigDecimal());
			this.setDensityUpper(stream.readBigDecimal());
			this.setDensityUnit(stream.readString());
			this.setDensitySource(stream.readString());
			this.setPhysicalState(stream.readString());
			this.setPhysicalStateSource(stream.readString());
			this.setVocUnit(stream.readString());
			this.setVoc(stream.readBigDecimal());
			this.setVocLower(stream.readBigDecimal());
			this.setVocUpper(stream.readBigDecimal());
			this.setRemark(stream.readString());
			this.setSolids(stream.readBigDecimal());
			this.setSolidsLower(stream.readBigDecimal());
			this.setSolidsUpper(stream.readBigDecimal());
			this.setSolidsUnit(stream.readString());
			this.setSolidsSource(stream.readString());
			this.setVocLowerPercent(stream.readBigDecimal());
			this.setVocUpperPercent(stream.readBigDecimal());
			this.setVocPercent(stream.readBigDecimal());
			this.setVocSource(stream.readString());
			this.setAltDataSource(stream.readString());
			this.setVaporPressureDetect(stream.readString());
			this.setVaporPressure(stream.readBigDecimal());
			this.setVaporPressureUpper(stream.readBigDecimal());
			this.setVaporPressureUnit(stream.readString());
			this.setVaporPressureTemp(stream.readBigDecimal());
			this.setVaporPressureTempUnit(stream.readString());
			this.setVaporPressureSource(stream.readString());
			this.setVocLessH2oExempt(stream.readBigDecimal());
			this.setVocLessH2oExemptUnit(stream.readString());
			this.setVocLessH2oExemptLower(stream.readBigDecimal());
			this.setVocLessH2oExemptUpper(stream.readBigDecimal());
			this.setVocLessH2oExemptSource(stream.readString());
			this.setVocLbPerSolidLb(stream.readBigDecimal());
			this.setVocLbPerSolidLbSource(stream.readString());
			this.setVocLbPerSolidLbLower(stream.readBigDecimal());
			this.setVocLbPerSolidLbUpper(stream.readBigDecimal());
			this.setChronic(stream.readString());
			this.setLfcCode(stream.readString());
			this.setPolymerize(stream.readString());
			this.setIncompatible(stream.readString());
			this.setCorrosive(stream.readString());
			this.setHealthEffects(stream.readString());
			this.setStable(stream.readString());
			this.setVaporDensity(stream.readString());
			this.setEvaporationRate(stream.readString());
			this.setFireConditionsToAvoid(stream.readString());
			this.setWaterReactive(stream.readString());
			this.setOxidizer(stream.readString());
			this.setCarcinogen(stream.readString());
			this.setAlloy(stream.readString());
			this.setBoilingPointDetect(stream.readString());
			this.setBoilingPointLower(stream.readBigDecimal());
			this.setBoilingPointUpper(stream.readBigDecimal());
			this.setBoilingPointUnit(stream.readString());
			this.setBoilingPointSource(stream.readString());
			this.setBoilingPointDetail(stream.readString());
			this.setPh(stream.readBigDecimal());
			this.setPhDetail(stream.readString());
			this.setPhDetect(stream.readString());
			this.setPhSource(stream.readString());
			this.setPhUpper(stream.readBigDecimal());
            this.setMsdsIndexingPriorityId(stream.readBigDecimal());
            this.setCoAlloy(stream.readString());
			this.setCoAltDataSource(stream.readString());
			this.setCoBoilingPointDetect(stream.readString());
			this.setCoBoilingPointLower(stream.readBigDecimal());
			this.setCoBoilingPointUpper(stream.readBigDecimal());
			this.setCoBoilingPointUnit(stream.readString());
			this.setCoBoilingPointSource(stream.readString());
			this.setCoBoilingPointDetail(stream.readString());
			this.setCoCarcinogen(stream.readString());
			this.setCoChronic(stream.readString());
			this.setCoCompatibility(stream.readString());
			this.setCoCompositionChanged(stream.readString());
			this.setCoContent(stream.readString());
			this.setCoCorrosive(stream.readString());
			this.setCoDensity(stream.readBigDecimal());
			this.setCoDensityDetect(stream.readString());
			this.setCoDensityUnit(stream.readString());
			this.setCoDensityUpper(stream.readBigDecimal());
			this.setCoEmergencyPhone(stream.readString());
			this.setCoEvaporationRate(stream.readString());
			this.setCoEyes(stream.readString());
			this.setCoFireConditionsToAvoid(stream.readString());
			this.setCoFlammability(stream.readString());
			this.setCoFlashPoint(stream.readString());
			this.setCoFlashPointDetect(stream.readString());
			this.setCoFlashPointLower(stream.readBigDecimal());
			this.setCoFlashPointMethod(stream.readString());
			this.setCoFlashPointSource(stream.readString());
			this.setCoFlashPointUnit(stream.readString());
			this.setCoFlashPointUpper(stream.readBigDecimal());
			this.setCoFreezingPoint(stream.readString());
			this.setCoFrenchContent(stream.readString());
			this.setCoHealth(stream.readString());
			this.setCoHealthEffects(stream.readString());
			this.setCoHmisChronic(stream.readString());
			this.setCoHmisFlammability(stream.readString());
			this.setCoHmisHealth(stream.readString());
			this.setCoHmisReactivity(stream.readString());
			this.setCoHmisSource(stream.readString());
			this.setCoIncompatible(stream.readString());
			this.setCoIngestion(stream.readString());
			this.setCoInhalation(stream.readString());
			this.setCoInjection(stream.readString());
			 dd = stream.readDate();			if( dd != null ) this.setCoInsertDate(new Date(dd.getTime()));
			this.setCoLfcCode(stream.readString());
			this.setCoMaterialId(stream.readBigDecimal());
			this.setCoMixture(stream.readString());
			this.setCoNfpaSource(stream.readString());
			this.setCoOnLine(stream.readString());
			this.setCoOshaHazard(stream.readString());
			this.setCoOxidizer(stream.readString());
			this.setCoPersonalProtection(stream.readString());
			this.setCoPh(stream.readBigDecimal());
			this.setCoPhysicalState(stream.readString());
			this.setCoPhysicalStateSource(stream.readString());
			this.setCoPhDetail(stream.readString());
			this.setCoPhDetect(stream.readString());
			this.setCoPhSource(stream.readString());
			this.setCoPhUpper(stream.readBigDecimal());
			this.setCoPolymerize(stream.readString());
			this.setCoPpe(stream.readString());
			this.setCoReactivity(stream.readString());
			this.setCoRemark(stream.readString());
			this.setCoReviewedBy(stream.readString());
			 dd = stream.readDate();			if( dd != null ) this.setCoReviewDate(new Date(dd.getTime()));
			this.setCoRouteOfEntry(stream.readString());
			this.setCoSara311312Acute(stream.readString());
			this.setCoSara311312Chronic(stream.readString());
			this.setCoSara311312Fire(stream.readString());
			this.setCoSara311312Pressure(stream.readString());
			this.setCoSara311312Reactivity(stream.readString());
			this.setCoSignalWord(stream.readString());
			this.setCoSkin(stream.readString());
			this.setCoSolids(stream.readBigDecimal());
			this.setCoSolidsLower(stream.readBigDecimal());
			this.setCoSolidsLowerPercent(stream.readBigDecimal());
			this.setCoSolidsPercent(stream.readBigDecimal());
			this.setCoSolidsSource(stream.readString());
			this.setCoSolidsUnit(stream.readString());
			this.setCoSolidsUpper(stream.readBigDecimal());
			this.setCoSolidsUpperPercent(stream.readBigDecimal());
			this.setCoSpecificGravity(stream.readBigDecimal());
			this.setCoSpecificGravityBasis(stream.readString());
			this.setCoSpecificGravityDetect(stream.readString());
			this.setCoSpecificGravityLower(stream.readBigDecimal());
			this.setCoSpecificGravityUpper(stream.readBigDecimal());
			this.setCoSpecificHazard(stream.readString());
			this.setCoStable(stream.readString());
			this.setCoStorageTemp(stream.readString());
			this.setCoTargetOrgan(stream.readString());
			this.setCoTsca12b(stream.readString());
			this.setCoTscaList(stream.readString());
			this.setCoVaporDensity(stream.readString());
			this.setCoVaporPressure(stream.readBigDecimal());
			this.setCoVaporPressureDetect(stream.readString());
			this.setCoVaporPressureSource(stream.readString());
			this.setCoVaporPressureTemp(stream.readBigDecimal());
			this.setCoVaporPressureTempUnit(stream.readString());
			this.setCoVaporPressureUnit(stream.readString());
			this.setCoVaporPressureUpper(stream.readBigDecimal());
			this.setCoVoc(stream.readBigDecimal());
			this.setCoVocLbPerSolidLb(stream.readBigDecimal());
			this.setCoVocLbPerSolidLbLower(stream.readBigDecimal());
			this.setCoVocLbPerSolidLbSource(stream.readString());
			this.setCoVocLbPerSolidLbUpper(stream.readBigDecimal());
			this.setCoVocLessH20ExemptSource(stream.readString());
			this.setCoVocLessH2oExempt(stream.readBigDecimal());
			this.setCoVocLessH2oExemptLower(stream.readBigDecimal());
			this.setCoVocLessH2oExemptSource(stream.readString());
			this.setCoVocLessH2oExemptUnit(stream.readString());
			this.setCoVocLessH2oExemptUpper(stream.readBigDecimal());
			this.setCoVocLower(stream.readBigDecimal());
			this.setCoVocLowerPercent(stream.readBigDecimal());
			this.setCoVocPercent(stream.readBigDecimal());
			this.setCoVocSource(stream.readString());
			this.setCoVocUnit(stream.readString());
			this.setCoVocUpper(stream.readBigDecimal());
			this.setCoVocUpperPercent(stream.readBigDecimal());
			this.setCoWaterReactive(stream.readString());
			//this.setLockheedMsds(stream.readString());
			this.setCoSpecificGravitySource(stream.readString());
			this.setCoDensitySource(stream.readString());
			this.setProductCode(stream.readString());
            this.setMfgShortName(stream.readString());
            this.setVerifiedBy(stream.readBigDecimal());
            this.setVerifiedDate(stream.readDate());
            this.setIdOnly(stream.readString());
            this.setVerifiedByName(stream.readString());
            this.setOzoneDepletingCompound(stream.readString());
            this.setLowVolumeExempt(stream.readString());
            this.setCompanyMsds((stream.readString()).equals("Y") ? true : false);
            this.setDataEntryComplete(stream.readString().equals("Y") ? true : false);
            this.setCoDataEntryComplete(stream.readString().equals("Y") ? true : false);
            this.setTscaStatement(stream.readString());
            this.setMiscible(stream.readString());
            this.setPyrophoric(stream.readString());
            this.setDetonable(stream.readString());
            stream.readString();
            this.setSpontaneouslyCombustible(stream.readString());
            this.setCompositionVarArray(stream.readArray());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".readSQL method failed").
			initCause(e);
		}
	}
	public void readObject(Object oo) throws SQLException {
		   STRUCT b = (STRUCT)oo;//(oracle.sql.STRUCT)rs.getObject(1);
	   	   Object[] attrs = b.getAttributes();
	   	   	int i = 0 ;
	   	   
	   	   	Date dd = null;
		
			this.setRequestId((BigDecimal)attrs[i++]);
			this.setLineItem((BigDecimal)attrs[i++]);
			this.setPartId((BigDecimal)attrs[i++]);
			this.setMaterialId((BigDecimal)attrs[i++]);
			 dd = (Timestamp) attrs[i++];			if( dd != null ) this.setRevisionDate(new Date(dd.getTime()));
			this.setMaterialDesc((String)attrs[i++]);
			this.setManufacturer((String)attrs[i++]);
			this.setMfgId((BigDecimal)attrs[i++]);
			this.setMsdsAuthorId((BigDecimal)attrs[i++]);
			this.setMsdsAuthorDesc((String)attrs[i++]);
			this.setMsdsAuthorCountryAbbrev((String)attrs[i++]);
			this.setMsdsAuthorNotes((String)attrs[i++]);
			this.setMfgTradeName((String)attrs[i++]);
			this.setComments((String)attrs[i++]);
			this.setMfgUrl((String)attrs[i++]);
			this.setContact((String)attrs[i++]);
			this.setPhone((String)attrs[i++]);
			this.setEmail((String)attrs[i++]);
			this.setNotes((String)attrs[i++]);
			this.setContent((String)attrs[i++]);
			this.setSpecificGravityDetect((String)attrs[i++]);
			this.setSpecificGravityLower((BigDecimal)attrs[i++]);
			this.setSpecificGravityUpper((BigDecimal)attrs[i++]);
			this.setSpecificGravityBasis((String)attrs[i++]);
			this.setSpecificGravitySource((String)attrs[i++]);
			this.setHealth((String)attrs[i++]);
			this.setFlammability((String)attrs[i++]);
			this.setReactivity((String)attrs[i++]);
			this.setSpecificHazard((String)attrs[i++]);
			this.setNfpaSource((String)attrs[i++]);
			this.setHmisHealth((String)attrs[i++]);
			this.setHmisFlammability((String)attrs[i++]);
			this.setHmisReactivity((String)attrs[i++]);
			this.setPersonalProtection((String)attrs[i++]);
			this.setHmisChronic((String)attrs[i++]);
			this.setHmisSource((String)attrs[i++]);
			this.setFlashPointDetect((String)attrs[i++]);
			this.setFlashPointLower((BigDecimal)attrs[i++]);
			this.setFlashPointUpper((BigDecimal)attrs[i++]);
			this.setFlashPointUnit((String)attrs[i++]);
			this.setFlashPointMethod((String)attrs[i++]);
			this.setFlashPointSource((String)attrs[i++]);
			this.setDensityDetect((String)attrs[i++]);
			this.setDensity((BigDecimal)attrs[i++]);
			this.setDensityUpper((BigDecimal)attrs[i++]);
			this.setDensityUnit((String)attrs[i++]);
			this.setDensitySource((String)attrs[i++]);
			this.setPhysicalState((String)attrs[i++]);
			this.setPhysicalStateSource((String)attrs[i++]);
			this.setVocUnit((String)attrs[i++]);
			this.setVoc((BigDecimal)attrs[i++]);
			this.setVocLower((BigDecimal)attrs[i++]);
			this.setVocUpper((BigDecimal)attrs[i++]);
			this.setRemark((String)attrs[i++]);
			this.setSolids((BigDecimal)attrs[i++]);
			this.setSolidsLower((BigDecimal)attrs[i++]);
			this.setSolidsUpper((BigDecimal)attrs[i++]);
			this.setSolidsUnit((String)attrs[i++]);
			this.setSolidsSource((String)attrs[i++]);
			this.setVocLowerPercent((BigDecimal)attrs[i++]);
			this.setVocUpperPercent((BigDecimal)attrs[i++]);
			this.setVocPercent((BigDecimal)attrs[i++]);
			this.setVocSource((String)attrs[i++]);
			this.setAltDataSource((String)attrs[i++]);
			this.setVaporPressureDetect((String)attrs[i++]);
			this.setVaporPressure((BigDecimal)attrs[i++]);
			this.setVaporPressureUpper((BigDecimal)attrs[i++]);
			this.setVaporPressureUnit((String)attrs[i++]);
			this.setVaporPressureTemp((BigDecimal)attrs[i++]);
			this.setVaporPressureTempUnit((String)attrs[i++]);
			this.setVaporPressureSource((String)attrs[i++]);
			this.setVocLessH2oExempt((BigDecimal)attrs[i++]);
			this.setVocLessH2oExemptUnit((String)attrs[i++]);
			this.setVocLessH2oExemptLower((BigDecimal)attrs[i++]);
			this.setVocLessH2oExemptUpper((BigDecimal)attrs[i++]);
			this.setVocLessH2oExemptSource((String)attrs[i++]);
			this.setVocLbPerSolidLb((BigDecimal)attrs[i++]);
			this.setVocLbPerSolidLbSource((String)attrs[i++]);
			this.setVocLbPerSolidLbLower((BigDecimal)attrs[i++]);
			this.setVocLbPerSolidLbUpper((BigDecimal)attrs[i++]);
			this.setChronic((String)attrs[i++]);
			this.setLfcCode((String)attrs[i++]);
			this.setPolymerize((String)attrs[i++]);
			this.setIncompatible((String)attrs[i++]);
			this.setCorrosive((String)attrs[i++]);
			this.setHealthEffects((String)attrs[i++]);
			this.setStable((String)attrs[i++]);
			this.setVaporDensity((String)attrs[i++]);
			this.setEvaporationRate((String)attrs[i++]);
			this.setFireConditionsToAvoid((String)attrs[i++]);
			this.setWaterReactive((String)attrs[i++]);
			this.setOxidizer((String)attrs[i++]);
			this.setCarcinogen((String)attrs[i++]);
			this.setAlloy((String)attrs[i++]);
			this.setBoilingPointDetect((String)attrs[i++]);
			this.setBoilingPointLower((BigDecimal)attrs[i++]);
			this.setBoilingPointUpper((BigDecimal)attrs[i++]);
			this.setBoilingPointUnit((String)attrs[i++]);
			this.setBoilingPointSource((String)attrs[i++]);
			this.setBoilingPointDetail((String)attrs[i++]);
			this.setPh((BigDecimal)attrs[i++]);
			this.setPhDetail((String)attrs[i++]);
			this.setPhDetect((String)attrs[i++]);
			this.setPhSource((String)attrs[i++]);
			this.setPhUpper((BigDecimal)attrs[i++]);
            this.setMsdsIndexingPriorityId((BigDecimal)attrs[i++]);
            this.setCoAlloy((String)attrs[i++]);
			this.setCoAltDataSource((String)attrs[i++]);
			this.setCoBoilingPointDetect((String)attrs[i++]);
			this.setCoBoilingPointLower((BigDecimal)attrs[i++]);
			this.setCoBoilingPointUpper((BigDecimal)attrs[i++]);
			this.setCoBoilingPointUnit((String)attrs[i++]);
			this.setCoBoilingPointSource((String)attrs[i++]);
			this.setCoBoilingPointDetail((String)attrs[i++]);
			this.setCoCarcinogen((String)attrs[i++]);
			this.setCoChronic((String)attrs[i++]);
			this.setCoCompatibility((String)attrs[i++]);
			this.setCoCompositionChanged((String)attrs[i++]);
			this.setCoContent((String)attrs[i++]);
			this.setCoCorrosive((String)attrs[i++]);
			this.setCoDetonable((String)attrs[i++]);
			this.setCoDensity((BigDecimal)attrs[i++]);
			this.setCoDensityDetect((String)attrs[i++]);
			this.setCoDensityUnit((String)attrs[i++]);
			this.setCoDensityUpper((BigDecimal)attrs[i++]);
			this.setCoEmergencyPhone((String)attrs[i++]);
			this.setCoEvaporationRate((String)attrs[i++]);
			this.setCoEyes((String)attrs[i++]);
			this.setCoFireConditionsToAvoid((String)attrs[i++]);
			this.setCoFlammability((String)attrs[i++]);
			this.setCoFlashPoint((String)attrs[i++]);
			this.setCoFlashPointDetect((String)attrs[i++]);
			this.setCoFlashPointLower((BigDecimal)attrs[i++]);
			this.setCoFlashPointMethod((String)attrs[i++]);
			this.setCoFlashPointSource((String)attrs[i++]);
			this.setCoFlashPointUnit((String)attrs[i++]);
			this.setCoFlashPointUpper((BigDecimal)attrs[i++]);
			this.setCoFreezingPoint((String)attrs[i++]);
			this.setCoFrenchContent((String)attrs[i++]);
			this.setCoHealth((String)attrs[i++]);
			this.setCoHealthEffects((String)attrs[i++]);
			this.setCoHmisChronic((String)attrs[i++]);
			this.setCoHmisFlammability((String)attrs[i++]);
			this.setCoHmisHealth((String)attrs[i++]);
			this.setCoHmisReactivity((String)attrs[i++]);
			this.setCoHmisSource((String)attrs[i++]);
			this.setCoIncompatible((String)attrs[i++]);
			this.setCoIngestion((String)attrs[i++]);
			this.setCoInhalation((String)attrs[i++]);
			this.setCoInjection((String)attrs[i++]);
				dd = (Timestamp) attrs[i++];			if( dd != null ) this.setCoInsertDate(new Date(dd.getTime()));
			this.setCoLfcCode((String)attrs[i++]);
			this.setCoLowVolumeExempt((String)attrs[i++]);
			this.setCoMaterialId((BigDecimal)attrs[i++]);
			this.setCoMiscible((String)attrs[i++]);
			this.setCoMixture((String)attrs[i++]);
			this.setCoNfpaSource((String)attrs[i++]);
			this.setCoOnLine((String)attrs[i++]);
			this.setCoOshaHazard((String)attrs[i++]);
			this.setCoOxidizer((String)attrs[i++]);
			this.setCoOzoneDepletingCompound((String)attrs[i++]);
			this.setCoPersonalProtection((String)attrs[i++]);
			this.setCoPh((BigDecimal)attrs[i++]);
			this.setCoPhysicalState((String)attrs[i++]);
			this.setCoPhysicalStateSource((String)attrs[i++]);
			this.setCoPhDetail((String)attrs[i++]);
			this.setCoPhDetect((String)attrs[i++]);
			this.setCoPhSource((String)attrs[i++]);
			this.setCoPhUpper((BigDecimal)attrs[i++]);
			this.setCoPolymerize((String)attrs[i++]);
			this.setCoPpe((String)attrs[i++]);
			this.setCoPyrophoric((String)attrs[i++]);
			this.setCoReactivity((String)attrs[i++]);
			this.setCoRemark((String)attrs[i++]);
			this.setCoReviewedBy((String)attrs[i++]);
			dd = (Timestamp) attrs[i++];			if( dd != null ) this.setCoReviewDate(new Date(dd.getTime()));
			this.setCoRouteOfEntry((String)attrs[i++]);
			this.setCoSara311312Acute((String)attrs[i++]);
			this.setCoSara311312Chronic((String)attrs[i++]);
			this.setCoSara311312Fire((String)attrs[i++]);
			this.setCoSara311312Pressure((String)attrs[i++]);
			this.setCoSara311312Reactivity((String)attrs[i++]);
			this.setCoSignalWord((String)attrs[i++]);
			this.setCoSkin((String)attrs[i++]);
			this.setCoSolids((BigDecimal)attrs[i++]);
			this.setCoSolidsLower((BigDecimal)attrs[i++]);
			this.setCoSolidsLowerPercent((BigDecimal)attrs[i++]);
			this.setCoSolidsPercent((BigDecimal)attrs[i++]);
			this.setCoSolidsSource((String)attrs[i++]);
			this.setCoSolidsUnit((String)attrs[i++]);
			this.setCoSolidsUpper((BigDecimal)attrs[i++]);
			this.setCoSolidsUpperPercent((BigDecimal)attrs[i++]);
			this.setCoSpecificGravity((BigDecimal)attrs[i++]);
			this.setCoSpecificGravityBasis((String)attrs[i++]);
			this.setCoSpecificGravityDetect((String)attrs[i++]);
			this.setCoSpecificGravityLower((BigDecimal)attrs[i++]);
			this.setCoSpecificGravityUpper((BigDecimal)attrs[i++]);
			this.setCoSpecificHazard((String)attrs[i++]);
			this.setCoSpontaneouslyCombustible((String)attrs[i++]);
			this.setCoStable((String)attrs[i++]);
			this.setCoStorageTemp((String)attrs[i++]);
			this.setCoTargetOrgan((String)attrs[i++]);
			this.setCoTsca12b((String)attrs[i++]);
			this.setCoTscaList((String)attrs[i++]);
			this.setCoTscaStatement((String)attrs[i++]);
			this.setCoVaporDensity((String)attrs[i++]);
			this.setCoVaporPressure((BigDecimal)attrs[i++]);
			this.setCoVaporPressureDetect((String)attrs[i++]);
			this.setCoVaporPressureSource((String)attrs[i++]);
			this.setCoVaporPressureTemp((BigDecimal)attrs[i++]);
			this.setCoVaporPressureTempUnit((String)attrs[i++]);
			this.setCoVaporPressureUnit((String)attrs[i++]);
			this.setCoVaporPressureUpper((BigDecimal)attrs[i++]);
			this.setCoVoc((BigDecimal)attrs[i++]);
			this.setCoVocLbPerSolidLb((BigDecimal)attrs[i++]);
			this.setCoVocLbPerSolidLbLower((BigDecimal)attrs[i++]);
			this.setCoVocLbPerSolidLbSource((String)attrs[i++]);
			this.setCoVocLbPerSolidLbUpper((BigDecimal)attrs[i++]);
			this.setCoVocLessH20ExemptSource((String)attrs[i++]);
			this.setCoVocLessH2oExempt((BigDecimal)attrs[i++]);
			this.setCoVocLessH2oExemptLower((BigDecimal)attrs[i++]);
			this.setCoVocLessH2oExemptSource((String)attrs[i++]);
			this.setCoVocLessH2oExemptUnit((String)attrs[i++]);
			this.setCoVocLessH2oExemptUpper((BigDecimal)attrs[i++]);
			this.setCoVocLower((BigDecimal)attrs[i++]);
			this.setCoVocLowerPercent((BigDecimal)attrs[i++]);
			this.setCoVocPercent((BigDecimal)attrs[i++]);
			this.setCoVocSource((String)attrs[i++]);
			this.setCoVocUnit((String)attrs[i++]);
			this.setCoVocUpper((BigDecimal)attrs[i++]);
			this.setCoVocUpperPercent((BigDecimal)attrs[i++]);
			this.setCoWaterReactive((String)attrs[i++]);
			this.setCoSpecificGravitySource((String)attrs[i++]);
			this.setCoDensitySource((String)attrs[i++]);
			this.setProductCode((String)attrs[i++]);
            this.setMfgShortName((String)attrs[i++]);
            this.setVerifiedBy((BigDecimal)attrs[i++]);
            dd = (Timestamp) attrs[i++];
            if( dd != null ) this.setVerifiedDate(new Date(dd.getTime()));
            this.setIdOnly((String)attrs[i++]);
        	this.setVerifiedByName((String)attrs[i++]);
        	this.setOzoneDepletingCompound((String)attrs[i++]);
        	this.setLowVolumeExempt((String)attrs[i++]);
        	String next = (String)attrs[i++];
        	if (next != null) 
        		this.setCompanyMsds(next.equals("Y") ? true : false);
        	else
        		setCompanyMsds(false);
        	next = (String)attrs[i++];
        	if (next != null) 
        		this.setDataEntryComplete(next.equals("Y") ? true : false);
        	else
        		setDataEntryComplete(false);
        	next = (String)attrs[i++];
        	if (next != null) 
        		this.setCoDataEntryComplete(next.equals("Y") ? true : false);
        	else
        		setCoDataEntryComplete(false);
        	this.setTscaStatement((String)attrs[i++]);
        	this.setMiscible((String)attrs[i++]);
        	this.setPyrophoric((String)attrs[i++]);
        	this.setDetonable((String)attrs[i++]);
        	i++;
        	this.setSpontaneouslyCombustible((String)attrs[i++]);
        	this.setSara311312Acute((String)attrs[i++]);
        	this.setSara311312Chronic((String)attrs[i++]);
        	this.setSara311312Fire((String)attrs[i++]);
        	this.setSara311312Pressure((String)attrs[i++]);
        	this.setSara311312Reactivity((String)attrs[i++]);
        	this.setSignalWord((String)attrs[i++]);
        	next = (String)attrs[i++];
        	if (next != null) 
        		this.setGhsCompliantImage(next.equals("Y") ? true : false);
        	this.setLabelCompanyName((String)attrs[i++]);
        	this.setLabelAddressLine1((String)attrs[i++]);
        	this.setLabelAddressLine2((String)attrs[i++]);
        	this.setLabelAddressLine3((String)attrs[i++]);
        	this.setLabelAddressLine4((String)attrs[i++]);
        	this.setLabelCity((String)attrs[i++]);
        	this.setLabelStateAbbrev((String)attrs[i++]);
        	this.setLabelCountryAbbrev((String)attrs[i++]);
        	this.setLabelZip((String)attrs[i++]);
        	this.setLabelPhone((String)attrs[i++]);
        	this.setMsdsId((BigDecimal)attrs[i++]);
        	this.setAsMixed((String)attrs[i++]);
        	next = (String)attrs[i++];
        	if (next != null) 
        		this.setGhsHazard(next.equals("Y") ? true : false); // reversed, web-page says non-hazardous, DB field says hazardous
            java.util.List list = Arrays.asList( (Object[]) ((java.sql.Array)attrs[i++]).getArray());
	   		Vector v = new Vector();
	   		this.setCompositionVar(v);
	   	
	   		for(int j=0;j<list.size();j++) {
	   			/*int k = 0;
	   			oracle.sql.STRUCT fac = (oracle.sql.STRUCT) list.get(j);
	    		Object[] attrs2 = fac.getAttributes();*/
	    		CatalogAddReqCompQcObjBean bb = new CatalogAddReqCompQcObjBean();
	    		/*bb.setMaterialId((BigDecimal)attrs2[k++]);
				dd = (Timestamp) attrs2[k++];	if( dd != null ) bb.setRevisionDate(new Date(dd.getTime()));
				bb.setPercent((BigDecimal)attrs2[k++]);
				bb.setChemicalId((String)attrs2[k++]);
				bb.setPercentAsCas((String)attrs2[k++]);
				bb.setPercentLower((BigDecimal)attrs2[k++]);
				bb.setPercentUpper((BigDecimal)attrs2[k++]);
				bb.setHazardous((String)attrs2[k++]);
				bb.setCasNumber((String)attrs2[k++]);
				bb.setTradeSecret("Y".equals((String)attrs2[k++]));
				bb.setRemark((String)attrs2[k++]);
				bb.setMsdsChemicalName((String)attrs2[k++]);
				dd = (Timestamp) attrs2[k++];  if( dd != null ) bb.setInsertDate(new Date(dd.getTime())); //bb.setInsertDate(stream.readDate());
				bb.setPercentLowerCalc((BigDecimal)attrs2[k++]);
				bb.setPercentCalc((BigDecimal)attrs2[k++]);
				bb.setPercentUpperCalc((BigDecimal)attrs2[k++]);
				bb.setDataSource((String)attrs2[k++]);
				bb.setTrace("Y".equals((String)attrs2[k++]));*/
//	    		com.tcmis.internal.catalog.beans.OpenUserFacilityObjBean cb = new com.tcmis.internal.catalog.beans.OpenUserFacilityObjBean();
//	   			cb.setCustomerId((BigDecimal)attrs2[0]);
//	   			cb.setFacilityId((String)attrs2[1]);
//	   			cb.setFacilityName((String)attrs2[2]);
	    		bb.readObject(list.get(j));
	   			v.add(bb);
	   		}	
	   		
	   		java.util.List ghsStmtList = Arrays.asList( (Object[]) ((java.sql.Array)attrs[i++]).getArray());
	   		Vector hv = new Vector();
	   		Vector pv = new Vector();
	   		this.setHazardStmts(hv);
	   		this.setPrecautionaryStmts(pv);
	   	
	   		for(int j=0;j<ghsStmtList.size();j++) {
	   			/*int k = 0;
	   			oracle.sql.STRUCT fac = (oracle.sql.STRUCT) list.get(j);
	    		Object[] attrs2 = fac.getAttributes();*/
	    		GHSStatementsViewBean bb = new GHSStatementsViewBean();
	    		bb.readObject(ghsStmtList.get(j));
	    		if (bb.getCode().startsWith("H") || bb.getCode().startsWith("EUH")) {
		    		hv.add(bb);
	    		}
	    		else if (bb.getCode().startsWith("P")) {
	    			pv.add(bb);
	    		}
	   		}
	   		
	   		java.util.List picList = Arrays.asList( (Object[]) ((java.sql.Array)attrs[i++]).getArray());
	   		v = new Vector();
	   		this.setPictogram(v);
	   	
	   		for(int j=0;j<picList.size();j++) {
	    		PictogramBean bb = new PictogramBean();
	    		bb.readObject(picList.get(j));
	   			v.add(bb);
	   		}	
	}

	public void writeSQL(SQLOutput stream) throws SQLException {
		try {
			stream.writeBigDecimal(this.getRequestId());
			stream.writeBigDecimal(this.getLineItem());
			stream.writeBigDecimal(this.getPartId());
			stream.writeBigDecimal(this.getMaterialId());
			stream.writeDate(new java.sql.Date(this.getRevisionDate().getTime()));
			stream.writeString(this.getMaterialDesc());
			stream.writeString(this.getManufacturer());
			stream.writeBigDecimal(this.getMfgId());
			stream.writeBigDecimal(this.getMsdsAuthorId());
			stream.writeString(this.getMsdsAuthorDesc());
			stream.writeString(this.getMfgTradeName());
			stream.writeString(this.getComments());
			stream.writeString(this.getMfgUrl());
			stream.writeString(this.getContact());
			stream.writeString(this.getPhone());
			stream.writeString(this.getEmail());
			stream.writeString(this.getNotes());
			stream.writeString(this.getContent());
			stream.writeString(this.getSpecificGravityDetect());
			stream.writeBigDecimal(this.getSpecificGravityLower());
			stream.writeBigDecimal(this.getSpecificGravityUpper());
			stream.writeString(this.getSpecificGravityBasis());
			stream.writeString(this.getSpecificGravitySource());
			stream.writeString(this.getHealth());
			stream.writeString(this.getFlammability());
			stream.writeString(this.getReactivity());
			stream.writeString(this.getSpecificHazard());
			stream.writeString(this.getNfpaSource());
			stream.writeString(this.getHmisHealth());
			stream.writeString(this.getHmisFlammability());
			stream.writeString(this.getHmisReactivity());
			stream.writeString(this.getPersonalProtection());
			stream.writeString(this.getHmisChronic());
			stream.writeString(this.getHmisSource());
			stream.writeString(this.getFlashPointDetect());
			stream.writeBigDecimal(this.getFlashPointLower());
			stream.writeBigDecimal(this.getFlashPointUpper());
			stream.writeString(this.getFlashPointUnit());
			stream.writeString(this.getFlashPointMethod());
			stream.writeString(this.getFlashPointSource());
			stream.writeString(this.getDensityDetect());
			stream.writeBigDecimal(this.getDensity());
			stream.writeBigDecimal(this.getDensityUpper());
			stream.writeString(this.getDensityUnit());
			stream.writeString(this.getDensitySource());
			stream.writeString(this.getPhysicalState());
			stream.writeString(this.getVocUnit());
			stream.writeBigDecimal(this.getVoc());
			stream.writeBigDecimal(this.getVocLower());
			stream.writeBigDecimal(this.getVocUpper());
			stream.writeString(this.getRemark());
			stream.writeBigDecimal(this.getSolids());
			stream.writeBigDecimal(this.getSolidsLower());
			stream.writeBigDecimal(this.getSolidsUpper());
			stream.writeString(this.getSolidsUnit());
			stream.writeString(this.getSolidsSource());
			stream.writeBigDecimal(this.getVocLowerPercent());
			stream.writeBigDecimal(this.getVocUpperPercent());
			stream.writeBigDecimal(this.getVocPercent());
			stream.writeString(this.getVocSource());
			stream.writeString(this.getAltDataSource());
			stream.writeString(this.getVaporPressureDetect());
			stream.writeBigDecimal(this.getVaporPressure());
			stream.writeBigDecimal(this.getVaporPressureUpper());
			stream.writeString(this.getVaporPressureUnit());
			stream.writeBigDecimal(this.getVaporPressureTemp());
			stream.writeString(this.getVaporPressureTempUnit());
			stream.writeString(this.getVaporPressureSource());
			stream.writeBigDecimal(this.getVocLessH2oExempt());
			stream.writeString(this.getVocLessH2oExemptUnit());
			stream.writeBigDecimal(this.getVocLessH2oExemptLower());
			stream.writeBigDecimal(this.getVocLessH2oExemptUpper());
			stream.writeString(this.getVocLessH2oExemptSource());
			stream.writeBigDecimal(this.getVocLbPerSolidLb());
			stream.writeString(this.getVocLbPerSolidLbSource());
			stream.writeBigDecimal(this.getVocLbPerSolidLbLower());
			stream.writeBigDecimal(this.getVocLbPerSolidLbUpper());
			stream.writeString(this.getChronic());
			stream.writeString(this.getLfcCode());
			stream.writeString(this.getPolymerize());
			stream.writeString(this.getIncompatible());
			stream.writeString(this.getCorrosive());
			stream.writeString(this.getHealthEffects());
			stream.writeString(this.getStable());
			stream.writeString(this.getVaporDensity());
			stream.writeString(this.getEvaporationRate());
			stream.writeString(this.getFireConditionsToAvoid());
			stream.writeString(this.getWaterReactive());
			stream.writeString(this.getOxidizer());
			stream.writeString(this.getCarcinogen());
			stream.writeString(this.getAlloy());
			stream.writeString(this.getBoilingPointDetect());
			stream.writeBigDecimal(this.getBoilingPointLower());
			stream.writeBigDecimal(this.getBoilingPointUpper());
			stream.writeString(this.getBoilingPointUnit());
			stream.writeString(this.getBoilingPointSource());
			stream.writeString(this.getBoilingPointDetail());
			stream.writeBigDecimal(this.getPh());
			stream.writeString(this.getPhDetail());
			stream.writeString(this.getPhDetect());
			stream.writeString(this.getPhSource());
			stream.writeBigDecimal(this.getPhUpper());
			stream.writeString(this.getCoAlloy());
			stream.writeString(this.getCoAltDataSource());
			stream.writeString(this.getCoBoilingPointDetect());
			stream.writeBigDecimal(this.getCoBoilingPointLower());
			stream.writeBigDecimal(this.getCoBoilingPointUpper());
			stream.writeString(this.getCoBoilingPointUnit());
			stream.writeString(this.getCoBoilingPointSource());
			stream.writeString(this.getCoBoilingPointDetail());
			stream.writeString(this.getCoCarcinogen());
			stream.writeString(this.getCoChronic());
			stream.writeString(this.getCoCompatibility());
			stream.writeString(this.getCoCompositionChanged());
			stream.writeString(this.getCoContent());
			stream.writeString(this.getCoCorrosive());
			stream.writeBigDecimal(this.getCoDensity());
			stream.writeString(this.getCoDensityDetect());
			stream.writeString(this.getCoDensityUnit());
			stream.writeBigDecimal(this.getCoDensityUpper());
			stream.writeString(this.getCoEmergencyPhone());
			stream.writeString(this.getCoEvaporationRate());
			stream.writeString(this.getCoEyes());
			stream.writeString(this.getCoFireConditionsToAvoid());
			stream.writeString(this.getCoFlammability());
			stream.writeString(this.getCoFlashPoint());
			stream.writeString(this.getCoFlashPointDetect());
			stream.writeBigDecimal(this.getCoFlashPointLower());
			stream.writeString(this.getCoFlashPointMethod());
			stream.writeString(this.getCoFlashPointSource());
			stream.writeString(this.getCoFlashPointUnit());
			stream.writeBigDecimal(this.getCoFlashPointUpper());
			stream.writeString(this.getCoFreezingPoint());
			stream.writeString(this.getCoFrenchContent());
			stream.writeString(this.getCoHealth());
			stream.writeString(this.getCoHealthEffects());
			stream.writeString(this.getCoHmisChronic());
			stream.writeString(this.getCoHmisFlammability());
			stream.writeString(this.getCoHmisHealth());
			stream.writeString(this.getCoHmisReactivity());
			stream.writeString(this.getCoHmisSource());
			stream.writeString(this.getCoIncompatible());
			stream.writeString(this.getCoIngestion());
			stream.writeString(this.getCoInhalation());
			stream.writeString(this.getCoInjection());
			stream.writeDate(new java.sql.Date(this.getCoInsertDate().getTime()));
			stream.writeString(this.getCoLfcCode());
			stream.writeBigDecimal(this.getCoMaterialId());
			stream.writeString(this.getCoMixture());
			stream.writeString(this.getCoNfpaSource());
			stream.writeString(this.getCoOnLine());
			stream.writeString(this.getCoOshaHazard());
			stream.writeString(this.getCoOxidizer());
			stream.writeString(this.getCoPersonalProtection());
			stream.writeBigDecimal(this.getCoPh());
			stream.writeString(this.getCoPhysicalState());
			stream.writeString(this.getCoPhDetail());
			stream.writeString(this.getCoPhDetect());
			stream.writeString(this.getCoPhSource());
			stream.writeBigDecimal(this.getCoPhUpper());
			stream.writeString(this.getCoPolymerize());
			stream.writeString(this.getCoPpe());
			stream.writeString(this.getCoReactivity());
			stream.writeString(this.getCoRemark());
			stream.writeString(this.getCoReviewedBy());
			stream.writeDate(new java.sql.Date(this.getCoReviewDate().getTime()));
			stream.writeString(this.getCoRouteOfEntry());
			stream.writeString(this.getCoSara311312Acute());
			stream.writeString(this.getCoSara311312Chronic());
			stream.writeString(this.getCoSara311312Fire());
			stream.writeString(this.getCoSara311312Pressure());
			stream.writeString(this.getCoSara311312Reactivity());
			stream.writeString(this.getCoSignalWord());
			stream.writeString(this.getCoSkin());
			stream.writeBigDecimal(this.getCoSolids());
			stream.writeBigDecimal(this.getCoSolidsLower());
			stream.writeBigDecimal(this.getCoSolidsLowerPercent());
			stream.writeBigDecimal(this.getCoSolidsPercent());
			stream.writeString(this.getCoSolidsSource());
			stream.writeString(this.getCoSolidsUnit());
			stream.writeBigDecimal(this.getCoSolidsUpper());
			stream.writeBigDecimal(this.getCoSolidsUpperPercent());
			stream.writeBigDecimal(this.getCoSpecificGravity());
			stream.writeString(this.getCoSpecificGravityBasis());
			stream.writeString(this.getCoSpecificGravityDetect());
			stream.writeBigDecimal(this.getCoSpecificGravityLower());
			stream.writeBigDecimal(this.getCoSpecificGravityUpper());
			stream.writeString(this.getCoSpecificHazard());
			stream.writeString(this.getCoStable());
			stream.writeString(this.getCoStorageTemp());
			stream.writeString(this.getCoTargetOrgan());
			stream.writeString(this.getCoTsca12b());
			stream.writeString(this.getCoTscaList());
			stream.writeString(this.getCoVaporDensity());
			stream.writeBigDecimal(this.getCoVaporPressure());
			stream.writeString(this.getCoVaporPressureDetect());
			stream.writeString(this.getCoVaporPressureSource());
			stream.writeBigDecimal(this.getCoVaporPressureTemp());
			stream.writeString(this.getCoVaporPressureTempUnit());
			stream.writeString(this.getCoVaporPressureUnit());
			stream.writeBigDecimal(this.getCoVaporPressureUpper());
			stream.writeBigDecimal(this.getCoVoc());
			stream.writeBigDecimal(this.getCoVocLbPerSolidLb());
			stream.writeBigDecimal(this.getCoVocLbPerSolidLbLower());
			stream.writeString(this.getCoVocLbPerSolidLbSource());
			stream.writeBigDecimal(this.getCoVocLbPerSolidLbUpper());
			stream.writeString(this.getCoVocLessH20ExemptSource());
			stream.writeBigDecimal(this.getCoVocLessH2oExempt());
			stream.writeBigDecimal(this.getCoVocLessH2oExemptLower());
			stream.writeString(this.getCoVocLessH2oExemptSource());
			stream.writeString(this.getCoVocLessH2oExemptUnit());
			stream.writeBigDecimal(this.getCoVocLessH2oExemptUpper());
			stream.writeBigDecimal(this.getCoVocLower());
			stream.writeBigDecimal(this.getCoVocLowerPercent());
			stream.writeBigDecimal(this.getCoVocPercent());
			stream.writeString(this.getCoVocSource());
			stream.writeString(this.getCoVocUnit());
			stream.writeBigDecimal(this.getCoVocUpper());
			stream.writeBigDecimal(this.getCoVocUpperPercent());
			stream.writeString(this.getCoWaterReactive());
			stream.writeString(this.getCoSpecificGravitySource());
			stream.writeString(this.getCoDensitySource());
			stream.writeString(this.getProductCode());
            stream.writeString(this.getMfgShortName());
            stream.writeBigDecimal(this.getVerifiedBy());
            stream.writeDate(new java.sql.Date(this.getVerifiedDate().getTime()));
            stream.writeString(this.getIdOnly());
            stream.writeString(this.getVerifiedByName());
            stream.writeString(this.getOzoneDepletingCompound());
            stream.writeString(this.getLowVolumeExempt());
            stream.writeString(this.isCompanyMsds() ? "Y" : "N");
            stream.writeString(this.isDataEntryComplete() ? "Y" : "N");
            stream.writeString(this.isCoDataEntryComplete() ? "Y" : "N");
            stream.writeArray(this.getCompositionVarArray());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").
			initCause(e);
		}
	}

	public String getMfgShortName() {
        return mfgShortName;
    }

    public void setMfgShortName(String mfgShortName) {
        this.mfgShortName = mfgShortName;
    }

    public String getMsdsVerified() {
        return msdsVerified;
    }

    public void setMsdsVerified(String msdsVerified) {
        this.msdsVerified = msdsVerified;
    }

    public BigDecimal getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(BigDecimal verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public Date getVerifiedDate() {
        return verifiedDate;
    }

    public void setVerifiedDate(Date verifiedDate) {
        this.verifiedDate = verifiedDate;
    }

    public String getKitMsds() {
        return kitMsds;
    }

    public void setKitMsds(String kitMsds) {
        this.kitMsds = kitMsds;
    }

    public String getComponentMsds() {
        return componentMsds;
    }

    public void setComponentMsds(String componentMsds) {
        this.componentMsds = componentMsds;
    }

	public String getCountryAbbrev() {
		return countryAbbrev;
	}

	public void setCountryAbbrev(String countryAbbrev) {
		this.countryAbbrev = countryAbbrev;
	}

	public String getVerifiedByName() {
		return verifiedByName;
	}

	public void setVerifiedByName(String verifiedByName) {
		this.verifiedByName = verifiedByName;
	}

	public boolean isDataEntryComplete() {
		return dataEntryComplete;
	}

	public boolean isCoDataEntryComplete() {
		return coDataEntryComplete;
	}

	public void setDataEntryComplete(boolean dataEntryComplete) {
		this.dataEntryComplete = dataEntryComplete;
	}

	public void setCoDataEntryComplete(boolean coDataEntryComplete) {
		this.coDataEntryComplete = coDataEntryComplete;
	}
	
	public boolean isSaveCustomerOverride() {
		return saveCustomerOverride;
	}

	public void setSaveCustomerOverride(boolean saveCustomerOverride) {
		this.saveCustomerOverride = saveCustomerOverride;
	}
	
	public void setMsdsAuthorId(BigDecimal msdsAuthorId) {
		this.msdsAuthorId = msdsAuthorId;
	}
	
	public void setMsdsAuthorDesc(String msdsAuthorDesc) {
		this.msdsAuthorDesc = msdsAuthorDesc;
	}

	public String getDetonable() {
		return detonable;
	}

	public String getMiscible() {
		return miscible;
	}

	public String getPyrophoric() {
		return pyrophoric;
	}

	public String getSpontaneouslyCombustible() {
		return coSpontaneouslyCombustible;
	}

	public String getTscaStatement() {
		return tscaStatement;
	}

	public void setDetonable(String detonable) {
		this.detonable = detonable;
	}

	public void setMiscible(String miscible) {
		this.miscible = miscible;
	}

	public void setPyrophoric(String pyrophoric) {
		this.pyrophoric = pyrophoric;
	}

	public void setSpontaneouslyCombustible(String spontaneouslyCombustible) {
		this.spontaneouslyCombustible = spontaneouslyCombustible;
	}

	public void setTscaStatement(String tscaStatement) {
		this.tscaStatement = tscaStatement;
	}

	public String getMsdsAuthorCountryAbbrev() {
		return msdsAuthorCountryAbbrev;
	}

	public String getMsdsAuthorNotes() {
		return msdsAuthorNotes;
	}

	public void setMsdsAuthorCountryAbbrev(String msdsAuthorCountryAbbrev) {
		this.msdsAuthorCountryAbbrev = msdsAuthorCountryAbbrev;
	}

	public void setMsdsAuthorNotes(String msdsAuthorNotes) {
		this.msdsAuthorNotes = msdsAuthorNotes;
	}

	public String getCoOzoneDepletingCompound() {
		return coOzoneDepletingCompound;
	}

	public String getCoLowVolumeExempt() {
		return coLowVolumeExempt;
	}

	public String getCoDetonable() {
		return coDetonable;
	}

	public String getCoMiscible() {
		return coMiscible;
	}

	public String getCoPyrophoric() {
		return coPyrophoric;
	}

	public String getCoSpontaneouslyCombustible() {
		return coSpontaneouslyCombustible;
	}

	public String getCoTscaStatement() {
		return coTscaStatement;
	}

	public String getPhysicalStateSource() {
		return physicalStateSource;
	}

	public String getCoPhysicalStateSource() {
		return coPhysicalStateSource;
	}
	
	public void setCoOzoneDepletingCompound(String coOzoneDepletingCompound) {
		this.coOzoneDepletingCompound = coOzoneDepletingCompound;
	}

	public void setCoLowVolumeExempt(String coLowVolumeExempt) {
		this.coLowVolumeExempt = coLowVolumeExempt;
	}

	public void setCoDetonable(String coDetonable) {
		this.coDetonable = coDetonable;
	}

	public void setCoMiscible(String coMiscible) {
		this.coMiscible = coMiscible;
	}

	public void setCoPyrophoric(String coPyrophoric) {
		this.coPyrophoric = coPyrophoric;
	}

	public void setCoSpontaneouslyCombustible(String coSpontaneouslyCombustible) {
		this.coSpontaneouslyCombustible = coSpontaneouslyCombustible;
	}

	public void setCoTscaStatement(String coTscaStatement) {
		this.coTscaStatement = coTscaStatement;
	}

	public void setPhysicalStateSource(String physicalStateSource) {
		this.physicalStateSource = physicalStateSource;
	}

	public void setCoPhysicalStateSource(String coPhysicalStateSource) {
		this.coPhysicalStateSource = coPhysicalStateSource;
	}

    public BigDecimal getMsdsIndexingPriorityId() {
        return msdsIndexingPriorityId;
    }

    public void setMsdsIndexingPriorityId(BigDecimal msdsIndexingPriorityId) {
        this.msdsIndexingPriorityId = msdsIndexingPriorityId;
    }

    public String getImageLocaleCode() {
        return imageLocaleCode;
    }

    public void setImageLocaleCode(String imageLocaleCode) {
        this.imageLocaleCode = imageLocaleCode;
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

	public String getLabelCompanyName() {
		return labelCompanyName;
	}

	public void setLabelCompanyName(String labelCompanyName) {
		this.labelCompanyName = labelCompanyName;
	}

	public String getLabelPhone() {
		return labelPhone;
	}

	public void setLabelPhone(String labelPhone) {
		this.labelPhone = labelPhone;
	}

	public String getSignalWord() {
		return signalWord;
	}

	public void setSignalWord(String signalWord) {
		this.signalWord = signalWord;
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

	public String getLabelCountryAbbrev() {
		return labelCountryAbbrev;
	}

	public void setLabelCountryAbbrev(String labelCountryAbbrev) {
		this.labelCountryAbbrev = labelCountryAbbrev;
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

	public void setPrecautionaryStmts(Collection<GHSStatementsViewBean> precautionaryStmts) {
		this.precautionaryStmts = precautionaryStmts;
	}

	public boolean isGhsCompliantImage() {
		return ghsCompliantImage;
	}

	public void setGhsCompliantImage(boolean ghsCompliantImage) {
		this.ghsCompliantImage = ghsCompliantImage;
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

	public Collection<PictogramBean> getPictogram() {
		return pictogram;
	}

	public void setPictogram(Collection<PictogramBean> pictogram) {
		this.pictogram = pictogram;
	}

	public String getCustomerMixtureNumber() {
		return customerMixtureNumber;
	}

	public void setCustomerMixtureNumber(String customerMixtureNumber) {
		this.customerMixtureNumber = customerMixtureNumber;
	}
}