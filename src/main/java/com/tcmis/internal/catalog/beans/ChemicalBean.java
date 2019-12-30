package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;


/******************************************************************************
 * CLASSNAME: ChemicalBean <br>
 * @version: 1.0, Jan 11, 2008 <br>
 *****************************************************************************/


public class ChemicalBean extends BaseDataBean {

	private String casNumber;
	private String preferredName;
	private BigDecimal molecularWeight;
	private String chemicalFormula;
	private String commonName;
	private BigDecimal specificGravity;
	private String ecNumber;


	//constructor
	public ChemicalBean() {
	}

	//setters
	public void setCasNumber(String casNumber) {
		this.casNumber = casNumber;
	}
	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}
	public void setMolecularWeight(BigDecimal molecularWeight) {
		this.molecularWeight = molecularWeight;
	}
	public void setChemicalFormula(String chemicalFormula) {
		this.chemicalFormula = chemicalFormula;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public void setSpecificGravity(BigDecimal specificGravity) {
		this.specificGravity = specificGravity;
	}
	public void setEcNumber(String ecNumber) {
		this.ecNumber = ecNumber;
	}

	public boolean isTradeSecret() {
		return !StringHandler.isBlankString(casNumber) && casNumber.startsWith("TS");
	}

	//getters
	public String getCasNumber() {
		return casNumber;
	}
	public String getPreferredName() {
		return preferredName;
	}
	public BigDecimal getMolecularWeight() {
		return molecularWeight;
	}
	public String getChemicalFormula() {
		return chemicalFormula;
	}
	public String getCommonName() {
		return commonName;
	}
	public BigDecimal getSpecificGravity() {
		return specificGravity;
	}
	public String getEcNumber() {
		return ecNumber;
	}
}