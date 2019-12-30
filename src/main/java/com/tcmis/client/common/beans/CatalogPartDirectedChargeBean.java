package com.tcmis.client.common.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CatalogPartDirectedChargeBean <br>
 * @version: 1.0, May 7, 2009 <br>
 *****************************************************************************/


public class CatalogPartDirectedChargeBean extends BaseDataBean {

	private String companyId;
	private String catalogId;
	private String catPartNo;
	private String chargeNumber;
	private String facilityId;
	private String application;
	private String chargeNumber2;
	private String poNumber;
	private String catalogCompanyId;

	//constructor
	public CatalogPartDirectedChargeBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setChargeNumber(String chargeNumber) {
		this.chargeNumber = chargeNumber;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setChargeNumber2(String chargeNumber2) {
		this.chargeNumber2 = chargeNumber2;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getChargeNumber() {
		return chargeNumber;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getApplication() {
		return application;
	}
	public String getChargeNumber2() {
		return chargeNumber2;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
}