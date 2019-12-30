package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: HubInventoryOwnerBean <br>
 * @version: 1.0, Sep 7, 2005 <br>
 *****************************************************************************/


public class HubInventoryOwnerBean extends BaseDataBean {

	private String hub;
	private String companyId;
	private String accountSysId;
	private String chargeType;
	private String accountNumber;
	private String accountNumber2;
	private BigDecimal blanketMr;
	private String catalogId;
	private BigDecimal partGroupNo;
	private String facilityId;
	private String poNumber;
  private String status;

	//constructor
	public HubInventoryOwnerBean() {
	}

	//setters
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public void setAccountNumber2(String accountNumber2) {
		this.accountNumber2 = accountNumber2;
	}
	public void setBlanketMr(BigDecimal blanketMr) {
		this.blanketMr = blanketMr;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setStatus(String status) {
	 this.status = status;
	}

	//getters
	public String getHub() {
		return hub;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getAccountSysId() {
		return accountSysId;
	}
	public String getChargeType() {
		return chargeType;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public String getAccountNumber2() {
		return accountNumber2;
	}
	public BigDecimal getBlanketMr() {
		return blanketMr;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public String getStatus() {
		return status;
	}
}