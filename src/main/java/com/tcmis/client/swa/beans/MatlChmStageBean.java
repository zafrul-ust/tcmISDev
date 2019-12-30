package com.tcmis.client.swa.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: MatlChmBean <br>
 * @version: 1.0, Apr 19, 2007 <br>
 *****************************************************************************/


public class MatlChmStageBean extends BaseDataBean {

	private String partNumber;
	private String manfPartNumber;
	private String partName;
	private String partDesc;
	private String unitOfIssue;
	private BigDecimal inventory;
	private Date startDate;
	private Date stopDate;
	private String chemicalFlag;
	private BigDecimal conversionFactor;
	private Date tcmLoadDate;


	//constructor
	public MatlChmStageBean() {
	}

	//setters
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public void setManfPartNumber(String manfPartNumber) {
		this.manfPartNumber = manfPartNumber;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}
	public void setUnitOfIssue(String unitOfIssue) {
		this.unitOfIssue = unitOfIssue;
	}
	public void setInventory(BigDecimal inventory) {
		this.inventory = inventory;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}
	public void setChemicalFlag(String chemicalFlag) {
		this.chemicalFlag = chemicalFlag;
	}
	public void setConversionFactor(BigDecimal conversionFactor) {
		this.conversionFactor = conversionFactor;
	}
	public void setTcmLoadDate(Date tcmLoadDate) {
		this.tcmLoadDate = tcmLoadDate;
	}


	//getters
	public String getPartNumber() {
		return partNumber;
	}
	public String getManfPartNumber() {
		return manfPartNumber;
	}
	public String getPartName() {
		return partName;
	}
	public String getPartDesc() {
		return partDesc;
	}
	public String getUnitOfIssue() {
		return unitOfIssue;
	}
	public BigDecimal getInventory() {
		return inventory;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getStopDate() {
		return stopDate;
	}
	public String getChemicalFlag() {
		return chemicalFlag;
	}
	public BigDecimal getConversionFactor() {
		return conversionFactor;
	}
	public Date getTcmLoadDate() {
		return tcmLoadDate;
	}
}