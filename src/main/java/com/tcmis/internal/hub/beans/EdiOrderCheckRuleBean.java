package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: EdiOrderCheckRuleBean <br>
 * @version: 1.0, Jul 5, 2007 <br>
 *****************************************************************************/


public class EdiOrderCheckRuleBean extends BaseDataBean {

	private String companyId;
	private String ruleId;
	private String transactionType;
	private BigDecimal contactPersonnelId1;
	private BigDecimal contactPersonnelId2;
	private BigDecimal contactPersonnelId3;
	private BigDecimal contactPersonnelId4;
	private BigDecimal contactPersonnelId5;
	private BigDecimal contactPersonnelId6;
	private BigDecimal contactPersonnelId7;
	private BigDecimal contactPersonnelId8;


	//constructor
	public EdiOrderCheckRuleBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public void setContactPersonnelId1(BigDecimal contactPersonnelId1) {
		this.contactPersonnelId1 = contactPersonnelId1;
	}
	public void setContactPersonnelId2(BigDecimal contactPersonnelId2) {
		this.contactPersonnelId2 = contactPersonnelId2;
	}
	public void setContactPersonnelId3(BigDecimal contactPersonnelId3) {
		this.contactPersonnelId3 = contactPersonnelId3;
	}
	public void setContactPersonnelId4(BigDecimal contactPersonnelId4) {
		this.contactPersonnelId4 = contactPersonnelId4;
	}
	public void setContactPersonnelId5(BigDecimal contactPersonnelId5) {
		this.contactPersonnelId5 = contactPersonnelId5;
	}
	public void setContactPersonnelId6(BigDecimal contactPersonnelId6) {
		this.contactPersonnelId6 = contactPersonnelId6;
	}
	public void setContactPersonnelId7(BigDecimal contactPersonnelId7) {
		this.contactPersonnelId7 = contactPersonnelId7;
	}
	public void setContactPersonnelId8(BigDecimal contactPersonnelId8) {
		this.contactPersonnelId8 = contactPersonnelId8;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getRuleId() {
		return ruleId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public BigDecimal getContactPersonnelId1() {
		return contactPersonnelId1;
	}
	public BigDecimal getContactPersonnelId2() {
		return contactPersonnelId2;
	}
	public BigDecimal getContactPersonnelId3() {
		return contactPersonnelId3;
	}
	public BigDecimal getContactPersonnelId4() {
		return contactPersonnelId4;
	}
	public BigDecimal getContactPersonnelId5() {
		return contactPersonnelId5;
	}
	public BigDecimal getContactPersonnelId6() {
		return contactPersonnelId6;
	}
	public BigDecimal getContactPersonnelId7() {
		return contactPersonnelId7;
	}
	public BigDecimal getContactPersonnelId8() {
		return contactPersonnelId8;
	}
}