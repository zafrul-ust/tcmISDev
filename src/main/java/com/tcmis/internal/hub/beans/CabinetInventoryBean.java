package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CabinetInventoryBean <br>
 * @version: 1.0, Sep 26, 2007 <br>
 *****************************************************************************/

public class CabinetInventoryBean extends BaseDataBean {

	private BigDecimal binId;
	private BigDecimal cabinetId;
	private String cabinetName;
	private String catalogId;
	private String catPartNo;
	private String companyId;
	//	private String 		countDatetime;
	private Date countDatetime;
	private String countedByName;
	private BigDecimal countedByPersonnelId;
	private BigDecimal countQuantity;
	private String countType;
	private String currencyId;
	//	private String 		dateProcessed;
	private Date dateProcessed;
	//	private String 		expireDate;
	private Date expireDate;
	private String facilityId;
	private String hub;
	private String itemDesc;
	private BigDecimal itemId;
	private BigDecimal leadTimeDays;
	//	private String 		maxCountDatetime;
	private Date maxCountDatetime;
	private String mfgLot;
	private String orderingApplication;
	private String qcDoc;
	private BigDecimal qtyAvailableAfterAlloc;
	private BigDecimal qtyIssuedAfterCount;
	private BigDecimal receiptId;
	private BigDecimal reorderPoint;
	private BigDecimal stockingLevel;
	private BigDecimal totalQuantity;
	private BigDecimal unitCost;
	private String useApplication;
	private BigDecimal reorderQuantity;
	private BigDecimal kanbanReorderQuantity;

	private String cpigStatus;
	private String inventoryGroup;
	private String catalogCompanyId;
	private BigDecimal partGroupNo;
	private String materialIdString;
    private String areaName;
    private String buildingName;
    private String tierIIStorage;
    
    private String tierIiTemperature;
    private String tierIiPressure;
    private String physicalState;
    private String packaging;
    private String qualityTrackingNumber;

    private BigDecimal qtyTransferredAfterCount;

    //constructor
	public CabinetInventoryBean() {
	}

	public BigDecimal getBinId() {
		return binId;
	}

	public BigDecimal getCabinetId() {
		return cabinetId;
	}

	public String getCabinetName() {
		return cabinetName;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public Date getCountDatetime() {
		return countDatetime;
	}

	public String getCountedByName() {
		return countedByName;
	}

	public BigDecimal getCountedByPersonnelId() {
		return countedByPersonnelId;
	}

	public BigDecimal getCountQuantity() {
		return countQuantity;
	}

	public String getCountType() {
		return countType;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public Date getDateProcessed() {
		return dateProcessed;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getHub() {
		return hub;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getLeadTimeDays() {
		return leadTimeDays;
	}

	public Date getMaxCountDatetime() {
		return maxCountDatetime;
	}

	public String getMfgLot() {
		return mfgLot;
	}

	public String getOrderingApplication() {
		return orderingApplication;
	}

	public String getQcDoc() {
		return qcDoc;
	}

	public BigDecimal getQtyAvailableAfterAlloc() {
		return qtyAvailableAfterAlloc;
	}

	public BigDecimal getQtyIssuedAfterCount() {
		return qtyIssuedAfterCount;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public BigDecimal getReorderPoint() {
		return reorderPoint;
	}

	public BigDecimal getStockingLevel() {
		return stockingLevel;
	}

	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public String getUseApplication() {
		return useApplication;
	}

	public void setBinId(BigDecimal binId) {
		this.binId = binId;
	}

	public void setCabinetId(BigDecimal cabinetId) {
		this.cabinetId = cabinetId;
	}

	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCountDatetime(Date countDatetime) {
		this.countDatetime = countDatetime;
	}

	public void setCountedByName(String countedByName) {
		this.countedByName = countedByName;
	}

	public void setCountedByPersonnelId(BigDecimal countedByPersonnelId) {
		this.countedByPersonnelId = countedByPersonnelId;
	}

	public void setCountQuantity(BigDecimal countQuantity) {
		this.countQuantity = countQuantity;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public void setDateProcessed(Date dateProcessed) {
		this.dateProcessed = dateProcessed;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setLeadTimeDays(BigDecimal leadTimeDays) {
		this.leadTimeDays = leadTimeDays;
	}

	public void setMaxCountDatetime(Date maxCountDatetime) {
		this.maxCountDatetime = maxCountDatetime;
	}

	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}

	public void setOrderingApplication(String orderingApplication) {
		this.orderingApplication = orderingApplication;
	}

	public void setQcDoc(String qcDoc) {
		this.qcDoc = qcDoc;
	}

	public void setQtyAvailableAfterAlloc(BigDecimal qtyAvailableAfterAlloc) {
		this.qtyAvailableAfterAlloc = qtyAvailableAfterAlloc;
	}

	public void setQtyIssuedAfterCount(BigDecimal qtyIssuedAfterCount) {
		this.qtyIssuedAfterCount = qtyIssuedAfterCount;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public void setReorderPoint(BigDecimal reorderPoint) {
		this.reorderPoint = reorderPoint;
	}

	public void setStockingLevel(BigDecimal stockingLevel) {
		this.stockingLevel = stockingLevel;
	}

	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public void setUseApplication(String useApplication) {
		this.useApplication = useApplication;
	}

	public BigDecimal getReorderQuantity() {
		return reorderQuantity;
	}

	public void setReorderQuantity(BigDecimal reorderQuantity) {
		this.reorderQuantity = reorderQuantity;
	}

	public BigDecimal getKanbanReorderQuantity() {
		return kanbanReorderQuantity;
	}

	public void setKanbanReorderQuantity(BigDecimal kanbanReorderQuantity) {
		this.kanbanReorderQuantity = kanbanReorderQuantity;
	}

	public String getCpigStatus() {
		return cpigStatus;
	}

	public void setCpigStatus(String cpigStatus) {
		this.cpigStatus = cpigStatus;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}

	public String getMaterialIdString() {
		return materialIdString;
	}

	public void setMaterialIdString(String materialIdString) {
		this.materialIdString = materialIdString;
	}

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public String getPhysicalState() {
		return physicalState;
	}

	public void setPhysicalState(String physicalState) {
		this.physicalState = physicalState;
	}

	public String getTierIiPressure() {
		return tierIiPressure;
	}

	public void setTierIiPressure(String tierIiPressure) {
		this.tierIiPressure = tierIiPressure;
	}

	public String getTierIIStorage() {
		return tierIIStorage;
	}

	public void setTierIIStorage(String tierIIStorage) {
		this.tierIIStorage = tierIIStorage;
	}

	public String getTierIiTemperature() {
		return tierIiTemperature;
	}

	public void setTierIiTemperature(String tierIiTemperature) {
		this.tierIiTemperature = tierIiTemperature;
	}

	public String getQualityTrackingNumber() {
		return qualityTrackingNumber;
	}

	public void setQualityTrackingNumber(String qualityTrackingNumber) {
		this.qualityTrackingNumber = qualityTrackingNumber;
	}

    public BigDecimal getQtyTransferredAfterCount() {
        return qtyTransferredAfterCount;
    }

    public void setQtyTransferredAfterCount(BigDecimal qtyTransferredAfterCount) {
        this.qtyTransferredAfterCount = qtyTransferredAfterCount;
    }
}