package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: VocetFugitiveCategoryBean <br>
 * @version: 1.0, Aug 3, 2012 <br>
 *****************************************************************************/

public class VocetFugitiveCategoryBean
 extends BaseDataBean {

 private String companyId;
 private String facilityId;
 private String vocetFugitiveCatId;
 private String vocetFugitiveCatName;

 //constructor
 public VocetFugitiveCategoryBean() {
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

public String getVocetFugitiveCatId() {
	return vocetFugitiveCatId;
}

public void setVocetFugitiveCatId(String vocetFugitiveCatId) {
	this.vocetFugitiveCatId = vocetFugitiveCatId;
}

public String getVocetFugitiveCatName() {
	return vocetFugitiveCatName;
}

public void setVocetFugitiveCatName(String vocetFugitiveCatName) {
	this.vocetFugitiveCatName = vocetFugitiveCatName;
}


 
}