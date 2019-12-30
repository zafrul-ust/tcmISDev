package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: UseApprovalDetailViewBean <br>
 * @version: 1.0, Jul 19, 2005 <br>
 *****************************************************************************/

public class UseApprovalDetailViewBean
 extends BaseDataBean {

 private String companyId;
 private String facPartNo;
 private String catalogId;
 private BigDecimal partGroupNo;
 private String userGroupId;
 private String userGroupMembers;
 private String facilityId;
 private String facilityDesc;
 private String application;
 private String applicationDesc;
 private String applicationApproved;
 private String limit1;
 private String limit2;
 private String approvalStatus;
 private String applicationDescDisplay;
 private String catalogCompanyId;
 private String applicationUseGroupName;
 private boolean allCatalog;
 private Date startDate;
 private Date expireDate;

 //constructor
 public UseApprovalDetailViewBean() {
 }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getFacPartNo() {
        return facPartNo;
    }

    public void setFacPartNo(String facPartNo) {
        this.facPartNo = facPartNo;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public BigDecimal getPartGroupNo() {
        return partGroupNo;
    }

    public void setPartGroupNo(BigDecimal partGroupNo) {
        this.partGroupNo = partGroupNo;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserGroupMembers() {
        return userGroupMembers;
    }

    public void setUserGroupMembers(String userGroupMembers) {
        this.userGroupMembers = userGroupMembers;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityDesc() {
        return facilityDesc;
    }

    public void setFacilityDesc(String facilityDesc) {
        this.facilityDesc = facilityDesc;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getApplicationDesc() {
        return applicationDesc;
    }

    public void setApplicationDesc(String applicationDesc) {
        this.applicationDesc = applicationDesc;
    }

    public String getApplicationApproved() {
        return applicationApproved;
    }

    public void setApplicationApproved(String applicationApproved) {
        this.applicationApproved = applicationApproved;
    }

    public String getLimit1() {
        return limit1;
    }

    public void setLimit1(String limit1) {
        this.limit1 = limit1;
    }

    public String getLimit2() {
        return limit2;
    }

    public void setLimit2(String limit2) {
        this.limit2 = limit2;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApplicationDescDisplay() {
        return applicationDescDisplay;
    }

    public void setApplicationDescDisplay(String applicationDescDisplay) {
        this.applicationDescDisplay = applicationDescDisplay;
    }

    public String getCatalogCompanyId() {
        return catalogCompanyId;
    }

    public void setCatalogCompanyId(String catalogCompanyId) {
        this.catalogCompanyId = catalogCompanyId;
    }

    public String getApplicationUseGroupName() {
        return applicationUseGroupName;
    }

    public void setApplicationUseGroupName(String applicationUseGroupName) {
        this.applicationUseGroupName = applicationUseGroupName;
    }

    public boolean isAllCatalog() {
        return allCatalog;
    }

    public void setAllCatalog(boolean allCatalog) {
        this.allCatalog = allCatalog;
    }

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
}