package com.tcmis.client.catalog.beans;

import java.util.Collection;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CatalogAddUserGroupBean <br>
 * @version: 1.0, Oct 30, 2007 <br>
 *****************************************************************************/


public class CatalogAddUserGroupBean extends BaseDataBean {

	private String companyId;
	private BigDecimal requestId;
	private String facilityId;
	private String workArea;
	private String userGroupId;
	private String processDesc;
	private BigDecimal quantity11;
	private BigDecimal per11;
	private String period11;
	private BigDecimal quantity22;
	private BigDecimal per22;
	private String period22;
	private String chargeNumber;
	private String applicationDesc;
	private String userGroupDesc;
	private BigDecimal estimatedAnnualUsage;
	private String approvalStatus;
	private String approvalRequestStatus;
	private String inventoryGroup;

	private Collection workAreaColl;
	private Collection userGroupColl;
	private String dataSource;
	private Collection inventoryGroupColl;
    private String hasApplicationUseGroup;
    private String applicationUseGroupId;
    private String applicationUseGroupName;
    private String applicationUseGroupDesc;
    private String specificUseApprovalRequired;
    private String wasteWaterDischarge;
    private String emissionPoints;
    private String emissionPointIdSeparator;
    private Collection emissionPointColl;
    private String emissionPointDescs;

    //constructor
	public CatalogAddUserGroupBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setWorkArea(String workArea) {
		this.workArea = workArea;
	}
	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}
	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}
	public void setQuantity1(BigDecimal quantity11) {
		this.quantity11 = quantity11;
	}
	public void setPer1(BigDecimal per11) {
		this.per11 = per11;
	}
	public void setPeriod1(String period11) {
		this.period11 = period11;
	}
	public void setQuantity2(BigDecimal quantity22) {
		this.quantity22 = quantity22;
	}
	public void setPer2(BigDecimal per22) {
		this.per22 = per22;
	}
	public void setPeriod2(String period22) {
		this.period22 = period22;
	}
	public void setChargeNumber(String chargeNumber) {
		this.chargeNumber = chargeNumber;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public void setUserGroupDesc(String userGroupDesc) {
		this.userGroupDesc = userGroupDesc;
	}

	public void setEstimatedAnnualUsage(BigDecimal estimatedAnnualUsage) {
		this.estimatedAnnualUsage = estimatedAnnualUsage;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public void setApprovalRequestStatus(String approvalRequestStatus) {
		this.approvalRequestStatus = approvalRequestStatus;
	}

	public void setUserGroupColl(Collection userGroupColl) {
		this.userGroupColl = userGroupColl;
	}

	public void setWorkAreaColl(Collection workAreaColl) {
		this.workAreaColl = workAreaColl;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	//getters
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getRequestId() {
		return requestId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getWorkArea() {
		return workArea;
	}
	public String getUserGroupId() {
		return userGroupId;
	}
	public String getProcessDesc() {
		return processDesc;
	}
	public BigDecimal getQuantity1() {
		return quantity11;
	}
	public BigDecimal getPer1() {
		return per11;
	}
	public String getPeriod1() {
		return period11;
	}
	public BigDecimal getQuantity2() {
		return quantity22;
	}
	public BigDecimal getPer2() {
		return per22;
	}
	public String getPeriod2() {
		return period22;
	}
	public String getChargeNumber() {
		return chargeNumber;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public String getUserGroupDesc() {
		return userGroupDesc;
	}

	public BigDecimal getEstimatedAnnualUsage() {
		return estimatedAnnualUsage;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public String getApprovalRequestStatus() {
		return approvalRequestStatus;
	}

	public Collection getWorkAreaColl() {
		return workAreaColl;
	}

	public Collection getUserGroupColl() {
		return userGroupColl;
	}

	public String getDataSource() {
		return dataSource;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public Collection getInventoryGroupColl() {
		return inventoryGroupColl;
	}

	public void setInventoryGroupColl(Collection inventoryGroupColl) {
		this.inventoryGroupColl = inventoryGroupColl;
	}

    public String getHasApplicationUseGroup() {
        return hasApplicationUseGroup;
    }

    public void setHasApplicationUseGroup(String hasApplicationUseGroup) {
        this.hasApplicationUseGroup = hasApplicationUseGroup;
    }

    public String getApplicationUseGroupId() {
        return applicationUseGroupId;
    }

    public void setApplicationUseGroupId(String applicationUseGroupId) {
        this.applicationUseGroupId = applicationUseGroupId;
    }

    public String getApplicationUseGroupName() {
        return applicationUseGroupName;
    }

    public void setApplicationUseGroupName(String applicationUseGroupName) {
        this.applicationUseGroupName = applicationUseGroupName;
    }

    public String getApplicationUseGroupDesc() {
        return applicationUseGroupDesc;
    }

    public void setApplicationUseGroupDesc(String applicationUseGroupDesc) {
        this.applicationUseGroupDesc = applicationUseGroupDesc;
    }

    public String getSpecificUseApprovalRequired() {
        return specificUseApprovalRequired;
    }

    public void setSpecificUseApprovalRequired(String specificUseApprovalRequired) {
        this.specificUseApprovalRequired = specificUseApprovalRequired;
    }

	public String getWasteWaterDischarge() {
		return wasteWaterDischarge;
	}

	public void setWasteWaterDischarge(String wasteWaterDischarge) {
		this.wasteWaterDischarge = wasteWaterDischarge;
	}

	public String getEmissionPoints() {
		return emissionPoints;
	}

	public void setEmissionPoints(String emissionPoints) {
		this.emissionPoints = emissionPoints;
	}

	public String getEmissionPointIdSeparator() {
		return emissionPointIdSeparator;
	}

	public void setEmissionPointIdSeparator(String emissionPointIdSeparator) {
		this.emissionPointIdSeparator = emissionPointIdSeparator;
	}

	public Collection getEmissionPointColl() {
		return emissionPointColl;
	}

	public void setEmissionPointColl(Collection emissionPointColl) {
		this.emissionPointColl = emissionPointColl;
	}

	public String getEmissionPointDescs() {
		return emissionPointDescs;
	}

	public void setEmissionPointDescs(String emissionPointDescs) {
		this.emissionPointDescs = emissionPointDescs;
	}
}