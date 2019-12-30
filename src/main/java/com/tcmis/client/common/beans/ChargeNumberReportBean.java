package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseDataBean;

public class ChargeNumberReportBean extends BaseDataBean {

	private String chargeLabel1;
	private String chargeLabel2;
	private String chargeLabel3;
	private String chargeLabel4;
	private String chargeType;
	private String applicationDesc;
	private String application;
	private String catPartNo;
	private String chargeNumber1;
	private String chargeNumber2;
	private String chargeNumber3;
	private String chargeNumber4;
	private BigDecimal percent;
	private String poRequired;
	private String poNumber;
	private String poLine;
	private String prAccountRequired;
	private String chargeTypeDefault;
    private String deptName;


    public ChargeNumberReportBean() {
		super();
	}
	
	public void setChargeTypeDefault(String chargeTypeDefault) {
		this.chargeTypeDefault = chargeTypeDefault;
	}
	
	public String getChargeTypeDefault() {
		return chargeTypeDefault;
	}
	
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	
	public String getPoNumber() {
		return poNumber;
	}
	
	public void setPoLine(String poLine) {
		this.poLine = poLine;
	}
	
	public String getPoLine() {
		return poLine;
	}
	
	public BigDecimal getPercent() {
		return percent;
	}
	
	public void setPrAccountRequired(String prAccountRequired) {
		this.prAccountRequired = prAccountRequired;
	}
	
	public String getPrAccountRequired() {
		return prAccountRequired;
	}
	
	public void setPoRequired(String poRequired) {
		this.poRequired = poRequired;
	}
	
	public String getPoRequired() {
		return poRequired;
	}
	
	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}
	
	public String getCatPartNo() {
		return catPartNo;
	}
	
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	
	public String getApplication() {
		return application;
	}
	
	public void setApplication(String application) {
		this.application = application;
	}
	
	public String getApplicationDesc() {
		return applicationDesc;
	}
	
	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}
	
	public String getChargeNumber4() {
		return chargeNumber4;
	}
	
	public void setChargeNumber4(String chargeNumber4) {
		this.chargeNumber4 = chargeNumber4;
	}
	
	public String getChargeNumber3() {
		return chargeNumber3;
	}
	
	public void setChargeNumber3(String chargeNumber3) {
		this.chargeNumber3 = chargeNumber3;
	}
	
	public String getChargeNumber2() {
		return chargeNumber2;
	}
	
	public void setChargeNumber2(String chargeNumber2) {
		this.chargeNumber2 = chargeNumber2;
	}
	
	public String getChargeNumber1() {
		return chargeNumber1;
	}
	
	public void setChargeNumber1(String chargeNumber1) {
		this.chargeNumber1 = chargeNumber1;
	}
    
	public String getChargeLabel4() {
		return chargeLabel4;
	}
	
	public void setChargeLabel4(String chargeLabel4) {
		this.chargeLabel4 = chargeLabel4;
	}
	
	public String getChargeLabel3() {
		return chargeLabel3;
	}
	
	public void setChargeLabel3(String chargeLabel3) {
		this.chargeLabel3 = chargeLabel3;
	}
	
	public String getChargeLabel2() {
		return chargeLabel2;
	}
	
	public void setChargeLabel2(String chargeLabel2) {
		this.chargeLabel2 = chargeLabel2;
	}
	
	public String getChargeLabel1() {
		return chargeLabel1;
	}
	
	public void setChargeLabel1(String chargeLabel1) {
		this.chargeLabel1 = chargeLabel1;
	}

	public String getChargeType() {
		return chargeType;
	}
	
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
