package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: Cfr49HazardousMaterialViewBean <br>
 * 
 * @version: 1.0, Nov 5, 2007 <br>
 *****************************************************************************/

public class IataViewBean extends BaseDataBean {

	private String packingGroup;
	private BigDecimal shippingNameCount;
	private BigDecimal iataDgId ;
	private BigDecimal hazmatId;
	private String properShippingNameDesc;
	private String classOrDivision;
	private String identificationNumber;
	private String technicalNameRequired;
	private String cOnlyComment;
	private String cOnlyG;
	private String cOnlyMaxNetQtyPerPkg;
	private String cOnlyMaxNetUnitPerPkg;
	private BigDecimal cOnlyMaxNetValPerPkg;
	private String cOnlyPkgInstr;
	private String ergCode;
	private String hazardLabel;
	private String pCComment;
	private String pCG;
	private String pCLtdQtyG;
	private String pCLtdQtyMaxNetQtyPerPkg;
	private String pCLtdQtyMaxNetUntPerPkg;
	private BigDecimal pCLtdQtyMaxNetValPerPkg;
	private String pCLtdQtyPkgInstr;
	private String pCMaxNetQtyPerPkg;
	private String pCMaxNetUnitPerPkg;
	private BigDecimal pCMaxNetValPerPkg;;
	private String pCPkgInstr;
	private String pickable;
	private String specialProvision;
	private String subrisk;
	private String properShippingName;

	// constructor
	public IataViewBean() {
	}

	// setters
	public void setPackingGroup(String packingGroup) {
		this.packingGroup = packingGroup;
	}

	public void setShippingNameCount(BigDecimal shippingNameCount) {
		this.shippingNameCount = shippingNameCount;
	}

	public void setIataDgId(BigDecimal iataDgId) {
		this.iataDgId = iataDgId;
	}

	public void setHazmatId(BigDecimal hazmatId) {
		this.hazmatId = hazmatId;
	}

	public void setProperShippingNameDesc(String properShippingNameDesc) {
		this.properShippingNameDesc = properShippingNameDesc;
	}

