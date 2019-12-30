package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Collection;

/******************************************************************************
 * CLASSNAME: MaterialSubCategoryBean <br>
 * @version: 1.0, Jul 11, 2011 <br>
 *****************************************************************************/

public class MaterialSubCategoryBean extends BaseDataBean {

    private BigDecimal materialCategoryId;
    private BigDecimal materialSubcategoryId;
    private String materialSubcategoryDesc;
    private String companyId;
    private String catalgoCompanyId;
    private String catalogId;
    private String coatCategory;
    private String showForProd;
    private String showForNonProd;
    private String toVocet;
    private String specialTyCoating;
    private String hideQtyPerShift;
    private String qtyPerShiftOptional;
    private String hideAnnualUsage;
    private String annualUsageOptional;
    private String hideMultiComponent;
    private String hideProcessInfo;
    private String hideFtwSpecific;


    //constructor
    public MaterialSubCategoryBean() {
    }

    public BigDecimal getMaterialCategoryId() {
        return materialCategoryId;
    }

    public void setMaterialCategoryId(BigDecimal materialCategoryId) {
        this.materialCategoryId = materialCategoryId;
    }

    public BigDecimal getMaterialSubcategoryId() {
        return materialSubcategoryId;
    }

    public void setMaterialSubcategoryId(BigDecimal materialSubcategoryId) {
        this.materialSubcategoryId = materialSubcategoryId;
    }

    public String getMaterialSubcategoryDesc() {
        return materialSubcategoryDesc;
    }

    public void setMaterialSubcategoryDesc(String materialSubcategoryDesc) {
        this.materialSubcategoryDesc = materialSubcategoryDesc;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCatalgoCompanyId() {
        return catalgoCompanyId;
    }

    public void setCatalgoCompanyId(String catalgoCompanyId) {
        this.catalgoCompanyId = catalgoCompanyId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCoatCategory() {
        return coatCategory;
    }

    public void setCoatCategory(String coatCategory) {
        this.coatCategory = coatCategory;
    }

    public String getShowForProd() {
        return showForProd;
    }

    public void setShowForProd(String showForProd) {
        this.showForProd = showForProd;
    }

    public String getShowForNonProd() {
        return showForNonProd;
    }

    public void setShowForNonProd(String showForNonProd) {
        this.showForNonProd = showForNonProd;
    }

    public String getToVocet() {
        return toVocet;
    }

    public void setToVocet(String toVocet) {
        this.toVocet = toVocet;
    }

    public String getSpecialTyCoating() {
        return specialTyCoating;
    }

    public void setSpecialTyCoating(String specialTyCoating) {
        this.specialTyCoating = specialTyCoating;
    }

    public String getHideQtyPerShift() {
        return hideQtyPerShift;
    }

    public void setHideQtyPerShift(String hideQtyPerShift) {
        this.hideQtyPerShift = hideQtyPerShift;
    }

    public String getQtyPerShiftOptional() {
        return qtyPerShiftOptional;
    }

    public void setQtyPerShiftOptional(String qtyPerShiftOptional) {
        this.qtyPerShiftOptional = qtyPerShiftOptional;
    }

    public String getHideAnnualUsage() {
        return hideAnnualUsage;
    }

    public void setHideAnnualUsage(String hideAnnualUsage) {
        this.hideAnnualUsage = hideAnnualUsage;
    }

    public String getAnnualUsageOptional() {
        return annualUsageOptional;
    }

    public void setAnnualUsageOptional(String annualUsageOptional) {
        this.annualUsageOptional = annualUsageOptional;
    }

    public String getHideMultiComponent() {
        return hideMultiComponent;
    }

    public void setHideMultiComponent(String hideMultiComponent) {
        this.hideMultiComponent = hideMultiComponent;
    }

    public String getHideProcessInfo() {
        return hideProcessInfo;
    }

    public void setHideProcessInfo(String hideProcessInfo) {
        this.hideProcessInfo = hideProcessInfo;
    }

    public String getHideFtwSpecific() {
        return hideFtwSpecific;
    }

    public void setHideFtwSpecific(String hideFtwSpecific) {
        this.hideFtwSpecific = hideFtwSpecific;
    }
}