package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Collection;

/******************************************************************************
 * CLASSNAME: UsageSubCategoryBean <br>
 * @version: 1.0, Jul 11, 2011 <br>
 *****************************************************************************/

public class UsageSubCategoryBean extends BaseDataBean {

    private BigDecimal usageSubcategoryId;
    private String usageSubcategoryName;
    private BigDecimal usageCategoryId;
    private String companyId;
    private String catalgoCompanyId;
    private String catalogId;
    private BigDecimal approvalCodeId;
    private String production;
    private String approvalExpires;
    private String includesSpecialCoatings;


    //constructor
    public UsageSubCategoryBean() {
    }

    public BigDecimal getUsageSubcategoryId() {
        return usageSubcategoryId;
    }

    public void setUsageSubcategoryId(BigDecimal usageSubcategoryId) {
        this.usageSubcategoryId = usageSubcategoryId;
    }

    public String getUsageSubcategoryName() {
        return usageSubcategoryName;
    }

    public void setUsageSubcategoryName(String usageSubcategoryName) {
        this.usageSubcategoryName = usageSubcategoryName;
    }

    public BigDecimal getUsageCategoryId() {
        return usageCategoryId;
    }

    public void setUsageCategoryId(BigDecimal usageCategoryId) {
        this.usageCategoryId = usageCategoryId;
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

    public BigDecimal getApprovalCodeId() {
        return approvalCodeId;
    }

    public void setApprovalCodeId(BigDecimal approvalCodeId) {
        this.approvalCodeId = approvalCodeId;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getApprovalExpires() {
        return approvalExpires;
    }

    public void setApprovalExpires(String approvalExpires) {
        this.approvalExpires = approvalExpires;
    }

    public String getIncludesSpecialCoatings() {
        return includesSpecialCoatings;
    }

    public void setIncludesSpecialCoatings(String includesSpecialCoatings) {
        this.includesSpecialCoatings = includesSpecialCoatings;
    }
}