	public void setClassOrDivision(String classOrDivision) {
		this.classOrDivision = classOrDivision;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public void setTechnicalNameRequired(String technicalNameRequired) {
		this.technicalNameRequired = technicalNameRequired;
	}

	public void setcOnlyComment(String cOnlyComment) {
		this.cOnlyComment = cOnlyComment;
	}

	public void setcOnlyG(String cOnlyG) {
		this.cOnlyG = cOnlyG;
	}

	public void setcOnlyMaxNetQtyPerPkg(String cOnlyMaxNetQtyPerPkg) {
		this.cOnlyMaxNetQtyPerPkg = cOnlyMaxNetQtyPerPkg;
	}

	public void setcOnlyMaxNetUnitPerPkg(String cOnlyMaxNetUnitPerPkg) {
		this.cOnlyMaxNetUnitPerPkg = cOnlyMaxNetUnitPerPkg;
	}

	public void setcOnlyMaxNetValPerPkg(BigDecimal cOnlyMaxNetValPerPkg) {
		this.cOnlyMaxNetValPerPkg = cOnlyMaxNetValPerPkg;
	}

	public void setcOnlyPkgInstr(String cOnlyPkgInstr) {
		this.cOnlyPkgInstr = cOnlyPkgInstr;
	}

	public void setErgCode(String ergCode) {
		this.ergCode = ergCode;
	}

	public void setHazardLabel(String hazardLabel) {
		this.hazardLabel = hazardLabel;
	}

	public void setpCComment(String pCComment) {
		this.pCComment = pCComment;
	}

	public void setpCG(String pCG) {
		this.pCG = pCG;
	}

	public void setpCLtdQtyG(String pCLtdQtyG) {
		this.pCLtdQtyG = pCLtdQtyG;
	}

	public void setpCLtdQtyMaxNetQtyPerPkg(String pCLtdQtyMaxNetQtyPerPkg) {
		this.pCLtdQtyMaxNetQtyPerPkg = pCLtdQtyMaxNetQtyPerPkg;
	}

	public void setpCLtdQtyMaxNetUntPerPkg(String pCLtdQtyMaxNetUntPerPkg) {
		this.pCLtdQtyMaxNetUntPerPkg = pCLtdQtyMaxNetUntPerPkg;
	}

	public void setpCLtdQtyMaxNetValPerPkg(BigDecimal pCLtdQtyMaxNetValPerPkg) {
		this.pCLtdQtyMaxNetValPerPkg = pCLtdQtyMaxNetValPerPkg;
	}

	public void setpCLtdQtyPkgInstr(String pCLtdQtyPkgInstr) {
		this.pCLtdQtyPkgInstr = pCLtdQtyPkgInstr;
	}

	public void setpCMaxNetQtyPerPkg(String pCMaxNetQtyPerPkg) {
		this.pCMaxNetQtyPerPkg = pCMaxNetQtyPerPkg;
	}

	public void setpCMaxNetUnitPerPkg(String pCMaxNetUnitPerPkg) {
		this.pCMaxNetUnitPerPkg = pCMaxNetUnitPerPkg;
	}

	public void setpCMaxNetValPerPkg(BigDecimal pCMaxNetValPerPkg) {
		this.pCMaxNetValPerPkg = pCMaxNetValPerPkg;
	}

	public void setpCPkgInstr(String pCPkgInstr) {
		this.pCPkgInstr = pCPkgInstr;
	}

	public void setPickable(String pickable) {
		this.pickable = pickable;
	}

	public void setSpecialProvision(String specialProvision) {
		this.specialProvision = specialProvision;
	}

	public void setSubrisk(String subrisk) {
		this.subrisk = subrisk;
	}
	
	public void setProperShippingName(String properShippingName) {
		this.properShippingName = properShippingName;
	}

	// getters

	public String getProperShippingName() {
		return properShippingName;
	}
	
	public String getPackingGroup() {
		return packingGroup;
	}

	public String getProperShippingNameDesc() {
		return properShippingNameDesc;
	}

	public BigDecimal getShippingNameCount() {
		return shippingNameCount;
	}

	public BigDecimal getIataDgId() {
		return this.iataDgId;
	}

	public BigDecimal getHazmatId() {
		return this.hazmatId;
	}

	public String getClassOrDivision() {
		return this.classOrDivision;
	}

	public String getIdentificationNumber() {
		return this.identificationNumber;
	}

	public String getTechnicalNameRequired() {
		return this.technicalNameRequired;
	}

	public String getcOnlyComment() {
		return this.cOnlyComment;
	}

	public String getcOnlyG() {
		return this.cOnlyG;
	}

	public String getcOnlyMaxNetQtyPerPkg() {
		return this.cOnlyMaxNetQtyPerPkg;
	}

	public String getcOnlyMaxNetUnitPerPkg() {
		return this.cOnlyMaxNetUnitPerPkg;
	}

	public BigDecimal getcOnlyMaxNetValPerPkg() {
		return this.cOnlyMaxNetValPerPkg;
	}

	public String getcOnlyPkgInstr() {
		return this.cOnlyPkgInstr;
	}

	public String getErgCode() {
		return this.ergCode;
	}

	public String getHazardLabel() {
		return this.hazardLabel;
	}

	public String getpCComment() {
		return this.pCComment;
	}

	public String getpCG() {
		return this.pCG;
	}

	public String getpCLtdQtyG() {
		return this.pCLtdQtyG;
	}

	public String getpCLtdQtyMaxNetQtyPerPkg() {
		return this.pCLtdQtyMaxNetQtyPerPkg;
	}

	public String getpCLtdQtyMaxNetUntPerPkg() {
		return this.pCLtdQtyMaxNetUntPerPkg;
	}

	public BigDecimal getpCLtdQtyMaxNetValPerPkg() {
		return this.pCLtdQtyMaxNetValPerPkg;
	}

	public String getpCLtdQtyPkgInstr() {
		return this.pCLtdQtyPkgInstr;
	}

	public String getpCMaxNetQtyPerPkg() {
		return this.pCMaxNetQtyPerPkg;
	}

	public String getpCMaxNetUnitPerPkg() {
		return this.pCMaxNetUnitPerPkg;
	}

	public BigDecimal getpCMaxNetValPerPkg() {
		return this.pCMaxNetValPerPkg;
	}

	public String getpCPkgInstr() {
		return this.pCPkgInstr;
	}

	public String getPickable() {
		return this.pickable;
	}

	public String getSpecialProvision() {
		return this.specialProvision;

	}

	public String getSubrisk() {
		return this.subrisk;
	}

}