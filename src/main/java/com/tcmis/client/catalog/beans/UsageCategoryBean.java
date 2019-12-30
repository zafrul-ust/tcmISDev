package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Collection;

/******************************************************************************
 * CLASSNAME: UsageCategoryBean <br>
 * @version: 1.0, Jul 11, 2011 <br>
 *****************************************************************************/

public class UsageCategoryBean extends BaseDataBean {

    private BigDecimal usageCategoryId;
    private String usageCategoryName;
    private String companyId;
    private String catalgoCompanyId;
    private String catalogId;
    private Collection usageSubCategoryColl;


    //constructor
    public UsageCategoryBean() {
    }

    public BigDecimal getUsageCategoryId() {
        return usageCategoryId;
    }

    public void setUsageCategoryId(BigDecimal usageCategoryId) {
        this.usageCategoryId = usageCategoryId;
    }

    public String getUsageCategoryName() {
        return usageCategoryName;
    }

    public void setUsageCategoryName(String usageCategoryName) {
        this.usageCategoryName = usageCategoryName;
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

    public Collection getUsageSubCategoryColl() {
        return usageSubCategoryColl;
    }

    public void setUsageSubCategoryColl(Collection usageSubCategoryColl) {
        this.usageSubCategoryColl = usageSubCategoryColl;
    }
}