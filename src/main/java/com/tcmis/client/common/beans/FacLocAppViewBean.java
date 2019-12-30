package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: FacLocAppViewBean <br>
 * @version: 1.0, Feb 22, 2012 <br>
 *****************************************************************************/

public class FacLocAppViewBean extends BaseDataBean {

	private String allocateByDistance;
	private String allowSplitKits;
	private String allowStocking;
	private String application;
	private String applicationDesc;
	private BigDecimal applicationId;
	private String areaDesc;
	private BigDecimal areaId;
	private String areaName;
	private String buildingDesc;
	private BigDecimal buildingId;
	private String buildingName;
	private String chargeTypeDefault;
	private String companyApplication;
	private String companyId;
	private String compassPoint;
	private String contact2Email;
	private String contact2Name;
	private String contact2Phone;
	private String contactEmail;
	private String contactName;
	private String contactPhone;
	private String customerCabinetId;
	private BigDecimal daysBetweenScan;
	private String deliveryPoint;
	private String deliveryPointDesc;
	private String deliveryPointSelectionRule;
	private String deptId;
	private String deptName;
	private String directedChargeAllowNull;
	private String dockDeliverToSelectionRule;
	private String dockSelectionRule;
	private String dropShip;
	private String editChargeNumber;
	private String emissionPoint;
	private String facilityId;
	private String floor;
	private BigDecimal floorId;
	private boolean includeExpiredMaterial;
	private String interior;
	private String inventoryGroup;
	private String locationDetail;
	private String locationId;
	private String locColumn;
	private String locSection;
	private String managedUseApproval;
	private String manualMrCreation;
	private String modified;
	private String offsite;
	private String organization;
	private String overrideUsageLogging;
	private String processId;
	private BigDecimal pullWithinDaysToExpiration;
	private String reportingEntityId;
	private boolean reportUsage = false;
	private String roomDesc;
	private BigDecimal roomId;
	private String roomName;
	private String status;
	private String stockingAccountSysId;
	private String updatedByName;
	private Date updatedOn;
	private String useApprovalLimitsOption;
	private String useCodeData;
	private String useCodeIdString;
	private String useCodeName;
	private String useCodeRequired;
	private String workAreaGroupDesc;

	//constructor
	public FacLocAppViewBean() {
	}

	public String getAllocateByDistance() {
		return allocateByDistance;
	}

	public String getAllowSplitKits() {
		return allowSplitKits;
	}

	public String getAllowStocking() {
		return allowStocking;
	}

