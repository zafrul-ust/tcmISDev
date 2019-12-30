package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CostReportInitialViewBean <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class CostReportInitialViewBean extends BaseDataBean {

	private String costReportGroup;
	private String facilityName;
	private String application;
	private String facilityId;
	private String accountSysId;
	private String chargeType;
	private String poRequired;
	private String prAccountRequired;
	private String chargeLabel11;
	private String chargeLabel22;
	private String applicationDesc;
	private String accountType;
	private BigDecimal personnelId;
	private String companyId;
	private String companyName;


	//constructor
	public CostReportInitialViewBean() {
	}

	//setters
	public void setCostReportGroup(String costReportGroup) {
		this.costReportGroup = costReportGroup;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public void setPoRequired(String poRequired) {
		this.poRequired = poRequired;
	}
	public void setPrAccountRequired(String prAccountRequired) {
		this.prAccountRequired = prAccountRequired;
	}
	public void setChargeLabel1(String chargeLabel11) {
		this.chargeLabel11 = chargeLabel11;
	}
	public void setChargeLabel2(String chargeLabel22) {
		this.chargeLabel22 = chargeLabel22;
	}
	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	//getters
	public String getCostReportGroup() {
		return costReportGroup;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public String getApplication() {
		return application;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getAccountSysId() {
		return accountSysId;
	}
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
		return chargeLabel11;
	}
	public String getChargeLabel2() {
		return chargeLabel22;
	}
	public String getApplicationDesc() {
		return applicationDesc;
	}
	public String getAccountType() {
		return accountType;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
}