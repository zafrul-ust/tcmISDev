package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: NewChemHeaderViewBean <br>
 * @version: 1.0, Jan 14, 2008 <br>
 *****************************************************************************/


public class NewChemHeaderViewBean extends BaseDataBean {

	private String catalogId;
	private String catPartNo;
	private String catalogCompanyId;
	private BigDecimal listItemId;
	private String partShortName;
	private String catalogDesc;
	private String compFac;
	private String partDescription;
	private String companyId;
	private String facilityId;
	private String newChemPackagingChange;


	//constructor
	public NewChemHeaderViewBean() {
	}

	//setters
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}
	public void setListItemId(BigDecimal listItemId) {
		this.listItemId = listItemId;
	}
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}
	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}
	public void setCompFac(String compFac) {
		this.compFac = compFac;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setNewChemPackagingChange(String newChemPackagingChange) {
		this.newChemPackagingChange = newChemPackagingChange;
	}


	//getters
	public String getCatalogId() {
		return catalogId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
	public BigDecimal getListItemId() {
		return listItemId;
	}
	public String getPartShortName() {
		return partShortName;
	}
	public String getCatalogDesc() {
		return catalogDesc;
	}
	public String getCompFac() {
		return compFac;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getNewChemPackagingChange() {
		return newChemPackagingChange;
	}
}