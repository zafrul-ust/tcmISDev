package com.tcmis.internal.hub.beans;

import java.util.Collection;
import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PicklistReprintViewBean <br>
 * @version: 1.0, Feb 13, 2008 <br>
 *****************************************************************************/


public class PicklistReprintViewBean extends BaseDataBean {

	private BigDecimal picklistId;
	private String hub;
	private BigDecimal prNumber;
	private String lineItem;
	private String stockingMethod;
	private String deliveryType;
	private String needDatePrefix;
	private Date needDate;
	private BigDecimal pickQty;
	private String application;
	private String facilityId;
	private String partDescription;
	private String packaging;
	private String catalogId;
	private String catPartNo;
	private BigDecimal partGroupNo;
	private String companyId;
	private String deliveryPoint;
	private String shipToLocationId;
	private String requestor;
	private String mrNotes;
	private String critical;
	private String inventoryGroup;
	private String carrierCode;
	private Date pickupTime;
	private BigDecimal stopNumber;
	private String trailerNumber;
	private BigDecimal packingGroupId;
	private String carrierName;
	private String transportationMode;
	private String requiresOverpack;
	private String shippedAsSingle;
	private String shipToLocationDesc;
	private String shipToCity;
	private String shipToStateAbbrev;
	private String shipToZip;
	private String shipToDodaac;
	private String packagedAs;
	private BigDecimal maxUnitOfIssuePerBox;
	private BigDecimal maxUnitOfIssuePerPallet;
	private String consolidationNumber;
    private String ok;
	private String oconusFlag;
	private String bin;
	private BigDecimal mrCount;
	private String transportationPriority;
	private String hazardous;
	private String searchField;
	private String searchMode;
    private String searchArgument;
    private String sortBy;
    private String showOCONUSonly;
    private String showHazardousOnly;
    private BigDecimal splitTcn;
    private String airGroundIndicator;
    private String cms;
    private String distribution;
    private String shippingReference;
    private String trackSerialNumber;
    
    private Collection boxLabels;

//constructor
	public PicklistReprintViewBean() {
	}

