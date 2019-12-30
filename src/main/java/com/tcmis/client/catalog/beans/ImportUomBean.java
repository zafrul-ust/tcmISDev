package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ImportUomBean <br>
 * @version: 1.0, Feb 2, 2012 <br>
 *****************************************************************************/

public class ImportUomBean
 extends BaseDataBean {

 private String companyId;
 private String facilityId;
 private String unitOfMeasure;

 //constructor
 public ImportUomBean() {
 }

public String getCompanyId() {
	return companyId;
}

public void setCompanyId(String companyId) {
	this.companyId = companyId;
}

public String getFacilityId() {
	return facilityId;
}

public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
}

public String getUnitOfMeasure() {
	return unitOfMeasure;
}

public void setUnitOfMeasure(String unitOfMeasure) {
	this.unitOfMeasure = unitOfMeasure;
}

 
}