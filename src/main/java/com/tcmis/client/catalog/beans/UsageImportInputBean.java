package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: UsageImportInputBean <br>
 * @version: 1.0, Feb 2, 2012 <br>
 *****************************************************************************/

public class UsageImportInputBean extends BaseDataBean {

 private String facilityId;
 private BigDecimal createdBy;
 private BigDecimal lastModifiedBy;
 private String searchField;
 private String searchMode;
 private String searchArgument;
 private Date createdFromDate;
 private Date createdToDate;
 
 private String includeTcmisCatalogParts;
 
//constructor
 public UsageImportInputBean() {
		
}

public BigDecimal getCreatedBy() {
	return createdBy;
}

public void setCreatedBy(BigDecimal createdBy) {
	this.createdBy = createdBy;
}

public Date getCreatedFromDate() {
	return createdFromDate;
}

public void setCreatedFromDate(Date createdFromDate) {
	this.createdFromDate = createdFromDate;
}

public Date getCreatedToDate() {
	return createdToDate;
}

public void setCreatedToDate(Date createdToDate) {
	this.createdToDate = createdToDate;
}

public String getFacilityId() {
	return facilityId;
}

public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
}

public BigDecimal getLastModifiedBy() {
	return lastModifiedBy;
}

public void setLastModifiedBy(BigDecimal lastModifiedBy) {
	this.lastModifiedBy = lastModifiedBy;
}

public String getSearchArgument() {
	return searchArgument;
}

public void setSearchArgument(String searchArgument) {
	this.searchArgument = searchArgument;
}

public String getSearchField() {
	return searchField;
}

public void setSearchField(String searchField) {
	this.searchField = searchField;
}

public String getSearchMode() {
	return searchMode;
}

public void setSearchMode(String searchMode) {
	this.searchMode = searchMode;
}

public String getIncludeTcmisCatalogParts() {
	return includeTcmisCatalogParts;
}

public void setIncludeTcmisCatalogParts(String includeTcmisCatalogParts) {
	this.includeTcmisCatalogParts = includeTcmisCatalogParts;
}

 
}