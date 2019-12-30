package com.tcmis.client.common.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: MaterialCompositionViewBean <br>
 * @version: 1.0, Jul 12, 2011 <br>
 *****************************************************************************/


public class MaterialCompositionViewBean extends BaseDataBean {

	private BigDecimal materialId;
	private Date revisionDate;
	private String tradeName;
	private BigDecimal specificGravity;
	private BigDecimal density;
	private String densityUnit;
	private String preferredName;
	private String displayName;
	private String chemicalId;
	private String casNumber;
	private BigDecimal percent;
	private String tradeSecret;
	private String hazardous;
	private BigDecimal percentLower;
	private BigDecimal percentUpper;
	private BigDecimal mfgId;
	private String mfgDesc;
	private String physicalState;
	private String health;
	private String flammability;
	private String reactivity;
	private String flashPoint;
	private String flashPointUnit;
	private BigDecimal voc;
	private BigDecimal vocLower;
	private BigDecimal vocUpper;
	private String vocUnit;
	private BigDecimal vocPercent;
	private BigDecimal vocLowerPercent;
	private BigDecimal vocUpperPercent;
	private String ecNumber;
	private String companyChemicalId;
	private String trace;

	//constructor
	public MaterialCompositionViewBean() {
	}

	//setters
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public void setSpecificGravity(BigDecimal specificGravity) {
		this.specificGravity = specificGravity;
	}
	public void setDensity(BigDecimal density) {
		this.density = density;
	}
	public void setDensityUnit(String densityUnit) {
		this.densityUnit = densityUnit;
	}
	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public void setChemicalId(String chemicalId) {
		this.chemicalId = chemicalId;
	}
	public void setCasNumber(String casNumber) {
		this.casNumber = casNumber;
	}
	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}
	public void setTradeSecret(String tradeSecret) {
		this.tradeSecret = tradeSecret;
	}
	public void setHazardous(String hazardous) {
		this.hazardous = hazardous;
	}
	public void setPercentLower(BigDecimal percentLower) {
		this.percentLower = percentLower;
	}
	public void setPercentUpper(BigDecimal percentUpper) {
		this.percentUpper = percentUpper;
	}
	public void setMfgId(BigDecimal mfgId) {
		this.mfgId = mfgId;
	}
	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}
	public void setPhysicalState(String physicalState) {
		this.physicalState = physicalState;
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
	public void setFlashPoint(String flashPoint) {
		this.flashPoint = flashPoint;
	}
	public void setFlashPointUnit(String flashPointUnit) {
		this.flashPointUnit = flashPointUnit;
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
	public void setVocUnit(String vocUnit) {
		this.vocUnit = vocUnit;
	}
	public void setVocPercent(BigDecimal vocPercent) {
		this.vocPercent = vocPercent;
	}
	public void setVocLowerPercent(BigDecimal vocLowerPercent) {
		this.vocLowerPercent = vocLowerPercent;
	}
	public void setVocUpperPercent(BigDecimal vocUpperPercent) {
		this.vocUpperPercent = vocUpperPercent;
	}
	public void setEcNumber(String ecNumber) {
		this.ecNumber = ecNumber;
	}
	public void setTrace(String trace) {
		this.trace = trace;
	}
	//getters
	public String getTrace() {
		return trace;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public Date getRevisionDate() {
		return revisionDate;
	}
	public String getTradeName() {
		return tradeName;
	}
	public BigDecimal getSpecificGravity() {
		return specificGravity;
	}
	public BigDecimal getDensity() {
		return density;
	}
	public String getDensityUnit() {
		return densityUnit;
	}
	public String getPreferredName() {
		return preferredName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public String getChemicalId() {
		return chemicalId;
	}
	public String getCasNumber() {
		return casNumber;
	}
	public BigDecimal getPercent() {
		return percent;
	}
	public String getTradeSecret() {
		return tradeSecret;
	}
	public String getHazardous() {
		return hazardous;
	}
	public BigDecimal getPercentLower() {
		return percentLower;
	}
	public BigDecimal getPercentUpper() {
		return percentUpper;
	}
	public BigDecimal getMfgId() {
		return mfgId;
	}
	public String getMfgDesc() {
		return mfgDesc;
	}
	public String getPhysicalState() {
		return physicalState;
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
	public String getFlashPoint() {
		return flashPoint;
	}
	public String getFlashPointUnit() {
		return flashPointUnit;
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
	public String getVocUnit() {
		return vocUnit;
	}
	public BigDecimal getVocPercent() {
		return vocPercent;
	}
	public BigDecimal getVocLowerPercent() {
		return vocLowerPercent;
	}
	public BigDecimal getVocUpperPercent() {
		return vocUpperPercent;
	}
	public String getEcNumber() {
		return ecNumber;
	}

	public String getCompanyChemicalId() {
		return companyChemicalId;
	}

	public void setCompanyChemicalId(String companyChemicalId) {
		this.companyChemicalId = companyChemicalId;
	}

}