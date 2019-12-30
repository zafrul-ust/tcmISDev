package com.tcmis.client.common.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: CabinetSetupViewBean <br>
 * 
 * @version: 1.0, Nov 10, 2010 <br>
 *****************************************************************************/

public class WorkAreaSetupInputBean extends BaseDataBean {

	private static final BigDecimal	NEW							= new BigDecimal("-1");
	private boolean					active						= true;
	private boolean					allowSplitKits				= false;
	private String					application;
	private String					applicationDesc;
	private BigDecimal				applicationId;
	private String					areaId;
	private String					buildingId;
	private String					chargeTypeDefault;
	private String					companyId;
	private String					compassPoint;
	private String					contact2Email;
	private String					contact2Name;
	private String					contact2Phone;
	private String					contactEmail;
	private String					contactName;
	private String					contactPhone;
	private String					customerCabinetId;
	private BigDecimal				daysBetweenScan;
	private String					deliveryPoint;
	private boolean					deliveryPointFixed			= false;
	private String					deptId;
	private boolean					dockFixed					= false;
	private boolean					dropShip					= false;
	private String					emissionPoint;
	private boolean					hetMultipleBuildingUsage	= false;
	private boolean					includeExpiredMaterial		= false;
	private String					inventoryGroup;
	private String					locationDetail;
	private String					locationId;
	private String					locColumn;
	private String					locSection;
	private String					modified;
	private boolean					offsite						= false;
	private String					orderingMethod;
	private boolean					overrideUsageLogging		= false;
	private BigDecimal				pullWithinDaysToExpiration;
	private String					reportingEntityId;
	private boolean					reportUsage					= false;
	private String					roomId;
	private boolean					specificUseApprovalRequired	= false;

	private String					status;
	private String					stockingAccountSysId;
	private boolean					touched						= false;
	private BigDecimal				updateBy;
	private String					useCodeIdString;

	private BigDecimal flammabilityControlZoneId;
	private BigDecimal vocZoneId;
	
	private boolean					reportInventory					= false;
	// constructor
	public WorkAreaSetupInputBean() {
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

	public String getAreaId() {
		return areaId;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public String getChargeTypeDefault() {
		return chargeTypeDefault;
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

	public String getDeptId() {
		return deptId;
	}

	public String getEmissionPoint() {
		return emissionPoint;
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

	public String getModified() {
		return modified;
	}

	public String getOrderingMethod() {
		return orderingMethod;
	}

	public BigDecimal getPullWithinDaysToExpiration() {
		return pullWithinDaysToExpiration;
	}

	public String getReportingEntityId() {
		return reportingEntityId;
	}

	public String getRoomId() {
		return roomId;
	}

	public boolean getSpecificUseApprovalRequired() {
		return specificUseApprovalRequired;
	}

	public String getStatus() {
		return status;
	}

	public String getStockingAccountSysId() {
		return stockingAccountSysId;
	}

	public BigDecimal getUpdateBy() {
		return updateBy;
	}

	public String getUseCodeIdString() {
		return useCodeIdString;
	}

	public boolean hasDescription() {
		return !StringHandler.isBlankString(this.applicationDesc);
	}

	public boolean isActive() {
		return active;
	}

	public boolean isAllowSplitKits() {
		return allowSplitKits;
	}

	public boolean isDeliveryPointFixed() {
		return deliveryPointFixed;
	}

	public boolean isDockFixed() {
		return dockFixed;
	}

	public boolean isDropShip() {
		return dropShip;
	}

	public boolean isHetMultipleBuildingUsage() {
		return hetMultipleBuildingUsage;
	}

	public boolean isIncludeExpiredMaterial() {
		return includeExpiredMaterial;
	}

	public boolean isNew() {
		return NEW.equals(this.applicationId);
	}

	public boolean isOffsite() {
		return offsite;
	}

	public boolean isOrderingBoth() {
		return "BOTH".equals(this.orderingMethod);
	}

	public boolean isOrderingManual() {
		return isOrderingBoth() || "MANUAL".equals(this.orderingMethod);
	}

	public boolean isOrderingStockLevel() {
		return isOrderingBoth() || "STOCKLEVEL".equals(this.orderingMethod);
	}

	public boolean isOverrideUsageLogging() {
		return overrideUsageLogging;
	}

	public boolean isReportUsage() {
		return reportUsage;
	}

	public boolean isSpecificUseApprovalRequired() {
		return specificUseApprovalRequired;
	}

	public boolean isTouched() {
		return touched;
	}

	public void setActive(boolean active) {
		this.active = active;
		this.status = active ? "A" : "I";
	}

	public void setAllowSplitKits(boolean allowSplitKits) {
		this.allowSplitKits = allowSplitKits;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setApplicationDesc(String cabinetName) {
		this.applicationDesc = cabinetName;
	}

	public void setApplicationId(BigDecimal cabinetId) {
		this.applicationId = cabinetId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}

	public void setChargeTypeDefault(String chargeTypeDefault) {
		this.chargeTypeDefault = chargeTypeDefault;
	}

	// setters
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

	public void setDeliveryPointFixed(boolean deliveryPointFixed) {
		this.deliveryPointFixed = deliveryPointFixed;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public void setDockFixed(boolean dockFixed) {
		this.dockFixed = dockFixed;
	}

	public void setDropShip(boolean dropShip) {
		this.dropShip = dropShip;
	}

	public void setEmissionPoint(String emissionPoint) {
		this.emissionPoint = emissionPoint;
	}

	public void setHetMultipleBuildingUsage(boolean hetMultipleBuildingUsage) {
		this.hetMultipleBuildingUsage = hetMultipleBuildingUsage;
	}

	public void setIncludeExpiredMaterial(boolean includeExpiredMaterial) {
		this.includeExpiredMaterial = includeExpiredMaterial;
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

	public void setModified(String modified) {
		this.modified = modified;
	}

	public void setOffsite(boolean offsite) {
		this.offsite = offsite;
	}

	public void setOrderingMethod(String orderingMethod) {
		this.orderingMethod = orderingMethod;
	}

	public void setOverrideUsageLogging(boolean overrideUsageLogging) {
		this.overrideUsageLogging = overrideUsageLogging;
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

	public void setRoomId(String room) {
		this.roomId = room;
	}

	public void setSpecificUseApprovalRequired(boolean specificUseApprovalRequired) {
		this.specificUseApprovalRequired = specificUseApprovalRequired;
	}

	public void setStatus(String status) {
		this.status = status;
		this.active = "A".equals(status) ? true : false;
	}

	public void setStockingAccountSysId(String stockingAccountSysId) {
		this.stockingAccountSysId = stockingAccountSysId;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}

	public void setUpdateBy(BigDecimal updateBy) {
		this.updateBy = updateBy;
	}

	public void setUseCodeIdString(String useCodeIdString) {
		this.useCodeIdString = useCodeIdString;
	}

	public BigDecimal getFlammabilityControlZoneId() {
		return flammabilityControlZoneId;
	}

	public void setFlammabilityControlZoneId(BigDecimal flammabilityControlZoneId) {
		this.flammabilityControlZoneId = flammabilityControlZoneId;
	}

	public BigDecimal getVocZoneId() {
		return vocZoneId;
	}

	public void setVocZoneId(BigDecimal vocZoneId) {
		this.vocZoneId = vocZoneId;
	}

	public boolean isReportInventory() {
		return reportInventory;
	}

	public void setReportInventory(boolean reportInventory) {
		this.reportInventory = reportInventory;
	}
}