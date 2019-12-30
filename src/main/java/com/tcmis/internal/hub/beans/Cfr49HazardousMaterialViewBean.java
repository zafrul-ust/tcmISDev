package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: Cfr49HazardousMaterialViewBean <br>
 * @version: 1.0, Nov 5, 2007 <br>
 *****************************************************************************/


public class Cfr49HazardousMaterialViewBean extends BaseDataBean 
{

	private BigDecimal hazmatId;
	private String symbol;
	private String hazardousMaterialDescription;
	private String hazardClass;
	private String identificationNumber;
	private String packingGroup;
	private String labelCode;
	private String specialProvision;
	private String packagingException;
	private String packagingNonbulk;
	private String packagingBulk;
	private String quantityLimitationPassenger;
	private String quantityLimitationCargo;
	private String vesselStowageLocation;
	private String vesselStowageOther;
	private String properShippingName;
	private String pickable;
	private BigDecimal referenceHazmatId;
	private BigDecimal shippingNameCount;


	//constructor
	public Cfr49HazardousMaterialViewBean() {
	}

	//setters
	public void setHazmatId(BigDecimal hazmatId) {
		this.hazmatId = hazmatId;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public void setHazardousMaterialDescription(String hazardousMaterialDescription) {
		this.hazardousMaterialDescription = hazardousMaterialDescription;
	}
	public void setHazardClass(String hazardClass) {
		this.hazardClass = hazardClass;
	}
	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}
	public void setPackingGroup(String packingGroup) {
		this.packingGroup = packingGroup;
	}
	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}
	public void setSpecialProvision(String specialProvision) {
		this.specialProvision = specialProvision;
	}
	public void setPackagingException(String packagingException) {
		this.packagingException = packagingException;
	}
	public void setPackagingNonbulk(String packagingNonbulk) {
		this.packagingNonbulk = packagingNonbulk;
	}
	public void setPackagingBulk(String packagingBulk) {
		this.packagingBulk = packagingBulk;
	}
	public void setQuantityLimitationPassenger(String quantityLimitationPassenger) {
		this.quantityLimitationPassenger = quantityLimitationPassenger;
	}
	public void setQuantityLimitationCargo(String quantityLimitationCargo) {
		this.quantityLimitationCargo = quantityLimitationCargo;
	}
	public void setVesselStowageLocation(String vesselStowageLocation) {
		this.vesselStowageLocation = vesselStowageLocation;
	}
	public void setVesselStowageOther(String vesselStowageOther) {
		this.vesselStowageOther = vesselStowageOther;
	}
	public void setProperShippingName(String properShippingName) {
		this.properShippingName = properShippingName;
	}
	public void setPickable(String pickable) {
		this.pickable = pickable;
	}
	public void setReferenceHazmatId(BigDecimal referenceHazmatId) {
		this.referenceHazmatId = referenceHazmatId;
	}
	public void setShippingNameCount(BigDecimal shippingNameCount) {
		this.shippingNameCount = shippingNameCount;
	}


	//getters
	public BigDecimal getHazmatId() {
		return hazmatId;
	}
	public String getSymbol() {
		return symbol;
	}
	public String getHazardousMaterialDescription() {
		return hazardousMaterialDescription;
	}
	public String getHazardClass() {
		return hazardClass;
	}
	public String getIdentificationNumber() {
		return identificationNumber;
	}
	public String getPackingGroup() {
		return packingGroup;
	}
	public String getLabelCode() {
		return labelCode;
	}
	public String getSpecialProvision() {
		return specialProvision;
	}
	public String getPackagingException() {
		return packagingException;
	}
	public String getPackagingNonbulk() {
		return packagingNonbulk;
	}
	public String getPackagingBulk() {
		return packagingBulk;
	}
	public String getQuantityLimitationPassenger() {
		return quantityLimitationPassenger;
	}
	public String getQuantityLimitationCargo() {
		return quantityLimitationCargo;
	}
	public String getVesselStowageLocation() {
		return vesselStowageLocation;
	}
	public String getVesselStowageOther() {
		return vesselStowageOther;
	}
	public String getProperShippingName() {
		return properShippingName;
	}
	public String getPickable() {
		return pickable;
	}
	public BigDecimal getReferenceHazmatId() {
		return referenceHazmatId;
	}
	public BigDecimal getShippingNameCount() {
		return shippingNameCount;
	}
}