package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.internal.catalog.beans.GHSStatementsViewBean;
import com.tcmis.internal.catalog.beans.ManufacturerBean;
import com.tcmis.internal.catalog.beans.PictogramBean;

public class MsdsIndexingKitBean extends BaseDataBean {
	
	private boolean allowMixture;
	private String mixtureDesc;
	private String mixtureManufacturer;
	private BigDecimal mixtureMfgId;
	private String mixtureProductCode;
	private String mixturePhysicalState;
	private String mixturePhysicalStateSource;
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
	private String facilityId;
	private BigDecimal kitSize;
	private String customerMixtureNumber;
	
	public boolean isAllowMixture() {
		return allowMixture;
	}
	
	public void setAllowMixture(boolean allowMixture) {
		this.allowMixture = allowMixture;
	}

	public String getMixtureDesc() {
		return mixtureDesc;
	}

	public void setMixtureDesc(String mixtureDesc) {
		this.mixtureDesc = mixtureDesc;
	}

	public String getMixtureManufacturer() {
		return mixtureManufacturer;
	}

	public void setMixtureManufacturer(String mixtureManufacturer) {
		this.mixtureManufacturer = mixtureManufacturer;
	}

	public BigDecimal getMixtureMfgId() {
		return mixtureMfgId;
	}

	public void setMixtureMfgId(BigDecimal mixtureMfgId) {
		this.mixtureMfgId = mixtureMfgId;
	}

	public String getMixtureProductCode() {
		return mixtureProductCode;
	}

	public void setMixtureProductCode(String mixtureProductCode) {
		this.mixtureProductCode = mixtureProductCode;
	}

	public String getMixturePhysicalState() {
		return mixturePhysicalState;
	}

	public void setMixturePhysicalState(String mixturePhysicalState) {
		this.mixturePhysicalState = mixturePhysicalState;
	}

	public String getMixturePhysicalStateSource() {
		return mixturePhysicalStateSource;
	}

	public void setMixturePhysicalStateSource(String mixturePhysicalStateSource) {
		this.mixturePhysicalStateSource = mixturePhysicalStateSource;
	}

	public BigDecimal getMixtureVoc() {
		return mixtureVoc;
	}

	public void setMixtureVoc(BigDecimal mixtureVoc) {
		this.mixtureVoc = mixtureVoc;
	}

	public BigDecimal getMixtureVocLower() {
		return mixtureVocLower;
	}

	public void setMixtureVocLower(BigDecimal mixtureVocLower) {
		this.mixtureVocLower = mixtureVocLower;
	}

	public BigDecimal getMixtureVocUpper() {
		return mixtureVocUpper;
	}

	public void setMixtureVocUpper(BigDecimal mixtureVocUpper) {
		this.mixtureVocUpper = mixtureVocUpper;
	}

	public String getMixtureVocUnit() {
		return mixtureVocUnit;
	}

	public void setMixtureVocUnit(String mixtureVocUnit) {
		this.mixtureVocUnit = mixtureVocUnit;
	}

	public String getMixtureVocSource() {
		return mixtureVocSource;
	}

	public void setMixtureVocSource(String mixtureVocSource) {
		this.mixtureVocSource = mixtureVocSource;
	}

	public BigDecimal getMixtureVocLwes() {
		return mixtureVocLwes;
	}

	public void setMixtureVocLwes(BigDecimal mixtureVocLwes) {
		this.mixtureVocLwes = mixtureVocLwes;
	}

	public BigDecimal getMixtureVocLwesLower() {
		return mixtureVocLwesLower;
	}

	public void setMixtureVocLwesLower(BigDecimal mixtureVocLwesLower) {
		this.mixtureVocLwesLower = mixtureVocLwesLower;
	}

	public BigDecimal getMixtureVocLwesUpper() {
		return mixtureVocLwesUpper;
	}

	public void setMixtureVocLwesUpper(BigDecimal mixtureVocLwesUpper) {
		this.mixtureVocLwesUpper = mixtureVocLwesUpper;
	}

	public String getMixtureVocLwesUnit() {
		return mixtureVocLwesUnit;
	}

	public void setMixtureVocLwesUnit(String mixtureVocLwesUnit) {
		this.mixtureVocLwesUnit = mixtureVocLwesUnit;
	}

	public String getMixtureVocLwesSource() {
		return mixtureVocLwesSource;
	}

	public void setMixtureVocLwesSource(String mixtureVocLwesSource) {
		this.mixtureVocLwesSource = mixtureVocLwesSource;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public BigDecimal getKitSize() {
		return kitSize;
	}

	public void setKitSize(BigDecimal kitSize) {
		this.kitSize = kitSize;
	}

	public String getCustomerMixtureNumber() {
		return customerMixtureNumber;
	}

	public void setCustomerMixtureNumber(String customerMixtureNumber) {
		this.customerMixtureNumber = customerMixtureNumber;
	}
}
