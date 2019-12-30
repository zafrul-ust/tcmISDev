package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.util.Collection;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: UsageCategorySubcatViewBean <br>
 * @version: 1.0, Jul 12, 2011 <br>
 *****************************************************************************/


public class UsageCategorySubcatViewBean extends BaseDataBean {

	private BigDecimal usageCategoryId;
	private String usageCategoryName;
	private String companyId;
	private String catalogCompanyId;
	private String catalogId;
	private BigDecimal usageSubcategoryId;
	private String usageSubcategoryName;
	private String production;
	private BigDecimal approvalCodeId;
	private String approvalExpires;
	private String includesSpecialCoatings;
	private String approvalCodeName;
	private String showProgram;
	private BigDecimal programId;
    private Collection usageSubCategoryColl;
    private String applicationUseGroupId;


    //constructor
	public UsageCategorySubcatViewBean() {
	}

	//setters
	public void setUsageCategoryId(BigDecimal usageCategoryId) {
		this.usageCategoryId = usageCategoryId;
	}
	public void setUsageCategoryName(String usageCategoryName) {
		this.usageCategoryName = usageCategoryName;
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
	public void setUsageSubcategoryId(BigDecimal usageSubcategoryId) {
		this.usageSubcategoryId = usageSubcategoryId;
	}
	public void setUsageSubcategoryName(String usageSubcategoryName) {
		this.usageSubcategoryName = usageSubcategoryName;
	}
	public void setProduction(String production) {
		this.production = production;
	}
	public void setApprovalCodeId(BigDecimal approvalCodeId) {
		this.approvalCodeId = approvalCodeId;
	}
	public void setApprovalExpires(String approvalExpires) {
		this.approvalExpires = approvalExpires;
	}
	public void setIncludesSpecialCoatings(String includesSpecialCoatings) {
		this.includesSpecialCoatings = includesSpecialCoatings;
	}
	public void setApprovalCodeName(String approvalCodeName) {
		this.approvalCodeName = approvalCodeName;
	}	
	public void setShowProgram(String showProgram) {
		this.showProgram = showProgram;
	}
	public void setProgramId(BigDecimal programId) {
		this.programId = programId;
	}
	public void setApplicationUseGroupId(String applicationUseGroupId) {
		this.applicationUseGroupId = applicationUseGroupId;
	}
	
	//getters
	public String getApplicationUseGroupId() {
		return applicationUseGroupId;
	}
	public BigDecimal getProgramId() {
		return programId;
	}
	public String getShowProgram() {
		return showProgram;
	}
	public BigDecimal getUsageCategoryId() {
		return usageCategoryId;
	}
	public String getUsageCategoryName() {
		return usageCategoryName;
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
	public BigDecimal getUsageSubcategoryId() {
		return usageSubcategoryId;
	}
	public String getUsageSubcategoryName() {
		return usageSubcategoryName;
	}
	public String getProduction() {
		return production;
	}
	public BigDecimal getApprovalCodeId() {
		return approvalCodeId;
	}
	public String getApprovalExpires() {
		return approvalExpires;
	}
	public String getIncludesSpecialCoatings() {
		return includesSpecialCoatings;
	}
	public String getApprovalCodeName() {
		return approvalCodeName;
	}

    public Collection getUsageSubCategoryColl() {
        return usageSubCategoryColl;
    }

    public void setUsageSubCategoryColl(Collection usageSubCategoryColl) {
        this.usageSubCategoryColl = usageSubCategoryColl;
    }
}
