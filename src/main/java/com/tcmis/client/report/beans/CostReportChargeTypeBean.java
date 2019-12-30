package com.tcmis.client.report.beans;


import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CostReportChargeTypeBean <br>
 * @version: 1.0, Oct 6, 2005 <br>
 *****************************************************************************/


public class CostReportChargeTypeBean extends BaseDataBean {

	private String chargeType;
	private String poRequired;
	private String prAccountRequired;
	private String chargeLabel1;
	private String chargeLabel2;

	//constructor
	public CostReportChargeTypeBean() {
	}

	//setters
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public void setPoRequired(String poRequired) {
		this.poRequired = poRequired;
	}
	public void setPrAccountRequired(String prAccountRequired) {
		this.prAccountRequired = prAccountRequired;
	}
	public void setChargeLabel1(String chargeLabel1) {
		this.chargeLabel1 = chargeLabel1;
	}
	public void SetChargeLabel2(String chargeLabel2) {
		this.chargeLabel2 = chargeLabel2;
	}

	//getters
	public String getChargeType() {
		return chargeType;
	}
	public String getPoRequired() {
		return poRequired;
	}
	public String getPrAccountRequired() {
		return prAccountRequired;
	}
	public String getChargeLabel1() {
		return chargeLabel1;
	}
	public String getChargeLabel2() {
		return chargeLabel2;
	}
}