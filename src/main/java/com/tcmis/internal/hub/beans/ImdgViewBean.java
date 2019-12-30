package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: Cfr49HazardousMaterialViewBean <br>
 * 
 * @version: 1.0, Nov 5, 2007 <br>
 *****************************************************************************/

public class ImdgViewBean extends BaseDataBean {

	private String classOrDivision;
	private String packingGroup;
	private String specialProvision;
	private BigDecimal shippingNameCount;
	private String limitedQuantity;
	private String packingInstruction;
	private String specialPackingProvision;
	private String ibcSpecialProvision;
	private String imoTankInstruction;
	private String unTankAndBulkContInstr;
	private String tankSpecialProvision;
	private String ems;
	private String stowageAndSegregation;
	private String property;
	private String observation;
	private String star;
	private String state;
	private String technicalNameRequired;
	private String subsidiaryRisk;
	private String imdgId; 
	private String unNumber; 
	private String description;
	private String properShippingName;

	// constructor
	public ImdgViewBean() {
	}

	// setters
	public void setPackingGroup(String packingGroup) {
		this.packingGroup = packingGroup;
	}

	public void setShippingNameCount(BigDecimal shippingNameCount) {
		this.shippingNameCount = shippingNameCount;
	}

	public void setClassOrDivision(String classOrDivision) {
		this.classOrDivision = classOrDivision;
	}

	public void setSpecialProvision(String specialProvision) {
		this.specialProvision = specialProvision;
	}
	
	public void setLimitedQuantity(String limitedQuantity) 
	{
		this.limitedQuantity = limitedQuantity;
	}
	public void  setPackingInstruction(String packingInstruction)
	{
		this.packingInstruction = packingInstruction;
	}
	public void setSpecialPackingProvision (String specialPackingProvision)
	{
		this.specialPackingProvision = specialPackingProvision;
	}
	public void setIbcSpecialProvision (String ibcSpecialProvision)
	{
		this.ibcSpecialProvision= ibcSpecialProvision;
	}
	public void  setImoTankInstruction(String imoTankInstruction)
	{
		this.imoTankInstruction= imoTankInstruction;
	}
	public void  setUnTankAndBulkContInstr(String unTankAndBulkContInstr)
	{
		this.unTankAndBulkContInstr= unTankAndBulkContInstr;
	}
	public void  setTankSpecialProvision (String tankSpecialProvision)
	{
		this.tankSpecialProvision= tankSpecialProvision;
	}
	public void setEms (String ems)
	{
		this.ems = ems;
	}
	public void setStowageAndSegregation (String stowageAndSegregation)
	{
		this.stowageAndSegregation = stowageAndSegregation;
	}
	public void setProperty (String property)
	{
		this.property= property;
	}
	public void setObservation (String observation)
	{
		this.observation= observation;
	}
	public void setStar (String star)
	{
		this.star= star;
	}
	public void setState (String state)
	{
		this.state= state;	
	}
	public void setSubsidiaryRisk (String subsidiaryRisk)
	{
		this.subsidiaryRisk= subsidiaryRisk;
	}
	public void setImdgId (String imdgId)
	{
		this.imdgId= imdgId; 
	}
	public void setUnNumber (String unNumber)
	{
		this.unNumber= unNumber; 
	}
	public void setDescription (String description)
	{
		this.description= description;
	}
	public void setProperShippingName (String properShippingName)
	{
		this.properShippingName = properShippingName;
	}

	// getters
	
	public String getProperShippingName () {
		return properShippingName;
	}

	public String getPackingGroup() {
		return packingGroup;
	}

	public BigDecimal getShippingNameCount() {
		return shippingNameCount;
	}

	public String getClassOrDivision() {
		return this.classOrDivision;
	}

	public String getSpecialProvision() {
		return this.specialProvision;

	}
	
	public String getLimitedQuantity()
	{
		return limitedQuantity;
	}
	public String getPackingInstruction()
	{
		return packingInstruction;
	}
	public String getSpecialPackingProvision()
	{
		return specialPackingProvision;
	}
	public String getIbcSpecialProvision()
	{
		return ibcSpecialProvision;
	}
	public String getImoTankInstruction()
	{
		return imoTankInstruction;
	}
	public String getUnTankAndBulkContInstr()
	{
		return unTankAndBulkContInstr;
	}
	public String getTankSpecialProvision()
	{
		return tankSpecialProvision;
	}
	public String getEms()
	{
		return ems;
	}
	public String getStowageAndSegregation()
	{
		return stowageAndSegregation;
	}
	public String getProperty()
	{
		return property;
	}
	public String getObservation()
	{
		return observation;
	}
	public String getStar()
	{
		return star;
	}
	public String getState()
	{
		return state;
	}
	public String getSubsidiaryRisk()
	{
		return subsidiaryRisk;
	}
	public String getImdgId()
	{
		return imdgId;
	}
	public String getUnNumber()
	{
		return unNumber;
	}
	public String getDescription()
	{
		return description;
	}

	public String getTechnicalNameRequired() {
		return technicalNameRequired;
	}

	public void setTechnicalNameRequired(String technicalNameRequired) {
		this.technicalNameRequired = technicalNameRequired;
	}
}