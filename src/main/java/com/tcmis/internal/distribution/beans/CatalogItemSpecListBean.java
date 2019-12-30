package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CatalogItemSpecListBean <br>
 * @version: 1.0, Sep 7, 2009 <br>
 *****************************************************************************/


public class CatalogItemSpecListBean extends BaseDataBean {

	private String catalogCompanyId;
	private String catalogId;
	private BigDecimal itemId;
	private String specId;
	private String specName;
	private String detail;
	private String specLibrary;
	private String specLibraryDesc;
	private String coc;
	private String coa;
	private String specForPart;
	private String specVersion;
	private String specAmendment;
	private String catPartNo;
	private BigDecimal remainingShelfLifePercent;


	//constructor
	public CatalogItemSpecListBean() {
	}

	//setters
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setSpecId(String specId) {
		this.specId = specId;
	}
	public void setSpecLibrary(String specLibrary) {
		this.specLibrary = specLibrary;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public void setCoc(String coc) {
		this.coc = coc;
	}
	public void setCoa(String coa) {
		this.coa = coa;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}	
	public void setRemainingShelfLifePercent(BigDecimal remainingShelfLifePercent) {
		this.remainingShelfLifePercent = remainingShelfLifePercent;
	}	
	
	//getters
	public BigDecimal getRemainingShelfLifePercent() {
		return remainingShelfLifePercent;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getSpecId() {
		return specId;
	}
	public String getSpecLibrary() {
		return specLibrary;
	}
	public String getDetail() {
		return detail;
	}
	public String getCoc() {
		return coc;
	}
	public String getCoa() {
		return coa;
	}

	public String getSpecLibraryDesc() {
		return specLibraryDesc;
	}

	public void setSpecLibraryDesc(String specLibraryDesc) {
		this.specLibraryDesc = specLibraryDesc;
	}

	public String getSpecForPart() {
		return specForPart;
	}

	public void setSpecForPart(String specForPart) {
		this.specForPart = specForPart;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public String getSpecAmendment() {
		return specAmendment;
	}

	public void setSpecAmendment(String specAmendment) {
		this.specAmendment = specAmendment;
	}

	public String getSpecVersion() {
		return specVersion;
	}

	public void setSpecVersion(String specVersion) {
		this.specVersion = specVersion;
	}

}