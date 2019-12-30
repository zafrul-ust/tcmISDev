package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;
import java.util.Collection;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: BuyPageViewBean <br>
 * @version: 1.0, Mar 15, 2007 <br>
 *****************************************************************************/

public class BuyPageViewBean
    extends BaseDataBean {

  private String frozen;
  private String critical;
  private String notes;
  private String cancelStatus;
  private String requestLineStatus;
  private BigDecimal catalogPrice;
  private BigDecimal baselinePrice;
  private BigDecimal mrQuantity;
  private String requestorFirstName;
  private String requestorLastName;
  private String email;
  private String phone;
  private BigDecimal prNumber;
  private String buyer;
  private Date dateAssigned;
  private BigDecimal itemId;
  private Date needDate;
  private String partId;
  private String itemDesc;
  private String shelfLifeDays;
  private String tradeName;
  private String sizeUnit;
  private String mfgId;
  private String mfgPartNo;
  private String itemType;
  private BigDecimal orderQuantity;
  private String uom;
  private BigDecimal priority;
  private BigDecimal radianPo;
  private String facility;
  private String raytheonPo;
  private String facilityId;
  private String branchPlant;
  private String homeCurrencyId;
  private Date dateIssued;
  private Date datePoCreated;
  private String status;
  private Date dateChanged;
  private String comments;
  private String companyId;
  private BigDecimal mrNumber;
  private String mrLineItem;
  private BigDecimal reorderPoint;
  private BigDecimal stockingLevel;
  private BigDecimal availableQuantity;
  private BigDecimal openPoQuantity;
  private String shipToDeliveryPoint;
  private String shipToLocationId;
  private String shipToCompanyId;
  private BigDecimal buyerId;
  private String lastSupplier;
  private String buyerName;
  private String everConfirmed;
  private String unconfirmed;
  private String deliveryType;
  private String poInJde;
  private String lpp;
  private String engineeringEvaluation;
  private BigDecimal requestId;
  private String catalogId;
  private String stocked;
  private String inventoryGroup;
  private String customerPoNumber;
  private String releaseNumber;
  private String bpoDetail;
  private String bpo;
  private String supplyPath;
  private String deliveryPointDesc;
  private String consolidationAllowed;
  private BigDecimal purchasingUnitsPerItem;
  private String purchasingUnitOfMeasure;
  private String buyerCompanyId;
  private String surplusInventory;
  private String buypageAssignable;
  private String lockStatus;
  private String hubName;
  private Collection buyerDropDown;
  private String itemDescShort;
  private String sizeUnitShort;
  private String notesShort;
  private String lastSupplierName;
  private String preferredSupplier;
  private String preferredSupplierName;
  private String selectedSupplier;
  private String selectedSupplierName;
  private String currentSupplier;
  private String currentSupplierName;
  private String opsEntityId;
  private String inventoryGroupName;
  
  private String shiptoNote;
  private String prInternalNote;
  private String lineInternalNote;
  private String linePurchasingNote;
  private String specList;
  private String csrName;
  
  private String shipToLocationDesc;
  private Date promiseDate;
  
  private String itemItemType;
  private Date releaseDate;

  private BigDecimal lastUpdatedBy;
  private Date lastUpdatedDate;
  private BigDecimal activeDbuyCount;
  private String buyType;
  private String buyTypeFlag;

public BigDecimal getActiveDbuyCount() {
	return activeDbuyCount;
}

public void setActiveDbuyCount(BigDecimal activeDbuyCount) {
	this.activeDbuyCount = activeDbuyCount;
}

public BigDecimal getLastUpdatedBy() {
	return lastUpdatedBy;
}

public void setLastUpdatedBy(BigDecimal lastUpdatedBy) {
	this.lastUpdatedBy = lastUpdatedBy;
}

public Date getLastUpdatedDate() {
	return lastUpdatedDate;
}
  
public void setLastUpdatedDate(Date lastUpdatedDate) {
	this.lastUpdatedDate = lastUpdatedDate;
}

private boolean reachAnnexXiv;


public String getNotesShort() {
    if (notes != null &&  notes.length() > 100)
    {
        return notes.substring(0,100)+"...";
    }
    else
    {
        return notes;
    }
 }

 public String getItemDescShort() {
    if (itemDesc != null &&  itemDesc.length() > 200)
    {
        return itemDesc.substring(0,200)+"...";
    }
    else
    {
        return itemDesc;
    }
}

 public String getSizeUnitShort() {
    if (sizeUnit != null &&  sizeUnit.length() > 100)
    {
        return sizeUnit.substring(0,100)+"...";
    }
    else
    {
        return sizeUnit;
    }
}
    
  public String getHubName() {
	return hubName;
}

public void setHubName(String hubName) {
	this.hubName = hubName;
}

//constructor
  public BuyPageViewBean() {
  }

  //setters
  public void setFrozen(String frozen) {
    this.frozen = frozen;
  }

  public void setCritical(String critical) {
    this.critical = critical;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public void setCancelStatus(String cancelStatus) {
    this.cancelStatus = cancelStatus;
  }

  public void setRequestLineStatus(String requestLineStatus) {
    this.requestLineStatus = requestLineStatus;
  }

  public void setCatalogPrice(BigDecimal catalogPrice) {
    this.catalogPrice = catalogPrice;
  }

  public void setBaselinePrice(BigDecimal baselinePrice) {
    this.baselinePrice = baselinePrice;
  }

  public void setMrQuantity(BigDecimal mrQuantity) {
    this.mrQuantity = mrQuantity;
  }

  public void setRequestorFirstName(String requestorFirstName) {
    this.requestorFirstName = requestorFirstName;
  }

  public void setRequestorLastName(String requestorLastName) {
    this.requestorLastName = requestorLastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setPrNumber(BigDecimal prNumber) {
    this.prNumber = prNumber;
  }

  public void setBuyer(String buyer) {
    this.buyer = buyer;
  }

  public void setDateAssigned(Date dateAssigned) {
    this.dateAssigned = dateAssigned;
  }

  public void setItemId(BigDecimal itemId) {
    this.itemId = itemId;
  }

  public void setNeedDate(Date needDate) {
    this.needDate = needDate;
  }

  public void setPartId(String partId) {
    this.partId = partId;
  }

  public void setItemDesc(String itemDesc) {
    this.itemDesc = itemDesc;
  }

  public void setShelfLifeDays(String shelfLifeDays) {
    this.shelfLifeDays = shelfLifeDays;
  }

  public void setTradeName(String tradeName) {
    this.tradeName = tradeName;
  }

  public void setSizeUnit(String sizeUnit) {
    this.sizeUnit = sizeUnit;
  }

  public void setMfgId(String mfgId) {
    this.mfgId = mfgId;
  }

  public void setMfgPartNo(String mfgPartNo) {
    this.mfgPartNo = mfgPartNo;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }

  public void setOrderQuantity(BigDecimal orderQuantity) {
    this.orderQuantity = orderQuantity;
  }

  public void setUom(String uom) {
    this.uom = uom;
  }

  public void setPriority(BigDecimal priority) {
    this.priority = priority;
  }

  public void setRadianPo(BigDecimal radianPo) {
    this.radianPo = radianPo;
  }

  public void setFacility(String facility) {
    this.facility = facility;
  }

  public void setRaytheonPo(String raytheonPo) {
    this.raytheonPo = raytheonPo;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setBranchPlant(String branchPlant) {
    this.branchPlant = branchPlant;
  }

  public void setHomeCurrencyId(String homeCurrencyId) {
    this.homeCurrencyId = homeCurrencyId;
  }

  public void setDateIssued(Date dateIssued) {
    this.dateIssued = dateIssued;
  }

  public void setDatePoCreated(Date datePoCreated) {
    this.datePoCreated = datePoCreated;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setDateChanged(Date dateChanged) {
    this.dateChanged = dateChanged;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setMrNumber(BigDecimal mrNumber) {
    this.mrNumber = mrNumber;
  }

  public void setMrLineItem(String mrLineItem) {
    this.mrLineItem = mrLineItem;
  }

  public void setReorderPoint(BigDecimal reorderPoint) {
    this.reorderPoint = reorderPoint;
  }

  public void setStockingLevel(BigDecimal stockingLevel) {
    this.stockingLevel = stockingLevel;
  }

  public void setAvailableQuantity(BigDecimal availableQuantity) {
    this.availableQuantity = availableQuantity;
  }

  public void setOpenPoQuantity(BigDecimal openPoQuantity) {
    this.openPoQuantity = openPoQuantity;
  }

  public void setShipToDeliveryPoint(String shipToDeliveryPoint) {
    this.shipToDeliveryPoint = shipToDeliveryPoint;
  }

  public void setShipToLocationId(String shipToLocationId) {
    this.shipToLocationId = shipToLocationId;
  }

  public void setShipToCompanyId(String shipToCompanyId) {
    this.shipToCompanyId = shipToCompanyId;
  }

  public void setBuyerId(BigDecimal buyerId) {
    this.buyerId = buyerId;
  }

  public void setLastSupplier(String lastSupplier) {
    this.lastSupplier = lastSupplier;
  }

  public void setBuyerName(String buyerName) {
    this.buyerName = buyerName;
  }

  public void setEverConfirmed(String everConfirmed) {
    this.everConfirmed = everConfirmed;
  }

  public void setUnconfirmed(String unconfirmed) {
    this.unconfirmed = unconfirmed;
  }

  public void setDeliveryType(String deliveryType) {
    this.deliveryType = deliveryType;
  }

  public void setPoInJde(String poInJde) {
    this.poInJde = poInJde;
  }

  public void setLpp(String lpp) {
    this.lpp = lpp;
  }

  public void setEngineeringEvaluation(String engineeringEvaluation) {
    this.engineeringEvaluation = engineeringEvaluation;
  }

  public void setRequestId(BigDecimal requestId) {
    this.requestId = requestId;
  }

  public void setCatalogId(String catalogId) {
    this.catalogId = catalogId;
  }

  public void setStocked(String stocked) {
    this.stocked = stocked;
  }

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setCustomerPoNumber(String customerPoNumber) {
    this.customerPoNumber = customerPoNumber;
  }

  public void setReleaseNumber(String releaseNumber) {
    this.releaseNumber = releaseNumber;
  }

  public void setBpoDetail(String bpoDetail) {
    this.bpoDetail = bpoDetail;
  }

  public void setBpo(String bpo) {
    this.bpo = bpo;
  }

  public void setSupplyPath(String supplyPath) {
    this.supplyPath = supplyPath;
  }

  public void setDeliveryPointDesc(String deliveryPointDesc) {
    this.deliveryPointDesc = deliveryPointDesc;
  }

  public void setConsolidationAllowed(String consolidationAllowed) {
    this.consolidationAllowed = consolidationAllowed;
  }

  public void setPurchasingUnitsPerItem(BigDecimal purchasingUnitsPerItem) {
    this.purchasingUnitsPerItem = purchasingUnitsPerItem;
  }

  public void setPurchasingUnitOfMeasure(String purchasingUnitOfMeasure) {
    this.purchasingUnitOfMeasure = purchasingUnitOfMeasure;
  }

  public void setBuyerCompanyId(String buyerCompanyId) {
    this.buyerCompanyId = buyerCompanyId;
  }

  public void setSurplusInventory(String surplusInventory) {
    this.surplusInventory = surplusInventory;
  }

  public void setBuypageAssignable(String buypageAssignable) {
    this.buypageAssignable = buypageAssignable;
  }

  public void setLockStatus(String lockStatus) {
    this.lockStatus = lockStatus;
  }

	public void setLastSupplierName(String lastSupplierName) {
		this.lastSupplierName = lastSupplierName;
	}

	public void setPreferredSupplier(String preferredSupplier) {
		this.preferredSupplier = preferredSupplier;
	}

	public void setPreferredSupplierName(String preferredSupplierName) {
		this.preferredSupplierName = preferredSupplierName;
	}
	
	public void setSelectedSupplier(String selectedSupplier) {
		this.selectedSupplier = selectedSupplier;
	}

	public void setSelectedSupplierName(String selectedSupplierName) {
		this.selectedSupplierName = selectedSupplierName;
	}

	public void setCurrentSupplier(String currentSupplier) {
		this.currentSupplier = currentSupplier;
	}

	public void setCurrentSupplierName(String currentSupplierName) {
		this.currentSupplierName = currentSupplierName;
	}

    public void setOpsEntityId(String opsEntityId) {
        this.opsEntityId = opsEntityId;
    }

    public void setInventoryGroupName(String inventoryGroupName) {
        this.inventoryGroupName = inventoryGroupName;
    }

	public void setSpecList(String specList) {
		this.specList = specList;
	}

	//getters
  public String getFrozen() {
    return frozen;
  }

  public String getCritical() {
    return critical;
  }

  public String getNotes() {
    return notes;
  }

  public String getCancelStatus() {
    return cancelStatus;
  }

  public String getRequestLineStatus() {
    return requestLineStatus;
  }

  public BigDecimal getCatalogPrice() {
    return catalogPrice;
  }

  public BigDecimal getBaselinePrice() {
    return baselinePrice;
  }

  public BigDecimal getMrQuantity() {
    return mrQuantity;
  }

  public String getRequestorFirstName() {
    return requestorFirstName;
  }

  public String getRequestorLastName() {
    return requestorLastName;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public BigDecimal getPrNumber() {
    return prNumber;
  }

  public String getBuyer() {
    return buyer;
  }

  public Date getDateAssigned() {
    return dateAssigned;
  }

  public BigDecimal getItemId() {
    return itemId;
  }

  public Date getNeedDate() {
    return needDate;
  }

  public String getPartId() {
    return partId;
  }

  public String getItemDesc() {
    return itemDesc;
  }

  public String getShelfLifeDays() {
    return shelfLifeDays;
  }

  public String getTradeName() {
    return tradeName;
  }

  public String getSizeUnit() {
    return sizeUnit;
  }

  public String getMfgId() {
    return mfgId;
  }

  public String getMfgPartNo() {
    return mfgPartNo;
  }

  public String getItemType() {
    return itemType;
  }

  public BigDecimal getOrderQuantity() {
    return orderQuantity;
  }

  public String getUom() {
    return uom;
  }

  public BigDecimal getPriority() {
    return priority;
  }

  public BigDecimal getRadianPo() {
    return radianPo;
  }

  public String getFacility() {
    return facility;
  }

  public String getRaytheonPo() {
    return raytheonPo;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getBranchPlant() {
    return branchPlant;
  }

  public String getHomeCurrencyId() {
    return homeCurrencyId;
  }

  public Date getDateIssued() {
    return dateIssued;
  }

  public Date getDatePoCreated() {
    return datePoCreated;
  }

  public String getStatus() {
    return status;
  }

  public Date getDateChanged() {
    return dateChanged;
  }

  public String getComments() {
    return comments;
  }

  public String getCompanyId() {
    return companyId;
  }

  public BigDecimal getMrNumber() {
    return mrNumber;
  }

  public String getMrLineItem() {
    return mrLineItem;
  }

  public BigDecimal getReorderPoint() {
    return reorderPoint;
  }

  public BigDecimal getStockingLevel() {
    return stockingLevel;
  }

  public BigDecimal getAvailableQuantity() {
    return availableQuantity;
  }

  public BigDecimal getOpenPoQuantity() {
    return openPoQuantity;
  }

  public String getShipToDeliveryPoint() {
    return shipToDeliveryPoint;
  }

  public String getShipToLocationId() {
    return shipToLocationId;
  }

  public String getShipToCompanyId() {
    return shipToCompanyId;
  }

  public BigDecimal getBuyerId() {
    return buyerId;
  }

  public String getLastSupplier() {
    return lastSupplier;
  }

  public String getBuyerName() {
    return buyerName;
  }

  public String getEverConfirmed() {
    return everConfirmed;
  }

  public String getUnconfirmed() {
    return unconfirmed;
  }

  public String getDeliveryType() {
    return deliveryType;
  }

  public String getPoInJde() {
    return poInJde;
  }

  public String getLpp() {
    return lpp;
  }

  public String getEngineeringEvaluation() {
    return engineeringEvaluation;
  }

  public BigDecimal getRequestId() {
    return requestId;
  }

  public String getCatalogId() {
    return catalogId;
  }

  public String getStocked() {
    return stocked;
  }

  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public String getCustomerPoNumber() {
    return customerPoNumber;
  }

  public String getReleaseNumber() {
    return releaseNumber;
  }

  public String getBpoDetail() {
    return bpoDetail;
  }

  public String getBpo() {
    return bpo;
  }

  public String getSupplyPath() {
    return supplyPath;
  }

  public String getDeliveryPointDesc() {
    return deliveryPointDesc;
  }

  public String getConsolidationAllowed() {
    return consolidationAllowed;
  }

  public BigDecimal getPurchasingUnitsPerItem() {
    return purchasingUnitsPerItem;
  }

  public String getPurchasingUnitOfMeasure() {
    return purchasingUnitOfMeasure;
  }

  public String getBuyerCompanyId() {
    return buyerCompanyId;
  }

  public String getSurplusInventory() {
    return surplusInventory;
  }

  public String getBuypageAssignable() {
    return this.buypageAssignable;
  }

  public String getLockStatus() {
    return this.lockStatus;
  }

public Collection getBuyerDropDown() {
	return buyerDropDown;
}

public void setBuyerDropDown(Collection buyerDropDown) {
	this.buyerDropDown = buyerDropDown;
}

	public String getLastSupplierName() {
		return lastSupplierName;
	}

	public String getPreferredSupplier() {
		return preferredSupplier;
	}

	public String getPreferredSupplierName() {
		return preferredSupplierName;
	}
	
	public String getSelectedSupplier() {
		return selectedSupplier;
	}

	public String getSelectedSupplierName() {
		return selectedSupplierName;
	}

	public String getCurrentSupplier() {
		return currentSupplier;
	}

	public String getCurrentSupplierName() {
		return currentSupplierName;
	}

    public String getOpsEntityId() {
        return opsEntityId;
    }

    public String getInventoryGroupName() {
        return inventoryGroupName;
    }

	public String getLineInternalNote() {
		return lineInternalNote;
	}

	public void setLineInternalNote(String lineInternalNote) {
		this.lineInternalNote = lineInternalNote;
	}

	public String getLinePurchasingNote() {
		return linePurchasingNote;
	}

	public void setLinePurchasingNote(String linePurchasingNote) {
		this.linePurchasingNote = linePurchasingNote;
	}

	public String getPrInternalNote() {
		return prInternalNote;
	}

	public void setPrInternalNote(String prInternalNote) {
		this.prInternalNote = prInternalNote;
	}

	public String getShiptoNote() {
		return shiptoNote;
	}

	public void setShiptoNote(String shiptoNote) {
		this.shiptoNote = shiptoNote;
	}

	public String getSpecList() {
		return specList;
	}

	public String getCsrName() {
		return csrName;
	}

	public void setCsrName(String csrName) {
		this.csrName = csrName;
	}

	public String getShipToLocationDesc() {
		return shipToLocationDesc;
	}

	public void setShipToLocationDesc(String shipToLocationDesc) {
		this.shipToLocationDesc = shipToLocationDesc;
	}

	public Date getPromiseDate() {
		return promiseDate;
	}

	public void setPromiseDate(Date promiseDate) {
		this.promiseDate = promiseDate;
	}

	public String getItemItemType() {
		return itemItemType;
	}

	public void setItemItemType(String itemItemType) {
		this.itemItemType = itemItemType;
	}
	
	public boolean isMxItem() {
		return "MX".equalsIgnoreCase(this.itemItemType);
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public boolean isReachAnnexXiv() {
		return reachAnnexXiv;
	}

	public void setReachAnnexXiv(boolean reachAnnexXiv) {
		this.reachAnnexXiv = reachAnnexXiv;
	}
	
	public String getBuyType() {
		return buyType;
		//return "TEST 2";
	}
	
	public void setBuyType(String buyType) {
		this.buyType = buyType;
	}

	public String getBuyTypeFlag() {
		return buyTypeFlag;
		//return "Y";
	}

	public void setBuyTypeFlag(String buyTypeFlag) {
		this.buyTypeFlag = buyTypeFlag;
	}
}