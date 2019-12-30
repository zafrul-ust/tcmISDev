package com.tcmis.client.common.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: MsdsViewerInputBean <br>
 * @version: 1.0, May 18, 2011 <br>
 *****************************************************************************/


public class MsdsViewerInputBean extends BaseDataBean {

	private String facilityId;
	private String application;
	private String searchField;
	private String searchText;
	private String matchType;

	private BigDecimal mfgId;
	private String fullDatabase;
	private String physicalState;
	private String phCompare;
	private BigDecimal ph;
	private String flashPointCompare;
	private BigDecimal flashPoint;
	private String vaporPressureCompare;
	private String vaporPressureUnit;
	private BigDecimal vaporPressure;
	private String vocPercentCompare;
	private BigDecimal vocPercent;
	private String solidsPercentCompare;
	private BigDecimal solidsPercent;
	
	private BigDecimal health;
	private String healthCompare;
	private BigDecimal flammability;
	private String flammabilityCompare;
	private BigDecimal reactivity;
	private String reactivityCompare;
	private String specificHazard;
	
	private BigDecimal hmisHealth;
	private String hmisHealthCompare;
	private BigDecimal hmisFlammability;
	private String hmisFlammabilityCompare;
	private BigDecimal hmisReactivity;
	private String hmisReactivityCompare;
	private String personalProtection;
	
	private String listOrCasNos;
	private String list[];
	private String casNumberAndOr;
	private String casNumberAndOrString;
	private String casNumberString;

//	private String searchMode;
	private String temperatureUnit;
// indicate simple or advance search	
	private String hideOrShowDiv;
	private String companyId;
	private String simpleSearchText;
// when advance search.	
	private String applicationList;
	private String approvedOnly;
	private String kitOnly;
	private String stocked;

    //msds only
    private String msdsOnly;
    private String listString;
    
    //JIRA DEV944
    private String vocSearchSelect;
    private String vocLwesSearchSelect;
    private String vocLwesPercentCompare;
    private BigDecimal vocLwesPercent;
	private String gridType="";
	private String gridSubmit;
	
	private String mfgDesc;
	private String matchTypeDesc;
	private String specificHazardDesc;
	private String personalProtectionDesc;
	private String gridDesc;
	private String searchFieldDesc;
	private String manufacturer;
	private String facilityName;
	private String applicationDesc = "";
    private String showMsds;
    private String showCASNumber;
    private String sourcePage;
    
    public String getVocLwesPercentCompare() {
		return vocLwesPercentCompare;
	}

	public void setVocLwesPercentCompare(String vocLwesPercentCompare) {
		this.vocLwesPercentCompare = vocLwesPercentCompare;
	}
	
    public BigDecimal getVocLwesPercent() {
		return vocLwesPercent;
	}

	public void setVocLwesPercent(BigDecimal vocLwesPercent) {
		this.vocLwesPercent = vocLwesPercent;
	}
    
    public String getVocLwesSearchSelect() {
		return vocLwesSearchSelect;
	}

	public void setVocLwesSearchSelect(String vocLwesSearchSelect) {
		this.vocLwesSearchSelect = vocLwesSearchSelect;
	}
    
    public String getVocSearchSelect() {
		return vocSearchSelect;
	}

	public void setVocSearchSelect(String vocSearchSelect) {
		this.vocSearchSelect = vocSearchSelect;
	}
    
    public String getApprovedOnly() {
		return approvedOnly;
	}

	public void setApprovedOnly(String approvedOnly) {
		this.approvedOnly = approvedOnly;
	}

	//constructor
	public MsdsViewerInputBean() {
	}

	public String getCasNumberAndOrString() {
		return casNumberAndOrString;
	}

	public void setCasNumberAndOrString(String casNumberAndOrString) {
		this.casNumberAndOrString = casNumberAndOrString;
	}

	public String getCasNumberString() {
		return casNumberString;
	}

