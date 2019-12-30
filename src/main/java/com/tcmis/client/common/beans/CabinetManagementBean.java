package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.client.het.beans.HetPermitBean;
import com.tcmis.client.het.beans.VvHetApplicationMethodBean;
import com.tcmis.client.het.beans.VvHetSubstrateBean;
import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CabinetManagementBean <br>
 * 
 * @version: 1.0, Oct 14, 2010 <br>
 * 
 *****************************************************************************/

public class CabinetManagementBean extends BaseDataBean {
	private Date									activationDate;
	private String									application;
	private String									applicationDesc;
	private BigDecimal								applicationId;
	private Collection<VvHetApplicationMethodBean>	applicationMethods;
	private String									areaName;
	private BigDecimal								avgAmount;
	private BigDecimal								binId;
	private String									binName;
	private String									binPartStatus;
	private BigDecimal								buildingId;
	private String									buildingName;
	private BigDecimal								cabinetId;
	private String									cabinetName;
	private String									catalogCompanyId;
	private String									catalogDesc;
	private String									catalogId;
	private String									catPartNo;
	private String									changed;
	private String									companyId;
	private String									containerSize;
	private String									countType;
	private String									cpiStatus;
	private String									customerCabinetId;
	private String									defaultApplicationMethodCod;
	private String									defaultPartType;
	private String									defaultPermitId;
	private String									defaultSubstrateCode;
	private String									deptId;
	private String									deptName;
	private String									facilityId;
	private String									facilityName;
	private String									haasMaterialIdString;
	private Date									inactivationDate;

	private String									insertedName;
	private Date									insertedOn;
	private String									inventoryGroup;
	private BigDecimal								itemId;
	private BigDecimal								kanbanReorderQuantity;
	private String									labelSpec;
	private BigDecimal								largestContainerSize;
	private BigDecimal								leadTimeDays;
	private String									materialDesc;
	private String									materialHandlingCode;
	private BigDecimal								materialId;

	private String									materialIdString;
	private BigDecimal								maxAmount;
	private BigDecimal								maxWtLb;
	private String									method;
	private String									mfgDesc;
	private String									minStorageTempDisplay;

	private String									msdsId;
	private String									msdsNumber;
	private String									msdsString;

	private String									nonManaged;
	private String									oldBinPartStatus;
	private String									oldCountType;
	private String									oldCpiStatus;

	private String									oldStatus;
	private String									orderingApplication;
	private String									orderingApplicationDesc;

	private String									packaging;
	private BigDecimal								partGroupNo;
	private String									permitName;

	private Collection<HetPermitBean>				permits;
	private BigDecimal								reorderPoint;
	private BigDecimal								reorderQuantity;

	private String									sizeUnit;
	private String									sizeUnitOption;
	private boolean									solvent	= false;
	private String									sourceHub;
	private String									status;

	private BigDecimal								stockingLevel;
	private String									substrate;
	private Collection<VvHetSubstrateBean>			substrates;
	private String									tierIIPressure;
	private String									tierIIStorage;
	private String									tierIITemperature;
	private String									useApplication;
	private String									useApplicationDesc;
	private BigDecimal								wtLb;
	private String 									allowStocking;
	private String									hcoFlag;
	private String									ownershipName;
	private String									partDescription;
	private Date									levelHoldEndDate;
    private String                                  putAwayMethodOverride;
    private String                                  dropShipOverride;
    private String                                  binSizeUnit;

    // constructor
	public CabinetManagementBean() {
	}

