package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CompositionBean <br>
 * @version: 1.0, Dec 13, 2010 <br>
 *****************************************************************************/

public class CompositionBean extends BaseDataBean {

	private String casNumber;
	private String chemicalId;
	private String dataSource;
	private String hazardous;
	private Date insertDate;
	private BigDecimal materialId;
	private String msdsChemicalName;
	private BigDecimal percent;
	private String percentAsCas;
	private BigDecimal percentCalc;
	private BigDecimal percentLower;
	private BigDecimal percentLowerCalc;
	private BigDecimal percentUpper;
	private BigDecimal percentUpperCalc;
	private String remark;
	private Date revisionDate;
	private String trace;
	private String tradeSecret;
	private BigDecimal sdsSectionId;

	//constructor
	public CompositionBean() {
	}

	public String getCasNumber() {
		return casNumber;
	}

	public String getChemicalId() {
		return chemicalId;
	}

	public String getDataSource() {
		return dataSource;
	}

	public String getHazardous() {
		return hazardous;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	//getters
	public BigDecimal getMaterialId() {
		return materialId;
	}

	public String getMsdsChemicalName() {
		return msdsChemicalName;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public String getPercentAsCas() {
		return percentAsCas;
	}

	public BigDecimal getPercentCalc() {
		return percentCalc;
	}

	public BigDecimal getPercentLower() {
		return percentLower;
	}

	public BigDecimal getPercentLowerCalc() {
		return percentLowerCalc;
	}

	public BigDecimal getPercentUpper() {
		return percentUpper;
	}

	public BigDecimal getPercentUpperCalc() {
		return percentUpperCalc;
	}

	public String getRemark() {
		return remark;
	}

	public Date getRevisionDate() {
		return revisionDate;
	}

	public String getTrace() {
		return trace;
	}

	public String getTradeSecret() {
		return tradeSecret;
	}

	public void setCasNumber(String casNumber) {
		this.casNumber = casNumber;
	}

	public void setChemicalId(String chemicalId) {
		this.chemicalId = chemicalId;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public void setHazardous(String hazardous) {
		this.hazardous = hazardous;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	//setters
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public void setMsdsChemicalName(String msdsChemicalName) {
		this.msdsChemicalName = msdsChemicalName;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public void setPercentAsCas(String percentAsCas) {
		this.percentAsCas = percentAsCas;
	}

	public void setPercentCalc(BigDecimal percentCalc) {
		this.percentCalc = percentCalc;
	}

	public void setPercentLower(BigDecimal percentLower) {
		this.percentLower = percentLower;
	}

	public void setPercentLowerCalc(BigDecimal percentLowerCalc) {
		this.percentLowerCalc = percentLowerCalc;
	}

	public void setPercentUpper(BigDecimal percentUpper) {
		this.percentUpper = percentUpper;
	}

	public void setPercentUpperCalc(BigDecimal percentUpperCalc) {
		this.percentUpperCalc = percentUpperCalc;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	public void setTradeSecret(String tradeSecret) {
		this.tradeSecret = tradeSecret;
	}

	public BigDecimal getSdsSectionId() {
		return sdsSectionId;
	}

	public void setSdsSectionId(BigDecimal sdsSectionId) {
		this.sdsSectionId = sdsSectionId;
	}
}