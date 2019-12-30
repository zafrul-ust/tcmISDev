package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class PropertyVersionBean extends BaseDataBean {
	
	private BigDecimal materialId;
	private Date revisionDate;
	private String health;
	private String flammability;
	private String reactivity;
	private String ppe;
	private String hmisHealth;
	private String hmisFlammability;
	private String hmisReactivity;
	private String personalProtection;
	private String density;
	private String voc;
	private String vocLessH2oExempt;
	private String flashPoint;
	private String boilingPoint;
	private String vaporPressure;
	
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
	
	//constructor
	public PropertyVersionBean() {
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

	public String getPpe() {
		return ppe;
	}

	public void setPpe(String ppe) {
		this.ppe = ppe;
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

	
	public String getFlashPoint() {
		return flashPoint;
	}

	public void setFlashPoint(String flashPoint) {
		this.flashPoint = flashPoint;
	}

	public String getBoilingPoint() {
		return boilingPoint;
	}

	public void setBoilingPoint(String boilingPoint) {
		this.boilingPoint = boilingPoint;
	}

	
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

	public String getVoc() {
		return voc;
	}

	public void setVoc(String voc) {
		this.voc = voc;
	}

	public String getDensity() {
		return density;
	}

	public void setDensity(String density) {
		this.density = density;
	}

	public String getVocLessH2oExempt() {
		return vocLessH2oExempt;
	}

	public void setVocLessH2oExempt(String vocLessH2oExempt) {
		this.vocLessH2oExempt = vocLessH2oExempt;
	}

	public String getVaporPressure() {
		return vaporPressure;
	}

	public void setVaporPressure(String vaporPressure) {
		this.vaporPressure = vaporPressure;
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

	
}
	