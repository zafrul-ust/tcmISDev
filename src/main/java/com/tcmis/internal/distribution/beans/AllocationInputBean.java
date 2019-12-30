package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: AllocationDetailBean <br>
 * @version: 1.0, Sep 7, 2009 <br>
 *****************************************************************************/


public class AllocationInputBean extends BaseDataBean {

// from page
	private String companyId;	
	private String facilityId;
	private BigDecimal itemId;
	private String refInventoryGroup;	
	private String inventoryGroup;	
	private String specList;	
	private String shipToCompanyId;
	private String shipToLocationId;
	private String billToCompanyId;
	private String billToLocationId;
	private String opsEntityId;
	private String opsCompanyId;
	private String remainingShelfLifePercent;
	
	private String shipComplete;
	private String consolidateShipment;
	private String currencyId;
	private String orderPrNumber;
	private String scratchPadLineItem;
	private String searchKey;
	private String priceGroupId;
	private String incoTerms;
	
	private String specDetailList;
	private String specLibraryList;
	private String specCocList;
	private String specCoaList;

	private String itemType;
	private String unitOfMeasure;
	private String unitsPerItem;
	private Date needDate;
	private String forceSpecHold;
	private String forceQualityHold;
    private String scrap;
    private Date promisedDate;
    private BigDecimal replenishQty;
    
    private String partDesc;
	private String mrNumber;
	private String mrLineItem;
	private String application;
	private String catPartNo;
	private String catalogCompanyId;
	private String catalogId;
	private String partGroupNo;
	
    public void setApplication(String application) {
        this.application = application;
    }
	public String getApplication() {
		return application;
	}
    public void setCatPartNo(String catPartNo) {
        this.catPartNo = catPartNo;
    }
    
	public String getCatPartNo() {
		return catPartNo;
	}
    public void setCatalogCompanyId(String catalogCompanyId) {
        this.catalogCompanyId = catalogCompanyId;
    }
    
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }
    
	public String getCatalogId() {
		return catalogId;
	}
    public void setPartGroupNo(String partGroupNo) {
        this.partGroupNo = partGroupNo;
    }
	public String getPartGroupNo() {
		return partGroupNo;
	}
	
    public void setMrNumber(String mrNumber) {
        this.mrNumber = mrNumber;
    }
    public void setMrLineItem(String mrLineItem) {
        this.mrLineItem = mrLineItem;
    }
    
	public String getMrNumber() {
		return mrNumber;
	}
	public String getMrLineItem() {
		return mrLineItem;
	}
    public BigDecimal getReplenishQty() {
		return replenishQty;
	}
	public void setReplenishQty(BigDecimal replenishQty) {
		this.replenishQty = replenishQty;
	}

    public String getScrap() {
        return scrap;
    }

    public void setScrap(String scrap) {
        this.scrap = scrap;
    }

    public String getForceSpecHold() {
		return forceSpecHold;
	}
	public void setForceSpecHold(String forceSpecHold) {
		this.forceSpecHold = forceSpecHold;
	}
	public String getForceQualityHold() {
		return forceQualityHold;
	}
	public void setForceQualityHold(String forceQualityHold) {
		this.forceQualityHold = forceQualityHold;
	}
	public String getIncoTerms() {
		return incoTerms;
	}
	public void setIncoTerms(String incoTerms) {
		this.incoTerms = incoTerms;
	}
	public String getPriceGroupId() {
		return priceGroupId;
	}
	public void setPriceGroupId(String priceGroupId) {
		this.priceGroupId = priceGroupId;
	}

	public String getScratchPadLineItem() {
		return scratchPadLineItem;
	}

	public void setScratchPadLineItem(String scratchPadLineItem) {
		this.scratchPadLineItem = scratchPadLineItem;
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

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public String getRefInventoryGroup() {
		return refInventoryGroup;
	}

	public void setRefInventoryGroup(String refInventoryGroup) {
		this.refInventoryGroup = refInventoryGroup;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public String getSpecList() {
		return specList;
	}

	public void setSpecList(String specList) {
		this.specList = specList;
	}

	public String getShipToCompanyId() {
		return shipToCompanyId;
	}

	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}

	public String getShipToLocationId() {
		return shipToLocationId;
	}

	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}

	public String getBillToCompanyId() {
		return billToCompanyId;
	}

	public void setBillToCompanyId(String billToCompanyId) {
		this.billToCompanyId = billToCompanyId;
	}

	public String getBillToLocationId() {
		return billToLocationId;
	}

	public void setBillToLocationId(String billToLocationId) {
		this.billToLocationId = billToLocationId;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public String getOpsCompanyId() {
		return opsCompanyId;
	}

	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}

	public String getRemainingShelfLifePercent() {
		return remainingShelfLifePercent;
	}

	public void setRemainingShelfLifePercent(String remainingShelfLifePercent) {
		this.remainingShelfLifePercent = remainingShelfLifePercent;
	}

	public String getShipComplete() {
		return shipComplete;
	}

	public void setShipComplete(String shipComplete) {
		this.shipComplete = shipComplete;
	}

	public String getConsolidateShipment() {
		return consolidateShipment;
	}

	public void setConsolidateShipment(String consolidateShipment) {
		this.consolidateShipment = consolidateShipment;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getOrderPrNumber() {
		return orderPrNumber;
	}

	public void setOrderPrNumber(String orderPrNumber) {
		this.orderPrNumber = orderPrNumber;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public String getSpecCoaList() {
		return specCoaList;
	}
	public void setSpecCoaList(String specCoaList) {
		this.specCoaList = specCoaList;
	}
	public String getSpecCocList() {
		return specCocList;
	}
	public void setSpecCocList(String specCocList) {
		this.specCocList = specCocList;
	}
	public String getSpecDetailList() {
		return specDetailList;
	}
	public void setSpecDetailList(String specDetailList) {
		this.specDetailList = specDetailList;
	}
	public String getSpecLibraryList() {
		return specLibraryList;
	}
	public void setSpecLibraryList(String specLibraryList) {
		this.specLibraryList = specLibraryList;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	public String getUnitsPerItem() {
		return unitsPerItem;
	}
	public void setUnitsPerItem(String unitsPerItem) {
		this.unitsPerItem = unitsPerItem;
	}
    public Date getNeedDate() {
        return needDate;
    }
    public void setNeedDate(Date needDate) {
        this.needDate = needDate;
    }

	public Date getPromisedDate() {
		return promisedDate;
	}

	public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}

	public String getPartDesc() {
		return partDesc;
	}

	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}
}