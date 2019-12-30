package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: WorkareaMovementPermViewBean<br>
 * @version: 1.0, Apr 24, 2011 <br>
 *****************************************************************************/

public class WorkareaMovementPermViewBean
    extends BaseDataBean {

  private String companyId;
  private String catalogCompanyId;
  private String facilityId;
  private String catalogId;
  private BigDecimal personnelId;
  private String facPartNo;
  private BigDecimal partGroupNo;
  private String application;
  private String applicationDesc;

  //constructor
  public WorkareaMovementPermViewBean() {
  }

public String getApplication() {
	return application;
}

public void setApplication(String application) {
	this.application = application;
}

public String getApplicationDesc() {
	return applicationDesc;
}

public void setApplicationDesc(String applicationDesc) {
	this.applicationDesc = applicationDesc;
}

public String getCatalogCompanyId() {
	return catalogCompanyId;
}

public void setCatalogCompanyId(String catalogCompanyId) {
	this.catalogCompanyId = catalogCompanyId;
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

public String getFacilityId() {
	return facilityId;
}

public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
}

public String getFacPartNo() {
	return facPartNo;
}

public void setFacPartNo(String facPartNo) {
	this.facPartNo = facPartNo;
}

public BigDecimal getPartGroupNo() {
	return partGroupNo;
}

public void setPartGroupNo(BigDecimal partGroupNo) {
	this.partGroupNo = partGroupNo;
}

public BigDecimal getPersonnelId() {
	return personnelId;
}

public void setPersonnelId(BigDecimal personnelId) {
	this.personnelId = personnelId;
}


 
}