package com.tcmis.client.ups.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: UpsDangerousGoodsViewBean <br>
 * @version: 1.0, Jan 29, 2009 <br>
 *****************************************************************************/


public class UpsDangerousGoodsViewBean extends BaseDataBean {

	private BigDecimal referenceNumber;
	private String regulationSet;
	private BigDecimal reportableQuantity;
	private String shippingName;
	private String technicalName;
	private String hazardClass;
	private String subriskClass;
	private String identificationNumber;
	private String packingGroupNumber;
	private String additionalDesc;
	private BigDecimal quantity;
	private String unitOfMeasure;
	private String packageType;
	private String customPackageType;
	private String packingInstructions;
	private String transportationMode;
	private String labelRequired;
	private String emergencyPhone;
	private String dangerousGoodsOption;
	private BigDecimal materialId;
	private String upsForbidden;
	private String dotLabelType;
	private String offeror;


	public String getDotLabelType() {
		return dotLabelType;
	}

	public void setDotLabelType(String dotLabelType) {
		this.dotLabelType = dotLabelType;
	}

	//constructor
	public UpsDangerousGoodsViewBean() {
	}

	//setters
	public void setReferenceNumber(BigDecimal referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public void setRegulationSet(String regulationSet) {
		if(regulationSet != null && this.doTrim) {
			this.regulationSet = regulationSet.trim();
		}
		else {
			this.regulationSet = regulationSet;
		}
	}
	public void setReportableQuantity(BigDecimal reportableQuantity) {
		this.reportableQuantity = reportableQuantity;
	}
	public void setShippingName(String shippingName) {
		if(shippingName != null && this.doTrim) {
			this.shippingName = shippingName.trim();
		}
		else {
			this.shippingName = shippingName;
		}
	}
	public void setTechnicalName(String technicalName) {
		if(technicalName != null && this.doTrim) {
			this.technicalName = technicalName.trim();
		}
		else {
			this.technicalName = technicalName;
		}
	}
	public void setHazardClass(String hazardClass) {
		if(hazardClass != null && this.doTrim) {
			this.hazardClass = hazardClass.trim();
		}
		else {
			this.hazardClass = hazardClass;
		}
	}
	public void setSubriskClass(String subriskClass) {
		if(subriskClass != null && this.doTrim) {
			this.subriskClass = subriskClass.trim();
		}
		else {
			this.subriskClass = subriskClass;
		}
	}
	public void setIdentificationNumber(String identificationNumber) {
		if(identificationNumber != null && this.doTrim) {
			this.identificationNumber = identificationNumber.trim();
		}
		else {
			this.identificationNumber = identificationNumber;
		}
	}
	public void setPackingGroupNumber(String packingGroupNumber) {
		if(packingGroupNumber != null && this.doTrim) {
			this.packingGroupNumber = packingGroupNumber.trim();
		}
		else {
			this.packingGroupNumber = packingGroupNumber;
		}
	}
	public void setAdditionalDesc(String additionalDesc) {
		if(additionalDesc != null && this.doTrim) {
			this.additionalDesc = additionalDesc.trim();
		}
		else {
			this.additionalDesc = additionalDesc;
		}
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		if(unitOfMeasure != null && this.doTrim) {
			this.unitOfMeasure = unitOfMeasure.trim();
		}
		else {
			this.unitOfMeasure = unitOfMeasure;
		}
	}
	public void setPackageType(String packageType) {
		if(packageType != null && this.doTrim) {
			this.packageType = packageType.trim();
		}
		else {
			this.packageType = packageType;
		}
	}
	public void setCustomPackageType(String customPackageType) {
		if(customPackageType != null && this.doTrim) {
			this.customPackageType = customPackageType.trim();
		}
		else {
			this.customPackageType = customPackageType;
		}
	}
	public void setPackingInstructions(String packingInstructions) {
		if(packingInstructions != null && this.doTrim) {
			this.packingInstructions = packingInstructions.trim();
		}
		else {
			this.packingInstructions = packingInstructions;
		}
	}
	public void setTransportationMode(String transportationMode) {
		if(transportationMode != null && this.doTrim) {
			this.transportationMode = transportationMode.trim();
		}
		else {
			this.transportationMode = transportationMode;
		}
	}
	public void setLabelRequired(String labelRequired) {
		if(labelRequired != null && this.doTrim) {
			this.labelRequired = labelRequired.trim();
		}
		else {
			this.labelRequired = labelRequired;
		}
	}
	public void setEmergencyPhone(String emergencyPhone) {
		if(emergencyPhone != null && this.doTrim) {
			this.emergencyPhone = emergencyPhone.trim();
		}
		else {
			this.emergencyPhone = emergencyPhone;
		}
	}
	public void setDangerousGoodsOption(String dangerousGoodsOption) {
		if(dangerousGoodsOption != null && this.doTrim) {
			this.dangerousGoodsOption = dangerousGoodsOption.trim();
		}
		else {
			this.dangerousGoodsOption = dangerousGoodsOption;
		}
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public void setUpsForbidden(String upsForbidden) {
		if(upsForbidden != null && this.doTrim) {
			this.upsForbidden = upsForbidden.trim();
		}
		else {
			this.upsForbidden = upsForbidden;
		}
	}


	//getters
	public BigDecimal getReferenceNumber() {
		return referenceNumber;
	}
	public String getRegulationSet() {
		return regulationSet;
	}
	public BigDecimal getReportableQuantity() {
		return reportableQuantity;
	}
	public String getShippingName() {
		return shippingName;
	}
	public String getTechnicalName() {
		return technicalName;
	}
	public String getHazardClass() {
		return hazardClass;
	}
	public String getSubriskClass() {
		return subriskClass;
	}
	public String getIdentificationNumber() {
		return identificationNumber;
	}
	public String getPackingGroupNumber() {
		return packingGroupNumber;
	}
	public String getAdditionalDesc() {
		return additionalDesc;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	public String getPackageType() {
		return packageType;
	}
	public String getCustomPackageType() {
		return customPackageType;
	}
	public String getPackingInstructions() {
		return packingInstructions;
	}
	public String getTransportationMode() {
		return transportationMode;
	}
	public String getLabelRequired() {
		return labelRequired;
	}
	public String getEmergencyPhone() {
		return emergencyPhone;
	}
	public String getDangerousGoodsOption() {
		return dangerousGoodsOption;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public String getUpsForbidden() {
		return upsForbidden;
	}

	public String getOfferor() {
		return offeror;
	}

	public void setOfferor(String offeror) {
		this.offeror = offeror;
	}
	
}