package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: CatalogAddPlannedUseBean <br>
 * @version: 1.0, Jul 11, 2011 <br>
 *****************************************************************************/

public class CatalogAddPlannedUseBean extends BaseDataBean {

	private BigDecimal plannedId;
    private String companyId;
    private BigDecimal requestId;
    private BigDecimal usageCategoryId;
    private BigDecimal usageSubcategoryId;
    private BigDecimal materialCategoryId;
    private BigDecimal materialSubcategoryId;
    private Date startDate;
    private Date endDate;
    private String haasPurchase;
    private String flyAwayWithAircraft;
    private String thinnedWhenUsed;
    private String shift1;
    private String shift2;
    private String shift3;
    private String saturday;
    private String sunday;
    private String additionalDescription;
    private String otherLocation;
    //edit data
    private BigDecimal useId;
    private BigDecimal substrateId;
    private BigDecimal useLocationId;
    private BigDecimal buildingId;
    private BigDecimal boothId;
    private BigDecimal maxQtyPerShift;
    private String maxQtyPerShiftUnit;
    private BigDecimal purchasingMethodId;
    private String gt54Gal;
    private String importFlag;
    private String exportFlag;
    private String thinnedCustomerMsdsNumber;
    private BigDecimal thinnedMatlAmountInRatio;
    private BigDecimal thinnerAmountInRatio;
    private String thinningUnit;
    private String pointOfContact;
    private String intendedProductFormulation;
    private BigDecimal programId;
    private BigDecimal estimatedAnnualUsage;
    private String estimatedAnnualUsageUnit;

    private String approvalCode;
    private String usageSubcategoryName;
    private String materialSubcategoryName;
    private String dischargeToSinkDrain;
    private String useDescriptionList;
    private String substrateName;
    private String buildingName;
    private String processLocation;
    private String boothName;

    //constructor
	public CatalogAddPlannedUseBean() {
	}
	
	
	public void setEstimatedAnnualUsage(BigDecimal estimatedAnnualUsage) {
		this.estimatedAnnualUsage = estimatedAnnualUsage;
	}
	
	public BigDecimal getEstimatedAnnualUsage() {
		return estimatedAnnualUsage;
	}
	
	public void setEstimatedAnnualUsageUnit(String estimatedAnnualUsageUnit) {
		this.estimatedAnnualUsageUnit = estimatedAnnualUsageUnit;
	}
	
	public String getEstimatedAnnualUsageUnit() {
		return estimatedAnnualUsageUnit;
	}
	public void setProgramId(BigDecimal programId) {
		this.programId = programId;
	}
	
	public BigDecimal getProgramId() {
		return programId;
	}

    public BigDecimal getPlannedId() {
        return plannedId;
    }

