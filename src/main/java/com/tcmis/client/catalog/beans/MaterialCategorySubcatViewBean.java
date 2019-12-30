package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.util.Collection;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: MaterialCategorySubcatViewBean <br>
 * @version: 1.0, Jul 12, 2011 <br>
 *****************************************************************************/


public class MaterialCategorySubcatViewBean extends BaseDataBean {

	private BigDecimal materialCategoryId;
	private String materialCategoryName;
	private String companyId;
	private String catalogCompanyId;
	private String catalogId;
	private BigDecimal materialSubcategoryId;
	private String materialSubcategoryName;
	private String materialSubcategoryDesc;
	private String coatCategory;
	private String showForProd;
	private String showForNonProd;
	private String toVocet;
	private String specialtyCoating;
	private String hideQtyPerShift;
	private String qtyPerShiftOptional;
	private String hideAnnualUsage;
	private String annualUsageOptional;
	private String hideMultiComponent;
	private String hideProcessInfo;
	private String hideFtwSpecific;
    private Collection materialSubCategoryColl;


    //constructor
	public MaterialCategorySubcatViewBean() {
	}

	//setters
	public void setMaterialCategoryId(BigDecimal materialCategoryId) {
		this.materialCategoryId = materialCategoryId;
	}
	public void setMaterialCategoryName(String materialCategoryName) {
		this.materialCategoryName = materialCategoryName;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setMaterialSubcategoryId(BigDecimal materialSubcategoryId) {
		this.materialSubcategoryId = materialSubcategoryId;
	}
	public void setMaterialSubcategoryName(String materialSubcategoryName) {
		this.materialSubcategoryName = materialSubcategoryName;
	}
	public void setMaterialSubcategoryDesc(String materialSubcategoryDesc) {
		this.materialSubcategoryDesc = materialSubcategoryDesc;
	}
	public void setCoatCategory(String coatCategory) {
		this.coatCategory = coatCategory;
	}
	public void setShowForProd(String showForProd) {
		this.showForProd = showForProd;
	}
	public void setShowForNonProd(String showForNonProd) {
		this.showForNonProd = showForNonProd;
	}
	public void setToVocet(String toVocet) {
		this.toVocet = toVocet;
	}
	public void setSpecialtyCoating(String specialtyCoating) {
		this.specialtyCoating = specialtyCoating;
	}
	public void setHideQtyPerShift(String hideQtyPerShift) {
		this.hideQtyPerShift = hideQtyPerShift;
	}
	public void setQtyPerShiftOptional(String qtyPerShiftOptional) {
		this.qtyPerShiftOptional = qtyPerShiftOptional;
	}
	public void setHideAnnualUsage(String hideAnnualUsage) {
		this.hideAnnualUsage = hideAnnualUsage;
	}
	public void setAnnualUsageOptional(String annualUsageOptional) {
		this.annualUsageOptional = annualUsageOptional;
	}
	public void setHideMultiComponent(String hideMultiComponent) {
		this.hideMultiComponent = hideMultiComponent;
	}
	public void setHideProcessInfo(String hideProcessInfo) {
		this.hideProcessInfo = hideProcessInfo;
	}
	public void setHideFtwSpecific(String hideFtwSpecific) {
		this.hideFtwSpecific = hideFtwSpecific;
	}


	//getters
	public BigDecimal getMaterialCategoryId() {
		return materialCategoryId;
	}
	public String getMaterialCategoryName() {
		return materialCategoryName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public BigDecimal getMaterialSubcategoryId() {
		return materialSubcategoryId;
	}
	public String getMaterialSubcategoryName() {
		return materialSubcategoryName;
	}
	public String getMaterialSubcategoryDesc() {
		return materialSubcategoryDesc;
	}
	public String getCoatCategory() {
		return coatCategory;
	}
	public String getShowForProd() {
		return showForProd;
	}
	public String getShowForNonProd() {
		return showForNonProd;
	}
	public String getToVocet() {
		return toVocet;
	}
	public String getSpecialtyCoating() {
		return specialtyCoating;
	}
	public String getHideQtyPerShift() {
		return hideQtyPerShift;
	}
	public String getQtyPerShiftOptional() {
		return qtyPerShiftOptional;
	}
	public String getHideAnnualUsage() {
		return hideAnnualUsage;
	}
	public String getAnnualUsageOptional() {
		return annualUsageOptional;
	}
	public String getHideMultiComponent() {
		return hideMultiComponent;
	}
	public String getHideProcessInfo() {
		return hideProcessInfo;
	}
	public String getHideFtwSpecific() {
		return hideFtwSpecific;
	}

    public Collection getMaterialSubCategoryColl() {
        return materialSubCategoryColl;
    }

    public void setMaterialSubCategoryColl(Collection materialSubCategoryColl) {
        this.materialSubCategoryColl = materialSubCategoryColl;
    }
}