	//setters
	public void setPicklistId(BigDecimal picklistId) {
		this.picklistId = picklistId;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setStockingMethod(String stockingMethod) {
		this.stockingMethod = stockingMethod;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public void setNeedDatePrefix(String needDatePrefix) {
		this.needDatePrefix = needDatePrefix;
	}
	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}
	public void setPickQty(BigDecimal pickQty) {
		this.pickQty = pickQty;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
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
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
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
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public void setPickupTime(Date pickupTime) {
		this.pickupTime = pickupTime;
	}
	public void setStopNumber(BigDecimal stopNumber) {
		this.stopNumber = stopNumber;
	}
	public void setTrailerNumber(String trailerNumber) {
		this.trailerNumber = trailerNumber;
	}
	public void setPackingGroupId(BigDecimal packingGroupId) {
		this.packingGroupId = packingGroupId;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setTransportationMode(String transportationMode) {
		this.transportationMode = transportationMode;
	}
	public void setRequiresOverpack(String requiresOverpack) {
		this.requiresOverpack = requiresOverpack;
	}
	public void setShippedAsSingle(String shippedAsSingle) {
		this.shippedAsSingle = shippedAsSingle;
	}
	public void setShipToLocationDesc(String shipToLocationDesc) {
		this.shipToLocationDesc = shipToLocationDesc;
	}
	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}
	public void setShipToStateAbbrev(String shipToStateAbbrev) {
		this.shipToStateAbbrev = shipToStateAbbrev;
	}
	public void setShipToZip(String shipToZip) {
		this.shipToZip = shipToZip;
	}
	public void setShipToDodaac(String shipToDodaac) {
		this.shipToDodaac = shipToDodaac;
	}
	public void setPackagedAs(String packagedAs) {
		this.packagedAs = packagedAs;
	}
	public void setMaxUnitOfIssuePerBox(BigDecimal maxUnitOfIssuePerBox) {
		this.maxUnitOfIssuePerBox = maxUnitOfIssuePerBox;
	}
	public void setMaxUnitOfIssuePerPallet(BigDecimal maxUnitOfIssuePerPallet) {
		this.maxUnitOfIssuePerPallet = maxUnitOfIssuePerPallet;
	}
	public void setConsolidationNumber(String consolidationNumber) {
		this.consolidationNumber = consolidationNumber;
	}
  public void setOk(String ok) {
    this.ok = ok;
  }
	public void setOconusFlag(String oconusFlag) {
		this.oconusFlag = oconusFlag;
	}

  //getters
	public BigDecimal getPicklistId() {
		return picklistId;
	}
	public String getHub() {
		return hub;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getStockingMethod() {
		return stockingMethod;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public String getNeedDatePrefix() {
		return needDatePrefix;
	}
	public Date getNeedDate() {
		return needDate;
	}
	public BigDecimal getPickQty() {
		return pickQty;
	}
	public String getApplication() {
		return application;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public String getPackaging() {
		return packaging;
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
	public String getCompanyId() {
		return companyId;
	}
	public String getDeliveryPoint() {
		return deliveryPoint;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getRequestor() {
		return requestor;
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
	public String getCarrierCode() {
		return carrierCode;
	}
	public Date getPickupTime() {
		return pickupTime;
	}
	public BigDecimal getStopNumber() {
		return stopNumber;
	}
	public String getTrailerNumber() {
		return trailerNumber;
	}
	public BigDecimal getPackingGroupId() {
		return packingGroupId;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getTransportationMode() {
		return transportationMode;
	}
	public String getRequiresOverpack() {
		return requiresOverpack;
	}
	public String getShippedAsSingle() {
		return shippedAsSingle;
	}
	public String getShipToLocationDesc() {
		return shipToLocationDesc;
	}
	public String getShipToCity() {
		return shipToCity;
	}
	public String getShipToStateAbbrev() {
		return shipToStateAbbrev;
	}
	public String getShipToZip() {
		return shipToZip;
	}
  public String getShipToDodaac() {
    return shipToDodaac;
  }
  public String getPackagedAs() {
    return packagedAs;
  }
  public BigDecimal getMaxUnitOfIssuePerBox() {
    return maxUnitOfIssuePerBox;
  }
  public BigDecimal getMaxUnitOfIssuePerPallet() {
    return maxUnitOfIssuePerPallet;
  }
  public String getConsolidationNumber() {
    return consolidationNumber;
  }
  public String getOk() {
    return ok;
  }

  public String getOconusFlag() {
    return oconusFlag;
  }

public String getBin() {
	return bin;
}

public void setBin(String bin) {
	this.bin = bin;
}

public BigDecimal getMrCount() {
	return mrCount;
}

public void setMrCount(BigDecimal mrCount) {
	this.mrCount = mrCount;
}

public String getHazardous() {
	return hazardous;
}

public void setHazardous(String hazardous) {
	this.hazardous = hazardous;
}

public String getTransportationPriority() {
	return transportationPriority;
}

public void setTransportationPriority(String transportationPriority) {
	this.transportationPriority = transportationPriority;
}

public String getSearchArgument() {
	return searchArgument;
}

public void setSearchArgument(String searchArgument) {
	this.searchArgument = searchArgument;
}

public String getSearchField() {
	return searchField;
}

public void setSearchField(String searchField) {
	this.searchField = searchField;
}

public String getSearchMode() {
	return searchMode;
}

public void setSearchMode(String searchMode) {
	this.searchMode = searchMode;
}

public String getShowHazardousOnly() {
	return showHazardousOnly;
}

public void setShowHazardousOnly(String showHazardousOnly) {
	this.showHazardousOnly = showHazardousOnly;
}

public String getShowOCONUSonly() {
	return showOCONUSonly;
}

public void setShowOCONUSonly(String showOCONUSonly) {
	this.showOCONUSonly = showOCONUSonly;
}

public String getSortBy() {
	return sortBy;
}

public void setSortBy(String sortBy) {
	this.sortBy = sortBy;
}

public BigDecimal getSplitTcn() {
	return splitTcn;
}

public void setSplitTcn(BigDecimal splitTcn) {
	this.splitTcn = splitTcn;
}

public String getAirGroundIndicator() {
	return airGroundIndicator;
}

public void setAirGroundIndicator(String airGroundIndicator) {
	this.airGroundIndicator = airGroundIndicator;
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

public String getShippingReference() {
	return shippingReference;
}

public void setShippingReference(String shippingReference) {
	this.shippingReference = shippingReference;
}

public String getTrackSerialNumber() {
	return this.trackSerialNumber;
}

public boolean isSerialNumberTracked() {
	return this.trackSerialNumber != null && this.trackSerialNumber.toUpperCase().equals("Y"); 
}

public void setTrackSerialNumber(String trackSerialNumber) {
	this.trackSerialNumber = trackSerialNumber;
}

public Collection getBoxLabels() {
	return boxLabels;
}

public void setBoxLabels (Collection boxLabels) {
	this.boxLabels = boxLabels;
}

}