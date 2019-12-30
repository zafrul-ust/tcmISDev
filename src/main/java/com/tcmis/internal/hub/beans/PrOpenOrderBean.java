package com.tcmis.internal.hub.beans;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: PrOpenOrderBean <br>
 * @version: 1.0, Sep 26, 2006 <br>
 *****************************************************************************/

public class PrOpenOrderBean
    extends BaseDataBean {

  private String supplier;
  private Date originalPromisedDate;
  private String requestorLastName;
  private String requestorFirstName;
  private String companyId;
  private Date releaseDate;
  private Date requiredDatetime;
  private Date systemRequiredDatetime;
  private String delay;
  private BigDecimal prNumber;
  private String lineItem;
  private BigDecimal openQuantity;
  private String allocationType;
  private BigDecimal refNo;
  private BigDecimal refLine;
  private BigDecimal allocQuantity;
  private String refStatus;
  private String progressStatus;
  private Date refDate;
  private String facPartNo;
  private String sourceHub;
  private String facilityId;
  private BigDecimal requestor;
  private String itemType;
  private BigDecimal itemId;
  private String hazmatIdMissing;
  private BigDecimal qtyOnHand;
  private BigDecimal qtyAvailable;
  private BigDecimal igQtyOnHand;
  private BigDecimal igQtyAvailable;
  private Date requiredDatetimeSort;
  private String notes;
  private String mrNotes;
  private String critical;
  private String inventoryGroup;
  private String pickable;
  private String mfgLot;
  private String application;
  private String applicationDesc;
  private BigDecimal lotStatusAge;
  private BigDecimal orderQuantity;
  private String partDescription;
  private String deliveryType;
  private BigDecimal lookAheadDays;
  private String requiredDatetimeType;
  private String requestLineStatus;
  private String cancelStatus;
  private String ownerInventoryGroup;
  private String oconus;
  private String fobHub;
  private String notesShort;
  private String globalCatalog;
  private String alternateName;
  private String catalogItemDescription;
  
  private BigDecimal customerId;
  private String customerName;
  private String facilityName;
  private String inventoryGroupName;
  private String ownerInventoryGroupName;
  private String materialRequestOrigin;
  
  private String shipToLocationId;
  private String priceGroupId;
  private String billToCompanyId;
  private String billToLocationId;
  private String incoTerms;
  private String unitOfSale;
  private String shipComplete;
  private String consolidateShipment;
  private String currencyId;
  private String opsEntityId;
  private String opsCompanyId;
  private String labelSpec;
  private String specList;
  private String specListConcat;
  private String specLibraryConcat;
  private String specDetailConcat;
  private String specCocConcat;
  private String specCoaConcat;
  
  private String shipToCompanyId;
  private BigDecimal remainingShelfLifePercent;
  private Date promisedDate;
  
  private String cms;
  private String distribution;
  private String shippingReference;
  private String customerServiceRepName;
  private String releaseStatus;
  private String distCustomerPartList;

  private BigDecimal catalogPrice;
  private BigDecimal totalOpenValue;

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public String getCustomerServiceRepName() {
     return customerServiceRepName;
 }

 public void setCustomerServiceRepName(String customerServiceRepName) {
    this.customerServiceRepName = customerServiceRepName;
 }

    public String getShippingReference() {
	return shippingReference;
}

public void setShippingReference(String shippingReference) {
	this.shippingReference = shippingReference;
}

	public Date getPromisedDate() {
	return promisedDate;
}

public void setPromisedDate(Date promisedDate) {
	this.promisedDate = promisedDate;
}

public BigDecimal getRemainingShelfLifePercent() {
	return remainingShelfLifePercent;
}

public void setRemainingShelfLifePercent(BigDecimal remainingShelfLifePercent) {
	this.remainingShelfLifePercent = remainingShelfLifePercent;
}

public String getShipToCompanyId() {
	return shipToCompanyId;
}

public void setShipToCompanyId(String shipToCompanyId) {
	this.shipToCompanyId = shipToCompanyId;
}

	public String getGlobalCatalog() {
        return globalCatalog;
    }

    public void setGlobalCatalog(String globalCatalog) {
        this.globalCatalog = globalCatalog;
    }

    public String getAlternateName() {
        return alternateName;
    }

    public void setAlternateName(String alternateName) {
        this.alternateName = alternateName;
    }

    public String getCatalogItemDescription() {
        return catalogItemDescription;
    }

    public void setCatalogItemDescription(String catalogItemDescription) {
        this.catalogItemDescription = catalogItemDescription;
    }

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
    
  private Collection neededDateBeanCollection = new Vector();
  private Collection itemBeanCollection = new Vector();

  //counter used for jsp
  private BigDecimal counter = new BigDecimal("0");

  //constructor
  public PrOpenOrderBean() {
  }

  public String getOconus() {
      return oconus;
  }

  public void setOconus(String oconus) {
      this.oconus = oconus;
  }

  public String getFobHub() {
      return fobHub;
  }

  public void setFobHub(String fobHub) {
      this.fobHub = fobHub;
  }

  //setters
  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public void setOriginalPromisedDate(Date originalPromisedDate) {
    this.originalPromisedDate = originalPromisedDate;
  }

  public void setRequestorLastName(String requestorLastName) {
    this.requestorLastName = requestorLastName;
  }

  public void setRequestorFirstName(String requestorFirstName) {
    this.requestorFirstName = requestorFirstName;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
  }

  public void setRequiredDatetime(Date requiredDatetime) {
    this.requiredDatetime = requiredDatetime;
  }

  public void setSystemRequiredDatetime(Date systemRequiredDatetime) {
    this.systemRequiredDatetime = systemRequiredDatetime;
  }

  public void setDelay(String delay) {
    this.delay = delay;
  }

  public void setPrNumber(BigDecimal prNumber) {
    this.prNumber = prNumber;
  }

  public void setLineItem(String lineItem) {
    this.lineItem = lineItem;
  }

  public void setOpenQuantity(BigDecimal openQuantity) {
    this.openQuantity = openQuantity;
  }

  public void setAllocationType(String allocationType) {
    this.allocationType = allocationType;
  }

  public void setRefNo(BigDecimal refNo) {
    this.refNo = refNo;
  }

  public void setRefLine(BigDecimal refLine) {
    this.refLine = refLine;
  }

  public void setAllocQuantity(BigDecimal allocQuantity) {
    this.allocQuantity = allocQuantity;
  }

  public void setRefStatus(String refStatus) {
    this.refStatus = refStatus;
  }

  public void setProgressStatus(String progressStatus) {
    this.progressStatus = progressStatus;
  }

  public void setRefDate(Date refDate) {
    this.refDate = refDate;
  }

  public void setFacPartNo(String facPartNo) {
    this.facPartNo = facPartNo;
  }

  public void setSourceHub(String sourceHub) {
    this.sourceHub = sourceHub;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setRequestor(BigDecimal requestor) {
    this.requestor = requestor;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }

  public void setItemId(BigDecimal itemId) {
    this.itemId = itemId;
  }

  public void setHazmatIdMissing(String hazmatIdMissing) {
    this.hazmatIdMissing = hazmatIdMissing;
  }

  public void setQtyOnHand(BigDecimal qtyOnHand) {
    this.qtyOnHand = qtyOnHand;
  }

  public void setQtyAvailable(BigDecimal qtyAvailable) {
    this.qtyAvailable = qtyAvailable;
  }

  public void setIgQtyOnHand(BigDecimal igQtyOnHand) {
    this.igQtyOnHand = igQtyOnHand;
  }

  public void setIgQtyAvailable(BigDecimal igQtyAvailable) {
    this.igQtyAvailable = igQtyAvailable;
  }

  public void setRequiredDatetimeSort(Date requiredDatetimeSort) {
    this.requiredDatetimeSort = requiredDatetimeSort;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public void setMrNotes(String mrNotes) {
    this.mrNotes = mrNotes;
  }

  public void setCritical(String critical) {
    this.critical = critical;
  }

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setPickable(String pickable) {
    this.pickable = pickable;
  }

  public void setMfgLot(String mfgLot) {
    this.mfgLot = mfgLot;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public void setApplicationDesc(String applicationDesc) {
    this.applicationDesc = applicationDesc;
  }

  public void setLotStatusAge(BigDecimal lotStatusAge) {
    this.lotStatusAge = lotStatusAge;
  }

  public void setNeededDateBeanCollection(Collection c) {
    this.neededDateBeanCollection = c;
  }

  public void setItemBeanCollection(Collection c) {
    this.itemBeanCollection = c;
  }

  public void addNeededDateBean(PrOpenOrderBean bean) {
    this.neededDateBeanCollection.add(bean);
  }

  public void addItemBean(PrOpenOrderBean bean) {
    this.itemBeanCollection.add(bean);
  }

  public void setCounter(BigDecimal b) {
    this.counter = b;
  }

  public void setOrderQuantity(BigDecimal b) {
    this.orderQuantity = b;
  }

  public void incrementCounter() {
    this.counter = this.counter.add(new BigDecimal("1"));
  }

  public void setPartDescription(String s) {
    this.partDescription = s;
  }

  public void setDeliveryType(String s) {
    this.deliveryType = s;
  }

  public void setLookAheadDays(BigDecimal b) {
    this.lookAheadDays = b;
  }

  //getters
  public String getSupplier() {
    return supplier;
  }

  public Date getOriginalPromisedDate() {
    return originalPromisedDate;
  }

  public String getRequestorLastName() {
    return requestorLastName;
  }

  public String getRequestorFirstName() {
    return requestorFirstName;
  }

  public String getCompanyId() {
    return companyId;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  public Date getRequiredDatetime() {
    return requiredDatetime;
  }

  public Date getSystemRequiredDatetime() {
    return systemRequiredDatetime;
  }

  public String getDelay() {
    return delay;
  }

  public BigDecimal getPrNumber() {
    return prNumber;
  }

  public String getLineItem() {
    return lineItem;
  }

  public BigDecimal getOpenQuantity() {
    return openQuantity;
  }

  public String getAllocationType() {
    return allocationType;
  }

  public BigDecimal getRefNo() {
    return refNo;
  }

  public BigDecimal getRefLine() {
    return refLine;
  }

  public BigDecimal getAllocQuantity() {
    return allocQuantity;
  }

  public String getRefStatus() {
    return refStatus;
  }

  public String getProgressStatus() {
    return progressStatus;
  }

  public Date getRefDate() {
    return refDate;
  }

  public String getFacPartNo() {
    return facPartNo;
  }

  public String getSourceHub() {
    return sourceHub;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public BigDecimal getRequestor() {
    return requestor;
  }

  public String getItemType() {
    return itemType;
  }

  public BigDecimal getItemId() {
    return itemId;
  }

  public String getHazmatIdMissing() {
    return hazmatIdMissing;
  }

  public BigDecimal getQtyOnHand() {
    return qtyOnHand;
  }

  public BigDecimal getQtyAvailable() {
    return qtyAvailable;
  }

  public BigDecimal getIgQtyOnHand() {
    return igQtyOnHand;
  }

  public BigDecimal getIgQtyAvailable() {
    return igQtyAvailable;
  }

  public Date getRequiredDatetimeSort() {
    return requiredDatetimeSort;
  }

  public String getNotes() {
    return notes;
  }

  public String getMrNotes() {
    return mrNotes;
  }

  public String getCritical() {
    return critical;
  }

  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public String getPickable() {
    return pickable;
  }

  public String getMfgLot() {
    return mfgLot;
  }

  public String getApplication() {
    return application;
  }

  public String getApplicationDesc() {
    return applicationDesc;
  }

  public BigDecimal getLotStatusAge() {
    return lotStatusAge;
  }

  public BigDecimal getOrderQuantity() {
    return this.orderQuantity;
  }

  public String getPartDescription() {
    return this.partDescription;
  }

  public Collection getNeededDateBeanCollection() {
    return this.neededDateBeanCollection;
  }

  public Collection getItemBeanCollection() {
    return this.itemBeanCollection;
  }

  public BigDecimal getCounter() {
    return this.counter;
  }

  public String getDeliveryType() {
    return this.deliveryType;
  }

  public BigDecimal getLookAheadDays() {
    return this.lookAheadDays;
  }

public String getRequiredDatetimeType() {
	return requiredDatetimeType;
}

public void setRequiredDatetimeType(String requiredDatetimeType) {
	this.requiredDatetimeType = requiredDatetimeType;
}

public String getCancelStatus() {
	return cancelStatus;
}

public void setCancelStatus(String cancelStatus) {
	this.cancelStatus = cancelStatus;
}

public String getOwnerInventoryGroup() {
	return ownerInventoryGroup;
}

public void setOwnerInventoryGroup(String ownerInventoryGroup) {
	this.ownerInventoryGroup = ownerInventoryGroup;
}

public String getRequestLineStatus() {
	return requestLineStatus;
}

public void setRequestLineStatus(String requestLineStatus) {
	this.requestLineStatus = requestLineStatus;
}

public BigDecimal getCustomerId() {
	return customerId;
}

public void setCustomerId(BigDecimal customerId) {
	this.customerId = customerId;
}

public String getCustomerName() {
	return customerName;
}

public void setCustomerName(String customerName) {
	this.customerName = customerName;
}

public String getFacilityName() {
	return facilityName;
}

public void setFacilityName(String facilityName) {
	this.facilityName = facilityName;
}

public String getInventoryGroupName() {
	return inventoryGroupName;
}

public void setInventoryGroupName(String inventoryGroupName) {
	this.inventoryGroupName = inventoryGroupName;
}

public String getMaterialRequestOrigin() {
	return materialRequestOrigin;
}

public void setMaterialRequestOrigin(String materialRequestOrigin) {
	this.materialRequestOrigin = materialRequestOrigin;
}

public String getOwnerInventoryGroupName() {
	return ownerInventoryGroupName;
}

public void setOwnerInventoryGroupName(String ownerInventoryGroupName) {
	this.ownerInventoryGroupName = ownerInventoryGroupName;
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

public String getIncoTerms() {
	return incoTerms;
}

public void setIncoTerms(String incoTerms) {
	this.incoTerms = incoTerms;
}

public String getLabelSpec() {
	return labelSpec;
}

public void setLabelSpec(String labelSpec) {
	this.labelSpec = labelSpec;
}

public String getOpsCompanyId() {
	return opsCompanyId;
}

public void setOpsCompanyId(String opsCompanyId) {
	this.opsCompanyId = opsCompanyId;
}

public String getOpsEntityId() {
	return opsEntityId;
}

public void setOpsEntityId(String opsEntityId) {
	this.opsEntityId = opsEntityId;
}

public String getPriceGroupId() {
	return priceGroupId;
}

public void setPriceGroupId(String priceGroupId) {
	this.priceGroupId = priceGroupId;
}

public String getShipComplete() {
	return shipComplete;
}

public void setShipComplete(String shipComplete) {
	this.shipComplete = shipComplete;
}

public String getShipToLocationId() {
	return shipToLocationId;
}

public void setShipToLocationId(String shipToLocationId) {
	this.shipToLocationId = shipToLocationId;
}

public String getSpecCoaConcat() {
	return specCoaConcat;
}

public void setSpecCoaConcat(String specCoaConcat) {
	this.specCoaConcat = specCoaConcat;
}

public String getSpecCocConcat() {
	return specCocConcat;
}

public void setSpecCocConcat(String specCocConcat) {
	this.specCocConcat = specCocConcat;
}

public String getSpecDetailConcat() {
	return specDetailConcat;
}

public void setSpecDetailConcat(String specDetailConcat) {
	this.specDetailConcat = specDetailConcat;
}

public String getSpecLibraryConcat() {
	return specLibraryConcat;
}

public void setSpecLibraryConcat(String specLibraryConcat) {
	this.specLibraryConcat = specLibraryConcat;
}

public String getSpecList() {
	return specList;
}

public void setSpecList(String specList) {
	this.specList = specList;
}

public String getSpecListConcat() {
	return specListConcat;
}

public void setSpecListConcat(String specListConcat) {
	this.specListConcat = specListConcat;
}

public String getUnitOfSale() {
	return unitOfSale;
}

public void setUnitOfSale(String unitOfSale) {
	this.unitOfSale = unitOfSale;
}

public String getCms() {
	return cms;
}

public void setCms(String cms) {
	this.cms = cms;
}

public String getDistribution() {
	return distribution;
}

public void setDistribution(String distribution) {
	this.distribution = distribution;
}

public String getDistCustomerPartList() {
	return distCustomerPartList;
}

public void setDistCustomerPartList(String distCustomerPartList) {
	this.distCustomerPartList = distCustomerPartList;
}

    public BigDecimal getCatalogPrice() {
        return catalogPrice;
    }

    public void setCatalogPrice(BigDecimal catalogPrice) {
        this.catalogPrice = catalogPrice;
    }

    public BigDecimal getTotalOpenValue() {
        if (catalogPrice != null && openQuantity != null)
            totalOpenValue = openQuantity.multiply(catalogPrice);
        return totalOpenValue;
    }
}