    public void setPlannedId(BigDecimal plannedId) {
        this.plannedId = plannedId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public BigDecimal getRequestId() {
        return requestId;
    }

    public void setRequestId(BigDecimal requestId) {
        this.requestId = requestId;
    }

    public BigDecimal getUsageCategoryId() {
        return usageCategoryId;
    }

    public void setUsageCategoryId(BigDecimal usageCategoryId) {
        this.usageCategoryId = usageCategoryId;
    }

    public BigDecimal getUsageSubcategoryId() {
        return usageSubcategoryId;
    }

    public void setUsageSubcategoryId(BigDecimal usageSubcategoryId) {
        this.usageSubcategoryId = usageSubcategoryId;
    }

    public BigDecimal getMaterialCategoryId() {
        return materialCategoryId;
    }

    public void setMaterialCategoryId(BigDecimal materialCategoryId) {
        this.materialCategoryId = materialCategoryId;
    }

    public BigDecimal getMaterialSubcategoryId() {
        return materialSubcategoryId;
    }

    public void setMaterialSubcategoryId(BigDecimal materialSubcategoryId) {
        this.materialSubcategoryId = materialSubcategoryId;
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

    public String getHaasPurchase() {
        return haasPurchase;
    }

    public void setHaasPurchase(String haasPurchase) {
        this.haasPurchase = haasPurchase;
    }

    public String getFlyAwayWithAircraft() {
        return flyAwayWithAircraft;
    }

    public void setFlyAwayWithAircraft(String flyAwayWithAircraft) {
        this.flyAwayWithAircraft = flyAwayWithAircraft;
    }

    public String getThinnedWhenUsed() {
        return thinnedWhenUsed;
    }

    public void setThinnedWhenUsed(String thinnedWhenUsed) {
        this.thinnedWhenUsed = thinnedWhenUsed;
    }

    public String getShift1() {
        return shift1;
    }

    public void setShift1(String shift1) {
        this.shift1 = shift1;
    }

    public String getShift2() {
        return shift2;
    }

    public void setShift2(String shift2) {
        this.shift2 = shift2;
    }

    public String getShift3() {
        return shift3;
    }

    public void setShift3(String shift3) {
        this.shift3 = shift3;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getAdditionalDescription() {
        return additionalDescription;
    }

    public void setAdditionalDescription(String additionalDescription) {
        this.additionalDescription = additionalDescription;
    }

    public String getOtherLocation() {
        return otherLocation;
    }

    public void setOtherLocation(String otherLocation) {
        this.otherLocation = otherLocation;
    }

    public BigDecimal getUseId() {
        return useId;
    }

    public void setUseId(BigDecimal useId) {
        this.useId = useId;
    }

    public BigDecimal getSubstrateId() {
        return substrateId;
    }

    public void setSubstrateId(BigDecimal substrateId) {
        this.substrateId = substrateId;
    }

    public BigDecimal getUseLocationId() {
        return useLocationId;
    }

    public void setUseLocationId(BigDecimal useLocationId) {
        this.useLocationId = useLocationId;
    }

    public BigDecimal getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(BigDecimal buildingId) {
        this.buildingId = buildingId;
    }

    public BigDecimal getBoothId() {
        return boothId;
    }

    public void setBoothId(BigDecimal boothId) {
        this.boothId = boothId;
    }

    public BigDecimal getMaxQtyPerShift() {
        return maxQtyPerShift;
    }

    public void setMaxQtyPerShift(BigDecimal maxQtyPerShift) {
        this.maxQtyPerShift = maxQtyPerShift;
    }

    public String getMaxQtyPerShiftUnit() {
        return maxQtyPerShiftUnit;
    }

    public void setMaxQtyPerShiftUnit(String maxQtyPerShiftUnit) {
        this.maxQtyPerShiftUnit = maxQtyPerShiftUnit;
    }

    public BigDecimal getPurchasingMethodId() {
        return purchasingMethodId;
    }

    public void setPurchasingMethodId(BigDecimal purchasingMethodId) {
        this.purchasingMethodId = purchasingMethodId;
    }

    public String getGt54Gal() {
        return gt54Gal;
    }

    public void setGt54Gal(String gt54Gal) {
        this.gt54Gal = gt54Gal;
    }

    public String getImportFlag() {
        return importFlag;
    }

    public void setImportFlag(String importFlag) {
        this.importFlag = importFlag;
    }

    public String getExportFlag() {
        return exportFlag;
    }

    public void setExportFlag(String exportFlag) {
        this.exportFlag = exportFlag;
    }

    public String getThinnedCustomerMsdsNumber() {
        return thinnedCustomerMsdsNumber;
    }

    public void setThinnedCustomerMsdsNumber(String thinnedCustomerMsdsNumber) {
        this.thinnedCustomerMsdsNumber = thinnedCustomerMsdsNumber;
    }

    public BigDecimal getThinnedMatlAmountInRatio() {
        return thinnedMatlAmountInRatio;
    }

    public void setThinnedMatlAmountInRatio(BigDecimal thinnedMatlAmountInRatio) {
        this.thinnedMatlAmountInRatio = thinnedMatlAmountInRatio;
    }

    public BigDecimal getThinnerAmountInRatio() {
        return thinnerAmountInRatio;
    }

    public void setThinnerAmountInRatio(BigDecimal thinnerAmountInRatio) {
        this.thinnerAmountInRatio = thinnerAmountInRatio;
    }

    public String getThinningUnit() {
        return thinningUnit;
    }

    public void setThinningUnit(String thinningUnit) {
        this.thinningUnit = thinningUnit;
    }

    public String getPointOfContact() {
        return pointOfContact;
    }

    public void setPointOfContact(String pointOfContact) {
        this.pointOfContact = pointOfContact;
    }

    public String getIntendedProductFormulation() {
        return intendedProductFormulation;
    }

    public void setIntendedProductFormulation(String intendedProductFormulation) {
        this.intendedProductFormulation = intendedProductFormulation;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getUsageSubcategoryName() {
        return usageSubcategoryName;
    }

    public void setUsageSubcategoryName(String usageSubcategoryName) {
        this.usageSubcategoryName = usageSubcategoryName;
    }

    public String getMaterialSubcategoryName() {
        return materialSubcategoryName;
    }

    public void setMaterialSubcategoryName(String materialSubcategoryName) {
        this.materialSubcategoryName = materialSubcategoryName;
    }

    public String getDischargeToSinkDrain() {
        return dischargeToSinkDrain;
    }

    public void setDischargeToSinkDrain(String dischargeToSinkDrain) {
        this.dischargeToSinkDrain = dischargeToSinkDrain;
    }

    public String getUseDescriptionList() {
        return useDescriptionList;
    }

    public void setUseDescriptionList(String useDescriptionList) {
        this.useDescriptionList = useDescriptionList;
    }

    public String getSubstrateName() {
        return substrateName;
    }

    public void setSubstrateName(String substrateName) {
        this.substrateName = substrateName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getProcessLocation() {
        return processLocation;
    }

    public void setProcessLocation(String processLocation) {
        this.processLocation = processLocation;
    }

    public String getBoothName() {
        return boothName;
    }

    public void setBoothName(String boothName) {
        this.boothName = boothName;
    }
}