	public String getApplication() {
		return application;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public BigDecimal getApplicationId() {
		return applicationId;
	}

	public String getAreaDesc() {
		return areaDesc;
	}

	public BigDecimal getAreaId() {
		return areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public String getBuildingDesc() {
		return buildingDesc;
	}

	public BigDecimal getBuildingId() {
		return buildingId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public String getChargeTypeDefault() {
		return chargeTypeDefault;
	}

	public String getCompanyApplication() {
		return companyApplication;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getCompassPoint() {
		return compassPoint;
	}

	public String getContact2Email() {
		return contact2Email;
	}

	public String getContact2Name() {
		return contact2Name;
	}

	public String getContact2Phone() {
		return contact2Phone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public String getContactName() {
		return contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public String getCustomerCabinetId() {
		return customerCabinetId;
	}

	public BigDecimal getDaysBetweenScan() {
		return daysBetweenScan;
	}

	public String getDeliveryPoint() {
		return deliveryPoint;
	}

	public String getDeliveryPointDesc() {
		return deliveryPointDesc;
	}

	public String getDeliveryPointSelectionRule() {
		return deliveryPointSelectionRule;
	}

	public String getDeptId() {
		return deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public String getDirectedChargeAllowNull() {
		return directedChargeAllowNull;
	}

	public String getDockDeliverToSelectionRule() {
		return dockDeliverToSelectionRule;
	}

	public String getDockSelectionRule() {
		return dockSelectionRule;
	}

	public String getDropShip() {
		return dropShip;
	}

	public String getEditChargeNumber() {
		return editChargeNumber;
	}

	public String getEmissionPoint() {
		return emissionPoint;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getFloor() {
		return floor;
	}

	public BigDecimal getFloorId() {
		return floorId;
	}

	public boolean getIncludeExpiredMaterial() {
		return includeExpiredMaterial;
	}

	public String getInterior() {
		return interior;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getLocationDetail() {
		return locationDetail;
	}

	public String getLocationId() {
		return locationId;
	}

	public String getLocColumn() {
		return locColumn;
	}

	public String getLocSection() {
		return locSection;
	}

	public String getManagedUseApproval() {
		return managedUseApproval;
	}

	public String getManualMrCreation() {
		return manualMrCreation;
	}

	public String getModified() {
		return modified;
	}

	public String getOffsite() {
		return offsite;
	}

	public String getOrganization() {
		return organization;
	}

	public String getOverrideUsageLogging() {
		return overrideUsageLogging;
	}

	public String getProcessId() {
		return processId;
	}

	public BigDecimal getPullWithinDaysToExpiration() {
		return pullWithinDaysToExpiration;
	}

	public String getReportingEntityId() {
		return reportingEntityId;
	}

	public boolean getReportUsage() {
		return reportUsage;
	}

	public String getRoomDesc() {
		return roomDesc;
	}

	public BigDecimal getRoomId() {
		return roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public String getStatus() {
		return status;
	}

	public String getStockingAccountSysId() {
		return stockingAccountSysId;
	}

	public String getUpdatedByName() {
		return updatedByName;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public String getUseApprovalLimitsOption() {
		return useApprovalLimitsOption;
	}

	public String getUseCodeData() {
		return useCodeData;
	}

	//getters
	public String getUseCodeIdString() {
		return useCodeIdString;
	}

	public String getUseCodeName() {
		return useCodeName;
	}

	public String getUseCodeRequired() {
		return useCodeRequired;
	}

	public String getWorkAreaGroupDesc() {
		return workAreaGroupDesc;
	}

	public String isUseCodeRequired() {
		return useCodeRequired;
	}

	public void setAllocateByDistance(String allocateByDistance) {
		this.allocateByDistance = allocateByDistance;
	}

	public void setAllowSplitKits(String allowSplitKits) {
		this.allowSplitKits = allowSplitKits;
	}

	public void setAllowStocking(String allowStocking) {
		this.allowStocking = allowStocking;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}

	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}

	public void setAreaId(BigDecimal areaId) {
		this.areaId = areaId;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public void setBuildingDesc(String buildingDesc) {
		this.buildingDesc = buildingDesc;
	}

	public void setBuildingId(BigDecimal buildingId) {
		this.buildingId = buildingId;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public void setChargeTypeDefault(String chargeTypeDefault) {
		this.chargeTypeDefault = chargeTypeDefault;
	}

	public void setCompanyApplication(String companyApplication) {
		this.companyApplication = companyApplication;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCompassPoint(String compassPoint) {
		this.compassPoint = compassPoint;
	}

	public void setContact2Email(String contact2Email) {
		this.contact2Email = contact2Email;
	}

	public void setContact2Name(String contact2Name) {
		this.contact2Name = contact2Name;
	}

	public void setContact2Phone(String contact2Phone) {
		this.contact2Phone = contact2Phone;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public void setCustomerCabinetId(String customerCabinetId) {
		this.customerCabinetId = customerCabinetId;
	}

	public void setDaysBetweenScan(BigDecimal daysBetweenScan) {
		this.daysBetweenScan = daysBetweenScan;
	}

	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}

	public void setDeliveryPointDesc(String deliveryPointDesc) {
		this.deliveryPointDesc = deliveryPointDesc;
	}

	public void setDeliveryPointSelectionRule(String deliveryPointSelectionRule) {
		this.deliveryPointSelectionRule = deliveryPointSelectionRule;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setDirectedChargeAllowNull(String directedChargeAllowNull) {
		this.directedChargeAllowNull = directedChargeAllowNull;
	}

	public void setDockDeliverToSelectionRule(String dockDeliverToSelectionRule) {
		this.dockDeliverToSelectionRule = dockDeliverToSelectionRule;
	}

	public void setDockSelectionRule(String dockSelectionRule) {
		this.dockSelectionRule = dockSelectionRule;
	}

	public void setDropShip(String dropShip) {
		this.dropShip = dropShip;
	}

	public void setEditChargeNumber(String editChargeNumber) {
		this.editChargeNumber = editChargeNumber;
	}

	public void setEmissionPoint(String emissionPoint) {
		this.emissionPoint = emissionPoint;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public void setFloorId(BigDecimal floorId) {
		this.floorId = floorId;
	}

	public void setIncludeExpiredMaterial(boolean includeExpiredMaterial) {
		this.includeExpiredMaterial = includeExpiredMaterial;
	}

	public void setInterior(String interior) {
		this.interior = interior;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setLocationDetail(String locationDetail) {
		this.locationDetail = locationDetail;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public void setLocColumn(String locColumn) {
		this.locColumn = locColumn;
	}

	public void setLocSection(String locSection) {
		this.locSection = locSection;
	}

	public void setManagedUseApproval(String managedUseApproval) {
		this.managedUseApproval = managedUseApproval;
	}

	public void setManualMrCreation(String manualMrCreation) {
		this.manualMrCreation = manualMrCreation;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public void setOffsite(String offsite) {
		this.offsite = offsite;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public void setOverrideUsageLogging(String overrideUsageLogging) {
		this.overrideUsageLogging = overrideUsageLogging;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public void setPullWithinDaysToExpiration(BigDecimal pullWithinDaysToExpiration) {
		this.pullWithinDaysToExpiration = pullWithinDaysToExpiration;
	}

	public void setReportingEntityId(String reportingEntityId) {
		this.reportingEntityId = reportingEntityId;
	}

	public void setReportUsage(boolean reportUsage) {
		this.reportUsage = reportUsage;
	}

	public void setRoomDesc(String roomDesc) {
		this.roomDesc = roomDesc;
	}

	public void setRoomId(BigDecimal roomId) {
		this.roomId = roomId;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStockingAccountSysId(String stockingAccountSysId) {
		this.stockingAccountSysId = stockingAccountSysId;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public void setUseApprovalLimitsOption(String useApprovalLimitsOption) {
		this.useApprovalLimitsOption = useApprovalLimitsOption;
	}

	public void setUseCodeData(String useCodeData) {
		this.useCodeData = useCodeData;
	}

	public void setUseCodeIdString(String useCodeIdString) {
		this.useCodeIdString = useCodeIdString;
	}

	//setters
	public void setUseCodeName(String useCodeName) {
		this.useCodeName = useCodeName;
	}

	public void setUseCodeRequired(String useCodeRequired) {
		this.useCodeRequired = useCodeRequired;
	}

	public void setWorkAreaGroupDesc(String workAreaGroupDesc) {
		this.workAreaGroupDesc = workAreaGroupDesc;
	}

}