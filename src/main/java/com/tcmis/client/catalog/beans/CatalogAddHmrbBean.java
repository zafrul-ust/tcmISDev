package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/******************************************************************************
 * CLASSNAME: CatalogAddHmrbBean <br>
 * @version: 1.0, Jul 11, 2011 <br>
 *****************************************************************************/

public class CatalogAddHmrbBean extends BaseDataBean {

    private BigDecimal requestId;
    private BigDecimal hmrbLineItem;
    private String calledFrom;
    private String companyId;
    private String catalogCompanyId;
    private String catalogId;
    private Collection usageCategoryColl;
    private Collection materialCategoryColl;
    private String facilityId;
    private String usageCategoryId;
    private String usageCategoryName;
    private String usageSubcategoryId;
    private String usageSubcategoryName;
    private Date beginDateJsp;
    private Date endDateJsp;
    private String materialCategoryId;
    private String materialCategoryName;
    private String materialSubcategoryId;
    private String materialSubcategoryName;
    private String haasPurchased;
    private String dataSource;
    private BigDecimal approvalCodeId;
    private String approvalCodeName;
    
    //process info
    private String matlFlyAwayWithAircraft;
    private String additionalDescription;
    private String matlThinnedWhenUsed;
    //ftw specific
    private String shift1;
    private String shift2;
    private String shift3;
    private String saturday;
    private String sunday;
    private Collection buildingColl;
    private String selectedBuildingIds;
    private String processLocationOtherText;
    private Collection boothColl;
    private String selectedBoothIds;
    private Collection useDescriptionColl;
    private String selectedUseDescriptionIds;
    private Collection substrateColl;
    private String selectedSubstrateIds;
    private Collection useLocationColl;
    private String selectedUseLocationIds;
    private BigDecimal maxQtyUsePerShift;
    private String maxQtyUsePerShiftUnit;
    private Collection maxQtyUsePerShiftUnitColl;
    private String approvalCodeStatus;
    private Collection purchasingMethodColl;
    private String selectedPurchasingMethodIds;
    private String gt54Gal;
    private String uAction;
    private String importFlag;
    private String exportFlag;
    private String thinnedCustomerMsdsNumber;
    private BigDecimal thinnedMatlAmountInRatio;
    private BigDecimal thinnerAmountInRatio;
    private String thinningUnit;
    private String eshContact;
    private String pointOfContact;
    private String intendedProductFormulation;
    private String applicationUseGroupId;
    private BigDecimal programId;
    private Collection programColl;
    private String showProgram;
    private String emapRequired;
    private BigDecimal estimatedAnnualUsage;
    private String estimatedAnnualUsageUnit;
    private boolean isKit;
    
    //constructor
    public CatalogAddHmrbBean() {
    }
    
