package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Collection;

/******************************************************************************
 * CLASSNAME: MaterialCategoryBean <br>
 * @version: 1.0, Jul 11, 2011 <br>
 *****************************************************************************/

public class MaterialCategoryBean extends BaseDataBean {

    private BigDecimal materialCategoryId;
    private String materialCategoryName;
    private String companyId;
    private String catalogCompanyId;
    private String catalogId;
    private String catalogDesc;
    private Collection materialSubCategoryColl;


    //constructor
    public MaterialCategoryBean() {
    }

    public BigDecimal getMaterialCategoryId() {
        return materialCategoryId;
    }

    public void setMaterialCategoryId(BigDecimal materialCategoryId) {
        this.materialCategoryId = materialCategoryId;
    }

    public String getMaterialCategoryName() {
        return materialCategoryName;
    }

    public void setMaterialCategoryName(String materialCategoryName) {
        this.materialCategoryName = materialCategoryName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public Collection getMaterialSubCategoryColl() {
        return materialSubCategoryColl;
    }

    public void setMaterialSubCategoryColl(Collection materialSubCategoryColl) {
        this.materialSubCategoryColl = materialSubCategoryColl;
    }
    public String getCatalogDesc() {
        return catalogDesc;
    }

    public void setCatalogDesc(String catalogDesc) {
        this.catalogDesc = catalogDesc;
    }
    
}