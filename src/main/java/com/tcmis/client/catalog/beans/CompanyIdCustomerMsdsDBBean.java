package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CompanyIdCustomerMsdsBean <br>
 * @version: 1.0, Dec 12, 2011 <br>
 *****************************************************************************/

public class CompanyIdCustomerMsdsDBBean
 extends BaseDataBean {

 private String companyId;
 private String facilityId;
 private String customerMsdsDb;
 private String catalogId;
 private String catalogDesc;
 
public String getCatalogDesc() {
	return catalogDesc;
}

public void setCatalogDesc(String catalogDesc) {
	this.catalogDesc = catalogDesc;
}

public String getCatalogId() {
	return catalogId;
}

public void setCatalogId(String catalogId) {
	this.catalogId = catalogId;
}

public String getCompanyId() {
	return companyId;
}

public void setCompanyId(String companyId) {
	this.companyId = companyId;
}

public String getCustomerMsdsDb() {
	return customerMsdsDb;
}

public void setCustomerMsdsDb(String customerMsdsDb) {
	this.customerMsdsDb = customerMsdsDb;
}

public String getFacilityId() {
	return facilityId;
}

public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
}

//constructor
public CompanyIdCustomerMsdsDBBean() {
}

 
}