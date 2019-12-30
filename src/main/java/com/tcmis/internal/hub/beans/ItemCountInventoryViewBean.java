package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ItemCountInventoryViewBean <br>
 * @version: 1.0, Nov 3, 2009 <br>
 *****************************************************************************/


public class ItemCountInventoryViewBean extends BaseDataBean {

	private String inventoryGroup;
	private String storageArea;
	private String issueGeneration;
	private BigDecimal itemId;
	private String catalogId;
	private String catPartNo;
	private BigDecimal partGroupNo;
	private String receiptProcessingMethod;
	private String countUom;
	private String itemType;
	private String itemDesc;
	private String packaging;
	private String billingMethod;
	private String applicationDesc;
	private String catalogCompanyId;
	private String companyId;
	private String facilityId;
	private String application;
	private String partOnHand;
	private String partInPurchasing;
	private String itemOnHand;
	private String itemInPurchasing;
	private String areaId;
	private String plantId;
	private String bldgId;
	private String deptId;
	private String processId;
	private BigDecimal percent;
	private String stockingMethod;
	private String receiptProcessingMethodDesc;
	private String inventoryGroupName;
	private String tankName;
	private String allowForceBuy;
	private BigDecimal reorderPoint;
	private BigDecimal stockingLevel;
	private BigDecimal reorderQuantity;
	private Date lastCountDate;
	private String itemPackaging;
	private String pricing;
	private String icmrcStatus;
	private String status;
	private BigDecimal	replenishQty;
	private Date needDate;
	private String oldIcmrcStatus;
	private String okDoUpdate;
	private String comment;
	private String mrNumber;
	private String mrLineItem;
	private String remainingShelfLifePercent;

	//constructor
	public ItemCountInventoryViewBean() {
	}

	//setters
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setStorageArea(String storageArea) {
		this.storageArea = storageArea;
	}
	public void setIssueGeneration(String issueGeneration) {
		this.issueGeneration = issueGeneration;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}
	public void setReceiptProcessingMethod(String receiptProcessingMethod) {
		this.receiptProcessingMethod = receiptProcessingMethod;
	}
	public void setCountUom(String countUom) {
		this.countUom = countUom;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setBillingMethod(String billingMethod) {
		this.billingMethod = billingMethod;
	}
	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setPartOnHand(String partOnHand) {
		this.partOnHand = partOnHand;
	}
	public void setPartInPurchasing(String partInPurchasing) {
		this.partInPurchasing = partInPurchasing;
	}
	public void setItemOnHand(String itemOnHand) {
		this.itemOnHand = itemOnHand;
	}
	public void setItemInPurchasing(String itemInPurchasing) {
		this.itemInPurchasing = itemInPurchasing;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public void setPlantId(String plantId) {
		this.plantId = plantId;
	}
	public void setBldgId(String bldgId) {
		this.bldgId = bldgId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}
	public void setStockingMethod(String stockingMethod) {
		this.stockingMethod = stockingMethod;
	}
	public void setReceiptProcessingMethodDesc(String receiptProcessingMethodDesc) {
		this.receiptProcessingMethodDesc = receiptProcessingMethodDesc;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
	public void setTankName(String tankName) {
		this.tankName = tankName;
	}
	public void setAllowForceBuy(String allowForceBuy) {
		this.allowForceBuy = allowForceBuy;
	}
	public void setReorderPoint(BigDecimal reorderPoint) {
		this.reorderPoint = reorderPoint;
	}
	public void setStockingLevel(BigDecimal stockingLevel) {
		this.stockingLevel = stockingLevel;
	}
	public void setReorderQuantity(BigDecimal reorderQuantity) {
		this.reorderQuantity = reorderQuantity;
	}
	public void setLastCountDate(Date lastCountDate) {
		this.lastCountDate = lastCountDate;
	}
	public void setItemPackaging(String itemPackaging) {
		this.itemPackaging = itemPackaging;
	}
	public void setPricing(String pricing) {
		this.pricing = pricing;
	}
	public void setIcmrcStatus(String icmrcStatus) {
		this.icmrcStatus = icmrcStatus;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    public void setReplenishQty(BigDecimal replenishQty) {
        this.replenishQty = replenishQty;
    }
    public void setNeedDate(Date needDate) {
        this.needDate = needDate;
    }
    public void setOldIcmrcStatus(String oldIcmrcStatus) {
        this.oldIcmrcStatus = oldIcmrcStatus;
    }
    public void setOkDoUpdate(String okDoUpdate) {
        this.okDoUpdate = okDoUpdate;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }    
    public void setMrNumber(String mrNumber) {
        this.mrNumber = mrNumber;
    }
    public void setMrLineItem(String mrLineItem) {
        this.mrLineItem = mrLineItem;
    }
	public void setRemainingShelfLifePercent(String remainingShelfLifePercent) {
		this.remainingShelfLifePercent = remainingShelfLifePercent;
	}
    //getters
	public String getRemainingShelfLifePercent() {
		return remainingShelfLifePercent;
	}
	public String getMrNumber() {
		return mrNumber;
	}
	public String getMrLineItem() {
		return mrLineItem;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getStorageArea() {
		return storageArea;
	}
	public String getIssueGeneration() {
		return issueGeneration;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}
	public String getReceiptProcessingMethod() {
		return receiptProcessingMethod;
	}
	public String getCountUom() {
		return countUom;
	}
	public String getItemType() {
		return itemType;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getPackaging() {
		return packaging;
	}
	public String getBillingMethod() {
		return billingMethod;
	}
	public String getApplicationDesc() {
		return applicationDesc;
	}
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getApplication() {
		return application;
	}
	public String getPartOnHand() {
		return partOnHand;
	}
	public String getPartInPurchasing() {
		return partInPurchasing;
	}
	public String getItemOnHand() {
		return itemOnHand;
	}
	public String getItemInPurchasing() {
		return itemInPurchasing;
	}
	public String getAreaId() {
		return areaId;
	}
	public String getPlantId() {
		return plantId;
	}
	public String getBldgId() {
		return bldgId;
	}
	public String getDeptId() {
		return deptId;
	}
	public String getProcessId() {
		return processId;
	}
	public BigDecimal getPercent() {
		return percent;
	}
	public String getStockingMethod() {
		return stockingMethod;
	}
	public String getReceiptProcessingMethodDesc() {
		return receiptProcessingMethodDesc;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public String getTankName() {
		return tankName;
	}
	public String getAllowForceBuy() {
		return allowForceBuy;
	}
	public BigDecimal getReorderPoint() {
		return reorderPoint;
	}
	public BigDecimal getStockingLevel() {
		return stockingLevel;
	}
	public BigDecimal getReorderQuantity() {
		return reorderQuantity;
	}
	public Date getLastCountDate() {
		return lastCountDate;
	}
	public String getItemPackaging() {
		return itemPackaging;
	}
	public String getPricing() {
		return pricing;
	}
	public String getIcmrcStatus() {
		return icmrcStatus;
	}
	public String getStatus() {
		return status;
	}
    public BigDecimal getReplenishQty() {
        return replenishQty;
    }
    public Date getNeedDate() {
        return needDate;
    }
    public String getOldIcmrcStatus() {
        return oldIcmrcStatus;
    }
    public String getOkDoUpdate() {
        return okDoUpdate;
    }
    public String getComment() {
        return comment;
    }
}