	public Date getActivationDate() {
		return activationDate;
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

	public Collection<VvHetApplicationMethodBean> getApplicationMethods() {
		return applicationMethods;
	}

	public String getAreaName() {
		return areaName;
	}

	public BigDecimal getAvgAmount() {
		return avgAmount;
	}

	public BigDecimal getBinId() {
		return binId;
	}

	public String getBinName() {
		return binName;
	}

	public String getBinPartStatus() {
		return binPartStatus;
	}

	public BigDecimal getBuildingId() {
		return buildingId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public BigDecimal getCabinetId() {
		return cabinetId;
	}

	public String getCabinetName() {
		return cabinetName;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public String getCatalogDesc() {
		return catalogDesc;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public String getChanged() {
		return changed;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getContainerSize() {
		return containerSize;
	}

	public String getCountType() {
		return countType;
	}

	public String getCpiStatus() {
		return cpiStatus;
	}

	public String getCustomerCabinetId() {
		return customerCabinetId;
	}

	public String getDefaultApplicationMethodCod() {
		return defaultApplicationMethodCod;
	}

	public String getDefaultPartType() {
		return defaultPartType;
	}

	public String getDefaultPermitId() {
		return defaultPermitId;
	}

	public String getDefaultSubstrateCode() {
		return defaultSubstrateCode;
	}

	public String getDeptId() {
		return deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public String getHaasMaterialIdString() {
		return haasMaterialIdString;
	}

	public Date getInactivationDate() {
		return inactivationDate;
	}

	public String getInsertedName() {
		return insertedName;
	}

	public Date getInsertedOn() {
		return insertedOn;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getKanbanReorderQuantity() {
		return kanbanReorderQuantity;
	}

	public String getLabelSpec() {
		return labelSpec;
	}

	public BigDecimal getLargestContainerSize() {
		return largestContainerSize;
	}

	public BigDecimal getLeadTimeDays() {
		return leadTimeDays;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public String getMaterialHandlingCode() {
		return materialHandlingCode;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public String getMaterialIdString() {
		return materialIdString;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public BigDecimal getMaxWtLb() {
		return maxWtLb;
	}

	public String getMethod() {
		return method;
	}

	public String getMfgDesc() {
		return mfgDesc;
	}

	public String getMinStorageTempDisplay() {
		return minStorageTempDisplay;
	}

	public String getMsdsId() {
		return msdsId;
	}

	public String getMsdsNumber() {
		return msdsNumber;
	}

	public String getMsdsString() {
		return msdsString;
	}

	public String getNonManaged() {
		return nonManaged;
	}

	public String getOldBinPartStatus() {
		return oldBinPartStatus;
	}

	public String getOldCountType() {
		return oldCountType;
	}

	public String getOldCpiStatus() {
		return oldCpiStatus;
	}

	public String getOldStatus() {
		return oldStatus;
	}

	public String getOrderingApplication() {
		return orderingApplication;
	}

	public String getOrderingApplicationDesc() {
		return orderingApplicationDesc;
	}

	public String getPackaging() {
		return packaging;
	}

	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}

	public String getPermitName() {
		return permitName;
	}

	public Collection<HetPermitBean> getPermits() {
		return permits;
	}

	public BigDecimal getReorderPoint() {
		return reorderPoint;
	}

	public BigDecimal getReorderQuantity() {
		return reorderQuantity;
	}

	public String getSizeUnit() {
		return sizeUnit;
	}

	public String getSizeUnitOption() {
		return sizeUnitOption;
	}

	public String getSourceHub() {
		return sourceHub;
	}

	public String getStatus() {
		return status;
	}

	public BigDecimal getStockingLevel() {
		return stockingLevel;
	}

	public String getSubstrate() {
		return substrate;
	}

	public Collection<VvHetSubstrateBean> getSubstrates() {
		return substrates;
	}

	public String getTierIIPressure() {
		return tierIIPressure;
	}

	public String getTierIIStorage() {
		return tierIIStorage;
	}

	public String getTierIITemperature() {
		return tierIITemperature;
	}

	public String getUseApplication() {
		return useApplication;
	}

	public String getUseApplicationDesc() {
		return useApplicationDesc;
	}

	public BigDecimal getWtLb() {
		return wtLb;
	}
	
	public String getAllowStocking() {
       return allowStocking.trim();
	}

	public String getHcoFlag() {
		return hcoFlag;
	}

	public String getOwnershipName() {
		return ownershipName;
	}

	public boolean isSolvent() {
		return solvent;
	}
	
	public void setAllowStocking(String allowStocking) {
		this.allowStocking = allowStocking.trim();
    }

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
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

	public void setApplicationMethods(Collection<VvHetApplicationMethodBean> applicationMethods) {
		this.applicationMethods = applicationMethods;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public void setAvgAmount(BigDecimal averageAmount) {
		this.avgAmount = averageAmount;
	}

	public void setBinId(BigDecimal binId) {
		this.binId = binId;
	}

	public void setBinName(String binName) {
		this.binName = binName;
	}

	public void setBinPartStatus(String binPartStatus) {
		this.binPartStatus = binPartStatus;
	}

	public void setBuildingId(BigDecimal buildingId) {
		this.buildingId = buildingId;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public void setCabinetId(BigDecimal cabinetId) {
		this.cabinetId = cabinetId;
	}

	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setChanged(String changed) {
		this.changed = changed;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public void setCpiStatus(String cpiStatus) {
		this.cpiStatus = cpiStatus;
	}

	public void setCustomerCabinetId(String customerCabinetId) {
		this.customerCabinetId = customerCabinetId;
	}

	public void setDefaultApplicationMethodCod(String defaultApplicationMethodCod) {
		this.defaultApplicationMethodCod = defaultApplicationMethodCod;
	}

	public void setDefaultPartType(String defaultPartType) {
		this.defaultPartType = defaultPartType;
	}

	public void setDefaultPermitId(String defaultPermitId) {
		this.defaultPermitId = defaultPermitId;
	}

	public void setDefaultSubstrateCode(String defaultSubstrateCode) {
		this.defaultSubstrateCode = defaultSubstrateCode;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public void setHaasMaterialIdString(String haasMaterialIdString) {
		this.haasMaterialIdString = haasMaterialIdString;
	}

	public void setInactivationDate(Date inactivationDate) {
		this.inactivationDate = inactivationDate;
	}

	public void setInsertedName(String insertedName) {
		this.insertedName = insertedName;
	}

	public void setInsertedOn(Date insertedOn) {
		this.insertedOn = insertedOn;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setKanbanReorderQuantity(BigDecimal kanbanReorderQuantity) {
		this.kanbanReorderQuantity = kanbanReorderQuantity;
	}

	public void setLabelSpec(String labelSpec) {
		this.labelSpec = labelSpec;
	}

	public void setLargestContainerSize(BigDecimal largestContainerSize) {
		this.largestContainerSize = largestContainerSize;
	}

	public void setLeadTimeDays(BigDecimal leadTimeDays) {
		this.leadTimeDays = leadTimeDays;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public void setMaterialHandlingCode(String materialHandlingCode) {
		this.materialHandlingCode = materialHandlingCode;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public void setMaterialIdString(String materialIdString) {
		this.materialIdString = materialIdString;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public void setMaxWtLb(BigDecimal maxWtLb) {
		this.maxWtLb = maxWtLb;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}

	public void setMinStorageTempDisplay(String minStorageTempDisplay) {
		this.minStorageTempDisplay = minStorageTempDisplay;
	}

	public void setMsdsId(String msdsId) {
		this.msdsId = msdsId;
	}

	public void setMsdsNumber(String msdsNumber) {
		this.msdsNumber = msdsNumber;
	}

	public void setMsdsString(String msdsString) {
		this.msdsString = msdsString;
	}

	public void setNonManaged(String nonManaged) {
		this.nonManaged = nonManaged;
	}

	public void setOldBinPartStatus(String oldBinPartStatus) {
		this.oldBinPartStatus = oldBinPartStatus;
	}

	public void setOldCountType(String oldCountType) {
		this.oldCountType = oldCountType;
	}

	public void setOldCpiStatus(String oldCpiStatus) {
		this.oldCpiStatus = oldCpiStatus;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	public void setOrderingApplication(String orderingApplication) {
		this.orderingApplication = orderingApplication;
	}

	public void setOrderingApplicationDesc(String orderingApplicationDesc) {
		this.orderingApplicationDesc = orderingApplicationDesc;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}

	public void setPermitName(String permitName) {
		this.permitName = permitName;
	}

	public void setPermits(Collection<HetPermitBean> permits) {
		this.permits = permits;
	}

	public void setReorderPoint(BigDecimal reorderPoint) {
		this.reorderPoint = reorderPoint;
	}

	public void setReorderQuantity(BigDecimal reorderQuantity) {
		this.reorderQuantity = reorderQuantity;
	}

	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}

	public void setSizeUnitOption(String sizeUnitOption) {
		this.sizeUnitOption = sizeUnitOption;
	}

	public void setSolvent(boolean solvent) {
		this.solvent = solvent;
	}

	public void setSourceHub(String sourceHub) {
		this.sourceHub = sourceHub;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStockingLevel(BigDecimal stockingLevel) {
		this.stockingLevel = stockingLevel;
	}

	public void setSubstrate(String substrate) {
		this.substrate = substrate;
	}

	public void setSubstrates(Collection<VvHetSubstrateBean> substrates) {
		this.substrates = substrates;
	}

	public void setTierIIPressure(String tierIIPressure) {
		this.tierIIPressure = tierIIPressure;
	}

	public void setTierIIStorage(String tierIIStorage) {
		this.tierIIStorage = tierIIStorage;
	}

	public void setTierIITemperature(String tierIITemperature) {
		this.tierIITemperature = tierIITemperature;
	}

	public void setUseApplication(String useApplication) {
		this.useApplication = useApplication;
	}

	public void setUseApplicationDesc(String useApplicationDesc) {
		this.useApplicationDesc = useApplicationDesc;
	}

	public void setWtLb(BigDecimal wtLb) {
		this.wtLb = wtLb;
	}

	public void setHcoFlag(String hcoFlag) {
		this.hcoFlag = hcoFlag;
	}

	public void setOwnershipName(String ownershipName) {
		this.ownershipName = ownershipName;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public Date getLevelHoldEndDate() {
		return levelHoldEndDate;
	}

	public void setLevelHoldEndDate(Date levelHoldEndDate) {
		this.levelHoldEndDate = levelHoldEndDate;
	}

    public String getDropShipOverride() {
        return dropShipOverride;
    }

    public void setDropShipOverride(String dropShipOverride) {
        this.dropShipOverride = dropShipOverride;
    }

    public String getPutAwayMethodOverride() {
        return putAwayMethodOverride;
    }

    public void setPutAwayMethodOverride(String putAwayMethodOverride) {
        this.putAwayMethodOverride = putAwayMethodOverride;
    }

	public String getBinSizeUnit() {
		return binSizeUnit;
	}

	public void setBinSizeUnit(String binSizeUnit) {
		this.binSizeUnit = binSizeUnit;
	}
} // end of class