	public void setIsKit(boolean isKit) {
		this.isKit = isKit;
	}
	public boolean getIsKit() {
		return isKit;
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
    
    public void setEmapRequired(String emapRequired) {
        this.emapRequired = emapRequired;
    }

    public String getEmapRequired() {
        return emapRequired;
    }
    
    public String getShowProgram() {
        return showProgram;
    }

    public void setShowProgram(String showProgram) {
        this.showProgram = showProgram;
    }
    
    public Collection getProgramColl() {
        return programColl;
    }

    public void setProgramColl(Collection programColl) {
        this.programColl = programColl;
    }
    
	public void setProgramId(BigDecimal programId) {
		this.programId = programId;
	}
	
	public BigDecimal getProgramId() {
		return programId;
	}

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public BigDecimal getRequestId() {
        return requestId;
    }

    public void setRequestId(BigDecimal requestId) {
        this.requestId = requestId;
    }

    public BigDecimal getHmrbLineItem() {
        return hmrbLineItem;
    }

    public void setHmrbLineItem(BigDecimal hmrbLineItem) {
        this.hmrbLineItem = hmrbLineItem;
    }

    public String getCalledFrom() {
        return calledFrom;
    }

    public void setCalledFrom(String calledFrom) {
        this.calledFrom = calledFrom;
    }

    public String getHaasPurchased() {
        return haasPurchased;
    }

    public void setHaasPurchased(String haasPurchased) {
        this.haasPurchased = haasPurchased;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public BigDecimal getApprovalCodeId() {
        return approvalCodeId;
    }

    public void setApprovalCodeId(BigDecimal approvalCodeId) {
        this.approvalCodeId = approvalCodeId;
    }

    public String getApprovalCodeName() {
        return approvalCodeName;
    }

    public void setApprovalCodeName(String approvalCodeName) {
        this.approvalCodeName = approvalCodeName;
    }

    public Collection getUsageCategoryColl() {
        return usageCategoryColl;
    }

    public void setUsageCategoryColl(Collection usageCategoryColl) {
        this.usageCategoryColl = usageCategoryColl;
    }

    public Collection getMaterialCategoryColl() {
        return materialCategoryColl;
    }

    public void setMaterialCategoryColl(Collection materialCategoryColl) {
        this.materialCategoryColl = materialCategoryColl;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getUsageCategoryId() {
        return usageCategoryId;
    }

    public void setUsageCategoryId(String usageCategoryId) {
        this.usageCategoryId = usageCategoryId;
    }

    public String getUsageCategoryName() {
        return usageCategoryName;
    }

    public void setUsageCategoryName(String usageCategoryName) {
        this.usageCategoryName = usageCategoryName;
    }

    public String getUsageSubcategoryId() {
        return usageSubcategoryId;
    }

    public void setUsageSubcategoryId(String usageSubcategoryId) {
        this.usageSubcategoryId = usageSubcategoryId;
    }

    public String getUsageSubcategoryName() {
        return usageSubcategoryName;
    }

    public void setUsageSubcategoryName(String usageSubcategoryName) {
        this.usageSubcategoryName = usageSubcategoryName;
    }

    public Date getBeginDateJsp() {
        return beginDateJsp;
    }

    public void setBeginDateJsp(Date beginDateJsp) {
        this.beginDateJsp = beginDateJsp;
    }

    public Date getEndDateJsp() {
        return endDateJsp;
    }

    public void setEndDateJsp(Date endDateJsp) {
        this.endDateJsp = endDateJsp;
    }

    public String getMaterialCategoryId() {
        return materialCategoryId;
    }

    public void setMaterialCategoryId(String materialCategoryId) {
        this.materialCategoryId = materialCategoryId;
    }

    public String getMaterialCategoryName() {
        return materialCategoryName;
    }

    public void setMaterialCategoryName(String materialCategoryName) {
        this.materialCategoryName = materialCategoryName;
    }

    public String getMaterialSubcategoryId() {
        return materialSubcategoryId;
    }

    public void setMaterialSubcategoryId(String materialSubcategoryId) {
        this.materialSubcategoryId = materialSubcategoryId;
    }

    public String getMaterialSubcategoryName() {
        return materialSubcategoryName;
    }

    public void setMaterialSubcategoryName(String materialSubcategoryName) {
        this.materialSubcategoryName = materialSubcategoryName;
    }

    public String getMatlFlyAwayWithAircraft() {
        return matlFlyAwayWithAircraft;
    }

    public void setMatlFlyAwayWithAircraft(String matlFlyAwayWithAircraft) {
        this.matlFlyAwayWithAircraft = matlFlyAwayWithAircraft;
    }

    public String getAdditionalDescription() {
        return additionalDescription;
    }

    public void setAdditionalDescription(String additionalDescription) {
        this.additionalDescription = additionalDescription;
    }

    public String getMatlThinnedWhenUsed() {
        return matlThinnedWhenUsed;
    }

    public void setMatlThinnedWhenUsed(String matlThinnedWhenUsed) {
        this.matlThinnedWhenUsed = matlThinnedWhenUsed;
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

    public Collection getBuildingColl() {
        return buildingColl;
    }

    public void setBuildingColl(Collection buildingColl) {
        this.buildingColl = buildingColl;
    }

    public String getSelectedBuildingIds() {
        return selectedBuildingIds;
    }

    public void setSelectedBuildingIds(String selectedBuildingIds) {
        this.selectedBuildingIds = selectedBuildingIds;
    }

    public String getProcessLocationOtherText() {
        return processLocationOtherText;
    }

    public void setProcessLocationOtherText(String processLocationOtherText) {
        this.processLocationOtherText = processLocationOtherText;
    }

    public Collection getBoothColl() {
        return boothColl;
    }

    public void setBoothColl(Collection boothColl) {
        this.boothColl = boothColl;
    }

    public String getSelectedBoothIds() {
        return selectedBoothIds;
    }

    public void setSelectedBoothIds(String selectedBoothIds) {
        this.selectedBoothIds = selectedBoothIds;
    }

    public Collection getUseDescriptionColl() {
        return useDescriptionColl;
    }

    public void setUseDescriptionColl(Collection useDescriptionColl) {
        this.useDescriptionColl = useDescriptionColl;
    }

    public String getSelectedUseDescriptionIds() {
        return selectedUseDescriptionIds;
    }

    public void setSelectedUseDescriptionIds(String selectedUseDescriptionIds) {
        this.selectedUseDescriptionIds = selectedUseDescriptionIds;
    }

    public Collection getSubstrateColl() {
        return substrateColl;
    }

    public void setSubstrateColl(Collection substrateColl) {
        this.substrateColl = substrateColl;
    }

    public String getSelectedSubstrateIds() {
        return selectedSubstrateIds;
    }

    public void setSelectedSubstrateIds(String selectedSubstrateIds) {
        this.selectedSubstrateIds = selectedSubstrateIds;
    }

    public Collection getUseLocationColl() {
        return useLocationColl;
    }

    public void setUseLocationColl(Collection useLocationColl) {
        this.useLocationColl = useLocationColl;
    }

    public String getSelectedUseLocationIds() {
        return selectedUseLocationIds;
    }

    public void setSelectedUseLocationIds(String selectedUseLocationIds) {
        this.selectedUseLocationIds = selectedUseLocationIds;
    }

    public BigDecimal getMaxQtyUsePerShift() {
        return maxQtyUsePerShift;
    }

    public void setMaxQtyUsePerShift(BigDecimal maxQtyUsePerShift) {
        this.maxQtyUsePerShift = maxQtyUsePerShift;
    }

    public String getMaxQtyUsePerShiftUnit() {
        return maxQtyUsePerShiftUnit;
    }

    public void setMaxQtyUsePerShiftUnit(String maxQtyUsePerShiftUnit) {
        this.maxQtyUsePerShiftUnit = maxQtyUsePerShiftUnit;
    }

    public Collection getMaxQtyUsePerShiftUnitColl() {
        return maxQtyUsePerShiftUnitColl;
    }

    public void setMaxQtyUsePerShiftUnitColl(Collection maxQtyUsePerShiftUnitColl) {
        this.maxQtyUsePerShiftUnitColl = maxQtyUsePerShiftUnitColl;
    }

    public String getApprovalCodeStatus() {
        return approvalCodeStatus;
    }

    public void setApprovalCodeStatus(String approvalCodeStatus) {
        this.approvalCodeStatus = approvalCodeStatus;
    }

    public Collection getPurchasingMethodColl() {
        return purchasingMethodColl;
    }

    public void setPurchasingMethodColl(Collection purchasingMethodColl) {
        this.purchasingMethodColl = purchasingMethodColl;
    }

    public String getSelectedPurchasingMethodIds() {
        return selectedPurchasingMethodIds;
    }

    public void setSelectedPurchasingMethodIds(String selectedPurchasingMethodIds) {
        this.selectedPurchasingMethodIds = selectedPurchasingMethodIds;
    }

    public String getGt54Gal() {
        return gt54Gal;
    }

    public void setGt54Gal(String gt54Gal) {
        this.gt54Gal = gt54Gal;
    }

    public String getUAction() {
        return uAction;
    }

    public void setUAction(String uAction) {
        this.uAction = uAction;
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

    public String getEshContact() {
        return eshContact;
    }

    public void setEshContact(String eshContact) {
        this.eshContact = eshContact;
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

    public String getApplicationUseGroupId() {
        return applicationUseGroupId;
    }

    public void setApplicationUseGroupId(String applicationUseGroupId) {
        this.applicationUseGroupId = applicationUseGroupId;
    }
} //end of class