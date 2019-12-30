package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FacItemLocaleBean <br>
 * @version: 1.0, Jul 23, 2009 <br>
 *****************************************************************************/


public class FacItemLocaleBean extends BaseDataBean {

	private String facilityId;
	private String companyId;
	private String facPartNo;
	private String partDescription;
	private String catalogNotes;
	private String search;
	private String alternateName;
	private String partShortName;
	private String partHazardImage;
	private String searchInNchar;
	private String chemicalCategory;
	private String localeCode;


	//constructor
	public FacItemLocaleBean() {
	}

	//setters
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setCatalogNotes(String catalogNotes) {
		this.catalogNotes = catalogNotes;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
	}
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}
	public void setPartHazardImage(String partHazardImage) {
		this.partHazardImage = partHazardImage;
	}
	public void setSearchInNchar(String searchInNchar) {
		this.searchInNchar = searchInNchar;
	}
	public void setChemicalCategory(String chemicalCategory) {
		this.chemicalCategory = chemicalCategory;
	}
	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}


	//getters
	public String getFacilityId() {
		return facilityId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getFacPartNo() {
		return facPartNo;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public String getCatalogNotes() {
		return catalogNotes;
	}
	public String getSearch() {
		return search;
	}
	public String getAlternateName() {
		return alternateName;
	}
	public String getPartShortName() {
		return partShortName;
	}
	public String getPartHazardImage() {
		return partHazardImage;
	}
	public String getSearchInNchar() {
		return searchInNchar;
	}
	public String getChemicalCategory() {
		return chemicalCategory;
	}
	public String getLocaleCode() {
		return localeCode;
	}
}