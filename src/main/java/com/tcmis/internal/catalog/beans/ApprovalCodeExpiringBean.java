package com.tcmis.internal.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import org.apache.commons.lang.StringUtils;


/******************************************************************************
 * CLASSNAME: ApprovalCodeExpiringBean <br>
 * @version: 1.0, Jan 15, 2008 <br>
 *****************************************************************************/


public class ApprovalCodeExpiringBean extends BaseDataBean {
	private BigDecimal approvedId;
	private String companyId;
	private String facilityId;
	private String catalogCompanyId;
	private String catalogId;
	private String catPartNo;
	private BigDecimal partGroupNo;
	private Date startDate;
	private Date endDate;
	private String applicationUseGroupId;
	private String usageSubcategoryName;
	private String application;
	private String cabinetPartInventoryStatus;
	private String emailAddress;
	private String facilityName;
	private String applicationDesc;


    //constructor
	public ApprovalCodeExpiringBean() {
	}

	public BigDecimal getApprovedId() {
		return approvedId;
	}

	public void setApprovedId(BigDecimal approvedId) {
		this.approvedId = approvedId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
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

	public String getCatPartNo() {
		return catPartNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getApplicationUseGroupId() {
		return applicationUseGroupId;
	}

	public void setApplicationUseGroupId(String applicationUseGroupId) {
		this.applicationUseGroupId = applicationUseGroupId;
	}

	public String getUsageSubcategoryName() {
		return usageSubcategoryName;
	}

	public void setUsageSubcategoryName(String usageSubcategoryName) {
		this.usageSubcategoryName = usageSubcategoryName;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getCabinetPartInventoryStatus() {
		return cabinetPartInventoryStatus;
	}

	public void setCabinetPartInventoryStatus(String cabinetPartInventoryStatus) {
		this.cabinetPartInventoryStatus = cabinetPartInventoryStatus;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public boolean isCabinetPartInventoryStatusActive() {
		return "A".equals(cabinetPartInventoryStatus);
	}

	public boolean hasApplication() {
		return StringUtils.isNotBlank(application);
	}

} //end of class