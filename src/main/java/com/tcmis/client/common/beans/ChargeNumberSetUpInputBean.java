package com.tcmis.client.common.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: charge Number Set Up Input Bean <br>
 * @version: 1.0, March 25, 2011 <br>
 *****************************************************************************/


public class ChargeNumberSetUpInputBean extends BaseDataBean {

	private String companyId;
	private String facilityId;
	private String accountSysId;
	private String chargeType;
	private String searchArgument;
	private String dependent;
	private String chargeNumber1;
	private String chargeNumber2;
	private String chargeNumber3;
	private String chargeNumber4;

	//	constructor
	public ChargeNumberSetUpInputBean() {
	}
	
	public String getChargeNumber1() {
		return chargeNumber1;
	}

	public void setChargeNumber1(String chargeNumber1) {
		this.chargeNumber1 = chargeNumber1;
	}

	public String getChargeNumber2() {
		return chargeNumber2;
	}

	public void setChargeNumber2(String chargeNumber2) {
		this.chargeNumber2 = chargeNumber2;
	}

	public String getChargeNumber3() {
		return chargeNumber3;
	}

	public void setChargeNumber3(String chargeNumber3) {
		this.chargeNumber3 = chargeNumber3;
	}

	public String getChargeNumber4() {
		return chargeNumber4;
	}

	public void setChargeNumber4(String chargeNumber4) {
		this.chargeNumber4 = chargeNumber4;
	}

	public String getAccountSysId() {
		return accountSysId;
	}

	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getSearchArgument() {
		return searchArgument;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public String getDependent() {
		return dependent;
	}

	public void setDependent(String dependent) {
		this.dependent = dependent;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}



}