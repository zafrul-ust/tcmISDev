package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CustomerMaterialNumberBean <br>
 * @version: 1.0, Dec 12, 2011 <br>
 *****************************************************************************/

public class CustomerMaterialNumberBean
 extends BaseDataBean {

 private String companyId;
 private String customerMsdsDb;
 private String customerMsdsOrMixtureNo;
 private String materialOrMixture;
 private BigDecimal materialId;
 private String materialDesc;
 private String tradeName;
 private String customerMsdsNumber;


//constructor
public CustomerMaterialNumberBean() {
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


public String getCustomerMsdsOrMixtureNo() {
	return customerMsdsOrMixtureNo;
}


public void setCustomerMsdsOrMixtureNo(String customerMsdsOrMixtureNo) {
	this.customerMsdsOrMixtureNo = customerMsdsOrMixtureNo;
}


public String getMaterialOrMixture() {
	return materialOrMixture;
}


public void setMaterialOrMixture(String materialOrMixture) {
	this.materialOrMixture = materialOrMixture;
}


public BigDecimal getMaterialId() {
	return materialId;
}


public void setMaterialId(BigDecimal materialId) {
	this.materialId = materialId;
}


public String getTradeName() {
	return tradeName;
}


public void setTradeName(String tradeName) {
	this.tradeName = tradeName;
}

public String getMaterialDesc() {
	return materialDesc;
}


public void setMaterialDesc(String materialDesc) {
	this.materialDesc = materialDesc;
}

public String getCustomerMsdsNumber() {
	return customerMsdsNumber;
}

public void setCustomerMsdsNumber(String customerMsdsNumber) {
	this.customerMsdsNumber = customerMsdsNumber;
}

 
}