package com.tcmis.client.common.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ACCOUNT_SYS_HEADERS_VIEW <br>
 * @version: 1.0, March 25, 2011 <br>
 *****************************************************************************/


public class AccountSysHeaderViewBean extends BaseDataBean {

	private String companyId;
	private String accountSysId;
	private String chargeType;
	private String chargeLabel1;
	private String chargeValid1;
	private String chargeLabel2;
	private String chargeValid2;
	private String chargeLabel3;
	private String chargeValid3;
	private String chargeLabel4;
	private String chargeValid4;

	//constructor
	public AccountSysHeaderViewBean() {
	}

	public String getAccountSysId() {
		return accountSysId;
	}

	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}

	public String getChargeLabel1() {
		return chargeLabel1;
	}

	public void setChargeLabel1(String chargeLabel1) {
		this.chargeLabel1 = chargeLabel1;
	}

	public String getChargeLabel2() {
		return chargeLabel2;
	}

	public void setChargeLabel2(String chargeLabel2) {
		this.chargeLabel2 = chargeLabel2;
	}

	public String getChargeLabel3() {
		return chargeLabel3;
	}

	public void setChargeLabel3(String chargeLabel3) {
		this.chargeLabel3 = chargeLabel3;
	}

	public String getChargeLabel4() {
		return chargeLabel4;
	}

	public void setChargeLabel4(String chargeLabel4) {
		this.chargeLabel4 = chargeLabel4;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getChargeValid1() {
		return chargeValid1;
	}

	public void setChargeValid1(String chargeValid1) {
		this.chargeValid1 = chargeValid1;
	}

	public String getChargeValid2() {
		return chargeValid2;
	}

	public void setChargeValid2(String chargeValid2) {
		this.chargeValid2 = chargeValid2;
	}

	public String getChargeValid3() {
		return chargeValid3;
	}

	public void setChargeValid3(String chargeValid3) {
		this.chargeValid3 = chargeValid3;
	}

	public String getChargeValid4() {
		return chargeValid4;
	}

	public void setChargeValid4(String chargeValid4) {
		this.chargeValid4 = chargeValid4;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	


}