	public void setCasNumberString(String casNumberString) {
		this.casNumberString = casNumberString;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public BigDecimal getFlammability() {
		return flammability;
	}

	public void setFlammability(BigDecimal flammability) {
		this.flammability = flammability;
	}

	public String getFlammabilityCompare() {
		return flammabilityCompare;
	}

	public void setFlammabilityCompare(String flammabilityCompare) {
		this.flammabilityCompare = flammabilityCompare;
	}

	public BigDecimal getFlashPoint() {
		return flashPoint;
	}

	public void setFlashPoint(BigDecimal flashPoint) {
		this.flashPoint = flashPoint;
	}

	public String getFlashPointCompare() {
		return flashPointCompare;
	}

	public void setFlashPointCompare(String flashPointCompare) {
		this.flashPointCompare = flashPointCompare;
	}

	public String getFullDatabase() {
		return fullDatabase;
	}

	public void setFullDatabase(String fullDatabase) {
		this.fullDatabase = fullDatabase;
	}

	public BigDecimal getHealth() {
		return health;
	}

	public void setHealth(BigDecimal health) {
		this.health = health;
	}

	public String getHealthCompare() {
		return healthCompare;
	}

	public void setHealthCompare(String healthCompare) {
		this.healthCompare = healthCompare;
	}

	public BigDecimal getHmisFlammability() {
		return hmisFlammability;
	}

	public void setHmisFlammability(BigDecimal hmisFlammability) {
		this.hmisFlammability = hmisFlammability;
	}

	public String getHmisFlammabilityCompare() {
		return hmisFlammabilityCompare;
	}

	public void setHmisFlammabilityCompare(String hmisFlammabilityCompare) {
		this.hmisFlammabilityCompare = hmisFlammabilityCompare;
	}

	public BigDecimal getHmisHealth() {
		return hmisHealth;
	}

	public void setHmisHealth(BigDecimal hmisHealth) {
		this.hmisHealth = hmisHealth;
	}

	public String getHmisHealthCompare() {
		return hmisHealthCompare;
	}

	public void setHmisHealthCompare(String hmisHealthCompare) {
		this.hmisHealthCompare = hmisHealthCompare;
	}

	public BigDecimal getHmisReactivity() {
		return hmisReactivity;
	}

	public void setHmisReactivity(BigDecimal hmisReactivity) {
		this.hmisReactivity = hmisReactivity;
	}

	public String getHmisReactivityCompare() {
		return hmisReactivityCompare;
	}

	public void setHmisReactivityCompare(String hmisReactivityCompare) {
		this.hmisReactivityCompare = hmisReactivityCompare;
	}

	public String[] getList() {
		return list;
	}

	public void setList(String[] list) {
		this.list = list;
	}

	public String getListOrCasNos() {
		return listOrCasNos;
	}

	public void setListOrCasNos(String listOrCasNos) {
		this.listOrCasNos = listOrCasNos;
	}

	public BigDecimal getMfgId() {
		return mfgId;
	}

	public void setMfgId(BigDecimal mfgId) {
		this.mfgId = mfgId;
	}

	public String getPersonalProtection() {
		return personalProtection;
	}

	public void setPersonalProtection(String personalProtection) {
		this.personalProtection = personalProtection;
	}

	public BigDecimal getPh() {
		return ph;
	}

	public void setPh(BigDecimal ph) {
		this.ph = ph;
	}

	public String getPhCompare() {
		return phCompare;
	}

	public void setPhCompare(String phCompare) {
		this.phCompare = phCompare;
	}

	public String getPhysicalState() {
		return physicalState;
	}

	public void setPhysicalState(String physicalState) {
		this.physicalState = physicalState;
	}

	public BigDecimal getReactivity() {
		return reactivity;
	}

	public void setReactivity(BigDecimal reactivity) {
		this.reactivity = reactivity;
	}

	public String getReactivityCompare() {
		return reactivityCompare;
	}

	public void setReactivityCompare(String reactivityCompare) {
		this.reactivityCompare = reactivityCompare;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public BigDecimal getSolidsPercent() {
		return solidsPercent;
	}

	public void setSolidsPercent(BigDecimal solidsPercent) {
		this.solidsPercent = solidsPercent;
	}

	public String getSolidsPercentCompare() {
		return solidsPercentCompare;
	}

	public void setSolidsPercentCompare(String solidsPercentCompare) {
		this.solidsPercentCompare = solidsPercentCompare;
	}

	public String getSpecificHazard() {
		return specificHazard;
	}

	public void setSpecificHazard(String specificHazard) {
		this.specificHazard = specificHazard;
	}

	public String getTemperatureUnit() {
		return temperatureUnit;
	}

	public void setTemperatureUnit(String temperatureUnit) {
		this.temperatureUnit = temperatureUnit;
	}

	public BigDecimal getVaporPressure() {
		return vaporPressure;
	}

	public void setVaporPressure(BigDecimal vaporPressure) {
		this.vaporPressure = vaporPressure;
	}

	public String getVaporPressureCompare() {
		return vaporPressureCompare;
	}

	public void setVaporPressureCompare(String vaporPressureCompare) {
		this.vaporPressureCompare = vaporPressureCompare;
	}

	public String getVaporPressureUnit() {
		return vaporPressureUnit;
	}

	public void setVaporPressureUnit(String vaporPressureUnit) {
		this.vaporPressureUnit = vaporPressureUnit;
	}

	public BigDecimal getVocPercent() {
		return vocPercent;
	}

	public void setVocPercent(BigDecimal vocPercent) {
		this.vocPercent = vocPercent;
	}

	public String getVocPercentCompare() {
		return vocPercentCompare;
	}

	public void setVocPercentCompare(String vocPercentCompare) {
		this.vocPercentCompare = vocPercentCompare;
	}

	public String getCasNumberAndOr() {
		return casNumberAndOr;
	}

	public void setCasNumberAndOr(String casNumberAndOr) {
		this.casNumberAndOr = casNumberAndOr;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getHideOrShowDiv() {
		return hideOrShowDiv;
	}

	public void setHideOrShowDiv(String hideOrShowDiv) {
		this.hideOrShowDiv = hideOrShowDiv;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getApplicationList() {
		return applicationList;
	}

	public void setApplicationList(String applicationList) {
		this.applicationList = applicationList;
	}
	public String getSimpleSearchText() {
		return simpleSearchText;
	}

	public void setSimpleSearchText(String simpleSearchText) {
		this.simpleSearchText = simpleSearchText;
	}

    public String getMsdsOnly() {
        return msdsOnly;
    }

    public void setMsdsOnly(String msdsOnly) {
        this.msdsOnly = msdsOnly;
    }

	public String getListString() {
		return listString;
	}

	public void setListString(String listString) {
		this.listString = listString;
	}

	public String getKitOnly() {
		return kitOnly;
	}

	public void setKitOnly(String kitOnly) {
		this.kitOnly = kitOnly;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	public String getStocked() {
		return stocked;
	}

	public void setStocked(String stocked) {
		this.stocked = stocked;
	}
	public String getGridType() {
		return gridType;
	}
	
	public void setGridType(String gridType) {
		this.gridType = gridType;
	}
	
	public String getGridSubmit() {
		return gridSubmit;
	}
	
	public void setGridSubmit(String gridSubmit) {
		this.gridSubmit = gridSubmit;
	}
	
	public String getSearchFieldDesc()
	{
		return searchFieldDesc;
	}
	
	public void setSearchFieldDesc(String searchFieldDesc)
	{
		this.searchFieldDesc = searchFieldDesc;
	}
	
	public String getManufacturer()
	{
		return manufacturer;
	}
	
	public void setManufacturer(String manufacturer)
	{
		this.manufacturer = manufacturer;
	}
	
	public String getMatchTypeDesc()
	{
		return matchTypeDesc;
	}
	
	public void setMatchTypeDesc(String matchTypeDesc)
	{
		this.matchTypeDesc = matchTypeDesc;
	}
	
	public String getSpecificHazardDesc()
	{
		return specificHazardDesc;
	}
	
	public void setSpecificHazardDesc(String specificHazardDesc)
	{
		this.specificHazardDesc = specificHazardDesc;
	}
	
	public String getPersonalProtectionDesc()
	{
		return personalProtectionDesc;
	}
	public void setPersonalProtectionDesc(String personalProtectionDesc)
	{
		this.personalProtectionDesc = personalProtectionDesc;
	}
	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}
	public String getMfgDesc() {
		return mfgDesc;
	}
	public String getGridDesc() {
		return gridDesc;
	}
	
	public void setGridDesc(String gridDesc) {
		this.gridDesc = gridDesc;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	
	public String getFacilityName() {
		return facilityName;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}
	
	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

    public String getShowMsds() {
        return showMsds;
    }

    public void setShowMsds(String showMsds) {
        this.showMsds = showMsds;
    }

    public String getShowCASNumber() {
        return showCASNumber;
    }

    public void setShowCASNumber(String showCASNumber) {
        this.showCASNumber = showCASNumber;
    }

    public String getSourcePage() {
        return sourcePage;
    }

    public void setSourcePage(String sourcePage) {
        this.sourcePage = sourcePage;
    }
}   //end of class